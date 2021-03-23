package com.dxjr.portal.account.mapper;

import java.util.List;
import java.util.Map;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.AccountOperateVO;

/**
 * <p>
 * Description:账户资金记录查询<br />
 * </p>
 * 
 * @title AccOperatingRecordMapper.java
 * @package com.dxjr.portal.account.mapper
 * @author 胡建盼
 * @version 0.1 2014年8月5日
 */
public interface AccountOperateRecordMapper {
	public Integer countAccOpertRecord(Map<String, Object> params);
	public List<AccountOperateVO> queryAccOpertRecordList(Map<String, Object> params,Page page);
	public List<AccountOperateVO> queryOneMonthAccOpertRecordList(
			Map<String, Object> params);
	/**
	 * 
	 * <p>
	 * Description:根据accountId查询操作日志记录<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年9月12日
	 * @param accountId
	 * void
	 */
	public AccountOperateVO searchPaymentDetail(Integer accountId);
}
