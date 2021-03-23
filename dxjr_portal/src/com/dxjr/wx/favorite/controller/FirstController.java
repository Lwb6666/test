package com.dxjr.wx.favorite.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.first.service.FirstBorrowService;
import com.dxjr.portal.first.service.FirstTenderDetailService;
import com.dxjr.portal.first.vo.FirstBorrowCnd;
import com.dxjr.portal.first.vo.FirstBorrowVo;
import com.dxjr.portal.first.vo.FirstTenderDetailCnd;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.DateUtils;
import com.dxjr.wx.account.service.SafeCenterService;

/**
 * 投标直通车专区
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title FirstController.java
 * @package com.dxjr.wx.favorite.controller
 * @author huangpin
 * @version 0.1 2014年10月28日
 */
@Controller
@RequestMapping(value = "/wx/first")
public class FirstController extends BaseController {

	public Logger logger = Logger.getLogger(FirstController.class);

	@Autowired
	private FirstBorrowService firstBorrowService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private FirstTenderDetailService firstTenderDetailService;
	@Autowired
	private SafeCenterService safeCenterService;
	@Autowired
	private CurAccountService curAccountService;

	/**
	 * 投标直通车专区-基本信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年10月28日
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/info/{firstId}")
	@ResponseBody
	public Map<String, Object> info(@PathVariable Integer firstId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// FirstBorrowVo f = firstBorrowService.getLatest();
			FirstBorrowVo f = firstBorrowService.getFirstBorrowForIndex(new Date());

			// FirstBorrowVo f = null;
			// if (firstId == 0) {
			// f = firstBorrowService.queryFirstBorrowForIndex();// 首页显示的直通车
			// } else {
			// f = firstBorrowService.queryAvailableFirstBorrowById(firstId);
			// }

			map.put("first", f);
			if (currentUser() != null) {
				map.put("currentUser", currentUser());
				map.put("useMoney", accountService.queryAccountByUserId(currentUser().getUserId()).getUseMoney());
			}
			if (f.getPublishTime().getTime() > new Date().getTime()) {
				map.put("beginFlag", false);
			}
			if (f.getEndTime().getTime() < new Date().getTime()) {
				map.put("endFlag", true);
			}
			// 是否显示直通车奖励
			if (DateUtils.parse("2015-09-01", "yyyy-MM-dd").getTime() - new Date().getTime() > 0) {
				map.put("showJiang", true);
			} else {
				map.put("showJiang", false);
			}
		} catch (Exception e) {
			logger.error("微信-投标直通车专区-基本信息获取异常", e);
		}
		return map;
	}

	/**
	 * 投标直通车专区-开通列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年10月28日
	 * @param firstId
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/joinList/{firstId}/{pageNum}")
	@ResponseBody
	public Map<String, Object> joinList(@PathVariable Integer firstId, @PathVariable Integer pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			FirstTenderDetailCnd cnd = new FirstTenderDetailCnd();
			cnd.setFirstBorrowId(firstId);
			cnd.setFirstTenderRealStatus(Constants.TENDER_REAL_STATUS_AVAILABLE);
			List<?> joinList = firstTenderDetailService.queryPageListByCnd(cnd, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE)).getResult();

			if (joinList != null) {
				map.put("joinList", joinList);
				if (joinList.size() == BusinessConstants.DEFAULT_PAGE_SIZE)
					map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
			}

			if (currentUser() != null) {
				map.put("loginUserName", currentUser().getUserName());
			}
		} catch (Exception e) {
			logger.error("微信-投标直通车专区-开通列表获取异常", e);
		}
		return map;
	}

	/**
	 * 投标直通车专区-填写开通信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年10月29日
	 * @param firstId
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/toJoin/{firstId}")
	@ResponseBody
	public Map<String, Object> toJoin(@PathVariable Integer firstId) {
		try {
			Map<String, Object> map = safeCenterService.certificationCheck(currentUser(), "mobile", "bank");
			if (map != null)
				return map;
			else {
				map = new HashMap<String, Object>();
				Integer userId = currentUser().getUserId();
				FirstBorrowVo f = firstBorrowService.queryAvailableFirstBorrowById(firstId);
				map.put("first", f);

				AccountVo accountVo = accountService.queryAccountByUserId(userId);
				map.put("useMoney", accountVo.getUseMoney());

				BigDecimal money = new BigDecimal(f.getMostAccount());
				BigDecimal effectiveTenderMoney = firstTenderDetailService.getMaxeffectiveMoney(f, userId, money, accountVo);
				map.put("effectiveTenderMoney", effectiveTenderMoney.setScale(2, BigDecimal.ROUND_DOWN));

				map.put("alsoNeed", f.getPlanAccount() - f.getAccountYes());

				// 活期宝相关
				map.put("maxCurMoney", curAccountService.getMaxUseMoneyByNow(userId));
				map.put("isExistCurAccount", curAccountService.selectByUserId(userId) == null ? false : true);
			}
			return map;
		} catch (Exception e) {
			logger.error("微信-投标直通车专区-填写开通信息异常", e);
		}
		return new HashMap<String, Object>();
	}

	/**
	 * 微信-投标直通车专区-直通车列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年1月5日
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/firstList/{pageNum}")
	@ResponseBody
	public Map<String, Object> firstList(@PathVariable Integer pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			FirstBorrowCnd firstBorrowCnd = new FirstBorrowCnd();
			List<?> firstList = firstBorrowService.queryFirstListByCnd(firstBorrowCnd, pageNum, BusinessConstants.DEFAULT_PAGE_SIZE).getResult();

			if (firstList != null) {
				map.put("firstList", firstList);
				if (firstList.size() == BusinessConstants.DEFAULT_PAGE_SIZE)
					map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
			}

		} catch (Exception e) {
			logger.error("微信-投标直通车专区-直通车列表获取异常", e);
		}
		return map;
	}

}
