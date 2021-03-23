package com.dxjr.portal.borrow.util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.Borrow;
import com.dxjr.base.entity.Configuration;
import com.dxjr.base.entity.GuarantyOrganization;
import com.dxjr.base.entity.RealNameAppro;
import com.dxjr.base.mapper.BaseGuarantyOrganizationMapper;
import com.dxjr.base.mapper.BaseRealNameApproMapper;
import com.dxjr.common.Dictionary;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.borrow.mapper.BusinessMapper;
import com.dxjr.portal.borrow.vo.BusinessCnd;
import com.dxjr.portal.borrow.vo.BusinessVo;
import com.dxjr.portal.borrow.vo.SalariatBorrowVo;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;

public class BorrowUtil {

	/**
	 * 检验Borrow数据
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年8月21日
	 * @param borrow
	 * @return boolean
	 */
	public static boolean checkBorrowData(Borrow borrow) {
		HashMap<String, Integer> strMap = new HashMap<String, Integer>();
		strMap.put(borrow.getName(), 100);
		if (borrow.getBorrowtype() == null || borrow.getBorrowtype() != 3) {
			strMap.put(borrow.getContent(), 3000);
		}
		if (borrow.getBidPassword() != null && !"".equals(borrow.getBidPassword())) {
			strMap.put(borrow.getBidPassword(), 12);
		}

		HashMap<BigDecimal, String> numMap = new HashMap<BigDecimal, String>();
		numMap.put(new BigDecimal(borrow.getValidTime()), "1|3"); // 有效天数

		// 还款天数
		if (borrow.getBorrowtype() == 3) {
			if (4 == borrow.getStyle()) {
				numMap.put(new BigDecimal(borrow.getTimeLimitDay()), "1|30"); // 还款天数
			} else {
				numMap.put(new BigDecimal(borrow.getTimeLimit()), "1|1"); // 还款月数
			}
		}

		numMap.put(borrow.getApr(), "6|24");

		numMap.put(borrow.getLowestAccount(), "50|" + borrow.getAccount());
		if (borrow.getMostAccount() != null) {
			numMap.put(borrow.getMostAccount(), borrow.getLowestAccount() + "|" + borrow.getAccount());
		}
		numMap.put(borrow.getAccount(), "500|999999999999");

		return validInfos(strMap, numMap);
	}

	public static boolean checkBorrowDataNew(Borrow borrow) {

		if (StringUtils.isEmpty(borrow.getName())) {
			return false;
		}

		if (borrow.getName().length() > 100) {
			return false;
		}

		if (borrow.getBorrowtype() == null || borrow.getBorrowtype() != 3) {

			if (StringUtils.isEmpty(borrow.getContent())) {
				return false;
			}

			if (borrow.getContent().length() > 3000) {
				return false;
			}

		}

		if (borrow.getBidPassword() != null && !"".equals(borrow.getBidPassword())) {
			if (borrow.getBidPassword().length() > 12) {
				return false;
			}
		}

		// 有效天数
		if (borrow.getValidTime().intValue() < 1 || borrow.getValidTime().intValue() > 3) {
			return false;
		}

		// 还款天数
		if (borrow.getBorrowtype() == 3) {
			if (4 == borrow.getStyle()) {
				if (borrow.getTimeLimitDay().intValue() < 1 || borrow.getTimeLimitDay().intValue() > 30) {
					return false;
				}
			} else {
				// 还款月数
				if (borrow.getTimeLimit().intValue() != 1) {
					return false;
				}
			}
		}

		if (borrow.getApr().compareTo(new BigDecimal(6)) == -1 || borrow.getApr().compareTo(new BigDecimal(24)) == 1) {
			return false;
		}

		if (borrow.getLowestAccount().compareTo(new BigDecimal(50)) == -1 || borrow.getLowestAccount().compareTo(borrow.getAccount()) == 1) {
			return false;
		}

		if (borrow.getMostAccount() != null) {
			if (borrow.getMostAccount().compareTo(borrow.getLowestAccount()) == -1 || borrow.getMostAccount().compareTo(borrow.getAccount()) == 1) {
				return false;
			}

		}

		if (borrow.getAccount().compareTo(new BigDecimal(500)) == -1 || borrow.getAccount().compareTo(new BigDecimal("999999999999")) == 1) {
			return false;
		}



		return true;
	}

	/*
	 * public static void main(String[] args) { HashMap<BigDecimal, String>
	 * numMap = new HashMap<BigDecimal, String>(); numMap.put(new
	 * BigDecimal(55), "500|999999999999");// 还款天数 System.out.println(new
	 * BigDecimal(0).compareTo(new BigDecimal(-1))); }
	 */

	/**
	 * 验证数据
	 * <p>
	 * Description:验证数据<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年8月21日
	 * @param strMap
	 * @param numMap
	 * @return boolean
	 */
	private static boolean validInfos(HashMap<String, Integer> strMap, HashMap<BigDecimal, String> numMap) {
		Iterator i = null;
		if (strMap != null) {
			i = strMap.entrySet().iterator();
			while (i.hasNext()) {
				Entry<String, Integer> entry = (Entry<String, Integer>) i.next();
				String s = entry.getKey();
				int maxLen = entry.getValue();
				if (null == s || "".equals(s) || "null".equals(s) || maxLen < s.length()) {
					return false;
				}
			}
		}

		if (numMap != null) {
			i = numMap.entrySet().iterator();
			while (i.hasNext()) {
				Entry<BigDecimal, String> entry = (Entry<BigDecimal, String>) i.next();
				BigDecimal val = entry.getKey();
				String[] strAry = entry.getValue().split("\\|");
				BigDecimal min = new BigDecimal(strAry[0]);
				BigDecimal max = new BigDecimal(strAry[1]);
				if (val.compareTo(min) == -1 || val.compareTo(max) == 1) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 获取合同号
	 * <p>
	 * Description:获取合同号<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年8月21日
	 * @param borrow 最新borrow
	 * @param type 标类型
	 * @return String
	 */
	public static String getContractNo(Borrow borrow, int type) {
		int no = 0;
		String num = "0001";
		if (borrow != null && borrow.getContractNo() != null) {
			String contract_no = borrow.getContractNo();
			if (contract_no.length() > 0) {
				no = Integer.parseInt(contract_no.substring(7)) + 1;
				// System.out.println(no);
			}
		} else {
			no = 1;
		}
		if (no < 10) {
			num = "000" + no;
		} else if (no >= 10 && no < 100) {
			num = "00" + no;
		} else if (no >= 100 && no < 1000) {
			num = "0" + no;
		} else if (no >= 1000) {
			num = String.valueOf(no);
		}
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2, 4);
		String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
		if (month.length() == 1) {
			month = "0" + month;
		}
		String prefix = "";
		switch (type) {
		case 1:
			prefix = "XY-";
			break;// 信用
		case 2:
			prefix = "DY-";
			break;// 抵押
		case 3:
			prefix = "JZ-";
			break;// 净值
		case 5:
			prefix = "DB-";
			break;// 担保
		}
		String contractNo = prefix + year + month + num;
		// System.out.println("no------------>"+contractNo);

		return contractNo;
	}

	/*
	 * public static void main(String[] args) { Borrow borrow = new Borrow();
	 * borrow.setContractNo("JZ-140910001");
	 * System.out.println(getContractNo(borrow, 3)); }
	 */

	/**
	 * 检查验证码
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年9月5日
	 * @param currentSession
	 * @param checkcode
	 * @return String
	 */
	public static String checkCode(HttpSession currentSession, String checkcode) {
		String randomCode = (String) currentSession.getAttribute("randomCode");
		String msg = "";
		if (randomCode == null) {
			msg = "验证码不存在，请刷新。";
		} else if (!checkcode.equals(randomCode)) {
			msg = "验证码输入错误。";
		}
		currentSession.removeAttribute("randomCode");
		return msg;
	}

	/**
	 * 借款标添加&修改初始化信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年10月13日
	 * @param mv
	 * @param loginMem
	 * @param approMapper
	 * @param organizationMapper
	 * @return
	 * @throws Exception ModelAndView
	 */
	public static ModelAndView initInfos(ModelAndView mv, ShiroUser loginMem, BaseRealNameApproMapper approMapper, 
			BaseGuarantyOrganizationMapper organizationMapper,BusinessMapper businessMapper) throws Exception {
		// 初始化信息
		int userId = loginMem.getUserId();// 当前登录用户
		RealNameAppro approMem = approMapper.queryByUserId(userId);// 用户认证信息
		List<GuarantyOrganization> organizationOptions = organizationMapper.getAll();// 担保机构
		/*获取业务员*/
		BusinessCnd businessCnd=new BusinessCnd();
		businessCnd.setStatus(0);
		List<BusinessVo> businessStaffOptions=businessMapper.searchBusinessListByCnd(businessCnd);

		Collection<Configuration> educationOptions = Dictionary.getValues(810);// 学历
		Collection<Configuration> maritalStatusOptions = Dictionary.getValues(811);// 婚姻状况
		Collection<Configuration> scaleOptions = Dictionary.getValues(812);// 公司规模
		Collection<Configuration> workYearsOptions = Dictionary.getValues(813);// 工作时间

		Collection<Configuration> repaymentStyleOptions = Dictionary.getValues(400);// 官方标-还款方式
		// 去除没有限制这条数据
		for (Configuration configuration : repaymentStyleOptions) {
			if (configuration.getName().equals(String.valueOf(Constants.BORROW_STYLE_NO_LIMIT))) {
				repaymentStyleOptions.remove(configuration);
				break;
			}
		}
		mv.addObject("loginMemName", loginMem.getUserName());// 用户名
		mv.addObject("loginMemGender", "0".equals(approMem.getSex()) ? '男' : '女');// 性别
		mv.addObject("loginMemBirthDay", approMem.getBirthDay());// 生日
		mv.addObject("loginMemNativePlace", approMem.getNativePlace());// 籍贯
		mv.addObject("organizationOptions", organizationOptions);
		mv.addObject("businessStaffOptions", businessStaffOptions);

		mv.addObject("educationOptions", educationOptions);
		mv.addObject("maritalStatusOptions", maritalStatusOptions);
		mv.addObject("scaleOptions", scaleOptions);
		mv.addObject("workYearsOptions", workYearsOptions);

		mv.addObject("repaymentStyleOptions", repaymentStyleOptions);		

		return mv;
	}

	/**
	 * 处理借款标信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年10月13日
	 * @param salariatBorrowVo
	 * @return SalariatBorrowVo
	 */
	public static SalariatBorrowVo dealBorrowInfo(SalariatBorrowVo salariatBorrowVo) {
		// 处理投标最大金额
		if (salariatBorrowVo.getMostAccount() == null) {
			salariatBorrowVo.setMostAccount(BigDecimal.ZERO);
		}
		if ("on".equals(salariatBorrowVo.getIsGuarantyCB())) {// 是否担保
			salariatBorrowVo.setIsGuaranty(1);
			salariatBorrowVo.setBorrowtype(Constants.BORROW_TYPE_GUARANTEED);// 5担保标
		} else {
			salariatBorrowVo.setIsGuaranty(0);// 无担保
			salariatBorrowVo.setGuarantyOrganization(null);

			if (1 == salariatBorrowVo.getIsMortgage()) {// 是否抵押
				salariatBorrowVo.setBorrowtype(Constants.BORROW_TYPE_PLEDGE);// 2抵押标
			} else {
				salariatBorrowVo.setMortgageType(null);// 无抵押-抵押类型置空
				salariatBorrowVo.setBorrowtype(Constants.BORROW_TYPE_RECOMMEND);// 1信用标
			}
		}
		if (salariatBorrowVo.getStyle() == Constants.BORROW_STYLE_MONTH_INSTALMENTS || salariatBorrowVo.getStyle() == Constants.BORROW_STYLE_MONTH_PAY_INTEREST) {// 1：等额本息/按月分期还款，2：按月付息到期还本
			salariatBorrowVo.setOrder(salariatBorrowVo.getTimeLimit());
		} else {
			salariatBorrowVo.setOrder(1);
		}
		// salariatBorrowVo.setContractNo(BorrowUtil.getContractNo(borrowMapper.queryLastBorrow(),salariatBorrowVo.getBorrowtype()));//获取合同编号
		salariatBorrowVo.setBorrowUse(salariatBorrowVo.getName());

		if (salariatBorrowVo.getIsJointGuaranty() == 0) {
			salariatBorrowVo.setJointGuaranty(null);
		}

		return salariatBorrowVo;
	}

	/**
	 * <p>
	 * Description:判断用户能发起净值<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年2月27日
	 * @param addtimeStr
	 * @return String
	 */
	public static String canLaunchEquity(String addtimeStr) {

		// 2015-06-01 所有用户不允许发起净值
		String time0601Str = "2015-06-01";
		Date date0601 = DateUtils.parse(time0601Str, "yyyy-MM-dd");
		long time0601Long = date0601.getTime();

		if (time0601Long <= new Date().getTime()) {
			return "2015年6月1日起所有用户不允许发布净值标！";
		}

		// 2015-03-01 注册的用户不允许发起净值
		String time = "2015-03-01";
		Date date = DateUtils.parse(time, "yyyy-MM-dd");
		Long time0301 = date.getTime() / 1000;
		Long addtimeLong = Long.parseLong(addtimeStr);

		if (time0301 <= addtimeLong) {
			return "2015年3月1日起注册用户不允许发布净值标！";
		}

		return "";
	}

}
