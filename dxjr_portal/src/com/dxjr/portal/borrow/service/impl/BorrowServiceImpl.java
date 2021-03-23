package com.dxjr.portal.borrow.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxjr.base.entity.Account;
import com.dxjr.base.entity.AccountLog;
import com.dxjr.base.entity.BRepaymentRecord;
import com.dxjr.base.entity.Borrow;
import com.dxjr.base.entity.BorrowAdvanceLog;
import com.dxjr.base.entity.BorrowApproved;
import com.dxjr.base.entity.MessageRecord;
import com.dxjr.base.mapper.BaseAccountLogMapper;
import com.dxjr.base.mapper.BaseBorrowAdvanceLogMapper;
import com.dxjr.base.mapper.BaseBorrowApprovedMapper;
import com.dxjr.base.mapper.MessageRecordMapper;
import com.dxjr.common.custody.xml.XmlUtil;
import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.mapper.AccountMapper;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.service.CGUtilService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.autoInvestConfig.service.AutoInvestService;
import com.dxjr.portal.borrow.mapper.BorrowMapper;
import com.dxjr.portal.borrow.mapper.BorrowerMapper;
import com.dxjr.portal.borrow.service.BorrowApprovedService;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.service.TendRecordService;
import com.dxjr.portal.borrow.vo.BorrowApprovedVo;
import com.dxjr.portal.borrow.vo.BorrowCnd;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.borrow.vo.TenderBorrowCnd;
import com.dxjr.portal.borrow.vo.TenderRecordVo;
import com.dxjr.portal.curAccount.entity.CurOut;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.curAccount.service.CurOutService;
import com.dxjr.portal.first.service.FirstBorrowService;
import com.dxjr.portal.fix.mapper.FixBorrowTransferMapper;
import com.dxjr.portal.fix.service.FixBorrowService;
import com.dxjr.portal.fix.service.FixBorrowTransferService;
import com.dxjr.portal.fix.vo.FixBorrowTransferVo;
import com.dxjr.portal.invest.service.CollectionRecordService;
import com.dxjr.portal.invest.vo.CollectionRepayInfoVo;
import com.dxjr.portal.invest.vo.CollectionStatisticCnd;
import com.dxjr.portal.red.entity.RedAccount;
import com.dxjr.portal.red.entity.RedAccountLog;
import com.dxjr.portal.red.mapper.RedAccountLogMapper;
import com.dxjr.portal.red.mapper.RedAccountMapper;
import com.dxjr.portal.repayment.mapper.BRepaymentRecordMapper;
import com.dxjr.portal.repayment.vo.BRepaymentRecordVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.transfer.mapper.BTransferMapper;
import com.dxjr.portal.transfer.service.TransferService;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.MD5;
import com.dxjr.utils.exception.AppException;

@Service
public class BorrowServiceImpl implements BorrowService {

	@Autowired
	private BaseAccountLogMapper baseAccountLogMapper;

	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private RedAccountLogMapper redLogMapper;

	@Autowired
	private RedAccountMapper redMapper;

	@Autowired
	private BorrowMapper borrowMapper;
	@Autowired
	private BRepaymentRecordMapper bRepaymentRecordMapper;
	@Autowired
	private BorrowerMapper borrowerMapper;
	@Autowired
	private BaseBorrowAdvanceLogMapper baseBorrowAdvanceLogMapper;
	@Autowired
	private BaseBorrowApprovedMapper baseBorrowApprovedMapper;

	@Autowired
	private BTransferMapper bTransferMapper;

	@Autowired
	private BorrowApprovedService borrowApprovedService;

	@Autowired
	private AutoInvestService autoInvestService;

	@Autowired
	private FirstBorrowService firstBorrowService;

	@Autowired
	private AccountService accountService;
	@Autowired
	private TendRecordService tendRecordService;
	@Autowired
	private CollectionRecordService collectionRecordService;

	@Autowired
	private TransferService transferService;

	@Autowired
	private CurAccountService curAccountService;
	@Autowired
	private CurOutService curOutService;

	@Autowired
	private FixBorrowTransferMapper fixBorrowTransferMapper;

	@Autowired
	private FixBorrowTransferService fixBorrowTransferService;

	@Autowired
	private FixBorrowService fixBorrowService;
	@Autowired
	private CGUtilService cGUtilService;
	@Autowired
	private MessageRecordMapper messageRecordMapper;

	@Override
	public Page selectTenderBorrow(Page page) {
		Integer count = borrowMapper.countTenderBorrow();
		page.setTotalCount(count);
		List<BorrowVo> list = borrowMapper.selectTenderBorrow(page);
		page.setResult(list);
		return page;
	}

	@Override
	public String saveApproveBorrowFirst(Integer borrowId, Integer flag, Integer userId, String remark, String creditRating) throws Exception {
		BorrowApprovedVo borrowApprovedVo = borrowApprovedService.selectByBorrowIdForUpdate(borrowId);
		BorrowVo borrowVo = borrowMapper.selectByPrimaryKeyForUpdate(borrowId);

		// System.out.println(borrowApprovedVo.getStatus());
		// System.out.println(borrowVo.getStatus());
		// 如果标的状态不是新标，审核中或标的审核状态不是待审核
		if (borrowApprovedVo.getStatus() != Constants.BORROW_APPSTATUS_WAIT_CODE || borrowVo.getStatus() != Constants.BORROW_STATUS_NEW_CODE) {
			return "标状态非待审核,请确认数据";
		}
		borrowVo.setCreditRating(creditRating);
		// 审核通过
		if (flag == Constants.APPROVE_BORROW_PASS) {
			borrowApprovedVo.setStatus(Constants.BORROW_APPSTATUS_FIRST_PASS_CODE);
			borrowVo.setApprstatus(Constants.BORROW_APPSTATUS_FIRST_PASS_CODE);
			// 审核不通过
		} else {
			borrowApprovedVo.setStatus(Constants.BORROW_APPSTATUS_DRAFT_CODE);
			borrowVo.setStatus(Constants.BORROW_STATUS_NOPASS_CODE);
			borrowVo.setApprstatus(Constants.BORROW_APPSTATUS_DRAFT_CODE);
		}
		borrowApprovedVo.setVerifyUser(userId);
		borrowApprovedVo.setVerifyRemark(remark);
		borrowApprovedVo.setVerifyTime(DateUtils.getTime());
		// 更新借款审核记录
		BorrowApproved borrowApproved = new BorrowApproved();
		BeanUtils.copyProperties(borrowApprovedVo, borrowApproved);
		baseBorrowApprovedMapper.updateEntity(borrowApproved);
		// 更新借款标状态
		Borrow borrow = new Borrow();
		BeanUtils.copyProperties(borrowVo, borrow);
		borrowMapper.updateBorrowStatusById(borrow);

		return BusinessConstants.SUCCESS;
	}

	@Override
	public String saveApproveBorrowAntiFraud(Integer borrowId, Integer flag, Integer userId, String remark) throws Exception {
		BorrowApprovedVo borrowApprovedVo = borrowApprovedService.selectByBorrowIdForUpdate(borrowId);
		BorrowVo borrowVo = borrowMapper.selectByPrimaryKeyForUpdate(borrowId);
		// 如果标的状态不是初审通过
		if (borrowApprovedVo.getStatus() != Constants.BORROW_APPSTATUS_FIRST_PASS_CODE || borrowVo.getApprstatus() != Constants.BORROW_APPSTATUS_FIRST_PASS_CODE) {
			return "标状态非初审通过,请确认数据";
		}
		// 审核通过
		if (flag == Constants.APPROVE_BORROW_PASS) {
			borrowApprovedVo.setStatus(Constants.BORROW_APPSTATUS_ANTIFRAUD_PASS_CODE);
			borrowVo.setApprstatus(Constants.BORROW_APPSTATUS_ANTIFRAUD_PASS_CODE);
			// 审核不通过
		} else {
			borrowApprovedVo.setStatus(Constants.BORROW_APPSTATUS_DRAFT_CODE);
			borrowVo.setStatus(Constants.BORROW_STATUS_NOPASS_CODE);
			borrowVo.setApprstatus(Constants.BORROW_APPSTATUS_DRAFT_CODE);
		}
		borrowApprovedVo.setVerifyUser2(userId);
		borrowApprovedVo.setVerifyRemark2(remark);
		borrowApprovedVo.setVerifyTime2(DateUtils.getTime());
		// 更新借款审核记录
		BorrowApproved borrowApproved = new BorrowApproved();
		BeanUtils.copyProperties(borrowApprovedVo, borrowApproved);
		baseBorrowApprovedMapper.updateEntity(borrowApproved);
		Borrow borrow = new Borrow();
		BeanUtils.copyProperties(borrowVo, borrow);
		borrowMapper.updateBorrowStatusById(borrow);

		return BusinessConstants.SUCCESS;
	}

	@Override
	public String saveApproveBorrowFinal(Integer borrowId, Integer flag, Integer userId, String remark, String addip) throws Exception {
		BorrowApprovedVo borrowApprovedVo = borrowApprovedService.selectByBorrowIdForUpdate(borrowId);
		BorrowVo borrowVo = borrowMapper.selectByPrimaryKeyForUpdate(borrowId);
		// 如果标的状态不是反欺诈通过通过
		if (borrowApprovedVo.getStatus() != Constants.BORROW_APPSTATUS_ANTIFRAUD_PASS_CODE || borrowVo.getApprstatus() != Constants.BORROW_APPSTATUS_ANTIFRAUD_PASS_CODE) {
			return "标状态非反欺诈通过,请确认数据";
		}
		if (flag == Constants.APPROVE_BORROW_PASS) {
			borrowApprovedVo.setStatus(Constants.BORROW_APPSTATUS_FINAL_PASS_CODE);
			borrowVo.setApprstatus(Constants.BORROW_APPSTATUS_FINAL_PASS_CODE);
			borrowVo.setStatus(Constants.BORROW_STATUS_TEND_CODE);
			borrowVo.setPublishTime(String.valueOf(System.currentTimeMillis() / 1000));
			if (borrowVo.getAreaType() != null && borrowVo.getAreaType().intValue() == 0) { // 普通专区
				borrowVo.setIsAutotender(1); // 开始直通车投标和自动投标
			}
		} else {
			borrowApprovedVo.setStatus(Constants.BORROW_APPSTATUS_DRAFT_CODE);
			borrowVo.setStatus(Constants.BORROW_STATUS_NOPASS_CODE);
			borrowVo.setApprstatus(Constants.BORROW_APPSTATUS_DRAFT_CODE);
		}
		borrowApprovedVo.setVerifyUser3(userId);
		borrowApprovedVo.setVerifyRemark3(remark);
		borrowApprovedVo.setVerifyTime3(DateUtils.getTime());
		// 更新借款审核记录
		BorrowApproved borrowApproved = new BorrowApproved();
		BeanUtils.copyProperties(borrowApprovedVo, borrowApproved);
		baseBorrowApprovedMapper.updateEntity(borrowApproved);
		Borrow borrow = new Borrow();
		BeanUtils.copyProperties(borrowVo, borrow);
		borrowMapper.updateBorrowStatusById(borrow);

		if (borrow.getAreaType() != null && borrow.getAreaType() == 0) { // 普通专区可以进行直通车投标和自动投标
			// 开始直通车投标和自动投标
			this.saveFirstOrAutoTender(borrowId);
		}
		// 自动复审
		this.saveApproveBorrowReCheck(borrowId, userId, "系统自动复审", addip);
		return BusinessConstants.SUCCESS;
	}

	@Override
	public String saveApproveBorrowReCheck(Integer borrowid, Integer checkUserId, String checkRemark, String addip) throws Exception {
		BorrowVo borrowVo = borrowMapper.selectByPrimaryKeyForUpdate(borrowid);
		if (!borrowVo.getStatus().equals(Constants.BORROW_STATUS_SUCCESS_CODE) || borrowVo.getAccount().compareTo(borrowVo.getAccountYes()) != 0) {
			return "借款标状态不是满标复审中";
		}
		// 查询已投标总额 (logic for leader)
		BigDecimal tenderTotal = borrowMapper.queryTenderTotalByBorrowId(borrowid);
		if (null == tenderTotal || tenderTotal.compareTo(borrowVo.getAccount()) != 0) {
			return "借款标金额与投标金额不相等";
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("borrowid", borrowid);
		params.put("checkUserId", checkUserId);
		params.put("checkRemark", checkRemark);
		params.put("addip", addip);
		borrowMapper.approveBorrowReCheck(params);
		String msg = params.get("msg").toString();
		if (!"00001".equals(msg)) {
			throw new AppException("借款标复审失败");
		}
		// 如果是秒标
		if (borrowVo.getBorrowtype() == Constants.BORROW_TYPE_SEC) {
			List<BRepaymentRecordVo> repaymentRecordVos = bRepaymentRecordMapper.queryBRepaymentRecordByBorrowId(borrowid);
			BRepaymentRecordVo repaymentRecord = repaymentRecordVos.get(0);
			String result = this.saveRepayBorrow(repaymentRecord.getId(), addip, borrowVo.getUserId());
			if (!"00001".equals(result)) {
				throw new AppException("秒标自动还款失败");
			}
		}

		// 查询该定期宝使用的红包，标记为已使用
		List<RedAccount> reds = redMapper.getBizReds(borrowVo.getId(), 3, 3);
		for (RedAccount r : reds) {
			r = redMapper.getByIdForUpdate(r.getId());
			int n = redMapper.useRed(r.getId());
			if (n == 1) {
				RedAccountLog redLog = new RedAccountLog();
				redLog.setAddip(addip);
				redLog.setRedId(r.getId());
				redLog.setBizType(3);// 3手动投标
				redLog.setBizId(borrowVo.getId());// 借款标的ID
				redLog.setRemark("满标更新红包状态：已使用");
				redLogMapper.addByRed(redLog);
			}
		}

		// 发邮件

		return BusinessConstants.SUCCESS;
	}

	@Override
	public BorrowVo selectByPrimaryKey(Integer id) throws Exception {
		return borrowMapper.selectByPrimaryKey(id);
	}

	/**
	 * <p>
	 * Description:还款<br />
	 * </p>
	 * @author zhanghao
	 * @version 0.1 2014年5月27日
	 * @param borrowid
	 * @throws Exception
	 */
	@Override
	public String saveRepayBorrow(Integer repaymentid, String addip, Integer userId) throws Exception {
		String result = BusinessConstants.SUCCESS;
		// 获得待还记录并锁定
		BRepaymentRecordVo bRepaymentRecordVo = bRepaymentRecordMapper.queryBRepaymentRecordByIdForupdate(repaymentid);
		// 验证还款的数据正确性
		result = validateRepayBorrowData(userId, bRepaymentRecordVo);
		if (!result.equals(BusinessConstants.SUCCESS)) {
			return result;
		}

		// 还款时间和当前时间相差的天数
		Date repaymentTimeDate = new Date(Long.parseLong(bRepaymentRecordVo.getRepaymentTime()) * 1000);
		repaymentTimeDate = DateUtils.parse(DateUtils.format(repaymentTimeDate, DateUtils.YMD_DASH), DateUtils.YMD_DASH);
		Date now = DateUtils.parse(DateUtils.format(new Date(), DateUtils.YMD_DASH), DateUtils.YMD_DASH);
		// 验证是否有足够的余额还款
		result = validateHaveEnoughMoney(userId, bRepaymentRecordVo, repaymentTimeDate, now);
		if (!result.equals(BusinessConstants.SUCCESS)) {
			return result;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("borrowid", bRepaymentRecordVo.getBorrowId());
		map.put("repaymentid", repaymentid);
		map.put("addip", addip);
		map.put("platform", ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getPlatform());
		// 借款标
		Borrow borrow = borrowMapper.selectByPrimaryKey(bRepaymentRecordVo.getBorrowId());
		// 提前天数
		int earlyDays = DateUtils.dayDiff(repaymentTimeDate, now);

		// 还款引发债权转让撤销
		cancelTransfer(bRepaymentRecordVo.getBorrowId());

		// 还款引发直通车转让撤销
		cancelFirstTransfer(bRepaymentRecordVo.getBorrowId(), addip);

		// 根据借款标获取转让中的定期宝
		List<FixBorrowTransferVo> bfList = fixBorrowTransferMapper.queryFixBorrowTransferByBorrowId(bRepaymentRecordVo.getBorrowId());
		boolean flag = false;

		// 只有抵押标到期还本付息和按月付息到期还本两种类型，才启用提前还款，比应还款日提前3天以上（不包含3天）
		// 只有信用标等额本息类型，才启用提前还款，比应还款日提前3天以上（不包含3天）
		if (((borrow.getBorrowtype() == Constants.BORROW_TYPE_PLEDGE && (borrow.getStyle() == Constants.BORROW_STYLE_DUE_PAY_ALL || borrow.getStyle() == Constants.BORROW_STYLE_MONTH_PAY_INTEREST)) || (borrow
				.getBorrowtype() == Constants.BORROW_TYPE_RECOMMEND && borrow.getStyle() == Constants.BORROW_STYLE_MONTH_INSTALMENTS)) && earlyDays > 3) {
			// 定期宝自动取消转让
			cancelOrTransferFixBorrow(bfList, "cancel");
			flag = true;
			// 提前还款
			borrowMapper.repayEarlyBorrow(map);
		} else {
			// 已垫付
			if (bRepaymentRecordVo.getWebstatus() == Constants.REPAYMENTRECORD_WEBSTATUS_HAVE_PAY) {
				// 垫付后还款
				borrowMapper.afterWebpayBorrow(map);
			} else {
				// 定期宝自动取消转让
				cancelOrTransferFixBorrow(bfList, "cancel");
				flag = true;
				// 正常还款
				borrowMapper.repayBorrow(map);
			}
		}
		String msg = map.get("msg").toString();
		if (!"00001".equals(msg)) {
			throw new AppException("还款失败");
		}

		if (flag) {
			// 定期宝自动发起转让
			cancelOrTransferFixBorrow(bfList, "transfer");
		}

		return BusinessConstants.SUCCESS;
	}

	/**
	 * <p>
	 * Description:还款引发债权转让撤销<br />
	 * </p>
	 * @author chenpeng
	 * @version 0.1 2014年12月24日
	 * @param borrowid
	 * @throws Exception
	 */
	private void cancelTransfer(Integer borrowId) throws Exception {
		// 还款，判断是否有债权转让存在，有的，撤销债权转让；
		// 借款表id查询对应的多个债权转让id列表
		List<Integer> cancelTransfers = bTransferMapper.queryCancelTransfers(borrowId);

		if (cancelTransfers.size() > 0) {
			for (Integer tid : cancelTransfers) {
				Map<String, Object> mapTrans = new HashMap<String, Object>();
				mapTrans.put("id", tid);
				mapTrans.put("cancelIP", "0.0.0.0");
				mapTrans.put("cancelUser", 0);
				mapTrans.put("cancelRemark", "还款时撤销");
				mapTrans.put("transferStatus", 6);
				mapTrans.put("msg", "");

				borrowMapper.transferCancelByRepay(mapTrans);
				if ("0001".equals(mapTrans.get("msg".toString()))) {

				} else {
					throw new AppException("还款时撤销出错");
				}
			}
		}

	}

	@Override
	public String saveOperatingPenalty(Integer repaymentid, String addip) throws Exception {
		BRepaymentRecordVo bRepaymentRecordVo = bRepaymentRecordMapper.queryBRepaymentRecordByIdForupdate(repaymentid);
		if (bRepaymentRecordVo.getIsRepairInterest() != null && bRepaymentRecordVo.getIsRepairInterest() == 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("repaymentid", repaymentid);
			map.put("addip", addip);
			borrowMapper.operatingPenalty(map);
			String msg = map.get("msg").toString();
			if (!"00001".equals(msg)) {
				throw new AppException("处理罚息失败");
			} else {
				bRepaymentRecordVo.setIsRepairInterest(1);
				bRepaymentRecordVo.setRepairInterestTime(new Date());
				BRepaymentRecord bRepaymentRecord = new BRepaymentRecord();
				BeanUtils.copyProperties(bRepaymentRecordVo, bRepaymentRecord);
				if (bRepaymentRecordMapper.updateBReaymentRecordById(bRepaymentRecord) <= 0) {
					throw new AppException("处理罚息失败");
				}
			}
		} else {
			BRepaymentRecord bRepaymentRecord = new BRepaymentRecord();
			BeanUtils.copyProperties(bRepaymentRecordVo, bRepaymentRecord);
			bRepaymentRecordMapper.updateBReaymentRecordById(bRepaymentRecord);
		}
		return BusinessConstants.SUCCESS;
	}

	/**
	 * <p>
	 * Description:取得合同编号<br />
	 * </p>
	 * @author zhanghao
	 * @version 0.1 2014年6月5日
	 * @param borrow
	 * @return String
	 * @throws Exception
	 */
	public String getContractNo(Borrow borrow) throws Exception {
		String contractNo = null;

		Borrow oldBorrow = borrowMapper.queryLastBorrowByBorrowType(borrow.getBorrowtype());

		int no = 0;
		String num = "0001";
		if (oldBorrow != null && oldBorrow.getContractNo() != null) {
			String contract_no = oldBorrow.getContractNo();
			if (contract_no.length() > 0) {
				no = Integer.parseInt(contract_no.substring(7)) + 1;
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
		} else if (no >= 1000 && no < 10000) {
			num = String.valueOf(no);
		}
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR)).substring(String.valueOf(cal.get(Calendar.YEAR)).length() - 2);
		int month = cal.get(Calendar.MONTH) + 1;
		String monthStr = String.valueOf(cal.get(Calendar.MONTH) + 1);
		if (month < 10) {
			monthStr = "0" + String.valueOf(cal.get(Calendar.MONTH) + 1);
		} else {
			monthStr = String.valueOf(cal.get(Calendar.MONTH) + 1);
		}
		if (borrow.getBorrowtype() == 1) { // 推荐标
			contractNo = "TJ-" + year + monthStr + num;
		} else if (borrow.getBorrowtype() == 2) { // 抵押标
			contractNo = "DY-" + year + monthStr + num;
		} else if (borrow.getBorrowtype() == 3) { // 净值标
			contractNo = "JZ-" + year + monthStr + num;
		} else if (borrow.getBorrowtype() == 4) { // 秒标
			contractNo = "MB-" + year + monthStr + num;
		} else if (borrow.getBorrowtype() == 5) { // 担保标
			contractNo = "DB-" + year + monthStr + num;
		}
		return contractNo;
	}

	/**
	 * <p>
	 * Description:取得最后一条借款<br />
	 * </p>
	 * @author zhanghao
	 * @version 0.1 2014年6月5日
	 * @return Borrow
	 */
	public Borrow queryLastBorrow() throws Exception {
		Borrow borrow = borrowMapper.queryLastBorrow();
		return borrow;
	}

	/**
	 * <p>
	 * Description:撤标<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年8月6日
	 * @param borrowId
	 * @throws Exception String
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String cancelBorrow(int borrowId, int userId, String ip) throws Exception {
		Borrow borrow = borrowMapper.selectByPrimaryKey(borrowId);
		if (borrow == null) {
			return "该借款标不存在，请刷新后重试！";
		} else if (borrow != null && borrow.getUserId().intValue() != userId) {
			return "您不是该标的发标人，无法撤销该标";
		} else if (borrow != null && borrow.getStatus() != 2) {
			return "该借款标的状态已变更，无法撤销该标";
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("borrowid", borrowId);
			map.put("type", 1);
			map.put("addip", ip);
			map.put("platform", ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getPlatform());
			borrowMapper.cancelBorrow(map);
			if (map.get("msg") != null && map.get("msg").toString().equals("00001")) {
				return "撤标成功！";
			} else {
				throw new AppException("撤标失败！");
			}
		}
	}

	@Override
	public BigDecimal getTotalMoney() throws Exception {
		Map<String, ?> map = borrowMapper.queryTotalMoney();
		if (null != map) {
			return new BigDecimal(map.get("TOTALMONEY").toString());
		}
		return BigDecimal.ZERO;
	}

	@Override
	public List<BorrowVo> getLatestNotFull(int pageNum, int pageSize) throws Exception {
		int startNum = (pageNum - 1) * pageSize;

		Map map = new HashMap();
		map.put("startNum", startNum);
		map.put("pageSize", pageSize);

		List<BorrowVo> list = borrowMapper.getLatestNotFull(map);
		if (list.size() > 0)
			list = BorrowVo.handleBorrow(list);
		return list;
	}

	@Override
	public List<BorrowVo> getLatestFull(int pageNum, int pageSize) throws Exception {
		int startNum = (pageNum - 1) * pageSize;

		Map map = new HashMap();
		map.put("startNum", startNum);
		map.put("pageSize", pageSize);

		List<BorrowVo> list = borrowMapper.getLatestFull(map);
		if (list.size() > 0)
			list = BorrowVo.handleBorrow(list);
		return list;
	}

	@Override
	public List<BorrowVo> getAdvanced(int pageNum, int pageSize) throws Exception {
		int startNum = (pageNum - 1) * pageSize;

		Map map = new HashMap();
		map.put("startNum", startNum);
		map.put("pageSize", pageSize);

		List<BorrowVo> list = borrowMapper.getAdvanced(map);
		if (list.size() > 0)
			list = BorrowVo.handleBorrow(list);
		return list;
	}

	/**
	 * <p>
	 * Description:进行手动投标<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年8月14日
	 * @param tenderBorrowCnd
	 * @param memberId
	 * @param addip
	 * @param tenderType
	 * @return
	 * @throws Exception String
	 */
	@Override
	public String saveManualTender(TenderBorrowCnd tenderBorrowCnd, Integer memberId, String addip, Integer tenderType) throws Exception {
		// 验证是否可以使用红包
		Integer redId = tenderBorrowCnd.getRedId();
		RedAccount red = null;
		if (redId != null && redId > 0) {
			red = redMapper.getById(redId);
			if (red != null) {
				if (red.getStatus() != 2 || !red.getUserId().equals(memberId)) {
					return "该红包不可使用";
				}
				if (tenderBorrowCnd.getTenderMoney().compareTo(new BigDecimal(red.getMoney().floatValue() * 100)) < 0) {
					return "投资金额不满足红包使用条件";
				}
				if(red.getRedType() == 1970 ){
					return "活动期间所获奖励红包只能用于购买定期宝";
				}
			} else {
				return "该红包不存在";
			}
		}

		String result = BusinessConstants.SUCCESS;
		result = this.validateTenderData(tenderBorrowCnd, memberId);
		if (!BusinessConstants.SUCCESS.equals(result)) {
			return result;
		}

		// 使用红包
		int redLogId = 0;
		if (red != null) {
			red = redMapper.getByIdForUpdate(redId);
			int n = redMapper.freezeRed(redId, tenderBorrowCnd.getBorrowid(), 3);// 先冻结红包
			if (n == 1) {
				// 红包日志
				RedAccountLog redLog = new RedAccountLog();
				redLog.setAddip(addip);
				redLog.setRedId(redId);
				redLog.setBizType(3);// 3手动投标
				redLog.setRemark("用户使用红包投标【标ID：" + tenderBorrowCnd.getBorrowid() + "】，红包冻结");
				redLogMapper.addByRed(redLog);
				redLogId = redLog.getId();

				// 更新账户表
				AccountVo accountVo = accountService.queryAccountByUserIdForUpdate(memberId);
				BigDecimal redMoney = red.getMoney();
				accountMapper.updateAccountForRed(redMoney, memberId);
				// 账户日志
				accountVo = accountService.queryAccountByUserId(memberId);
				AccountLog accountLog = new AccountLog();
				accountLog.setUserId(memberId);
				accountLog.setAddip(addip);
				accountLog.setAddtime(new Date().getTime() / 1000 + "");
				accountLog.setMoney(redMoney);
				accountLog.setTotal(accountVo.getTotal());
				accountLog.setDrawMoney(accountVo.getDrawMoney());
				accountLog.setNoDrawMoney(accountVo.getNoDrawMoney());
				accountLog.setUseMoney(accountVo.getUseMoney());
				accountLog.setNoUseMoney(accountVo.getNoUseMoney());
				accountLog.setCollection(accountVo.getCollection());
				accountLog.setFirstBorrowUseMoney(accountVo.getFirstBorrowUseMoney());
				accountLog.setToUser(memberId);
				accountLog.setType("red_envelop_in");// 字典表定义（红包转入）
				accountLog.setIdType(7);// 7：红包转入
				accountLog.setBorrowId(redLogId);// 红包操作日志ID
				accountLog.setRemark("投标使用红包，红包金额转入账户");
				baseAccountLogMapper.insertEntity(accountLog);

			}
		}

		AccountVo accountvo = accountService.queryAccountByUserIdForUpdate(memberId);
		BigDecimal useMoney = accountvo.getUseMoney();
		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (tenderBorrowCnd.getIsUseCurMoney() != null && tenderBorrowCnd.getIsUseCurMoney().equals("1")) {
			// 当投标金额大于普通账户的可用余额时，活期宝转出剩余不足的金额
			if (tenderBorrowCnd.getTenderMoney().compareTo(useMoney) == 1) {
				BigDecimal remainTenderMoney = tenderBorrowCnd.getTenderMoney().subtract(useMoney);
				// 活期宝金额转出用于投标
				result = curOutService.turnCurOutForInvest(memberId, remainTenderMoney, "0.0.0.1", BusinessConstants.CUR_OUT_TYPE_TO_TENDER_BORROW);
				if (!BusinessConstants.SUCCESS.equals(result)) {
					return result;
				}
			}
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("borrowid", tenderBorrowCnd.getBorrowid());
		params.put("tenderMoney", tenderBorrowCnd.getTenderMoney());
		params.put("memberId", memberId);
		params.put("addip", addip);
		params.put("tenderType", tenderType);
		params.put("platform", ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getPlatform());
		borrowMapper.saveManualTender(params);
		String msg = params.get("msg").toString();
		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (tenderBorrowCnd.getIsUseCurMoney() != null && tenderBorrowCnd.getIsUseCurMoney().equals("1") && "0001".equals(msg)) {
			// 当投标金额大于普通账户的可用余额时，活期宝转出剩余不足的金额
			if (tenderBorrowCnd.getTenderMoney().compareTo(useMoney) == 1) {
				BigDecimal remainTenderMoney = tenderBorrowCnd.getTenderMoney().subtract(useMoney);
				// 查询该笔投标记录
				TenderRecordVo tenderRecordVo = tendRecordService.queryTenderRecordLastByUserIdAndAccount(tenderBorrowCnd.getBorrowid(), memberId, tenderBorrowCnd.getTenderMoney());
				// 获取转出记录
				CurOut curOut = curOutService.queryCurOutLastByUserIdAndType(BusinessConstants.CUR_OUT_TYPE_TO_TENDER_BORROW, memberId, remainTenderMoney);
				if (tenderRecordVo == null || curOut == null) {
					throw new AppException("活期宝投标失败");
				} else {
					curOut.setTargetId(tenderRecordVo.getId());
					Integer updateCurOutCount = curOutService.updateByPrimaryKeySelective(curOut);
					if (updateCurOutCount == null || updateCurOutCount == 0) {
						throw new AppException("活期宝投标失败");
					}
				}
			}
		}

		// 更新红包日志bizId
		if (redLogId != 0) {
			// 查询该笔投标记录
			TenderRecordVo tenderRecordVo = tendRecordService.queryTenderRecordLastByUserIdAndAccount(tenderBorrowCnd.getBorrowid(), memberId, tenderBorrowCnd.getTenderMoney());
			redLogMapper.updateBizId(tenderRecordVo.getId(), redLogId);// 投标记录【ROCKY_B_TENDERRECORD】的ID
		}

		if ("0000".equals(msg)) {
			throw new AppException("投标失败");
		} else if ("00002".equals(msg)) {
			throw new AppException("投标金额错误");
		} else if ("00003".equals(msg)) {
			throw new AppException("投标金额超过标剩余金额,请重新操作");
		}
		return result;
	}

	@Override
	public BorrowVo selectByPrimaryKeyForUpdate(Integer id) throws Exception {
		return borrowMapper.selectByPrimaryKeyForUpdate(id);
	}

	@Override
	public Map<Integer, BorrowVo> repaymentList2map(List<BRepaymentRecordVo> list) throws Exception {

		Map<Integer, BorrowVo> map = new HashMap<Integer, BorrowVo>();
		for (BRepaymentRecordVo brr : list) {
			map.put(brr.getBorrowId(), BorrowVo.handleBorrow(queryById(brr.getBorrowId())));
		}
		return map;
	}

	/**
	 * <p>
	 * 根据ID查询借款标<br />
	 * </p>
	 * @author chenlu
	 * @version 0.1 2014年8月16日
	 * @param id
	 * @return Borrow
	 * @throws Exception
	 */
	public BorrowVo queryById(int id) throws Exception {
		return selectByPrimaryKey(id);
	}

	@Override
	public Page queryTendering(BorrowCnd borrowCnd, int pageNum, int pageSize) throws Exception {
		Page page = new Page(pageNum, pageSize);
		if (borrowCnd.getBeginTime() != null && !borrowCnd.getBeginTime().equals("")) {
			borrowCnd.setBeginTime(DateUtils.dateTime2TimeStamp(borrowCnd.getBeginTime() + " 00:00:00"));
		}
		if (borrowCnd.getEndTime() != null && !borrowCnd.getEndTime().equals("")) {
			borrowCnd.setEndTime(DateUtils.dateTime2TimeStamp(borrowCnd.getEndTime() + " 23:59:59"));
		}
		int totalCount = borrowMapper.queryTenderingCount(borrowCnd);
		page.setTotalCount(totalCount);
		List<BorrowVo> list = borrowMapper.queryTendering(borrowCnd, page);
		page.setResult(list);
		return page;
	}

	@Override
	public Page queryAll(BorrowCnd borrowCnd, int pageNum, int pageSize) throws Exception {
		Page page = new Page(pageNum, pageSize);
		if (borrowCnd.getBeginTime() != null && !borrowCnd.getBeginTime().equals("")) {
			borrowCnd.setBeginTime(DateUtils.dateTime2TimeStamp(borrowCnd.getBeginTime() + " 00:00:00"));
		}
		if (borrowCnd.getEndTime() != null && !borrowCnd.getEndTime().equals("")) {
			borrowCnd.setEndTime(DateUtils.dateTime2TimeStamp(borrowCnd.getEndTime() + " 23:59:59"));
		}
		int totalCount = borrowMapper.queryAllCount(borrowCnd);
		page.setTotalCount(totalCount);
		List<BorrowVo> list = borrowMapper.queryAll(borrowCnd, page);
		page.setResult(list);
		return page;
	}

	@Override
	public Page getBorrowList(Map<String, Object> map, Page p) throws Exception {
		Page page = new Page();

		List<BorrowVo> list = borrowMapper.getBorrowList(map, p);

		BorrowVo.handleBorrow(list);

		page.setResult(list);
		return page;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String advanceFullBorrow(int borrowId, int userId, String ip) throws Exception {
		Borrow borrow = borrowMapper.selectByPrimaryKeyForUpdate(borrowId);
		if (borrow == null) {
			return "该借款标不存在，请刷新后重试！";
		} else if (borrow != null && borrow.getUserId().intValue() != userId) {
			return "您不是该标的发标人，无法操作该标！";
		} else if (borrow != null && borrow.getBorrowtype() != 3) {
			return "非净值标无法操作提前满标！";
		} else if (borrow != null && borrow.getStatus() != 2) {
			return "该借款标的状态已变更，请刷新后重试！";
		} else if (borrow != null && borrow.getAccountYes().divide(borrow.getAccount(), 4, BigDecimal.ROUND_DOWN).compareTo(new BigDecimal("0.8")) < 0) {
			return "已投金额没有达到借款金额的80%，无法操作提前满标！";
		} else {
			// 记录日志
			BorrowAdvanceLog borrowAdvanceLog = new BorrowAdvanceLog();
			borrowAdvanceLog.setBorrowId(borrowId);
			borrowAdvanceLog.setUserId(userId);
			borrowAdvanceLog.setAccount(borrow.getAccount());
			borrowAdvanceLog.setRealAccount(borrow.getAccountYes());
			borrowAdvanceLog.setAddtime(new Date());
			borrowAdvanceLog.setAddip(ip);
			borrowAdvanceLog.setOperatorId(userId);
			borrowAdvanceLog.setPlatform(((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getPlatform());
			borrowAdvanceLog.setType(Constants.BORROW_ADVANCE_LOG_TYPE_SUCCESS);

			borrow.setAccount(borrow.getAccountYes());
			borrow.setStatus(3); // 满标
			borrow.setSuccessTime(DateUtils.getTime()); // 满标时间
			if (borrow.getStyle() == 4) {
				String endTime = DateUtils.dateTime2TimeStamp(DateUtils.format(DateUtils.dayOffset(new Date(), borrow.getTimeLimit()), DateUtils.YMD_HMS));
				borrow.setEndTime(endTime);
			} else {
				String endTime = DateUtils.dateTime2TimeStamp(DateUtils.format(DateUtils.monthOffset(new Date(), borrow.getTimeLimit()), DateUtils.YMD_HMS));
				borrow.setEndTime(endTime);
			}
			borrowMapper.updateByPrimaryKey(borrow);
			// 自动复审
			this.saveApproveBorrowReCheck(borrowId, userId, "系统自动复审", ip);
			// 插入提前满标日志
			baseBorrowAdvanceLogMapper.insertEntity(borrowAdvanceLog);
			return "提前满标成功！";
		}
	}

	/**
	 * <p>
	 * Description:验证还款数据的正确性<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年8月27日
	 * @param borrowid
	 * @param userId
	 * @param bRepaymentRecordVo
	 * @return
	 * @throws Exception String
	 */
	private String validateRepayBorrowData(Integer userId, BRepaymentRecordVo bRepaymentRecordVo) throws Exception {
		String result = BusinessConstants.SUCCESS;
		if (null == bRepaymentRecordVo) {
			return "待还数据不存在，请确认！";
		}
		if (!bRepaymentRecordVo.getUserId().equals(userId)) {
			return "非法还款数据,请确认！";
		}
		if (bRepaymentRecordVo.getStatus() == Constants.REPAYMENTRECORD_STATUS_HAVE_PAY) {
			return "该笔借款已还清！请勿重复操作！";
		}
		if (bRepaymentRecordVo.getStatus() != Constants.REPAYMENTRECORD_STATUS_NO_PAY) {
			return "待还数据非待还中,请确认！";
		}

		// 判断在这之前是否还有未还资金
		Integer lessOrderUnpayCount = bRepaymentRecordMapper.queryBeforeOrderUnPayCount(bRepaymentRecordVo.getBorrowId(), bRepaymentRecordVo.getOrder());
		if (null != lessOrderUnpayCount && lessOrderUnpayCount > 0) {
			return "该笔还款之前尚有未结清的还款,请核实！";
		}

		// 还款时判断待收总额(待收表）是否与待还总额（待还表)差异的绝对值
		CollectionStatisticCnd collectionStatisticCnd = new CollectionStatisticCnd();
		collectionStatisticCnd.setRepaymentId(bRepaymentRecordVo.getId());
		collectionStatisticCnd.setBorrowId(bRepaymentRecordVo.getBorrowId());
		// 已垫付
		if (bRepaymentRecordVo.getWebstatus() == Constants.REPAYMENTRECORD_WEBSTATUS_HAVE_PAY) {
			collectionStatisticCnd.setStatus(Constants.COLLECTION_RECORD_STATUS_WEBPAY);
		} else {
			collectionStatisticCnd.setStatus(Constants.COLLECTION_RECORD_STATUS_UNPAY);
		}
		collectionStatisticCnd.setLockedRecordYn(BusinessConstants.LOCKED_RECORD_YES);
		CollectionRepayInfoVo collectionRepayInfoVo = collectionRecordService.queryRepayTotalByCnd(collectionStatisticCnd);
		// 投标人数小于等于一千，差值为5块钱，投标人数小于等于两千但大于一千，差值可以为10块,大于两千小于等于三千，差值为15，大于三千20 判断
		BigDecimal compareMoney = new BigDecimal("5");
		if (collectionRepayInfoVo.getCollectionPersons() <= 1000) {
			compareMoney = new BigDecimal("5");
		} else if (collectionRepayInfoVo.getCollectionPersons() > 1000 && collectionRepayInfoVo.getCollectionPersons() <= 2000) {
			compareMoney = new BigDecimal("10");
		} else if (collectionRepayInfoVo.getCollectionPersons() > 2000 && collectionRepayInfoVo.getCollectionPersons() <= 3000) {
			compareMoney = new BigDecimal("15");
		} else {
			compareMoney = new BigDecimal("20");
		}
		BigDecimal diff = bRepaymentRecordVo.getRepaymentAccount().subtract(collectionRepayInfoVo.getRepayAccountTotal());
		if (diff.abs().compareTo(compareMoney) == 1) {
			return "应还款金额与待收金额不匹配,请联系客服";
		}

		return result;
	}

	/**
	 * <p>
	 * Description:验证是否有足够的余额还款<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年8月27日
	 * @param userId
	 * @param bRepaymentRecordVo
	 * @param repaymentTimeDate
	 * @param now
	 * @return
	 * @throws Exception String
	 */
	private String validateHaveEnoughMoney(Integer userId, BRepaymentRecordVo bRepaymentRecordVo, Date repaymentTimeDate, Date now) throws Exception {
		String result = BusinessConstants.SUCCESS;
		// 逾期天数
		int lateDays = DateUtils.dayDiff(now, repaymentTimeDate);
		// 罚息金额
		BigDecimal lateDayInterest = BigDecimal.ZERO;
		if (lateDays > 0) {
			lateDayInterest = bRepaymentRecordVo.getRepaymentAccount().multiply(new BigDecimal(BusinessConstants.OUT_OF_DAYE_RATE)).multiply(new BigDecimal(lateDays)).setScale(2, RoundingMode.UP);
		}
		// 应还总金额 = 还款金额 + 罚息金额
		BigDecimal totalPayMoney = bRepaymentRecordVo.getRepaymentAccount().setScale(2, RoundingMode.UP).add(lateDayInterest);
		// 借款者帐号
		AccountVo accoutVo = accountService.queryAccountByUserIdForUpdate(userId);
		if (totalPayMoney.compareTo(accoutVo.getUseMoney()) == 1) {
			StringBuffer noenoughMoneymsg = new StringBuffer();
			noenoughMoneymsg.append("您的账户余额不足！应还金额为：");
			noenoughMoneymsg.append(bRepaymentRecordVo.getRepaymentAccount().setScale(2, RoundingMode.UP));
			if (lateDays > 0) {
				noenoughMoneymsg.append(",缴纳罚息为：");
				noenoughMoneymsg.append(lateDayInterest);
			}
			return noenoughMoneymsg.toString();
		}
		return result;
	}

	/**
	 * <p>
	 * Description:开始直通车投标和自动投标<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年8月31日
	 * @param borrowId
	 * @return String
	 */
	private String saveFirstOrAutoTender(Integer borrowId) throws Exception {
		BorrowVo borrowVo = borrowMapper.selectByPrimaryKeyForUpdate(borrowId);
		if (borrowVo.getStatus() == 2) {
			// 开始自动投标
			if (borrowVo.getAccount().compareTo(new BigDecimal(200000)) >= 0
					&& (borrowVo.getBorrowtype() == Constants.BORROW_TYPE_RECOMMEND || borrowVo.getBorrowtype() == Constants.BORROW_TYPE_PLEDGE || borrowVo.getBorrowtype() == Constants.BORROW_TYPE_GUARANTEED)) { // 借款金额大于20万，且为抵押标才开启直通车投标
				// 开始直通车投标
				firstBorrowService.saveFirstBorrow(borrowVo.getId());
				borrowVo = borrowMapper.selectByPrimaryKeyForUpdate(borrowId);
				// 开始自动投标
				if (borrowVo.getStatus() == Constants.BORROW_STATUS_TEND_CODE && (borrowVo.getBidPassword() == null || borrowVo.getBidPassword().equals(""))) {
					autoInvestService.saveAutoTenderBorrow(borrowVo.getId());
				}
			} else {
				// 开始自动投标
				if (borrowVo.getStatus() == Constants.BORROW_STATUS_TEND_CODE && (borrowVo.getBidPassword() == null || borrowVo.getBidPassword().equals(""))) {
					autoInvestService.saveAutoTenderBorrow(borrowVo.getId());
				}
			}
			Borrow borrow = new Borrow();
			borrow.setIsAutotender(0); // 开始手动投标
			borrow.setId(borrowVo.getId());
			borrowMapper.updateBorrowStatusById(borrow);
		}
		return BusinessConstants.SUCCESS;
	}

	@Override
	public Page queryTenderingForOtherBorrow(Map<String, Object> map, Page page) throws Exception {

		List<BorrowVo> list = borrowMapper.queryTenderingForOtherBorrow(map, page);
		int totalCount = borrowMapper.queryTenderingForOtherBorrowCount(map);

		BorrowVo.handleBorrow(list);

		page.setTotalCount(totalCount);
		page.setResult(list);
		return page;

	}
	
	@Override
	public Page queryTenderingForOtherBorrowNew(Map<String, Object> map, Page page) throws Exception {
		
		List<BorrowVo> list = borrowMapper.queryTenderingForOtherBorrowNew(map, page);
		int totalCount = borrowMapper.queryTenderingForOtherBorrowCountNew(map);

		BorrowVo.handleBorrow(list);

		page.setTotalCount(totalCount);
		page.setResult(list);		
		return page;
	}


	/**
	 * <p>
	 * Description:验证投标数据是否正确<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年9月17日
	 * @param tenderBorrowCnd
	 * @param memberId
	 * @return
	 * @throws Exception String
	 */
	private String validateTenderData(TenderBorrowCnd tenderBorrowCnd, Integer memberId) throws Exception {

		// 查询借款标并锁定
		BorrowVo borrowVo = this.selectByPrimaryKeyForUpdate(tenderBorrowCnd.getBorrowid());
		if (null == borrowVo) {
			return "未找到此借款标！";
		}
		if (borrowVo.getUserId().equals(memberId)) {
			return "不允许投自己的标！";
		}
		if (borrowVo.getStatus() != Constants.BORROW_STATUS_TEND_CODE) {
			return "借款标剩余金额已变，请重试！";
		}

		if (borrowVo.getIsAutotender() == 1) {
			return "正在自动投标中，请稍后!";
		}

		if (borrowVo.getBidPassword() != null && !"".equals(borrowVo.getBidPassword())) {
			if (!MD5.toMD5(tenderBorrowCnd.getBidPassword()).equals(borrowVo.getBidPassword())) {
				return "定向标密码错误！";
			}
		}
		AccountVo accountvo = accountService.queryAccountByUserIdForUpdate(memberId);
		BigDecimal useMoney = accountvo.getUseMoney();
		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (tenderBorrowCnd.getIsUseCurMoney() != null && tenderBorrowCnd.getIsUseCurMoney().equals("1")) { // 可使用活期宝
			try {
				BigDecimal maxCurUseMoney = curAccountService.getMaxUseMoneyByNow(memberId);
				useMoney = useMoney.add(maxCurUseMoney);
				borrowVo.setIsUseCurMoney(tenderBorrowCnd.getIsUseCurMoney());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (useMoney.compareTo(tenderBorrowCnd.getTenderMoney()) == -1) {
			return "账户余额不足，无法投标！";
		}
		BigDecimal money = tendRecordService.isEffectiveMoney(borrowVo, memberId, tenderBorrowCnd.getTenderMoney(), accountvo);
		if (money.compareTo(BigDecimal.ZERO) == 0) {
			return "投标资金错误！";
		}

		return BusinessConstants.SUCCESS;
	}

	@Override
	public String queryTenderDataBefore(TenderBorrowCnd tenderBorrowCnd, Integer memberId) throws Exception {
		// 查询借款标并锁定
		BorrowVo borrowVo = this.selectByPrimaryKey(tenderBorrowCnd.getBorrowid());
		if (null == borrowVo) {
			return "未找到此借款标！";
		}
		if (borrowVo.getStatus() != Constants.BORROW_STATUS_TEND_CODE) {
			return "该借款标不允许继续投标！";
		}
		if (borrowVo.getIsAutotender() == 1) {
			return "正在自动投标中，请稍后!";
		}
		return BusinessConstants.SUCCESS;
	}

	@Override
	public List<BorrowVo> getLatestNotFullConstainTransfer(int pageNum, int pageSize) {
		int startNum = (pageNum - 1) * pageSize;
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("startNum", startNum);
		map.put("pageSize", pageSize);
		List<BorrowVo> list = borrowMapper.getLatestNotFullConstainTransfer(map);
		if (list.size() > 0)
			list = BorrowVo.handleBorrow(list);
		return list;

	}

	@Override
	public List<BorrowVo> getLatestFullConstainTransfer(int pageNum, int pageSize) {
		int startNum = (pageNum - 1) * pageSize;
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("startNum", startNum);
		map.put("pageSize", pageSize);
		List<BorrowVo> list = borrowMapper.getLatestFullConstainTransfer(map);
		if (list.size() > 0)
			list = BorrowVo.handleBorrow(list);
		return list;
	}

	/**
	 * <p>
	 * Description:直通车转让撤销<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年4月1日
	 * @param borrowId
	 * @param addIp
	 * @throws Exception void
	 */
	private void cancelFirstTransfer(Integer borrowId, String addIp) throws Exception {
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		Map<String, Object> params = new HashMap<String, Object>();
		// 直通车转让Id
		params.put("borrowId", borrowId);
		// 用户ID
		params.put("userId", shiroUser.getUserId());
		// 用户名
		params.put("userName", shiroUser.getUserName());
		// IP地址
		params.put("addip", addIp);
		// 备注
		params.put("remark", "还款时直通车转让撤销");
		// 平台来源
		params.put("platform", shiroUser.getPlatform());
		// 撤销原因;1:还款时撤销
		params.put("type", 1);
		// 状态
		params.put("status", 6);
		borrowMapper.cancelFirstTransfer(params);
		// 存储过程返回参数
		String msg = params.get("msg").toString();
		if (!"00000".equals(msg)) {
			throw new AppException("还款时直通车撤销出错");
		}

	}

	/**
	 * 定期宝取消转让或发起转让
	 * @author WangQianJin
	 * @throws Exception
	 * @title @param bfList
	 * @title @param type
	 * @date 2015年9月15日
	 */
	private void cancelOrTransferFixBorrow(List<FixBorrowTransferVo> bfList, String type) throws Exception {
		if (bfList != null && bfList.size() > 0) {
			for (FixBorrowTransferVo fixBorrowTransferVo : bfList) {
				if ("cancel".equals(type)) {
					// 定期宝自动取消转让
					fixBorrowTransferService.saveTransferCancel(fixBorrowTransferVo.getId(), "0.0.0.1");
				} else if ("transfer".equals(type)) {
					// 定期宝自动发起转让
					fixBorrowService.saveTransfer(fixBorrowTransferVo.getFixBorrowId(), "0.0.0.1");
				}
			}
		}
	}

	
	public Integer updateBorrow(BorrowVo borrowVo) {
		return borrowMapper.updateBorrow(borrowVo);
	}

	@Override
	public BorrowVo queryTenderingForId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return borrowMapper.queryTenderingForId(map);
	}

	@Override
	public BorrowVo queryTenderingForMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return borrowMapper.queryTenderingForMap(map);
	}
	
	
	
}
