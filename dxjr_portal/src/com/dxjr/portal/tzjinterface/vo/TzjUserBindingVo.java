package com.dxjr.portal.tzjinterface.vo;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author WangQianJin
 * @title 用户绑定关系
 * @date 2015年11月30日
 */
public class TzjUserBindingVo implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -2994005507321031657L;
	
	/** 投之家用户名 */
	@JsonProperty("username")
	private String username;
	/** 平台绑定用户名  */
	@JsonProperty("usernamep")
	private String usernamep;
	/** 投之家用户与平台绑定时间，时间戳 */
	@JsonProperty("regTime")
	private String regTime;
	/** 绑定渠道 0代表通过一键注册绑定  1代表通过老用户关联绑定  */
	@JsonProperty("type")
	private String type;
	
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
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	

}
