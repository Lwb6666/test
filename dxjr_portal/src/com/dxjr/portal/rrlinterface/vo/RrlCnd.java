package com.dxjr.portal.rrlinterface.vo;

/**
 * @author WangQianJin
 * @title 人人利查询条件
 * @date 2016年4月23日
 */
public class RrlCnd extends RrlMemberBinding {

	//订单号
	private String order_no;
	//开始日期（时间戳）
	private String start_date;
	//结束日期（时间戳）
	private String end_date;
	//标的认购ID
	private String order_bno;
	//债转认购ID
	private String order_zno;
	//定期宝认购ID
	private String order_dno;
	
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getOrder_bno() {
		return order_bno;
	}
	public void setOrder_bno(String order_bno) {
		this.order_bno = order_bno;
	}
	public String getOrder_zno() {
		return order_zno;
	}
	public void setOrder_zno(String order_zno) {
		this.order_zno = order_zno;
	}
	public String getOrder_dno() {
		return order_dno;
	}
	public void setOrder_dno(String order_dno) {
		this.order_dno = order_dno;
	}
	
	
}
