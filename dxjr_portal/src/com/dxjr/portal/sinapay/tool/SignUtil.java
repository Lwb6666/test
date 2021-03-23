package com.dxjr.portal.sinapay.tool;

/**
 * 
 * <p>
 * Description:签名工具类<br />
 * </p>
 * 
 * @title SignUtil.java
 * @package com.dxjr.portal.sinapay.tool
 * @author hujianpan
 * @version 0.1 2015年5月25日
 */
public class SignUtil {
	/**
	 * 签名
	 * 
	 * @param content 签名内容
	 * @param signMsg 网关返回签名
	 * @param signType MD5/RSA
	 * @param signKey 密钥
	 * @param charset 字符集
	 * @return
	 * @throws Exception
	 */
	public static boolean Check_sign(String content, String signMsg, String signType, String signKey, String charset) throws Exception {
		if ("MD5".equalsIgnoreCase(signType)) {
			return MD5.verify(content, signMsg, signKey, charset);
		} else if ("RSA".equalsIgnoreCase(signType)) {
			return RSA.verify(content, signMsg, signKey, charset);
		}
		return false;
	}

	public static String sign(String content, String signType, String signKey, String charset) throws Exception {
		if ("MD5".equalsIgnoreCase(signType)) {
			return MD5.sign(content, signKey, charset);
		} else if ("RSA".equalsIgnoreCase(signType)) {
			return RSA.sign(content, signKey, charset);
		}
		return "";
	}

}
