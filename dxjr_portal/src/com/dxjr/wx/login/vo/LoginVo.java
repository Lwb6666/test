/**   
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title LoginVo.java
 * @package com.dxjr.wx.login.vo 
 * @author ZHUCHEN
 * @version 0.1 2014年11月5日
 */
package com.dxjr.wx.login.vo;

/**
 * 登录时用于传递到WX的参数类
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title LoginVo.java
 * @package com.dxjr.wx.login.vo
 * @author ZHUCHEN
 * @version 0.1 2014年11月5日
 */

public class LoginVo {
	/** 微信号 */
	private String openId;
	/** 微信关注表的id */
	private Integer wxId;

	public LoginVo() {
	}

	public LoginVo(String openid, Integer wid) {
		openId = openid;
		wxId = wid;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getWxId() {
		return wxId;
	}

	public void setWxId(Integer wxId) {
		this.wxId = wxId;
	}
}
