package com.dxjr.portal.first.vo;

import java.io.Serializable;

import com.dxjr.common.page.BaseCnd;

/**
 * 
 * <p>
 * Description:直通车转让查询条件<br />
 * </p>
 * 
 * @title FirstTransferCnd.java
 * @package com.dxjr.portal.first.vo
 * @author 朱泳霖
 * @version 0.1 2015年3月16日
 */
public class FirstTransferCnd extends BaseCnd implements Serializable {
	private static final long serialVersionUID = 8387186801624482411L;
	/**
	 * 转让id
	 */
	private Integer id;
	/**
	 * 资金范围
	 */
	private String acountRange;

	/**
	 * 转让状态;2:转让中,4:已转让
	 */
	private Integer transferStatus;

	/**
	 * 已转让状态
	 */
	private String hasTransferStatus;

	/**
	 * 排序内容
	 */
	private String orderBy;
	/**
	 * 排序方式
	 */
	private String orderType;
	/**
	 * 转让人用户id
	 */
	private Integer userId;
	/**
	 * 是否锁定记录, 锁定： yes
	 */
	private String lockedRecordYn;
	
	/**
	 * 认购开通时间 
	 */
	private String beginTime;
	
	/**
	 * 认购开通结束时间
	 */
	private String endTime;
	
	/**
	 * 待收ID
	 */
	private Integer collectionId;
	
	/**
	 * 最终认购记录ID
	 */
	private Integer firstTenderRealId;

	public String getAcountRange() {
		return acountRange;
	}

	public void setAcountRange(String acountRange) {
		this.acountRange = acountRange;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Integer getTransferStatus() {
		return transferStatus;
	}

	public void setTransferStatus(Integer transferStatus) {
		this.transferStatus = transferStatus;
	}

	public String getHasTransferStatus() {
		return hasTransferStatus;
	}

	public void setHasTransferStatus(String hasTransferStatus) {
		this.hasTransferStatus = hasTransferStatus;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLockedRecordYn() {
		return lockedRecordYn;
	}

	public void setLockedRecordYn(String lockedRecordYn) {
		this.lockedRecordYn = lockedRecordYn;
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

	public Integer getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Integer collectionId) {
		this.collectionId = collectionId;
	}

	public Integer getFirstTenderRealId() {
		return firstTenderRealId;
	}

	public void setFirstTenderRealId(Integer firstTenderRealId) {
		this.firstTenderRealId = firstTenderRealId;
	}
}
