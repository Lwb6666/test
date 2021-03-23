package com.dxjr.portal.member.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.base.BaseController;
import com.dxjr.security.CookieRetrievingCookieGenerator;

/**
 * <p>
 * Description:会员action,非https请求<br />
 * </p>
 * 
 * @title MemberHttpController.java
 * @package com.dxjr.portal.member.controller
 * @author justin.xu
 * @version 0.1 2015年2月2日
 */
@Controller
@RequestMapping
public class MemberHttpController extends BaseController {

	@Autowired
	private CookieRetrievingCookieGenerator cookieRetrievingCookieGenerator;

	/**
	 * 以ajax方式登出
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletResponse response) {
		SecurityUtils.getSubject().logout();
		cookieRetrievingCookieGenerator.removeCookie(response);
		return redirect("/home/index.html");
	}

}
