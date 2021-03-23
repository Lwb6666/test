package com.dxjr.portal.autoInvestConfig.vo;

import java.io.Serializable;

import com.dxjr.common.page.BaseCnd;

public class AutoInvestConfigRecordCnd extends BaseCnd implements Serializable {
	private static final long serialVersionUID = 7371537867372259682L;

	/** 
	 * 用户Id 
	 */
	private Integer user_id;

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
}
