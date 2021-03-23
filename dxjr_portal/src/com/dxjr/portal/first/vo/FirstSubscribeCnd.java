package com.dxjr.portal.first.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.dxjr.common.page.BaseCnd;

/**
 * <p>
 * Description:直通车转让认购查询条件封装类<br />
 * </p>
 * 
 * @title FirstSubscribeCnd.java
 * @package com.dxjr.portal.first.vo
 * @author 朱泳霖
 * @version 0.1 2015年3月20日
 */
public class FirstSubscribeCnd extends BaseCnd implements Serializable {
	private static final long serialVersionUID = 8387186801624482411L;

	/**
	 * 直通车转让ID
	 */
	private Integer transferId;

	/**
	 * 平台来源
	 */
	private Integer platform;

	/**
	 * 转让价格
	 */
	private BigDecimal accountReal;

	/**
	 * 交易密码
	 */
	private String payPassword;

	/**
	 * 认购密码
	 */
	private String bidPassword;

	/**
	 * 验证码
	 */
	private String checkCode;

	/**
	 * IP地址
	 */
	private String addIp;

	/** 是否使用活期宝金额 0：不使用，1：使用 */
	private String isUseCurMoney;

	public Integer getTransferId() {
		return transferId;
	}

	public void setTransferId(Integer transferId) {
		this.transferId = transferId;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public BigDecimal getAccountReal() {
		return accountReal;
	}

	public void setAccountReal(BigDecimal accountReal) {
		this.accountReal = accountReal;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	public String getBidPassword() {
		return bidPassword;
	}

	public void setBidPassword(String bidPassword) {
		this.bidPassword = bidPassword;
	}

	public String getIsUseCurMoney() {
		return isUseCurMoney;
	}

	public void setIsUseCurMoney(String isUseCurMoney) {
		this.isUseCurMoney = isUseCurMoney;
	}
}
