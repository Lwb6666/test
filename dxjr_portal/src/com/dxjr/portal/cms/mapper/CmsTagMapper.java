package com.dxjr.portal.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dxjr.common.page.Page;
import com.dxjr.portal.cms.vo.CmsArticle;
import com.dxjr.portal.cms.vo.CmsTag;
import com.dxjr.portal.cms.vo.SearchPageVo;

public interface CmsTagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CmsTag record);

    int insertSelective(CmsTag record);

    CmsTag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CmsTag record);

    int updateByPrimaryKey(CmsTag record);

	List<CmsTag> queryCmsTagListForPage(Page page);

	int getCountCmsTagListForPage();

	CmsTag getTagByName(@Param("name") String name);

	List<CmsTag> queryCmsTagList(@Param("channelId") Integer channelId, @Param("start") int start, @Param("count") int count);

	List<CmsTag> queryCmsTagListByParentChannelId(@Param("channelId") Integer ParentChannelId, @Param("start") int start, @Param("count") int count);

	List<CmsTag> queryTagsByName(@Param("name") String name, @Param("start") int start, @Param("count") int count);

	/**
	 * <p>
	 * Description:标签列表查询<br />
	 * </p>
	 * @author chenpeng
	 * @version 0.1 2015年2月2日
	 * @param 
	 * @return 
	 */
	List<CmsTag> queryCmsTagListByTypeAndNum(@Param("type") Integer type,@Param("num")  Integer num);

	/**
	 * <p>
	 * Description:文章列表查询-by标签<br />
	 * </p>
	 * @author chenpeng
	 * @version 0.1 2015年2月2日
	 * @param 
	 * @return 
	 */
	Integer queryArticlePageByCndCount(SearchPageVo seach);
	List<CmsArticle> queryArticlePageByCndList(SearchPageVo seach, Page p);
	
	/**
	 * <p>
	 * Description:热门的2篇文章-by标签<br />
	 * </p>
	 * @author chenpeng
	 * @version 0.1 2015年2月3日
	 * @param 
	 * @return 
	 */
	List<CmsArticle> queryHotArticlesByTagLimit(@Param("id") int id, @Param("start") int start,  @Param("count") int count);

	/**
	 * <p>
	 * Description:标签-参数查询<br />
	 * </p>
	 * @author chenpeng
	 * @version 0.1 2015年2月3日
	 * @param 
	 * @return 
	 */
	CmsTag queryCmsTagByParam(@Param("param") String param);

}