package com.dxjr.portal.outerInterface.service;

/**
 * 
 * <p>
 * Description:网贷之家接口<br />
 * </p>
 * 
 * @title WDZJInterfaceService.java
 * @package com.dxjr.portal.outerInterface.service
 * @author yangshijin
 * @version 0.1 2014年8月21日
 */
public interface WDZJInterfaceService {

	/**
	 * 
	 * <p>
	 * Description:获取当前正在进行投标中的标信息(网贷之家接口)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param ip
	 * @param url
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String getNowProjects(String ip, String url) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取某天内满标的借款标信息(网贷之家接口)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param ip
	 * @param url
	 * @param date
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String getProjectsByDate(String ip, String url, String date)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取今天内满标的借款标信息(网贷之家接口)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param ip
	 * @param url
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String getTodayProjects(String ip, String url) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取某天还款的标的信息.(网贷之家接口)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param ip
	 * @param url
	 * @param date
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String getPaiedLoanInfo(String ip, String url, String date)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:验证用户是否是平台用户(网贷之家接口)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param ip
	 * @param url
	 * @param userName
	 * @param realName
	 * @param cardId
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String checkUserInfo(String ip, String url, String userName,
			String realName, String cardId) throws Exception;

}
