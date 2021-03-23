package com.dxjr.portal.test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.utils.zucp.ZucpSmsUtil;

/**
 * <p>
 * Description:给系统用户群发发送短信<br />
 * </p>
 * 
 * @title SendSmsTest.java
 * @package com.dxjr.portal.test
 * @author 邹理享
 * @version 0.1 2015年1月4日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext*.xml" })
public class SendSmsTest {

	@Autowired
	private MobileApproService mobileApproService;

	@Test
	public void testSendSms() throws UnsupportedEncodingException, Exception {

		int splitSize = 5000;
        int result=0;
        int group=0;
		List<String> mobiles = productData();
		if (mobiles.size() % splitSize != 0) {
			group=1;
		}
		result = mobiles.size() / splitSize;
		result += group;
		System.out.println("一共" + mobiles.size() + "个手机号");
		for (int i = 0; i < result; i++) {
			List<String> mobiles1 = null;
			if (i == result - 1) {
				mobiles1 = mobiles.subList(i * splitSize, mobiles.size());
			} else {
				mobiles1 = mobiles.subList(i * splitSize, (i + 1) * splitSize - 1);
			}
			String mobilesstr = getDotStr(mobiles1);
			System.out.println("第" + (i + 1) + "组手机号：" + mobilesstr);
			// 调用发送短信接口
			// String content =
			// "尊敬的顶玺金融用户，由于3月30日中午顶玺金融网站遭受攻击，导致官网无法访问，目前技术部门正在积极修复，在此向您表示由衷的歉意。 今天21:00前的提现申请我们会在当天进行处理打款，给您带来的不便敬请谅解！如有疑问请联系在线客服或拨打（400-000-0000）进行咨询，退订回复td【顶玺金融】";
			// 发送短信
			// ZucpSmsUtil.sendSms(URLEncoder.encode(content, "UTF-8"),
			// mobilesstr);
			System.out.println("提交成功");
		}
		


	}

	private List<String> productData() {
		List<String> mobiles = mobileApproService.querySendSmsMobile();
		return mobiles;
	}

	/**
	 * 将List中的数据组成用逗号分隔的字符串，如'a','b','c'
	 */
	public static String getDotStr(List<String> strList) {
		String resultStr = "";
		if (strList != null && strList.size() > 0) {
			for (int i = 0; i < strList.size(); i++) {
				resultStr = resultStr + strList.get(i) + ',';
			}
			resultStr = resultStr.substring(0, resultStr.length() - 1);
		}
		return resultStr;
	}

	public static void main(String[] args) throws UnsupportedEncodingException, Exception {
		// String content =
		// "尊敬的顶玺金融用户，由于公司将上线移动端充值功能以及采用第三方支付打款给提现的用户。不在支持列表里的银行卡将不能提现。请您重新绑定您在顶玺金融的银行卡或者联系客服400-000-0000。给您带来不便敬请谅解！谢谢！顶玺金融运营部。【顶玺金融】";
		// ZucpSmsUtil.sendSms(URLEncoder.encode(content, "UTF-8"),
		// "18606277695,18918785887,13636585212,18616966903,18717817751,13918133823,13601910734");
	}

}
