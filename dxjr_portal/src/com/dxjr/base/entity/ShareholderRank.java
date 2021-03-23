package com.dxjr.base.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * Description:股东加权待收<br />
 * </p>
 * 
 * @title ShareholderRank.java
 * @package com.dxjr.base.entity
 * @author justin.xu
 * @version 0.1 2014年5月20日
 */
public class ShareholderRank implements Serializable {
	private static final long serialVersionUID = -8828964992406883929L;
	/** 主键ID **/
	private Integer id;
	/** 用户ID　 **/
	private Integer userId;
	/** 加权待收　 **/
	private BigDecimal dayInterst;
	/** 加权待收排名　 **/
	private Integer dayInterstRank;
	/** 加权待收排名得分　 **/
	private BigDecimal dayInterstScore;
	/** 累计总积分　 **/
	private Integer accumulatepoints;
	/** 累计总积分排名　 **/
	private Integer accumulatepointsRank;
	/** 累计总积分排名得分　 **/
	private BigDecimal accumulatepointsScore;
	/** 优先投标总额　 **/
	private BigDecimal firstTenderTotal;
	/** 优先投标总额排名　 **/
	private Integer firstTenderTotalRank;
	/** 优先投标总额排名得分　 **/
	private BigDecimal firstTenderTotalScore;
	/** 推广有效人数　 **/
	private Integer extensionNumber;
	/** 推广有效人数排名　 **/
	private Integer extensionNumberRank;
	/** 推广有效人数排名得分　 **/
	private BigDecimal extensionNumberScore;
	/** 综合得分　 **/
	private BigDecimal totalScore;
	/** 添加时间 **/
	private String addtime;

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

	public BigDecimal getDayInterst() {
		return dayInterst;
	}

	public void setDayInterst(BigDecimal dayInterst) {
		this.dayInterst = dayInterst;
	}

	public Integer getDayInterstRank() {
		return dayInterstRank;
	}

	public void setDayInterstRank(Integer dayInterstRank) {
		this.dayInterstRank = dayInterstRank;
	}

	public BigDecimal getDayInterstScore() {
		return dayInterstScore;
	}

	public void setDayInterstScore(BigDecimal dayInterstScore) {
		this.dayInterstScore = dayInterstScore;
	}

	public Integer getAccumulatepoints() {
		return accumulatepoints;
	}

	public void setAccumulatepoints(Integer accumulatepoints) {
		this.accumulatepoints = accumulatepoints;
	}

	public Integer getAccumulatepointsRank() {
		return accumulatepointsRank;
	}

	public void setAccumulatepointsRank(Integer accumulatepointsRank) {
		this.accumulatepointsRank = accumulatepointsRank;
	}

	public BigDecimal getAccumulatepointsScore() {
		return accumulatepointsScore;
	}

	public void setAccumulatepointsScore(BigDecimal accumulatepointsScore) {
		this.accumulatepointsScore = accumulatepointsScore;
	}

	public BigDecimal getFirstTenderTotal() {
		return firstTenderTotal;
	}

	public void setFirstTenderTotal(BigDecimal firstTenderTotal) {
		this.firstTenderTotal = firstTenderTotal;
	}

	public Integer getFirstTenderTotalRank() {
		return firstTenderTotalRank;
	}

	public void setFirstTenderTotalRank(Integer firstTenderTotalRank) {
		this.firstTenderTotalRank = firstTenderTotalRank;
	}

	public BigDecimal getFirstTenderTotalScore() {
		return firstTenderTotalScore;
	}

	public void setFirstTenderTotalScore(BigDecimal firstTenderTotalScore) {
		this.firstTenderTotalScore = firstTenderTotalScore;
	}

	public Integer getExtensionNumber() {
		return extensionNumber;
	}

	public void setExtensionNumber(Integer extensionNumber) {
		this.extensionNumber = extensionNumber;
	}

	public Integer getExtensionNumberRank() {
		return extensionNumberRank;
	}

	public void setExtensionNumberRank(Integer extensionNumberRank) {
		this.extensionNumberRank = extensionNumberRank;
	}

	public BigDecimal getExtensionNumberScore() {
		return extensionNumberScore;
	}

	public void setExtensionNumberScore(BigDecimal extensionNumberScore) {
		this.extensionNumberScore = extensionNumberScore;
	}

	public BigDecimal getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

}
