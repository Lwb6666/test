package com.dxjr.portal.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dxjr.common.page.Page;
import com.dxjr.portal.cms.vo.CmsChannel;

public interface CmsChannelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CmsChannel record);

    int insertSelective(CmsChannel record);

    CmsChannel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CmsChannel record);

    int updateByPrimaryKey(CmsChannel record);

	List<CmsChannel> queryCmsChannelList();

	List<CmsChannel> queryCmsChannelListForPage(Page page);

	Integer getCountCmsChannelListForPage();

	List<CmsChannel> queryCmsParentChannelList();

	List<CmsChannel> queryCmsChannelListByParentId(@Param("parentId") int moneymanagement);

	CmsChannel getChannelByPinyin(@Param("pinyin") String pinyin);

	List<CmsChannel> queryShowChannels();

}