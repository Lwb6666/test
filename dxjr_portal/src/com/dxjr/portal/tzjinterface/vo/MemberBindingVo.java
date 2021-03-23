package com.dxjr.portal.tzjinterface.vo;

import java.io.Serializable;

public class MemberBindingVo implements Serializable{

	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = -7007586654718763582L;
	/**主键ID*/
	private Integer id;
	/**服务名 默认为：bindreport*/
	private String service;
	/**平台名称 默认设置为：顶玺金融*/
	private String requestFrom;
	/**订单号*/
	private String oid;
	/**投之家用户名*/
	private String userName;
	/**在平台注册的用户名*/
	private String userNamep;
	/**注册时间*/
	private String regTime;
	/**数据新增时间*/
	private String addTime;
	/**时间戳*/
	private String ts;
	/**删除标识(0：删除 1：未删除)*/
	private String dr;
	/**登陆类型 0：使用投之家账号新注册的用户 1：平台已有用户*/
	private String loggingType;
	/**是否成功标识（-1:初始状态 0：失败 1：成功）*/
	private Integer isSuccess;
	/**处理信息（结果）*/
	private String message;
	/**数字签名*/
	private String sign;
	/**平台用户ID*/
	private Integer userId;
	/**身份证名称*/
	private String realName;
	/**身份证号码*/
	private String cardNo;
	/**邮箱*/
	private String email;
	/**手机*/
	private String telephone;

	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getRequestFrom() {
		return requestFrom;
	}
	public void setRequestFrom(String requestFrom) {
		this.requestFrom = requestFrom;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserNamep() {
		return userNamep;
	}
	public void setUserNamep(String userNamep) {
		this.userNamep = userNamep;
	}
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public String getDr() {
		return dr;
	}
	public void setDr(String dr) {
		this.dr = dr;
	}
	public String getLoggingType() {
		return loggingType;
	}
	public void setLoggingType(String loggingType) {
		this.loggingType = loggingType;
	}
	public Integer getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
