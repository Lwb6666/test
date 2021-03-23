package com.dxjr.portal.investment.vo;

import java.io.Serializable;

public class UserInfo  implements Serializable{

	/**
	 * @author JingBinbin
     * @title 网贷天下用户绑定实体
     * @date 2016年6月30日
	 */
	private static final long serialVersionUID = 1L;

	private String from; //来源wdtx
	
	private String wdtxUserId; //网贷天下用户ID
	
	private String username; //用户名
	
	private String realName; //真实姓名
	
	private String bankCardNo; //银行卡号
	
	private String bankCode; //银行编码
	
	private String bankName; //银行名称
	
	private String idCardNo; //身份证号码
	
	private String phone;  //手机号码
	
	//0无效、1正常
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getWdtxUserId() {
		return wdtxUserId;
	}

	public void setWdtxUserId(String wdtxUserId) {
		this.wdtxUserId = wdtxUserId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
