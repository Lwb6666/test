package com.dxjr.portal.account.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * Description:账户首页金额查询<br />
 * </p>
 * 
 * @title AccountLogVo.java
 * @package com.dxjr.portal.account.vo
 * @author liutao
 * @version 0.1 2016年05月25日
 */
public class AccountInfo implements Serializable {
	private static final long serialVersionUID = 8983061145517554053L;
	//账户总额
	private BigDecimal accountTotal;
	//定期宝总额
	private BigDecimal fixTotal;
	//活期宝总额
	private BigDecimal curTotal;
	//投标总额
	private BigDecimal tenderTotal;
	//可用余额总额
    private BigDecimal useMoneyTotal;
    //冻结总额
    private BigDecimal freezeTotal;
    //非存管可用余额总额
    private BigDecimal pUseMoneyTotal;
    //非存管冻结总额
    private BigDecimal pFreezeTotal;
    //存管可用余额总额
    private BigDecimal eUseMoneyTotal;
    //存管冻结总额
    private BigDecimal eFreezeTotal;
    //待收总额
    private BigDecimal collectionTotal;
    //待收本金
    private BigDecimal collectionCapitalTotal;
    //待收利息
    private BigDecimal collectionInterstTotal;
    //净收益
    private BigDecimal netEaring;
    //账户百分比
    private  Percentage  percentage;
    //rocky_account总额
    private BigDecimal raTotal;
    //rocky_account代收
    private BigDecimal raCollectionTotal;
    //定期宝本金
    private BigDecimal fixCapitalTotal;
    //定期宝利息
    private BigDecimal fixInterstTotal;
    //标本金
    private BigDecimal BCapitalTotal;
    //标利息
    private BigDecimal BInterstTotal;
    
   //微服务推送用
    private BigDecimal drawMoney;// 可提余额
	private BigDecimal noDrawMoney;// 受限金额
	private BigDecimal ptUseMoney;// 可用余额（普通）
	private BigDecimal firstUseMoney;// 可用余额（直通车）
	
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
	public BigDecimal getPtUseMoney() {
		return ptUseMoney;
	}
	public void setPtUseMoney(BigDecimal ptUseMoney) {
		this.ptUseMoney = ptUseMoney;
	}
	public BigDecimal getFirstUseMoney() {
		return firstUseMoney;
	}
	public void setFirstUseMoney(BigDecimal firstUseMoney) {
		this.firstUseMoney = firstUseMoney;
	}
	public BigDecimal getRaTotal() {
		return raTotal;
	}
	public void setRaTotal(BigDecimal raTotal) {
		this.raTotal = raTotal;
	}
	public BigDecimal getRaCollectionTotal() {
		return raCollectionTotal;
	}
	public void setRaCollectionTotal(BigDecimal raCollectionTotal) {
		this.raCollectionTotal = raCollectionTotal;
	}
	public BigDecimal getFixCapitalTotal() {
		return fixCapitalTotal;
	}
	public void setFixCapitalTotal(BigDecimal fixCapitalTotal) {
		this.fixCapitalTotal = fixCapitalTotal;
	}
	public BigDecimal getFixInterstTotal() {
		return fixInterstTotal;
	}
	public void setFixInterstTotal(BigDecimal fixInterstTotal) {
		this.fixInterstTotal = fixInterstTotal;
	}
	public BigDecimal getBCapitalTotal() {
		return BCapitalTotal;
	}
	public void setBCapitalTotal(BigDecimal bCapitalTotal) {
		BCapitalTotal = bCapitalTotal;
	}
	public BigDecimal getBInterstTotal() {
		return BInterstTotal;
	}
	public void setBInterstTotal(BigDecimal bInterstTotal) {
		BInterstTotal = bInterstTotal;
	}
	public BigDecimal getAccountTotal() {
		return accountTotal;
	}
	public void setAccountTotal(BigDecimal accountTotal) {
		this.accountTotal = accountTotal;
	}
	public BigDecimal getFixTotal() {
		return fixTotal;
	}
	public void setFixTotal(BigDecimal fixTotal) {
		this.fixTotal = fixTotal;
	}
	public BigDecimal getCurTotal() {
		return curTotal;
	}
	public void setCurTotal(BigDecimal curTotal) {
		this.curTotal = curTotal;
	}
	public BigDecimal getTenderTotal() {
		return tenderTotal;
	}
	public void setTenderTotal(BigDecimal tenderTotal) {
		this.tenderTotal = tenderTotal;
	}
	public BigDecimal getUseMoneyTotal() {
		return useMoneyTotal;
	}
	public void setUseMoneyTotal(BigDecimal useMoneyTotal) {
		this.useMoneyTotal = useMoneyTotal;
	}
	public BigDecimal getFreezeTotal() {
		return freezeTotal;
	}
	public void setFreezeTotal(BigDecimal freezeTotal) {
		this.freezeTotal = freezeTotal;
	}
	public BigDecimal getpUseMoneyTotal() {
		return pUseMoneyTotal;
	}
	public void setpUseMoneyTotal(BigDecimal pUseMoneyTotal) {
		this.pUseMoneyTotal = pUseMoneyTotal;
	}
	public BigDecimal getpFreezeTotal() {
		return pFreezeTotal;
	}
	public void setpFreezeTotal(BigDecimal pFreezeTotal) {
		this.pFreezeTotal = pFreezeTotal;
	}
	public BigDecimal geteUseMoneyTotal() {
		return eUseMoneyTotal;
	}
	public void seteUseMoneyTotal(BigDecimal eUseMoneyTotal) {
		this.eUseMoneyTotal = eUseMoneyTotal;
	}
	public BigDecimal geteFreezeTotal() {
		return eFreezeTotal;
	}
	public void seteFreezeTotal(BigDecimal eFreezeTotal) {
		this.eFreezeTotal = eFreezeTotal;
	}
	public BigDecimal getCollectionTotal() {
		return collectionTotal;
	}
	public void setCollectionTotal(BigDecimal collectionTotal) {
		this.collectionTotal = collectionTotal;
	}
	public BigDecimal getNetEaring() {
		return netEaring;
	}
	public void setNetEaring(BigDecimal netEaring) {
		this.netEaring = netEaring;
	}
	public BigDecimal getCollectionCapitalTotal() {
		return collectionCapitalTotal;
	}
	public void setCollectionCapitalTotal(BigDecimal collectionCapitalTotal) {
		this.collectionCapitalTotal = collectionCapitalTotal;
	}
	public BigDecimal getCollectionInterstTotal() {
		return collectionInterstTotal;
	}
	public void setCollectionInterstTotal(BigDecimal collectionInterstTotal) {
		this.collectionInterstTotal = collectionInterstTotal;
	}
	public Percentage getPercentage() {
		return percentage;
	}
	public void setPercentage(Percentage percentage) {
		this.percentage = percentage;
	}
}
