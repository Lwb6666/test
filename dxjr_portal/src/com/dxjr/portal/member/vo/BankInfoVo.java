package com.dxjr.portal.member.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.dxjr.base.entity.BankInfo;

/**
 * <p>
 * Description:银行信息<br />
 * </p>
 * 
 * @title BankInfoVo.java
 * @package com.dxjr.portal.member.vo
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
public class BankInfoVo extends BankInfo implements Serializable {
	private static final long serialVersionUID = 3279069815765729961L;

	/** 带*的卡号 */
	private String securityCardNum;
	/**
	 * 银行卡尾号
	 */
	private String bankCardTailNum;

	public String getBankCardTailNum() {
		if (StringUtils.isEmpty(getCardNum())) {
			return StringUtils.EMPTY;
		}
		return getCardNum().substring(getCardNum().length() - 4);
	}

	public String getSecurityCardNum() {
		StringBuilder sb = new StringBuilder(getCardNum().length()).append(getCardNum());
		for (int i = 4; i < getCardNum().length() - 3; i++) {
			sb.replace(i, i + 1, "*");
		}
		securityCardNum = sb.toString();
		return securityCardNum;
	}

	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
