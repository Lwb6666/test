package com.dxjr.wx.login.service;

import java.math.BigDecimal;

import com.dxjr.portal.member.vo.MemberVo;

/**
 * <p>
 * Description:账号业务类<br />
 * </p>
 * 
 * @title AccountService.java
 * @package com.dxjr.account.service
 * @author justin.xu
 * @version 0.1 2014年4月16日
 */
public interface LoginService {
	/**
	 * 根据operId查询用户名和密码
	 */
	public MemberVo queryMemberByWxId(Integer wId);

	/**
	 * 
	 * <p>
	 * Description:根据userId查询投资总额<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年8月10日
	 * @param userId
	 * @return BigDecimal
	 */
	public BigDecimal queryInvestTotalByUserId(Integer userId);
}
