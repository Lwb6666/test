package com.dxjr.wx.advertised.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.dxjr.base.entity.BankInfo;
import com.dxjr.base.entity.SmsRecord;
import com.dxjr.wx.advertised.vo.AdvertisedVo;

/**
 * <p>
 * Description:微信端市场活动服务实现类<br />
 * </p>
 * 
 * @title AdvertisedService.java
 * @package com.dxjr.wx.advertised.service
 * @author hujianpan
 * @version 0.1 2015年4月13日
 */
public interface AdvertisedService {

	/**
	 * <p>
	 * Description:注册<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2015年4月13日
	 * @param vo
	 * @param request
	 * @param modelName
	 * @param session
	 * @return
	 * @throws Exception String
	 */
	public String regist(AdvertisedVo vo, HttpServletRequest request, String modelName, HttpSession session) throws Exception;
	
	/**
	 * <p>
	 * Description:无手机验证码的注册<br />
	 * </p>
	 * 
	 * @author tongjuxing
	 * @version 0.1 2015年8月25日
	 * @param vo
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception String
	 */
	public String registNoActiveCode(AdvertisedVo vo, HttpServletRequest request, HttpSession session) throws Exception;

	/**
	 * <p>
	 * Description:保存银行卡，与其他保存银行卡服务的区别是，不用进行交易密码认证，并且会设置默认的交易密码<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2015年4月13日
	 * @param bankInfo
	 * @return
	 * @throws Exception String
	 */
	public String saveBankcard(BankInfo bankInfo) throws Exception;

	/**
	 * <p>
	 * Description:发送短信<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2015年4月14日
	 * @param smsRecord 短信内容
	 * @param map 短信模板中参数内容设置
	 * @throws Exception void
	 */
	public void sendMobileMessage(SmsRecord smsRecord, Map<String, Object> map) throws Exception;
}
