package com.dxjr.portal.lianlian.vo;

/**
 * <p>
 * Description:连连支付返回信息<br />
 * </p>
 * 
 * @title LianlianRetBean.java
 * @package com.dxjr.portal.lianlian.vo
 * @author justin.xu
 * @version 0.1 2014年10月29日
 */
public class LianlianRetBean {
	/** 返回编码 0000 代表成功 */
	private String ret_code;
	/** 返回信息 交易成功 */
	private String ret_msg;

	public String getRet_code() {
		return ret_code;
	}

	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}

	public String getRet_msg() {
		return ret_msg;
	}

	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}

}
