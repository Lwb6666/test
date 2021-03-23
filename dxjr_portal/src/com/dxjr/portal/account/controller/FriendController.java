package com.dxjr.portal.account.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.service.FriendService;
import com.dxjr.portal.account.vo.InviteRankVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.security.ShiroUser;

/**
 * 我要推广Controller
 * <p>
 * Description:<br />
 * </p>
 * 
 * @title FriendController.java
 * @package com.dxjr.portal.account.controller
 * @author huangpin
 * @version 0.1 2014年8月26日
 */
@Controller
@RequestMapping(value = "/myaccount/friend")
public class FriendController extends BaseController {

	public Logger logger = Logger.getLogger(FriendController.class);

	@Autowired
	private FriendService friendService;

	/**
	 * 我要推广
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年9月18日
	 * @param request
	 * @param pageSize
	 * @return ModelAndView
	 */
	@RequestMapping(value = "friendList/{pageNo}")
	@RequiresAuthentication
	public ModelAndView firendList(@PathVariable(value = "pageNo") int pageNo) {
		ModelAndView mv = new ModelAndView("account/friend/friendList");
		ShiroUser shiroUser = currentUser();
        String red=currentRequest().getParameter("redId");
		try {
			if(red==null){
				String recommendPath = "/member/toRegisterPage.html?code=" + shiroUser.getUserIdMD5();
				mv.addObject("recommendPath", recommendPath);
			}else{
				String recommendPath = "/member/toRegisterPage.html?code=" + shiroUser.getUserIdMD5()+"&redId="+red;
				mv.addObject("recommendPath", recommendPath);
			}

			Page page = friendService.queryInviteDetailsByUserId(shiroUser.getUserId(), pageNo, 10);
			mv.addObject("page", page);

			mv.addObject("sumMoney", friendService.queryAllFriendSumMoneyByUserId(shiroUser.getUserId()));
		} catch (Exception e) {
			logger.error("我要推广异常", e);
		}
		return mv;
	}

	/**
	 * 推荐人列表
	 * <p>
	 * Description:<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月5日
	 * @param request
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "toFriendGeneralize/{pageNo}")
	@RequiresAuthentication
	public ModelAndView toFriendGeneralize(@PathVariable(value = "pageNo") int pageNo) {
		ModelAndView mv = new ModelAndView("account/friend/listDiv");
		try {
			Page page = friendService.queryInviteDetailsByUserId(currentUser().getUserId(), pageNo, 10);
			mv.addObject("page", page);
		} catch (Exception e) {
			logger.error("我要推广列表异常", e);
		}
		return mv;
	}

	@RequestMapping(value = "inviteRank")
	@RequiresAuthentication
	public ModelAndView inviteRank() {
		Integer userId = currentUser().getUserId();

		Calendar cal = Calendar.getInstance();
		int curMonth = cal.get(Calendar.MONTH) + 1;
		int curQuarter = (curMonth == 12 ? 0 : curMonth) / 3 + 1;

		Date startDate = DateUtils.truncate(cal.getTime(), Calendar.MONTH);
		Date endDate = DateUtils.addMonths(startDate, 1);
		InviteRankVo inviteNumRankCurMonth = friendService.queryInviteNumRank(userId, startDate, endDate); // 当月推荐成功人数,排名

		int i = curMonth % 3;
		startDate = DateUtils.truncate(DateUtils.addMonths(cal.getTime(), -i), Calendar.MONTH);
		endDate = DateUtils.addMonths(startDate, i + 1);
		InviteRankVo inviteNumRankCurQuarter = friendService.queryInviteNumRank(userId, startDate, endDate); // 当季推荐成功人数,排名

		InviteRankVo inviteNumRankYear = friendService.queryInviteNumRank(userId, null, null); // 年度推荐成功人数,排名

		InviteRankVo inviteInterestRankLastMonth = friendService.queryInviteInterestRank(userId, (curMonth - 1 == 0) ? 12 : curMonth - 1, "MONTH_INTEREST"); // 上月推荐共享奖,排名

		InviteRankVo inviteInterestRankYear = friendService.queryInviteInterestRank(userId, null, "TOTAL_INTEREST"); // 累积推荐共享奖,排名

		BigDecimal issuedRewardLastMonth = friendService.queryInviteIssuedReward(userId, 1, (curMonth - 1 == 0) ? 12 : curMonth - 1); // 上月推荐成功人数竞技奖(已发)

		BigDecimal issuedRewardLastQuarter = friendService.queryInviteIssuedReward(userId, 2, curQuarter - 1); // 上季推荐成功人数竞技奖(已发)

		BigDecimal issuedRewardYear = friendService.queryInviteIssuedReward(userId, 3, null); // 年度推荐成功人数竞技奖(已发)

		return forword("account/friend/inviteRank").addObject("inviteNumRankCurMonth", inviteNumRankCurMonth).addObject("inviteNumRankCurQuarter", inviteNumRankCurQuarter)
				.addObject("inviteNumRankYear", inviteNumRankYear).addObject("inviteInterestRankLastMonth", inviteInterestRankLastMonth).addObject("inviteInterestRankYear", inviteInterestRankYear)
				.addObject("issuedRewardLastMonth", issuedRewardLastMonth).addObject("issuedRewardLastQuarter", issuedRewardLastQuarter).addObject("issuedRewardYear", issuedRewardYear);
	}

	@RequestMapping(value = "inviteNumRankList/{type}/{pageNo}")
	public ModelAndView inviteNumRankList(@PathVariable(value = "type") String type, @PathVariable(value = "pageNo") int pageNo) {
		Date startDate = null, endDate = null;

		// 当月推荐成功人数排名
		if ("m".equalsIgnoreCase(type)) {
			startDate = DateUtils.truncate(new Date(), Calendar.MONTH);
			endDate = DateUtils.addMonths(startDate, 1);

			// 当季推荐成功人数排名
		} else if ("q".equalsIgnoreCase(type)) {
			Calendar cal = Calendar.getInstance();
			int i = (cal.get(Calendar.MONTH) + 1) % 3;
			startDate = DateUtils.truncate(DateUtils.addMonths(cal.getTime(), -i), Calendar.MONTH);
			endDate = DateUtils.addMonths(startDate, i + 1);

			// 年度推荐成功人数排名
		} else if ("y".equalsIgnoreCase(type)) {
			startDate = null;
			endDate = null;
		}
		Page page = friendService.queryInviteNumRank(startDate, endDate, new Page(pageNo, 5));

		return forword("account/friend/inviteRank_numRank").addObject("type", type).addObject("page", page);
	}

	@RequestMapping(value = "inviteNumDetailList/{type}/{pageNo}")
	@RequiresAuthentication
	public ModelAndView inviteNumDetailList(@PathVariable(value = "type") String type, @PathVariable(value = "pageNo") int pageNo) {
		Date startDate = null, endDate = null;

		// 用户当月推荐人数列表
		if ("m".equalsIgnoreCase(type)) {
			Calendar cal = Calendar.getInstance();
			startDate = DateUtils.truncate(cal.getTime(), Calendar.MONTH);
			endDate = DateUtils.addMonths(startDate, 1);

			// 用户当季推荐人数列表
		} else if ("q".equalsIgnoreCase(type)) {
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH);
			int i = (month + 1) % 3;
			startDate = DateUtils.truncate(DateUtils.addMonths(cal.getTime(), -i), Calendar.MONTH);
			endDate = DateUtils.addMonths(startDate, i + 1);

			// 用户年度推荐人数列表
		} else if ("y".equalsIgnoreCase(type)) {
			startDate = null;
			endDate = null;
		}

		Page page = friendService.queryInviteNumDetail(currentUser().getUserId(), startDate, endDate, new Page(pageNo, 5));
		return forword("account/friend/inviteRank_numDetail").addObject("type", type).addObject("page", page);
	}

	@RequestMapping(value = "inviteInterestRankList/{type}/{pageNo}")
	public ModelAndView inviteInterestRankList(@PathVariable(value = "type") String type, @PathVariable(value = "pageNo") int pageNo) {
		Page page = null;
		if ("m".equalsIgnoreCase(type)) {
			// 上月推荐共享奖排名
			int curMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
			page = friendService.queryInviteInterestRank((curMonth - 1 == 0) ? 12 : curMonth - 1, "MONTH_INTEREST", new Page(pageNo, 5));
		} else if ("y".equalsIgnoreCase(type)) {
			// 累计推荐共享奖排名
			page = friendService.queryInviteInterestRank(null, "TOTAL_INTEREST", new Page(pageNo, 5));
		}

		return forword("account/friend/inviteRank_interestRank").addObject("type", type).addObject("page", page);
	}

	@RequestMapping(value = "inviteInterestDetailList")
	@RequiresAuthentication
	public ModelAndView inviteInterestDetailList() {
		List<InviteRankVo> inviteInterestDetailList = friendService.queryInviteInterestDetailList(currentUser().getUserId());
		return forword("account/friend/inviteRank_interestDetail").addObject("inviteInterestDetailList", inviteInterestDetailList);
	}
}
