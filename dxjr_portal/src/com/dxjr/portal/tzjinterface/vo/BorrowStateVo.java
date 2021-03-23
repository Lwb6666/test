package com.dxjr.portal.tzjinterface.vo;

import java.io.Serializable;

public class BorrowStateVo implements Serializable{

	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = 3953534619207060568L;
	private Integer bid;
	/**（3）	status是标的状态，取值有三种：
     *                                0：还款中；1：已还清；2：逾期；3：已转让
     */
	private Integer status;
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	
}
