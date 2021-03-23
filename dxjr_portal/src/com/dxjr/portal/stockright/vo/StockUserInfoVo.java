package com.dxjr.portal.stockright.vo;

import org.apache.commons.lang3.StringUtils;

public class StockUserInfoVo {
	private String realName;//用户真实姓名
	private String userName;//用户名称
	private String idCardNo;//身份证号
	private String mobile;//注册手机号
	private Integer sex; //性别
	
	
	private String securityRealName; // 带*的姓名
	private String securityIdCardNo; // 带*的身份证
	private String securitRealName;//  姓用*号代替
	
	public String getSecurityRealName() {
		if(!StringUtils.isEmpty(realName) && realName.length() > 1){
			String str1 = realName.substring(0,1);
			String str3 = realName.substring(realName.length());
			String str2 = "";
			for(int i=0; i<realName.length()-1;i++){
				str2 = str2 + "*";
			}

			securityRealName = str1 + str2 + str3;
		}else {
			securityRealName = "*";
		}

		return securityRealName;
	}
	
	public String getSecurityIdCardNo() {
		if(!StringUtils.isEmpty(idCardNo) && idCardNo.length() > 2){
			String str1 = idCardNo.substring(0,2);
			StringBuilder str2 = new StringBuilder();
			for(int i=0; i<idCardNo.length()-2;i++){
				str2 .append("*");
			}

			securityIdCardNo = str1 + str2.toString();
			return securityIdCardNo;
		}else{
			securityIdCardNo = "**";
		}

		return securityIdCardNo;
		
	}
	
	public String getSecuritRealName() {
		if(!StringUtils.isEmpty(realName) && realName.length() > 2){
			String str1 = realName.substring(0,1);
			String str3 = realName.substring(realName.length()-1,realName.length());
			String str2 = "";
			for(int i=0; i<realName.length()-2;i++){
				str2 = str2 + "*";
			}

			securitRealName = str1 + str2 + str3;
		}else {
			securitRealName = "*"+realName.substring(1,2);
		}
		return securitRealName;
	}
	
	/** 手机号码 */
	private String securitymobileNum;
	
	public String getSecuritymobileNum() {
		/**内陆地区手机号码*/
		if(mobile != null && mobile.length() == 11){
			String str1 = mobile.substring(0,3);
			String str3 = mobile.substring(mobile.length()-4);
			StringBuilder str2 = new StringBuilder();
			for(int i=0; i<mobile.length()-4;i++){
				str2.append("*");
			}

			securitymobileNum = str1 + str2.toString() + str3;
		}else 
			/**港澳台、国外号码特殊处理*/
			if(mobile != null && mobile.length() > 0 ){

				String str1 = mobile.substring(0,3);
				String str3 = mobile.substring(mobile.length()-3);
				StringBuilder str2 = new StringBuilder();
				for(int i=0; i<mobile.length()-3;i++){
					str2.append("*");
				}

				securitymobileNum = str1 + str2.toString() + str3;
		}
		
		return securitymobileNum;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}
