package com.dxjr.wx.favorite.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.first.service.FirstTransferBorrowService;
import com.dxjr.portal.first.service.FirstTransferService;
import com.dxjr.portal.first.vo.FirstTransferCnd;
import com.dxjr.portal.first.vo.FirstTransferVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.transfer.entity.BTransfer;
import com.dxjr.portal.transfer.service.TransferService;
import com.dxjr.portal.transfer.vo.BTransferVo;
import com.dxjr.portal.transfer.vo.SearchTransferVo;
import com.dxjr.portal.transfer.vo.TransferCnd;
import com.dxjr.security.ShiroUser;
import com.dxjr.wx.account.service.SafeCenterService;

/**
 * 微信-债转专区
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title TransferController.java
 * @package com.dxjr.wx.favorite.controller
 * @author huangpin
 * @version 0.1 2015年2月10日
 */
@Controller(value = "wxTransferController")
@RequestMapping(value = "/wx/transfer")
public class TransferController extends BaseController {
	public Logger logger = Logger.getLogger(TransferController.class);

	@Autowired
	private TransferService transferService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private SafeCenterService safeCenterService;
	@Autowired
	private FirstTransferService firstTransferService;
	@Autowired
	private FirstTransferBorrowService firstTransferBorrowService;
	@Autowired
	private CurAccountService curAccountService;

	/**
	 * 微信-债转专区-列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年2月10日
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/list/{pageNum}")
	@ResponseBody
	public Map<String, Object> bidList(@PathVariable Integer pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SearchTransferVo seach = new SearchTransferVo();
			seach.setTransferStatus("2");
			List<?> bidList = transferService.findtransferList(seach, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE)).getResult();
			if (bidList != null) {
				map.put("bidList", bidList);
				if (bidList.size() == BusinessConstants.DEFAULT_PAGE_SIZE)
					map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
			}
		} catch (Exception e) {
			logger.error("微信-债转专区-列表获取异常", e);
		}
		return map;
	}

	/**
	 * 微信-债转专区-直通车-列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年5月11日
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/first/list/{pageNum}")
	@ResponseBody
	public Map<String, Object> firstList(@PathVariable Integer pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			FirstTransferCnd cnd = new FirstTransferCnd();
			cnd.setTransferStatus(2);// 转让中
			List<?> list = firstTransferService.queryFirstTransferByCnd(cnd, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE)).getResult();
			if (list != null) {
				map.put("firstList", list);
				if (list.size() == BusinessConstants.DEFAULT_PAGE_SIZE)
					map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
			}
		} catch (Exception e) {
			logger.error("微信-债转专区-直通车-列表获取异常", e);
		}
		return map;
	}

	/**
	 * 微信-债转专区-基本信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年2月10日
	 * @param transferId
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/{transferId}")
	@ResponseBody
	public Map<String, Object> bid(@PathVariable Integer transferId) {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			BTransfer bTransfer = transferService.getTransferDetailById(transferId);
			map.put("t", bTransfer);

			if (currentUser() != null && map != null) {
				map.put("currentUser", currentUser());
				map.put("useMoney", accountService.queryAccountByUserId(currentUser().getUserId()).getUseMoney());
			}
			return map;
		} catch (Exception e) {
			logger.error("微信-债转专区-基本信息异常", e);
		}
		return new HashMap<String, Object>();
	}

	/**
	 * 微信-债转专区-直通车-基本信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年5月12日
	 * @param transferId
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/first/{transferId}")
	@ResponseBody
	public Map<String, Object> firstBid(@PathVariable Integer transferId) {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			FirstTransferVo f = firstTransferService.queryTransferById(transferId);
			map.put("f", f);

			if (currentUser() != null && map != null) {
				map.put("currentUser", currentUser());
				map.put("useMoney", accountService.queryAccountByUserId(currentUser().getUserId()).getUseMoney());
			}
			return map;
		} catch (Exception e) {
			logger.error("微信-债转专区-直通车-基本信息异常", e);
		}
		return new HashMap<String, Object>();
	}

	/**
	 * 微信-债转专区-直通车-相关标的债权信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年5月12日
	 * @param transferId
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/first/bidList/{transferId}/{pageNum}")
	@ResponseBody
	public Map<String, Object> firstBidList(@PathVariable("transferId") Integer transferId, @PathVariable Integer pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<?> list = firstTransferBorrowService.queryTransferBorrowListByTransferId(transferId, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE)).getResult();
			if (list != null) {
				map.put("bidList", list);
				map.put("transferTotal", firstTransferBorrowService.queryBorrowCollectionAccountSum(transferId));// 债权剩余价值合计
				map.put("transferUse", firstTransferService.queryTransferById(transferId).getUseMoney());// 获取直通车转让信息
				if (list.size() == BusinessConstants.DEFAULT_PAGE_SIZE)
					map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
			}
		} catch (Exception e) {
			logger.error("微信-债转专区-直通车-相关标的债权信息获取异常", e);
		}
		return map;
	}

	/**
	 * 微信-债转专区-购买列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年2月10日
	 * @param transferId
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/buyList/{transferId}/{pageNum}")
	@ResponseBody
	public Map<String, Object> buyList(@PathVariable Integer transferId, @PathVariable Integer pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Page page = new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE);
			transferService.querySubscribesByTransferId(transferId, page);
			List<?> buyList = page.getResult();
			if (buyList != null) {
				map.put("buyList", buyList);
				if (buyList.size() == BusinessConstants.DEFAULT_PAGE_SIZE)
					map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
			}
			if (currentUser() != null)
				map.put("currentUserNameEncrypt", currentUser().getUserNameEncrypt());
		} catch (Exception e) {
			logger.error("微信-债转专区-购买列表获取异常", e);
		}
		return map;
	}

	/**
	 * 微信-债转专区-进入认购页面
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author zhuchen
	 * @version 0.1 2015年2月11日
	 * @param request
	 * @param response
	 * @param id
	 * @return Map<String,Object>
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/toTransferSubscribe/{id}")
	@ResponseBody
	public Map<String, Object> toTransferSubscribe(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
		Map<String, Object> map = null;
		ShiroUser shiroUser = currentUser();
		try {
			map = safeCenterService.certificationCheck(shiroUser, "mobile", "bank");
			if (map != null) {
				return map;
			}
			map = new HashMap<String, Object>();
			AccountVo accountVo = new AccountVo();
			BTransferVo bTransferVo = new BTransferVo();

			// 自动购买金额
			BigDecimal effectiveTenderMoney = new BigDecimal(0);
			BigDecimal alsoNeedMoney = new BigDecimal(0);

			// 获取个人账户余额
			accountVo = accountService.queryAccountByUserId(shiroUser.getUserId());
			// 债权转让信息
			bTransferVo = transferService.selectByPrimaryKey(id);
			// 剩余可认购金额
			alsoNeedMoney = bTransferVo.getAccountReal().subtract(bTransferVo.getAccountYes()).setScale(2, BigDecimal.ROUND_DOWN);
			map.put("alsoNeed", alsoNeedMoney);

			// 个人已认购金额
			TransferCnd transferCnd = new TransferCnd();
			transferCnd.setTransferId(id);
			transferCnd.setUserId(shiroUser.getUserId());
			String sumAccount = transferService.querySumAccountByUserId(transferCnd);
			map.put("sumAccount", sumAccount);

			/*
			 * // 自动购买金额 = 用户余额 | 当：用户余额大于最小认购额度，并小于债权余额时； if (accountVo.getUseMoney().compareTo(bTransferVo.getLowestAccount()) == 1 && accountVo.getUseMoney().compareTo(alsoNeedMoney) == -1) {
			 * effectiveTenderMoney = accountVo.getUseMoney(); effectiveTenderMoney = effectiveTenderMoney.subtract(new BigDecimal(sumAccount)); } // 自动购买金额 = 剩余可认购金额 | 当：用户金额大于债转余额，或等于债转余额时； if
			 * (accountVo.getUseMoney().compareTo(alsoNeedMoney) == 1 || accountVo.getUseMoney().compareTo(alsoNeedMoney) == 0) { effectiveTenderMoney = alsoNeedMoney; effectiveTenderMoney =
			 * effectiveTenderMoney.subtract(new BigDecimal(sumAccount)); }
			 */

			// 自动购买金额赋值：
			// 默认赋值=用户金额
			effectiveTenderMoney = accountVo.getUseMoney();
			// 如果自动购买金额大于最大认购额度；=最大认购额度
			if (effectiveTenderMoney.compareTo(bTransferVo.getMostAccount()) == 1) {
				effectiveTenderMoney = bTransferVo.getMostAccount();
			}
			// 如果自动购买金额大于债权余额；=债权余额
			if (effectiveTenderMoney.compareTo(alsoNeedMoney) == 1) {
				effectiveTenderMoney = alsoNeedMoney;
			}
			// 如果用户余额<最小额度&&债权余额>最小额度；=债权余额
			if (accountVo.getUseMoney().compareTo(bTransferVo.getLowestAccount()) == -1 && alsoNeedMoney.compareTo(bTransferVo.getLowestAccount()) == 1) {
				effectiveTenderMoney = new BigDecimal(0);
			}

			// 活期宝相关
			map.put("maxCurMoney", curAccountService.getMaxUseMoneyByNow(shiroUser.getUserId()));
			map.put("isExistCurAccount", curAccountService.selectByUserId(shiroUser.getUserId()) == null ? false : true);

			map.put("account", accountVo);
			map.put("transfer", bTransferVo);
			map.put("effectiveTenderMoney", effectiveTenderMoney);
		} catch (Exception e) {
			logger.error("进入认购页面出错", e);
		}
		return map;
	}

	/**
	 * 微信-债转专区-直通车-购买初始化
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年5月13日
	 * @param transferId
	 * @return Map<String,Object>
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/first/tobuy/{transferId}")
	@ResponseBody
	public Map<String, Object> initFirstBuy(@PathVariable Integer transferId) {
		Map<String, Object> map = null;
		ShiroUser shiroUser = currentUser();
		try {
			map = safeCenterService.certificationCheck(shiroUser, "mobile", "bank");
			if (map != null) {
				return map;
			}
			map = new HashMap<String, Object>();

			FirstTransferVo f = firstTransferService.queryTransferById(transferId);
			map.put("firstMoney", f.getAccountReal());// 转让价格
			map.put("firstPwd", f.getBidPassword());
			map.put("firstId", f.getId());// 转让车ID

			AccountVo accountVo = accountService.queryAccountByUserId(currentUser().getUserId());
			map.put("useMoney", accountVo.getUseMoney());// 账户余额

			// 活期宝相关
			map.put("maxCurMoney", curAccountService.getMaxUseMoneyByNow(shiroUser.getUserId()));
			map.put("isExistCurAccount", curAccountService.selectByUserId(shiroUser.getUserId()) == null ? false : true);
		} catch (Exception e) {
			logger.error("微信-债转专区-直通车-购买初始化异常", e);
		}
		return map;
	}
}
