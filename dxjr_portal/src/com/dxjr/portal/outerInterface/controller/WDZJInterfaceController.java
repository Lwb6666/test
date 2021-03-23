package com.dxjr.portal.outerInterface.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.portal.outerInterface.service.WDZJInterfaceService;
import com.dxjr.utils.HttpTookit;

/**
 * 
 * <p>
 * Description:网贷之家接口Controller<br />
 * </p>
 * 
 * @title WDZJInterfaceController.java
 * @package com.dxjr.portal.outerInterface.controller
 * @author yangshijin
 * @version 0.1 2014年8月19日
 */
@Controller
@RequestMapping(value = "/wdzj/api/")
public class WDZJInterfaceController {

	@Autowired
	private WDZJInterfaceService wdzjInterfaceService;

	/**
	 * 
	 * <p>
	 * Description:获取当前正在进行投标中的标信息(网贷之家接口)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "interface/getNowProjects")
	public @ResponseBody
	String getNowProjects(HttpServletRequest request) {
		String result = "-2";
		try {
			result = wdzjInterfaceService.getNowProjects(
					HttpTookit.getRealIpAddr(request),
					request.getHeader("Referer"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取某天内满标的借款标信息(网贷之家接口)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "interface/getProjectsByDate")
	public @ResponseBody
	String getProjectsByDate(HttpServletRequest request) {
		String result = "-2";
		try {
			String date = request.getParameter("date"); // 格式为:yyyy-MM-dd
			result = wdzjInterfaceService.getProjectsByDate(
					HttpTookit.getRealIpAddr(request),
					request.getHeader("Referer"), date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取今天内满标的借款标信息(网贷之家接口)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "interface/getTodayProjects")
	public @ResponseBody
	String getTodayProjects(HttpServletRequest request) {
		String result = "-2";
		try {
			result = wdzjInterfaceService.getTodayProjects(
					HttpTookit.getRealIpAddr(request),
					request.getHeader("Referer"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取某天还款的标的信息.(网贷之家接口)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "interface/getPaiedLoanInfo")
	public @ResponseBody
	String getPaiedLoanInfo(HttpServletRequest request) {
		String result = "-2";
		try {
			String date = request.getParameter("date"); // 格式为:yyyy-MM-dd
			result = wdzjInterfaceService.getPaiedLoanInfo(
					HttpTookit.getRealIpAddr(request),
					request.getHeader("Referer"), date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 验证用户是否是平台用户.(网贷之家接口)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "interface/checkUserInfo")
	public @ResponseBody
	String checkUserInfo(HttpServletRequest request) {
		String result = "-2";
		try {
			// 获取用户名
			String userName = request.getParameter("userName");
			// 获取真实姓名
			String realName = request.getParameter("realName");
			// 获取身份证号码
			String cardId = request.getParameter("cardId");
			result = wdzjInterfaceService.checkUserInfo(
					HttpTookit.getRealIpAddr(request),
					request.getHeader("Referer"), userName, realName, cardId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
