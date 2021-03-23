package com.dxjr.portal.account.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.dxjr.common.Dictionary;
import com.dxjr.utils.DateUtils;

/**
 * <p>
 * Description:提现记录Vo<br />
 * </p>
 * 
 * @title CashRecordVo.java
 * @package com.dxjr.portal.account.vo
 * @author justin.xu
 * @version 0.1 2014年6月18日
 */
public class CashRecordVo implements Serializable {
	private static final long serialVersionUID = 253259912412431081L;

	private Integer id;
	private Integer userId;
	/** 状态【-1：审核失败；0：申请提现；1：审核通过；2：打款结束】 3:取消提现 */
	private Integer status;
	/** 账户 */
	private String account;
	/** 银行 */
	private String bank;
	/** 所属分行 */
	private String branch;
	/** 总金额 */
	private BigDecimal total;
	/** 到账总额 */
	private BigDecimal credited;
	/** 手续费 */
	private BigDecimal fee;
	/** 审核人ID */
	private Integer verifyUserid;
	/** 审核时间 */
	private String verifyTime;
	/** 审核备注 */
	private String verifyRemark;
	/** 打款ID */
	private int verifyUserid2;
	/** 打款时间 */
	private String verifyTime2;
	/** 打款备注 */
	private String verifyRemark2;
	/** 作废用户ID */
	private Integer verifyUserid3;
	/** 作废时间 */
	private String verifyTime3;
	/** 作废备注 */
	private String verifyRemark3;
	/** 添加时间 */
	private String addtime;
	/** 添加ip */
	private String addip;
	/** 版本号. **/
	private Integer version;

    /** 银行充值流水号 **/
    private String eTradeNo;
    /** 存管银行卡主键 **/
    private Integer eBankInfoId;
    /** 平台充值业务流水号 **/
    private String bizNo;
    /** 存管方式 **/
    private Integer isCustody;
	/** 转换格式 */
	private String statusStr;
	private String addtimeymd;
	private String addtimeymdhms;
	private String verifyTime2ymd;
	private String verifyTime2ymdhms;
	private String accountFormat;

	// 将日间秒数转换成日期
	private Date verifyTimeDate;
	private Date verifyTime2Date;
	private Date verifyTime3Date;

	/**
	 * 显示带*的账号
	 */
	public String getAccountFormat() {
		if (null != account && !"".equals(account)) {
			String str1 = account.substring(0, 4);
			String str3 = account.substring(account.length() - 3);
			String str2 = "";
			for (int i = 4; i < account.length() - 3; i++) {
				str2 = str2 + "*";
			}
			return str1 + str2 + str3;
		}
		return accountFormat;
	}

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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getCredited() {
		return credited;
	}

	public void setCredited(BigDecimal credited) {
		this.credited = credited;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Integer getVerifyUserid() {
		return verifyUserid;
	}

	public void setVerifyUserid(Integer verifyUserid) {
		this.verifyUserid = verifyUserid;
	}

	public String getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(String verifyTime) {
		this.verifyTime = verifyTime;
	}

	public String getVerifyRemark() {
		return verifyRemark;
	}

	public void setVerifyRemark(String verifyRemark) {
		this.verifyRemark = verifyRemark;
	}

	public int getVerifyUserid2() {
		return verifyUserid2;
	}

	public void setVerifyUserid2(int verifyUserid2) {
		this.verifyUserid2 = verifyUserid2;
	}

	public String getVerifyTime2() {
		return verifyTime2;
	}

	public void setVerifyTime2(String verifyTime2) {
		this.verifyTime2 = verifyTime2;
	}

	public String getVerifyRemark2() {
		return verifyRemark2;
	}

	public void setVerifyRemark2(String verifyRemark2) {
		this.verifyRemark2 = verifyRemark2;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getAddip() {
		return addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getStatusStr() {
		return Dictionary.getValue(802, String.valueOf(status));
	}

	public String getAddtimeymd() {
		if (null != addtime) {
			return DateUtils.timeStampToDate(addtime, DateUtils.YMD_HMS);
		}
		return addtimeymd;
	}

	public String getAddtimeymdhms() {
		if (null != addtime) {
			return DateUtils.timeStampToDate(addtime, DateUtils.YMD_HMS);
		}
		return addtimeymdhms;
	}

	public String getVerifyTime2ymd() {
		if (null != verifyTime2) {
			return DateUtils.timeStampToDate(verifyTime2, DateUtils.YMD_DASH);
		}
		return verifyTime2ymd;
	}

	public String getVerifyTime2ymdhms() {
		if (null != verifyTime2) {
			return DateUtils.timeStampToDate(verifyTime2, DateUtils.YMD_HMS);
		}
		return verifyTime2ymdhms;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public Integer getVerifyUserid3() {
		return verifyUserid3;
	}

	public void setVerifyUserid3(Integer verifyUserid3) {
		this.verifyUserid3 = verifyUserid3;
	}

	public String getVerifyTime3() {
		return verifyTime3;
	}

	public void setVerifyTime3(String verifyTime3) {
		this.verifyTime3 = verifyTime3;
	}

	public String getVerifyRemark3() {
		return verifyRemark3;
	}

	public void setVerifyRemark3(String verifyRemark3) {
		this.verifyRemark3 = verifyRemark3;
	}

	public Date getVerifyTimeDate() {
		if (null != verifyTime) {
			return DateUtils.timeStampToDate(verifyTime);
		}
		return verifyTimeDate;
	}

	public Date getVerifyTime2Date() {
		if (null != verifyTime2) {
			return DateUtils.timeStampToDate(verifyTime2);
		}
		return verifyTime2Date;
	}

	public Date getVerifyTime3Date() {
		if (null != verifyTime3) {
			return DateUtils.timeStampToDate(verifyTime3);
		}
		return verifyTime3Date;
	}

    public String geteTradeNo() {
        return eTradeNo;
    }

    public void seteTradeNo(String eTradeNo) {
        this.eTradeNo = eTradeNo;
    }

    public Integer geteBankInfoId() {
        return eBankInfoId;
    }

    public void seteBankInfoId(Integer eBankInfoId) {
        this.eBankInfoId = eBankInfoId;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public Integer getIsCustody() {
        return isCustody;
    }

    public void setIsCustody(Integer isCustody) {
        this.isCustody = isCustody;
    }
}
