package com.dxjr.portal.cms.service;

import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.cms.vo.CmsChannel;

public interface CmsChannelService {

	public List<CmsChannel> queryCmsChannelList();

	public Page queryCmsChannelListForPage(int pageNo, int pageSize);

	public void deleteCmsChannelById(Integer cmsChannelId);

	public void updateCmsChannel(CmsChannel cmsChannel);

	public void saveCmsChannel(CmsChannel cmsChannel);

	public CmsChannel getCmsChannelById(Integer cmsChannelId);

	public List<CmsChannel> queryCmsParentChannelList();

	public List<CmsChannel> queryCmsChannelListByParentId(int moneymanagement);

	public CmsChannel getChannelByPinyin(String pinyin);

	public List<CmsChannel> queryShowChannels();

}
