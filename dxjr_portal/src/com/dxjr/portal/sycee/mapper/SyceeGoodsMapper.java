package com.dxjr.portal.sycee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.dxjr.portal.sycee.entity.SyceeGoods;

public interface SyceeGoodsMapper {

	/**
	 * 根据属性查询商品
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年10月23日
	 * @param pname
	 * @param pvalue
	 * @return List<SyceeGoods>
	 */
	List<SyceeGoods> getByProperty(@Param("pname") String pname, @Param("pvalue") int pvalue);

	/**
	 * 查询签到帖
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年10月23日
	 * @return int
	 */
	@Select("select ID from rocky_bbs_items where COLUMNID = 3 and `STATUS` = 0 and ISDELETE = 0 and ISSCREEN = 0 and CREATETIME >= curdate() and CREATETIME <= now()")
	@ResultType(Integer.class)
	Integer getSignItem();

	/**
	 * 根据ID查询商品
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年10月24日
	 * @param id
	 * @return SyceeGoods
	 */
	SyceeGoods getById(@Param("id") int id);

	/**
	 * 获取打折标记：1打折 0不打折
	 * 
	 * @return
	 */
	@Select("SELECT `NAME`<=CURDATE() AND CURDATE()<`VALUE` from rocky_configuration where TYPE=1398 and `STATUS`=0")
	@ResultType(Integer.class)
	Integer getDiscountFlag();

	@Select("SELECT COUNT(1) FROM t_sycee_exchange WHERE sycee_goods_id=#{goodsId}")
	@ResultType(Integer.class)
	Integer getExchangeNum(@Param("goodsId") int goodsId);

	@Select("SELECT COUNT(1) FROM t_sycee_exchange WHERE sycee_goods_id in(SELECT id FROM t_sycee_goods "
			+ "WHERE first_class=#{firstClass} AND second_class=#{secondClass}) AND user_id=#{userId}")
	@ResultType(Integer.class)
	Integer getMyExchangeNum(@Param("firstClass") int firstClass, @Param("secondClass") int secondClass, @Param("userId") int userId);

}
