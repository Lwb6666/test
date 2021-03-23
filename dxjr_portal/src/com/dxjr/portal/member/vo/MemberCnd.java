package com.dxjr.portal.member.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:会员类查询条件<br />
 * </p>
 * 
 * @title MemberCnd.java
 * @package com.dxjr.member.vo
 * @author justin.xu
 * @version 0.1 2014年4月15日
 */
public class MemberCnd implements Serializable {
	private static final long serialVersionUID = -5890980057423538506L;
	
	/***
	 * userId
	 * 
	 */
	private Integer userId;
	
	/**
	 * 主键ID
	 */
	private Integer id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * email
	 */
	private String email;
	/**
	 * 登录开始时间
	 */
	private String lastlogintimeBegin;
	/**
	 * 登录结束时间
	 */
	private String lastlogintimeEnd;
	/**
	 * 用户名MD5值
	 */
	private String userIdMD5;

	public String getUserIdMD5() {
		return userIdMD5;
	}

	public void setUserIdMD5(String userIdMD5) {
		this.userIdMD5 = userIdMD5;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastlogintimeBegin() {
		return lastlogintimeBegin;
	}

	public void setLastlogintimeBegin(String lastlogintimeBegin) {
		this.lastlogintimeBegin = lastlogintimeBegin;
	}

	public String getLastlogintimeEnd() {
		return lastlogintimeEnd;
	}

	public void setLastlogintimeEnd(String lastlogintimeEnd) {
		this.lastlogintimeEnd = lastlogintimeEnd;
	}

	/**
	 * @return userId : return the property userId.
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId : set the property userId.
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	

}