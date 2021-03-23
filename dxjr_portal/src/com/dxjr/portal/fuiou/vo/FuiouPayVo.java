package com.dxjr.portal.fuiou.vo;

import org.apache.commons.codec.digest.DigestUtils;

import com.dxjr.portal.fuiou.util.FuiouUtil;
import com.dxjr.utils.PropertiesUtil;

/**
 * 富友支付表单参数
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title FuiouPayVo.java
 * @package com.dxjr.portal.fuiou.vo
 * @author huangpin
 * @version 0.1 2015年8月20日
 */
public class FuiouPayVo {

	private String mchnt_cd = FuiouUtil.mchnt_cd;// 商户代码
	private String order_id;// 商户订单号MAX(30)，数字或字母
	private String order_amt;// 交易金额,以分为单位,不可以为零
	private String order_pay_type = "B2C";// 支付类型
	private String page_notify_url = PropertiesUtil.getValue("fuiou_page_notify_url");// 页面跳转URL
	private String back_notify_url = PropertiesUtil.getValue("fuiou_back_notify_url");// 后台通知URL，如果page和back都填了，富友会调用back2次，暂时不做配置
	private String order_valid_time = "10m";// 超时时间:1m-15天，m：分钟、h：小时、d天、1c当天有效，
	private String iss_ins_cd;// 银行代码
	private String goods_name = "";// 商品名称
	private String goods_display_url = "";// 商品展示网址
	private String rem = "";// 备注
	private String ver = PropertiesUtil.getValue("fuiou_ver");// 版本号,目前填1.0.1
	private String md5;// MD5摘要数据

	public String getMchnt_cd() {
		return mchnt_cd;
	}

	public void setMchnt_cd(String mchnt_cd) {
		this.mchnt_cd = mchnt_cd;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_amt() {
		return order_amt;
	}

	public void setOrder_amt(String order_amt) {
		this.order_amt = order_amt;
	}

	public String getOrder_pay_type() {
		return order_pay_type;
	}

	public void setOrder_pay_type(String order_pay_type) {
		this.order_pay_type = order_pay_type;
	}

	public String getPage_notify_url() {
		return page_notify_url;
	}

	public void setPage_notify_url(String page_notify_url) {
		this.page_notify_url = page_notify_url;
	}

	public String getBack_notify_url() {
		return back_notify_url;
	}

	public void setBack_notify_url(String back_notify_url) {
		this.back_notify_url = back_notify_url;
	}

	public String getOrder_valid_time() {
		return order_valid_time;
	}

	public void setOrder_valid_time(String order_valid_time) {
		this.order_valid_time = order_valid_time;
	}

	public String getIss_ins_cd() {
		return iss_ins_cd;
	}

	public void setIss_ins_cd(String iss_ins_cd) {
		this.iss_ins_cd = iss_ins_cd;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getGoods_display_url() {
		return goods_display_url;
	}

	public void setGoods_display_url(String goods_display_url) {
		this.goods_display_url = goods_display_url;
	}

	public String getRem() {
		return rem;
	}

	public void setRem(String rem) {
		this.rem = rem;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getMd5() {
		String pins = mchnt_cd + "|" + order_id + "|" + order_amt + "|" + order_pay_type + "|" + page_notify_url + "|" + back_notify_url + "|" + order_valid_time + "|" + iss_ins_cd + "|" + goods_name
				+ "|" + goods_display_url + "|" + rem + "|" + ver + "|" + FuiouUtil.mchnt_key;
		md5 = DigestUtils.md5Hex(pins);
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

}
