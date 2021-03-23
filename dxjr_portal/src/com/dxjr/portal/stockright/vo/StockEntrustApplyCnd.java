package com.dxjr.portal.stockright.vo;

import java.io.Serializable;

import com.dxjr.portal.stockright.entity.StockEntrust;


public class StockEntrustApplyCnd extends StockEntrust implements Serializable{
	private static final long serialVersionUID = -8005802218725609268L;
	
	private Integer isProtocol;//1：同意协议 2： 默认 
	private Integer isCur;//是否使用活期宝
	
	
	public Integer getIsCur() {
		return isCur;
	}

	public void setIsCur(Integer isCur) {
		this.isCur = isCur;
	}

	public Integer getIsProtocol() {
		return isProtocol;
	}

	public void setIsProtocol(Integer isProtocol) {
		this.isProtocol = isProtocol;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}