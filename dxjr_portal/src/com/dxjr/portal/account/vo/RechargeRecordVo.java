package com.dxjr.portal.account.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.dxjr.common.Dictionary;
import com.dxjr.utils.DateUtils;

/**
 * <p>
 * Description:充值记录<br />
 * </p>
 * 
 * @title RechargeRecordVo.java
 * @package com.dxjr.portal.account.vo
 * @author justin.xu
 * @version 0.1 2014年5月23日
 */
public class RechargeRecordVo implements Serializable {
	private static final long serialVersionUID = 253259912412431081L;
	private Integer id;
	/** 用户唯一ID */
	private Integer userId;
	/** 类型(1：在线充值，2：线下转账) */
	private Integer type;
	/** 订单号 */
	private String tradeNo;
	/** 状态（0充值审核中，1充值成功，9表示失败，2在线充值待付款， 3初审成功） */
	private Integer status;
	/** 充值金额 */
	private BigDecimal money;
	/** 充值银行 */
	private String payment;
	/** 备注 */
	private String remark;
	/** 充值手续费 */
	private BigDecimal fee;
	/** 初审审核人id(0默认值，表示在线充值) */
	private Integer verifyUserid;
	/** 初审审核时间 */
	private String verifyTime;
	/** 初审审核备注 */
	private String verifyRemark;
	/** 添加时间 */
	private String addtime;
	/** 添加ip */
	private String addip;
	/** 第三方支付平台 1：网银在线 2：国付宝 3：盛付通 */
	private Integer onlinetype;
	/** 终审审核人id */
	private Integer verifyUserid2;
	/** 终审审核时间 */
	private String verifyTime2;
	/** 终审审核备注 */
	private String verifyRemark2;
	/** 银行帐号 */
	private String cardNum;
	/** 银行开户人姓名 */
	private String bankUsername;
	/** 版本号. **/
	private Integer version;

	/** 用户名 */
	private String username;
	/** 类型转换为中文 */
	private String typeStr;
	private String statusStr;
	private String addtimeymd;
	private String verifyTime2ymd;
	
    public String getVerifyTime2ymd() {
    	if (null != verifyTime2) {
			return DateUtils.timeStampToDate(verifyTime2, DateUtils.YMD_HMS);
		}
		return verifyTime2ymd;
	}

	public void setVerifyTime2ymd(String verifyTime2ymd) {
		this.verifyTime2ymd = verifyTime2ymd;
	}
	/** 银行充值流水号 **/
    private String eTradeNo;
    /** 存管银行卡主键 **/
    private Integer eBankInfoId;
    /** 平台充值业务流水号 **/
    private String bizNo;
    /** 存管方式 **/
    private Integer isCustody;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getOnlinetype() {
		return onlinetype;
	}

	public void setOnlinetype(Integer onlinetype) {
		this.onlinetype = onlinetype;
	}

	public Integer getVerifyUserid2() {
		return verifyUserid2;
	}

	public void setVerifyUserid2(Integer verifyUserid2) {
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

	public String getBankUsername() {
		return bankUsername;
	}

	public void setBankUsername(String bankUsername) {
		this.bankUsername = bankUsername;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTypeStr() {
		if (type == 1) {
			return "在线充值";
		} else if (type == 2 && null != cardNum
				&& "44461248@qq.com".equals(cardNum)) {
			return "支付宝充值";
		} else if (type == 2 && null != cardNum
				&& !"44461248@qq.com".equals(cardNum)) {
			return "线下充值";
		}
		return typeStr;
	}

	public String getStatusStr() {
		return Dictionary.getValue(801, String.valueOf(status));
	}

	public String getAddtimeymd() {
		if (null != addtime) {
			return DateUtils.timeStampToDate(addtime, DateUtils.YMD_HMS);
		}
		return addtimeymd;
	}

    public String getETradeNo() {
        return eTradeNo;
    }

    public void setETradeNo(String eTradeNo) {
        this.eTradeNo = eTradeNo;
    }

    public Integer getEBankInfoId() {
        return eBankInfoId;
    }

    public void setEBankInfoId(Integer eBankInfoId) {
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
