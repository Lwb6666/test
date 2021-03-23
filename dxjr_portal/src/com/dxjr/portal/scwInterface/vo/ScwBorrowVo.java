package com.dxjr.portal.scwInterface.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author WangQianJin
 * @title 生菜网可投标列表
 * @date 2016年5月5日
 */
public class ScwBorrowVo implements Serializable{


	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 4987204208162078055L;
	
	//标的id
	private String pid;
	//标的名称
	private String pname;
	//标的描述
	private String desc;	
	//合作平台标识（如合作方为顶玺金融，则值可为“顶玺金融”或者“jrq”或者其他字符串），合作平台在生菜网的唯一标识。需要和注册信息读取接口中参数中的from值及用户投资记录接口中的from值保持一致
	private String from;
	//年化收益(带%和不带%号都可以)
	private String rate;
	//投资周期 （按月为单位）
	private String cycle;	
	//借款总额 (按人民币元为单位)
	private BigDecimal p_sum;	
	//平台标的链接
	private String url;	
	//平台Wap站的标的链接（取决于合作平台是否有wap站）：生菜网有APP，可以让用户在手机端直接跳转到合作平台的H5标的页，提高用户体验
	private String url_h5;	
	//开放时间
	private String start_time;	
	//还款方式
	private String paytype;	
	//起投金额
	private BigDecimal minmoney;	
	//资金保障
	private String guarantee;	
	//剩余投资额 (按人民币元为单位)
	private BigDecimal amountleft;	
	//计息时间
	private String interest_time;
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getCycle() {
		return cycle;
	}
	public void setCycle(String cycle) {
		this.cycle = cycle;
	}
	public BigDecimal getP_sum() {
		return p_sum;
	}
	public void setP_sum(BigDecimal p_sum) {
		this.p_sum = p_sum;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl_h5() {
		return url_h5;
	}
	public void setUrl_h5(String url_h5) {
		this.url_h5 = url_h5;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public BigDecimal getMinmoney() {
		return minmoney;
	}
	public void setMinmoney(BigDecimal minmoney) {
		this.minmoney = minmoney;
	}
	public String getGuarantee() {
		return guarantee;
	}
	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}
	public BigDecimal getAmountleft() {
		return amountleft;
	}
	public void setAmountleft(BigDecimal amountleft) {
		this.amountleft = amountleft;
	}
	public String getInterest_time() {
		return interest_time;
	}
	public void setInterest_time(String interest_time) {
		this.interest_time = interest_time;
	}
	
	
	

}
