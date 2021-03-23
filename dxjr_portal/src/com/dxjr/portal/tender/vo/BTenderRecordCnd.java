package com.dxjr.portal.tender.vo;

import java.io.Serializable;

import com.dxjr.common.page.BaseCnd;

/**
 * <p>
 * Description:投标记录查询条件<br />
 * </p>
 * 
 * @title BTenderRecordCnd.java
 * @package com.dxjr.portal.tender.vo
 * @author justin.xu
 * @version 0.1 2014年12月23日
 */
public class BTenderRecordCnd extends BaseCnd implements Serializable {

	private static final long serialVersionUID = -7120622884570013103L;
	private Integer id;
	private Integer borrowId;
	/** 投标状态（ -1：所投标失败 ，0：正在投标 ，1：所投标还款中 ，2：所投标完成结束） */
	private Integer status;
	/**
	 * 是否锁定记录, 锁定： yes
	 */
	private String lockedRecordYn;
	/**
	 * 记录的类型 0：原始投标记录 1：目前实际债权人记录，即原始投标记录和认购债转的记录，不包含已经债转出去的数据
	 */
	private Integer recordType;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLockedRecordYn() {
		return lockedRecordYn;
	}

	public void setLockedRecordYn(String lockedRecordYn) {
		this.lockedRecordYn = lockedRecordYn;
	}

	public Integer getRecordType() {
		return recordType;
	}

	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}

}
