package com.dxjr.portal.account.util;

import java.security.MessageDigest;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;

import com.dxjr.portal.account.vo.ShengPayForm;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.util.JsonUtils;

public class ShengpayUtils {

	/**
	 * 获取服务器时间 用于时间戳
	 * 
	 * @return 格式YYYYMMDDHHMMSS
	 */
	public static String getShengpayServerTime() {
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(CookiePolicy.RFC_2109);
		httpClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT,
				10000);
		GetMethod getMethod = new GetMethod(ShengpayConfig.server_time_url);
		getMethod.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
		getMethod.getParams().setParameter("merchantNo",
				BusinessConstants.ONLINE_PAY_SHENGPAY_MSGSENDER);
		// 执行getMethod
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK) {
				// {"responseStatus":{"statusCode":200,"reasonPhrase":"请求成功完成","family":"SUCCESSFUL"},"timestamp":"20140123091029"}
				String respString = StringUtils.trim((new String(getMethod
						.getResponseBody(), "GBK")));

//				Gson gson = new Gson();
//				Map result = gson.fromJson(respString, Map.class);
				Map result = JsonUtils.json2Map(respString);
				if (result != null) {
					statusCode = Integer.parseInt(((Map) result.get("responseStatus")).get("statusCode").toString());
					if (statusCode == 200) {
						return result.get("timestamp").toString();
					}
				}
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
		return null;
	}

	/**
	 * 获取订单信息
	 * 
	 * @return 格式YYYYMMDDHHMMSS
	 */
	public static String getShengpayrderInfo() {
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(CookiePolicy.RFC_2109);
		httpClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT,
				10000);
		GetMethod getMethod = new GetMethod(ShengpayConfig.server_time_url);
		getMethod.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
		getMethod.getParams().setParameter("merchantNo",
				BusinessConstants.ONLINE_PAY_SHENGPAY_MSGSENDER);
		// 执行getMethod
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK) {
				// {"responseStatus":{"statusCode":200,"reasonPhrase":"请求成功完成","family":"SUCCESSFUL"},"timestamp":"20140123091029"}
				String respString = StringUtils.trim((new String(getMethod
						.getResponseBody(), "GBK")));
//				Gson gson = new Gson();
//				Map result = gson.fromJson(respString, Map.class);
				Map result = JsonUtils.json2Map(respString);
				if (result != null) {
					statusCode = Integer.parseInt(((Map) result.get("responseStatus")).get("statusCode").toString());
					if (statusCode == 200) {
						return result.get("timestamp").toString();
					}
				}
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
		return null;
	}

	/*
	 * 加密
	 */
	public static ShengPayForm signForm(ShengPayForm form, String key) {
		String text = form.toSignString();
		String mac = sign(text, key, form.getCharset());
		form.setSignMsg(mac);
		return form;
	}

	/**
	 * <p>
	 * Description:签名密钥<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年1月23日
	 * @param data
	 * @param key
	 * @param charset
	 * @return String
	 */
	private static String sign(String data, String key, String charset) {
		MessageDigest msgDigest = null;
		data = data + key;
		byte[] enbyte = null;
		try {
			msgDigest = MessageDigest.getInstance("MD5");
			msgDigest.update(data.getBytes(charset));
			enbyte = msgDigest.digest();
		} catch (Exception e) {

		}
		return bin2hex(enbyte);
	}

    /**
     * <p>
     * Description:签名密钥<br />
     * </p>
     * @author justin.xu
     * @version 0.1 2014年1月23日
     * @param data
     * @param charset
     * @return
     * String
     */
	public static String signCallback(String data, String charset) {
		MessageDigest msgDigest = null;
		byte[] enbyte = null;
		try {
			msgDigest = MessageDigest.getInstance("MD5");
			msgDigest.update(data.getBytes(charset));
			enbyte = msgDigest.digest();
		} catch (Exception e) {

		}
		return bin2hex(enbyte);
	}
	
	/**
	 * 字符串转换成十六进制值
	 * 
	 * @param bin
	 *            String 我们看到的要转换成十六进制的字符串
	 * @return
	 */
	public static String bin2hex(byte[] bs) {
		char[] digital = "0123456789ABCDEF".toCharArray();
		StringBuffer sb = new StringBuffer("");
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(digital[bit]);
			bit = bs[i] & 0x0f;
			sb.append(digital[bit]);
		}
		return sb.toString();
	}
}
