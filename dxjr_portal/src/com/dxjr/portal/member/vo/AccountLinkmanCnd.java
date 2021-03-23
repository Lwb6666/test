package com.dxjr.portal.member.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:用户关联人查询条件<br />
 * </p>
 * 
 * @title AccountLinkmanVo.java
 * @package com.dxjr.portal.member.vo
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
public class AccountLinkmanCnd implements Serializable {
	private static final long serialVersionUID = 5970945871389634498L;

	/** 主键 */
	private Integer id;

	/** 被关联用户ID */
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