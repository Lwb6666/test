package com.dxjr.portal.tzjinterface.exception;

/**
 * @author WangQianJin
 * @title 投之家接口异常
 * @date 2016年3月23日
 */
public class PlatformException extends Exception {
	private static final long serialVersionUID = -2125031025287319917L;

	private int code;
	
	public PlatformException(int code, String message) {
		super(message);
		this.setCode(code);
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
