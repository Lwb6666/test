package com.dxjr.portal.report.service;

import java.util.List;

import com.dxjr.portal.account.vo.CashRecordCnd;
import com.dxjr.portal.account.vo.CashRecordVo;
import com.dxjr.portal.account.vo.RechargeRecordCnd;
import com.dxjr.portal.account.vo.RechargeRecordVo;

/**
 * <p>
 * Description:当前用户的提现记录导出excel Service<br />
 * </p>
 * 
 * @title CashReportService.java
 * @package com.dxjr.portal.report.service
 * @author justin.xu
 * @version 0.1 2014年6月10日
 */
public interface CashReportService {

	/**
	 * <p>
	 * Description:根据条件查询对象充值集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月23日
	 * @param rechargeRecordCnd
	 * @return
	 * @throws Exception
	 *             List<RechargeRecordVo>
	 */
	public List<RechargeRecordVo> queryRechargeRecordList(
			RechargeRecordCnd rechargeRecordCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询提现记录集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月18日
	 * @param cashRecordCnd
	 * @return
	 * @throws Exception
	 *             List<CashRecordVo>
	 */
	public List<CashRecordVo> queryCashRecordList(CashRecordCnd cashRecordCnd)
			throws Exception;

}
