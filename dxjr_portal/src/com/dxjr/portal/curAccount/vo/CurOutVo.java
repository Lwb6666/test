package com.dxjr.portal.curAccount.vo;

import java.io.Serializable;

import com.dxjr.portal.curAccount.entity.CurOut;

public class CurOutVo extends CurOut implements Serializable {

	private static final long serialVersionUID = -6903226903535940857L;

	/** 支付密码 */
	private String paypassword;

	public String getPaypassword() {
		return paypassword;
	}

	public void setPaypassword(String paypassword) {
		this.paypassword = paypassword;
	}
}