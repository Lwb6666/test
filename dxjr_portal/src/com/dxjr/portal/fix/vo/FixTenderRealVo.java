package com.dxjr.portal.fix.vo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dxjr.base.entity.FixTenderReal;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.DateUtils;

/**
 * <p>
 * Description:定期宝认购明细类<br />
 * </p>
 * @title FixTenderDetailVo.java
 * @package com.dxjr.portal.fix1.vo
 * @author caodefeng
 * @version 0.1 2015年5月14日
 */
public class FixTenderRealVo extends FixTenderReal {

	/**
	 * 用户认购总额
	 */
	private BigDecimal sumAccount;

	/**
	 * 投标次数
	 */
	private Integer cnt;

	/**
	 * 用户名字
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

	private Date lockEndTime;
	/**
	 * 投宝方式
	 */
	private String tenderType;//认购方式【0：手动投宝，1：自动投宝】，设默认值为0
	/**
	 * 红包金额
	 */
	private BigDecimal redMoney;
	private BigDecimal hasReturnMoney;
	private Integer lockLimit;

	private String contractNo;
	
	/**
	 * 专区类型【0：普通专区，1：新手专区】
	 */
	private Integer areaType;
	/**
	 * 新手专区转普通专区时间
	 */
	private Date areaChangeTime;	
	/**
	 * 满宝时间
	 */
	private Date successTime;	

	/**
	 * 预计还款日期
	 */
	private Date repayTime; 
	
	/**
	 * 实际还款时间
	 */
	private Date repayYestime; 
	
	/**
	 * 退出类型【1：到期退出，2：提前退出】
	 */
	private Integer exitType;
	
	private Date exitAddtime;
	
	private BigDecimal exitInterestReal;
	
	private BigDecimal exitFee;
	
	private BigDecimal sumMoney;
	
	/**
	 * 加息年化利率
	 */
	private BigDecimal floatApr;
	
	/**
	 * 是否发放加息券  1：已发 0：未发
	 */
	private Integer isFloatCoupon;
	
	/**
	 * 加息利息
	 */
	private BigDecimal floatInterest;
	/**
	 * 预期收益
	 */
	private String yqsyStr;
	
	/**
	 * 已赚利息
	 */
	private String hasReturnMoneyStr;
	
	/**
	 * 加息本金
	 */
	private BigDecimal floatCapital;
	
	public Date getSuccessTime() {
		return successTime;
	}


	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}

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

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getHasReturnMoneyStr() {
		if (hasReturnMoney != null) {
			if (floatInterest != null) {
				return BusinessConstants.decimalPercentDf.format(hasReturnMoney.add(floatInterest));
			}else {
				return BusinessConstants.decimalPercentDf.format(hasReturnMoney);
			}
		}
		return "";

	}
	
	public void setHasReturnMoneyStr(String hasReturnMoneyStr) {
		this.hasReturnMoneyStr = hasReturnMoneyStr;
	}


	public Date getLockEndTime() {
		return lockEndTime;
	}

	public void setLockEndTime(Date lockEndTime) {
		this.lockEndTime = lockEndTime;
	}

	public String getLockEndTimeStr() {

		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lockEndTime);
		return date;
	}

	public int getRemainDay() {
		try {
			return DateUtils.dayDiffByDay(lockEndTime, new Date());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public BigDecimal getHasReturnMoney() {
		return hasReturnMoney;
	}

	public void setHasReturnMoney(BigDecimal hasReturnMoney) {
		this.hasReturnMoney = hasReturnMoney;
	}

	public String getYqsyStr() {
		if (floatInterest != null) {
			yqsyStr = BusinessConstants.decimalPercentDf.format(this.getAccount().multiply(this.getApr()).divide(new BigDecimal(1200), 3, BigDecimal.ROUND_HALF_UP)
					.multiply(new BigDecimal(this.getLockLimit())).add(floatInterest));
		}else {
			yqsyStr = BusinessConstants.decimalPercentDf.format(this.getAccount().multiply(this.getApr()).divide(new BigDecimal(1200), 3, BigDecimal.ROUND_HALF_UP)
					.multiply(new BigDecimal(this.getLockLimit())));
		}
		return yqsyStr;
	}
	
	public void setYqsyStr(String yqsyStr) {
		this.yqsyStr = yqsyStr;
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

	public BigDecimal getSumAccount() {
		return sumAccount;
	}

	public void setSumAccount(BigDecimal sumAccount) {
		this.sumAccount = sumAccount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	// 加入金额
	public String getAccountStr() {
		return BusinessConstants.decimalPercentDf.format(this.getAccount());

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


	public Date getRepaymentTime() {
		return repayTime;
	}


	public void setRepaymentTime(Date repaymentTime) {
		this.repayTime = repaymentTime;
	}


	public Date getRepaymentYestime() {
		return repayYestime;
	}


	public void setRepaymentYestime(Date repaymentYestime) {
		this.repayYestime = repaymentYestime;
	}


	public Date getRepayTime() {
		return repayTime;
	}


	public void setRepayTime(Date repayTime) {
		this.repayTime = repayTime;
	}


	public Date getRepayYestime() {
		return repayYestime;
	}


	public void setRepayYestime(Date repayYestime) {
		this.repayYestime = repayYestime;
	}


	public Integer getExitType() {
		if(repayTime != null && repayYestime != null){
			// 应还日期大于实际还款日期
			if (DateUtils.formateDate(repayTime, DateUtils.YMD_DASH).compareTo(DateUtils.formateDate(repayYestime, DateUtils.YMD_DASH)) == 1) {
				return 2;
			}else {
				return 1;
			}
		}
		return exitType;
	}

	public void setExitType(Integer exitType) {
		this.exitType = exitType;
	}

	public Date getExitAddtime() {
		return exitAddtime;
	}

	public void setExitAddtime(Date exitAddtime) {
		this.exitAddtime = exitAddtime;
	}

	public BigDecimal getExitInterestReal() {
		return exitInterestReal;
	}

	public void setExitInterestReal(BigDecimal exitInterestReal) {
		this.exitInterestReal = exitInterestReal;
	}

	public BigDecimal getExitFee() {
		return exitFee;
	}

	public void setExitFee(BigDecimal exitFee) {
		this.exitFee = exitFee;
	}


	public BigDecimal getSumMoney() {
		return sumMoney;
	}


	public void setSumMoney(BigDecimal sumMoney) {
		this.sumMoney = sumMoney;
	}

	public BigDecimal getFloatApr() {
		return floatApr;
	}

	public void setFloatApr(BigDecimal floatApr) {
		this.floatApr = floatApr;
	}

	public Integer getIsFloatCoupon() {
		return isFloatCoupon;
	}

	public void setIsFloatCoupon(Integer isFloatCoupon) {
		this.isFloatCoupon = isFloatCoupon;
	}


	public BigDecimal getFloatInterest() {
		return floatInterest;
	}


	public void setFloatInterest(BigDecimal floatInterest) {
		this.floatInterest = floatInterest;
	}


	public BigDecimal getFloatCapital() {
		return floatCapital;
	}


	public void setFloatCapital(BigDecimal floatCapital) {
		this.floatCapital = floatCapital;
	}
}
