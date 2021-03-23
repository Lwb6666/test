package com.dxjr.portal.lianlian.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:查询银行卡信息返回的业务数据<br />
 * </p>
 * 
 * @title LlWapBankcardInfo.java
 * @package com.dxjr.portal.lianlian.vo
 * @author justin.xu
 * @version 0.1 2015年4月8日
 */
public class LlWapBankcardInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 所属银行编号
	 */
	private String bank_code;
	/**
	 * 所属银行名称
	 */
	private String bank_name;
	/**
	 * 返回信息
	 */
	private String resultMsg;

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

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

}
