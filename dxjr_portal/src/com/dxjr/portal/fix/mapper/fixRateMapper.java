package com.dxjr.portal.fix.mapper;

import org.apache.ibatis.annotations.Param;

import com.dxjr.base.entity.FixRate;

public interface fixRateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FixRate record);

    int insertSelective(FixRate record);

    FixRate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FixRate record);

    int updateByPrimaryKey(FixRate record);
    
    /**
     * 
     * <p>
     * Description:根据定期宝类型和提前退出类型查询费率配置记录<br />
     * </p>
     * @author Shijin.Yang
     * @version 0.1 2016年7月22日
     * @param fixType
     * @param signoutType
     * @return
     * @throws Exception
     * fixRate
     */
    public FixRate selectByFixTypeAndSignoutType(@Param("fixType") Integer fixType, @Param("signoutType") Integer signoutType) throws Exception;
}