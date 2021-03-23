package com.dxjr.wx.activity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.index.vo.SlideshowCnd;
import com.dxjr.portal.news_notice.service.NewsNoticeService;
import com.dxjr.portal.news_notice.vo.NewsNoticeCnd;

/**
 * 微信-推荐
 * 
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/wx/activity")
public class WxActivityController extends BaseController {

	public Logger logger = Logger.getLogger(WxActivityController.class);

	@Autowired
	private NewsNoticeService newsNoticeService;

	/**
	 * 推荐-主页
	 * 
	 * @return
	 */
	@RequestMapping(value = "")
	@ResponseBody
	public Map<String, Object> index() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 幻灯片
			SlideshowCnd slideshowCnd = new SlideshowCnd();
			slideshowCnd.setsType(6); // 类型；
			slideshowCnd.setsStart(0);
			slideshowCnd.setsEnd(50);
			slideshowCnd.setNowTime(new Date());
			map.put("slideshowVoList", newsNoticeService.queryListSlideshowByCnd(slideshowCnd));

			// 公告
			NewsNoticeCnd newsNoticeCnd = new NewsNoticeCnd();
			newsNoticeCnd.setType(0);
			newsNoticeCnd.setStatus(0);
			List<?> noticeList = newsNoticeService.queryListByNewsNoticeCnd(newsNoticeCnd, 1, 1).getResult();
			map.put("noticeList", noticeList);

			// 用户
			if (currentUser() != null) {
				map.put("loginUser", currentUser());
			}

		} catch (Exception e) {
			logger.error("微信-活动信息获取异常", e);
		}
		return map;
	}

}
