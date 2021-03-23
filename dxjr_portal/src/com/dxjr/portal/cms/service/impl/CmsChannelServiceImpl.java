package com.dxjr.portal.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.cms.mapper.CmsChannelMapper;
import com.dxjr.portal.cms.service.CmsChannelService;
import com.dxjr.portal.cms.vo.CmsChannel;

@Service
public class CmsChannelServiceImpl implements CmsChannelService {

	@Autowired
	CmsChannelMapper cmsChannelMapper;

	@Override
	public List<CmsChannel> queryCmsChannelList() {
		return cmsChannelMapper.queryCmsChannelList();
	}

	@Override
	public Page queryCmsChannelListForPage(int pageNo, int pageSize) {
		Page page = new Page(pageNo, pageSize);
		page.setResult(cmsChannelMapper.queryCmsChannelListForPage(page));
		page.setTotalCount(cmsChannelMapper.getCountCmsChannelListForPage());
		return page;
	}

	@Override
	public void deleteCmsChannelById(Integer cmsChannelId) {
		cmsChannelMapper.deleteByPrimaryKey(cmsChannelId);
	}

	@Override
	public void updateCmsChannel(CmsChannel cmsChannel) {
		cmsChannelMapper.updateByPrimaryKey(cmsChannel);
	}

	@Override
	public void saveCmsChannel(CmsChannel cmsChannel) {
		cmsChannelMapper.insert(cmsChannel);
	}

	@Override
	public CmsChannel getCmsChannelById(Integer cmsChannelId) {
		return cmsChannelMapper.selectByPrimaryKey(cmsChannelId);
	}

	@Override
	public List<CmsChannel> queryCmsParentChannelList() {
		return cmsChannelMapper.queryCmsParentChannelList();
	}

	@Override
	public List<CmsChannel> queryCmsChannelListByParentId(int moneymanagement) {
		return cmsChannelMapper.queryCmsChannelListByParentId(moneymanagement);
	}

	@Override
	public CmsChannel getChannelByPinyin(String pinyin) {
		return cmsChannelMapper.getChannelByPinyin(pinyin);
	}

	@Override
	public List<CmsChannel> queryShowChannels() {
		return cmsChannelMapper.queryShowChannels();
	}

}
