package com.dxjr.portal.chart.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.CacheService;
import com.dxjr.common.SerializeUtil;
import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.chart.service.FinanceChartService;
import com.dxjr.portal.chart.vo.DateMoneyVo;
import com.dxjr.portal.chart.vo.PublicChartsInfoVo;
import com.dxjr.portal.chart.vo.RepaymentChartCnd;
import com.dxjr.portal.chart.vo.RepaymentChartVo;
import com.dxjr.utils.DateUtils;

@Controller
@RequestMapping(value = "/chart/finance")
public class FinanceChartController {

	@Autowired
	private FinanceChartService financeChartService;



	/**
	 * <p>
	 * Description:金额人数折线图<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月25日
	 * @param request
	 * @param username
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping(value = "moneyPersonChart")
	@ResponseBody
	public Map<String, Object> moneyPersonChart(HttpServletRequest request, String username) throws Exception {
		String enable = CacheService.getInstance().getEnable();
		Map<String, Object> resultMap=null;
		String cacheKey="PT_MoneyPersonChart";

		if (enable != null && enable.equals("1")) {
			byte[] key = CacheService.getInstance().getBytes(cacheKey.getBytes());
			if (key != null) {
				resultMap = (Map<String, Object>) SerializeUtil.unserialize(key);

			} else {
				resultMap = financeChartService.queryMoneyPersonChart();
				CacheService.getInstance().put(cacheKey.getBytes(), SerializeUtil.serialize(resultMap));
				CacheService.getInstance().expire(cacheKey.getBytes(), 7200);// 2小时候失效
			}
		} else {
			resultMap = financeChartService.queryMoneyPersonChart();
		}
		return resultMap;
	}

	/**
	 * <p>
	 * Description:成交分布图<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月25日
	 * @param request
	 * @param username
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping(value = "successBorrowChart")
	@ResponseBody
	public PublicChartsInfoVo successBorrowChart(HttpServletRequest request, String username) throws Exception {

		String enable = CacheService.getInstance().getEnable();
		PublicChartsInfoVo publicChartsInfo=null;
		String cacheKey="PT_SuccessBorrowChart";

		if (enable != null && enable.equals("1")) {
			byte[] key = CacheService.getInstance().getBytes(cacheKey.getBytes());
			if (key != null) {
				publicChartsInfo = (PublicChartsInfoVo) SerializeUtil.unserialize(key);
			} else {
				publicChartsInfo = financeChartService.querySuccessBorrowChart();
				CacheService.getInstance().put(cacheKey.getBytes(),  SerializeUtil.serialize(publicChartsInfo));
				CacheService.getInstance().expire(cacheKey.getBytes(), 7200);// 2小时候失效
			}
		} else {
			publicChartsInfo = financeChartService.querySuccessBorrowChart();
		}


		if (request.getParameter("chartWidth") != null && !request.getParameter("chartWidth").equals("")) {
			publicChartsInfo.setChartWidth(Integer.parseInt(request.getParameter("chartWidth")));
		}
		if (request.getParameter("chartHight") != null && !request.getParameter("chartHight").equals("")) {
			publicChartsInfo.setChartHight(Integer.parseInt(request.getParameter("chartHight")));
		}
		return publicChartsInfo;
	}

	/**
	 * <p>
	 * Description:待收分布图<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月25日
	 * @param request
	 * @param username
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping(value = "waitReceiveChart")
	@ResponseBody
	public PublicChartsInfoVo waitReceiveChart(HttpServletRequest request, String username) throws Exception {

		String enable = CacheService.getInstance().getEnable();
		PublicChartsInfoVo publicChartsInfo=null;
		String cacheKey="PT_WaitReceiveChart";

		if (enable != null && enable.equals("1")) {
			byte[] key = CacheService.getInstance().getBytes("PT_WaitReceiveChart".getBytes());
			if (key != null) {
				publicChartsInfo = (PublicChartsInfoVo) SerializeUtil.unserialize(key);
			} else {
				publicChartsInfo = financeChartService.queryWaitReceiveChart();
				CacheService.getInstance().put("PT_WaitReceiveChart".getBytes(), SerializeUtil.serialize(publicChartsInfo));
				CacheService.getInstance().expire("PT_WaitReceiveChart".getBytes(), 7200);// 2小时候失效
			}
		} else {
			publicChartsInfo = financeChartService.queryWaitReceiveChart();
		}

		if (request.getParameter("chartWidth") != null && !request.getParameter("chartWidth").equals("")) {
			publicChartsInfo.setChartWidth(Integer.parseInt(request.getParameter("chartWidth")));
		}
		if (request.getParameter("chartHight") != null && !request.getParameter("chartHight").equals("")) {
			publicChartsInfo.setChartHight(Integer.parseInt(request.getParameter("chartHight")));
		}
		return publicChartsInfo;
	}

	/**
	 * <p>
	 * Description:查询金额人数数据<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月25日
	 * @param request
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping(value = "moneyPersonData")
	public ModelAndView moneyPersonData(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("chart/finance/moneyperson");
		String enable = CacheService.getInstance().getEnable();
		DateMoneyVo dateMoneyVo=null;
		String cacheKey="PT_MoneyPersonData";
		if (enable != null && enable.equals("1")) {
			byte[] por_moneyPersonData = CacheService.getInstance().getBytes(cacheKey.getBytes());
			if (por_moneyPersonData != null) {
				dateMoneyVo = (DateMoneyVo) SerializeUtil.unserialize(por_moneyPersonData);

			} else {
				dateMoneyVo=financeChartService.queryDateMoney();
				CacheService.getInstance().put(cacheKey.getBytes(), SerializeUtil.serialize(dateMoneyVo));
				CacheService.getInstance().expire(cacheKey.getBytes(), 7200);// 2小时候失效
			}
		} else {
			dateMoneyVo=financeChartService.queryDateMoney();
		}
		dateMoneyVo.setTransactionAmount(dateMoneyVo.getTransactionAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
//		Map<String, Object> resultMap = financeChartService.queryMoneyPersonData();
		mav.addObject("dateMoneyVo", dateMoneyVo);
		return mav;
	}

	/**
	 * 
	 * <p>
	 * Description:本周数据详情<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月16日
	 * @param request
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping(value = "/weekInfo/{pageSize}")
	public ModelAndView weekInfo(HttpServletRequest request, @PathVariable int pageSize) throws Exception {
		ModelAndView mv = new ModelAndView("/chart/week-info");
		String search_type = request.getParameter("search_type");
		Map<String, String> map = queryTimeByNow(search_type);

		String pageNum_str = request.getParameter("pageNum");
		int pageNum = 1;
		if (pageNum_str != null && !pageNum_str.equals("")) {
			pageNum = Integer.parseInt(pageNum_str);
		}
		RepaymentChartCnd repaymentChartCnd = new RepaymentChartCnd();
		if (map.get("beignTime") != null && !map.get("beignTime").equals("") && !map.get("beignTime").equals("null")) {
			repaymentChartCnd.setBeignTime(map.get("beignTime"));
		}
		if (map.get("endTime") != null && !map.get("endTime").equals("") && !map.get("endTime").equals("null")) {
			repaymentChartCnd.setEndTime(map.get("endTime"));
		}
		String enable = CacheService.getInstance().getEnable();
		Page page=null;
		String cacheKey="PT_weekInfo_1";

		if(search_type ==null && pageNum ==1 ){
			if (enable != null && enable.equals("1")) {
				byte[] weekInfo = CacheService.getInstance().getBytes("PT_weekInfo_1".getBytes());
				if (weekInfo != null) {
					 page = (Page) SerializeUtil.unserialize(weekInfo);
				} else {
					page = financeChartService.findRepaymentChartForWeekPages(repaymentChartCnd, pageNum, pageSize);
					CacheService.getInstance().put("PT_weekInfo_1".getBytes(), SerializeUtil.serialize(page));
					CacheService.getInstance().expire("PT_weekInfo_1".getBytes(), 7200);// 2小时候失效
				}
			} else {
				page = financeChartService.findRepaymentChartForWeekPages(repaymentChartCnd, pageNum, pageSize);
			}
		}else{
			page = financeChartService.findRepaymentChartForWeekPages(repaymentChartCnd, pageNum, pageSize);
		}
		request.setAttribute("repaymentChartVoMap", financeChartService.queryRepaymentChartForWeek(repaymentChartCnd));
		request.setAttribute("page", page);
		request.setAttribute("search_type", search_type);
		return mv;
	}

	/**
	 * 
	 * <p>
	 * Description:逾期列表详情<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月16日
	 * @param request
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping(value = "/overdueInfo/{pageSize}")
	public ModelAndView overdueInfo(HttpServletRequest request, @PathVariable int pageSize) throws Exception {
		ModelAndView mv = new ModelAndView("/chart/overdue-info");
		String pageNum_str = request.getParameter("pageNum");
		int pageNum = 1;
		if (pageNum_str != null && !pageNum_str.equals("")) {
			pageNum = Integer.parseInt(pageNum_str);
		}

		RepaymentChartCnd repaymentChartCnd = new RepaymentChartCnd();
		Page page=null;

		if(pageNum == 1){
			String enable = CacheService.getInstance().getEnable();
			String cacheKey="PT_overdueInfo_1";
			if (enable != null && enable.equals("1")) {
				byte[] overdueInfo = CacheService.getInstance().getBytes(cacheKey.getBytes());
				if (overdueInfo != null) {
					page = (Page) SerializeUtil.unserialize(overdueInfo);

				} else {
					page = financeChartService.findRepaymentChartForOverduePages(repaymentChartCnd, pageNum, pageSize);
					CacheService.getInstance().put(cacheKey.getBytes(), SerializeUtil.serialize(page));
					CacheService.getInstance().expire(cacheKey.getBytes(), 7200);// 2小时候失效
				}
			} else {
				page = financeChartService.findRepaymentChartForOverduePages(repaymentChartCnd, pageNum, pageSize);
			}

		}else {
			page = financeChartService.findRepaymentChartForOverduePages(repaymentChartCnd, pageNum, pageSize);
		}

		request.setAttribute("page", page);
		return mv;
	}

	/**
	 * 
	 * <p>
	 * Description:结清列表详情<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月16日
	 * @param request
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping(value = "/finishInfo/{pageSize}")
	public ModelAndView finishInfo(HttpServletRequest request, @PathVariable int pageSize) throws Exception {
		ModelAndView mv = new ModelAndView("/chart/finish-info");
		String pageNum_str = request.getParameter("pageNum");
		int pageNum = 1;
		if (pageNum_str != null && !pageNum_str.equals("")) {
			pageNum = Integer.parseInt(pageNum_str);
		}
		RepaymentChartCnd repaymentChartCnd = new RepaymentChartCnd();
		Page page =null;
		if(pageNum == 1){
			String enable = CacheService.getInstance().getEnable();
			String cacheKey="PT_finishInfo_1";
			if (enable != null && enable.equals("1")) {
				byte[] finishInfo = CacheService.getInstance().getBytes(cacheKey.getBytes());
				if (finishInfo != null) {
					page = (Page) SerializeUtil.unserialize(finishInfo);

				} else {
					page = financeChartService.findRepaymentChartForFinishPages(repaymentChartCnd, pageNum, pageSize);
					CacheService.getInstance().put(cacheKey.getBytes(), SerializeUtil.serialize(page));
					CacheService.getInstance().expire(cacheKey.getBytes(), 7200);// 2小时候失效
				}
			} else {
				page = financeChartService.findRepaymentChartForFinishPages(repaymentChartCnd, pageNum, pageSize);
			}
		}else{
			page = financeChartService.findRepaymentChartForFinishPages(repaymentChartCnd, pageNum, pageSize);
		}
		request.setAttribute("page", page);
		return mv;
	}

	/**
	 * 
	 * <p>
	 * Description:根据查询匹配类型获取相应的时间段<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月16日
	 * @param search_type
	 * @return Map<String,String>
	 */
	public static Map<String, String> queryTimeByNow(String search_type) {
		Map<String, String> map = new HashMap<String, String>();
		Date now = new Date();
		String beignTime = "";
		String endTime = "";
		if (search_type != null) {
			if (search_type.equals("week")) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(now);
				// 当前日期是星期几
				int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
				if (w == 0) {
					beignTime = DateUtils.dateTime2TimeStamp(DateUtils.format(DateUtils.dayOffset(now, -6), DateUtils.YMD_DASH) + " 00:00:00");
					endTime = DateUtils.dateTime2TimeStamp(DateUtils.format(now, DateUtils.YMD_DASH) + " 23:59:59");
				} else {
					beignTime = DateUtils.dateTime2TimeStamp(DateUtils.format(DateUtils.dayOffset(now, 1 - w), DateUtils.YMD_DASH) + " 00:00:00");
					endTime = DateUtils.dateTime2TimeStamp(DateUtils.format(DateUtils.dayOffset(now, 7 - w), DateUtils.YMD_DASH) + " 23:59:59");
				}
				map.put("beignTime", beignTime);
				map.put("endTime", endTime);
			} else if (search_type.equals("month")) {
				beignTime = DateUtils.dateTime2TimeStamp(DateUtils.format(DateUtils.firstDay(now), DateUtils.YMD_DASH) + " 00:00:00");
				endTime = DateUtils.dateTime2TimeStamp(DateUtils.format(DateUtils.lastDay(now), DateUtils.YMD_DASH) + " 23:59:59");
				map.put("beignTime", beignTime);
				map.put("endTime", endTime);
			} else if (search_type.equals("quarter")) {
				// 当前日期是哪个月
				int now_month = DateUtils.getMonthByDay();
				if (now_month >= 1 && now_month <= 3) {
					beignTime = DateUtils.dateTime2TimeStamp(String.valueOf(DateUtils.getYearByDay()) + "-01-01 00:00:00");
					endTime = DateUtils.dateTime2TimeStamp(String.valueOf(DateUtils.getYearByDay()) + "-03-31 23:59:59");
				} else if (now_month >= 4 && now_month <= 6) {
					beignTime = DateUtils.dateTime2TimeStamp(String.valueOf(DateUtils.getYearByDay()) + "-04-01 00:00:00");
					endTime = DateUtils.dateTime2TimeStamp(String.valueOf(DateUtils.getYearByDay()) + "-06-30 23:59:59");
				} else if (now_month >= 7 && now_month <= 9) {
					beignTime = DateUtils.dateTime2TimeStamp(String.valueOf(DateUtils.getYearByDay()) + "-07-01 00:00:00");
					endTime = DateUtils.dateTime2TimeStamp(String.valueOf(DateUtils.getYearByDay()) + "-09-30 23:59:59");
				} else if (now_month >= 10 && now_month <= 12) {
					beignTime = DateUtils.dateTime2TimeStamp(String.valueOf(DateUtils.getYearByDay()) + "-10-01 00:00:00");
					endTime = DateUtils.dateTime2TimeStamp(String.valueOf(DateUtils.getYearByDay()) + "-12-31 23:59:59");
				}
				map.put("beignTime", beignTime);
				map.put("endTime", endTime);
			} else if (search_type.equals("year")) {
				beignTime = DateUtils.dateTime2TimeStamp(String.valueOf(DateUtils.getYearByDay()) + "-01-01 00:00:00");
				endTime = DateUtils.dateTime2TimeStamp(String.valueOf(DateUtils.getYearByDay()) + "-12-31 23:59:59");
				map.put("beignTime", beignTime);
				map.put("endTime", endTime);
			}
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			// 当前日期是星期几
			int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if (w == 0) {
				beignTime = DateUtils.dateTime2TimeStamp(DateUtils.format(DateUtils.dayOffset(now, -6), DateUtils.YMD_DASH) + " 00:00:00");
				endTime = DateUtils.dateTime2TimeStamp(DateUtils.format(now, DateUtils.YMD_DASH) + " 23:59:59");
			} else {
				beignTime = DateUtils.dateTime2TimeStamp(DateUtils.format(DateUtils.dayOffset(now, 1 - w), DateUtils.YMD_DASH) + " 00:00:00");
				endTime = DateUtils.dateTime2TimeStamp(DateUtils.format(DateUtils.dayOffset(now, 7 - w), DateUtils.YMD_DASH) + " 23:59:59");
			}
			map.put("beignTime", beignTime);
			map.put("endTime", endTime);
		}
		return map;
	}

	public List<RepaymentChartVo> queryRepaymentChartVoList(List<RepaymentChartVo> list) {
		List<RepaymentChartVo> listReturn = new ArrayList<RepaymentChartVo>();
		for (int i = 0; i < list.size(); i++) {
			RepaymentChartVo repaymentChartVo = list.get(i);
			Date repayday = DateUtils.parse(DateUtils.timeStampToDate(repaymentChartVo.getRepaymentTime(), DateUtils.YMD_DASH), DateUtils.YMD_DASH);
			int day = DateUtils.dayDiff(new Date(), repayday);
			if (day > 0) {
				if (repaymentChartVo.getBorrowStatus() == 4 || repaymentChartVo.getBorrowStatus() == 42) {
					BigDecimal account = repaymentChartVo.getRepaymentAccount();
					BigDecimal lateDayInterest = account.multiply(new BigDecimal(Constants.OUT_OF_DAYE_RATE)).multiply(new BigDecimal(day));
					if ((repaymentChartVo.getBorrowStatus() == 42 || repaymentChartVo.getBorrowStatus() == 4) && (repaymentChartVo.getStatus() == 0)) {
						repaymentChartVo.setLateDays(day);
						repaymentChartVo.setLateInterest(lateDayInterest);
					}
				}
			} else {
				repaymentChartVo.setLateDays(0);
				repaymentChartVo.setLateInterest(new BigDecimal(0));
			}
			if (repaymentChartVo.getFirstAccount() != null) {
				repaymentChartVo.setFirstAccount(repaymentChartVo.getFirstAccount().setScale(2, BigDecimal.ROUND_DOWN));
			}
			repaymentChartVo.setRepaymentAccount(repaymentChartVo.getRepaymentAccount().setScale(2, BigDecimal.ROUND_DOWN));
			repaymentChartVo.setLateInterest(repaymentChartVo.getLateInterest().setScale(2, BigDecimal.ROUND_DOWN));
			listReturn.add(repaymentChartVo);
		}
		return listReturn;
	}
}
