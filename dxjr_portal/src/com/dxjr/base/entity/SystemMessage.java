package com.dxjr.base.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Description:系统站内信实体类<br />
 * </p>
 * 
 * @title SystemMessage.java
 * @package com.dxjr.base.entity
 * @author justin.xu
 * @version 0.1 2014年4月19日
 */
public class SystemMessage implements Serializable {

	private static final long serialVersionUID = -8291731970985047635L;

	/** 主键 */
	private Integer id;

	/** 标题 */
	private String title;

	/** 模板类型 */
	private Integer type;

	/** 站内信接受人 */
	private Integer userId;

	/** 站内信内容 */
	private String content;

	/** 发送时间 */
	private Date addtime;

	/** 是否已读 ,0表示未读,1表示已读 */
	private Integer isRead;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

}