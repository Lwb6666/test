package com.dxjr.base.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * <p>
 * Description:转可提历史记录<br />
 * </p>
 * 
 * @title TodrawLog.java
 * @package com.dxjr.base.entity
 * @author yangshijin
 * @version 0.1 2014年8月8日
 */
public class TodrawLog implements Serializable {
	private static final long serialVersionUID = -3492472543209894310L;

	private Integer id;
	private Integer userId;
	/** 状态【-1：审核失败 0：申请转可提 1：审核通过】 **/
	private Integer status;
	/** 转可提金额 **/
	private BigDecimal money;
	/** 到账总额 */
	private BigDecimal credited;
	/** 手续费 */
	private BigDecimal fee;
	/** 备注 **/
	private String remark;
	/** 申请时间 **/
	private Date addtime;
	/** 申请IP **/
	private String addip;
	/** 审核人 **/
	private Integer verifyUserId;
	/** 审核时间 **/
	private Date verifyTime;
	/** 审核备注 **/
	private String verifyRemark;
	/** 版本号. **/
	private Integer version;
	/** 平台来源 */
	private Integer platform;
	/**
	 * 本身版本号，主要用于根据版本号更新
	 */
	private Integer selfVersion;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getCredited() {
		return credited;
	}

	public void setCredited(BigDecimal credited) {
		this.credited = credited;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
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

	public Integer getVerifyUserId() {
		return verifyUserId;
	}

	public void setVerifyUserId(Integer verifyUserId) {
		this.verifyUserId = verifyUserId;
	}

	public Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	public String getVerifyRemark() {
		return verifyRemark;
	}

	public void setVerifyRemark(String verifyRemark) {
		this.verifyRemark = verifyRemark;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getSelfVersion() {
		return selfVersion;
	}

	public void setSelfVersion(Integer selfVersion) {
		this.selfVersion = selfVersion;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

}
