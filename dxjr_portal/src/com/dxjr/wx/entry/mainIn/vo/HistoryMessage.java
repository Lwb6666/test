package com.dxjr.wx.entry.mainIn.vo;

/**
 * 历史消息
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title HistoryMessage.java
 * @package com.dxjr.wx.entry.mainIn.vo
 * @author Wang Bo
 * @version 0.1 2014年10月27日
 */
public class HistoryMessage {
	// id
	private Integer id;
	// 微信id
	private Integer wId;
	// 消息创建时间
	private Long createTime;
	// 消息类型
	private String msgType;
	// 消息id
	private String msgId;
	// 描述
	private String description;
	// 内容
	private String content;
	// 事件key
	private String eventKey;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getwId() {
		return wId;
	}

	public void setwId(Integer wId) {
		this.wId = wId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

}