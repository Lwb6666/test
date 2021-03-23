package com.dxjr.portal.stockright.mapper;

import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.stockright.entity.StockEntrust;
import com.dxjr.portal.stockright.vo.StockEntrustCnd;

public interface StockEntrustMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockEntrust record);

    int insertSelective(StockEntrust record);

    StockEntrust selectByPrimaryKey(StockEntrustCnd entrustCnd);

    int updateByPrimaryKeySelective(StockEntrust record);

    int updateByPrimaryKey(StockEntrust record);
    
    List<StockEntrust> findEntrustListByUserId(StockEntrustCnd entrustCnd);
    
    /**
     * 
     * <p>
     * Description:条件获取委托查询列表<br />
     * </p>
     * @author xiaofei.li
     * @version 0.1 2016-5-11
     * @param entrustCnd
     * @return
     * Integer
     */
    Integer queryListCount(StockEntrustCnd entrustCnd);
    List<StockEntrust> queryList(StockEntrustCnd entrustCnd,Page page);
    
    /**
     * 
     * <p>
     * Description:查询锁定撮合对象列表<br />
     * </p>
     * @author xiaofei.li
     * @version 0.1 2016-5-15
     * @param entrustCnd
     * @return
     * List<StockEntrust>
     */
    List<StockEntrust> queryListForUpdate(StockEntrustCnd entrustCnd);
    /***
     * 根据id查询委托记录
     * <p>
     * Description:这里写描述<br />
     * </p>
     * @author xinwugang
     * @version 0.1 2016-5-16
     * @param entrustCnd
     * @return
     * StockEntrust
     */
    StockEntrust findEntrustById(Integer id);
    
    Integer countSellerEntrust();
    
    
    
    Integer queryUserEntrust(StockEntrustCnd entrustCnd);
     
}