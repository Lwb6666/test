package com.dxjr.portal.index.service;

/**
 * <p>
 * Description:手机验证业务类<br />
 * </p>
 * 
 * @title MobileApproService.java
 * @package com.dxjr.portal.member.service
 * @author justin.xu
 * @version 0.1 2014年4月25日
 */
public interface IndexService {

	/**
	 * 
	 * <p>
	 * Description:保存反馈手机号<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年9月20日
	 * @param mobileNum
	 * @return
	 * @throws Exception String
	 */
	public String savefeedback(String mobileNum) throws Exception;
}
