package com.dxjr.portal.fix.vo;

import java.io.Serializable;
import java.util.Date;

import com.dxjr.utils.StrinUtils;

/**
 * @author WangQianJin
 * @title 活动Vo
 * @date 2016年1月27日
 */
public class FixActivityVo implements Serializable {

	/**
	 * 序列化ID
	 */	
	private static final long serialVersionUID = -8493697070272654872L;
	
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 注册时间
	 */
	private Date regTime;
	
	/**
	 * 用户名
	 */
	private String username;	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 加密用户名
	 */
	private String userNameEncrypt;
	
	public String getUserNameEncrypt() {
		userNameEncrypt = StrinUtils.stringEncryptEn(username);
		return userNameEncrypt;
	}
	
	public String getMobile() {
		return StrinUtils.stringMobileSecretTo(mobile);
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	
	
	

}
