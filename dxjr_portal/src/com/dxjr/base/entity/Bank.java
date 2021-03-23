package com.dxjr.base.entity;

/**
 * <p>
 * Description:银行卡分支行信息<br />
 * </p>
 * 
 * @title Bank.java
 * @package com.dxjr.base.entity
 * @author qiongbiao.zhang
 * @version 0.1 2014年9月6日
 */
public class Bank {
	private Long cnapsCode; // 联行号

	private String province; // 省

	private String city; // 市

	private String district; // 区

	private String bankName; // 银行名称

	private String branchName; // 支行名称

	private String bankCode; // 银行编码

	public Long getCnapsCode() {
		return cnapsCode;
	}

	public void setCnapsCode(Long cnapsCode) {
		this.cnapsCode = cnapsCode;
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

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
}