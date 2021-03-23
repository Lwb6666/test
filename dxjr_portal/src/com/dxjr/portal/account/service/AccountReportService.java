package com.dxjr.portal.account.service;

import java.math.BigDecimal;

import com.dxjr.portal.account.vo.UnReceiveInterestCnd;

/**
 * <p>
 * Description:帐号统计信息业务类<br />
 * </p>
 * 
 * @title AccountReportService.java
 * @package com.dxjr.portal.account.service
 * @author justin.xu
 * @version 0.1 2014年5月14日
 */
public interface AccountReportService {

	
	/**
	 * <p>
	 * Description: 普通投标- 待收罚息-去掉直通车的     <br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年5月14日
	 * @param memberId
	 * @return
	 * @throws Exception
	 * BigDecimal
	 */
	public BigDecimal queryUnReceiveLateInterestList(Integer memberId)
			throws Exception;

	/**
	 * <p>
	 * Description:查询某个用户的投标直通车总额<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月16日
	 * @param memberId
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryFirstTotalByUserId(Integer memberId)
			throws Exception;
	
	
/**
 * 计算定期宝下的本金统计
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @author 陈建国
 * @version 0.1 2015年5月26日
 * @param memberId
 * @return
 * @throws Exception
 * BigDecimal
 */
	public BigDecimal queryFixTotalByUserId(Integer memberId)
			throws Exception;

	/**
	 * <p>
	 * Description:查询某个用户的投标直通车可用余额<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月16日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal queryFirstUseMoneyByUserId(Integer memberId)
			throws Exception;
	
	/**
	 * <p>
	 * Description:查询某个用户的转让中直通车可用余额<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年5月7日
	 * @param memberId
	 * @return
	 * @throws Exception
	 * BigDecimal
	 */
	public BigDecimal queryFirstTransferingUseMoney(Integer memberId)
			throws Exception;

	/**
	 * <p>
	 * Description:查询某个用户ID投标直通车冻结总额<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月16日
	 * @param memberId
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryFirstFreezeAccountByMemberId(Integer memberId)
			throws Exception;

	/**
	 * <p>
	 * Description:查询某个用户ID下投标冻结的总计<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月16日
	 * @param memberId
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryLockAccountTotalByMemberId(Integer memberId)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某个用户所有的提现冻结<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月3日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal queryLockCashTotalByMemberId(Integer memberId)
			throws Exception;

	/**
	 * <p>
	 * Description:查询某个用户ID下已赚利息的总计<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月16日
	 * @param memberId
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryYesInterstTotalByMemberId(Integer memberId)
			throws Exception;

	/**
	 * <p>
	 * Description:查询某个用户ID下待收利息的总计<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月16日
	 * @param memberId
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryUnReceiveInterstTotalByMemberId(Integer memberId)
			throws Exception;
	
	/**
	 * <p>
	 * Description:查询某个用户ID下非vip收取利息总计<br />
	 * </p>
	 * 
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年9月9日
	 * @param memberId
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal queryInterestBackTotalByMemberId(Integer memberId) throws Exception;

	/**
	 * 根据用户Id和类型集合获取金额.
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月3日
	 * @param memberId
	 * @param types
	 *            可为多个
	 * @return
	 */
	public BigDecimal queryMoneyByType(Integer memberId, String[] types)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某个用户所有的已收罚息总计<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月3日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal queryReceiveInterestByMemberId(Integer memberId)
			throws Exception;

	/**
	 * <p>
	 * Description:查询某个用户ID待付利息管理费的总计<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月18日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal queryUnPayManageInterstByMemberId(Integer memberId)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某个用户ID下已付罚息的总计<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月3日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal queryPayLateInterestByMemberId(Integer memberId)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某个用户ID下待付罚息的总计<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月3日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal queryUnPayLateInterestByMemberId(Integer memberId)
			throws Exception;

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
	public BigDecimal queryCashCostByMemberId(Integer memberId)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某个用户ID下单纯的已付利息的总计(未去除提前还款)<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月3日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal queryHavaPayInterestByMemberId(Integer memberId)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某个用户ID下待还利息的总计<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月3日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal queryWaitPayInterestByMemberId(Integer memberId)
			throws Exception;
	/**
	 * 
	 * <p>
	 * Description:查询某个用户ID充值的金额手续费总计<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月3日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal queryRechargeFeeTotalByMemberId(Integer memberId)
			throws Exception;

	/**
	 * <p>
	 * Description:查询某个用户ID下债转冻结的总计<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2014年12月23日
	 * @param memberId
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryTransferLockAccountTotalByMemberId(Integer memberId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:计算转让 和 认购的 盈亏 总和<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年12月30日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal queryTransferDiffByMemberId(Integer memberId);
	
	/**
	 * <p>
	 * Description:债权认购支出总额<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年5月25日
	 * @param memberId
	 * @return
	 * BigDecimal
	 */
	public BigDecimal querySubscribeTransferDiffByMemberId(Integer memberId);
	
	
	/**
	 * <p>
	 * Description: （普通标+购买债权）- 代收本金 <br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年5月13日
	 * @param memberId
	 * @return
	 * @throws Exception
	 * BigDecimal
	 */
	public BigDecimal queryDsbjByMemberId(Integer memberId) throws Exception;
	
	
	/**
	 * <p>
	 * Description:转让方债权金额合计<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年4月26日
	 * @param memberId
	 * @return
	 * BigDecimal
	 */
	public BigDecimal queryFirstTransferDiffByMemberId(Integer memberId);
	
	/**
	 * <p>
	 * Description:认购方债权金额合计<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年4月26日
	 * @param memberId
	 * @return
	 * BigDecimal
	 */
	public BigDecimal queryFirstSubscribeDiffByMemberId(Integer memberId);
	
	/**
	 * <p>
	 * Description:根据用户ID查询直通车转让管理费合计<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年4月26日
	 * @param memberId
	 * @return
	 * BigDecimal
	 */
	public BigDecimal querySumManageFeeByMemberId(Integer memberId);

	
	/**
	 * 定期宝预期收益
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author 陈建国
	 * @version 0.1 2015年6月4日
	 * @param memberId
	 * @return
	 * @throws Exception
	 * BigDecimal
	 */
	public BigDecimal queryFixInterestNoByUserId(Integer memberId) throws Exception;
	
	
	public BigDecimal queryFixInterestYesByUserId(Integer memberId) throws Exception;
	
	/**
	 * <p>
	 * Description: 按月份 :  债权转让+直通车转让  统计债权金额的和  <br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年5月27日
	 * @param memberId
	 * @return
	 * BigDecimal
	 */
	public BigDecimal queryTransferDiffByMonth(UnReceiveInterestCnd unReceiveInterestCnd  ) throws Exception;
	
	
	
	/**
	 * <p>
	 * Description: 按月份 ： 债权转让认购 +  直通车转让认购 <br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年5月27日
	 * @param memberId
	 * @return
	 * @throws Exception
	 * BigDecimal
	 */
	public BigDecimal querySubscribeTransferDiffByMemberIdMonth(UnReceiveInterestCnd unReceiveInterestCnd) throws Exception;
	
	
	
}
