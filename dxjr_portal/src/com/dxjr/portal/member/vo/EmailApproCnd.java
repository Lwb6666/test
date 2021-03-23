package com.dxjr.portal.member.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:邮箱认证查询条件<br />
 * </p>
 * 
 * @title EmailApproCnd.java
 * @package com.dxjr.portal.member.vo
 * @author justin.xu
 * @version 0.1 2014年4月23日
 */
public class EmailApproCnd implements Serializable {
	private static final long serialVersionUID = -1748725774856611015L;
	/** 主键ID */
	private Integer id;
	/** 会员ID */
	private Integer userId;

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

}
