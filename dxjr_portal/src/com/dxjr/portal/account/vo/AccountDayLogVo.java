package com.dxjr.portal.account.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.dxjr.utils.DateUtils;

public class AccountDayLogVo implements Serializable{

	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = -8785571630838683833L;
	
	private Integer id;
	private Integer userId;
	private String userName;
	private BigDecimal total;
	private BigDecimal useMoney;
	private BigDecimal noUserMoney;
	private BigDecimal collection;
	private BigDecimal netValue;
	private Date addTime;
	private String addTimeStr;
	private BigDecimal firstBorrowUserMoney;
	private BigDecimal drawMoney;
	private BigDecimal noDrawMoney;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getUseMoney() {
		return useMoney;
	}
	public void setUseMoney(BigDecimal useMoney) {
		this.useMoney = useMoney;
	}
	public BigDecimal getNoUserMoney() {
		return noUserMoney;
	}
	public void setNoUserMoney(BigDecimal noUserMoney) {
		this.noUserMoney = noUserMoney;
	}
	public BigDecimal getCollection() {
		return collection;
	}
	public void setCollection(BigDecimal collection) {
		this.collection = collection;
	}
	public BigDecimal getNetValue() {
		return netValue;
	}
	public void setNetValue(BigDecimal netValue) {
		this.netValue = netValue;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getAddTimeStr() {
		if(addTime != null){
			return DateUtils.format(addTime,DateUtils.YMD_HMS);
		}
		
		return "";
	}
	public BigDecimal getFirstBorrowUserMoney() {
		return firstBorrowUserMoney;
	}
	public void setFirstBorrowUserMoney(BigDecimal firstBorrowUserMoney) {
		this.firstBorrowUserMoney = firstBorrowUserMoney;
	}
	public BigDecimal getDrawMoney() {
		return drawMoney;
	}
	public void setDrawMoney(BigDecimal drawMoney) {
		this.drawMoney = drawMoney;
	}
	public BigDecimal getNoDrawMoney() {
		return noDrawMoney;
	}
	public void setNoDrawMoney(BigDecimal noDrawMoney) {
		this.noDrawMoney = noDrawMoney;
	}
	

}
