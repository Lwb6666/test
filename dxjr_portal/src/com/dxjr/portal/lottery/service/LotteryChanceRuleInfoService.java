package com.dxjr.portal.lottery.service;

import com.dxjr.portal.lottery.vo.LotteryChanceRuleInfoVo;

/**
 * 
 * <p>
 * Description:抽奖机会来源类型规则信息业务逻辑处理接口<br />
 * </p>
 * @title LotteryChanceRuleInfoService.java
 * @package com.dxjr.portal.lottery.service 
 * @author YangShiJin
 * @version 0.1 2015年4月7日
 */
public interface LotteryChanceRuleInfoService {

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
    public LotteryChanceRuleInfoVo selectByCode(String code) throws Exception;
}
