package com.dxjr.portal.statics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dxjr.common.statics.Constants;
import com.dxjr.utils.DateUtils;

public class CalculatorUtil {

	/**
	 * 
	 * <p>
	 * Description:等额本息计算方法(按月计算)：查询每期应还款的本金和利息<br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月6日
	 * @param money ： 本金
	 * @param rate ： 年利率
	 * @param period ： 借款期限
	 * @return BigDecimal
	 */
	public static BigDecimal getCostInterestPercenageMonth(BigDecimal money, BigDecimal rate, BigDecimal period) {
		// 单月利率
		BigDecimal ratePercenage = getRatePercenageMonth(rate);
		BigDecimal cifang = ratePercenage.add(new BigDecimal("1")).pow(period.intValue());
		BigDecimal costInterestPercenage = money.multiply(ratePercenage).multiply(cifang).divide(cifang.subtract(new BigDecimal("1")), Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_HALF_UP)
				.setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_HALF_UP);
		return costInterestPercenage;
	}

	/**
	 * 
	 * <p>
	 * Description:月平均利率(按月计算)<br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月6日
	 * @param rate
	 * @return BigDecimal
	 */
	public static BigDecimal getRatePercenageMonth(BigDecimal rate) {
		BigDecimal ratePercenage = rate.divide(new BigDecimal(12).multiply(new BigDecimal("100")), Constants.CALCULATOR_INTEREST_NUM, BigDecimal.ROUND_HALF_UP);
		return ratePercenage;
	}

	/**
	 * 
	 * <p>
	 * Description:等额本息还款应还所有的本金和利息总和(按月计算)<br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月6日
	 * @param money ： 本金
	 * @param rate ： 年利率
	 * @param period ： 借款期限
	 * @return BigDecimal
	 */
	public static BigDecimal getCostInterestDebxhkMonth(BigDecimal money, BigDecimal rate, BigDecimal period) {
		BigDecimal costInterestPercenage = getCostInterestPercenageMonth(money, rate, period);
		BigDecimal costInterest = costInterestPercenage.multiply(period);
		return costInterest;
	}

	/**
	 * 
	 * <p>
	 * Description:获取等额本息每期还款数据(按月计算)<br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月6日
	 * @param money ： 本金
	 * @param rate ： 年利率
	 * @param period ： 借款期限
	 * @return List<Map<String,BigDecimal>>
	 */
	public static List<Map<String, BigDecimal>> getPeriodPercenageDebxhkListMonth(BigDecimal money, BigDecimal rate, BigDecimal period) {
		List<Map<String, BigDecimal>> dataList = new ArrayList<Map<String, BigDecimal>>();
		// 本息
		BigDecimal costInterestPercenage = getCostInterestPercenageMonth(money, rate, period);
		BigDecimal ratePercenage = getRatePercenageMonth(rate);
		BigDecimal sumCost = new BigDecimal("0");
		// 已循环本金总额
		for (int i = 0; i < period.intValue(); i++) {
			Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
			// 利息
			BigDecimal interestTime = new BigDecimal(0);
			// 本金
			BigDecimal costTime = new BigDecimal(0);
			if (i + 1 == period.intValue()) {
				// 本金
				costTime = money.subtract(sumCost).setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_HALF_UP);
				// 利息
				interestTime = costInterestPercenage.subtract(costTime).setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_HALF_UP);
			} else {
				// 利息
				interestTime = money.subtract(sumCost).multiply(ratePercenage).setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_HALF_UP);// 当期应还利息
				// 本金
				costTime = costInterestPercenage.subtract(interestTime).setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_HALF_UP);// 当期应还本金
				sumCost = sumCost.add(costTime).setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_HALF_UP);// 更改已还总额
			}
			if (i == period.intValue() - 1) {
				map.put("balance", new BigDecimal("0").setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_HALF_UP));
			} else {
				BigDecimal balance = money.subtract(sumCost).setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_HALF_UP);
				map.put("balance", balance);
			}
			map.put("costInterest", costInterestPercenage.setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_HALF_UP));
			map.put("interest", interestTime.setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_HALF_UP));
			map.put("cost", costTime.setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_HALF_UP));

			dataList.add(map);
		}
		return dataList;

	}

	/**
	 * 
	 * <p>
	 * Description:等额本息还款应还的利息总额(按月计算)<br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月6日
	 * @param money ： 本金
	 * @param rate ： 年利率
	 * @param period ： 借款期限
	 * @return BigDecimal
	 */
	public static BigDecimal getInterestDebxhkMonth(BigDecimal money, BigDecimal rate, BigDecimal period) {
		BigDecimal costInterest = getCostInterestDebxhkMonth(money, rate, period);
		BigDecimal interest = costInterest.subtract(money).setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_HALF_UP);
		return interest;
	}

	/**
	 * 
	 * <p>
	 * Description:按月付息到期还本算法计算应还的本金和所有的利息之和(按月计算)<br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月6日
	 * @param money ： 本金
	 * @param rate ： 年利率
	 * @param period ： 借款期限
	 * @return BigDecimal
	 */
	public static BigDecimal getCostInterestAyfxdqhbMonth(BigDecimal money, BigDecimal rate, BigDecimal period) {
		BigDecimal interestPercenage = getInterestPercenageAyfxdqhbMonth(money, rate);
		BigDecimal totalInterest = interestPercenage.multiply(period).setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_DOWN);
		BigDecimal costInterest = totalInterest.add(money);
		return costInterest;
	}

	/**
	 * 
	 * <p>
	 * Description:按月付息到期还本算法：计算每期应还的利息(按月计算)<br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月6日
	 * @param money ： 本金
	 * @param rate ： 年利率
	 * @return BigDecimal
	 */
	public static BigDecimal getInterestPercenageAyfxdqhbMonth(BigDecimal money, BigDecimal rate) {
		BigDecimal ratePercenage = getRatePercenageMonth(rate);
		BigDecimal interestPercenage = money.multiply(ratePercenage).setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_DOWN);
		return interestPercenage;
	}

	/**
	 * 
	 * <p>
	 * Description:按月付息到期还本算法：计算所有的利息(按月计算)<br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月6日
	 * @param money
	 * @param rate
	 * @param period
	 * @return BigDecimal
	 */
	public static BigDecimal getInterestTotaslAyfxdqhbMonth(BigDecimal money, BigDecimal rate, BigDecimal period) {
		BigDecimal interestPercenage = getInterestPercenageAyfxdqhbMonth(money, rate);
		BigDecimal interestTotal = interestPercenage.multiply(period).setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_DOWN);
		return interestTotal;
	}

	/**
	 * 
	 * <p>
	 * Description:获取按月付息到期还本每期还款数据(按月计算)<br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月6日
	 * @param money
	 * @param rate
	 * @param period
	 * @return List<Map<String,BigDecimal>>
	 */
	public static List<Map<String, BigDecimal>> getPeriodPercenageAyfxdqhbListMonth(BigDecimal money, BigDecimal rate, BigDecimal period) {
		List<Map<String, BigDecimal>> dataList = new ArrayList<Map<String, BigDecimal>>();
		BigDecimal interestPercenage = getInterestPercenageAyfxdqhbMonth(money, rate);
		for (int i = 0; i < period.intValue(); i++) {
			Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
			if (i == period.intValue() - 1) {
				map.put("balance", new BigDecimal("0").setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_DOWN));
				map.put("costInterest", interestPercenage.add(money));
				map.put("interest", interestPercenage);
				map.put("cost", money);

			} else {
				map.put("balance", money);
				map.put("costInterest", interestPercenage);
				map.put("interest", interestPercenage);
				map.put("cost", new BigDecimal("0").setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_DOWN));
			}

			dataList.add(map);
		}
		return dataList;

	}

	/**
	 * 
	 * <p>
	 * Description:到期还本付息算法：计算应还成本和总利息之和(按月计算)<br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月6日
	 * @param money
	 * @param rate
	 * @param period
	 * @return BigDecimal
	 */
	public static BigDecimal getCostInterestDqhbfxMonth(BigDecimal money, BigDecimal rate, BigDecimal period) {
		BigDecimal interestPercenage = getInterestPercenageDqhbfxMonth(money, rate);
		BigDecimal totalInterest = interestPercenage.multiply(period).setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_DOWN);
		BigDecimal costInterest = totalInterest.add(money);
		return costInterest;
	}

	/**
	 * 
	 * <p>
	 * Description:到期还本付息算法（按月计算）：计算月平均利息<br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月6日
	 * @param money
	 * @param rate
	 * @return BigDecimal
	 */
	public static BigDecimal getInterestPercenageDqhbfxMonth(BigDecimal money, BigDecimal rate) {
		BigDecimal ratePercenage = getRatePercenageMonth(rate);
		BigDecimal interestPercenage = money.multiply(ratePercenage).setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_HALF_UP);
		return interestPercenage;
	}

	/**
	 * 
	 * <p>
	 * Description:到期还本付息算法：计算利息总额<br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月6日
	 * @param money
	 * @param rate
	 * @param period
	 * @return BigDecimal
	 */
	public static BigDecimal getInterestTotaslDqhbfxMonth(BigDecimal money, BigDecimal rate, BigDecimal period) {
		BigDecimal interestTotal = money.multiply(getRatePercenageMonth(rate)).multiply(period).setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_HALF_UP);
		return interestTotal;
	}

	/**
	 * 
	 * <p>
	 * Description:天平均利率(按天计算)<br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月6日
	 * @param rate
	 * @return BigDecimal
	 */
	public static BigDecimal getRatePercenageDay(BigDecimal rate) {
		BigDecimal ratePercenage = rate.divide(new BigDecimal(360).multiply(new BigDecimal("100")), Constants.CALCULATOR_INTEREST_NUM, BigDecimal.ROUND_DOWN);
		return ratePercenage;
	}

	/**
	 * 
	 * <p>
	 * Description:到期还本付息算法（按天计算）：计算利息总额<br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月6日
	 * @param money
	 * @param rate
	 * @param period
	 * @return BigDecimal
	 */
	public static BigDecimal getInterestTotaslDqhbfxDay(BigDecimal money, BigDecimal rate, BigDecimal period) {
		BigDecimal ratePercenage = getRatePercenageDay(rate);
		BigDecimal interestTotal = money.multiply(ratePercenage).multiply(period).setScale(Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_HALF_UP);
		return interestTotal;
	}

	/**
	 * 
	 * <p>
	 * Description:按月付息到期还本管理费计算方式<br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月6日
	 * @param money
	 * @param rate
	 * @param period
	 * @return BigDecimal
	 */
	public static BigDecimal getManageFeeAyfxdqhbMonth(BigDecimal money, BigDecimal period) {
		BigDecimal manageFee = BigDecimal.ZERO;
		if (period.compareTo(new BigDecimal(1)) == 0 || period.compareTo(new BigDecimal(2)) == 0 || period.compareTo(new BigDecimal(3)) == 0) {
			manageFee = money.multiply(new BigDecimal(Constants.CALCULATOR_POUNDAGE_ONE_STEP).divide(new BigDecimal("100"), Constants.CALCULATOR_INTEREST_NUM, BigDecimal.ROUND_DOWN));
		} else if (period.compareTo(new BigDecimal(5)) == 0 || period.compareTo(new BigDecimal(6)) == 0) {
			manageFee = money.multiply(new BigDecimal(Constants.CALCULATOR_POUNDAGE_TWO_STEP).divide(new BigDecimal("100"), Constants.CALCULATOR_INTEREST_NUM, BigDecimal.ROUND_DOWN));
		} else if (period.compareTo(new BigDecimal(7)) == 0 || period.compareTo(new BigDecimal(8)) == 0 || period.compareTo(new BigDecimal(9)) == 0 || period.compareTo(new BigDecimal(10)) == 0
				|| period.compareTo(new BigDecimal(11)) == 0 || period.compareTo(new BigDecimal(12)) == 0) {
			manageFee = money.multiply(new BigDecimal(Constants.CALCULATOR_POUNDAGE_THREE_STEP).divide(new BigDecimal("100"), Constants.CALCULATOR_INTEREST_NUM, BigDecimal.ROUND_DOWN));
		}
		return manageFee;

	}

	/**
	 * 
	 * <p>
	 * Description:到期还本付息管理费计算方式<br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月6日
	 * @param money
	 * @param period
	 * @return BigDecimal
	 */
	public static BigDecimal getManageFeeDqhbfxMonth(BigDecimal money, BigDecimal period) {
		BigDecimal manageFee = BigDecimal.ZERO;
		if (period.compareTo(new BigDecimal(1)) == 0 || period.compareTo(new BigDecimal(2)) == 0 || period.compareTo(new BigDecimal(3)) == 0) {
			manageFee = money.multiply(new BigDecimal(Constants.CALCULATOR_POUNDAGE_ONE_STEP).divide(new BigDecimal("100"), Constants.CALCULATOR_INTEREST_NUM, BigDecimal.ROUND_DOWN));
		} else if (period.compareTo(new BigDecimal(5)) == 0 || period.compareTo(new BigDecimal(6)) == 0) {
			manageFee = money.multiply(new BigDecimal(Constants.CALCULATOR_POUNDAGE_TWO_STEP).divide(new BigDecimal("100"), Constants.CALCULATOR_INTEREST_NUM, BigDecimal.ROUND_DOWN));
		} else if (period.compareTo(new BigDecimal(7)) == 0 || period.compareTo(new BigDecimal(8)) == 0 || period.compareTo(new BigDecimal(9)) == 0 || period.compareTo(new BigDecimal(10)) == 0
				|| period.compareTo(new BigDecimal(11)) == 0 || period.compareTo(new BigDecimal(12)) == 0) {
			manageFee = money.multiply(new BigDecimal(Constants.CALCULATOR_POUNDAGE_THREE_STEP).divide(new BigDecimal("100"), Constants.CALCULATOR_INTEREST_NUM, BigDecimal.ROUND_DOWN));
		}
		return manageFee;

	}

	/**
	 * 
	 * <p>
	 * Description:到期还本付息（按天计算）管理费用<br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月6日
	 * @param money
	 * @param period
	 * @return BigDecimal
	 */
	public static BigDecimal getManageFeeDqhbfxDay(BigDecimal money, BigDecimal period) {
		BigDecimal manageFee = money.multiply(new BigDecimal(Constants.CALCULATOR_POUNDAGE_DAY).divide(new BigDecimal("100"), Constants.CALCULATOR_INTEREST_NUM, BigDecimal.ROUND_DOWN)).multiply(
				period);
		return manageFee;
	}

	/**
	 * 
	 * <p>
	 * Description:等额本息还款（按月计算）：计算管理费<br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月6日
	 * @param money
	 * @param period
	 * @return BigDecimal
	 */
	public static BigDecimal getManageFeeDebxhkMonth(BigDecimal money, BigDecimal period) {
		BigDecimal manageFee = money.multiply(new BigDecimal(Constants.CALCULATOR_POUNDAGE_DEBXHK).divide(new BigDecimal("100"), Constants.CALCULATOR_INTEREST_NUM, BigDecimal.ROUND_DOWN)).multiply(
				period);
		return manageFee;
	}

	/**
	 * 
	 * <p>
	 * Description:根据充值金额计算充值手续费,计算规则：即时充值所有产生的转帐费用，以100元为上限，按转账金额的0.5%，由第三方平台收取
	 * <br />
	 * </p>
	 * 
	 * @author gang.li
	 * @version 0.1 2013年9月11日
	 * @param money
	 * @return BigDecimal
	 */
	public static BigDecimal getRechargeChinabankCzsxf(BigDecimal money) {
		BigDecimal fee = money.multiply(new BigDecimal("0.0025"));
		/*
		 * if(fee.compareTo(new BigDecimal("100")) == 1){ fee = new
		 * BigDecimal("100"); }
		 */
		return fee;
	}

	/**
	 * 
	 * <p>
	 * Description:秒标管理费计算br />
	 * </p>
	 * 
	 * @author zhanghao
	 * @version 0.1 2013年9月18日
	 * @param money
	 * @param rate
	 * @return BigDecimal
	 */
	public static BigDecimal getManagementCostOfSecBid(BigDecimal money, BigDecimal rate) {
		BigDecimal result = null;
		result = (money.multiply(rate.divide(new BigDecimal("100"), Constants.CALCULATOR_INTEREST_NUM, BigDecimal.ROUND_DOWN))).divide(new BigDecimal("12"), Constants.CALCULATOR_INTEREST_NUM,
				BigDecimal.ROUND_DOWN);
		return result;
	}

	/**
	 * 
	 * <p>
	 * Description:推荐标管理费计算br />
	 * </p>
	 * 
	 * @author zhanghao
	 * @version 0.1 2013年9月18日
	 * @param money
	 * @param rate
	 * @return BigDecimal pass
	 */
	public static BigDecimal getManagementCostOfRecommendBid(BigDecimal money) {
		BigDecimal result = null;
		result = money.multiply(getRatePercent(new BigDecimal(Constants.MANAGEMENT_COST_RECOMMEND_BID)));
		return result;
	}

	/**
	 * 
	 * <p>
	 * Description:净值标管理费计算（月）br />
	 * </p>
	 * 
	 * @author zhanghao
	 * @version 0.1 2013年9月18日
	 * @param money
	 * @param rate
	 * @return BigDecimal pass
	 */
	public static BigDecimal getManagementCostOfWorthBidByMonth(BigDecimal money, BigDecimal month) {
		BigDecimal result = null;
		result = money.multiply(getRatePercent(new BigDecimal(Constants.MANAGEMENT_COST_WORTH_MONTH_BID))).multiply(month);
		return result;
	}

	/**
	 * 
	 * <p>
	 * Description:净值标管理费计算（天）br />
	 * </p>
	 * 
	 * @author zhanghao
	 * @version 0.1 2013年9月18日
	 * @param money
	 * @param rate
	 * @return BigDecimal pass
	 */
	public static BigDecimal getManagementCostOfWorthBidByDay(BigDecimal money, BigDecimal day) {
		BigDecimal result = null;
		BigDecimal r1 = money.multiply(getRatePercent(new BigDecimal(Constants.MANAGEMENT_COST_WORTH_DAY_BID))).multiply(day)
				.divide(new BigDecimal("30"), Constants.CALCULATOR_INTEREST_NUM, BigDecimal.ROUND_DOWN);
		BigDecimal r2 = money.multiply(getRatePercent(new BigDecimal(Constants.MANAGEMENT_COST_WORTH_DAY_BID_2)));

		if (r1.compareTo(r2) == -1) {
			result = r2;
		} else {
			result = r1;
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * Description:抵押标管理费计算（月 基础）br />
	 * </p>
	 * 
	 * @author zhanghao
	 * @version 0.1 2013年9月18日
	 * @param money
	 * @param rate
	 * @return BigDecimal pass
	 */
	public static BigDecimal getManagementCostOfPledgeBid(BigDecimal money, String rate) {
		BigDecimal result = null;
		result = money.multiply(getRatePercent(new BigDecimal(rate)));
		return result;
	}

	/**
	 * 
	 * <p>
	 * Description:抵押标管理费计算（月 ）br />
	 * </p>
	 * 
	 * @author zhanghao
	 * @version 0.1 2013年9月18日
	 * @param money
	 * @param rate
	 * @return BigDecimal pass
	 */
	public static BigDecimal getManagementCostOfPledgeBidByPeriodMonth(BigDecimal money, BigDecimal period) {
		BigDecimal result = null;
		if (period.compareTo(new BigDecimal(3)) == -1 || period.compareTo(new BigDecimal(3)) == 0) {
			result = getManagementCostOfPledgeBid(money, Constants.MANAGEMENT_COST_PLEDGE_BID_MIN);
		}
		if ((period.compareTo(new BigDecimal(3)) == 1) && (period.compareTo(new BigDecimal(6)) == -1 || period.compareTo(new BigDecimal(6)) == 0)) {
			result = getManagementCostOfPledgeBid(money, Constants.MANAGEMENT_COST_PLEDGE_BID_MEDIUM);
		}
		if (period.compareTo(new BigDecimal(6)) == 1) {
			result = getManagementCostOfPledgeBid(money, Constants.MANAGEMENT_COST_PLEDGE_BID_MAX);
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * Description:抵押标管理费计算（天）br />
	 * </p>
	 * 
	 * @author zhanghao
	 * @version 0.1 2013年9月18日
	 * @param money
	 * @param rate
	 * @return BigDecimal pass
	 */
	public static BigDecimal getManagementCostOfPledgeBidByPeriodDay(BigDecimal money, BigDecimal rate, BigDecimal day) {
		BigDecimal result = null;
		result = money.multiply(day).multiply(getRatePercent(new BigDecimal(Constants.MANAGEMENT_COST_PLEDGE_BID_DAY)));
		return result;
	}

	/**
	 * 
	 * <p>
	 * Description:得到rate小数百分比br />
	 * </p>
	 * 
	 * @author zhanghao
	 * @version 0.1 2013年9月18日
	 * @param rate
	 * @return BigDecimal
	 */
	public static BigDecimal getRatePercent(BigDecimal rate) {
		rate = rate.divide(new BigDecimal("100"), Constants.CALCULATOR_INTEREST_NUM, BigDecimal.ROUND_DOWN);
		return rate;
	}

	/**
	 * 
	 * <p>
	 * Description:得到rate小数千分比br />
	 * </p>
	 * 
	 * @author zhanghao
	 * @version 0.1 2013年9月18日
	 * @param rate
	 * @return BigDecimal
	 */
	public static BigDecimal getRatePermillage(BigDecimal rate) {
		rate = rate.divide(new BigDecimal("1000"), Constants.CALCULATOR_INTEREST_NUM, BigDecimal.ROUND_DOWN);
		return rate;
	}

	/**
	 * 使用java正则表达式去掉多余的.与0
	 * 
	 * @param s
	 * @return
	 */
	public static String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");// 去掉多余的0
			s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return s;
	}

	/**
	 * 
	 * <p>
	 * Description:参数转换处理<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月24日
	 * @param parameter void
	 */
	public static void convertParameter(Map<String, Object> parameter) {
		String beginTime = (String) parameter.get("beginTime");
		String endTime = (String) parameter.get("endTime");
		// 时间参数格式转换
		if (beginTime != null && !"".equals(beginTime)) {
			beginTime = com.dxjr.utils.DateUtils.date2TimeStamp(beginTime);

			// beginTime = com.util.DateUtils.dayOffset(
			// DateUtils.parse(beginTime, DateUtils.YMD_DASH), 1)
			// .getTime()
			// + "";

			parameter.put("beginTime", Long.parseLong(beginTime));
		}

		if (endTime != null && !"".equals(endTime)) {
			endTime = com.dxjr.utils.DateUtils.dayOffset(DateUtils.parse(endTime, DateUtils.YMD_DASH), 1).getTime() + "";
			parameter.put("endTime", Long.parseLong(endTime));
		}
	}

	public static void main(String args[]) {
		// System.out.println(CalculatorUtil.getManagementCostOfPledgeBidByPeriodDay(new
		// BigDecimal("100"),new BigDecimal("20"),new BigDecimal("30")));
		// System.out.println((new BigDecimal("1")).compareTo(new
		// BigDecimal("2")));
	}
}
