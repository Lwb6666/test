package com.dxjr.wx.entry.message.autoreply.vo;

import java.math.BigDecimal;

import com.dxjr.wx.utils.NumberUtil;

public class MoneyDetail {
	/**
	 * 尊敬的xxx， 您的资金详情如下： 可用余额（普通）：0 可用余额（直通车）：0 可提余额：0 受限金额：0 资产总额：1,735,472.58
	 * 充值总额：5,827,088.84 提现总额：4,674,984.00 冻结总额：0 直通车总额：740,000.00
	 */

	private BigDecimal ordinaryAvailable;// 可用余额（普通）
	private BigDecimal trainAvailable;// 可用余额（直通车）
	private BigDecimal drawMoney;// 可提余额
	private BigDecimal noDrawMoney;// 受限金额
	private BigDecimal totalMoney;// 资产总额
	private BigDecimal totalMoneyByCharge;// 充值总额
	private BigDecimal totalDrawMoney;// 提现总额
	private BigDecimal totalNoDrawMoney;// 冻结总额：
	private BigDecimal totalTrain;// 直通车总额
	private BigDecimal currTotal;// 活期宝总额
	private BigDecimal fixTotal;// 定期宝总额
	private BigDecimal totalCollection;//待收总额
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOrdinaryAvailable() {

		return NumberUtil.fmtMicrometer(ordinaryAvailable);
	}

	public void setOrdinaryAvailable(BigDecimal ordinaryAvailable) {
		this.ordinaryAvailable = ordinaryAvailable;
	}

	public String getTrainAvailable() {
		return NumberUtil.fmtMicrometer(trainAvailable);
	}

	public void setTrainAvailable(BigDecimal trainAvailable) {
		this.trainAvailable = trainAvailable;
	}

	public String getDrawMoney() {
		return NumberUtil.fmtMicrometer(drawMoney);
	}

	public void setDrawMoney(BigDecimal drawMoney) {
		this.drawMoney = drawMoney;
	}

	public String getNoDrawMoney() {
		return NumberUtil.fmtMicrometer(noDrawMoney);
	}

	public void setNoDrawMoney(BigDecimal noDrawMoney) {
		this.noDrawMoney = noDrawMoney;
	}

	public String getTotalMoney() {
		return NumberUtil.fmtMicrometer(totalMoney);
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getTotalMoneyByCharge() {
		return NumberUtil.fmtMicrometer(totalMoneyByCharge);
	}

	public void setTotalMoneyByCharge(BigDecimal totalMoneyByCharge) {
		this.totalMoneyByCharge = totalMoneyByCharge;
	}

	public String getTotalDrawMoney() {
		return NumberUtil.fmtMicrometer(totalDrawMoney);
	}

	public void setTotalDrawMoney(BigDecimal totalDrawMoney) {
		this.totalDrawMoney = totalDrawMoney;
	}

	public String getTotalNoDrawMoney() {
		return NumberUtil.fmtMicrometer(totalNoDrawMoney);
	}

	public void setTotalNoDrawMoney(BigDecimal totalNoDrawMoney) {
		this.totalNoDrawMoney = totalNoDrawMoney;
	}

	public String getTotalTrain() {
		return NumberUtil.fmtMicrometer(totalTrain);
	}

	public void setTotalTrain(BigDecimal totalTrain) {
		this.totalTrain = totalTrain;
	}


	public void setCurrTotal(BigDecimal currTotal) {
		this.currTotal = currTotal;
	}


	public void setFixTotal(BigDecimal fixTotal) {
		this.fixTotal = fixTotal;
	}
	public String getCurrTotal() {
		return NumberUtil.fmtMicrometer(currTotal);
	}
	public String getFixTotal() {
		return NumberUtil.fmtMicrometer(fixTotal);
	}
	public String getTotalCollection() {
		return NumberUtil.fmtMicrometer(totalCollection);
	}

	public void setTotalCollection(BigDecimal totalCollection) {
		this.totalCollection = totalCollection;
	}

	@Override
	public String toString() {
		return "尊敬的" + username + ",\n您的资金详情如下（元）:\n资产总额：" + getTotalMoney() + "\n可用余额（普通）:" + getOrdinaryAvailable() + "\n可用余额（直通车）:" + getTrainAvailable() + "\n可提余额：" + getDrawMoney() + "\n受限金额：" + getNoDrawMoney() + "\n冻结总额：" + getTotalNoDrawMoney() + "\n直通车总额：" + getTotalTrain() + "\n待收总额：" + getTotalCollection()+ "\n活期宝总额：" + getCurrTotal() +"\n定期宝总额：" + getFixTotal() +"\n充值总额：" + getTotalMoneyByCharge() + "\n提现总额：" + getTotalDrawMoney();
	}

}
