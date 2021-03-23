package com.dxjr.portal.sinapay.tool;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Description:参数处理工具类<br />
 * </p>
 * 
 * @title Tools.java
 * @package com.dxjr.portal.sinapay.tool
 * @author hujianpan
 * @version 0.1 2015年5月25日
 */
public class Tools {
	// 签名版本名称
	public static final String SIGN_VERSION_NAME = "sign_version";

	// 签名类型名称
	public static final String SIGN_TYPE_NAME = "sign_type";
	// 签名名称
	public static final String SIGN_NAME = "sign";

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params 需要排序并参与字符拼接的参数组
	 * @param encode 是否需要urlEncode
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params, boolean encode) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		String charset = params.get("_input_charset");
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (encode) {
				try {
					value = URLEncoder.encode(URLEncoder.encode(value, charset), charset);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}

	/**
	 * request 转map
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getParameterMap(HttpServletRequest request, boolean isFilter) {
		// 参数Map
		Map properties = request.getParameterMap();
		// 返回值Map
		Map<String, String> returnMap = new HashMap<String, String>();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			if (isFilter) {
				if (!name.equals("sign") && !name.equals("sign_type") && !name.equals("sign_version")) {
					Object valueObj = entry.getValue();
					if (null == valueObj) {
						value = "";
					} else if (valueObj instanceof String[]) {
						String[] values = (String[]) valueObj;
						for (int i = 0; i < values.length; i++) {
							value = values[i] + ",";
						}
						value = value.substring(0, value.length() - 1);
					} else {
						value = valueObj.toString();
					}
					returnMap.put(name, value);
				}
			} else {
				Object valueObj = entry.getValue();
				if (null == valueObj) {
					value = "";
				} else if (valueObj instanceof String[]) {
					String[] values = (String[]) valueObj;
					for (int i = 0; i < values.length; i++) {
						value = values[i] + ",";
					}
					value = value.substring(0, value.length() - 1);
				} else {
					value = valueObj.toString();
				}
				returnMap.put(name, value);
			}
		}
		return returnMap;
	}

}
