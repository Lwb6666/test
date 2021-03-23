package com.dxjr.portal.lianlian.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:支付基本返回数据<br />
 * </p>
 * 
 * @title LlBaseResponse.java
 * @package com.dxjr.portal.lianlian.vo
 * @author justin.xu
 * @version 0.1 2015年3月26日
 */
public class LlBaseResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 签名 RSA 加密签名，见安全签名机制
	 */
	private String sign;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
