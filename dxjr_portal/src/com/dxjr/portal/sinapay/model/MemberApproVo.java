package com.dxjr.portal.sinapay.model;

import java.io.Serializable;

/**
 * <p>
 * Description:会员所有认证类Vo<br />
 * </p>
 * 
 * @title MemberApproVo.java
 * @package com.dxjr.portal.member.vo
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
public class MemberApproVo implements Serializable {
	private static final long serialVersionUID = -5890980057423538506L;
	/** 主键ID */
	private Integer id;
	/** 用户名 */
	private String username;
	/** 是否通过实名认证 【-1：审核不通过，0：等待审核，1：审核通过】 */
	private Integer namePassed;
	/** 是否通手机验证【0：未通过；1：通过】 */
	private Integer mobilePassed;
	/** 是否通过邮箱验证【0：未通过，1：通过】 */
	private Integer emailChecked;
	/** 是否通过VIP审核【0:初始状态；1：审核通过；-1：审核不通过】 */
	private Integer vipPassed;
	/** 是否有银行名称 */
	private String bankName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getNamePassed() {
		return namePassed;
	}

	public void setNamePassed(Integer namePassed) {
		this.namePassed = namePassed;
	}

	public Integer getMobilePassed() {
		return mobilePassed;
	}

	public void setMobilePassed(Integer mobilePassed) {
		this.mobilePassed = mobilePassed;
	}

	public Integer getEmailChecked() {
		return emailChecked;
	}

	public void setEmailChecked(Integer emailChecked) {
		this.emailChecked = emailChecked;
	}

	public Integer getVipPassed() {
		return vipPassed;
	}

	public void setVipPassed(Integer vipPassed) {
		this.vipPassed = vipPassed;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}