package com.dxjr.base.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangQianJin
 * @title 渠道绑定实体
 * @date 2016年5月27日
 */
public class ChannelBindingVo implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -9191759436795884363L;
	
	/** 编号 */
	private Integer id;
	/** 用户ID */
	private Integer userId;
	/** 渠道用户ID */
	private String channelUid;
	/** 渠道来源 */
	private Integer source;
	/** 绑定状态（0：失败 1：成功） */
	private Integer status;
	/** 创建时间 */
	private Date addtime;
	/** 修改时间 */
	private Date updatetime;
	
	
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
	public String getChannelUid() {
		return channelUid;
	}
	public void setChannelUid(String channelUid) {
		this.channelUid = channelUid;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	
	
}
