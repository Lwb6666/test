package com.dxjr.portal.outerInterface.vo;

import java.io.Serializable;

/**
 * @author WangQianJin
 * @title 网贷天眼返回Vo
 * @date 2015年12月15日
 */
public class WdtyResultVo implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -1998589611748052731L;
	
	/**
	 * 手机号
	 */
	private String mobile;	
	/**
	 * 平台用户名
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
	 * 手机号
	 */
	private String loan_id;
	/**
	 * 0未注册、1已注册
	 */
	private Integer is_reg;
	/**
	 * 用户绑定标识
	 */
	private String userkey;
	/**
	 * 平台生成的校验标识
	 */
	private String login_key;
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
	public String getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(String loan_id) {
		this.loan_id = loan_id;
	}
	public Integer getIs_reg() {
		return is_reg;
	}
	public void setIs_reg(Integer is_reg) {
		this.is_reg = is_reg;
	}
	public String getUserkey() {
		return userkey;
	}
	public void setUserkey(String userkey) {
		this.userkey = userkey;
	}
	public String getLogin_key() {
		return login_key;
	}
	public void setLogin_key(String login_key) {
		this.login_key = login_key;
	}
	
	

}
