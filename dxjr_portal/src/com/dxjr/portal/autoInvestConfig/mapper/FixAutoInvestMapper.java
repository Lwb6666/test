package com.dxjr.portal.autoInvestConfig.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.dxjr.portal.autoInvestConfig.entity.FixAutoInvest;

public interface FixAutoInvestMapper {
     
    int insert(FixAutoInvest record)throws Exception;

    int updateByPrimaryKeySelective(FixAutoInvest record)throws Exception;
    
    FixAutoInvest selectByPrimaryKey(Integer id)throws Exception;
    
    FixAutoInvest selectByUserId(Integer userId)throws Exception;
    
    @Select("select count(1) from t_fix_auto_invest where user_id=#{userId} and status=1")
    @ResultType(Integer.class)
   int checkExists(@Param("userId") int userId);

}