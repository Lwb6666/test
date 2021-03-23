package com.dxjr.portal.account.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.Account;
import com.dxjr.base.entity.BaseEBankInfo;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.service.CGUtilService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.borrow.service.BorrowDetailService;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.service.TendRecordService;
import com.dxjr.portal.borrow.vo.BorrowDetailVo;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.borrow.vo.TenderBorrowCnd;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.invest.service.InvestRecordService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.BankInfoVo;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.statics.CGBusinessConstants;
import com.dxjr.portal.transfer.service.TransferService;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.UUIDGenerator;

/**
 * <p>
 * Description:投标控制类<br />
 * </p>
 * 
 * @title ToubiaoController.java
 * @package com.dxjr.portal.account.controller
 * @author justin.xu
 * @version 0.1 2015年1月31日
 */
@Controller
@RequestMapping(value = "/toubiao")
public class ToubiaoController extends BaseController {

	public Logger logger = Logger.getLogger(ToubiaoController.class);

	@Autowired
	private AccountService accountService;
	@Autowired
	private InvestRecordService investRecordService;
	@Autowired
	private BorrowDetailService borrowDetailService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private TendRecordService tenderRecordService;
	@Autowired
	private TransferService transferService;
    @Autowired
    private CurAccountService curAccountService;
    @Autowired
    private TendRecordService tendRecordService;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private CGUtilService cGUtilService;

	/**
	 * <p>
	 * Description:我要投标页面 value = "myInvest"<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月19日
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping
	public ModelAndView myInvest(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("account/myInvest1");

		String borrowType = request.getParameter("borrowType");
		request.setAttribute("borrowT", borrowType);
		return mv;
	}

	/**
	 * <p>
	 * Description:进入我要投标详细页面<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月21日
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/{id}")
	public ModelAndView toInvest(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		ModelAndView mv = new ModelAndView("investment/toInvest");
		try {
			if (null == id) {
				/** 报500错误 */
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return null;
			}
			// 获取借款标详情
			Map<String, ?> resultMap = borrowDetailService.queryBorrowDetailInfo(id);
			if (null == resultMap || null == resultMap.get("borrowDetailVo")) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
			BorrowDetailVo borrowDetailVo = (BorrowDetailVo)resultMap.get("borrowDetailVo");
			
			ShiroUser shiroUser = currentUser();
			
			if (borrowDetailVo != null) {
				if (borrowDetailVo.getAreaType() != null && borrowDetailVo.getAreaType() == 1) {  //如果为新手标
					//ShiroUser shiroUser = currentUser();
					if (shiroUser != null && shiroUser.getUserId() != null) {
						// 查询用户信息
						MemberCnd memberCnd = new MemberCnd();
						memberCnd.setId(shiroUser.getUserId());
						MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
						if (memberVo != null) {
							// 有投标记录或者认购债转记录，不可投新手标
							if (tenderRecordService.getTenderPowderCountByUserId(shiroUser.getUserId()) > 0 || transferService.querySubscribesCountByUserId(shiroUser.getUserId()) > 0) {
								// 新手标是否可投标
								mv.addObject("isToTender", "false");
							}else {
								// 新手标是否可投标
								mv.addObject("isToTender", "true");
							}
						}
					}
				}
			}
			//ShiroUser shiroUser = currentUser();
			
			// 获取借款标信息
			BorrowVo borrow = borrowService.selectByPrimaryKey(id);
			// 投资人账户余额
			if (shiroUser != null) {
				// 查询用户信息
				MemberCnd memberCnd = new MemberCnd();
				memberCnd.setId(shiroUser.getUserId());
				MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
				// 获取借款标信息
				//BorrowVo borrow = borrowService.selectByPrimaryKey(id);
				AccountVo accountVo = accountService.queryAccountByUserId(shiroUser.getUserId());
				
				if(borrow.getIsCustody()==null || borrow.getIsCustody()==0){
					BigDecimal useMoney = accountVo.getUseMoney();
					mv.addObject("useMoney", useMoney);
					BigDecimal mostAccount = borrow.getMostAccount() == null ? new BigDecimal(0) : borrow.getMostAccount();
					// 获取投标有效金额
					BigDecimal effectiveTenderMoney = tendRecordService.getMaxeffectiveMoney(borrow, shiroUser.getUserId(), mostAccount.compareTo(BigDecimal.ZERO) < 1 ? borrow.getAccount() : mostAccount,accountVo);
					mv.addObject("effectiveTenderMoney", effectiveTenderMoney.setScale(2, BigDecimal.ROUND_DOWN));
				}else
				//如果是存管标
				if(borrow.getIsCustody()==1){
					if(borrow.getStatus()==2){
					//如果是存管用户
					if(memberVo.getIsCustody()==1){
						String relateNo=UUIDGenerator.generate(CGBusinessConstants.RELATENO);
						String remark="进入投标详情页面，余额查询";
						//调用存管余额查询接口
						String rep= cGUtilService.saveCGAccount(shiroUser, relateNo,remark,borrow.getId());
						//解析报文
						Account account= cGUtilService.parseAQReqXml(rep, shiroUser, remark, relateNo,borrow.getId());
						if(account==null){
							logger.error("返回ERROR报文或验签失败");
							return mv;
						}
						//平台与存管资金校验
						String scene="进入投标详情";//业务发生场景
						account.setUserId(shiroUser.getUserId());
						String msg= cGUtilService.savecheckAccount(account, HttpTookit.getRealIpAddr(request), shiroUser.getPlatform(), scene);
							BaseEBankInfo eUserInfo= cGUtilService.eUserInfo(shiroUser.getUserId());
							mv.addObject("useMoney", account.getEUseMoney());
							mv.addObject("mobile", eUserInfo.getMobileStr());
					}
				}
					
					BigDecimal mostAccount = borrow.getMostAccount() == null ? new BigDecimal(0) : borrow.getMostAccount();
					// 获取投标有效金额
					BigDecimal effectiveTenderMoney = tendRecordService.getCGMaxeffectiveMoney(borrow, shiroUser.getUserId(), mostAccount.compareTo(BigDecimal.ZERO) < 1 ? borrow.getAccount() : mostAccount,accountVo);
					mv.addObject("effectiveTenderMoney", effectiveTenderMoney.setScale(2, BigDecimal.ROUND_DOWN));
					
				}
				
				//新增
				// 获取借款标信息
				//BorrowVo borrow = borrowService.selectByPrimaryKey(id);
			/*	BigDecimal mostAccount = borrow.getMostAccount() == null ? new BigDecimal(0) : borrow.getMostAccount();
				// 获取投标有效金额
				BigDecimal effectiveTenderMoney = tendRecordService.getMaxeffectiveMoney(borrow, shiroUser.getUserId(), mostAccount.compareTo(BigDecimal.ZERO) < 1 ? borrow.getAccount() : mostAccount,accountVo);*/
				mv.addObject("isCustody", memberVo.getIsCustody());
				mv.addObject("account", accountVo);
				mv.addObject("maxCurMoney", curAccountService.getMaxUseMoneyByNow(shiroUser.getUserId()));
				mv.addObject("isExistCurAccount", curAccountService.selectByUserId(shiroUser.getUserId()) == null ? false : true);
				//mv.addObject("effectiveTenderMoney", effectiveTenderMoney.setScale(2, BigDecimal.ROUND_DOWN));
				mv.addObject("remainMoney", borrow.getAccount().subtract(borrow.getAccountYes()).setScale(2, BigDecimal.ROUND_HALF_UP));
				mv.addObject("alsoNeed", borrow.getAccount().subtract(borrow.getAccountYes()).setScale(2, BigDecimal.ROUND_DOWN));
				// 新手标 传来一个标志
				String xs_tag = (String) request.getParameter("xs_tag");
				mv.addObject("xs_tag", xs_tag);
			}
			mv.addObject("borrowIsCustody", borrow.getIsCustody());
			mv.addObject("nowTime", new Date());
			mv.addObject("loginMember", shiroUser);
			mv.addObject("borrow", borrowDetailVo);
			// 待还本息统计数据
			mv.addObject("borrowDetailRepayVo", resultMap.get("borrowDetailRepayVo"));
			// 已还本息总额
			mv.addObject("repaymentYesAccountTotal", resultMap.get("repaymentYesAccountTotal"));
			// 垫付统计数据
			mv.addObject("borrowDetailWebPayVo", resultMap.get("borrowDetailWebPayVo"));
			// 借款者信用档案
			mv.addObject("borrowDetailCreditVo", resultMap.get("borrowDetailCreditVo"));
			// 借款者信息
			mv.addObject("borrower", resultMap.get("borrowerVo"));
			// 借款者抵押信息
			mv.addObject("mortgageVo", resultMap.get("mortgageVo"));
			// 借款者认证信息
			mv.addObject("accountUploadDocs", resultMap.get("accountUploadDocs"));
			// 计算投标100元能赚的利息
			mv.addObject("inter", resultMap.get("inter"));
	 		
		} catch (Exception e) {
			logger.error("进入借款详情出错", e);
		}
		return mv;
	}

	
	/**
	 * <p>
	 * Description:进入我要投标详细页面<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月21日
	 * @param request
	 * @return ModelAndView
	 */
	/*@RequestMapping(value = "/{id}")
	public ModelAndView toInvest(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		ModelAndView mv = new ModelAndView("investment/toInvest");
		try {
			if (null == id) {
				*//** 报500错误 *//*
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return null;
			}
			// 获取借款标详情
			Map<String, ?> resultMap = borrowDetailService.queryBorrowDetailInfo(id);
			if (null == resultMap || null == resultMap.get("borrowDetailVo")) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
			BorrowDetailVo borrowDetailVo = (BorrowDetailVo)resultMap.get("borrowDetailVo");
			if (borrowDetailVo != null) {
				if (borrowDetailVo.getAreaType() != null && borrowDetailVo.getAreaType() == 1) {  //如果为新手标
					ShiroUser shiroUser = currentUser();
					if (shiroUser != null && shiroUser.getUserId() != null) {
						// 查询用户信息
						MemberCnd memberCnd = new MemberCnd();
						memberCnd.setId(shiroUser.getUserId());
						MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
						if (memberVo != null) {
							// 有投标记录或者认购债转记录，不可投新手标
							if (tenderRecordService.getTenderPowderCountByUserId(shiroUser.getUserId()) > 0 || transferService.querySubscribesCountByUserId(shiroUser.getUserId()) > 0) {
								// 新手标是否可投标
								mv.addObject("isToTender", "false");
							}else {
								// 新手标是否可投标
								mv.addObject("isToTender", "true");
							}
						}
					}
				}
			}
			ShiroUser shiroUser = currentUser();
			// 投资人账户余额
			if (shiroUser != null) {
				AccountVo accountVo = accountService.queryAccountByUserId(shiroUser.getUserId());
				BigDecimal useMoney = accountVo.getUseMoney();
				mv.addObject("useMoney", useMoney);
				//新增
				// 获取借款标信息
				BorrowVo borrow = borrowService.selectByPrimaryKey(id);
				BigDecimal mostAccount = borrow.getMostAccount() == null ? new BigDecimal(0) : borrow.getMostAccount();
				// 获取投标有效金额
				BigDecimal effectiveTenderMoney = tendRecordService.getMaxeffectiveMoney(borrow, shiroUser.getUserId(), mostAccount.compareTo(BigDecimal.ZERO) < 1 ? borrow.getAccount() : mostAccount,accountVo);
				mv.addObject("account", accountVo);
				mv.addObject("maxCurMoney", curAccountService.getMaxUseMoneyByNow(shiroUser.getUserId()));
				mv.addObject("isExistCurAccount", curAccountService.selectByUserId(shiroUser.getUserId()) == null ? false : true);
				mv.addObject("effectiveTenderMoney", effectiveTenderMoney.setScale(2, BigDecimal.ROUND_DOWN));
				mv.addObject("remainMoney", borrow.getAccount().subtract(borrow.getAccountYes()).setScale(2, BigDecimal.ROUND_HALF_UP));
				mv.addObject("alsoNeed", borrow.getAccount().subtract(borrow.getAccountYes()).setScale(2, BigDecimal.ROUND_DOWN));
				// 新手标 传来一个标志
				String xs_tag = (String) request.getParameter("xs_tag");
				mv.addObject("xs_tag", xs_tag);
			}
			mv.addObject("nowTime", new Date());
			mv.addObject("loginMember", shiroUser);
			mv.addObject("borrow", borrowDetailVo);
			// 待还本息统计数据
			mv.addObject("borrowDetailRepayVo", resultMap.get("borrowDetailRepayVo"));
			// 已还本息总额
			mv.addObject("repaymentYesAccountTotal", resultMap.get("repaymentYesAccountTotal"));
			// 垫付统计数据
			mv.addObject("borrowDetailWebPayVo", resultMap.get("borrowDetailWebPayVo"));
			// 借款者信用档案
			mv.addObject("borrowDetailCreditVo", resultMap.get("borrowDetailCreditVo"));
			// 借款者信息
			mv.addObject("borrower", resultMap.get("borrowerVo"));
			// 借款者抵押信息
			mv.addObject("mortgageVo", resultMap.get("mortgageVo"));
			// 借款者认证信息
			mv.addObject("accountUploadDocs", resultMap.get("accountUploadDocs"));
			// 计算投标100元能赚的利息
			mv.addObject("inter", resultMap.get("inter"));
	 		
		} catch (Exception e) {
			logger.error("进入借款详情出错", e);
		}
		return mv;
	}
*/
	
	
	
	
	
	
	public ModelAndView toInvest(){
		
		
		
		return null;
		
	}
	
	
	
	/**
	 * <p>
	 * Description:进入我要投标详细页面<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月21日
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "showBorrowDetail/{id}")
	public ModelAndView showBorrowDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		ModelAndView mv = new ModelAndView("investment/toInvest");
		try {
			if (null == id) {
				/** 报500错误 */
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return null;
			}
			// 获取借款标详情
			Map<String, ?> resultMap = borrowDetailService.showBorrowDetailInfo(id);
			if (null == resultMap || null == resultMap.get("borrowDetailVo")) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
			BorrowDetailVo borrowDetailVo = (BorrowDetailVo) resultMap.get("borrowDetailVo");
			// 投资人账户余额
			if (borrowDetailVo != null) {
				AccountVo accountVo = accountService.queryAccountByUserId(borrowDetailVo.getUserId());
				BigDecimal useMoney = accountVo.getUseMoney();
				mv.addObject("useMoney", useMoney);
			}
			mv.addObject("nowTime", new Date());
			mv.addObject("loginMember", null);
			mv.addObject("borrow", resultMap.get("borrowDetailVo"));
			// 待还本息统计数据
			mv.addObject("borrowDetailRepayVo", resultMap.get("borrowDetailRepayVo"));
			// 已还本息总额
			mv.addObject("repaymentYesAccountTotal", resultMap.get("repaymentYesAccountTotal"));
			// 垫付统计数据
			mv.addObject("borrowDetailWebPayVo", resultMap.get("borrowDetailWebPayVo"));
			// 借款者信用档案
			mv.addObject("borrowDetailCreditVo", resultMap.get("borrowDetailCreditVo"));
			// 借款者信息
			mv.addObject("borrower", resultMap.get("borrowerVo"));
			// 借款者抵押信息
			mv.addObject("mortgageVo", resultMap.get("mortgageVo"));
			// 借款者认证信息
			mv.addObject("accountUploadDocs", resultMap.get("accountUploadDocs"));
			// 计算投标100元能赚的利息
			mv.addObject("inter", resultMap.get("inter"));
			mv.addObject("borrowIsCustody", borrowDetailVo.getIsCustody());
			mv.addObject("isCustody", borrowDetailVo.getIsCustody());
		} catch (Exception e) {
			logger.error("进入借款详情出错", e);
		}
		return mv;
	}
	/**
	 * 
	 * <p>
	 * Description:官网改版-散标投资-列表<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2016年1月11日
	 * @param request
	 * @return
	 * ModelAndView
	 */
	@RequestMapping("toubiao1")
	public ModelAndView myInvestCopy(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("account/myInvest1");

		String borrowType = request.getParameter("borrowType");
		request.setAttribute("borrowT", borrowType);
		return mv;
	}

	
	
	
}
