package com.dxjr.portal.first.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.dxjr.common.Dictionary;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.DateUtils;

/**
 * <p>
 * Description:直通车Vo<br />
 * </p>
 * 
 * @title FirstBorrowVo.java
 * @package com.dxjr.portal.first.vo
 * @author justin.xu
 * @version 0.1 2014年7月14日
 */
public class FirstBorrowVo implements Serializable {
	private static final long serialVersionUID = -5552272784546139026L;
	/**
	 * 主键ID
	 */
	private Integer id;
	/**
	 * 标题
	 */
	private String name;
	/**
	 * 期数
	 */
	private String periods;
	/**
	 * 期数说明
	 */
	private String periodsDesc;
	/**
	 * 状态 （0:草稿标
	 * 1：新标,审核中，2：审核不通过，3：审核通过投标中，4：满标复审中，5：满标审核通过，6：满标审核拒绝，-1：流标，-2：被撤销,-3:已过期
	 * ）
	 */
	private Integer status;
	/**
	 * 计划金额
	 */
	private Integer planAccount;
	/**
	 * 实际金额
	 */
	private Integer realAccount;
	/**
	 * 最低投标金额
	 */
	private Integer lowestAccount;
	/**
	 * 最多投标金额
	 */
	private Integer mostAccount;
	/**
	 * 锁定期限
	 */
	private Integer lockLimit;
	/**
	 * 锁定方式 （0：月）
	 */
	private Integer lockStyle;
	/**
	 * 锁定结束日期，精确到天
	 */
	private Date lockEndtime;
	/**
	 * 预期收益
	 */
	private String perceivedRate;
	/**
	 * 加入费率
	 */
	private Double addRate;
	/**
	 * 服务费率
	 */
	private Double serviceRate;
	/**
	 * 退出费率
	 */
	private Double exitRate;
	/**
	 * 满标时间
	 */
	private Date successTime;
	/**
	 * 投标有效期限分钟数
	 */
	private Integer validTime;
	/**
	 * 计划详细说明
	 */
	private String content;
	/**
	 * 完成后发送站内信，0不发送，1发送
	 */
	private Integer sendMessage;
	/**
	 * 添加时间
	 */
	private Date addtime;
	/**
	 * 添加IP
	 */
	private String addIp;
	/**
	 * 撤销人（0：发标人自己撤销，其他数字表示员工ID，-1：系统流标）
	 */
	private Integer cancelUser;
	/**
	 * 撤销时间
	 */
	private Date cancelTime;
	/**
	 * 撤销备注
	 */
	private String cancelRemark;
	/**
	 * 借款标唯一标识
	 */
	private String uuid;
	/**
	 * 合同编号
	 */
	private String contractNo;
	/**
	 * 投标密码
	 */
	private String bidPassword;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 发布时间，审核通过的时间（即用户可以投标的时间）
	 */
	private Date publishTime;
	/**
	 * 用户IO
	 */
	private Integer userId;
	/**
	 * 投标次数 满标时更新此字段
	 */
	private Integer tenderTimes;
	/**
	 * 已经借到的金额
	 */
	private Integer accountYes;
	/**
	 * 密码原文
	 */
	private String passwordSource;
	/** 版本号. **/
	private String version;
	/** 认购截止时间 */
	private Date endTime;

	/** 转换后格式 */
	private String statusStr;
	private String planAccountStr;// 以万返回千位分隔
	private String mostAccountStr;// 以万返回千位分隔
	private String lowestAccountStr;// 以万返回千位分隔
	private String planAccountFrontStr;// 以返回千位分隔
	private String mostAccountFrontStr;// 以返回千位分隔
	private String lowestAccountFrontStr;// 以返回千位分隔
	private String lockStyleStr;
	private String publishTimeStr;
	private String lockEndtimeStr;
	private String successTimeStr;
	private String planAccountSource;// 以万返回
	private String mostAccountSource;// 以万返回
	private String lowestAccountSource;// 以万返回
	private String publishTimeYmdSalhms;// 以YMD_SLAHMS返回日期，用于前台计算日期
	private String scheduleStr;
	private Boolean havePassPublishTime;
	private String percent; // 百分比

	private Boolean havePassEndTime; // 是否结束,false：未结束，true：已结束

	/** 是否使用活期宝金额 0：不使用，1：使用 */
	private String isUseCurMoney;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPeriods() {
		return periods;
	}

	public void setPeriods(String periods) {
		this.periods = periods;
	}

	public String getPeriodsDesc() {
		return periodsDesc;
	}

	public void setPeriodsDesc(String periodsDesc) {
		this.periodsDesc = periodsDesc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPlanAccount() {
		return planAccount;
	}

	public void setPlanAccount(Integer planAccount) {
		this.planAccount = planAccount;
	}

	public Integer getLowestAccount() {
		return lowestAccount;
	}

	public void setLowestAccount(Integer lowestAccount) {
		this.lowestAccount = lowestAccount;
	}

	public Integer getMostAccount() {
		return mostAccount;
	}

	public void setMostAccount(Integer mostAccount) {
		this.mostAccount = mostAccount;
	}

	public Integer getLockLimit() {
		return lockLimit;
	}

	public void setLockLimit(Integer lockLimit) {
		this.lockLimit = lockLimit;
	}

	public Integer getLockStyle() {
		return lockStyle;
	}

	public void setLockStyle(Integer lockStyle) {
		this.lockStyle = lockStyle;
	}

	public Date getLockEndtime() {
		return lockEndtime;
	}

	public void setLockEndtime(Date lockEndtime) {
		this.lockEndtime = lockEndtime;
	}

	public String getPerceivedRate() {
		return perceivedRate;
	}

	public void setPerceivedRate(String perceivedRate) {
		this.perceivedRate = perceivedRate;
	}

	public Double getAddRate() {
		return addRate;
	}

	public void setAddRate(Double addRate) {
		this.addRate = addRate;
	}

	public Double getServiceRate() {
		return serviceRate;
	}

	public void setServiceRate(Double serviceRate) {
		this.serviceRate = serviceRate;
	}

	public Double getExitRate() {
		return exitRate;
	}

	public void setExitRate(Double exitRate) {
		this.exitRate = exitRate;
	}

	public Date getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}

	public Integer getValidTime() {
		return validTime;
	}

	public void setValidTime(Integer validTime) {
		this.validTime = validTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(Integer sendMessage) {
		this.sendMessage = sendMessage;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	public Integer getCancelUser() {
		return cancelUser;
	}

	public void setCancelUser(Integer cancelUser) {
		this.cancelUser = cancelUser;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getBidPassword() {
		return bidPassword;
	}

	public void setBidPassword(String bidPassword) {
		this.bidPassword = bidPassword;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTenderTimes() {
		return tenderTimes;
	}

	public void setTenderTimes(Integer tenderTimes) {
		this.tenderTimes = tenderTimes;
	}

	public Integer getAccountYes() {
		return accountYes;
	}

	public void setAccountYes(Integer accountYes) {
		this.accountYes = accountYes;
	}

	public String getPasswordSource() {
		return passwordSource;
	}

	public void setPasswordSource(String passwordSource) {
		this.passwordSource = passwordSource;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStatusStr() {
		return Dictionary.getValue(803, String.valueOf(status));
	}

	public String getPlanAccountStr() {
		if (null != planAccount) {
			return BusinessConstants.numberDf.format(planAccount / 10000);
		}
		return planAccountStr;
	}

	public String getLockStyleStr() {
		return Dictionary.getValue(804, String.valueOf(lockStyle));
	}

	public String getPublishTimeStr() {
		if (null != publishTime) {
			return DateUtils.format(publishTime, DateUtils.YMD_HMS);
		}
		return publishTimeStr;
	}

	public String getPublishTimeYmdSalhms() {
		if (null != publishTime) {
			return DateUtils.format(publishTime, DateUtils.YMD_SLAHMS);
		}
		return publishTimeYmdSalhms;
	}

	public String getLockEndtimeStr() {
		if (null != lockEndtime) {
			return DateUtils.format(lockEndtime, DateUtils.YMD_DASH);
		}
		return lockEndtimeStr;
	}

	public String getSuccessTimeStr() {
		if (null != successTime) {
			return DateUtils.format(successTime, DateUtils.YMD_HMS);
		}
		return successTimeStr;
	}

	public String getPlanAccountSource() {
		if (null != planAccount) {
			return String.valueOf(new BigDecimal(planAccount).divide(new BigDecimal(10000)));
		}
		return planAccountSource;
	}

	public String getMostAccountSource() {
		if (null != mostAccount) {
			return String.valueOf(new BigDecimal(mostAccount).divide(new BigDecimal(10000)));
		}
		return mostAccountSource;
	}

	public String getLowestAccountSource() {
		if (null != lowestAccount) {
			return String.valueOf(new BigDecimal(lowestAccount).divide(new BigDecimal(10000)));
		}
		return lowestAccountSource;
	}

	public String getMostAccountStr() {
		if (null != mostAccount) {
			return BusinessConstants.decimalPercentDf.format(new BigDecimal(mostAccount).divide(new BigDecimal(10000)));
		}
		return mostAccountStr;
	}

	public String getLowestAccountStr() {
		if (null != lowestAccount) {
			return BusinessConstants.decimalPercentDf.format(new BigDecimal(lowestAccount).divide(new BigDecimal(10000)));
		}
		return lowestAccountStr;
	}

	public String getPlanAccountFrontStr() {
		if (null != planAccount) {
			return BusinessConstants.numberDf.format(planAccount);
		}
		return planAccountFrontStr;
	}

	public String getMostAccountFrontStr() {
		if (null != mostAccount) {
			return BusinessConstants.numberDf.format(mostAccount);
		}
		return mostAccountFrontStr;
	}

	public String getLowestAccountFrontStr() {
		if (null != lowestAccount) {
			return BusinessConstants.numberDf.format(lowestAccount);
		}
		return lowestAccountFrontStr;
	}

	public String getScheduleStr() {
		scheduleStr = "0.00%";
		if (null != accountYes && null != planAccount) {
			BigDecimal schedule = new BigDecimal(accountYes).multiply(new BigDecimal(100)).divide(new BigDecimal(planAccount),
					BusinessConstants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_DOWN);
			scheduleStr = String.valueOf(schedule) + "%";
		}
		return scheduleStr;
	}

	public Boolean getHavePassPublishTime() {
		if (null != publishTime) {
			if (new Date().compareTo(publishTime) > 0) {
				return true;
			}
		}
		return false;
	}

	public Integer getRealAccount() {
		return realAccount;
	}

	public void setRealAccount(Integer realAccount) {
		this.realAccount = realAccount;
	}

	public String getPercent() {
		if (status == 3) {
			if (accountYes == null || accountYes == 0) {
				return "0";
			} else if (accountYes == planAccount) {
				return "100";
			} else {
				return String.valueOf((int) accountYes * 100 / planAccount);
			}
		}
		if (status == 5) {
			if (realAccount == null || realAccount == 0) {
				return "0";
			} else if (realAccount == planAccount) {
				return "100";
			} else {
				return String.valueOf((int) realAccount * 100 / planAccount);
			}
		}
		return "0";
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Boolean getHavePassEndTime() {
		if (null != endTime) {
			if (new Date().compareTo(endTime) > 0) {
				return true;
			}
		}
		return false;
	}

	public void setHavePassEndTime(Boolean havePassEndTime) {
		this.havePassEndTime = havePassEndTime;
	}

	public String getIsUseCurMoney() {
		return isUseCurMoney;
	}

	public void setIsUseCurMoney(String isUseCurMoney) {
		this.isUseCurMoney = isUseCurMoney;
	}

}
