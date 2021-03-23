/**
 * 盛付通接收类
 */
package com.dxjr.portal.account.vo;

/**
 * @author justin.xu
 * 
 */
public class ShengReceiveForm {
	private String PaymentNo;
	private String TransNo;
	private String TransTime;
	private String OrderAmount;
	private String SendTime;
	private String TraceNo;
	private String BankSerialNo;
	private String TransAmount;
	private String Charset;
	private String OrderNo;
	private String TransType;
	private String PayableFee;
	private String Version;
	private String Ext1;
	private String Ext2;
	private String TransStatus;
	private String PayChannel;
	private String SignType;
	private String ReceivableFee;
	private String Name;
	private String InstCode;
	private String MerchantNo;
	private String MsgSender;
	private String SignMsg;
	private String ErrorMsg;
	private String ErrorCode;

	public String getPaymentNo() {
		return PaymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		PaymentNo = paymentNo;
	}

	public String getTransNo() {
		return TransNo;
	}

	public void setTransNo(String transNo) {
		TransNo = transNo;
	}

	public String getTransTime() {
		return TransTime;
	}

	public void setTransTime(String transTime) {
		TransTime = transTime;
	}

	public String getOrderAmount() {
		return OrderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		OrderAmount = orderAmount;
	}

	public String getSendTime() {
		return SendTime;
	}

	public void setSendTime(String sendTime) {
		SendTime = sendTime;
	}

	public String getTraceNo() {
		return TraceNo;
	}

	public void setTraceNo(String traceNo) {
		TraceNo = traceNo;
	}

	public String getBankSerialNo() {
		return BankSerialNo;
	}

	public void setBankSerialNo(String bankSerialNo) {
		BankSerialNo = bankSerialNo;
	}

	public String getTransAmount() {
		return TransAmount;
	}

	public void setTransAmount(String transAmount) {
		TransAmount = transAmount;
	}

	public String getCharset() {
		return Charset;
	}

	public void setCharset(String charset) {
		Charset = charset;
	}

	public String getOrderNo() {
		return OrderNo;
	}

	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}

	public String getTransType() {
		return TransType;
	}

	public void setTransType(String transType) {
		TransType = transType;
	}

	public String getPayableFee() {
		return PayableFee;
	}

	public void setPayableFee(String payableFee) {
		PayableFee = payableFee;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public String getExt1() {
		return Ext1;
	}

	public void setExt1(String ext1) {
		Ext1 = ext1;
	}

	public String getExt2() {
		return Ext2;
	}

	public void setExt2(String ext2) {
		Ext2 = ext2;
	}

	public String getTransStatus() {
		return TransStatus;
	}

	public void setTransStatus(String transStatus) {
		TransStatus = transStatus;
	}

	public String getPayChannel() {
		return PayChannel;
	}

	public void setPayChannel(String payChannel) {
		PayChannel = payChannel;
	}

	public String getSignType() {
		return SignType;
	}

	public void setSignType(String signType) {
		SignType = signType;
	}

	public String getReceivableFee() {
		return ReceivableFee;
	}

	public void setReceivableFee(String receivableFee) {
		ReceivableFee = receivableFee;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getInstCode() {
		return InstCode;
	}

	public void setInstCode(String instCode) {
		InstCode = instCode;
	}

	public String getMerchantNo() {
		return MerchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		MerchantNo = merchantNo;
	}

	public String getMsgSender() {
		return MsgSender;
	}

	public void setMsgSender(String msgSender) {
		MsgSender = msgSender;
	}

	public String getSignMsg() {
		return SignMsg;
	}

	public void setSignMsg(String signMsg) {
		SignMsg = signMsg;
	}

	public String getErrorMsg() {
		return ErrorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}

	public String getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}
	
	public String toSignString() {
		StringBuffer buf = new StringBuffer();
		buf.append(Name != null ? Name : "");
		buf.append(Version != null ? Version : "");
		buf.append(Charset != null ? Charset : "");
		buf.append(TraceNo != null ? TraceNo : "");
		buf.append(MsgSender != null ? MsgSender : "");
		buf.append(SendTime != null ? SendTime : "");
		buf.append(InstCode != null ? InstCode : "");
		buf.append(OrderNo != null ? OrderNo : "");
		buf.append(OrderAmount != null ? OrderAmount : "");
		buf.append(TransNo != null ? TransNo : "");
		buf.append(TransAmount != null ? TransAmount : "");
		buf.append(TransStatus != null ? TransStatus : "");
		buf.append(TransType != null ? TransType : "");
		buf.append(TransTime != null ? TransTime : "");
		buf.append(MerchantNo != null ? MerchantNo : "");
		buf.append(ErrorCode != null ? ErrorCode : "");
		buf.append(ErrorMsg != null ? ErrorMsg : "");
		buf.append(Ext1 != null ? Ext1 : "");
		buf.append(SignType != null ? SignType : "");
		return buf.toString();

	}

}
