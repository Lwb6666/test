package com.dxjr.portal.fix.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.dxjr.base.entity.FixBorrow;
import com.dxjr.utils.DateUtils;

/**
 * <p>
 * Description:定期宝信息<br />
 * </p>
 * 
 * @title FixBorrowVo.java
 * @package com.dxjr.portal.fix1.vo
 * @author caodefeng
 * @version 0.1 2015年5月14日
 */
public class FixBorrowVo extends FixBorrow {

	/**
	 * 加入状态
	 */
	private Integer joinStatus;
	
	/** 是否使用活期宝金额 0：不使用，1：使用 */
	private String isUseCurMoney;	
	/** 最大有效金额 */
	private BigDecimal maxEffectiveMoney;	
	
	private String flagJoin;	
	
	private String successTimeStr;

	public String getSuccessTimeStr() {
		if(super.getSuccessTime()!=null){
			return DateUtils.format(super.getSuccessTime(), DateUtils.YMD_NYRSH);
		}
		return "";
	}

	public void setSuccessTimeStr(String successTimeStr) {		
		this.successTimeStr = successTimeStr;
	}

	public String getFlagJoin() {
		if(super.getPublishTime()!=null && super.getPublishTime().before(new Date())){
			return "1";
		}else{
			return "2";
		}
	}

	public void setFlagJoin(String flagJoin) {
		this.flagJoin = flagJoin;
	}

	public BigDecimal getMaxEffectiveMoney() {
		return maxEffectiveMoney;
	}

	public void setMaxEffectiveMoney(BigDecimal maxEffectiveMoney) {
		this.maxEffectiveMoney = maxEffectiveMoney;
	}

	public String getIsUseCurMoney() {
		return isUseCurMoney;
	}

	public void setIsUseCurMoney(String isUseCurMoney) {
		this.isUseCurMoney = isUseCurMoney;
	}


	/**
	 * joinMoney
	 */
	private BigDecimal joinMoney;
	
	/**
	 * 结束日期
	 */
	private Integer endDays;
 
	
	/**
	 * 针对首页用的定期宝状态排序
	 */
	private Integer statusId;
	
	

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
 
	
	/**
	 * 累计收益(为用户赚取)
	 */
	private BigDecimal sumInterest;
	
	/**
	 * 锁定状态
	 */
	private Integer lockStatus;
	
	
	/**
	 * 进度
	 */
	private String scheduleStrNoDecimal;
	
	/**
	 * 本金
	 */    
	private BigDecimal capital;
	/**
	 * 定期宝账户可用余额
	 */
	private BigDecimal fixAccountUserMoney;
	
	private String sourceFrom;
	private String totalInvest;
	private String repaymentMoney;
	private String useMoney; //可用金额
	//默认开通时间
	private Date defaultPubTime;
	//转让价格
	private BigDecimal zrMoney;
		
	
	public String getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(String sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	public String getTotalInvest() {
		return totalInvest;
	}

	public void setTotalInvest(String totalInvest) {
		this.totalInvest = totalInvest;
	}

	public String getRepaymentMoney() {
		return repaymentMoney;
	}

	public void setRepaymentMoney(String repaymentMoney) {
		this.repaymentMoney = repaymentMoney;
	}

	public String getUseMoney() {
		return useMoney;
	}

	public void setUseMoney(String useMoney) {
		this.useMoney = useMoney;
	}

	public Date getDefaultPubTime() {
		return defaultPubTime;
	}

	public void setDefaultPubTime(Date defaultPubTime) {
		this.defaultPubTime = defaultPubTime;
	}

	public BigDecimal getZrMoney() {
		return zrMoney;
	}

	public void setZrMoney(BigDecimal zrMoney) {
		this.zrMoney = zrMoney;
	}

	public BigDecimal getCapital() {
		return capital;
	}

	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}

	public BigDecimal getFixAccountUserMoney() {
		return fixAccountUserMoney;
	}

	public void setFixAccountUserMoney(BigDecimal fixAccountUserMoney) {
		this.fixAccountUserMoney = fixAccountUserMoney;
	}

	public Integer getEndDays() {
		return endDays;
	}

	public void setEndDays(Integer endDays) {
		this.endDays = endDays;
	}

	public Integer getJoinStatus() {
		if(getPublishTime()!=null){
			Date date = new Date();
			if(date.before(getPublishTime())){
				setJoinStatus(0);//不可以认购
			}else{
				setJoinStatus(1);//可以认购
			}
		}
		return joinStatus;
	}

	public BigDecimal getJoinMoney() {
		return joinMoney;
	}

	public void setJoinMoney(BigDecimal joinMoney) {
		this.joinMoney = joinMoney;
	}

	public void setJoinStatus(Integer joinStatus) {
		this.joinStatus = joinStatus;
	}

	public BigDecimal getSumInterest() {
		return sumInterest;
	}

	public void setSumInterest(BigDecimal sumInterest) {
		this.sumInterest = sumInterest;
	}

	public Integer getLockStatus() {
		if(null!=getEndTime()){
			if(new Date().before(getEndTime())){
				setLockStatus(0);//未锁定
			}else{
				setLockStatus(1);//已锁定
			}
		}
		return lockStatus;
	}

	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}


	public String getScheduleStrNoDecimal() {
		if (null != getAccountYes()) {
			scheduleStrNoDecimal = String.valueOf(getAccountYes().multiply(new BigDecimal(100)).divide(getPlanAccount(), 0, BigDecimal.ROUND_DOWN));
		} else {
			scheduleStrNoDecimal = "0";
		}
		return scheduleStrNoDecimal;
	}

	public void setScheduleStrNoDecimal(String scheduleStrNoDecimal) {
		this.scheduleStrNoDecimal = scheduleStrNoDecimal;
	}
	
	
	
}
