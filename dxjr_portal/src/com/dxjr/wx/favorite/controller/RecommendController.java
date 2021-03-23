package com.dxjr.wx.favorite.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.service.FriendService;
import com.dxjr.portal.account.vo.InviteRankVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.statics.BusinessConstants;

@Controller
@RequestMapping(value = "/wx/recommend")
public class RecommendController extends BaseController {

	private static final Logger logger = Logger.getLogger(RecommendController.class);

	@Autowired
	private FriendService friendService;

	/**
	 * 微信-推荐活动-推荐人数
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年3月3日
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/friendList/{pageNum}")
	@ResponseBody
	public Map<String, Object> friendList(@PathVariable Integer pageNum) {
		try {
			if (currentUser() != null) {
				List<?> friendList = friendService.queryInviteDetailsByUserId(currentUser().getUserId(), pageNum, BusinessConstants.DEFAULT_PAGE_SIZE).getResult();
				if (friendList != null) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("friendList", friendList);
					if (friendList.size() == BusinessConstants.DEFAULT_PAGE_SIZE)
						map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
					return map;
				}
			}
		} catch (Exception e) {
			logger.error("微信-推荐活动-推荐人数获取异常", e);
		}
		return null;
	}

	/**
	 * 微信-推荐活动-推荐奖励
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年3月3日
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/reward")
	@ResponseBody
	public Map<String, Object> reward() {
		try {
			if (currentUser() != null) {
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

				Map<String, Object> map = new HashMap<String, Object>();
				if (inviteNumRankCurMonth != null) {
					map.put("monNum", inviteNumRankCurMonth.getNum());
					map.put("monRank", inviteNumRankCurMonth.getRank());
				}
				map.put("lastMonReward", issuedRewardLastMonth);

				if (inviteNumRankCurQuarter != null) {
					map.put("quarterNum", inviteNumRankCurQuarter.getNum());
					map.put("quarterRank", inviteNumRankCurQuarter.getRank());
				}
				map.put("lastQuarterReward", issuedRewardLastQuarter);

				if (inviteNumRankYear != null) {
					map.put("yearNum", inviteNumRankYear.getNum());
					map.put("yearRank", inviteNumRankYear.getRank());
				}
				map.put("yearReward", issuedRewardYear);

				map.put("monShareReward", inviteInterestRankLastMonth.getInterest());
				map.put("monShareRank", inviteInterestRankLastMonth.getRank());

				map.put("yearShareReward", inviteInterestRankYear.getInterest());
				map.put("yearShareRank", inviteInterestRankYear.getRank());

				return map;
			}
		} catch (Exception e) {
			logger.error("微信-推荐活动-推荐奖励获取异常", e);
		}
		return null;
	}

	/**
	 * 微信-推荐活动-历史推荐共享奖
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年3月3日
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/rewardHistory")
	@ResponseBody
	public Map<String, Object> rewardHistory() {
		try {
			if (currentUser() != null) {
				List<?> historyList = friendService.queryInviteInterestDetailList(currentUser().getUserId());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("historyList", historyList);
				return map;
			}
		} catch (Exception e) {
			logger.error("微信-推荐活动-历史推荐共享奖获取异常", e);
		}
		return null;
	}

	/**
	 * 微信-推荐活动-推荐竞技奖排名
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年3月3日
	 * @param type
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/recommendRank/{type}/{pageNum}")
	@ResponseBody
	public Map<String, Object> recommendRank(@PathVariable String type, @PathVariable int pageNum) {
		try {
			Date startDate = null, endDate = null;
			if ("m".equalsIgnoreCase(type)) {
				startDate = DateUtils.truncate(new Date(), Calendar.MONTH);
				endDate = DateUtils.addMonths(startDate, 1);
			} else if ("q".equalsIgnoreCase(type)) {
				Calendar cal = Calendar.getInstance();
				int i = (cal.get(Calendar.MONTH) + 1) % 3;
				startDate = DateUtils.truncate(DateUtils.addMonths(cal.getTime(), -i), Calendar.MONTH);
				endDate = DateUtils.addMonths(startDate, i + 1);
			}

			Map<String, Object> map = new HashMap<String, Object>();
			List<?> rankList = friendService.queryInviteNumRank(startDate, endDate, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE)).getResult();
			map.put("rankList", rankList);

			if (rankList.size() == BusinessConstants.DEFAULT_PAGE_SIZE)
				map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
			return map;
		} catch (Exception e) {
			logger.error("微信-推荐活动-推荐竞技奖排名获取异常", e);
		}
		return null;
	}

	/**
	 * 微信-推荐活动-推荐共享奖排名
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年3月3日
	 * @param type
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/rewardRank/{type}/{pageNum}")
	@ResponseBody
	public Map<String, Object> rewardRank(@PathVariable String type, @PathVariable int pageNum) {
		try {
			Page page = null;
			if ("m".equalsIgnoreCase(type)) {
				int curMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
				page = friendService.queryInviteInterestRank((curMonth - 1 == 0) ? 12 : curMonth - 1, "MONTH_INTEREST", new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE));
			} else if ("y".equalsIgnoreCase(type)) {
				page = friendService.queryInviteInterestRank(null, "TOTAL_INTEREST", new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE));
			}

			Map<String, Object> map = new HashMap<String, Object>();
			List<?> rankList = page.getResult();
			map.put("rankList", rankList);

			if (rankList.size() == BusinessConstants.DEFAULT_PAGE_SIZE)
				map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
			return map;
		} catch (Exception e) {
			logger.error("微信-推荐活动-推荐共享奖排名获取异常", e);
		}
		return null;
	}

	/**
	 * 微信-推荐活动-推荐竞技-人数
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年3月4日
	 * @param type
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/recommendList/{type}/{pageNum}")
	@ResponseBody
	public Map<String, Object> recommendList(@PathVariable String type, @PathVariable int pageNum) {
		try {
			Date startDate = null, endDate = null;
			if ("m".equalsIgnoreCase(type)) {
				startDate = DateUtils.truncate(new Date(), Calendar.MONTH);
				endDate = DateUtils.addMonths(startDate, 1);
			} else if ("q".equalsIgnoreCase(type)) {
				Calendar cal = Calendar.getInstance();
				int i = (cal.get(Calendar.MONTH) + 1) % 3;
				startDate = DateUtils.truncate(DateUtils.addMonths(cal.getTime(), -i), Calendar.MONTH);
				endDate = DateUtils.addMonths(startDate, i + 1);
			}

			Map<String, Object> map = new HashMap<String, Object>();
			List<?> recommendList = friendService.queryInviteNumDetail(currentUser().getUserId(), startDate, endDate, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE)).getResult();
			map.put("recommendList", recommendList);

			if (recommendList.size() == BusinessConstants.DEFAULT_PAGE_SIZE)
				map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
			return map;
		} catch (Exception e) {
			logger.error("微信-推荐活动-推荐竞技-人数获取异常", e);
		}
		return null;
	}

}
