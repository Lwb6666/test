package com.dxjr.portal.news_notice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.base.entity.NewsNotice;
import com.dxjr.common.page.Page;
import com.dxjr.portal.news_notice.service.NewsNoticeService;
import com.dxjr.portal.news_notice.vo.NewsNoticeCnd;

/**
 * 
 * <p>
 * Description:媒体报道 Controller<br />
 * </p>
 * 
 * @title NewsNoticeController.java
 * @package com.dxjr.portal.news_notice.controller
 * @author yangshijin
 * @version 0.1 2014年4月23日
 */
@Controller
@RequestMapping(value = "/baodao")
public class NewsNoticeController {
	@Autowired
	private NewsNoticeService newsNoticeService;

	/**
	 * 
	 * <p>
	 * Description:进入新闻列表（新闻报道）<br />
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
		request.setAttribute("type", 1);
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

			newsNoticeCnd.setType(1);
			newsNoticeCnd.setStatus(0);
			Page page = newsNoticeService.queryListByNewsNoticeCnd(newsNoticeCnd, pageNo, pageSize);
			request.setAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("type", 1);
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
	@RequestMapping(value = "{id}")
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
			 * 5); request.setAttribute("page", page); if (nn != null &&
			 * nn.getUpdatetime() != null) {
			 * request.setAttribute("updatetimeStr",
			 * DateUtils.timeStampToDate(nn.getUpdatetime(),
			 * DateUtils.YMD_DASH)); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("type", 1);
		return "index/news_notice/news-detail";
	}

	@RequestMapping(value = "/hits/{id}")
	@ResponseBody
	public String hits(HttpServletRequest request, @PathVariable int id) {
		NewsNotice newsNotice;
		try {
			newsNotice = newsNoticeService.queryById(id);
			newsNotice.setHits(newsNotice.getHits() + 1);
			newsNoticeService.updateNewsNotice(newsNotice);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

}
