package com.dxjr.portal.tzjinterface.entity;

import com.dxjr.portal.util.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;


public class ServiceData {
	// 业务标识
	private String service;
	// 业务请求或响应
	private Object body;
	
	private GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public ServiceData(String service, Object body) {
		this.service = service;
		if (body instanceof JsonElement) {
			this.body = (JsonElement) body;
		} else {
			Gson gson=gsonBuilder.create();
			this.body = gson.toJsonTree(body);
//			this.body=JsonUtils.bean2Json(body);
		}
	}
	
	public String getService() {
		return service;
	}

	public Object getBody() {
		return body;
	}
}
