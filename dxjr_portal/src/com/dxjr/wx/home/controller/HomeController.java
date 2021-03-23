package com.dxjr.wx.home.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.portal.account.service.RiskMarginService;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.fix.service.FixBorrowService;
import com.dxjr.portal.fix.vo.FixBorrowVo;
import com.dxjr.portal.index.vo.SlideshowCnd;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.news_notice.service.NewsNoticeService;
import com.dxjr.wx.favorite.service.TenderService;
import com.dxjr.wx.favorite.service.TransferService;

/**
 * 微信主页控制类
 * 
 * @title HomeController.java
 * @package com.dxjr.wx.home
 * @author ZHUCHEN
 * @version 0.1 2014年10月19日
 */
@Controller
@RequestMapping(value = "/wx/home")
public class HomeController extends BaseController {

	public Logger logger = Logger.getLogger(HomeController.class);

	@Autowired
	private TenderService tenderService;
	@Autowired
	private TransferService transferService;
	@Autowired
	private NewsNoticeService newsNoticeService;
	@Autowired
	private BorrowService borrowService;
	@Autowired
	private RiskMarginService riskMarginService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private FixBorrowService fixBorrowService;

	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/init")
	@ResponseBody
	public Map<String, Object> initHome() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 幻灯片
			SlideshowCnd slideshowCnd = new SlideshowCnd();
			slideshowCnd.setsType(5); // 类型；
			slideshowCnd.setsStart(0);
			slideshowCnd.setsEnd(5); // 微信首页，目前取 5 张图片；
			slideshowCnd.setNowTime(new Date());
			map.put("slideshowVoList", newsNoticeService.queryListSlideshowByCnd(slideshowCnd));

			// 成交金额/万元
			map.put("totalMoney", String.valueOf(borrowService.getTotalMoney().divide(new BigDecimal(10000)).intValue()));
			// 风险备用金/万元
			map.put("riskMargin", String.valueOf(riskMarginService.queryRiskMargin().divide(new BigDecimal(10000)).intValue()));
			// 注册人数
			map.put("regCount", String.valueOf(memberService.getRegistUserCount()));

			// 新手宝,定期宝1，3，6，12
			List<FixBorrowVo> fixs = new ArrayList<>();
			fixs.add(fixBorrowService.getNewFixBorrow());// 新手宝
			fixs.add(fixBorrowService.getLimitFixBorrow(1));
			fixs.add(fixBorrowService.getLimitFixBorrow(3));
			fixs.add(fixBorrowService.getLimitFixBorrow(6));
			fixs.add(fixBorrowService.getLimitFixBorrow(12));
			map.put("fixs", fixs);

			// 散标投资
			map.put("bidCountVo", tenderService.bidCount());

			// 债权转让
			map.put("transferCountVo", transferService.bidCount());
			map.put("firstNum", transferService.firstCount());
		} catch (Exception e) {
			logger.error("微信-首页用户信息获取异常", e);
		}
		return map;
	}

}
