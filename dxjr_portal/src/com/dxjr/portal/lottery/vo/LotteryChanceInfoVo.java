package com.dxjr.portal.lottery.vo;

import java.io.Serializable;

import com.dxjr.base.entity.LotteryChanceInfo;

/**
 * 
 * <p>
 * Description:抽奖机会信息<br />
 * </p>
 * @title LotteryChanceInfoVo.java
 * @package com.dxjr.portal.lottery.vo 
 * @author YangShiJin
 * @version 0.1 2015年4月7日
 */
public class LotteryChanceInfoVo extends LotteryChanceInfo implements Serializable{

	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = 8367345831292556565L;

	/** 来源信息名称  */
	private String chanceRuleInfoName;

	public String getChanceRuleInfoName() {
		return chanceRuleInfoName;
	}

	public void setChanceRuleInfoName(String chanceRuleInfoName) {
		this.chanceRuleInfoName = chanceRuleInfoName;
	}
}
