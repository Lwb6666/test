package com.dxjr.portal.repayment.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import com.dxjr.base.entity.BRepaymentRecord;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.StrinUtils;

/**
 * <p>
 * Description:待还记录<br />
 * </p>
 * 
 * @title BRepaymentRecordVo.java
 * @package com.dxjr.portal.repayment.vo
 * @author justin.xu
 * @version 0.1 2014年5月15日
 */
public class BRepaymentRecordVo extends BRepaymentRecord implements Serializable {

	private DecimalFormat df = new DecimalFormat("#,##0.##");

	private static final long serialVersionUID = -1464099049609856305L;
	/** 期数 */
	private String periodStr;

	private String repaymentTimeStr;

	private Date repaymentTime2;
	private String repaymentAccountStr;
	private String lateInterestStr;
	private String interestStr;
	private String capitalStr;
	private String advancetimeStr;
	/**信用等级*/
	private String creditRating;
	/**借款标类型*/
	private Integer borrowType;
	/** 借款金额 */
	private BigDecimal account;
	
	/** 借款标题 */
	private String name;
	/** 还款方式【 0：没有限制， 1：按月分期还款，2：按月付息到期还本 , 3：到期还本付息,4:按天到期还款 */
	private Integer style;
	/** 借款标种类（ 1：信用标，2：抵押标，3：净值标，4：秒标 5：担保标 ） */
	private Integer borrowtype;
	/** 借款期限 */
	private Integer timeLimit;
	/** 年利率 */
	private BigDecimal apr;
	private String aprStr;
	/** 满标时间 **/
	private String successTime;
	/** 用户 **/
	private String username;
	/** 邮箱 **/
	private String email;

	/** 待还总额 **/
	private BigDecimal sumRepaymentAccount;
	private String sumRepaymentAccountStr;
	/** 本金总额 **/
	private BigDecimal sumCapital;
	private String sumCapitalStr;
	/** 利息总额 **/
	private BigDecimal sumInterest;
	private String sumInterestStr;
	
	private String userNameSecret;  //隐藏用户名
	
	public String getCreditRating() {
		return creditRating;
	}

	public void setCreditRating(String creditRating) {
		this.creditRating = creditRating;
	}

	public Integer getBorrowType() {
		return borrowType;
	}

	public void setBorrowType(Integer borrowType) {
		this.borrowType = borrowType;
	}

	/** 逾期罚息总额 **/
	private BigDecimal sumLateInterest;
	private String sumLateInterestStr;

	private boolean isDueToday;
	private boolean isDueTomorrow;

	/** 预计还款日期 */
	private Date repaymentTimeDate;

	private String timeLimitStr;
	/**不带小数的期数*/
	private String orderString;
	
	
	public String getOrderString() {
		if (null != super.getOrder()) {			
			return super.getOrder().toString();
		}
		return orderString;
	}

	public void setOrderString(String orderString) {
		this.orderString = orderString;
	}

	public boolean isDueToday() {
		return isDueToday;
	}

	public void setDueToday(boolean isDueToday) {
		this.isDueToday = isDueToday;
	}

	public boolean isDueTomorrow() {
		return isDueTomorrow;
	}

	public void setDueTomorrow(boolean isDueTomorrow) {
		this.isDueTomorrow = isDueTomorrow;
	}

	public String getSumRepaymentAccountStr() {
		return sumRepaymentAccountStr;
	}

	public void setSumRepaymentAccountStr(String sumRepaymentAccountStr) {
		this.sumRepaymentAccountStr = sumRepaymentAccountStr;
	}

	public String getSumCapitalStr() {
		return sumCapitalStr;
	}

	public void setSumCapitalStr(String sumCapitalStr) {
		this.sumCapitalStr = sumCapitalStr;
	}

	public String getSumInterestStr() {
		return sumInterestStr;
	}

	public void setSumInterestStr(String sumInterestStr) {
		this.sumInterestStr = sumInterestStr;
	}

	public String getSumLateInterestStr() {
		return sumLateInterestStr;
	}

	public void setSumLateInterestStr(String sumLateInterestStr) {
		this.sumLateInterestStr = sumLateInterestStr;
	}

	public BigDecimal getSumRepaymentAccount() {
		return sumRepaymentAccount;
	}

	public void setSumRepaymentAccount(BigDecimal sumRepaymentAccount) {
		this.sumRepaymentAccount = sumRepaymentAccount;
		this.setSumRepaymentAccountStr(df.format(sumRepaymentAccount));
	}

	public BigDecimal getSumCapital() {
		return sumCapital;
	}

	public void setSumCapital(BigDecimal sumCapital) {
		this.sumCapital = sumCapital;
		this.setSumCapitalStr(df.format(sumCapital));
	}

	public BigDecimal getSumInterest() {
		return sumInterest;
	}

	public void setSumInterest(BigDecimal sumInterest) {
		this.sumInterest = sumInterest;
		this.setSumInterestStr(df.format(sumInterest));
	}

	public BigDecimal getSumLateInterest() {
		return sumLateInterest;
	}

	public void setSumLateInterest(BigDecimal sumLateInterest) {
		this.sumLateInterest = sumLateInterest;
		this.setSumLateInterestStr(df.format(sumLateInterest));
	}

	public String getPeriodStr() {
		if (super.getOrder() != null && timeLimit != null && style != null) {
			if (style == 4) {
				return "1/1";
			} else {
				return super.getOrder() + "/" + this.timeLimit;
			}
		}
		return periodStr;
	}

	public void setPeriodStr() {
		this.periodStr = super.getOrder() + "/" + this.timeLimit;
	}

	public String getRepaymentTimeStr() {
		return repaymentTimeStr;
	}

	public void setRepaymentTimeStr(String repaymentTimeStr) {
		this.repaymentTimeStr = repaymentTimeStr;
	}

	public Date getRepaymentTime2() {
		return repaymentTime2;
	}

	public void setRepaymentTime2(Date repaymentTime2) {
		this.repaymentTime2 = repaymentTime2;
		this.setRepaymentTimeStr(DateUtils.format(repaymentTime2, "yyyy-MM-dd"));

		this.setDueToday(false);
		this.setDueTomorrow(false);

		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_YEAR);

		calendar.set(Calendar.DAY_OF_YEAR, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		long todayMillis = calendar.getTimeInMillis();

		if (repaymentTime2.getTime() - todayMillis == 0) {
			this.setDueToday(true);
		} else if (repaymentTime2.getTime() - todayMillis == 24 * 60 * 60 * 1000) {
			this.setDueTomorrow(true);
		}
	}

	public String getRepaymentAccountStr() {
		return repaymentAccountStr;
	}

	public void setRepaymentAccountStr(String repaymentAccountStr) {
		this.repaymentAccountStr = repaymentAccountStr;
	}

	public String getLateInterestStr() {
		return lateInterestStr;
	}

	public void setLateInterestStr(String lateInterestStr) {
		this.lateInterestStr = lateInterestStr;
	}

	public String getInterestStr() {
		return interestStr;
	}

	public void setInterestStr(String interestStr) {
		this.interestStr = interestStr;
	}

	public String getCapitalStr() {
		return capitalStr;
	}

	public void setCapitalStr(String capitalStr) {
		this.capitalStr = capitalStr;
	}

	public String getAdvancetimeStr() {
		return advancetimeStr;
	}

	public void setAdvancetimeStr(String advancetimeStr) {
		this.advancetimeStr = advancetimeStr;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStyle() {
		return style;
	}

	public void setStyle(Integer style) {
		this.style = style;
	}

	public Integer getBorrowtype() {
		return borrowtype;
	}

	public void setBorrowtype(Integer borrowtype) {
		this.borrowtype = borrowtype;
	}

	public Integer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	public BigDecimal getApr() {
		return apr;
	}

	public void setApr(BigDecimal apr) {
		this.apr = apr;
		String aprStr = df.format(apr);
		this.setAprStr(aprStr);
	}

	public String getAprStr() {
		return aprStr;
	}

	public void setAprStr(String aprStr) {
		this.aprStr = aprStr;
	}

	public String getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(String successTime) {
		this.successTime = successTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRepaymentTimeDate() {
		if (null != super.getRepaymentTime()) {
			return DateUtils.timeStampToDate(super.getRepaymentTime());
		}
		return repaymentTimeDate;
	}

	public String getTimeLimitStr() {
		if (timeLimit != null && style != null) {
			if (style != 4) {
				return timeLimit + "个月";
			} else {
				return timeLimit + "天";
			}
		}
		return timeLimitStr;
	}

	public void setTimeLimitStr(String timeLimitStr) {
		this.timeLimitStr = timeLimitStr;
	}
	
	public String getUserNameSecret() {
		userNameSecret = this.getUsername();
			userNameSecret = StrinUtils.stringSecretTo(userNameSecret);
		return userNameSecret;
	}

}
