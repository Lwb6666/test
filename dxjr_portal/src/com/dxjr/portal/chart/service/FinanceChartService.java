package com.dxjr.portal.chart.service;

import java.math.BigDecimal;
import java.util.Map;

import com.dxjr.common.page.Page;
import com.dxjr.portal.chart.vo.DateMoneyVo;
import com.dxjr.portal.chart.vo.PublicChartsInfoVo;
import com.dxjr.portal.chart.vo.RepaymentChartCnd;

public interface FinanceChartService {

	/**
	 * <p>
	 * Description:金额人数折线图<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月26日
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryMoneyPersonChart() throws Exception;

	/**
	 * <p>
	 * Description:查询成交分布图<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月25日
	 * @return PublicChartsInfoVo
	 */
	public PublicChartsInfoVo querySuccessBorrowChart() throws Exception;

	/**
	 * <p>
	 * Description:查询待收分布图<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月25日
	 * @return PublicChartsInfoVo
	 */
	public PublicChartsInfoVo queryWaitReceiveChart() throws Exception;

	/**
	 * <p>
	 * Description:查询金额人数数据<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月26日
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryMoneyPersonData() throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某段时间内的抵押标还款记录(分页)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月16日
	 * @param repaymentChartCnd
	 * @param curPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             Page
	 */
	public Page findRepaymentChartForWeekPages(
			RepaymentChartCnd repaymentChartCnd, int curPage, int pageSize)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询逾期的抵押标还款记录(分页)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月16日
	 * @param repaymentChartCnd
	 * @param curPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             Page
	 */
	public Page findRepaymentChartForOverduePages(
			RepaymentChartCnd repaymentChartCnd, int curPage, int pageSize)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询结清的抵押标还款记录(分页)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月16日
	 * @param repaymentChartCnd
	 * @param curPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             Page
	 */
	public Page findRepaymentChartForFinishPages(
			RepaymentChartCnd repaymentChartCnd, int curPage, int pageSize)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:统计某段时间内的抵押标未还总金额、已还总金额、应还总金额<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月16日
	 * @param repaymentChartCnd
	 * @return
	 * @throws Exception
	 *             Map<String,BigDecimal>
	 */
	public Map<String, BigDecimal> queryRepaymentChartForWeek(
			RepaymentChartCnd repaymentChartCnd) throws Exception;

	public DateMoneyVo queryDateMoney() throws Exception;
}
