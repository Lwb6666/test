package com.dxjr.portal.account.vo;

import java.io.Serializable;

import com.dxjr.common.page.BaseCnd;

/**
 * <p>
 * Description:标查询条件<br />
 * </p>
 * 
 * @title AccountDayLogCnd.java
 * @package com.dxjr.portal.account.vo
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
public class SearchInvestCnd extends BaseCnd implements Serializable {
	private static final long serialVersionUID = 8983061145517554053L;

	// 标的类型
	private String borrowType;
	// 标的状态
	private String limitTime;
	// 还款方式
	private String repayType;
	// 排序内容
	private String orderBy;
	// 排序方式
	private String orderType;
	// 标题
	private String title;
	// 借款人
	private String borrowPeople;

	// 投标时间
	private String beginTime;
	// 投标时间
	private String endTime;
	// 借款标题
	private String borrowID;

	private String remainingterm;

	private Integer excludeJinzhi;
	
	//是否显示新手标 0:显示   1：不显示
	private Integer isShowNewBorrow;

	// 默认查询 只查询 预发和投标
	private Integer isdefult;
	private String isCustody;
	public String getIsCustody() {
		return isCustody;
	}

	public void setIsCustody(String isCustody) {
		this.isCustody = isCustody;
	}

	public String getBorrowID() {
		return borrowID;
	}

	public void setBorrowID(String borrowID) {
		this.borrowID = borrowID;
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

	public String getBorrowType() {
		return borrowType;
	}

	public void setBorrowType(String borrowType) {
		this.borrowType = borrowType;
	}

	public String getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(String limitTime) {
		this.limitTime = limitTime;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBorrowPeople() {
		return borrowPeople;
	}

	public void setBorrowPeople(String borrowPeople) {
		this.borrowPeople = borrowPeople;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getRemainingterm() {
		return remainingterm;
	}

	public void setRemainingterm(String remainingterm) {
		this.remainingterm = remainingterm;
	}

	public Integer getExcludeJinzhi() {
		return excludeJinzhi;
	}

	public void setExcludeJinzhi(Integer excludeJinzhi) {
		this.excludeJinzhi = excludeJinzhi;
	}

	public Integer getIsShowNewBorrow() {
		return isShowNewBorrow;
	}

	public void setIsShowNewBorrow(Integer isShowNewBorrow) {
		this.isShowNewBorrow = isShowNewBorrow;
	}

	public Integer getIsdefult() {
		return isdefult;
	}

	public void setIsdefult(Integer isdefult) {
		this.isdefult = isdefult;
	}

}
