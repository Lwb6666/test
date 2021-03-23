package com.dxjr.portal.account.vo;

import java.io.Serializable;

import com.dxjr.base.entity.AccountLog;

/**
 * <p>
 * Description:资金操作日志查询条件<br />
 * </p>
 * 
 * @title AccountLogCnd.java
 * @package com.dxjr.portal.account.vo
 * @author justin.xu
 * @version 0.1 2014年4月30日
 */
public class AccountLogCnd extends AccountLog implements Serializable {
	private static final long serialVersionUID = 8983061145517554053L;
	private Integer id;
	/** 會員ID */
	private Integer userId;
	/** 日誌類型 */
	private String type;
	/** 日誌備註 */
	private String remark;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
