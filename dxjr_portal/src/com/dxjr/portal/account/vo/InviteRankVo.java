package com.dxjr.portal.account.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.dxjr.utils.StrinUtils;

public class InviteRankVo implements Serializable {
	public static final InviteRankVo EMPTY = new InviteRankVo();

	private Integer userId;
	private String userName;
	private Integer num;
	private BigDecimal interest;
	private Integer rank;
	private Integer month;
	private Date rewardIssuedTime; // 奖励发放时间
	private String userNameSecret;  //隐藏用户名
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public BigDecimal getInterest() {
		return interest;
	}
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Date getRewardIssuedTime() {
		return rewardIssuedTime;
	}
	public void setRewardIssuedTime(Date rewardIssuedTime) {
		this.rewardIssuedTime = rewardIssuedTime;
	}
	public String getUserNameSecret() {
		userNameSecret = this.getUserName();
		if(userNameSecret!=null && userNameSecret.length()>2){
			userNameSecret = StrinUtils.stringSecretTo(userNameSecret);
		}
		return userNameSecret;
	}
}
