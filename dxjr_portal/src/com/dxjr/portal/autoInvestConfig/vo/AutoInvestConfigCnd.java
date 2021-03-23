package com.dxjr.portal.autoInvestConfig.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.dxjr.common.page.BaseCnd;

/**
 * 
 * <p>
 * Description:自动投标规则匹配条件类<br />
 * </p>
 * @title AutoInvestConfigCnd.java
 * @package com.dxjr.portal.autoInvestConfig.vo 
 * @author yangshijin
 * @version 0.1 2014年5月26日
 */
public class AutoInvestConfigCnd extends BaseCnd implements Serializable {
	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = 2974034919186861148L;

	/** 
	 * 用户Id 
	 */
	private Integer user_id;
	
	/**
	 * 利率
	 */
	private BigDecimal apr;
	
	/**
	 * 借款还款方式
	 */
	private Integer borrow_type;
	
	/**
	 * 借款期限
	 */
	private Integer time_limit;
	
	/** 
	 * 更新时间 
	 */
	private String uptime;
	
	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public BigDecimal getApr() {
		return apr;
	}

	public void setApr(BigDecimal apr) {
		this.apr = apr;
	}

	public Integer getBorrow_type() {
		return borrow_type;
	}

	public void setBorrow_type(Integer borrow_type) {
		this.borrow_type = borrow_type;
	}

	public Integer getTime_limit() {
		return time_limit;
	}

	public void setTime_limit(Integer time_limit) {
		this.time_limit = time_limit;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
}
