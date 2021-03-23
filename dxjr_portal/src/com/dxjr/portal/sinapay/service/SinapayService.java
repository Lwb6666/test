package com.dxjr.portal.sinapay.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dxjr.portal.account.vo.TopupMoneyVo;
import com.dxjr.portal.sinapay.protocol.SinaPayReponse;
import com.dxjr.portal.sinapay.protocol.SinaQueryRecordListReponse;

/**
 * <p>
 * Description:新浪支付service<br />
 * </p>
 * 
 * @title SinapayService.java
 * @package com.dxjr.portal.sinapay.service
 * @author justin.xu
 * @version 0.1 2014年6月10日
 */
public interface SinapayService {
	/**
	 * <p>
	 * Description:保存新浪支付充值记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月10日
	 * @param memberVo
	 * @param topupMoneyVo
	 * @param request
	 * @return
	 * @throws Exception Map<String,Object>
	 */
	public Map<String, Object> savesend(Integer userId, TopupMoneyVo topupMoneyVo, HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * <p>
	 * Description:保存新浪支付回调记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月10日
	 * @param sinaPayReponse
	 * @param request
	 * @return
	 * @throws Exception String
	 */
	public String saveAutoReceive(SinaPayReponse sinaPayReponse, HttpServletRequest request) throws Exception;

	/**
	 * <p>
	 * Description:保存支付回调信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年11月26日
	 * @param sinaPayReponse
	 * @throws Exception void
	 */
	public void saveAccountRechargeFeedback(SinaPayReponse sinaPayReponse) throws Exception;

	/**
	 * <p>
	 * Description:进行新浪支付补单<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年12月30日
	 * @param sinaQueryRecordListReponse
	 * @param ip
	 * @return
	 * @throws Exception String
	 */
	public String saveLoseOrder(SinaQueryRecordListReponse sinaQueryRecordListReponse, String ip) throws Exception;

	/**
	 * <p>
	 * Description:保存补单回调信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年12月30日
	 * @param sinaQueryRecordListReponse
	 * @throws Exception void
	 */
	public void savePackageLoseFeedBack(SinaQueryRecordListReponse sinaQueryRecordListReponse) throws Exception;
}
