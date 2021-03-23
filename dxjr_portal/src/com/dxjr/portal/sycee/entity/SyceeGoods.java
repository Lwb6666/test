package com.dxjr.portal.sycee.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 元宝商品
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title SyceeGoods.java
 * @package com.dxjr.portal.sycee.controller.entity
 * @author huangpin
 * @version 0.1 2015年10月22日
 */
public class SyceeGoods implements Serializable {

	private static final long serialVersionUID = -1826435038154466525L;

	private Integer id;
	private String name;// 商品名称
	private Float money;// 实际价值
	private Integer oldSycee;// 兑换所需元宝(原价)
	private Float discount;// 折扣
	private Integer sycee;// 兑换所需元宝
	private Integer num;// 数量，目前只能为1
	private Integer firstClass;// 商品一级分类：1线上，2线下
	private Integer secondClass;// 商品二级分类：11 红包 12抽奖 21 电影 22 实体
	private Integer showFlag;// 商品上下架：1上架，2下架
	private String imgurl;// 商品图片路径
	private String useExp;// 使用说明，使用范围
	private String useWay;// 使用方法
	private String exchangeExp;// 兑换说明，兑换方法
	private Integer validDay;// 有效天数,0代表长期有效
	private Integer adduser;
	private Date addtime;
	private String addusername;
	private Integer approveUser;
	private String approveUsername;
	private Integer approveStatus;// 0未审核；1审核通过；2审核不通过
	private Date approveTime;
	private String approveRemark;
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public Integer getSycee() {
		return sycee;
	}

	public void setSycee(Integer sycee) {
		this.sycee = sycee;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getFirstClass() {
		return firstClass;
	}

	public void setFirstClass(Integer firstClass) {
		this.firstClass = firstClass;
	}

	public Integer getSecondClass() {
		return secondClass;
	}

	public void setSecondClass(Integer secondClass) {
		this.secondClass = secondClass;
	}

	public Integer getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(Integer showFlag) {
		this.showFlag = showFlag;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getUseExp() {
		return useExp;
	}

	public void setUseExp(String useExp) {
		this.useExp = useExp;
	}

	public String getUseWay() {
		return useWay;
	}

	public void setUseWay(String useWay) {
		this.useWay = useWay;
	}

	public String getExchangeExp() {
		return exchangeExp;
	}

	public void setExchangeExp(String exchangeExp) {
		this.exchangeExp = exchangeExp;
	}

	public Integer getValidDay() {
		return validDay;
	}

	public void setValidDay(Integer validDay) {
		this.validDay = validDay;
	}

	public Integer getAdduser() {
		return adduser;
	}

	public void setAdduser(Integer adduser) {
		this.adduser = adduser;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAddusername() {
		return addusername;
	}

	public void setAddusername(String addusername) {
		this.addusername = addusername;
	}

	public Integer getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(Integer approveUser) {
		this.approveUser = approveUser;
	}

	public String getApproveUsername() {
		return approveUsername;
	}

	public void setApproveUsername(String approveUsername) {
		this.approveUsername = approveUsername;
	}

	public Integer getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(Integer approveStatus) {
		this.approveStatus = approveStatus;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getApproveRemark() {
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOldSycee() {
		return oldSycee;
	}

	public void setOldSycee(Integer oldSycee) {
		this.oldSycee = oldSycee;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

}
