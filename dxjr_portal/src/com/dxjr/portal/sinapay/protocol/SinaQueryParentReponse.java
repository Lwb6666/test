package com.dxjr.portal.sinapay.protocol;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * Description:查询接口返回的整体数据<br />
 * </p>
 * 
 * @title SinaQueryParentReponse.java
 * @package com.dxjr.portal.sinapay.protocol
 * @author justin.xu
 * @version 0.1 2014年12月26日
 */
@XmlRootElement(name = "queryResult")
public class SinaQueryParentReponse {
	/**
	 * 字符集 2 可为空 固定选择值：1、2、3 1代表UTF-8; 2代表GBK; 3代表GB2312 默认值为1
	 */
	private String inputCharset;
	/**
	 * 固定为 weibopay_query_api_1
	 */
	private String version;
	/**
	 * 合作伙伴在微汇的用户编号
	 */
	private String pid;
	/**
	 * 商户订单查询开始时间 数字串，一共14位 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]例如：20071117020101
	 */
	private String startTime;
	/**
	 * 商户订单查询结束时间，一共14位 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]例如：20071117020101
	 */
	private String endTime;
	/**
	 * 商户订单号
	 */
	private String orderId;
	/**
	 * 新浪支付交易号
	 */
	private String dealId;
	/**
	 * 查询页码 默认为0
	 */
	private String pageNo;
	/**
	 * 页包含记录数 默认为30，目前不可超过3000
	 */
	private String pageSize;
	/**
	 * 返回记录数
	 */
	private String totalCount;
	/**
	 * 返回错误码
	 */
	private String errorCode;
	/**
	 * 返回错误原因
	 */
	private String errorMessage;
	/**
	 * 纪录表,列表对象，包含多个返回结果，
	 */
	private List<SinaQueryRecordListReponse> recordList;
	/**
	 * 签名类型
	 */
	private String signType;
	/**
	 * 签名字符串
	 */
	private String signMsg;

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@XmlElementWrapper(name = "recordList")
	@XmlElement(name = "orderDetail")
	public List<SinaQueryRecordListReponse> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<SinaQueryRecordListReponse> recordList) {
		this.recordList = recordList;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String generateSignContent() {
		StringBuilder builder = new StringBuilder();
		if (inputCharset != null) {
			builder.append("inputCharset").append('=').append(inputCharset);
		}
		if (version != null) {
			builder.append('&').append("version").append('=').append(version);
		}
		if (pid != null) {
			builder.append('&').append("pid").append('=').append(pid);
		}
		if (startTime != null) {
			builder.append('&').append("startTime").append('=').append(startTime);
		}
		if (endTime != null) {
			builder.append('&').append("endTime").append('=').append(endTime);
		}
		if (orderId != null) {
			builder.append('&').append("orderId").append('=').append(orderId);
		}
		if (dealId != null) {
			builder.append('&').append("dealId").append('=').append(dealId);
		}
		if (pageNo != null) {
			builder.append('&').append("pageNo").append('=').append(pageNo);
		}
		if (pageSize != null) {
			builder.append('&').append("pageSize").append('=').append(pageSize);
		}
		if (totalCount != null) {
			builder.append('&').append("totalCount").append('=').append(totalCount);
		}
		if (errorCode != null) {
			builder.append('&').append("totalCount").append('=').append(errorCode);
		}
		if (signType != null) {
			builder.append('&').append("sharingData").append('=').append(signType);
		}
		return builder.toString();
	}

}
