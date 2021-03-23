package com.dxjr.portal.red.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 红包规则
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title RedAccount.java
 * @package com.dxjr.portal.red.entity
 * @author liutao
 * @version 0.1 2015年12月7日
 */
public class RedRule implements Serializable {
	private static final long serialVersionUID = 8920693453057286413L;
	private Integer id;
	private String redType;// 红包类型 1910生日红包;1920推荐成功红包；1930贵宾特权红包；1940被推荐成功红包；1950元宝兑换红包；1960抽奖红包[1920][1930]或者不限
	private String redMoney;//该规则适用的红包面额：[10][20][30]或者不限
	private Integer useType;//可投类型(1：定期宝，2：直通车，3：标的)：[1][2]或者不限
	private Integer useNew;//用于新人专享：1 可用，0 不可用
	private Integer useApp;//用于移动端：1 可用，0 不可用
	private String useMonth;//投宝、标期限：[1][3][6]或者不限
	private Integer useMultiple;//起投金额倍数：默认100
	private String useBidType;//可投标的类型(1:抵用标，2：信用标，3：担保标)：[1][2][3]或者不限
	private Integer useAuto;//用于自动：1 可用，0 不可用
	private Date addTime;// 添加时间
	private String remark;// 备注
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRedType() {
		return redType;
	}
	public void setRedType(String redType) {
		this.redType = redType;
	}
	public String getRedMoney() {
		return redMoney;
	}
	public void setRedMoney(String redMoney) {
		this.redMoney = redMoney;
	}
	public Integer getUseType() {
		return useType;
	}
	public void setUseType(Integer useType) {
		this.useType = useType;
	}
	public Integer getUseNew() {
		return useNew;
	}
	public void setUseNew(Integer useNew) {
		this.useNew = useNew;
	}
	public Integer getUseApp() {
		return useApp;
	}
	public void setUseApp(Integer useApp) {
		this.useApp = useApp;
	}
	public String getUseMonth() {
		return useMonth;
	}
	public void setUseMonth(String useMonth) {
		this.useMonth = useMonth;
	}
	public Integer getUseMultiple() {
		return useMultiple;
	}
	public void setUseMultiple(Integer useMultiple) {
		this.useMultiple = useMultiple;
	}
	public String getUseBidType() {
		return useBidType;
	}
	public void setUseBidType(String useBidType) {
		this.useBidType = useBidType;
	}
	public Integer getUseAuto() {
		return useAuto;
	}
	public void setUseAuto(Integer useAuto) {
		this.useAuto = useAuto;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
