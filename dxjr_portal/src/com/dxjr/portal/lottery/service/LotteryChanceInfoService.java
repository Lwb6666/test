package com.dxjr.portal.lottery.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.dxjr.portal.lottery.vo.LotteryChanceInfoVo;
import com.dxjr.portal.lottery.vo.LotteryDraw;

/**
 * 
 * <p>
 * Description:抽奖机会信息业务逻辑处理接口<br />
 * </p>
 * 
 * @title LotteryChanceInfoService.java
 * @package com.dxjr.portal.lottery.service
 * @author YangShiJin
 * @version 0.1 2015年4月7日
 */
public interface LotteryChanceInfoService {

	/**
	 * 
	 * <p>
	 * Description:根据机会来源类型和userId查询记录数量<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月7日
	 * @param code
	 * @param userId
	 * @return Integer
	 */
	public Integer selectCountByCodeAndUserId(String code, Integer userId);

	/**
	 * 
	 * <p>
	 * Description:新增抽奖机会信息记录<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月8日
	 * @param userId
	 * @param code
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String insertLotteryChanceInfoByCode(Integer userId, String code) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:新增抽奖机会信息记录<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月8日
	 * @param userId
	 * @param code
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String insertLotteryChanceInfoByCode(Integer userId, String code, Integer lotteryNum) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:发放首次达到投资等级奖励抽奖机会（首次投资奖和理财里程碑奖）<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月9日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String investLevelAwardLotteryChance(Integer userId, BigDecimal account) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:发放开通直通车奖励抽奖机会<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月9日
	 * @param userId
	 * @param account
	 * @param firstTenderRealId
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String firstBorrowAwardLotteryChance(Integer userId, Integer account, Integer firstTenderRealId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:开始抽奖<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月11日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             LotteryDraw
	 */
	public LotteryDraw lotteryDraw(Integer userId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询抽奖机会有效次数总条数<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月22日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer selectChanceUseNumTotalByUserId(int userId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据时间查询大于该时间的抽奖机会信息<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月22日
	 * @param date
	 * @return
	 * @throws Exception
	 *             List<LotteryChanceInfoVo>
	 */
	public List<LotteryChanceInfoVo> selectNewChanceInfoByDate(Date date) throws Exception;

	/**
	 * <p>
	 * Description:根据userId查询投资总金额 <br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月12日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryInvestTotalByUserId(int userId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据机会来源类型和userId查询送的抽奖总次数<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月25日
	 * @param code
	 * @param userId
	 * @return Integer
	 */
	public Integer queryLotteryNumTotalByCodeAndUserId(String code, Integer userId);
	/**
	 * 
	 * <p>
	 * Description:查询未提醒抽奖次数<br />
	 * </p>
	 * @author liutao
	 * @version 0.1 2016年1月21日
	 */
	public Integer queryLotteryNumTotal(String code, Integer userId);
	/**
	 * 
	 * <p>
	 * Description:更新提醒状态为未提醒<br />
	 * </p>
	 * @author liutao
	 * @version 0.1 2016年1月21日
	 */
	public void updatelotteryChanceState(Integer userId);
}
