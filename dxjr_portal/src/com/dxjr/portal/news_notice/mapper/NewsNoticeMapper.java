package com.dxjr.portal.news_notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dxjr.common.page.Page;
import com.dxjr.portal.index.vo.SlideshowCnd;
import com.dxjr.portal.index.vo.SlideshowVo;
import com.dxjr.portal.news_notice.vo.NewsNoticeCnd;
import com.dxjr.portal.news_notice.vo.NewsNoticeVo;

/**
 * 
 * <p>
 * Description:新闻公告类数据访问类<br />
 * </p>
 * 
 * @title NewsNoticeMapper.java
 * @package com.dxjr.portal.news_notice.mapper
 * @author yangshijin
 * @version 0.1 2014年4月23日
 */
public interface NewsNoticeMapper {
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
	 * Description:根据条件查询新闻公告记录集合（分页）<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年4月23日
	 * @param newsNoticeCnd
	 * @return
	 * @throws Exception
	 *             List<NewsNoticeVo>
	 */
	public List<NewsNoticeVo> queryNewsNoticeListForPage(
			NewsNoticeCnd newsNoticeCnd, Page page) throws Exception;

	public List<NewsNoticeVo> queryNewNewsNoticeList(@Param("type") int type, @Param("start") int start, @Param("count") int count);

	public List<SlideshowVo> queryListSlideshowByCnd(@Param("slideshowCnd") SlideshowCnd slideshowCnd);
}
