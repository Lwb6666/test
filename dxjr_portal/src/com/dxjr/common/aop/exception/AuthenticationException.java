package com.dxjr.common.aop.exception;

import com.dxjr.common.aop.struct.AuthenticationType;

/**
 * 
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title AuthenticationException.java
 * @package com.dxjr.common.aop.processor 
 * @author zhaowei
 * @version 0.1 2015年11月19日
 */
public class AuthenticationException extends Exception {
	
	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = 5353895551899227813L;
	private String code;
	private String message;
	
	public AuthenticationException(AuthenticationType type, String message) {
		this.setCode(type.getValue());
		this.setMessage(message);
	}

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

}
