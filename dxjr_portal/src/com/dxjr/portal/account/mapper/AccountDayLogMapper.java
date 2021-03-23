package com.dxjr.portal.account.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.dxjr.portal.account.vo.AccountDayLogCnd;
import com.dxjr.portal.account.vo.AccountDayLogVo;

/**
 * <p>
 * Description:账户每日动态数据访问类<br />
 * </p>
 * 
 * @title AccountDayLogMapper.java
 * @package com.dxjr.portal.account.mapper
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
public interface AccountDayLogMapper {
	/**
	 * <p>
	 * Description:根据条件查询待收总额<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月13日
	 * @param accountDayLogCnd
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryAccountDayLogCollectionTotal(
			AccountDayLogCnd accountDayLogCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询指定用户加权待收金额明细<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年12月24日
	 * @return
	 * List<AccountDayLogVo>
	 */
	public List<AccountDayLogVo> queryAccountDayLogCollection(Integer memberId);
}
