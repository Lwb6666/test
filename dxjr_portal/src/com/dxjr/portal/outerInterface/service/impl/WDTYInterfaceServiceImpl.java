package com.dxjr.portal.outerInterface.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxjr.common.statics.Constants;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.outerInterface.entity.ExternalAccessLog;
import com.dxjr.portal.outerInterface.entity.WdtyMemberBinding;
import com.dxjr.portal.outerInterface.mapper.OuterInterfaceMapper;
import com.dxjr.portal.outerInterface.mapper.WDTYInterfaceMapper;
import com.dxjr.portal.outerInterface.service.WDTYInterfaceService;
import com.dxjr.portal.outerInterface.vo.ExternalAccessLogCnd;
import com.dxjr.portal.outerInterface.vo.WdtyParamCnd;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.JsonUtils;
import com.dxjr.utils.exception.AppException;

@Service
public class WDTYInterfaceServiceImpl implements WDTYInterfaceService {

	@Autowired
	private OuterInterfaceMapper outerInterfaceMapper;
	
	@Autowired
	private WDTYInterfaceMapper wdtyInterfaceMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String transactionInfo(String ip, String url, String date) throws Exception {
		// 记录访问日志
		ExternalAccessLog externalAccessLog = packageExternalAccessLog(ip, url, Constants.DAILY_VOLUME_DATA, "WDTYInterfaceController.class", "transaction_info");
		if (outerInterfaceMapper.judgeIPForWDTY(ip) > 0) {
			boolean flag = true;
			String beginTime_now = DateUtils.dateTime2TimeStamp(DateUtils.format(new Date(), DateUtils.YMD_DASH) + " 00:00:00");
			String endTime_now = DateUtils.dateTime2TimeStamp(DateUtils.format(new Date(), DateUtils.YMD_DASH) + " 23:59:59");

			// 获取某IP在当前内访问该接口的次数
			ExternalAccessLogCnd externalAccessLogCnd = new ExternalAccessLogCnd();
			externalAccessLogCnd.setAccessIp(ip);
			externalAccessLogCnd.setAccessType(Constants.DAILY_VOLUME_DATA);
			externalAccessLogCnd.setBeginTime(beginTime_now);
			externalAccessLogCnd.setEndTime(endTime_now);
			int count = outerInterfaceMapper.findAccessCountForTransactionInfo(externalAccessLogCnd);
			if (count >= 10) {
				flag = false;
			}
			if (flag) { // 该接口每个IP每天只能调用10次
				try {
					DateUtils.parse(date, DateUtils.YMD_DASH);
				} catch (Exception e) {
					externalAccessLog.setStatus(-4);
					externalAccessLog.setReturnTime(DateUtils.getTime());
					outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
					return "-4"; // 参数错误
				}
				try {

					// 统计当日净值标标总额、当日抵押标满标总额、当日天标金额、当日月标金额、当日贷款总额、当日平均年利率
					Map<String, Object> borrow_map = findTodayBorrowInfo(date);
					// 当日贷款周期(平均值)
					BigDecimal today_period = findTodayPeriod(date);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("platform_name", "顶玺金融"); // 平台名称
					if (borrow_map.get("today_net_amount") != null) {
						map.put("today_net_amount", borrow_map.get("today_net_amount")); // 当日净值标金额
					} else {
						map.put("today_net_amount", 0); // 当日净值标金额
					}
					if (borrow_map.get("today_loan_amount") != null) {
						map.put("today_loan_amount", borrow_map.get("today_loan_amount")); // 当日普通标金额(即抵押标)
					} else {
						map.put("today_loan_amount", 0); // 当日普通标金额(即抵押标)
					}
					if (borrow_map.get("today_day_amount") != null) {
						map.put("today_day_amount", borrow_map.get("today_day_amount")); // 当日天标金额
					} else {
						map.put("today_day_amount", 0); // 当日天标金额
					}
					if (borrow_map.get("today_month_amount") != null) {
						map.put("today_month_amount", borrow_map.get("today_month_amount")); // 当日月标金额
					} else {
						map.put("today_month_amount", 0); // 当日月标金额
					}
					if (today_period != null) {
						map.put("today_period", today_period); // 当日贷款周期(平均值)
					} else {
						map.put("today_period", 0); // 当日贷款周期(平均值)
					}
					if (borrow_map.get("v_amount") != null) {
						map.put("v_amount", borrow_map.get("v_amount")); // 今日贷款总额
					} else {
						map.put("v_amount", 0); // 今日贷款总额
					}
					map.put("v_rate", new BigDecimal(String.valueOf(borrow_map.get("v_rate"))).setScale(2, BigDecimal.ROUND_DOWN)); // 平均年利率
					int day_investes = 0;
					int day_loaner = 0;
					int month_investes = 0;
					int month_loaner = 0;
					int year_investes = 0;
					int year_loaner = 0;
					if (date != null && !date.equals("")) {
						// 当天时间范围
						String beginTime = DateUtils.dateTime2TimeStamp(date + " 00:00:00");
						String endTime = DateUtils.dateTime2TimeStamp(date + " 23:59:59");
						day_investes = outerInterfaceMapper.findInvestesCount(beginTime, endTime);
						day_loaner = outerInterfaceMapper.findLoanerCount(beginTime, endTime);
						// 当月时间范围
						String beginTime2 = DateUtils.dateTime2TimeStamp(DateUtils.format(DateUtils.firstDay(DateUtils.parse(date, DateUtils.YMD_DASH)), DateUtils.YMD_DASH) + " 00:00:00");
						String endTime2 = DateUtils.dateTime2TimeStamp(DateUtils.format(DateUtils.lastDay(DateUtils.parse(date, DateUtils.YMD_DASH)), DateUtils.YMD_DASH) + " 23:59:59");
						month_investes = outerInterfaceMapper.findInvestesCount(beginTime2, endTime2);
						month_loaner = outerInterfaceMapper.findLoanerCount(beginTime2, endTime2);
						// 今年时间范围
						String year = date.split("-")[0];
						String beginTime3 = DateUtils.dateTime2TimeStamp(year + "-01-01 00:00:00");
						String endTime3 = DateUtils.dateTime2TimeStamp(year + "-12-31 23:59:59");
						year_investes = outerInterfaceMapper.findInvestesCount(beginTime3, endTime3);
						year_loaner = outerInterfaceMapper.findLoanerCount(beginTime3, endTime3);
					}
					BigDecimal collecting_amount = BigDecimal.ZERO;
					if (outerInterfaceMapper.queryNowCollectionData() != null) {
						collecting_amount = outerInterfaceMapper.queryNowCollectionData().setScale(2, BigDecimal.ROUND_DOWN);
					}
					map.put("day_investes", day_investes); // 日投资人数
					map.put("day_loaner", day_loaner); // 日借款人数
					map.put("month_investes", month_investes); // 本月投资人数
					map.put("month_loaner", month_loaner); // 本月借款人数
					map.put("year_investes", year_investes); // 今年以来投资人数
					map.put("year_loaner", year_loaner); // 今年以来借款人人数
					map.put("collecting_amount", collecting_amount); // 当前待收总金额
					map.put("date", date); // 数据日期

					externalAccessLog.setStatus(0); // 成功
					externalAccessLog.setReturnTime(DateUtils.getTime());
					outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
					return JsonUtils.toJson(map);
				} catch (Exception e) {
					e.printStackTrace();
					externalAccessLog.setStatus(-2);
					externalAccessLog.setReturnTime(DateUtils.getTime());
					outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
					// 调用失败
					return "-2";
				}
			} else {
				externalAccessLog.setStatus(-3);
				externalAccessLog.setReturnTime(DateUtils.getTime());
				outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
				// 调用过于频繁
				return "-3";
			}
		} else {
			externalAccessLog.setStatus(-1);
			externalAccessLog.setReturnTime(DateUtils.getTime());
			outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
			// ip无访问权限
			return "-1";
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String loanInfo(String ip, String url, String date) throws Exception {
		// 记录访问日志
		ExternalAccessLog externalAccessLog = packageExternalAccessLog(ip, url, Constants.DAILY_VOLUME_DATA, "WDTYInterfaceController.class", "transaction_info");
		if (outerInterfaceMapper.judgeIPForWDTY(ip) > 0) {
			// 当前时间戳
			String now = DateUtils.getTime();
			// 每隔1分钟调用一次
			if (judgeIPByAccessByDate(ip, Constants.DAILY_VOLUME_DATA, 60, now) == 0) {
				try {
					DateUtils.parse(date, DateUtils.YMD_DASH);
				} catch (Exception e) {
					externalAccessLog.setStatus(-2); // 调用失败
					externalAccessLog.setReturnTime(DateUtils.getTime());
					outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
					return "-4"; // 参数错误
				}
				try {
					List<BorrowVo> list = findBorrowInfo(date);
					List<String> loans = new ArrayList<String>();
					if (list.size() > 0) {
						for (BorrowVo borrow : list) {
							Map<String, Object> borrow_map = new HashMap<String, Object>();
							borrow_map.put("title", borrow.getName()); // 标题
							borrow_map.put("amount", borrow.getAccount().setScale(2, BigDecimal.ROUND_DOWN)); // 贷款额度（元）
							borrow_map.put("rate", borrow.getApr()); // 年化利率
							borrow_map.put("invest_num", borrow.getTenderTimes()); // 投资人数
							borrow_map.put("start_time", DateUtils.timeStampToDate(borrow.getPublishTime(), DateUtils.DATETIME_YMD_DASH)); // 发标时间
							borrow_map.put("url", "https://www.dxjr.com/toubiao/" + borrow.getId() + ".html"); // url链接
							borrow_map.put("period", borrow.getTimeLimit()); // 还款期限
							if (borrow.getStyle() == 4) {
								borrow_map.put("period_type", 1); // 还款期限类型（1天,
																	// 2月）
							} else {
								borrow_map.put("period_type", 2); // 还款期限类型（1天,
																	// 2月）
							}
							if (borrow.getStyle() == 1) { // 按月分期还款
								borrow_map.put("pay_way", "等额本息"); // 还款方式
							} else if (borrow.getStyle() == 2) { // 按月付息到期还本
								borrow_map.put("pay_way", "按月付息到期还本"); // 还款方式
							} else if (borrow.getStyle() == 3) { // 到期还本付息
								borrow_map.put("pay_way", "到期还本付息"); // 还款方式
							} else if (borrow.getStyle() == 4) { // 按天还款
								borrow_map.put("pay_way", "按天还款"); // 还款方式
							}
							BigDecimal schedule = borrow.getAccountYes().multiply(new BigDecimal(100)).divide(borrow.getAccount(), Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_DOWN);
							borrow_map.put("process", schedule); // 进度
							borrow_map.put("reward", 0.00); // 奖励
							borrow_map.put("date", DateUtils.format(new Date(), DateUtils.YMD_DASH)); // 当前日期
							borrow_map.put("c_reward", 0); // 续投奖励
							if (borrow.getBorrowtype() == 2) {
								borrow_map.put("ctype", 2); // 标类型(1,信用,2抵押,3质押,4担保,5净值,6秒标,7其它)
							} else if (borrow.getBorrowtype() == 3) {
								borrow_map.put("ctype", 5); // 标类型(1,信用,2抵押,3质押,4担保,5净值,6秒标,7其它)
							}
							loans.add(JsonUtils.toJson(borrow_map));
						}
					}
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("date", date);
					map.put("loans", loans);
					map.put("total", list.size());

					externalAccessLog.setStatus(0); // 成功
					externalAccessLog.setReturnTime(DateUtils.getTime());
					outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
					return JsonUtils.toJson(map).replace("\\", "").replace("\\", "").replace("\"{", "{").replace("}\"", "}");
				} catch (Exception e) {
					externalAccessLog.setStatus(-2);
					externalAccessLog.setReturnTime(DateUtils.getTime());
					outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
					return "-2"; // 调用失败
				}
			} else {
				externalAccessLog.setStatus(-3);
				externalAccessLog.setReturnTime(DateUtils.getTime());
				outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
				// 调用过于频繁
				return "-3";
			}
		} else {
			try {
				externalAccessLog.setStatus(-1); // 此IP无权限访问
				externalAccessLog.setReturnTime(DateUtils.getTime());
				outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// ip无访问权限
			return "-1";
		}
	}

	public ExternalAccessLog packageExternalAccessLog(String ip, String url, String accessType, String accessClass, String accessMethod) {
		ExternalAccessLog externalAccessLog = new ExternalAccessLog();
		externalAccessLog.setAccessIp(ip);
		externalAccessLog.setAccessUrl(url);
		externalAccessLog.setAccessType(accessType);
		externalAccessLog.setAccessClass(accessClass);
		externalAccessLog.setAccessMethod(accessMethod);
		externalAccessLog.setAccessTime(DateUtils.getTime());
		return externalAccessLog;
	}

	public Map<String, Object> findTodayBorrowInfo(String date) throws Exception {
		String beginTime = "";
		String endTime = "";
		if (date != null && !date.equals("")) {
			beginTime = DateUtils.dateTime2TimeStamp(date + " 00:00:00");
			endTime = DateUtils.dateTime2TimeStamp(date + " 23:59:59");
		}
		return outerInterfaceMapper.findTodayBorrowInfo(beginTime, endTime);
	}

	public BigDecimal findTodayPeriod(String date) throws Exception {
		String beginTime = "";
		String endTime = "";
		if (date != null && !date.equals("")) {
			beginTime = DateUtils.dateTime2TimeStamp(date + " 00:00:00");
			endTime = DateUtils.dateTime2TimeStamp(date + " 23:59:59");
		}
		return outerInterfaceMapper.findTodayPeriod(beginTime, endTime);
	}

	public int judgeIPByAccessByDate(String ip, String accessType, int accessTimeAdd, String accessTime) throws Exception {
		ExternalAccessLogCnd externalAccessLogCnd = new ExternalAccessLogCnd();
		externalAccessLogCnd.setAccessIp(ip);
		externalAccessLogCnd.setAccessType(accessType);
		externalAccessLogCnd.setAccessTimeAdd(accessTimeAdd);
		externalAccessLogCnd.setAccessTime(accessTime);
		return outerInterfaceMapper.judgeIPByAccessByDate(externalAccessLogCnd);
	}

	public List<BorrowVo> findBorrowInfo(String date) throws Exception {
		String beginTime = "";
		String endTime = "";
		if (date != null && !date.equals("")) {
			beginTime = DateUtils.dateTime2TimeStamp(date + " 00:00:00");
			endTime = DateUtils.dateTime2TimeStamp(date + " 23:59:59");
		}
		return outerInterfaceMapper.findBorrowInfo(beginTime, endTime);
	}
	
	/**
	 * 根据查询条件获取信息
	 * @author WangQianJin
	 * @title @param parameteVo
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年12月16日
	 */
	public WdtyMemberBinding queryMemberBindingInfo(WdtyParamCnd parameteVo) throws AppException{
		return wdtyInterfaceMapper.queryMemberBindingInfo(parameteVo);
	}
	
	/**
	 * 添加网贷信息
	 * @author WangQianJin
	 * @title @param externalAccessLog
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年12月16日
	 */
	public int insertWdtyMemberBinding(WdtyMemberBinding wdtyMemberBinding) throws AppException{
		return wdtyInterfaceMapper.insertWdtyMemberBinding(wdtyMemberBinding);
	}
	
	/**
	 * 修改网贷天眼信息
	 * @author WangQianJin
	 * @title @param externalAccessLog
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年12月16日
	 */
	public int updateMemberBindingInfo(WdtyMemberBinding wdtyMemberBinding) throws AppException{
		return wdtyInterfaceMapper.updateMemberBindingInfo(wdtyMemberBinding);
	}
	
	/**
	 * 根据Key查询网贷信息
	 * @author WangQianJin
	 * @title @param parameteVo
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年12月16日
	 */
	public WdtyMemberBinding queryMemberBindingInfoByKey(WdtyParamCnd parameteVo) throws AppException{
		return wdtyInterfaceMapper.queryMemberBindingInfoByKey(parameteVo);
	}
	
	/**
	 * 根据ID修改loginKey
	 * @author WangQianJin
	 * @title @param externalAccessLog
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年12月16日
	 */
	public int updateLoginKeyById(WdtyMemberBinding wdtyMemberBinding) throws AppException{
		return wdtyInterfaceMapper.updateLoginKeyById(wdtyMemberBinding);
	}
}
