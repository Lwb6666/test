package com.dxjr.portal.tzjinterface.entity;

public class QueryReq {

	private TimeRange timeRange;
	
	private Index index;
	
	
	public TimeRange getTimeRange() {
		return timeRange;
	}

	public void setTimeRange(TimeRange timeRange) {
		this.timeRange = timeRange;
	}

	public Index getIndex() {
		return index;
	}
	
	public void setIndex(Index index) {
		this.index = index;
	}
}
