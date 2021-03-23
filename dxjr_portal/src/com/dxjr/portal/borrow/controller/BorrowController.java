package com.dxjr.portal.borrow.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.borrow.vo.TenderBorrowCnd;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.invest.service.InvestRecordService;
import com.dxjr.portal.lottery.service.LotteryChanceInfoService;
import com.dxjr.portal.member.service.BankInfoService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.BankInfoVo;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.red.service.RedAccountService;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.transfer.service.TransferService;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.exception.AppException;

@Controller
@RequestMapping(value = "/borrow/borrow")
public class BorrowController extends BaseController {

	private final static Logger logger = Logger.getLogger(BorrowController.class);

	@Autowired
	RedAccountService redService;

	@Autowired
	private BorrowService borrowService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TendRecordService tendRecordService;
	@Autowired
	private MemberService memberService;

	@Autowired
	private InvestRecordService investRecordService;
	@Autowired
	private BankInfoService bankInfoService;
	@Autowired
	private TransferService transferService;
	@Autowired
	private LotteryChanceInfoService lotteryChanceInfoService;

	@Autowired
	private CurAccountService curAccountService;

	/**
	 * <p>
	 * Description:分页查询投标中的借款标<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月13日
	 * @param request
	 * @param pageNo
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/forborrowlist")
	public ModelAndView forPledgebid(HttpServletRequest request, Integer pageNo) {
		ModelAndView view = new ModelAndView("/borrow/borrowlist");
		try {
			Page page = borrowService.selectTenderBorrow(new Page(pageNo, BusinessConstants.DEFAULT_PAGE_SIZE));
			view.addObject("page", page);
		} catch (Exception e) {
			logger.error(e);
		}
		return view;
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
		ModelAndView view = new ModelAndView("/borrow/borrowtender");
		try {
			ShiroUser shiroUser = currentUser();
			TenderBorrowCnd tenderBorrowCnd = new TenderBorrowCnd();
			tenderBorrowCnd.setBorrowid(id);
			String result = borrowService.queryTenderDataBefore(tenderBorrowCnd, shiroUser.getUserId());
			if (!BusinessConstants.SUCCESS.equals(result)) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}

			// 新手标 传来一个标志
			String xs_tag = (String) request.getParameter("xs_tag");
			view.addObject("xs_tag", xs_tag);

			AccountVo accountVo = accountService.queryAccountByUserId(shiroUser.getUserId());
			// 获取借款标信息
			BorrowVo borrow = borrowService.selectByPrimaryKey(id);
			BigDecimal mostAccount = borrow.getMostAccount() == null ? new BigDecimal(0) : borrow.getMostAccount();
			// 获取投标有效金额
			BigDecimal effectiveTenderMoney = tendRecordService.getMaxeffectiveMoney(borrow, shiroUser.getUserId(),
					mostAccount.compareTo(BigDecimal.ZERO) < 1 ? borrow.getAccount() : mostAccount, accountVo);
			view.addObject("account", accountVo);
			view.addObject("borrow", borrow);
			view.addObject("remainMoney", borrow.getAccount().subtract(borrow.getAccountYes()).setScale(2, BigDecimal.ROUND_HALF_UP));
			view.addObject("alsoNeed", borrow.getAccount().subtract(borrow.getAccountYes()).setScale(2, BigDecimal.ROUND_DOWN));
			view.addObject("effectiveTenderMoney", effectiveTenderMoney.setScale(2, BigDecimal.ROUND_DOWN));
			view.addObject("maxCurMoney", curAccountService.getMaxUseMoneyByNow(shiroUser.getUserId()));
			view.addObject("isExistCurAccount", curAccountService.selectByUserId(shiroUser.getUserId()) == null ? false : true);

			// 查询用户可用红包
			view.addObject("reds", redService.getMyReds(shiroUser.getUserId()));

			return view;
		} catch (Exception e) {
			logger.error("进入投标页面出错", e);
		}
		return view;
	}

	/**
	 * <p>
	 * Description:根据借款标id获取当前用户的有效投标金额<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年11月10日
	 * @param request
	 * @param id
	 * @return MessageBox
	 */
	@RequestMapping(value = "/findEffectiveTenderMoney/{id}")
	@ResponseBody
	public BigDecimal findEffectiveTenderMoney(HttpServletRequest request, @PathVariable Integer id) {
		ShiroUser shiroUser = currentUser();
		BigDecimal result = new BigDecimal(0);
		try {
			// 获取借款标信息
			BorrowVo borrow = borrowService.selectByPrimaryKey(id);
			BigDecimal mostAccount = borrow.getMostAccount() == null ? new BigDecimal(0) : borrow.getMostAccount();
			AccountVo accountVo = accountService.queryAccountByUserId(shiroUser.getUserId());
			String isUseCurMoney = request.getParameter("isUseCurMoney");
			if (isUseCurMoney != null && !isUseCurMoney.equals("")) {
				borrow.setIsUseCurMoney(isUseCurMoney);
			}
			// 获取投标有效金额
			BigDecimal effectiveTenderMoney = tendRecordService.getMaxeffectiveMoney(borrow, shiroUser.getUserId(),
					mostAccount.compareTo(BigDecimal.ZERO) < 1 ? borrow.getAccount() : mostAccount, accountVo);
			result = effectiveTenderMoney;
		} catch (Exception e) {
			result = new BigDecimal(0);
		}
		return result;
	}

	/**
	 * <p>
	 * Description:手动投标<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月12日
	 * @param request
	 * @param tenderBorrowCnd
	 * @return String
	 */
	@RequestMapping(value = "/tenderBorrow")
	@ResponseBody
	public MessageBox tenderBorrow(HttpServletRequest request, TenderBorrowCnd tenderBorrowCnd) {
		try {
			ShiroUser shiroUser = currentUser();
			// 判断当前用户是否能投标
			String result = this.judgTender();
			if (!BusinessConstants.SUCCESS.equals(result)) {
				return MessageBox.build("0", result);
			}
			// 验证投标数据是否正确
			result = this.validateManualTenderData(shiroUser, tenderBorrowCnd, request);
			if (!BusinessConstants.SUCCESS.equals(result)) {
				return MessageBox.build("0", result);
			}
			if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_TENDER_BORROW)) {
				return new MessageBox("0", "");
			}
			// 增加md5判断，用于检验是否是从本页面进入
			BorrowVo borrowVo = borrowService.selectByPrimaryKey(tenderBorrowCnd.getBorrowid());
			if (borrowVo.getBorrowtype() == 3) { // 净值标
				// 2015-06-01 所有用户不允许发起净值
				String time0601Str = "2015-06-01";
				Date date0601 = DateUtils.parse(time0601Str, "yyyy-MM-dd");
				long time0601Long = date0601.getTime();
				if (time0601Long <= new Date().getTime()) {
					return MessageBox.build("0", "2015年6月1日起所有用户不能投净值标！");
				}
			}

			if (borrowVo.getIsCustody() == 1) {
				return MessageBox.build("0", "非存管标手动投标错误");
			}

			// A轮众筹已结束，判断去除
			/*
			 * if (77345 == tenderBorrowCnd.getBorrowid().intValue()) { Date d = DateUtils.parse("2016-03-08", "yyyy-MM-dd"); long time0601Long = d.getTime(); if (time0601Long <= new Date().getTime())
			 * { return MessageBox.build("0", "A轮众筹定向标已截止投标，谢谢您的支持！"); } }
			 */

			if (null == borrowVo || null == request.getParameter(borrowVo.getUuid())
					|| !request.getParameter(borrowVo.getUuid()).toString().equals(borrowVo.getUuid())) {
				return MessageBox.build("0", "数据不正确,请确认！");
			}
			// 查询投标记录已投总金额
			BigDecimal tenderAccountYes = tendRecordService.getTenderAccountYesByBorrowId(tenderBorrowCnd.getBorrowid());
			if (tenderAccountYes != null) {
				if (borrowVo.getAccountYes().compareTo(tenderAccountYes) != 0) {
					return MessageBox.build("0", "该标已投金额错误，请联系客服！");
				}
			} else {
				if (borrowVo.getAccountYes().compareTo(new BigDecimal(0)) == 1) {
					return MessageBox.build("0", "该标已投金额错误，请联系客服！");
				}
			}
			if (borrowVo.getAreaType() != null && borrowVo.getAreaType() == 1) { // 新手标
				if (tendRecordService.getTenderPowderCountByUserId(shiroUser.getUserId()) > 0) {
					return MessageBox.build("0", "您不是新手，无法投新手标！");
				}
				if (transferService.querySubscribesCountByUserId(shiroUser.getUserId()) > 0) {
					return MessageBox.build("0", "您不是新手，无法投新手标！");
				}
			}

			// 进行手动投标
			tenderBorrowCnd.setRedId(null);// 取消使用红包 12.7
			result = borrowService.saveManualTender(tenderBorrowCnd, shiroUser.getUserId(), HttpTookit.getRealIpAddr(request), Constants.TENDER_TYPE_MANUAL);
			if (!BusinessConstants.SUCCESS.equals(result)) {
				return MessageBox.build("0", result);
			}
			// 自动复审
			borrowService.saveApproveBorrowReCheck(tenderBorrowCnd.getBorrowid(), Constants.AUTO_CHECK_USERID, Constants.AUTO_CHECK_REMARK,
					HttpTookit.getRealIpAddr(request));
			// 发放“首次投资奖或理财里程碑奖”抽奖机会
			/* lotteryChanceInfoService.investLevelAwardLotteryChance(shiroUser.getUserId(), tenderBorrowCnd.getTenderMoney()); */
		} catch (Exception e) {
			logger.error("手动投标失败", e);
			return MessageBox.build("0", "系统繁忙,请刷新页面或稍后重试！");
		}

		return MessageBox.build("1", "投标成功");
	}

	/**
	 * <p>
	 * Description:还款<br />
	 * </p>
	 * 
	 * @author zhanghao
	 * @version 0.1 2014年5月29日
	 * @return String
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/repayBorrow")
	@ResponseBody
	public MessageBox repayBorrow(HttpServletRequest request, HttpSession session, Integer repaymentid) {
		try {
			ShiroUser shiroUser = currentUser();
			String result = borrowService.saveRepayBorrow(repaymentid, HttpTookit.getRealIpAddr(request), shiroUser.getUserId());
			if (!result.equals(BusinessConstants.SUCCESS)) {
				return new MessageBox("0", result);
			}
		} catch (AppException e) {
			logger.error("还款失败", e);
			return new MessageBox("0", "还款失败");
		} catch (Exception e) {
			logger.error("还款失败", e);
			return new MessageBox("0", "还款失败");
		}
		return new MessageBox("1", "还款成功");
	}

	/**
	 * <p>
	 * Description:撤标<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月6日
	 * @param request
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = "/cancelBorrow")
	@ResponseBody
	public String cancelBorrow(HttpServletRequest request, HttpSession session) {
		String result = "";
		String borrowid = request.getParameter("borrowid");
		ShiroUser shiroUser = currentUser();
		if (borrowid == null || borrowid.equals("")) {
			result = "该记录不存在，请刷新后重试！";
		} else {
			try {
				result = borrowService.cancelBorrow(Integer.parseInt(borrowid), shiroUser.getUserId(), HttpTookit.getRealIpAddr(request));
			} catch (AppException e) {
				e.printStackTrace();
				result = "撤标失败！";
			} catch (Exception e) {
				e.printStackTrace();
				result = "撤标失败！";
			}
		}
		return result;
	}

	/**
	 * <p>
	 * 预发标 +最近投标
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月8日
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/mixedBorrowList")
	public ModelAndView mixedBorrowList(HttpServletRequest request) {

		ModelAndView view = new ModelAndView("/borrow/borrowGrid");
		Page page = new Page(1, 2);

		List<BorrowVo> listAdvanced;
		List<BorrowVo> listLatestNotFull;
		List<BorrowVo> listLatestFull;
		List<BorrowVo> list = new ArrayList<BorrowVo>();
		int count;

		try {
			// 预发标
			listAdvanced = borrowService.getAdvanced(1, 2);
			list.addAll(listAdvanced);
			count = list.size();

			/*
			 * if (count < 6) { // 除预发标外 Map<String, Object> map = new HashMap<String, Object>(); map.put("statuses", new Integer[] { 2, 4, 5, 42 }); map.put("orderByStatusSpecified", "Y");
			 * map.put("StatusSpecified", new Integer[] { 2, 4, 5, 42 }); Page p = borrowService.getBorrowList(map, new Page(1, 6)); list.addAll((Collection<? extends BorrowVo>) p.getResult()); count
			 * = list.size(); }
			 */

			if (count < 10) {
				// 未满抵押标
				listLatestNotFull = borrowService.getLatestNotFull(1, 10);
				list.addAll(listLatestNotFull);
				count = list.size();
			}

			if (count < 10) {
				// 已经满的抵押标
				listLatestFull = borrowService.getLatestFull(1, 10);
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
		view.addObject("page", page);
		return view;
	}

	/**
	 * <p>
	 * Description:净值标提前满标<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月27日
	 * @param request
	 * @param session
	 * @return String
	 */
	@RequestMapping(value = "/advanceFullBorrow")
	@ResponseBody
	public String advanceFullBorrow(HttpServletRequest request, HttpSession session) {
		String result = "";
		ShiroUser shiroUser = currentUser();
		String borrowid = request.getParameter("borrowid");
		if (borrowid == null || borrowid.equals("")) {
			result = "该记录不存在，请刷新后重试！";
		} else {
			try {
				result = borrowService.advanceFullBorrow(Integer.parseInt(borrowid), shiroUser.getUserId(), HttpTookit.getRealIpAddr(request));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				result = "提前满标失败";
			} catch (Exception e) {
				e.printStackTrace();
				result = "提前满标失败";
			}
		}
		return result;
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

	/**
	 * <p>
	 * Description:验证手动投标数据是否正确<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月14日
	 * @return
	 * @throws Exception
	 *             String
	 */
	private String validateManualTenderData(ShiroUser shiroUser, TenderBorrowCnd tenderBorrowCnd, HttpServletRequest request) throws Exception {
		String result = BusinessConstants.SUCCESS;
		if (shiroUser.getIsFinancialUser().toString().equals(Constants.IS_BORROWER_USER)) {
			return "借款用户不允许投标！";
		}
		if (null == tenderBorrowCnd.getBorrowid()) {
			return "参数异常！";
		}
		if (null == tenderBorrowCnd.getTenderMoney()) {
			return "投标金额不能为空！";
		}

		/*
		 * if (null == tenderBorrowCnd.getPayPassword()) { return "交易密码不能为空"; }
		 */
		// 股权众筹标只能是100的整数倍
		if (tenderBorrowCnd.getBorrowid().intValue() == 77345) {
			if (tenderBorrowCnd.getTenderMoney().doubleValue() % 100 != 0) {
				return "投标金额必须是100的整数倍";
			}
		}
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setId(shiroUser.getUserId());
		/*
		 * MemberVo memberVo = memberService.queryMemberByCnd(memberCnd); if (null == memberVo.getPaypassword() || !memberVo.getPaypassword().equals(MD5.toMD5(tenderBorrowCnd.getPayPassword()))) {
		 * return "交易密码有误"; }
		 */
		/*
		 * // 验证验证码 String validatecode = tenderBorrowCnd.getCheckCode(); String randCode = (String) request.getSession().getAttribute("randomCode"); if (null == validatecode || null == randCode ||
		 * !validatecode.equals(randCode)) { return "验证码输入有误！"; } else { // 验证码输入正确 立马删除验证码 request.getSession().removeAttribute("randomCode"); }
		 */
		return result;
	}
}
