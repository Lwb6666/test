package com.dxjr.portal.lottery.vo;

import com.dxjr.common.page.BaseCnd;

public class LotteryUseRecordCnd extends BaseCnd{
	private static final long serialVersionUID = -6258853088975523801L;

	private Integer id;

    private Integer userId;
    
    //获奖领取时间
    private String drawTime;
    
    //确认收货，填写remark 
    private String remark;
    
    //实物奖励 id 
    private Integer lott_id;

    /**
     * 状态【0：未领取，1：已领取，2：待处理，3：派发中，4：已过期】
     */
    private Integer status;
	
    /**
     * 奖品类型【1：现金奖励，2：实物，3：谢谢参与，4：抽奖机会】
     */
    private Integer awardType;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAwardType() {
		return awardType;
	}

	public void setAwardType(Integer awardType) {
		this.awardType = awardType;
	}

	/**
	 * @return drawTime : return the property drawTime.
	 */
	public String getDrawTime() {
		return drawTime;
	}

	/**
	 * @param drawTime : set the property drawTime.
	 */
	public void setDrawTime(String drawTime) {
		this.drawTime = drawTime;
	}

	/**
	 * @return remark : return the property remark.
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark : set the property remark.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return lott_id : return the property lott_id.
	 */
	public Integer getLott_id() {
		return lott_id;
	}

	/**
	 * @param lott_id : set the property lott_id.
	 */
	public void setLott_id(Integer lott_id) {
		this.lott_id = lott_id;
	}
	
}
