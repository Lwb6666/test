package com.dxjr.base.mapper;

import com.dxjr.base.entity.LotteryGoodsLimit;

public interface BaseLotteryGoodsLimitMapper {
    int insert(LotteryGoodsLimit record);

    int insertSelective(LotteryGoodsLimit record);

    LotteryGoodsLimit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LotteryGoodsLimit record);

    int updateByPrimaryKey(LotteryGoodsLimit record);
}