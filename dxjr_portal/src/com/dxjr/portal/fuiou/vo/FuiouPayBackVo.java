package com.dxjr.portal.fuiou.vo;

/**
 * 富友支付返回参数
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title FuiouPayBackVo.java
 * @package com.dxjr.portal.fuiou.vo
 * @author huangpin
 * @version 0.1 2015年8月20日
 */
public class FuiouPayBackVo {

	private String mchnt_cd;// 商户代码
	private String order_id;// 商户订单号
	private String order_date;// 订单日期
	private String order_amt;// 交易金额:以分为单位,MAX(12)
	private String order_st;// 订单状态(‘00’ – 订单已生成(初始状态) ‘01’ – 订单已撤消 ‘02’ – 订单已合并 ‘03’ – 订单已过期 ‘04’ – 订单已确认(等待支付) ‘05’ – 订单支付失败 ‘11’ – 订单已支付 ‘18’ – 已发货 ‘19’ – 已确认收货)
	private String order_pay_code;// 错误代码
	private String order_pay_error;// 错误中文描述
	private String resv1;// 保留字段
	private String fy_ssn;// 富友流水号
	private String md5;// MD5摘要数据

	public String getMchnt_cd() {
		return mchnt_cd;
	}

	public void setMchnt_cd(String mchnt_cd) {
		this.mchnt_cd = mchnt_cd;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	public String getOrder_amt() {
		return order_amt;
	}

	public void setOrder_amt(String order_amt) {
		this.order_amt = order_amt;
	}

	public String getOrder_st() {
		return order_st;
	}

	public void setOrder_st(String order_st) {
		this.order_st = order_st;
	}

	public String getOrder_pay_code() {
		return order_pay_code;
	}

	public void setOrder_pay_code(String order_pay_code) {
		this.order_pay_code = order_pay_code;
	}

	public String getOrder_pay_error() {
		return order_pay_error;
	}

	public void setOrder_pay_error(String order_pay_error) {
		this.order_pay_error = order_pay_error;
	}

	public String getResv1() {
		return resv1;
	}

	public void setResv1(String resv1) {
		this.resv1 = resv1;
	}

	public String getFy_ssn() {
		return fy_ssn;
	}

	public void setFy_ssn(String fy_ssn) {
		this.fy_ssn = fy_ssn;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

}
