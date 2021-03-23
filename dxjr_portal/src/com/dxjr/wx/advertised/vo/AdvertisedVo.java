package com.dxjr.wx.advertised.vo;

import java.io.Serializable;

public class AdvertisedVo implements Serializable {

	private static final long serialVersionUID = -4010048561890513448L;
	/** 邮箱 */
	private String email;
	/** 真实姓名 */
	private String realname;
	/** 身份证号码 */
	private String idcard;
	/** 手机号 */
	private String mobile;
	/** 短信验证码 */
	private String activeCode;
	/** 登录旧密码 */
	private String oldPwd;
	/** 登录新密码 */
	private String newPwd;
	/** 确认新密码 */
	private String reNewPwd;
	/** 交易密码 */
	private String paypassword;
	/** 重复提交控制 */
	private String token;

	/** 平台来源 */
	private Integer platform;
	/** source来源 */
	private Integer source;
	/** 用户名 */
	private String username;

	private String bankCode;// 银行编号

	private String cardNum;// 卡号
	
	public Integer getInviterid() {
		return inviterid;
	}

	public void setInviterid(Integer inviterid) {
		this.inviterid = inviterid;
	}

	private Integer inviterid;

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
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

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getReNewPwd() {
		return reNewPwd;
	}

	public void setReNewPwd(String reNewPwd) {
		this.reNewPwd = reNewPwd;
	}

	public String getPaypassword() {
		return paypassword;
	}

	public void setPaypassword(String paypassword) {
		this.paypassword = paypassword;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}
}
