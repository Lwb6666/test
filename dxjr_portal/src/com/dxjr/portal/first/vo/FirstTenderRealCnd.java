package com.dxjr.portal.first.vo;

import java.io.Serializable;
import java.util.Date;

import com.dxjr.common.page.BaseCnd;

/**
 * 
 * <p>
 * Description:优先投标封装查询条件<br />
 * </p>
 * 
 * @title FirstTenderRealCnd.java
 * @package com.dxjr.portal.first.vo
 * @author yangshijin
 * @version 0.1 2014年7月24日
 */
public class FirstTenderRealCnd extends BaseCnd implements Serializable {

	private static final long serialVersionUID = 4033975705063158765L;
	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 优先投标计划ID
	 */
	private Integer firstBorrowId;
	/**
	 * 用户Id
	 */
	private Integer userId;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 版本号
	 */
	private String version;
	/**
	 * 排序sql
	 */
	private String orderSql;
	/**
	 * 直通车标题
	 */
	private String firstBorrowName;
	/**
	 * 是否锁定记录, 锁定： yes
	 */
	private String lockedRecordYn;
	/** 直通车期数 */
	private Integer periods;

	private Date beginTime;

	private Date endTime;

	public Integer getPeriods() {
		return periods;
	}

	public void setPeriods(Integer periods) {
		this.periods = periods;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFirstBorrowId() {
		return firstBorrowId;
	}

	public void setFirstBorrowId(Integer firstBorrowId) {
		this.firstBorrowId = firstBorrowId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOrderSql() {
		return orderSql;
	}

	public void setOrderSql(String orderSql) {
		this.orderSql = orderSql;
	}

	public String getFirstBorrowName() {
		return firstBorrowName;
	}

	public void setFirstBorrowName(String firstBorrowName) {
		this.firstBorrowName = firstBorrowName;
	}

	public String getLockedRecordYn() {
		return lockedRecordYn;
	}

	public void setLockedRecordYn(String lockedRecordYn) {
		this.lockedRecordYn = lockedRecordYn;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
