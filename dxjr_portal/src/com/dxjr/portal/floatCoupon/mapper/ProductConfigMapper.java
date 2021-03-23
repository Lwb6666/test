package com.dxjr.portal.floatCoupon.mapper;

import com.dxjr.portal.floatCoupon.entity.ProductConfig;

public interface ProductConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductConfig record);

    int insertSelective(ProductConfig record);

    ProductConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductConfig record);

    int updateByPrimaryKey(ProductConfig record);
    
    /**
     * 
     * <p>
     * Description:查询有效的定期宝加息券<br />
     * </p>
     * @author Shijin.Yang
     * @version 0.1 2016年8月25日
     * @param lockLimit
     * @return
     * ProductConfig
     */
    public ProductConfig selectProductConfigForFix(Integer lockLimit);
}