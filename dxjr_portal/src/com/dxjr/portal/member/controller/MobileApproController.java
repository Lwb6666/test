package com.dxjr.portal.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.Member;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.mapper.MobileApproMapper;
import com.dxjr.portal.member.service.EmailApprService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.member.vo.EmailApproVo;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.MobileApproCnd;
import com.dxjr.portal.member.vo.MobileApproVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.util.JsonUtils;
import com.dxjr.portal.util.service.PhoneService;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:手机认证Controller<br />
 * </p>
 * 
 * @title MobileApproController.java
 * @package com.dxjr.portal.member.controller
 * @author justin.xu
 * @version 0.1 2014年4月29日
 */
@Controller
@RequestMapping(value = "/account/approve/mobile")
public class MobileApproController extends BaseController {

	private final static Logger logger = Logger.getLogger(MobileApproController.class);
	@Autowired
	private EmailApprService emailApprService;
	@Autowired
	private MobileApproService mobileApproService;
	@Autowired
	private PhoneService phoneService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MobileApproMapper mobileApproMapper;

	/**
	 * <p>
	 * Description:进入手机认证页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月25日
	 * @param session
	 * @param request
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "mobileforinsert")
	public ModelAndView mobileforinsert(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		try {
			ShiroUser shiroUser = currentUser();
			// 手机认证
			MobileApproVo mobileAppro = mobileApproService.queryMobileApproByUserId(shiroUser.getUserId());
			// 邮件认证
			EmailApproVo emailApproVo = emailApprService.queryEmailApproByUserId(shiroUser.getUserId());
			mv.addObject("mobileAppro", mobileAppro);
			mv.addObject("emailApproVo", emailApproVo);

			// 修改手机号时原有手机号是否验证通过,不为空代表通过
			if (null != session.getAttribute(BusinessConstants.MOBILE_APPRO_RESET_FUNCTION + shiroUser.getUserId())) {
				mv.addObject("flag",0);
				mv.setViewName("account/approve/mobile/newMobile");
				return mv;
			}
			if (null != mobileAppro && null != mobileAppro.getPassed() && mobileAppro.getPassed() == Constants.YES) {
				mv.setViewName("account/approve/mobile/verifyOldMobile");
			} else {
				mv.addObject("flag",1);
				mv.setViewName("account/approve/mobile/newMobile");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * <p>
	 * Description:发送手机认证验证码<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月29日
	 * @param request
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = "activeMobileAndSendMsg")
	@ResponseBody
	public String activeMobileAndSendMsg(HttpServletRequest request, HttpSession session, String mobile, String activeCode) {
		String result = "success";
		try {
			ShiroUser shiroUser = currentUser();
			result = mobileApproService.sendMobileApprValidate(mobile, request, shiroUser.getUserName(), BusinessConstants.MOBILE_APPRO_FUNCTION);
			MemberVo memberVo = new MemberVo();
			memberVo.setId(shiroUser.getUserId());

			// mobileApproService.packageMobileApproCode(memberVo, mobile,
			// activeCode, request);
		} catch (Exception e) {
			result = "网络连接异常，请刷新页面或稍后重试!";
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * <p>
	 * Description:跳转到会员手机认证界面<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年8月30日
	 * @return String
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/toMobailCheckMemberInfo")
	public ModelAndView checkMemberInfo(HttpServletRequest request) {
		Member member = currentMember();
		if (null == member || StringUtils.isEmpty(member.getUsername())) {
			return forword(BusinessConstants.TOP_HOME_ADDRESS);
		}
		// 通过用户名查询上一步的信息
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setUsername(member.getUsername());
		MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
		// 0：正式身份 -1：游客身份
		if (null != memberVo && BusinessConstants.VISITOR_OFFICIAL == memberVo.getType()) {
			return forword(BusinessConstants.TOP_HOME_ADDRESS);
		}
		return forword("/member/register_CheckMemberByMobail");
	}

	/**
	 * <p>
	 * Description:手机认证成功<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年9月01日
	 * @param request
	 * @throws Exception
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/mobailCheckSuccess")
	public ModelAndView mobailCheckSuccess(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("member/registerSucess");
		Member member = currentMember();
		String userName = null;
		if (member == null || "".equals(member.getUsername().trim())) {
			forword(BusinessConstants.NO_PAGE_FOUND_404);
		} else {
			userName = member.getUsername();
		}
		MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(member.getId());
		mv.addObject("memberApproVo", memberApproVo);
		mv.addObject("userName", null == userName ? "手机认证出现问题" : userName + "  您好，恭喜您注册并激活成功！");

		return mv;
	}

	/**
	 * <p>
	 * Description:注册-验证账户信息-发送手机认证验证码<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年8月30日
	 * @param request
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = "/sendMobailActiveByMessage")
	@ResponseBody
	public MessageBox sendMobailActiveByMessage(HttpServletRequest request, HttpSession session) {
		try {
			String result = BusinessConstants.SUCCESS;
			Member member = currentMember();

			if (null == member || StringUtils.isEmpty(member.getUsername())) {
				return new MessageBox("0", "未关联到要绑定的用户！");
			}
			result = mobileApproService.sendMobailMessageActiveCode(request, member);
			if (!BusinessConstants.SUCCESS.equals(result)) {
				return new MessageBox("0", result);
			}
			return new MessageBox("1", "发送成功，请注意查收");
		} catch (Exception e) {
			logger.error("注册-验证账户信息-发送手机认证验证码出错", e);
			return new MessageBox("0", "发送异常");
		}
	}

	private Member currentMember() {
		ShiroUser user = currentUser();
		Member member = new Member();
		member.setUsername(user.getUserName());
		member.setId(user.getUserId());
		return member;
	}

	/**
	 * <p>
	 * Description:安全中心-验证账户信息-发送手机认证验证码<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年8月30日
	 * @param request
	 * @param session
	 * @return String
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/sendMobailActiveInSaftCenter")
	@ResponseBody
	public MessageBox sendMobailActiveInSaftCenter(HttpServletRequest request, HttpSession session) {
		String result = BusinessConstants.SUCCESS;
		Member member = currentMember();
		if (StringUtils.isEmpty(member.getUsername())) {
			return new MessageBox("0", "请先登入！");
		}
		String mobile = request.getParameter("mobile");
		String activeCode = request.getParameter("activeCode");
		try {
			MobileApproVo mobileApproVo = mobileApproService.queryMobileApproByUserId(member.getId());
			// 修改手机号码操作
			if (null == session.getAttribute(BusinessConstants.MOBILE_APPRO_RESET_FUNCTION + member.getId())
					&& (null != mobileApproVo && null != mobileApproVo.getPassed() && mobileApproVo.getPassed() == Constants.YES)) {
				return new MessageBox("0", "手机号码已认证通过");
			}
			result = mobileApproService.sendMobileApprValidate(mobile, request, member.getUsername(), BusinessConstants.MOBILE_APPRO_FUNCTION);
			if (!BusinessConstants.SUCCESS.equals(result)) {
				return new MessageBox("0", result);
			}
			ShiroUser shiroUser = currentUser();
			MemberVo memberVo = new MemberVo();
			memberVo.setId(shiroUser.getUserId());

			mobileApproService.packageMobileApproCode(memberVo, mobile, activeCode, request);
			// 删除验证原有手机号码session
			session.removeAttribute(BusinessConstants.MOBILE_APPRO_RESET_FUNCTION + member.getId());
		} catch (Exception e) {
			result = "发送验证码出错,请联系客服";
			e.printStackTrace();
		}
		if (!BusinessConstants.SUCCESS.equals(result)) {
			return new MessageBox("0", result);
		}
		return new MessageBox("1", "发送成功，请注意查收");
	}

	/**
	 * <p>
	 * Description:根据当前用户的手机号发送验证码<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月30日
	 * @param request
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = "sendResetMsg")
	@ResponseBody
	public MessageBox sendResetMsg(HttpServletRequest request, HttpSession session) {
		String result = BusinessConstants.SUCCESS;
		try {
			ShiroUser shiroUser = currentUser();
			MobileApproVo mobileApproVo = mobileApproService.queryMobileApproByUserId(shiroUser.getUserId());
			result = mobileApproService.sendMobileApprValidate(mobileApproVo.getMobileNum(), request, shiroUser.getUserName(), BusinessConstants.MOBILE_APPRO_RESET_FUNCTION,
					BusinessConstants.SMS_TEMPLATE_TYPE_VERIFY_MOBILE_CODE);
		} catch (Exception e) {
			result = "网络连接异常，请刷新页面或稍后重试!";
			e.printStackTrace();
		}
		if (BusinessConstants.SUCCESS.equals(result)) {
			return new MessageBox("1", "验证码发送成功，请查收手机短信。");
		}
		return new MessageBox("0", result);
	}

	/**
	 * <p>
	 * Description:修改绑定手机时，对原来的手机号码认证<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年4月30日
	 * @param request
	 * @param session
	 * @param mobile 手机号
	 * @param activeCode 验证码
	 * @return String
	 */
	@RequestMapping(value = "verifyCurrentUserMobile")
	@ResponseBody
	public MessageBox verifyCurrentUserMobile(HttpServletRequest request, HttpSession session, String mobile, String activeCode) {
		String result = "success";
		ShiroUser shiroUser = currentUser();
		try {
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(shiroUser.getUserId());
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			memberVo.setIsModify(true);
			result = mobileApproService.saveMobileAppro(memberVo, mobile, activeCode, request, BusinessConstants.MOBILE_APPRO_RESET_FUNCTION,
					BusinessConstants.SMS_TEMPLATE_TYPE_VERIFYSUCCESS_MOBILE_CODE);
		} catch (AppException ae) {
			result = ae.getMessage();
		} catch (Exception e) {
			result = "网络连接异常，请刷新页面或稍后重试!";
			e.printStackTrace();
		}
		if (BusinessConstants.SUCCESS.equals(result)) {
			// 记录到session中，代表验证原有手机通过
			session.setAttribute(BusinessConstants.MOBILE_APPRO_RESET_FUNCTION + shiroUser.getUserId(), "true");
			return new MessageBox("1", "短信认证成功！");
		}
		return new MessageBox("0", result);
	}

	/**
	 * <p>
	 * Description:进行手机认证<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月30日
	 * @param request
	 * @param session
	 * @param mobile 手机号
	 * @param activeCode 验证码
	 * @return String
	 */
	@RequestMapping(value = "activeMobile")
	@ResponseBody
	public MessageBox activeMobile(HttpServletRequest request, HttpSession session, String mobile, String activeCode) {
		String result = "success";
		try {
			ShiroUser shiroUser = currentUser();
			MobileApproVo mobileApproVo = mobileApproService.queryMobileApproByUserId(shiroUser.getUserId());
			// 修改手机号码操作
			if (null != mobileApproVo && null != mobileApproVo.getPassed() && mobileApproVo.getPassed() == Constants.YES) {
				return new MessageBox("0", "手机号码已认证通过");
			}
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(shiroUser.getUserId());
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			result = mobileApproService.saveMobileAppro(memberVo, mobile, activeCode, request, BusinessConstants.MOBILE_APPRO_FUNCTION);
		} catch (AppException ae) {
			result = ae.getMessage();
		} catch (Exception e) {
			result = "网络连接异常，请刷新页面或稍后重试!";
			e.printStackTrace();
		}
		if (BusinessConstants.SUCCESS.equals(result)) {
			return new MessageBox("1", "短信认证成功！");
		}
		return new MessageBox("0", result);
	}

	/**
	 * <p>
	 * Description:安全中心-手机验证码校验通过<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年9月4日
	 * @param request
	 * @param user_id
	 * @param uuid
	 * @param email
	 * @return String
	 * @throws Exception
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/approMobileSuccess")
	public ModelAndView approMobileSuccess(HttpServletRequest request) throws Exception {
		Member member = currentMember();
		ModelAndView mv = new ModelAndView("account/approve/mobile/approMobileSuccess");
		mv.addObject("mobile", request.getParameter("mobile"));
		MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(member.getId());
		mv.addObject("memberApproVo", memberApproVo);
		return mv;
	}

	/**
	 * <p>
	 * Description:进行手机认证<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年8月30日
	 * @param request
	 * @param session
	 * @param mobile 手机号
	 * @param activeCode 验证码
	 * @return String
	 */
	@RequestMapping(value = "verificationMobailActiveCode")
	@ResponseBody
	public MessageBox verificationMobailActiveCode(HttpServletRequest request, HttpSession session) {
		String result = BusinessConstants.SUCCESS;
		Member member = currentMember();
		String userNameParam = "@@@@";
		String mobileParam = request.getParameter("mobile");
		String activeCodeParam = request.getParameter("activeCode");
		if (member != null) {
			userNameParam = member.getUsername();
			if (StringUtils.isEmpty(userNameParam)) {
				return new MessageBox("0", "用户名不能为空");
			}
		} else {
			return new MessageBox("0", "注册信息丢失，请重新登入或注册");
		}

		if (StringUtils.isEmpty(mobileParam)) {
			return new MessageBox("0", "用户手机号不能为空");
		}
		if (StringUtils.isEmpty(activeCodeParam)) {
			return new MessageBox("0", "手机验证码不能为空");
		}		
		if(mobileApproService.isMobileNumUsed(mobileParam)){
			return new MessageBox("0", "手机号已认证");
		}

		result = mobileApproService.verificationMobailActiveCode(request, member, mobileParam, activeCodeParam);
		if (!BusinessConstants.SUCCESS.equals(result)) {
			return new MessageBox("0", result);
		}

		return new MessageBox("1", "验证成功");
	}

	/**
	 * <p>
	 * Description:进行手机认证<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年8月30日
	 * @param request
	 * @param session
	 * @param mobile 手机号
	 * @param activeCode 验证码
	 * @return String
	 */
	@RequestMapping(value = "/jsonpVerificationMobail", produces = { "text/plain;charset=UTF-8" })
	@ResponseBody
	public String verificationMobailActiveCodeJsonp(HttpServletRequest request, HttpSession session, String jsonpcallback) {
		MessageBox box = null;
		String result = BusinessConstants.SUCCESS;
		Member member = currentMember();
		String userNameParam = "@@@@";
		String mobileParam = request.getParameter("mobile");
		String activeCodeParam = request.getParameter("activeCode");
		if (member != null) {
			userNameParam = member.getUsername();
			if (StringUtils.isEmpty(userNameParam)) {
				box = new MessageBox("0", "用户名不能为空");
				return jsonpcallback + "(" + JsonUtils.bean2Json(box) + ")";
			}
		} else {
			box = new MessageBox("0", "注册信息丢失，请重新登入或注册");
			return jsonpcallback + "(" + JsonUtils.bean2Json(box) + ")";
		}

		if (StringUtils.isEmpty(mobileParam)) {
			box = new MessageBox("0", "用户手机号不能为空");
			return jsonpcallback + "(" + JsonUtils.bean2Json(box) + ")";
		}
		if (StringUtils.isEmpty(activeCodeParam)) {

			box = new MessageBox("0", "手机验证码不能为空");
			return jsonpcallback + "(" + JsonUtils.bean2Json(box) + ")";
		}

		result = mobileApproService.verificationMobailActiveCode(request, member, mobileParam, activeCodeParam);
		if (!BusinessConstants.SUCCESS.equals(result)) {
			box = new MessageBox("0", result);
			return jsonpcallback + "(" + JsonUtils.bean2Json(box) + ")";
		}

		box = new MessageBox("1", "验证成功");
		return jsonpcallback + "(" + JsonUtils.bean2Json(box) + ")";

	}

	/**
	 * <p>
	 * Description:重设手机提交方法<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月25日
	 * @param session
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "toModifyPhone")
	public ModelAndView toModifyPhone(HttpSession session, HttpServletRequest request, String resetActiveCode) {
		ModelAndView mv = new ModelAndView("account/approve/mobile/resetMobile");
		try {
			ShiroUser shiroUser = currentUser();
			MobileApproVo mobileApproVo = mobileApproService.queryMobileApproByUserId(shiroUser.getUserId());
			mv.addObject("mobileAppro", mobileApproVo);
			// 验证验证码是否正确
			String valiateResult = phoneService.compareSmsValidate(mobileApproVo.getMobileNum(), resetActiveCode, BusinessConstants.MOBILE_APPRO_RESET_FUNCTION);
			if (valiateResult != "success") {
				mv.addObject("errorMsg", valiateResult);
			} else {
				mv.setViewName("account/approve/mobile/newMobile");
			}
		} catch (Exception e) {
			mv.addObject("errorMsg", "网络异常，请刷新页面或稍候重试");
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping(value = "/isUserMobileBinded")
	public @ResponseBody
	String isMobileNumExist() {
		String result = "success";
		try {
			ShiroUser shiroUser = currentUser();
			if(shiroUser==null || shiroUser.getUserId()==null){
				return "1"; 
			}		
			// 验证手机号是否存在						
			MobileApproCnd mobileApproCnd = new MobileApproCnd();
			mobileApproCnd.setUserId(shiroUser.getUserId());
			mobileApproCnd.setPassed(1);
			Integer count = mobileApproMapper.queryMobileApproCount(mobileApproCnd);			
			if (null != count && count.intValue() > 0) {
				return "2";
			}
		} catch (Exception e) {
			result = "failer";
			logger.error("当前用户手机已绑定！");
		}
		if (!BusinessConstants.SUCCESS.equals(result)) {
			return "0";
		}
		return "3";
	}

}
