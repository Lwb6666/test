package com.dxjr.portal.account.vo;

import java.math.BigDecimal;

import com.dxjr.utils.StrinUtils;
/**
 * <p>
 * Description:投资详情信息Vo<br />
 * </p>
 * 
 * @title InvestVo.java
 * @package com.dxjr.portal.account.vo
 * @author 胡建盼
 * @version 0.1 2014年8月14日
 */
public class InvestVo {
	
	/**借款标名称*/
	private String borrowName;
	/**借款标ID*/
	private Integer borrowId;
	/**借款用户ID*/
	private Integer borrowUserid;
	/**借款用户名称*/
	private String borrowUsername;
	/**投标金额*/
	private BigDecimal tenderAccount;
	/**满标时间*/
	private String succcessTime;
	private String userNameSecret;  //隐藏用户名
	private String userNameEncrypt;  //加密用户名
	private String tenderType;  //投标方式
	private String contractNo;//定期宝编号
	private Integer fixborrowId;//定期宝ID
	private String locklimit;//定期宝期限
	private String tenderFixType;  //投宝方式
	private String tenderFixAddTime;  //投资时间
	private String userIdMD5;  
	
public String getUserIdMD5() {
		return userIdMD5;
	}
	public void setUserIdMD5(String userIdMD5) {
		this.userIdMD5 = userIdMD5;
	}
/*************************************getxx() setxx() *********************************************************/	
	public String getBorrowName() {
		return borrowName;
	}
	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}
	public Integer getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}
	public Integer getBorrowUserid() {
		return borrowUserid;
	}
	public void setBorrowUserid(Integer borrowUserid) {
		this.borrowUserid = borrowUserid;
	}
	public String getBorrowUsername() {
		return borrowUsername;
	}
	public void setBorrowUsername(String borrowUsername) {
		this.borrowUsername = borrowUsername;
	}
	public BigDecimal getTenderAccount() {
		return tenderAccount;
	}
	public void setTenderAccount(BigDecimal tenderAccount) {
		this.tenderAccount = tenderAccount;
	}
	public String getSucccessTime() {
		return succcessTime;
	}
	public void setSucccessTime(String succcessTime) {
		this.succcessTime = succcessTime;
	}
	
	public String getUserNameSecret() {
		userNameSecret = this.getBorrowUsername();
			userNameSecret = StrinUtils.stringSecretTo(userNameSecret);
		return userNameSecret;
	}
	public String getUserNameEncrypt() {
		userNameEncrypt = this.getBorrowUsername();
		userNameEncrypt = StrinUtils.stringEncryptEn(userNameEncrypt);
		return userNameEncrypt;
	}
	public String getTenderType() {
		return tenderType;
	}
	public void setTenderType(String tenderType) {
		this.tenderType = tenderType;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getLocklimit() {
		return locklimit;
	}
	public void setLocklimit(String locklimit) {
		this.locklimit = locklimit;
	}
	public String getTenderFixType() {
		return tenderFixType;
	}
	public void setTenderFixType(String tenderFixType) {
		this.tenderFixType = tenderFixType;
	}
	public String getTenderFixAddTime() {
		return tenderFixAddTime;
	}
	public void setTenderFixAddTime(String tenderFixAddTime) {
		this.tenderFixAddTime = tenderFixAddTime;
	}
	public Integer getFixborrowId() {
		return fixborrowId;
	}
	public void setFixborrowId(Integer fixborrowId) {
		this.fixborrowId = fixborrowId;
	}
}
