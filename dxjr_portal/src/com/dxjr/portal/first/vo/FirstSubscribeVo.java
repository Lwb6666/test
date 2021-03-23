package com.dxjr.portal.first.vo;

import java.io.Serializable;

import com.dxjr.base.entity.FirstSubscribe;
import com.dxjr.utils.StrinUtils;

/**
 * <p>
 * Description:直通车转让认购Vo<br />
 * </p>
 * 
 * @title FirstSubscribeVo.java
 * @package com.dxjr.portal.first.vo
 * @author 朱泳霖
 * @version 0.1 2015年3月19日
 */
public class FirstSubscribeVo extends FirstSubscribe implements Serializable {
	private static final long serialVersionUID = -191815169850848319L;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 隐藏用户名
	 */
	private String userNameSecret;

	/**
	 * 加密用户名
	 */
	private String userNameEncrypt;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNameSecret() {
		userNameSecret = this.getUserName();
		userNameSecret = StrinUtils.stringSecretTo(userNameSecret);
		return userNameSecret;
	}

	public String getUserNameEncrypt() {
		userNameEncrypt = this.getUserName();
		userNameEncrypt = StrinUtils.stringEncryptEn(userNameEncrypt);
		return userNameEncrypt;
	}

}
