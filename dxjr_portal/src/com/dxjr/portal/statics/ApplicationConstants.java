package com.dxjr.portal.statics;

/**
 * <p>
 * Description:应用常量、区别生产和测试<br />
 * </p>
 * 
 * @title ApplicationConstants.java
 * @package com.dxjr.portal.statics
 * @author justin.xu
 * @version 0.1 2014年11月12日
 */
public interface ApplicationConstants {

	/*************** 投之家服务地址 begin *****************************************************************/
	/*
	 * 正式环境http://www.touzhijia.com/pingtaiservice
	 * 测试环境http://test.touzhijia.com:8005/pingtaiservice
	 */
	public static final String TZJ_PINGTAI_SERVICE_POSTDATE_URL = new String("http://test.touzhijia.com:8005/pingtaiservice");
	/*
	 * 正式环境tzjgcjr7t91mlqvx78omebau 
	 * 测试环境123456788765432112345678 
	 */
	public static final String TZJ_KEY = new String("123456788765432112345678");
	/*
	 * 正式环境http://www.touzhijia.com/platform/message/callback
	 * 测试环境http://test.touzhijia.com:8005/message/callback
	 */
	public static final String TZJ_BINGUSER_SERVICE_POSTDATE_URL = new String("http://test.touzhijia.com:8005/message/callback");
	/*************** 投之家服务地址 end *****************************************************************/

}
