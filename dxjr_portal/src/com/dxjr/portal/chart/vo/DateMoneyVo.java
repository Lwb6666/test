package com.dxjr.portal.chart.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Description:平台披露数据<br />
 * </p>
 * 
 * @title DateMoneyInfoVo.java
 * @package com.dxjr.portal.chart.vo
 * @author justin.xu
 * @version 0.1 2014年9月7日
 */
public class DateMoneyVo  implements Serializable {
	/**
	 * 交易总额
	 */
	private BigDecimal transactionAmount;
	/**
	 * 交易总笔数
	 */
	private BigDecimal transactionNumber;
	/**
	 * 借款人数量
	 */
	private BigDecimal borrowersNumber;
	/**
	 * 投资人数量
	 */
	private BigDecimal  investorsNumber;
	/**
	 * 人均累计借款额度
	 */
	private BigDecimal cumulativeAmount;
	/**
	 * 笔均借款额度
	 */
	private BigDecimal averageAmount;
	/**
	 * 人均累计投资额度
	 */
	private BigDecimal investmentAmount;
	/**
	 * 笔均投资额度
	 */
	private BigDecimal investmentAverageAmount;
	/**
	 * 贷款余额
	 */
	private BigDecimal loanBalance;
	/**
	 * 最大单户借款余额占比
	 */
	private String  largestAccounted;
	/**
	 * 最大10户借款余额占比
	 */
	private String tenlargestAccounted;
	/**
	 * 平均满标时间
	 */
	private String meanFullTime;
	/**
	 * 累计违约率
	 */
	private String  cumulativeRate;
	/**
	 * 平台项目逾期率
	 */
	private String  platformProjectRate;
	/**
	 * 近三月项目逾期率 
	 */
	private String  platformProjectThreeRate;
	/**
	 * 借款逾期金额
	 */
	private BigDecimal borrowingAmount;
	/**
	 * 代偿金额
	 */
	private BigDecimal compensatoryAmount;
	/**
	 * 借贷逾期率
	 */
	private String borrowingRate;
	/**
	 * 借贷坏账率
	 */
	private String  debtBadRate;
	/**
	 * 当年成交额
	 */
	private BigDecimal  yearTransactionAmount;
	/**
	 * 当年成交笔数
	 */
	private BigDecimal  yearTransactionCount;
	/**
	 * 代偿笔数
	 */
	private BigDecimal  compensatoryCount;
	public BigDecimal getYearTransactionAmount() {
		return yearTransactionAmount;
	}
	public void setYearTransactionAmount(BigDecimal yearTransactionAmount) {
		this.yearTransactionAmount = yearTransactionAmount;
	}
	public BigDecimal getYearTransactionCount() {
		return yearTransactionCount;
	}
	public void setYearTransactionCount(BigDecimal yearTransactionCount) {
		this.yearTransactionCount = yearTransactionCount;
	}
	public BigDecimal getCompensatoryCount() {
		return compensatoryCount;
	}
	public void setCompensatoryCount(BigDecimal compensatoryCount) {
		this.compensatoryCount = compensatoryCount;
	}
	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	
	public BigDecimal getTransactionNumber() {
		return transactionNumber;
	}
	public void setTransactionNumber(BigDecimal transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	public BigDecimal getBorrowersNumber() {
		return borrowersNumber;
	}
	public void setBorrowersNumber(BigDecimal borrowersNumber) {
		this.borrowersNumber = borrowersNumber;
	}
	public BigDecimal getInvestorsNumber() {
		return investorsNumber;
	}
	public void setInvestorsNumber(BigDecimal investorsNumber) {
		this.investorsNumber = investorsNumber;
	}
	public BigDecimal getCumulativeAmount() {
		return this.getTransactionAmount().divide(this.getBorrowersNumber(),2,BigDecimal.ROUND_HALF_UP);
	}
	public void setCumulativeAmount(BigDecimal cumulativeAmount) {
		this.cumulativeAmount = cumulativeAmount;
	}
	public BigDecimal getAverageAmount() {
		return this.getTransactionAmount().divide(this.getTransactionNumber(),2,BigDecimal.ROUND_HALF_UP);
	}
	public void setAverageAmount(BigDecimal averageAmount) {
		this.averageAmount = averageAmount;
	}
	public BigDecimal getInvestmentAmount() {
		return this.getTransactionAmount().divide(this.getInvestorsNumber(),2,BigDecimal.ROUND_HALF_UP);
	}
	public void setInvestmentAmount(BigDecimal investmentAmount) {
		this.investmentAmount = investmentAmount;
	}
	public BigDecimal getLoanBalance() {
		return loanBalance;
	}
	public void setLoanBalance(BigDecimal loanBalance) {
		this.loanBalance = loanBalance;
	}
	public String getLargestAccounted() {
		return largestAccounted;
	}
	public void setLargestAccounted(String largestAccounted) {
		this.largestAccounted = largestAccounted;
	}

	
	public String getTenlargestAccounted() {
		return tenlargestAccounted;
	}
	public void setTenlargestAccounted(String tenlargestAccounted) {
		this.tenlargestAccounted = tenlargestAccounted;
	}
	public String getCumulativeRate() {
		return cumulativeRate;
	}
	public void setCumulativeRate(String cumulativeRate) {
		this.cumulativeRate = cumulativeRate;
	}
	public String getPlatformProjectRate() {
		return this.cumulativeRate;
	}
	public void setPlatformProjectRate(String platformProjectRate) {
		this.platformProjectRate = platformProjectRate;
	}
	public String getPlatformProjectThreeRate() {
		return platformProjectThreeRate;
	}
	public void setPlatformProjectThreeRate(String platformProjectThreeRate) {
		this.platformProjectThreeRate = platformProjectThreeRate;
	}
	public BigDecimal getBorrowingAmount() {
		return borrowingAmount;
	}
	public void setBorrowingAmount(BigDecimal borrowingAmount) {
		this.borrowingAmount = borrowingAmount;
	}
	public BigDecimal getCompensatoryAmount() {
		return compensatoryAmount;
	}
	public void setCompensatoryAmount(BigDecimal compensatoryAmount) {
		this.compensatoryAmount = compensatoryAmount;
	}
	public String getBorrowingRate() {
		return this.cumulativeRate;
	}
	public void setBorrowingRate(String borrowingRate) {
		this.borrowingRate = borrowingRate;
	}
	public String getDebtBadRate() {
		return "0.00";
	}
	public void setDebtBadRate(String debtBadRate) {
		this.debtBadRate = debtBadRate;
	}
	public BigDecimal getInvestmentAverageAmount() {
		return investmentAverageAmount;
	}
	public void setInvestmentAverageAmount(BigDecimal investmentAverageAmount) {
		this.investmentAverageAmount = investmentAverageAmount;
	}
	public String getMeanFullTime() {
		if(StringUtils.isNotEmpty(meanFullTime)){
			Integer second = Integer.valueOf(meanFullTime)% 60; // 计算秒     
			Integer minute = Integer.valueOf(meanFullTime) / 60 % 60; //计算分 
			Integer hour = Integer.valueOf(meanFullTime) / 3600 % 24; //计算小时 
			Integer day = Integer.valueOf(meanFullTime) / 3600 / 24; //计算天 
			if (day>0) {
						return String.valueOf(day) + '天' + hour
								+ "小时" + minute
								+ '分' + second + '秒';

			}else if (hour>0) {
				return
						String.valueOf(hour)
								+ "小时" + minute
								+ '分' + second + '秒';

			}else if (minute>0) {
				return
						String.valueOf(minute)
								+ '分' + second + '秒';

			} else {
				return String.valueOf(second) + '秒';
			}
		}else{
			return meanFullTime;
		}
	}
	public void setMeanFullTime(String meanFullTime) {
		this.meanFullTime = meanFullTime;
	}
}