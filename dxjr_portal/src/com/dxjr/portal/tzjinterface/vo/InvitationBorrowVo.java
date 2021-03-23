package com.dxjr.portal.tzjinterface.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import org.codehaus.jackson.annotate.JsonProperty;

public class InvitationBorrowVo implements Serializable {

	/**
	 * @fields serialVersionUID
	 */
	private static final long serialVersionUID = 4604820678821135971L;
	/** 平台名称 */
	@JsonProperty("platId")
	private String platId;
	/** 标ID */
	@JsonProperty("bid")
	private String bid;
	/** 标种 */
	@JsonProperty("type")
	private String type;
	/**标的是否在开售中、0代表可以投标、1代表未开启投标*/
	@JsonProperty("isSale")
	private String isSale;
	/**标的距离开售倒计时，单位是秒*/
	@JsonProperty("countDownTime")
	private String countDownTime;
	/** 借款标标题 */
	private String title;
	/** 平台标的相关描述信息 */
	private String desc;
	/** 平台标的借款人姓名 */
	private String name;
	/** 借款金额 */
	private BigDecimal amount;
	/** 借款期限 */
	private Integer period;
	/** 借款期限单位(天月年) */
	@JsonProperty("periodUnit")
	private String periodUnit;
	/** 年利率 */
	private BigDecimal rate;
	/** 奖励 */
	private String reward;	
	/** 还款方式 */
	@JsonProperty("returnType")
	private String returnType;
	/** 借款进度 */
	@JsonProperty("progress")
	private String progress;
	
	/** 标的链接 */
	@JsonProperty("bidUrl")
	private String bidUrl;
	
	/**是否属于流转标 0代表不是、1代表是*/
	@JsonProperty("is_circulation")
	private Integer is_circulation;

	/******************************************** get() set() method ***********************************************/

	public String getPlatId() {
		return platId;
	}

	public void setPlatId(String platId) {
		this.platId = platId;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsSale() {
		return isSale;
	}

	public void setIsSale(String isSale) {
		this.isSale = isSale;
	}

	public String getCountDownTime() {
		return countDownTime;
	}

	public void setCountDownTime(String countDownTime) {
		this.countDownTime = countDownTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public String getPeriodUnit() {
		return periodUnit;
	}

	public void setPeriodUnit(String periodUnit) {
		this.periodUnit = periodUnit;
	}

	public String getReward() {
		return reward;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	public void setReward(String reward) {
		this.reward = reward;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getBidUrl() {
		return bidUrl;
	}

	public void setBidUrl(String bidUrl) {
		this.bidUrl = bidUrl;
	}

	public Integer getIs_circulation() {
		return is_circulation;
	}

	public void setIs_circulation(Integer is_circulation) {
		this.is_circulation = is_circulation;
	}

	/*************************************************************************************************************/

}
