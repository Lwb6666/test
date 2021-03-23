package com.dxjr.portal.transfer.vo;

import com.dxjr.portal.transfer.entity.BSubscribe;
import com.dxjr.utils.StrinUtils;

public class BsubscribeVo extends BSubscribe {

	private String userName; // 用户名
	private String transferUserName; // 债权转让用户
	private Integer isTransfer;
	private String userNameSecret;  //隐藏用户名
	private String transferUserNameSecret; // 债权转让用户-隐藏
	private String userNameEncrypt;  //加密用户名
	private String transferUserNameEncrypt;  //债权转让用户-隐藏
	private Integer tenderId;  //投标ID
	private Integer firstTenderRealId;  //最终认购记录ID
	private Integer oldFixId;  //转让定期宝ID
	private Integer newFixId;  //认购定期宝ID
	private Integer firstStatus; //直通车转让状态
	private Integer parentId; //直通车转让状态
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTransferUserName() {
		return transferUserName;
	}

	public void setTransferUserName(String transferUserName) {
		this.transferUserName = transferUserName;
	}

	public Integer getIsTransfer() {
		return isTransfer;
	}

	public void setIsTransfer(Integer isTransfer) {
		this.isTransfer = isTransfer;
	}
	public String getUserNameSecret() {
		userNameSecret = this.getUserName();
			userNameSecret = StrinUtils.stringSecretTo(userNameSecret);
		return userNameSecret;
	}
	public String getTransferUserNameSecret() {
		transferUserNameSecret = this.getTransferUserName();
			transferUserNameSecret = StrinUtils.stringSecretTo(transferUserNameSecret);
		return transferUserNameSecret;
	}
	public String getUserNameEncrypt() {
		userNameEncrypt = this.getUserName();
		userNameEncrypt = StrinUtils.stringEncryptEn(userNameEncrypt);
		return userNameEncrypt;
	}
	public String getTransferUserNameEncrypt() {
		transferUserNameEncrypt = this.getTransferUserName();
		transferUserNameEncrypt = StrinUtils.stringEncryptEn(transferUserNameEncrypt);
		return transferUserNameEncrypt;
	}

	public Integer getTenderId() {
		return tenderId;
	}

	public void setTenderId(Integer tenderId) {
		this.tenderId = tenderId;
	}

	public Integer getFirstTenderRealId() {
		return firstTenderRealId;
	}

	public void setFirstTenderRealId(Integer firstTenderRealId) {
		this.firstTenderRealId = firstTenderRealId;
	}

	public Integer getOldFixId() {
		return oldFixId;
	}

	public void setOldFixId(Integer oldFixId) {
		this.oldFixId = oldFixId;
	}

	public Integer getNewFixId() {
		return newFixId;
	}

	public void setNewFixId(Integer newFixId) {
		this.newFixId = newFixId;
	}

	public Integer getFirstStatus() {
		return firstStatus;
	}

	public void setFirstStatus(Integer firstStatus) {
		this.firstStatus = firstStatus;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	
}
