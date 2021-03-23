package com.dxjr.portal.member.vo;

import java.io.Serializable;

import com.dxjr.common.page.BaseCnd;

/**
 * 
 * <p>
 * Description:VIP申请<br />
 * </p>
 * 
 * @title VIPApply.java
 * @package com.dxjr.base.entity
 * @author yangshijin
 * @version 0.1 2014年8月7日
 */
public class VIPApplyCnd extends BaseCnd implements Serializable {

	private static final long serialVersionUID = -7511370996332456648L;

	/** ID */
	private Integer id;
	/** 用户ID */
	private Integer userId;

	/** 申请类型（0：首次开通，1：续费，2：重新开通） **/
	private Integer type;

	/** 状是否通过VIP审核【0:初始状态；1：审核通过；-1：审核不通过】 */
	private Integer passed;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPassed() {
		return passed;
	}

	public void setPassed(Integer passed) {
		this.passed = passed;
	}

}
