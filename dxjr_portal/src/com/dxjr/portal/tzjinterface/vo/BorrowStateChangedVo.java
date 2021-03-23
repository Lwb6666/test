package com.dxjr.portal.tzjinterface.vo;

import java.io.Serializable;

public class BorrowStateChangedVo implements Serializable {
	private static final long serialVersionUID = 3953534619207060568L;
	private Integer bid;
	/**
	 * （3） status是标的状态，取值有三种： 0：还款中；1：已还清；2：逾期；3：已转让
	 */
	private Integer status;

	public Integer getBid() {
		return bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	public Integer getStatus() {
		// 0：还款中 4；1：已还清；5 42 2：逾期；3：已转让；4：投标中 2 ；5：流标 -1 ；6：撤标 -2 ；7：审核失败 -3；		
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
