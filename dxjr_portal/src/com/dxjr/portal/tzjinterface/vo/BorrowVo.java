package com.dxjr.portal.tzjinterface.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class BorrowVo implements Serializable {

	/**
	 * @fields serialVersionUID
	 */

	private static final long serialVersionUID = 5213693494831305108L;
	/**标题*/
	private String title;
	/**年利率*/
	private Float rate;
	/**借款期限*/
	private Integer period;
	/**借款期限单位(天月年)*/
	private String periodUnit;
	/**借款人*/
	private String name;
	/**借款时间（毫秒表示）*/
	private String time;
	/**满标时间*/
	private String fullTime;
	/**借款金额*/
	private BigDecimal amount;
	/**借款说明*/
	private String desc;
	/**借款详情*/
	private String detail;
	/**还款方式*/
	private String returnType;
	/**还款保障*/
	private String returnAssue;
	/**平台标的id*/
	private Integer bid;
	/**借款标类型*/
	private String type;
	private Integer status;
	/**是否属于流转标 0代表不是 1代表是*/
	private String isCirculation;
	
	public String getIsCirculation() {
		return isCirculation;
	}
	public void setIsCirculation(String isCirculation) {
		this.isCirculation = isCirculation;
	}
	public Integer getStatusp() {
		return status;
	}
	public Integer getStatus() {		
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Float getRate() {
		return rate;
	}
	public void setRate(Float rate) {
		this.rate = rate;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public String getPeriodUnit() {
		return periodUnit;
	}
	public void setPeriodUnit(String periodUnit) {
		this.periodUnit = periodUnit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getFullTime() {
		return fullTime;
	}
	public void setFullTime(String fullTime) {
		this.fullTime = fullTime;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getReturnAssue() {
		return returnAssue;
	}
	public void setReturnAssue(String returnAssue) {
		this.returnAssue = returnAssue;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}

}
