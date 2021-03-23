package com.dxjr.portal.transfer.vo;


public class TransferCnd {

	private Integer transferId;// 转让记录id

	private Integer subjectType;// 标的类型

	private String title;// 标的标题

	private Integer type; // 1：可转让，2：转让中，3：转入，4：转出

	private Integer userId;

	private Integer status; // 审核状态

	private int yearDay;

	public TransferCnd() {
		super();
	}

	public TransferCnd(Integer transferId, Integer userId, Integer status) {
		super();
		this.transferId = transferId;
		this.userId = userId;
		this.status = status;
	}



	public Integer getTransferId() {
		return transferId;
	}

	public void setTransferId(Integer transferId) {
		this.transferId = transferId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public int getYearDay() {
		return yearDay;
	}

	public void setYearDay(int yearDay) {
		this.yearDay = yearDay;
	}

}
