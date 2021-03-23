package com.dxjr.portal.curAccount.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * Description:活期宝加入发起参数<br />
 * </p>
 * 
 * @title CurInLaunchVo.java
 * @package com.dxjr.portal.curAccount.vo
 * @author 朱泳霖
 * @version 0.1 2015年5月5日
 */
public class CurInLaunchVo implements Serializable {

	private static final long serialVersionUID = -6903226903535940857L;

	/**
	 * 加入金额
	 */
	private BigDecimal tenderMoney;

	/**
	 * 交易密码
	 */
	private String payPassword;

	/**
	 * 验证码
	 */
	private String checkCode;
	
	/**
	 * 用户ID
	 */
	private Integer userId;
	
	/**
	 * ip地址
	 */
	private String addIp;
	
	/**
	 * 平台来源
	 */
	private Integer platform;

	public BigDecimal getTenderMoney() {
		return tenderMoney;
	}

	public void setTenderMoney(BigDecimal tenderMoney) {
		this.tenderMoney = tenderMoney;
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
}