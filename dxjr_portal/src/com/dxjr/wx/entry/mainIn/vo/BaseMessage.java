package com.dxjr.wx.entry.mainIn.vo;

/**
 * 基本消息类 所有推送消息的父类
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title BaseMessage.java
 * @package com.dxjr.wx.entry.mainIn.vo
 * @author Wang Bo
 * @version 0.1 2014年10月25日
 */
public class BaseMessage {
	// 被推送
	private String ToUserName;
	// 推送方
	private String FromUserName;
	// 推送时间
	private long CreateTime;
	// 消息类型
	private String MsgType;
	// 消息id
	private long MsgId;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public long getMsgId() {
		return MsgId;
	}

	public void setMsgId(long msgId) {
		MsgId = msgId;
	}

}
