package com.dxjr.wx.pay.vo;

/**
 * <p>
 * Description:连连支付异步回调返回信息<br />
 * </p>
 * 
 * @title LianlianAutoReceiveBean.java
 * @package com.dxjr.wx.pay.vo
 * @author justin.xu
 * @version 0.1 2015年4月10日
 */
public class LianlianAutoReceiveBean {
	/** 异步回调返回的字符串 */
	private String reqStr;

	public String getReqStr() {
		return reqStr;
	}

	public void setReqStr(String reqStr) {
		this.reqStr = reqStr;
	}

}
