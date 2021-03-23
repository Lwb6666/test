package com.dxjr.portal.member.service;

import javax.servlet.http.HttpServletRequest;

import com.dxjr.portal.member.vo.VIPApproVo;

/**
 * <p>
 * Description:VIP验证业务类<br />
 * </p>
 * 
 * @title VIPApproService.java
 * @package com.dxjr.portal.member.service
 * @author justin.xu
 * @version 0.1 2014年4月25日
 */
public interface VIPApproService {

	/**
	 * <p>
	 * Description:根据用户ID查询对象<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param memberId
	 * @return VIPApproVo
	 */
	public VIPApproVo queryVIPApproByUserId(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:保存VIP认证,包括新增和修改<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月25日
	 * @param vipApply
	 * @param payPassword
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String saveVIPAppro(String payPassword, Integer userId,
			HttpServletRequest request) throws Exception;
}
