package com.dxjr.base.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Description:用户关联人实体类<br />
 * </p>
 * 
 * @title AccountLinkman.java
 * @package com.dxjr.base.entity
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
public class AccountLinkman implements Serializable {
	private static final long serialVersionUID = 5970945871389634498L;

	/** 主键 */
	private Integer id;

	/** 被关联用户ID */
	private Integer userId;

	/** 联系人姓名 */
	private String name;

	/** 关联人手机 */
	private String mobile;

	/** 关联人电子邮箱 */
	private String email;

	/** 关系 */
	private String relationship;

	/** 插入时间 */
	private Date addtime;

	/** 插入IP */
	private String addip;

	/** 最后一次修改时间 */
	private Date modifytime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAddip() {
		return addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

}