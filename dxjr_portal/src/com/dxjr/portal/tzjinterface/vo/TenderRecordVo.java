package com.dxjr.portal.tzjinterface.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.dxjr.portal.borrow.entity.Interest;
import com.dxjr.portal.tzjinterface.util.CalculatorServiceTool;


public class TenderRecordVo implements Serializable {

	/** 
	 * @fields serialVersionUID 
	 */ 
	private static final long serialVersionUID = 5803057932877754529L;
	/** 投之家用户 */
	private String username;
	/** 平台用户名 */
	private String usernamep;
	/** 订单号 */
	private String oid;
	/** 投标金额 */
	private BigDecimal bidAmount;
	/** 有效金额 */
	private BigDecimal amount;
	/** 投标类型 */
	private String type;
	/** 投标时间 */
	private String time;
	/** 状态【0：还款中；1：已还清；2：逾期；3：已转让】 */
	@JsonIgnore
	private Integer status;
	@JsonIgnore
	private Integer tenderUserId;
	/** 投标记录ID */
	@JsonIgnore
	private Integer tId;
	/** 预期收益 */
	private BigDecimal income;

	/** 年利率 */
	@JsonIgnore
	private Float arp;
	/** 借款期限 */
	@JsonIgnore
	private Integer timeLimit;
	/** 还款方式 */
	@JsonIgnore
	private Integer style;
	/** 是否属于转让记录 0代表非转让 1代表转让 示例：该记录如果属于用户从别人那转让而来则值为1，如果记录为用户自己购买未转让出去则值为0 */
	@JsonProperty(value = "isTransfer")
	private Integer isTransfer;

	public BigDecimal getIncome() {
		Interest inter = new Interest();
		inter.setMoney(bidAmount);
		inter.setRate(new BigDecimal(this.getArp()));
		inter.setPeriod(new BigDecimal(this.getTimeLimit()));
		if (this.getStyle() == 4) {
			inter.setCategory(1);
		} else {
			inter.setCategory(0);
		}
		inter.setStyle(this.getStyle());
		BigDecimal income = CalculatorServiceTool.getInterest(inter);
		if (income != null) {
			income.setScale(2, BigDecimal.ROUND_HALF_UP);
			return income;
		}
		return new BigDecimal(0.00);
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public Float getArp() {
		return arp;
	}

	public void setArp(Float arp) {
		this.arp = arp;
	}

	public Integer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Integer getIsTransfer() {
		return isTransfer;
	}

	public void setIsTransfer(Integer isTransfer) {
		this.isTransfer = isTransfer;
	}

	public Integer getStyle() {
		return style;
	}

	public void setStyle(Integer style) {
		this.style = style;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsernamep() {
		return usernamep;
	}

	public void setUsernamep(String usernamep) {
		this.usernamep = usernamep;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public BigDecimal getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(BigDecimal bidAmount) {
		this.bidAmount = bidAmount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer gettId() {
		return tId;
	}

	public void settId(Integer tId) {
		this.tId = tId;
	}

	public Integer getTenderUserId() {
		return tenderUserId;
	}

	public void setTenderUserId(Integer tenderUserId) {
		this.tenderUserId = tenderUserId;
	}	
	
}
