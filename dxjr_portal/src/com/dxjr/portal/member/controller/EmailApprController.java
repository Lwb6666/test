package com.dxjr.portal.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.service.EmailApprService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.member.vo.EmailApproVo;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.MobileApproVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:邮箱认证Controller<br />
 * </p>
 * 
 * @title EmailController.java
 * @package com.dxjr.portal.member.controller
 * @author justin.xu
 * @version 0.1 2014年4月23日
 */
@Controller
@RequestMapping(value = "/account/approve")
public class EmailApprController extends BaseController {

	@Autowired
	private EmailApprService emailApprService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MobileApproService mobileApproService;

	/**
	 * <p>
	 * Description:进入邮箱认证页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param session
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/email")
	public ModelAndView emailforinsert(HttpSession session,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(
				"account/approve/email/certificationMail");
		try {
			ShiroUser shiroUser = currentUser();
			MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
			//邮箱已经验证
			if(memberApproVo !=null && memberApproVo.getEmailChecked() != null && 1 == memberApproVo.getEmailChecked()){
				return  redirect("/AccountSafetyCentre/safetyIndex.html");
			}
			EmailApproVo mailApproVo = emailApprService 
					.queryEmailApproByUserId(shiroUser.getUserId());
			MobileApproVo mobileAppro = mobileApproService.queryMobileApproByUserId(shiroUser.getUserId());
			mv.addObject("emailApproVo", mailApproVo);
			mv.addObject("mobileAppro", mobileAppro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}


	@RequestMapping(value = "/emailDiv")
	public ModelAndView emailforinsertDiv(HttpSession session,
									   HttpServletRequest request) {
		ModelAndView mv =  forword("account/approve/email/certificationMailDiv");
		try {
			ShiroUser shiroUser = currentUser();
			MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
			//邮箱已经验证
			if(memberApproVo !=null && memberApproVo.getEmailChecked() != null && 1 == memberApproVo.getEmailChecked()){
				mv.addObject("msg", "email_already_appro");
				return mv;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * <p>
	 * Description:更新邮箱并发送邮件<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param request
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = "updateAndSendMail")
	@ResponseBody
	public String updateAndSendMail(HttpServletRequest request,
			HttpSession session, String email) {
		String result = "success";
		try {
			ShiroUser shiroUser = currentUser();
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(shiroUser.getUserId());
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			result = emailApprService.updateAndSendMail(memberVo, email, request);
		} catch (Exception e) {
			result = "网络连接异常，请稍后重试！";
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * <p>
	 * Description:安全中心-进行邮箱认证并发送邮件<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年9月3日
	 * @param request
	 * @param session
	 * @return String
	 */
	@RequiresAuthentication
	@RequestMapping(value = "emailVerify")
	@ResponseBody
	public MessageBox emailVerify(HttpServletRequest request,HttpSession session, String email) {
		String result = "success";
		try {
			ShiroUser shiroUser = currentUser();
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(shiroUser.getUserId());
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			result = emailApprService.emailVerifyInSafeCenter(memberVo, email, request);
		}catch(AppException ae){
			result = ae.getMessage();
		}catch (Exception e) {
			result = "网络连接异常，请稍后重试！";
			e.printStackTrace();
		}
		if(BusinessConstants.SUCCESS.equals(result)){
			return new MessageBox("1","邮箱");
		}
		return new MessageBox("0",result);
	}
	
	
	/**
	 * <p>
	 * Description:安全中心-重新发送验证邮件链接<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年10月31日
	 * @param request
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = "sendEmailVerifyLinkAgain")
	@ResponseBody
	public MessageBox sendEmailVerifyLinkAgain(HttpServletRequest request,
			HttpSession session, String email) {
		String result = "success";
		try {
			ShiroUser shiroUser = currentUser();
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(shiroUser.getUserId());
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			result = emailApprService.sendVerifyEmailAgain(memberVo, request);
		}catch(AppException ae){
			result = ae.getMessage();
		}catch (Exception e) {
			result = "网络连接异常，请稍后重试！";
			e.printStackTrace();
		}
		if(BusinessConstants.SUCCESS.equals(result)){
			return new MessageBox("1","邮件发送成功！");
		}
		return new MessageBox("0",result);
	}
	
	@RequestMapping(value = "/safeCenterEmailVerifySuccess")
	public ModelAndView safeCenterEmailVerifySuccess(HttpSession session,
			HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView(
				"account/approve/email/certificationEmailSuccess");
		MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(currentUser().getUserId());
		mv.addObject("memberApproVo", memberApproVo);
		return mv;
	}

}
