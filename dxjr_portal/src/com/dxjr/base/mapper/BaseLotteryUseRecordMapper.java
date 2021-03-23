package com.dxjr.base.mapper;

import com.dxjr.base.entity.LotteryUseRecord;

public interface BaseLotteryUseRecordMapper {
    int insert(LotteryUseRecord record);

    int insertSelective(LotteryUseRecord record);

    LotteryUseRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LotteryUseRecord record);

    int updateByPrimaryKey(LotteryUseRecord record);
}