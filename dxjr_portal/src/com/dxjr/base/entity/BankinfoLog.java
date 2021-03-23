package com.dxjr.base.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Description:银行卡信息日志表<br />
 * </p>
 * 
 * @title BankinfoLog.java
 * @package com.dxjr.base.entity
 * @author chenpeng
 * @version 0.1 2014年11月19日
 */
public class BankinfoLog  implements Serializable {
	private static final long serialVersionUID = -7600234119551306240L;
	
	private Integer id;
	/** 用户ID */
	private Integer userId;
	/** 銀行帳戶號 */
	private String cardNum;
	/** 操作类型 */
	private Integer type;
	/** 状态 */
	private Integer status;
	/** 添加人 */
	private Integer addBy;
	/** 添加时间 */
	private Date addTime;
	/** 备注 */
	private String remark;
 /** 银行四要素验证是否成功*/
   private String bankVerif;
	public String getBankVerif() {
		return bankVerif;
	}

	public void setBankVerif(String bankVerif) {
		this.bankVerif = bankVerif;
	}
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
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getAddBy() {
		return addBy;
	}
	public void setAddBy(Integer addBy) {
		this.addBy = addBy;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
