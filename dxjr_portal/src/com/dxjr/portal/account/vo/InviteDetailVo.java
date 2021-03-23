package com.dxjr.portal.account.vo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dxjr.base.entity.InviteDetail;
import com.dxjr.utils.StrinUtils;

public class InviteDetailVo extends InviteDetail {

	private String invitedUsername; // 被推荐人用户名
	private BigDecimal awardMoney; // 赠送金额
	private Integer mobilePassed; // 手机是否通过验证【0：未通过；1：通过】
	private Integer emailPassed; // 邮箱是否通过验证【0：未通过，1：通过】
	private Integer realnamePassed; // 实名认证是否通过验证 【-1：审核不通过，0：等待审核，1：审核通过】
	private Integer vipPassed; // VIP状态【1：正常；-1：失效】
	private Integer rechargePassed; // 充值是否满1000【1：是；0：否】
	private Integer tenderPassed; // 投资是否满1000【1：是；0：否】
	
	private String userNameSecret;  //隐藏用户名
	private String isViewCash;//根据上线时间判断金额显示现金还是红包
	private BigDecimal rewardMonry;//共享收益
	
	public BigDecimal getRewardMonry() {
		return rewardMonry;
	}
	public void setRewardMonry(BigDecimal rewardMonry) {
		this.rewardMonry = rewardMonry;
	}
	public String getInvitedUsername() {
		return invitedUsername;
	}
	public void setInvitedUsername(String invitedUsername) {
		this.invitedUsername = invitedUsername;
	}
	public BigDecimal getAwardMoney() {
		return awardMoney;
	}
	public void setAwardMoney(BigDecimal awardMoney) {
		this.awardMoney = awardMoney;
	}
	public Integer getMobilePassed() {
		return mobilePassed;
	}
	public void setMobilePassed(Integer mobilePassed) {
		this.mobilePassed = mobilePassed;
	}
	public Integer getEmailPassed() {
		return emailPassed;
	}
	public void setEmailPassed(Integer emailPassed) {
		this.emailPassed = emailPassed;
	}
	public Integer getRealnamePassed() {
		return realnamePassed;
	}
	public void setRealnamePassed(Integer realnamePassed) {
		this.realnamePassed = realnamePassed;
	}
	public Integer getVipPassed() {
		return vipPassed;
	}
	public void setVipPassed(Integer vipPassed) {
		this.vipPassed = vipPassed;
	}
	public Integer getRechargePassed() {
		return rechargePassed;
	}
	public void setRechargePassed(Integer rechargePassed) {
		this.rechargePassed = rechargePassed;
	}
	public Integer getTenderPassed() {
		return tenderPassed;
	}
	public void setTenderPassed(Integer tenderPassed) {
		this.tenderPassed = tenderPassed;
	}
	public String getUserNameSecret() {
		userNameSecret = this.getInvitedUsername();
		userNameSecret = StrinUtils.stringSecretTo(userNameSecret);
		return userNameSecret;
	}
	public String getIsViewCash() {
		String isViewCash="0";
	    //为方便测试暂停上线时间为今天中午，待上线时间确认后再修改
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date LineTime=sdf.parse("2015-12-01 00:00:00");
			if(null!=this.getRegisterTime()){
				if(this.getRegisterTime().after(LineTime)){
					isViewCash="1";
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return isViewCash;
	}
	public void setIsViewCash(String isViewCash) {
		this.isViewCash = isViewCash;
	}
}
