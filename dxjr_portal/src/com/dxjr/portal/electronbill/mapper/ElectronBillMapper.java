package com.dxjr.portal.electronbill.mapper;

import java.util.Map;

import com.dxjr.portal.electronbill.entity.ElectronBill;

public interface ElectronBillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElectronBill record);

    int insertSelective(ElectronBill record);

    ElectronBill selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElectronBill record);

    int updateByPrimaryKey(ElectronBill record);
    
    ElectronBill selectMyBillByYearMonth(Map<String,Object> map);
}