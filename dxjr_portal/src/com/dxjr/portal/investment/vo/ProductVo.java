package com.dxjr.portal.investment.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @User : xiaobaicai
 * @Date : 2016/1/4 14:48
 * @From : apiDemo
 */
public class ProductVo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7616016507721471262L;

	// 平台自己产品的Id
    private Integer relationId;

    // 平台logo
    private String platformLogo;

    // 产品名称
    private String name;

    // 总金额(元)
    private Double totalFee;

    // 完成金额 (元)
    private Double finishFee;

    // 还款方式
    private String repayment;

    // 借款时间 (单位：天)
    private Integer borrowTime;

    // 起投金额
    private Double minBorrowMoney;

    // 资金保障
    private String fundsSafeguard;

    // 年化收益率
    private Float annual;

    // 开始时间
    private Date startTime;

    // 结束时间
    private Date endTime;

    // 产品网站连接地址
    private String website;
    
    //产品详情页手机端的地址
    private String mobileWebsite;

    // 是否已结束 1.未结束 0.已结束
    private Integer isOver;

    // 是否显示标 1.显示 0 不显示 默认为1
    private Integer state = 1;
    

    public String getMobileWebsite() {
		return mobileWebsite;
	}

	public void setMobileWebsite(String mobileWebsite) {
		this.mobileWebsite = mobileWebsite;
	}

	public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public String getPlatformLogo() {
        return platformLogo;
    }

    public void setPlatformLogo(String platformLogo) {
        this.platformLogo = platformLogo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepayment() {
        return repayment;
    }

    public void setRepayment(String repayment) {
        this.repayment = repayment;
    }

    public Integer getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Integer borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getFundsSafeguard() {
        return fundsSafeguard;
    }

    public void setFundsSafeguard(String fundsSafeguard) {
        this.fundsSafeguard = fundsSafeguard;
    }

    public Float getAnnual() {
        return annual;
    }

    public void setAnnual(Float annual) {
        this.annual = annual;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getIsOver() {
        return isOver;
    }

    public void setIsOver(Integer isOver) {
        this.isOver = isOver;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	public Double getFinishFee() {
		return finishFee;
	}

	public void setFinishFee(Double finishFee) {
		this.finishFee = finishFee;
	}

	public Double getMinBorrowMoney() {
		return minBorrowMoney;
	}

	public void setMinBorrowMoney(Double minBorrowMoney) {
		this.minBorrowMoney = minBorrowMoney;
	}
}
