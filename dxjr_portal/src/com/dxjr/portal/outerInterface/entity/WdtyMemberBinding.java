package com.dxjr.portal.outerInterface.entity;

import java.util.Date;

/**
 * @author WangQianJin
 * @title 网贷天眼实体类
 * @date 2015年12月16日
 */
public class WdtyMemberBinding {

	/**
	 * 编号
	 */
	private Integer id;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 网贷天眼用户名
	 */
	private String username;
	/**
	 * 平台用户名
	 */
	private String usernamep;
	/**
	 * 请求时间戳
	 */
	private String send_time;
	/**
	 * 会话id
	 */
	private String sid;
	/**
	 * 平台标的id
	 */
	private Integer loan_id;
	/**
	 * 平台标的类型：1普通标、2债转标、3、定期宝
	 */
	private Integer loan_type;
	/**
	 * 是否注册：0未注册、1已注册
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
	/**
	 * 创建时间
	 */
	private Date addtime;
	/**
	 * 用户ID
	 */
	private Integer user_id;
	/**
	 * 状态：0无效、1正常
	 */
	private Integer status;
	/**
	 * 类型：0一键注册绑定、1老用户绑定
	 */
	private Integer loggingType;
	
	/**
	 * 是否实名认证：0未认证、1已认证
	 */
	private Integer is_realname;
	/**
	 * 是否绑定银行卡：0未绑定、1已绑定
	 */
	private Integer is_bank;
	
	public Integer getIs_realname() {
		return is_realname;
	}
	public void setIs_realname(Integer is_realname) {
		this.is_realname = is_realname;
	}
	public Integer getIs_bank() {
		return is_bank;
	}
	public void setIs_bank(Integer is_bank) {
		this.is_bank = is_bank;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getUsernamep() {
		return usernamep;
	}
	public void setUsernamep(String usernamep) {
		this.usernamep = usernamep;
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
	public Integer getLoan_type() {
		return loan_type;
	}
	public void setLoan_type(Integer loan_type) {
		this.loan_type = loan_type;
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
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
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
	public Integer getLoggingType() {
		return loggingType;
	}
	public void setLoggingType(Integer loggingType) {
		this.loggingType = loggingType;
	}	
	
}
