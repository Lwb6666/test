package com.dxjr.portal.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dxjr.common.page.Page;
import com.dxjr.portal.cms.vo.CmsPediaEntry;
import com.dxjr.portal.cms.vo.CmsPediaEntryCnd;

public interface CmsPediaEntryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CmsPediaEntry record);

    int insertSelective(CmsPediaEntry record);

    CmsPediaEntry selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CmsPediaEntry record);

    int updateByPrimaryKeyWithBLOBs(CmsPediaEntry record);

    int updateByPrimaryKey(CmsPediaEntry record);

	List<CmsPediaEntry> queryCmsPediaEntryListForPage(CmsPediaEntryCnd cmsPediaEntryCnd, Page page);

	int getCountCmsPediaEntryListForPage(CmsPediaEntryCnd cmsPediaEntryCnd);

	void deleteByIds(@Param("cmsPediaEntryIds") String[] cmsPediaEntryIds);

	List<CmsPediaEntry> queryPediaEntryByLimit(@Param("start") int start, @Param("count") int count);

	List<String> queryAllInitials();

	List<CmsPediaEntry> queryPediaEntrysByInitials(@Param("initials") String initials, Page page);

}