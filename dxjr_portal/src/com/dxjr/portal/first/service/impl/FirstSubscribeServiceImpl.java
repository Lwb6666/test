package com.dxjr.portal.first.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.curAccount.entity.CurOut;
import com.dxjr.portal.curAccount.service.CurOutService;
import com.dxjr.portal.first.mapper.FirstSubscribeMapper;
import com.dxjr.portal.first.mapper.FirstTransferBorrowMapper;
import com.dxjr.portal.first.mapper.FirstTransferMapper;
import com.dxjr.portal.first.service.FirstSubscribeService;
import com.dxjr.portal.first.vo.FirstSubscribeCnd;
import com.dxjr.portal.first.vo.FirstSubscribeVo;
import com.dxjr.portal.first.vo.FirstTransferVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:直通车转让认购业务实现类<br />
 * </p>
 * 
 * @title FirstSubscribeServiceImpl.java
 * @package com.dxjr.portal.first.service.impl
 * @author 朱泳霖
 * @version 0.1 2015年3月19日
 */
@Service
public class FirstSubscribeServiceImpl implements FirstSubscribeService {

	// 日志类取得
	Logger logger = LoggerFactory.getLogger(FirstSubscribeServiceImpl.class);

	@Autowired
	private FirstSubscribeMapper firstSubscribeMapper;
	@Autowired
	private AccountService accountService;
	@Autowired
	private CurOutService curOutService;
	@Autowired
	private FirstTransferBorrowMapper firstTransferBorrowMapper;
	@Autowired
	private FirstTransferMapper firstTransferMapper;

	@Override
	public Page querySubscribeListByTransferId(FirstSubscribeCnd firstSubscribeCnd, Page page) throws Exception {
		// 获得购买记录件数
		Integer totalCount = firstSubscribeMapper.querySubscribeListByTransferCount(firstSubscribeCnd.getTransferId());
		page.setTotalCount(totalCount);
		// 获得购买记录
		List<FirstSubscribeVo> firstSubscribeList = firstSubscribeMapper.querySubscribeListByTransferId(firstSubscribeCnd.getTransferId(), page);
		page.setResult(firstSubscribeList);
		return page;
	}

	@Override
	public String saveManualSubscribe(FirstSubscribeCnd firstSubscribeCnd, Integer userId) throws Exception {
		// 认购成功标志
		String result = BusinessConstants.SUCCESS;
		// 查询直通车转让记录并锁定
		FirstTransferVo firstTransferVo = firstTransferMapper.selectFirstTransferByIdForUpdate(firstSubscribeCnd.getTransferId());
		AccountVo accountvo = accountService.queryAccountByUserIdForUpdate(userId);
		BigDecimal useMoney = accountvo.getUseMoney();
		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (firstSubscribeCnd.getIsUseCurMoney() != null && firstSubscribeCnd.getIsUseCurMoney().equals("1")) {
			// 当认购金额大于普通账户的可用余额时，活期宝转出剩余不足的金额
			if (firstSubscribeCnd.getAccountReal().compareTo(useMoney) == 1) {
				BigDecimal remainTenderMoney = firstSubscribeCnd.getAccountReal().subtract(useMoney);
				// 活期宝金额转出用于投标
				result = curOutService.turnCurOutForInvest(userId, remainTenderMoney, "0.0.0.1",
						BusinessConstants.CUR_OUT_TYPE_TO_BUY_FIRST_BORROW_TRANSFER);
				if (!BusinessConstants.SUCCESS.equals(result)) {
					return result;
				}
			}
		}
		// 调用手动认购存储过程
		Map<String, Object> params = new HashMap<String, Object>();
		// 直通车转让Id
		params.put("transferId", firstTransferVo.getId());
		// 用户ID
		params.put("userId", userId);
		// IP地址
		params.put("addip", firstSubscribeCnd.getAddIp());
		// 平台来源
		params.put("platform", firstSubscribeCnd.getPlatform());
		firstSubscribeMapper.saveManualSubscribe(params);
		// 存储过程返回参数
		String msg = params.get("msg").toString();
		// 如果可使用活期宝金额，则可用金额加上活期宝可转出最大金额
		if (firstSubscribeCnd.getIsUseCurMoney() != null && firstSubscribeCnd.getIsUseCurMoney().equals("1") && "00000".equals(msg)) {
			// 当认购金额大于普通账户的可用余额时，活期宝转出剩余不足的金额
			if (firstSubscribeCnd.getAccountReal().compareTo(useMoney) == 1) {
				BigDecimal remainTenderMoney = firstSubscribeCnd.getAccountReal().subtract(useMoney);
				// 查询该笔直通车转让认购记录
				FirstSubscribeVo firstSubscribeVo = firstSubscribeMapper.querySubscribesLastByUserIdAndTransferId(firstSubscribeCnd.getTransferId(),
						userId, firstSubscribeCnd.getAccountReal());
				// 获取转出记录
				CurOut curOut = curOutService.queryCurOutLastByUserIdAndType(BusinessConstants.CUR_OUT_TYPE_TO_BUY_FIRST_BORROW_TRANSFER, userId,
						remainTenderMoney);
				if (firstSubscribeVo == null || curOut == null) {
					throw new AppException("活期宝认购直通车转让失败");
				} else {
					curOut.setTargetId(firstSubscribeVo.getId());
					Integer updateCurOutCount = curOutService.updateByPrimaryKeySelective(curOut);
					if (updateCurOutCount == null || updateCurOutCount == 0) {
						throw new AppException("活期宝认购直通车转让失败");
					}
				}
			}
		}
		if ("00001".equals(msg)) {
			throw new Exception("直通车转让存储过程,转让状态不正确,请重新操作");
		} else if ("00002".equals(msg)) {
			throw new Exception("直通车转让存储过程,账户余额不足,请重新操作");
		}
		return result;
	}

	@Override
	public String saveTransferRecheck(FirstSubscribeCnd firstSubscribeCnd, Integer userId, String userName, String checkRemark) throws Exception {
		// 认购成功标志
		String result = BusinessConstants.SUCCESS;
		// 调用自动复审存储过程
		Map<String, Object> params = new HashMap<String, Object>();
		// 直通车转让Id
		params.put("transferId", firstSubscribeCnd.getTransferId());
		// 用户ID
		params.put("userId", userId);
		// 用户名
		params.put("userName", userName);
		// IP地址
		params.put("addip", firstSubscribeCnd.getAddIp());
		// 审核备注
		params.put("checkRemark", checkRemark);
		// 平台来源
		params.put("platform", firstSubscribeCnd.getPlatform());
		firstSubscribeMapper.saveTransferRecheck(params);
		// 存储过程返回参数
		String msg = params.get("msg").toString();
		if ("00001".equals(msg)) {
			throw new Exception("直通车转让存储过程,债权价格不正确,请重新操作");
		} else if ("00002".equals(msg)) {
			throw new Exception("直通车转让存储过程,转让价格和认购价格不相等,请重新操作");
		} else if ("00003".equals(msg)) {
			throw new Exception("直通车转让存储过程,直通车转让状态不正确,请重新操作");
		} else if ("00004".equals(msg)) {
			throw new Exception("直通车转让存储过程,借款标待收总额和预还金额总额不相等,请重新操作");
		}
		return result;
	}

}
