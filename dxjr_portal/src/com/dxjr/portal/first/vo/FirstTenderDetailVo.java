package com.dxjr.portal.first.vo;

import java.io.Serializable;

import com.dxjr.base.entity.FirstTenderDetail;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.StrinUtils;

/**
 * <p>
 * Description:优先投标计划开通明细<br />
 * </p>
 * 
 * @title FirstTenderDetailVo.java
 * @package com.dxjr.portal.first.vo
 * @author justin.xu
 * @version 0.1 2014年7月15日
 */
public class FirstTenderDetailVo extends FirstTenderDetail implements Serializable {
	private static final long serialVersionUID = -191815169850848319L;
	/**
	 * 投标人
	 */
	private String username;

	/** 页面显示用到的转换字符 begin */
	/** 投标直通车标题 */
	private String firstBorrowName;
	private String accountStr;
	private String addtimeStr;
	private String userEmail;
	private String name; // 优先计划标题
	private String planAccount; // 计划金额
	
	private String userNameSecret;  //隐藏用户名
	private String userNameEncrypt;  //加密用户名

	/** 页面显示用到的转换字符 end */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccountStr() {
		if (null != super.getAccount()) {
			return BusinessConstants.numberDf.format(super.getAccount());
		}
		return accountStr;
	}

	public void setAccountStr(String accountStr) {
		this.accountStr = accountStr;
	}

	public String getAddtimeStr() {
		if (null != super.getAddtime()) {
			return DateUtils.format(super.getAddtime(), DateUtils.YMD_HMS);
		}
		return addtimeStr;
	}

	public void setAddtimeStr(String addtimeStr) {
		this.addtimeStr = addtimeStr;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlanAccount() {
		return planAccount;
	}

	public void setPlanAccount(String planAccount) {
		this.planAccount = planAccount;
	}

	public String getFirstBorrowName() {
		return firstBorrowName;
	}

	public void setFirstBorrowName(String firstBorrowName) {
		this.firstBorrowName = firstBorrowName;
	}
	
	public String getUserNameSecret() {
		userNameSecret = this.getUsername();	
			userNameSecret = StrinUtils.stringSecretTo(userNameSecret);
		return userNameSecret;
	}
	public String getUserNameEncrypt() {
		userNameEncrypt = this.getUsername();
		userNameEncrypt = StrinUtils.stringEncryptEn(userNameEncrypt);
		return userNameEncrypt;
	}
}
