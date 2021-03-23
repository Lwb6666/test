package com.dxjr.portal.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.cms.mapper.CmsPediaEntryMapper;
import com.dxjr.portal.cms.service.CmsPediaEntryService;
import com.dxjr.portal.cms.vo.CmsPediaEntry;
import com.dxjr.portal.cms.vo.CmsPediaEntryCnd;

@Service
public class CmsPediaEntryServiceImpl implements CmsPediaEntryService {

	@Autowired
	CmsPediaEntryMapper cmsPediaEntryMapper;

	@Override
	public CmsPediaEntry getCmsPediaEntryById(Integer cmsPediaEntryId) {
		return cmsPediaEntryMapper.selectByPrimaryKey(cmsPediaEntryId);
	}

	@Override
	public void updateCmsPediaEntry(CmsPediaEntry cmsPediaEntry) {
		cmsPediaEntryMapper.updateByPrimaryKeySelective(cmsPediaEntry);
	}

	@Override
	public void saveCmsPediaEntry(CmsPediaEntry cmsPediaEntry) {
		cmsPediaEntryMapper.insert(cmsPediaEntry);
	}

	@Override
	public void deleteCmsPediaEntryByIds(String[] cmsPediaEntryIds) {
		cmsPediaEntryMapper.deleteByIds(cmsPediaEntryIds);
	}

	@Override
	public Page searchCmsPediaEntryPageByCnd(CmsPediaEntryCnd cmsPediaEntryCnd, int pageNo, int pageSize) {
		Page page = new Page(pageNo, pageSize);
		page.setResult(cmsPediaEntryMapper.queryCmsPediaEntryListForPage(cmsPediaEntryCnd, page));
		page.setTotalCount(cmsPediaEntryMapper.getCountCmsPediaEntryListForPage(cmsPediaEntryCnd));
		return page;
	}

	@Override
	public List<CmsPediaEntry> findPediaEntryByLimit(int start, int count) {
		return cmsPediaEntryMapper.queryPediaEntryByLimit(start, count);
	}

	@Override
	public List<String> queryAllInitials() {
		return cmsPediaEntryMapper.queryAllInitials();
	}

	@Override
	public List<CmsPediaEntry> queryPediaEntrysByInitials(String initials, int start, int count) {
		Page page = new Page(start, count);
		return cmsPediaEntryMapper.queryPediaEntrysByInitials(initials, page);
	}

}
