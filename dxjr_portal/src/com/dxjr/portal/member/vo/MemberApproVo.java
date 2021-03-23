package com.dxjr.portal.member.vo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dxjr.utils.DateUtils;

/**
 * <p>
 * Description:会员所有认证类Vo<br />
 * </p>
 * 
 * @title MemberApproVo.java
 * @package com.dxjr.portal.member.vo
 * @author justin.xu
 * @version 0.1 2014年5月13日
 */
public class MemberApproVo implements Serializable {
	private static final long serialVersionUID = -5890980057423538506L;
	/** 主键ID */
	private Integer id;
	/** 用户名 */
	private String username;
	/** 是否通过实名认证 【-1：审核不通过，0：等待审核，1：审核通过】 */
	private Integer namePassed;
	/** 是否通手机验证【0：未通过；1：通过】 */
	private Integer mobilePassed;
	/** 是否通过邮箱验证【0：未通过，1：通过】 */
	private Integer emailChecked;
	/** 是否通过VIP审核【0:初始状态；1：审核通过；-1：审核不通过】 */
	private Integer vipPassed;
	/** 是否有银行名称 */
	// private String bankName;

	/** 审核通过时间 */
	private Date approveTime;
	private String approveTimeStr;
	/** 有效截止日期 */
	private Date inDate;
	private String inDateStr;
	/** VIP 是否可以续费 */
	private String isCanRenew;
	/** 是否终身顶级会员 【0：正常，-1：失效】 */
	private Integer svipPassed;
	/** 终身顶级会员 开通时间 */
	private Date svipAddDate;
	private Integer svipOrder;

	/** 用户注册时间 */
	private String userAddtime;
	private Date userAddtimeDate;
	/** 用户手机号码 */
	private String mobilenum;
	/** 用户真实姓名 */
	private String realname;
	/** 证件类型 */
	private String cardtype;
	/** 证件号码 */
	private String idcardno;

	public String getIsCanRenew() {
		if (this.getInDate() == null) {
			return "Y";// 没有通过VIP审核是返回Y,表示可以去开通
		}
		String start_indate = DateUtils.format(DateUtils.monthOffset(this.getInDate(), -3), DateUtils.YMD_DASH);
		if (Long.parseLong(DateUtils.date2TimeStamp(start_indate)) > Long.parseLong(DateUtils.getTime())) {
			return "N";// vip有效期比当前日期大于 3个月，不用续VIP
		} else {
			return "Y";// 可以续vip
		}
	}

	public static List<MemberApproVo> handleBean(List<MemberApproVo> pList, Map<String, Object> mapCondition) {
		List<MemberApproVo> list = new ArrayList<MemberApproVo>();

		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat(",###.##");
		// df.setRoundingMode(RoundingMode.DOWN);

		for (int i = 0; i < pList.size(); i++) {
			MemberApproVo record = pList.get(i);

			if (record.getApproveTime() != null) {
				record.setApproveTimeStr(formatDate.format(record.getApproveTime()));
			}

			if (record.getInDate() != null) {
				record.setInDateStr(formatDate.format(record.getInDate().getTime()));
			}

			list.add(record);
		}
		return list;
	}

	public static MemberApproVo handleBean(MemberApproVo record, Map<String, Object> mapCondition) {

		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		// DecimalFormat df = new DecimalFormat(",###.##");
		// df.setRoundingMode(RoundingMode.DOWN);

		if (record.getApproveTime() != null) {
			record.setApproveTimeStr(formatDate.format(Long.parseLong(record.getApproveTime().toString())));
		}

		if (record.getInDate() != null) {
			record.setInDateStr(formatDate.format(record.getInDate().getTime()));
		}
		return record;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getApproveTimeStr() {
		return approveTimeStr;
	}

	public void setApproveTimeStr(String approveTimeStr) {
		this.approveTimeStr = approveTimeStr;
	}

	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	public String getInDateStr() {
		return inDateStr;
	}

	public void setInDateStr(String inDateStr) {
		this.inDateStr = inDateStr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getNamePassed() {
		return namePassed;
	}

	public void setNamePassed(Integer namePassed) {
		this.namePassed = namePassed;
	}

	public Integer getMobilePassed() {
		return mobilePassed;
	}

	public void setMobilePassed(Integer mobilePassed) {
		this.mobilePassed = mobilePassed;
	}

	public Integer getEmailChecked() {
		return emailChecked;
	}

	public void setEmailChecked(Integer emailChecked) {
		this.emailChecked = emailChecked;
	}

	public Integer getVipPassed() {
		return vipPassed;
	}

	public void setVipPassed(Integer vipPassed) {
		this.vipPassed = vipPassed;
	}

	public Integer getSvipPassed() {
		return svipPassed;
	}

	public void setSvipPassed(Integer svipPassed) {
		this.svipPassed = svipPassed;
	}

	public Date getSvipAddDate() {
		return svipAddDate;
	}

	public void setSvipAddDate(Date svipAddDate) {
		this.svipAddDate = svipAddDate;
	}

	public Integer getSvipOrder() {
		return svipOrder;
	}

	public void setSvipOrder(Integer svipOrder) {
		this.svipOrder = svipOrder;
	}

	// public String getBankName() {
	// return bankName;
	// }
	//
	// public void setBankName(String bankName) {
	// this.bankName = bankName;
	// }

	public String getUserAddtime() {
		return userAddtime;
	}

	public void setUserAddtime(String userAddtime) {
		this.userAddtime = userAddtime;
	}

	public Date getUserAddtimeDate() {
		return DateUtils.parse(DateUtils.timeStampToDate(userAddtime, DateUtils.YMD_HMS), DateUtils.YMD_HMS);
	}

	public String getMobilenum() {
		return mobilenum;
	}

	public void setMobilenum(String mobilenum) {
		this.mobilenum = mobilenum;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public String getIdcardno() {
		return idcardno;
	}

	public void setIdcardno(String idcardno) {
		this.idcardno = idcardno;
	}

	public void setIsCanRenew(String isCanRenew) {
		this.isCanRenew = isCanRenew;
	}

	public void setUserAddtimeDate(Date userAddtimeDate) {
		this.userAddtimeDate = userAddtimeDate;
	}

	/**
	 * <p>
	 * Description:用于返回手机支付的返回值<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年4月7日
	 * @return String
	 */
	public String getCardTypeForPhonePay() {
		// 用户注册证件类型 0:身份证或企业经营证件 1：户口簿 2：护照 3：军官证 4：士兵证 5：港澳居民来往内地通行证
		// 6：台湾同胞来往内地通行证
		// * 7：临时身份证 8：外国人居住证 9：警官证 X:其它证件 默认为0
		String result = "0";
		if (null != cardtype) {
			if ("身份证".equals(cardtype)) {
				return "0";
			} else if ("港澳通行证".equals(cardtype)) {
				return "5";
			} else if ("护照".equals(cardtype)) {
				return "2";
			}
		}
		return result;
	}
}