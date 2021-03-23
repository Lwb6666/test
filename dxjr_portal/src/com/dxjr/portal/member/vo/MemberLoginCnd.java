package com.dxjr.portal.member.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:登录参数Cnd<br />
 * </p>
 * 
 * @title MemberLoginCnd.java
 * @package com.dxjr.portal.member.vo
 * @author justin.xu
 * @version 0.1 2014年5月7日
 */
public class MemberLoginCnd implements Serializable {
	private static final long serialVersionUID = -1748725774856611015L;

	/** 用户名 */
	private String username;
	/** 密码 */
	private String passwd;
	/** 验证码 */
	private String checkCode;
	/** 是否记住用户名 */
	private String saveid;
	/** 记住的用户名 */
	private String cookieusername;
	
	/** 登陆成功后跳转的URL **/
	
	private String backUrl;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getSaveid() {
		return saveid;
	}

	public void setSaveid(String saveid) {
		this.saveid = saveid;
	}

	public String getCookieusername() {
		return cookieusername;
	}

	public void setCookieusername(String cookieusername) {
		this.cookieusername = cookieusername;
	}


	/**
	 * @return the backUrl
	 */
	public String getBackUrl() {
		return backUrl;
	}

	/**
	 * @param backUrl the backUrl to set
	 */
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
}
