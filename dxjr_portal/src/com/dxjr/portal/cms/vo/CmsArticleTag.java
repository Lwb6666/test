package com.dxjr.portal.cms.vo;

public class CmsArticleTag {

	public CmsArticleTag(Integer articleId, Integer tagId, Integer channelId) {
		super();
		setTagId(tagId);
		setArticleId(articleId);
		this.channelId = channelId;
	}

	private Integer channelId;

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	private Integer articleId;

	private Integer tagId;

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public CmsArticleTag() {
		super();

	}



}