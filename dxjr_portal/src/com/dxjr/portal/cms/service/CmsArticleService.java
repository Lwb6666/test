package com.dxjr.portal.cms.service;

import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.cms.vo.CmsArticle;
import com.dxjr.portal.cms.vo.CmsArticleCnd;
import com.dxjr.portal.cms.vo.CmsArticleVo;
import com.dxjr.portal.cms.vo.CmsComment;
import com.dxjr.portal.cms.vo.CmsCommentVo;
import com.dxjr.portal.cms.vo.SearchPageVo;

public interface CmsArticleService {

	void saveCmsArticle(CmsArticle cmsArticle);

	/**
	 * <p>
	 * Description:批量删除文章<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年1月22日
	 * @param cmsArticleIds void
	 */
	void deleteCmsArticleByIds(String[] cmsArticleIds);

	/**
	 * <p>
	 * Description:更新文章<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年1月28日
	 * @param cmsArticle void
	 */
	void updateCmsArticle(CmsArticle cmsArticle);

	/**
	 * <p>
	 * Description:查询文章 <br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年1月28日
	 * @param cmsArticleCnd
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page searchCmsArticlePageByCnd(CmsArticleCnd cmsArticleCnd, int pageNo, int pageSize);

	/**
	 * <p>
	 * Description:id查询文章<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年1月28日
	 * @param cmsArticleId
	 * @return CmsArticle
	 */
	CmsArticle getCmsArticleById(Integer cmsArticleId);

	/**
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年1月31日
	 * @param i
	 * @param start FSTASK
	 * @return List<CmsArticle>
	 */
	List<CmsArticle> findHotArticlesByLimit(int i, int start);

	List<CmsArticleVo> queryNewArticleByChannel(int[] channels);

	List<CmsArticle> queryCmsArticleList(Integer id, int i, int j);

	List<CmsArticle> queryArticlesByTag(String name, int i, int j);

	/**
	 * <p>
	 * Description:栏目列表-文章分页查询<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2015年1月31日
	 * @param
	 * @param
	 * @return List<CmsArticle>
	 */
	Page findPageByCnd(CmsArticleCnd cmsArticleCnd, Page page);

	/**
	 * <p>
	 * Description:栏目-热门文章<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2015年2月3日
	 * @param
	 * @param
	 * @return List<CmsArticle>
	 */
	List<CmsArticle> findHotArticlesByChannelLimit(int channelId,int i, int j);

	List<CmsArticle> queryTopArticlesByChannels(int[] channelTopArticles, int i, int j);

	/**
	 * <p>
	 * Description:资讯详情-相关文章<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2015年2月3日
	 * @param
	 * @param
	 * @return List<CmsArticle>
	 */
	List<CmsArticle> findAboutArticlesByTag(Integer id);

	/**
	 * <p>
	 * Description:资讯详情-上一篇文章<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2015年2月4日
	 * @param
	 * @param
	 * @return CmsArticle
	 */
	CmsArticle findArticlesLast(Integer id);

	/**
	 * <p>
	 * Description:资讯详情-下一篇文章<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2015年2月4日
	 * @param
	 * @param
	 * @return CmsArticle
	 */
	CmsArticle findArticlesNext(Integer id);

	/**
	 * <p>
	 * Description:保存评论<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2015年2月5日
	 * @param
	 * @return
	 */
	void saveComment(CmsComment cmsComment);

	/**
	 * <p>
	 * Description:查询该文章的评论总数，总人数；<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2015年2月5日
	 * @param
	 * @return
	 */
	CmsCommentVo queryCommentSum(Integer articleId);

	/**
	 * <p>
	 * Description:文章点击数；<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2015年2月5日
	 * @param
	 * @return
	 */
	void updateCmsArticleHits(CmsArticle cmsArticle2);

	/**
	 * <p>
	 * Description:查询父栏目下最新文章<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年2月5日
	 * @param moneymanagement
	 * @param i
	 * @param j
	 * @return List<CmsArticle>
	 */
	List<CmsArticle> queryCmsArticleListByParentChannelId(int moneymanagement, int i, int j);

	/**
	 * <p>
	 * Description:栏目-文章最新<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2015年2月3日
	 * @param
	 * @param
	 * @return List<CmsArticle>
	 */
	List<CmsArticle> findNewArticlesByChannelLimit(int channelId, int i, int j);

	/**
	 * <p>
	 * Description:评论-文章id分页查询<br />两篇置顶文章---本栏目
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2015年2月3日
	 * @param
	 * @param
	 * @return List<CmsArticle>
	 */
	Page queryCommentPageByCnd(SearchPageVo searchPageVo, Page page);

	/**
	 * <p>
	 * Description:两篇置顶文章---本栏目<br />
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2015年2月9日
	 * @param
	 * @param
	 * @return List<CmsArticle>
	 */
	List<CmsArticle> findTopArticlesByChannelLimit(Integer id, int i, int j);
	/**
	 * <p>
	 * Description:评论列表查询<br /> 
	 * </p>
	 * 
	 * @author 陈鹏
	 * @version 0.1 2015年2月9日
	 * @param
	 * @return  ajax
	 */
	List<CmsCommentVo> queryCommentListByCnd(SearchPageVo searchPageVo);
}
