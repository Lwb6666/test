package com.dxjr.portal.lianlian.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:手机支付信息提交表单请求参数<br />
 * </p>
 * 
 * @title LlWapPaymentRequest.java
 * @package com.dxjr.portal.lianlian.vo
 * @author justin.xu
 * @version 0.1 2015年3月25日
 */
public class LlWapSendFormRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 请求数据参数 */
	private String req_data;

	public String getReq_data() {
		return req_data;
	}

	public void setReq_data(String req_data) {
		this.req_data = req_data;
	}

}
