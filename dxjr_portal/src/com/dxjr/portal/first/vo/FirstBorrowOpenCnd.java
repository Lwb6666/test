package com.dxjr.portal.first.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.dxjr.common.page.BaseCnd;

/**
 * <p>
 * Description:直通车开通参数值<br />
 * </p>
 * 
 * @title FirstBorrowOpenCnd.java
 * @package com.dxjr.portal.first.vo
 * @author justin.xu
 * @version 0.1 2014年7月16日
 */
public class FirstBorrowOpenCnd extends BaseCnd implements Serializable {
	private static final long serialVersionUID = 8387186801624482411L;
	/**
	 * 直通车id
	 */
	private Integer firstBorrowId;
	/**
	 * 投标金额
	 */
	private BigDecimal tenderMoney;
	/**
	 * 交易密码
	 */
	private String payPassword;
	/**
	 * 投标密码
	 */
	private String bidPassword;
	/**
	 * 验证码
	 */
	private String checkCode;
     
	/** 是否使用活期宝金额 0：不使用，1：使用 */
	private String isUseCurMoney;
    private Integer redId;//红包ID
    
	public Integer getRedId() {
		return redId;
	}

	public void setRedId(Integer redId) {
		this.redId = redId;
	}

	public Integer getFirstBorrowId() {
		return firstBorrowId;
	}

	public void setFirstBorrowId(Integer firstBorrowId) {
		this.firstBorrowId = firstBorrowId;
	}

	public BigDecimal getTenderMoney() {
		return tenderMoney;
	}

	public void setTenderMoney(BigDecimal tenderMoney) {
		this.tenderMoney = tenderMoney;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getBidPassword() {
		return bidPassword;
	}

	public void setBidPassword(String bidPassword) {
		this.bidPassword = bidPassword;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getIsUseCurMoney() {
		return isUseCurMoney;
	}

	public void setIsUseCurMoney(String isUseCurMoney) {
		this.isUseCurMoney = isUseCurMoney;
	}
}
