package com.dxjr.portal.lianlian.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:支付信息bean<br />
 * </p>
 * 
 * @title PaymentInfo.java
 * @package com.dxjr.portal.lianlian.vo
 * @author justin.xu
 * @version 0.1 2014年10月26日
 */
public class LianlianPaymentRequest extends LlBaseRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 版本号:1.0
	 */
	private String version;
	/**
	 * 商户编号是商户在连连支付支付平台上开设的商户号码， 为18 位数字， 如：201304121000001004，此商户号为支付 交易时的商户号
	 */
	private String oid_partner;
	/**
	 * 该用户在商户系统中的唯一编号，要求是该编号在商户系统中唯一标识该用户
	 */
	private String user_id;
	/**
	 * 时间戳 : 格式：YYYYMMDDH24MISS 14 位数字，精确到秒，签名时间戳有效期30 分钟（包含服务器之间存在的误差）
	 */
	private String timestamp;
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
	 * 用户端申请IP 用户访问商户的请求IP,银通支付网关会根据这个ip 校验用户支付的ip 是否一致，防止钓鱼 请将IP
	 * 中的“.”替换为“_”，例如：IP 是122.11.37.211 的,请转换为122_11_37_211
	 */
	private String userreq_ip;
	/**
	 * 订单地址 合作系统中订单的详情链接地址
	 */
	private String url_order;
	/**
	 * 订单有效时间
	 */
	private String valid_order;
	/**
	 * 指定银行网银编 号
	 */
	private String bank_code;
	/**
	 * 支付方式 1：网银支付（借记卡）8：网银支付（信用卡）9：B2B 企业网银支付 不传则1 和8 都支持
	 */
	private String pay_type;
	/**
	 * 风险控制参数
	 */
	private String risk_item;
	public String no_agree;
	private String id_type; // 证件类型
	private String id_no; // 证件号码
	private String acct_name; // 银行账号姓名
	private String flag_modify; // 修改标记
	private String card_no; // 银行卡号
	private String back_url;

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

	public String getUserreq_ip() {
		return userreq_ip;
	}

	public void setUserreq_ip(String userreq_ip) {
		this.userreq_ip = userreq_ip;
	}

	public String getUrl_order() {
		return url_order;
	}

	public void setUrl_order(String url_order) {
		this.url_order = url_order;
	}

	public String getValid_order() {
		return valid_order;
	}

	public void setValid_order(String valid_order) {
		this.valid_order = valid_order;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getRisk_item() {
		return risk_item;
	}

	public void setRisk_item(String risk_item) {
		this.risk_item = risk_item;
	}

	public String getNo_agree() {
		return no_agree;
	}

	public void setNo_agree(String no_agree) {
		this.no_agree = no_agree;
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

	public String getFlag_modify() {
		return flag_modify;
	}

	public void setFlag_modify(String flag_modify) {
		this.flag_modify = flag_modify;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getBack_url() {
		return back_url;
	}

	public void setBack_url(String back_url) {
		this.back_url = back_url;
	}

}
