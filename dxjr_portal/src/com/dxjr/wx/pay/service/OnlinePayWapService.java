package com.dxjr.wx.pay.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dxjr.portal.account.vo.ChinabankReceiveForm;
import com.dxjr.portal.account.vo.TopupMoneyVo;
import com.dxjr.wx.pay.vo.OnlineCardTrade;


/**
 * @author WangQianJin
 * @title 网银在线手机支付服务接口
 * @date 2016年6月1日
 */
public interface OnlinePayWapService {
	
	/**
	 * 签约交易
	 * @author WangQianJin
	 * @title @param cardTrade
	 * @title @return
	 * @date 2016年6月1日
	 */
	public Map<String, String> signTransaction(OnlineCardTrade cardTrade);
	
	/**
	 * 支付交易
	 * @author WangQianJin
	 * @title @param cardTrade
	 * @title @return
	 * @date 2016年6月1日
	 */
	public Map<String, String> paymentTransaction(OnlineCardTrade cardTrade);
	
	/**
	 * 查询交易
	 * @author WangQianJin
	 * @title @param cardTrade
	 * @title @return
	 * @date 2016年6月1日
	 */
	public Map<String, String> queryTransaction(OnlineCardTrade cardTrade);
	
	/**
	 * 根据异步返回结果进行解析
	 * @author WangQianJin
	 * @title @param cardTrade
	 * @title @return
	 * @date 2016年6月1日
	 */
	public Map<String, String> getAsynReturnResult(String respStr);
	
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
	 * 接收支付信息，更新用户帐号和充值状态
	 * @author WangQianJin
	 * @title @param chinabankReceiveForm
	 * @title @param request
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年6月1日
	 */
	public String saveAutoReceive(ChinabankReceiveForm chinabankReceiveForm, HttpServletRequest request) throws Exception;
}
