package com.dxjr.portal.sinapay.protocol;

/**
 * <p>
 * Description:查询接口返回的列表对象<br />
 * </p>
 * 
 * @title SinaQueryRecordListReponse.java
 * @package com.dxjr.portal.sinapay.protocol
 * @author justin.xu
 * @version 0.1 2014年12月26日
 */
public class SinaQueryRecordListReponse {
	/**
	 * 商户订单号
	 */
	private String orderId;
	/**
	 * 新浪支付交易号
	 */
	private String dealId;
	/**
	 * 订单状态 当前订单状态，目前有四种状态：Ready，Processing，Closed，Success
	 */
	private String currentStatus;
	/**
	 * 退款状态 当前订单状态，当前订单的退款状态。 ReadyAudit：待审核， AuditProcessing：审核中，
	 * ReadyRefund：待退款， AuditFaliure：审核失败， RefundProcessing：退款中，
	 * RefundSuccess：退款成功， RefundFailure：退款失败
	 */
	private String refundCurrentStatus;
	/**
	 * 币种类型 目前为 “CNY”
	 */
	private String currencyType;
	/**
	 * 订单金额 以分为单位
	 */
	private String orderAmount;
	/**
	 * 订单实际支付金额 以分为单位
	 */
	private String payAmount;
	/**
	 * 应收手续费 以分为单位
	 */
	private String fee;
	/**
	 * 退款金额 以分为单位
	 */
	private String refundAmount;
	/**
	 * 合作伙伴在新浪支付的用户编号
	 */
	private String pid;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 商品数量
	 */
	private String productNum;
	/**
	 * 用户在新浪支付支付成功的时间 数字串，一共14位 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]
	 * 例如：20071117020101
	 */
	private String dealTime;
	/**
	 * 支付成功时间 数字串，一共14位 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位] 例如：20071117020101
	 */
	private String paymentSuccessTime;
	/**
	 * 银行代码
	 */
	private String bankId;
	/**
	 * 银行交易流水号
	 */
	private String bankDealId;
	/**
	 * 人民币账号
	 */
	private String merchantAcctId;
	/**
	 * 指定的付款人id类型 数字串类型固定值0，1，2 0代表不指定 1代表通过payerId为商户方ID 2代表通过payerId为新浪支付账户 3
	 * 代表通过payerId为微博UID 如果为空代表不需 要指定
	 */
	private String payerIdType;
	/**
	 * 付款人标识
	 */
	private String payerId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getRefundCurrentStatus() {
		return refundCurrentStatus;
	}

	public void setRefundCurrentStatus(String refundCurrentStatus) {
		this.refundCurrentStatus = refundCurrentStatus;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public String getPaymentSuccessTime() {
		return paymentSuccessTime;
	}

	public void setPaymentSuccessTime(String paymentSuccessTime) {
		this.paymentSuccessTime = paymentSuccessTime;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankDealId() {
		return bankDealId;
	}

	public void setBankDealId(String bankDealId) {
		this.bankDealId = bankDealId;
	}

	public String getMerchantAcctId() {
		return merchantAcctId;
	}

	public void setMerchantAcctId(String merchantAcctId) {
		this.merchantAcctId = merchantAcctId;
	}

	public String getPayerIdType() {
		return payerIdType;
	}

	public void setPayerIdType(String payerIdType) {
		this.payerIdType = payerIdType;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public static SinaQueryRecordListReponse buildSinaQueryRecordListReponse(String orderId, String currentStatus, String payAmount, String orderAmount) {
		SinaQueryRecordListReponse sinaQueryRecordListReponse = new SinaQueryRecordListReponse();
		sinaQueryRecordListReponse.setOrderId(orderId);
		sinaQueryRecordListReponse.setCurrentStatus(currentStatus);
		sinaQueryRecordListReponse.setPayAmount(payAmount);
		sinaQueryRecordListReponse.setOrderAmount(orderAmount);
		return sinaQueryRecordListReponse;
	}

}
