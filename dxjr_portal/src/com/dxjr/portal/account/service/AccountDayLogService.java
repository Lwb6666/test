package com.dxjr.portal.account.service;

import java.math.BigDecimal;
import java.util.List;

import com.dxjr.portal.account.vo.AccountDayLogVo;

/**
 * <p>
 * Description:账户每日动态操作接口<br />
 * </p>
 * 
 * @title AccountDayLogService.java
 * @package com.dxjr.portal.account.service
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
public interface AccountDayLogService {
	/**
	 * <p>
	 * Description:根据用户id查询日均待收金额<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月13日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryDayAverageCollectionTotal(Integer userId)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询指定用户  账户每日动态待收信息 <br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年12月24日
	 * @param userId
	 * @return
	 * List<AccountDayLogVo>
	 */
	public List<AccountDayLogVo> queryAccountDayLogCollection(Integer memberId);
}
