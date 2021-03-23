package com.dxjr.portal.autoInvestConfig.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class FixAutoInvest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;

    private Integer userId;

    private String status;//状态【0：未启用 1：启用 -1：删除】

    private Integer autoTenderType;//自动投宝方式【1：按金额投宝，2：按账户余额】

    private Integer tenderMoney;//按金额投宝-投宝金额，最低100且为100的整数倍

    private String fixLimit;//定期宝期限【1：1月宝，3：3月宝，6：6月宝】[1][3][6]

    private String isUseCur;//是否使用活期宝【0：不使用，1：使用】

    private String uptime;//排队时间,18位（毫秒13位+序号5位），设置唯一约束

    private Integer platform;//平台来源【1：网页 2：微信 3：安卓端 4：IOS端】

    private String remark;//备注

    private Date addtime;

    private String addip;
    
    //非数据库字段
    private String fixLimitStr;////定期宝期限【1：1月宝，3：3月宝，6：6月宝】
    private Integer rownum;//排队号
    private Integer limitMoney;//可投额度
    private BigDecimal useMoney;
    private BigDecimal curMoney;
    
    public Integer getLimitMoney() {
    	if(this.autoTenderType.intValue()==1){
    		limitMoney=this.getTenderMoney();
    	}else if(this.autoTenderType.intValue()==2){
    		if("1".equals(this.getIsUseCur())){
    			limitMoney = (int) (useMoney.add(curMoney).doubleValue()/100)*100;//取100整数倍
    		}else{
    			limitMoney = (int) (useMoney.doubleValue()/100)*100;//取100整数倍
    		}
    	}
		return limitMoney;
	}
    

	public void setLimitMoney(Integer limitMoney) {
		this.limitMoney = limitMoney;
	}

	public String getFixLimitStr() {
		if(fixLimit!=null){
			return this.fixLimit.replaceAll("\\[", "").replaceAll("\\]", "月宝 ");
		}
		return fixLimitStr;
	}

	public void setFixLimitStr(String fixLimitStr) {
		this.fixLimitStr = fixLimitStr;
	}

	public Integer getRownum() {
		return rownum;
	}

	public void setRownum(Integer rownum) {
		this.rownum = rownum;
	}
	public String[] fixLimits;
    
	public String[] getFixLimits() {
		return fixLimits;
	}

	public void setFixLimits(String[] fixLimits) {
		this.fixLimits = fixLimits;
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

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getAutoTenderType() {
		return autoTenderType;
	}

	public void setAutoTenderType(Integer autoTenderType) {
		this.autoTenderType = autoTenderType;
	}

	public Integer getTenderMoney() {
		return tenderMoney;
	}

	public void setTenderMoney(Integer tenderMoney) {
		this.tenderMoney = tenderMoney;
	}

	public String getFixLimit() {
		 
		if(fixLimits!=null&&this.fixLimit==null){
			this.fixLimit="";
			for(String temp:fixLimits){
			   fixLimit=fixLimit+"["+temp+"]";
			}
		}
		return fixLimit;
	}

	public void setFixLimit(String fixLimit) {
		this.fixLimit = fixLimit;
	}

	public String getIsUseCur() {
		return isUseCur;
	}

	public void setIsUseCur(String isUseCur) {
		this.isUseCur = isUseCur;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAddip() {
		return addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	public BigDecimal getUseMoney() {
		return useMoney;
	}

	public void setUseMoney(BigDecimal useMoney) {
		this.useMoney = useMoney;
	}

	public BigDecimal getCurMoney() {
		return curMoney;
	}

	public void setCurMoney(BigDecimal curMoney) {
		this.curMoney = curMoney;
	}


    

}
