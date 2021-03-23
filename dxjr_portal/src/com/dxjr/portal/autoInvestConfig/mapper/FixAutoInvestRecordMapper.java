package com.dxjr.portal.autoInvestConfig.mapper;

import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.autoInvestConfig.entity.FixAutoInvestRecord;

public interface FixAutoInvestRecordMapper {
   
    int insert(FixAutoInvestRecord record);
    
    public List<FixAutoInvestRecord> queryFixAutoInvestRecordListForPage(Integer userId, Page page) throws Exception;
    
    public int queryFixAutoInvestRecordCount(Integer userId) throws Exception;
}