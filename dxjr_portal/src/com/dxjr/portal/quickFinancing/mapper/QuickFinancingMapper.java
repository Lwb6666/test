package com.dxjr.portal.quickFinancing.mapper;

import com.dxjr.portal.quickFinancing.entity.QuickFinancing;

public interface QuickFinancingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QuickFinancing record);

    int insertSelective(QuickFinancing record);

    QuickFinancing selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuickFinancing record);

     int updateByPrimaryKey(QuickFinancing record);
}