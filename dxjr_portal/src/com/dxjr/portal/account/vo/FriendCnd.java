package com.dxjr.portal.account.vo;

import java.io.Serializable;

import com.dxjr.common.page.BaseCnd;

public class FriendCnd extends BaseCnd implements Serializable{
	private static final long serialVersionUID = -67496093806755950L;

	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 是否推荐成功
	 */
	private Integer isRecommendSuccess;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getIsRecommendSuccess() {
		return isRecommendSuccess;
	}

	public void setIsRecommendSuccess(Integer isRecommendSuccess) {
		this.isRecommendSuccess = isRecommendSuccess;
	}
}
