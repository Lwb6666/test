package com.dxjr.portal.account.service;

import java.math.BigDecimal;

/**
 * <p>
 * Description:我的投资统计信息业务类<br />
 * </p>
 * 
 * @title InvestReportService.java
 * @package com.dxjr.portal.account.service
 * @author justin.xu
 * @version 0.1 2014年5月20日
 */
public interface InvestReportService {

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
	 * Description:累计投标次数<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月18日
	 * @param memberId
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int queryInvestTimes(Integer memberId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:正在进行的投标数<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月18日
	 * @param memberId
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int queryInvestCountNowRunning(Integer memberId) throws Exception;
}
