package com.dxjr.portal.borrow.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.dxjr.base.entity.Borrow;
import com.dxjr.common.Dictionary;
import com.dxjr.portal.util.SimpleMoneyFormat;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.StrinUtils;

/**
 * <p>
 * Description:借款标<br />
 * </p>
 * 
 * @title BorrowVo.java
 * @package com.dxjr.portal.borrow.vo
 * @author justin.xu
 * @version 0.1 2014年5月15日
 */
public class BorrowVo extends Borrow implements Serializable {

	private static final long serialVersionUID = -662710165519685271L;
	private static DecimalFormat df = new DecimalFormat("#,##0.##");
	// 临时属性.............
	/** 进度 **/
	private String scheduleStr;
	/** 借款人用户名 **/
	private String userName;
	/** 还款时间 **/
	private String repaymentTime;
	/** 借款人头像 **/
	private String headimg;
	/** 还款方式 **/
	private String styleStr;
	/** 定时发标时间 */
	private String timingBorrowTimeStr;
	/** 发布时间 */
	private String publishTimeStr;
	private Date publishTimeDate;
	/** 借款人姓名 */
	private String borrowUserName;
	/** 信用等级 */
	private String creditRating;

	private Date successTimeDate;
	private Date endTimeDate;
	/** 金额转换为大写 */
	private String accountToChinese;
	/** 完整时间期限 */
	private String timelimitformat;
    private BigDecimal rbtAccount; //投标金额
	private String userNameSecret; // 隐藏用户名
	private String userNameEncrypt; // 加密用户名
	private String userIdMD5;
	private int isTransfer; // 0 :不是转让标；1 ：是转让标
	private int tenderType; // 0：手动投标，1：自动投标，2：直通车投标，3：定期宝投标，4：业务员投标
	private String ishowfloat;
    private BigDecimal yearApr;
    
	public String getUserIdMD5() {
		return userIdMD5;
	}

	public void setUserIdMD5(String userIdMD5) {
		this.userIdMD5 = userIdMD5;
	}



	private Integer tenderStatus;
	public String getIshowfloat() {
        /**
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date activityTimeStart=sdf.parse("2016-07-01 00:00:00");
			Date activityTimeend=sdf.parse("2016-07-31 23:59:59");
			if(getTenderType()==1){
				if(getAddtime().before(activityTimeStart)||getAddtime().after(activityTimeend)){
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

	/**
	 * 理财/借款用户（1 理财用户 0 借款用户）
	 */
	private Integer isFinancialUser;
	/** 是否使用活期宝金额 0：不使用，1：使用 */
	private String isUseCurMoney;

	public String getBorrowUserName() {
		return borrowUserName;
	}

	public void setBorrowUserName(String borrowUserName) {
		this.borrowUserName = borrowUserName;
	}

	@Override
	public String getCreditRating() {
		return creditRating;
	}

	@Override
	public void setCreditRating(String creditRating) {
		this.creditRating = creditRating;
	}

	/** 还款时间 **/
	private String repaymentTimeStr;

	private String addtimeStr;

	private String successTimeStr;
	private String endTimeStr;

	private String accountStr;
	private String statusStr;

	private String aprStr;

	private Integer recordId;
	
	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	/**
	 * <p>
	 * 处理Borrow类的属性
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月8日
	 * @param list
	 * @return List<BorrowIndex>
	 */
	public static List<BorrowVo> handleBorrow(List<BorrowVo> pList) {
		List<BorrowVo> list = new ArrayList<BorrowVo>();

		for (int i = 0; i < pList.size(); i++) {
			BorrowVo borrow = pList.get(i);

			if (borrow.getApr() != null) {
				String aprStr = df.format(borrow.getApr());
				borrow.setAprStr(aprStr);
			}

			String accountStr = df.format(borrow.getAccount());
			borrow.setAccountStr(accountStr);

			list.add(borrow);
		}
		return list;
	}

	public static BorrowVo handleBorrow(BorrowVo borrow) {
		if (borrow != null) {

			if (borrow.getApr() != null) {
				String aprStr = df.format(borrow.getApr()).toString();
				borrow.setAprStr(aprStr);
			}

			String accountStr = df.format(borrow.getAccount()).toString();
			borrow.setAccountStr(accountStr);
		}
		return borrow;
	}

	public String getScheduleStr() {
		if (null != super.getAccountYes()) {
			scheduleStr = df.format(super.getAccountYes().multiply(new BigDecimal(100)).divide(super.getAccount(), 2, BigDecimal.ROUND_DOWN));
		} else {
			scheduleStr = "0";
		}
		return scheduleStr;
	}

	public String getScheduleStrNoDecimal() {
		if (null != super.getAccountYes()) {
			scheduleStr = String.valueOf(super.getAccountYes().multiply(new BigDecimal(100)).divide(super.getAccount(), 0, BigDecimal.ROUND_DOWN));
		} else {
			scheduleStr = "0";
		}
		return scheduleStr;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRepaymentTime() {
		return repaymentTime;
	}

	public void setRepaymentTime(String repaymentTime) {
		this.repaymentTime = repaymentTime;
	}

	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	public String getStyleStr() {
		if (null != super.getStyle()) {
			return Dictionary.getValue(400, String.valueOf(super.getStyle()));
		}
		return styleStr;
	}

	public void setStyleStr(String styleStr) {
		this.styleStr = styleStr;
	}

	public String getTimingBorrowTimeStr() {
		if (super.getTimingBorrowTime() != null) {
			return DateUtils.timeStampToDate(super.getTimingBorrowTime(), DateUtils.YMD_HMS);
		}
		return timingBorrowTimeStr;
	}

	public void setTimingBorrowTimeStr(String timingBorrowTimeStr) {
		this.timingBorrowTimeStr = timingBorrowTimeStr;
	}

	public String getPublishTimeStr() {
		if (super.getPublishTime() != null) {
			return DateUtils.timeStampToDate(super.getPublishTime(), DateUtils.YMD_HMS);
		}
		return publishTimeStr;
	}

	public void setPublishTimeStr(String publishTimeStr) {
		this.publishTimeStr = publishTimeStr;
	}

	public Date getPublishTimeDate() {
		return publishTimeDate;
	}

	public void setPublishTimeDate(Date publishTimeDate) {
		this.publishTimeDate = publishTimeDate;
	}

	public String getRepaymentTimeStr() {
		if (repaymentTime != null) {
			return DateUtils.timeStampToDate(repaymentTime, DateUtils.YMD_HMS);
		}
		return repaymentTimeStr;
	}

	public void setRepaymentTimeStr(String repaymentTimeStr) {
		this.repaymentTimeStr = repaymentTimeStr;
	}

	public String getAddtimeStr() {
		if (super.getAddtime() != null) {
			return DateUtils.format(super.getAddtime(), DateUtils.YMD_HMS);
		}
		return addtimeStr;
	}

	public void setAddtimeStr(String addtimeStr) {
		this.addtimeStr = addtimeStr;
	}

	public String getSuccessTimeStr() {
		if (super.getSuccessTime() != null) {

			return DateUtils.timeStampToDate(super.getSuccessTime(), DateUtils.YMD_HMS);

		}
		return successTimeStr;
	}

	public void setSuccessTimeStr(String successTimeStr) {
		this.successTimeStr = successTimeStr;
	}

	public String getEndTimeStr() {
		if (super.getEndTime() != null) {
			return DateUtils.timeStampToDate(super.getEndTime(), DateUtils.YMD_HMS);
		}
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getAccountStr() {
		return accountStr;
	}

	public void setAccountStr(String accountStr) {
		this.accountStr = accountStr;
	}

	// 0:草稿标 1：新标，审核中，2：投标中，3：满标复审中，4：还款中，5：还款结束，-1：流标，-2：被撤销 ，-3：审核失败，42：已垫付
	public String getStatusStr() {
		if (getStatus() != null) {
			statusStr = Dictionary.getValue(100, getStatus() + "");
		}
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getAprStr() {
		return aprStr;
	}

	public void setAprStr(String aprStr) {
		this.aprStr = aprStr;
	}

	public String getSubName() {
		if (getName() == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder(14);
		int l = 0;
		for (int i = 0; i < getName().length(); i++) {
			char ch = getName().charAt(i);
			sb.append(ch);
			// 判断是否数字，字母，特殊字符

			if (isChinese(ch)) {
				l += 2;
			} else {
				l += 1;
			}

			if (l >= 14) {
				break;
			}
		}
		sb.append(l >= 14 ? ".." : "");
		return sb.toString();
	}

	public static boolean isChinese(char a) {
		int v = a;
		return (v >= 19968 && v <= 171941);
	}

	public String getAccountToChinese() {
		if (null != super.getAccount()) {
			return SimpleMoneyFormat.getInstance().format(super.getAccount());
		}
		return "";
	}

	public String getTimelimitformat() {
		if (super.getBorrowtype() == 4) {
			return "秒还";
		}
		if (super.getStyle() == 4) {
			return super.getTimeLimit() + "天";
		}
		return super.getTimeLimit() + "个月";
	}

	public Date getEndTimeDate() {
		if (null != super.getEndTime()) {
			return DateUtils.timeStampToDate(super.getEndTime());
		}
		return endTimeDate;
	}

	public Date getSuccessTimeDate() {
		if (null != super.getSuccessTime()) {
			return DateUtils.timeStampToDate(super.getSuccessTime());
		}
		return successTimeDate;
	}

	public String getUserNameSecret() {
		userNameSecret = this.getUserName();
		userNameSecret = StrinUtils.stringSecretTo(userNameSecret);
		return userNameSecret;
	}

	public String getUserNameEncrypt() {
		userNameEncrypt = this.getUserName();
		userNameEncrypt = StrinUtils.stringEncryptEn(userNameEncrypt);
		return userNameEncrypt;
	}

	public int getIsTransfer() {
		return isTransfer;
	}

	public void setIsTransfer(int isTransfer) {
		this.isTransfer = isTransfer;
	}

	public Integer getIsFinancialUser() {
		return isFinancialUser;
	}

	public void setIsFinancialUser(Integer isFinancialUser) {
		this.isFinancialUser = isFinancialUser;
	}

	public String getIsUseCurMoney() {
		return isUseCurMoney;
	}

	public void setIsUseCurMoney(String isUseCurMoney) {
		this.isUseCurMoney = isUseCurMoney;
	}

	public int getTenderType() {
		return tenderType;
	}

	public void setTenderType(int tenderType) {
		this.tenderType = tenderType;
	}

	public BigDecimal getRbtAccount() {
		return rbtAccount;
	}

	public void setRbtAccount(BigDecimal rbtAccount) {
		this.rbtAccount = rbtAccount;
	}

    public BigDecimal getYearApr() {
        return yearApr;
    }

    public void setYearApr(BigDecimal yearApr) {
        this.yearApr = yearApr;
    }



	public Integer getTenderStatus() {
		return tenderStatus;
	}

	public void setTenderStatus(Integer tenderStatus) {
		this.tenderStatus = tenderStatus;
	}
}