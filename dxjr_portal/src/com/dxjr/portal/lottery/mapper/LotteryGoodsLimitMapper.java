package com.dxjr.portal.lottery.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.dxjr.portal.lottery.vo.LotteryGoodsLimitVo;

public interface LotteryGoodsLimitMapper {

    LotteryGoodsLimitVo selectByPrimaryKey(Integer id);

    /**
     * 
     * <p>
     * Description:根据当前时间及奖品ID查询该奖品的限制记录信息<br />
     * </p>
     * @author YangShiJin
     * @version 0.1 2015年4月11日
     * @param goodsId
     * @param now
     * @return
     * @throws Exception
     * LotteryGoodsLimitVo
     */
    public LotteryGoodsLimitVo selectByGoodsIdAndNow(@Param("goodsId") Integer goodsId, @Param("now") Date now) throws Exception;
}