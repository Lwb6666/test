package com.dxjr.portal.member.service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Description:会员认证业务类<br />
 * </p>
 * 
 * @title ApproService.java
 * @package com.dxjr.portal.member.service
 * @author justin.xu
 * @version 0.1 2014年4月23日
 */
public interface ApproService {
	/**
	 * <p>
	 * Description:进行邮箱认证<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param userId
	 * @param uuid
	 * @param email
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String updateEmailAppr(Integer userId, String uuid, String email,
			HttpServletRequest request) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:自动审核邮箱<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @param userId
	 * @param request
	 * @throws Exception
	 * void
	 */
	public void autoApprEmail(Integer userId, HttpServletRequest request)
			throws Exception;
}
