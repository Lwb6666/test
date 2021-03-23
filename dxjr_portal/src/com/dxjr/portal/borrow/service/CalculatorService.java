package com.dxjr.portal.borrow.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.dxjr.portal.borrow.entity.Interest;

public interface CalculatorService {

	/**
	 * 
	 * <p>
	 * Description:等额本息还款<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月15日
	 * @param interest
	 * @return List<Map<String,BigDecimal>>
	 */
	public List<Map<String, BigDecimal>> debxhkMonth(Interest interest);

	/**
	 * 
	 * <p>
	 * Description:按月付息到期还本<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月15日
	 * @param interest
	 * @return List<Map<String,BigDecimal>>
	 */
	public List<Map<String, BigDecimal>> ayfxdqhbMonth(Interest interest);

	/**
	 * 
	 * <p>
	 * Description:到期还本付息<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月15日
	 * @param interest
	 *            void
	 */
	public void dqhbfxMonth(Interest interest);

	/**
	 * 
	 * <p>
	 * Description:按天借款<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月15日
	 * @param interest
	 *            void
	 */
	public void dqhbfxDay(Interest interest);

	/**
	 * 
	 * <p>
	 * Description:月利率<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月15日
	 * @param interest
	 * @return BigDecimal
	 */
	public BigDecimal getRatePercenageMonth(Interest interest);

	/**
	 * 
	 * <p>
	 * Description:日利率<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月15日
	 * @param interest
	 * @return BigDecimal
	 */
	public BigDecimal getRatePercenageDay(Interest interest);

	/**
	 * 
	 * <p>
	 * Description:计算总利息<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月15日
	 * @param interest
	 * @return BigDecimal
	 */
	public BigDecimal getInterest(Interest interest);
}
