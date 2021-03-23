package com.dxjr.portal.outerInterface.vo;

import java.util.Date;

import com.dxjr.common.page.BaseCnd;

/**
 * 
 * <p>
 * Description:接口参数Cnd<br />
 * </p>
 * 
 * @title OuterParamCnd.java
 * @package com.dxjr.portal.outerInterface.vo
 * @author yangshijin
 * @version 0.1 2014年8月20日
 */
public class OuterParamCnd extends BaseCnd {

	private static final long serialVersionUID = -1433741065630041669L;

	private int borrowid;
	private String type;
	private String beginTime;
	private String endTime;
	private Date beginDate;
	private Date endDate;

	public int getBorrowid() {
		return borrowid;
	}

	public void setBorrowid(int borrowid) {
		this.borrowid = borrowid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
