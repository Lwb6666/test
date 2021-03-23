package com.dxjr.portal.outerInterface.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.borrow.vo.TenderRecordVo;
import com.dxjr.portal.outerInterface.entity.ExternalAccessLog;
import com.dxjr.portal.outerInterface.mapper.OuterInterfaceMapper;
import com.dxjr.portal.outerInterface.service.HSWInterfaceService;
import com.dxjr.portal.outerInterface.vo.ExternalAccessLogCnd;
import com.dxjr.portal.outerInterface.vo.OuterParamCnd;
import com.dxjr.portal.outerInterface.vo.OverdueBorrowVo;
import com.dxjr.portal.repayment.vo.BRepaymentRecordVo;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.JsonUtils;

@Service
public class HSWInterfaceServiceImpl implements HSWInterfaceService {

	@Autowired
	private OuterInterfaceMapper outerInterfaceMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String getBorrows(String ip, String url, OuterParamCnd outerParamCnd, int pageNum, int pageSize) throws Exception {
		// 记录访问日志
		ExternalAccessLog externalAccessLog = packageExternalAccessLog(ip, url, Constants.HSW_BORROW_INFO, "HSWInterfaceController.class", "getBorrows");

		// 判断是否有访问权限
		if (outerInterfaceMapper.judgeIPForHSW(ip) > 0) {
			// 当前时间戳
			String now = DateUtils.getTime();
			if (judgeIPByAccessByDate(ip, Constants.HSW_BORROW_INFO, 60, now) == 0) {
				try {
					if (outerParamCnd.getBeginTime() != null && !outerParamCnd.getBeginTime().equals("")) {
						outerParamCnd.setBeginDate(DateUtils.parse(outerParamCnd.getBeginTime() + " 00:00:00", DateUtils.YMD_HMS));
						outerParamCnd.setBeginTime(DateUtils.dateTime2TimeStamp(outerParamCnd.getBeginTime() + " 00:00:00"));
					}
					if (outerParamCnd.getEndTime() != null && !outerParamCnd.getEndTime().equals("")) {
						outerParamCnd.setEndDate(DateUtils.parse(outerParamCnd.getEndTime() + " 23:59:59", DateUtils.YMD_HMS));
						outerParamCnd.setEndTime(DateUtils.dateTime2TimeStamp(outerParamCnd.getEndTime() + " 23:59:59"));
					}
				} catch (Exception e) {
					e.printStackTrace();
					externalAccessLog.setStatus(-4);
					externalAccessLog.setReturnTime(DateUtils.getTime());
					outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
					// 参数错误
					return "-4";
				}
				try {
					Page page = new Page(pageNum, pageSize);

					int totalCount = outerInterfaceMapper.queryBorrowCountByOuterParam(outerParamCnd);
					page.setTotalCount(totalCount);
					List<BorrowVo> list = outerInterfaceMapper.queryBorrowListByOuterParam(outerParamCnd, page);
					page.setResult(list);

					Map<String, Object> result_map = new HashMap<String, Object>();
					result_map.put("result_code", 1); // 返回码
					result_map.put("result_msg", "获取数据成功"); // 返回消息
					result_map.put("page_count", page.getTotalPage()); // 总页数
					result_map.put("page_index", pageNum); // 当前页数
					List<String> data = new ArrayList<String>();

					for (int i = 0; i < list.size(); i++) {
						Map<String, Object> borrow_map = new HashMap<String, Object>();
						BorrowVo borrow = (BorrowVo) list.get(i);
						borrow_map.put("id", borrow.getId());
						borrow_map.put("link", "https://www.dxjr.com/toubiao/" + borrow.getId() + ".html"); // 当前页数
						borrow_map.put("title", borrow.getName()); // 借款标题
						borrow_map.put("username", borrow.getUserName()); // 借款用户名
						borrow_map.put("userid", borrow.getUserId()); // 借款用户ID
						if (borrow.getBorrowtype() == 1) {
							borrow_map.put("borrow_type", "信用标"); // 借款类型
						} else if (borrow.getBorrowtype() == 2) {
							borrow_map.put("borrow_type", "抵押标"); // 借款类型
						} else if (borrow.getBorrowtype() == 3) {
							borrow_map.put("borrow_type", "净值标"); // 借款类型
						} else if (borrow.getBorrowtype() == 5) {
							borrow_map.put("borrow_type", "担保标"); // 借款类型
						}
						borrow_map.put("amount", borrow.getAccount().setScale(2, BigDecimal.ROUND_DOWN)); // 借款金额
						borrow_map.put("interest", borrow.getApr().multiply(new BigDecimal(0.01)).setScale(2, BigDecimal.ROUND_DOWN)); // 借款年化利率
						if (borrow.getStyle() == 4) {
							borrow_map.put("borrow_period", borrow.getTimeLimit() + "天"); // 借款期限
						} else {
							borrow_map.put("borrow_period", borrow.getTimeLimit() + "个月"); // 借款期限
						}
						if (borrow.getStyle() == 1) {
							borrow_map.put("repay_type", "等额本息"); // 还款方式
						} else if (borrow.getStyle() == 2) {
							borrow_map.put("repay_type", "按月付息到期还本"); // 还款方式
						} else if (borrow.getStyle() == 3) {
							borrow_map.put("repay_type", "到期还本付息"); // 还款方式
						} else if (borrow.getStyle() == 4) {
							borrow_map.put("repay_type", "按天到期还款"); // 还款方式
						}
						BigDecimal schedule = borrow.getAccountYes().multiply(new BigDecimal(100)).divide(borrow.getAccount(), Constants.CALCULATOR_MONEY_NUM, BigDecimal.ROUND_DOWN);
						borrow_map.put("percentage", schedule); // 完成进度
						borrow_map.put("reward", "0"); // 投标奖励
						borrow_map.put("guarantee", "0"); // 担保奖励
						borrow_map.put("credit", ""); // 信用等级
						if (borrow.getPublishTime() != null && !borrow.getPublishTime().equals("")) {
							borrow_map.put("verify_time", DateUtils.timeStampToDate(borrow.getPublishTime(), DateUtils.YMD_HMS)); // 发标时间
						}
						if (borrow.getSuccessTime() != null && !borrow.getSuccessTime().equals("")) {
							borrow_map.put("reverify_time", DateUtils.timeStampToDate(borrow.getSuccessTime(), DateUtils.YMD_HMS)); // 满标时间
						} else {
							borrow_map.put("reverify_time", "");
						}
						data.add(JsonUtils.toJson(borrow_map));
					}
					result_map.put("data", data);
					externalAccessLog.setStatus(0); // 成功
					externalAccessLog.setReturnTime(DateUtils.getTime());
					outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
					return JsonUtils.toJson(result_map).replace("\\", "").replace("\\", "").replace("\"{", "{").replace("}\"", "}");
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
	public String getTenderRecord(String ip, String url, String id, int pageNum, int pageSize) throws Exception {
		// 记录访问日志
		ExternalAccessLog externalAccessLog = packageExternalAccessLog(ip, url, Constants.HSW_TENDERRECORD_INFO, "HSWInterfaceController.class", "getTenderRecord");

		// 判断是否有访问权限
		if (outerInterfaceMapper.judgeIPForHSW(ip) > 0) {
			// 当前时间戳
			String now = DateUtils.getTime();
			if (judgeIPByAccessByDate(ip, Constants.HSW_TENDERRECORD_INFO, 60, now) == 0) {
				if (id == null || id.equals("")) {
					externalAccessLog.setStatus(-4);
					externalAccessLog.setReturnTime(DateUtils.getTime());
					outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
					// 参数错误
					return "-4";
				}
				try {
					Page page = new Page(pageNum, pageSize);
					OuterParamCnd outerParamCnd = new OuterParamCnd();
					outerParamCnd.setBorrowid(Integer.parseInt(id));

					int totalCount = outerInterfaceMapper.queryTenderRecordCountByBorrow(outerParamCnd);
					page.setTotalCount(totalCount);
					List<TenderRecordVo> list = outerInterfaceMapper.queryTenderRecordListByBorrow(outerParamCnd, page);
					page.setResult(list);

					Map<String, Object> result_map = new HashMap<String, Object>();
					result_map.put("result_code", 1); // 返回码
					result_map.put("result_msg", "获取数据成功"); // 返回消息
					result_map.put("page_count", page.getTotalPage()); // 总页数
					result_map.put("page_index", pageNum); // 当前页数
					List<String> data = new ArrayList<String>();

					for (int i = 0; i < list.size(); i++) {
						Map<String, Object> tender_record_map = new HashMap<String, Object>();
						TenderRecordVo tenderRecord = (TenderRecordVo) list.get(i);
						tender_record_map.put("id", tenderRecord.getId());
						tender_record_map.put("link", "https://www.dxjr.com/toubiao/" + tenderRecord.getBorrowId() + ".html"); // 当前页数
						tender_record_map.put("username", tenderRecord.getUsername()); // 借款用户名
						tender_record_map.put("userid", tenderRecord.getUserId()); // 借款用户ID
						if (tenderRecord.getTenderType() == 0) {
							tender_record_map.put("type", "手动投标"); // 投标方式
						} else if (tenderRecord.getTenderType() == 1) {
							tender_record_map.put("type", "自动投标"); // 投标方式
						} else if (tenderRecord.getTenderType() == 2) {
							tender_record_map.put("type", "投标直通车"); // 投标方式
						}
						tender_record_map.put("money", tenderRecord.getAccount().setScale(2, BigDecimal.ROUND_DOWN)); // 投标金额
						tender_record_map.put("account", tenderRecord.getAccount().setScale(2, BigDecimal.ROUND_DOWN)); // 有效金额

						if (tenderRecord.getStatus() == -1) {
							tender_record_map.put("status", "投标失败"); // 投标状态
						} else if (tenderRecord.getStatus() == 0) {
							tender_record_map.put("status", "投标中"); // 投标状态
						} else if (tenderRecord.getStatus() == 1) {
							tender_record_map.put("status", "投标成功"); // 投标状态
						} else if (tenderRecord.getStatus() == 2) {
							tender_record_map.put("status", "投标成功"); // 投标状态
						}
						tender_record_map.put("add_time", DateUtils.timeStampToDate(tenderRecord.getAddtime(), DateUtils.YMD_HMS)); // 发标时间
						data.add(JsonUtils.toJson(tender_record_map));
					}
					result_map.put("data", data);

					externalAccessLog.setStatus(0); // 成功
					externalAccessLog.setReturnTime(DateUtils.getTime());
					outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);

					return JsonUtils.toJson(result_map).replace("\\", "").replace("\\", "").replace("\"{", "{").replace("}\"", "}");
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
	public String getOverdueBorrows(String ip, String url, int pageNum, int pageSize) throws Exception {
		// 记录访问日志
		ExternalAccessLog externalAccessLog = packageExternalAccessLog(ip, url, Constants.HSW_OVERDUEBORROWS_INFO, "HSWInterfaceController.class", "getOverdueBorrows");

		// 判断是否有访问权限
		if (outerInterfaceMapper.judgeIPForHSW(ip) > 0) {
			// 当前时间戳
			String now = DateUtils.getTime();
			if (judgeIPByAccessByDate(ip, Constants.HSW_OVERDUEBORROWS_INFO, 60, now) == 0) {
				try {
					Page page = new Page(pageNum, pageSize);

					int totalCount = outerInterfaceMapper.findOverdueBorrowCout();
					page.setTotalCount(totalCount);
					List<OverdueBorrowVo> list = outerInterfaceMapper.findOverdueBorrowList(page);
					page.setResult(list);

					List<OverdueBorrowVo> new_overdueBorrowVoList = new ArrayList<OverdueBorrowVo>();
					if (list.size() > 0) {
						for (OverdueBorrowVo overdueBorrowVo : list) {
							// 获取某人的逾期的记录
							List<BRepaymentRecordVo> repaymentRecordList = outerInterfaceMapper.findOverdueRepaymentRecordList(overdueBorrowVo.getUserId());
							BigDecimal overdue_total = BigDecimal.ZERO; // 逾期总额
							BigDecimal overdue_principal = BigDecimal.ZERO; // 逾期本金
							for (int i = 0; i < repaymentRecordList.size(); i++) {
								BRepaymentRecordVo repaymentRecord = (BRepaymentRecordVo) repaymentRecordList.get(i);
								overdue_total = overdue_total.add(repaymentRecord.getRepaymentAccount());
								overdue_principal = overdue_principal.add(repaymentRecord.getCapital());
							}
							overdueBorrowVo.setOverdueTotal(overdue_total);
							overdueBorrowVo.setOverduePrincipal(overdue_principal);
							// 待还本金
							BigDecimal unpayCapital = outerInterfaceMapper.queryUnpayCapitalByMemberId(overdueBorrowVo.getUserId());
							if (unpayCapital == null) {
								unpayCapital = new BigDecimal(0);
							}
							// 待还利息
							BigDecimal waitPayInterest = outerInterfaceMapper.queryWaitPayInterestByMemberId(overdueBorrowVo.getUserId());
							if (waitPayInterest == null) {
								waitPayInterest = new BigDecimal(0);
							}
							overdueBorrowVo.setWaitAmount(unpayCapital.add(waitPayInterest)); // 待还金额
							new_overdueBorrowVoList.add(overdueBorrowVo);
						}
					}
					Map<String, Object> result_map = new HashMap<String, Object>();
					result_map.put("result_code", 1); // 返回码
					result_map.put("result_msg", "获取数据成功"); // 返回消息
					result_map.put("page_count", page.getTotalPage()); // 总页数
					result_map.put("page_index", pageNum); // 当前页数
					List<String> data = new ArrayList<String>();
					for (int i = 0; i < new_overdueBorrowVoList.size(); i++) {
						Map<String, Object> overdueBorrow_map = new HashMap<String, Object>();
						OverdueBorrowVo overdueBorrowVo = (OverdueBorrowVo) new_overdueBorrowVoList.get(i);
						overdueBorrow_map.put("userid", overdueBorrowVo.getUserId()); // 借款用户ID
						overdueBorrow_map.put("username", overdueBorrowVo.getUsername()); // 借款用户名
						overdueBorrow_map.put("idcard", ""); // 证件号码
						overdueBorrow_map.put("overdue_count", overdueBorrowVo.getOverdueCount()); // 逾期笔数
						overdueBorrow_map.put("overdue_period", overdueBorrowVo.getOverduePeriod()); // 逾期期数
						overdueBorrow_map.put("overdue_total", overdueBorrowVo.getOverdueTotal().setScale(2, BigDecimal.ROUND_DOWN)); // 逾期总额
						overdueBorrow_map.put("overdue_principal", overdueBorrowVo.getOverduePrincipal().setScale(2, BigDecimal.ROUND_DOWN)); // 逾期总额
						if (overdueBorrowVo.getPaymentTotal() != null) {
							overdueBorrow_map.put("payment_total", overdueBorrowVo.getPaymentTotal().setScale(2, BigDecimal.ROUND_DOWN)); // 垫付总额
						} else {
							overdueBorrow_map.put("payment_total", 0.00); // 垫付总额
						}
						if (overdueBorrowVo.getPaymentCount() != null) {
							overdueBorrow_map.put("payment_count", overdueBorrowVo.getPaymentCount()); // 垫付次数
						} else {
							overdueBorrow_map.put("payment_count", 0); // 垫付次数
						}

						if (overdueBorrowVo.getPaymentPeriod() != null) {
							overdueBorrow_map.put("payment_period", overdueBorrowVo.getPaymentPeriod()); // 垫付期数
						} else {
							overdueBorrow_map.put("payment_period", 0); // 垫付期数
						}
						if (overdueBorrowVo.getRepayAmount() != null) {
							overdueBorrow_map.put("repay_amount", overdueBorrowVo.getRepayAmount().setScale(2, BigDecimal.ROUND_DOWN)); // 已还金额
						} else {
							overdueBorrow_map.put("repay_amount", 0.00); // 已还金额
						}
						if (overdueBorrowVo.getWaitAmount() != null) {
							overdueBorrow_map.put("wait_amount", overdueBorrowVo.getWaitAmount().setScale(2, BigDecimal.ROUND_DOWN)); // 待还金额
						} else {
							overdueBorrow_map.put("wait_amount", 0.00); // 待还金额
						}
						data.add(JsonUtils.toJson(overdueBorrow_map));
					}
					result_map.put("data", data);

					externalAccessLog.setStatus(0); // 成功
					externalAccessLog.setReturnTime(DateUtils.getTime());
					outerInterfaceMapper.insertExternalAccessLog(externalAccessLog);
					return JsonUtils.toJson(result_map).replace("\\", "").replace("\\", "").replace("\"{", "{").replace("}\"", "}");
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
}
