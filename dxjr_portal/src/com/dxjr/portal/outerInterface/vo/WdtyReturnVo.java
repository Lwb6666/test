package com.dxjr.portal.outerInterface.vo;

import java.io.Serializable;

/**
 * @author WangQianJin
 * @title 网贷天眼返回Vo
 * @date 2015年12月15日
 */
public class WdtyReturnVo implements Serializable {
	
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -4766361553076556205L;
	
	/**
	 * 响应码
	 */
	private String code;	
	/**
	 * 响应消息
	 */
	private String message;
	/**
	 * 返回数据
	 */
	private String data;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

}
