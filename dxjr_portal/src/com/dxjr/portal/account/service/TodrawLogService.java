package com.dxjr.portal.account.service;

import java.math.BigDecimal;

import com.dxjr.portal.member.vo.MemberVo;

public interface TodrawLogService {

	/**
	 * 
	 * <p>
	 * Description:保存转可提现记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param memberVo
	 * @param money
	 * @param paypassword
	 * @param addip
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String saveTodrawLog(MemberVo memberVo, String money,
			String paypassword, String addip) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据userid查询冻结中的转可提金额总额<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param userid
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public BigDecimal queryTodrawNoMoney(int userid) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:执行取消转可提操作<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param draw_id
	 * @param userid
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String cancelCash(int draw_id, int userid, String addip)
			throws Exception;;
}
