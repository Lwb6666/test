package com.dxjr.portal.account.vo;

import com.dxjr.portal.statics.BusinessConstants;

/**
 * <p>
 * Description:网银在线支付成功返回的数据<br />
 * </p>
 * 
 * @title ChinabankReceiveForm.java
 * @package com.dxjr.portal.account.vo
 * @author justin.xu
 * @version 0.1 2014年6月11日
 */
public class ChinabankReceiveForm {
	/** 订单号 */
	private String v_oid;
	/** 支付方式中文说明，如"中行长城信用卡" */
	private String v_pmode;
	/** 支付结果，20支付完成；30支付失败； */
	private String v_pstatus;
	/** 对支付结果的说明，成功时（v_pstatus=20）为"支付成功"，支付失败时（v_pstatus=30）为"支付失败" */
	private String v_pstring;
	/** 订单实际支付金额 */
	private String v_amount;
	/** 币种 */
	private String v_moneytype;
	/** MD5校验码 */
	private String v_md5str;
	/** 备注1 */
	private String remark1;
	/** 备注2 */
	private String remark2;

	public String getV_oid() {
		return v_oid;
	}

	public void setV_oid(String v_oid) {
		this.v_oid = v_oid;
	}

	public String getV_pmode() {
		return v_pmode;
	}

	public void setV_pmode(String v_pmode) {
		this.v_pmode = v_pmode;
	}

	public String getV_pstatus() {
		return v_pstatus;
	}

	public void setV_pstatus(String v_pstatus) {
		this.v_pstatus = v_pstatus;
	}

	public String getV_pstring() {
		return v_pstring;
	}

	public void setV_pstring(String v_pstring) {
		this.v_pstring = v_pstring;
	}

	public String getV_amount() {
		return v_amount;
	}

	public void setV_amount(String v_amount) {
		this.v_amount = v_amount;
	}

	public String getV_moneytype() {
		return v_moneytype;
	}

	public void setV_moneytype(String v_moneytype) {
		this.v_moneytype = v_moneytype;
	}

	public String getV_md5str() {
		return v_md5str;
	}

	public void setV_md5str(String v_md5str) {
		this.v_md5str = v_md5str;
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

	/**
	 * <p>
	 * Description:生成要拼接的md5字符串<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月11日
	 * @return String
	 */
	public String generateMd5String() {
		// v_oid+v_pstatus+v_amount+v_moneytype+key
		StringBuffer result = new StringBuffer();
		result.append(v_oid);
		result.append(v_pstatus);
		result.append(v_amount);
		result.append(v_moneytype);
		result.append(BusinessConstants.ONLINE_PAY_CHINABANK_MD5KEY);
		return result.toString();
	}
}
