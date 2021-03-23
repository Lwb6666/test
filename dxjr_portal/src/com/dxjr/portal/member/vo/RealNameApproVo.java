package com.dxjr.portal.member.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * Description:实名认证<br />
 * </p>
 * 
 * @title RealNameApproVo.java
 * @package com.dxjr.portal.member.vo
 * @author justin.xu
 * @version 0.1 2014年4月24日
 */
public class RealNameApproVo implements Serializable {
	private static final long serialVersionUID = -368068524132797849L;
	/** ID */
	private Integer id;
	/** 用户ID */
	private Integer userId;
	/** 用户真实姓名 */
	private String realName;
	/** 身份证号码 */
	private String idCardNo;
	/** 身份证扫描1（正面） */
	private String pic1;
	/** 身份证扫描2（反面） */
	private String pic2;
	/** 是否通过实名认证 【-1：审核不通过，0：等待审核，1：审核通过】 */
	private Integer isPassed;
	/** 审核用户 */
	private String approveUser;
	/** 审核时间 */
	private String approveTime;
	/** 申请时间 */
	private String appTime;
	/** 申请IP */
	private String appIp;
	/** 审核备注 */
	private String reason;
	/** 籍贯 */
	private String nativePlace;
	/** 民族 */
	private String nation;
	/** 证件类型 */
	private String cardType;
	/** 生日 */
	private String birthDay;
	/** 性别 */
	private String sex;
	/** 版本号 */
	private Integer version;

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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getPic1() {
		return pic1;
	}

	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}

	public String getPic2() {
		return pic2;
	}

	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}

	public Integer getIsPassed() {
		return isPassed;
	}

	public void setIsPassed(Integer isPassed) {
		this.isPassed = isPassed;
	}

	public String getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(String approveUser) {
		this.approveUser = approveUser;
	}

	public String getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}

	public String getAppTime() {
		return appTime;
	}

	public void setAppTime(String appTime) {
		this.appTime = appTime;
	}

	public String getAppIp() {
		return appIp;
	}

	public void setAppIp(String appIp) {
		this.appIp = appIp;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

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
    public static void main(String[] args) {
    	RealNameApproVo realNameApproVo=new RealNameApproVo();
    	realNameApproVo.setRealName("于彬彬");
		System.out.println(realNameApproVo.getSecuritRealName());
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
}
