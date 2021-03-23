package com.dxjr.portal.account.vo;

import java.io.Serializable;

import com.dxjr.common.page.BaseCnd;

/**
 * <p>
 * Description:股东加权待收vo<br />
 * </p>
 * 
 * @title ShareholderRankVo.java
 * @package com.dxjr.portal.account.vo
 * @author justin.xu
 * @version 0.1 2014年5月20日
 */
public class ShareholderRankCnd extends BaseCnd implements Serializable {
	private static final long serialVersionUID = -6777678878186683130L;
	/**
	 * 主键ID
	 */
	private Integer id;
	/** 用户ID　 **/
	private Integer userId;
	/** 添加时间 **/
	private String addtime;
	/**
	 * 综合排名
	 */
	private Integer totalRank;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 排名方式（1：按加权待收排名 2：按总积分收排名 3：按直通车认购总额排名 4：推广人数排名 5：综合排名）
	 */
	private String type;

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

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public Integer getTotalRank() {
		return totalRank;
	}

	public void setTotalRank(Integer totalRank) {
		this.totalRank = totalRank;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
