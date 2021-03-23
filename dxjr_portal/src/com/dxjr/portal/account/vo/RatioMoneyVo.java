package com.dxjr.portal.account.vo;

import java.math.BigDecimal;

/**
 * <p>
 * Description:会员等级比率与利息<br />
 * </p>
 * 
 * @title RatioMoneyVo.java
 * @package com.dxjr.portal.account.vo
 * @author justin.xu
 * @version 0.1 2014年5月16日
 */
public class RatioMoneyVo {
	/** 费率 */
	private String ratio;
	/** 待收利息 */
	private BigDecimal interest;

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

}
