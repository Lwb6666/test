package com.dxjr.portal.first.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.dxjr.base.entity.FirstTransfer;
import com.dxjr.utils.StrinUtils;

/**
 * 
 * <p>
 * Description:直通车转让列表<br />
 * </p>
 * 
 * @title FirstTransferVo.java
 * @package com.dxjr.portal.first.vo
 * @author 朱泳霖
 * @version 0.1 2015年3月16日
 */
public class FirstTransferVo extends FirstTransfer implements Serializable {

	private static final long serialVersionUID = -191815169850848319L;

	private Integer userIdVo;

	/**
	 * 排队序列号
	 */
	private Integer ordernum;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 隐藏用户名
	 */
	private String userNameSecret;

	/**
	 * 加密用户名
	 */
	private String userNameEncrypt;

	/**
	 * 认购人名
	 */
	private String subscribeUsername;
	private Integer subUserId;
	
	/**
	 * 隐藏用户名
	 */
	private String subscribeUserNameSecret;

	/**
	 * 加密用户名
	 */
	private String subscribeUserNameEncrypt;

	/**
	 * 优先投标计划ID
	 */
	private Integer firstBorrowId;
	/**
	 * 直通车开通时间
	 */
	private Date firstOpenTime;

	/**
	 * 交易金额
	 */
	private BigDecimal transactionAccount;

	/**
	 * 可用余额
	 */
	private BigDecimal useMoney;

	/**
	 * 借款标ID
	 */
	private Integer borrowId;

	/**
	 * 投标直通车服务计划的开通时间
	 */
	private Date realAddtime;

	/**
	 * 排队号
	 */
	private Integer realOrdernum;

	/**
	 * 开通金额
	 */
	private BigDecimal realAccount;
	/**
	 * 剩余现金
	 */
	private BigDecimal realUseMoney;

	/**
	 * 新生成的最终认购记录ID
	 */
	private Integer tenderRealId;

	/**
	 * 奖励比例（奖励金额/奖励价格 四舍五入后保留两位小数）
	 */
	private BigDecimal awardApr;

	public Integer getSubUserId() {
		return subUserId;
	}

	public void setSubUserId(Integer subUserId) {
		this.subUserId = subUserId;
	}

	public Integer getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(Integer ordernum) {
		this.ordernum = ordernum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNameSecret() {
		userNameSecret = this.getUserName();
		userNameSecret = StrinUtils.stringSecretTo(userNameSecret);
		return userNameSecret;
	}

	public String getUserNameEncrypt() {
		userNameEncrypt = this.getUserName();
		userNameEncrypt = StrinUtils.stringEncryptEn(userNameEncrypt);
		return userNameEncrypt;
	}

	public BigDecimal getTransactionAccount() {
		// 转出价格不为空的场合
		if (this.getAccountReal() != null) {
			// 转让管理费不为的场合
			if (this.getManageFee() == null) {
				transactionAccount = this.getAccountReal();
			} else {
				transactionAccount = this.getAccountReal().subtract(this.getManageFee());
			}
		} else {
			transactionAccount = null;
		}
		return transactionAccount;
	}

	public Integer getFirstBorrowId() {
		return firstBorrowId;
	}

	public void setFirstBorrowId(Integer firstBorrowId) {
		this.firstBorrowId = firstBorrowId;
	}

	public Date getFirstOpenTime() {
		return firstOpenTime;
	}

	public void setFirstOpenTime(Date firstOpenTime) {
		this.firstOpenTime = firstOpenTime;
	}

	public BigDecimal getUseMoney() {
		return useMoney;
	}

	public void setUseMoney(BigDecimal useMoney) {
		this.useMoney = useMoney;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	public Integer getTenderRealId() {
		return tenderRealId;
	}

	public void setTenderRealId(Integer tenderRealId) {
		this.tenderRealId = tenderRealId;
	}

	public Date getRealAddtime() {
		return realAddtime;
	}

	public void setRealAddtime(Date realAddtime) {
		this.realAddtime = realAddtime;
	}

	public Integer getRealOrdernum() {
		return realOrdernum;
	}

	public void setRealOrdernum(Integer realOrdernum) {
		this.realOrdernum = realOrdernum;
	}

	public BigDecimal getRealAccount() {
		return realAccount;
	}

	public void setRealAccount(BigDecimal realAccount) {
		this.realAccount = realAccount;
	}

	public BigDecimal getRealUseMoney() {
		return realUseMoney;
	}

	public void setRealUseMoney(BigDecimal realUseMoney) {
		this.realUseMoney = realUseMoney;
	}

	public String getSubscribeUsername() {
		return subscribeUsername;
	}

	public void setSubscribeUsername(String subscribeUsername) {
		this.subscribeUsername = subscribeUsername;
	}

	public String getSubscribeUserNameSecret() {
		subscribeUserNameSecret = this.getSubscribeUsername();
		subscribeUserNameSecret = StrinUtils.stringSecretTo(subscribeUserNameSecret);
		return subscribeUserNameSecret;
	}

	public String getSubscribeUserNameEncrypt() {
		subscribeUserNameEncrypt = this.getSubscribeUsername();
		subscribeUserNameEncrypt = StrinUtils.stringEncryptEn(subscribeUserNameEncrypt);
		return subscribeUserNameEncrypt;
	}

	public Integer getUserIdVo() {
		return userIdVo;
	}

	public void setUserIdVo(Integer userIdVo) {
		this.userIdVo = userIdVo;
	}

	public BigDecimal getAwardApr() {
		return awardApr;
	}

	public void setAwardApr(BigDecimal awardApr) {
		this.awardApr = awardApr;
	}
}
