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

import com.dxjr.base.entity.BRepaymentRecord;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.borrow.vo.TenderRecordVo;
import com.dxjr.portal.outerInterface.entity.ExternalAccessLog;
import com.dxjr.portal.outerInterface.mapper.OuterInterfaceMapper;
import com.dxjr.portal.outerInterface.service.WDZJInterfaceService;
import com.dxjr.portal.outerInterface.vo.ExternalAccessLogCnd;
import com.dxjr.portal.repayment.vo.BRepaymentRecordVo;
import com.dxjr.portal.statics.CalculatorUtil;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.JsonUtils;

@Service
public class WDZJInterfaceServiceImpl implements WDZJInterfaceService {

	@Autowired
	private OuterInterfaceMapper outerInterfaceMapper;

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

	public int judgeIPByAccessByDate(String ip, String accessType, int accessTimeAdd, String accessTime) throws Exception {
		ExternalAccessLogCnd externalAccessLogCnd = new ExternalAccessLogCnd();
		externalAccessLogCnd.setAccessIp(ip);
		externalAccessLogCnd.setAccessType(accessType);
		externalAccessLogCnd.setAccessTimeAdd(accessTimeAdd);
		externalAccessLogCnd.setAccessTime(accessTime);
		return outerInterfaceMapper.judgeIPByAccessByDate(externalAccessLogCnd);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String getNowProjects(String ip, String url) throws Exception {
		// 记录访问日志
		ExternalAccessLog externalAccessLog = packageExternalAccessLog(ip, url, Constants.PLATFORM_NOW_BORROW_DATA, "WDZJInterfaceController.class", "getNowProjects");
		// 当前时间戳
		String now = DateUtils.getTime();
		// 获取当前正在进行投标中的标信息.（每1分钟访问一次）
		if (judgeIPByAccessByDate(ip, Constants.PLATFORM_NOW_BORROW_DATA, 60, now) == 0) {
			try {
				// 获取正在投标中的借款标
				List<BorrowVo> list = outerInterfaceMapper.findBorrowByNow();
				// 封装借款标信息.
				List<String> loans = getLoans(list, Constants.PLATFORM_NOW_BORROW_DATA);
				externalAccessLog.setStatus(0); // 成功
				externalAccessLog.setReturnTime(DateUtils.getTime());
				outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
				return JsonUtils.toJson(loans).replace("\\", "").replace("\\", "").replace("\"{", "{").replace("}\"", "}");
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
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String getProjectsByDate(String ip, String url, String date) throws Exception {
		// 记录访问日志
		ExternalAccessLog externalAccessLog = packageExternalAccessLog(ip, url, Constants.PLATFORM_GET_BORROW_BY_DATA, "WDZJInterfaceController.class", "getProjectsByDate");
		// 当前时间戳
		String now = DateUtils.getTime();
		// 获取当前正在进行投标中的标信息.（每1分钟访问一次）
		if (judgeIPByAccessByDate(ip, Constants.PLATFORM_GET_BORROW_BY_DATA, 60, now) == 0) {
			try {
				DateUtils.parse(date, DateUtils.YMD_DASH);
			} catch (Exception e) {
				externalAccessLog.setStatus(-4);
				externalAccessLog.setReturnTime(DateUtils.getTime());
				outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
				return "-4"; // 参数错误
			}

			try {
				// 获取正在投标中的借款标
				List<BorrowVo> list = findBorrowByDate(date);
				// 封装借款标信息.
				List<String> loans = getLoans(list, Constants.PLATFORM_GET_BORROW_BY_DATA);
				externalAccessLog.setStatus(0); // 成功
				externalAccessLog.setReturnTime(DateUtils.getTime());
				outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
				return JsonUtils.toJson(loans).replace("\\", "").replace("\\", "").replace("\"{", "{").replace("}\"", "}");
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
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String getTodayProjects(String ip, String url) throws Exception {
		// 记录访问日志
		ExternalAccessLog externalAccessLog = packageExternalAccessLog(ip, url, Constants.PLATFORM_GET_BORROW_BY_NOW, "WDZJInterfaceController.class", "getTodayProjects");
		// 当前时间戳
		String now = DateUtils.getTime();
		// 获取当前正在进行投标中的标信息.（每1分钟访问一次）
		if (judgeIPByAccessByDate(ip, Constants.PLATFORM_GET_BORROW_BY_NOW, 60, now) == 0) {
			try {
				// 今天的日期格式字符串
				String date = DateUtils.format(new Date(), DateUtils.YMD_DASH); // 格式为:yyyy-MM-dd
				// 获取正在投标中的借款标
				List<BorrowVo> list = findBorrowByDate(date);
				// 封装借款标信息.
				List<String> loans = getLoans(list, Constants.PLATFORM_GET_BORROW_BY_DATA);
				externalAccessLog.setStatus(0); // 成功
				externalAccessLog.setReturnTime(DateUtils.getTime());
				outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
				return JsonUtils.toJson(loans).replace("\\", "").replace("\\", "").replace("\"{", "{").replace("}\"", "}");
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
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String getPaiedLoanInfo(String ip, String url, String date) throws Exception {
		// 记录访问日志
		ExternalAccessLog externalAccessLog = packageExternalAccessLog(ip, url, Constants.PLATFORM_GET_PAIED_LOAN_INFO, "WDZJInterfaceController.class", "getPaiedLoanInfo");
		// 当前时间戳
		String now = DateUtils.getTime();
		// 获取当前正在进行投标中的标信息.（每1分钟访问一次）
		if (judgeIPByAccessByDate(ip, Constants.PLATFORM_GET_PAIED_LOAN_INFO, 60, now) == 0) {
			try {
				try {
					DateUtils.parse(date, DateUtils.YMD_DASH);
				} catch (Exception e) {
					externalAccessLog.setStatus(-4);
					externalAccessLog.setReturnTime(DateUtils.getTime());
					outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
					return "-4"; // 参数错误
				}
				// 获取某天还款的借款标信息
				List<BorrowVo> list = getPaiedLoanInfo(date);
				// 封装借款标信息.
				List<String> loans = new ArrayList<String>();
				if (list.size() > 0) {
					for (BorrowVo borrow : list) {
						Map<String, Object> borrow_map = new HashMap<String, Object>();
						String now_str = DateUtils.date2TimeStamp(DateUtils.format(new Date(), DateUtils.YMD_DASH));
						List<BRepaymentRecordVo> repaymentRecordlist = outerInterfaceMapper.queryRepaymentRecordByBorrow(borrow.getId());
						String status_str = "";
						StringBuffer sb = new StringBuffer();
						for (BRepaymentRecord repaymentRecord : repaymentRecordlist) {
							String repay_time = DateUtils.date2TimeStamp(DateUtils.timeStampToDate(repaymentRecord.getRepaymentTime(), DateUtils.YMD_DASH)); // 当前还款日期
							if (repaymentRecord.getStatus() == 0 && repaymentRecord.getWebstatus() == 1) {
								sb.append("垫付");
							}
							if (repaymentRecord.getStatus() == 0 && repaymentRecord.getWebstatus() == 0 && Integer.parseInt(now_str) > Integer.parseInt(repay_time)) {
								sb.append("推迟还款");
							}
							if (repaymentRecord.getStatus() == 1) {
								sb.append("已还款");
							}
						}
						if (!sb.equals("")) {
							if (sb.indexOf("已还款") >= 0 && (sb.indexOf("推迟还款") >= 0 || sb.indexOf("垫付") >= 0)) {
								status_str = "部分还款";
							} else if (sb.indexOf("已还款") >= 0 && sb.indexOf("推迟还款") < 0 && sb.indexOf("垫付") < 0) {
								status_str = "全部还款";
							} else if (sb.indexOf("已还款") < 0 && sb.indexOf("推迟还款") >= 0 && sb.indexOf("垫付") < 0) {
								status_str = "推迟还款";
							} else if (sb.indexOf("已还款") < 0 && sb.indexOf("垫付") >= 0) {
								status_str = "垫付";
							} else {
								status_str = "到期未还款";
							}
						} else {
							status_str = "到期未还款";
						}
						borrow_map.put("status", status_str); // 还款状态
						borrow_map.put("ID", borrow.getId()); // 标ID
						loans.add(JsonUtils.toJson(borrow_map));
					}
				}

				externalAccessLog.setStatus(0); // 成功
				externalAccessLog.setReturnTime(DateUtils.getTime());
				outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
				return JsonUtils.toJson(loans).replace("\\", "").replace("\\", "").replace("\"{", "{").replace("}\"", "}");
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
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String checkUserInfo(String ip, String url, String userName, String realName, String cardId) throws Exception {
		// 记录访问日志
		ExternalAccessLog externalAccessLog = packageExternalAccessLog(ip, url, Constants.CHECK_USER_INFO, "WDZJInterfaceController.class", "checkUserInfo");
		if (userName == null || userName.equals("") || realName == null || realName.equals("") || cardId == null || cardId.equals("")) {
			externalAccessLog.setStatus(-4);
			externalAccessLog.setReturnTime(DateUtils.getTime());
			outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
			return "-4"; // 参数错误
		}
		try {
			boolean result = false;
			if (outerInterfaceMapper.judgeUser(userName, realName, cardId) > 0) {
				result = true;
			}
			externalAccessLog.setStatus(0); // 成功
			externalAccessLog.setReturnTime(DateUtils.getTime());
			outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
			return String.valueOf(result);
		} catch (Exception e) {
			e.printStackTrace();
			externalAccessLog.setStatus(-2);
			externalAccessLog.setReturnTime(DateUtils.getTime());
			outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
			// 调用失败
			return "-2";
		}
	}

	/**
	 * 
	 * <p>
	 * Description:封装借款标信息.<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param list
	 * @param type
	 * @return List<String>
	 */
	private List<String> getLoans(List<BorrowVo> list, String type) throws Exception {
		List<String> loans = new ArrayList<String>();
		if (list.size() > 0) {
			for (BorrowVo borrow : list) {
				List<TenderRecordVo> bTenderRecordList = outerInterfaceMapper.queryTenderRecordByBorrow(borrow.getId());

				Map<String, Object> borrow_map = new HashMap<String, Object>();
				borrow_map.put("Title", borrow.getName()); // 标题
				borrow_map.put("Amount", borrow.getAccount().setScale(2, BigDecimal.ROUND_DOWN)); // 贷款额度（元）
				BigDecimal schedule = borrow.getAccountYes().divide(borrow.getAccount(), Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_DOWN);
				borrow_map.put("Schedule", schedule); // 进度
				borrow_map.put("InterestRate", borrow.getApr()); // 年化利率
				borrow_map.put("Deadline", borrow.getTimeLimit());// 还款期限
				borrow_map.put("TuandaiRate", BigDecimal.ZERO); // 奖励
				if (borrow.getBorrowtype() == 2) {
					borrow_map.put("Type", "抵押标"); // 借款类型
				} else if (borrow.getBorrowtype() == 3) {
					borrow_map.put("Type", "净值标"); // 借款类型
				} else if (borrow.getBorrowtype() == 1) {
					borrow_map.put("Type", "信用标"); // 借款类型
				} else if (borrow.getBorrowtype() == 5) {
					borrow_map.put("Type", "担保标"); // 借款类型
				}
				if (borrow.getStyle() == 1) { // 按月分期还款
					borrow_map.put("RepaymentType", "等额本息"); // 还款方式
				} else if (borrow.getStyle() == 2) { // 按月付息到期还本
					borrow_map.put("RepaymentType", "按月付息到期还本"); // 还款方式
				} else if (borrow.getStyle() == 3) { // 到期还本付息
					borrow_map.put("RepaymentType", "到期还本付息"); // 还款方式
				} else if (borrow.getStyle() == 4) { // 按天还款
					borrow_map.put("RepaymentType", "按天还款"); // 还款方式
				}
				borrow_map.put("ProjectId", borrow.getId()); // 项目编号
				borrow_map.put("TotalShares", bTenderRecordList.size()); // 总共多少份
				borrow_map.put("CastedShares", bTenderRecordList.size()); // 已投多少份
				if (borrow.getStyle() == 4) {
					borrow_map.put("DeadLineUnit", "天"); // 期限单位
				} else {
					borrow_map.put("DeadLineUnit", "月"); // 期限单位
				}
				borrow_map.put("Province", ""); // 省
				borrow_map.put("City", ""); // 城市
				borrow_map.put("UserName", borrow.getUserName()); // 发标人
				if (borrow.getHeadimg() != null && borrow.getHeadimg().equals("")) {
					borrow_map.put("UserAvatarUr", "https://www.dxjr.com" + borrow.getHeadimg()); // 发标人头像
				} else {
					borrow_map.put("UserAvatarUr", ""); // 发标人头像
				}
				if (borrow.getUse() != null) {
					if (borrow.getUse().equals("1")) {
						borrow_map.put("AmountUsedDesc", "短期周转"); // 用途
					} else if (borrow.getUse().equals("2")) {
						borrow_map.put("AmountUsedDesc", "生意周转"); // 用途
					} else if (borrow.getUse().equals("3")) {
						borrow_map.put("AmountUsedDesc", "生活周转"); // 用途
					} else if (borrow.getUse().equals("4")) {
						borrow_map.put("AmountUsedDesc", "购物消费"); // 用途
					} else if (borrow.getUse().equals("5")) {
						borrow_map.put("AmountUsedDesc", "创业借款"); // 用途
					} else if (borrow.getUse().equals("6")) {
						borrow_map.put("AmountUsedDesc", "其它借款"); // 用途
					}
				} else {
					borrow_map.put("AmountUsedDesc", "其它借款"); // 用途
				}

				// 借款管理费
				BigDecimal manageFee = getManagerFee(borrow);
				borrow_map.put("Revenue", manageFee.setScale(2, BigDecimal.ROUND_DOWN)); // 营收金额即借款管理费

				if (type.equals("platform_get_paied_loan_info")) { // 某天还款的标的信息
					String now = DateUtils.date2TimeStamp(DateUtils.format(new Date(), DateUtils.YMD_DASH));
					List<BRepaymentRecordVo> repaymentRecordlist = outerInterfaceMapper.queryRepaymentRecordByBorrow(borrow.getId());
					String status_str = "";
					StringBuffer sb = new StringBuffer();
					for (BRepaymentRecordVo repaymentRecord : repaymentRecordlist) {
						String repay_time = DateUtils.date2TimeStamp(DateUtils.timeStampToDate(repaymentRecord.getRepaymentTime(), DateUtils.YMD_DASH)); // 当前还款日期
						if (repaymentRecord.getStatus() == 0 && repaymentRecord.getWebstatus() == 1) {
							sb.append("垫付");
						}
						if (repaymentRecord.getStatus() == 0 && repaymentRecord.getWebstatus() == 0 && Integer.parseInt(now) > Integer.parseInt(repay_time)) {
							sb.append("推迟还款");
						}
						if (repaymentRecord.getStatus() == 1) {
							sb.append("已还款");
						}
					}
					if (!sb.equals("")) {
						if (sb.indexOf("已还款") >= 0 && (sb.indexOf("推迟还款") >= 0 || sb.indexOf("垫付") >= 0)) {
							status_str = "部分还款";
						} else if (sb.indexOf("已还款") >= 0 && sb.indexOf("推迟还款") < 0 && sb.indexOf("垫付") < 0) {
							status_str = "全部还款";
						} else if (sb.indexOf("已还款") < 0 && sb.indexOf("推迟还款") >= 0 && sb.indexOf("垫付") < 0) {
							status_str = "推迟还款";
						} else if (sb.indexOf("已还款") < 0 && sb.indexOf("垫付") >= 0) {
							status_str = "垫付";
						} else {
							status_str = "到期未还款";
						}
					} else {
						status_str = "到期未还款";
					}
					borrow_map.put("status", status_str); // 还款状态
					borrow_map.put("ID", borrow.getId()); // 标ID
				}

				List<String> investorInfoList = new ArrayList<String>();
				if (bTenderRecordList.size() > 0) {
					for (TenderRecordVo bTenderRecord : bTenderRecordList) {
						Map<String, Object> tenderRecord_map = new HashMap<String, Object>();
						tenderRecord_map.put("Id", bTenderRecord.getId()); // 主键
						tenderRecord_map.put("ProjectId", borrow.getContractNo()); // 项目编号
						tenderRecord_map.put("SubscribeUserId", bTenderRecord.getUsername()); // 投标人用户名
						tenderRecord_map.put("AddDate", DateUtils.timeStampToDate(bTenderRecord.getAddtime(), DateUtils.YMD_HMS)); // 投标时间
						if (borrow.getStatus() == 2 || borrow.getStatus() == 3) {
							tenderRecord_map.put("Status", "正在申购"); // 状态
						} else if (borrow.getStatus() < 1) {
							tenderRecord_map.put("Status", "申购失败"); // 状态
						} else if (borrow.getStatus() == 4) {
							tenderRecord_map.put("Status", "回收中"); // 状态
						} else if (borrow.getStatus() == 5) {
							tenderRecord_map.put("Status", "投资完成"); // 状态
						}
						tenderRecord_map.put("Amount", bTenderRecord.getAccount().setScale(2, BigDecimal.ROUND_DOWN)); // 投标金额
						if (bTenderRecord.getTenderType() == 0) {
							tenderRecord_map.put("Type", "手动"); // 投标类型 0:自动
																// 1:手动
						} else {
							tenderRecord_map.put("Type", "自动"); // 投标类型 0:自动
																// 1:手动
						}
						investorInfoList.add(JsonUtils.toJson(tenderRecord_map));
					}
				}
				borrow_map.put("InvestorInfoList", investorInfoList); // 用途
				loans.add(JsonUtils.toJson(borrow_map));
			}
		}
		return loans;
	}

	public List<BorrowVo> findBorrowByDate(String date) throws Exception {
		String beginTime = "";
		String endTime = "";
		if (date != null && !date.equals("")) {
			beginTime = DateUtils.dateTime2TimeStamp(date + " 00:00:00");
			endTime = DateUtils.dateTime2TimeStamp(date + " 23:59:59");
		}
		return outerInterfaceMapper.findBorrowByDate(beginTime, endTime);
	}

	public List<BorrowVo> getPaiedLoanInfo(String date) throws Exception {
		String beginTime = "";
		String endTime = "";
		if (date != null && !date.equals("")) {
			beginTime = DateUtils.dateTime2TimeStamp(date + " 00:00:00");
			endTime = DateUtils.dateTime2TimeStamp(date + " 23:59:59");
		}
		return outerInterfaceMapper.getPaiedLoanInfo(beginTime, endTime);
	}

	public BigDecimal getManagerFee(BorrowVo borrow) {
		BigDecimal manageFee = BigDecimal.ZERO;
		// 推荐标 借款金额*1%
		if (borrow.getBorrowtype() == 1) {
			if (borrow.getStyle() == 4) {
				// 按天
				manageFee = CalculatorUtil.getManagementCostOfPledgeBidByPeriodDay(borrow.getAccount(), borrow.getApr(), new BigDecimal(borrow.getTimeLimit()));
			} else {
				// 按月
				manageFee = CalculatorUtil.getManagementCostOfPledgeBidByPeriodMonth(borrow.getAccount(), new BigDecimal(borrow.getTimeLimit()));
			}
		}
		// 净值标
		if (borrow.getBorrowtype() == 3) {
			if (borrow.getStyle() == 4) {
				// 按天 （天数*0.3%*借款金额）/30 与 借款金额*0.1% 哪个大取哪个
				manageFee = CalculatorUtil.getManagementCostOfWorthBidByDay(borrow.getAccount(), new BigDecimal(borrow.getTimeLimit()));
			} else {
				// 借款金额*月数*0.3%
				manageFee = CalculatorUtil.getManagementCostOfWorthBidByMonth(borrow.getAccount(), new BigDecimal(borrow.getTimeLimit()));
			}
		}
		// 抵押标
		if (borrow.getBorrowtype() == 2) {
			if (borrow.getStyle() == 4) {
				// 按天
				manageFee = CalculatorUtil.getManagementCostOfPledgeBidByPeriodDay(borrow.getAccount(), borrow.getApr(), new BigDecimal(borrow.getTimeLimit()));
			} else {
				// 按月
				manageFee = CalculatorUtil.getManagementCostOfPledgeBidByPeriodMonth(borrow.getAccount(), new BigDecimal(borrow.getTimeLimit()));
			}
		}
		// 秒标 0
		if (borrow.getBorrowtype() == 4) {
			manageFee = BigDecimal.ZERO;
		}

		System.out.println("manageFeemanageFeemanageFee==" + manageFee);
		return manageFee;
	}
}
