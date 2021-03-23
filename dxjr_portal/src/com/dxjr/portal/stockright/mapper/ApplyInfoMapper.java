package com.dxjr.portal.stockright.mapper;

import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.stockright.entity.ApplyInfo;
import com.dxjr.portal.stockright.vo.StockApplyRequest;

public interface ApplyInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApplyInfo record);

    public void insertSelective(ApplyInfo record);

    ApplyInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApplyInfo record);

    int updateByPrimaryKey(ApplyInfo record);

    
    public Integer countApplyInfo(StockApplyRequest record);
    
	public List<ApplyInfo> queryApplyInfoList(StockApplyRequest record,Page page);

    
    Integer checkBlackList(StockApplyRequest request);

    
    public ApplyInfo querySignOutApply(int userId);

    
    public ApplyInfo findApplyList(StockApplyRequest record);

}