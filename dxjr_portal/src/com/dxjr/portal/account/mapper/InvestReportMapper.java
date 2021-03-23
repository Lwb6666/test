package com.dxjr.portal.account.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Description:我的投资统计数据访问类<br />
 * </p>
 * 
 * @title InvestReportMapper.java
 * @package com.dxjr.portal.account.mapper
 * @author justin.xu
 * @version 0.1 2014年5月20日
 */
public interface InvestReportMapper {
	/**
	 * <p>
	 * Description:查询某个用户ID下投标金额的总计(只统计满标后的投标金额)<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月20日
	 * @param memberId
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryTenderTotalByMemberId(Integer memberId)
			throws Exception;

	/**
	 * <p>
	 * Description:根据用户id查询待收排名<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月20日
	 * @param userId
	 * @return Integer
	 */
	public Integer queryUncollectedRankingByUserId(Integer memberId)
			throws Exception;

	/**
	 * <p>
	 * Description:查询某个用户ID充值的金额总计<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月20日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal queryRechargeTotalByMemberId(Integer memberId)
			throws Exception;

	/**
	 * <p>
	 * Description:查询某个用户所有的提现总额<<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月20日
	 * @param memberId
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryCashTotalByMemberId(Integer memberId)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:投标次数<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月18日
	 * @param memberId
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int queryInvestCount(@Param("memberId") Integer memberId,
			@Param("status") int status[],@Param("borrowStatus") int borrowStatus[]) throws Exception;

}
