package com.dxjr.portal.rrlinterface.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangQianJin
 * @title 人人利用户绑定实体
 * @date 2016年4月23日
 */
public class RrlMemberBinding implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 4076484763385528348L;
	
	//编号
	private Integer id;
	//手机号
	private String phone;
	//人人利用户名
	private String username;
	//平台用户名
	private String usernamep;
	//商务编号
	private String cust_id;
	//加密方式（MD5）
	private String sign_type;
	//加密验证参数
	private String sign;
	//来源：mobile或者pc
	private String cfrom;
	//用户id
	private Integer user_id;
	//0无效、1正常
	private Integer status;
	//0一键注册绑定、1老用户绑定
	private Integer loggingType;
	//注册后返回的用户唯一值
	private String cust_key;
	//创建时间
	private Date addtime;
	//修改时间
	private Date updatetime;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getCfrom() {
		return cfrom;
	}
	public void setCfrom(String cfrom) {
		this.cfrom = cfrom;
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
	public String getCust_key() {
		return cust_key;
	}
	public void setCust_key(String cust_key) {
		this.cust_key = cust_key;
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
