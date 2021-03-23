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
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.service.MyAccountReportService;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.curAccount.entity.CurAccount;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.curAccount.service.CurInService;
import com.dxjr.portal.curAccount.service.CurInterestDetailService;
import com.dxjr.portal.curAccount.vo.CurAccountCnd;
import com.dxjr.portal.statics.BusinessConstants;

@Controller
@RequestMapping(value = "/wx/cur")
public class CurController extends BaseController {
	public Logger logger = Logger.getLogger(CurController.class);

	@Autowired
	private AccountService accountService;
	@Autowired
	private MyAccountReportService myAccountReportService;
	@Autowired
	private CurInterestDetailService curInterestDetailService;
	@Autowired
	private CurAccountService curAccountService;
	@Autowired
	private CurInService curInService;

	/**
	 * 微信-活期宝初始化
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年6月3日
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "")
	@ResponseBody
	public Map<String, Object> index() {
		try {
			if (currentUser() != null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("currentUser", currentUser());
				map.put("useMoney", accountService.queryAccountByUserId(currentUser().getUserId()).getUseMoney());
				return map;
			}
		} catch (Exception e) {
			logger.error("微信-活期宝初始化-异常", e);
		}
		return null;
	}

	/**
	 * 微信-我的账户-活期生息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年6月3日
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/accountIndex")
	@ResponseBody
	public Map<String, Object> accountIndex(String type) {
		try {
			if (currentUser() != null) {
				int userId = currentUser().getUserId();
				Map<String, BigDecimal> userDetailMap = myAccountReportService.queryUserAccountDetail(userId);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("interestTol", userDetailMap.get("cur_interest_total"));
				map.put("interestYes", userDetailMap.get("cur_interest_yesterday"));

				if ("initOut".equals(type)) {
					// 最大可转出金额
					BigDecimal noDrawMoneyTotal = curInService.queryNoDrawMoneyTotalByUserIdAndDate(userId, new Date());// 查询当天转入受限总额
					BigDecimal maxAccount = BigDecimal.ZERO;
					CurAccount curAccount = curAccountService.selectByUserId(userId);
					if (curAccount != null) {
						maxAccount = curAccount.getTotal().subtract(noDrawMoneyTotal).setScale(2, BigDecimal.ROUND_DOWN);
						if (maxAccount.compareTo(BigDecimal.ZERO) == -1) {
							maxAccount = BigDecimal.ZERO;
						}
					}
					map.put("curTotal", maxAccount);
				} else {
					map.put("curTotal", userDetailMap.get("cur_total"));
				}

				return map;
			}
		} catch (Exception e) {
			logger.error("微信-我的账户-活期生息初始化-异常", e);
		}
		return null;
	}

	/**
	 * 微信-我的账户-活期生息-收益明细
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年6月3日
	 * @param type
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/interestList/{pageNum}")
	@ResponseBody
	public Map<String, Object> interestList(@PathVariable int pageNum) {
		try {
			if (currentUser() != null) {
				CurAccountCnd cnd = new CurAccountCnd();
				cnd.setUserId(currentUser().getUserId());
				List<?> list = curInterestDetailService.queryCurInterestDetailByPage(cnd, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE)).getResult();
				if (list != null && list.size() > 0) {
					Map<String, Object> map = new HashMap<String, Object>();
					if (list.size() == 10) {
						map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
					}
					map.put("interestList", list);
					return map;
				}
			}
		} catch (Exception e) {
			logger.error("微信-我的账户-活期生息-收益明细-异常", e);
		}
		return null;
	}
}
