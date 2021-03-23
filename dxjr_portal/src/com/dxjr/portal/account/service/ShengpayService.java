package com.dxjr.portal.account.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dxjr.portal.account.vo.ShengReceiveForm;
import com.dxjr.portal.account.vo.TopupMoneyVo;
import com.dxjr.portal.member.vo.MemberVo;

/**
 * <p>
 * Description:盛付通支付业务类<br />
 * </p>
 * 
 * @title ShengpayService.java
 * @package com.dxjr.portal.account.service
 * @author justin.xu
 * @version 0.1 2014年5月22日
 */
public interface ShengpayService {
	/**
	 * <p>
	 * Description:使用盛付通进行在线充值，进入银联充值页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月22日
	 * @param memberVo
	 * @param topupMoneyVo
	 * @return
	 * @throws Exception
	 *             Map<String, Object>
	 */
	public Map<String, Object> savesend(MemberVo memberVo,
			TopupMoneyVo topupMoneyVo, HttpServletRequest request)
			throws Exception;

	/**
	 * <p>
	 * Description:接收支付信息，更新用户帐号和充值状态<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月23日
	 * @param shengReceiveForm
	 * @param request
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String saveAutoReceive(ShengReceiveForm shengReceiveForm,
			HttpServletRequest request) throws Exception;
}
