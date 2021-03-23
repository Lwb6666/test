package com.dxjr.portal.stockright.mapper;

import java.util.List;

import com.dxjr.portal.stockright.entity.StockApprove;
import com.dxjr.portal.stockright.vo.StockApplyRequest;

public interface StockApproveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockApprove record);

    public int insertSelective(StockApprove record);

    StockApprove selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockApprove record);

    int updateByPrimaryKey(StockApprove record);
    
    List<StockApprove> findApplyApprove(StockApplyRequest record);
}