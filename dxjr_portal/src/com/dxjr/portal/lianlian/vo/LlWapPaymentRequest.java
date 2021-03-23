package com.dxjr.portal.lianlian.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:手机支付信息bean(商户提交参数)<br />
 * </p>
 * 
 * @title LlWapPaymentRequest.java
 * @package com.dxjr.portal.lianlian.vo
 * @author justin.xu
 * @version 0.1 2015年3月25日
 */
public class LlWapPaymentRequest extends LlBaseRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 接口版本号 版本号 :1.1
	 */
	private String version;
	/**
	 * 商户编号是在连支付平台上开 设的商户号码，为 18 位数字
	 */
	private String oid_partner;
	/**
	 * 该用户在商户系统中的唯一编号，要求是该编号在商户系统中唯一标识该用户
	 */
	private String user_id;
	/**
	 * 请求应用标识 1：Android 2：ios 3：WAP
	 */
	private String app_request;
	/**
	 * 签名 RSA 加密签名，见安全签名机制
	 */
	private String sign;
	/**
	 * 商户业务类型 虚拟商品销售：101001 实物商品销售：109001 外部账户充值：108001
	 */
	private String busi_partner;
	/**
	 * 商户唯一订单号
	 */
	private String no_order;
	/**
	 * 商户订单时间 格式：YYYYMMDDH24MISS 14 位数 字，精确到秒
	 */
	private String dt_order;
	/**
	 * 商品名称
	 */
	private String name_goods;
	/**
	 * 订单描述
	 */
	private String info_order;
	/**
	 * 交易金额 该笔订单的资金总额，单位为RMB-元。大于0 的数字，精确到小数点后两位。如：49.65
	 */
	private String money_order;
	/**
	 * 服务器异步通知 地址 连连支付支付平台在用户支付成功后通知商户服务端的地址
	 */
	private String notify_url;
	/**
	 * 支付结束回显url 支付结束后显示的合作商户系统页面地址
	 */
	private String url_return;
	/**
	 * 签约协议号,已经记录快捷银行卡的用户，商户在调用的时候可以与pay_type一起使用
	 */
	private String no_agree;
	/**
	 * 订单有效时间 分钟为单位，默认为10080分钟（7天）
	 */
	private String valid_order;
	/**
	 * 证件类型 默认为0 （0：身份证)
	 */
	private String id_type;
	/**
	 * 证件号码
	 */
	private String id_no;
	/**
	 * 银行账号姓名
	 */
	private String acct_name;
	/**
	 * 分账商信息数据
	 */
	private String shareing_data;
	/**
	 * 风险控制参数
	 */
	private String risk_item;
	/**
	 * 银行卡号
	 */
	private String card_no;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOid_partner() {
		return oid_partner;
	}

	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getApp_request() {
		return app_request;
	}

	public void setApp_request(String app_request) {
		this.app_request = app_request;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBusi_partner() {
		return busi_partner;
	}

	public void setBusi_partner(String busi_partner) {
		this.busi_partner = busi_partner;
	}

	public String getNo_order() {
		return no_order;
	}

	public void setNo_order(String no_order) {
		this.no_order = no_order;
	}

	public String getDt_order() {
		return dt_order;
	}

	public void setDt_order(String dt_order) {
		this.dt_order = dt_order;
	}

	public String getName_goods() {
		return name_goods;
	}

	public void setName_goods(String name_goods) {
		this.name_goods = name_goods;
	}

	public String getInfo_order() {
		return info_order;
	}

	public void setInfo_order(String info_order) {
		this.info_order = info_order;
	}

	public String getMoney_order() {
		return money_order;
	}

	public void setMoney_order(String money_order) {
		this.money_order = money_order;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getUrl_return() {
		return url_return;
	}

	public void setUrl_return(String url_return) {
		this.url_return = url_return;
	}

	public String getNo_agree() {
		return no_agree;
	}

	public void setNo_agree(String no_agree) {
		this.no_agree = no_agree;
	}

	public String getValid_order() {
		return valid_order;
	}

	public void setValid_order(String valid_order) {
		this.valid_order = valid_order;
	}

	public String getId_type() {
		return id_type;
	}

	public void setId_type(String id_type) {
		this.id_type = id_type;
	}

	public String getId_no() {
		return id_no;
	}

	public void setId_no(String id_no) {
		this.id_no = id_no;
	}

	public String getAcct_name() {
		return acct_name;
	}

	public void setAcct_name(String acct_name) {
		this.acct_name = acct_name;
	}

	public String getShareing_data() {
		return shareing_data;
	}

	public void setShareing_data(String shareing_data) {
		this.shareing_data = shareing_data;
	}

	public String getRisk_item() {
		return risk_item;
	}

	public void setRisk_item(String risk_item) {
		this.risk_item = risk_item;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String md5KeyProperty() {
		return "key";
	}

}
