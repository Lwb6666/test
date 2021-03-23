package com.dxjr.base.mapper;

import com.dxjr.base.entity.LotteryGoods;

public interface BaseLotteryGoodsMapper {
    int insert(LotteryGoods record);

    int insertSelective(LotteryGoods record);

    LotteryGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LotteryGoods record);

    int updateByPrimaryKey(LotteryGoods record);
}