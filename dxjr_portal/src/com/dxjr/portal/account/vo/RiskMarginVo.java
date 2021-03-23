package com.dxjr.portal.account.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * <p>
 * 风险保证金实体类
 * </p>
 * 
 * @title RiskMarginVo.java
 * @package com.dxjr.portal.account.vo
 * @author chenlu
 * @version 0.1 2014年8月6日
 */

public class RiskMarginVo implements Serializable {

	private static final long serialVersionUID = -644364538055101295L;

	// 主键
	private Integer id;

	// 风险保证金
	private BigDecimal account;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}
}
