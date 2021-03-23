package com.dxjr.portal.fix.vo;

import java.math.BigDecimal;

/**
 * 
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title FixBorrowStaticVo.java
 * @package com.dxjr.portal.fix.vo 
 * @author 陈建国
 * @version 0.1 2015年6月4日
 */
public class FixBorrowStaticVo {

	 
	
	/**
	 * 已赚
	 */
	private BigDecimal repayYesAccountYes;
	
	/**
	 * 本金
	 */
	private BigDecimal capital;
 
	
	/**
	 * 预期
	 */
	private BigDecimal repayYesAccountNo;
	

	/**
	 * 加入中人次
	 */
	private Integer jrz;
	

	/**
	 * 收益中人次
	 */
	private Integer syz; 
	
	
	/**
	 * 退出中
	 */
	private Integer ytc;


	public BigDecimal getRepayYesAccountYes() {
		return repayYesAccountYes;
	}


	public void setRepayYesAccountYes(BigDecimal repayYesAccountYes) {
		this.repayYesAccountYes = repayYesAccountYes;
	}


	public BigDecimal getCapital() {
		return capital;
	}


	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}


	public BigDecimal getRepayYesAccountNo() {
		return repayYesAccountNo;
	}


	public void setRepayYesAccountNo(BigDecimal repayYesAccountNo) {
		this.repayYesAccountNo = repayYesAccountNo;
	}


	public Integer getJrz() {
		return jrz;
	}


	public void setJrz(Integer jrz) {
		this.jrz = jrz;
	}


	public Integer getSyz() {
		return syz;
	}


	public void setSyz(Integer syz) {
		this.syz = syz;
	}


	public Integer getYtc() {
		return ytc;
	}


	public void setYtc(Integer ytc) {
		this.ytc = ytc;
	} 
	
	 
}
