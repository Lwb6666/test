package com.dxjr.portal.lianlian.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:手机支付信息同步返回表单参数<br />
 * </p>
 * 
 * @title LlWapReceiveFormResponse.java
 * @package com.dxjr.portal.lianlian.vo
 * @author justin.xu
 * @version 0.1 2015年3月28日
 */
public class LlWapReceiveFormResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 返回数据参数 */
	private String res_data;

	public String getRes_data() {
		return res_data;
	}

	public void setRes_data(String res_data) {
		this.res_data = res_data;
	}

}
