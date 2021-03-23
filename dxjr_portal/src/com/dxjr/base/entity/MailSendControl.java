package com.dxjr.base.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <p>
 * Description:邮件发送控制<br />
 * </p>
 * 
 * @title MailSendControl.java
 * @package com.dxjr.base.entity
 * @author yangshijin
 * @version 0.1 2014年9月15日
 */
public class MailSendControl implements Serializable {

	private static final long serialVersionUID = -2280725937015053259L;

	/** 主键Id */
	private Integer id;
	/** 类型（1：满标发邮件，2：注册实名认证，3：还款提醒） */
	private Integer type;
	/** 匹配日期 */
	private Date indate;
	/** 邮件总共次数 */
	private Integer mailTotalNum;
	/** 邮件已使用次数 */
	private Integer mailYesNum;
	/** 邮件剩余次数 */
	private Integer mailNoNum;
	/** 添加时间 */
	private Date addtime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getIndate() {
		return indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}

	public Integer getMailTotalNum() {
		return mailTotalNum;
	}

	public void setMailTotalNum(Integer mailTotalNum) {
		this.mailTotalNum = mailTotalNum;
	}

	public Integer getMailYesNum() {
		return mailYesNum;
	}

	public void setMailYesNum(Integer mailYesNum) {
		this.mailYesNum = mailYesNum;
	}

	public Integer getMailNoNum() {
		return mailNoNum;
	}

	public void setMailNoNum(Integer mailNoNum) {
		this.mailNoNum = mailNoNum;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
}
