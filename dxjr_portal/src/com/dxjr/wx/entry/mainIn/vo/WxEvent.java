package com.dxjr.wx.entry.mainIn.vo;

/**
 * 事件消息
 * <p>
 * Description:<br />
 * </p>
 * 
 * @title WxEvent.java
 * @package com.dxjr.wx.entry.mainIn.vo
 * @author Wang Bo
 * @version 0.1 2014年10月25日
 */
public class WxEvent {
	// 发送者
	private String fromUserName;
	// 被发送至
	private String toUserName;
	// 内容类型
	private String msgType;
	// 内容
	private String content;
	// 事件类型
	private String eventType;
	// click事件类型
	private String clickKey;
	private Integer wxId;
	/** 地理位置维度 */
	private String latitude;
	/** 地理位置经度 */
	private String longitude;
	/** 地理位置精度 */
	private String precision;
	/** 地理位置 */
	private String label;

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getWxId() {
		return wxId;
	}

	public void setWxId(Integer wxId) {
		this.wxId = wxId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getClickKey() {
		return clickKey;
	}

	public void setClickKey(String clickKey) {
		this.clickKey = clickKey;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

}
