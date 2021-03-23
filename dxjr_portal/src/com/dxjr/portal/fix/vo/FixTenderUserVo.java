package com.dxjr.portal.fix.vo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dxjr.base.entity.FixTenderUser;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.DateUtils;

/**
 * <p>
 * Description:定期宝认购明细类<br />
 * </p>
 * 
 * @title FixTenderDetailVo.java
 * @package com.dxjr.portal.fix1.vo
 * @author caodefeng
 * @version 0.1 2015年5月14日
 */
public class FixTenderUserVo extends FixTenderUser {
	
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
	
	private BigDecimal hasReturnMoney;
	
	
	private Integer lockLimit;
	
	 
    private String contractNo;
    
    private Integer tenderStatus; //借款标状态
    
    
	
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public String getHasReturnMoneyStr(){
	
		return BusinessConstants.decimalPercentDf.format(hasReturnMoney);

	}


	public Date getLockEndTime() {
		return lockEndTime;
	}


	public void setLockEndTime(Date lockEndTime) {
		this.lockEndTime = lockEndTime;
	}

	public String getLockEndTimeStr() {
		
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lockEndTime);
		return  date ;
	}

	
public String getAddTimeStr() {
		
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.getAddTime());
		return  date ;
	}
	public int getRemainDay() {
		return DateUtils.dayDiff( lockEndTime,new Date());
	}



	public BigDecimal getHasReturnMoney() {
		return hasReturnMoney;
	}


	public void setHasReturnMoney(BigDecimal hasReturnMoney) {
		this.hasReturnMoney = hasReturnMoney;
	}


	public String getYqsyStr() {
 
		return BusinessConstants.decimalPercentDf.format(this.getAccount().multiply(this.getApr()).divide(new BigDecimal(1200)).multiply(new BigDecimal(this.getLockLimit())));
	}

	public String getAprStr() {
		return apr+"%";
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

	public Integer getTenderStatus() {
		return tenderStatus;
	}

	public void setTenderStatus(Integer tenderStatus) {
		this.tenderStatus = tenderStatus;
	}
	public String getTenderStatusStr() {
		
		if(tenderStatus==Constants.BORROW_STATUS_DRAFT_CODE){
			return  "草稿标";
		}else if(tenderStatus==Constants.BORROW_STATUS_NEW_CODE){
			return  "新标审核中";
		}else if(tenderStatus==Constants.BORROW_STATUS_TEND_CODE){
			return  "投标中";
		}else if(tenderStatus==Constants.BORROW_STATUS_SUCCESS_CODE){
			return  "满标复审中";
		}else if(tenderStatus==Constants.BORROW_STATUS_REPAY_CODE){
			return  "还款中";
		}else if(tenderStatus==Constants.BORROW_STATUS_END_CODE){
			return  "还款结束";
		}else if(tenderStatus==Constants.BORROW_STATUS_FAILING_CODE){
			return  "流标";
		}else if(tenderStatus==Constants.BORROW_STATUS_REVOCATION_CODE){
			return  "被撤销";
		}else if(tenderStatus==Constants.BORROW_STATUS_NOPASS_CODE){
			return  "审核失败";
		}else if(tenderStatus==Constants.BORROW_STATUS_WEBPAY_CODE){
			return  "已垫付";
		}
		return "不存在";
	}
}
