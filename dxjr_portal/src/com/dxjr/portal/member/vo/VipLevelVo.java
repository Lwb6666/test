package com.dxjr.portal.member.vo;

import java.io.Serializable;

import com.dxjr.base.entity.VipLevel;

/**
 * 
 * <p>
 * Description:VIP会员等级<br />
 * </p>
 * 
 * @title VipLevelVo.java
 * @package com.dxjr.base.entity
 * @author yangshijin
 * @version 0.1 2015年1月13日
 */
public class VipLevelVo extends VipLevel implements Serializable {
	private static final long serialVersionUID = -2033213769093637666L;

	/** 主键ID */
	private String username;
	/** 真实姓名 */
	private String realName;
	/** 邮箱 */
	private String email;
	/** 手机 */
	private String mobileNum;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
}
