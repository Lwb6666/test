package com.dxjr.portal.tzjinterface.vo;

import java.io.Serializable;

public class RequesturlLogVo implements Serializable{

	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = 1811375451880912983L;
	/**主键*/
	private Integer id;
	/**用户ID*/
	private Integer userId;
	/**请求时间*/
	private String  addTime;
	/**时间戳*/
	private String ts;
	/**删除标识 删除标识(0：删除 1：未删除)*/
	private String dr;
	/**访问的URL路径*/
	private String urlString;
	/**相对平台来说 ，PUSH:推数据，PULL：拉数据*/
	private String direction;
    /**是否成功标识（0：失败 1：成功）*/
	private Integer isSuccess;
	/**错误日志*/
	private String message;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public String getDr() {
		return dr;
	}
	public void setDr(String dr) {
		this.dr = dr;
	}
	public String getUrlString() {
		return urlString;
	}
	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public Integer getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
