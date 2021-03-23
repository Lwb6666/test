package com.dxjr.portal.account.service;

import javax.servlet.http.HttpServletRequest;

import com.dxjr.portal.account.vo.TopupMoneyVo;

/**
 * <p>
 * Description:支付宝充值业务类<br />
 * </p>
 * 
 * @title AlipayService.java
 * @package com.dxjr.portal.account.service
 * @author justin.xu
 * @version 0.1 2014年6月12日
 */
public interface AlipayService {
	/**
	 * <p>
	 * Description:保存支付宝支付信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月12日
	 * @param memberVo
	 * @param topupMoneyVo
	 * @param request
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String savepay(Integer userId, TopupMoneyVo topupMoneyVo,
			HttpServletRequest request) throws Exception;
}
