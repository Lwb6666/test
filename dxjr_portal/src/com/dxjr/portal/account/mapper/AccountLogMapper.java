package com.dxjr.portal.account.mapper;

import java.math.BigDecimal;

import com.dxjr.portal.account.vo.AccountLogCnd;

/**
 * <p>
 * Description:资金记录数据访问类<br />
 * </p>
 * 
 * @title AccountLogMapper.java
 * @package com.dxjr.portal.account.mapper
 * @author justin.xu
 * @version 0.1 2014年4月30日
 */
public interface AccountLogMapper {

	/**
	 * <p>
	 * Description:根据条件查询对象集合数量<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param AccountLogCnd
	 *            accountLogCnd
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer queryAccountLogCount(AccountLogCnd accountLogCnd) throws Exception;

	/**
	 * 参与股东加权最低总额
	 * <p>
	 * Description:即历史最大总额的十分之一<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年9月23日
	 * @param userId
	 * @return BigDecimal
	 */
	public BigDecimal haveStockMoney(Integer userId);

	/**
	 * 
	 * <p>
	 * Description: 奖励: 添加 账户记录 <br />
	 * </p>
	 * 
	 * @author HuangJun
	 * @version 0.1 2015年4月13日
	 * @return int
	 */
	public int lott_insert(AccountLogCnd accountLogCnd);

}
