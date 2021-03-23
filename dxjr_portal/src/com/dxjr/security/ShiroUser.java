package com.dxjr.security;

import java.io.Serializable;

import com.dxjr.utils.StrinUtils;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
 */
public class ShiroUser implements Serializable {

	public ShiroUser() {
	}

	private Integer userId;
	private String userIdMD5;
	private String userName;
	private String headImg;
	private Integer isFinancialUser;
	/** 1：官网 2、微信 */
	private Integer platform;
	
	private String userNameEncrypt;  //加密用户名

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserIdMD5() {
		return userIdMD5;
	}

	public void setUserIdMD5(String userIdMD5) {
		this.userIdMD5 = userIdMD5;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public Integer getIsFinancialUser() {
		return isFinancialUser;
	}

	public void setIsFinancialUser(Integer isFinancialUser) {
		this.isFinancialUser = isFinancialUser;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getSsoTicket() {
		return new StringBuilder(10).append(this.userIdMD5).append(TicketCryptor.SPLIT).append(this.platform).toString();
	}
	
	public String getUserNameEncrypt() {
		userNameEncrypt = this.getUserName();
		userNameEncrypt = StrinUtils.stringEncryptEn(userNameEncrypt);
		return userNameEncrypt;
	}
	
}
