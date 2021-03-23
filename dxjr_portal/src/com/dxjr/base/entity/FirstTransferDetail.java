package com.dxjr.base.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Description:直通车转让明细类<br />
 * </p>
 * 
 * @title FirstTransferDetail.java
 * @package com.dxjr.base.entity
 * @author justin.xu
 * @version 0.1 2015年3月15日
 */
public class FirstTransferDetail implements Serializable {

	private static final long serialVersionUID = -3531851612222097637L;
	private Integer id;
	/** 直通车转让ID */
	private Integer firstTransferId;
	/** 直通车ID */
	private Integer firstBorrowId;
	/** 转让的直通车此笔最终认购可用余额 */
	private BigDecimal firstUseMoney;
	/** 直通车待收本金 */
	private BigDecimal firstCollectionCapital;
	/** 直通车开通时间 */
	private Date firstTenderTime;
	/** 转让管理费的月数=直通车开通时间到转让时间的月数差额 */
	private Integer manageFeeMonth;
	/** 转让管理费的比率（3月及3月以内1%，3月以上0.5%） */
	private BigDecimal manageFeeRatio;
	/** 添加时间 */
	private Date addtime;
	/** 备注 */
	private String remark;
	/** 直通车所投标待收总利息 */
	private BigDecimal firstRepayInterest;
	/** 借款标个数 **/
	private Integer borrowCount;
	/** 所有借款标总月数 **/
	private Integer borrowMonths;
	/** 所有借款标总利率 **/
	private BigDecimal borrowAprs;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFirstTransferId() {
		return firstTransferId;
	}

	public void setFirstTransferId(Integer firstTransferId) {
		this.firstTransferId = firstTransferId;
	}

	public Integer getFirstBorrowId() {
		return firstBorrowId;
	}

	public void setFirstBorrowId(Integer firstBorrowId) {
		this.firstBorrowId = firstBorrowId;
	}

	public BigDecimal getFirstUseMoney() {
		return firstUseMoney;
	}

	public void setFirstUseMoney(BigDecimal firstUseMoney) {
		this.firstUseMoney = firstUseMoney;
	}

	public BigDecimal getFirstCollectionCapital() {
		return firstCollectionCapital;
	}

	public void setFirstCollectionCapital(BigDecimal firstCollectionCapital) {
		this.firstCollectionCapital = firstCollectionCapital;
	}

	public Date getFirstTenderTime() {
		return firstTenderTime;
	}

	public void setFirstTenderTime(Date firstTenderTime) {
		this.firstTenderTime = firstTenderTime;
	}

	public Integer getManageFeeMonth() {
		return manageFeeMonth;
	}

	public void setManageFeeMonth(Integer manageFeeMonth) {
		this.manageFeeMonth = manageFeeMonth;
	}

	public BigDecimal getManageFeeRatio() {
		return manageFeeRatio;
	}

	public void setManageFeeRatio(BigDecimal manageFeeRatio) {
		this.manageFeeRatio = manageFeeRatio;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getFirstRepayInterest() {
		return firstRepayInterest;
	}

	public void setFirstRepayInterest(BigDecimal firstRepayInterest) {
		this.firstRepayInterest = firstRepayInterest;
	}

	public Integer getBorrowCount() {
		return borrowCount;
	}

	public void setBorrowCount(Integer borrowCount) {
		this.borrowCount = borrowCount;
	}

	public Integer getBorrowMonths() {
		return borrowMonths;
	}

	public void setBorrowMonths(Integer borrowMonths) {
		this.borrowMonths = borrowMonths;
	}

	public BigDecimal getBorrowAprs() {
		return borrowAprs;
	}

	public void setBorrowAprs(BigDecimal borrowAprs) {
		this.borrowAprs = borrowAprs;
	}
}
