package com.dxjr.portal.lianlian.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:连连支付掉通知返回信息<br />
 * </p>
 * 
 * @title LianlianPayDataResponse.java
 * @package com.dxjr.portal.lianlian.vo
 * @author justin.xu
 * @version 0.1 2014年10月29日
 */
public class LianlianLosePaymentResponse extends LlBaseResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private String bank_code; // 银行编号
	private String dt_order; // 商户订单时间
	private String money_order; // 交易金额
	private String no_order; // 商户唯一订单号
	private String oid_partner; // 商户编号
	private String oid_paybill; // 连连钱包支付单号
	/** 支付方式 0：余额支付2：快捷支付（借记卡） 3：快捷支付（信用卡）1：网银支付（借记卡）8：网银支付（信用卡） */
	private String pay_type;
	private String result_pay; // 支付结果 SUCCESS 成功
	/** 交易结果代码 */
	private String ret_code;
	/** 交易结果描述 */
	private String ret_msg;
	private String settle_date; // 清算日期
	private String sign_type; // RSA 或者 MD5

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getDt_order() {
		return dt_order;
	}

	public void setDt_order(String dt_order) {
		this.dt_order = dt_order;
	}

	public String getMoney_order() {
		return money_order;
	}

	public void setMoney_order(String money_order) {
		this.money_order = money_order;
	}

	public String getNo_order() {
		return no_order;
	}

	public void setNo_order(String no_order) {
		this.no_order = no_order;
	}

	public String getOid_partner() {
		return oid_partner;
	}

	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}

	public String getOid_paybill() {
		return oid_paybill;
	}

	public void setOid_paybill(String oid_paybill) {
		this.oid_paybill = oid_paybill;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getResult_pay() {
		return result_pay;
	}

	public void setResult_pay(String result_pay) {
		this.result_pay = result_pay;
	}

	public String getRet_code() {
		return ret_code;
	}

	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}

	public String getRet_msg() {
		return ret_msg;
	}

	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}

	public String getSettle_date() {
		return settle_date;
	}

	public void setSettle_date(String settle_date) {
		this.settle_date = settle_date;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

}
