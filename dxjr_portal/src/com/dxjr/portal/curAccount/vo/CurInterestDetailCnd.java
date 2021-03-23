package com.dxjr.portal.curAccount.vo;

import java.io.Serializable;
import java.util.Date;

import com.dxjr.portal.curAccount.entity.CurInterestDetail;

/**
 * <p>
 * Description:收益发放日志表 Cnd <br />
 * </p>
 * 
 * @title CurInterestDetailCnd.java
 * @package com.dxjr.portal.curAccount.vo
 * @author HuangJun
 * @version 0.1 2015年5月27日
 */
public class CurInterestDetailCnd extends CurInterestDetail implements Serializable {

	private static final long serialVersionUID = 2632359236814905721L;

	// 月初日期
	private Date sDate;
	// 月末日期
	private Date eDate;

	/**
	 * @return sDate : return the property sDate.
	 */
	public Date getsDate() {
		return sDate;
	}

	/**
	 * @param sDate
	 *            : set the property sDate.
	 */
	public void setsDate(Date sDate) {
		this.sDate = sDate;
	}

	/**
	 * @return eDate : return the property eDate.
	 */
	public Date geteDate() {
		return eDate;
	}

	/**
	 * @param eDate
	 *            : set the property eDate.
	 */
	public void seteDate(Date eDate) {
		this.eDate = eDate;
	}

}