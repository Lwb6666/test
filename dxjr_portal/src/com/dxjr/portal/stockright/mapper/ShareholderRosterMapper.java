package com.dxjr.portal.stockright.mapper;

import com.dxjr.portal.stockright.entity.ShareholderRoster;

public interface ShareholderRosterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShareholderRoster record);

    int insertSelective(ShareholderRoster record);

    ShareholderRoster selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShareholderRoster record);

    int updateByPrimaryKey(ShareholderRoster record);
    
    ShareholderRoster selectShareholder(ShareholderRoster record);
}