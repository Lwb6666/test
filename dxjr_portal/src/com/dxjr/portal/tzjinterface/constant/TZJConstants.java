package com.dxjr.portal.tzjinterface.constant;

import com.dxjr.utils.PropertiesUtil;


public interface TZJConstants {
	// 请求方式
	public static final String GET_URL = new String("GET_URL");
	public static final String POST_URL = new String("POST_URL");
	// 无记录则返回NONE
	public static final String NONE = new String("NONE");
	
	/**
	 * 投之家请求失效时间
	 */
	public static final int expireTime=300;
	
	public static final String aesKey=PropertiesUtil.getValue("tzj_aesKey");

	public static final String token=PropertiesUtil.getValue("tzj_token");

	public static final String appId=PropertiesUtil.getValue("tzj_appId");
	
	public static final String callback=PropertiesUtil.getValue("tzj_callback");
	
	public static final String WX_SSO_URL=PropertiesUtil.getValue("tzj_wx_ssologin");
	
	public static final String WX_OLDUSER_URL=PropertiesUtil.getValue("tzj_wx_forTzjLoginNew");
}
