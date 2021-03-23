package com.dxjr.wx.account.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.dxjr.common.Dictionary;
import com.dxjr.common.page.BaseCnd;

/**
 * 红包
 * <p>
 * Description:红包类<br />
 * </p>
 * @title RedAccountVo.java
 * @package com.dxjr.wx.account.vo
 * @author wushaoling
 * @version 0.1 2016年5月18日
 */
public class WxRedAccountVo  extends BaseCnd{

	private static final long serialVersionUID = 8920693453057286413L;

	private Integer id;
	private Integer userId;// 用户ID
	private Integer usebizId;// 目标业务ID，包含定期宝、直通车、手动投标ID
	private Integer redType;// 红包类型 1910生日红包;1920推荐成功红包；1930贵宾特权红包；1940被推荐成功红包；1950元宝兑换红包；1960抽奖红包
	private Integer usebizType;// 1-认购定期宝，2-认购直通车，3-认购标的
	private BigDecimal money;// 红包金额
	private Date addTime;// 获取时间
	private Date openTime;// 开启时间
	private Date endTime;// 到期时间
	private Date freezeTime;// 冻结时间
	private Date useTime;// 使用时间
	private Integer status;// 状态：1未开启；2未使用；3已冻结；4已使用；5已过期
	private Date lastUpdateTime;// 最后更新时间
	private Integer flag;// 查看红包最新记录的标识 1表示最新记录的位置
	private Integer inviterId;// 贵宾推荐表ID
	private String remark;// 备注
	private String openCondition;// 开启条件
	
	public String getOpenCondition() {
		return openCondition;
	}
	public void setOpenCondition(String openCondition) {
		this.openCondition = openCondition;
	}
	public String getRedSource(){
		if(this.redType!=null&&this.redType>0){
			return Dictionary.getValue(1900,this.redType.toString());// 红包类型;

		}
		return "";
	}
	public String getViewStatus(){
		if(this.redType==1930 && this.status==1){
			return "0";//显示未开启
		}else if(this.redType==1930 && this.status==2){
			return "1";//显示已开启
		}else{
			return "2";//不显示文字
		}
	}
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

	public Integer getUsebizId() {
		return usebizId;
	}

	public void setUsebizId(Integer usebizId) {
		this.usebizId = usebizId;
	}

	public Integer getRedType() {
		return redType;
	}

	public void setRedType(Integer redType) {
		this.redType = redType;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getFreezeTime() {
		return freezeTime;
	}

	public void setFreezeTime(Date freezeTime) {
		this.freezeTime = freezeTime;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getInviterId() {
		return inviterId;
	}

	public void setInviterId(Integer inviterId) {
		this.inviterId = inviterId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getUsebizType() {
		return usebizType;
	}

	public void setUsebizType(Integer usebizType) {
		this.usebizType = usebizType;
	}
}
