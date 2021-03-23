package com.dxjr.portal.sinapay.protocol;

public class SinaPayRequest {
	/**
	 * 字符集 2 可为空 固定选择值：1、2、3 1代表UTF-8; 2代表GBK; 3代表GB2312 默认值为1
	 */
	private String inputCharset;
	/**
	 * 接受支付结果的页面地址 256 可为空 需要是绝对地址，与bgUrl不能同时为空 当bgUrl为空时，微汇直接将支付结果GET到pageUrl
	 * 当bgUrl不为空时，按照bgUrl的方式返回
	 */
	private String pageUrl;
	/**
	 * 服务器接受支付结果的后台地址 256 可为空 需要是绝对地址，与pageUrl不能同时为空
	 * 微汇将支付结果发送到bgUrl对应的地址，并且获取商户按照约定格式输出的地址，显示页面给用户
	 */
	private String bgUrl;
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
	 * 人民币账号
	 */
	private String merchantAcctId;

	/**
	 * 商户唯一标识类型
	 */
	private String merchantIdentityType;
	/**
	 * 商户id
	 */
	private String merchantIdentity;

	/**
	 * 支付人姓名
	 */
	private String payerName;
	/**
	 * 支付人联系方式类型
	 */
	private String payerContactType;
	/**
	 * 支付人联系方式
	 */
	private String payerContact;
	/**
	 * 指定付款人
	 */
	private String payerIdType;
	/**
	 * 付款人标识
	 */
	private String payerId;
	/**
	 * 商户订单号
	 */
	private String orderId;
	/**
	 * 商户订单金额
	 */
	private String orderAmount;
	/**
	 * 商户订单提交时间
	 */
	private String orderTime;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 商品数量
	 */
	private String productNum;
	/**
	 * 商品代码
	 */
	private String productId;
	/**
	 * 商品描述
	 */
	private String productDesc;
	/**
	 * 代理平台编号
	 */
	private String platformId;
	/**
	 * 代理签名类型
	 */
	private String platformSignType;
	/**
	 * 代理平台URL
	 */
	private String platformUrl;
	/**
	 * 扩展字段1
	 */
	private String ext1;
	/**
	 * 扩展字段2
	 */
	private String ext2;
	/**
	 * 支付方式
	 */
	private String payType;
	/**
	 * 银行代码
	 */
	private String bankId;
	/**
	 * 发卡机构
	 */
	private String cardIssuer;
	/**
	 * 卡号
	 */
	private String cardNum;
	/**
	 * 线下汇款类型
	 */
	private String remitType;
	/**
	 * 汇款识别码
	 */
	private String remitCode;
	/**
	 * 同一订单禁止重复提交标志
	 */
	private String redoFlag;
	/**
	 * 合作伙伴在微汇的用户编号
	 */
	private String pid;
	/**
	 * 提交方式
	 */
	private String submitType;
	/**
	 * 签名字符串
	 */
	private String signMsg;
	/**
	 * 代理签名数据
	 */
	private String platformSignMsg;
	/**
	 * 商户下单IP
	 */
	private String ip;
	/**
	 * 商户下单设备mac地址
	 */
	private String deviceId;

	/**
	 * 分账标志
	 */
	private String sharingFlag;

	/**
	 * 分账数据
	 */
	private String sharingData;


	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getBgUrl() {
		return bgUrl;
	}

	public void setBgUrl(String bgUrl) {
		this.bgUrl = bgUrl;
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

	public String getMerchantAcctId() {
		return merchantAcctId;
	}

	public void setMerchantAcctId(String merchantAcctId) {
		this.merchantAcctId = merchantAcctId;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayerContactType() {
		return payerContactType;
	}

	public void setPayerContactType(String payerContactType) {
		this.payerContactType = payerContactType;
	}

	public String getPayerContact() {
		return payerContact;
	}

	public void setPayerContact(String payerContact) {
		this.payerContact = payerContact;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getPlatformSignType() {
		return platformSignType;
	}

	public void setPlatformSignType(String platformSignType) {
		this.platformSignType = platformSignType;
	}

	public String getPlatformUrl() {
		return platformUrl;
	}

	public void setPlatformUrl(String platformUrl) {
		this.platformUrl = platformUrl;
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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getRemitType() {
		return remitType;
	}

	public void setRemitType(String remitType) {
		this.remitType = remitType;
	}

	public String getRemitCode() {
		return remitCode;
	}

	public void setRemitCode(String remitCode) {
		this.remitCode = remitCode;
	}

	public String getRedoFlag() {
		return redoFlag;
	}

	public void setRedoFlag(String redoFlag) {
		this.redoFlag = redoFlag;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getSubmitType() {
		return submitType;
	}

	public void setSubmitType(String submitType) {
		this.submitType = submitType;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String getPlatformSignMsg() {
		return platformSignMsg;
	}

	public void setPlatformSignMsg(String platformSignMsg) {
		this.platformSignMsg = platformSignMsg;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String generateSignContent() {
		StringBuilder builder = new StringBuilder();
		if (inputCharset != null) {
			builder.append("inputCharset").append('=').append(inputCharset);
		}
		if (pageUrl != null) {
			builder.append('&').append("pageUrl").append('=').append(pageUrl);
		}
		if (bgUrl != null) {
			builder.append('&').append("bgUrl").append('=').append(bgUrl);
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
		if (merchantAcctId != null) {
			builder.append('&').append("merchantAcctId").append('=').append(merchantAcctId);
		}
		if (merchantIdentityType != null) {
			builder.append('&').append("merchantIdentityType").append('=').append(merchantIdentityType);
		}
		if (merchantIdentity != null) {
			builder.append('&').append("merchantIdentity").append('=').append(merchantIdentity);
		}
		if (payerName != null) {
			builder.append('&').append("payerName").append('=').append(payerName);
		}
		if (payerContactType != null) {
			builder.append('&').append("payerContactType").append('=').append(payerContactType);
		}
		if (payerContact != null) {
			builder.append('&').append("payerContact").append('=').append(payerContact);
		}
		if (payerIdType != null) {
			builder.append('&').append("payerIdType").append('=').append(payerIdType);
		}
		if (payerId != null) {
			builder.append('&').append("payerId").append('=').append(payerId);
		}
		if (orderId != null) {
			builder.append('&').append("orderId").append('=').append(orderId);
		}
		if (orderAmount != null) {
			builder.append('&').append("orderAmount").append('=').append(orderAmount);
		}
		if (orderTime != null) {
			builder.append('&').append("orderTime").append('=').append(orderTime);
		}

		if (productName != null) {
			builder.append('&').append("productName").append('=').append(productName);
		}

		if (productNum != null) {
			builder.append('&').append("productNum").append('=').append(productNum);
		}

		if (productId != null) {
			builder.append('&').append("productId").append('=').append(productId);
		}

		if (productDesc != null) {
			builder.append('&').append("productDesc").append('=').append(productDesc);
		}

		if (platformId != null) {
			builder.append('&').append("platformId").append('=').append(platformId);
		}

		if (platformSignType != null) {
			builder.append('&').append("platformSignType").append('=').append(platformSignType);
		}

		if (platformUrl != null) {
			builder.append('&').append("platformUrl").append('=').append(platformUrl);
		}

		if (ext1 != null) {
			builder.append('&').append("ext1").append('=').append(ext1);
		}
		if (ext2 != null) {
			builder.append('&').append("ext2").append('=').append(ext2);
		}
		if (payType != null) {
			builder.append('&').append("payType").append('=').append(payType);
		}
		if (bankId != null) {
			builder.append('&').append("bankId").append('=').append(bankId);
		}
		if (cardIssuer != null) {
			builder.append('&').append("cardIssuer").append('=').append(cardIssuer);
		}
		if (cardNum != null) {
			builder.append('&').append("cardNum").append('=').append(cardNum);
		}
		if (remitType != null) {
			builder.append('&').append("remitType").append('=').append(remitType);
		}
		if (remitCode != null) {
			builder.append('&').append("remitCode").append('=').append(remitCode);
		}
		if (redoFlag != null) {
			builder.append('&').append("redoFlag").append('=').append(redoFlag);
		}
		if (pid != null) {
			builder.append('&').append("pid").append('=').append(pid);
		}
		if (ip != null) {
			builder.append('&').append("ip").append('=').append(ip);
		}
		if (deviceId != null) {
			builder.append('&').append("deviceId").append('=').append(deviceId);
		}
		if (submitType != null) {
			builder.append('&').append("submitType").append('=').append(submitType);
		}
		if (sharingFlag != null) {
			builder.append('&').append("sharingFlag").append('=').append(sharingFlag);
		}
		if (sharingData != null) {
			builder.append('&').append("sharingData").append('=').append(sharingData);
		}
		return builder.toString();
	}

	public String md5KeyProperty() {
		return "key";
	}

	public void sign(String signMsg) {
	}

	public String getSharingFlag() {
		return sharingFlag;
	}

	public void setSharingFlag(String sharingFlag) {
		this.sharingFlag = sharingFlag;
	}

	public String getSharingData() {
		return sharingData;
	}

	public void setSharingData(String sharingData) {
		this.sharingData = sharingData;
	}

	public String getMerchantIdentity() {
		return merchantIdentity;
	}

	public void setMerchantIdentity(String merchantIdentity) {
		this.merchantIdentity = merchantIdentity;
	}

	public String getMerchantIdentityType() {
		return merchantIdentityType;
	}

	public void setMerchantIdentityType(String merchantIdentityType) {
		this.merchantIdentityType = merchantIdentityType;
	}

	@Override
	public String toString() {
		return "KuaiqianRequest [inputCharset=" + inputCharset + ", pageUrl=" + pageUrl + ", bgUrl=" + bgUrl
				+ ", version=" + version + ", language=" + language + ", signType=" + signType + ", merchantAcctId="
				+ merchantAcctId + ", merchantIdentityType=" + merchantIdentityType + ", merchantIdentity="
				+ merchantIdentity + ", payerName=" + payerName + ", payerContactType=" + payerContactType
				+ ", payerContact=" + payerContact + ", payerIdType=" + payerIdType + ", payerId=" + payerId
				+ ", orderId=" + orderId + ", orderAmount=" + orderAmount + ", orderTime=" + orderTime
				+ ", productName=" + productName + ", productNum=" + productNum + ", productId=" + productId
				+ ", productDesc=" + productDesc + ", platformId=" + platformId + ", platformSignType="
				+ platformSignType + ", platformUrl=" + platformUrl + ", ext1=" + ext1 + ", ext2=" + ext2
				+ ", payType=" + payType + ", bankId=" + bankId + ", cardIssuer=" + cardIssuer + ", cardNum=" + cardNum
				+ ", remitType=" + remitType + ", remitCode=" + remitCode + ", redoFlag=" + redoFlag + ", pid=" + pid
				+ ", submitType=" + submitType + ", signMsg=" + signMsg + ", platformSignMsg=" + platformSignMsg
				+ ", ip=" + ip + ", deviceId=" + deviceId + ", sharingFlag=" + sharingFlag + ", sharingData="
				+ sharingData + "]";
	}

}
