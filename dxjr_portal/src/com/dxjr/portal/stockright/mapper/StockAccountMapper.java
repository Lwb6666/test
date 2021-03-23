package com.dxjr.portal.stockright.mapper;

import java.math.BigDecimal;

import com.dxjr.portal.stockright.entity.StockAccount;
import com.dxjr.portal.stockright.vo.StockAccountRequest;
import com.dxjr.portal.stockright.vo.StockUserInfoVo;

public interface StockAccountMapper {
    int deleteByPrimaryKey(Integer id);

    Integer insert(StockAccount record);

    int insertSelective(StockAccount record);

    StockAccount selectByPrimaryKey(StockAccountRequest request);

    int updateByPrimaryKeySelective(StockAccount record);

    int updateByPrimaryKey(StockAccount record);
    
    StockUserInfoVo queryUserInfoById(StockAccountRequest request);
    
    BigDecimal querySanBiao(StockAccountRequest request);
    BigDecimal queryDingQiBao(StockAccountRequest request);
    BigDecimal queryCunGuan(StockAccountRequest request);
    
    

}