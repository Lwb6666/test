package com.dxjr.base.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * <p>
 * Description:自动投标规则实体类<br />
 * </p>
 * 
 * @title AutoInvestConfig.java
 * @package com.dxjr.base.entity
 * @author yangshijin
 * @version 0.1 2014年5月19日
 */
public class AutoInvestConfig implements Serializable {
	private static final long serialVersionUID = 4351752556031464800L;

	/**
	 * 主键Id
	 */
	private Integer id;

	/**
	 * 用户Id
	 */
	private int user_id;

	/**
	 * 是否启用 （0：不启用，1：启用，2：删除）
	 */
	private int status;

	/**
	 * 自动投标方式 (1：按金额投标，2：按比例投标， 3：按余额投标)
	 */
	private int tender_type;

	/**
	 * 借款还款方式（0：没有限制， 1：按月分期还款，2：按月付息到期还本 , 3：到期还本付息，4：按天还款）
	 */
	private int borrow_type;

	/**
	 * 借款期限无限定状态(0：未选中，1：已选中)
	 */
	private int timelimit_status;

	/**
	 * 最短借款月数
	 */
	private int min_time_limit;

	/**
	 * 最长借款月数
	 */
	private int max_time_limit;

	/**
	 * 最短借款天数
	 */
	private int min_day_limit;

	/**
	 * 最长借款天数
	 */
	private int max_day_limit;

	/**
	 * 单笔最低投标金额
	 */
	private BigDecimal min_tender_account;

	/**
	 * 自动投标金额
	 */
	private BigDecimal tender_account_auto;

	/**
	 * 投标比例
	 */
	private double tender_scale;

	/**
	 * 投标奖励比例，默认为0，表示不考虑是否有简历 （该字段作废）
	 */
	private BigDecimal award_flag;

	/**
	 * 当余额不足的情况下如何处理 1、余额全部投 2、不参与本次投标
	 */
	private int balance_not_enough;

	/**
	 * 时间，默认系统生成时间
	 */
	private String settime;

	/**
	 * Ip地址、系统抓起
	 */
	private String setip;

	/**
	 * 最小年化收益率
	 */
	private BigDecimal min_apr;

	/**
	 * 最大年化收益率
	 */
	private BigDecimal max_apr;

	/**
	 * 推荐标状态（0：无，1：选中）
	 */
	private int borrow_type1_status;

	/**
	 * 抵押标状态（0：无，1：选中）
	 */
	private int borrow_type2_status;

	/**
	 * 净值标状态（0：无，1：选中）
	 */
	private int borrow_type3_status;

	/**
	 * 秒标状态（0：无，1：选中）
	 */
	private int borrow_type4_status;

	/**
	 * 排队时间
	 */
	private String uptime;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 担保标状态（0：无，1：选中）
	 */
	private int borrow_type5_status;

	/**
	 * 自动类型（1：按抵押标、担保标投标，2：按净值标、信用标投标）
	 */
	private Integer autoType;

	/**
	 * VIP会员等级【1：终身顶级会员会员，0：普通vip会员】
	 */
	private Integer vipLevel;

	/**
	 * 平台来源(1：网页 2：微信 3：安卓端 4： IOS端)
	 */
	private Integer platform;
	
	/**
	 * 是否使用活期宝（0：未使用；1：使用；）
	 */
	private Integer isUseCur;	
	
	private Integer custodyFlag;//自动投标类型  1：只投非存管，2只投存管,3:所有
	
	private String mobileCode;//短信验证码
	
	
	
	public String getMobileCode() {
		return mobileCode;
	}

	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}

	public Integer getCustodyFlag() {
		return custodyFlag;
	}

	public void setCustodyFlag(Integer custodyFlag) {
		this.custodyFlag = custodyFlag;
	}

	public Integer getIsUseCur() {
		return isUseCur;
	}

	public void setIsUseCur(Integer isUseCur) {
		this.isUseCur = isUseCur;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTender_type() {
		return tender_type;
	}

	public void setTender_type(int tender_type) {
		this.tender_type = tender_type;
	}

	public int getBorrow_type() {
		return borrow_type;
	}

	public void setBorrow_type(int borrow_type) {
		this.borrow_type = borrow_type;
	}

	public int getTimelimit_status() {
		return timelimit_status;
	}

	public void setTimelimit_status(int timelimit_status) {
		this.timelimit_status = timelimit_status;
	}

	public int getMin_time_limit() {
		return min_time_limit;
	}

	public void setMin_time_limit(int min_time_limit) {
		this.min_time_limit = min_time_limit;
	}

	public int getMax_time_limit() {
		return max_time_limit;
	}

	public void setMax_time_limit(int max_time_limit) {
		this.max_time_limit = max_time_limit;
	}

	public int getMin_day_limit() {
		return min_day_limit;
	}

	public void setMin_day_limit(int min_day_limit) {
		this.min_day_limit = min_day_limit;
	}

	public int getMax_day_limit() {
		return max_day_limit;
	}

	public void setMax_day_limit(int max_day_limit) {
		this.max_day_limit = max_day_limit;
	}

	public BigDecimal getTender_account_auto() {
		return tender_account_auto;
	}

	public void setTender_account_auto(BigDecimal tender_account_auto) {
		this.tender_account_auto = tender_account_auto;
	}

	public BigDecimal getAward_flag() {
		return award_flag;
	}

	public void setAward_flag(BigDecimal award_flag) {
		this.award_flag = award_flag;
	}

	public int getBalance_not_enough() {
		return balance_not_enough;
	}

	public void setBalance_not_enough(int balance_not_enough) {
		this.balance_not_enough = balance_not_enough;
	}

	public String getSettime() {
		return settime;
	}

	public void setSettime(String settime) {
		this.settime = settime;
	}

	public String getSetip() {
		return setip;
	}

	public void setSetip(String setip) {
		this.setip = setip;
	}

	public BigDecimal getMin_apr() {
		return min_apr;
	}

	public void setMin_apr(BigDecimal min_apr) {
		this.min_apr = min_apr;
	}

	public BigDecimal getMax_apr() {
		return max_apr;
	}

	public void setMax_apr(BigDecimal max_apr) {
		this.max_apr = max_apr;
	}

	public int getBorrow_type1_status() {
		return borrow_type1_status;
	}

	public void setBorrow_type1_status(int borrow_type1_status) {
		this.borrow_type1_status = borrow_type1_status;
	}

	public int getBorrow_type2_status() {
		return borrow_type2_status;
	}

	public void setBorrow_type2_status(int borrow_type2_status) {
		this.borrow_type2_status = borrow_type2_status;
	}

	public int getBorrow_type3_status() {
		return borrow_type3_status;
	}

	public void setBorrow_type3_status(int borrow_type3_status) {
		this.borrow_type3_status = borrow_type3_status;
	}

	public int getBorrow_type4_status() {
		return borrow_type4_status;
	}

	public void setBorrow_type4_status(int borrow_type4_status) {
		this.borrow_type4_status = borrow_type4_status;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	public BigDecimal getMin_tender_account() {
		return min_tender_account;
	}

	public void setMin_tender_account(BigDecimal min_tender_account) {
		this.min_tender_account = min_tender_account;
	}

	public double getTender_scale() {
		return tender_scale;
	}

	public void setTender_scale(double tender_scale) {
		this.tender_scale = tender_scale;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getBorrow_type5_status() {
		return borrow_type5_status;
	}

	public void setBorrow_type5_status(int borrow_type5_status) {
		this.borrow_type5_status = borrow_type5_status;
	}

	public Integer getAutoType() {
		return autoType;
	}

	public void setAutoType(Integer autoType) {
		this.autoType = autoType;
	}

	public Integer getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(Integer vipLevel) {
		this.vipLevel = vipLevel;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}
}
