package com.dxjr.portal.scwInterface.vo;

/**
 * @author WangQianJin
 * @title 生菜网查询条件
 * @date 2016年5月5日
 */
public class ScwCnd extends ScwMemberBinding {

	//生菜来源标识，如：shengcai18
	private String fr;
	//开始日期，如：20150804120000
	private String stime;
	//结束日期，如：20150804130000
	private String etime;
	//limitType=30，表示请求的是标的期限为30天以内的（包含30天）所有上线中的标的;如果limitType值为空，则是查询全部
	private Integer limitType; 
	
	public String getFr() {
		return fr;
	}
	public void setFr(String fr) {
		this.fr = fr;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public Integer getLimitType() {
		return limitType;
	}
	public void setLimitType(Integer limitType) {
		this.limitType = limitType;
	}	
	
}
