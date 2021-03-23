package com.dxjr.portal.cms.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.cms.mapper.CmsArticleMapper;
import com.dxjr.portal.cms.mapper.CmsArticleTagMapper;
import com.dxjr.portal.cms.service.CmsArticleService;
import com.dxjr.portal.cms.service.CmsTagService;
import com.dxjr.portal.cms.vo.CmsArticle;
import com.dxjr.portal.cms.vo.CmsArticleCnd;
import com.dxjr.portal.cms.vo.CmsArticleTag;
import com.dxjr.portal.cms.vo.CmsArticleVo;
import com.dxjr.portal.cms.vo.CmsComment;
import com.dxjr.portal.cms.vo.CmsCommentVo;
import com.dxjr.portal.cms.vo.CmsTag;
import com.dxjr.portal.cms.vo.SearchPageVo;

@Service
public class CmsArticleServiceImpl implements CmsArticleService {

	@Autowired
	CmsArticleMapper cmsArticleMapper;

	@Autowired
	CmsTagService cmsTagService;

	@Autowired
	CmsArticleTagMapper articleTagMapper;


	@Override
	public void saveCmsArticle(CmsArticle cmsArticle) {
		cmsArticleMapper.insert(cmsArticle);
		if (cmsArticle.getId() != null) {
			String tags = cmsArticle.getTags();
			if (!StringUtils.isEmpty(tags)) {
				String[] tagsSpl = StringUtils.splitByWholeSeparator(tags, ",");
				for (int i = 0; i < tagsSpl.length; i++) {
					String tag = tagsSpl[i];
					if (!StringUtils.isEmpty(tag)) {
						CmsTag cmsTag = new CmsTag(tag);
						// 保存标签
						cmsTagService.save(cmsTag);
						// 保存标签和文章关系
						CmsArticleTag cmsArticleTag = new CmsArticleTag(cmsArticle.getId(), cmsTag.getId(), cmsArticle.getChannelId());
						articleTagMapper.insert(cmsArticleTag);
					}

				}

			}

		}

	}

	@Override
	public void deleteCmsArticleByIds(String[] cmsArticleIds) {
		cmsArticleMapper.deleteByIds(cmsArticleIds);
	}

	@Override
	public void updateCmsArticle(CmsArticle cmsArticle) {
		cmsArticleMapper.updateByPrimaryKeyWithBLOBs(cmsArticle);

		String tags = cmsArticle.getTags();
		if (!StringUtils.isEmpty(tags)) {
			String[] tagsSpl = StringUtils.splitByWholeSeparator(tags, ",");
			for (int i = 0; i < tagsSpl.length; i++) {
				String tag = tagsSpl[i];
				if (!StringUtils.isEmpty(tag)) {
					CmsTag cmsTag = new CmsTag(tag);
					// 保存标签
					cmsTagService.save(cmsTag);
					// 保存标签和文章关系
					CmsArticleTag cmsArticleTag = new CmsArticleTag(cmsArticle.getId(), cmsTag.getId(), cmsArticle.getChannelId());
					articleTagMapper.insert(cmsArticleTag);
				}
			}
			// 解除标签和文章关系
			articleTagMapper.deleteArticleTag(cmsArticle.getId(), tagsSpl);
		}

	}

	@Override
	public Page searchCmsArticlePageByCnd(CmsArticleCnd cmsArticleCnd, int pageNo, int pageSize) {
		Page page = new Page(pageNo, pageSize);
		page.setResult(cmsArticleMapper.queryCmsArticleListByCnd(cmsArticleCnd, page));
		page.setTotalCount(cmsArticleMapper.getCountCmsArticleListByCnd(cmsArticleCnd));
		return page;
	}

	@Override
	public CmsArticle getCmsArticleById(Integer cmsArticleId) {
		return cmsArticleMapper.getCmsArticleById(cmsArticleId);
	}

	@Override
	public List<CmsArticle> findHotArticlesByLimit(int start, int count) {
		return cmsArticleMapper.queryHotArticlesByLimit(start, count);
	}

	@Override
	public List<CmsArticleVo> queryNewArticleByChannel(int[] channels) {
		return cmsArticleMapper.queryNewArticleByChannel(channels);
	}

	@Override
	public List<CmsArticle> queryCmsArticleList(Integer id, int start, int count) {
		return cmsArticleMapper.queryCmsArticleList(id, start, count);
	}

	@Override
	public List<CmsArticle> queryArticlesByTag(String name, int start, int count) {
		return cmsArticleMapper.queryArticlesByTag(name, start, count);
	}

	@Override
	public Page findPageByCnd(CmsArticleCnd seach, Page p) {
		Integer totalCount = cmsArticleMapper.queryPageByCndCount(seach);
		p.setTotalCount(totalCount);

		List<CmsArticle> list = cmsArticleMapper.queryPageByCndList(seach, p);
		p.setResult(list);
		return p;
	}

	@Override
	public List<CmsArticle> findHotArticlesByChannelLimit(int channelId, int i, int j) {
		
		return cmsArticleMapper.queryHotArticlesByChannelLimit(channelId,i,j);
	}

	@Override
	public List<CmsArticle> queryTopArticlesByChannels(int[] channelTopArticles, int i, int j) {
		Page page = new Page(i, j);
		return cmsArticleMapper.queryTopArticlesByChannels(channelTopArticles, page);
	}

	@Override
	public List<CmsArticle> findAboutArticlesByTag(Integer id) {
		return cmsArticleMapper.queryAboutArticlesByTag(id);
	}

	@Override
	public CmsArticle findArticlesLast(Integer id) {
		return cmsArticleMapper.queryArticlesLast(id);
	}

	@Override
	public CmsArticle findArticlesNext(Integer id) {
		return cmsArticleMapper.queryArticlesNext(id);
	}

	@Override
	public void saveComment(CmsComment cmsComment) {
	
		cmsArticleMapper.insertComment(cmsComment);
	}
	@Override
	public CmsCommentVo queryCommentSum(Integer articleId) {
	
		return cmsArticleMapper.queryCommentSum(articleId);
	}

	@Override
	public void updateCmsArticleHits(CmsArticle cmsArticle2) {
		cmsArticleMapper.updateCmsArticleHits(cmsArticle2);
	}

	@Override
	public List<CmsArticle> queryCmsArticleListByParentChannelId(int moneymanagement, int i, int j) {
		return cmsArticleMapper.queryCmsArticleListByParentChannelId(moneymanagement, i, j);
	}

	@Override
	public List<CmsArticle> findNewArticlesByChannelLimit(int channelId, int i, int j) {
		return cmsArticleMapper.queryNewArticlesByChannelLimit(channelId, i, j);
	}

	@Override
	public Page queryCommentPageByCnd(SearchPageVo seach, Page p) {
		Integer totalCount = cmsArticleMapper.queryCommentPageByCndCount(seach);
		p.setTotalCount(totalCount);

		List<CmsCommentVo> list = cmsArticleMapper.queryCommentPageByCndList(seach, p);
		p.setResult(list);
		return p;
	}

	@Override
	public List<CmsArticle> findTopArticlesByChannelLimit(Integer id, int i, int j) {
		return cmsArticleMapper.queryTopArticlesByChannelLimit( id,  i,  j);
	}

	@Override
	public List<CmsCommentVo> queryCommentListByCnd(SearchPageVo searchPageVo) {
		return cmsArticleMapper.queryCommentListByCnd(searchPageVo);
	}


}
