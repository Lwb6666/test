package com.dxjr.portal.repayment.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//
/**
 * <p>
 * Description:待还记录和关联的借款标信息<br />
 * </p>
 * 
 * @title RepaymentBorrowVo.java
 * @package com.dxjr.portal.repayment.vo
 * @author justin.xu
 * @version 0.1 2014年8月13日
 */
public class RepaymentBorrowVo implements Serializable {
	private static final long serialVersionUID = 1253001488105937294L;
	/** 待还记录id */
	private String repamentId;
	/** 待还状态 */
	private String repamentStatus;
	/** 待还网站垫付状态 */
	private String repamentWebStatus;
	/** 借款标id */
	private String id;
	/** 状态 （0:草稿标 1：新标，审核中，2：投标中，3：满标复审中，4：还款中，5：还款结束，-1：流标，-2：被撤销 ，-3：审核失败） */
	private String status;
	/** 借款标题 */
	private String name;
	/** 借款标种类（ 1：信用标，2：抵押标，3：净值标，4：秒标 ） */
	private Integer borrowType;
	/** 用户ID */
	private Integer userId;
	private String username;
	/** 应还总额 */
	private BigDecimal account;
	/** 年利率 */
	private BigDecimal apr;
	/** 还款方式【 0：没有限制， 1：按月分期还款，2：按月付息到期还本 , 3：到期还本付息,4:按天到期还款 */
	private Integer style;
	/** 有效时间 天数 */
	private Integer validTime;
	/** 添加时间 */
	private Date addTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRepamentId() {
		return repamentId;
	}

	public void setRepamentId(String repamentId) {
		this.repamentId = repamentId;
	}

	public String getRepamentStatus() {
		return repamentStatus;
	}

	public void setRepamentStatus(String repamentStatus) {
		this.repamentStatus = repamentStatus;
	}

	public String getRepamentWebStatus() {
		return repamentWebStatus;
	}

	public void setRepamentWebStatus(String repamentWebStatus) {
		this.repamentWebStatus = repamentWebStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBorrowType() {
		return borrowType;
	}

	public void setBorrowType(Integer borrowType) {
		this.borrowType = borrowType;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

	public BigDecimal getApr() {
		return apr;
	}

	public void setApr(BigDecimal apr) {
		this.apr = apr;
	}

	public Integer getStyle() {
		return style;
	}

	public void setStyle(Integer style) {
		this.style = style;
	}

	public Integer getValidTime() {
		return validTime;
	}

	public void setValidTime(Integer validTime) {
		this.validTime = validTime;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

}
