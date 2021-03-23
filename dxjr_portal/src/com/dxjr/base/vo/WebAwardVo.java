package com.dxjr.base.vo;

import java.io.Serializable;

/**
 * @author WangQianJin
 * @title 网站奖励VO
 * @date 2016年3月3日
 */
public class WebAwardVo implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 3152681046119261802L;
	
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 奖励类型
	 */
	private Integer type;
	
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
	
	

}
