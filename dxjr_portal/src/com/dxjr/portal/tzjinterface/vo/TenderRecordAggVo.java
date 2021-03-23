package com.dxjr.portal.tzjinterface.vo;

import java.io.Serializable;
import java.util.List;

import com.dxjr.portal.util.JsonUtils;

import org.codehaus.jackson.annotate.JsonIgnore;



public class TenderRecordAggVo implements AggregatedValueObject, IDataPlugin,Serializable {

	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = 6217903516414872065L;
	private BorrowVo subject;
	private List<TenderRecordVo> bidlist;

	public BorrowVo getSubject() {
		return subject;
	}

	public void setSubject(BorrowVo subject) {
		this.subject = subject;
	}

	public List<TenderRecordVo> getBidlist() {
		return bidlist;
	}

	public void setBidlist(List<TenderRecordVo> bidlist) {
		this.bidlist = bidlist;
	}



	/** 平台标的id */
	@JsonIgnore
	private Integer bid;

	public Integer getBid() {
		return bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	@Override
	public String toJson() {
		return JsonUtils.bean2Json(this);
	}

	@Override
	@JsonIgnore
	public Boolean isEmpty() {
		return false;
	}

	@Override
	@JsonIgnore
	public Boolean isDirty() {
		return null;
	}	
	
}

