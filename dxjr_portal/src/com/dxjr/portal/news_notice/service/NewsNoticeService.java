package com.dxjr.portal.news_notice.service;

import java.util.List;

import com.dxjr.base.entity.NewsNotice;
import com.dxjr.common.page.Page;
import com.dxjr.portal.index.vo.SlideshowCnd;
import com.dxjr.portal.index.vo.SlideshowVo;
import com.dxjr.portal.news_notice.vo.NewsNoticeCnd;
import com.dxjr.portal.news_notice.vo.NewsNoticeVo;

/**
 * 
 * <p>
 * Description:新闻公告业务类<br />
 * </p>
 * 
 * @title NewsNoticeService.java
 * @package com.dxjr.news_notice.service
 * @author yangshijin
 * @version 0.1 2014年4月23日
 */
public interface NewsNoticeService {
	/**
	 * 
	 * <p>
	 * Description:插入记录到新闻公告表<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年4月23日
	 * @param newsNotice
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int insertNewsNotice(NewsNotice newsNotice) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:更新新闻公告类<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年4月23日
	 * @param newsNotice
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int updateNewsNotice(NewsNotice newsNotice) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据ID查询新闻公告类<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年4月23日
	 * @param id
	 * @return NewsNotice
	 */
	public NewsNotice queryById(int id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据条件查询新闻公告记录集合<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年4月23日
	 * @param newsNoticeCnd
	 * @return
	 * @throws Exception
	 *             List<NewsNoticeVo>
	 */
	public List<NewsNoticeVo> queryNewsNoticeList(NewsNoticeCnd newsNoticeCnd)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据条件查询新闻公告记录集合数量<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年4月23日
	 * @param newsNoticeCnd
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer queryNewsNoticeCount(NewsNoticeCnd newsNoticeCnd)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据类型查询记录 并分页<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年4月23日
	 * @param newsNoticeCnd
	 * @param curPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *             Page
	 */
	public Page queryListByNewsNoticeCnd(NewsNoticeCnd newsNoticeCnd,
			int curPage, int pageSize) throws Exception;

	/**
	 * <p>
	 * Description:查询最新公告<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年1月31日
	 * @param type FSTASK
	 * @param start
	 * @param count
	 * @return List<NewsNoticeVo>
	 */
	public List<NewsNoticeVo> queryNewNewsNoticeList(int type, int start, int count);

	/**
	 * <p>
	 * Description:官网首页幻灯管理查询<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2015年2月15日
	 * @param SlideshowCnd
	 * @return List<SlideshowVo>
	 */
	public List<SlideshowVo> queryListSlideshowByCnd(SlideshowCnd slideshowCnd);

}
