package com.dxjr.base.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Description:第三方支付平台反馈信息<br />
 * </p>
 * 
 * @title AccountRechargeFeedback.java
 * @package com.dxjr.base.entity
 * @author justin.xu
 * @version 0.1 2014年5月23日
 */
public class AccountRechargeFeedback implements Serializable {
	private static final long serialVersionUID = -2001593671763863571L;

	/** 主键 */
	private Integer id;

	/** 订单编号 */
	private String orderno;

	/** 支付方式中文说明，如"中行长城信用卡" */
	private String paymode;

	/** 支付结果(网银在线支付结果：20支付完成；30支付失败；国付宝支付结果：0000表示支付成功,其他表示支付失败) */
	private String paystatus;

	/** 对支付结果的说明 */
	private String paystring;

	/** 订单实际支付金额 */
	private String amount;

	/** 币种 */
	private String moneytype;

	/** MD5校验码 */
	private String md5str;

	/** 备注1 */
	private String remark1;

	/** 备注2 */
	private String remark2;

	/** 支付平台,1表示网银在线 */
	private int onlinetype;

	/** 插入时间 */
	private Date addtime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getPaymode() {
		return paymode;
	}

	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}

	public String getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}

	public String getPaystring() {
		return paystring;
	}

	public void setPaystring(String paystring) {
		this.paystring = paystring;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMoneytype() {
		return moneytype;
	}

	public void setMoneytype(String moneytype) {
		this.moneytype = moneytype;
	}

	public String getMd5str() {
		return md5str;
	}

	public void setMd5str(String md5str) {
		this.md5str = md5str;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public int getOnlinetype() {
		return onlinetype;
	}

	public void setOnlinetype(int onlinetype) {
		this.onlinetype = onlinetype;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

}