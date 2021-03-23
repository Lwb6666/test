package com.dxjr.portal.cms.vo;

import java.io.Serializable;

import com.dxjr.common.page.BaseCnd;

public class SearchPageVo   extends BaseCnd implements Serializable {

	private static final long serialVersionUID = 5622354453575758413L;
	
	// id
	private Integer id;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

}
