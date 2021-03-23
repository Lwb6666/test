package com.dxjr.portal.account.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.dxjr.utils.MoneyUtil;

/**
 * <p>
 * Description:年待收数据<br />
 * </p>
 */
public class YearCollect implements Serializable {

	private static final long serialVersionUID = -3531851612222097637L;
	private String type;//1待收 2 已收  3当天的
	private String collectTime;//1待收 2 已收  3当天的
	private BigDecimal totalCollect;
	private BigDecimal fixCollect;
	private BigDecimal borrowCollect;
	private BigDecimal totalYesCollect;
	private BigDecimal fixYesCollect;
	private BigDecimal borrowYesCollect;
	private String totalCollectStr;
	private String fixCollectStr;
	private String borrowCollectStr;
	private String totalYesCollectStr;
	private String fixYesCollectStr;
	private String borrowYesCollectStr;
	
	public String getTotalCollectStr() {
		return MoneyUtil.fmtMoneyByDot(totalCollect);
	}
	public void setTotalCollectStr(String totalCollectStr) {
		this.totalCollectStr = totalCollectStr;
	}
	public String getFixCollectStr() {
		return MoneyUtil.fmtMoneyByDot(fixCollect);
	}
	public void setFixCollectStr(String fixCollectStr) {
		this.fixCollectStr = fixCollectStr;
	}
	public String getBorrowCollectStr() {
		return MoneyUtil.fmtMoneyByDot(borrowCollect);
	}
	public void setBorrowCollectStr(String borrowCollectStr) {
		this.borrowCollectStr = borrowCollectStr;
	}
	public String getTotalYesCollectStr() {
		return MoneyUtil.fmtMoneyByDot(totalYesCollect);
	}
	public void setTotalYesCollectStr(String totalYesCollectStr) {
		this.totalYesCollectStr = totalYesCollectStr;
	}
	public String getFixYesCollectStr() {
		return MoneyUtil.fmtMoneyByDot(fixYesCollect);
	}
	public void setFixYesCollectStr(String fixYesCollectStr) {
		this.fixYesCollectStr = fixYesCollectStr;
	}
	public String getBorrowYesCollectStr() {
		return MoneyUtil.fmtMoneyByDot(borrowYesCollect);
	}
	public void setBorrowYesCollectStr(String borrowYesCollectStr) {
		this.borrowYesCollectStr = borrowYesCollectStr;
	}
	public String getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getTotalCollect() {
		return totalCollect;
	}
	public void setTotalCollect(BigDecimal totalCollect) {
		this.totalCollect = totalCollect;
	}
	public BigDecimal getFixCollect() {
		return fixCollect;
	}
	public void setFixCollect(BigDecimal fixCollect) {
		this.fixCollect = fixCollect;
	}
	public BigDecimal getBorrowCollect() {
		return borrowCollect;
	}
	public void setBorrowCollect(BigDecimal borrowCollect) {
		this.borrowCollect = borrowCollect;
	}
	public BigDecimal getTotalYesCollect() {
		return totalYesCollect;
	}
	public void setTotalYesCollect(BigDecimal totalYesCollect) {
		this.totalYesCollect = totalYesCollect;
	}
	public BigDecimal getFixYesCollect() {
		return fixYesCollect;
	}
	public void setFixYesCollect(BigDecimal fixYesCollect) {
		this.fixYesCollect = fixYesCollect;
	}
	public BigDecimal getBorrowYesCollect() {
		return borrowYesCollect;
	}
	public void setBorrowYesCollect(BigDecimal borrowYesCollect) {
		this.borrowYesCollect = borrowYesCollect;
	}
}
