package com.dxjr.portal.fix.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.dxjr.common.page.BaseCnd;

/**
 * <p>
 * Description:定期宝开通参数值<br />
 * </p>
 * @title FixBorrowOpenCnd.java
 * @package com.dxjr.portal.fix1.vo
 * @author caodefeng
 * @version 0.1 2015年5月14日
 */
public class FixBorrowOpenCnd extends BaseCnd implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1274232326956459426L;
	/**
	 * 定期宝id
	 */
	private Integer fixBorrowId;
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

	/** 红包ID */
	private Integer redId;
	
	private Integer platform; //平台来源  1：网页  2：微信
	
	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}
	/**
	 * 专区类型【0：普通专区，1：新手专区】
	 */
	private Integer areaType;	

	public Integer getAreaType() {
		return areaType;
	}

	public void setAreaType(Integer areaType) {
		this.areaType = areaType;
	}

	public String getIsUseCurMoney() {
		return isUseCurMoney;
	}

	public void setIsUseCurMoney(String isUseCurMoney) {
		this.isUseCurMoney = isUseCurMoney;
	}

	public Integer getFixBorrowId() {
		return fixBorrowId;
	}

	public void setFixBorrowId(Integer fixBorrowId) {
		this.fixBorrowId = fixBorrowId;
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

	public Integer getRedId() {
		return redId;
	}

	public void setRedId(Integer redId) {
		this.redId = redId;
	}

}
