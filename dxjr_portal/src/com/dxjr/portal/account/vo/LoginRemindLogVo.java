package com.dxjr.portal.account.vo;

import java.io.Serializable;

public class LoginRemindLogVo  implements Serializable{

	private static final long serialVersionUID = -7469227693776781732L;
	private Integer userId;//用户id
	private String message; //提示信息
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
