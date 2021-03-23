package com.dxjr.portal.transfer.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.dxjr.common.page.BaseCnd;

/**
 * <p>
 * Description:认购参数<br />
 * </p>
 * 
 * @title SubscribeTransferBean.java
 * @package com.dxjr.portal.transfer.vo
 * @author chenpeng
 * @version 0.1 2014年10月27日
 */
public class SubscribeTransferVo extends BaseCnd implements Serializable {

	private static final long serialVersionUID = 8478837565072960747L;
	/**
	 * 认购金额
	 */
	private BigDecimal tenderMoney;
	/**
	 * 转让密码
	 */
	private String bidPassword;
	/**
	 * 交易密码
	 */
	private String payPassword;
	/**
	 * 验证码
	 */
	private String checkCode;
	/**
	 * 债权转让id
	 */
	private Integer transferid;
	/**
	 * 操作来源
	 */
	private Integer platform;
	/**
	 * 用户累计购买金额
	 */
	private BigDecimal sumAccount;

	/** 是否使用活期宝金额 0：不使用，1：使用 */
	private String isUseCurMoney;

	/**短信验证码****/
	private String mobileCode;


	private BigDecimal useMoney;


	public BigDecimal getTenderMoney() {
		return tenderMoney;
	}

	public void setTenderMoney(BigDecimal tenderMoney) {
		this.tenderMoney = tenderMoney;
	}

	public String getBidPassword() {
		return bidPassword;
	}

	public void setBidPassword(String bidPassword) {
		this.bidPassword = bidPassword;
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

	public Integer getTransferid() {
		return transferid;
	}

	public void setTransferid(Integer transferid) {
		this.transferid = transferid;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public BigDecimal getSumAccount() {
		return sumAccount;
	}

	public void setSumAccount(BigDecimal sumAccount) {
		this.sumAccount = sumAccount;
	}

	public String getIsUseCurMoney() {
		return isUseCurMoney;
	}

	public void setIsUseCurMoney(String isUseCurMoney) {
		this.isUseCurMoney = isUseCurMoney;
	}


	public String getMobileCode() {
		return mobileCode;
	}

	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}


	public BigDecimal getUseMoney() {
		return useMoney;
	}

	public void setUseMoney(BigDecimal useMoney) {
		this.useMoney = useMoney;
	}
}
