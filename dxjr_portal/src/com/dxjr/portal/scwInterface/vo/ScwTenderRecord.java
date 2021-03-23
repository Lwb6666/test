package com.dxjr.portal.scwInterface.vo;

import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author WangQianJin
 * @title 生菜网投资记录
 * @date 2016年5月5日
 */
public class ScwTenderRecord implements Serializable{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 4252260906520091335L;	
	
	//用户在合作方平台注册的唯一标识：id或者uid
	@JsonProperty("rbuid")
	private String rbuid;
	
	//用户投资的标的的唯一标识id
	@JsonProperty("pid")
	private String pid;
	
	//用户投资成功的时间
	@JsonProperty("time")
	private String time;
	
	//用户投资金额（单位：元）：实际发生的投资额，不包括体验金、现金券、红包等数据
	@JsonProperty("money")
	private String money;

	public String getRbuid() {
		return rbuid;
	}

	public void setRbuid(String rbuid) {
		this.rbuid = rbuid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	

}
