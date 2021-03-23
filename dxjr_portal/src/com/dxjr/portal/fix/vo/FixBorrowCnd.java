package com.dxjr.portal.fix.vo;

import com.dxjr.common.page.BaseCnd;

/**
 * <p>
 * Description:定期宝查询条件<br />
 * </p>
 * 
 * @title FirstBorrowCnd.java
 * @package com.dxjr.portal.fix1.vo
 * @author caodefeng
 * @version 0.1 2015年5月14日
 */
public class FixBorrowCnd extends BaseCnd {

	/**
	 * 
	 */
	private static final long serialVersionUID = -747561170614279281L;

	/**
	 * userId
	 */
	private Integer userId;
	
	/**
	 * lockLimit
	 */
	private Integer lockLimit;
	
	/**
	 * fixBorrowId
	 */
	private Integer fixBorrowId;


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getLockLimit() {
		return lockLimit;
	}

	public void setLockLimit(Integer lockLimit) {
		this.lockLimit = lockLimit;
	}

	public Integer getFixBorrowId() {
		return fixBorrowId;
	}

	public void setFixBorrowId(Integer fixBorrowId) {
		this.fixBorrowId = fixBorrowId;
	}

 
	
	
}
