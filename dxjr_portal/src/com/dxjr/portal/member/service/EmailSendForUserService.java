package com.dxjr.portal.member.service;

import com.dxjr.base.email.vo.EmailSendVo;

/**
 * <p>
 * Description:邮件发送Service<br />
 * </p>
 * 
 * @title EmailSendForUserService.java
 * @package com.dxjr.base.email.service
 * @author justin.xu
 * @version 0.1 2014年4月17日
 */
public interface EmailSendForUserService {
	/**
	 * <p>
	 * Description:发送邮件 <br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月17日
	 * @param emailSendVo
	 * @throws Exception
	 *             void
	 */
	public void send(EmailSendVo emailSendVo) throws Exception;
	
	public void sendEmail(String content,String email) throws Exception;
}
