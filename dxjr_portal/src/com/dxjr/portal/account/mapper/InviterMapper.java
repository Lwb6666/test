package com.dxjr.portal.account.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.FriendCnd;
import com.dxjr.portal.account.vo.InviteDetailVo;
import com.dxjr.portal.account.vo.InviteRankVo;

/**
 * <p>
 * Description:推广邀请态数据访问类<br />
 * </p>
 * 
 * @title InviterMapper.java
 * @package com.dxjr.portal.account.mapper
 * @author yangshijin
 * @version 0.1 2014年6月6日
 */
public interface InviterMapper {
	/**
	 * <p>
	 * Description:调用首充1000元奖励10元的存储过程<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月6日
	 * @param map
	 *            void
	 */
	public void awardInviter(Map<?, ?> map);

	public int countInviteDetailsByUserId(Integer userId);

	public List<InviteDetailVo> queryInviteDetailsByUserId(@Param("userId") Integer userId, Page page);

	public int countInviteNumRankList(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	public List<InviteRankVo> queryInviteNumRankList(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	public int countInviteInterestRankList(@Param("month") Integer month, @Param("interestType") String interestType);

	// interestType:MONTH_INTEREST,TOTAL_INTEREST
	public List<InviteRankVo> queryInviteInterestRankList(@Param("month") Integer month, @Param("interestType") String interestType);

	public int countInviteNumDetailList(@Param("userId") Integer userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	public List<InviteDetailVo> queryInviteNumDetailList(@Param("userId") Integer userId, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate, Page page);

	public List<InviteRankVo> queryInviteInterestDetailList(@Param("userId") Integer userId);

	public BigDecimal queryInviteIssuedReward(@Param("userId") Integer userId, @Param("type") Integer type, @Param("month") Integer month);

	/**
	 * 
	 * <p>
	 * Description:根据userId查询投资总额<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年8月10日
	 * @param userId
	 * @return BigDecimal
	 */
	public BigDecimal queryInvestTotalByUserId(Integer userId);
	
	
	
	public int countInviteDetailsByUserIdNew(FriendCnd friendCnd);

	public List<InviteDetailVo> queryInviteDetailsByUserIdNew(FriendCnd friendCnd, Page page);
	
	@Select("select IFNULL(SUM(AWARDMONEY),0) from rocky_member m where m.INVITERID =#{userId}")
	@ResultType(java.math.BigDecimal.class)
	public BigDecimal queryRewardTotal(@Param("userId") Integer userId);
	@Select("select IFNULL(SUM(ACTUAL_MONEY),0) from  t_invite_reward_issue where USERID=#{userId} and type=#{type}")
	@ResultType(java.math.BigDecimal.class)
	public BigDecimal queryReward(@Param("userId") Integer userId,@Param("type") Integer type);
}