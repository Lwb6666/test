package com.dxjr.portal.tzjinterface.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangQianJin
 * @title 查询用户条件
 * @date 2016年3月28日
 */
public class TzjQueryCnd implements Serializable {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 8649269597924600073L;
	
	/**开始时间*/
	private Date startTime;
	/**结束时间*/
	private Date endTime;
	/**数组转换为字符串，用逗号进行分割*/
	private String arrayValStr;
	/**index查询类型1:id; 2:bid; 3:username */
	private String type;	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getArrayValStr() {
		return arrayValStr;
	}
	public void setArrayValStr(String arrayValStr) {
		this.arrayValStr = arrayValStr;
	}

}
