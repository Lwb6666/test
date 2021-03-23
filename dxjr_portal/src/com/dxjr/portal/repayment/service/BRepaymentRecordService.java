package com.dxjr.portal.repayment.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.dxjr.common.page.Page;
import com.dxjr.portal.repayment.vo.BRepaymentRecordVo;
import com.dxjr.portal.repayment.vo.RepaymentRecordCnd;

/**
 * <p>
 * Description:待还记录业务类<br />
 * </p>
 * 
 * @title BRepaymentRecordService.java
 * @package com.dxjr.portal.repayment.service
 * @author justin.xu
 * @version 0.1 2014年8月13日
 */
public interface BRepaymentRecordService {
	/**
	 * <p>
	 * Description:根据当前用户分页查询待还记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月13日
	 * @param userId
	 * @param page
	 * @return
	 * @throws Exception Page
	 */
	public Page queryRepaymentsByUserId(Integer userId, Page page) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取待还列表<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年9月7日
	 * @param repaymentRecordCnd
	 * @param page
	 * @return
	 * @throws Exception Page
	 */
	public Page queryRepaymentList(RepaymentRecordCnd repaymentRecordCnd, int pageNo, int PageSize) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:待还列表记录数<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年9月7日
	 * @param repaymentRecordCnd
	 * @return
	 * @throws Exception int
	 */
	public int queryRepaymentCount(RepaymentRecordCnd repaymentRecordCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:统计还款总额、本金总额、利息总额、罚息总额等数据项<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年9月7日
	 * @param repaymentRecordCnd
	 * @return Map<String,Object>
	 */
	public Map<String, Object> repaymentRecordTongji(RepaymentRecordCnd repaymentRecordCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:单笔借款的还款总额<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月27日
	 * @param map
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal queryRepaymentAccountTotalByBorrowId(Map<String, Object> map) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:计算各项总额<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月28日
	 * @param map
	 * @param p
	 * @return
	 * @throws Exception Page
	 */
	public Page querySums(Map<String, Object> map, Page page) throws Exception;
	
	/**
	 * 根据借款标ID查询待还记录
	 * @author WangQianJin
	 * @title @param borrowId
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年8月19日
	 */
	public List<BRepaymentRecordVo> queryRepaymentByBorrowId(Integer borrowId) throws Exception;

}
