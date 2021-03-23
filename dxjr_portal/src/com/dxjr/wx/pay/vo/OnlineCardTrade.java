package com.dxjr.wx.pay.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class OnlineCardTrade implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -3073060630772903864L;
	
	/**
	 * 持卡人支付卡号发卡行
	 */
	private String cardBank;
	/**
	 * 持卡人支付卡号卡类型
	 */
	private String cardType;
	/**
	 * 持卡人支付卡号
	 */
	private String cardNo;
	/**
	 * 持卡人信用卡有效期
	 */
	private String cardExp;
	/**
	 * 持卡人信用卡校验码
	 */
	private String cardCvv2;
	/**
	 * 持卡人姓名
	 */
	private String cardName;
	/**
	 * 持卡人证件类型
	 */
	private String cardIdtype;
	/**
	 * 持卡人证件号
	 */
	private String cardIdno;
	/**
	 * 持卡人手机号
	 */
	private String cardPhone;
	/**
	 * 交易类型
	 */
	private String tradeType;
	/**
	 * 交易号
	 */
	private String tradeId;
	/**
	 * 交易金额
	 */
	private String tradeAmount;
	/**
	 * 交易币种
	 */
	private String tradeCurrency;
	/**
	 * 交易日期
	 */
	private String tradeDate;
	/**
	 * 交易时间
	 */
	private String tradeTime;
	/**
	 * 通知地址
	 */
	private String tradeNotice;
	/**
	 * 交易备注
	 */
	private String tradeNote;
	/**
	 * 交易验证码
	 */
	private String tradeCode;
	
	public String getCardBank() {
		return cardBank;
	}
	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardExp() {
		return cardExp;
	}
	public void setCardExp(String cardExp) {
		this.cardExp = cardExp;
	}
	public String getCardCvv2() {
		return cardCvv2;
	}
	public void setCardCvv2(String cardCvv2) {
		this.cardCvv2 = cardCvv2;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getCardIdtype() {
		return cardIdtype;
	}
	public void setCardIdtype(String cardIdtype) {
		this.cardIdtype = cardIdtype;
	}
	public String getCardIdno() {
		return cardIdno;
	}
	public void setCardIdno(String cardIdno) {
		this.cardIdno = cardIdno;
	}
	public String getCardPhone() {
		return cardPhone;
	}
	public void setCardPhone(String cardPhone) {
		this.cardPhone = cardPhone;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public String getTradeAmount() {
		return tradeAmount;
	}
	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	public String getTradeCurrency() {
		return tradeCurrency;
	}
	public void setTradeCurrency(String tradeCurrency) {
		this.tradeCurrency = tradeCurrency;
	}
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getTradeNotice() {
		return tradeNotice;
	}
	public void setTradeNotice(String tradeNotice) {
		this.tradeNotice = tradeNotice;
	}
	public String getTradeNote() {
		return tradeNote;
	}
	public void setTradeNote(String tradeNote) {
		this.tradeNote = tradeNote;
	}
	public String getTradeCode() {
		return tradeCode;
	}
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	

}
