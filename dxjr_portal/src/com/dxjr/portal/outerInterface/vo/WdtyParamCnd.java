package com.dxjr.portal.outerInterface.vo;

import java.io.Serializable;

/**
 * @author WangQianJin
 * @title 网贷天眼参数查询条件
 * @date 2015年12月15日
 */
public class WdtyParamCnd implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 2113850142083382091L;
	
	/**
	 * 手机号
	 */
	private String mobile;	
	/**
	 * 天眼用户名
	 */
	private String username;
	/**
	 * 请求时间戳
	 */
	private String send_time;		
	/**
	 * 会话id
	 */
	private String sid;
	/**
	 * 标ID
	 */
	private Integer loan_id;
	
	/**
	 * 用户ID
	 */
	private Integer user_id;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 是否注册：0未注册、1已注册
	 */
	private Integer is_reg;
	
	/**
	 * 0一键注册绑定、1老用户绑定
	 */
	private Integer loggingType;
	
	/**
	 * 平台生成的校验标识
	 */
	private String login_key;
	
	/**
	 * 平台生成的校验标识
	 */
	private String userkey;
	
	/** 用户名 */
	private String usernamep;
	/** 密码 */
	private String passwd;
	/** 验证码 */
	private String checkCode;
	/** 是否记住用户名 */
	private String saveid;
	/** 记住的用户名 */
	private String cookieusername;
	
	private Integer loan_type;	
	
	private String signkey;
	private String datas;	

	public String getSignkey() {
		return signkey;
	}

	public void setSignkey(String signkey) {
		this.signkey = signkey;
	}

	public String getDatas() {
		return datas;
	}

	public void setDatas(String datas) {
		this.datas = datas;
	}

	public Integer getLoan_type() {
		return loan_type;
	}

	public void setLoan_type(Integer loan_type) {
		this.loan_type = loan_type;
	}

	public String getUsernamep() {
		return usernamep;
	}

	public void setUsernamep(String usernamep) {
		this.usernamep = usernamep;
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
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public Integer getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(Integer loan_id) {
		this.loan_id = loan_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIs_reg() {
		return is_reg;
	}
	public void setIs_reg(Integer is_reg) {
		this.is_reg = is_reg;
	}
	public Integer getLoggingType() {
		return loggingType;
	}
	public void setLoggingType(Integer loggingType) {
		this.loggingType = loggingType;
	}
	public String getLogin_key() {
		return login_key;
	}
	public void setLogin_key(String login_key) {
		this.login_key = login_key;
	}
	public String getUserkey() {
		return userkey;
	}
	public void setUserkey(String userkey) {
		this.userkey = userkey;
	}
	
	
}
