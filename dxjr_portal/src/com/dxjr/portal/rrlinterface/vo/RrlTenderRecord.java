package com.dxjr.portal.rrlinterface.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author WangQianJin
 * @title 人人利投资记录
 * @date 2016年4月25日
 */
public class RrlTenderRecord implements Serializable{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -8641993648757044469L;
	
	//用户名
	@JsonProperty("User_name")
	private String user_name;
	
	//投资单号
	@JsonProperty("Order_no")
	private String order_no;
	
	//项目名
	@JsonProperty("Pro_name")
	private String pro_name;
	
	//项目ID
	@JsonProperty("Pro_id")
	private String pro_id;
	
	//投资金额
	@JsonProperty("Invest_money")
	private BigDecimal invest_money;
	
	//实际投资金额
	@JsonProperty("Actual_invest_money")
	private BigDecimal actual_invest_money;
	
	//年化率
	@JsonProperty("Rate")
	private Float rate;
	
	//投资开始时间（13位时间戳）
	@JsonProperty("Invest_start_date")
	private String invest_start_date;
	
	//投资到期时间（13位时间戳，时分秒设置为23:59:59）
	@JsonProperty("Invest_end_date")
	private String invest_end_date;
	
	//满标时间（13位时间戳）
	@JsonProperty("Invest_full_scale_date")
	private String invest_full_scale_date;
	
	//已还金额
	@JsonProperty("Back_money")
	private BigDecimal back_money;
	
	//最近还款日期（13位时间戳）
	@JsonProperty("Back_last_date")
	private String back_last_date;
	
	//用户名（注册时的key）
	@JsonProperty("Cust_key")
	private String cust_key;

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	public String getPro_id() {
		return pro_id;
	}

	public void setPro_id(String pro_id) {
		this.pro_id = pro_id;
	}

	public BigDecimal getInvest_money() {
		return invest_money;
	}

	public void setInvest_money(BigDecimal invest_money) {
		this.invest_money = invest_money;
	}

	public BigDecimal getActual_invest_money() {
		return actual_invest_money;
	}

	public void setActual_invest_money(BigDecimal actual_invest_money) {
		this.actual_invest_money = actual_invest_money;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public String getInvest_start_date() {
		return invest_start_date;
	}

	public void setInvest_start_date(String invest_start_date) {
		this.invest_start_date = invest_start_date;
	}

	public String getInvest_end_date() {
		return invest_end_date;
	}

	public void setInvest_end_date(String invest_end_date) {
		this.invest_end_date = invest_end_date;
	}

	public String getInvest_full_scale_date() {
		return invest_full_scale_date;
	}

	public void setInvest_full_scale_date(String invest_full_scale_date) {
		this.invest_full_scale_date = invest_full_scale_date;
	}

	public BigDecimal getBack_money() {
		return back_money;
	}

	public void setBack_money(BigDecimal back_money) {
		this.back_money = back_money;
	}

	public String getBack_last_date() {
		return back_last_date;
	}

	public void setBack_last_date(String back_last_date) {
		this.back_last_date = back_last_date;
	}

	public String getCust_key() {
		return cust_key;
	}

	public void setCust_key(String cust_key) {
		this.cust_key = cust_key;
	}
	
	

}
