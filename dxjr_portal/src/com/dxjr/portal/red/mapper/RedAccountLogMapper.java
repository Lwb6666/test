package com.dxjr.portal.red.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Update;

import com.dxjr.portal.red.entity.RedAccountLog;

public interface RedAccountLogMapper {

	int add(RedAccountLog redAccountLog) throws Exception;

	int addByRed(RedAccountLog redAccountLog) throws Exception;

	@Update("update t_red_envelop_account_log set BIZ_ID=#{bizId} where id=#{redLogId}")
	@ResultType(Integer.class)
	int updateBizId(@Param("bizId") int bizId, @Param("redLogId") int redLogId);

}
