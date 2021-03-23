package com.dxjr.portal.news_notice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dxjr.base.entity.NewsNotice;
import com.dxjr.common.CacheService;
import com.dxjr.common.SerializeUtil;
import com.dxjr.common.page.Page;
import com.dxjr.portal.news_notice.service.NewsNoticeService;
import com.dxjr.portal.news_notice.vo.NewsNoticeCnd;

/**
 * <p>
 * Description:新闻公告 Controller<br />
 * </p>
 * 
 * @title GonggaoController.java
 * @package com.dxjr.portal.news_notice.controller
 * @author justin.xu
 * @version 0.1 2015年1月31日
 */
@Controller
@RequestMapping(value = "/gonggao")
public class GonggaoController {
	@Autowired
	private NewsNoticeService newsNoticeService;

	/**
	 * 
	 * <p>
	 * Description:进入新闻列表（包含网站公告）<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月29日
	 * @param request
	 * @param type
	 * @return String
	 */
	@RequestMapping
	public String toNewMain(HttpServletRequest request) {
		request.setAttribute("type", 0);
		return "index/news_notice/news-main";
	}

	/**
	 * 
	 * <p>
	 * Description:查询新闻公告列表<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年4月23日
	 * @param request
	 * @param type
	 * @param pageSize
	 * @return String
	 */
	@RequestMapping(value = "/queryList/{pageSize}")
	public String queryList(HttpServletRequest request, @PathVariable int pageSize) {
		String pageNo_str = request.getParameter("pageNo");
		int pageNo = 1;
		if (pageNo_str != null && !pageNo_str.equals("")) {
			pageNo = Integer.parseInt(pageNo_str);
		}

		try {
			NewsNoticeCnd newsNoticeCnd = new NewsNoticeCnd();

			// fateson 2014年11月19日 update start
			String title = request.getParameter("title");
			if (!StringUtils.isEmpty(title)) {
				newsNoticeCnd.setTitle(title);
			}
			// fateson end

			newsNoticeCnd.setType(0);
			newsNoticeCnd.setStatus(0);

			String enable = CacheService.getInstance().getEnable();
			Page page=null;
			String cacheKey="PT_newsNotice_P1";

			if(pageNo ==1 ){
				if (enable != null && enable.equals("1")) {
					byte[] weekInfo = CacheService.getInstance().getBytes(cacheKey.getBytes());
					if (weekInfo != null) {
						page = (Page) SerializeUtil.unserialize(weekInfo);
					} else {
						page = newsNoticeService.queryListByNewsNoticeCnd(newsNoticeCnd, pageNo, pageSize);
						CacheService.getInstance().put(cacheKey.getBytes(), SerializeUtil.serialize(page));
						CacheService.getInstance().expire(cacheKey.getBytes(), 1800);// 半小时候失效
					}
				} else {
					page = newsNoticeService.queryListByNewsNoticeCnd(newsNoticeCnd, pageNo, pageSize);
				}
			}else{
				page = newsNoticeService.queryListByNewsNoticeCnd(newsNoticeCnd, pageNo, pageSize);
			}
			request.setAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("type", 0);
		return "index/news_notice/news-list";
	}

	/**
	 * 
	 * <p>
	 * Description:查询新闻公告详情<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年4月23日
	 * @param request
	 * @param id
	 * @return String
	 */
	@RequestMapping(value = "/{id}")
	public String news_notice_DetailNew(HttpServletRequest request, @PathVariable int id, HttpServletResponse response) {
		String pageNo_str = request.getParameter("pageNo");
		int pageNo = 1;
		if (pageNo_str != null && !pageNo_str.equals("")) {
			pageNo = Integer.parseInt(pageNo_str);
		}
		try {
			NewsNotice newsNotice;
			newsNotice = newsNoticeService.queryById(id);
			newsNotice.setHits(newsNotice.getHits() + 1);
			newsNoticeService.updateNewsNotice(newsNotice);

			NewsNotice nn = newsNoticeService.queryById(id);
			if (nn.getStatus() == 1) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
			request.setAttribute("news_notice_detail", nn);

			/*
			 * NewsNoticeCnd newsNoticeCnd = new NewsNoticeCnd();
			 * newsNoticeCnd.setType(nn.getType()); newsNoticeCnd.setStatus(0);
			 * Page page =
			 * newsNoticeService.queryListByNewsNoticeCnd(newsNoticeCnd, pageNo,
			 * 5); request.setAttribute("page", page);
			 * 
			 * if (nn != null && nn.getUpdatetime() != null) {
			 * request.setAttribute("updatetimeStr",
			 * DateUtils.timeStampToDate(nn.getUpdatetime(),
			 * DateUtils.YMD_DASH)); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("type", 0);
		return "index/news_notice/news-detail";
	}

}
