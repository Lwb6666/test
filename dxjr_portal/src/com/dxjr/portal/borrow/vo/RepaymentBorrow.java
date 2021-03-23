package com.dxjr.portal.borrow.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "repaymentBorrow")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class RepaymentBorrow {

	private String id;
	private String repamentId;
	private String repamentStatus;
	private String repamentWebStatus;
	private String status;
	private String name;
	private int borrowType;
	private String borrowTypeName;
	private Integer userId;
	private String username;
	private BigDecimal account;
	private BigDecimal apr;
	private int style;
	private String styleName;
	private int validTime;
	private Date addTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRepamentStatus() {
		return repamentStatus;
	}

	public void setRepamentStatus(String repamentStatus) {
		this.repamentStatus = repamentStatus;
	}

	public String getRepamentWebStatus() {
		return repamentWebStatus;
	}

	public void setRepamentWebStatus(String repamentWebStatus) {
		this.repamentWebStatus = repamentWebStatus;
	}

	public String getRepamentId() {
		return repamentId;
	}

	public void setRepamentId(String repamentId) {
		this.repamentId = repamentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBorrowType() {
		return borrowType;
	}

	public void setBorrowType(int borrowType) {
		this.borrowType = borrowType;
	}

	public String getBorrowTypeName() {
		return borrowTypeName;
	}

	public void setBorrowTypeName(String borrowTypeName) {
		this.borrowTypeName = borrowTypeName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

	public BigDecimal getApr() {
		return apr;
	}

	public void setApr(BigDecimal apr) {
		this.apr = apr;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public int getValidTime() {
		return validTime;
	}

	public void setValidTime(int validTime) {
		this.validTime = validTime;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "RepaymentBorrow [id=" + id + ", repamentId=" + repamentId
				+ ", repamentStatus=" + repamentStatus + ", repamentWebStatus="
				+ repamentWebStatus + ", status=" + status + ", name=" + name
				+ ", borrowType=" + borrowType + ", borrowTypeName="
				+ borrowTypeName + ", userId=" + userId + ", username="
				+ username + ", account=" + account + ", apr=" + apr
				+ ", style=" + style + ", styleName=" + styleName
				+ ", validTime=" + validTime + ", addTime=" + addTime + "]";
	}

}
