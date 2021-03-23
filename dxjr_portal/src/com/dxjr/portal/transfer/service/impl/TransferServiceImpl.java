package com.dxjr.portal.transfer.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dxjr.common.page.Page;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.curAccount.entity.CurOut;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.curAccount.service.CurOutService;
import com.dxjr.portal.first.mapper.FirstTransferBorrowMapper;
import com.dxjr.portal.first.vo.FirstTransferBorrowCnd;
import com.dxjr.portal.invest.service.CollectionRecordService;
import com.dxjr.portal.invest.vo.CommonCollectionVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.transfer.constant.TransferConstant;
import com.dxjr.portal.transfer.entity.BTransfer;
import com.dxjr.portal.transfer.entity.TransferApproved;
import com.dxjr.portal.transfer.mapper.BSubscribeMapper;
import com.dxjr.portal.transfer.mapper.BTransferMapper;
import com.dxjr.portal.transfer.mapper.BtransferApprovedMapper;
import com.dxjr.portal.transfer.service.TransferService;
import com.dxjr.portal.transfer.vo.BTransferVo;
import com.dxjr.portal.transfer.vo.BsubscribeVo;
import com.dxjr.portal.transfer.vo.SearchTransferVo;
import com.dxjr.portal.transfer.vo.SubscribeTransferVo;
import com.dxjr.portal.transfer.vo.TransferCnd;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:债权转让业务类<br />
 * </p>
 * 
 * @title TransferServiceImpl.java
 * @package com.dxjr.portal.transfer.service.impl
 * @author chenpeng、zoulixiang
 * @version 0.1 2014年10月21日
 */
@Service
public class TransferServiceImpl implements TransferService {

	Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);

	@Autowired
	BTransferMapper bTransferMapper;

	@Autowired
	BtransferApprovedMapper btransferApprovedMapper;

	@Autowired
	CollectionRecordService collectionRecordService;

	@Autowired
	BSubscribeMapper bSubscribeMapper; // 最好使用service

	@Autowired
	FirstTransferBorrowMapper firstTransferBorrowMapper;
	@Autowired
	private AccountService accountService;
	@Autowired
	private CurAccountService curAccountService;
	@Autowired
	private CurOutService curOutService;

	@Override
	public void queryTransferClaim(TransferCnd transferCnd, Page page) {
		page.setTotalCount(bTransferMapper.queryCountTransferClaim(transferCnd));
		transferCnd.setYearDay(TransferConstant.YEARDAYS);
		page.setResult(bTransferMapper.queryTransferClaim(transferCnd, page));
	}

	/*
	 * 根据投标记录id 和 用户id 查询我转让的债权
	 * 
	 * @see com.dxjr.portal.transfer.service.TransferService#
	 * queryTransferByTenderIdAndUserId(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public BTransferVo queryTransferByTenderIdAndUserId(Integer tenderId, Integer userId) {

		BTransferVo transferVo = bTransferMapper.queryTransferByTenderIdAndUserId(tenderId, userId, TransferConstant.YEARDAYS);

		if (transferVo == null || transferVo.getAccount() == null) {
			logger.error("[tenderId=" + tenderId + ",userId=" + userId + "]，Account查出为空");
			throw new RuntimeException("没有查询到转让记录");
		}

		return transferVo;
	}

	@Override
	public Page findtransferList(SearchTransferVo seach, Page p) throws Exception {

		Integer totalCount = bTransferMapper.querytransferRecordCount(seach);
		p.setTotalCount(totalCount);
		// set orderType
		String orderType = seach.getOrderType();
		if (null != orderType && (orderType.equals("asc") || orderType.equals("desc"))) {
			seach.setOrderType(orderType);
		} else {
			seach.setOrderType("asc");
		}

		List<BTransferVo> list = bTransferMapper.querytransferRecordList(seach, p);
		p.setResult(list);
		return p;
	}

	@Override
	public BTransferVo selectByPrimaryKey(Integer id) throws Exception {
		return bTransferMapper.selectById(id);
	}

	@Override
	public String saveManualSubscribe(SubscribeTransferVo stf, Integer userId, String realIpAddr, Integer tenderTypeManual) throws Exception {
		String result = BusinessConstants.SUCCESS;
		// 查询债转记录并锁定
		BTransferVo bTransferVo = bTransferMapper.selectByIdForUpdate(stf.getTransferid());
		// 查询账户并
		AccountVo accountvo = accountService.queryAccountByUserIdForUpdate(userId);
		BigDecimal useMoney = accountvo.getUseMoney();
		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (stf.getIsUseCurMoney() != null && stf.getIsUseCurMoney().equals("1")) {
			// 当认购金额大于普通账户的可用余额时，活期宝转出剩余不足的金额
			if (stf.getTenderMoney().compareTo(useMoney) == 1) {
				BigDecimal remainTenderMoney = stf.getTenderMoney().subtract(useMoney);
				BigDecimal maxUseMoney = curAccountService.getMaxUseMoneyByNow(userId);
				if (remainTenderMoney.compareTo(maxUseMoney) == 1 || maxUseMoney.compareTo(BigDecimal.ZERO) <= 0) {
					return "活期宝金额不足，无法使用活期宝金额进行认购！";
				}
				// 活期宝金额转出用于投标
				result = curOutService.turnCurOutForInvest(userId, remainTenderMoney, "0.0.0.1", BusinessConstants.CUR_OUT_TYPE_TO_BUY_TRANSFER);
				if (!BusinessConstants.SUCCESS.equals(result)) {
					return result;
				}
			}
		}
		// 验证...
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("transferid", bTransferVo.getId());
		params.put("tenderMoney", stf.getTenderMoney());
		params.put("memberId", userId);
		params.put("addip", realIpAddr);
		params.put("tenderType", tenderTypeManual);
		params.put("platform", stf.getPlatform());
		bTransferMapper.saveManualSubscribe(params);

		String msg = params.get("msg").toString();

		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (stf.getIsUseCurMoney() != null && stf.getIsUseCurMoney().equals("1") && "0001".equals(msg)) {
			// 当认购金额大于普通账户的可用余额时，活期宝转出剩余不足的金额
			if (stf.getTenderMoney().compareTo(useMoney) == 1) {
				BigDecimal remainTenderMoney = stf.getTenderMoney().subtract(useMoney);
				// 查询该笔债权转让认购记录
				BsubscribeVo bsubscribeVo = bSubscribeMapper.querySubscribesLastByUserIdAndTransferId(stf.getTransferid(), userId,
						stf.getTenderMoney());
				// 获取转出记录
				CurOut curOut = curOutService.queryCurOutLastByUserIdAndType(BusinessConstants.CUR_OUT_TYPE_TO_BUY_TRANSFER, userId,
						remainTenderMoney);
				if (bsubscribeVo == null || curOut == null) {
					throw new AppException("活期宝认购债权转让失败");
				} else {
					curOut.setTargetId(bsubscribeVo.getId());
					Integer updateCurOutCount = curOutService.updateByPrimaryKeySelective(curOut);
					if (updateCurOutCount == null || updateCurOutCount == 0) {
						throw new AppException("活期宝认购债权转让失败");
					}
				}
			}
		}
		if ("0000".equals(msg)) {
			throw new Exception("债权转让认购存储过程，执行失败");
		} else if ("00002".equals(msg)) {
			throw new Exception("债权转让认购存储过程，验证认购金额错误");
		} else if ("00003".equals(msg)) {
			throw new Exception("债权转让认购存储过程，验证认购金额超过标剩余金额,请重新操作");
		} else if ("00004".equals(msg)) {
			throw new Exception("债权转让认购存储过程，验证账户余额小于投标金额,请重新操作");
		} else if ("00005".equals(msg)) {
			throw new Exception("认购超过最大认购额度,请重新操作");
		}
		return result;
	}



	public String saveManualSubscribeForCustody(SubscribeTransferVo stf, Integer userId, String realIpAddr, Integer tenderTypeManual) throws Exception {
		String result = BusinessConstants.SUCCESS;
		// 查询债转记录并锁定
		BTransferVo bTransferVo = bTransferMapper.selectByIdForUpdate(stf.getTransferid());
		// 查询账户并
		AccountVo accountvo = accountService.queryAccountByUserIdForUpdate(userId);
		BigDecimal useMoney = accountvo.getUseMoney();
		// 验证...
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("transferid", bTransferVo.getId());
		params.put("tenderMoney", stf.getTenderMoney());
		params.put("memberId", userId);
		params.put("addip", realIpAddr);
		params.put("tenderType", tenderTypeManual);
		params.put("platform", stf.getPlatform());
		bTransferMapper.saveManualSubscribe(params);

		String msg = params.get("msg").toString();

		if ("0000".equals(msg)) {
			throw new Exception("债权转让认购存储过程，执行失败");
		} else if ("00002".equals(msg)) {
			throw new Exception("债权转让认购存储过程，验证认购金额错误");
		} else if ("00003".equals(msg)) {
			throw new Exception("债权转让认购存储过程，验证认购金额超过标剩余金额,请重新操作");
		} else if ("00004".equals(msg)) {
			throw new Exception("债权转让认购存储过程，验证账户余额小于投标金额,请重新操作");
		} else if ("00005".equals(msg)) {
			throw new Exception("认购超过最大认购额度,请重新操作");
		}
		return result;
	}



	/**
	 * <p>
	 * Description:认购满额复审<br />
	 * </p>
	 * 
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年11月7日
	 * @param transferId
	 * @param checkUserId
	 * @param checkRemark
	 * @param addIp
	 * @return String
	 * @throws Exception
	 */
	@Override
	public String saveApproveTransferRecheck(Integer transferId, Integer checkUserId, String checkRemark, String addIp, Integer platForm)
			throws Exception {

		BTransferVo transferVo = bTransferMapper.selectByIdForUpdate(transferId);
		if (!transferVo.getStatus().equals(Constants.TRANSFER_FULL_RECHECK) || !transferVo.getAccountReal().equals(transferVo.getAccountYes())) {
			return "债权转让状态不是满标复审中";
		}

		// 查询已投标总额 (logic for leader)
		BigDecimal total = bTransferMapper.querySubscribeTotalByTransferId(transferId);
		if (null == total || !total.equals(transferVo.getAccountReal())) {
			return "债权转让金额与认购金额不相等";
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("transferId", transferId);
		params.put("addIp", addIp);
		params.put("checkUserId", checkUserId);
		params.put("checkRemark", checkRemark);
		params.put("platForm", platForm);
		bTransferMapper.saveApproveTransferRecheck(params);
		String msg = params.get("msg").toString();
		if (!"00001".equals(msg)) {
			throw new AppException("债权转让复审失败");
		}

		return BusinessConstants.SUCCESS;
	}

	/*
	 * 添加转让记录和审核记录 rocky_b_transfer_approved
	 * 
	 * @see
	 * com.dxjr.portal.transfer.service.TransferService#addTransfer(com.dxjr
	 * .portal.transfer.entity.BTransfer)
	 */
	@Override
	public void addTransfer(BTransferVo bTransferVo) {
		if (bTransferMapper.judgeParentTenderIsFix(bTransferVo.getUserId(), bTransferVo.getTenderId()) > 0) {
			throw new RuntimeException("该债权为认购定期宝提前退出转让所得债权，存在问题，暂不支持发起转让");
		}
		// 处于投标中的债转最多只能有3个。
		Integer userId = bTransferVo.getUserId();
		TransferCnd transferCnd = new TransferCnd();
		transferCnd.setType(2);
		transferCnd.setUserId(userId);
		transferCnd.setStatus(2);// 认购中
		int result = bTransferMapper.queryCountMyTransferingForLock(transferCnd);

		if (result >= 3) {
			throw new RuntimeException("最多只能有3个正在转让中的债权");
		}

		// 锁住代收表中 第一条待还记录 的期数是不是原先期数 ，数据库加锁防止并发 转让操作，事务不结束，锁不解开
		CommonCollectionVo conCollectionVo = lockCollectionRecordByTenderId(bTransferVo.getTenderId(), bTransferVo.getTransferBeginOrder().intValue());
		// 判断期数 和 转让期数是不是同一期
		if (conCollectionVo == null) {
			throw new RuntimeException("该笔代收已经不存在,不能发起转让");
		}

		if (existsTransferRecordBytenderId(bTransferVo.getTenderId())) {
			throw new RuntimeException("该投标已经被转让");
		}

		try {
			// 插入转让记录
			bTransferMapper.insert(bTransferVo);
			// 审核表中插入一条待审核的记录
			TransferApproved transferApproved = new TransferApproved();
			transferApproved.setTransferId(bTransferVo.getId());
			transferApproved.setStatus(Constants.TRANSFER_STATU_WAIT);
			btransferApprovedMapper.insert(transferApproved);

			// 程序自动审核--相当于点击 后台 “初审通过” 功能 到时候 , 如果后台手动审核，去掉自动审核 注释掉即可
			updateTransferApprovedForAutoCheck(bTransferVo.getId(), transferApproved.getId());

		} catch (Exception e) {
			logger.error("转让出错userid=" + bTransferVo.getUserId() + ",tenderid=" + bTransferVo.getTenderId(), e);
			throw new RuntimeException("转让出错");
		}

	}

	private CommonCollectionVo lockCollectionRecordByTenderId(Integer tenderId, Integer transferBeginOrder) {
		return bTransferMapper.lockCollectionRecordByTenderId(tenderId, transferBeginOrder);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private void updateTransferApprovedForAutoCheck(Integer id, Integer transferApprovedId) {
		btransferApprovedMapper.updateStute(transferApprovedId, Constants.TRANSFER_FIRST_STATU_PASS); // 更新原先状态为初审通过
		bTransferMapper.updateStute(id, Constants.TRANSFER_STATU_ING); // 改变该转让状态为“投标中”
	}

	@Override
	public Boolean existsTransferRecordBytenderId(Integer tenderId) {
		int result = bTransferMapper.getTransferCountBytenderId(tenderId);
		if (result > 0) {
			return true;
		}
		return false;

	}

	@Override
	public void queryMyTransfer(TransferCnd transferCnd, Page page) {
		page.setTotalCount(bTransferMapper.queryCountMyTransfer(transferCnd));
		page.setResult(bTransferMapper.queryMyTransfer(transferCnd, page));
	}

	@Override
	public boolean existsTransferRecord(BTransfer bTransfer) {
		int result = bTransferMapper.getTransferCountByVo(bTransfer);
		if (result > 0) {
			return true;
		}
		return false;
	}

	private void lockTransferRecordByTransferId(Integer bTransferId) {
		bTransferMapper.lockTransferRecordByTransferId(bTransferId);
	}

	@Override
	public void updateMyTransferForCancel(BTransfer bTransfer) {
		lockTransferRecordByTransferId(bTransfer.getId());

		// 查询是否存在转让记录
		bTransfer.setStatus(Constants.TRANSFER_STATU_ING);
		if (!existsTransferRecord(bTransfer)) {
			throw new RuntimeException("没有查询到转让记录");
		}

		bTransfer.setStatus(Constants.TRANSFER_CANCEL);
		bTransferMapper.updateStuteForCancel(bTransfer);
	}

	@Override
	public BTransfer getTransferDetailById(Integer id) {
		return bTransferMapper.getTransferDetailById(id);
	}

	@Override
	public void querySubscribesByTransferId(Integer transferId, Page page) {
		page.setTotalCount(bSubscribeMapper.queryCountSubscribesByTransferId(transferId));
		page.setResult(bSubscribeMapper.querySubscribesByTransferId(transferId, page));
	}

	@Override
	public void queryBorrowSubscribesByBorrowId(Integer borrowId, Page page) {
		page.setTotalCount(bSubscribeMapper.queryCountBorrowSubscribesByBorrowId(borrowId));
		page.setResult(bSubscribeMapper.queryBorrowSubscribesByBorrowId(borrowId, page));
	}

	@Override
	public void queryTransferinfosByBorrowId(Integer borrowId, Integer status, Page page) {
		page.setTotalCount(bSubscribeMapper.queryCountTransferinfosByBorrowId(borrowId, status));
		List<BsubscribeVo> list = bSubscribeMapper.queryTransferinfosByBorrowId(borrowId, status, page);
		if (list != null && list.size() > 0) {
			for (BsubscribeVo bSubscribe : list) {
				if (bSubscribe.getAccount() == null) {
					FirstTransferBorrowCnd firstTransferBorrowCnd = new FirstTransferBorrowCnd();
					firstTransferBorrowCnd.setBorrowId(borrowId);
					firstTransferBorrowCnd.setFirstTenderRealId(bSubscribe.getFirstTenderRealId());
					firstTransferBorrowCnd.setTenderId(bSubscribe.getParentId());
//					FirstTransferBorrowVo firstTransferBorrowVo = firstTransferBorrowMapper.queryBorrowListByRealId(firstTransferBorrowCnd);					
//					if (firstTransferBorrowVo != null) {
//						bSubscribe.setAccount(firstTransferBorrowVo.getAccount());
//					}
					BigDecimal transferAccount=firstTransferBorrowMapper.queryTransferAccountByRealId(firstTransferBorrowCnd);
					bSubscribe.setAccount(transferAccount);
				}
			}
		}
		page.setResult(list);
	}

	@Override
	public String querySumAccountByUserId(TransferCnd transferCnd) {
		return bSubscribeMapper.querySumAccountByUserId(transferCnd);
	}

	@Override
	public void updateTransferOld() {
		List<BTransferVo> list = bTransferMapper.querytransferRecordAllList();

		BTransferVo bv = new BTransferVo();

		for (int i = 0; i < list.size(); i++) {
			bv = list.get(i);
			bv.setCurrApr(new BigDecimal(bv.getNowApr()));

			bTransferMapper.saveTransfer(bv);
		}

	}

	@Override
	public List<BTransferVo> querytransferRecordList(SearchTransferVo seach, Page page) {
		List<BTransferVo> list = bTransferMapper.querytransferRecordList(seach, page);
		return list;
	}

	@Override
	public int getCountTransfering() {
		return bTransferMapper.getCountTransfering();
	}

	@Override
	public Integer querySubscribesCountByUserId(Integer userId) throws Exception {
		return bSubscribeMapper.querySubscribesCountByUserId(userId);
	}

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public MessageBox toTransferJudge(Integer userId, Integer tenderId) throws Exception {
		if (userId.intValue() == 371) {  // 顶玺金融
			/*Map<String, Object> params = new HashMap<String, Object>();
	        params.put("userId", userId);
	        params.put("tenderId", tenderId);
	        bTransferMapper.transferCheck(params);
			if (params.get("msg") == null || !params.get("msg").equals("00001")) {
				return new MessageBox("1", "该债权为认购定期宝提前退出转让所得债权，存在问题，暂不支持发起转让");
			}*/
			if (bTransferMapper.judgeParentTenderIsFix(userId, tenderId) > 0) {
				return new MessageBox("1", "该债权为认购定期宝提前退出转让所得债权，存在问题，暂不支持发起转让");
			}
		}
		return new MessageBox("0", "success");
	}

}
