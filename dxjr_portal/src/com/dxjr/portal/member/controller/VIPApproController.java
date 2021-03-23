package com.dxjr.portal.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.VIPApply;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.member.service.EmailApprService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.member.service.VIPApproService;
import com.dxjr.portal.member.service.VipLevelService;
import com.dxjr.portal.member.vo.VIPApproVo;
import com.dxjr.security.ShiroUser;

/**
 * <p>
 * Description:实名认证Controller<br />
 * </p>
 * 
 * @title VIPApproController.java
 * @package com.dxjr.portal.member.controller
 * @author justin.xu
 * @version 0.1 2014年4月25日
 */
@Controller
@RequestMapping(value = "/account/approve/vip")
public class VIPApproController extends BaseController {

	private final static Logger logger = Logger.getLogger(VIPApproController.class);

	@Autowired
	private VIPApproService vipApproService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private VipLevelService vipLevelService;

	@Autowired
	private EmailApprService emailApprService;

	@Autowired
	private MobileApproService mobileApproService;

	/**
	 * <p>
	 * Description:临时vip页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月25日
	 * @param session
	 * @param request
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/toVipIndex")
	public ModelAndView vipApproPrompt(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("account/approve/vip/vipApproIndex");
		return mv;
	}

	/**
	 * <p>
	 * Description:检查当前用户VIP认证是否通过<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月25日
	 * @param request
	 * @return boolean
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/checkVipAppro")
	@ResponseBody
	public String checkVipAppro(HttpServletRequest request) {
		String result = "success";
		try {
			ShiroUser shiroUser = currentUser();
			VIPApproVo vipApproVo = vipApproService.queryVIPApproByUserId(shiroUser.getUserId());
			if (null != vipApproVo && vipApproVo.getPassed() == 1) {
				result = "true";
			} else {
				result = "false";
			}
		} catch (Exception e) {
			result = "网络连接异常，请稍候重试";
			logger.error("VIP认证是否通过", e);
		}
		return result;
	}

	/**
	 * <p>
	 * Description:进入VIP认证页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月25日
	 * @param session
	 * @param request
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "vipforinsert")
	public ModelAndView vipforinsert(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("redirect:/myaccount/toIndex.html");
		/*
		 * try { ShiroUser shiroUser = currentUser(); // 未设置交易密码 MemberCnd
		 * memberCnd = new MemberCnd(); memberCnd.setId(shiroUser.getUserId());
		 * MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
		 * EmailApproVo emailApproVo =
		 * emailApprService.queryEmailApproByUserId(shiroUser.getUserId());
		 * MobileApproVo mobileAppro =
		 * mobileApproService.queryMobileApproByUserId(shiroUser.getUserId());
		 * 
		 * MemberApproVo memberApproVo =
		 * memberService.queryMemberApproByUserId(shiroUser.getUserId()); if
		 * ((memberApproVo == null || memberApproVo.getEmailChecked() == null ||
		 * memberApproVo.getEmailChecked() != 1) &&
		 * (memberApproVo.getMobilePassed() == null ||
		 * memberApproVo.getMobilePassed() != 1)) { mv = new
		 * ModelAndView("account/safe/safetyCentre"); // 查询用户信息
		 * mv.addObject("memberVo", memberVo); mv.addObject("emailApproVo",
		 * emailApproVo); mv.addObject("mobileAppro", mobileAppro);
		 * mv.addObject("msg", "email_mobile_no_appro"); //
		 * 邮箱或手机未认证通过，请先进行邮箱或手机认证！ return mv; } if
		 * (memberApproVo.getNamePassed() == null ||
		 * memberApproVo.getNamePassed() != 1) { mv = new
		 * ModelAndView("account/safe/safetyCentre"); // 查询用户信息
		 * mv.addObject("memberVo", memberVo); mv.addObject("emailApproVo",
		 * emailApproVo); mv.addObject("mobileAppro", mobileAppro);
		 * 
		 * mv.addObject("msg", "realName_no_appro"); // 实名未认证通过，请先进行实名认证！ return
		 * mv; } VIPApproVo vipApproVo =
		 * vipApproService.queryVIPApproByUserId(shiroUser.getUserId()); //
		 * 进入VIP成功页面 if (null != vipApproVo && null != vipApproVo.getPassed()) {
		 * if (vipApproVo.getPassed() == Constants.VIP_APPRO_PASSED_YES) {
		 * mv.addObject("vIPAppro", vipApproVo); if
		 * (vipLevelService.getIsSvipByUserId(shiroUser.getUserId())) { mv = new
		 * ModelAndView("account/approve/vip/vipsuccess");
		 * mv.addObject("indateTip", "您是终身顶级会员。"); } else { // VIP到期截止日期的前3个月日期
		 * String start_indate =
		 * DateUtils.format(DateUtils.monthOffset(vipApproVo.getIndate(), -3),
		 * DateUtils.YMD_DASH); String indate =
		 * DateUtils.format(vipApproVo.getIndate(), DateUtils.YMD_DASH); if
		 * (Long.parseLong(DateUtils.date2TimeStamp(start_indate)) >
		 * Long.parseLong(DateUtils.getTime())) { mv = new
		 * ModelAndView("account/approve/vip/vipsuccess");
		 * mv.addObject("indateTip", "您的VIP服务到期截止日期为" + indate); } else {
		 * mv.addObject("indateTip", "您的VIP服务到期截止日期为" + indate + "，请尽快申请续期。"); }
		 * } } } if (null == memberVo.getPaypassword() ||
		 * "".equals(memberVo.getPaypassword())) {
		 * mv.addObject("nosetPaypassword", true); } } catch (Exception e) {
		 * logger.error("进入VIP认证页面出错", e); }
		 */
		return mv;
	}

	/**
	 * <p>
	 * Description:查看VIP协议<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月25日
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/toVipXiyi")
	public ModelAndView toVipXiyi() {
		ModelAndView mv = new ModelAndView("account/approve/vip/vipXiyi");
		return mv;
	}

	/**
	 * <p>
	 * Description:保存VIP认证<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月25日
	 * @param session
	 * @param request
	 * @param vIPAppro
	 * @return String
	 */
	@RequestMapping(value = "saveOrUpdate")
	@ResponseBody
	public String saveOrUpdate(HttpSession session, HttpServletRequest request, VIPApply vIPApply) {
		//String result = "success";
		/*
		 * try { ShiroUser shiroUser = currentUser(); MemberApproVo
		 * memberApproVo =
		 * memberService.queryMemberApproByUserId(shiroUser.getUserId()); if
		 * ((memberApproVo == null || memberApproVo.getEmailChecked() == null ||
		 * memberApproVo.getEmailChecked() != 1) &&
		 * (memberApproVo.getMobilePassed() == null ||
		 * memberApproVo.getMobilePassed() != 1)) { return
		 * "邮箱或手机未认证通过，请先进行邮箱或手机认证！"; } if (memberApproVo.getNamePassed() ==
		 * null || memberApproVo.getNamePassed() != 1) { return
		 * "实名未认证通过，请先进行实名认证！"; } String payPassword =
		 * request.getParameter("paypassword"); result =
		 * vipApproService.saveVIPAppro(payPassword, shiroUser.getUserId(),
		 * request); } catch (AppException ae) { return ae.getMessage(); } catch
		 * (Exception e) { result = "网络连接异常，请刷新页面或稍后重试!"; e.printStackTrace(); }
		 */
		return "VIP已下线，无法申请VIP！";
	}

}
