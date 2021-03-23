package com.dxjr.portal.account.vo;

import java.io.Serializable;

import com.dxjr.portal.member.vo.MemberApproVo;

/**
 * <p>
 * Description:我的账户认证返回信息类<br />
 * </p>
 * 
 * @title MyAccountApproMsgVo.java
 * @package com.dxjr.portal.account.vo
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
public class MyAccountApproMsgVo implements Serializable {

	private static final long serialVersionUID = -3531851612222097637L;
	/** 返回的信息 */
	private String msg;
	/** 返回的认证链接 */
	private String linkUrl;
	/** 返回的认证链接名字 */
	private String linkName;
	/** 返回的结果,用于判断是否通过了认证 */
	private String result;
	/** 用户认证信息情况 */
	private MemberApproVo memberApproVo;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public MemberApproVo getMemberApproVo() {
		return memberApproVo;
	}

	public void setMemberApproVo(MemberApproVo memberApproVo) {
		this.memberApproVo = memberApproVo;
	}

}
