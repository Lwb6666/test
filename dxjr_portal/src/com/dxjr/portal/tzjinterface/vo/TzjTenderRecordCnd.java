package com.dxjr.portal.tzjinterface.vo;

import java.io.Serializable;

/**
 * @author WangQianJin
 * @title 投标记录查询条件
 * @date 2015年11月29日
 */
public class TzjTenderRecordCnd implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 6877591680016541270L;
	
	/**初始时间	UNIXTIME时间戳，单位秒。是用来查询投资时间范围段的起始时间*/
	private String startTime;
	/**结束时间	UNIXTIME时间戳，单位秒。是用来查询投资时间范围段的结束时间*/
	private String endTime;	
	/**投之家用户名*/
	private String username;
	/**平台用户名，对应绑定的投之家用户名*/
	private String usernamep;
	/**JSON字符，存储单个或多个标的ID*/
	private String bid;
	/**请求类型，0时传时间，1时传bid*/
	private String type;
	
	/**多个投之家用户名，用逗号进行分割*/
	private String usernameStr;
	/**多个标的ID，用逗号进行分割*/
	private String bidStr; 
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUsernameStr() {
		return usernameStr;
	}
	public void setUsernameStr(String usernameStr) {
		this.usernameStr = usernameStr;
	}
	public String getBidStr() {
		return bidStr;
	}
	public void setBidStr(String bidStr) {
		this.bidStr = bidStr;
	}
	

}
