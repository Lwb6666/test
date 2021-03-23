package com.dxjr.portal.first.vo;

import java.io.Serializable;

import com.dxjr.common.page.BaseCnd;

/**
 * <p>
 * Description:直通车所投借款标转让资金明细查询条件<br />
 * </p>
 * 
 * @title FirstTransferBorrowCnd.java
 * @package com.dxjr.portal.first.vo
 * @author justin.xu
 * @version 0.1 2015年3月17日
 */
public class FirstTransferBorrowCnd extends BaseCnd implements Serializable {
	private static final long serialVersionUID = 7343325710006818136L;
	/**
	 * 直通车转让id
	 */
	private Integer firstTransferId;
	/**
	 * 直通车最终认购记录id
	 */
	private Integer firstTenderRealId;
	/**
	 * 转让用户id
	 */
	private Integer userId;
	
	/**
	 * 借款标ID
	 */
	private Integer borrowId;
	
	/**
	 * 投标ID
	 */
	private Integer tenderId;

	public Integer getFirstTransferId() {
		return firstTransferId;
	}

	public void setFirstTransferId(Integer firstTransferId) {
		this.firstTransferId = firstTransferId;
	}

	public Integer getFirstTenderRealId() {
		return firstTenderRealId;
	}

	public void setFirstTenderRealId(Integer firstTenderRealId) {
		this.firstTenderRealId = firstTenderRealId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Integer borrowId) {
		this.borrowId = borrowId;
	}

	public Integer getTenderId() {
		return tenderId;
	}

	public void setTenderId(Integer tenderId) {
		this.tenderId = tenderId;
	}
}
