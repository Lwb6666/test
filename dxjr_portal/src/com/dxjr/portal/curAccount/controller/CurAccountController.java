package com.dxjr.portal.curAccount.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.dxjr.common.page.Page;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.curAccount.service.CurInterestDetailService;
import com.dxjr.portal.curAccount.vo.CurAccountCnd;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;

/****
 * <p>
 * Description: 活期宝账户 <br />
 * </p>
 * 
 * @title CurAccountController.java
 * @package com.dxjr.portal.curAccount.controller
 * @author HuangJun
 * @version 0.1 2015年4月27日
 */
@Controller
@RequestMapping(value = "/curAccountController")
public class CurAccountController extends BaseController {

	private static final Logger logger = Logger.getLogger(CurAccountController.class);

	// 保存查询条件
	private String beginDay = "";

	private String endDay = "";

	@Autowired
	private CurAccountService curAccountService;

	@Autowired
	private CurInterestDetailService curInterestDetailService;

	public CurAccountController() {

	}

	/***
	 * <p>
	 * Description:活期宝首次进入<br />
	 * </p>
	 * 
	 * @author HuangJun
	 * @version 0.1 2015年5月4日
	 * @param request
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping(value = "/curAccountInterest")
	@RequiresAuthentication
	public ModelAndView curAccountfirstInto(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/curAccount/curAccountInterest");
		try {
			// 活期生息:根据userId 查询curAccount
			ShiroUser shiroUser = super.currentUser();
			List<Map<String, Object>> retLstMap = null;
			CurAccountCnd curAccountCnd = new CurAccountCnd();
			if (shiroUser != null) {
				curAccountCnd.setUserId(shiroUser.getUserId());
			} else {
				logger.error("当前未登陆，无当前登录用户!");
				return new ModelAndView(BusinessConstants.NO_PAGE_FOUND_404);
			}
			retLstMap = curAccountService.queryCurAccountListMap(curAccountCnd);
			if (retLstMap != null && retLstMap.size() > 0) {
				for (Map<String, Object> retMap : retLstMap) {
					String[] total_zu = retMap.get("TOTAL").toString().split("\\.");
					for (int i = 0; i < total_zu.length; i++) {
						retMap.put("total" + i, total_zu[i]);
					}
					String[] interest_yesterday_zu = retMap.get("INTEREST_YESTERDAY").toString().split("\\.");
					for (int j = 0; j < interest_yesterday_zu.length; j++) {
						retMap.put("interest_yesterday" + j, interest_yesterday_zu[j]);
					}
					String[] interest_total_zu = retMap.get("INTEREST_TOTAL").toString().split("\\.");
					for (int k = 0; k < interest_total_zu.length; k++) {
						retMap.put("interest_total" + k, interest_total_zu[k]);
					}
					mv.addObject("retMap", retMap);
				}
			}
		} catch (Exception e) {
			logger.error("活期宝首次进入异常", e);
		}
		return mv;
	}
	/**
	 * <p>
	 * Description:点击tab1- 活期宝收益明细 <br />
	 * </p>
	 * 
	 * @author HuangJun
	 * @version 0.1 2015年5月5日
	 * @param request
	 * @param pageNum
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/tab1InterestDetail/{pageNum}")
	public ModelAndView tab1InterestDetail(HttpServletRequest request, @PathVariable int pageNum, @ModelAttribute CurAccountCnd curAccountCnd) {
		ModelAndView mv = new ModelAndView("/curAccount/curAccount_symx");
		Page retPage = null;
		ShiroUser shiroUser = super.currentUser();
		if (shiroUser != null) {
			curAccountCnd.setUserId(shiroUser.getUserId());
		} else {
			logger.error("当前未登陆，无当前登录用户!");
			return new ModelAndView(BusinessConstants.NO_PAGE_FOUND_404);
		}

		try {

			// 开始day
			String beginDay = curAccountCnd.getBeginDay();
			// 结束 day
			String endDay = curAccountCnd.getEndDay();
			// tag:1:查询    btn,today:今天，3：3个月  6:6个月
			String tag = curAccountCnd.getTag();
			// 收益明细首次加载tag
			String initTag = curAccountCnd.getInitTag();

			// 首次加载，查询7天记录
			if (!StringUtils.isEmpty(initTag) && initTag.equals("0")) {
				// 默认显示7天记录
				String sysDate = DateUtils.getSysdate();
				String sysDate_pre7 = DateUtils.dateFormatPreDay(6);
				curAccountCnd.setBeginDay(sysDate_pre7);
				curAccountCnd.setEndDay(sysDate);
				mv.addObject("beginDay", sysDate_pre7);
				mv.addObject("endDay", sysDate);
			}

			// 非首次加载
			if (!StringUtils.isEmpty(tag)) {
				if (!StringUtils.isEmpty(beginDay) && !StringUtils.isEmpty(endDay) && tag.equalsIgnoreCase("1")) {
					curAccountCnd.setBeginDay(beginDay);
					curAccountCnd.setEndDay(endDay);
					mv.addObject("beginDay", beginDay);
					mv.addObject("endDay", endDay);
				}

				//今天
				if (tag.equalsIgnoreCase("today")) {
					String sysDate = DateUtils.getSysdate();
					curAccountCnd.setBeginDay(sysDate);
					curAccountCnd.setEndDay(sysDate);
					mv.addObject("beginDay", sysDate);
					mv.addObject("endDay", sysDate);
				}
				
				//最近三个月
				if(tag.equalsIgnoreCase("threemonth")){
					String sysDate = DateUtils.getSysdate();
					String sysDate_pre3 =  DateUtils.format(DateUtils.monthOffset(new Date(),-3),DateUtils.YMD_DASH);
					
					curAccountCnd.setBeginDay(sysDate_pre3);
					curAccountCnd.setEndDay(sysDate);
					
					mv.addObject("beginDay", sysDate_pre3);
					mv.addObject("endDay", sysDate);
				}
				
				//最近六个月
				if(tag.equalsIgnoreCase("sixmonth")){
					String sysDate = DateUtils.getSysdate();
					String sysDate_pre6 =  DateUtils.format(DateUtils.monthOffset(new Date(),-6),DateUtils.YMD_DASH);
					
					curAccountCnd.setBeginDay(sysDate_pre6);
					curAccountCnd.setEndDay(sysDate);
					
					mv.addObject("beginDay", sysDate_pre6);
					mv.addObject("endDay", sysDate);
				}
				
				//全部
				if(tag.equalsIgnoreCase("all")){
					curAccountCnd.setBeginDay("");
					curAccountCnd.setEndDay("");
					
					mv.addObject("beginDay", "");
					mv.addObject("endDay", "");
				}
			}

			// 根据userId 查询收益明细,分页
			retPage = curInterestDetailService.queryCurInterestDetailByPage(curAccountCnd, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE));
//			if (retPage != null) {
//				if (!StringUtils.isEmpty(curAccountCnd.getBeginDay()) && !StringUtils.isEmpty(curAccountCnd.getEndDay())) {
//					this.beginDay = curAccountCnd.getBeginDay();
//					this.endDay = curAccountCnd.getEndDay();
//				}
//			}
//			
			mv.addObject("page", retPage);
			mv.addObject("tag",tag);
			
		} catch (Exception e) {
			logger.error("活期宝收益明细 异常", e);
		}
		return mv;
	}

	@RequestMapping(value = "toInterestDetailChart")
	@RequiresAuthentication
	public ModelAndView toInterestDetailChart(HttpServletRequest request, CurAccountCnd curAccountCnd) throws Exception {
		ModelAndView mv = new ModelAndView("/curAccount/curAccount_sychart");
		ShiroUser shiroUser = super.currentUser();
		curAccountCnd.setUserId(shiroUser.getUserId());
		// 开始day
		String beginDay = curAccountCnd.getBeginDay();
		// 结束 day
		String endDay = curAccountCnd.getEndDay();
		// tag:1:查询btn,7:7天，30：30天
		String tag = curAccountCnd.getTag();
		// 收益明细首次加载tag
		String initTag = curAccountCnd.getInitTag();
		
		// 首次加载，查询30天记录
		if (!StringUtils.isEmpty(initTag) && initTag.equals("0")) {
			// 默认显示30天记录
			String sysDate = DateUtils.getSysdate();
			String sysDate_pre30 = DateUtils.dateFormatPreDay(29);
			curAccountCnd.setBeginDay(sysDate_pre30);
			curAccountCnd.setEndDay(sysDate);
			mv.addObject("beginDay", sysDate_pre30);
			mv.addObject("endDay", sysDate);
		}

		// 非首次加载
		if (!StringUtils.isEmpty(tag)) {
			if (!StringUtils.isEmpty(beginDay) && !StringUtils.isEmpty(endDay) && tag.equalsIgnoreCase("1")) {
				curAccountCnd.setBeginDay(beginDay);
				curAccountCnd.setEndDay(endDay);
				mv.addObject("beginDay", beginDay);
				mv.addObject("endDay", endDay);
			}

			// 最近7天
			if (tag.equalsIgnoreCase("7")) {
				String sysDate = DateUtils.getSysdate();
				String sysDate_pre7 = DateUtils.dateFormatPreDay(6);
				curAccountCnd.setBeginDay(sysDate_pre7);
				curAccountCnd.setEndDay(sysDate);
				mv.addObject("beginDay", sysDate_pre7);
				mv.addObject("endDay", sysDate);
			}

			// 最近30天
			if (tag.equalsIgnoreCase("30")) {
				String sysDate = DateUtils.getSysdate();
				String sysDate_pre30 = DateUtils.dateFormatPreDay(29);
				curAccountCnd.setBeginDay(sysDate_pre30);
				curAccountCnd.setEndDay(sysDate);
				mv.addObject("beginDay", sysDate_pre30);
				mv.addObject("endDay", sysDate);
			}
		}
		List<BigDecimal> moneyList = curInterestDetailService.queryInterestMoneyListByUserId(curAccountCnd, new Page(1, 10));
		List<Date> interestDateList = curInterestDetailService.queryDayInterestDateListByUserId(curAccountCnd, new Page(1, 10));
		mv.addObject("moneyList", moneyList);
		mv.addObject("dateList", interestDateList);
		return mv;
	}

	/**
	 * <p>
	 * Description:收益折线图<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2016年1月20日
	 * @param request
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 */
	@RequestMapping(value = "interestDetailChart")
	@RequiresAuthentication
	@ResponseBody
	public Map<String, Object> interestDetailChart(HttpServletRequest request, CurAccountCnd curAccountCnd) throws Exception {
		ShiroUser shiroUser = super.currentUser();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		curAccountCnd.setUserId(shiroUser.getUserId());
		//默认一个月
		String beginDay = DateUtils.format(DateUtils.monthOffset(new Date(),-1),DateUtils.YMD_DASH);
		String endDay = DateUtils.getSysdate();   
		
		curAccountCnd.setBeginDay(beginDay);
		curAccountCnd.setEndDay(endDay);
		
		// tag:1:查询btn,7:7天，30：30天
		//String tag = curAccountCnd.getTag();
		// 收益明细首次加载tag
		//String initTag = curAccountCnd.getInitTag();

		// 首次加载，查询30天记录
//		if (!StringUtils.isEmpty(initTag) && initTag.equals("0")) {
//			// 默认显示30天记录
//			String sysDate = DateUtils.getSysdate();
//			String sysDate_pre30 = DateUtils.dateFormatPreDay(29);
//			curAccountCnd.setBeginDay(sysDate_pre30);
//			curAccountCnd.setEndDay(sysDate);
//			resultMap.put("beginDay", sysDate_pre30);
//			resultMap.put("endDay", sysDate);
//		}
//
//		// 非首次加载
//		if (!StringUtils.isEmpty(tag)) {
//			if (!StringUtils.isEmpty(beginDay) && !StringUtils.isEmpty(endDay) && tag.equalsIgnoreCase("1")) {
//				curAccountCnd.setBeginDay(beginDay);
//				curAccountCnd.setEndDay(endDay);
//				resultMap.put("beginDay", beginDay);
//				resultMap.put("endDay", endDay);
//			}
//
//			// 最近7天
//			if (tag.equalsIgnoreCase("7")) {
//				String sysDate = DateUtils.getSysdate();
//				String sysDate_pre7 = DateUtils.dateFormatPreDay(6);
//				curAccountCnd.setBeginDay(sysDate_pre7);
//				curAccountCnd.setEndDay(sysDate);
//				resultMap.put("beginDay", sysDate_pre7);
//				resultMap.put("endDay", sysDate);
//			}
//
//			// 最近30天
//			if (tag.equalsIgnoreCase("30")) {
//				String sysDate = DateUtils.getSysdate();
//				String sysDate_pre30 = DateUtils.dateFormatPreDay(29);
//				curAccountCnd.setBeginDay(sysDate_pre30);
//				curAccountCnd.setEndDay(sysDate);
//				resultMap.put("beginDay", sysDate_pre30);
//				resultMap.put("endDay", sysDate);
//			}
//		}
		List<BigDecimal> moneyList = curInterestDetailService.queryInterestMoneyListByUserId(curAccountCnd, new Page(1, 10));
		List<Date> interestDateList = curInterestDetailService.queryDayInterestDateListByUserId(curAccountCnd, new Page(1, 10));
		resultMap.put("moneyList", moneyList);
		resultMap.put("dateList", interestDateList);
		return resultMap;
	}

}
