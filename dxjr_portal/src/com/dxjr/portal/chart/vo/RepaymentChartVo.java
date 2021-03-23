package com.dxjr.portal.chart.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.dxjr.utils.DateUtils;
import com.dxjr.utils.StrinUtils;

public class RepaymentChartVo implements Serializable {

	/** Id */
	private Integer id;

	/** borrowId */
	private Integer borrowId;

	/** 借款用户ID */
	private Integer userId;

	/** 借款标状态 */
	private int borrowStatus;

	/** 还款状态 0---未还 1---已还 */
	private int status;

	/** 网站代还状态 0--未代还 1--已代还 */
	private int webstatus;

	/** 估计还款时间 */
	private String repaymentTime;

	/** 已经还款时间 */
	private String repaymentYestime;

	/** 预还金额 */
	private BigDecimal repaymentAccount;

	/** 实际金额 */
	private BigDecimal repaymentYesaccount;

	/** 逾期天数 */
	private int lateDays;

	/** 逾期利息 */
	private BigDecimal lateInterest;

	/** 利息 */
	private BigDecimal interest;

	/** 本金 */
	private BigDecimal capital;

	/** 借款标题 */
	private String borrowName;

	/** 用户名 */
	private String userName;

	/** 垫付时间 **/
	private Date advancetime;

	/** 应还直通车金额 **/
	private BigDecimal firstAccount;

	/** 估计还款时间 */
	private String repaymentTimeStr;

	/** 已经还款时间 */
	private String repaymentYestimeStr;
	/** 排序 */
	private Integer order;
	/** 还款方式 */
	private Integer style;
	/** 借款期限 **/
	private Integer timeLimit;
	/** 借款标类型（1：信用标，2：抵押标，3：净值标，4：秒标 5：担保标） */
	private Integer borrowType;
	
	private String userNameSecret;  //隐藏用户名
	private String userNameEncrypt;  //加密用户名

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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getWebstatus() {
		return webstatus;
	}

	public void setWebstatus(int webstatus) {
		this.webstatus = webstatus;
	}

	public String getRepaymentTime() {
		return repaymentTime;
	}

	public void setRepaymentTime(String repaymentTime) {
		this.repaymentTime = repaymentTime;
	}

	public String getRepaymentYestime() {
		return repaymentYestime;
	}

	public void setRepaymentYestime(String repaymentYestime) {
		this.repaymentYestime = repaymentYestime;
	}

	public BigDecimal getRepaymentAccount() {
		return repaymentAccount;
	}

	public void setRepaymentAccount(BigDecimal repaymentAccount) {
		this.repaymentAccount = repaymentAccount;
	}

	public BigDecimal getRepaymentYesaccount() {
		return repaymentYesaccount;
	}

	public void setRepaymentYesaccount(BigDecimal repaymentYesaccount) {
		this.repaymentYesaccount = repaymentYesaccount;
	}

	public int getLateDays() {
		return lateDays;
	}

	public void setLateDays(int lateDays) {
		this.lateDays = lateDays;
	}

	public BigDecimal getLateInterest() {
		return lateInterest;
	}

	public void setLateInterest(BigDecimal lateInterest) {
		this.lateInterest = lateInterest;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getCapital() {
		return capital;
	}

	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getAdvancetime() {
		return advancetime;
	}

	public void setAdvancetime(Date advancetime) {
		this.advancetime = advancetime;
	}

	public BigDecimal getFirstAccount() {
		return firstAccount;
	}

	public void setFirstAccount(BigDecimal firstAccount) {
		this.firstAccount = firstAccount;
	}

	public String getRepaymentTimeStr() {
		if (repaymentTime != null) {
			return DateUtils.timeStampToDate(repaymentTime, DateUtils.YMD_DASH);
		}
		return repaymentTimeStr;
	}

	public void setRepaymentTimeStr(String repaymentTimeStr) {
		this.repaymentTimeStr = repaymentTimeStr;
	}

	public String getRepaymentYestimeStr() {
		if (repaymentYestime != null) {
			return DateUtils.timeStampToDate(repaymentYestime, DateUtils.YMD_DASH);
		}
		return repaymentYestimeStr;
	}

	public void setRepaymentYestimeStr(String repaymentYestimeStr) {
		this.repaymentYestimeStr = repaymentYestimeStr;
	}

	public int getBorrowStatus() {
		return borrowStatus;
	}

	public void setBorrowStatus(int borrowStatus) {
		this.borrowStatus = borrowStatus;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getStyle() {
		return style;
	}

	public void setStyle(Integer style) {
		this.style = style;
	}

	public Integer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Integer getBorrowType() {
		return borrowType;
	}

	public void setBorrowType(Integer borrowType) {
		this.borrowType = borrowType;
	}
	
	public String getUserNameSecret() {
		userNameSecret = this.getUserName();
		userNameSecret = StrinUtils.stringSecretTo(userNameSecret);
		return userNameSecret;
	}
	public String getUserNameEncrypt() {
		userNameEncrypt = this.getUserName();
		userNameEncrypt = StrinUtils.stringEncryptEn(userNameEncrypt);
		return userNameEncrypt;
	}
}
