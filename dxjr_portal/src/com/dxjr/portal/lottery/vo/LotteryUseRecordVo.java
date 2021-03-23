package com.dxjr.portal.lottery.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.dxjr.base.entity.LotteryUseRecord;

public class LotteryUseRecordVo extends LotteryUseRecord implements Serializable {
	private static final long serialVersionUID = 5435591965383872871L;

	// 剩余N次抽奖机会
	private Integer useNum;
	// 用户的可用余额
	private BigDecimal useMoney;

	// 状态:可使用
	private String status_z;

	// 操作:使用
	private String status_c;

	// 奖品来源名称
	private String name;
	
	private String contact; //联系人
	private String mobile; //手机
	
	private String address; //地址
	private String postcode; //邮编
	
	private String express_company; //快递公司
	private String express_code; //快递编号
	
	

	/**
	 * @return useNum : return the property useNum.
	 */
	public Integer getUseNum() {
		return useNum;
	}

	/**
	 * @param useNum
	 *            : set the property useNum.
	 */
	public void setUseNum(Integer useNum) {
		this.useNum = useNum;
	}



	/**
	 * @return status_z : return the property status_z.
	 */
	public String getStatus_z() {
		return status_z;
	}

	/**
	 * @param status_z
	 *            : set the property status_z.
	 */
	public void setStatus_z(String status_z) {
		this.status_z = status_z;
	}

	/**
	 * @return status_c : return the property status_c.
	 */
	public String getStatus_c() {
		return status_c;
	}

	/**
	 * @param status_c
	 *            : set the property status_c.
	 */
	public void setStatus_c(String status_c) {
		this.status_c = status_c;
	}

	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return name : return the property name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            : set the property name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return useMoney : return the property useMoney.
	 */
	public BigDecimal getUseMoney() {
		return useMoney;
	}

	/**
	 * @param useMoney : set the property useMoney.
	 */
	public void setUseMoney(BigDecimal useMoney) {
		this.useMoney = useMoney;
	}

	/**
	 * @return contact : return the property contact.
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @param contact : set the property contact.
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * @return mobile : return the property mobile.
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile : set the property mobile.
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return address : return the property address.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address : set the property address.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return postcode : return the property postcode.
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * @param postcode : set the property postcode.
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * @return express_company : return the property express_company.
	 */
	public String getExpress_company() {
		return express_company;
	}

	/**
	 * @param express_company : set the property express_company.
	 */
	public void setExpress_company(String express_company) {
		this.express_company = express_company;
	}

	/**
	 * @return express_code : return the property express_code.
	 */
	public String getExpress_code() {
		return express_code;
	}

	/**
	 * @param express_code : set the property express_code.
	 */
	public void setExpress_code(String express_code) {
		this.express_code = express_code;
	}

	
	
	
	
	
}