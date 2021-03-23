package com.dxjr.portal.first.vo;

import java.io.Serializable;
import java.util.Date;

import com.dxjr.base.entity.FirstTransferBorrow;
import com.dxjr.utils.DateUtils;

/**
 * <p>
 * Description:我的账户-直通车转让菜单中列表<br />
 * </p>
 * 
 * @title FirstTransferTypeVo.java
 * @package com.dxjr.portal.first.vo
 * @author justin.xu
 * @version 0.1 2015年3月12日
 */
public class FirstTransferBorrowVo extends FirstTransferBorrow implements Serializable {
	private static final long serialVersionUID = -191815169850848319L;

	/** 借款类型 **/
	private String borrowStyleStr;
	
	/** 定时发布时间 */
	private String publishTimeStr;
	private Date publishTimeDate;

	/**
	 * 剩余还款期数
	 */
	private String remainPeriod;

	public String getBorrowStyleStr() {
		if (super.getBorrowStyle() != null) {
			if (super.getBorrowStyle() == 1) {
				return "等额本息";
			}
			if (super.getBorrowStyle() == 2) {
				return "按月付息到期还本";
			}
			if (super.getBorrowStyle() == 3) {
				return "到期还本付息";
			}
			if (super.getBorrowStyle() == 4) {
				return "按天还款";
			}
		}
		return borrowStyleStr;
	}

	public void setBorrowStyleStr(String borrowStyleStr) {
		this.borrowStyleStr = borrowStyleStr;
	}

	public String getRemainPeriod() {
		if (super.getBorrowOrder() != null && super.getStartOrder() != null) {
			return String.valueOf(super.getBorrowOrder() - super.getStartOrder() + 1);
		}
		return remainPeriod;
	}

	public void setRemainPeriod(String remainPeriod) {
		this.remainPeriod = remainPeriod;
	}

	public String getPublishTimeStr() {
		return publishTimeStr;
	}

	public void setPublishTimeStr(String publishTimeStr) {
		this.publishTimeStr = publishTimeStr;
	}

	public Date getPublishTimeDate() {
		if (this.getPublishTimeStr() != null) {
			return DateUtils.timeStampToDate(this.getPublishTimeStr());
		}
		return publishTimeDate;
	}

	public void setPublishTimeDate(Date publishTimeDate) {
		this.publishTimeDate = publishTimeDate;
	}
}
