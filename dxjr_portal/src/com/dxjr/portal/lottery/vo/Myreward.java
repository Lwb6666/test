package com.dxjr.portal.lottery.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.dxjr.base.entity.LotteryChanceInfo;

/**
 * 
 * <p>
 * Description:我的奖励信息<br />
 * </p>
 * @title Myreward.java
 * @package com.dxjr.portal.lottery.vo 
 * @author liutao
 * @version 0.1 2016年05月20日
 */
public class Myreward  implements Serializable{
	private static final long serialVersionUID = 8367345831292556565L;
	private Date rewardTime;
	private String rewardType;
	private String rewardSource;
	private String rewardStatus;
	private String rewardopert;
	private BigDecimal rewardMoney;
	private Integer id;
	public BigDecimal getRewardMoney() {
		return rewardMoney;
	}
	public void setRewardMoney(BigDecimal rewardMoney) {
		this.rewardMoney = rewardMoney;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getRewardTime() {
		return rewardTime;
	}
	public void setRewardTime(Date rewardTime) {
		this.rewardTime = rewardTime;
	}
	public String getRewardType() {
		return rewardType;
	}
	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}
	public String getRewardSource() {
		return rewardSource;
	}
	public void setRewardSource(String rewardSource) {
		this.rewardSource = rewardSource;
	}
	public String getRewardStatus() {
		return rewardStatus;
	}
	public void setRewardStatus(String rewardStatus) {
		this.rewardStatus = rewardStatus;
	}
	public String getRewardopert() {
		return rewardopert;
	}
	public void setRewardopert(String rewardopert) {
		this.rewardopert = rewardopert;
	}
	
}
