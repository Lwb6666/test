package com.dxjr.portal.account.vo;

/**
 * <p>
 * Description:盛付通提交充值数据<br />
 * </p>
 * 
 * @title ShengPayForm.java
 * @package com.dxjr.portal.account.vo
 * @author justin.xu
 * @version 0.1 2014年5月22日
 */
public class ShengPayForm {
	private String name;
	private String version;
	private String charset;
	private String msgSender;
	private String sendTime;
	private String orderNo;
	private String orderAmount;
	private String orderTime;
	private String payType;
	private String payChannel;
	private String instCode;
	private String pageUrl;
	private String notifyUrl;
	private String productName;
	private String buyerContact;
	private String buyerIp;
	private String ext1;
	private String signType;
	private String signMsg;

	public ShengPayForm() {
		name = "B2CPayment";
		version = "V4.1.1.1.1";

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getMsgSender() {
		return msgSender;
	}

	public void setMsgSender(String msgSender) {
		this.msgSender = msgSender;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getInstCode() {
		return instCode;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBuyerContact() {
		return buyerContact;
	}

	public void setBuyerContact(String buyerContact) {
		this.buyerContact = buyerContact;
	}

	public String getBuyerIp() {
		return buyerIp;
	}

	public void setBuyerIp(String buyerIp) {
		this.buyerIp = buyerIp;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String toSignString() {
		StringBuffer buf = new StringBuffer();
		buf.append(name != null ? name : "");
		buf.append(version != null ? version : "");
		buf.append(charset != null ? charset : "");
		buf.append(msgSender != null ? msgSender : "");
		buf.append(sendTime != null ? sendTime : "");
		buf.append(orderNo != null ? orderNo : "");
		buf.append(orderAmount != null ? orderAmount : "");
		buf.append(orderTime != null ? orderTime : "");
		buf.append(payType != null ? payType : "");
		buf.append(payChannel != null ? payChannel : "");
		buf.append(instCode != null ? instCode : "");
		buf.append(pageUrl != null ? pageUrl : "");
		buf.append(notifyUrl != null ? notifyUrl : "");
		buf.append(productName != null ? productName : "");
		buf.append(buyerContact != null ? buyerContact : "");
		buf.append(buyerIp != null ? buyerIp : "");
		buf.append(ext1 != null ? ext1 : "");
		buf.append(signType != null ? signType : "");
		return buf.toString();

	}

	/**
	 * 直连，不显示收银台
	 * 
	 * @param payType
	 * @param instCode
	 */
	public void setDirectPay(String payType, String instCode) {
		this.payType = payType;
		this.instCode = instCode;
	}
}
