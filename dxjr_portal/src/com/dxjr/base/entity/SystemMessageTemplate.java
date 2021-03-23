package com.dxjr.base.entity;

import java.io.Serializable;

/**
 * <p>
 * Description:系统站内信模板表<br />
 * </p>
 * 
 * @title SystemMessageTemplate.java
 * @package com.dxjr.base.entity
 * @author justin.xu
 * @version 0.1 2014年4月19日
 */
public class SystemMessageTemplate implements Serializable {

	private static final long serialVersionUID = 4097697021129703558L;

	/** 主键 */
	private Integer id;

	/** 标题 */
	private String title;

	/** 模板类型 */
	private Integer type;

	/** 模板内容 */
	private String content;

	/** 逻辑删除标识【0：有效；-1：无效】 */
	private Integer flag;

	/** 备注信息 */
	private String remark;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}