package com.dxjr.wx.entry.message.pushmessage.vo;

import java.util.Map;

public class PushMessage {
	private String touser;
	private String template_id;
	private String url;
	private String topcolor;
	private Map data;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}

	public PushMessage() {
		super();
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public PushMessage(String template_id, String url, String topcolor, Map data) {
		super();
		this.template_id = template_id;
		this.url = url;
		this.topcolor = topcolor;
		this.data = data;
	}

}
