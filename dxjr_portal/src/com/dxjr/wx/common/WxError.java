package com.dxjr.wx.common;

/**
 * 失败异常类
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title WxError.java
 * @package com.dxjr.wx.common
 * @author Wang Bo
 * @version 0.1 2015年3月12日
 */
public class WxError {
	/** 错误码 */
	private Integer errcode;
	/** 错误消息原因 */
	private String errmsg;
	private String msgid;
	private String newToken;

	public String getNewToken() {
		return newToken;
	}

	public void setNewToken(String newToken) {
		this.newToken = newToken;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
