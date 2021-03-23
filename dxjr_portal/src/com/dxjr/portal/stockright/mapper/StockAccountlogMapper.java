package com.dxjr.portal.stockright.mapper;

import com.dxjr.portal.stockright.entity.StockAccountlog;

public interface StockAccountlogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockAccountlog record);

    int insertSelective(StockAccountlog record);

    StockAccountlog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockAccountlog record);

    int updateByPrimaryKey(StockAccountlog record);
}