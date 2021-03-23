package com.dxjr.wx.pay.util;

import com.dxjr.utils.PropertiesUtil;

/**
 * @author WangQianJin
 * @title 网银在线常量内容
 * @date 2016年6月4日
 */
public class OnlineContext {

	//网银在线请求URL
	public static String url = PropertiesUtil.getValue("online_url");
	
	public static String des = PropertiesUtil.getValue("online_des");
	
	public static String md5 = PropertiesUtil.getValue("online_md5");
	
	public static String charset = "UTF-8";
	
	public static String version = "1.0.0";
	
	//京东终端号
	public static String JINGDONG_MERCHANT = PropertiesUtil.getValue("merchant");
	
	public static String terminal = "00000001";

	//网银在线异步回调地址
	public static String notice_url = PropertiesUtil.getValue("notice_url");
	
	public static String ONLINE_PAY_CHINABANK_RETURN_CODE_SUCCESS = "0000";//返回码-成功
	public static String ONLINE_PAY_CHINABANK_STATUS_SUCCESS = "0";//状态-成功
	public static String ONLINE_PAY_CHINABANK_STATUS_MONEYBACK = "3";//状态-退款
	public static String ONLINE_PAY_CHINABANK_STATUS_PARTBACK = "4";//状态-部分退款
	public static String ONLINE_PAY_CHINABANK_STATUS_DEALING = "6";//状态-处理中
	public static String ONLINE_PAY_CHINABANK_STATUS_FAILED = "7";//状态-失败
	
	public static String ONLINE_PAY_CHINABANK_TRADE_V = "V";  //网银支付签约
	public static String ONLINE_PAY_CHINABANK_TRADE_S = "S";  //网银支付消费
	public static String ONLINE_PAY_CHINABANK_TRADE_Q = "Q";  //网银支付查询
	
	public static String ONLINE_PAY_CHINABANK_CARDTYPE_D = "D";  //借记卡：D
	public static String ONLINE_PAY_CHINABANK_IDTYPE_I = "I";  //I表示身份证
	public static String ONLINE_PAY_CHINABANK_CURRENCY = "CNY";  //CNY表示人民币
}
