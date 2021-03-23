package com.dxjr.portal.account.util;

import com.dxjr.common.Dictionary;

/**
 * <p>
 * Description:获得用户会员等级和比率<br />
 * </p>
 * @title UserLevelRatio.java
 * @package com.dxjr.portal.account.util
 * @author yangshijin
 * @version 0.1 2014年12月18日
 */
public class UserLevelRatio {
	/** 用户id */
	private Integer userid;
	/** 等级 */
	private String o_userLevel;
	/** 比率 */
	private String o_ratio;
	/** 待收元宝倍率 */
	private String o_sycee_ratio;
	/** 等级名称 */
	private String levelName;

	public String getO_sycee_ratio() {
		return o_sycee_ratio;
	}

	public void setO_sycee_ratio(String o_sycee_ratio) {
		this.o_sycee_ratio = o_sycee_ratio;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getO_userLevel() {
		return o_userLevel;
	}

	public void setO_userLevel(String o_userLevel) {
		this.o_userLevel = o_userLevel;
	}

	public String getO_ratio() {
		return o_ratio;
	}

	public void setO_ratio(String o_ratio) {
		this.o_ratio = o_ratio;
	}

	public String getLevelName() {
		if (o_userLevel != null) {
			return Dictionary.getValue(1700, getO_userLevel());
		}
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

}
