package com.dxjr.portal.transfer.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Description:债权认购表<br />
 * </p>
 * @title BSubscribe.java
 * @package com.dxjr.portal.transfer.entity
 * @author qiongbiao.zhang
 * @version 0.1 2014年10月20日
 */
public class BSubscribe {
	// 主键ID
	private Integer id;
	// 债权认购人
	private Integer userId;
	// 债权转让ID
	private Integer transferId;
	// 借款标ID
	private Integer borrowId;
	// 认购金额
	private BigDecimal account;
	// 本金
	private BigDecimal repaymentCapital;
	// 利息
	private BigDecimal repaymentInterest;
	// 待收金额
	private BigDecimal repaymentAccount;
	// 可提现金额
	private BigDecimal drawMoney;
	// 受限金额
	private BigDecimal noDrawMoney;
	// 用户等级
	private String userLevel;
	// 利息管理费比率
	private String ratio;
	// 是否是VIP 1:是：0：否
	private Boolean isVip;
	// 认购状态
	private Boolean status;
	// 添加时间
	private Date addTime;
	// 添加IP
	private String addIp;
	// 添加MAC
	private String addMac;
	// 认购方式（0：手动投标，1：自动投标，2：优先投标）
	private Boolean subscribeType;
	// 自动投标排名位数
	private Integer autotenderOrder;
	// 自动排名位置不变备注
	private String autotenderOrderRemark;

	// 补偿已占用期间利息的利息管理费
	private BigDecimal occupInterestManageFee;

	// 转让方已到期的利息在未占用期间的利息的补偿金
	private BigDecimal unoccupInterestComp;

	// 平台来源
	private Integer platform;

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

	public Integer getTransferId() {
		return transferId;
	}

	public void setTransferId(Integer transferId) {
		this.transferId = transferId;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

	public BigDecimal getRepaymentCapital() {
		return repaymentCapital;
	}

	public void setRepaymentCapital(BigDecimal repaymentCapital) {
		this.repaymentCapital = repaymentCapital;
	}

	public BigDecimal getRepaymentInterest() {
		return repaymentInterest;
	}

	public void setRepaymentInterest(BigDecimal repaymentInterest) {
		this.repaymentInterest = repaymentInterest;
	}

	public BigDecimal getRepaymentAccount() {
		return repaymentAccount;
	}

	public void setRepaymentAccount(BigDecimal repaymentAccount) {
		this.repaymentAccount = repaymentAccount;
	}

	public BigDecimal getDrawMoney() {
		return drawMoney;
	}

	public void setDrawMoney(BigDecimal drawMoney) {
		this.drawMoney = drawMoney;
	}

	public BigDecimal getNoDrawMoney() {
		return noDrawMoney;
	}

	public void setNoDrawMoney(BigDecimal noDrawMoney) {
		this.noDrawMoney = noDrawMoney;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	public Boolean getIsVip() {
		return isVip;
	}

	public void setIsVip(Boolean isVip) {
		this.isVip = isVip;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	public String getAddMac() {
		return addMac;
	}

	public void setAddMac(String addMac) {
		this.addMac = addMac;
	}

	public Boolean getSubscribeType() {
		return subscribeType;
	}

	public void setSubscribeType(Boolean subscribeType) {
		this.subscribeType = subscribeType;
	}

	public Integer getAutotenderOrder() {
		return autotenderOrder;
	}

	public void setAutotenderOrder(Integer autotenderOrder) {
		this.autotenderOrder = autotenderOrder;
	}

	public String getAutotenderOrderRemark() {
		return autotenderOrderRemark;
	}

	public void setAutotenderOrderRemark(String autotenderOrderRemark) {
		this.autotenderOrderRemark = autotenderOrderRemark;
	}

	public BigDecimal getOccupInterestManageFee() {
		return occupInterestManageFee;
	}

	public void setOccupInterestManageFee(BigDecimal occupInterestManageFee) {
		this.occupInterestManageFee = occupInterestManageFee;
	}

	public BigDecimal getUnoccupInterestComp() {
		return unoccupInterestComp;
	}

	public void setUnoccupInterestComp(BigDecimal unoccupInterestComp) {
		this.unoccupInterestComp = unoccupInterestComp;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

}