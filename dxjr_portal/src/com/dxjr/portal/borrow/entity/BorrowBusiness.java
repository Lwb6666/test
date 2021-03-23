package com.dxjr.portal.borrow.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <p>
 * Description:借款标业务人员关联信息<br />
 * </p>
 * 
 * @title BorrowBusiness.java
 * @package com.dxjr.portal.borrow.entity
 * @author YangShiJin
 * @version 0.1 2015年6月26日
 */
public class BorrowBusiness implements Serializable {
	private static final long serialVersionUID = 8181775780962272784L;

	/**
	 * 主键ID
	 */
	private Integer id;

	/**
	 * 借款标ID
	 */
	private Integer borrowId;

	/**
	 * 用户ID（业务员user_id）
	 */
	private Integer userId;

	/**
	 * 用户名（业务员用户名）
	 */
	private String username;

	/**
	 * 添加时间
	 */
	private Date addtime;

	/**
	 * 添加IP
	 */
	private String addip;

	/**
	 * 平台来源(1：网页 2：微信 3：安卓端 4： IOS端)
	 */
	private Integer platform;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
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

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAddip() {
		return addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}
}
