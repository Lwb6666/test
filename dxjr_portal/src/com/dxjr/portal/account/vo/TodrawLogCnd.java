package com.dxjr.portal.account.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.dxjr.common.page.BaseCnd;

/**
 * 
 * <p>
 * Description:转可提历史记录<br />
 * </p>
 * 
 * @title TodrawLogCnd.java
 * @package com.dxjr.portal.account.vo
 * @author yangshijin
 * @version 0.1 2014年8月12日
 */
public class TodrawLogCnd extends BaseCnd implements Serializable {
	private static final long serialVersionUID = 2464027643293596116L;

	private Integer id;
	private Integer userId;
	/** 状态【-1：审核失败 0：申请转可提 1：审核通过】 **/
	private Integer status;
	/** 转可提金额 **/
	private BigDecimal money;
	/** 申请时间 **/
	private Date addtime;
	/** 版本号. **/
	private Integer version;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
