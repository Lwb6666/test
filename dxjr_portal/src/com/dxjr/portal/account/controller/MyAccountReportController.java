package com.dxjr.portal.account.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.account.service.AccountDayLogService;
import com.dxjr.portal.account.service.MyAccountReportService;
import com.dxjr.portal.account.service.ShareholderRankService;
import com.dxjr.portal.account.vo.AccountDayLogVo;
import com.dxjr.portal.account.vo.ShareholderRankVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;

/**
 * <p>
 * Description:我的账户统计信息<br />
 * </p>
 * 
 * @title MyAccountReportController.java
 * @package com.dxjr.portal.account.controller
 * @author justin.xu
 * @version 0.1 2014年5月14日
 */
@Controller
@RequestMapping(value = "/myaccount/report")
public class MyAccountReportController extends BaseController {

	@Autowired
	private MyAccountReportService myAccountReportService;
	@Autowired
	private ShareholderRankService shareholderRankService;
	@Autowired
	private AccountDayLogService accountDayLogService;

	/**
	 * <p>
	 * Description:统计账户详情<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月4日
	 * @param request
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/accountBase")
	public ModelAndView accountBase(HttpServletRequest request, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView("account/report/accountBase");
		ShiroUser shiroUser = currentUser();
		// 账户详情各种金额的统计
		Map<String, BigDecimal> userDetailMap = myAccountReportService.queryUserAccountDetail(shiroUser.getUserId());
		mv.addObject("userDetailMap", userDetailMap);
		return mv;
	}

	/**
	 * <p>
	 * Description:统计投资详情<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月4日
	 * @param request
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/investInfo")
	public ModelAndView investInfo(HttpServletRequest request, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView("account/report/investInfo");
		ShiroUser shiroUser = currentUser();
		// 投资详情各种金额的统计
		Map<String, Object> userInvestDetailMap = myAccountReportService.queryUserInvestDetail(shiroUser.getUserId());
		mv.addObject("userInvestDetailMap", userInvestDetailMap);
		return mv;
	}

	/**
	 * <p>
	 * Description:统计借款详情<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2013年12月4日
	 * @param request
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/borrowInfo")
	public ModelAndView borrowInfo(HttpServletRequest request, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView("account/report/borrowInfo");
		ShiroUser shiroUser = currentUser();
		// 账户详情各种金额的统计
		Map<String, Object> userBorrowDetail = myAccountReportService.queryUserBorrowDetail(shiroUser.getUserId());
		mv.addObject("userBorrowDetail", userBorrowDetail);
		return mv;
	}

	/**
	 * <p>
	 * Description:股东加权统计<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月20日
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequestMapping(value = "/shareholderRankInfo")
	public ModelAndView shareholderRankInfo(HttpServletRequest request, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView("account/report/shareholderRankInfo");
		ShiroUser shiroUser = currentUser();
		ShareholderRankVo shareholderRank = shareholderRankService.queryShareholderRankByUserId(shiroUser.getUserId());
		if (shareholderRank == null) {

			Map<String, String> map = (Map<String, String>) shareholderRankService.queryNoRanksByUserId(shiroUser.getUserId());
			if (map.get("day_total") != null && map.get("max_total") != null) {
				BigDecimal day_total = new BigDecimal(map.get("day_total").toString());
				BigDecimal max_total = new BigDecimal(map.get("max_total").toString());
				if (max_total.compareTo(BigDecimal.ZERO) == 0) {
					map.put("apr", "100");
				} else if (day_total.compareTo(BigDecimal.ZERO) == -1) {
					map.put("apr", "100");
				} else {
					BigDecimal s = day_total.divide(max_total, 4, BigDecimal.ROUND_DOWN);
					map.put("apr", String.valueOf(new BigDecimal(1).subtract(s).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN)));
				}
			}
			mv.addObject("map", map);
		}
		mv.addObject("shareholderRank", shareholderRank);

		return mv;
	}

	/**
	 * <p>
	 * Description:股东加权统计，查询某类型排名200名的记录。<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月21日
	 * @param request
	 * @param session
	 * @param type
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequestMapping(value = "/shareholderRank/{type}")
	public ModelAndView shareholderRank(HttpServletRequest request, HttpSession session, @PathVariable String type) throws Exception {
		ModelAndView mv = new ModelAndView("account/report/shareholderRankInfoRank");
		List<ShareholderRankVo> shareholderRankList = shareholderRankService.queryShareholderRankByType(type);
		mv.addObject("shareholderRankList", shareholderRankList);
		mv.addObject("type", type);
		return mv;
	}
	
	
	@RequestMapping(value = "/showCollectionDetail")
	public ModelAndView showCollectionDetail(HttpServletRequest request, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView("account/report/accountDayLogDetail");
		ShiroUser currentUser = currentUser();
		if(currentUser == null){
			return forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		List<AccountDayLogVo> accountDayLogList =accountDayLogService.queryAccountDayLogCollection(currentUser.getUserId());
		if(null == accountDayLogList || accountDayLogList.size() == 0){
			accountDayLogList =null;
		}
		return mv.addObject("accountDayLogList", accountDayLogList);
	}
}
