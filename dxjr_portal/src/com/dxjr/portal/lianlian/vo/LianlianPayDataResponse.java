package com.dxjr.portal.lianlian.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:连连支付返回信息<br />
 * </p>
 * 
 * @title LianlianPayDataResponse.java
 * @package com.dxjr.portal.lianlian.vo
 * @author justin.xu
 * @version 0.1 2014年10月29日
 */
public class LianlianPayDataResponse extends LlBaseResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private String oid_partner; // 商户编号
	private String sign_type; // RSA 或者 MD5
	private String dt_order; // 商户订单时间
	private String no_order; // 商户唯一订单号
	private String oid_paybill; // 连连钱包支付单号
	private String money_order; // 交易金额
	private String result_pay; // 支付结果 SUCCESS 成功
	private String settle_date; // 清算日期
	private String info_order; // 订单描述
	/** 支付方式 0：余额支付2：快捷支付（借记卡） 3：快捷支付（信用卡）1：网银支付（借记卡）8：网银支付（信用卡） */
	/** 手机未付 ：D:认证支付（借记卡） */
	private String pay_type;
	private String bank_code; // 银行编号

	private String no_agree; // 签约协议号
	private String id_type; // 证件类型 默认为0:身份证
	private String id_no; // 证件号码
	private String acct_name; // 银行帐号姓名

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getOid_partner() {
		return oid_partner;
	}

	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}

	public String getDt_order() {
		return dt_order;
	}

	public void setDt_order(String dt_order) {
		this.dt_order = dt_order;
	}

	public String getNo_order() {
		return no_order;
	}

	public void setNo_order(String no_order) {
		this.no_order = no_order;
	}

	public String getOid_paybill() {
		return oid_paybill;
	}

	public void setOid_paybill(String oid_paybill) {
		this.oid_paybill = oid_paybill;
	}

	public String getMoney_order() {
		return money_order;
	}

	public void setMoney_order(String money_order) {
		this.money_order = money_order;
	}

	public String getResult_pay() {
		return result_pay;
	}

	public void setResult_pay(String result_pay) {
		this.result_pay = result_pay;
	}

	public String getSettle_date() {
		return settle_date;
	}

	public void setSettle_date(String settle_date) {
		this.settle_date = settle_date;
	}

	public String getInfo_order() {
		return info_order;
	}

	public void setInfo_order(String info_order) {
		this.info_order = info_order;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
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

}
