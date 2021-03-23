package com.dxjr.portal.lianlian.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:银行卡卡BIN查询API接口同步返回参数<br />
 * </p>
 * 
 * @title LlWapBankcardResponse.java
 * @package com.dxjr.portal.lianlian.vo
 * @author justin.xu
 * @version 0.1 2015年3月25日
 */
public class LlWapBankcardResponse extends LlBaseResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 交易结果代码 */
	private String ret_code;
	/** 交易结果描述 */
	private String ret_msg;
	/**
	 * 签名方式 RSA 或者MD5
	 */
	private String sign_type;
	/**
	 * 签名 RSA 加密签名，见安全签名机制
	 */
	private String sign;
	/**
	 * 银行卡号
	 */
	private String card_no;
	/**
	 * 所属银行编号
	 */
	private String bank_code;
	/**
	 * 所属银行名称
	 */
	private String bank_name;
	/**
	 * 银行卡类型 2 储蓄卡 3 信用卡
	 */
	private String card_type;

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

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

}
