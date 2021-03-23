package com.dxjr.portal.tzjinterface.vo;

import java.io.Serializable;

public class EncryptionKeyVo implements Serializable{

	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = -1183109222143212000L;
	/**主键ID*/
	private Integer id ;
	/**新增时间*/
	private String addTime;
	/**时间戳*/
	private String ts;
	/**删除标记*/
	private String dr;
	/**版本*/
	private Integer version;
	
	 
	/********************************get() set() method **********************************/
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public String getDr() {
		return dr;
	}
	public void setDr(String dr) {
		this.dr = dr;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	/*************************************************************************************/
	  
	  

}
