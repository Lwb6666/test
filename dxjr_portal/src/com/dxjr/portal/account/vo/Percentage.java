package com.dxjr.portal.account.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.dxjr.utils.MoneyUtil;

/**
 * <p>
 * Description:百分比<br />
 * </p>
 */
public class Percentage implements Serializable {

	private static final long serialVersionUID = -3531851612222097637L;
	//账户首页百分比
	private BigDecimal fixreax;
	private BigDecimal tenderTotal;
	private BigDecimal currTotal;
	private BigDecimal useTotal;
	//账户-投资管理-定期宝页面百分比
	String checkStyle;
	BigDecimal fixTotal;   
	BigDecimal fixAccountOne;   //1月宝总额
	BigDecimal fixAccountThree;  //3月宝总额
	BigDecimal fixAccountSix;   //6月宝总额
	BigDecimal fixAccountTwel;  //12月宝总额
	
	BigDecimal fixAccountOneRatio;   //1月宝占用比例
	BigDecimal fixAccountThreeRatio;   //3月宝占用比例
	BigDecimal fixAccountSixRatio;   //6月宝占用比例
	BigDecimal fixAccountTwelRatio;  //12月宝占用比例
	
	public BigDecimal getFixreax() {
		return fixreax;
	}
	public void setFixreax(BigDecimal fixreax) {
		this.fixreax = fixreax;
	}
	public BigDecimal getTenderTotal() {
		return tenderTotal;
	}
	public void setTenderTotal(BigDecimal tenderTotal) {
		this.tenderTotal = tenderTotal;
	}
	public BigDecimal getCurrTotal() {
		return currTotal;
	}
	public void setCurrTotal(BigDecimal currTotal) {
		this.currTotal = currTotal;
	}
	public BigDecimal getUseTotal() {
		return useTotal;
	}
	public void setUseTotal(BigDecimal useTotal) {
		this.useTotal = useTotal;
	}
	public BigDecimal getFixAccountOneRatio() {
		return fixAccountOneRatio;
	}
	public void setFixAccountOneRatio(BigDecimal fixAccountOneRatio) {
		this.fixAccountOneRatio = fixAccountOneRatio;
	}
	public BigDecimal getFixAccountThreeRatio() {
		return fixAccountThreeRatio;
	}
	public void setFixAccountThreeRatio(BigDecimal fixAccountThreeRatio) {
		this.fixAccountThreeRatio = fixAccountThreeRatio;
	}
	public BigDecimal getFixAccountSixRatio() {
		return fixAccountSixRatio;
	}
	public void setFixAccountSixRatio(BigDecimal fixAccountSixRatio) {
		this.fixAccountSixRatio = fixAccountSixRatio;
	}
	public BigDecimal getFixAccountTwelRatio() {
		return fixAccountTwelRatio;
	}
	public void setFixAccountTwelRatio(BigDecimal fixAccountTwelRatio) {
		this.fixAccountTwelRatio = fixAccountTwelRatio;
	}
	public BigDecimal getFixTotal() {
		return fixTotal;
	}
	public void setFixTotal(BigDecimal fixTotal) {
		this.fixTotal = fixTotal;
	}
	public BigDecimal getFixAccountOne() {
		return fixAccountOne;
	}
	public void setFixAccountOne(BigDecimal fixAccountOne) {
		this.fixAccountOne = fixAccountOne;
	}
	public BigDecimal getFixAccountThree() {
		return fixAccountThree;
	}
	public void setFixAccountThree(BigDecimal fixAccountThree) {
		this.fixAccountThree = fixAccountThree;
	}
	public BigDecimal getFixAccountSix() {
		return fixAccountSix;
	}
	public void setFixAccountSix(BigDecimal fixAccountSix) {
		this.fixAccountSix = fixAccountSix;
	}
	public BigDecimal getFixAccountTwel() {
		return fixAccountTwel;
	}
	public void setFixAccountTwel(BigDecimal fixAccountTwel) {
		this.fixAccountTwel = fixAccountTwel;
	}
	public String getCheckStyle() {
		return checkStyle;
	}
	public void setCheckStyle(String checkStyle) {
		this.checkStyle = checkStyle;
	}
}
