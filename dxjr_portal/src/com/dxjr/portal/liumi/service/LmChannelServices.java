package com.dxjr.portal.liumi.service;

import com.dxjr.portal.liumi.vo.BaseRequest;
import com.dxjr.portal.liumi.vo.PlaceOrderRequest;

public interface LmChannelServices {

	/**
	 * liumi渠道获取Token接口
	 * @author jingbinbin
	 * @version 0.1 2016年7月12日
	 * @param BaseRequest
	 * @return String
	 */
	public String getToken() throws Exception;
	
	/**
	 * liumi渠道获下订单
	 * @author jingbinbin
	 * @version 0.1 2016年7月12日
	 * @param PlaceOrderRequest
	 * @return String
	 */
	public String placeOeder(PlaceOrderRequest por) throws Exception;
	
	
	/**
	 * liumi判断联通手机号的下单资格
	 * @author jingbinbin
	 * @version 0.1 2016年7月12日
	 * @param BaseRequest
	 * @return 
	 * @throws Exception int
	 */
	public String checkLTPhone(PlaceOrderRequest por) throws Exception;
	
	
	/**
	 * liumi手机号码归属地查询
	 * @author jingbinbin
	 * @version 0.1 2016年7月12日
	 * @param BaseRequest
	 * @return 
	 * @throws Exception int
	 */
	public String phoneInfo(PlaceOrderRequest req) throws Exception;
	
}
