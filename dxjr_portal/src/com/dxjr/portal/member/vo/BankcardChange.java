package com.dxjr.portal.member.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 银行卡更换申请记录表
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title BankcardChange.java
 * @package com.dxjr.portal.member.vo
 * @author huangpin
 * @version 0.1 2015年3月31日
 */
public class BankcardChange implements Serializable {
	private static final long serialVersionUID = -5292768592815317113L;

	private Integer id; // 自增ID
	private String realName; // 真实姓名
	private String idCard; // 证件号码，与rocky_realname_appro应一致
	private String idCardType; // 证件类型
	private String phone; // 手机号

	private String oldBankcard; // 原银行卡号
	private String newBank; // 新卡银行
	private String newBankCode;
	private String newBranch; // 新卡分行
	private String newBranchNo; // 新卡分行行号
	private String newBankcard; // 新卡卡号

	private String updateReason;// 换卡原因：丢失，损坏，卡号错误
	private String remark; // 备注
	private Date lastAddTime; // 上次申请时间
	private Date addTime; // 创建时间（申请时间）
	private String addIp; // 添加IP
	private Integer userId; // 申请人

	private String userName; // 申请人用户名
	private Integer approveStatus; // 审核状态：0，待审核，1，审核通过，-1，审核不通过，-2，草稿
	private Date approveTime; // 审核时间
	private Integer approveUserId; // 审核人员ID
	private String approveUserName; // 审批人姓名
	private String approveIp;// 审批IP
	private String approveRemark; // 审核备注

	private Date recheckTime; // 复审时间
	private Integer recheckUserId; // 复审人员ID
	private String recheckUserName; // 复审人姓名
	private String recheckIp;// 复审IP

	private String recheckRemark; // 复审备注
	private Integer platform; // 平台来源
    private String bankPhone;
	public String getBankPhone() {
		return bankPhone;
	}

	public void setBankPhone(String bankPhone) {
		this.bankPhone = bankPhone;
	}

	// 显示属性
	private String approveStatusStr;
	private String picString;

	public String getPicString() {
		return picString;
	}

	public void setPicString(String picString) {
		this.picString = picString;
	}

	List<BankcardPic> pics;
	/**
	 * 手机校验码
	 */
	private String activeCode;

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOldBankcard() {
		return oldBankcard;
	}

	public void setOldBankcard(String oldBankcard) {
		this.oldBankcard = oldBankcard;
	}

	public String getNewBank() {
		return newBank;
	}

	public void setNewBank(String newBank) {
		this.newBank = newBank;
	}

	public String getNewBranch() {
		return newBranch;
	}

	public void setNewBranch(String newBranch) {
		this.newBranch = newBranch;
	}

	public String getNewBankcard() {
		return newBankcard;
	}

	public void setNewBankcard(String newBankcard) {
		this.newBankcard = newBankcard;
	}

	public String getUpdateReason() {
		return updateReason;
	}

	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(Integer approveStatus) {
		this.approveStatus = approveStatus;
	}

	public Integer getApproveUserId() {
		return approveUserId;
	}

	public void setApproveUserId(Integer approveUserId) {
		this.approveUserId = approveUserId;
	}

	public String getApproveRemark() {
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIdCardType() {
		return idCardType;
	}

	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}

	public String getNewBranchNo() {
		return newBranchNo;
	}

	public void setNewBranchNo(String newBranchNo) {
		this.newBranchNo = newBranchNo;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getApproveUserName() {
		return approveUserName;
	}

	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getApproveStatusStr() {
		if (approveStatus != null) {
			switch (approveStatus) {
			case 0:
				approveStatusStr = "待审核";
				break;
			case 1:
				approveStatusStr = "初审通过";
				break;
			case -1:
				approveStatusStr = "不通过";
				break;
			case 2:
				approveStatusStr = "复审通过";
				break;
			default:
				break;
			}
		}
		return approveStatusStr;
	}

	public Date getRecheckTime() {
		return recheckTime;
	}

	public void setRecheckTime(Date recheckTime) {
		this.recheckTime = recheckTime;
	}

	public Integer getRecheckUserId() {
		return recheckUserId;
	}

	public void setRecheckUserId(Integer recheckUserId) {
		this.recheckUserId = recheckUserId;
	}

	public String getRecheckUserName() {
		return recheckUserName;
	}

	public void setRecheckUserName(String recheckUserName) {
		this.recheckUserName = recheckUserName;
	}

	public String getRecheckIp() {
		return recheckIp;
	}

	public void setRecheckIp(String recheckIp) {
		this.recheckIp = recheckIp;
	}

	public String getRecheckRemark() {
		return recheckRemark;
	}

	public void setRecheckRemark(String recheckRemark) {
		this.recheckRemark = recheckRemark;
	}

	public void setApproveStatusStr(String approveStatusStr) {
		this.approveStatusStr = approveStatusStr;
	}

	public String getApproveIp() {
		return approveIp;
	}

	public void setApproveIp(String approveIp) {
		this.approveIp = approveIp;
	}

	public Date getLastAddTime() {
		return lastAddTime;
	}

	public void setLastAddTime(Date lastAddTime) {
		this.lastAddTime = lastAddTime;
	}

	public String getNewBankCode() {
		return newBankCode;
	}

	public void setNewBankCode(String newBankCode) {
		this.newBankCode = newBankCode;
	}

	public List<BankcardPic> getPics() {
		return pics;
	}

	public void setPics(List<BankcardPic> pics) {
		this.pics = pics;
	}

}
