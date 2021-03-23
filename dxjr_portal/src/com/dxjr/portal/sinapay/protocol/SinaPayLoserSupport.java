package com.dxjr.portal.sinapay.protocol;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.dxjr.portal.sinapay.service.SinapayService;
import com.dxjr.portal.sinapay.tool.SignUtil;
import com.dxjr.portal.sinapay.tool.Tools;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.HttpTookit;

/**
 * <p>
 * Description:新浪充值在线补单<br />
 * </p>
 * 
 * @title SinaPayLoserSupport.java
 * @package com.dxjr.portal.sinapay.protocol
 * @author hujianpan
 * @version 0.1 2015年5月27日
 */
public class SinaPayLoserSupport {
	/**
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2015年5月25日
	 * @param v_oid
	 * @param start_time
	 * @param end_time
	 * @param str_date1
	 * @return Map<String,String>
	 */
	public static Map<String, String> initQueryB2COrderParameter(String v_oid, String start_time, String end_time, String str_date1) {
		Map<String, String> requestParamterMap = new HashMap<String, String>();
		requestParamterMap.put("service", BusinessConstants.ONLINE_PAY_SINAPAY_QUERY_B2C_ORDERENAME);// 服务名称
		requestParamterMap.put("version", BusinessConstants.ONLINE_PAY_SINAPAY_CREATE_INSTANT_ORDER_SERVICEVERSION);// 接口版本
		requestParamterMap.put("request_time", str_date1);// 请求时间
		requestParamterMap.put("partner_id", BusinessConstants.ONLINE_PAY_SINAPAY_PARTNER_ID);// 合作者身份ID
		requestParamterMap.put("_input_charset", "UTF-8");// 参数编码字符集

		requestParamterMap.put("start_time", start_time);// 开始时间
		requestParamterMap.put("end_time", end_time);// 结束时间
		requestParamterMap.put("out_trade_no", v_oid);// 商户订单号
		return requestParamterMap;
	}

	public static void generateSignForQuery(String _input_charset, String sign_type, final String MD5Key, final String RSASignKey, Map<String, String> requestParamterMap) {
		String content = Tools.createLinkString(requestParamterMap, false);
		String signKey = "";

		if (BusinessConstants.ONLINE_PAY_SINAPAY_MD5.equalsIgnoreCase(sign_type)) {
			signKey = MD5Key;
		} else {
			signKey = RSASignKey;
		}
		try {
			String sign = SignUtil.sign(content, sign_type, signKey, _input_charset);
			requestParamterMap.put("sign", sign);// 签名
			requestParamterMap.put("sign_type", sign_type);// 签名方式
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean checkSignForQueryOrder(Map<String, String> queryContent, String isSignKeyOK) throws Exception {
		String sign_result = queryContent.get("sign").toString();
		String sign_type_result = queryContent.get("sign_type").toString();
		String _input_charset_result = queryContent.get("_input_charset").toString();
		queryContent.remove("sign");
		queryContent.remove("sign_type");
		queryContent.remove("sign_version");
		String like_result = Tools.createLinkString(queryContent, false);
		final boolean check_sign = SignUtil.Check_sign(like_result.toString(), sign_result, sign_type_result, isSignKeyOK, _input_charset_result);
		return check_sign;
	}

	public static boolean isQueryResult(Map<String, String> queryContent) {
		return !queryContent.get("total_item").equals("0") && "APPLY_SUCCESS".equals(queryContent.get("response_code"));
	}

	/**
	 * <p>
	 * Description:校验输入参数是否为空<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2015年5月24日
	 * @param v_oid
	 * @param startTime
	 * @param endTime
	 * @return String
	 */
	public static String verifiyParameterHasValueForLoser(String v_oid, String startTime, String endTime) {
		if (StringUtils.isEmpty(v_oid)) {
			return "订单号不能为空";
		}
		if (startTime == null) {
			return "充值开始时间不能为空";
		}
		if (endTime == null) {
			return "充值结束时间不能为空";
		}
		return StringUtils.EMPTY;
	}

	public static String processLoseOrder(String v_oid, HttpServletRequest request, Map<String, String> queryContent, SinapayService sinapayService) {
		String recordList = queryContent.get("record_list");
		if (recordList == null) {
			return "没有符合条件的交易记录！";
		}
		// 定义第一次分割符
		String reg = "[$]";
		String reg2 = "[~]";
		String reg3 = "[|]";
		String reg4 = "[\\^]";
		// 声明map将String数组中的内容按照key=value放到map中
		String[] arys = recordList.split(reg);

		for (String item : arys) {
			String[] arys2 = item.split(reg2);
			// 判断状态是否正确
			if (null == arys2 || arys2[0] == null) {
				return "";
			}
			if (v_oid.equals(arys2[0]) && null != arys2[12]) {
				// 处理业务
				String[] arys3 = arys2[12].split(reg3);
				String[] payList = null;
				if (null != arys3 && arys3.length != 0) {
					payList = arys3[0].split(reg4);
				}

				try {

					SinaQueryRecordListReponse sinaQueryRecordListReponse = SinaQueryRecordListReponse.buildSinaQueryRecordListReponse(arys2[0], arys2[2], arys2[3], arys2[3]);
					// 保存回调信息
					sinapayService.savePackageLoseFeedBack(sinaQueryRecordListReponse);
					// 接收支付信息，更新用户帐号和充值状态
					String msg = sinapayService.saveLoseOrder(sinaQueryRecordListReponse, HttpTookit.getRealIpAddr(request));
					return msg;
				} catch (Exception e) {
					return "新浪支付补单出错";
				}

			}
		}
		return "没有找到订单！";
	}
}
