package com.dxjr.portal.member.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:实名认证查询条件<br />
 * </p>
 * 
 * @title RealNameApproCnd.java
 * @package com.dxjr.portal.member.vo
 * @author justin.xu
 * @version 0.1 2014年4月24日
 */
public class RealNameApproCnd implements Serializable {
	private static final long serialVersionUID = -1748725774856611015L;
	/** 主键ID */
	private Integer id;
	/** 会员ID */
	private Integer userId;
	/** 身份证号码 */
	private String idCardNo;
    /** 真实姓名 */
	private String realName;
	/**
	 * 审核状态
	 */
	private Integer isPassed;
	
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

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

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public Integer getIsPassed() {
		return isPassed;
	}

	public void setIsPassed(Integer isPassed) {
		this.isPassed = isPassed;
	}

}
