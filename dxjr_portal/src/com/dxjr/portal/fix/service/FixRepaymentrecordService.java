package com.dxjr.portal.fix.service;

import java.math.BigDecimal;

/**
 * <p>
 * Description:定期宝待还接口类<br />
 * </p>
 * 
 * @title FixTenderDetailService.java
 * @package com.dxjr.portal.fix1.service
 * @author caodefeng
 * @version 0.1 2015年5月14日
 */
public interface FixRepaymentrecordService {
	
	/**
	 * 统计查询已经付给用户利息总金额
	 * @return
	 */
	public BigDecimal queryTotalInterest() throws Exception;
	
	/**
	 * 统计查询累计加入(收益中和已退出的)总金额
	 * @return
	 * @throws Exception
	 */
	public BigDecimal querySumCapital() throws Exception;
}
