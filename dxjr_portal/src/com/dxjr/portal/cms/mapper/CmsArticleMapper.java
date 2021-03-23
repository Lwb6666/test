package com.dxjr.portal.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dxjr.common.page.Page;
import com.dxjr.portal.cms.vo.CmsArticle;
import com.dxjr.portal.cms.vo.CmsArticleCnd;
import com.dxjr.portal.cms.vo.CmsArticleVo;
import com.dxjr.portal.cms.vo.CmsChannel;
import com.dxjr.portal.cms.vo.CmsComment;
import com.dxjr.portal.cms.vo.CmsCommentVo;
import com.dxjr.portal.cms.vo.SearchPageVo;

public interface CmsArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CmsArticle record);

    int insertSelective(CmsArticle record);

    CmsArticle selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CmsArticle record);

    int updateByPrimaryKeyWithBLOBs(CmsArticle record);

    int updateByPrimaryKey(CmsArticle record);

	void deleteByIds(@Param("cmsArticleIds") String[] cmsArticleIds);

	List<CmsArticle> queryCmsArticleListByCnd(CmsArticleCnd cmsArticleCnd, Page page);

	int getCountCmsArticleListByCnd(CmsArticleCnd cmsArticleCnd);

	List<CmsArticle> queryHotArticlesByLimit(@Param("start") int start, @Param("count") int count);

	List<CmsArticleVo> queryNewArticleByChannel(@Param("channels") int[] channels);

	List<CmsArticle> queryCmsArticleList(@Param("channelId") Integer id, @Param("start") int start, @Param("count") int count);

	List<CmsArticle> queryCmsArticleListByParentChannelId(@Param("channelId") Integer id, @Param("start") int start, @Param("count") int count);

	List<CmsArticle> queryArticlesByTag(@Param("name") String name, @Param("start") int start, @Param("count") int count);

	Integer queryPageByCndCount(CmsArticleCnd seach);

	List<CmsArticle> queryPageByCndList(CmsArticleCnd seach, Page p);

	List<CmsArticle> queryHotArticlesByChannelLimit(@Param("channelId") int channelId, @Param("start") int start,  @Param("count") int count);

	List<CmsArticle> queryTopArticlesByChannels(@Param("channelTopArticles") int[] channelTopArticles, Page page);
	
	List<CmsArticle> queryAboutArticlesByTag(Integer id);

	CmsArticle queryArticlesLast(Integer id);

	CmsArticle queryArticlesNext(Integer id);

	CmsArticle getCmsArticleById(Integer cmsArticleId);

	CmsChannel getChannelByPinyin(@Param("pinyin") String pinyin);

	int insertComment(CmsComment cmsComment);

	CmsCommentVo queryCommentSum(Integer articleId);

	void updateCmsArticleHits(CmsArticle cmsArticle2);

	List<CmsArticle> queryNewArticlesByChannelLimit(@Param("channelId") int channelId, @Param("start") int start,  @Param("count") int count);

	Integer queryCommentPageByCndCount(SearchPageVo seach);

	List<CmsCommentVo> queryCommentPageByCndList(SearchPageVo seach, Page p);

	List<CmsArticle> queryTopArticlesByChannelLimit(@Param("channelId") int channelId, @Param("start") int start,  @Param("count") int count);

	List<CmsCommentVo> queryCommentListByCnd(SearchPageVo seach);
}