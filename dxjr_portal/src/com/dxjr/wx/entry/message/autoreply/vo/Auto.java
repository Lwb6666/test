package com.dxjr.wx.entry.message.autoreply.vo;

import java.io.Serializable;

/**
 * 被动回复
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title Auto.java
 * @package com.dxjr.wx.entry.message.autoreply.vo
 * @author Wang Bo
 * @version 0.1 2014年10月25日
 */
public class Auto implements Serializable {

	private static final long serialVersionUID = -6993505979195482036L;
	private Integer id;
	private Integer kId;
	private Integer needPermission;
	private String content;
	private Integer status;
	private String eventKey;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getkId() {
		return kId;
	}

	public void setkId(Integer kId) {
		this.kId = kId;
	}

	public Integer getNeedPermission() {
		return needPermission;
	}

	public void setNeedPermission(Integer needPermission) {
		this.needPermission = needPermission;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

}