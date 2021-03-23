package com.dxjr.wx.common;

/**
 * 基本常量
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title Constants.java
 * @package com.dxjr.bbs.base
 * @author ZHUCHEN
 * @version 0.1 2014年10月18日
 */
public class WxConstants {
	public static String default_enc = "UTF-8";
	/***************************************** 开发id和密钥 *************************************************/
	public static String AppId = WxBase.AppId;
	public static String AppSecret = WxBase.AppSecret;
	/***************************************** 菜单地址 *************************************************/
	/******************************************** WX主url *******************************************/
	public static String PROJECT_URL = WxBase.PROJECT_URL;// WX测试环境外网地址
	public static String AUTO_PUSH_MESSAGE_URL = PROJECT_URL + "/entry/pushMessage/autoPushMessage.html";
	public static String CLEAR_WITH_START_UP_URL = PROJECT_URL + "/entry/wxUserInfo/updateUser.html";
	public static String TRANSFERRED_MENU_URL = PROJECT_URL + "/entry/transferred.html";
	public static String CHECK_WXID_URL = PROJECT_URL + "/entry/wxUserInfo/checkWxid";

	/***************************************** 事件类型 *************************************************/
	public static String REQ_MESSAGE_TYPE_TEXT = "text";
	public static String REQ_MESSAGE_TYPE_EVENT = "event";
	public static String EVENT_TYPE_SUBSCRIBE = "subscribe";
	public static String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	public static String EVENT_TYPE_LOCATION = "LOCATION";
	public static String EVENT_TYPE_CLICK = "CLICK";
	public static String EVENT_TYPE_VIEW = "view";
	public static String RESP_MESSAGE_TYPE_NEWS = "news";

	/***************************************** 主动推送地址 *************************************************/
	public static String PUSH_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	/***************************************** oauth2.0地址 *************************************************/
	public static String OAUTO2_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=0#wechat_redirect";
	public static String ONLY_OPENID = "snsapi_base";
	public static String MULTI_OPENID = "snsapi_userinfo";
	public static String CODE_ACCESSTOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	/*********************************************** 微信推送消息模板id **********************************************************/
	public static String PUSH_BIND_TEMPLET = WxBase.PUSH_BIND_TEMPLET;
	/*********************************************** 微信返回结果码 *********************************************************/
	public static Integer WX_BUST = -1;
	public static Integer WX_SUCCESS = 0;
	public static Integer ERROR_OPENID = 40013;
	public static Integer NO_TOKEN = 41003;
	public static Integer TOKEN_OUT = 41006;
	public static Integer REQUIRE_SUBSCRIBE = 43004;

	/********************************************* 微信菜单click事件key ****************************************************/
	/** 资金详情 */
	// public static String MONEYDETAIL = "moneydetail";
	public static String MONEYDETAIL = "1";
	/** 收益详情 */
	// public static String COLLECTION_DETAIL = "collectiondetail";
	public static String COLLECTION_DETAIL = "2";
	/** 投标详情 */
	// public static String Tender_DETAIL = "tubiaodetail";
	public static String Tender_DETAIL = "3";
	/** 借款详情 */
	// public static String NETVALUE_DETAIL = "netvalueinfo";
	public static String NETVALUE_DETAIL = "4";
	/** 更多查询 */
	public static String MORESEARCH = "moresearch";
	/** 微博 */
	public static String WEIBO = "weibo";
	/** 电话 */
	public static String TEL = "tel";
	/** 在线客服 */
	public static String ONLINE_KEFU = "onlinekefu";
	/** 在线客服回复的type */
	public static String ONLINE_KEFU_TYPE = "transfer_customer_service";
	/** 地图api地址 */
	public static String SOSOMAP_URL = "http://apis.map.qq.com/ws/geocoder/v1/?location=LOCATION&key=KEY&get_poi=0";
	/********************************************* 微信关键字查询 *************************************************/
	/** 期权详情 */
	// public static String OPTIONS_KEY = "2";
	public static String OPTIONS_KEY = null;
	/** 加权待收详情 */
	// public static String COLLECTWEIGHT_KEY = "1";
	public static String COLLECTWEIGHT_KEY = null;
	/** 最少投资额查询 */
	// public static String LEASTINVEST_KEY = "3";
	public static String LEASTINVEST_KEY = null;
	/** 推广人数查询 */
	// public static String PROMOT_KEY = "4";
	public static String PROMOT_KEY = "5";
	/** vip查询 */
	// public static String VIP_KEY = "5";
	// public static String VIP_KEY = "6";
	/** 解除绑定 */
	public static String UNBIND_KEY = "6";
	// public static String UNBIND_KEY = "7";
	/********************************************* 微信主动推送颜色 *************************************************/
	public static String YELLOW_COLOR = "#EF6D0E";
	/********************************************* 微信更多查询 *************************************************/
	public static String ONE = "请回复序号查询更多内容\n【2】收益详情\n【3】投标详情\n【4】借款详情\n【5】推广人数查询\n【6】VIP有效期查询\n【7】解除绑定";
	public static String TWO = "请回复序号查询更多内容\n【1】资金详情\n【3】投标详情\n【4】借款详情\n【5】推广人数查询\n【6】VIP有效期查询\n【7】解除绑定";
	public static String THREE = "请回复序号查询更多内容\n【1】资金详情\n【2】收益详情\n【4】借款详情\n【5】推广人数查询\n【6】VIP有效期查询\n【7】解除绑定";
	public static String FOUR = "请回复序号查询更多内容\n【1】资金详情\n【2】收益详情\n【3】投标详情\n【5】推广人数查询\n【6】VIP有效期查询\n【7】解除绑定";
	public static String FIVE = "请回复序号查询更多内容\n【1】资金详情\n【2】收益详情\n【3】投标详情\n【4】借款详情\n【6】VIP有效期查询\n【7】解除绑定";
	public static String SIX = "请回复序号查询更多内容\n【1】资金详情\n【2】收益详情\n【3】投标详情\n【4】借款详情\n【5】推广人数查询\n【7】解除绑定";

}
