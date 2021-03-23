package com.dxjr.portal.stockright.vo;

import java.io.Serializable;

import com.dxjr.common.page.BaseCnd;


public class StockApplyRequest extends BaseCnd implements Serializable{
		private static final long serialVersionUID = 8983061145517554053L;
		private Integer userId;//用户主键
		private Integer type;//黑名单类型
		private String beginTime;
		private String endTime;
		private Integer status;
		private Integer pageNum;
		private Integer applyId;
		private Integer[] statusArray;// 合同状态集合
		
		
		
		
		 
		
		
		public Integer[] getStatusArray() {
			return statusArray;
		}
		public void setStatusArray(Integer[] statusArray) {
			this.statusArray = statusArray;
		}
		public Integer getPageNum() {
			return pageNum;
		}
		public void setPageNum(Integer pageNum) {
			this.pageNum = pageNum;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public String getBeginTime() {
			return beginTime;
		}
		public void setBeginTime(String beginTime) {
			this.beginTime = beginTime;
		}
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
		public Integer getUserId() {
			return userId;
		}
		public void setUserId(Integer userId) {
			this.userId = userId;
		}
		public Integer getApplyId() {
			return applyId;
		}
		public void setApplyId(Integer applyId) {
			this.applyId = applyId;
		}
		
		
	
}
