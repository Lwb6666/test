package com.dxjr.wx.entry.bind.vo;

/**
 * 绑定历史实体
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title BindHistoryVo.java
 * @package com.dxjr.wx.entry.bind.vo
 * @author Wang Bo
 * @version 0.1 2014年11月1日
 */
public class BindHistoryVo {
	/** id */
	private Long id;
	/** 微信id */
	private String wId;
	/** 用户id */
	private String uId;
	/** 绑定时间 */
	private Long createTime;
	/** 取消绑定时间 */
	private Long updateTime;
	/** 标记 */
	private int removeTag;
	/** 备注 */
	private String exemmo;
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getExemmo() {
		return exemmo;
	}

	public void setExemmo(String exemmo) {
		this.exemmo = exemmo;
	}

	private String wIds;

	public String getwIds() {
		return wIds;
	}

	public void setwIds(String wIds) {
		this.wIds = wIds;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getwId() {
		return wId;
	}

	public void setwId(String wId) {
		this.wId = wId;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public int getRemoveTag() {
		return removeTag;
	}

	public void setRemoveTag(int removeTag) {
		this.removeTag = removeTag;
	}

}
