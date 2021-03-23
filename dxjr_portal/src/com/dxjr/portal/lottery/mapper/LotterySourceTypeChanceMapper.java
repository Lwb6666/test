package com.dxjr.portal.lottery.mapper;

import org.apache.ibatis.annotations.Param;

import com.dxjr.portal.lottery.vo.LotterySourceTypeChanceVo;

public interface LotterySourceTypeChanceMapper {
    LotterySourceTypeChanceVo selectByPrimaryKey(Integer id);
    
    /**
     * 
     * <p>
     * Description:查询奖品与抽奖机会来源类型的匹配中奖概率信息<br />
     * </p>
     * @author YangShiJin
     * @version 0.1 2015年4月11日
     * @param lotteryChanceRuleInfoId
     * @param lotteryGoodsId
     * @return
     * @throws Exception
     * LotterySourceTypeChanceVo
     */
    public LotterySourceTypeChanceVo selectByLotteryChanceRuleInfoIdAndLotteryGoodsId(@Param("lotteryChanceRuleInfoId") Integer lotteryChanceRuleInfoId,
    		@Param("lotteryGoodsId") Integer lotteryGoodsId) throws Exception;
}