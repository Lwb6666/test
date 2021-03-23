package com.dxjr.portal.cms.vo;

import java.util.Date;

/**
 * <p>
 * Description:cms文章评论<br />
 * </p>
 * 
 * @author 陈鹏
 * @version 0.1 2015年2月5日
 */
public class CmsComment {
	
	private Integer id;
	private Integer articleId;
	private String content;
	private Integer parentId;
	private Integer createBy = 0;
	private Integer status = 0;
	private Date createTime = new Date();
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}