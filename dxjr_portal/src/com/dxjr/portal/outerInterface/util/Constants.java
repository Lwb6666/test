package com.dxjr.portal.outerInterface.util;

public class Constants {

	public static String CODE = "200";
	public static String MESSAGE = "success";
	
	/*
	 * 秘钥
	 * 测试环境123456781234567812345678
	 * 正式环境2WHEGjUuBAs0g7xNBZHp3cJO
	 */
	public static String KEY = "123456781234567812345678";
	
	/*
	 * 商户号
	 * 测试环境guochengjinrong_test
	 * 正式环境guochengjinrong
	 */
	public static String APPID = "guochengjinrong_test";	
	
	/*
	 * 用户绑定接口URL
	 * 测试环境http://218.249.39.138:8000/third.php?mod=member_bind
	 * 正式环境http://open.p2peye.com/cps/member_bind
	 */
	public static final String WDTY_MEMBER_BIND_URL = new String("http://218.249.39.138:8000/third.php?mod=member_bind");
	
	/*
	 * 用户解绑接口URL
	 * 测试环境http://218.249.39.138/third.php?mod=member_unbind
	 * 正式环境    无
	 */
	public static final String WDTY_MEMBER_UNBIND_URL = new String("http://218.249.39.138/third.php?mod=member_unbind");
	
}
