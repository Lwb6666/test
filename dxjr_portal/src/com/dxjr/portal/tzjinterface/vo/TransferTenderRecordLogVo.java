package com.dxjr.portal.tzjinterface.vo;

import java.io.Serializable;

/**
 * 
 * @author Administrator
 *  推送国的投标记录流水
 */
public class TransferTenderRecordLogVo implements Serializable{

	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = 7293290417147452543L;
	/**主键ID*/
	private Integer id;
	/**用户ID*/
	private Integer userId;
	/**投标记录ID*/
	private Integer tenderrecordId;
	/**新增时间*/
	private String addTime;
	/**时间戳*/
	private String ts;
	/**删除标识*/
	private String dr;
	/**是否成功标识*/
	private Integer  isSuccess;
	/**处理消息*/
	private String  message;
	/**借款标ID*/
	private Integer borrowId;
	/**借款标状态*/
	private Integer  status;
	/**交易流水好*/
	private String OId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getTenderrecordId() {
		return tenderrecordId;
	}
	public void setTenderrecordId(Integer tenderrecordId) {
		this.tenderrecordId = tenderrecordId;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public String getDr() {
		return dr;
	}
	public void setDr(String dr) {
		this.dr = dr;
	}
	public Integer getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOId() {
		return OId;
	}
	public void setOId(String oId) {
		OId = oId;
	}
	
	
}
