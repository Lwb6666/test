package com.dxjr.wx.login.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.CookieGenerator;

import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.mapper.BlackListMapper;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.LoginCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.security.CookieRetrievingCookieGenerator;
import com.dxjr.security.TicketCryptor;
import com.dxjr.security.UsernamePasswordToken;
import com.dxjr.utils.MD5;
import com.dxjr.wx.login.service.LoginService;
import com.mysql.jdbc.StringUtils;

@Controller
@RequestMapping(value = "/wx/login")
public class LoginController extends BaseController {

	private static final Logger logger = Logger.getLogger(LoginController.class);

	/** 记住用户名 的缓存名字 */
	public static final String COOKIE_LOGIN_USERID = "LOGIN_USERID";

	@Autowired
	private LoginService loginService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private CookieRetrievingCookieGenerator cookieRetrievingCookieGenerator;
	@Autowired
	private BlackListMapper blackListMapper;

	/**
	 * 微信的登录：
	 * <p>
	 * 1.验证获取的用户名和密码 2.带着用户名和密码去登录 3.判断登录结果： a.登录成功，调本地查询对象的方法，查出用户对象放于内存中b.登录失败：跳到对应的页面，显示错误信息
	 * </p>
	 * 
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月18日
	 * @param MemberVo
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return MessageBox
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public Object login(HttpServletResponse response, MemberVo memberVo, String openId, Integer wxId, String ip) {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		MemberVo tempUser = null;
		if (memberVo.getPlatform() == null) {
			map.put("code", "0");
			map.put("message", "平台来源不能为空！");
			return map;
		}
		if (memberVo.getCounter() != null && memberVo.getCounter().intValue() > 2) {
			if (StringUtils.isNullOrEmpty(memberVo.getCheckCode()) && memberVo.getCheckCode().length() != 4) {
				map.put("code", "0");
				map.put("message", "请输入验证码");
				return map;

			}
			String randomCode = (String) currentSession().getAttribute("randomCode");
			if (!memberVo.getCheckCode().equals(randomCode)) {
				map.put("code", "0");
				map.put("message", "验证码不正确");
				return map;
			}
		}
		try {
//			List<BlackListVo> blackListVos = blackListMapper.queryByUserName(memberVo.getUsername().trim());
//			if (null != blackListVos && blackListVos.size() > 0) {
//				for (BlackListVo blackListVo : blackListVos) {
//					if (blackListVo.getType() != null && blackListVo.getType() == 12) {
//						map.put("code", "0");
//						map.put("message", "您的账户有异常，请联系客服");
//						return map;
//					}
//				}
//			}
			UsernamePasswordToken token = null;
			SecurityUtils.getSubject().logout();
			if (StringUtils.isNullOrEmpty(openId) || wxId == null) {
				token = new UsernamePasswordToken(memberVo.getUsername(), MD5.toMD5(memberVo.getLogpassword()), memberVo.getPlatform());
			} else {
				tempUser = loginService.queryMemberByWxId(wxId);
				if (tempUser != null) {
					map.put("bindFlag", "1");
					tempUser.setPlatform(memberVo.getPlatform());
					memberVo = tempUser;
					token = new UsernamePasswordToken(memberVo.getUsername(), memberVo.getLogpassword(), memberVo.getPlatform());
				} else {
					cookieRetrievingCookieGenerator.removeCookie(response);
					if (memberVo.getUsername() == null || memberVo.getLogpassword() == null) {
						map.put("code", "0");
						map.put("message", "用户名或密码错误");
						return map;
					}
					token = new UsernamePasswordToken(memberVo.getUsername(), MD5.toMD5(memberVo.getLogpassword()), memberVo.getPlatform());
				}
			}
			// shiro登录
			Subject subject = SecurityUtils.getSubject();
			if (subject == null) {
				map.put("code", "0");
				map.put("message", "用户名或密码错误");
				return map;
			}
			subject.login(token);

			LoginCnd loginCnd = new LoginCnd();
			loginCnd.setUserId(currentUser().getUserId());
			loginCnd.setUserName(currentUser().getUserName());
			loginCnd.setIp(ip);
			loginCnd.setSessionId(currentSession().getId());
			loginCnd.setPlatform(memberVo.getPlatform());
			// 调用登录逻辑
			MemberVo tempMemberVo = memberMapper.queryMemberByloginname(currentUser().getUserName());
			if (tempMemberVo.getIsFinancialUser() == 0) {
				map.put("code", "0");
				map.put("message", "暂不支持借款用户");
				map.put("userName", currentUser().getUserName());
				return map;
			}

			int blackNum = blackListMapper.queryCountByUserIdAndType(tempMemberVo.getId(), 12);
			if (blackNum > 0) {
				map.put("code", "0");
				map.put("message", "您的账户有异常，请联系客服");
				return map;
			}

			msg = memberService.saveLogin(loginCnd);
			// 用于sso
			cookieRetrievingCookieGenerator.addCookie(currentRequest(), response, TicketCryptor.encrypt(currentUser().getSsoTicket()));
			// 保存cookie
			CookieGenerator cookieGenerator = new CookieGenerator();
			cookieGenerator.setCookieMaxAge(2147483647);
			BigDecimal investTotal = loginService.queryInvestTotalByUserId(currentUser().getUserId());
			map.put("investTotal", investTotal);
			if (tempUser != null) {
				map.put("code", "2");
				map.put("message", "您已绑定账号" + tempUser.getUsername() + "，将使用该账号登录。");
				map.put("userName", currentUser().getUserName());
				return map;
			} else {
				map.put("code", "1");
				map.put("message", msg);
				map.put("userName", currentUser().getUserName());
				map.put("userId", currentUser().getUserId());
				map.put("headImg", currentUser().getHeadImg());
				return map;
			}
		} catch (Exception e) {
			if (isLogin()) {
				SecurityUtils.getSubject().logout();
			}
			logger.error("微信-登录异常", e);
			map.put("code", "0");
			map.put("message", "用户名或密码错误");
			return map;
		}
	}

	/**
	 * 登出
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author ZHUCHEN
	 * @version 0.1 2014年11月3日
	 * @param response
	 * @return MessageBox
	 */
	@RequestMapping(value = "/logout", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public MessageBox logout(HttpServletResponse response) {
		SecurityUtils.getSubject().logout();
		cookieRetrievingCookieGenerator.removeCookie(response);
		return MessageBox.build("1", "您已退出站点，现在将以游客身份浏览该系统。");
	}

	/**
	 * 读取权限
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author ZHUCHEN
	 * @version 0.1 2014年11月3日
	 * @return boolean
	 */
	@RequestMapping(value = "/accessPermission")
	@ResponseBody
	public String accessPermission() {
		if (currentUser() == null)
			return "false";
		else if (currentUser().getIsFinancialUser() != 1)
			return "false";
		return "true";
	}

	/**
	 * <p>
	 * Description:这里是android和ios 一键登录功能<br />
	 * </p>
	 * 
	 * @author 陈建国
	 * @version 0.1 2015年4月15日
	 * @param response
	 * @param memberVo
	 * @param openId
	 * @param wxId
	 * @param ipm
	 * @return Object
	 */
	@RequestMapping(value = "/directLogin")
	@ResponseBody
	public Object directLogin(HttpServletResponse response, MemberVo memberVo, Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		if (memberVo.getPlatform() == null) {
			map.put("code", "0");
			map.put("message", "平台来源不能为空！");
			return map;
		}

		MemberVo memberVoBasic = memberMapper.queryPasswordInfoById(userId);
		try {
//			List<BlackListVo> blackListVos = blackListMapper.queryByUserName(memberVoBasic.getUsername().trim());
//			if (null != blackListVos && blackListVos.size() > 0) {
//				for (BlackListVo blackListVo : blackListVos) {
//					if (blackListVo.getType() != null && blackListVo.getType() == 12) {
//						map.put("code", "0");
//						map.put("message", "您的账户有异常，请联系客服");
//						return map;
//					}
//				}
//			}
			UsernamePasswordToken token = null;
			SecurityUtils.getSubject().logout();
			token = new UsernamePasswordToken(memberVoBasic.getUsername(), memberVoBasic.getLogpassword(), memberVo.getPlatform());
			// shiro登录
			Subject subject = SecurityUtils.getSubject();
			if (subject == null) {
				map.put("code", "0");
				map.put("message", "用户名或密码错误");
				return map;
			}
			subject.login(token);
			LoginCnd loginCnd = new LoginCnd();
			loginCnd.setUserId(currentUser().getUserId());
			loginCnd.setUserName(currentUser().getUserName());
			loginCnd.setSessionId(currentSession().getId());
			loginCnd.setPlatform(memberVo.getPlatform());
			// 调用登录逻辑
			MemberVo tempMemberVo = memberMapper.queryMemberByloginname(currentUser().getUserName());
			if (tempMemberVo.getIsFinancialUser() == 0) {
				map.put("code", "0");
				map.put("message", "暂不支持借款用户");
				map.put("userName", currentUser().getUserName());
				return map;
			}

			int blackNum = blackListMapper.queryCountByUserIdAndType(tempMemberVo.getId(), 12);
			if (blackNum > 0) {
				map.put("code", "0");
				map.put("message", "您的账户有异常，请联系客服");
				return map;
			}

			msg = memberService.saveLogin(loginCnd);
			// 用于sso
			cookieRetrievingCookieGenerator.addCookie(currentRequest(), response, TicketCryptor.encrypt(currentUser().getSsoTicket()));
			// 保存cookie
			CookieGenerator cookieGenerator = new CookieGenerator();
			cookieGenerator.setCookieMaxAge(2147483647);
			map.put("code", "1");
			map.put("message", msg);
			map.put("userName", currentUser().getUserName());
			map.put("userId", currentUser().getUserId());
			return map;
		} catch (Exception e) {
			if (isLogin()) {
				SecurityUtils.getSubject().logout();
			}
			logger.error("微信-登录异常", e);
			map.put("code", "0");
			map.put("message", "用户名或密码错误");
			return map;
		}
	}
}