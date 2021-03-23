package com.dxjr.portal.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.util.JsonUtils;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:广告推广Controller<br />
 * </p>
 * 
 * @title AdvertisementController.java
 * @package com.dxjr.portal.member.controller
 * @author hujianpan
 * @version 0.1 2015年1月13日
 */
@Controller
@RequestMapping(value = "/advertisement")
public class AdvertisementController {

	private final static Logger logger = Logger.getLogger(AdvertisementController.class);
	@Autowired
	private MobileApproService mobileApproService;

	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2015年6月2日
	 * @param request
	 * @param jsonpcallback
	 * @return String
	 */
	@RequestMapping(value = "/baidu/sendMobailCode", produces = { "text/plain;charset=UTF-8" })
	@ResponseBody
	public String sendMobailActiveWithBaiduAdvertisement(HttpServletRequest request, String jsonpcallback) {
		String result = BusinessConstants.SUCCESS;
		String mobile = request.getParameter("mobile");
		try {
			if (!StringUtils.isEmpty(mobile)) {

				if (!verifyMobail(mobile)) {
					return jsonpcallback + "(" + JsonUtils.bean2Json(new MessageBox("0", "请输入正确的手机号！")) + ")";
				}
				if (!mobileApproService.isMobileNumUsed(mobile)) {
					result = mobileApproService.sendMobileApprValidate(mobile, request, "", BusinessConstants.MOBILE_APPRO_FUNCTION, BusinessConstants.SMS_TEMPLATE_TYPE_REGIST_MOBILE_CODE);

				} else {
					return jsonpcallback + "(" + JsonUtils.bean2Json(new MessageBox("0", "手机号已被使用！")) + ")";
				}
			} else {
				return jsonpcallback + "(" + JsonUtils.bean2Json(new MessageBox("0", "手机号不能为空！")) + ")";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!BusinessConstants.SUCCESS.equals(result)) {
			return jsonpcallback + "(" + JsonUtils.bean2Json(new MessageBox("0", result)) + ")";
		}
		return jsonpcallback + "(" + JsonUtils.bean2Json(new MessageBox("1", "发送成功，请注意查收")) + ")";
	}

	/**
	 * <p>
	 * Description:高铁广告推广-发送手机认证验证码<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年8月30日
	 * @param request
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = "gaotie/sendMobailCode", produces = { "text/plain;charset=UTF-8" })
	@ResponseBody
	public String sendMobailActiveWithGaoTieAdvertisement(HttpServletRequest request, String jsonpcallback) {
		String result = BusinessConstants.SUCCESS;
		String mobile = request.getParameter("mobile");
		try {
			if (!StringUtils.isEmpty(mobile)) {

				if (!verifyMobail(mobile)) {
					return jsonpcallback + "(" + JsonUtils.bean2Json(new MessageBox("0", "请输入正确的手机号！")) + ")";
				}
				if (!mobileApproService.isMobileNumUsed(mobile)) {
					result = mobileApproService.sendMobileApprValidate(mobile, request, "", BusinessConstants.MOBILE_APPRO_FUNCTION, BusinessConstants.SMS_TEMPLATE_TYPE_GT_ADVERTISEMENT_REGIST);

				} else {
					return jsonpcallback + "(" + JsonUtils.bean2Json(new MessageBox("0", "手机号已被使用！")) + ")";
				}
			} else {
				return jsonpcallback + "(" + JsonUtils.bean2Json(new MessageBox("0", "手机号不能为空！")) + ")";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!BusinessConstants.SUCCESS.equals(result)) {
			return jsonpcallback + "(" + JsonUtils.bean2Json(new MessageBox("0", result)) + ")";
		}
		return jsonpcallback + "(" + JsonUtils.bean2Json(new MessageBox("1", "发送成功，请注意查收")) + ")";
	}

	private Boolean verifyMobail(String mobile) {
		if (mobile.trim().length() != 11) {
			return false;
		}
		try {
			Long.parseLong(mobile.trim());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * Description:进行手机校验码认证，并生成账号<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年12月31日
	 * @param request
	 * @param session
	 * @param mobile 手机号
	 * @param activeCode 验证码
	 * @return String
	 */
	@RequestMapping(value = "gaotie/activeMobile", produces = { "text/plain;charset=UTF-8" })
	@ResponseBody
	public String GTactiveMobile(HttpServletRequest request, HttpSession session, String mobile, String activeCode, String jsonpcallback) {
		String result = "success";
		try {
			if (StringUtils.isEmpty(mobile)) {
				return jsonpcallback + "(" + JsonUtils.bean2Json(new MessageBox("0", "手机号不能为空！")) + ")";
			}
			if (StringUtils.isEmpty(activeCode)) {
				return jsonpcallback + "(" + JsonUtils.bean2Json(new MessageBox("0", "手机验证码不能为空！")) + ")";
			}
			result = mobileApproService.saveMobile(mobile, activeCode, request, BusinessConstants.MOBILE_APPRO_FUNCTION, session);
		} catch (AppException ae) {
			result = ae.getMessage();
		} catch (Exception e) {
			result = "网络连接异常，请刷新页面或稍后重试!";
			logger.error(e.getStackTrace());
		}
		if (BusinessConstants.SUCCESS.equals(result)) {
			return jsonpcallback + "(" + JsonUtils.bean2Json(new MessageBox("1", "短信认证成功！")) + ")";
		}
		return jsonpcallback + "(" + JsonUtils.bean2Json(new MessageBox("0", result)) + ")";
	}
}
