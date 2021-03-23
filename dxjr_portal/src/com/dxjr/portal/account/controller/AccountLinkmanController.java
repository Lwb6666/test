package com.dxjr.portal.account.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.AccountLinkman;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.member.service.AccountLinkmanService;
import com.dxjr.security.ShiroUser;

/**
 * 
 * <p>
 * Description:关联人<br />
 * </p>
 * 
 * @title AccountLinkmanController.java
 * @package com.dxjr.portal.account.controller
 * @author yangshijin
 * @version 0.1 2014年6月4日
 */
@Controller
@RequestMapping(value = "/myaccount/accoutlinkman")
public class AccountLinkmanController extends BaseController {

	@Autowired
	private AccountLinkmanService accountLinkmanService;

	/**
	 * 进入当前用户关联人信息新增/更新页面.
	 * 
	 * @param request
	 * @return
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/toLinkman")
	public ModelAndView toLinkman(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("account/linkman/linkman");
		ShiroUser shiroUser = currentUser();
		try {
			AccountLinkman accountLinkman = accountLinkmanService.queryByUserId(shiroUser.getUserId());
			mv.addObject("accountLinkman", accountLinkman);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 保存/更新自动投标信息.
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/saveOrUpdateLinkman")
	public @ResponseBody
	String saveOrUpdateLinkman(HttpServletRequest request) {
		String randcode = (String) request.getSession().getAttribute("randomCode");
		StringBuffer msg = new StringBuffer();
		if (!randcode.equals(request.getParameter("checkCode"))) {
			msg.append("-验证码输入有误！\n");
		}
		String name = request.getParameter("name");
		String mobile = request.getParameter("mobile");
		String email = request.getParameter("email");
		String relationship = request.getParameter("relationship");

		ShiroUser shiroUser = currentUser();
		try {
			accountLinkmanService.saveOrUpdateLinkman(shiroUser.getUserId(), name, mobile, email, relationship, request.getRemoteAddr());
			msg.append("OK,Rocky.J!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg.toString();
	}
}
