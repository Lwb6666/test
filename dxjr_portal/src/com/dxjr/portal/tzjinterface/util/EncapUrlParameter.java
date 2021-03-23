package com.dxjr.portal.tzjinterface.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

import com.dxjr.portal.tzjinterface.constant.TZJConstants;
import com.dxjr.utils.MD5;

/**
 * 
 * @author hujianpan
 * @desc 封装URL请求的参数
 */
public class EncapUrlParameter {

	public static final String UTF_8_CHARSET = new String("UTF-8");

	public static void mains(String[] args) {
		// strTest = URLEncoder.encode(strTest, "UTF-8");
		// System.out.println(strTest);
		// strTest = URLDecoder.decode(strTest,"UTF-8");
		Map<String, String> params = new TreeMap<String, String>();
		// params.put("from","touzhijia");
		// params.put("oid","1000000001");
		// params.put("username","tzjtest");
		// params.put("email","com_panpan@162.com");
		// params.put("telephone","18321836453");
		params.put("bid", "38244");
		// params.put("sign","18321836453");

		Map<String, String> params2 = new TreeMap<String, String>();
		// params2.put("from","ijwzDDukg9Qxgmmq870lPw==");
		// params2.put("oid","MDQzNTcyMTA1RDM4MEE1QkYyNTg5MzZBRTA2M0Q5RUM=");
		// params2.put("username","OUY4NTBGMDM3RDQ1NTgwMA==");
		// params2.put("email","MTk0REVERTc5RDhERDExMDY5MzhGNjk0MDczNzIzRDcyQ0U1Qjk2ODI4NDdEQ0VD");
		// params2.put("telephone","QTRCRTU1M0FGOTEwQ0QyRjczQjhCODBCRDQxNjUwMzc=");
		params2.put("bid", "fUbZ%2BQxgQ3k%3D");
		// params2.put("bid","fUbZ+QxgQ3k=");
		params2.put("sign", "20be502b33fc07a96411c11b26a2a1b0");

		try {
			new EncapUrlParameter().buildEncryptParameter(params,
					TZJConstants.GET_URL,"1454545545");
			new EncapUrlParameter().buildDecryptionParameter(params2,
					TZJConstants.GET_URL,"1454545454");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * <p>
	 * Description:构造URL请求的加密参数<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年10月15日
	 * @param map
	 * @return
	 * @throws Exception
	 *             String
	 */
	public static String buildEncryptParameter(Map<String, String> map,
			String askMode, String timestamp) throws Exception {

		StringBuilder paramsUncoded = new StringBuilder(map.size() * 16);
		StringBuilder paramsEncoded = new StringBuilder(map.size() * 16);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			// sign参数放在最后
			if (!"sign".equals(entry.getKey()) && !"timestamp".equals(entry.getKey())) {
				paramsUncoded.append(entry.getKey()).append("=")
						.append(entry.getValue()).append("&");

				if (TZJConstants.GET_URL.equals(askMode)) { // GET 请求
					paramsEncoded
							.append(entry.getKey())
							.append("=")
							//
							.append(URLEncoder.encode(
									new String(
											(DES3.encryptModeTzj(entry.getValue(),timestamp)
													.getBytes(EncapUrlParameter.UTF_8_CHARSET))),
									EncapUrlParameter.UTF_8_CHARSET))
							.append("&");
				} else if (TZJConstants.POST_URL.equals(askMode)) {// POST 请求
					paramsEncoded
							.append(entry.getKey())
							.append("=")
							.append(new String((DES3.encryptModeTzj(entry
									.getValue(),timestamp)
									.getBytes(EncapUrlParameter.UTF_8_CHARSET))))
							.append("&");
				}

			}
		}
		final String substring = new String(paramsUncoded.toString()
				.substring(0, paramsUncoded.toString().length() - 1)
				.getBytes(UTF_8_CHARSET), UTF_8_CHARSET);
		paramsEncoded.append("sign").append("=").append(EncryptMD5.toMD5(substring));
		paramsEncoded.append("timestamp").append("=").append(timestamp);
		System.out.println(paramsEncoded.toString());
		return paramsEncoded.toString();
	}

	/**
	 * 
	 * <p>
	 * Description:构造URL请求的加密参数<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年10月15日
	 * @param map
	 * @return
	 * @throws Exception
	 *             String
	 */
	public static Map<String, String> buildEncryptParameter(
			Map<String, String> map,String timestamp) throws Exception {

		Map<String, String> returnMap = new TreeMap<String, String>();
		StringBuilder paramsUncoded = new StringBuilder(map.size() * 16);
		StringBuilder paramsEncoded = new StringBuilder(map.size() * 16);
		returnMap.put("timestamp", timestamp);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			// sign参数放在最后
			if (!"sign".equals(entry.getKey()) && !"timestamp".equals(entry.getKey())) {
				String key = entry.getKey();
				String value = new String(
						(DES3.encryptModeTzj(entry.getValue(),timestamp)
								.getBytes(EncapUrlParameter.UTF_8_CHARSET)));
				paramsUncoded.append(key).append("=").append(entry.getValue())
						.append("&");

				paramsEncoded.append(key).append("=").append(value).append("&");
				returnMap.put(key, value);

			}
		}
		final String substring = new String(paramsUncoded.toString()
				.substring(0, paramsUncoded.toString().length() - 1)
				.getBytes(UTF_8_CHARSET), UTF_8_CHARSET);
		final String paramMD5 = EncryptMD5.toMD5(substring);
		System.err.println(substring);
		System.err.println(substring.length());
		paramsEncoded.append("sign").append("=").append(paramMD5);
		returnMap.put("sign", paramMD5);
		System.err.println(paramMD5);
		return returnMap;
	}

	/**
	 * 
	 * <p>
	 * Description:构造带签名的参数<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年10月15日
	 * @param map
	 * @return
	 * @throws Exception
	 *             String
	 */
	public static Map<String, String> buildSignParameter(Map<String, String> map)
			throws Exception {

		Map<String, String> returnMap = new TreeMap<String, String>();
		StringBuilder signParams = new StringBuilder(map.size() * 16);
		returnMap.put("timestamp", map.get("timestamp"));
		for (Map.Entry<String, String> entry : map.entrySet()) {
			// sign参数放在最后
			if (!"sign".equals(entry.getKey()) && !"timestamp".equals(entry.getKey())) {
				String key = entry.getKey();
				String value = entry.getValue();

				signParams.append(key).append("=").append(value).append("&");
				returnMap.put(key, value);

			}
		}
		final String substring = new String(signParams.toString()
				.substring(0, signParams.toString().length() - 1)
				.getBytes(UTF_8_CHARSET), UTF_8_CHARSET);
		final String paramMD5 = EncryptMD5.toMD5(substring);
		signParams.append("sign").append("=").append(paramMD5);
		returnMap.put("sign", paramMD5);
		return returnMap;
	}

	/**
	 * @throws Exception
	 * 
	 *             <p>
	 *             Description:解密URL请求的参数<br />
	 *             </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月15日
	 * @param map
	 *            void
	 * @throws
	 */
	public static Map<String, String> buildDecryptionParameter(
			Map<String, String> map, String askMode,String timestamp) throws Exception {
		Map<String, String> returnMap = new TreeMap<String, String>();
		StringBuilder paramsUncoded = new StringBuilder(map.size() * 16);
		StringBuilder paramsDecoded = new StringBuilder(map.size() * 16);
		returnMap.put("timestamp", timestamp);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			// sign参数放在最后
			if (!"sign".equals(key) && !"timestamp".equals(key)) {
				String value = null;
				if (TZJConstants.GET_URL.equals(askMode)) { // GET 请求
					value = DES3.decryptModeTzj(new String(((entry.getValue())
							.getBytes(EncapUrlParameter.UTF_8_CHARSET))),timestamp);
				} else if (TZJConstants.POST_URL.equals(askMode)) {
					value = DES3.decryptModeTzj(new String((entry.getValue()
							.getBytes(EncapUrlParameter.UTF_8_CHARSET))),timestamp);
				}

				paramsUncoded.append(key).append("=").append(value).append("&");

				paramsDecoded.append(key).append("=").append(value).append("&");
				returnMap.put(key, value);
			}
		}
		/** 计算MD5值 */
		String src = new String(paramsUncoded.toString()
				.substring(0, paramsUncoded.toString().length() - 1)
				.getBytes(UTF_8_CHARSET), UTF_8_CHARSET);
		String paramDM5 = EncryptMD5.toMD5(src);
		paramsDecoded.append("sign").append("=").append(paramDM5);

		// 校验MD5值是否正确
		if (paramDM5.equals(map.get("sign"))) {
			returnMap.put("sign", paramDM5);

		} else {
			returnMap.put("sign", null);
		}
		return returnMap;
	}

	public static void main(String[] args) {
		try {
			String s = new String("投之家tzj".getBytes(UTF_8_CHARSET),
					UTF_8_CHARSET);

			System.out.println(MD5.toMD5(s));
			System.out.println(s.length());
			try {
				System.out.println(EncryptMD5.toMD5(s));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(s.length());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
