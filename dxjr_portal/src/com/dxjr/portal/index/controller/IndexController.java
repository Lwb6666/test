package com.dxjr.portal.index.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.CookieGenerator;

import com.dxjr.common.CacheService;
import com.dxjr.common.Dictionary;
import com.dxjr.common.SerializeUtil;
import com.dxjr.common.page.Page;
import com.dxjr.portal.account.service.RiskMarginService;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.chart.mapper.FinanceChartMapper;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.first.service.FirstBorrowService;
import com.dxjr.portal.first.vo.FirstBorrowVo;
import com.dxjr.portal.fix.service.FixBorrowService;
import com.dxjr.portal.fix.vo.FixBorrowCnd;
import com.dxjr.portal.fix.vo.FixBorrowVo;
import com.dxjr.portal.index.vo.CountVo;
import com.dxjr.portal.index.vo.SlideshowCnd;
import com.dxjr.portal.index.vo.SlideshowVo;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.newP.service.NewPService;
import com.dxjr.portal.news_notice.service.NewsNoticeService;
import com.dxjr.portal.news_notice.vo.NewsNoticeCnd;
import com.dxjr.portal.util.JsonUtils;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;

/**
 * @title IndexController.java
 * @package com.dxjr.portal.index.controller
 * @author chenlu
 * @version 0.1 2014年8月6日
 */
@Controller
@RequestMapping(value = "/home")
public class IndexController extends BaseController {

	private final static Logger logger = Logger.getLogger(IndexController.class);

	@Autowired
	private BorrowService borrowService;
	@Autowired
	private RiskMarginService riskMarginService;
	@Autowired
	private FirstBorrowService firstBorrowService;
	@Autowired
	private NewsNoticeService newsNoticeService;

	@Autowired
	private FinanceChartMapper financeChartMapper;
	@Autowired
	private NewPService newPService;

	@Autowired
	private CurAccountService curAccountService;

	@Autowired
	private FixBorrowService fixBorrowService;

	@Autowired
	private MemberService memberService;

	/**
	 * <p>
	 * 查询登陆信息
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月8日
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/index")
	public ModelAndView queryIsLogin(HttpServletRequest request, HttpServletResponse response) {
		/***** 微信站点首页域名 *****/
		final String WX_MAIN_URL = new String("http://m.xxxx.com/");
		// 判断是否需要重定向和跳转
		String ua = request.getHeader("User-Agent");
		if (currentSession().getAttribute("PCplatform") == null) {
			currentSession().setAttribute("PCplatform", request.getParameter("platform"));
		}

		if (isVisitPCVersionPortal(ua, String.valueOf(currentSession().getAttribute("PCplatform")))) {
			if (isMobileTerminal(ua)) {
				return redirect(WX_MAIN_URL);
			}
		}

		ModelAndView mv = new ModelAndView("/common/main");
		ShiroUser shiroUser = currentUser();
		request.setAttribute("loginMember", shiroUser);
		extendLinkSource(request, response);
		request.setAttribute("logotype", "1");
		/** 网站主数据统计开始 胡建盼 */
		Map mainCountMap = null;
		String enable = CacheService.getInstance().getEnable();
		if (enable != null && enable.equals("1")) {
			byte[] mainCount = CacheService.getInstance().getBytes("idx_mainCount1209".getBytes());
			if (mainCount != null) {
				mainCountMap = (Map) SerializeUtil.unserialize(mainCount);

			} else {
				mainCountMap = this.count(request);
				CacheService.getInstance().put("idx_mainCount1209".getBytes(), SerializeUtil.serialize(mainCountMap));
				CacheService.getInstance().expire("idx_mainCount1209".getBytes(), 28800);// 8小时候失效
			}
		} else {
			mainCountMap = this.count(request);
		}

		if (null == mainCountMap) {
//			mv.addObject("TotalMoney", String.valueOf(0)).addObject("RiskMargin", String.valueOf(0)).addObject("FirstAccountMoney", String.valueOf(0))
//					.addObject("firstTotalAccountMoney", String.valueOf(0)).addObject("regCount", String.valueOf(0));
			mv.addObject("TotalMoney", 0);
			mv.addObject("RiskMargin", 0);
			mv.addObject("yestDeal", 0);
			mv.addObject("regCount", 0);
		} else {
//			mv.addObject("TotalMoney", mainCountMap.get("TotalMoney")).addObject("RiskMargin", mainCountMap.get("RiskMargin"))
//					.addObject("FirstAccountMoney", mainCountMap.get("FirstAccountMoney"))
//					.addObject("firstTotalAccountMoney", mainCountMap.get("firstTotalAccountMoney")).addObject("regCount", mainCountMap.get("regCount"));
			mv.addObject("TotalMoney", mainCountMap.get("TotalMoney"));
			mv.addObject("RiskMargin", mainCountMap.get("RiskMargin"));
			mv.addObject("yestDeal", mainCountMap.get("yestDeal"));
			mv.addObject("regCount", mainCountMap.get("regCount"));
		}

		/** 网站主数据统计结束 */

		/** 统计活期宝信息开始 */

		/** 统计活期宝信息结束 */
		// 活期宝加入人数
		// mv.addObject("curAccountCount", curAccountService.queryCurAccountCount());

		/** 开通统计定期宝信息 */

		FixBorrowVo fixBorrow1Vo = null;
		FixBorrowCnd fixBorrowCnd = new FixBorrowCnd();
		fixBorrowCnd.setLockLimit(1);
		List<FixBorrowVo> list1 = null;
		try {
			list1 = fixBorrowService.queryFixBorrowData(fixBorrowCnd);
		} catch (Exception e) {
			logger.error("首页初始化定期宝信息", e);
		}
		if (list1 != null && list1.size() > 0) {
			fixBorrow1Vo = list1.get(0);
		}
		FixBorrowVo fixBorrow3Vo = null;
		fixBorrowCnd.setLockLimit(3);
		List<FixBorrowVo> list3 = null;
		try {
			list3 = fixBorrowService.queryFixBorrowData(fixBorrowCnd);
		} catch (Exception e) {
			logger.error("首页初始化定期宝信息", e);
		}
		if (list3 != null && list3.size() > 0) {
			fixBorrow3Vo = list3.get(0);
		}
		// 定期6月宝
		FixBorrowVo fixBorrow6Vo = null;
		fixBorrowCnd.setLockLimit(6);
		List<FixBorrowVo> list6 = null;
		try {
			list6 = fixBorrowService.queryFixBorrowData(fixBorrowCnd);
		} catch (Exception e) {
			logger.error("首页初始化定期宝信息", e);
		}
		if (list6 != null && list6.size() > 0) {
			fixBorrow6Vo = list6.get(0);
		}
		// 定期12月宝
		FixBorrowVo fixBorrow12Vo = null;
		fixBorrowCnd.setLockLimit(12);
		List<FixBorrowVo> list12 = null;
		try {
			list12 = fixBorrowService.queryFixBorrowData(fixBorrowCnd);
		} catch (Exception e) {
			logger.error("首页初始化定期宝信息", e);
		}
		if (list12 != null && list12.size() > 0) {
			fixBorrow12Vo = list12.get(0);
		}

//		mv.addObject("fixBorrow1Vo", fixBorrow1Vo);
//		mv.addObject("fixBorrow3Vo", fixBorrow3Vo);
//		mv.addObject("fixBorrow6Vo", fixBorrow6Vo);
//		mv.addObject("fixBorrow12Vo", fixBorrow12Vo);

		List<FixBorrowVo> fixList = new ArrayList<>();
		fixList.add(fixBorrow1Vo);
		fixList.add(fixBorrow3Vo);
		fixList.add(fixBorrow6Vo);
		fixList.add(fixBorrow12Vo);
		mv.addObject("fixList", fixList);

		// 新增新手标记录
//		BorrowVo borrowNew=null;
//		try {
//			borrowNew = newPService.getAdvancedNew();
//			if (borrowNew != null) {
//				mv.addObject("borrowNew", borrowNew);
//			}
//		} catch (Exception e) {
//			logger.error("首页取新手标报错", e);
//		}

		// 新增新手宝记录
		FixBorrowVo fixBorrowNew = null;
		try {
			fixBorrowNew = fixBorrowService.getNewFixBorrow();
			if (fixBorrowNew != null) {
				mv.addObject("fixBorrowNew", fixBorrowNew);
			}
		} catch (Exception e) {
			logger.error("首页取新手宝报错", e);
		}

		mv.addObject("nowTime", DateUtils.format(new Date(), DateUtils.YMD_SLAHMS));

		/** 统计直通车信息开始 */
//		Map firstCountMap = getFirstInfo();
//		mv.addObject("periods", firstCountMap.get("periods"));
//		mv.addObject("firstBorrowVo", firstCountMap.get("firstBorrowVo"));
//		// //预期收益
//		String rate = firstCountMap.get("perceived_rate") == null ? "0" : firstCountMap.get("perceived_rate").toString();
//		String[] rateArr = rate.split("-");
//		if (rateArr != null) {
//			if (rateArr.length > 1) {
//				mv.addObject("perceivedRate1", rateArr[0]);
//				mv.addObject("perceivedRate2", rateArr[1]);
//			} else {
//				mv.addObject("perceivedRate1", rateArr[0]);
//				mv.addObject("perceivedRate2", null);
//			}
//
//		}
//		// 开始时间
//		mv.addObject("publish_time", firstCountMap.get("publish_time"));
//		// 计划总额
//		mv.addObject("plan_account", firstCountMap.get("plan_account"));
//		// 加入条件
//		mv.addObject("lowest_account", firstCountMap.get("lowest_account"));
//		Integer percent = Integer.valueOf(firstCountMap.get("percent") == null ? "0" : firstCountMap.get("percent").toString());
//		if (percent == null) {
//			percent = 0;
//		}
//
//		// 设置直通车状态颜色
//		mv.addObject("percent", percent);
//		FirstBorrowVo firstBorrow = (FirstBorrowVo) firstCountMap.get("firstBorrowVo");
//		if (firstBorrow != null) {
//			mv.addObject("firstBorrow", firstBorrow);
//			
//			String firstStatus = "0"; // 准备中
//			if (new Date().compareTo(firstBorrow.getPublishTime()) < 0) {
//				firstStatus = "0"; // 准备中
//			}
//			if (new Date().compareTo(firstBorrow.getPublishTime()) >= 0 && firstBorrow.getStatus().intValue() == 3) {
//				firstStatus = "1"; // 进行中
//			}
//			if ((new Date().compareTo(firstBorrow.getEndTime()) > 0 && firstBorrow.getStatus().intValue() == 3)
//					|| firstBorrow.getStatus().intValue() == 5) {
//				firstStatus = "2"; // 已完成
//			}
//			mv.addObject("firstStatus", firstStatus);
//		}
		/** 统计直通车信息结束 */
		/** 最新公告列表开始 */
		Page noticePage = null;
		if (enable != null && enable.equals("1")) {
			byte[] notice = CacheService.getInstance().getBytes("idx_notice".getBytes());
			if (notice != null) {
				noticePage = (Page) SerializeUtil.unserialize(notice);

			} else {
				noticePage = queryNotice(request, 0, 10);
				CacheService.getInstance().put("idx_notice".getBytes(), SerializeUtil.serialize(noticePage));
				CacheService.getInstance().expire("idx_notice".getBytes(), 1800);
			}
		} else {
			noticePage = queryNotice(request, 0, 10);
		}

		mv.addObject("noticePage", noticePage);
		/** 最新公告列表结束 */
		/** 投资列表开始 */

		Page mixedBorrowList = mixedBorrowList(null);

		mv.addObject("mixedBorrowList", mixedBorrowList);
		/** 投资列表结束 */
		mv.addObject("year", DateUtils.getYear());
		mv.addObject("month", DateUtils.getMonth());

		// 幻灯管理
		SlideshowCnd slideshowCnd = new SlideshowCnd();
		slideshowCnd.setsType(1); // 类型；
		slideshowCnd.setsStart(0);
		slideshowCnd.setsEnd(14); // 首页，14张后换行；
		slideshowCnd.setNowTime(new Date());

		List<SlideshowVo> slideshowVoList = newsNoticeService.queryListSlideshowByCnd(slideshowCnd);
		mv.addObject("slideshowVoList", slideshowVoList);

		// 幻灯管理
		slideshowCnd.setsType(7); // 类型；
		slideshowCnd.setsStart(0);
		slideshowCnd.setsEnd(4); // 公司动态只显示4张；
		slideshowCnd.setNowTime(new Date());

		List<SlideshowVo> dongtaiList = newsNoticeService.queryListSlideshowByCnd(slideshowCnd);
		mv.addObject("dongtaiList", dongtaiList);

		// 元旦活动
		/*
		 * Object yuandan = currentSession().getAttribute("yuandan"); mv.addObject("newYear", yuandan); if (yuandan == null) { currentSession().setAttribute("yuandan", "yuandan"); }
		 */
		return mv;
	}

	@RequestMapping(value = "/getMainDataJsonp", produces = { "text/plain;charset=UTF-8" })
	@ResponseBody
	public String getMainDataJsonp(String jsonpcallback) {
		HashMap<Object, Object> resultMap = new HashMap<>();
		Map<String, String> mainCountMap = this.count(currentRequest());
		if (null == mainCountMap) {
			resultMap.put("TotalMoney", String.valueOf(0));
			resultMap.put("RiskMargin", String.valueOf(0));
			resultMap.put("FirstAccountMoney", String.valueOf(0));
			resultMap.put("firstTotalAccountMoney", String.valueOf(0));
			resultMap.put("regCount", String.valueOf(0));
		} else {
			resultMap.put("TotalMoney", mainCountMap.get("TotalMoney"));
			resultMap.put("RiskMargin", mainCountMap.get("RiskMargin"));
			resultMap.put("FirstAccountMoney", mainCountMap.get("FirstAccountMoney"));
			resultMap.put("firstTotalAccountMoney", mainCountMap.get("firstTotalAccountMoney"));
			resultMap.put("regCount", mainCountMap.get("regCount"));
		}
		return "jsonpcallback" + "(" + JsonUtils.bean2Json(resultMap) + ")";
	}

	/**
	 * <p>
	 * Description:判断是否指定访问PC版本官网<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2015年5月7日
	 * @param ua
	 * @param platform
	 * @return boolean
	 */
	private boolean isVisitPCVersionPortal(String ua, String platform) {
		return ua != null && !"portal".equals(platform);
	}

	/**
	 * <p>
	 * Description:判断是否移动终端访问官网：www.dxjr.com <br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2015年5月7日
	 * @param ua
	 * @return true:移动终端 | false:PC访问
	 */
	private boolean isMobileTerminal(String ua) {
		return ua.indexOf("iPhone") > -1 || ua.indexOf("iPad") > -1 || (ua.indexOf("Android") > -1 && ua.indexOf("WebKit") > -1);
	}

	/**
	 * <p>
	 * 预发标 +最近投标
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年10月11日
	 * @return Page
	 */
	public Page mixedBorrowList(BorrowVo borrowNew) {

		Page page = new Page(1, 2);

		List<BorrowVo> listAdvanced;
		List<BorrowVo> listLatestNotFull;
		List<BorrowVo> listLatestFull;
		List<BorrowVo> list = new ArrayList<BorrowVo>();
		int count;
		try {

			// 新手标
			// borrowNew = newPService.getAdvancedNew();
			if (borrowNew != null) {
				list.add(borrowNew);
				count = list.size();
			}
			// 预发标
			listAdvanced = borrowService.getAdvanced(1, 2);
			list.addAll(listAdvanced);
			count = list.size();

			if (count < 10) {
				// 未满抵押标
				String enable = CacheService.getInstance().getEnable();
				if (enable != null && enable.equals("1")) {
					byte[] notfull = CacheService.getInstance().getBytes("idx_notfull".getBytes());
					if (notfull != null) {
						listLatestNotFull = (List<BorrowVo>) SerializeUtil.unserialize(notfull);

					} else {
						listLatestNotFull = borrowService.getLatestNotFull(1, 10);
						CacheService.getInstance().put("idx_notfull".getBytes(), SerializeUtil.serialize(listLatestNotFull));
						CacheService.getInstance().expire("idx_notfull".getBytes(), 90);
					}
				} else {
					listLatestNotFull = borrowService.getLatestNotFull(1, 10);
				}

				list.addAll(listLatestNotFull);
				count = list.size();
			}

			if (count < 10) {
				// 已经满的抵押标
				// 未满抵押标
				String enable = CacheService.getInstance().getEnable();
				if (enable != null && enable.equals("1")) {
					byte[] full = CacheService.getInstance().getBytes("idx_full".getBytes());
					if (full != null) {
						listLatestFull = (List<BorrowVo>) SerializeUtil.unserialize(full);

					} else {
						listLatestFull = borrowService.getLatestFull(1, 10);
						CacheService.getInstance().put("idx_full".getBytes(), SerializeUtil.serialize(listLatestFull));
						CacheService.getInstance().expire("idx_full".getBytes(), 180);
					}
				} else {
					listLatestFull = borrowService.getLatestFull(1, 10);
				}

				list.addAll(listLatestFull);
				count = list.size();
			}

			// 保留四条记录
			if (count > 10) {
				for (int i = count - 1; i >= 10; i--) {
					list.remove(i);
				}
			}
		} catch (Exception e) {
			logger.error("获取预发标 +最近投标出错", e);
		}

		page.setResult(list);
		return page;
	}

	/**
	 * <p>
	 * Description:查询新闻公告列表<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年10月11日
	 * @param request
	 * @param type
	 * @param pageSize
	 * @return String
	 */
	private Page queryNotice(HttpServletRequest request, int type, int pageSize) {
		int pageNo = 1;
		Page page = null;
		try {
			NewsNoticeCnd newsNoticeCnd = new NewsNoticeCnd();
			newsNoticeCnd.setType(type);
			newsNoticeCnd.setStatus(0);
			page = newsNoticeService.queryListByNewsNoticeCnd(newsNoticeCnd, pageNo, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return page;

	}

	/**
	 * <p>
	 * 获取最新直通车信息
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年10月11日
	 * @return Map
	 */
	private Map<String, Object> getFirstInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		FirstBorrowVo fbv = null;
		try {
			// 获取首页显示的直通车
			fbv = firstBorrowService.getFirstBorrowForIndex(new Date());
			map.put("firstBorrowVo", fbv);
			if (null != fbv) {
				map.put("perceived_rate", fbv.getPerceivedRate().toString());
				map.put("periods", fbv.getPeriods().toString());
				map.put("publish_time", DateUtils.format(fbv.getPublishTime(), DateUtils.DATETIME_YMD_DASH));
				map.put("plan_account", fbv.getPlanAccount());
				map.put("lowest_account", fbv.getLowestAccount());
				int yesAccount = fbv.getAccountYes();
				int planAccount = fbv.getPlanAccount();
				double percent;
				if (fbv.getStatus() == 3) { // 开通中
					if (yesAccount == 0 | planAccount == 0) {
						map.put("percent", "0");
					} else if (yesAccount == planAccount) {
						map.put("percent", "100");
					} else {
						if (fbv.getEndTime().compareTo(new Date()) < 0) {
							map.put("percent", "100");
						} else {
							percent = new BigDecimal(fbv.getAccountYes()).divide(new BigDecimal(fbv.getPlanAccount()), 3, BigDecimal.ROUND_DOWN).doubleValue() * 100;
							// 首页直通车百分比直接截取，不需要四舍五入
							map.put("percent", (int) percent);
						}
					}
				}
				if (fbv.getStatus() == 5) { // 开通完成
					map.put("percent", "100");
				}
			}

		} catch (Exception e) {
			logger.error("获取最新直通车信息出错", e);
		}
		return map;
	}

	/**
	 * <p>
	 * Description:处理外部链接推广来源注册<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年9月1日
	 * @param request
	 *            void
	 */
	private void extendLinkSource(HttpServletRequest request, HttpServletResponse response) {
		String url = request.getHeader("Referer");
		if (url != null && url.indexOf("http://www.wangdai3.com") != -1) {
			request.getSession().setAttribute("wangdai3", true); // 网贷第三方
		}
		/** begin add by hujianpan 2014/8/29 【易瑞特】广告链接来源 */
		String extendLinkSourceName = request.getParameter("source");// 链接来源
		String tid = request.getParameter("tid");// 如果来源于易瑞通，则为必有参数

		CookieGenerator cookieGenerator = new CookieGenerator();
		cookieGenerator.setCookieMaxAge(30 * 24 * 60 * 60);
		// 保存cookie记录
		if (null != tid) {
			cookieGenerator.setCookieName("tid");
			cookieGenerator.addCookie(response, tid);
			request.getSession().setAttribute("tid", tid);
		}
		logger.debug("extendLinkSourceName=============================" + extendLinkSourceName);
		if (!StringUtils.isEmpty(extendLinkSourceName)) {
			String linkSourceValue = Dictionary.getValue(1100, extendLinkSourceName.trim());
			logger.debug("linkSourceValue=============================" + linkSourceValue);
			request.getSession().setAttribute("linkSourceValue", linkSourceValue);
			cookieGenerator.setCookieName("linkSourceValue");
			cookieGenerator.addCookie(response, linkSourceValue);
		}

		/** end add by hujianpan */
	}

	@RequestMapping(value = "/count")
	public @ResponseBody
	Map count(HttpServletRequest request) {

		Map<String, String> map = new HashMap<String, String>();

		// 平台成交总额
		try {
			map.put("TotalMoney", String.valueOf(borrowService.getTotalMoney().divide(new BigDecimal(10000)).intValue()));
		} catch (Exception e) {
			logger.error("获取平台成交总额出错", e);
			return null;
		}

		try {
			// 风险保证金
			map.put("RiskMargin", String.valueOf(riskMarginService.queryRiskMargin().divide(new BigDecimal(10000)).intValue()));
			// 昨日成交：散标，定期宝，标转，车转，以满标&满宝时间为准
			map.put("yestDeal", String.valueOf(riskMarginService.queryYestDeal()));
		} catch (Exception e) {
			logger.error("获取风险保证金出错", e);
			return null;
		}

		try {
//			Map<String, Object> firstData = firstBorrowService.queryFirstData();
			// 优先余额
//			map.put("FirstAccountMoney", String.valueOf(new BigDecimal(firstData.get("firstUseMoney").toString()).intValue()));
			// 投标直通车总额
//			map.put("firstTotalAccountMoney",
//					String.valueOf(new BigDecimal(firstData.get("firstTotalAccount").toString()).divide(new BigDecimal(10000)).intValue()));

			// 获取注册用户
			map.put("regCount", String.valueOf(memberService.getRegistUserCount()));

		} catch (Exception e) {
			logger.error("获取直通车数据出错", e);
			return null;
		}

		return map;
	}

	@RequestMapping(value = "queryCount")
	@ResponseBody
	// 查询统计数据
	public String queryCount(HttpServletRequest request, String jsonpcallback) {

		try {

			DecimalFormat d = new DecimalFormat(",##0.00");

			BigDecimal TotalMoney = borrowService.getTotalMoney();
			BigDecimal RiskMargin = riskMarginService.queryRiskMargin();

			// 投资者总收益
			BigDecimal investerNetMoney = financeChartMapper.queryInvesterNetMoney();

			// 逾期率
			// 正常还款总额
			BigDecimal normalRepaymentMoney = financeChartMapper.queryNormalRepaymentMoney();

			// 逾期总额
			BigDecimal overdueMoney = financeChartMapper.queryOverdueMoney();

			StringBuilder sb = new StringBuilder();
			sb.append("仅");
			String str = overdueMoney.multiply(new BigDecimal(100)).divide(overdueMoney.add(normalRepaymentMoney), 2, BigDecimal.ROUND_HALF_UP).toString();
			sb.append(str);
			sb.append("%");

			// return String str2 =
			// "{\"totalMoney\":\"1\",\"income\":\"2\",\"riskMargin\":\"3\",\"rate\":\"5\"}";
			// return new
			// CountVo(TotalMoney.toString(),RiskMargin.toString(),investerNetMoney.toString(),sb.toString());

			CountVo cv = new CountVo();
			cv.setTotalMoney(d.format(TotalMoney));
			cv.setRiskMargin(d.format(RiskMargin));
			cv.setIncome(d.format(investerNetMoney));
			cv.setRate(sb.toString());

			return jsonpcallback + "(" + JsonUtils.bean2Json(cv) + ")";

		} catch (Exception e) {
			logger.error("资金统计数据查询失败.", e);
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 2周年活动
	 * 
	 * @author WangQianJin
	 * @title @param request
	 * @title @param response
	 * @title @return
	 * @date 2015年9月18日
	 */
	@RequestMapping(value = "/pcTwoYears")
	public ModelAndView pcTwoYears(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/index/pcTwoYears");
		return mv;
	}

}
