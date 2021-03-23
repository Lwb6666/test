package com.dxjr.portal.borrow.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.dxjr.base.entity.Borrower;
import com.dxjr.common.Dictionary;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.StrinUtils;

/**
 * <p>
 * Description:借款者基本信息<br />
 * </p>
 * @title BorrowerVo.java
 * @package com.dxjr.portal.borrow.vo
 * @author justin.xu
 * @version 0.1 2014年9月13日
 */
public class BorrowerVo extends Borrower {

	private static final long serialVersionUID = 5854128305054101351L;
	/** 生日 */
	private String birthday;
	/** 年龄 */
	private Integer age;
	/** 借款者用户名 */
	private String username;
	/** 月收入 */
	private String income;
	/** 性别 */
	private String sex;
	private String userNameSecret;  //隐藏用户名
	private String userNameEncrypt;  //加密用户名
	private String emergencyContact;
	private BigDecimal userIncome;
	
	

	public BigDecimal getUserIncome() {
		return userIncome;
	}

	public void setUserIncome(BigDecimal userIncome) {
		this.userIncome = userIncome;
	}

	public String getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Integer getAge() {
		if (null != birthday) {
			Date birthdayDate = DateUtils.parse(birthday, DateUtils.YMD_DASH);
			int birthYear = birthdayDate.getYear();
			int nowYear = new Date().getYear();
			return nowYear - birthYear;
		}
		return null;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	private String educationStr;
	private String maritalStatusStr;
	private String scaleStr;
	private String workYearsStr;

	public String getEducationStr() {
		if (getEducation() != null)
			return Dictionary.getValue(810, getEducation() + "");
		return educationStr;
	}

	public void setEducationStr(String educationStr) {
		this.educationStr = educationStr;
	}

	public String getMaritalStatusStr() {
		if (getMaritalStatus() != null)
			return Dictionary.getValue(811, getMaritalStatus() + "");
		return maritalStatusStr;
	}

	public void setMaritalStatusStr(String maritalStatusStr) {
		this.maritalStatusStr = maritalStatusStr;
	}

	public String getScaleStr() {
		if (getScale() != null)
			return Dictionary.getValue(812, getScale() + "");
		return scaleStr;
	}

	public void setScaleStr(String scaleStr) {
		this.scaleStr = scaleStr;
	}

	public String getWorkYearsStr() {
		if (getWorkYears() != null)
			return Dictionary.getValue(813, getWorkYears() + "");
		return workYearsStr;
	}

	public void setWorkYearsStr(String workYearsStr) {
		this.workYearsStr = workYearsStr;
	}
	
	public String getUserNameSecret() {
		userNameSecret = this.getUsername();
			userNameSecret = StrinUtils.stringSecretTo(userNameSecret);
		return userNameSecret;
	}
	public String getUserNameEncrypt() {
		userNameEncrypt = this.getUsername();
		userNameEncrypt = StrinUtils.stringEncryptEn(userNameEncrypt);
		return userNameEncrypt;
	}
}