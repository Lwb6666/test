package com.dxjr.portal.sycee.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Update;

import com.dxjr.portal.sycee.entity.SyceeExchange;

public interface SyceeExchangeMapper {

	int add(SyceeExchange exchange);

	@Update("UPDATE t_sycee_exchange SET deal_status=1,deal_username='系统',deal_time=NOW(),deal_remark=#{remark} WHERE sycee_goods_id=#{goodsId} AND user_id=#{userId}")
	@ResultType(Integer.class)
	int updateDeal(@Param("remark") String remark, @Param("goodsId") int goodsId, @Param("userId") int userId);

}
