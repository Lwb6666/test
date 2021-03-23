package com.dxjr.portal.member.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 银行卡更换申请点击日志
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title BankcardClick.java
 * @package com.dxjr.portal.member.vo
 * @author huangpin
 * @version 0.1 2015年3月31日
 */
public class BankcardClick implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id; // 自增ID
	private String errorMsg; // 错误信息
	private Integer userId; // 用户ID
	private Date addTime; // 添加时间
	private String addIp; // 添加IP
	private Integer type; // 类型(1:正确，-1：错误)
	private Integer platform; // 平台来源(1：网页 2、微信 3 安卓 4 IOS)

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

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

}
