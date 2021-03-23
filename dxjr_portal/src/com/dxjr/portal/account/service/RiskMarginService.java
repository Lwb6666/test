package com.dxjr.portal.account.service;

import java.math.BigDecimal;

/**
 * @title RiskMarginService.java
 * @package com.dxjr.portal.account.service
 * @author chenlu
 * @version 0.1 2014年8月6日
 */
public interface RiskMarginService {

	/**
	 * 查询风险保证金
	 * 
	 * @return
	 */
	public BigDecimal queryRiskMargin() throws Exception;

	/**
	 * 昨日成交：散标，定期宝，标转，车转，以满标&满宝时间为准
	 * 
	 * @return
	 */
	public BigDecimal queryYestDeal();
}
