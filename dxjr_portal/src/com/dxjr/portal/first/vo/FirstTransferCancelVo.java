package com.dxjr.portal.first.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:我的账户-直通车转让取消转让参数封装<br />
 * </p>
 * 
 * @title FirstTransferCancelVo.java
 * @package com.dxjr.portal.first.vo
 * @author justin.xu
 * @version 0.1 2015年3月20日
 */
public class FirstTransferCancelVo implements Serializable {
	private static final long serialVersionUID = -191815169850848319L;
	/**
	 * 直通车转让id
	 */
	private Integer firstTransferId;
	/**
	 * 转让人id
	 */
	private Integer userId;
	/**
	 * ip
	 */
	private String addIp;
	/**
	 * 平台来源
	 */
	private Integer platform;
	/**
	 * 用户名
	 */
	private String userName;

	public Integer getFirstTransferId() {
		return firstTransferId;
	}

	public void setFirstTransferId(Integer firstTransferId) {
		this.firstTransferId = firstTransferId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
