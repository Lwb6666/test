package com.dxjr.portal.fix.vo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dxjr.base.entity.FixTenderDetail;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.StrinUtils;

/**
 * <p>
 * Description:定期宝认购明细类<br />
 * </p>
 * 
 * @title FixTenderDetailVo.java
 * @package com.dxjr.portal.fix1.vo
 * @author caodefeng
 * @version 0.1 2015年5月14日
 */
public class FixTenderDetailVo extends FixTenderDetail {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7393386115454776951L;

	/**
	 * 主键id
	 */
	private Integer id;

	/**
	 * 用户认购总额
	 */
	private BigDecimal sumAccount;

	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 定期宝名称
	 */
	private String name;

	/**
	 * 定期宝利率
	 */
	private BigDecimal apr;

	/**
	 * 锁定期限
	 */
	private Integer lockLimit;
	/**
	 * 合同号
	 */
	private String contractNo;

	/**
	 * //隐藏用户名
	 */
	private String userNameSecret;
	private String userIdMD5;
	/**
	 * //加密用户名
	 */
	private String userNameEncrypt;

	/**
	 * 专区类型【0：普通专区，1：新手专区】
	 */
	private Integer areaType;
	/**
	 * 新手专区转普通专区时间
	 */
	private Date areaChangeTime;
	/**
	 * 红包金额
	 */
	private BigDecimal redMoney;

	public Integer getAreaType() {
		return areaType;
	}

	public void setAreaType(Integer areaType) {
		this.areaType = areaType;
	}

	public Date getAreaChangeTime() {
		return areaChangeTime;
	}

	public void setAreaChangeTime(Date areaChangeTime) {
		this.areaChangeTime = areaChangeTime;
	}

	public String getUserIdMD5() {
		return userIdMD5;
	}

	public void setUserIdMD5(String userIdMD5) {
		this.userIdMD5 = userIdMD5;
	}

	/**
	 * 投宝方式
	 */
	private String tenderType;// 认购方式【0：手动投宝，1：自动投宝】，设默认值为0

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getYqsyStr() {
		if (this.getAccount() != null && this.getApr() != null) {
			return BusinessConstants.decimalPercentDf.format(this.getAccount().multiply(this.getApr())
					.divide(new BigDecimal(1200), 3, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(this.getLockLimit())));
		}
		return "";
	}

	public String getAprStr() {
		return apr + "%";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getApr() {
		return apr;
	}

	public void setApr(BigDecimal apr) {
		this.apr = apr;
	}

	public Integer getLockLimit() {
		return lockLimit;
	}

	public void setLockLimit(Integer lockLimit) {
		this.lockLimit = lockLimit;
	}

	public String getAddTimeStr() {
		if (this.getAddTime() != null)
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.getAddTime());
		return "";
	}

	public BigDecimal getSumAccount() {
		return sumAccount;
	}

	public void setSumAccount(BigDecimal sumAccount) {
		this.sumAccount = sumAccount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	// 加入金额
	public String getAccountStr() {
		return BusinessConstants.decimalPercentDf.format(this.getAccount());

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

	public void setUserNameSecret(String userNameSecret) {
		this.userNameSecret = userNameSecret;
	}

	public void setUserNameEncrypt(String userNameEncrypt) {
		this.userNameEncrypt = userNameEncrypt;
	}

	public String getTenderType() {
		return tenderType;
	}

	public void setTenderType(String tenderType) {
		this.tenderType = tenderType;
	}

	public BigDecimal getRedMoney() {
		return redMoney;
	}

	public void setRedMoney(BigDecimal redMoney) {
		this.redMoney = redMoney;
	}
}
