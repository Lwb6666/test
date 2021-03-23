package com.dxjr.portal.curAccount.vo;

import java.io.Serializable;

import com.dxjr.common.page.BaseCnd;

public class CurAccountLogCnd extends BaseCnd implements Serializable {

	private static final long serialVersionUID = -6903226903535940857L;

	private Integer userId;

	private String beginDay;
	private String endDay;
	private String type;

	/**
	 * @return userId : return the property userId.
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            : set the property userId.
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return beginDay : return the property beginDay.
	 */
	public String getBeginDay() {
		return beginDay;
	}

	/**
	 * @param beginDay
	 *            : set the property beginDay.
	 */
	public void setBeginDay(String beginDay) {
		this.beginDay = beginDay;
	}

	/**
	 * @return endDay : return the property endDay.
	 */
	public String getEndDay() {
		return endDay;
	}

	/**
	 * @param endDay
	 *            : set the property endDay.
	 */
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	/**
	 * @return type : return the property type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            : set the property type.
	 */
	public void setType(String type) {
		this.type = type;
	}

}