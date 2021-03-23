package com.dxjr.portal.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.RealNameAppro;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.service.EmailApprService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.RealNameApproService;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.RealNameApproVo;
import com.dxjr.portal.tzjinterface.entity.Message;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;

/**
 * <p>
 * Description:实名认证Controller<br />
 * </p>
 * 
 * @title RealnameController.java
 * @package com.dxjr.portal.member.controller
 * @author justin.xu
 * @version 0.1 2014年4月23日
 */
@Controller
@RequestMapping(value = "/account/approve/realname")
public class RealNameApproController extends BaseController {
	private final static Logger logger = Logger.getLogger(RealNameApproController.class);

	@Autowired
	private RealNameApproService realNameApproService;
	@Autowired
	private EmailApprService emailApprService;
	@Autowired
	MemberService memberService;

	/**
	 * 
	 * <p>
	 * Description:进入实名认证页面<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月22日
	 * @param session
	 * @param request
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/toRealNameAppro")
	public ModelAndView toRealNameAppro(HttpSession session, HttpServletRequest request) {
		ModelAndView mv =  forword("/account/approve/realname/realnameApproDiv");
		ShiroUser shiroUser = currentUser();
		try {
			MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
			if ((memberApproVo == null || memberApproVo.getEmailChecked() == null || memberApproVo.getEmailChecked() != 1)
					&& (memberApproVo.getMobilePassed() == null || memberApproVo.getMobilePassed() != 1)) {
				mv.addObject("msg", "email_mobile_no_appro"); // 邮箱和手机都未认证通过！
				return mv;
			}
			RealNameApproVo realNameApproVo = realNameApproService.queryRealNameApproByUserId(shiroUser.getUserId());
			if (realNameApproVo != null && realNameApproVo.getIsPassed() == 1) {
				mv.addObject("msg", "realName_no_appro");
			}
		} catch (Exception e) {
			logger.error("异常信息", e);
		}
		return  mv;
	}



	/**
	 * <p>
	 * Description:进入实名认证页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param session
	 * @param request
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "display")
	public ModelAndView display(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("account/approve/realname/display");
		try {
			ShiroUser shiroUser = currentUser();
			MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
			if ((memberApproVo == null || memberApproVo.getEmailChecked() == null || memberApproVo.getEmailChecked() != 1)
					&& (memberApproVo.getMobilePassed() == null || memberApproVo.getMobilePassed() != 1)) {
				mv = new ModelAndView("account/safe/safetyCentre");
				mv.addObject("msg", "email_mobile_no_appro"); // 邮箱和手机都未认证通过！
				return mv;
			}
			RealNameApproVo realNameApproVo = realNameApproService.queryRealNameApproByUserId(shiroUser.getUserId());
			if (realNameApproVo != null && realNameApproVo.getIsPassed() == 1) {
				mv = new ModelAndView("account/approve/realname/realnameSuccess");
				mv.addObject("realNameApproVo", realNameApproVo);
			} else {
				// 查询实名认证对象
				RealNameApproVo realNameAppro = realNameApproService.queryRealNameApproByUserId(shiroUser.getUserId());
				mv.addObject("realNameAppro", realNameAppro);
				mv.addObject("shiroUser", shiroUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 
	 * <p>
	 * Description:进行实名认证（自动认证）<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月22日
	 * @param request
	 * @return String
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/realnameAppro")
	@ResponseBody
	public String realnameAppro(HttpServletRequest request) {
		String result = "success";
		try {
			ShiroUser shiroUser = currentUser();
			RealNameApproVo realNameApproVo = realNameApproService.queryRealNameApproByUserId(shiroUser.getUserId());
			if (realNameApproVo != null && realNameApproVo.getIsPassed() == 1) {
				return "你的实名认证已通过";
			}

			MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
			if ((memberApproVo == null || memberApproVo.getEmailChecked() == null || memberApproVo.getEmailChecked() != 1)
					&& (memberApproVo.getMobilePassed() == null || memberApproVo.getMobilePassed() != 1)) {
				return "请先进行邮箱或手机认证！";
			}
			String realname = request.getParameter("realname");
			String idcard = request.getParameter("idcard");
			result = realNameApproService.realnameAppro(shiroUser.getUserId(), realname, idcard, HttpTookit.getRealIpAddr(request));
		} catch (Exception e) {
			result = "网络连接异常，请稍候重试！";
			e.printStackTrace();
		}
		return result;
	}




	/**
	 * <p>
	 * Description:保存实名认证对象（人工认证）<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月24日
	 * @param files
	 * @param realNameAppro
	 * @param session
	 * @param request
	 * @param response
	 * @return String
	 */
	@RequestMapping(value = "saveOrUpdate")
	@ResponseBody
	public String saveRealnameAppr(@RequestParam("files") MultipartFile[] files, RealNameAppro realNameAppro, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		String result = "success";
		try {
			ShiroUser shiroUser = currentUser();
			RealNameApproVo realNameApproVo = realNameApproService.queryRealNameApproByUserId(shiroUser.getUserId());
			if (realNameApproVo != null && realNameApproVo.getIsPassed() == 1) {
				return "你的实名认证已通过";
			}
			MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
			if ((memberApproVo == null || memberApproVo.getEmailChecked() == null || memberApproVo.getEmailChecked() != 1)
					&& (memberApproVo.getMobilePassed() == null || memberApproVo.getMobilePassed() != 1)) {
				return "请先进行邮箱或手机认证！";
			}
			realNameAppro.setUserId(shiroUser.getUserId());
			result = realNameApproService.saveRealNameAppr(files, realNameAppro, request);
		} catch (Exception e) {
			result = "网络连接异常，请稍候重试！";
			e.printStackTrace();
		}
		return result;
	}
}
