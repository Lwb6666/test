package com.dxjr.portal.electronbill.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.electronbill.entity.ElectronBill;
import com.dxjr.portal.electronbill.mapper.ElectronBillMapper;
import com.dxjr.portal.electronbill.service.AccountElectronbillService;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;

/**
 * <p>
 * Description:我的账户电子账单<br />
 * </p>
 * 
 * @title AccountElectronbillController.java
 * @package com.dxjr.portal.electronbill.controller
 * @author jianxin.chen
 * @version 0.1 2016年8月10日
 */
@Controller
@RequestMapping("/myBill")
public class AccountElectronbillController extends BaseController {
	public Logger logger = Logger.getLogger(AccountElectronbillController.class);

	@Autowired
	private AccountElectronbillService accountElectronbillService;
	@Autowired
	private ElectronBillMapper electronBillMapper;

	@RequiresAuthentication
	@RequestMapping(value = "/accElectronbill")
	public ModelAndView electronbill(String year, String month) {
		ModelAndView mv = new ModelAndView("/account/bill/mybilllist");
		ShiroUser shiroUser = super.currentUser();
		int userId;
		if (shiroUser != null) {
			userId = shiroUser.getUserId();
		} else {
			logger.error("当前未登陆，无当前登录用户!");
			return new ModelAndView(BusinessConstants.NO_PAGE_FOUND_404);
		}
		Date date = new Date();
		String ymonth = new SimpleDateFormat("yyyyMM").format(date);
		String yearyear = ymonth.substring(0, 4);// 当前年
		String monthmonth = ymonth.substring(4, 6);// 当前月

		String lastMonth = "";// 上一月
		String lastYear = String.valueOf(Integer.parseInt(yearyear) - 1);// 上一年
		if (monthmonth.equals("01")) {
			lastMonth = "12";
		} else {
			lastMonth = String.valueOf(Integer.parseInt(monthmonth) - 01);
			if (Integer.parseInt(lastMonth) < 10) {
				lastMonth = "0" + lastMonth;
			}
		}
		if ((null == month || month.trim().equals("")) && (null == year || year.trim().equals(""))) {
			month = lastMonth;
			if (monthmonth.equals("01")) {
				year = lastYear;
			} else {
				year = yearyear;
			}
		} else if ((null == month || month.trim().equals("")) && (null != year && !year.equals(""))) {
			month = lastMonth;
		} else if ((null != month && !month.equals("")) && (null == year || year.equals(""))) {
			year = yearyear;
		}
		String yearMonth = "";
		if (Integer.parseInt(month) < 10 && !month.contains("0")) {
			yearMonth = year + "0" + month;// 查询的年月
		} else {
			yearMonth = year + month;// 查询的年月
		}

		if (Integer.parseInt(year) < 2013 || Integer.parseInt(year) > Integer.parseInt(yearyear)
				|| (Integer.parseInt(year) == Integer.parseInt(yearyear) && Integer.parseInt(month) >= Integer.parseInt(monthmonth))) {
			logger.error("查询年月不能低于2013且不能高于当年当月!");
			return new ModelAndView(BusinessConstants.NO_PAGE_FOUND_404);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("userId", userId);
		map1.put("year", year);
		map1.put("month", yearMonth.substring(4, 6));
		ElectronBill electronBill = null;
		electronBill = electronBillMapper.selectMyBillByYearMonth(map1);
		if (electronBill == null) {
			electronBill = accountElectronbillService.AccountElectronbill(userId, yearMonth);
			if ((yearMonth.substring(4, 6).compareTo(monthmonth) < 0 && yearyear.equals(year)) || (year.compareTo(yearyear) < 0)) {
				electronBillMapper.insert(electronBill);
			}
		}
		map.put("currYear", yearyear);// 当前年
		map.put("year", year);
		map.put("currMonth", monthmonth);// 当前月
		map.put("month", month);
		map.put("lastYear", lastYear);
		map.put("lastMonth", lastMonth);
		mv.addObject("resMap", map);
		mv.addObject("electronBill", electronBill);
		return mv;
	}
}
