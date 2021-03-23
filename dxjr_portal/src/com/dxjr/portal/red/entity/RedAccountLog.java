package com.dxjr.portal.red.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 红包账户日志表
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title RedAccountLog.java
 * @package com.dxjr.portal.red.entity
 * @author huangpin
 * @version 0.1 2015年11月3日
 */
public class RedAccountLog implements Serializable {

	private static final long serialVersionUID = -2941709621396659368L;

	private Integer id;
	private Integer redId;// 红包账户表ID
	private BigDecimal money;// 红包金额
	private Integer userId;// 用户ID
	private Integer usebizId;// 目标业务ID，包含定期宝、直通车、手动投标ID
	private Integer bizId;// 定期宝认购明细表ID，直通车认购明细表ID，投标记录ID
	private Integer bizType;// 业务类型 0创建；1定期宝；2直通车；3手动投标；6撤宝；7流宝；8撤标；9流标
	private Integer status;// 状态：1未开启；2未使用；3已冻结；4已使用；5已过期
	private Integer optuser;// 操作人
	private Date opttime;// 操作时间
	private Date freezeTime;// 冻结时间
	private Date useTime;// 使用时间
	private String addip;// 操作IP
	private String remark;// 备注

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRedId() {
		return redId;
	}

	public void setRedId(Integer redId) {
		this.redId = redId;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUsebizId() {
		return usebizId;
	}

	public void setUsebizId(Integer usebizId) {
		this.usebizId = usebizId;
	}

	public Integer getBizId() {
		return bizId;
	}

	public void setBizId(Integer bizId) {
		this.bizId = bizId;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOptuser() {
		return optuser;
	}

	public void setOptuser(Integer optuser) {
		this.optuser = optuser;
	}

	public Date getOpttime() {
		return opttime;
	}

	public void setOpttime(Date opttime) {
		this.opttime = opttime;
	}

	public Date getFreezeTime() {
		return freezeTime;
	}

	public void setFreezeTime(Date freezeTime) {
		this.freezeTime = freezeTime;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public String getAddip() {
		return addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
