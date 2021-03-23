package com.dxjr.portal.member.vo;

import java.io.Serializable;

public class BankcardTimes implements Serializable {

	private static final long serialVersionUID = 1041042801118767257L;

	private Integer id; // 自增ID
	private Integer userId; // 用户ID
	private Integer changeTimes; // 换卡次数
	private Integer applyTimes; // 申请次数
	private Integer clickTimes; // 点击申请次数

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getChangeTimes() {
		return changeTimes;
	}

	public void setChangeTimes(Integer changeTimes) {
		this.changeTimes = changeTimes;
	}

	public Integer getApplyTimes() {
		return applyTimes;
	}

	public void setApplyTimes(Integer applyTimes) {
		this.applyTimes = applyTimes;
	}

	public Integer getClickTimes() {
		return clickTimes;
	}

	public void setClickTimes(Integer clickTimes) {
		this.clickTimes = clickTimes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
