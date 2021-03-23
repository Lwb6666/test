package com.dxjr.portal.curAccount.vo;

import java.io.Serializable;

import com.dxjr.portal.curAccount.entity.CurAccountlog;

public class CurAccountLogVo extends CurAccountlog implements Serializable {

	private static final long serialVersionUID = -6903226903535940857L;

	// 类型：
	private String type_z;

	/**
	 * @return type_z : return the property type_z.
	 */
	public String getType_z() {
		return type_z;
	}

	/**
	 * @param type_z
	 *            : set the property type_z.
	 */
	public void setType_z(String type_z) {
		this.type_z = type_z;
	}

}