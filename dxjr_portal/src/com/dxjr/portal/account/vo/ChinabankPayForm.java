package com.dxjr.portal.account.vo;

/**
 * <p>
 * Description:网银在线提交充值数据<br />
 * </p>
 * 
 * @title ChinabankPayForm.java
 * @package com.dxjr.portal.account.vo
 * @author justin.xu
 * @version 0.1 2014年6月11日
 */
public class ChinabankPayForm {
	/** MD5值 */
	private String v_md5info;
	/** 开通的商户号 */
	private String v_mid;
	/** 订单ID */
	private String v_oid;
	/** 充值金额 */
	private String v_amount;
	/** 币种 */
	private String v_moneytype;
	/** 充值成功之后,系统显示的返回商户链接地址 */
	private String v_url;
	/** 银行编号 */
	private String pmode_id;
	/** 回调地址 */
	private String remark2;

	public String getV_md5info() {
		return v_md5info;
	}

	public void setV_md5info(String v_md5info) {
		this.v_md5info = v_md5info;
	}

	public String getV_mid() {
		return v_mid;
	}

	public void setV_mid(String v_mid) {
		this.v_mid = v_mid;
	}

	public String getV_oid() {
		return v_oid;
	}

	public void setV_oid(String v_oid) {
		this.v_oid = v_oid;
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

	public String getV_url() {
		return v_url;
	}

	public void setV_url(String v_url) {
		this.v_url = v_url;
	}

	public String getPmode_id() {
		return pmode_id;
	}

	public void setPmode_id(String pmode_id) {
		this.pmode_id = pmode_id;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

}
