package com.dxjr.portal.member.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:在线人数记录查询条件<br />
 * </p>
 * 
 * @title OnlineCounterCnd.java
 * @package com.dxjr.portal.member.vo
 * @author justin.xu
 * @version 0.1 2014年4月19日
 */
public class OnlineCounterCnd implements Serializable {

	private static final long serialVersionUID = -8005802218725609268L;

	/** 主键 */
	private Integer id;

	/** 用户ID */
	private Integer userId;

	/** 用户登录名 */
	private String username;

	/** SESSIONID */
	private String sessionid;

	/** 前台还是后台 :0表示前台;1表示后台 */
	private String system;

	/** 国家或省份 */
	private String province;

	/** 城市 */
	private String city;

	/** 区域 */
	private String area;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

}