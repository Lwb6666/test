package com.dxjr.portal.lianlian.service;

import java.util.Map;

import com.dxjr.portal.account.vo.TopupMoneyVo;
import com.dxjr.portal.lianlian.vo.LianlianPayDataResponse;

/**
 * <p>
 * Description:连连支付service<br />
 * </p>
 * 
 * @title LianlianpayService.java
 * @package com.dxjr.portal.lianlian.service
 * @author justin.xu
 * @version 0.1 2014年10月26日
 */
public interface LianlianpayService {
	/**
	 * <p>
	 * Description:保存连连支付充值记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年10月26日
	 * @param userId
	 * @param topupMoneyVo
	 * @param ip
	 * @param realPath
	 * @return
	 * @throws Exception Map<String,Object>
	 */
	public Map<String, Object> savesend(Integer userId, TopupMoneyVo topupMoneyVo, String ip, String realPath) throws Exception;

	/**
	 * <p>
	 * Description:保存连连支付回调记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月10日
	 * @param lianlianPayDataResponse
	 * @param ip
	 * @return
	 * @throws Exception String
	 */
	public String saveAutoReceive(LianlianPayDataResponse lianlianPayDataResponse, String ip) throws Exception;

	/**
	 * <p>
	 * Description:保存支付返回信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月23日
	 * @param lianlianPayDataResponse
	 * @throws Exception void
	 */
	public void savePackageAccountRechargeFeedback(LianlianPayDataResponse lianlianPayDataResponse) throws Exception;

}
