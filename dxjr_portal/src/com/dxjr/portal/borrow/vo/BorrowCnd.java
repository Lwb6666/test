package com.dxjr.portal.borrow.vo;

import java.io.Serializable;
import java.util.Date;

import com.dxjr.common.page.BaseCnd;

/**
 * <p>
 * Description:借款标<br />
 * </p>
 * 
 * @title BorrowVo.java
 * @package com.dxjr.portal.borrow.vo
 * @author justin.xu
 * @version 0.1 2014年5月15日
 */
public class BorrowCnd extends BaseCnd implements Serializable {
	/**
	 * @fields serialVersionUID
	 */

	private static final long serialVersionUID = -2343651735385175138L;

	/** 借款标题 */
	private String name;
	private Integer userId;

	/** 查询栏目类型 **/
	private String searchType;

	/** 借款标发布人用户名 **/
	private String userName;

	/** 排序类型： apr、period、schedule、account **/
	private String searchOrderBy;

	/** 排序方式：asc或desc **/
	private String orderByType;

	/** 合同编号 **/
	private String contractNo;

	/** 满标开始时间 **/
	private Date successTimeBegin;
	private String successTimeBeginStr;

	/** 满标结束时间 **/
	private Date successTimeEnd;
	private String successTimeEndStr;

	/** 借款标类型 **/
	private String borrowtype;

	private String beginTime;
	private String endTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSearchOrderBy() {
		return searchOrderBy;
	}

	public void setSearchOrderBy(String searchOrderBy) {
		this.searchOrderBy = searchOrderBy;
	}

	public String getOrderByType() {
		return orderByType;
	}

	public void setOrderByType(String orderByType) {
		this.orderByType = orderByType;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getBorrowtype() {
		return borrowtype;
	}

	public void setBorrowtype(String borrowtype) {
		this.borrowtype = borrowtype;
	}

	public Date getSuccessTimeBegin() {
		return successTimeBegin;
	}

	public void setSuccessTimeBegin(Date successTimeBegin) {
		this.successTimeBegin = successTimeBegin;
	}

	public String getSuccessTimeBeginStr() {
		return successTimeBeginStr;
	}

	public void setSuccessTimeBeginStr(String successTimeBeginStr) {
		this.successTimeBeginStr = successTimeBeginStr;
	}

	public Date getSuccessTimeEnd() {
		return successTimeEnd;
	}

	public void setSuccessTimeEnd(Date successTimeEnd) {
		this.successTimeEnd = successTimeEnd;
	}

	public String getSuccessTimeEndStr() {
		return successTimeEndStr;
	}

	public void setSuccessTimeEndStr(String successTimeEndStr) {
		this.successTimeEndStr = successTimeEndStr;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

}