package com.dxjr.utils;

import java.util.Map;

/**
 * <p>
 * Description:站内信发送帮助类<br />
 * </p>
 * 
 * @title SystemMessageUtil.java
 * @package com.dxjr.util
 * @author justin.xu
 * @version 0.1 2014年4月19日
 */
public class SystemMessageUtil {

	public static String generateParamContent(String systemMessageTempale,
			Map<String, Object> paramMap) {
		String systemMessageContent = systemMessageTempale;
		if (null != paramMap) {
			for (String key : paramMap.keySet()) {
				if (systemMessageContent.indexOf(key) != -1) {
					systemMessageContent = systemMessageContent.replace("${"
							+ key + "}", String.valueOf(paramMap.get(key)));
				}

			}
		}
		return systemMessageContent;
	}
}
