package com.dxjr.portal.lottery.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Update;

import com.dxjr.portal.lottery.vo.LotteryChanceInfoVo;

/**
 * 
 * <p>
 * Description:抽奖机会信息数据访问类<br />
 * </p>
 * 
 * @title LotteryChanceInfoMapper.java
 * @package com.dxjr.portal.lottery.mapper
 * @author YangShiJin
 * @version 0.1 2015年4月7日
 */
public interface LotteryChanceInfoMapper {

	LotteryChanceInfoVo selectByPrimaryKey(Integer id);

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
	 * @return LotteryChanceInfoVo
	 */
	public Integer selectCountByCodeAndUserId(@Param("code") String code, @Param("userId") Integer userId);

	/**
	 * 
	 * <p>
	 * Description:调用发放首次达到投资等级奖励抽奖机会存储过程 <br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月8日
	 * @param map
	 *            void
	 */
	public void investLevelAwardLotteryChance(Map map);

	/**
	 * 
	 * <p>
	 * Description:调用发放开通直通车奖励抽奖机会存储过程 <br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月8日
	 * @param map
	 *            void
	 */
	public void firstBorrowAwardLotteryChance(Map map);

	/**
	 * 
	 * <p>
	 * Description:查询最早一条未使用完的抽奖机会信息（用于系统抽奖）<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年4月11日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             LotteryChanceInfoVo
	 */
	public LotteryChanceInfoVo selectFirstChanceInfoByUserId(int userId) throws Exception;

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
	 * 
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
	public Integer queryLotteryNumTotalByCodeAndUserId(@Param("code") String code, @Param("userId") Integer userId);
	/**
	 * 
	 * <p>
	 * Description:查询未提醒抽奖次数<br />
	 * </p>
	 * @author liutao
	 * @version 0.1 2016年1月21日
	 */
	public Integer queryLotteryNumTotal(@Param("code") String code, @Param("userId") Integer userId);
	/**
	 * 
	 * <p>
	 * Description:更新提醒状态为未提醒<br />
	 * </p>
	 * @author liutao
	 * @version 0.1 2016年1月21日
	 */
	@Update("update t_lottery_chance_info set IS_REMINDED =1 where LOTTERY_CHANCE_RULE_INFO_ID=11 and (IS_REMINDED=2 or IS_REMINDED is null) and USER_ID = #{userId}")
	@ResultType(Integer.class)
	public void updatelotteryChanceState(@Param("userId") Integer userId);
}