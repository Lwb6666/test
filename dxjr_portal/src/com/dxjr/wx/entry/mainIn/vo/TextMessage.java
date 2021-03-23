package com.dxjr.wx.entry.mainIn.vo;

/**
 * 文本消息
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title TextMessage.java
 * @package com.dxjr.wx.entry.mainIn.vo
 * @author Wang Bo
 * @version 0.1 2014年10月25日
 */
public class TextMessage extends BaseMessage {
	// 内容
	private String Content;

	public String getContent() {
		return this.Content;
	}

	public void setContent(String content) {
		this.Content = content;
	}
}
