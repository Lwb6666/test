package com.dxjr.wx.favorite.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.base.entity.BaseEBankInfo;
import com.dxjr.base.mapper.BaseEBankInfoMapper;
import com.dxjr.common.page.Page;
import com.dxjr.portal.account.controller.CZBankAccountController;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.service.BorrowReportService;
import com.dxjr.portal.account.service.CashRecordService;
import com.dxjr.portal.account.service.DrawLogRecordService;
import com.dxjr.portal.account.service.InvestReportService;
import com.dxjr.portal.account.service.TodrawLogService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.account.vo.CashRecordCnd;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.service.BankInfoService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.VipLevelService;
import com.dxjr.portal.member.vo.BankInfoVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.MD5;
import com.dxjr.utils.exception.AppException;
import com.dxjr.wx.account.service.SafeCenterService;

/**
 * 提现类
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title WithdrawCashController.java
 * @package com.dxjr.wx.favorite.controller
 * @author ZHUCHEN
 * @version 0.1 2014年10月28日
 */
@Controller
@RequestMapping(value = "/wx/withdrawCash")
public class WithdrawCashController extends BaseController {
	private static final Logger logger = Logger.getLogger(WithdrawCashController.class);
	@Autowired
	private MemberService memberService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private BankInfoService bankInfoService;
	@Autowired
	private BorrowReportService borrowReportService;
	@Autowired
	private TodrawLogService todrawLogService;
	@Autowired
	private DrawLogRecordService drawLogRecordService;
	@Autowired
	private CashRecordService cashRecordService;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private InvestReportService investReportService;
	@Autowired
	private SafeCenterService safeCenterService;
	@Autowired
	private VipLevelService vipLevelService;
	@Autowired
	private BaseEBankInfoMapper baseEBankInfoMapper;
	@Autowired
	private CZBankAccountController czBankAccountController;

	/**
	 * 提现总额
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年11月17日
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/drawTotal")
	@ResponseBody
	public Map<String, Object> drawTotal() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			BigDecimal drawTotal = investReportService.queryCashTotalByMemberId(currentUser().getUserId());
			map.put("drawTotal", drawTotal);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 初始化我要提现首页
	 * <p>
	 * Description:将两个方法合一了（toTopupMain，getcash），以errorCode为媒介，<0则为验证不通过，=0为验证通过 ，并传输数据
	 * </p>
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月23日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/index")
	@ResponseBody
	public Map<String, Object> toTopupMain() {
		Map<String, Object> map = null;
		try {
			ShiroUser shiroUser = currentUser();
			map = safeCenterService.certificationCheck(currentUser(), "mobile", "bank");
			if (map != null) {
				return map;
			}
			map = new HashMap<String, Object>();
			// 验证通过，获取主页所需数据
			map.put("errorCode", "1");
			// 当前帐号信息
			AccountVo accountVo = accountService.queryAccountByUserId(shiroUser.getUserId());
			map.put("accountVo", accountVo);

			// 查询用户信息
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(shiroUser.getUserId());
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			// 未设置交易密码
			if (null == memberVo.getPaypassword() || "".equals(memberVo.getPaypassword())) {
				map.put("nosetPaypassword", true);
			}

			// 当前用户银行卡信息
			BankInfoVo bankInfoVo = bankInfoService.getUserCurrentCard(shiroUser.getUserId());
			map.put("bankCode", bankInfoVo.getBankCode());
			map.put("bankName", bankInfoVo.getBankName());
			map.put("securityCardNum", bankInfoVo.getSecurityCardNum());
			map.put("bankInfoId", bankInfoVo.getId());
			map.put("status", bankInfoVo.getStatus());

			// 是否超级VIP
			if (vipLevelService.getIsSvipByUserId(shiroUser.getUserId())) {
				map.put("svip", "yes");
			}

			// 提现次数
			map.put("getCashedCount", cashRecordService.getCashedCount(shiroUser.getUserId(), new Date()));
		} catch (Exception e) {
			logger.error("微信-初始化我要提现首页异常", e);
			map.put("errorCode", "-4");

		}
		return map;
	}

	/**
	 * 初始化我要提现-存管首页
	 * <p>
	 * Description:将两个方法合一了（toTopupMain，getcash），以errorCode为媒介，<0则为验证不通过，=0为验证通过 ，并传输数据
	 * </p>
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月23日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toCashGc")
	@ResponseBody
	public Map<String, Object> toCashGc() {
		Map<String, Object> map = null;
		try {
			ShiroUser shiroUser = currentUser();
			map = safeCenterService.certificationCheck(currentUser(), "mobile", "bank");
			if (map != null) {
				return map;
			}
			map = new HashMap<String, Object>();
			// 验证通过，获取主页所需数据
			map.put("errorCode", "1");
			// 当前帐号信息
			if (super.judgeBlackByType(BusinessConstants.BLACK_TYPE_CASH)) {
				map.put("isBlack", true);
				return map;
			} else {
				map.put("isBlack", false);
			}
			Integer userId = shiroUser.getUserId();
			// 当前用户资金帐号
			AccountVo accountVo = accountService.queryAccountByUserId(userId);
			// 验证用户是否已经开通账户
			BaseEBankInfo baseEBankInfo = baseEBankInfoMapper.selectByUserId(userId);
			MemberVo memberVo = memberMapper.queryUserBaseInfo(userId);
			map.put("member", memberVo);
			map.put("bankInfo", baseEBankInfo);
			try {
				BigDecimal drawMoney = czBankAccountController.getDrawMoney(baseEBankInfo);
				if (baseEBankInfo != null) {
					map.put("withdrawMoney", drawMoney);
				}
			} catch (Exception e) {
				map.put("msg", "查询可提金额请求超时");
			}

			// 查询用户信息
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(shiroUser.getUserId());
			MemberVo member = memberService.queryMemberByCnd(memberCnd);
			// 未设置交易密码
			if (null == member.getPaypassword() || "".equals(member.getPaypassword())) {
				map.put("nosetPaypassword", true);
			}
			if (baseEBankInfo != null && baseEBankInfo.getStatus() == 1) {
				map.put("isCustody", true);
			} else {
				map.put("isCustody", false);
			}
			map.put("account", accountVo);
		} catch (Exception e) {
			logger.error("微信-初始化我要提现首页异常", e);
			map.put("errorCode", "-4");
		}
		return map;
	}
	/**
	 * 初始化转可提页面
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月28日
	 * @return
	 */
	@RequestMapping(value = "/toGetDraw")
	@ResponseBody
	public Map<String, Object> getcash() {
		Map<String, Object> map = null;
		try {
			map = safeCenterService.certificationCheck(currentUser(), "paypwd", "");
			if (map != null) {
				return map;
			}
			map = new HashMap<String, Object>();
			map.put("flag", "1");
			ShiroUser shiroUser = currentUser();
			if (shiroUser != null) {
				// 当前帐号信息
				AccountVo accountVo = accountService.queryAccountByUserId(shiroUser.getUserId());
				// 计算当前的净值额度
				BigDecimal maxAccount = borrowReportService.queryNetMoneyLimit(shiroUser.getUserId());
				// 查询冻结中的转可提金额
				// BigDecimal noMoneyToal =
				// todrawLogService.queryTodrawNoMoney(shiroUser.getUserId());
				map.put("maxAccount", maxAccount);
				// 实名认证信息
				map.put("accountVo", accountVo);
			} else {
				map.put("flag", "0");
			}
		} catch (Exception e) {
			logger.error("微信-初始化转可提页面初始化异常", e);
			map.put("flag", "0");
		}
		return map;
	}

	/**
	 * 提现记录数据的获取
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月29日
	 * @param request
	 * @param pageNum
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/toCashRecordInner/{pageNum}")
	@ResponseBody
	public Map<String, Object> toCashRecordInner(HttpServletRequest request, @PathVariable int pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CashRecordCnd cashRecordCnd = new CashRecordCnd();
			ShiroUser shiroUser = currentUser();
			if (null == shiroUser) {
				return map;
			}
			cashRecordCnd.setUserId(shiroUser.getUserId());
			Page p = cashRecordService.queryPageListByCnd(cashRecordCnd, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE));

			List<?> cashRecordList = null;
			if (p.getResult() != null) {
				cashRecordList = p.getResult();
				if (cashRecordList.size() == 10)
					map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
			}
			map.put("cashRecordList", cashRecordList);
		} catch (Exception e) {
			logger.error("微信-提现记录数据的获取异常", e);
		}

		return map;
	}

	/**
	 * 转可提记录查询
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author ZHUCHEN
	 * @version 0.1 2014年11月4日
	 * @param request
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/toDrawLogRecord/{pageNum}")
	@ResponseBody
	public Map<String, Object> toDrawLogRecord(HttpServletRequest request, @PathVariable int pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			ShiroUser shiroUser = currentUser();
			if (null == shiroUser) {
				return map;
			}
			Page p = drawLogRecordService.queryDrawPageListByCnd(shiroUser.getUserId(), startTime, endTime, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE));
			List<?> drawLogRecordList = null;
			if (p.getResult() != null) {
				drawLogRecordList = p.getResult();
				if (drawLogRecordList.size() == BusinessConstants.DEFAULT_PAGE_SIZE)
					map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
			}
			map.put("drawLogRecordList", drawLogRecordList);
		} catch (Exception e) {
			logger.error("微信- 转可提记录查询异常", e);
		}
		return map;
	}

	/**
	 * 取消提现
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author ZHUCHEN
	 * @version 0.1 2014年11月17日
	 * @param request
	 * @param session
	 * @param response
	 * @param cashRecordCnd
	 * @return MessageBox
	 */
	@RequestMapping(value = "cancelCash")
	@RequiresAuthentication
	public @ResponseBody
	MessageBox cancelCash(CashRecordCnd cashRecordCnd, String paypassword, String ip) {
		String result = BusinessConstants.SUCCESS;
		try {
			ShiroUser shiroUser = currentUser();
			// 验证交易密码
			paypassword = MD5.toMD5(paypassword);
			MemberVo mem = memberMapper.queryMemberByloginname(shiroUser.getUserName());
			if (!mem.getPaypassword().equals(paypassword)) {
				return MessageBox.build("0", "交易密码错误，请重新输入。");
			}
			cashRecordCnd.setUserId(shiroUser.getUserId());
			result = cashRecordService.saveCancelCash(cashRecordCnd, currentRequest());
			if (!"success".equals(result)) {
				return MessageBox.build("0", result);
			}
		} catch (AppException ae) {
			return MessageBox.build("0", ae.getMessage());
		} catch (Exception e) {
			logger.error("取消提现出错", e);
			return MessageBox.build("0", "网络连接异常，请刷新页面或稍后重试!");
		}
		return new MessageBox("1", result);
	}

}