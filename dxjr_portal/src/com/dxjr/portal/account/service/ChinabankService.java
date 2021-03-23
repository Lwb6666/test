package com.dxjr.portal.account.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dxjr.portal.account.vo.ChinabankReceiveForm;
import com.dxjr.portal.account.vo.TopupMoneyVo;

/**
 * <p>
 * Description:网银在线业务类<br />
 * </p>
 * 
 * @title ChinabankService.java
 * @package com.dxjr.portal.account.service
 * @author justin.xu
 * @version 0.1 2014年6月11日
 */
public interface ChinabankService {
	/**
	 * <p>
	 * Description:使用网银在线进行在线充值，进入银联充值页面<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月11日
	 * @param memberVo
	 * @param topupMoneyVo
	 * @param request
	 * @return
	 * @throws Exception Map<String,Object>
	 */
	public Map<String, Object> savesend(Integer userId, TopupMoneyVo topupMoneyVo, HttpServletRequest request) throws Exception;

	/**
	 * <p>
	 * Description:接收支付信息，更新用户帐号和充值状态<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月23日
	 * @param chinabankReceiveForm
	 * @param request
	 * @return
	 * @throws Exception String
	 */
	public String saveAutoReceive(ChinabankReceiveForm chinabankReceiveForm, HttpServletRequest request) throws Exception;

	/**
	 * <p>
	 * Description:保存支付返回信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月23日
	 * @param chinabankReceiveForm
	 * @throws Exception void
	 */
	public void saveAccountRechargeFeedback(ChinabankReceiveForm chinabankReceiveForm) throws Exception;
}
