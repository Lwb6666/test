package com.dxjr.wx.pay.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.lianlian.service.LianlianpayService;
import com.dxjr.portal.lianlian.utils.LLPayUtil;
import com.dxjr.portal.lianlian.vo.LianlianPayDataResponse;
import com.dxjr.portal.lianlian.vo.LianlianRetBean;
import com.dxjr.portal.util.JsonUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.wx.pay.vo.LianlianAutoReceiveBean;

/**
 * <p>
 * Description:微信充值异步回调Controller<br />
 * </p>
 * 
 * @title WXTopupAutoReceiveController.java
 * @package com.dxjr.wx.pay.controller
 * @author justin.xu
 * @version 0.1 2015年4月10日
 */
@Controller
@RequestMapping(value = "/wx/autoreceive")
public class WXTopupAutoReceiveController extends BaseController {
	private final static Logger logger = Logger.getLogger(WXTopupAutoReceiveController.class);
	@Autowired
	private LianlianpayService lianlianpayService;

	/**
	 * <p>
	 * Description:连连支付充值结果会自动调用本方法,本接口的地址每次提交支付申请时提交给盛付通<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年1月23日
	 * @param request
	 * @param session
	 * @param respons void
	 */
	@RequestMapping(value = "lianlianpay")
	public @ResponseBody
	Map<String, Object> llAutoRecevie(HttpServletRequest request, LianlianAutoReceiveBean lianlianAutoReceiveBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		LianlianRetBean lianlianRetBean = new LianlianRetBean();
		lianlianRetBean.setRet_code("0000");
		lianlianRetBean.setRet_msg("交易成功");
		try {
			String reqStr = lianlianAutoReceiveBean.getReqStr();
			if (null == reqStr || "".equals(reqStr.trim())) {
				lianlianRetBean.setRet_code("9999");
				lianlianRetBean.setRet_msg("解析数据失败");
				map.put("lianlianRetBean", JsonUtils.bean2Json(lianlianRetBean));
				return map;
			}
			LianlianPayDataResponse lianlianPayDataResponse = JsonUtils.json2Bean(StringEscapeUtils.unescapeHtml4(reqStr), LianlianPayDataResponse.class);
			// 保存支付返回信息
			lianlianpayService.savePackageAccountRechargeFeedback(lianlianPayDataResponse);
			// 处理结果 SUCCESS：支付成功
			if (null == lianlianPayDataResponse || null == lianlianPayDataResponse.getResult_pay() || !"SUCCESS".equals(lianlianPayDataResponse.getResult_pay())) {
				lianlianRetBean.setRet_code("9999");
				lianlianRetBean.setRet_msg("TransStatusError");
				map.put("lianlianRetBean", JsonUtils.bean2Json(lianlianRetBean));
				return map;
			}
			// 验签是否成功
			if (!LLPayUtil.validateSignMsg(lianlianPayDataResponse)) {
				lianlianRetBean.setRet_code("9999");
				lianlianRetBean.setRet_msg("signMsgVerifyError");
				map.put("lianlianRetBean", JsonUtils.bean2Json(lianlianRetBean));
				return map;
			}
			// 接收支付信息，更新用户帐号和充值状态
			String msg = lianlianpayService.saveAutoReceive(lianlianPayDataResponse, HttpTookit.getRealIpAddr(request));
			if (!"success".equals(msg)) {
				lianlianRetBean.setRet_code("9999");
				lianlianRetBean.setRet_msg(msg);
				map.put("lianlianRetBean", JsonUtils.bean2Json(lianlianRetBean));
				return map;
			}
		} catch (Exception e) {
			logger.error("连连支付回调出错", e);
			lianlianRetBean.setRet_code("9999");
			lianlianRetBean.setRet_msg("ExceptionERROR");
		}
		map.put("lianlianRetBean", JsonUtils.bean2Json(lianlianRetBean));
		return map;
	}
}
