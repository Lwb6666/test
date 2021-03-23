package com.dxjr.base.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Description:定期宝提前退出bean<br />
 * </p>
 * 
 * @title FixExit.java
 * @package com.dxjr.base.entity
 * @author caodefeng
 * @version 0.1 2015年5月18日
 */
public class FixExit {

	/**
	 * 主键Id
	 */
	private Integer id;

	/**
	 * 定期宝id
	 */
	private Integer fixBorrowId;

	/**
	 * 用户id
	 */
	private Integer userId;

	/**
	 * 最终认购记录ID
	 */
	private Integer fixTenderRealId;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 用户认购金额
	 */
	private BigDecimal account;
	
	/**
	 * 可用余额
	 */
	private BigDecimal useMoney;

	/**
	 * 预收利息
	 */
	private BigDecimal fixInterest;

	/**
	 * 定期宝总天数
	 */
	private Integer fixDays;

	/**
	 * 退出服务费率
	 */
	private BigDecimal serviceRate;

	/**
	 * 退出服务费
	 */
	private BigDecimal fee;
	/**
	 * 添加时间
	 */
	private Date addTime;

	/**
	 * 定期宝满宝时间
	 */
	private Date fixSuccessTime;

	/**
	 * 实际结算时间，如果为null，自动审核调度未执行或审核失败
	 */
	private Date calcTime;

	/**
	 * 计算利息天数=实际计算时间-定期宝满宝时间
	 */
	private Integer calcDays;

	/**
	 * 实际利息
	 */
	private BigDecimal interestReal;

	/**
	 * 添加人
	 */
	private Integer adduser;

	/**
	 * ip
	 */
	private String addip;

	/**
	 * 更新时间
	 */
	private Date updatetime;

	/**
	 * 最后修改人(portal 对应账号用户名,console对应真实姓名,系统对应系统自动操作)
	 */
	private Integer updateuser;
	/**
	 * 备注
	 */
	private String remark;

	private Integer platform;
	
	/**
	 * 定期宝基础利率
	 */
	private BigDecimal fixApr;


	public Integer getId () {
		return id;
	}


	public void setId (Integer id) {
		this.id = id;
	}

	public Integer getFixBorrowId () {
		return fixBorrowId;
	}


	public void setFixBorrowId (Integer fixBorrowId) {
		this.fixBorrowId = fixBorrowId;
	}

	public Integer getUserId () {
		return userId;
	}


	public void setUserId (Integer userId) {
		this.userId = userId;
	}

	public BigDecimal getAccount () {
		return account;
	}


	public void setAccount (BigDecimal account) {
		this.account = account;
	}

	public Integer getStatus () {
		return status;
	}


	public void setStatus (Integer status) {
		this.status = status;
	}



	public Date getAddTime() {
		return addTime;
	}


	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getFixTenderRealId() {
		return fixTenderRealId;
	}

	public void setFixTenderRealId(Integer fixTenderRealId) {
		this.fixTenderRealId = fixTenderRealId;
	}

	public BigDecimal getFixInterest() {
		return fixInterest;
	}

	public void setFixInterest(BigDecimal fixInterest) {
		this.fixInterest = fixInterest;
	}

	public Integer getFixDays() {
		return fixDays;
	}

	public void setFixDays(Integer fixDays) {
		this.fixDays = fixDays;
	}

	public BigDecimal getServiceRate() {
		return serviceRate;
	}

	public void setServiceRate(BigDecimal serviceRate) {
		this.serviceRate = serviceRate;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Date getFixSuccessTime() {
		return fixSuccessTime;
	}

	public void setFixSuccessTime(Date fixSuccessTime) {
		this.fixSuccessTime = fixSuccessTime;
	}

	public Date getCalcTime() {
		return calcTime;
	}

	public void setCalcTime(Date calcTime) {
		this.calcTime = calcTime;
	}

	public Integer getCalcDays() {
		return calcDays;
	}

	public void setCalcDays(Integer calcDays) {
		this.calcDays = calcDays;
	}

	public BigDecimal getInterestReal() {
		return interestReal;
	}

	public void setInterestReal(BigDecimal interestReal) {
		this.interestReal = interestReal;
	}

	public Integer getAdduser() {
		return adduser;
	}

	public void setAdduser(Integer adduser) {
		this.adduser = adduser;
	}

	public String getAddip() {
		return addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(Integer updateuser) {
		this.updateuser = updateuser;
	}

	public String getRemark () {
		return remark;
	}
	
	public void setRemark (String remark) {
		this.remark = remark;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public BigDecimal getUseMoney() {
		return useMoney;
	}

	public void setUseMoney(BigDecimal useMoney) {
		this.useMoney = useMoney;
	}

	public BigDecimal getFixApr() {
		return fixApr;
	}

	public void setFixApr(BigDecimal fixApr) {
		this.fixApr = fixApr;
	}
}
