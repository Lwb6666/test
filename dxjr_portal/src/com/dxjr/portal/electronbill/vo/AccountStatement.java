package com.dxjr.portal.electronbill.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class AccountStatement implements Serializable{
    private Integer id;

    private Integer userId;

    private Integer year;

	private Integer month;

    private BigDecimal interst;

    private BigDecimal totalInterst;

    private BigDecimal lateInterest;

    private BigDecimal awardMoney;

    private BigDecimal stockMoney;

    private BigDecimal transferDiff;

    private BigDecimal otherTotalIncome;

    private BigDecimal totalIncome;

    private BigDecimal interestRepay;

    private BigDecimal interestCost;

    private BigDecimal lateInterestRepay;

    private BigDecimal rechargeFee;

    private BigDecimal cashCost;

    private BigDecimal vipCost;
    
    private BigDecimal fixInterest;
 

	public BigDecimal getFixInterest() {
		return fixInterest;
	}

	public void setFixInterest(BigDecimal fixInterest) {
		this.fixInterest = fixInterest;
	}

	private BigDecimal borrowManageFee;

    private BigDecimal drawDeductFee;

    private BigDecimal transferManageFee;

    private BigDecimal totalExpenditure;

    private BigDecimal netIncome;

    private Integer tenderCount;

    private BigDecimal avgApr;

    private BigDecimal tenderAccount;

    private BigDecimal totalTenderAccount;

	private BigDecimal tenderRate;
	private Double tenderRateDouble;

	private Integer bbsPoint;

    private BigDecimal bbsPointMoney;

    private Integer bbsItems;

    private Integer bbsNotes;

    private Integer bbsBeNotes;

    private Integer bbsCategoryItems;

	private Integer addBy;

    private Date addTime;

    private String remark;
    
	private List<MonthlyInterst> monthlyInterstList;

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

	public Integer getMonth() {
        return month;
    }

	public void setMonth(Integer month) {
        this.month = month;
    }

    public BigDecimal getInterst() {
        return interst;
    }

    public void setInterst(BigDecimal interst) {
        this.interst = interst;
    }

    public BigDecimal getTotalInterst() {
        return totalInterst;
    }

    public void setTotalInterst(BigDecimal totalInterst) {
        this.totalInterst = totalInterst;
    }

    public BigDecimal getLateInterest() {
        return lateInterest;
    }

    public void setLateInterest(BigDecimal lateInterest) {
        this.lateInterest = lateInterest;
    }

    public BigDecimal getAwardMoney() {
        return awardMoney;
    }

    public void setAwardMoney(BigDecimal awardMoney) {
        this.awardMoney = awardMoney;
    }

    public BigDecimal getStockMoney() {
        return stockMoney;
    }

    public void setStockMoney(BigDecimal stockMoney) {
        this.stockMoney = stockMoney;
    }

    public BigDecimal getTransferDiff() {
        return transferDiff;
    }

    public void setTransferDiff(BigDecimal transferDiff) {
        this.transferDiff = transferDiff;
    }

    public BigDecimal getOtherTotalIncome() {
        return otherTotalIncome;
    }

    public void setOtherTotalIncome(BigDecimal otherTotalIncome) {
        this.otherTotalIncome = otherTotalIncome;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getInterestRepay() {
        return interestRepay;
    }

    public void setInterestRepay(BigDecimal interestRepay) {
        this.interestRepay = interestRepay;
    }

    public BigDecimal getInterestCost() {
        return interestCost;
    }

    public void setInterestCost(BigDecimal interestCost) {
        this.interestCost = interestCost;
    }

    public BigDecimal getLateInterestRepay() {
        return lateInterestRepay;
    }

    public void setLateInterestRepay(BigDecimal lateInterestRepay) {
        this.lateInterestRepay = lateInterestRepay;
    }

    public BigDecimal getRechargeFee() {
        return rechargeFee;
    }

    public void setRechargeFee(BigDecimal rechargeFee) {
        this.rechargeFee = rechargeFee;
    }

    public BigDecimal getCashCost() {
        return cashCost;
    }

    public void setCashCost(BigDecimal cashCost) {
        this.cashCost = cashCost;
    }

    public BigDecimal getVipCost() {
        return vipCost;
    }

    public void setVipCost(BigDecimal vipCost) {
        this.vipCost = vipCost;
    }

    public BigDecimal getBorrowManageFee() {
        return borrowManageFee;
    }

    public void setBorrowManageFee(BigDecimal borrowManageFee) {
        this.borrowManageFee = borrowManageFee;
    }

    public BigDecimal getDrawDeductFee() {
        return drawDeductFee;
    }

    public void setDrawDeductFee(BigDecimal drawDeductFee) {
        this.drawDeductFee = drawDeductFee;
    }

    public BigDecimal getTransferManageFee() {
        return transferManageFee;
    }

    public void setTransferManageFee(BigDecimal transferManageFee) {
        this.transferManageFee = transferManageFee;
    }

    public BigDecimal getTotalExpenditure() {
        return totalExpenditure;
    }

    public void setTotalExpenditure(BigDecimal totalExpenditure) {
        this.totalExpenditure = totalExpenditure;
    }

    public BigDecimal getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(BigDecimal netIncome) {
        this.netIncome = netIncome;
    }

    public Integer getTenderCount() {
        return tenderCount;
    }

    public void setTenderCount(Integer tenderCount) {
        this.tenderCount = tenderCount;
    }

    public BigDecimal getAvgApr() {
        return avgApr;
    }

    public void setAvgApr(BigDecimal avgApr) {
        this.avgApr = avgApr;
    }

    public BigDecimal getTenderAccount() {
        return tenderAccount;
    }

    public void setTenderAccount(BigDecimal tenderAccount) {
        this.tenderAccount = tenderAccount;
    }

    public BigDecimal getTotalTenderAccount() {
        return totalTenderAccount;
    }

    public void setTotalTenderAccount(BigDecimal totalTenderAccount) {
        this.totalTenderAccount = totalTenderAccount;
    }

	public BigDecimal getTenderRate() {
		return tenderRate;
	}

	public void setTenderRate(BigDecimal tenderRate) {
		this.tenderRate = tenderRate;
	}

	public Double getTenderRateDouble() {
		tenderRateDouble = tenderRate.doubleValue();
		return tenderRateDouble;
	}

	public Integer getBbsPoint() {
        return bbsPoint;
    }

    public void setBbsPoint(Integer bbsPoint) {
        this.bbsPoint = bbsPoint;
    }

    public BigDecimal getBbsPointMoney() {
        return bbsPointMoney;
    }

    public void setBbsPointMoney(BigDecimal bbsPointMoney) {
        this.bbsPointMoney = bbsPointMoney;
    }

    public Integer getBbsItems() {
        return bbsItems;
    }

    public void setBbsItems(Integer bbsItems) {
        this.bbsItems = bbsItems;
    }

    public Integer getBbsNotes() {
        return bbsNotes;
    }

    public void setBbsNotes(Integer bbsNotes) {
        this.bbsNotes = bbsNotes;
    }

    public Integer getBbsBeNotes() {
        return bbsBeNotes;
    }

    public void setBbsBeNotes(Integer bbsBeNotes) {
        this.bbsBeNotes = bbsBeNotes;
    }

    public Integer getBbsCategoryItems() {
        return bbsCategoryItems;
    }

    public void setBbsCategoryItems(Integer bbsCategoryItems) {
        this.bbsCategoryItems = bbsCategoryItems;
    }

	public Integer getAddBy() {
        return addBy;
    }

	public void setAddBy(Integer addBy) {
        this.addBy = addBy;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public List<MonthlyInterst> getMonthlyInterstList() {
		return monthlyInterstList;
	}

	public void setMonthlyInterstList(List<MonthlyInterst> monthlyInterstList) {
		this.monthlyInterstList = monthlyInterstList;
	}

	 
	
}