package com.dxjr.portal.lottery.mapper;

import com.dxjr.portal.lottery.vo.LotteryChanceRuleInfoVo;

/**
 * 
 * <p>
 * Description:抽奖机会来源类型规则信息数据访问类<br />
 * </p>
 * @title LotteryChanceRuleInfoMapper.java
 * @package com.dxjr.portal.lottery.mapper 
 * @author YangShiJin
 * @version 0.1 2015年4月7日
 */
public interface LotteryChanceRuleInfoMapper {

    LotteryChanceRuleInfoVo selectByPrimaryKey(Integer id);

    /**
     * 
     * <p>
     * Description:根据Code查询符合当前时间的生效记录<br />
     * </p>
     * @author YangShiJin
     * @version 0.1 2015年4月7日
     * @param code
     * @return
     * LotteryChanceRuleInfoVo
     */
    public LotteryChanceRuleInfoVo selectByCode(String code);
}