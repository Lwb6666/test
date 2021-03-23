package com.dxjr.portal.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.cms.mapper.CmsTagMapper;
import com.dxjr.portal.cms.service.CmsTagService;
import com.dxjr.portal.cms.vo.CmsArticle;
import com.dxjr.portal.cms.vo.CmsTag;
import com.dxjr.portal.cms.vo.SearchPageVo;

@Service
public class CmsTagServiceImpl implements CmsTagService {

	@Autowired
	CmsTagMapper cmsTagMapper;


	@Override
	public Page searchCmsTagPage(int pageNo, int pageSize) {
		Page page = new Page(pageNo, pageSize);
		page.setResult(cmsTagMapper.queryCmsTagListForPage(page));
		page.setTotalCount(cmsTagMapper.getCountCmsTagListForPage());
		return page;
	}


	@Override
	public void save(CmsTag cmsTag) {
		CmsTag cmsTagTemp = cmsTagMapper.getTagByName(cmsTag.getName());
		if (cmsTagTemp != null) {
			cmsTag.setId(cmsTagTemp.getId());
		} else {
			cmsTagMapper.insert(cmsTag);
		}
	}


	@Override
	public List<CmsTag> queryCmsTagList(Integer channelId, int start, int count) {
		return cmsTagMapper.queryCmsTagList(channelId, start, count);
	}

	@Override
	public List<CmsTag> queryTagsByName(String name, int start, int count) {
		return cmsTagMapper.queryTagsByName(name, start, count);
	}


	@Override
	public List<CmsTag> queryCmsTagListByTypeAndNum(Integer type, Integer num) {
		
		return cmsTagMapper.queryCmsTagListByTypeAndNum(type,num);
	}


	@Override
	public Page queryArticlePageByCnd(SearchPageVo seach, Page p) {
		Integer totalCount = cmsTagMapper.queryArticlePageByCndCount(seach);
		p.setTotalCount(totalCount);

		List<CmsArticle> list = cmsTagMapper.queryArticlePageByCndList(seach, p);
		p.setResult(list);
		return p;
	}


	@Override
	public List<CmsArticle> findHotArticlesByTagLimit(Integer id, int i, int j) {
		return cmsTagMapper.queryHotArticlesByTagLimit(id,i,j);
	}


	@Override
	public CmsTag getCmsTagById(Integer id) {
		return cmsTagMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<CmsTag> queryCmsTagListByParentChannelId(int moneymanagement, int i, int j) {
		return cmsTagMapper.queryCmsTagListByParentChannelId(moneymanagement, i, j);
	}


	@Override
	public CmsTag getCmsTagByParam(String param) {
		
		return cmsTagMapper.queryCmsTagByParam(param);
	}

}
