package com.dxjr.portal.first.controller;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.service.TendRecordService;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.first.service.FirstBorrowNewService;
import com.dxjr.portal.first.service.FirstBorrowService;
import com.dxjr.portal.first.service.FirstTenderDetailService;
import com.dxjr.portal.first.vo.FirstBorrowCnd;
import com.dxjr.portal.first.vo.FirstBorrowOpenCnd;
import com.dxjr.portal.first.vo.FirstBorrowVo;
import com.dxjr.portal.member.service.BankInfoService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.BankInfoVo;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.red.entity.RedAccount;
import com.dxjr.portal.red.service.RedAccountService;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:优先投标前台Controller<br />
 * </p>
 * 
 * @title FirstBorrowController.java
 * @package com.dxjr.portal.first.controller
 * @author justin.xu
 * @version 0.1 2014年7月11日
 */
@Controller
@RequestMapping(value = "/zhitongche")
public class FirstBorrowController extends BaseController {

	private final static Logger logger = Logger.getLogger(FirstBorrowController.class);
	@Autowired
	private FirstBorrowService firstBorrowService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private FirstTenderDetailService firstTenderDetailService;
	@Autowired
	private BorrowService borrowService;
	@Autowired
	private TendRecordService tendRecordService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private FirstBorrowNewService firstBorrowNewService;
	@Autowired
	private BankInfoService bankInfoService;
	@Autowired
	private CurAccountService curAccountService;
	@Autowired
	private RedAccountService redAccountService;

	/**
	 * 
	 * <p>
	 * Description:进入直通车专区列表页面:value = "/toFirstList" 默认跳转<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年11月20日
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping
	public ModelAndView toFirstList(HttpServletRequest request, HttpServletResponse response) {
		try {
			FirstBorrowVo firstBorrowVo = firstBorrowService.getFirstBorrowForIndex(new Date());
			return redirect("/zhitongche/" + firstBorrowVo.getId() + ".html");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return redirect("/zhitongche/0.html");
	}

	/**
	 * 前台页面 －进入直通车历史列表页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toFirstHistoryIndex")
	public String toFirstBorrowIndex(HttpServletRequest request) {
		return "first/firstHistoryIndex";
	}

	/**
	 * <p>
	 * Description:我要理财－－优先投标历史列表<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年3月3日
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchFirstHistorys/{pageNo}")
	public ModelAndView searchFirstHistorys(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer pageNo) {
		ModelAndView mv = new ModelAndView("first/firstHistoryInner");
		try {
			FirstBorrowCnd firstBorrowCnd = new FirstBorrowCnd();
			firstBorrowCnd.setStatus(Constants.FIRST_BORROW_STATUS_SUCCESS_APPROVE_PASS);
			Page page = firstBorrowService.queryPageListByCnd(firstBorrowCnd, (new Page(pageNo, BusinessConstants.DEFAULT_PAGE_SIZE)));
			mv.addObject("page", page);
		} catch (Exception e) {
			logger.error("查询直通车历史列表出错", e);
		}
		return mv;
	}

	/**
	 * <p>
	 * Description:我要投标-直通车专区<br />
	 * </p>
	 * 
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年9月3日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toInvest")
	public ModelAndView toInvest() {
		FirstBorrowVo firstBorrowVo = firstBorrowService.getLatest();
		return redirect("/zhitongche/" + firstBorrowVo.getId() + ".html");
	}

	/**
	 * <p>
	 * Description:进入直通车开通或详情页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月15日
	 * @param request
	 * @return
	 * @throws Exception
	 *             String
	 */
	@RequestMapping(value = "/{id}")
	public ModelAndView toInvest(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("first/invest");
		try {
			if (null == id) {
				/** 报500错误 */
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return null;
			}
			// 获取优先投标信息
			FirstBorrowVo firstBorrow = firstBorrowService.queryAvailableFirstBorrowById(id);
			if (null == firstBorrow) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}

			if (isLogin()) {
				ShiroUser shiroUser = currentUser();
				// 帐号资金
				AccountVo account = accountService.queryAccountByUserId(shiroUser.getUserId());
				// 获取投标有效金额
				BigDecimal money = new BigDecimal(firstBorrow.getMostAccount());
				// 目前所剩余额
				BigDecimal syje = new BigDecimal(0);
				if (firstBorrow.getStatus() == Constants.FIRST_BORROW_STATUS_APPROVE_PASS) {
					syje = new BigDecimal(firstBorrow.getPlanAccount()).subtract(new BigDecimal(firstBorrow.getAccountYes()));
				}
				if (firstBorrow.getStatus() == Constants.FIRST_BORROW_STATUS_SUCCESS_APPROVE_PASS) {
					syje = new BigDecimal(firstBorrow.getPlanAccount()).subtract(new BigDecimal(firstBorrow.getRealAccount()));
				}
				// BigDecimal effectiveTenderMoney =
				// firstTenderDetailService.getMaxeffectiveMoney(firstBorrow,
				// shiroUser.getUserId(), money, account);
				mav.addObject("effectiveTenderMoney", syje);
				mav.addObject("account", account);
			}

			// 投标直通车数据统计
			// Map<String, Object> fristMapData =
			// firstBorrowService.queryFirstData();
			// request.setAttribute("fristMapData", fristMapData);

			mav.addObject("firstBorrow", firstBorrow);
			// 还差满标金额
			mav.addObject("alsoNeed", firstBorrow.getPlanAccount() - firstBorrow.getAccountYes());
			// 发布时间，格式转换 yyyy/MM/dd HH:mm:ss
			mav.addObject("publishTimeStr", DateUtils.format(firstBorrow.getPublishTime(), DateUtils.YMD_SLAHMS));
			// 取当前时间
			mav.addObject("nowTime", DateUtils.format(new Date(), DateUtils.YMD_SLAHMS));
		} catch (Exception e) {
			logger.error("进入直通车详情出错", e);
		}
		return mav;
	}

	/**
	 * <p>
	 * Description:我要投标-直通车专区-功能详细<br />
	 * </p>
	 * 
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年9月3日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toInvestDetail")
	public ModelAndView toInvestDetail() {
		// 投标直通车数据统计
		Map<String, Object> fristMapData = Collections.emptyMap();
		try {
			fristMapData = firstBorrowService.queryFirstData();
			if (fristMapData.get("firstTotalAccount") != null) {
				fristMapData.put("firstTotalAccount",
						String.valueOf(new BigDecimal(fristMapData.get("firstTotalAccount").toString()).divide(new BigDecimal(10000)).intValue()));
			}
		} catch (Exception e) {
			logger.error("进入直通车详情出错", e);
		}
		return forword("first/investDetail").addObject("fristMapData", fristMapData);
	}

	/**
	 * <p>
	 * Description:我要投标-直通车专区-常见问题<br />
	 * </p>
	 * 
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年9月3日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toInvestFAQ")
	public ModelAndView toInvestFAQ() {
		return forword("first/investFAQ");
	}

	/**
	 * <p>
	 * Description:开通<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年3月6日
	 * @param request
	 * @param session
	 * @param firstBorrowId
	 * @return String
	 */
	@RequestMapping(value = "/tender/{firstBorrowId}")
	public @ResponseBody
	MessageBox tenderFirstBorrow(HttpServletRequest request, HttpSession session, FirstBorrowOpenCnd firstBorrowOpenCnd) {
		String result = "success";
		try {
			ShiroUser shiroUser = currentUser();
			if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_TENDER_FIRST)) {
				return MessageBox.build("0", "");
			}
			 
			// 判断当前用户是否能投标
			result = this.judgTender();
			if (!BusinessConstants.SUCCESS.equals(result)) {
				return MessageBox.build("0", result);
			}
			 
			// 开通直通车,直接生成最终认购记录
			result = firstBorrowNewService.saveTenderFirstBorrowForTenderReal(shiroUser.getUserId(), firstBorrowOpenCnd);
			if (!"success".equals(result)) {
				return new MessageBox("0", result);
			}
		} catch (AppException ae) {
			return new MessageBox("0", ae.getMessage());
		} catch (Exception e) {
			logger.error("开通直通车出错", e);
			return new MessageBox("0", "网络连接异常,请刷新页面或稍后重试！");
		}

		return new MessageBox("1", "开通成功");
	}

	/**
	 * 
	 * <p>
	 * 获取最新直通车信息
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月8日
	 * @param request
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = "/latest")
	public @ResponseBody
	Map<String, Object> getLatest(HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		FirstBorrowVo fbv = null;

		// 获取最新直通车信息
		try {
			fbv = firstBorrowService.getLatest();

			if (null != fbv) {
				map.put("perceived_rate", fbv.getPerceivedRate().toString());
				map.put("periods", fbv.getPeriods().toString());
				map.put("publish_time", DateUtils.format(fbv.getPublishTime(), DateUtils.DATETIME_YMD_DASH));
				map.put("plan_account", fbv.getPlanAccount().toString());
				map.put("lowest_account", fbv.getLowestAccount().toString());
				map.put("lock_limit", fbv.getLockLimit().toString());

				// 发布时间
				map.put("start_date", fbv.getPublishTimeStr());
				// 满标时间
				map.put("end_date", fbv.getSuccessTimeStr());

				int yesAccount = fbv.getAccountYes();
				int planAccount = fbv.getPlanAccount();
				double percent;

				if (yesAccount == 0 | planAccount == 0) {
					map.put("percent", "0");
				} else if (yesAccount == planAccount) {
					map.put("percent", "100");
				} else {
					percent = new BigDecimal(fbv.getAccountYes()).divide(new BigDecimal(fbv.getPlanAccount()), 3, BigDecimal.ROUND_DOWN)
							.doubleValue() * 100;
					// 首页直通车百分比直接截取，不需要四舍五入
					map.put("percent", (int) percent);
				}

			}

		} catch (Exception e) {
			logger.error("获取最新直通车信息出错", e);
		}
		return map;
	}

	/**
	 * <p>
	 * Description:进入投标页面<br />
	 * </p>
	 * 
	 * @author zhanghao
	 * @version 0.1 2014年5月23日
	 * @param request
	 * @return String
	 */
	@RequiresAuthentication
	@RequiresRoles("invest")
	@RequestMapping(value = "/toTender/{id}")
	public ModelAndView toTender(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		ModelAndView view = new ModelAndView("/first/tender/firstborrowtender");
		try {
			ShiroUser shiroUser = currentUser();
			AccountVo accountVo = accountService.queryAccountByUserId(shiroUser.getUserId());
			// 获取优先投标信息
			FirstBorrowVo firstBorrow = firstBorrowService.queryAvailableFirstBorrowById(id);
			if (null == firstBorrow) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
			// 获取投标有效金额
			BigDecimal money = new BigDecimal(firstBorrow.getMostAccount());
			BigDecimal effectiveTenderMoney = firstTenderDetailService.getMaxeffectiveMoneyForTenderReal(firstBorrow, shiroUser.getUserId(), money,
					accountVo);
			view.addObject("reds", redAccountService.getMyReds(shiroUser.getUserId()));
			view.addObject("account", accountVo);
			view.addObject("firstBorrow", firstBorrow);
			view.addObject("alsoNeed", firstBorrow.getPlanAccount() - firstBorrow.getRealAccount());
			view.addObject("effectiveTenderMoney", effectiveTenderMoney.setScale(2, BigDecimal.ROUND_DOWN));
			view.addObject("maxCurMoney", curAccountService.getMaxUseMoneyByNow(shiroUser.getUserId()));
			view.addObject("isExistCurAccount", curAccountService.selectByUserId(shiroUser.getUserId()) == null ? false : true);
			return view;
		} catch (Exception e) {
			logger.error("进入直通车投标页面出错", e);
		}
		return view;
	}

	/**
	 * 
	 * <p>
	 * Description:根据直通车id获取当前用户的有效开通金额<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月7日
	 * @param request
	 * @param id
	 * @return BigDecimal
	 */
	@RequestMapping(value = "/findEffectiveTenderMoney/{id}")
	@ResponseBody
	public BigDecimal findEffectiveTenderMoney(HttpServletRequest request, @PathVariable Integer id) {
		ShiroUser shiroUser = currentUser();
		BigDecimal result = new BigDecimal(0);
		try {
			// 获取优先投标信息
			FirstBorrowVo firstBorrow = firstBorrowService.queryAvailableFirstBorrowById(id);
			AccountVo accountVo = accountService.queryAccountByUserId(shiroUser.getUserId());
			String isUseCurMoney = request.getParameter("isUseCurMoney");
			if (isUseCurMoney != null && !isUseCurMoney.equals("")) {
				firstBorrow.setIsUseCurMoney(isUseCurMoney);
			}
			// 获取投标有效金额
			BigDecimal money = new BigDecimal(firstBorrow.getMostAccount());
			BigDecimal effectiveTenderMoney = firstTenderDetailService.getMaxeffectiveMoneyForTenderReal(firstBorrow, shiroUser.getUserId(), money,
					accountVo);
			result = effectiveTenderMoney;
		} catch (Exception e) {
			result = new BigDecimal(0);
		}
		return result;
	}

	@RequestMapping("/firstInfo")
	public ModelAndView firstInfo() {
		return forword("/first/firstInfo");
	}

	/**
	 * <p>
	 * Description:判断当前用户是否能投标<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年10月29日
	 * @param request
	 * @return
	 * @throws Exception
	 *             String
	 */
	private String judgTender() throws Exception {
		String result = BusinessConstants.SUCCESS;
		if (!isLogin()) {
			return ("请先登录");
		}

		if (!hasRole("invest")) {
			return ("您是借款用户,不能进行此操作");
		}

		ShiroUser shiroUser = currentUser();
		// 查询用户认证信息
		MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
		// 查询用户信息
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setId(shiroUser.getUserId());
		MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
		// 判断是否通过了认证
		if ((memberApproVo == null || memberApproVo.getEmailChecked() == null || memberApproVo.getEmailChecked() != 1)
				&& (memberApproVo.getMobilePassed() == null || memberApproVo.getMobilePassed() != 1)) {
			return ("请先进行邮箱或手机认证！");
		}

		// 检查实名认证
		if (null == memberApproVo.getNamePassed() || memberApproVo.getNamePassed() != Constants.REALNAME_APPR_ISPASSED_PASSED) {
			return ("请先进行实名认证");
		}

		if (null == memberVo.getPaypassword() || "".equals(memberVo.getPaypassword())) {
			return ("您还没有设置交易密码，请先设置");
		}

		// 检查是否绑定了银行卡
		BankInfoVo bankInfoVo = bankInfoService.getUserCurrentCard(shiroUser.getUserId());
		if (null == bankInfoVo) {
			return "请先绑定银行卡!";
		}
		return result;
	}
}
