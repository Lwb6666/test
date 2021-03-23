package com.dxjr.portal.fuiou.util;

import java.util.Iterator;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import com.dxjr.portal.fuiou.vo.FuiouPayBackVo;
import com.dxjr.utils.PropertiesUtil;

/**
 * 富友帮助类
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title FuiouUtil.java
 * @package com.dxjr.portal.fuiou.util
 * @author huangpin
 * @version 0.1 2015年8月20日
 */
public class FuiouUtil {

	public static String mchnt_cd = PropertiesUtil.getValue("fuiou_mchnt_cd");// 商户代码
	public static String mchnt_key = PropertiesUtil.getValue("fuiou_mchnt_key");// 32位的商户密钥
	public static String pay_url = PropertiesUtil.getValue("fuiou_pay_url");// 支付提交地址,通过form表单的形式递交给富友系统，递交的方式必须为post，页面编码必须为UTF-8
	public static String query_pay_url = PropertiesUtil.getValue("fuiou_query_pay_url");// 支付查询

	/**
	 * 验证返回通知是否有效
	 * <p>
	 * Description:商户网站接收到该请求，取出MD5摘要数据。 将返回各域加上系统分配的商户密钥(MD5摘要数据除外)，按照文档顺序拼成一个字符串，用“|”分割。 对这个字符串做MD5摘要。 比较两个MD5值是否相等(不区分大小写)，如果相等，说明该笔通知信息有效。
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年8月20日
	 * @param vo
	 * @return boolean
	 */
	public static boolean checkBackMd5(FuiouPayBackVo vo) {
		String pins = vo.getMchnt_cd() + "|" + vo.getOrder_id() + "|" + vo.getOrder_date() + "|" + vo.getOrder_amt() + "|" + vo.getOrder_st() + "|" + vo.getOrder_pay_code() + "|"
				+ vo.getOrder_pay_error() + "|" + vo.getResv1() + "|" + vo.getFy_ssn() + "|" + mchnt_key;
		String md5 = DigestUtils.md5Hex(pins);
		if (vo.getMd5().equals(md5)) {
			return true;
		}
		return false;
	}

	/**
	 * 生成表单字符串
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年8月20日
	 * @param object
	 * @param formId 表单ID
	 * @param isNoEncode 转码
	 * @return String
	 */
	public static String generateForm(Object object, String formId, String isNoEncode) {
		BeanMap params = new BeanMap(object);
		StringBuilder formBuilder = new StringBuilder();
		formBuilder.append("<form id=\"" + formId + "\" action=\"" + pay_url + "\" method=\"post\">\r\n");
		Iterator<?> keyIterator = params.keyIterator();
		while (keyIterator.hasNext()) {
			String propertyName = (String) keyIterator.next();
			Object value = params.get(propertyName);
			if (value != null && value instanceof String) {
				formBuilder.append("<input type=\"hidden\" name=\"").append(propertyName).append("\" value=\'");
				if (null != isNoEncode) {
					formBuilder.append((String) value).append("\' />\r\n");
				} else {
					formBuilder.append(StringEscapeUtils.escapeHtml4((String) value)).append("\' />\r\n");
				}
			}
		}
		formBuilder.append("</form>");
		return formBuilder.toString();
	}

	/**
	 * 查询支付参数
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年8月21日
	 * @param order_id
	 * @return String
	 */
	public static String queryPayArguments(String order_id) {
		String pins = mchnt_cd + "|" + order_id + "|" + mchnt_key;
		return "?md5=" + DigestUtils.md5Hex(pins) + "&mchnt_cd=" + mchnt_cd + "&order_id=" + order_id;
	}

	/**
	 * 获取xml文件标签内容
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年8月21日
	 * @param xml
	 * @param label
	 * @return String
	 */
	public static String getXmlCode(String xml, String label) {
		return xml.substring(xml.indexOf("<" + label + ">") + ("<" + label + ">").length(), xml.indexOf("</" + label + ">"));
	}

	/*
	 * public static String hex(String[] strs, String key) { Arrays.sort(strs); StringBuffer source = new StringBuffer(); for (String s : strs) { source.append(s).append("|"); } String bigstr =
	 * source.substring(0, source.length() - 1); System.out.println("bigstr---------->" + bigstr); String result = DigestUtils.shaHex(DigestUtils.shaHex(bigstr) + "|" + key); return result; }
	 */

	/*
	 * public static String hex(List<String> values, String key) { String[] strs = new String[values.size()]; for (int i = 0; i < strs.length; i++) { strs[i] = values.get(i); } Arrays.sort(strs);
	 * StringBuffer source = new StringBuffer(); for (String s : strs) { System.out.println(s); source.append(s).append("|"); } String bigstr = source.substring(0, source.length() - 1); // String
	 * bigstr = "0|0|0002900F0345178|01|0104|13651744618|510704198605078872|6216610800001265405|AC01|DSF|张三"; System.out.println("bigstr---------->" + bigstr); String result =
	 * DigestUtils.shaHex(DigestUtils.shaHex(bigstr) + "|" + key); // 4add188e3a707dd35b4eb6317986fc981e7357c4 System.out.println("result---------->" + result); return result; }
	 */

	/*
	 * public static void main(String[] args) { String bankCd = "0104"; String userNm = "张三"; String mobileNo = "13651744618"; String credtNo = "510704198605078872"; String acntNo =
	 * "6216610800001265405"; String mchntCd = "0002900F0345178"; String reserved1 = "备注"; String key = "123456"; List<String> values = new ArrayList<String>(); values.add("DSF"); values.add("AC01");
	 * values.add(bankCd); values.add(userNm); values.add(mobileNo); values.add("0"); values.add(credtNo); values.add("01"); values.add(acntNo); values.add(mchntCd); values.add("0");
	 * values.add(reserved1); String signature = FuiouUtil.hex(values, key); }
	 */
}
