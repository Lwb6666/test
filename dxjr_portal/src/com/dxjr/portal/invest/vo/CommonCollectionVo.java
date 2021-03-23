package com.dxjr.portal.invest.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.dxjr.base.entity.BCollectionRecord;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.repayment.vo.BRepaymentRecordVo;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.MoneyUtil;

public class CommonCollectionVo extends BCollectionRecord implements Serializable {
	/**
	 * @fields serialVersionUID
	 */

	/** begin add by hujianpan */
	private BigDecimal apr;
	private Integer timeLimit;
	private String name;
	private Integer borrowId;
	private Integer status;
	private String userName;
	private String tenderType;
	private Integer iscustody;  //'存管方式(0：非存管，1：浙商存管)',
	private String ishowfloat;
    private BigDecimal yearApr;
	
	public String getIshowfloat() {
        /**
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date activityTimeStart=sdf.parse("2016-07-01 00:00:00");
			Date activityTimeend=sdf.parse("2016-07-31 23:59:59");
			if(StringUtils.isNotEmpty(getTenderType())&&getTenderType().equals("1")){
				if(DateUtils.timeStampToDate(getTenderTime()).before(activityTimeStart)||DateUtils.timeStampToDate(getTenderTime()).after(activityTimeend)){
					ishowfloat="0";
				}else{
					ishowfloat="1";
				}
			}else{
				ishowfloat="0";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
         **/
		return ishowfloat;
	}

	public void setIshowfloat(String ishowfloat) {
		this.ishowfloat = ishowfloat;
	}

	public String getTenderType() {
		return tenderType;
	}

	public void setTenderType(String tenderType) {
		this.tenderType = tenderType;
	}

	public Integer getIscustody() {
		return iscustody;
	}

	public void setIscustody(Integer iscustody) {
		this.iscustody = iscustody;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public Integer getStatus() {
		return status;
	}

	@Override
	public void setStatus(Integer status) {
		this.status = status;
	}

	private Integer vipApp_passed;

	private String nameStr;
	private String borrowTypeStr;
	private String time_limitStr;
	private String repay_timeStr;
	private String statusStr;
	private String borrowStyle;
	private String aprStr;
	private String tenderTime; // 投标时间
	private Integer parentId; // 债权转让id
	private String first_name; // 直通车名称
	private String first_periods; // 直通车期数
	private String first_periodsStr; // 直通车期数
	private String tenderTimeStr;
	private String advancetimeStr;
	private String capitalStr;
	private String repayAccountStr;
	private String interestStr;
	private String lateInterestStr;// 逾期罚息
	private String borrowOrder;// 总共的期数
	/** 实收总额 */
	private BigDecimal getYesAccount;
	private String getYesAccountStr;
	/** 利息管理费 */
	private BigDecimal inverestFee;
	private String inverestFeeStr;
	/** 实收利息 */
	private BigDecimal yesInterest;
	private String yesInterestStr;

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getBorrowOrder() {
		return borrowOrder;
	}

	public void setBorrowOrder(String borrowOrder) {
		this.borrowOrder = borrowOrder;
	}

	public String getLateInterestStr() {
		return MoneyUtil.roundMoney(this.getLateInterest());
	}

	public String getRepayAccountStr() {
		return MoneyUtil.roundMoney(this.getRepayAccount());
	}

	public String getInterestStr() {
		return MoneyUtil.roundMoney(this.getInterest());
	}

	public BigDecimal getApr() {
		return apr;
	}

	public void setApr(BigDecimal apr) {
		this.apr = apr;
	}

	public Integer getTimeLimit() {
		return timeLimit;
	}

	public Integer getVipApp_passed() {
		return vipApp_passed;
	}

	public void setVipApp_passed(Integer vipApp_passed) {
		this.vipApp_passed = vipApp_passed;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	public String getAprStr() {
		return apr + "%";
	}

	public void setAprStr(String aprStr) {
		this.aprStr = aprStr;
	}

	public String getCapitalStr() {
		return MoneyUtil.roundMoney(this.getCapital());
	}

	public String getNameStr() {
		if (null != name && !"".equals(name)) {
			return name;
		}
		return "";
	}

	public void setNameStr(String nameStr) {
		this.nameStr = nameStr;
	}

	public String getBorrowTypeStr() {
		String result = "";
		if (null != this.getBorrowVo().getBorrowtype() && !"".equals(this.getBorrowVo().getBorrowtype())) {
			if (Integer.valueOf("1").equals(this.getBorrowVo().getBorrowtype())) {
				return "推荐标";
			} else if (Integer.valueOf("2").equals(this.getBorrowVo().getBorrowtype())) {
				return "抵押标";
			} else if (Integer.valueOf("3").equals(this.getBorrowVo().getBorrowtype())) {
				return "净值标";
			} else if (Integer.valueOf("4").equals(this.getBorrowVo().getBorrowtype())) {
				return "秒标";
			} else if (Integer.valueOf("5").equals(this.getBorrowVo().getBorrowtype())) {
				return "担保标";
			}
		}
		return result;
	}

	public void setBorrowTypeStr(String borrowTypeStr) {
		this.borrowTypeStr = borrowTypeStr;
	}

	public String getTime_limitStr() {
		if (this.getBorrowVo().getBorrowtype() == 4) {
			return "秒还";
		} else if (this.getBorrowVo().getBorrowtype() != 4 && this.getBorrowVo().getStyle() != 4) {
			return timeLimit + "个月";
		} else if (this.getBorrowVo().getBorrowtype() != 4 && this.getBorrowVo().getStyle() == 4) {
			return timeLimit + "天";
		}
		return "数据异常";
	}

	public String getRepay_timeStr() {
		if (null != this.getRepayTime()) {
			return DateUtils.TimeStamp2Date(this.getRepayTime());
		}
		return repay_timeStr;
	}

	public void setCapitalStr(String capitalStr) {
		this.capitalStr = capitalStr;
	}

	public void setRepay_timeStr(String repay_timeStr) {
		this.repay_timeStr = repay_timeStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getStatusStr() {
		if (this.getStatus() == 1) {
			return "已还款";
		} else if (this.getStatus() == 2) {
			return "垫付完成";
		} else if (this.getStatus() == 3) {
			return "已还款";
		} else if (this.getStatus() == 0) {
			if (this.getLateDays() > 0) {
				return "逾期中";
			} else if (this.getLateDays() == 0) {
				return "未还款";
			}
		}
		return statusStr;
	}

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public void setTime_limitStr(String time_limitStr) {
		this.time_limitStr = time_limitStr;
	}

	public String getTenderTime() {
		return tenderTime;
	}

	public void setTenderTime(String tenderTime) {
		this.tenderTime = tenderTime;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getFirst_periods() {
		return first_periods;
	}

	public void setFirst_periods(String first_periods) {
		this.first_periods = first_periods;
	}

	public String getTenderTimeStr() {
		if (null != tenderTime) {
			return DateUtils.TimeStamp2Date(tenderTime);
		}
		return tenderTimeStr;
	}

	public void setTenderTimeStr(String tenderTimeStr) {
		this.tenderTimeStr = tenderTimeStr;
	}

	public String getAdvancetimeStr() {
		if (null != this.getAdvancetime()) {
			return DateUtils.format(this.getAdvancetime(), DateUtils.YMD_DASH);
		} else if (null != this.getRepayYestime()) {

			return DateUtils.TimeStamp2Date(this.getRepayYestime());
		}
		return advancetimeStr;
	}

	public void setAdvancetimeStr(String advancetimeStr) {
		this.advancetimeStr = advancetimeStr;
	}

	public String getFirst_periodsStr() {
		if (null != first_periods) {
			return "第" + first_periods + "期";
		} else {
			return "";
		}

	}

	public void setFirst_periodsStr(String first_periodsStr) {
		this.first_periodsStr = first_periodsStr;
	}

	/** end add by hujianpan */

	private static final long serialVersionUID = -8899762280740005578L;
	/** 借款标 */
	private BorrowVo borrowVo;
	/** 待还记录 */
	private BRepaymentRecordVo brepaymentRecordVo;

	public BorrowVo getBorrowVo() {
		return borrowVo;
	}

	public void setBorrowVo(BorrowVo borrowVo) {
		this.borrowVo = borrowVo;
	}

	public BRepaymentRecordVo getBrepaymentRecordVo() {
		return brepaymentRecordVo;
	}

	public void setBrepaymentRecordVo(BRepaymentRecordVo brepaymentRecordVo) {
		this.brepaymentRecordVo = brepaymentRecordVo;
	}

	public BigDecimal getGetYesAccount() {
		return getYesAccount;
	}

	public void setGetYesAccount(BigDecimal getYesAccount) {
		this.getYesAccount = getYesAccount;
	}

	public String getGetYesAccountStr() {
		return MoneyUtil.roundMoney(this.getGetYesAccount());
	}

	public void setGetYesAccountStr(String getYesAccountStr) {
		this.getYesAccountStr = getYesAccountStr;
	}

	public BigDecimal getInverestFee() {
		return inverestFee;
	}

	public void setInverestFee(BigDecimal inverestFee) {
		this.inverestFee = inverestFee;
	}

	public String getInverestFeeStr() {
		return MoneyUtil.roundMoney(this.getInverestFee());
	}

	public void setInverestFeeStr(String inverestFeeStr) {
		this.inverestFeeStr = inverestFeeStr;
	}

	public BigDecimal getYesInterest() {
		return yesInterest;
	}

	public void setYesInterest(BigDecimal yesInterest) {
		this.yesInterest = yesInterest;
	}

	public String getYesInterestStr() {
		return MoneyUtil.roundMoney(this.getYesInterest());
	}

	public void setYesInterestStr(String yesInterestStr) {
		this.yesInterestStr = yesInterestStr;
	}

    public BigDecimal getYearApr() {
        return yearApr;
    }

    public void setYearApr(BigDecimal yearApr) {
        this.yearApr = yearApr;
    }
}
