package com.dxjr.portal.first.vo;

import java.io.Serializable;
import java.util.Date;

import com.dxjr.common.page.BaseCnd;

/**
 * <p>
 * Description:直通车查询条件<br />
 * </p>
 * 
 * @title FirstBorrowCnd.java
 * @package com.dxjr.portal.first.vo
 * @author justin.xu
 * @version 0.1 2014年7月14日
 */
public class FirstBorrowCnd extends BaseCnd implements Serializable {
	private static final long serialVersionUID = 8387186801624482411L;
	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 排序sql
	 */
	private String orderSql;
	/**
	 * 是否锁定记录, 锁定： yes
	 */
	private String lockedRecordYn;
	/** 直通车状态类型（1：即将开通 2：开通未满 3：开通已满） */
	private String type;
	/** 当前时间 */
	private Date now;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrderSql() {
		return orderSql;
	}

	public void setOrderSql(String orderSql) {
		this.orderSql = orderSql;
	}

	public String getLockedRecordYn() {
		return lockedRecordYn;
	}

	public void setLockedRecordYn(String lockedRecordYn) {
		this.lockedRecordYn = lockedRecordYn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}
}
