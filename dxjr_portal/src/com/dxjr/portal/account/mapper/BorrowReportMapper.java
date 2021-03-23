package com.dxjr.portal.account.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Description:我的借款统计数据访问类<br />
 * </p>
 * 
 * @title BorrowReportMapper.java
 * @package com.dxjr.portal.account.mapper
 * @author justin.xu
 * @version 0.1 2014年5月20日
 */
public interface BorrowReportMapper {
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
	 * Description:查询某个用户ID下单纯 的已还总额的总计，（未去除提前还款）<br />
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
	 * Description:根据借款人获取净值标未还款的记录数<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月20日
	 * @param userId
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryUnPayBorrowCountByUserId(Integer memberId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某个用户所有的待收本金总计<br />
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
	 * Description:借款次数<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月18日
	 * @param memberId
	 * @return
	 * @throws Exception int
	 */
	public int queryLoanCount(@Param("memberId") Integer memberId, @Param("status") Integer status) throws Exception;

}
