package com.dxjr.portal.first.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.dxjr.common.Dictionary;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.MoneyUtil;

/**
 * 
 * <p>
 * Description:优先投标计划最终开通<br />
 * </p>
 * 
 * @title FirstTenderRealVo.java
 * @package com.dxjr.portal.first.vo
 * @author yangshijin
 * @version 0.1 2014年7月24日
 */
public class FirstTenderRealVo implements Serializable {
	private static final long serialVersionUID = -191815169850848319L;
	/**
	 * 主键Id
	 */
	private Integer id;
	/**
	 * 优先投标计划ID
	 */
	private Integer firstBorrowId;
	/**
	 * 用户开通金额
	 */
	private Integer account;
	/**
	 * 所占比例,直接精确到小数点位，比如：0.001，即千分之一
	 */
	private BigDecimal rate;
	/**
	 * 用户Id
	 */
	private Integer userId;
	/**
	 * 可用余额
	 */
	private BigDecimal useMoney;
	/**
	 * 计划总金额
	 */
	private Integer planAccount;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 状态 0 ：未失效 1 ：已失效
	 */
	private Integer status;
	/**
	 * 版本号.
	 */
	private String version;

	/**
	 * 添加时间
	 */
	private Date addtime;
	/**
	 * 开通类型（1：首次开通，2：上车）
	 */
	private Integer firstTenderType;

	/**
	 * 上车时间
	 */
	private Date onBusTime;

	/**
	 * 排队号
	 */
	private Integer orderNum;
	/**
	 * 直通车原始认购记录ID
	 */
	private Integer parentId;

	/**
	 * 直通车标题
	 */
	private String firstBorrowName;
	private Date firstSuccessTime;
	private String firstPeriods;
	private Integer firstStatus;
	private Date publishTime;
	private String publishTimeStr;
	private Date firstAddtime;
	private String firstAddtimeStr;
	/** 是否可以解锁 是：Y */
	private String unLockYn;
	private String useMoneyStr;
	private String statusStr;
	/** 直通车已获收益 */
	private BigDecimal firstEarnedIncome;
	/** 解锁日期 */
	private Date lockDate;

	/**
	 * 直通车成功时间
	 */
	private Date successTime;

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getPublishTimeStr() {
		return DateUtils.format(publishTime, DateUtils.YMD_DASH);
	}

	public Date getFirstAddtime() {
		return firstAddtime;
	}

	public void setFirstAddtime(Date firstAddtime) {
		this.firstAddtime = firstAddtime;
	}

	public String getFirstAddtimeStr() {
		return DateUtils.format(firstAddtime, DateUtils.YMD_DASH);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFirstBorrowId() {
		return firstBorrowId;
	}

	public void setFirstBorrowId(Integer firstBorrowId) {
		this.firstBorrowId = firstBorrowId;
	}

	public Integer getAccount() {
		return account;
	}

	public void setAccount(Integer account) {
		this.account = account;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public BigDecimal getUseMoney() {
		return useMoney;
	}

	public void setUseMoney(BigDecimal useMoney) {
		this.useMoney = useMoney;
	}

	public Integer getPlanAccount() {
		return planAccount;
	}

	public void setPlanAccount(Integer planAccount) {
		this.planAccount = planAccount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getFirstBorrowName() {
		return firstBorrowName;
	}

	public void setFirstBorrowName(String firstBorrowName) {
		this.firstBorrowName = firstBorrowName;
	}

	public String getUseMoneyStr() {
		return MoneyUtil.fmtMoneyByDot(useMoney);
	}

	public String getStatusStr() {
		return Dictionary.getValue(808, String.valueOf(status));
	}

	public Date getFirstSuccessTime() {
		return firstSuccessTime;
	}

	public void setFirstSuccessTime(Date firstSuccessTime) {
		this.firstSuccessTime = firstSuccessTime;
	}

	public String getUnLockYn() {
		if (null != addtime) {
			// 获取锁定日期 
			Date canlockEndTime = DateUtils.monthOffset(DateUtils.convert2StartDate(addtime), BusinessConstants.FIRST_UNLOCK_AFTER_MONTH);
			if ((new Date().compareTo(canlockEndTime) > 0 )
					&& status == Constants.TENDER_REAL_STATUS_AVAILABLE) {
				return "Y";
			}
		}
		return unLockYn;
	}

	public String getFirstPeriods() {
		return firstPeriods;
	}

	public void setFirstPeriods(String firstPeriods) {
		this.firstPeriods = firstPeriods;
	}

	public Integer getFirstStatus() {
		return firstStatus;
	}

	public void setFirstStatus(Integer firstStatus) {
		this.firstStatus = firstStatus;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Integer getFirstTenderType() {
		return firstTenderType;
	}

	public void setFirstTenderType(Integer firstTenderType) {
		this.firstTenderType = firstTenderType;
	}

	public Date getOnBusTime() {
		return onBusTime;
	}

	public void setOnBusTime(Date onBusTime) {
		this.onBusTime = onBusTime;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public BigDecimal getFirstEarnedIncome() {
		return firstEarnedIncome;
	}

	public void setFirstEarnedIncome(BigDecimal firstEarnedIncome) {
		this.firstEarnedIncome = firstEarnedIncome;
	}

	public Date getLockDate() {
		return lockDate;
	}

	public void setLockDate(Date lockDate) {
		this.lockDate = lockDate;
	}

	public Date getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}
}
