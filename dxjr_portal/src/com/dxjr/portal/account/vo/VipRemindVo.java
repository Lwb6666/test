package com.dxjr.portal.account.vo;

import java.io.Serializable;

public class VipRemindVo implements Serializable{

	private static final long serialVersionUID = -145170582010367476L;
	private Integer remindNum;//提醒间隔类型  | 1，每天1次；2，只一次；
	private Long remindTime; //首次提醒日期
	
	public Integer getRemindNum() {
		return remindNum;
	}
	public void setRemindNum(Integer remindNum) {
		this.remindNum = remindNum;
	}
	public Long getRemindTime() {
		return remindTime;
	}
	public void setRemindTime(Long remindTime) {
		this.remindTime = remindTime;
	}
}
