package com.dxjr.wx.account.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.MyAccountService;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.service.EmailApprService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.member.service.RealNameApproService;
import com.dxjr.portal.member.service.VIPApproService;
import com.dxjr.portal.member.vo.EmailApproVo;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.MobileApproVo;
import com.dxjr.portal.member.vo.RealNameApproVo;
import com.dxjr.portal.member.vo.VIPApproVo;
import com.dxjr.portal.sms.service.SmsSendService;
import com.dxjr.portal.sms.vo.SendMobileCnd;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;

/**
 * 微信安全中心控制类
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title SafeCenterController.java
 * @package com.dxjr.wx.account.controller
 * @author ZHUCHEN
 * @version 0.1 2014年11月3日
 */
@Controller(value = "wxSafeCenter")
@RequestMapping(value = "/wx/account/safeCenter")
public class SafeCenterController extends BaseController {

	public Logger logger = Logger.getLogger(SafeCenterController.class);

	@Autowired
	private MyAccountService myAccountServiceImpl;
	@Autowired
	private RealNameApproService realNameApproService;
	@Autowired
	private EmailApprService emailApprService;
	@Autowired
	private MobileApproService mobileApproService;
	@Autowired
	private SmsSendService sendService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private VIPApproService vipApproService;

	/**
	 * 获取账户安全中心主页面所需数据
	 * <p>
	 * Description:进入账户安全中心主页面<br />
	 * 修改的内容：1.增加了返回标志：0-用户未登录，1-正常 2.返回值由modelandview改为了map
	 * </p>
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月27日
	 * @param request
	 * @return
	 * @throws Exception Map<String,Object>
	 */
	@RequestMapping(value = "")
	@ResponseBody
	public Map<String, Object> index() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("flag", "1");
			ShiroUser shiroUser = currentUser();
			// 没有登入
			if (null == shiroUser) {
				map.put("flag", "0");
				return map;
			}
			Integer memberId = shiroUser.getUserId();
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(memberId);
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			RealNameApproVo realNameApproVo = realNameApproService.queryRealNameApproByUserId(memberId);
			EmailApproVo emailApproVo = emailApprService.queryEmailApproByUserId(memberId);
			MobileApproVo mobileAppro = mobileApproService.queryMobileApproByUserId(memberId);
			VIPApproVo vipApproVo = vipApproService.queryVIPApproByUserId(memberId);
			// GesturePwdVo gesturePwdVo = vipApproService.queryVIPApproByUserId(memberId);

			// map.put("realNameApproVo", realNameApproVo);
			if (realNameApproVo != null) {
				map.put("realNameIsPassed", realNameApproVo.getIsPassed());
				map.put("securityRealName", realNameApproVo.getSecurityRealName());
			}

			// map.put("emailApproVo", emailApproVo);
			if (emailApproVo != null) {
				map.put("emailChecked", emailApproVo.getChecked());
				map.put("securityemail", emailApproVo.getSecurityemail());
			}

			// map.put("mobileAppro", mobileAppro);
			if (mobileAppro != null) {
				map.put("mobilePassed", mobileAppro.getPassed());
				map.put("securitymobileNum", mobileAppro.getSecuritymobileNum());
			}

			// map.put("memberVo", memberVo);
			if (memberVo.getPaypassword() != null && memberVo.getPaypassword().length() > 0) {
				map.put("hasPayPwd", "1");
			} else {
				map.put("hasPayPwd", "0");
			}

			// map.put("vipApproVo", vipApproVo);
			if (vipApproVo != null) {
				map.put("vipPassed", vipApproVo.getPassed());
			}
		} catch (Exception e) {
			logger.error("微信获取安全中心首页数据异常", e);
		}
		return map;
	}

	/**
	 * 判断是否可以进入邮箱认证页面
	 * <p>
	 * Description:复制于原邮箱认证逻辑，修改的部分有：1.返回类型为map对象 ;2.增加返回标记：0-已认证，1-未认证
	 * </p>
	 * *
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月25日
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/email")
	@ResponseBody
	public Map<String, Object> email() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("flag", "1");
			ShiroUser shiroUser = currentUser();
			if (shiroUser == null)
				return null;
			MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
			// 邮箱已经验证
			if (memberApproVo != null && memberApproVo.getEmailChecked() != null && 1 == memberApproVo.getEmailChecked()) {
				map.put("flag", "0");
			} else {
				EmailApproVo obj = emailApprService.queryEmailApproByUserId(shiroUser.getUserId());
				if (obj != null)
					map.put("email", obj.getEmail());
			}
			return map;
		} catch (Exception e) {
			logger.error("微信判断是否可以进入邮箱验证异常", e);
		}
		return null;
	}

	/**
	 * 打开VIP认证页面
	 * <p>
	 * Description:复制于原VIP认证逻辑，修改的部分有：1.返回类型为map对象 ;
	 * </p>
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月27日
	 * @param session
	 * @param request
	 * @return Map<String,Object>,0未登录，1-请先进行手机或邮箱认证 2--请先进行实名认证 3-未设置密码，4可进入认证页面，5-可进入已认证页面
	 */
	@RequestMapping(value = "vipforinsert")
	@ResponseBody
	public Map<String, Object> vipforinsert() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("path", "/account/safeCenter/validateVIP");
		map.put("msg", "");
		try {
			ShiroUser shiroUser = currentUser();
			if (shiroUser == null) {
				map.put("path", "/login/login");
				map.put("msg", "请先登录");
				return map;
			}
			MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
			if ((memberApproVo == null || memberApproVo.getEmailChecked() == null || memberApproVo.getEmailChecked() != 1)
					&& (memberApproVo.getMobilePassed() == null || memberApproVo.getMobilePassed() != 1)) {
				map.put("path", "/account/safeCenter/validateMobile");
				map.put("msg", "请先进行手机认证");
				return map;
			}
			if (memberApproVo.getNamePassed() == null || memberApproVo.getNamePassed() != 1) {
				map.put("path", "/account/safeCenter/validateRealName");
				map.put("msg", "请先进行实名认证");
				return map;
			}
			map.put("isCanRenew", memberApproVo.getIsCanRenew());
			map.put("vipPassed", memberApproVo.getVipPassed());

			VIPApproVo vipApproVo = vipApproService.queryVIPApproByUserId(shiroUser.getUserId());
			// 进入VIP成功页面
			if (null != vipApproVo && null != vipApproVo.getPassed()) {
				if (vipApproVo.getPassed() == Constants.VIP_APPRO_PASSED_YES) {
					map.put("path", "/account/safeCenter/validateHasVIP");
					// VIP到期截止日期的前3个月日期
					String start_indate = DateUtils.format(DateUtils.monthOffset(vipApproVo.getIndate(), -3), DateUtils.YMD_DASH);
					String indate = DateUtils.format(vipApproVo.getIndate(), DateUtils.YMD_DASH);
					if (Long.parseLong(DateUtils.date2TimeStamp(start_indate)) > Long.parseLong(DateUtils.getTime())) {
						map.put("indateTip", "您的VIP服务到期日期为" + indate);
						map.put("vIPAppro", vipApproVo);
					} else {
						map.put("indateTip", "您的VIP服务到期日期为" + indate + "，请尽快申请续期。");
					}
				}
			}
			// 查询用户信息
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(shiroUser.getUserId());
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			// 未设置交易密码
			if (null == memberVo.getPaypassword() || "".equals(memberVo.getPaypassword())) {
				// map.put("nosetPaypassword", true);
				map.put("path", "/account/safeCenter/setPayPassword");
				map.put("msg", "尚未设置交易密码，请先设置交易密码！");
			}
		} catch (Exception e) {
			logger.error("微信获取VIP认证页面的数据异常", e);
		}
		return map;
	}

	/**
	 * 安全中心-发送手机认证验证码
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author ZHUCHEN
	 * @version 0.1 2014年11月18日
	 * @param mobile
	 * @param activeCode
	 * @return MessageBox
	 */
	@RequestMapping(value = "/sendMobailActiveInSaftCenter")
	@ResponseBody
	public MessageBox sendMobailActiveInSaftCenter(String mobile, String activeCode, String ip) {
		// System.out.println("hahahaip:::::::" + ip);
		String result = BusinessConstants.SUCCESS;
		ShiroUser user = currentUser();
		if (user == null) {
			return new MessageBox("0", "请先登录！");
		}
		try {
			MobileApproVo mobileApproVo = mobileApproService.queryMobileApproByUserId(user.getUserId());
			// 修改手机号码操作
			if (null == currentSession().getAttribute(BusinessConstants.MOBILE_APPRO_RESET_FUNCTION + user.getUserId())
					&& (null != mobileApproVo && null != mobileApproVo.getPassed() && mobileApproVo.getPassed() == Constants.YES)) {
				return new MessageBox("0", "手机号码已认证通过");
			}
			SendMobileCnd sendMobileCnd = new SendMobileCnd();
			sendMobileCnd.setMobile(mobile);
			sendMobileCnd.setNeedRandcode(true);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("username", user.getUserName());
			sendMobileCnd.setParamMap(paramMap);
			sendMobileCnd.setIp(ip);
			sendMobileCnd.setModelName(BusinessConstants.MOBILE_APPRO_FUNCTION);
			sendMobileCnd.setPlatform(user.getPlatform());
			sendMobileCnd.setSmsTemplateType(BusinessConstants.SMS_TEMPLATE_TYPE_WX_SAFECENTER_MOBILEVALIDATE);
			result = sendService.saveTemplateMessage(sendMobileCnd);
			MemberVo memberVo = new MemberVo();
			memberVo.setId(user.getUserId());
			mobileApproService.packageMobileApproCode(memberVo, mobile, activeCode, currentRequest());
			// 删除验证原有手机号码session
			currentSession().removeAttribute(BusinessConstants.MOBILE_APPRO_RESET_FUNCTION + user.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!BusinessConstants.SUCCESS.equals(result)) {
			return new MessageBox("0", result);
		}
		return new MessageBox("1", "发送成功，请注意查收");
	}
	
	/**
	 * 手机信息
	 * @author WangQianJin
	 * @title @return
	 * @date 2016年5月24日
	 */
	@RequestMapping(value = "mobileInfo")
	@ResponseBody
	public Map<String, Object> mobileInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser shiroUser = currentUser();
			Integer memberId = shiroUser.getUserId();			
			MobileApproVo mobileAppro = mobileApproService.queryMobileApproByUserId(memberId);
			if (mobileAppro != null) {
				map.put("mobilePassed", mobileAppro.getPassed());
				map.put("securitymobileNum", mobileAppro.getSecuritymobileNum());
				map.put("mobileNum", mobileAppro.getMobileNum());
			}
		} catch (Exception e) {
			logger.error("微信获取手机号数据异常", e);
		}
		return map;
	}
}
