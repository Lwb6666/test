package com.dxjr.wx.entry.bind.vo;

/**
 * 绑定的参数类
 * <p>
 * Description:
 * </p>
 * 
 * @title LoginVo.java
 * @package com.dxjr.wx.login.vo
 * @author Wang bo
 * @version 0.1 2014年10月18日
 */
public class BindVo {
	/* 用户名 */
	private String username;
	/* 密码 */
	private String password;
	/* 手机 */
	private String telphone;
	/* 验证码 */
	private String activeCode;
	/* 绑定时间 */
	private Long createTime;
	/* 解绑时间 */
	private Long updateTime;
	/* 微信id */
	private Integer wxId;
	/* 微信openId */
	private String openId;
	private Integer status;
	private Integer fromstatus;
	private Integer userId;

	public Integer getFromstatus() {
		return fromstatus;
	}

	public void setFromstatus(Integer fromstatus) {
		this.fromstatus = fromstatus;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getWxId() {
		return wxId;
	}

	public void setWxId(Integer wxId) {
		this.wxId = wxId;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public BindVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** 用户名 */
	public String getUsername() {
		return username;
	}

	/** 用户名 */
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public BindVo(String username, String passwd, String telphone, String activeCode, Long createTime, Long updateTime, Integer wxId, String openId) {
		super();
		this.username = username;
		this.password = passwd;
		this.telphone = telphone;
		this.activeCode = activeCode;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.wxId = wxId;
		this.openId = openId;
	}

}
