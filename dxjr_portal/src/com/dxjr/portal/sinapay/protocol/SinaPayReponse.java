package com.dxjr.portal.sinapay.protocol;

public class SinaPayReponse {
	/**
	 * 人民币账号
	 */
	private String merchantAcctId;
	/**
	 * 网关版本 10 不可空 固定值：v2.1 v2.3 注意为小写字母
	 */
	private String version;
	/**
	 * 网关页面显示语言种类 2 不可空 固定值：1 1代表中文显示
	 */
	private String language;
	/**
	 * 签名类型 4 不可空 1代表MD5加密签名方式 4代表PKI/DSA签名方式
	 */
	private String signType;
	/**
	 * 支付方式
	 */
	private String payType;
	/**
	 * 银行代码
	 */
	private String bankId;
	/**
	 * 商户订单号
	 */
	private String orderId;
	/**
	 * 商户订单提交时间
	 */
	private String orderTime;

	/**
	 * 服务器接受支付结果的后台地址 256 可为空 需要是绝对地址，与pageUrl不能同时为空
	 * 微汇将支付结果发送到bgUrl对应的地址，并且获取商户按照约定格式输出的地址，显示页面给用户
	 */
	private String bgUrl;

	/**
	 * 新浪支付交易号
	 */
	private String dealId;
	/**
	 * 用户在新浪支付支付成功的时间
	 */
	private String dealTime;
	/**
	 * 订单实际支付金额
	 */
	private String payAmount;
	/**
	 * 费用
	 */
	private String fee;
	/**
	 * 处理结果 10：支付成功 11：支付失败
	 */
	private String payResult;
	/**
	 * 签名字符串
	 */
	private String signMsg;
	/**
	 * 订单金额
	 */
	private String orderAmount;
	/**
	 * 银行交易流水号
	 */
	private String bankDealId;

	private String ext1;
	private String ext2;
	private String payIp;
	private String errCode;

	public String getMerchantAcctId() {
		return merchantAcctId;
	}

	public void setMerchantAcctId(String merchantAcctId) {
		this.merchantAcctId = merchantAcctId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getBgUrl() {
		return bgUrl;
	}

	public void setBgUrl(String bgUrl) {
		this.bgUrl = bgUrl;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
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

	public String getPayResult() {
		return payResult;
	}

	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getBankDealId() {
		return bankDealId;
	}

	public void setBankDealId(String bankDealId) {
		this.bankDealId = bankDealId;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getPayIp() {
		return payIp;
	}

	public void setPayIp(String payIp) {
		this.payIp = payIp;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String generateSignContent() {
		StringBuilder builder = new StringBuilder();
		if (merchantAcctId != null) {
			builder.append("merchantAcctId").append('=').append(merchantAcctId);
		}
		if (version != null) {
			builder.append('&').append("version").append('=').append(version);
		}
		if (language != null) {
			builder.append('&').append("language").append('=').append(language);
		}
		if (signType != null) {
			builder.append('&').append("signType").append('=').append(signType);
		}
		if (payType != null) {
			builder.append('&').append("payType").append('=').append(payType);
		}
		if (bankId != null) {
			builder.append('&').append("bankId").append('=').append(bankId);
		}
		if (orderId != null) {
			builder.append('&').append("orderId").append('=').append(orderId);
		}
		if (orderTime != null) {
			builder.append('&').append("orderTime").append('=').append(orderTime);
		}
		if (orderAmount != null) {
			builder.append('&').append("orderAmount").append('=').append(orderAmount);
		}
		if (dealId != null) {
			builder.append('&').append("dealId").append('=').append(dealId);
		}
		if (bankDealId != null) {
			builder.append('&').append("bankDealId").append('=').append(bankDealId);
		}
		if (dealTime != null) {
			builder.append('&').append("dealTime").append('=').append(dealTime);
		}
		if (payAmount != null) {
			builder.append('&').append("payAmount").append('=').append(payAmount);
		}
		if (fee != null) {
			builder.append('&').append("fee").append('=').append(fee);
		}
		if (ext1 != null) {
			builder.append('&').append("ext1").append('=').append(ext1);
		}
		if (ext2 != null) {
			builder.append('&').append("ext2").append('=').append(ext2);
		}
		if (payResult != null) {
			builder.append('&').append("payResult").append('=').append(payResult);
		}
		if (payIp != null) {
			builder.append('&').append("payIp").append('=').append(payIp);
		}
		if (errCode != null) {
			builder.append('&').append("errCode").append('=').append(errCode);
		}
		return builder.toString();
	}

	public static SinaPayReponse buildSinaPayReponse(SinaPayNotifyRequest sinaPayNotifyRequest, String trade_status) {
		SinaPayReponse sinaPayReponse = new SinaPayReponse();
		sinaPayReponse.setOrderId(sinaPayNotifyRequest.getOut_trade_no());
		sinaPayReponse.setPayAmount(sinaPayNotifyRequest.getTrade_amount());
		sinaPayReponse.setPayResult(trade_status);
		return sinaPayReponse;
	}

}
