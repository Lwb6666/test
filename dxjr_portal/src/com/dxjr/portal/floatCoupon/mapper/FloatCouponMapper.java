package com.dxjr.portal.floatCoupon.mapper;

import com.dxjr.portal.floatCoupon.entity.FloatCoupon;

public interface FloatCouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FloatCoupon record);

    int insertSelective(FloatCoupon record);

    FloatCoupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FloatCoupon record);

    int updateByPrimaryKey(FloatCoupon record);
}