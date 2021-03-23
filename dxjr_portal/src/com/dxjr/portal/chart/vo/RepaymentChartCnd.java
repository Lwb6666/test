package com.dxjr.portal.chart.vo;

import java.io.Serializable;

import com.dxjr.common.page.BaseCnd;



public class RepaymentChartCnd extends BaseCnd implements Serializable{

	private static final long serialVersionUID = 2001557620098460482L;

	/** 开始时间 */
	private String beignTime;
	
	/** 截止时间 */
	private String endTime;

	public String getBeignTime() {
		return beignTime;
	}

	public void setBeignTime(String beignTime) {
		this.beignTime = beignTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
