package com.dxjr.portal.stockright.mapper;

import com.dxjr.portal.stockright.entity.StockEntrustlog;

public interface StockEntrustlogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockEntrustlog record);

    int insertSelective(StockEntrustlog record);

    StockEntrustlog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockEntrustlog record);

    int updateByPrimaryKey(StockEntrustlog record);
}