package com.dxjr.wx.notice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.base.entity.NewsNotice;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.news_notice.service.NewsNoticeService;
import com.dxjr.portal.news_notice.vo.NewsNoticeCnd;
import com.dxjr.portal.statics.BusinessConstants;

@Controller
@RequestMapping(value = "/wx/notice")
public class NoticeController extends BaseController {

	private static final Logger logger = Logger.getLogger(NoticeController.class);

	@Autowired
	private NewsNoticeService newsNoticeService;

	/**
	 * 微信-平台公告-列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年12月5日
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/{pageNum}")
	@ResponseBody
	public Map<String, Object> noticeList(@PathVariable int pageNum, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			NewsNoticeCnd newsNoticeCnd = new NewsNoticeCnd();
			newsNoticeCnd.setType(0);
			newsNoticeCnd.setStatus(0);
			if (size == null || size <= 0) {
				size = BusinessConstants.DEFAULT_PAGE_SIZE;
			}
			List<?> noticeList = newsNoticeService.queryListByNewsNoticeCnd(newsNoticeCnd, pageNum, size).getResult();

			if (noticeList != null) {
				map.put("noticeList", noticeList);
				if (noticeList.size() == BusinessConstants.DEFAULT_PAGE_SIZE)
					map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
				return map;
			}
		} catch (Exception e) {
			logger.error("微信-平台公告-列表获取异常", e);
		}
		return map;
	}

	/**
	 * 微信-平台公告-详情
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年12月5日
	 * @param noticeId
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/detail/{noticeId}")
	@ResponseBody
	public Map<String, Object> notice(@PathVariable Integer noticeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			NewsNotice n = newsNoticeService.queryById(noticeId);
			n.setHits(n.getHits() + 1);
			newsNoticeService.updateNewsNotice(n);
			n = newsNoticeService.queryById(noticeId);
			map.put("n", n);
			return map;
		} catch (Exception e) {
			logger.error("微信-平台公告-详情获取异常", e);
		}
		return map;
	}

}
