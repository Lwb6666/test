package com.dxjr.portal.first.vo;

import java.io.Serializable;

import com.dxjr.common.page.BaseCnd;

/**
 * <p>
 * Description:优先投标封装查询条件<br />
 * </p>
 * 
 * @title FirstTenderDetailCnd.java
 * @package com.dxjr.portal.first.vo
 * @author justin.xu
 * @version 0.1 2014年7月15日
 */
public class FirstTenderDetailCnd extends BaseCnd implements Serializable {

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
	 * 直通车最终记录id
	 */
	private Integer firstTenderRealId;
	/**
	 * 最终认购记录状态 状态 0 ：未失效 1 ：已失效 2：解锁中
	 */
	private Integer firstTenderRealStatus;

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

	public Integer getFirstTenderRealId() {
		return firstTenderRealId;
	}

	public void setFirstTenderRealId(Integer firstTenderRealId) {
		this.firstTenderRealId = firstTenderRealId;
	}

	public Integer getFirstTenderRealStatus() {
		return firstTenderRealStatus;
	}

	public void setFirstTenderRealStatus(Integer firstTenderRealStatus) {
		this.firstTenderRealStatus = firstTenderRealStatus;
	}

}
