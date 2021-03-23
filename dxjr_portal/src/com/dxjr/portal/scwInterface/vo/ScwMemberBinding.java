package com.dxjr.portal.scwInterface.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangQianJin
 * @title 生菜网用户绑定实体
 * @date 2016年5月5日
 */
public class ScwMemberBinding implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 6635757304269935565L;
	
	//编号
	private Integer id;
	//手机号
	private String phone;
	//生菜网用户名
	private String username;
	//平台用户名
	private String usernamep;
	//生菜来源标识
	private String fr;
	//生菜用户密码
	private String userpwd;
	//用户id
	private Integer user_id;
	//0无效、1正常
	private Integer status;
	//0一键注册绑定、1老用户绑定
	private Integer loggingType;
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
	public String getFr() {
		return fr;
	}
	public void setFr(String fr) {
		this.fr = fr;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
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
