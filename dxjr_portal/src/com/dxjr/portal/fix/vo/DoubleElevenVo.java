package com.dxjr.portal.fix.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.dxjr.utils.StrinUtils;

/**
 * @author WangQianJin
 * @title 双十一活动Vo
 * @date 2015年11月9日
 */
public class DoubleElevenVo implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -5105932631418220696L;
	
	/**
	 * 排名
	 */
	private Integer numberNo;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 投资金额
	 */
	private BigDecimal invertTotal;
	
	/**
	 * 加密用户名
	 */
	private String userNameEncrypt;
	
	public String getUserNameEncrypt() {
		userNameEncrypt = StrinUtils.stringEncryptEn(username);
		return userNameEncrypt;
	}
	
	public Integer getNumberNo() {
		return numberNo;
	}
	public void setNumberNo(Integer numberNo) {
		this.numberNo = numberNo;
	}
	public String getUsername() {
		return StrinUtils.stringSecretTo(username);
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public BigDecimal getInvertTotal() {
		return invertTotal;
	}
	public void setInvertTotal(BigDecimal invertTotal) {
		this.invertTotal = invertTotal;
	}
	
	

}
