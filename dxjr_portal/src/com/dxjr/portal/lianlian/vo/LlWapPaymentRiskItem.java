package com.dxjr.portal.lianlian.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:手机支付信息风控参数(商户提交参数)<br />
 * </p>
 * 
 * @title LlWapPaymentRequest.java
 * @package com.dxjr.portal.lianlian.vo
 * @author justin.xu
 * @version 0.1 2015年3月25日
 */
public class LlWapPaymentRiskItem implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 商品类目 */
	private String frms_ware_category;
	/** 商品用户唯一标识 */
	private String user_info_mercht_userno;
	/** 绑定手机号 */
	private String user_info_bind_phone;
	/** 注册时间 */
	private String user_info_dt_register;
	/** 用户注册姓名 */
	private String user_info_full_name;
	/**
	 * 用户注册证件类型 0:身份证或企业经营证件 1：户口簿 2：护照 3：军官证 4：士兵证 5：港澳居民来往内地通行证 6：台湾同胞来往内地通行证
	 * 7：临时身份证 8：外国人居住证 9：警官证 X:其它证件 默认为0
	 */
	private String user_info_id_type;
	/** 用户注册证件号码 */
	private String user_info_id_no;
	/**
	 * 是否实名认证 1:是 0：无认证 商户自身是否对用户信息进行实名认证
	 */
	private String user_info_identify_state;

	/**
	 * 实名认证方式 是实名认证时，必填 1：银行卡认证 2：现场认证 3：身份证远程认证 4：其它认证
	 */
	private String user_info_identify_type;

	public String getFrms_ware_category() {
		return frms_ware_category;
	}

	public void setFrms_ware_category(String frms_ware_category) {
		this.frms_ware_category = frms_ware_category;
	}

	public String getUser_info_mercht_userno() {
		return user_info_mercht_userno;
	}

	public void setUser_info_mercht_userno(String user_info_mercht_userno) {
		this.user_info_mercht_userno = user_info_mercht_userno;
	}

	public String getUser_info_bind_phone() {
		return user_info_bind_phone;
	}

	public void setUser_info_bind_phone(String user_info_bind_phone) {
		this.user_info_bind_phone = user_info_bind_phone;
	}

	public String getUser_info_dt_register() {
		return user_info_dt_register;
	}

	public void setUser_info_dt_register(String user_info_dt_register) {
		this.user_info_dt_register = user_info_dt_register;
	}

	public String getUser_info_full_name() {
		return user_info_full_name;
	}

	public void setUser_info_full_name(String user_info_full_name) {
		this.user_info_full_name = user_info_full_name;
	}

	public String getUser_info_id_type() {
		return user_info_id_type;
	}

	public void setUser_info_id_type(String user_info_id_type) {
		this.user_info_id_type = user_info_id_type;
	}

	public String getUser_info_id_no() {
		return user_info_id_no;
	}

	public void setUser_info_id_no(String user_info_id_no) {
		this.user_info_id_no = user_info_id_no;
	}

	public String getUser_info_identify_state() {
		return user_info_identify_state;
	}

	public void setUser_info_identify_state(String user_info_identify_state) {
		this.user_info_identify_state = user_info_identify_state;
	}

	public String getUser_info_identify_type() {
		return user_info_identify_type;
	}

	public void setUser_info_identify_type(String user_info_identify_type) {
		this.user_info_identify_type = user_info_identify_type;
	}

}
