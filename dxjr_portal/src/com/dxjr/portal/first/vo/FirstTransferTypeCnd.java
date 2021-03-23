package com.dxjr.portal.first.vo;

import java.io.Serializable;

import com.dxjr.common.page.BaseCnd;
import com.dxjr.portal.statics.BusinessConstants;

/**
 * <p>
 * Description:我的账户-直通车转让菜单中列表查询条件<br />
 * </p>
 * 
 * @title FirstTransferTypeCnd.java
 * @package com.dxjr.portal.first.vo
 * @author justin.xu
 * @version 0.1 2015年3月11日
 */
public class FirstTransferTypeCnd extends BaseCnd implements Serializable {
	private static final long serialVersionUID = 8387186801624482411L;
	/** 直通车转让类型： 1：可转让 2：转让中 3：已转出 4：已转入 */
	private Integer type;
	/** 转让人id */
	private Integer userId;
	/** 认购开通时间 */
	private String beginTime;
	/** 认购开通结束时间 */
	private String endTime;
	/** 直通车认购记录 id */
	private Integer firstTenderRealId;
	/**
	 * 一年天数: 用于计算利息的利息
	 * 如果代收表中的利息是按照360天那么下面的应该改成360天，如果代收表中的利息是按照365天计算那么下面的就是365天
	 */
	private Integer yearDays = BusinessConstants.FIRST_TRANSFER_YEAR_DAYS;

	/**
	 * 是否锁定记录, 锁定： yes
	 */
	private String lockedRecordYn;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFirstTenderRealId() {
		return firstTenderRealId;
	}

	public void setFirstTenderRealId(Integer firstTenderRealId) {
		this.firstTenderRealId = firstTenderRealId;
	}

	public Integer getYearDays() {
		return yearDays;
	}

	public void setYearDays(Integer yearDays) {
		this.yearDays = yearDays;
	}

	public String getLockedRecordYn() {
		return lockedRecordYn;
	}

	public void setLockedRecordYn(String lockedRecordYn) {
		this.lockedRecordYn = lockedRecordYn;
	}

}
