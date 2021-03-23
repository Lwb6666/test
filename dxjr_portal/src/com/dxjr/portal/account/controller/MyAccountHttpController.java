package com.dxjr.portal.account.controller;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.portal.account.service.MyAccountService;
import com.dxjr.portal.account.vo.VipRemindVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;

/**
 * <p>
 * Description:我的帐号控制类，非https请求<br />
 * </p>
 * 
 * @title MyAccountHttpController.java
 * @package com.dxjr.portal.account.controller
 * @author justin.xu
 * @version 0.1 2015年2月2日
 */
@Controller
@RequestMapping
public class MyAccountHttpController extends BaseController {

	public Logger logger = Logger.getLogger(MyAccountHttpController.class);

	// vip提醒cache配置名字
	private static final String vipCache = "vipCache";

	@Autowired
	private MyAccountService myAccountService;
	@Autowired
	private MemberService memberService;

	@Autowired
	private CacheManager cacheManager;

	// 查询vip提醒保存的缓存信息 | 1），有,是同一天的，返回存在；2）， 有，不是同一天的，重置缓存，返回不存在；3），没缓存的，设置缓存；
	@RequestMapping(value = "getRemindStatus")
	@ResponseBody
	public MessageBox getRemindStatus() throws Exception {

		ShiroUser shiroUser = currentUser();

		if (shiroUser == null) {
			return new MessageBox("-1", "");
		}

		String msg = "";
		VipRemindVo vipRemindVo2 = new VipRemindVo(); // 缓存内容实体类；

		String key = shiroUser.getUserId().toString();

		Cache cache = cacheManager.getCache(vipCache);
		ValueWrapper valueWrapper = cache.get(key);
		if (valueWrapper != null) {

			// Long time = (Long) valueWrapper.get();
			vipRemindVo2 = (VipRemindVo) valueWrapper.get();

			int remindNum2 = vipRemindVo2.getRemindNum();
			Long remindTime2 = vipRemindVo2.getRemindTime();

			// 每天提醒一次的情形，
			// 获得时间：同一天，返回1； | 不是同一天，重置cache；
			if (remindNum2 == 1) {
				if (org.apache.commons.lang3.time.DateUtils.isSameDay(new Date(remindTime2), new Date())) {
					return new MessageBox("1", "");
				} else {
					msg = setRemindStatus();
					return new MessageBox("0", msg);
				}
			}
			// 只提醒一次的情形， | 存在,判断日期间隔是否还大于7，大于不再提醒；小于等于，重新设置缓存等；
			if (remindNum2 == 2) {
				if (DateUtils.dayDiffByDay(new Date(remindTime2), new Date()) > 7) {
					return new MessageBox("1", "");
				} else {
					msg = setRemindStatus();
					return new MessageBox("0", msg);
				}
			}

		} else { // 无缓存的，建立缓存，返回相应的提示；
			msg = setRemindStatus();
			return new MessageBox("0", msg);
		}
		return null;

	}

	// 设置缓存信息，提醒状态 | 提前前，判断实名(从实名表查询数据)；
	public String setRemindStatus() throws Exception {
		// ShiroUser shiroUser = currentUser();

		String msg = "";
		// String msg2 = "";
		/*
		 * MemberApproVo memberApproVo =
		 * memberService.queryMemberApproByUserId(shiroUser.getUserId());
		 * 
		 * if (memberApproVo.getVipPassed() != null) { String start_indate =
		 * DateUtils.format(DateUtils.monthOffset(memberApproVo.getInDate(),
		 * -1), DateUtils.YMD_DASH); if
		 * (Long.parseLong(DateUtils.date2TimeStamp(start_indate)) >
		 * Long.parseLong(DateUtils.getTime())) { msg = "0";// vip有效期比当前日期大于
		 * 1个月，不用提醒 } else { // 需要提醒 1,即将过期；2已过期；3即将过期；
		 * 
		 * int dayNum = DateUtils.dayDiffByDay(memberApproVo.getInDate(), new
		 * Date()); // 7天-1月，只提醒一次； if (dayNum > 7) { msg = "3"; msg2 =
		 * "温馨提示：您的vip临近过期。为享有本息保障，请提前做好VIP续费准备！！"; } // 一星期后过期 if (dayNum > 0
		 * && dayNum < 8) { msg = "1"; msg2 =
		 * "温馨提示：您的vip即将过期。为享有本息保障，请提前做好VIP续费准备！"; } // 过期一星期， if (dayNum < 1 &&
		 * dayNum > -8) { msg = "2"; msg2 =
		 * "温馨提示：您的vip已过期。为享有本息保障，请及时做好VIP续费准备！"; }
		 * 
		 * //当天 if(dayNum==0){ //未过期 if (memberApproVo.getInDate().getTime() >
		 * new Date().getTime() ) { msg="4"; //当天未过期
		 * msg2="温馨提示：您的vip即将过期。为享有本息保障，请提前做好VIP续费准备！"; }else{ msg="2";
		 * msg2="温馨提示：您的vip已过期。为享有本息保障，请及时做好VIP续费准备！"; } }
		 * 
		 * 
		 * }
		 * 
		 * // 需要提醒的，设置vip提醒缓存； | 每天提醒一次的情形； if (msg.equals("1") ||
		 * msg.equals("2")) { String key = shiroUser.getUserId().toString();
		 * 
		 * Cache cache = cacheManager.getCache(vipCache); ValueWrapper
		 * valueWrapper = cache.get(key); if (valueWrapper != null) {
		 * cache.evict(key); } // 保存数据，key-实体类形式； // cache.put(key,
		 * System.currentTimeMillis());
		 * 
		 * VipRemindVo vipRemindVo = new VipRemindVo();
		 * vipRemindVo.setRemindNum(1); // 区别于下面的内容；
		 * vipRemindVo.setRemindTime(System.currentTimeMillis()); cache.put(key,
		 * vipRemindVo);
		 * 
		 * // 添加提醒日志 LoginRemindLogVo loginRemindLogVo = new LoginRemindLogVo();
		 * loginRemindLogVo.setUserId(shiroUser.getUserId());
		 * loginRemindLogVo.setMessage(msg2);
		 * 
		 * try { myAccountService.saveLoginRemindLog(loginRemindLogVo);
		 * 
		 * } catch (SQLException e) { logger.error("添加登录提醒日志出错");
		 * e.printStackTrace(); } } if (msg.equals("3")) { String key =
		 * shiroUser.getUserId().toString();
		 * 
		 * Cache cache = cacheManager.getCache(vipCache); ValueWrapper
		 * valueWrapper = cache.get(key); if (valueWrapper != null) {
		 * cache.evict(key); } // 保存数据，key-实体类形式； VipRemindVo vipRemindVo = new
		 * VipRemindVo(); vipRemindVo.setRemindNum(2);
		 * vipRemindVo.setRemindTime(memberApproVo.getInDate().getTime()); //
		 * 保存vip到期时间； cache.put(key, vipRemindVo);
		 * 
		 * // 添加提醒日志 LoginRemindLogVo loginRemindLogVo = new LoginRemindLogVo();
		 * loginRemindLogVo.setUserId(shiroUser.getUserId());
		 * loginRemindLogVo.setMessage(msg2);
		 * 
		 * try { myAccountService.saveLoginRemindLog(loginRemindLogVo);
		 * 
		 * } catch (SQLException e) { logger.error("添加登录提醒日志出错");
		 * e.printStackTrace(); } } }
		 */

		return msg;

	}
}
