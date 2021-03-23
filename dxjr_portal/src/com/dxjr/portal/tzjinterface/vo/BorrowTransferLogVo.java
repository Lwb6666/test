package com.dxjr.portal.tzjinterface.vo;

import java.io.Serializable;

public class BorrowTransferLogVo implements Serializable{
	
	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = -1100321290418068310L;
	/**主键ID*/
	private Integer id;
	/**标Id*/
	private Integer borrowId;
	/**新增时间*/
	private String addTime;
	/**时间戳*/
	private String ts;
	/**删除标识 0：已删除 1：未删除*/
	private String dr;
	/**是否成功标识（0：失败 1：成功）*/
	private Integer isSuccess;
	/**处理结果*/
	private String  message;
	/**
	 * 借款标状态 （0:草稿标 1：新标，审核中，2：投标中，3：满标复审中，4：还款中，5：还款结束，-1：流标，-2：被撤销 ，-3：审核失败，42：已垫付）
	 */
	private Integer  status;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
