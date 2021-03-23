package com.dxjr.portal.account.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import com.dxjr.common.SerializeUtil;
import com.dxjr.common.aop.annotation.GenerateToken;
import com.dxjr.common.aop.annotation.ValidateToken;
import com.dxjr.common.aop.exception.AuthenticationException;
import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.service.AccountUploadDocService;
import com.dxjr.portal.account.service.MyAccountReportService;
import com.dxjr.portal.account.service.MyAccountService;
import com.dxjr.portal.account.service.ShareholderRankService;
import com.dxjr.portal.account.util.UserLevelRatio;
import com.dxjr.portal.account.vo.AccountInfo;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.account.vo.LoginRemindLogVo;
import com.dxjr.portal.account.vo.MyAccountApproMsgVo;
import com.dxjr.portal.account.vo.Percentage;
import com.dxjr.portal.account.vo.ShareholderRankVo;
import com.dxjr.portal.account.vo.UnReceiveInterestCnd;
import com.dxjr.portal.account.vo.VipRemindVo;
import com.dxjr.portal.account.vo.YearCollect;
import com.dxjr.portal.autoInvestConfig.service.AutoInvestService;
import com.dxjr.portal.autoInvestConfig.vo.AutoInvestConfigVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.vo.BorrowCnd;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.fix.service.FixBorrowService;
import com.dxjr.portal.fix.vo.FixBorrowCnd;
import com.dxjr.portal.fix.vo.FixBorrowStaticVo;
import com.dxjr.portal.invest.service.CollectionRecordService;
import com.dxjr.portal.invest.service.CollectionStatisticService;
import com.dxjr.portal.invest.vo.CollectionStatisticCnd;
import com.dxjr.portal.invest.vo.CollectionStatisticVo;
import com.dxjr.portal.lottery.service.LotteryChanceInfoService;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.service.AccountLinkmanService;
import com.dxjr.portal.member.service.BankInfoService;
import com.dxjr.portal.member.service.EmailApprService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.member.service.OnlineCounterService;
import com.dxjr.portal.member.service.RealNameApproService;
import com.dxjr.portal.member.service.VIPApproService;
import com.dxjr.portal.member.vo.AccountLinkmanVo;
import com.dxjr.portal.member.vo.BankInfoVo;
import com.dxjr.portal.member.vo.EmailApproVo;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.MobileApproVo;
import com.dxjr.portal.member.vo.OnlineCounterVo;
import com.dxjr.portal.member.vo.RealNameApproVo;
import com.dxjr.portal.news_notice.service.NewsNoticeService;
import com.dxjr.portal.news_notice.vo.NewsNoticeCnd;
import com.dxjr.portal.red.mapper.RedAccountMapper;
import com.dxjr.portal.repayment.service.BRepaymentRecordService;
import com.dxjr.portal.repayment.vo.BRepaymentRecordVo;
import com.dxjr.portal.repayment.vo.RepaymentRecordCnd;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.stock.service.StockService;
import com.dxjr.portal.stockright.entity.ShareholderRoster;
import com.dxjr.portal.stockright.entity.StockAccount;
import com.dxjr.portal.stockright.entity.StockEntrust;
import com.dxjr.portal.stockright.service.StockAccountService;
import com.dxjr.portal.stockright.service.StockApplyService;
import com.dxjr.portal.stockright.service.StockEntrustService;
import com.dxjr.portal.stockright.vo.StockApplyRequest;
import com.dxjr.portal.stockright.vo.StockEntrustCnd;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.MoneyUtil;

/**
 * <p>
 * Description: 我的账户 MB<br />
 * </p>
 * @title MyAccountController.java
 * @package com.dxjr.portal.account.controller
 * @author HuangJun
 * @version 0.1 2015年5月19日
 */
@Controller
@RequestMapping(value = "/myaccount")
public class MyAccountController extends BaseController {

	public Logger logger = Logger.getLogger(MyAccountController.class);

	// vip提醒cache配置名字
	private static final String vipCache = "vipCache";

	@Autowired
	private MyAccountService myAccountService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountLinkmanService accountLinkmanService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private AccountUploadDocService accountUploadDocService;
	@Autowired
	private NewsNoticeService newsNoticeService;
	@Autowired
	private MyAccountReportService myAccountReportService;
	@Autowired
	private ShareholderRankService shareholderRankService;
	@Autowired
	private BRepaymentRecordService bRepaymentRecordService;
	@Autowired
	private BorrowService borrowService;
	@Autowired
	private CollectionRecordService collectionRecordServiceImpl;
	@Autowired
	private OnlineCounterService onlineCounterService;

	@Autowired
	private MyAccountService myAccountServiceImpl;
	@Autowired
	private RealNameApproService realNameApproService;
	@Autowired
	private EmailApprService emailApprService;
	@Autowired
	private MobileApproService mobileApproService;

	@Autowired
	private VIPApproService vipApproService;
	@Autowired
	private BankInfoService bankInfoService;

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private CollectionStatisticService collectionStatisticService;
	@Autowired
	private FixBorrowService fixBorrowService;
	@Autowired
	private StockApplyService stockApplyService;
	@Autowired
	private RedAccountMapper redAccountMapper;
	@Autowired
	private LotteryChanceInfoService lotteryChanceInfoService;
	@Autowired
	private StockService stockService;  //期权业务类;
	
	@Autowired
	private StockEntrustService stockEntrustService;
	@Autowired
	private StockAccountService stockAccountService;
	@Autowired
	private AutoInvestService autoInvestService;
	@Autowired
	private MemberMapper memberMapper;
	/**
	 * 进入我的帐号页面
	 * <p>
	 * Description:进入我的帐号页面<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年5月13日
	 * @param request
	 * @return
	 * @throws Exception String
	 */
	// 判断是否登录 
	@RequiresAuthentication
	@RequestMapping(value = "toIndexOld")
	public ModelAndView toIndexOld(HttpServletRequest request, HttpServletResponse response) {

		ShiroUser shiroUser = currentUser();

		// if (shiroUser == null) {
		// return redirect("/member/toLoginPage.html");
		// }

		// 默认进入认证失败信息页面
		ModelAndView mav = new ModelAndView("account/myAccountBackmsg");
		MyAccountApproMsgVo myAccountApproMsgVo = new MyAccountApproMsgVo();

		Map<String, BigDecimal> mapCapitalInfo;
		Map<String, Object> mapInvestInfo;
		Map<String, Object> mapLoanInfo;
		Map<String, Object> mapShareholderRankInfo;
		List<BRepaymentRecordVo> listPayment;
		Map<Integer, BorrowVo> borrowMap;

		try {
			/*
			 * // 验证用户的认证信息是否通过（包含邮箱、实名、手机认证） myAccountApproMsgVo = myAccountService .validateAccountAppro(shiroUser.getUserId()); if (!myAccountApproMsgVo.getResult().equals("success")) { //
			 * 保存错误信息返回到前台 mav.addObject("myAccountApproMsgVo", myAccountApproMsgVo); return mav; }
			 */
			// 验证用户的认证信息是否通过（包含邮箱、实名、手机认证）
			myAccountApproMsgVo = myAccountService.validateAccountAppro(shiroUser.getUserId());
			MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());
			// 当前用户资金帐号
			AccountVo accountVo = accountService.queryAccountByUserId(shiroUser.getUserId());
			// 查询当前用户关联人信息.
			AccountLinkmanVo accountLinkmanVo = accountLinkmanService.queryAccountLinkmanByUserId(shiroUser.getUserId());
			// 用户等级
			// String userLevel = memberService.queryUserLevelByMemberId(shiroUser.getUserId());
			UserLevelRatio ul = memberService.queryUserLevel(shiroUser.getUserId());
			// 如果是借款用户，查询出此用户是否已上传过资料
			if (null != shiroUser.getIsFinancialUser() && shiroUser.getIsFinancialUser() == Constants.MEMBER_BORROW_USER) {
				// 查询当前用户上传资料数量
				Integer uploadDocCount = accountUploadDocService.queryUploadDocCountByUserId(shiroUser.getUserId());
				mav.addObject("uploadDocCount", uploadDocCount);
			}
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(shiroUser.getUserId());
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			shiroUser.setHeadImg(memberVo.getHeadimg());
			mav.addObject("sycee", memberVo.getAccumulatepoints());
			mav.addObject("honor", memberVo.getHonor());
			mav.addObject("accountVo", accountVo);
			mav.addObject("userLevel", ul.getO_userLevel());
			mav.addObject("userLevelName", ul.getLevelName());
			mav.addObject("memberVo", shiroUser);
			mav.addObject("accountLinkmanVo", accountLinkmanVo);
			// 用户认证信息
			// mav.addObject("memberApproVo",
			// myAccountApproMsgVo.getMemberApproVo());
			mav.addObject("memberApproVo", memberApproVo);
			// 获取资金信息-收益总额
			mapCapitalInfo = this.queryCapitalInfo();
			// 获取投标信息 (普通标投资（普通标+购买债权）)
			mapInvestInfo = this.queryInvestInfo();
			// 获取融资信息 (我的融资 - 待还罚息 )
			mapLoanInfo = this.queryLoanInfo();
			// 获取股东加权统计信息
			mapShareholderRankInfo = this.queryShareholderRankInfo();

			mav.addObject("mapCapitalInfo", mapCapitalInfo);
			mav.addObject("mapInvestInfo", mapInvestInfo);
			mav.addObject("mapLoanInfo", mapLoanInfo);
			mav.addObject("map", mapShareholderRankInfo.get("mapShareholderRank"));
			mav.addObject("shareholderRank", mapShareholderRankInfo.get("shareholderRank"));
			BorrowCnd borrowCnd = new BorrowCnd();
			borrowCnd.setUserId(shiroUser.getUserId());
			Page p = borrowService.queryTendering(borrowCnd, 1, 5);
			request.setAttribute("latestBorrowList", p.getResult());

			// 待还
			RepaymentRecordCnd repaymentRecordCnd = new RepaymentRecordCnd();
			repaymentRecordCnd.setUserId(shiroUser.getUserId());
			repaymentRecordCnd.setStatus(0);
			Page page = bRepaymentRecordService.queryRepaymentList(repaymentRecordCnd, 1, 5);
			listPayment = (List<BRepaymentRecordVo>) page.getResult();
			borrowMap = borrowService.repaymentList2map(listPayment);

			mav.addObject("listRepayment", listPayment);
			mav.addObject("borrowMap", borrowMap);

			OnlineCounterVo lastOnLineCounterVo = onlineCounterService.queryLastOnlineCounterByUserId(shiroUser.getUserId());
			mav.addObject("lastOnLineCounterVo", lastOnLineCounterVo);

			// 最低入股金额
			mav.addObject("haveStockMoney", memberService.haveStockMoney(shiroUser));

			mav = memberService.addUserNetValue(shiroUser.getUserId(), mav);

			// 安全级别 100/7*i
			Map<Object, Object> safeMap = safetyCertificatComm(shiroUser);
			mav.addObject("safeMap", safeMap);

			// 直通车- 待收利息，
			double ztcDslx = 0.00;
			BigDecimal ztcDslxBd = new BigDecimal(0);
			CollectionStatisticCnd collectionStatisticCnd = new CollectionStatisticCnd();
			collectionStatisticCnd.setUserId(shiroUser.getUserId());
			CollectionStatisticVo collectionStatisticVoDS = collectionStatisticService.myAccountCollectionMoneyDS(collectionStatisticCnd);
			if (collectionStatisticVoDS != null) {
				ztcDslxBd = collectionStatisticVoDS.getInterestSum();
				ztcDslx = ztcDslxBd.doubleValue();
				mav.addObject("ztcDslx", ztcDslx);

			} else {
				mav.addObject("ztcDslx", 0);
			}

			// 普通投标- 待收利息
			BigDecimal ptbDslx = ((BigDecimal) mapInvestInfo.get("interstTotal")).subtract(ztcDslxBd);
			mapInvestInfo.put("interstTotal", ptbDslx);

			// 已收利息
			CollectionStatisticVo collectionStatisticVoYS = collectionStatisticService.myAccountCollectionMoneyYS(collectionStatisticCnd);
			if (collectionStatisticVoYS != null) {
				BigDecimal bdYslx = collectionStatisticVoYS.getRepayYesAccountSum().subtract(collectionStatisticVoYS.getCapitalSum());
				double yslx = bdYslx.doubleValue();
				mav.addObject("yslx", yslx);
				mapInvestInfo.put("yesInterstTotal", ((BigDecimal) mapInvestInfo.get("yesInterstTotal")).subtract(bdYslx));
			} else {
				mav.addObject("yslx", 0);
			}
			// 定期宝信息统计

			List<FixBorrowStaticVo> retFixlists = new ArrayList<FixBorrowStaticVo>();
			FixBorrowCnd fixBorrowCnd = new FixBorrowCnd();
			fixBorrowCnd.setUserId(shiroUser.getUserId());
			retFixlists = fixBorrowService.queryFixAccountInfoMap(fixBorrowCnd);
			Map<String, String> retMap = new HashMap<String, String>();
			if (retFixlists != null && retFixlists.size() > 0) {
				for (FixBorrowStaticVo fixBorrowStaticVo : retFixlists) {
					retMap.put("repayYesAccountYes", MoneyUtil.fmtMoneyByDot(fixBorrowStaticVo.getRepayYesAccountYes()));
					retMap.put("capital", MoneyUtil.fmtMoneyByDot(fixBorrowStaticVo.getCapital().add(fixBorrowStaticVo.getRepayYesAccountNo())));
					retMap.put("repayYesAccountNo", MoneyUtil.fmtMoneyByDot(fixBorrowStaticVo.getRepayYesAccountNo()));
				}
			}

			FixBorrowStaticVo fixBorrowStaticVo = fixBorrowService.queryFixStatusCount(fixBorrowCnd);
			if (fixBorrowStaticVo != null) {
				String jrz = fixBorrowStaticVo.getJrz().toString();
				String syz = fixBorrowStaticVo.getSyz().toString();
				String ytc = fixBorrowStaticVo.getYtc().toString();
				retMap.put("jrz", jrz);
				retMap.put("syz", syz);
				retMap.put("ytc", ytc);
			}
			mav.addObject("retMap", retMap);
			// 上午，下午，晚上好
			String sayHello = getSayHello();
			mav.addObject("sayHello", sayHello);
				 
			// 跳转到正常页面
			mav.setViewName("account/myAccount");
		} catch (Exception e) {
			myAccountApproMsgVo.setMsg("系统异常，请刷新页面或稍后重试");
			e.printStackTrace();
		}
		return mav;
	}

	/**
	 * <p>
	 * Description: 根据时间阶段 返回问好 <br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年5月13日
	 * @return String
	 */
	public String getSayHello() {
		String sayHello = "早上好";

		Calendar cal = Calendar.getInstance();
		System.out.println("cal = " + cal);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		System.out.println("hour = " + hour);
		if (hour >= 6 && hour < 8) {
			sayHello = "早上好";
		} else if (hour >= 8 && hour < 11) {
			sayHello = "上午好";
		} else if (hour >= 11 && hour < 13) {
			sayHello = "中午好";
		} else if (hour >= 13 && hour < 18) {
			sayHello = "下午好";
		} else if (hour >= 18 && hour < 24) {
			sayHello = "晚上好";
		} else {
			System.out.println("凌晨好");
		}

		return sayHello;
	}

	/**
	 * <p>
	 * Description: 安全中心 7项认证 <br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年5月6日
	 * @param shiroUser
	 * @return Map<Object,Object>
	 */
	public Map<Object, Object> safetyCertificatComm(ShiroUser shiroUser) {

		HashMap<Object, Object> retMap = new HashMap<Object, Object>();
		MemberCnd memberCnd = new MemberCnd();
		Integer userId = shiroUser.getUserId();
		int i = 0;// 判断认证通过的个数
		try {

			memberCnd.setId(userId);

			// 登录密码 , 交易密码 认证
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			// 登录密码
			if (null != memberVo && memberVo.getLogpassword() != null) {
				i++;
			}

			// 交易密码
			if (null != memberVo && memberVo.getPaypassword() != null) {
				i++;
			}

			// 实名认证
			RealNameApproVo realNameApproVo = realNameApproService.queryRealNameApproByUserId(userId);
			if (null != realNameApproVo && realNameApproVo.getIsPassed() == 1) {
				i++;
			}

			// 邮箱认证
			EmailApproVo emailApproVo = emailApprService.queryEmailApproByUserId(userId);
			if (null != emailApproVo && emailApproVo.getChecked() == 1) {
				i++;
			}

			// 手机认证
			MobileApproVo mobileAppro = mobileApproService.queryMobileApproByUserId(userId);
			if (null != mobileAppro && mobileAppro.getPassed() == 1) {
				i++;
			}

			// vip 认证
			/*
			 * VIPApproVo vipApproVo = vipApproService.queryVIPApproByUserId(userId); if (null != vipApproVo && vipApproVo.getPassed() == 1) { i++; }
			 */

			// 银行卡认证
			BankInfoVo currentBankCardVo = bankInfoService.getUserCurrentCard(userId);
			if (currentBankCardVo != null && currentBankCardVo.getStatus() == 0) {
				i++;
			}

			retMap.put("memberVo", memberVo);
			retMap.put("realNameApproVo", realNameApproVo);

			retMap.put("emailApproVo", emailApproVo);
			retMap.put("mobileAppro", mobileAppro);

			/* retMap.put("vipApproVo", vipApproVo); */
			retMap.put("currentBankCardVo", currentBankCardVo);

			// 安全等级
			int safeDegree = 0;
			if (i < 6) {
				safeDegree = (int) 100 * i / 6;
			}
			if (i == 6) {
				safeDegree = 100;
			}

			retMap.put("safeDegree", safeDegree);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return retMap;
	}

	// 查询vip提醒保存的缓存信息 | 1），有,是同一天的，返回存在；2）， 有，不是同一天的，重置缓存，返回不存在；3），没缓存的，设置缓存；
	@RequestMapping(value = "getRemindStatus")
	@ResponseBody
	public MessageBox getRemindStatus() throws Exception {

		ShiroUser shiroUser = currentUser();

		if (shiroUser == null) {
			return new MessageBox("-1", "");
		}

		String msg = "";
		VipRemindVo vipRemindVo2 = new VipRemindVo(); // 缓存内容实体类；

		String key = shiroUser.getUserId().toString();

		Cache cache = cacheManager.getCache(vipCache);
		ValueWrapper valueWrapper = cache.get(key);
		if (valueWrapper != null) {

			// Long time = (Long) valueWrapper.get();
			vipRemindVo2 = (VipRemindVo) valueWrapper.get();

			int remindNum2 = vipRemindVo2.getRemindNum();
			Long remindTime2 = vipRemindVo2.getRemindTime();

			// 每天提醒一次的情形，
			// 获得时间：同一天，返回1； | 不是同一天，重置cache；
			if (remindNum2 == 1) {
				if (org.apache.commons.lang3.time.DateUtils.isSameDay(new Date(remindTime2), new Date())) {
					return new MessageBox("1", "");
				} else {
					msg = setRemindStatus();
					return new MessageBox("0", msg);
				}
			}
			// 只提醒一次的情形， | 存在,判断日期间隔是否还大于7，大于不再提醒；小于等于，重新设置缓存等；
			if (remindNum2 == 2) {
				if (DateUtils.dayDiffByDay(new Date(remindTime2), new Date()) > 7) {
					return new MessageBox("1", "");
				} else {
					msg = setRemindStatus();
					return new MessageBox("0", msg);
				}
			}

		} else { // 无缓存的，建立缓存，返回相应的提示；
			msg = setRemindStatus();
			return new MessageBox("0", msg);
		}
		return null;

	}

	// 设置缓存信息，提醒状态 | 提前前，判断实名(从实名表查询数据)；
	public String setRemindStatus() throws Exception {
		ShiroUser shiroUser = currentUser();

		String msg = "";
		String msg2 = "";
		MemberApproVo memberApproVo = memberService.queryMemberApproByUserId(shiroUser.getUserId());

		if (memberApproVo.getVipPassed() != null) {
			String start_indate = DateUtils.format(DateUtils.monthOffset(memberApproVo.getInDate(), -1), DateUtils.YMD_DASH);
			if (Long.parseLong(DateUtils.date2TimeStamp(start_indate)) > Long.parseLong(DateUtils.getTime())) {
				msg = "0";// vip有效期比当前日期大于 1个月，不用提醒
			} else {
				// 需要提醒 1,即将过期；2已过期；3即将过期；

				int dayNum = DateUtils.dayDiffByDay(memberApproVo.getInDate(), new Date());
				// 7天-1月，只提醒一次；
				if (dayNum > 7) {
					msg = "3";
					msg2 = "温馨提示：您的vip临近过期。为享有本息保障，请提前做好VIP续费准备！！";
				}
				// 一星期后过期
				if (dayNum > 0 && dayNum < 8) {
					msg = "1";
					msg2 = "温馨提示：您的vip即将过期。为享有本息保障，请提前做好VIP续费准备！";
				}
				// 过期一星期，
				if (dayNum < 1 && dayNum > -8) {
					msg = "2";
					msg2 = "温馨提示：您的vip已过期。为享有本息保障，请及时做好VIP续费准备！";
				}
				/*
				 * //当天 if(dayNum==0){ //未过期 if (memberApproVo.getInDate().getTime() > new Date().getTime() ) { msg="4"; //当天未过期 msg2="温馨提示：您的vip即将过期。为享有本息保障，请提前做好VIP续费准备！"; }else{ msg="2";
				 * msg2="温馨提示：您的vip已过期。为享有本息保障，请及时做好VIP续费准备！"; } }
				 */

			}

			// 需要提醒的，设置vip提醒缓存； | 每天提醒一次的情形；
			if (msg.equals("1") || msg.equals("2")) {
				String key = shiroUser.getUserId().toString();

				Cache cache = cacheManager.getCache(vipCache);
				ValueWrapper valueWrapper = cache.get(key);
				if (valueWrapper != null) {
					cache.evict(key);
				}
				// 保存数据，key-实体类形式；
				// cache.put(key, System.currentTimeMillis());

				VipRemindVo vipRemindVo = new VipRemindVo();
				vipRemindVo.setRemindNum(1); // 区别于下面的内容；
				vipRemindVo.setRemindTime(System.currentTimeMillis());
				cache.put(key, vipRemindVo);

				// 添加提醒日志
				LoginRemindLogVo loginRemindLogVo = new LoginRemindLogVo();
				loginRemindLogVo.setUserId(shiroUser.getUserId());
				loginRemindLogVo.setMessage(msg2);

				try {
					myAccountService.saveLoginRemindLog(loginRemindLogVo);

				} catch (SQLException e) {
					logger.error("添加登录提醒日志出错");
					e.printStackTrace();
				}
			}
			if (msg.equals("3")) {
				String key = shiroUser.getUserId().toString();

				Cache cache = cacheManager.getCache(vipCache);
				ValueWrapper valueWrapper = cache.get(key);
				if (valueWrapper != null) {
					cache.evict(key);
				}
				// 保存数据，key-实体类形式；
				VipRemindVo vipRemindVo = new VipRemindVo();
				vipRemindVo.setRemindNum(2);
				vipRemindVo.setRemindTime(memberApproVo.getInDate().getTime()); // 保存vip到期时间；
				cache.put(key, vipRemindVo);

				// 添加提醒日志
				LoginRemindLogVo loginRemindLogVo = new LoginRemindLogVo();
				loginRemindLogVo.setUserId(shiroUser.getUserId());
				loginRemindLogVo.setMessage(msg2);

				try {
					myAccountService.saveLoginRemindLog(loginRemindLogVo);

				} catch (SQLException e) {
					logger.error("添加登录提醒日志出错");
					e.printStackTrace();
				}
			}
		}

		return msg;

	}

	/**
	 * <p>
	 * 获取资金信息<br />
	 * </p>
	 * @author chenlu
	 * @version 0.1 2014年8月15日
	 * @return Map
	 * @throws Exception
	 */
	public Map<String, BigDecimal> queryCapitalInfo() throws Exception {
		logger.info("------------ 获取资金信息 s  ------------- ");
		ShiroUser shiroUser = currentUser();
		// 账户详情各种金额的统计
		Map<String, BigDecimal> userDetailMap = myAccountReportService.queryUserAccountDetail(shiroUser.getUserId());
		return userDetailMap;
	}

	/**
	 * <p>
	 * 获取投标信息<br />
	 * 普通投标
	 * </p>
	 * @author chenlu
	 * @version 0.1 2014年8月13日
	 * @return Map
	 * @throws Exception
	 */
	public Map<String, Object> queryInvestInfo() throws Exception {
		logger.info("------------（普通标+购买债权） s ------------------");
		ShiroUser shiroUser = currentUser();
		// 投资详情各种金额的统计
		Map<String, Object> userInvestDetailMap = myAccountReportService.queryUserInvestDetail(shiroUser.getUserId());
		return userInvestDetailMap;
	}

	/**
	 * <p>
	 * 获取融资信息<br />
	 * 我的融资 - 待还罚息
	 * </p>
	 * @author chenlu
	 * @version 0.1 2014年8月13日
	 * @param Map
	 * @throws Exception
	 */
	public Map<String, Object> queryLoanInfo() throws Exception {

		ShiroUser shiroUser = currentUser();
		// 账户详情各种金额的统计
		Map<String, Object> userBorrowDetail = myAccountReportService.queryUserBorrowDetail(shiroUser.getUserId());
		return userBorrowDetail;
	}

	/**
	 * <p>
	 * 获取股东加权统计信息<br />
	 * </p>
	 * @author chenlu
	 * @version 0.1 2014年8月13日
	 * @throws Exception
	 */
	public Map<String, Object> queryShareholderRankInfo() throws Exception {

		ShiroUser shiroUser = currentUser();

		Map<String, Object> mapShareholderRankInfo = new HashMap<String, Object>();

		ShareholderRankVo shareholderRank = shareholderRankService.queryShareholderRankByUserId(shiroUser.getUserId());
		if (shareholderRank == null) {

			Map<String, String> map = shareholderRankService.queryNoRanksByUserId(shiroUser.getUserId());

			if (map.get("day_total") != null && map.get("max_total") != null) {

				DecimalFormat df = new DecimalFormat("0.00");

				String strDayTotal = df.format(map.get("day_total"));
				BigDecimal day_total = new BigDecimal(strDayTotal);

				String strMaxTotal = df.format(map.get("max_total"));
				BigDecimal max_total = new BigDecimal(strMaxTotal);

				if (max_total.compareTo(BigDecimal.ZERO) == 0) {
					map.put("apr", "100");
				} else if (day_total.compareTo(BigDecimal.ZERO) == -1) {
					map.put("apr", "100");
				} else {
					BigDecimal s = day_total.divide(max_total, 4, BigDecimal.ROUND_DOWN);
					map.put("apr", String.valueOf(new BigDecimal(1).subtract(s).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN)));
				}
			}
			mapShareholderRankInfo.put("mapShareholderRank", map);
		}
		mapShareholderRankInfo.put("shareholderRank", shareholderRank);

		return mapShareholderRankInfo;
	}

	/**
	 * <p>
	 * 查询新闻公告列表
	 * </p>
	 * @author chenlu
	 * @version 0.1 2014年8月8日
	 * @param request
	 * @param type
	 * @param pageSize
	 * @return String
	 */
	@RequestMapping(value = "/queryNotice/{type}/{pageSize}")
	public String queryNotice(HttpServletRequest request, @PathVariable int type, @PathVariable int pageSize) {
		String pageNo_str = request.getParameter("pageNo");
		int pageNo = 1;
		if (pageNo_str != null && !pageNo_str.equals("")) {
			pageNo = Integer.parseInt(pageNo_str);
		}
		try {
			NewsNoticeCnd newsNoticeCnd = new NewsNoticeCnd();
			newsNoticeCnd.setType(type);
			newsNoticeCnd.setStatus(0);
			Page page = newsNoticeService.queryListByNewsNoticeCnd(newsNoticeCnd, pageNo, pageSize);
			request.setAttribute("page", page);
			request.setAttribute("type", type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/account/myAccount_rightNotice";

	}

	/**
	 * <p>
	 * Description:待收列表，包含普通待收和投标直通车待收。<br />
	 * </p>
	 * @author chenlu
	 * @version 0.1 2014年8月13日
	 * @param type_collection
	 * @param status_type

	 * @return ModelAndView
	 */
	// 判断是否登录 
	@RequiresAuthentication
	@RequestMapping(value = "collection_record/{status_type}/{type_collection}")
	public ModelAndView collection_record(HttpServletRequest request, @PathVariable String type_collection, @PathVariable String status_type) {

		ModelAndView mv = new ModelAndView("/account/myAccount_rightCollection");
		ShiroUser shiroUser = currentUser();

		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String keyword = request.getParameter("keyword");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("keyword", keyword);
		map.put("status_type", status_type);
		map.put("type_collection", type_collection);
		map.put("user_id", shiroUser.getUserId());

		Page p = new Page();

		try {
			p = collectionRecordServiceImpl.queryMyCollections(map, new Page(5));
		} catch (Exception e) {
			logger.error("查询待收列表出错");
			e.printStackTrace();
		}

		return mv.addObject("CollectionRecordList", p.getResult());
	}

	/**
	 * <p>
	 * Description:进入投标中列表页面<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年9月9日
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "toTendering")
	public ModelAndView toTendering() {
		ModelAndView mv = new ModelAndView("/account/borrow/tendering/tendering-main");
		return mv;
	}

	/**
	 * 进入融资列表页面
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年10月10日
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "borrowList")
	public ModelAndView borrowList() {
		ModelAndView mv = new ModelAndView("/account/borrow/tendering/borrowList");
		return mv;
	}

	/**
	 * <p>
	 * Description: 正在投标中<br />
	 * </p>
	 * @author chenlu
	 * @version 0.1 2014年8月20日
	 * @param session
	 * @param request
	 * @param pPageNum
	 * @param pageSize
	 * @return ModelAndView
	 */
	// 判断是否登录 
	@RequiresAuthentication
	@RequestMapping(value = "queryTendering")
	public ModelAndView queryTendering(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView("/account/borrow/tendering/tendering");
		ShiroUser shiroUser = currentUser();

		// 设置菜单名
		mv.addObject(BusinessConstants.ACCOUNT_FIRST_MENU, BusinessConstants.LEFT_MENU_RZ);
		mv.addObject(BusinessConstants.ACCOUNT_SECOND_MENU, BusinessConstants.LEFT_MENU_RZ_RENDERING);

		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String borrowName = request.getParameter("borrowName");
		int pageNum = 1;
		int pageSize = 10;
		if (request.getParameter("pageNum") != null && !request.getParameter("pageNum").equals("")) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}

		if (request.getParameter("pageSize") != null && !request.getParameter("pageSize").equals("")) {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		if (borrowName != null && !borrowName.equals("")) {
			borrowName = borrowName.trim();
		}
		BorrowCnd borrowCnd = new BorrowCnd();
		borrowCnd.setUserId(shiroUser.getUserId());
		borrowCnd.setName(borrowName);
		borrowCnd.setBeginTime(beginTime);
		borrowCnd.setEndTime(endTime);
		Page page = new Page();
		try {
			page = borrowService.queryTendering(borrowCnd, pageNum, pageSize);
		} catch (Exception e) {
			logger.error("查询正在招标出错");
			e.printStackTrace();
		}
		request.setAttribute("beginTime", beginTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("borrowName", borrowName);
		request.setAttribute("page", page);
		return mv;
	}

	/**
	 * 融资列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年10月10日
	 * @param session
	 * @param request
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "queryAll")
	public ModelAndView queryAll(HttpSession session, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView("/account/borrow/tendering/borrowListDiv");
		ShiroUser shiroUser = currentUser();

		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String borrowName = request.getParameter("borrowName");
		int pageNum = 1;
		int pageSize = 10;
		if (request.getParameter("pageNum") != null && !request.getParameter("pageNum").equals("")) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}

		if (request.getParameter("pageSize") != null && !request.getParameter("pageSize").equals("")) {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		if (borrowName != null && !borrowName.equals("")) {
			borrowName = borrowName.trim();
		}
		BorrowCnd borrowCnd = new BorrowCnd();
		borrowCnd.setUserId(shiroUser.getUserId());
		borrowCnd.setName(borrowName);
		borrowCnd.setBeginTime(beginTime);
		borrowCnd.setEndTime(endTime);
		Page page = new Page();
		try {
			page = borrowService.queryAll(borrowCnd, pageNum, pageSize);
		} catch (Exception e) {
			logger.error("查询融资列表出错");
			e.printStackTrace();
		}
		request.setAttribute("beginTime", beginTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("borrowName", borrowName);
		request.setAttribute("page", page);
		return mv;
	}

	/**
	 * <p>
	 * Description:进入上传头像页面<br />
	 * </p>
	 * @author chenlu
	 * @version 0.1 2014年9月14日
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequiresAuthentication
	@GenerateToken
	@RequestMapping(value = "/addHeadImg")
	public ModelAndView addHeadImg() throws Exception {
		ModelAndView mav = new ModelAndView("account/headupload");
		return mav;
	}

	/**
	 * <p>
	 * Description:上传头像<br />
	 * </p>
	 * @author chenlu
	 * @version 0.1 2014年9月14日

	 * @return String
	 */
	@RequiresAuthentication
	@RequestMapping(value = "saveHeadImg")
	@ValidateToken
	@ResponseBody
	public MessageBox saveHeadImg(@RequestParam("headimg") MultipartFile file, HttpServletRequest request) throws AuthenticationException {
		String result = "success";
		try {
			/*if(!isValidCsrfHeaderToken()){
				return MessageBox.build("0", "网络异常...");
			}*/
			ShiroUser shiroUser = currentUser();
			result = myAccountService.saveHeadImg(file, shiroUser.getUserId(), request);
			if(BusinessConstants.SUCCESS.equals(result)){
				return MessageBox.build("1", "上传成功");
			}
		} catch (Exception e) {
			result = "网络连接异常，请稍候重试！";
			logger.error("上传头像", e);
		}
		return MessageBox.build("0", result);
	}

	/**
	 * 查看会员等级说明.
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toUserLevel")
	public ModelAndView toUserLevel(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/account/userLevel-info");
		try {
			Map<String, BigDecimal> mapCapitalInfo;
			mapCapitalInfo = this.queryCapitalInfo();
			mav.addObject("mapCapitalInfo", mapCapitalInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	/**
	 * <p>
	 * Description:转向视频广告界面<br />
	 * </p>
	 * @author chenlu
	 * @version 0.1 2014年9月16日
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showVideoAD")
	public ModelAndView toShowVideoAD(HttpServletRequest request) {

		return forword("/index/videoAD");
	}

	/**
	 * <p>
	 * Description:查询ip记录<br />
	 * </p>
	 * @author zoulixiang
	 * @version 0.1 2014年9月16日

	 * @return ModelAndView
	 */
	@RequestMapping(value = "/searchIpRecord")
	public ModelAndView searchIpRecord() {
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			return null;
		}

		Integer userId = shiroUser.getUserId();

		Page page = onlineCounterService.queryPageByCnd(userId, new Page(0, 10));

		return forword("/account/showiprecord").addObject("page", page);
	}
	
	
	//======================官网改版 刘涛 开始===============================
	/**
	 * 进入我的帐号页面
	 * <p>
	 * Description:进入我的帐号页面<br />
	 * </p>
	 * @author liutao
	 * @version 0.1 2016年5月25日
	 * @param request
	 * @return
	 * @throws Exception String
	 */
	// 判断是否登录 
	@RequiresAuthentication
	@RequestMapping(value = "toIndex")
	public ModelAndView toIndex(HttpServletRequest request, HttpServletResponse response) {
		ShiroUser shiroUser = currentUser();
		// 默认进入认证失败信息页面
		ModelAndView mav = new ModelAndView("account/myAccountBackmsg");
		MyAccountApproMsgVo myAccountApproMsgVo = new MyAccountApproMsgVo();
		try {
			// 当前用户资金帐号
			AccountVo accountVo = accountService.queryAccountByUserId(shiroUser.getUserId());
			UserLevelRatio ul = memberService.queryUserLevel(shiroUser.getUserId());
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(shiroUser.getUserId());
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			shiroUser.setHeadImg(memberVo.getHeadimg());
			mav.addObject("sycee", memberVo.getAccumulatepoints());
			mav.addObject("honor", memberVo.getHonor());
			mav.addObject("isCustody", memberVo.getIsCustody());
			mav.addObject("accountVo", accountVo);
			mav.addObject("userLevel", ul.getO_userLevel());
			mav.addObject("userLevelName", ul.getLevelName());
			mav.addObject("memberVo", shiroUser);
			UnReceiveInterestCnd unReceiveInterestCnd =new UnReceiveInterestCnd();
			unReceiveInterestCnd.setUserId(shiroUser.getUserId());
			AccountInfo accountInfo=null;
			accountInfo=myAccountReportService.queryAccountInfo(unReceiveInterestCnd);
			if(null!=accountInfo && null!=accountInfo.getPercentage()){
				mav.addObject("fixreax", accountInfo.getPercentage().getFixreax()).addObject("tenderTotal", accountInfo.getPercentage().getTenderTotal()).addObject("currTotal", accountInfo.getPercentage().getCurrTotal()).addObject("useTotal", accountInfo.getPercentage().getUseTotal());
			}else{
				mav.addObject("fixreax", 0).addObject("tenderTotal", 0).addObject("currTotal", 0).addObject("useTotal", 0);
			}
			mav.addObject("accountInfo", accountInfo);
			
			// 最低入股金额
			mav.addObject("haveStockMoney", memberService.haveStockMoney(shiroUser));
			//==============================股权系统代码块开始==============================
			if(shiroUser.getUserId()!=2 && shiroUser.getUserId() != 173438 && shiroUser.getUserId() != 66){
				StockApplyRequest stockApp = new StockApplyRequest();
				stockApp.setUserId(shiroUser.getUserId());
				//查询用户是否在黑名单
				Integer count = stockApplyService.checkBlackList(stockApp);
				//查询用户是否存在委托挂单记录
				StockEntrustCnd entrustCnd = new StockEntrustCnd();
				entrustCnd.setUserId(shiroUser.getUserId());
				entrustCnd.setStatusArray(new Integer[] {1, 2});
				List<StockEntrust> entrustList = stockEntrustService.findEntrustListByUserId(entrustCnd);
				//查询股东账户
				StockAccount account = stockAccountService.selectByPrimaryKey(shiroUser.getUserId());
				
				//=============非A轮股东不能查看内转窗口开始=============
				ShareholderRoster record  = new ShareholderRoster();
				record.setUserId(shiroUser.getUserId());
				record.setVersion(1);
				record.setStatus(1);
				ShareholderRoster shareHolder = stockApplyService.selectShareholder(record);
				if(shareHolder==null){
					mav.addObject("isBlack", "false");
				}
				//=============非A轮股东不能查看内转窗口结束==================
				if(entrustList.size() ==0 && account==null){
					if(count>0){
						mav.addObject("isBlack", "false");
					}
				}
			 }
			//=============================股权系统代码块结束==============================
			
			//查询我的未使用红包及未使用抽奖机会次数
			if(shiroUser!=null&&shiroUser.getUserId()>0){
				int redCount=redAccountMapper.queryRedCount(shiroUser.getUserId());
				int chanceUseNum=lotteryChanceInfoService.selectChanceUseNumTotalByUserId(shiroUser.getUserId());
				mav.addObject("redCount", redCount).addObject("chanceUseNum", chanceUseNum);
				BankInfoVo currentBankCardVo = bankInfoService.getUserCurrentCard(shiroUser.getUserId());
				mav.addObject("currentBankCardVo", currentBankCardVo);
				Map<Object, Object> safeMap = safetyCertificatComm(shiroUser);
				mav.addObject("safeMap", safeMap);
				//判断是否弹出自动投标提示，每天弹出三次后不再弹出
				Boolean flagTemp=true;
				List<AutoInvestConfigVo> autoInvestConfigVoList = autoInvestService.queryListByUserId(shiroUser.getUserId());
				if(null!=autoInvestConfigVoList&&autoInvestConfigVoList.size()>0){
					for (AutoInvestConfigVo autoInvestConfigVo : autoInvestConfigVoList) {
						if (autoInvestConfigVo.getStatus() == 1) {
							 flagTemp=false;
							 break;
						}else{
							 flagTemp=true;
						}
					}
				}else{
					flagTemp=true;
				}
				if(!flagTemp){
					 mav.addObject("isAutoInvest", 1); 
				}else{
					Integer plNumber=memberVo.getPlNumber();
					 if (plNumber != null && (plNumber.intValue()) > 2) {
						 mav.addObject("isAutoInvest", 1); 
					 }else{
						 mav.addObject("isAutoInvest", 0); 
						 memberMapper.updatePlNumberById(memberVo.getId());
					 }
				}
			}
			// 跳转到正常页面
			mav.setViewName("account/myAccount");
			
			int stockNum = stockService.getCountByUserId(shiroUser.getUserId());
			mav.addObject("stockNum",stockNum);
			 
		} catch (Exception e) {
			myAccountApproMsgVo.setMsg("系统异常，请刷新页面或稍后重试");
			e.printStackTrace();
		}
		return mav;
	}
	/**
	 * 查询一年的待收 已收
	 * <p>
	 * Description:查询一年的待收 已收<br />
	 * </p>
	 * @author liutao
	 * @version 0.1 2016年5月25日
	 * @param request
	 * @return
	 * @throws Exception String
	 */
	@RequestMapping(value = "/collect/{year}")
	@ResponseBody
	public List<YearCollect> queryYearCollect(@PathVariable("year") String year) throws Exception {
		if(currentUser()!=null&&currentUser().getUserId()>0){
			Integer userId = currentUser().getUserId();
			List<YearCollect> yearCollects=myAccountService.queryYearCollect(year,userId);
			return  yearCollects;
		}
		return  null;
	}
	/**
	 * 查询某一天的待收 已收
	 * <p>
	 * Description:查询某一天的待收 已收<br />
	 * </p>
	 * @author liutao
	 * @version 0.1 2016年5月25日
	 * @param request
	 * @return
	 * @throws Exception String
	 */
	@RequestMapping(value = "/dayCollect/{dateTime}")
	@ResponseBody
	public YearCollect queryDayCollect(@PathVariable("dateTime") String dateTime) throws Exception {
		if(currentUser()!=null&&currentUser().getUserId()>0){
			Integer userId = currentUser().getUserId();
			YearCollect yearCollect= myAccountService.queryDayCollect(dateTime,userId);
			return  yearCollect;
		}
		return  null;
		
	}
	//======================官网改版 刘涛 结束===============================
}
