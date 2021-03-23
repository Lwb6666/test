package com.dxjr.base.mapper;

import com.dxjr.base.entity.LotteryChanceRuleInfo;

/**
 * 
 * <p>
 * Description:抽奖机会来源类型规则信息数据访问类<br />
 * </p>
 * @title BaseLotteryChanceRuleInfoMapper.java
 * @package com.dxjr.base.mapper 
 * @author YangShiJin
 * @version 0.1 2015年4月7日
 */
public interface BaseLotteryChanceRuleInfoMapper {
    int insert(LotteryChanceRuleInfo record);

    int insertSelective(LotteryChanceRuleInfo record);

    LotteryChanceRuleInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LotteryChanceRuleInfo record);

    int updateByPrimaryKey(LotteryChanceRuleInfo record);
}