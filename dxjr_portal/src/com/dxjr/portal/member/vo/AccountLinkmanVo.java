package com.dxjr.portal.member.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Description:用户关联人实体<br />
 * </p>
 * 
 * @title AccountLinkmanVo.java
 * @package com.dxjr.portal.member.vo
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
public class AccountLinkmanVo implements Serializable {
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

	/** 格式化后的名字，除了最后一位显示，其它的都用*代替 */
	private String formatName;

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

	public String getFormatName() {
		if (null != name && !"".equals(name)) {
			String str1 = name.substring(name.length() - 1);
			String str2 = "";
			for (int i = 0; i < name.length() - 1; i++) {
				str2 = str2 + "*";
			}
			formatName = str2 + str1;
		}
		return formatName;
	}

	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}

}