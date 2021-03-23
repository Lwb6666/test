package com.dxjr.portal.tzjinterface.util;

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

public class ParseURLTool {

	/**
	 * 
	 * <p>
	 * Description:解析URL参数<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @param request
	 * @return
	 * Map<String,String>
	 */
	public static Map<String, String> parseURLParameters(HttpServletRequest request) {
		Map<String,String> params  = new TreeMap<String,String>();
		  for (Enumeration<String>  names = request.getParameterNames(); names.hasMoreElements();){
			String name = names.nextElement();
			params.put(name, request.getParameter(name));
		}
		return params;
	}
	
	/**
	 * 
	 * <p>
	 * Description:构造解密后的参数Map<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @param params
	 * @return
	 * Map<String,String>
	 */
	public static Map<String, String> buildDecryptionParameter(
			Map<String, String> params,String askMode,String timestamp) {
		Map<String,String> result  = new TreeMap<String,String>();
		try {
			result = EncapUrlParameter.buildDecryptionParameter(params,askMode,timestamp);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 * <p>
	 * Description:构造加密后的参数Map<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @param params
	 * @return
	 * Map<String,String>
	 */
	public static Map<String, String> buildEncryptionParameter(
			Map<String, String> params,String timestamp) {
		Map<String,String> result  = new TreeMap<String,String>();
		try {
			result = EncapUrlParameter.buildEncryptParameter(params, timestamp);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 * <p>
	 * Description:构造带签名的参数Map<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @param params
	 * @return
	 * Map<String,String>
	 */
	public static Map<String, String> buildSignParameter(
			Map<String, String> params) {
		Map<String,String> result  = new TreeMap<String,String>();
		try {
			result = EncapUrlParameter.buildSignParameter(params);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return result;
	}
}
