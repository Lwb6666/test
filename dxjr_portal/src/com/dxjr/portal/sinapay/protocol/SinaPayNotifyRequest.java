package com.dxjr.portal.sinapay.protocol;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class SinaPayNotifyRequest implements Serializable {
	/**
	 * @fields serialVersionUID
	 */

	private static final long serialVersionUID = 2912432234876352610L;

	/******************************* 基本参数 ************************/
	/**
	 * @fields notify_type 订单处理结果通知类型
	 */
	private String notify_type;

	/**
	 * @fields notify_id 通知编号 32位长，随机生成
	 */
	private String notify_id;
	/**
	 * @fields _input_charset 参数编码字符集 商户网站使用的编码格式，如utf-8、gbk、gb2312等
	 */
	private String _input_charset;
	private String input_charset;
	/**
	 * @fields notify_time 通知时间 数字串，一共14位 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]
	 */
	private String notify_time;
	/**
	 * @fields sign 签名
	 */
	private String sign;
	/**
	 * @fields sign_type 签名方式 :签名方式支持RSA、MD5。建议使用MD5
	 */
	private String sign_type;
	/**
	 * @fields version 接口版本号
	 */
	private String version;
	/**
	 * @fields memo 备注
	 */
	private String memo;
	/**
	 * @fields error_code 返回错误码
	 */
	private String error_code;
	/**
	 * @fields error_message 返回错误原因
	 */
	private String error_message;
	/******************************* 基本参数 ************************/

	/******************************* 业务参数 ************************/
	/**
	 * @fields out_trade_no 交易订单号
	 */
	private String out_trade_no;
	/**
	 * @fields trade_amount 交易金额 单位为：RMB Yuan。取值范围为[0.01,1000000.00]，精确到小数点后两位。
	 */
	private String trade_amount;
	/**
	 * @fields inner_trade_no 新浪支付内部交易凭证号
	 */
	private String inner_trade_no;
	/**
	 * @fields fee 平台方费用 :单位为：RMB Yuan。取值范围为[0.01,1000000.00]，精确到小数点后两位。
	 */
	private String fee;
	/**
	 * @fields gmt_create 交易创建时间 :数字串，一共14位 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]
	 */
	private String gmt_create;
	/**
	 * @fields gmt_payment 交易支付时间 :数字串，一共14位 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]
	 */
	private String gmt_payment;
	/**
	 * @fields gmt_close 交易关闭时间:数字串，一共14位 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]
	 */
	private String gmt_close;
	/**
	 * @fields trade_status 交易状态
	 */
	private String trade_status;

	/******************************* 业务参数 ************************/
	public String getNotify_type() {
		return notify_type;
	}

	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}

	public String getNotify_id() {
		return notify_id;
	}

	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}

	public String get_input_charset() {
		return _input_charset;
	}

	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
	}

	public String getNotify_time() {
		return notify_time;
	}

	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getInput_charset() {
		return input_charset;
	}

	public void setInput_charset(String input_charset) {
		this.input_charset = input_charset;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTrade_amount() {
		return trade_amount;
	}

	public void setTrade_amount(String trade_amount) {
		this.trade_amount = trade_amount;
	}

	public String getInner_trade_no() {
		return inner_trade_no;
	}

	public void setInner_trade_no(String inner_trade_no) {
		this.inner_trade_no = inner_trade_no;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getGmt_create() {
		return gmt_create;
	}

	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}

	public String getGmt_payment() {
		return gmt_payment;
	}

	public void setGmt_payment(String gmt_payment) {
		this.gmt_payment = gmt_payment;
	}

	public String getGmt_close() {
		return gmt_close;
	}

	public void setGmt_close(String gmt_close) {
		this.gmt_close = gmt_close;
	}

	public String getTrade_status() {
		return trade_status;
	}

	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}

	public Boolean hasNullParameter() {
		if (StringUtils.isEmpty(this.get_input_charset())) {
			return true;
		}
		if (StringUtils.isEmpty(this.getTrade_status())) {
			return true;
		}
		if (StringUtils.isEmpty(this.getSign())) {
			return true;
		}
		if (StringUtils.isEmpty(this.getSign_type())) {
			return true;
		}
		return false;
	}
}
