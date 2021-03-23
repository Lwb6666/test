package com.dxjr.portal.lianlian.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:银行卡卡BIN查询API接口（商户提交参数)<br />
 * </p>
 * 
 * @title LlWapBankcardRequest.java
 * @package com.dxjr.portal.lianlian.vo
 * @author justin.xu
 * @version 0.1 2015年3月25日
 */
public class LlWapBankcardRequest extends LlBaseRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 商户编号是在连支付平台上开 设的商户号码，为 18 位数字
	 */
	private String oid_partner;
	/**
	 * 银行卡号
	 */
	private String card_no;

	public String getOid_partner() {
		return oid_partner;
	}

	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
}
