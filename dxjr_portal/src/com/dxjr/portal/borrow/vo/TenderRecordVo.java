package com.dxjr.portal.borrow.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.dxjr.base.entity.BTenderRecord;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.StrinUtils;

/**
 * <p>
 * Description:投标记录Vo<br />
 * </p>
 * 
 * @title TenderRecordVo.java
 * @package com.dxjr.portal.borrow.vo
 * @author justin.xu
 * @version 0.1 2014年9月13日
 */
public class TenderRecordVo extends BTenderRecord implements Serializable {
	private String username;
	private String email;
	private Date addtimeDate;
	/** 直通车标题 */
	private String firstBorrowName;
	/** 直通车期 数 */
	private String firstPeriods;
	private String userNameSecret;  //隐藏用户名
	private String userNameEncrypt;  //加密用户名
	
	//定期宝转让
	private BigDecimal tenderCapital;
	private BigDecimal tenderInterest;
	private BigDecimal tenderRepayAccount;
	private String borrowContractNo;
	
	private BigDecimal sumAccount;//投标记录总金额
	private Integer tenderCount;//投标记录条数
	
	
	private BigDecimal apr;  //年利率
	private Integer style;  //还款方式
	private String ishowfloat;
	/**存管方式   0：非存管，1：浙商存管 **/
	private Integer isCustody;
    private BigDecimal yearApr;
    private String  userIdMD5;

    private int activityYear;
    private int activityMonth;

    public int getActivityMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getAddtimeDate());
        return cal.get(Calendar.MONTH) + 1;
    }

    public int getActivityYear() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getAddtimeDate());
        return cal.get(Calendar.YEAR);
    }

    public String getUserIdMD5() {
		return userIdMD5;
	}

	public void setUserIdMD5(String userIdMD5) {
		this.userIdMD5 = userIdMD5;
	}

	public Integer getIsCustody() {
		return isCustody;
	}

	public void setIsCustody(Integer isCustody) {
		this.isCustody = isCustody;
	}

	public String getIshowfloat() {
        /**
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date activityTimeStart=sdf.parse("2016-07-01 00:00:00");
			Date activityTimeend=sdf.parse("2016-07-31 23:59:59");
			if(getTenderType()!=null&&getTenderType()==1){
				if(DateUtils.timeStampToDate(getAddtime()).before(activityTimeStart)||DateUtils.timeStampToDate(getAddtime()).after(activityTimeend)){
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

	public Integer getStyle() {
		return style;
	}

	public void setStyle(Integer style) {
		this.style = style;
	}

	public BigDecimal getApr() {
		return apr;
	}

	public void setApr(BigDecimal apr) {
		this.apr = apr;
	}

	public Integer getBorrowtype() {
		return borrowtype;
	}

	public void setBorrowtype(Integer borrowtype) {
		this.borrowtype = borrowtype;
	}

	public Integer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	private Integer borrowtype;
	private Integer timeLimit;
	
	
	public BigDecimal getSumAccount() {
		return sumAccount;
	}

	public void setSumAccount(BigDecimal sumAccount) {
		this.sumAccount = sumAccount;
	}

	public Integer getTenderCount() {
		return tenderCount;
	}

	public void setTenderCount(Integer tenderCount) {
		this.tenderCount = tenderCount;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstBorrowName() {
		return firstBorrowName;
	}

	public void setFirstBorrowName(String firstBorrowName) {
		this.firstBorrowName = firstBorrowName;
	}

	public Date getAddtimeDate() {
		if (null != super.getAddtime()) {
			return DateUtils.timeStampToDate(super.getAddtime());
		}
		return addtimeDate;
	}

	public void setAddtimeDate(Date addtimeDate) {
		this.addtimeDate = addtimeDate;
	}

	public String getFirstPeriods() {
		return firstPeriods;
	}

	public void setFirstPeriods(String firstPeriods) {
		this.firstPeriods = firstPeriods;
	}
	
	public String getUserNameSecret() {
		userNameSecret = this.getUsername();
		userNameSecret = StrinUtils.stringSecretTo(userNameSecret);
		return userNameSecret;
	}
	
	public String getUserNameEncrypt() {
		userNameEncrypt = this.getUsername();
		userNameEncrypt = StrinUtils.stringEncryptEn(userNameEncrypt);
		return userNameEncrypt;
	}

	public BigDecimal getTenderCapital() {
		return tenderCapital;
	}

	public void setTenderCapital(BigDecimal tenderCapital) {
		this.tenderCapital = tenderCapital;
	}

	public BigDecimal getTenderInterest() {
		return tenderInterest;
	}

	public void setTenderInterest(BigDecimal tenderInterest) {
		this.tenderInterest = tenderInterest;
	}

	public BigDecimal getTenderRepayAccount() {
		return tenderRepayAccount;
	}

	public void setTenderRepayAccount(BigDecimal tenderRepayAccount) {
		this.tenderRepayAccount = tenderRepayAccount;
	}

	public String getBorrowContractNo() {
		return borrowContractNo;
	}

	public void setBorrowContractNo(String borrowContractNo) {
		this.borrowContractNo = borrowContractNo;
	}

    public BigDecimal getYearApr() {
        return yearApr;
    }

    public void setYearApr(BigDecimal yearApr) {
        this.yearApr = yearApr;
    }
}