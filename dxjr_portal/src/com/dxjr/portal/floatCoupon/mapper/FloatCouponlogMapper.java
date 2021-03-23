package com.dxjr.portal.floatCoupon.mapper;

import com.dxjr.portal.floatCoupon.entity.FloatCouponlog;

public interface FloatCouponlogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FloatCouponlog record);

    int insertSelective(FloatCouponlog record);

    FloatCouponlog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FloatCouponlog record);

    int updateByPrimaryKey(FloatCouponlog record);
}