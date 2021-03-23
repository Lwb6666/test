package com.dxjr.portal.first.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * Description:直通车转让发起参数<br />
 * </p>
 * 
 * @title FirstTransferLaunchCnd.java
 * @package com.dxjr.portal.first.vo
 * @author justin.xu
 * @version 0.1 2015年3月15日
 */
public class FirstTransferLaunchVo implements Serializable {

	private static final long serialVersionUID = -1272534286573229542L;
	/**
	 * 直通车最终认购记录id
	 */
	private Integer firstTenderRealId;
	/**
	 * 转让用户id
	 */
	private Integer userId;
	/**
	 * 奖励金额
	 */
	private BigDecimal awards;
	/**
	 * 有效时间(天)
	 */
	private Integer validTime;
	/**
	 * 投标密码
	 */
	private String bidPassword;
	/**
	 * 验证码
	 */
	private String checkCode;
	/**
	 * session中的验证码
	 */
	private String sessionCheckCode;
	/**
	 * 平台来源
	 */
	private Integer platform;
	/**
	 * 添加ip
	 */
	private String addip;
	/**
	 * 用户名
	 */
	private String userName;

	public Integer getFirstTenderRealId() {
		return firstTenderRealId;
	}

	public void setFirstTenderRealId(Integer firstTenderRealId) {
		this.firstTenderRealId = firstTenderRealId;
	}

	public BigDecimal getAwards() {
		return awards;
	}

	public void setAwards(BigDecimal awards) {
		this.awards = awards;
	}

	public Integer getValidTime() {
		return validTime;
	}

	public void setValidTime(Integer validTime) {
		this.validTime = validTime;
	}

	public String getBidPassword() {
		return bidPassword;
	}

	public void setBidPassword(String bidPassword) {
		this.bidPassword = bidPassword;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getSessionCheckCode() {
		return sessionCheckCode;
	}

	public void setSessionCheckCode(String sessionCheckCode) {
		this.sessionCheckCode = sessionCheckCode;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getAddip() {
		return addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}