package com.dxjr.portal.quickFinancing.entity;

import java.math.BigDecimal;

public class QuickFinancing {
    private Integer id;

    private String name;

    private String tel;

    private String qq;

    private Integer productType;

    private Integer mortgageType;

    private BigDecimal account;

    private Integer timeLimit;

    private String provinceCode;

    private String provinceName;

    private String cityCode;

    private String cityName;

    private String districtName;

    private String districtCode;

    private String appIp;

    private String appTime;

    private Integer approveStatus;

    private Integer approveUser;

    private String approveTime;

    private String approveRemark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getMortgageType() {
        return mortgageType;
    }

    public void setMortgageType(Integer mortgageType) {
        this.mortgageType = mortgageType;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getAppIp() {
        return appIp;
    }

    public void setAppIp(String appIp) {
        this.appIp = appIp == null ? null : appIp.trim();
    }

    public String getAppTime() {
        return appTime;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime == null ? null : appTime.trim();
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Integer getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(Integer approveUser) {
        this.approveUser = approveUser;
    }

    public String getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime == null ? null : approveTime.trim();
    }

    public String getApproveRemark() {
        return approveRemark;
    }

    public void setApproveRemark(String approveRemark) {
        this.approveRemark = approveRemark == null ? null : approveRemark.trim();
    }
    
    public String toString(){
    	return  name+","+provinceName+","+cityName+","+districtName+","+tel+","+"申请借款"+account+"万，期限"+timeLimit+"个月,"+getMortgageType(mortgageType);
    }
    public String getMortgageType(Integer mortgageType){
    	if(mortgageType==1){
    		return "房产抵押";
    	}
    	if(mortgageType==2){
    		return "车产抵押";
    	}
    	return "民间抵押" ;
    }

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
}