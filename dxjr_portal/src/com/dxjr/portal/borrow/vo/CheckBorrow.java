package com.dxjr.portal.borrow.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="checkBorrow")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class CheckBorrow {
	
	private String id;
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
	
	@Override
	public String toString() {
		return "CheckBorrow [name=" + name + ", borrowType=" + borrowType
				+ ", borrowTypeName=" + borrowTypeName + ", userId=" + userId
				+ ", username=" + username + ", account=" + account + ", apr="
				+ apr + ", style=" + style + ", styleName=" + styleName
				+ ", validTime=" + validTime + ", addTime=" + addTime + "]";
	}
	
	
}
