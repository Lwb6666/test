package com.dxjr.base.entity;

import java.math.BigDecimal;

/**
 * 
 * <p>
 * Description:风险保证金实体类<br />
 * </p>
 * @title RiskMargin.java
 * @package com.dxjr.base.entity 
 * @author yangshijin
 * @version 0.1 2014年7月3日
 */
public class RiskMargin {
    
	/**
	 * 主键
	 */
    private Integer id;

    /**
     * 风险保证金
     */
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