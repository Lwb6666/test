package com.dxjr.portal.outerInterface.vo;

import java.io.Serializable;

public class WdtyCnd implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 741051303996774715L;
	
	/**多个普通标和债转标的ID，用逗号进行分割*/
	private String bidStr;
	
	/**多个定期宝的ID，用逗号进行分割*/
	private String fidStr;
	
	/**借款标ID*/
	private String borrowId;
	
	/**借款标类型1普通标、2债转标、3、定期宝*/
	private Integer borrowType;
	
	/**标的状态 1在投,2还款中,3正常还款, 4提前还款, 5下架*/
	private Integer status;	
	
	/**平台交易订单唯一标识*/
	private String orderId;

	public String getBidStr() {
		return bidStr;
	}

	public void setBidStr(String bidStr) {
		this.bidStr = bidStr;
	}

	public String getFidStr() {
		return fidStr;
	}

	public void setFidStr(String fidStr) {
		this.fidStr = fidStr;
	}

	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public Integer getBorrowType() {
		return borrowType;
	}

	public void setBorrowType(Integer borrowType) {
		this.borrowType = borrowType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
	
}
