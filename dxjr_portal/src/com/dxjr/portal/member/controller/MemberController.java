package com.dxjr.portal.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.CookieGenerator;

import com.dxjr.base.entity.Member;
import com.dxjr.common.Dictionary;
import com.dxjr.common.aop.annotation.GenerateToken;
import com.dxjr.common.aop.annotation.ValidateToken;
import com.dxjr.common.aop.exception.AuthenticationException;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.lottery.service.LotteryChanceInfoService;
import com.dxjr.portal.lottery.service.LotteryChanceRuleInfoService;
import com.dxjr.portal.member.mapper.BlackListMapper;
import com.dxjr.portal.member.mapper.MemberRegisterMapper;
import com.dxjr.portal.member.mapper.MobileApproMapper;
import com.dxjr.portal.member.mapper.RealNameApproMapper;
import com.dxjr.portal.member.service.EmailApprService;
import com.dxjr.portal.member.service.MemberRegisterService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.member.util.VerifyContainSpecialChar;
import com.dxjr.portal.member.vo.BlackListVo;
import com.dxjr.portal.member.vo.LoginCnd;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberLoginCnd;
import com.dxjr.portal.member.vo.MemberRegisterCnd;
import com.dxjr.portal.member.vo.MemberRegisterVo;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.MobileApproCnd;
import com.dxjr.portal.member.vo.RealNameApproVo;
import com.dxjr.portal.red.entity.RedAccount;
import com.dxjr.portal.red.mapper.RedAccountLogMapper;
import com.dxjr.portal.red.mapper.RedAccountMapper;
import com.dxjr.portal.red.service.RedAccountService;
import com.dxjr.portal.sms.service.SmsSendService;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.util.CharacterEncoder;
import com.dxjr.portal.util.JsonUtils;
import com.dxjr.portal.util.service.PhoneService;
import com.dxjr.security.CookieRetrievingCookieGenerator;
import com.dxjr.security.ShiroUser;
import com.dxjr.security.TicketCryptor;
import com.dxjr.security.UsernamePasswordToken;
import com.dxjr.utils.CSRFTokenManager;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.MD5;
import com.dxjr.wx.entry.bind.service.BindService;

/**
 * <p>
 * Description:会员action<br />
 * </p>
 * 
 * @title MemberController.java
 * @package com.dxjr.portal.member.action
 * @author justin.xu
 * @version 0.1 2014年4月21日
 */
@Controller
@RequestMapping(value = "/member")
public class MemberController extends BaseController {

	public Logger logger = Logger.getLogger(MemberController.class);
	/** 记住用户名 的缓存名字 */
	public static final String COOKIE_LOGIN_USERID = "LOGIN_USERID";
	@Autowired
	private MemberRegisterService memberRegisterService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private EmailApprService emailApprService;
	@Autowired
	private CookieRetrievingCookieGenerator cookieRetrievingCookieGenerator;
	@Autowired
	private MobileApproService mobileApproServiceImpl;
	@Autowired
	private MobileApproMapper mobileApproMapper;
	@Autowired
	private MemberRegisterMapper memberRegisterMapper;
	@Autowired
	private MobileApproService mobileApproService;
	@Autowired
	private PhoneService phoneService;
	@Autowired
	private RealNameApproMapper realNameApproMapper;
	@Autowired
	private RedAccountMapper redAccountMapper;
	@Autowired
	private RedAccountService redAccountService;

	@Autowired
	private RedAccountLogMapper redAccountLogMapper;
	@Autowired
	private LotteryChanceRuleInfoService lotteryChanceRuleInfoService;
	@Autowired
	private SmsSendService smsSendService;
	@Autowired
	private BindService bindService;
	@Autowired
	private LotteryChanceInfoService lotteryChanceInfoService;
	@Autowired
	private BlackListMapper blackListMapper;
	
	/**
	 * <p>
	 * Description:判断是否移动终端访问官网：www.dxjr.com <br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2015年5月7日
	 * @param ua
	 * @return true:移动终端 | false:PC访问
	 */
	private boolean isMobileTerminal(String ua) {
		return ua.indexOf("iPhone") > -1 || ua.indexOf("iPad") > -1 || (ua.indexOf("Android") > -1 && ua.indexOf("WebKit") > -1);
	}

	/**
	 * <p>
	 * Description:检查用户名或邮箱或推荐人是否存在<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月22日
	 * @param memberRegisterCnd
	 * @return String
	 */
	@RequestMapping(value = "/checkMemberRepeat")
	public @ResponseBody
	String checkMemberRepeat(MemberRegisterCnd memberRegisterCnd, HttpServletRequest request, String inviterName, HttpSession session) {
		String result = "success";
		try {
			ShiroUser shiroUser = currentUser();
			result = memberRegisterService.queryMemberRepeat(memberRegisterCnd, shiroUser.getUserId());
		} catch (Exception e) {
			result = "failer";
			logger.error("验证用户名或邮箱是否存在错误");
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * <p>
	 * Description:检查用户名或邮箱或推荐人是否存在<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月22日
	 * @param memberRegisterCnd
	 * @return String
	 */
	@RequestMapping(value = "/checkMemberRepeatForRegist")
	public @ResponseBody
	MessageBox checkMemberRepeatForRegist(MemberRegisterCnd memberRegisterCnd, HttpServletRequest request, String inviterName, HttpSession session) {
		String result = "success";
		try {
			if (!VerifyContainSpecialChar.isContainSpecialChars(memberRegisterCnd.getUsername())) {
				return new MessageBox("0", "用户名包含特殊字符串");
			}
			result = memberRegisterService.queryMemberRepeat(memberRegisterCnd, null);

		} catch (Exception e) {
			result = "failer";
			logger.error("验证用户名或邮箱是否存在错误");
			e.printStackTrace();
		}
		if (!BusinessConstants.SUCCESS.equals(result)) {
			return new MessageBox("0", result);
		}

		return new MessageBox("1", result);
	}

	/**
	 * <p>
	 * Description:检查用户名或邮箱或推荐人是否存在<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月22日
	 * @param memberRegisterCnd
	 * @return String
	 */
	@RequestMapping(value = "/isInviterNameExist")
	public @ResponseBody
	MessageBox isInviterNameExist(MemberRegisterCnd memberRegisterCnd, HttpServletRequest request, HttpSession session) {
		String result = "success";
		try {
			String inviterName = request.getParameter("inviterName");
			memberRegisterCnd.setInviterName(inviterName);
			result = memberRegisterService.queryMemberRepeat(memberRegisterCnd, null);

		} catch (Exception e) {
			result = "failer";
			logger.error("验证用户名或邮箱是否存在错误");
			e.printStackTrace();
		}
		if (!BusinessConstants.SUCCESS.equals(result)) {
			return new MessageBox("0", result);
		}

		return new MessageBox("1", result);
	}

	/**
	 * <p>
	 * Description:执行会员注册方法<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月22日
	 * @param memberRegisterCnd
	 * @return String
	 */
	@RequestMapping(value = "/register")
	public @ResponseBody
	String register(Member member, String inviterName, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		String result = "success";
		try {
			result = memberRegisterService.insertMember(member, inviterName, request, session);
			if ("success".equals(result)) {
				// 用于sso
				cookieRetrievingCookieGenerator.addCookie(request, response, TicketCryptor.encrypt(currentUser().getSsoTicket()));
			}
		} catch (Exception e) {
			result = "网络连接异常，请稍候重试！";
			logger.error("register", e);
		}

		return result;
	}

	/**
	 * <p>
	 * Description:执行会员基本信息录入，即注册流程第一步<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年8月30日
	 * @param memberRegisterCnd
	 * @return String
	 */
	@RequestMapping(value = "/registMemberInfoCollect")
	@ResponseBody
	public MessageBox registMemberInfoCollect(Member member, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		String result = BusinessConstants.SUCCESS;
		try {
			if (verifyUserNameLength(member)) {
				return new MessageBox("0", "用户名称长度应该位于2~20位之间");
			}
			if (!VerifyContainSpecialChar.isContainSpecialChars(member.getUsername())) {
				return new MessageBox("0", "用户名包含特殊字符串");
			}
			if (null == member.getLogpassword() || "".equals(member.getLogpassword().trim())) {
				return new MessageBox("0", "密码输入有误！");
			}

			// 验证验证码
			// 推广抢红包注册2，无验证码；
			if (request.getParameter("novalidatecode") == null) {
				String validatecode = request.getParameter("validatecode");
				String randCode = (String) session.getAttribute("randomCode");
				if (null == validatecode || null == randCode || !validatecode.equals(randCode)) {
					return new MessageBox("0", "验证码输入有误！");
				}
			}

			String inviterName = request.getParameter("inviterName");
			member.setPlatform(BusinessConstants.MEMBER_OPERATE_ON_PORTAL);
			member.setIp(HttpTookit.getRealIpAddr(request));
			result = memberRegisterService.insertMemberWithoutEmail(member, inviterName, request, session);
			if (!"success".equals(result)) {
				return new MessageBox("0", result);
			}
			// 注册借款用户需后台审核，审核通过才能登录系统
			if (member.getIsFinancialUser() != null && member.getIsFinancialUser() == Integer.parseInt(Constants.IS_FINANCIAL_USER)) {
				if ("success".equals(result)) {
					// 用于sso
					cookieRetrievingCookieGenerator.addCookie(request, response, TicketCryptor.encrypt(currentUser().getSsoTicket()));
					return new MessageBox("1", result);
				}
			} else {
				return new MessageBox("2", "尊敬的用户，您好！您注册的用户是借款用户，需要审核后才能登录系统进行下一步操作！");
			}

		} catch (Exception e) {
			logger.error("register", e);
			return new MessageBox("0", e.getMessage());
		}

		return new MessageBox("0", result);
	}

	/**
	 * <p>
	 * Description:校验用户名长度是否符合要求<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年9月11日
	 * @param member
	 * @return boolean
	 */
	private boolean verifyUserNameLength(Member member) {
		return !(member != null && null != member.getUsername() && (member.getUsername().trim().length() <= 20 && member.getUsername().trim().length() >= 2));
	}

	/**
	 * <p>
	 * Description:跳转到会员邮箱和手机认证界面<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年8月30日
	 * @param memberRegisterCnd
	 * @return String
	 */
	@RequestMapping(value = "/toCheckMemberInfo")
	public ModelAndView checkMemberInfo(HttpServletRequest request) {
		Member member = new Member();
		ShiroUser shiroUser = currentUser();
		member.setId(shiroUser.getUserId());
		member.setUsername(shiroUser.getUserName());
		if (null == member || StringUtils.isEmpty(member.getUsername())) {
			return redirect("/" + BusinessConstants.TOP_HOME_ADDRESS);
		}
		// 通过用户名查询上一步的信息
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setUsername(member.getUsername());
		MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
		// 0：正式身份 -1：游客身份
		if (null != memberVo && BusinessConstants.VISITOR_OFFICIAL == memberVo.getType()) {
			logger.info("当前用户已经认证通过");
			return redirect("/" + BusinessConstants.TOP_HOME_ADDRESS);
		}
		return forword("/member/register_CheckMember").addObject("currentEmail", memberVo.getEmail() == null ? "" : memberVo.getEmail());
	}

	/**
	 * <p>
	 * Description:发送邮箱验证信息<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年8月30日
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "sendEmailVerifyLink")
	@ResponseBody
	public MessageBox sendEmailVerifyLink(HttpServletRequest request) {
		String destinationEmail = request.getParameter("email");
		Boolean again = Boolean.valueOf(request.getParameter("again"));//
		Member member = currentMember();
		if (null == member || StringUtils.isEmpty(member.getUsername())) {
			return MessageBox.build("0", "发送验证邮箱时出错,当前用户可能已验证，请核对。");
		}
		String result = "";
		try {
			result = memberRegisterService.sendEmailLinkActivateMember(request, destinationEmail, again, member);
		} catch (Exception e) {
			return MessageBox.build("0", e.getMessage());
		}
		if (!BusinessConstants.SUCCESS.equals(result)) {
			return MessageBox.build("0", result);
		}

		return MessageBox.build("1", "发送验证邮件成功，请去激活！");
	}

	private Member currentMember() {
		ShiroUser user = currentUser();
		Member member = new Member();
		member.setUsername(user.getUserName());
		member.setId(user.getUserId());
		member.setPlatform(user.getPlatform());
		return member;
	}

	/**
	 * <p>
	 * Description:发送邮件后跳转至下一步<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年9月01日
	 * @param request
	 */
	@RequestMapping(value = "/toEmailAppro")
	public ModelAndView mobailCheckSuccess(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("member/registerSucess");

		mv.addObject("userName", "邮件发送成功，请去激活！");
		return mv;
	}

	/**
	 * <p>
	 * Description: 查询注册使用条例<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toXiyi")
	public ModelAndView toXiyi(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("member/xiyi");
		return mv;
	}

	/**
	 * <p>
	 * Description:进入登录页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月21日
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toLoginPage")
	@GenerateToken
	public ModelAndView toLoginPage(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("member/login");

		// 判断是否通过需要跳转
		String backUrl = request.getParameter("backUrl");
		if (backUrl != null && !backUrl.trim().equals("")) {
			if (backUrl.equals("1")) {
				String referer = request.getHeader("referer");
				mv.addObject("backUrl", referer);
			} else {
				mv.addObject("backUrl", backUrl);
			}
		}
		return mv;
	}

	/**
	 * <p>
	 * Description:登录方法<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月7日
	 * @param request
	 * @param session
	 * @param response
	 * @param memberLoginCnd
	 * @return String
	 */
	@RequestMapping(value = "/login")
	@ValidateToken
	@ResponseBody
	public MessageBox login(HttpSession session, HttpServletResponse response, MemberLoginCnd memberLoginCnd) throws AuthenticationException {
		try {
			Object object = session.getAttribute(CSRFTokenManager.CSRF_PARAM_COUNTER);
			if (object != null && ((Integer) (object)).intValue() > 2) {
				if (memberLoginCnd.getCheckCode() == null || "".equals(memberLoginCnd.getCheckCode())) {
					return MessageBox.build("0", "请你输入验证码");
				}
				String randomCode = (String) currentSession().getAttribute("randomCode");
				if (!memberLoginCnd.getCheckCode().equals(randomCode)) {
					return MessageBox.build("0", "验证码不正确");
				}
			}
			if(StringUtils.isEmpty(memberLoginCnd.getUsername())){
				return MessageBox.build("0", "用户名不能为空");
			}else{
				List<BlackListVo> blackListVos= blackListMapper.queryByUserName(memberLoginCnd.getUsername().trim());
				if(null!=blackListVos&&blackListVos.size()>0){
					for(BlackListVo blackListVo:blackListVos){
						 if(blackListVo.getType()!=null&&blackListVo.getType()==12){
							 return MessageBox.build("0", "您的账户有异常，请联系客服");
						 }
					}
				}
			}
		
			long t1 = System.currentTimeMillis();
			logger.info(memberLoginCnd.getUsername() + " ----- login ----- begin time : " + t1);

			// shiro登录
			UsernamePasswordToken token = new UsernamePasswordToken(memberLoginCnd.getUsername(), MD5.toMD5(memberLoginCnd.getPasswd()),
					BusinessConstants.MEMBER_OPERATE_ON_PORTAL);
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);

			long t2 = System.currentTimeMillis();
			logger.info(memberLoginCnd.getUsername() + " ----- login ----- shiro login cost : " + (t2 - t1));

			LoginCnd loginCnd = new LoginCnd();
			loginCnd.setUserId(currentUser().getUserId());
			loginCnd.setUserName(currentUser().getUserName());
			loginCnd.setIp(HttpTookit.getRealIpAddr(currentRequest()));
			loginCnd.setSessionId(session.getId());
			loginCnd.setPlatform(currentUser().getPlatform());
			// 调用登录逻辑
			String msg = memberService.saveLogin(loginCnd);

			long t3 = System.currentTimeMillis();
			logger.info(memberLoginCnd.getUsername() + " ----- login ----- save login cost : " + (t3 - t2));

			// 用于sso
			cookieRetrievingCookieGenerator.addCookie(currentRequest(), response, TicketCryptor.encrypt(currentUser().getSsoTicket()));

			long t4 = System.currentTimeMillis();
			logger.error(memberLoginCnd.getUsername() + " ----- login ----- add sso cookie cost : " + (t4 - t3));
			System.out.println(memberLoginCnd.getSaveid() + "hhe");
			// 保存cookie
			if ("1".equals(memberLoginCnd.getSaveid())) {
				CookieGenerator cookieGenerator = new CookieGenerator();
				cookieGenerator.setCookieMaxAge(2147483647);
				cookieGenerator.setCookieName(COOKIE_LOGIN_USERID);
				cookieGenerator.addCookie(response, memberLoginCnd.getCookieusername());
			} else {
				CookieGenerator cookieGenerator = new CookieGenerator();
				cookieGenerator.setCookieMaxAge(0);
				cookieGenerator.setCookieName(COOKIE_LOGIN_USERID);
				cookieGenerator.addCookie(response, memberLoginCnd.getCookieusername());
			}

			long t5 = System.currentTimeMillis();
			logger.error(memberLoginCnd.getUsername() + " ----- login ----- add username cookie cost : " + (t5 - t4));

			// 小红点提示状态
			RedAccount redAccount = redAccountService.queryRedDotState(currentUser().getUserId());
			if (redAccount != null) {
				currentSession().setAttribute("redDot", redAccount);
			}
			// 抽奖机会提醒
			int lotteryChanceCount = lotteryChanceInfoService.queryLotteryNumTotal("11", currentUser().getUserId());
			if (lotteryChanceCount > 0) {
				currentSession().setAttribute("lotteryChanceCount", lotteryChanceCount);
			}
			if (BusinessConstants.VISITOR_UNAUTHERIZED.equals(msg)) {
				return MessageBox.build("2", "请先前往认证");
			}

			if (object != null) {
				currentSession().removeAttribute(CSRFTokenManager.CSRF_PARAM_COUNTER);
			}
		} catch (UnknownAccountException e) {
			return MessageBox.build("0", "账户名不存在");
		} catch (IncorrectCredentialsException ice) {
			return MessageBox.build("0", "账户名与密码不匹配");
		} catch (LockedAccountException lae) {
			return MessageBox.build("0", "账户已经锁定");
		} catch (Exception e) {
			if (isLogin()) {
				SecurityUtils.getSubject().logout();
			}
			logger.error("登录失败", e);
			return MessageBox.buildForCounter("3", "账号与密码不匹配");
		}

		String backUrl = memberLoginCnd.getBackUrl();
		if (backUrl != null && !backUrl.trim().equals("")) {
			logger.info("******自动跳转登陆前页面=" + backUrl + "******");
			return MessageBox.build("8", backUrl);
		}

		return MessageBox.build("1", "success");
	}

	/**
	 * <p>
	 * Description:判断当前登录用户是否设置了交易密码,如果设置了交易密码,则返回：true,如果没有设置,则返回：false<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月14日
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = "/isPayPasswordExist")
	public @ResponseBody
	String isPayPasswordExist(HttpSession session) {
		String result = "success";
		ShiroUser shiroUser = currentUser();
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setId(shiroUser.getUserId());
		MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
		if (null == memberVo) {
			result = "notlogin";
		} else if (null == memberVo.getPaypassword() || "".equals(memberVo.getPaypassword())) {
			result = "nopaypassword";
		}
		return result;
	}

	@RequestMapping(value = "/checkMemberContainSensitiveForRegist")
	public @ResponseBody
	MessageBox checkMemberContainSensitiveForRegist(MemberRegisterCnd memberRegisterCnd) {
		try {
			String username = memberRegisterCnd.getUsername();

			if (StringUtils.isEmpty(username)) {
				return new MessageBox("0", "用户名不能为空");
			}

			Member member = new Member();
			member.setUsername(username);
			if (verifyUserNameLength(member)) {
				return new MessageBox("0", "用户名称长度应该位于2~16位之间");
			}

			if (!VerifyContainSpecialChar.isContainSpecialChars(username)) {
				return new MessageBox("0", "用户名包含特殊字符串");
			}

			boolean result = memberRegisterService.existsContainSensitiveForUserName(username.replaceAll(" ", ""));

			if (!result) {
				return new MessageBox("1", "成功");
			} else {
				return new MessageBox("0", "注册用户名包含敏感词");
			}
		} catch (Exception e) {
			return new MessageBox("0", "验证出错");
		}

	}

	/**
	 * 推广注册
	 */
	@RequestMapping(value = "/registMemberInfoCollectJsonp", produces = { "text/plain;charset=UTF-8" })
	@ResponseBody
	public String registMemberInfoCollectJsonp(Member member, HttpServletRequest request, HttpSession session, HttpServletResponse response,
			String jsonpcallback) {
		member.setUsername(CharacterEncoder.decodeURL(member.getUsername(), "UTF-8"));

		MessageBox box = null;

		if (StringUtils.isEmpty(member.getUsername())) {
			box = MessageBox.build("0", "用户名不能为空");
		} else if (verifyUserNameLength(member)) {
			box = MessageBox.build("0", "用户名称长度应该位于2~16位之间");
		} else if (!VerifyContainSpecialChar.isContainSpecialChars(member.getUsername())) {
			box = MessageBox.build("0", "用户名包含特殊字符串");
		} else if (memberRegisterService.existsContainSensitiveForUserName(member.getUsername().replaceAll(" ", ""))) {
			box = MessageBox.build("0", "注册用户名包含敏感词");
		} else {
			box = registVerify(member, request, session);
		}
		return jsonpcallback + "(" + JsonUtils.bean2Json(box) + ")";
	}
	
	/**
	 * 进行手机认证
	 * @author WangQianJin
	 * @title @param request
	 * @title @param session
	 * @title @param jsonpcallback
	 * @title @return
	 * @date 2016年3月10日
	 */
	@RequestMapping(value = "/jsonpVerificationMobail", produces = { "text/plain;charset=UTF-8" })
	@ResponseBody
	public String verificationMobailActiveCodeJsonp(HttpServletRequest request, HttpSession session, String jsonpcallback,HttpServletResponse response) {
		MessageBox box = null;
		Member member = new Member();
		String mobileParam = request.getParameter("mobile");
		String activeCodeParam = request.getParameter("activeCode");
		String userName = request.getParameter("userName");
		String logpassword = request.getParameter("logpassword");
		if (StringUtils.isEmpty(mobileParam)) {
			box = new MessageBox("0", "用户手机号不能为空");
			return jsonpcallback + "(" + JsonUtils.bean2Json(box) + ")";
		}
		if (StringUtils.isEmpty(activeCodeParam)) {
			box = new MessageBox("0", "手机验证码不能为空");
			return jsonpcallback + "(" + JsonUtils.bean2Json(box) + ")";
		}
		if (StringUtils.isEmpty(userName)) {
			box = new MessageBox("0", "用户名不能为空");
			return jsonpcallback + "(" + JsonUtils.bean2Json(box) + ")";
		}
		if (StringUtils.isEmpty(logpassword)) {
			box = new MessageBox("0", "密码不能为空");
			return jsonpcallback + "(" + JsonUtils.bean2Json(box) + ")";
		}

		//封装手机号验证码
		member.setMobileNum(mobileParam);
		member.setActiveCode(activeCodeParam);
		member.setUsername(userName);
		member.setLogpassword(logpassword);
		//注册逻辑处理
		box = registMemberInfo(member, request, session, response);

		return jsonpcallback + "(" + JsonUtils.bean2Json(box) + ")";

	}

	/**
	 * 推广注册2
	 */
	@RequestMapping(value = "/registMemberInfoCollectJson", produces = { "text/plain;charset=UTF-8" })
	@ResponseBody
	public String registMemberInfoCollectJson(Member member, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		member.setUsername(CharacterEncoder.decodeURL(member.getUsername(), "UTF-8"));

		MessageBox box = null;

		if (StringUtils.isEmpty(member.getUsername())) {
			box = MessageBox.build("0", "用户名不能为空");
		} else if (verifyUserNameLength(member)) {
			box = MessageBox.build("0", "用户名称长度应该位于2~16位之间");
		} else if (!VerifyContainSpecialChar.isContainSpecialChars(member.getUsername())) {
			box = MessageBox.build("0", "用户名包含特殊字符串");
		} else if (memberRegisterService.existsContainSensitiveForUserName(member.getUsername().replaceAll(" ", ""))) {
			box = MessageBox.build("0", "注册用户名包含敏感词");
		} else {
			box = registMemberInfo(member, request, session, response);
		}

		return JsonUtils.bean2Json(box);
	}

	/**
	 * <p>
	 * Description:判断手机号码是否存在<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年9月17日
	 * @param memberRegisterCnd
	 * @param request
	 * @param session
	 * @return MessageBox
	 */
	@RequestMapping(value = "/isMobileNumExist")
	public @ResponseBody
	MessageBox isMobileNumExist(String mobileNum) {
		String result = "success";
		try {

			if (null != mobileNum && !"".equals(mobileNum.trim())) {
				// 验证手机号是否存在
				MobileApproCnd mobileApproCnd = new MobileApproCnd();
				mobileApproCnd.setMobileNum(mobileNum);
				Integer usernameCount = mobileApproMapper.queryRepeatMobileApproCount(mobileApproCnd);
				if (null != usernameCount && usernameCount > 0) {
					return new MessageBox("2", "该手机号已经被使用！");
				}
			}

		} catch (Exception e) {
			result = "failer";
			logger.error("该手机号已经被使用！");
		}
		if (!BusinessConstants.SUCCESS.equals(result)) {
			return new MessageBox("0", result);
		}

		return new MessageBox("1", result);
	}

	/**
	 * <p>
	 * Description:用户注册手机验证<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年9月18日
	 * @param member
	 * @param request
	 * @param session
	 * @return MessageBox
	 */
	@RequestMapping(value = "/registVerify")
	@ResponseBody
	public MessageBox registVerify(Member member, HttpServletRequest request, HttpSession session) {
		String result = BusinessConstants.SUCCESS;
		try {
			if (verifyUserNameLength(member)) {
				return new MessageBox("0", "用户名称长度应该位于2~20位之间");
			}
			if (!VerifyContainSpecialChar.isContainSpecialChars(member.getUsername())) {
				return new MessageBox("0", "用户名包含特殊字符串");
			}
			if (null == member.getLogpassword() || "".equals(member.getLogpassword().trim())) {
				return new MessageBox("0", "密码输入有误！");
			}

			// 验证验证码
			// 推广抢红包注册2，无验证码；
			if (request.getParameter("novalidatecode") == null) {
				String validatecode = request.getParameter("validatecode");
				String randCode = (String) session.getAttribute("randomCode");
				if (null == validatecode || null == randCode || !validatecode.equals(randCode)) {
					return new MessageBox("0", "验证码输入有误！");
				}
			}
			String inviterName = request.getParameter("inviterName");
			// 验证推荐人
			if (null != inviterName && !"".equals(inviterName.trim())) {
				MemberRegisterCnd inviterNameCnd = new MemberRegisterCnd();
				inviterNameCnd.setInviterName(inviterName);
				String flag = memberRegisterService.queryInviterName(inviterNameCnd);
				if (!flag.equals(BusinessConstants.SUCCESS)) {
					return new MessageBox("0", flag);
				}
				List<MemberRegisterVo> inviterNameList = memberRegisterMapper.queryinviterList(inviterNameCnd);
				// 设置推荐人
				member.setInviterid(inviterNameList.get(0).getId());
				// 校验红包ID是否存在 liutao 20151103
				if (null != member.getRedId() && StringUtils.isNotEmpty(member.getRedId()) && null != member.getInviterid()) {
					int membercount = redAccountMapper.isExistRed(member);
					if (membercount <= 0) {
						return new MessageBox("0", "尊敬的用户，您好！推荐您注册的红包不存在");
					}
				}
			}
			if (!"success".equals(result)) {
				return new MessageBox("0", result);
			} else {
				// 发送短信验证码
				System.out.println("发送短信验证码====");
				mobileApproService.sendMobileApprValidate(member.getMobileNum(), request, "", BusinessConstants.MOBILE_APPRO_FUNCTION,
						BusinessConstants.SMS_TEMPLATE_TYPE_WX_SAFECENTER_MOBILEVALIDATE);
				return new MessageBox("1", result);
			}

		} catch (Exception e) {
			logger.error("register", e);
			return new MessageBox("0", e.getMessage());
		}

	}

	/**
	 * <p>
	 * Description:手机短信验证码<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年9月18日
	 * @return MessageBox
	 */
	@RequestMapping(value = "/isMobileCode")
	public @ResponseBody
	MessageBox isMobileCode() {
		String result = "success";
		try {
			String mobileCode = currentRequest().getParameter("mobileCode");
			String mobileNum = currentRequest().getParameter("mobileNum");
			if (null == mobileCode) {
				return new MessageBox("0", "请输入手机验证码");
			}
			if (null != mobileCode && !"".equals(mobileCode.trim())) {
				// 验证手机验证码是否输入正确
				// 验证验证码是否正确
				String valiateResult = phoneService.compareSmsValidate(mobileNum, mobileCode, BusinessConstants.MOBILE_APPRO_FUNCTION);
				if (valiateResult != "success") {
					return new MessageBox("0", valiateResult);
				}
			}

		} catch (Exception e) {
			result = "failer";
			logger.error("手机验证码错误");
		}
		if (!BusinessConstants.SUCCESS.equals(result)) {
			return new MessageBox("0", result);
		}

		return new MessageBox("1", result);
	}

	/**
	 * <p>
	 * Description:手机短信验证码发送<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年9月18日
	 * @return MessageBox
	 */
	@RequestMapping(value = "/sendMobileCode")
	public @ResponseBody
	MessageBox sendMobileCode() {
		String result = "success";
		try {
			String mobileNum = currentRequest().getParameter("mobileNum");
			if (null != mobileNum && !"".equals(mobileNum.trim())) {
				// 验证码发送
				result = mobileApproService.sendMobileApprValidate(mobileNum, currentRequest(), "", BusinessConstants.MOBILE_APPRO_FUNCTION,
						BusinessConstants.SMS_TEMPLATE_TYPE_WX_SAFECENTER_MOBILEVALIDATE);
			}
		} catch (Exception e) {
			result = "failer";
			logger.error("网络异常");
		}
		if (!BusinessConstants.SUCCESS.equals(result)) {
			return new MessageBox("0", result);
		}
		return new MessageBox("1", result);
	}

	/**
	 * <p>
	 * Description:校验验证码是否正确<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年9月22日
	 * @return MessageBox
	 */
	@RequestMapping(value = "/isValiteCode")
	@ResponseBody
	public MessageBox isValiteCode() {
		String validatecode = currentRequest().getParameter("validatecode");
		String randCode = (String) currentSession().getAttribute("randomCode");
		if (null == validatecode || null == randCode || !validatecode.equals(randCode)) {
			return new MessageBox("0", "验证码输入有误！");
		}
		return new MessageBox("1", "");
	}

	/**
	 * 最终注册
	 * <p>
	 * Description:执行会员基本信息录入，即注册流程第一步<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年8月30日
	 * @param memberRegisterCnd
	 * @return String
	 */
	@RequestMapping(value = "/registMemberInfo")
	@ResponseBody
	public MessageBox registMemberInfo(Member member, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		String linkSourceValue = (String) request.getSession().getAttribute("linkSourceValue");
		if ("7".equals(linkSourceValue) || "7".equals(member.getSource())) {
			// 关闭快乐赚注册渠道
			// return new MessageBox("0", "注册失败！");
		}
		String result = BusinessConstants.SUCCESS;
		try {
			if (null == member.getActiveCode() && !"".equals(member.getActiveCode().trim().trim())) {
				return new MessageBox("0", "请输入手机验证码");
			}
			// 验证手机验证码是否输入正确
			// 验证验证码是否正确
			String valiateResult = phoneService.compareSmsValidate(member.getMobileNum(), member.getActiveCode(), BusinessConstants.MOBILE_APPRO_FUNCTION);
			if (valiateResult != "success") {
				return new MessageBox("0", valiateResult);
			}

			if (verifyUserNameLength(member)) {
				return new MessageBox("0", "用户名称长度应该位于2~16位之间");
			}
			if (!VerifyContainSpecialChar.isContainSpecialChars(member.getUsername())) {
				return new MessageBox("0", "用户名包含特殊字符串");
			}
			if (null == member.getLogpassword() || "".equals(member.getLogpassword().trim())) {
				return new MessageBox("0", "密码输入有误！");
			}
			// 校验红包ID是否存在 liutao 20151103
			if (null != member.getRedId() && StringUtils.isNotEmpty(member.getRedId()) && null != member.getInviterid()) {
				int membercount = redAccountMapper.isExistRed(member);
				if (membercount <= 0) {
					return new MessageBox("0", "尊敬的用户，您好！推荐您注册的红包不存在");
				}
			}
			String inviterName = request.getParameter("inviterName");
			member.setPlatform(BusinessConstants.MEMBER_OPERATE_ON_PORTAL);
			member.setIp(HttpTookit.getRealIpAddr(request));
			request.setAttribute("activeCode", member.getActiveCode());//验证码 add20160823
			result = memberRegisterService.insertMemberWithoutEmail(member, inviterName, request, session);
			if (!"success".equals(result)) {
				return new MessageBox("0", result);
			}
			// 注册借款用户需后台审核，审核通过才能登录系统
			if (member.getIsFinancialUser() != null && member.getIsFinancialUser() == Integer.parseInt(Constants.IS_FINANCIAL_USER)) {
				if ("success".equals(result)) {
					cookieRetrievingCookieGenerator.addCookie(request, response, TicketCryptor.encrypt(currentUser().getSsoTicket()));
					return new MessageBox("1", result);
				}
			} else {
				return new MessageBox("2", "尊敬的用户，您好！您注册的用户是借款用户，需要审核后才能登录系统进行下一步操作！");
			}

		} catch (Exception e) {
			logger.error("register", e);
			return new MessageBox("0", e.getMessage());
		}

		return new MessageBox("0", result);
	}

	/**
	 * <p>
	 * Description:进入注册页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月21日
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toRegisterPage")
	public ModelAndView toRegisterPage(HttpServletRequest request, HttpServletResponse response) {
		/***** 微信站点首页域名 *****/
		final String WX_MAIN_URL = new String("http://m.dxjr.com/regist");
		// 判断是否需要重定向和跳转
		String ua = currentRequest().getHeader("User-Agent");
		String inviterName = null;
		String realName = null;// 显示真实姓名
		ModelAndView mv = new ModelAndView("member/userRegiste");
		String userIdMD5 = request.getParameter("code");
		String redId = request.getParameter("redId");
		// 如果来自内部推广，则判断该链接的有效性，即邀请人是否存在
		if (userIdMD5 != null && !StringUtils.isEmpty(userIdMD5)) {
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setUserIdMD5(userIdMD5);
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			RealNameApproVo vo = realNameApproMapper.getByUserId(memberVo.getId());
			if (vo != null && !StringUtils.isEmpty(vo.getSecuritRealName())) {
				realName = vo.getSecuritRealName();
			}
			if (null != memberVo && !StringUtils.isEmpty(memberVo.getUsername())) {
				inviterName = memberVo.getUsername();

			}

		}

		if (isMobileTerminal(ua)) {
			return redirect(WX_MAIN_URL).addObject("inviterName", CharacterEncoder.encodeURL(inviterName, "UTF-8"))
					.addObject("realName", CharacterEncoder.encodeURL(realName, "UTF-8")).addObject("redId", CharacterEncoder.encodeURL(redId, "UTF-8"));
		}
		CookieGenerator cookieGenerator = new CookieGenerator();
		cookieGenerator.setCookieMaxAge(30 * 24 * 60 * 60);
		String tid = request.getParameter("tid");// 如果来源于易瑞特，则为必有参数
		if (null != tid) {
			cookieGenerator.setCookieName("tid");
			cookieGenerator.addCookie(response, tid);
			request.getSession().setAttribute("tid", tid);
		}

		String extendLinkSourceName = request.getParameter("source");// 链接来源
		if (!StringUtils.isEmpty(extendLinkSourceName)) {
			//缓存有时无法获取数据，name与value一样，直接将source存入session
			request.getSession().setAttribute("linkSourceValue", extendLinkSourceName.trim());
		}

		return mv.addObject("inviterName", inviterName).addObject("realName", realName).addObject("redId", CharacterEncoder.encodeURL(redId, "UTF-8"));
	}

	/**
	 * <p>
	 * Description:找回密码第一步<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年12月29日
	 * @param mobileNum
	 * @return MessageBox
	 */
	@RequestMapping(value = "/findMobileByOne")
	public @ResponseBody
	MessageBox findMobileByOne(String mobileNum, String validatecode) {
		String result = "success";
		try {

			if (null != mobileNum && !"".equals(mobileNum.trim())) {
				MobileApproCnd mobileApproCnd = new MobileApproCnd();
				mobileApproCnd.setMobileNum(mobileNum);
				Integer usernameCount = mobileApproMapper.queryRepeatMobileApproCount(mobileApproCnd);
				if (usernameCount == null || usernameCount.intValue() < 1) {
					return new MessageBox("0", "您的手机号码没有绑定账号");
				}
			}
			String randCode = (String) currentSession().getAttribute("randomCode");
			if (null == validatecode || null == randCode || !validatecode.equals(randCode)) {
				return new MessageBox("0", "验证码输入有误！");
			}

		} catch (Exception e) {
			result = "failer";
			logger.error("找回密码第一步:", e);
		}
		if (!BusinessConstants.SUCCESS.equals(result)) {
			return new MessageBox("0", result);
		}

		return new MessageBox("1", result);
	}
	
	/**
	 * BBS注册校验用户名
	 */
	@RequestMapping(value = "/bbsCheckMemberRepeatForRegist", produces = { "text/plain;charset=UTF-8" })
	@ResponseBody
	public String bbsCheckMemberRepeatForRegist(MemberRegisterCnd memberRegisterCnd, HttpServletRequest request, String inviterName, HttpSession session,String jsonpcallback) {
		MessageBox box = checkMemberRepeatForRegist(memberRegisterCnd, request,inviterName, session);
		if("1".equals(box.getCode())){
			box = checkMemberContainSensitiveForRegist(memberRegisterCnd);
		}
		return jsonpcallback + "(" + JsonUtils.bean2Json(box) + ")";
	}
	
	/**
	 * BBS注册校验用户名
	 */
	@RequestMapping(value = "/bbsIsMobileNumExist", produces = { "text/plain;charset=UTF-8" })
	@ResponseBody
	public String bbsIsMobileNumExist(String mobileNum ,String jsonpcallback) {
		MessageBox box = isMobileNumExist(mobileNum);
		return jsonpcallback + "(" + JsonUtils.bean2Json(box) + ")";
	}
}
