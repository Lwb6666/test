package com.dxjr.portal.fix.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.dxjr.base.entity.FixTenderReal;
import com.dxjr.common.page.Page;
import com.dxjr.portal.fix.vo.FixTenderRealCnd;
import com.dxjr.portal.fix.vo.FixTenderRealVo;

/**
 * <p>
 * Description:定期宝最终认购记录数据库接口类<br />
 * </p>
 * 
 * @title FixTenderRealMapper.java
 * @package com.dxjr.portal.fix1.mapper
 * @author caodefeng
 * @version 0.1 2015年5月18日
 */
public interface FixTenderRealMapper {
	/**
	 * 添加最终认购记录
	 * 
	 * @param fixTenderReal
	 */
	public Integer insertFixTenderReal(FixTenderReal fixTenderReal) throws Exception;

	/**
	 * 更新最终认购记录状态
	 * @param id
	 * @param status
	 * @return
	 */
	int updateStatus(@Param("id") int id, @Param("status") int status);
	/**
	 * 统计定期宝认购总次数
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryTotalSYCounts(FixTenderRealCnd fixTendeRealCnd);

	/**
	 * 统计定期宝认购总次数
	 * 
	 * @param id
	 * @return
	 */
	public List<FixTenderRealVo> queryTotalSYByPage(FixTenderRealCnd fixTendeRealCnd, Page page);

	/**
	 * 统计定期宝认购总次数
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryTotalTCCounts(FixTenderRealCnd fixTendeRealCnd);


	/**
	 * 根据ID查询最终认购记录
	 * @param id
	 * @return
	 */
	FixTenderRealVo getFixTenderRealById(Integer id);
	
	/**
	 * 
	 * <p>
	 * Description:根据ID查询最终认购记录并锁定<br />
	 * </p>
	 * @author Shijin.Yang
	 * @version 0.1 2016年7月17日
	 * @param id
	 * @return
	 * FixTenderRealVo
	 */
	public FixTenderRealVo getFixTenderRealByIdForUpdate(Integer id);

	/**
	 * 根据最终认购记录id查询正在投标数量
	 * @param id
	 * @return
	 */
	int countBorrowById(Integer id);
	/**
	 * 统计定期宝认购总次数
	 * 
	 * @param id
	 * @return
	 */
	public List<FixTenderRealVo> queryTotalTCByPage(FixTenderRealCnd fixTendeRealCnd, Page page);
	
	/**
	 * 统计定期宝退出中记录列表
	 * 
	 * @param id
	 * @return
	 */
	public List<FixTenderRealVo> queryTotalTCZByPage(FixTenderRealCnd fixTendeRealCnd, Page page);
	
	/**
	 * 统计定期宝退出中记录数量
	 * 
	 * @param id
	 * @return
	 */
	public Integer queryTotalTCZCounts(FixTenderRealCnd fixTendeRealCnd);

	/**
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author 陈建国
	 * @version 0.1 2015年5月23日
	 * @param fixTenderDetailCnd
	 * @return FixTenderDetailVo
	 */

	public List<FixTenderRealVo> getFixTenderRealstaticByBorrowId(FixTenderRealCnd fixTenderRealCnd);

	/**
	 * 退出信息统计
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author 陈建国
	 * @version 0.1 2015年5月23日
	 * @param fixTenderRealCnd
	 * @return List<FixTenderRealVo>
	 */

	public List<FixTenderRealVo> getTcstaticByBorrowId(FixTenderRealCnd fixTenderRealCnd);

	/**
	 * 查询最大排序号
	 * 
	 * @return
	 */
	public Integer queryMaxOrderNum();

	/**
	 * 根据定期宝收益中的预期收益
	 * 
	 * @param
	 * @return
	 */
	public BigDecimal getYqsyBySzy(FixTenderRealCnd fixTenderRealCnd) throws Exception;

	/**
	 * 根据用户ID获取有效认购总额
	 * 
	 * @author WangQianJin
	 * @title @param firstBorrowId
	 * @title @param userId
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年8月25日
	 */
	public Integer findAccountTotalByUserIdAndFixBorrowId(@Param("fixBorrowId") Integer fixBorrowId, @Param("userId") Integer userId) throws Exception;

	/**
	 * 查询用户定期宝当月投宝且满宝的总额
	 * 
	 * @param userId
	 * @return
	 */
	@Select("SELECT IFNULL(SUM(ACCOUNT),0) FROM t_fix_tender_real WHERE FIX_BORROW_ID in("
			+ "SELECT ID FROM t_fix_borrow WHERE SUCCESS_TIME LIKE CONCAT(LEFT(CURDATE(),7),'%') AND `STATUS`>=4)AND USER_ID=#{userId}")
	@ResultType(Integer.class)
	public Integer getMonthTender(@Param("userId") int userId);
	/**
	 * 查询用户定期宝使用红包金额
	 * 
	 * @param
	 * @return
	 */
	public BigDecimal getRedMoneyByfixId(FixTenderRealCnd fixTenderRealCnd) throws Exception;

    /**
     * 根据用户id查询投宝次数
     *
     * @param
     * @return
     */
	int getFixTenderSuccessCountByUserId(@Param("userId")int userId);
	
	/**
	 * 
	 * <p>
	 * Description:根据定期宝id查询最终记录<br />
	 * </p>
	 * @author Shijin.Yang
	 * @version 0.1 2016年8月25日
	 * @param fixTenderRealCnd
	 * @return
	 * List<FixTenderReal>
	 */
	public List<FixTenderReal> getFixTenderRealByFixBorrowId(FixTenderRealCnd fixTenderRealCnd);
	
	/**
	 * 
	 * <p>
	 * Description:查询该定期宝在加息活动期间的加入记录数量<br />
	 * </p>
	 * @author Shijin.Yang
	 * @version 0.1 2016年8月25日
	 * @param fixBorrowId
	 * @return
	 * Integer
	 */
	public Integer selectCountForFloatByFixBorrowId(Integer fixBorrowId);
	
	/**
	 * 
	 * <p>
	 * Description:根据条件查询认购记录（发放加息券）<br />
	 * </p>
	 * @author Shijin.Yang
	 * @version 0.1 2016年8月25日
	 * @param fixTenderRealCnd
	 * @return
	 * List<FixTenderRealVo>
	 */
	public List<FixTenderRealVo> getFixTenderRealByCnd(FixTenderRealCnd fixTenderRealCnd);
	
	/**
	 * 
	 * <p>
	 * Description:根据条件查询认购总额（发放加息券）<br />
	 * </p>
	 * @author Shijin.Yang
	 * @version 0.1 2016年8月25日
	 * @param fixTenderRealCnd
	 * @return
	 * BigDecimal
	 */
	public BigDecimal getTenderAccountTotalByCnd(FixTenderRealCnd fixTenderRealCnd);
	
	/**
	 * 
	 * <p>
	 * Description:根据条件查询待发放加息的记录<br />
	 * </p>
	 * @author Shijin.Yang
	 * @version 0.1 2016年8月25日
	 * @param fixTenderRealCnd
	 * @return
	 * List<FixTenderRealVo>
	 */
	public List<FixTenderRealVo> selectFixTenderRealForFloatCouponByCnd(FixTenderRealCnd fixTenderRealCnd);
}
