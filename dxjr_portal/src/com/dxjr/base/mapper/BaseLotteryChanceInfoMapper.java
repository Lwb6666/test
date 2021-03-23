package com.dxjr.base.mapper;

import com.dxjr.base.entity.LotteryChanceInfo;

/**
 * 
 * <p>
 * Description:抽奖机会信息数据访问类<br />
 * </p>
 * @title BaseLotteryChanceInfoMapper.java
 * @package com.dxjr.base.mapper 
 * @author YangShiJin
 * @version 0.1 2015年4月7日
 */
public interface BaseLotteryChanceInfoMapper {
    int insert(LotteryChanceInfo record);

    int insertSelective(LotteryChanceInfo record);

    LotteryChanceInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LotteryChanceInfo record);

    int updateByPrimaryKey(LotteryChanceInfo record);
}