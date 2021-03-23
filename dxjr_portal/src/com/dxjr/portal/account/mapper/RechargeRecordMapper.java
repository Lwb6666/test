package com.dxjr.portal.account.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.RechargeRecordCnd;
import com.dxjr.portal.account.vo.RechargeRecordVo;

/**
 * <p>
 * Description:充值记录数据访问类<br />
 * </p>
 * 
 * @title RechargeRecordMapper.java
 * @package com.dxjr.portal.account.mapper
 * @author justin.xu
 * @version 0.1 2014年5月23日
 */
public interface RechargeRecordMapper {
	/**
	 * <p>
	 * Description:根据条件查询对象集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月23日
	 * @param rechargeRecordCnd
	 * @return
	 * @throws Exception
	 *             List<RechargeRecordVo>
	 */
	public List<RechargeRecordVo> queryRechargeRecordList(RechargeRecordCnd rechargeRecordCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据条件分页查询对象集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月23日
	 * @param rechargeRecordCnd
	 * @return
	 * @throws Exception
	 *             List<RechargeRecordVo>
	 */
	public List<RechargeRecordVo> queryRechargeRecordList(RechargeRecordCnd rechargeRecordCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询数量<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月4日
	 * @param rechargeRecordCnd
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer queryRechargeRecordCount(RechargeRecordCnd rechargeRecordCnd) throws Exception;

	/**
	 * <p>
	 * Description:查询某用户大于起始日期的线上充值总和<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月19日
	 * @param memberId
	 * @param datetime
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryOnlineMoneyTotalByDatetime(@Param("memberId") Integer memberId, @Param("datetime") String datetime) throws Exception;

	/**
	 * <p>
	 * Description:查询某用户大于起始日期的线下充值总和<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月19日
	 * @param memberId
	 * @param datetime
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryOfflineMoneyTotalByDatetime(@Param("memberId") Integer memberId, @Param("datetime") String datetime) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据查询条件统计充值总额<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年6月1日
	 * @param rechargeRecordCnd
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryRechargeTotalByCnd(RechargeRecordCnd rechargeRecordCnd) throws Exception;

}
