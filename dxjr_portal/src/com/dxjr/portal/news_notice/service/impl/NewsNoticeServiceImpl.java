package com.dxjr.portal.news_notice.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.NewsNotice;
import com.dxjr.base.mapper.BaseNewsNoticeMapper;
import com.dxjr.common.page.Page;
import com.dxjr.portal.index.vo.SlideshowCnd;
import com.dxjr.portal.index.vo.SlideshowVo;
import com.dxjr.portal.news_notice.mapper.NewsNoticeMapper;
import com.dxjr.portal.news_notice.service.NewsNoticeService;
import com.dxjr.portal.news_notice.vo.NewsNoticeCnd;
import com.dxjr.portal.news_notice.vo.NewsNoticeVo;

/**
 * 
 * <p>
 * Description:新闻公告接口的实现类<br />
 * </p>
 * 
 * @title NewsNoticeServiceImpl.java
 * @package com.dxjr.news_notice.service.impl
 * @author yangshijin
 * @version 0.1 2014年4月24日
 */
@Service
public class NewsNoticeServiceImpl implements NewsNoticeService {
	public Logger logger = Logger.getLogger(NewsNoticeServiceImpl.class);

	@Autowired
	private NewsNoticeMapper newsNoticeMapper;
	@Autowired
	private BaseNewsNoticeMapper baseNewsNoticeMapper;

	@Override
	public NewsNotice queryById(int id) throws Exception {
		return baseNewsNoticeMapper.queryById(id);
	}

	@Override
	public int insertNewsNotice(NewsNotice newsNotice) throws Exception {
		return baseNewsNoticeMapper.insertEntity(newsNotice);
	}

	@Override
	public int updateNewsNotice(NewsNotice newsNotice) throws Exception {
		return baseNewsNoticeMapper.updateEntity(newsNotice);
	}

	@Override
	public List<NewsNoticeVo> queryNewsNoticeList(NewsNoticeCnd newsNoticeCnd)
			throws Exception {
		return newsNoticeMapper.queryNewsNoticeList(newsNoticeCnd);
	}

	@Override
	public Integer queryNewsNoticeCount(NewsNoticeCnd newsNoticeCnd)
			throws Exception {
		return newsNoticeMapper.queryNewsNoticeCount(newsNoticeCnd);
	}

	@Override
	public Page queryListByNewsNoticeCnd(NewsNoticeCnd newsNoticeCnd,
			int curPage, int pageSize) throws Exception {
		Page page = new Page(curPage, pageSize);
		int totalCount = queryNewsNoticeCount(newsNoticeCnd);
		page.setTotalCount(totalCount);
		List<NewsNoticeVo> list = newsNoticeMapper.queryNewsNoticeListForPage(
				newsNoticeCnd, page);
		page.setResult(list);
		return page;
	}

	@Override
	public List<NewsNoticeVo> queryNewNewsNoticeList(int type, int start, int count) {
		List<NewsNoticeVo> list = newsNoticeMapper.queryNewNewsNoticeList(type, start, count);
		return list;
	}

	@Override
	public List<SlideshowVo> queryListSlideshowByCnd(SlideshowCnd slideshowCnd) {
		List<SlideshowVo> list = newsNoticeMapper.queryListSlideshowByCnd(slideshowCnd);
		return list;
	}
}
