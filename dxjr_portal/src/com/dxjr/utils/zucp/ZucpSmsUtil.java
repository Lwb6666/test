package com.dxjr.utils.zucp;

/**
 * <p>
 * Description:漫道发送短信工具类<br />
 * </p>
 * 
 * @title ZucpSmsUtil.java
 * @package com.dxjrweb.sms.util.zucp
 * @author justin.xu
 * @version 0.1 2014年1月17日
 */
public class ZucpSmsUtil {

	private static String sn = "";//　
	private static String pwd = "";

	private static Client client;

	private static Client getClient() throws Exception {
		if (client == null) {
			client = new Client(sn, pwd);
		}
		return client;
	}

	/**
	 * <p>
	 * Description:发送单条短信<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年1月17日
	 * @param content
	 * @param mobile
	 *            手机号码
	 * @throws Exception
	 *             void
	 */
	public static String sendSms(String content, String mobile) throws Exception {

//		Client client = ZucpSmsUtil.getClient();
//		// ext为空是顶玺金融 ext=1是发营销 
//		String result_mt = client.mdSmsSend_u(mobile, content, "", "", "");
//		return result_mt;
		return null;
	}

}
