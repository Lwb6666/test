package com.dxjr.portal.autoInvestConfig.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.dxjr.common.page.BaseCnd;

public class FixAutoInvestRecord extends BaseCnd implements Serializable{

 
	private static final long serialVersionUID = 1L;
	private Integer id;

    private Integer userId;

    private String  status;//状态【0：未启用 1：启用 -1：删除】

    private Integer autoTenderType;//自动投宝方式【1：按金额投宝，2：按账户余额】

    private Integer tenderMoney;//按金额投宝-投宝金额，最低100且为100的整数倍

    private String fixLimit;//定期宝期限【1：1月宝，3：3月宝，6：6月宝】[1][3][6]

    private String isUseCur;//是否使用活期宝【0：不使用，1：使用】

    private String uptime;

    private Integer platform;

    private String remark;

    private Date addtime;

    private String addip;

    private Integer rownum;

    private Integer autoInvestId;//t_fix_auto_invest的id

    private Integer autoTenderMoney;//自动投宝金额，实际投出的

    private Integer fixId;//定期宝ID

    private String fixNo;//定期宝编号

    private Integer fixType;//定期宝期限【1：1月宝，3：3月宝，6：6月宝】

    private Integer recordType;//记录类型【1：设置 2：修改 3：停用 4：启用 5：删除 6：投宝 7：满宝 8：重新排队】

    private BigDecimal useMoney;//自动投宝时用户的可用余额，rocky_account的use_money

    private BigDecimal curMoney;//自动投宝时用户的活期宝总额，is_use_cur=1才记，t_cur_account的total

    private Integer limitMoney;//最后一笔投宝金额差：用户可投额度-实际投的额度，一般为0，正数
    
    private String preUptime;//record_type=6时记录，投宝时的uptime
    
    
	public String getPreUptime() {
		return preUptime;
	}

	public void setPreUptime(String preUptime) {
		this.preUptime = preUptime;
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

	public Integer getRownum() {
		return rownum;
	}

	public void setRownum(Integer rownum) {
		this.rownum = rownum;
	}

	public Integer getAutoInvestId() {
		return autoInvestId;
	}

	public void setAutoInvestId(Integer autoInvestId) {
		this.autoInvestId = autoInvestId;
	}

	public Integer getAutoTenderMoney() {
		return autoTenderMoney;
	}

	public void setAutoTenderMoney(Integer autoTenderMoney) {
		this.autoTenderMoney = autoTenderMoney;
	}

	public Integer getFixId() {
		return fixId;
	}

	public void setFixId(Integer fixId) {
		this.fixId = fixId;
	}

	public String getFixNo() {
		return fixNo;
	}

	public void setFixNo(String fixNo) {
		this.fixNo = fixNo;
	}

	public Integer getFixType() {
		return fixType;
	}

	public void setFixType(Integer fixType) {
		this.fixType = fixType;
	}

	public Integer getRecordType() {
		return recordType;
	}

	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
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

	public Integer getLimitMoney() {
		return limitMoney;
	}

	public void setLimitMoney(Integer limitMoney) {
		this.limitMoney = limitMoney;
	}

	 
    
    
}
