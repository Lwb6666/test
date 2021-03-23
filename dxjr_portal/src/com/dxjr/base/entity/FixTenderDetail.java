package com.dxjr.base.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Description:定期宝认购明细类<br />
 * </p>
 * 
 * @title Account.java
 * @package com.dxjr.base.account
 * @author caodefeng
 * @version 0.1 2015年5月14日
 */
public class FixTenderDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 用户id
	 */
	private Integer userId;
	
	/**
	 * 定期宝id
	 */
	private Integer fixBorrowId;
	
	/**
	 * 明细状态(0 ：投标中  1 ：投标成功   2：投标失败 3：已还款)
	 */
	private Integer status;
	
	/**
	 * 认购金额
	 */
	private BigDecimal account; 
	
	/**
	 * 认购金额中包含的可提现金额
	 */
	private BigDecimal drawMoney;
	
	/**
	 * 认购金额中包含的受限金额
	 */
	private BigDecimal noDrawMoney; 
	
	/**
	 * 添加时间
	 */
	private Date addTime;
	
	/**
	 * 添加ip
	 */
	private String addIp;
	
	/**
	 * 平台来源
	 */
	private Integer platForm;
	/**
	 * 最终认购记录id
	 */
	private Integer fixTenderRealId;

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

	public Integer getFixBorrowId() {
		return fixBorrowId;
	}

	public void setFixBorrowId(Integer fixBorrowId) {
		this.fixBorrowId = fixBorrowId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

	public BigDecimal getDrawMoney() {
		return drawMoney;
	}

	public void setDrawMoney(BigDecimal drawMoney) {
		this.drawMoney = drawMoney;
	}

	public BigDecimal getNoDrawMoney() {
		return noDrawMoney;
	}

	public void setNoDrawMoney(BigDecimal noDrawMoney) {
		this.noDrawMoney = noDrawMoney;
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

	public Integer getPlatForm() {
		return platForm;
	}

	public void setPlatForm(Integer platForm) {
		this.platForm = platForm;
	}

	public Integer getFixTenderRealId() {
		return fixTenderRealId;
	}

	public void setFixTenderRealId(Integer fixTenderRealId) {
		this.fixTenderRealId = fixTenderRealId;
	}
}
