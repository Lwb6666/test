package com.dxjr.portal.member.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:邮箱认证Vo<br />
 * </p>
 * 
 * @title EmailApproVo.java
 * @package com.dxjr.portal.member.vo
 * @author justin.xu
 * @version 0.1 2014年4月23日
 */
public class EmailApproVo implements Serializable {
	private static final long serialVersionUID = -1748725774856611015L;
	/** 主键ID */
	private Integer id;
	/** 会员ID */
	private Integer userId;
	/** 随机UUID */
	private String randUUID;
	/** 是否通过验证【0：未通过，1：通过】 */
	private Integer checked;
	/** 认证时间 */
	private String apprTime;
	/** 认证IP */
	private String apprIp;

	/** 邮箱地址 */
	private String email;
	private String securityemail; // 带*的email
	
	/**平台来源 1：官网 2、微信，即用户登录的客户端*/
	private Integer  platform;


	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}
	public String getSecurityemail() {
		/**例如  email = com_panpan@163.com 将显示成： co********@163.com */
		int index = email.indexOf("@");
		if (email != null && email.indexOf("@") > 0 &&  email.substring(0,index).length() > 3) {
			String str1 = email.substring(0, 2);
			String str3 = email.substring(index, email.length());
			StringBuilder str2 = new StringBuilder();
			if (str1.length() > 1) {
				for (int i = 0; i < repeatTime(str3); i++) {
					str2.append("*") ;
				}
				return securityemail = str1 + str2.toString() + str3;
			} else {
				return securityemail = "*" + str3;
			}

		} else {
			//特殊后缀（不带  @  ）以及长度过短 的邮箱则特殊处理  
			return securityemail = (email != null ?email:" "); 
		}
	}



	/**需要循环的次数，即有多少个字符需要替换成 “*”*/
	private int repeatTime(String str3) {
		return email.length() - str3.length() - 2;
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

	public String getRandUUID() {
		return randUUID;
	}

	public void setRandUUID(String randUUID) {
		this.randUUID = randUUID;
	}

	public Integer getChecked() {
		return checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
	}

	public String getApprTime() {
		return apprTime;
	}

	public void setApprTime(String apprTime) {
		this.apprTime = apprTime;
	}

	public String getApprIp() {
		return apprIp;
	}

	public void setApprIp(String apprIp) {
		this.apprIp = apprIp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
