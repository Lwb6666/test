package com.dxjr.portal.investment.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangQianJin
 * @title 渠道绑定实体
 * @date 2016年5月27日
 */
public class ChannelBindingInvestVo implements Serializable{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -9191759436795884363L;
	
	/** 编号 */
	private Integer id;
	/** 用户ID */
	private Integer userId;
	/** 渠道用户ID */
	private String channelUid;
	/** 渠道来源 */
	private Integer source;
	/** 绑定状态（0：失败 1：成功） */
	private Integer status;
	/** 创建时间 */
	private Date addtime;
	/** 修改时间 */
	private Date updatetime;
	/** 用户名 */
	private String username;
	/** 真是姓名 */
	private String realName;
	/** 银行卡号 */
	private String bankCardNo;
	/** 银行编码 */
	private String bankCode;
	/** 银行名称 */
	private String bankName;
	/** 身份证号 */
	private String idCardNo;
	/** 电话 */
	private String phone;
	/** 来源：wdtx */
	private String fromly;
	/** 用户登录的token */
	private String token;
	/** 新老用户判断 0：新用户 ；1：老用户  */
	private String userStatus;
	
	private String secret;//平台密串
	
	
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getFromly() {
		return fromly;
	}
	public void setFromly(String fromly) {
		this.fromly = fromly;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getChannelUid() {
		return channelUid;
	}
	public void setChannelUid(String channelUid) {
		this.channelUid = channelUid;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
}
