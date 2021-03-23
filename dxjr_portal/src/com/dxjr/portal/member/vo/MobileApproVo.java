package com.dxjr.portal.member.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:手机验证Vo<br />
 * </p>
 * 
 * @title MobileApproVo.java
 * @package com.dxjr.portal.member.vo
 * @author justin.xu
 * @version 0.1 2014年4月25日
 */
public class MobileApproVo implements Serializable {

	private static final long serialVersionUID = -3441075247554353199L;
	/** ID */
	private Integer id;
	/** 用户ID */
	private Integer userId;
	/** 验证码 */
	private String randCode;
	/** 手机号码 */
	private String mobileNum;
	/** 是否通过验证【0：未通过；1：通过】 */
	private Integer passed;
	/** 验证码发送时间 */
	private String addTime;
	/** 发送IP */
	private String addIp;
	/** 验证通过时间 */
	private String approTime;

	/** 手机号码 */
	private String securitymobileNum;
	
	public String getSecuritymobileNum() {
		/**内陆地区手机号码*/
		if(mobileNum != null && mobileNum.length() == 11){
			String str1 = mobileNum.substring(0,3);
			String str3 = mobileNum.substring(mobileNum.length()-4);
			StringBuilder str2 = new StringBuilder();
			for(int i=0; i<mobileNum.length()-4;i++){
				str2.append("*");
			}

			securitymobileNum = str1 + str2.toString() + str3;
		}else 
			/**港澳台、国外号码特殊处理*/
			if(mobileNum != null && mobileNum.length() > 0 ){

				String str1 = mobileNum.substring(0,3);
				String str3 = mobileNum.substring(mobileNum.length()-3);
				StringBuilder str2 = new StringBuilder();
				for(int i=0; i<mobileNum.length()-3;i++){
					str2.append("*");
				}

				securitymobileNum = str1 + str2.toString() + str3;
		}
		
		return securitymobileNum;
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

	public String getRandCode() {
		return randCode;
	}

	public void setRandCode(String randCode) {
		this.randCode = randCode;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public Integer getPassed() {
		return passed;
	}

	public void setPassed(Integer passed) {
		this.passed = passed;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	public String getApproTime() {
		return approTime;
	}

	public void setApproTime(String approTime) {
		this.approTime = approTime;
	}

}
