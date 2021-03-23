package com.dxjr.portal.stockright.vo;

import java.io.Serializable;

import com.dxjr.common.page.BaseCnd;

public class StockDealRequest extends BaseCnd implements Serializable{
		private static final long serialVersionUID = -8005802218725609268L;
		private Integer id;//主键
		private Integer userId;//用户主键
		private Integer sellerId;
		private Integer buyerId;
		private Integer sellerEntrustId;
		private Integer buyerEntrustId;
		
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Integer getUserId() {
			return userId;
		}
		public void setUserId(Integer userId) {
			this.userId = userId;
		}
		public Integer getSellerId() {
			return sellerId;
		}
		public void setSellerId(Integer sellerId) {
			this.sellerId = sellerId;
		}
		public Integer getBuyerId() {
			return buyerId;
		}
		public void setBuyerId(Integer buyerId) {
			this.buyerId = buyerId;
		}
		public Integer getSellerEntrustId() {
			return sellerEntrustId;
		}
		public void setSellerEntrustId(Integer sellerEntrustId) {
			this.sellerEntrustId = sellerEntrustId;
		}
		public Integer getBuyerEntrustId() {
			return buyerEntrustId;
		}
		public void setBuyerEntrustId(Integer buyerEntrustId) {
			this.buyerEntrustId = buyerEntrustId;
		}
		
}
