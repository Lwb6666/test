package com.dxjr.wx.favorite.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.base.entity.Account;
import com.dxjr.base.entity.BaseEBankInfo;
import com.dxjr.common.page.Page;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.service.CGUtilService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.borrow.service.BorrowDetailService;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.service.TendRecordService;
import com.dxjr.portal.borrow.vo.BorrowDetailVo;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.invest.service.InvestRecordService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.statics.CGBusinessConstants;
import com.dxjr.portal.transfer.service.TransferService;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.UUIDGenerator;
import com.dxjr.wx.account.service.SafeCenterService;
import com.dxjr.wx.favorite.service.TenderService;

/**
 * 我的最爱-普通投标专区
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title TenderController.java
 * @package com.dxjr.wx.favorite.controller
 * @author huangpin
 * @version 0.1 2014年10月27日
 */
@Controller(value = "wxTenderController")
@RequestMapping(value = "/wx/tender")
public class TenderController extends BaseController {
	public Logger logger = Logger.getLogger(TenderController.class);

	@Autowired
	private InvestRecordService investRecordService;
	@Autowired
	private BorrowDetailService borrowDetailService;
	@Autowired
	private TendRecordService tenderRecordService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private BorrowService borrowService;
	@Autowired
	private TenderService tenderService;
	@Autowired
	private SafeCenterService safeCenterService;
	@Autowired
	private CurAccountService curAccountService;
	@Autowired
	private TransferService transferService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private CGUtilService cGUtilService;
	@Autowired
	private TendRecordService tendRecordService;
	/**
	 * 普通投标专区-列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年10月26日
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/bidList/{pageNum}")
	@ResponseBody
	public Map<String, Object> bidList(@PathVariable Integer pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<?> bidList = tenderService.bidList(new Page(pageNum, 10));
			if (bidList != null) {
				map.put("bidList", bidList);
				if (bidList.size() == BusinessConstants.DEFAULT_PAGE_SIZE)
					map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
			}
		} catch (Exception e) {
			logger.error("微信-普通投标专区-列表获取异常", e);
		}
		return map;
	}

	/**
	 * 普通投标专区-基本信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年10月27日
	 * @param borrowId
	 * @return Map<String,?>
	 */
	@RequestMapping(value = "/bid/{borrowId}")
	@ResponseBody
	public Map<String, Object> bid(@PathVariable Integer borrowId) {
		Map<String, Object> map = null;
		try {
			map = (Map<String, Object>) borrowDetailService.queryBorrowDetailInfo(borrowId);
			map.put("code","1");
			ShiroUser shiroUser = currentUser();
			if (shiroUser != null && map != null) {
				int userId = currentUser().getUserId();
				map.put("currentUser", currentUser());
//				map.put("useMoney", accountService.queryAccountByUserId(userId).getUseMoney());
//				BorrowDetailVo borrowDetailVo = (BorrowDetailVo)map.get("borrowDetailVo");
				// 新手标是否可投标
				if (tenderRecordService.getTenderPowderCountByUserId(userId) > 0 || transferService.querySubscribesCountByUserId(userId) > 0) {
					map.put("isNewUser", "false");
				} else {
					map.put("isNewUser", "true");
				}
				BorrowVo borrow = borrowService.selectByPrimaryKey(borrowId);
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
						map.put("useMoney", useMoney);
						//BigDecimal mostAccount = borrow.getMostAccount() == null ? new BigDecimal(0) : borrow.getMostAccount();
						// 获取投标有效金额
						//BigDecimal effectiveTenderMoney = tendRecordService.getMaxeffectiveMoney(borrow, shiroUser.getUserId(), mostAccount.compareTo(BigDecimal.ZERO) < 1 ? borrow.getAccount() : mostAccount,accountVo);
						//map.put("effectiveTenderMoney", effectiveTenderMoney.setScale(2, BigDecimal.ROUND_DOWN));
					}else
						//如果是存管标
						if(borrow.getIsCustody()==1){
							if(borrow.getStatus()==2){
								//如果是存管用户
								if(memberVo.getIsCustody()==1){
									String relateNo= UUIDGenerator.generate(CGBusinessConstants.RELATENO);
									String remark="进入投标详情页面，余额查询";
									//调用存管余额查询接口
									String rep= cGUtilService.saveCGAccount(shiroUser, relateNo,remark,borrow.getId());
									//解析报文
									Account account= cGUtilService.parseAQReqXml(rep, shiroUser, remark, relateNo,borrow.getId());
									if(account==null){
										map.put("code","0");
										logger.error("返回ERROR报文或验签失败");
										return map;
									}
									//平台与存管资金校验
									String scene="进入投标详情";//业务发生场景
									account.setUserId(shiroUser.getUserId());
									 cGUtilService.savecheckAccount(account, HttpTookit.getRealIpAddr(currentRequest()), shiroUser.getPlatform(), scene);
									//BaseEBankInfo eUserInfo= cGUtilService.eUserInfo(shiroUser.getUserId());
									map.put("useMoney", account.getEUseMoney());
									//map.put("mobile", eUserInfo.getMobileStr());
								}
							}

							//BigDecimal mostAccount = borrow.getMostAccount() == null ? new BigDecimal(0) : borrow.getMostAccount();
							// 获取投标有效金额
							//BigDecimal effectiveTenderMoney = tendRecordService.getCGMaxeffectiveMoney(borrow, shiroUser.getUserId(), mostAccount.compareTo(BigDecimal.ZERO) < 1 ? borrow.getAccount() : mostAccount,accountVo);
						//	map.put("effectiveTenderMoney", effectiveTenderMoney.setScale(2, BigDecimal.ROUND_DOWN));

						}

					//新增
					// 获取借款标信息
					//BorrowVo borrow = borrowService.selectByPrimaryKey(id);
			/*	BigDecimal mostAccount = borrow.getMostAccount() == null ? new BigDecimal(0) : borrow.getMostAccount();
				// 获取投标有效金额
				BigDecimal effectiveTenderMoney = tendRecordService.getMaxeffectiveMoney(borrow, shiroUser.getUserId(), mostAccount.compareTo(BigDecimal.ZERO) < 1 ? borrow.getAccount() : mostAccount,accountVo);*/
					map.put("isCustody", memberVo.getIsCustody());
					map.put("borrow",borrow);
					map.put("account", accountVo);
					//map.put("maxCurMoney", curAccountService.getMaxUseMoneyByNow(shiroUser.getUserId()));
					//map.put("isExistCurAccount", curAccountService.selectByUserId(shiroUser.getUserId()) == null ? false : true);
					//mv.addObject("effectiveTenderMoney", effectiveTenderMoney.setScale(2, BigDecimal.ROUND_DOWN));
					//map.put("remainMoney", borrow.getAccount().subtract(borrow.getAccountYes()).setScale(2, BigDecimal.ROUND_HALF_UP));
					//map.put("alsoNeed", borrow.getAccount().subtract(borrow.getAccountYes()).setScale(2, BigDecimal.ROUND_DOWN));
					// 新手标 传来一个标志
//					String xs_tag = (String) request.getParameter("xs_tag");
//					map.put("xs_tag", xs_tag);
				}
			}

		} catch (Exception e) {
			map.put("code","0");
			logger.error("微信-普通投标专区-基本信息异常", e);
		}
		return map;
	}

	/**
	 * 普通投标专区-投标列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年10月27日
	 * @param borrowId
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/bid/tenderList/{borrowId}/{pageNum}")
	@ResponseBody
	public Map<String, Object> bidTenderList(@PathVariable Integer borrowId, @PathVariable Integer pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<?> tenderList = tenderRecordService.queryTenderRecordByBorrowId(borrowId, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE)).getResult();
			if (tenderList != null) {
				map.put("tenderList", tenderList);
				if (tenderList.size() == BusinessConstants.DEFAULT_PAGE_SIZE)
					map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
			}
			map.put("currentUser", currentUser());
		} catch (Exception e) {
			logger.error("微信-普通投标专区-投标列表获取异常", e);
		}
		return map;
	}

	/**
	 * 普通投标专区-填写投标信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年10月28日
	 * @param borrowId
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/toTender/{borrowId}")
	@ResponseBody
	public Map<String, Object> toTender(@PathVariable Integer borrowId) {
		try {
			Map<String, Object> map = safeCenterService.certificationCheck(currentUser(), "mobile", "bank");
			if (map != null)
				return map;
			map = new HashMap<String, Object>();
			map.put("code","1");
			Integer userId = currentUser().getUserId();
//			AccountVo accountVo = accountService.queryAccountByUserId(userId);
//			map.put("useMoney", accountVo.getUseMoney());

			BorrowVo borrow = borrowService.selectByPrimaryKey(borrowId);
			map.put("borrow", borrow);
			ShiroUser shiroUser= currentUser();

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
					map.put("useMoney", useMoney);
					BigDecimal mostAccount = borrow.getMostAccount() == null ? new BigDecimal(0) : borrow.getMostAccount();
					// 获取投标有效金额
					BigDecimal effectiveTenderMoney = tendRecordService.getMaxeffectiveMoney(borrow, shiroUser.getUserId(), mostAccount.compareTo(BigDecimal.ZERO) < 1 ? borrow.getAccount() : mostAccount,accountVo);
					map.put("effectiveTenderMoney", effectiveTenderMoney.setScale(2, BigDecimal.ROUND_DOWN));
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
									map.put("code","0");
								}
								//平台与存管资金校验
								String scene="进入投标详情";//业务发生场景
								account.setUserId(shiroUser.getUserId());
								cGUtilService.savecheckAccount(account, HttpTookit.getRealIpAddr(currentRequest()), shiroUser.getPlatform(), scene);
								BaseEBankInfo eUserInfo= cGUtilService.eUserInfo(shiroUser.getUserId());
								map.put("useMoney", account.getEUseMoney());
								map.put("mobile", eUserInfo.getMobileStr());
							}
						}

						BigDecimal mostAccount = borrow.getMostAccount() == null ? new BigDecimal(0) : borrow.getMostAccount();
						// 获取投标有效金额
						BigDecimal effectiveTenderMoney = tendRecordService.getCGMaxeffectiveMoney(borrow, shiroUser.getUserId(), mostAccount.compareTo(BigDecimal.ZERO) < 1 ? borrow.getAccount() : mostAccount,accountVo);
						map.put("effectiveTenderMoney", effectiveTenderMoney.setScale(2, BigDecimal.ROUND_DOWN));

					}

				//新增
				// 获取借款标信息
				//BorrowVo borrow = borrowService.selectByPrimaryKey(id);
//				BigDecimal mostAccount = borrow.getMostAccount() == null ? new BigDecimal(0) : borrow.getMostAccount();
				// 获取投标有效金额
//				BigDecimal effectiveTenderMoney = tendRecordService.getMaxeffectiveMoney(borrow, shiroUser.getUserId(), mostAccount.compareTo(BigDecimal.ZERO) < 1 ? borrow.getAccount() : mostAccount,accountVo);
				map.put("isCustody", memberVo.getIsCustody());
				map.put("account", accountVo);
				map.put("maxCurMoney", curAccountService.getMaxUseMoneyByNow(shiroUser.getUserId()));
				map.put("isExistCurAccount", curAccountService.selectByUserId(shiroUser.getUserId()) == null ? false : true);
//				map.put("effectiveTenderMoney", effectiveTenderMoney.setScale(2, BigDecimal.ROUND_DOWN));
				map.put("remainMoney", borrow.getAccount().subtract(borrow.getAccountYes()).setScale(2, BigDecimal.ROUND_HALF_UP));
				map.put("alsoNeed", borrow.getAccount().subtract(borrow.getAccountYes()).setScale(2, BigDecimal.ROUND_DOWN));
				// 新手标 传来一个标志
//				String xs_tag = (String) request.getParameter("xs_tag");
//				map.put("xs_tag", xs_tag);
			}
//			BigDecimal mostAccount = borrow.getMostAccount() == null ? new BigDecimal(0) : borrow.getMostAccount();

			// 获取投标有效金额
//			BigDecimal effectiveTenderMoney = tenderRecordService.getMaxeffectiveMoney(borrow, userId, mostAccount.compareTo(BigDecimal.ZERO) < 1 ? borrow.getAccount() : mostAccount, accountVo);
//			map.put("effectiveTenderMoney", effectiveTenderMoney);

//			map.put("alsoNeed", borrow.getAccount().subtract(borrow.getAccountYes()).setScale(2, BigDecimal.ROUND_DOWN));

			// 活期宝相关
//			map.put("maxCurMoney", curAccountService.getMaxUseMoneyByNow(userId));
//			map.put("isExistCurAccount", curAccountService.selectByUserId(userId) == null ? false : true);

			return map;
		} catch (Exception e) {
			logger.error("微信-普通投标专区-填写投标信息异常", e);
		}
		return new HashMap<String, Object>();
	}
}
