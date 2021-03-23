package com.dxjr.portal.member.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * <p>
 * Description:VIP申请<br />
 * </p>
 * 
 * @title VIPApply.java
 * @package com.dxjr.base.entity
 * @author yangshijin
 * @version 0.1 2014年8月7日
 */
public class VIPApplyVo implements Serializable {

	private static final long serialVersionUID = 7511663395937691845L;

	/** ID */
	private Integer id;
	/** 用户ID */
	private Integer userId;
	/** 申请类型（0：首次开通，1：续费，2：重新开通） **/
	private Integer type;
	/** 状是否通过VIP审核【0:初始状态；1：审核通过；-1：审核不通过】 */
	private Integer passed;
	/** 审核人 */
	private String approver;
	/** 审核备注 */
	private String approveRemark;
	/** 审核通过时间 */
	private Date approveTime;
	/** 客服编号 */
	private String serviceNum;
	/** 有效截止 期 */
	private Date indate;
	/** 申请时间 */
	private Date addTime;
	/** 申请IP */
	private String addIp;
	/** 是否缴费【0：否，1：是】 */
	private Integer isFee;
	/** 备注 */
	private String remark;
	/** 开通费用 */
	private BigDecimal fee;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPassed() {
		return passed;
	}

	public void setPassed(Integer passed) {
		this.passed = passed;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getApproveRemark() {
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getServiceNum() {
		return serviceNum;
	}

	public void setServiceNum(String serviceNum) {
		this.serviceNum = serviceNum;
	}

	public Date getIndate() {
		return indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	public Integer getIsFee() {
		return isFee;
	}

	public void setIsFee(Integer isFee) {
		this.isFee = isFee;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
}
