package com.dxjr.portal.account.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:账户每日动态数据查询条件<br />
 * </p>
 * 
 * @title AccountDayLogCnd.java
 * @package com.dxjr.portal.account.vo
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
public class AccountDayLogCnd implements Serializable {
	private static final long serialVersionUID = 8983061145517554053L;
	private Integer id;
	/** 會員ID */
	private Integer userId;
	/** 开始时间 */
	private String beginAddDate;
	/** 结束时间 */
	private String endAddDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getBeginAddDate() {
		return beginAddDate;
	}

	public void setBeginAddDate(String beginAddDate) {
		this.beginAddDate = beginAddDate;
	}

	public String getEndAddDate() {
		return endAddDate;
	}

	public void setEndAddDate(String endAddDate) {
		this.endAddDate = endAddDate;
	}

}
