package com.dxjr.portal.account.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.dxjr.portal.account.vo.AccountInfo;
import com.dxjr.portal.account.vo.RatioMoneyVo;
import com.dxjr.portal.account.vo.UnReceiveInterestCnd;

/**
 * <p>
 * Description:我的帐号统计数据访问类<br />
 * </p>
 * 
 * @title AccountReportMapper.java
 * @package com.dxjr.portal.account.mapper
 * @author justin.xu
 * @version 0.1 2014年5月14日
 */
public interface AccountReportMapper {

	/**
	 * <p>
	 * Description:根据用户id查询用来统计待收罚息总额<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年2月9日
	 * @param memberId
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal queryUnReceiveLateInterestTotal(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:查询某个用户的投标直通车总额<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月16日
	 * @param memberId
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal queryFirstTotalByUserId(Integer memberId) throws Exception;

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
	public BigDecimal queryFirstUseMoneyByUserId(Integer memberId) throws Exception;
	
	
	/**
	 * <p>
	 * Description:查询某个用户转让中直通车可用余额<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年5月7日
	 * @param memberId
	 * @return
	 * @throws Exception
	 * BigDecimal
	 */
	public BigDecimal queryFirstTransferingUseMoney(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:查询某个用户ID投标直通车冻结总额<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月16日
	 * @param memberId
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal queryFirstFreezeAccountByMemberId(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:查询某个用户ID下投标冻结的总计<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月16日
	 * @param memberId
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal queryLockAccountTotalByMemberId(Integer memberId) throws Exception;

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
	public BigDecimal queryLockCashTotalByMemberId(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:查询某个用户ID下已赚利息的总计<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月16日
	 * @param memberId
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal queryYesInterstTotalByMemberId(Integer memberId) throws Exception;
	
	
	/**
	 * 增加定期宝的已赚利息
	 */
	public BigDecimal queryYesInterstDingQiBaoTotalByMemberId(Integer memberId) throws Exception;
	
	/**
	 * <p>
	 * Description:  （普通标+购买债权）- 代收本金  <br />
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
	 * Description:  通投标- 待收罚息-去掉直通车的  <br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年5月14日
	 * @param memberId
	 * @return
	 * @throws Exception
	 * BigDecimal
	 */
	public BigDecimal queryUnReceiveLateInterestList(Integer memberId) throws Exception;
	

	/**
	 * 
	 * <p>
	 * Description:查询某个用户ID下扣除提前还款利息的总计<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月14日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal querySubtractEarlyInterestTotalByMemberId(Integer memberId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某个用户ID下增加提前还款利息的总计<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月14日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal queryAddEarlyInterestTotalByMemberId(Integer memberId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某个用户ID下非VIP阶段网站垫付不用付出的利息的总计<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月14日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal queryNoVipNoInterestTotalByMemberId(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:查询某个用户ID下待收利息的总计<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月16日
	 * @param memberId
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal queryUnReceiveInterstTotalByMemberId(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:非vip收取利息<br />
	 * </p>
	 * 
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年9月9日
	 * @param memberId
	 * @return BigDecimal
	 */
	public BigDecimal queryInterestBackTotalByMemberId(Integer memberId) throws Exception;

	/**
	 * 根据用户Id和类型集合获取金额.
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月3日
	 * @param memberId
	 * @param types 可为多个
	 * @return
	 */
	public BigDecimal queryMoneyByType(@Param("memberId") Integer memberId, @Param("types") String[] types) throws Exception;

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
	public BigDecimal queryReceiveInterestByMemberId(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:查询某个用户ID待付利息费和比率集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月18日
	 * @param memberId
	 * @return List<RatioMoneyVo>
	 */
	public List<RatioMoneyVo> queryToPayInterstListByMemberId(Integer memberId) throws Exception;

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
	public BigDecimal queryPayLateInterestByMemberId(Integer memberId) throws Exception;

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
	public BigDecimal queryUnPayLateInterestByMemberId(Integer memberId) throws Exception;

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
	public BigDecimal queryHavaPayInterestByMemberId(Integer memberId) throws Exception;

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
	public BigDecimal queryWaitPayInterestByMemberId(Integer memberId) throws Exception;

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
	public BigDecimal queryRechargeFeeTotalByMemberId(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:查询某个用户ID下债转冻结的总计<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2014年12月23日
	 * @param memberId
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal queryTransferLockAccountTotalByMemberId(Integer memberId) throws Exception;

	public BigDecimal queryTransferDiffByMemberId(@Param("memberId") Integer memberId);
	
	/**
	 * <p>
	 * Description:根据用户ID查询债权转让转让方的债权金额<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年5月19日
	 * @param memberId
	 * @return
	 * BigDecimal
	 */
	public BigDecimal queryTransferDiffByUserId(@Param("memberId") Integer memberId);
	
	/**
	 * <p>
	 * Description:根据用户ID查询债权转让认购方的债权金额<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年5月19日
	 * @param memberId
	 * @return
	 * BigDecimal
	 */
	public BigDecimal querySubscribeDiffByUserId(@Param("memberId") Integer memberId);
	
	/**
	 * <p>
	 * Description:根据用户ID查询直通车转让转让方的债权金额<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年5月19日
	 * @param memberId
	 * @return
	 * BigDecimal
	 */
	public BigDecimal queryFirstTransferDiffByUserId(@Param("memberId") Integer memberId);
	
	/**
	 * <p>
	 * Description:根据用户ID查询直通车转让认购方的债权金额<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年5月19日
	 * @param memberId
	 * @return
	 * BigDecimal
	 */
	public BigDecimal queryFirstSubscribeDiffByUserId(@Param("memberId") Integer memberId);
	
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
	public BigDecimal queryFirstTransferDiffByMemberId(@Param("memberId") Integer memberId);
	
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
	public BigDecimal queryFirstSubscribeDiffByMemberId(@Param("memberId") Integer memberId);
	
	/**
	 * <p>
	 * Description:根据用户ID查询转让管理费合计<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年4月26日
	 * @param memberId
	 * @return
	 * BigDecimal
	 */
	public BigDecimal querySumManageFeeByMemberId(@Param("memberId") Integer memberId);

	/**
	 * 查询定期宝某用户的本金
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author 陈建国
	 * @version 0.1 2015年5月26日
	 * @param memberId
	 * @return
	 * BigDecimal
	 */
	public BigDecimal queryFixTotalByUserId(@Param("memberId") Integer memberId);
    /**
     * 查询定期宝的预期收益
     * <p>
     * Description:这里写描述<br />
     * </p>
     * @author 陈建国
     * @version 0.1 2015年5月26日
     * @param memberId
     * @return
     * BigDecimal
     */
	public BigDecimal queryFixInterestNoByUserId(@Param("memberId") Integer memberId);
 
	
	
    /**
     * 查询定期宝的已赚收益
     * <p>
     * Description:这里写描述<br />
     * </p>
     * @author 陈建国
     * @version 0.1 2015年5月26日
     * @param memberId
     * @return
     * BigDecimal
     */
	public BigDecimal queryFixInterestYesByUserId(@Param("memberId") Integer memberId);
 
	
	/**
	 * <p>
	 * Description: 按月份 - 查询某个用户的转让债权金额总和 <br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年5月27日
	 * @param unReceiveInterestCnd
	 * @return
	 * BigDecimal
	 */
	public BigDecimal queryTransferDiffByUserIdMonth(UnReceiveInterestCnd unReceiveInterestCnd );
	
	
	/**
	 * <p>
	 * Description:  按月份 -  查询某个用户的转让直通车债权金额总和 <br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年5月27日
	 * @param unReceiveInterestCnd
	 * @return
	 * BigDecimal
	 */
	public BigDecimal queryFirstTransferDiffByUserIdMonth(UnReceiveInterestCnd unReceiveInterestCnd );
	
	
	
	/**
	 * <p>
	 * Description: 按月份 - 查询某个用户的认购债权金额总和  <br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年5月27日
	 * @param unReceiveInterestCnd
	 * @return
	 * BigDecimal
	 */
	public BigDecimal querySubscribeDiffByUserIdMonth(UnReceiveInterestCnd unReceiveInterestCnd );
	
	
	/**
	 * <p>
	 * Description: 按月份 -  查询某个用户的认购直通车债权金额总和 <br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年5月27日
	 * @param unReceiveInterestCnd
	 * @return
	 * BigDecimal
	 */
	public BigDecimal queryFirstSubscribeDiffByUserIdMonth(UnReceiveInterestCnd unReceiveInterestCnd );
	
	public AccountInfo queryRaAccountInfo(UnReceiveInterestCnd unReceiveInterestCnd);
	public AccountInfo queryCurInfo(UnReceiveInterestCnd unReceiveInterestCnd);
	public AccountInfo queryFixInfo(UnReceiveInterestCnd unReceiveInterestCnd);
	public AccountInfo queryBorrowInfo(UnReceiveInterestCnd unReceiveInterestCnd);
	@Select("select IFNULL(SUM(MONEY),0) from  rocky_rechargerecord where USER_ID=#{userId} and `STATUS`=1")
	@ResultType(java.math.BigDecimal.class)
	public BigDecimal queryRechargerecord(@Param("userId") Integer userId) throws Exception;
	
	@Select("select IFNULL(SUM(TOTAL),0) from  rocky_cashrecord where USER_ID=#{userId} and status=2")
	@ResultType(java.math.BigDecimal.class)
	public BigDecimal queryCashrecord(@Param("userId") Integer userId) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:统计认购债权转让支付利息<br />
	 * </p>
	 * @author Shijin.Yang
	 * @version 0.1 2016年8月12日
	 * @param userId
	 * @return
	 * @throws Exception
	 * BigDecimal
	 */
	public BigDecimal querySubsribeTransferPayMoney(Integer userId) throws Exception;
}
