package com.dxjr.portal.account.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.portal.account.service.ChinabankService;
import com.dxjr.portal.account.vo.ChinabankReceiveForm;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.lianlian.service.LianlianpayService;
import com.dxjr.portal.lianlian.utils.LLPayUtil;
import com.dxjr.portal.lianlian.vo.LianlianPayDataResponse;
import com.dxjr.portal.lianlian.vo.LianlianRetBean;
import com.dxjr.portal.sinapay.protocol.SinaPayNotifyRequest;
import com.dxjr.portal.sinapay.protocol.SinaPayRechargeSupport;
import com.dxjr.portal.sinapay.service.SinapayService;
import com.dxjr.portal.util.JsonUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:充值异步回调Controller<br />
 * </p>
 * 
 * @title TopupAutoReceiveController.java
 * @package com.dxjr.portal.account.controller
 * @author justin.xu
 * @version 0.1 2015年2月6日
 */
@Controller
@RequestMapping(value = "/autoreceive")
public class TopupAutoReceiveController extends BaseController {
	private final static Logger logger = Logger.getLogger(TopupAutoReceiveController.class);
	@Autowired
	private ChinabankService chinabankService;
	@Autowired
	private SinapayService sinapayService;
	@Autowired
	private LianlianpayService lianlianpayService;

	/**
	 * <p>
	 * Description:网银在线充值结果会自动调用本方法<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年1月23日
	 * @param request
	 * @param session
	 * @param response void
	 */
	@RequestMapping(value = "chinabank")
	public @ResponseBody
	String chinabankAutoRecevie(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		String result = "ok";
		try {
			request.setCharacterEncoding("GBK");
			ChinabankReceiveForm chinabankReceiveForm = new ChinabankReceiveForm();
			chinabankReceiveForm.setV_oid(request.getParameter("v_oid")); // 订单号
			chinabankReceiveForm.setV_pmode(request.getParameter("v_pmode")); // 支付方式中文说明，如"中行长城信用卡"
			chinabankReceiveForm.setV_pstatus(request.getParameter("v_pstatus")); // 支付结果，20支付完成；30支付失败；
			chinabankReceiveForm.setV_pstring(request.getParameter("v_pstring")); // 对支付结果的说明，成功时（v_pstatus=20）为"支付成功"，支付失败时（v_pstatus=30）为"支付失败"
			chinabankReceiveForm.setV_amount(request.getParameter("v_amount")); // 订单实际支付金额
			chinabankReceiveForm.setV_moneytype(request.getParameter("v_moneytype")); // 币种
			chinabankReceiveForm.setV_md5str(request.getParameter("v_md5str")); // MD5校验码
			chinabankReceiveForm.setRemark1(request.getParameter("remark1")); // 备注1
			chinabankReceiveForm.setRemark2(request.getParameter("remark2")); // 备注2

			// 保存支付返回信息
			chinabankService.saveAccountRechargeFeedback(chinabankReceiveForm);
			// 接收支付信息，更新用户帐号和充值状态
			String msg = chinabankService.saveAutoReceive(chinabankReceiveForm, request);
			if (!"success".equals(msg)) {
				return msg;
			}
		} catch (AppException ae) {
			return ae.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return "ExceptionERROR";
		}
		return result;
	}

	/**
	 * <p>
	 * Description:新浪支付充值结果会自动调用本方法,本接口的地址每次提交支付申请时提交给盛付通<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @modify hujianpan
	 * @version 0.1 2014年1月23日
	 * @param request
	 * @param session
	 * @param respons void
	 */
	@RequestMapping(value = "sinapay")
	public @ResponseBody
	String sinaAutoRecevie(HttpServletRequest request, HttpSession session, HttpServletResponse response, SinaPayNotifyRequest sinaPayNotifyRequest) {
		Assert.notNull(sinaPayNotifyRequest, "sinaPayNotifyRequest must not be null");
		return SinaPayRechargeSupport.processNotify(sinapayService, sinaPayNotifyRequest, request);
	}

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
	String llAutoRecevie(HttpServletRequest request) {

		LianlianRetBean lianlianRetBean = new LianlianRetBean();
		lianlianRetBean.setRet_code("0000");
		lianlianRetBean.setRet_msg("交易成功");
		try {
			// 读取request流
			String reqStr = LLPayUtil.readReqStr(request);
			if (null == reqStr || "".equals(reqStr.trim())) {
				lianlianRetBean.setRet_code("9999");
				lianlianRetBean.setRet_msg("解析数据失败");
				return JsonUtils.bean2Json(lianlianRetBean);
			}
			LianlianPayDataResponse lianlianPayDataResponse = JsonUtils.json2Bean(reqStr, LianlianPayDataResponse.class);
			// 保存支付返回信息
			lianlianpayService.savePackageAccountRechargeFeedback(lianlianPayDataResponse);
			// 处理结果 SUCCESS：支付成功
			if (null == lianlianPayDataResponse || null == lianlianPayDataResponse.getResult_pay() || !"SUCCESS".equals(lianlianPayDataResponse.getResult_pay())) {
				lianlianRetBean.setRet_code("9999");
				lianlianRetBean.setRet_msg("TransStatusError");
				return JsonUtils.bean2Json(lianlianRetBean);
			}
			// 验签是否成功
			if (!LLPayUtil.validateSignMsg(lianlianPayDataResponse)) {
				lianlianRetBean.setRet_code("9999");
				lianlianRetBean.setRet_msg("signMsgVerifyError");
				return JsonUtils.bean2Json(lianlianRetBean);
			}
			// 接收支付信息，更新用户帐号和充值状态
			String msg = lianlianpayService.saveAutoReceive(lianlianPayDataResponse, HttpTookit.getRealIpAddr(request));
			if (!"success".equals(msg)) {
				lianlianRetBean.setRet_code("9999");
				lianlianRetBean.setRet_msg(msg);
				return JsonUtils.bean2Json(lianlianRetBean);
			}
		} catch (Exception e) {
			logger.error("连连支付回调出错", e);
			lianlianRetBean.setRet_code("9999");
			lianlianRetBean.setRet_msg("ExceptionERROR");
		}
		return JsonUtils.bean2Json(lianlianRetBean);
	}

}
