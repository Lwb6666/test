package com.dxjr.portal.sinapay.protocol;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.dxjr.portal.account.vo.TopupMoneyVo;
import com.dxjr.portal.sinapay.service.SinapayService;
import com.dxjr.portal.sinapay.tool.SignUtil;
import com.dxjr.portal.sinapay.tool.Tools;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.file.FileUtil;

public class SinaPayRechargeSupport {
	public static Map<String, String> initCreateInstantOrderParameter(Integer userId, TopupMoneyVo topupMoneyVo, Date currentTime) {

		Map<String, String> params = new HashMap<String, String>();
		String str_date1 = DateUtils.format(currentTime, DateUtils.YMDHMS); // 将日期时间格式化

		// 商户订单号
		String merOrderNum = "gcjr_" + str_date1 + "_" + userId;

		String request_time = str_date1; // 请求时间

		/**
		 * 参数编码字符集：UTF-8 GBK GB2312
		 */
		String _input_charset = BusinessConstants.ONLINE_PAY_SINAPAY_INPUT_CHARSET;

		String seller_identity_id = BusinessConstants.ONLINE_PAY_SINAPAY_PARTNER_ID;// 卖家标志
		String seller_identity_type = "MEMBER_ID";// 卖家标志类型 ID的类型，参考'标志类型'

		String out_trade_no = merOrderNum;// 商户订单号
		String amount = String.valueOf(topupMoneyVo.getMoney().setScale(2, RoundingMode.DOWN));// 充值金额
		String product_desc = "新浪在线充值";
		/**
		 * online_bank (网银支付)
		 * 格式：支付方式^金额^扩展|支付方式^金额^扩展。扩展信息内容以“，”f分割，针对不同支付方式的扩展定义见附录。
		 */
		String pay_bankmethod = "online_bank";// 支付方式

		String online_bank_card_type = "DEBIT";// 卡类型: DEBIT 借记|CREDIT
		// 贷记（信用卡）
		if (topupMoneyVo.getPayChannel().equals("debit")) {
			online_bank_card_type = "DEBIT";
		} else if (topupMoneyVo.getPayChannel().equals("credit")) {
			online_bank_card_type = "CREDIT";
		}
		String online_bank_bankid;
		if (StringUtils.isEmpty(topupMoneyVo.getBankcode())) {
			return null;
		}
		// online_bank_bankid = "TESTBANK";// 银行编码 测试银行号
		online_bank_bankid = topupMoneyVo.getBankcode();

		/**
		 * 卡属性：C 对私|B 对公
		 */
		String online_bank_card_attribute = "C";
		pay_bankmethod = pay_bankmethod + "^" + amount + "^" + online_bank_bankid + "," + online_bank_card_type + "," + online_bank_card_attribute;

		params.put("service", BusinessConstants.ONLINE_PAY_SINAPAY_CREATE_INSTANT_ORDER_SERVICENAME);
		params.put("version", BusinessConstants.ONLINE_PAY_SINAPAY_CREATE_INSTANT_ORDER_SERVICEVERSION);
		params.put("request_time", request_time);
		params.put("partner_id", BusinessConstants.ONLINE_PAY_SINAPAY_PARTNER_ID);
		params.put("_input_charset", _input_charset);
		params.put("seller_identity_id", seller_identity_id);
		params.put("out_trade_no", out_trade_no);
		params.put("seller_identity_type", seller_identity_type);
		params.put("amount", amount);
		params.put("product_desc", product_desc);
		params.put("pay_method", pay_bankmethod);
		params.put("return_url", BusinessConstants.ONLINE_PAY_SINAPAY_RETURN_URL);
		params.put("notify_url", BusinessConstants.ONLINE_PAY_SINAPAY_NOTIFY_URL);
		return params;
	}

	public static String generateFormForRecharge(Map<String, String> params, HttpServletRequest request) throws UnsupportedEncodingException, IOException {
		// 获取数据签名
		params.put("sign", generateSignForRecharge(params, request));
		params.put("sign_type", BusinessConstants.ONLINE_PAY_SINAPAY_SIGN_TYPE);

		String html = "<form name='sinapay_checkout' id='sinapay_checkout' method='post'>";
		for (Map.Entry<String, String> m : params.entrySet()) {
			html += "<input type='hidden' name='" + m.getKey() + "' value='" + URLEncoder.encode(m.getValue(), params.get("_input_charset")) + "'/>";
		}
		html += "<script type = 'text/javascript'>";
		String url = BusinessConstants.ONLINE_PAY_SINAPAY_SERVICEURL;
		html += "document.sinapay_checkout.action = '" + url + "';";
		html += "document.sinapay_checkout.submit();";
		html += "</script>";
		return html;
	}

	public static String generateSignForRecharge(Map<String, String> params, HttpServletRequest request) {

		String content = Tools.createLinkString(params, false);
		String signKey = "";

		if (BusinessConstants.ONLINE_PAY_SINAPAY_MD5.equalsIgnoreCase(BusinessConstants.ONLINE_PAY_SINAPAY_SIGN_TYPE)) {
			signKey = BusinessConstants.ONLINE_PAY_SINAPAY_MD5KEY;
		} else {
			try {
				signKey = FileUtil.readFile(request.getRealPath("") + "/WEB-INF/classes/sinapay/rsa_sign_private.pem");
			} catch (IOException e) {
				return StringUtils.EMPTY;
			}
		}
		try {
			String sign = SignUtil.sign(content, BusinessConstants.ONLINE_PAY_SINAPAY_SIGN_TYPE, signKey, params.get("_input_charset"));
			return sign;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 
	 * <p>
	 * Description:新浪支付会进行7次回调，分别间隔：2, 10, 10, 60, 120, 360, 900（分钟）<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2015年5月27日
	 * @param sinapayService
	 * @param sinaPayNotifyRequest
	 * @param request
	 * @return String
	 */
	public static String processNotify(SinapayService sinapayService, SinaPayNotifyRequest sinaPayNotifyRequest, HttpServletRequest request) {
		String msg = StringUtils.EMPTY;

		final String charset = request.getParameter("_input_charset");
		String trade_status = request.getParameter("trade_status");
		String sign = request.getParameter("sign");
		String sign_type = request.getParameter("sign_type");// 签名类型
		sinaPayNotifyRequest.set_input_charset(charset);// 设置字符编码

		if (sinaPayNotifyRequest.hasNullParameter()) {
			return "参数为空";
		}

		SinaPayReponse sinaPayReponse = SinaPayReponse.buildSinaPayReponse(sinaPayNotifyRequest, trade_status);

		String like_result = Tools.createLinkString(Tools.getParameterMap(request, true), false);
		String signKey = "";
		if (BusinessConstants.ONLINE_PAY_SINAPAY_MD5.equalsIgnoreCase(sign_type.toString())) {
			signKey = BusinessConstants.ONLINE_PAY_SINAPAY_MD5KEY;
		} else {
			try {
				signKey = FileUtil.readFile(request.getRealPath("") + "/WEB-INF/classes/sinapay/rsa_sign_public.pem");
			} catch (IOException e) {
				return "密钥加载出现异常";
			}
		}

		try {
			// 回调签名进行判断
			if (StringUtils.isEmpty(like_result)) {
				return "参数为空";
			}
			if (SignUtil.Check_sign(like_result.toString(), sign, sign_type, signKey, charset)) {
				switch (trade_status) {
				case "PAY_FINISHED":
					// 接收支付信息，更新用户帐号和充值状态
					msg = sinapayService.saveAutoReceive(sinaPayReponse, request);
					break;
				case "TRADE_FAILED":
					// 交易失败
					msg = "trade_failed";
					break;
				case "TRADE_FINISHED":
					// 交易结束，代表交易完全成功
					msg = "success";
					break;
				case "TRADE_CLOSED":
					// 交易关闭，代表交易到关闭时间客户未付款，交易失败
					msg = "trade_closed";
					break;
				default:
					msg = "通知业务类型错误！";
					sinaPayReponse.setPayResult(msg);
					break;
				}
			} else {
				msg = "sign error!";
				sinaPayReponse.setPayResult(msg);
			}
		} catch (Exception e) {
			msg = "exception";
			sinaPayReponse.setPayResult(msg);
		} finally {
			// 保存支付返回信息
			try {
				sinapayService.saveAccountRechargeFeedback(sinaPayReponse);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return msg;

	}

}
