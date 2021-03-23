package com.dxjr.portal.account.service;

import java.math.BigDecimal;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.RechargeRecordCnd;
import com.dxjr.portal.account.vo.RechargeRecordVo;

/**
 * <p>
 * Description:充值记录业务类<br />
 * </p>
 * 
 * @title ShengpayService.java
 * @package com.dxjr.portal.account.service
 * @author justin.xu
 * @version 0.1 2014年5月22日
 */
public interface RechargeRecordService {
	/**
	 * <p>
	 * Description:根据条件查询充值记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月23日
	 * @param rechargeRecordCnd
	 * @return
	 * @throws Exception
	 *             RechargeRecordVo
	 */
	public RechargeRecordVo queryRechargeRecordVoByCnd(RechargeRecordCnd rechargeRecordCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据条件分页查询充值记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月4日
	 * @param rechargeRecordCnd
	 * @param page
	 * @return
	 * @throws Exception
	 *             Page
	 */
	public Page queryPageListByCnd(RechargeRecordCnd rechargeRecordCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:查询某用户大于起始日期（秒数）的充值总和，实际比如统计某用户15内充值总额.<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月19日
	 * @param memberId
	 * @param datetime
	 *            起始日期 秒数
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryRechargeTotalByMemberIdAndDay(Integer memberId, String datetime) throws Exception;

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
