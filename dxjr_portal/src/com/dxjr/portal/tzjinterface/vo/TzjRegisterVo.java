package com.dxjr.portal.tzjinterface.vo;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class TzjRegisterVo implements Serializable {

	
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -4461174352929304573L;
	
	/** 平台英文简称 */
	@JsonProperty("from")
	private String from;
	/** 投之家用户名 */
	@JsonProperty("username")
	private String username;
	/** 平台新生产的用户名  */
	@JsonProperty("usernamep")
	private String usernamep;
	/** 新用户在平台的注册时间（时间戳） */
	@JsonProperty("registerAt")
	private String registerAt;
	/** 区分绑定途径、0代表新用户的注册绑定、1代表平台老用户绑定  */
	@JsonProperty("type")
	private String type;
	/** 解密时间戳  */
	@JsonProperty("timestamp")
	private String timestamp;
	/** 校验MD5值  */
	@JsonProperty("sign")
	private String sign;
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
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
	public String getRegisterAt() {
		return registerAt;
	}
	public void setRegisterAt(String registerAt) {
		this.registerAt = registerAt;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}

}
