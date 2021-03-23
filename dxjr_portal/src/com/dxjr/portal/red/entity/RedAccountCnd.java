package com.dxjr.portal.red.entity;

import java.io.Serializable;

import com.dxjr.common.page.BaseCnd;

/**
 * 红包账户表
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title RedAccount.java
 * @package com.dxjr.portal.red.entity
 * @author huangpin
 * @version 0.1 2015年11月3日
 */
public class RedAccountCnd extends BaseCnd implements Serializable {

	private static final long serialVersionUID = 8920693453057286413L;
	
	private Integer userId;// 用户ID
	private String year;// 红包使用年份
	private String month;// 红包使用月份
	private String redType;// 红包类型
	private String redMoney;// 红包金额
	private String redMonth;// 红包月份
	private String useType;//可投类型
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getRedType() {
		return redType;
	}

	public void setRedType(String redType) {
		this.redType = redType;
	}

	public String getRedMoney() {
		return redMoney;
	}

	public void setRedMoney(String redMoney) {
		this.redMoney = redMoney;
	}

	public String getRedMonth() {
		return redMonth;
	}

	public void setRedMonth(String redMonth) {
		this.redMonth = redMonth;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}
}
