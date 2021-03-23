package com.dxjr.portal.cms.vo;

import java.io.Serializable;

import com.dxjr.common.page.BaseCnd;

public class CmsArticleCnd extends BaseCnd implements Serializable {

	/**
	 * @fields serialVersionUID
	 */

	private static final long serialVersionUID = 1L;

	private Integer channelId;

    private String title;

	private String author;
	
	private Integer lave;

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

	public Integer getLave() {
		return lave;
	}
	public void setLave(Integer lave) {
		this.lave = lave;
	}

}