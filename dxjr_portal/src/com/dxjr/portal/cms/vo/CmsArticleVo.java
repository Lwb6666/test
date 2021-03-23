package com.dxjr.portal.cms.vo;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.dxjr.utils.DateUtils;

public class CmsArticleVo extends CmsArticle {
	private String channelName;

	private String urlCode;

	private String createTimeStr;
	private String createTimeStr2;
	
	private String tags;

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getCreateTimeStr() {
		if (getCreateTime() != null) {
			return DateUtils.format(getUpdateTime(), DateUtils.YMD_HMS);
		}
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	public String getCreateTimeStr2() {
		//当年的，显示月-日；非当年的，显示年月日；
		if (getCreateTime() != null) {
			String thieYear = DateFormatUtils.format(new Date(), "yyyy");
			
			if(DateFormatUtils.format(getUpdateTime(), "yyyy").equals(thieYear))
			{
				return DateUtils.format(getUpdateTime(), DateUtils.MD_DASH);
			}else
			{
				return DateUtils.format(getUpdateTime(), DateUtils.YMD_DASH);
			}
		}
		return createTimeStr2;
	}

	@Override
	public String getTags() {
		return tags;
	}
	@Override
	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getUrlCode() {
		return urlCode;
	}

	public void setUrlCode(String urlCode) {
		this.urlCode = urlCode;
	}

}