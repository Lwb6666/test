package com.dxjr.portal.lottery.vo;

/**
 * 
 * <p>
 * Description:抽奖结果类<br />
 * </p>
 * @title LotteryDraw.java
 * @package com.dxjr.portal.lottery.vo 
 * @author YangShiJin
 * @version 0.1 2015年4月11日
 */
public class LotteryDraw {

	/**
	 * 是否成功【success，fail】
	 */
	private String sFlag;
	/**
	 * 起始角度
	 */
	private int sAngel;
	/**
	 * 起始角度 + 默认旋转的圈数
	 */
	private int eAngel;
	/**
	 * 转动时间
	 */
	private int dtnTime;
	/**
	 * 结果内容
	 */
	private String message;
	
	/**
	 * 活动来源名称
	 */
	private String sourceTypeName;
	
	/**
	 * 最终位置
	 */
	private Integer position;
	
	/**
	 * 奖励抽奖次数
	 */
	private Integer awardNum;
	
	/**
	 * 奖品类型
	 */
	private Integer awardType;
	
	public String getsFlag() {
		return sFlag;
	}
	public void setsFlag(String sFlag) {
		this.sFlag = sFlag;
	}
	public int getsAngel() {
		return sAngel;
	}
	public void setsAngel(int sAngel) {
		this.sAngel = sAngel;
	}
	public int geteAngel() {
		return eAngel;
	}
	public void seteAngel(int eAngel) {
		this.eAngel = eAngel;
	}
	public int getDtnTime() {
		return dtnTime;
	}
	public void setDtnTime(int dtnTime) {
		this.dtnTime = dtnTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSourceTypeName() {
		return sourceTypeName;
	}
	public void setSourceTypeName(String sourceTypeName) {
		this.sourceTypeName = sourceTypeName;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public Integer getAwardNum() {
		return awardNum;
	}
	public void setAwardNum(Integer awardNum) {
		this.awardNum = awardNum;
	}
	public Integer getAwardType() {
		return awardType;
	}
	public void setAwardType(Integer awardType) {
		this.awardType = awardType;
	}
}
