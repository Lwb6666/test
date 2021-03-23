package com.dxjr.portal.account.service;

import java.math.BigDecimal;

import com.dxjr.portal.account.vo.AccountVo;

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
public interface AccountService {
	/**
	 * <p>
	 * Description:初始化帐号，新增,返回 主键<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月16日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             String
	 */
	public Integer insertAccount(Integer userId) throws Exception;

	/**
	 * <p>
	 * Description:根据用户ID查询对象<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param memberId
	 * @return AccountVo
	 */
	public AccountVo queryAccountByUserId(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:根据用户ID查询对象并锁定记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param memberId
	 * @return AccountVo
	 */
	public AccountVo queryAccountByUserIdForUpdate(Integer memberId)
			throws Exception;

	public BigDecimal queryCollectionCapital(Integer memberId) throws Exception;
}
