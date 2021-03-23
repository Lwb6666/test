package com.dxjr.portal.cms.service;

import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.cms.vo.CmsPediaEntry;
import com.dxjr.portal.cms.vo.CmsPediaEntryCnd;

public interface CmsPediaEntryService {

	CmsPediaEntry getCmsPediaEntryById(Integer cmsPediaEntryId);

	void updateCmsPediaEntry(CmsPediaEntry cmsPediaEntry);

	void saveCmsPediaEntry(CmsPediaEntry cmsPediaEntry);

	void deleteCmsPediaEntryByIds(String[] cmsPediaEntryIds);

	Page searchCmsPediaEntryPageByCnd(CmsPediaEntryCnd cmsPediaEntryCnd, int pageNo, int pageSize);

	List<CmsPediaEntry> findPediaEntryByLimit(int i, int j);

	List<String> queryAllInitials();

	List<CmsPediaEntry> queryPediaEntrysByInitials(String initials, int i, int j);

}
