package com.dxjr.portal.stockright.mapper;

import com.dxjr.portal.stockright.entity.ShareholderRosterlog;

public interface ShareholderRosterlogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShareholderRosterlog record);

    int insertSelective(ShareholderRosterlog record);

    ShareholderRosterlog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShareholderRosterlog record);

    int updateByPrimaryKey(ShareholderRosterlog record);
}