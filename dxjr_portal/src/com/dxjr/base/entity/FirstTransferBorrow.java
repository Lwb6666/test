package com.dxjr.base.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Description:直通车所投借款标转让资金明细类<br />
 * </p>
 * 
 * @title FirstTransferBorrow.java
 * @package com.dxjr.base.entity
 * @author justin.xu
 * @version 0.1 2015年3月18日
 */
public class FirstTransferBorrow implements Serializable {

	private static final long serialVersionUID = -3531851612222097637L;
	private Integer id;
	/** 直通车转让人ID */
	private Integer userId;
	/** 直通车转让ID */
	private Integer firstTransferId;
	/** 直通车ID */
	private Integer firstBorrowId;
	/** 直通车转让人最终认购记录ID */
	private Integer firstTenderRealId;
	/** 借款标ID */
	private Integer borrowId;
	/** 当期待收ID[用于计算占用利息] */
	private Integer collectionId;
	/** 投标ID */
	private Integer tenderId;
	/** 借款标标题 */
	private String borrowName;
	/** 借款标还款方式 */
	private Integer borrowStyle;
	/** 借款标年化利率 */
	private BigDecimal borrowApr;
	/** 借款标期限 */
	private Integer borrowTimeLimit;
	/** 借款标种类 */
	private Integer borrowtype;
	/** 转让开始期数 */
	private Integer startOrder;
	/** 借款标期数 */
	private Integer borrowOrder;
	/** 借款标未还剩余期限 */
	private BigDecimal borrowRemainLimit;
	/** 借款标实际剩余期限 */
	private BigDecimal borrowActualLimit;
	/** 当期待收开始时间[用于计算占用天数的开始时间,如果没有上一期,取待收添加时间] */
	private Date beforeRepayTime;
	/** 当期待收结束时间[取待收的本期应还款时间] */
	private Date nextRepayTime;
	/** 直通车转让发起的时间 */
	private Date firstTransferAddTime;
	/** 资金占用的天数=直通车转让发起的时间（天）-当期待收开始时间 */
	private Integer occupyDays;
	/** 资金未占用的天数=当期待收结束时间-直通车转让发起的时间（天） */
	private Integer unoccupyDays;
	/** 此标此期待收还款总天数=当期待收结束时间-当期待收开始时间 */
	private Integer orderDays;
	/** 此标此期待收总利息=待收表当期的利息 */
	private BigDecimal orderInterest;
	/** 每一天的利息【总利息/总天数】 */
	private BigDecimal perDayInterest;
	/** 资金占用的利息[E]=每一天的利息*资金占用的天数 */
	private BigDecimal occupyInterest;
	/** 利息的利息[G]=资金占用利息*年化利率/360*资金未占用的天数 */
	private BigDecimal interestDiff;
	/** 债权价格[A+E-G] */
	private BigDecimal account;
	/** 借款标总待收本金 */
	private BigDecimal borrowCollectionCapital;
	/** 借款标总待收利息,即[未还款的总利息] */
	private BigDecimal borrowCollectionInterest;
	/** 借款标总待收总额 */
	private BigDecimal borrowCollectionAccount;
	/** 备注 */
	private String remark;

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

	public Integer getFirstTenderRealId() {
		return firstTenderRealId;
	}

	public void setFirstTenderRealId(Integer firstTenderRealId) {
		this.firstTenderRealId = firstTenderRealId;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	public Integer getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Integer collectionId) {
		this.collectionId = collectionId;
	}

	public Integer getTenderId() {
		return tenderId;
	}

	public void setTenderId(Integer tenderId) {
		this.tenderId = tenderId;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public Integer getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(Integer borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public BigDecimal getBorrowApr() {
		return borrowApr;
	}

	public void setBorrowApr(BigDecimal borrowApr) {
		this.borrowApr = borrowApr;
	}

	public Integer getBorrowTimeLimit() {
		return borrowTimeLimit;
	}

	public void setBorrowTimeLimit(Integer borrowTimeLimit) {
		this.borrowTimeLimit = borrowTimeLimit;
	}

	public Integer getBorrowtype() {
		return borrowtype;
	}

	public void setBorrowtype(Integer borrowtype) {
		this.borrowtype = borrowtype;
	}

	public Integer getStartOrder() {
		return startOrder;
	}

	public void setStartOrder(Integer startOrder) {
		this.startOrder = startOrder;
	}

	public Integer getBorrowOrder() {
		return borrowOrder;
	}

	public void setBorrowOrder(Integer borrowOrder) {
		this.borrowOrder = borrowOrder;
	}

	public BigDecimal getBorrowRemainLimit() {
		return borrowRemainLimit;
	}

	public void setBorrowRemainLimit(BigDecimal borrowRemainLimit) {
		this.borrowRemainLimit = borrowRemainLimit;
	}

	public BigDecimal getBorrowActualLimit() {
		return borrowActualLimit;
	}

	public void setBorrowActualLimit(BigDecimal borrowActualLimit) {
		this.borrowActualLimit = borrowActualLimit;
	}

	public Date getBeforeRepayTime() {
		return beforeRepayTime;
	}

	public void setBeforeRepayTime(Date beforeRepayTime) {
		this.beforeRepayTime = beforeRepayTime;
	}

	public Date getNextRepayTime() {
		return nextRepayTime;
	}

	public void setNextRepayTime(Date nextRepayTime) {
		this.nextRepayTime = nextRepayTime;
	}

	public Date getFirstTransferAddTime() {
		return firstTransferAddTime;
	}

	public void setFirstTransferAddTime(Date firstTransferAddTime) {
		this.firstTransferAddTime = firstTransferAddTime;
	}

	public Integer getOccupyDays() {
		return occupyDays;
	}

	public void setOccupyDays(Integer occupyDays) {
		this.occupyDays = occupyDays;
	}

	public Integer getUnoccupyDays() {
		return unoccupyDays;
	}

	public void setUnoccupyDays(Integer unoccupyDays) {
		this.unoccupyDays = unoccupyDays;
	}

	public Integer getOrderDays() {
		return orderDays;
	}

	public void setOrderDays(Integer orderDays) {
		this.orderDays = orderDays;
	}

	public BigDecimal getOrderInterest() {
		return orderInterest;
	}

	public void setOrderInterest(BigDecimal orderInterest) {
		this.orderInterest = orderInterest;
	}

	public BigDecimal getPerDayInterest() {
		return perDayInterest;
	}

	public void setPerDayInterest(BigDecimal perDayInterest) {
		this.perDayInterest = perDayInterest;
	}

	public BigDecimal getOccupyInterest() {
		return occupyInterest;
	}

	public void setOccupyInterest(BigDecimal occupyInterest) {
		this.occupyInterest = occupyInterest;
	}

	public BigDecimal getInterestDiff() {
		return interestDiff;
	}

	public void setInterestDiff(BigDecimal interestDiff) {
		this.interestDiff = interestDiff;
	}

	public BigDecimal getBorrowCollectionCapital() {
		return borrowCollectionCapital;
	}

	public void setBorrowCollectionCapital(BigDecimal borrowCollectionCapital) {
		this.borrowCollectionCapital = borrowCollectionCapital;
	}

	public BigDecimal getBorrowCollectionInterest() {
		return borrowCollectionInterest;
	}

	public void setBorrowCollectionInterest(BigDecimal borrowCollectionInterest) {
		this.borrowCollectionInterest = borrowCollectionInterest;
	}

	public BigDecimal getBorrowCollectionAccount() {
		return borrowCollectionAccount;
	}

	public void setBorrowCollectionAccount(BigDecimal borrowCollectionAccount) {
		this.borrowCollectionAccount = borrowCollectionAccount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

}
