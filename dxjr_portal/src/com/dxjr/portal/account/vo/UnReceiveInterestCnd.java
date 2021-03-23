package com.dxjr.portal.account.vo;

import java.io.Serializable;
import java.util.Date;


/**   
 * <p>
 * Description:
 * 电子账单<br/>
 * 查询某个用户的转让债权金额总和 MB <br />
 * </p>
 * @title UnReceiveInterestCnd.java
 * @package com.dxjr.portal.account.vo 
 * @author HuangJun
 * @version 0.1 2015年5月27日
 */
public class UnReceiveInterestCnd implements Serializable {

	private static final long serialVersionUID = -6170257318417414069L;

	// 用户
	private int userId;
	// 年月初
	private Date sDate;
	// 年月末
	private Date eDate;

	/**
	 * @return userId : return the property userId.
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            : set the property userId.
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

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
