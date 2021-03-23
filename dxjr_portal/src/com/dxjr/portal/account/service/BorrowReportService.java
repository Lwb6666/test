package com.dxjr.portal.account.service;

import java.math.BigDecimal;

/**
 * <p>
 * Description:我的借款统计信息业务类<br />
 * </p>
 * 
 * @title BorrowReportService.java
 * @package com.dxjr.portal.account.service
 * @author justin.xu
 * @version 0.1 2014年5月20日
 */
public interface BorrowReportService {
	/**
	 * <p>
	 * Description:查询某个用户下的借款总额<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月4日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal queryBorrowTotalByMemberId(Integer memberId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某个用户ID下待还本金的总计<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月3日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal queryUnpayCapitalByMemberId(Integer memberId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某个用户ID下已还总额的总计<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月3日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal queryHavePayTotalByMemberId(Integer memberId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某个用户所有的提现手续费<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月3日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal queryCashCostByMemberId(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:统计某个用户下的净值额度, 1、仅限使用在查询的方法中
	 * 2、如果使用在新增或修改的数据中，请直接使用accountNetValueMapper类获得净值额度<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月5日
	 * @return BigDecimal
	 */
	public BigDecimal queryNetMoneyLimit(Integer memberId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某个用户所有的待收本金总计，<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月3日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal queryWaitReceiveCapitalByMemberId(Integer memberId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某个用户ID下待还本息的总计<br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月11日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal queryRepaymentAccountTotalByMemberId(Integer memberId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:累计借款次数<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月18日
	 * @param memberId
	 * @return
	 * @throws Exception int
	 */
	public int queryLoanTimes(Integer memberId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:正在还款<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月18日
	 * @param memberId
	 * @return
	 * @throws Exception int
	 */
	public int queryLoanNowRepaying(Integer memberId) throws Exception;

}
