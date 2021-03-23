package com.dxjr.portal.repayment.vo;

import java.io.Serializable;

import com.dxjr.common.page.BaseCnd;

/**
 * <p>
 * Description:待还记录<br />
 * </p>
 * 
 * @title BRepaymentRecordVo.java
 * @package com.dxjr.portal.repayment.vo
 * @author justin.xu
 * @version 0.1 2014年5月15日
 */
public class RepaymentRecordCnd extends BaseCnd implements Serializable {

	private static final long serialVersionUID = -7120622884570013103L;
	private Integer id;
	private Integer borrowId;
	/** 状态 0---未还 1---已还 */
	private Integer status;
	private Integer webStatus;
	private Integer borrowStatus;

	private String borrowName;

	private String beginTime;

	private String endTime;

	private Integer userId;
	private String orderByOrder;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	public Integer getWebStatus() {
		return webStatus;
	}

	public void setWebStatus(Integer webStatus) {
		this.webStatus = webStatus;
	}

	public Integer getBorrowStatus() {
		return borrowStatus;
	}

	public void setBorrowStatus(Integer borrowStatus) {
		this.borrowStatus = borrowStatus;
	}

	public String getOrderByOrder() {
		return orderByOrder;
	}

	public void setOrderByOrder(String orderByOrder) {
		this.orderByOrder = orderByOrder;
	}
}
