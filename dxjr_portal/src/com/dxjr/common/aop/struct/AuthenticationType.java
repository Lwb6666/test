package com.dxjr.common.aop.struct;

/**
 * 
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title AuthenticationType.java
 * @package com.dxjr.common.aop.processor 
 * @author zhaowei
 * @version 0.1 2015年11月19日
 */
public enum AuthenticationType {
	
	/**
	 * 认证失败
	 */
	AUTHENTICATION_FAILED("10010"),
	/**
	 * 认证不支持，建议Controller方法中加入HttpServletRequest对象
	 */
	AUTHENTICATION_NOT_SUPPORTED("10011"),
	/**
	 * 缓存数据错误
	 */
	AUTHENTICATION_CACHE_ERROR("10012");
	
	private AuthenticationType(String value) {
		this.value = value;
	}
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
