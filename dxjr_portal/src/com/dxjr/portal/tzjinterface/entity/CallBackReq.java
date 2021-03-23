package com.dxjr.portal.tzjinterface.entity;

import java.util.Date;

public class CallBackReq {

	// 投之家用户名
	private String username;
	
	// 平台用户名
	private String usernamep;
	
	// 平台注册时间
	private Date registerAt;
	
	// 绑定投之家时间
    private Date bindAt;
	
    //0:新用户 1:表示平台已有用户
	private Integer bindType;
	
	//标签
	private String[] tags;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsernamep() {
		return usernamep;
	}

	public void setUsernamep(String usernamep) {
		this.usernamep = usernamep;
	}

	public Date getRegisterAt() {
		return registerAt;
	}

	public void setRegisterAt(Date registerAt) {
		this.registerAt = registerAt;
	}

	public Date getBindAt() {
		return bindAt;
	}

	public void setBindAt(Date bindAt) {
		this.bindAt = bindAt;
	}

	public Integer getBindType() {
		return bindType;
	}

	public void setBindType(Integer bindType) {
		this.bindType = bindType;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}
	
	
}
