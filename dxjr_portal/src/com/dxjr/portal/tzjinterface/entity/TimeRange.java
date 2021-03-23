package com.dxjr.portal.tzjinterface.entity;

import java.util.Date;

public class TimeRange {
	// 开始时间
	private Date startTime;
	// 结束时间
	private Date endTime;
	
	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
