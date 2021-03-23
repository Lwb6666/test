package com.dxjr.portal.investment.vo;

import java.io.Serializable;

public class WdtxRtInfo implements Serializable{

	/**
	 * @author JingBinbin
     * @title 网贷天下用户请求返回实体
     * @date 2016年6月30日
	 */
	private static final long serialVersionUID = -7576172802454851083L;

	private boolean success; //请求是否成功
	
	private int code; //1:表示成功 错误代码 如用户名已存在 请返回1001
	
	private String msg; //错误的提示信息
	
	private String username;//用户帐号
	
	private String pfUserId;//平台用户ID
	
	private String token;//用于用户登录的token
	
	private String resultParams;//由我们上传的加密串再返回给我们
	
	private String wdtxUserId;//网贷天下用户Id
	
	private String secret; //平台密串
	
	public String getWdtxUserId() {
		return wdtxUserId;
	}

	public void setWdtxUserId(String wdtxUserId) {
		this.wdtxUserId = wdtxUserId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPfUserId() {
		return pfUserId;
	}

	public void setPfUserId(String pfUserId) {
		this.pfUserId = pfUserId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getResultParams() {
		return resultParams;
	}

	public void setResultParams(String resultParams) {
		this.resultParams = resultParams;
	}
	
}
