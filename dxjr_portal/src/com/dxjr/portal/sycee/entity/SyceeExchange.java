package com.dxjr.portal.sycee.entity;

import java.io.Serializable;
import java.util.Date;

public class SyceeExchange implements Serializable {

	private static final long serialVersionUID = -5890332628953747763L;
	private Integer id;
	private Integer syceeGoodsId;// 元宝商品ID
	private Integer userId;// 用户ID
	private Integer sycee;// 兑换所用元宝=price*num
	private Integer num;// 兑换数量
	private Integer price;// 单价(元宝)
	private Date exchangeTime;// 兑换时间
	private Integer dealStatus;// 0未处理；1已处理（线上商品及时发放均为已处理）；2暂缓处理
	private Integer dealUser;// 处理人ID
	private String dealUsername;// 处理人姓名
	private Date dealTime;// 处理时间
	private String dealRemark;
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSyceeGoodsId() {
		return syceeGoodsId;
	}

	public void setSyceeGoodsId(Integer syceeGoodsId) {
		this.syceeGoodsId = syceeGoodsId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSycee() {
		return sycee;
	}

	public void setSycee(Integer sycee) {
		this.sycee = sycee;
	}

	public Date getExchangeTime() {
		return exchangeTime;
	}

	public void setExchangeTime(Date exchangeTime) {
		this.exchangeTime = exchangeTime;
	}

	public Integer getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(Integer dealStatus) {
		this.dealStatus = dealStatus;
	}

	public Integer getDealUser() {
		return dealUser;
	}

	public void setDealUser(Integer dealUser) {
		this.dealUser = dealUser;
	}

	public String getDealUsername() {
		return dealUsername;
	}

	public void setDealUsername(String dealUsername) {
		this.dealUsername = dealUsername;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public String getDealRemark() {
		return dealRemark;
	}

	public void setDealRemark(String dealRemark) {
		this.dealRemark = dealRemark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}
