package com.dxjr.portal.index.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.index.service.IndexService;
import com.dxjr.portal.util.JsonUtils;

/**
 * @title IndexController.java
 * @package com.dxjr.portal.index.controller
 * @author chenlu
 * @version 0.1 2014年8月6日
 */
@Controller
@RequestMapping(value = "/feedback")
public class FeedbackController extends BaseController {

	private final static Logger logger = Logger.getLogger(FeedbackController.class);

	@Autowired
	private IndexService indexService;

	/**
	 * 
	 * <p>
	 * Description:提交手机号<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年9月20日
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/feedback")
	public @ResponseBody
	String feedback(HttpServletRequest request) {
		String result = "success";
		String mobileNum = request.getParameter("mobileNum");
		try {
			if (mobileNum != null && !mobileNum.equals("")) {
				result = indexService.savefeedback(mobileNum);
			} else {
				result = "请输入手机号！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("提交手机号失败！");
			result = "系统繁忙，请稍后重试！";
		}
		return result;
	}

	/**
	 * 推广注册
	 */
	@RequestMapping(value = "/feedbackJsonp", produces = { "text/plain;charset=UTF-8" })
	@ResponseBody
	public String feedbackJsonp(HttpServletRequest request, String jsonpcallback) {
		String result = "success";
		String mobileNum = request.getParameter("mobileNum");
		try {
			if (mobileNum != null && !mobileNum.equals("")) {
				result = indexService.savefeedback(mobileNum);
			} else {
				result = "请输入手机号！";
			}
		} catch (Exception e) {
			logger.error("提交手机号失败！");
			result = "系统繁忙，请稍后重试！";
		}
		return jsonpcallback + "(" + JsonUtils.bean2Json(result) + ")";
	}

}
