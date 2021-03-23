package com.dxjr.portal.account.service;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.CashRecordCnd;
import com.dxjr.portal.account.vo.CashRecordVo;
import com.dxjr.portal.account.vo.TakeCashMoneyVo;
import com.dxjr.portal.member.vo.MemberVo;

/**
 * <p>
 * Description:提现记录业务类<br />
 * </p>
 * 
 * @title CashRecordService.java
 * @package com.dxjr.portal.account.service
 * @author justin.xu
 * @version 0.1 2014年6月18日
 */
public interface CashRecordService {
	/**
	 * <p>
	 * Description:根据条件查询提现记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月18日
	 * @param cashRecordCnd
	 * @return
	 * @throws Exception
	 *             CashRecordVo
	 */
	public CashRecordVo queryCashRecordVoByCnd(CashRecordCnd cashRecordCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据条件分页查询提现记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月18日
	 * @param cashRecordCnd
	 * @param page
	 * @return
	 * @throws Exception
	 *             Page
	 */
	public Page queryPageListByCnd(CashRecordCnd cashRecordCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:执行取消提现操作<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月18日
	 * @param cashRecordCnd
	 * @return String
	 */
	public String saveCancelCash(CashRecordCnd cashRecordCnd, HttpServletRequest request) throws Exception;

	/**
	 * <p>
	 * Description:查询用户的合理提现额度<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月18日
	 * @param memberId
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryCanTakeMoneyTotalByMemberId(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:保存提现申请 <br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月19日
	 * @param takeCashMoneyVo
	 * @param memberVo
	 * @param request
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String saveTakeCash(TakeCashMoneyVo takeCashMoneyVo, MemberVo memberVo, HttpServletRequest request) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取可提金额<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年10月28日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal getMaxDrawMoney(Integer userId) throws Exception;

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
	 * Description:查询某月内已免费的提现次数<br />
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
	public Integer getCashedCount(Integer userId, Date date) throws Exception;
}
