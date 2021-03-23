package com.dxjr.portal.index.vo;

import java.util.Date;

public class SlideshowCnd {
	private Integer sType;
	private Integer sStart;
	private Integer sEnd;
	private Date nowTime;
	
	public Integer getsType() {
		return sType;
	}
	public void setsType(Integer sType) {
		this.sType = sType;
	}
	public Integer getsStart() {
		return sStart;
	}
	public void setsStart(Integer sStart) {
		this.sStart = sStart;
	}
	public Integer getsEnd() {
		return sEnd;
	}
	public void setsEnd(Integer sEnd) {
		this.sEnd = sEnd;
	}
	public Date getNowTime() {
		return nowTime;
	}
	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}
	
	
}
