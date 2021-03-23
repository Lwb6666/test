package com.dxjr.portal.account.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.CashRecordCnd;
import com.dxjr.portal.account.vo.CashRecordVo;

/**
 * <p>
 * Description:提现记录数据访问类<br />
 * </p>
 * 
 * @title CashRecordMapper.java
 * @package com.dxjr.portal.account.mapper
 * @author justin.xu
 * @version 0.1 2014年6月18日
 */
public interface CashRecordMapper {
	/**
	 * <p>
	 * Description:根据条件查询对象集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月18日
	 * @param cashRecordCnd
	 * @return
	 * @throws Exception
	 *             List<CashRecordVo>
	 */
	public List<CashRecordVo> queryCashRecordList(CashRecordCnd cashRecordCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据条件分页查询对象集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月18日
	 * @param cashRecordCnd
	 * @param page
	 * @return
	 * @throws Exception
	 *             List<CashRecordVo>
	 */
	public List<CashRecordVo> queryCashRecordList(CashRecordCnd cashRecordCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询数量<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月18日
	 * @param cashRecordCnd
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer queryCashRecordCount(CashRecordCnd cashRecordCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据用户查询逾期未还款的净值标数量<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月19日
	 * @param memberId
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer queryRepaymentlaterCountForJZ(Integer memberId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据查询条件统计提现总额<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年6月1日
	 * @param cashRecordCnd
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryCashRecordTotalByCnd(CashRecordCnd cashRecordCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某月内的提现次数<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年6月17日
	 * @param userId
	 * @param date
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer getCashedCount(@Param("userId") Integer userId, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate)
			throws Exception;
}
