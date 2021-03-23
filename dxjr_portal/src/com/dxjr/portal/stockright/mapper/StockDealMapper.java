package com.dxjr.portal.stockright.mapper;

import java.util.List;
import java.util.Map;

import com.dxjr.common.page.Page;
import com.dxjr.portal.stockright.entity.StockDeal;
import com.dxjr.portal.stockright.vo.StockDealRequest;

public interface StockDealMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockDeal record);

    int insertSelective(StockDeal record);

    StockDeal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockDeal record);

    int updateByPrimaryKey(StockDeal record);
    
    List<StockDeal> queryFistStockDeal();
    
    List<Map<String,Object>> summaryStockDeal(StockDealRequest request);
    
    
    Integer countDetailList(StockDealRequest dealCnd);
    List<StockDeal> fundDetailList(StockDealRequest dealCnd,Page page);
    
}