package com.dxjr.portal.member.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:员工查询条件<br />
 * </p>
 * 
 * @title StaffCnd.java
 * @package com.dxjr.portal.member.vo
 * @author justin.xu
 * @version 0.1 2014年4月25日
 */
public class StaffCnd implements Serializable {
	private static final long serialVersionUID = 1206107633267339937L;

	private Integer id;

	/** 部门值 */
	private String deptValue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeptValue() {
		return deptValue;
	}

	public void setDeptValue(String deptValue) {
		this.deptValue = deptValue;
	}

}
