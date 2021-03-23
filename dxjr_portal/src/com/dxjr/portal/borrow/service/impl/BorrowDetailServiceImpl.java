package com.dxjr.portal.borrow.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.AccountUploadDocService;
import com.dxjr.portal.account.service.BorrowReportService;
import com.dxjr.portal.account.vo.AccountUploadDocVo;
import com.dxjr.portal.borrow.entity.Interest;
import com.dxjr.portal.borrow.mapper.BorrowDetailMapper;
import com.dxjr.portal.borrow.mapper.BorrowerMapper;
import com.dxjr.portal.borrow.service.BorrowDetailService;
import com.dxjr.portal.borrow.service.CalculatorService;
import com.dxjr.portal.borrow.service.MortgageService;
import com.dxjr.portal.borrow.vo.BorrowDetailCreditVo;
import com.dxjr.portal.borrow.vo.BorrowDetailRepayVo;
import com.dxjr.portal.borrow.vo.BorrowDetailVo;
import com.dxjr.portal.borrow.vo.BorrowDetailWebPayVo;
import com.dxjr.portal.borrow.vo.BorrowerVo;
import com.dxjr.portal.borrow.vo.MortgageVo;

@Service
public class BorrowDetailServiceImpl implements BorrowDetailService {

	@Autowired
	private BorrowDetailMapper borrowDetailMapper;
	@Autowired
	private BorrowReportService borrowReportService;
	@Autowired
	private BorrowerMapper borrowerMapper;
	@Autowired
	private MortgageService mortgageService;
	@Autowired
	private AccountUploadDocService accountUploadDocService;
	@Autowired
	private CalculatorService calculatorService;

	@Override
	public Map<String, ?> queryBorrowDetailInfo(Integer borrowId) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		BorrowDetailVo borrowDetailVo = borrowDetailMapper.selectBorrowDetailByBorrowId(borrowId);
		if (null == borrowDetailVo) {
			return null;
		}
		resultMap.put("borrowDetailVo", borrowDetailVo);
		// 如果是还款中
		if (borrowDetailVo.getStatus() == Constants.BORROW_STATUS_REPAY_CODE) {
			// 根据借款标id查询待还统计数据（用于投标）
			BorrowDetailRepayVo borrowDetailRepayVo = borrowDetailMapper.selectBorrowDetailRepayByBorrowId(borrowId);
			// 根据借款标id查询待还本息总额
			BigDecimal waitToPayMoney = borrowDetailMapper.selectRepayTotal(borrowId);
			borrowDetailRepayVo.setWaitToPayMoney(waitToPayMoney);
			resultMap.put("borrowDetailRepayVo", borrowDetailRepayVo);
			// 如果是还款结束
		} else if (borrowDetailVo.getStatus() == Constants.BORROW_STATUS_END_CODE) {
			BigDecimal repaymentYesAccountTotal = borrowDetailMapper.queryHavePayTotal(borrowId);
			resultMap.put("repaymentYesAccountTotal", repaymentYesAccountTotal);
			// 如果是已垫付
		} else if (borrowDetailVo.getStatus() == Constants.BORROW_STATUS_WEBPAY_CODE) {
			BorrowDetailWebPayVo borrowDetailWebPayVo = borrowDetailMapper.queryBorrowDetailWebPayById(borrowId);
			resultMap.put("borrowDetailWebPayVo", borrowDetailWebPayVo);
		}
		// 借款者信用档案
		BorrowDetailCreditVo borrowDetailCreditVo = borrowDetailMapper.queryBorrowDetailCrediByMemberId(borrowDetailVo.getUserId());
		if (null != borrowDetailCreditVo) {
			// 借款用户下的待还本息总计
			BigDecimal repaymentAccountTotal = borrowReportService.queryRepaymentAccountTotalByMemberId(borrowDetailVo.getUserId());
			borrowDetailCreditVo.setWaitToPay(repaymentAccountTotal);
			resultMap.put("borrowDetailCreditVo", borrowDetailCreditVo);
		}
		// 除了净值标和秒标以外显示用户信息和审核状态
		BorrowerVo borrowerVo = new BorrowerVo();
		if (borrowDetailVo.getBorrowtype() != Constants.BORROW_TYPE_NETVALUE && borrowDetailVo.getBorrowtype() != Constants.BORROW_TYPE_SEC) {
			// 获取借款者详细信息
			borrowerVo = borrowerMapper.queryBorrowerByBorrowId(borrowId);
			// 获取借款者抵押信息
			MortgageVo mortgageVo = mortgageService.getMortgageByBorrowId(borrowId);
			resultMap.put("mortgageVo", mortgageVo);
			// 获取用户的认证信息
			List<AccountUploadDocVo> accountUploadDocs = accountUploadDocService.queryUniqueStylesByBorrowId(borrowId);
			resultMap.put("accountUploadDocs", accountUploadDocs);
		}
		// 查询用户的信息和生日信息
		BorrowerVo borrowerBaseVo = borrowerMapper.queryBorrowerBaseInfoByBorrowId(borrowDetailVo.getUserId());
		if (null != borrowerVo) {
			borrowerVo.setUsername(borrowerBaseVo.getUsername());
			borrowerVo.setBirthday(borrowerBaseVo.getBirthday());
			borrowerVo.setSex("0".equals(borrowerBaseVo.getSex()) ? "男" : "女");
			resultMap.put("borrowerVo", borrowerVo);
		} else {
			resultMap.put("borrowerVo", borrowerBaseVo);
		}
		// 计算投标100元能赚的利息
		Interest inter = new Interest();
		inter.setMoney(new BigDecimal(100));
		inter.setRate(borrowDetailVo.getApr());
		inter.setPeriod(new BigDecimal(borrowDetailVo.getTimeLimit()));
		if (borrowDetailVo.getStyle() == 4) {
			inter.setCategory(1);
		} else {
			inter.setCategory(0);
		}
		inter.setStyle(borrowDetailVo.getStyle());
		calculatorService.getInterest(inter);
		resultMap.put("inter", inter);
		return resultMap;
	}

	@Override
	public Map<String, ?> showBorrowDetailInfo(Integer borrowId) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		BorrowDetailVo borrowDetailVo = borrowDetailMapper.selectBorrowDetail(borrowId);
		if (null == borrowDetailVo) {
			return null;
		}
		resultMap.put("borrowDetailVo", borrowDetailVo);
		// 如果是还款中
		if (borrowDetailVo.getStatus() == Constants.BORROW_STATUS_REPAY_CODE) {
			// 根据借款标id查询待还统计数据（用于投标）
			BorrowDetailRepayVo borrowDetailRepayVo = borrowDetailMapper.selectBorrowDetailRepayByBorrowId(borrowId);
			// 根据借款标id查询待还本息总额
			BigDecimal waitToPayMoney = borrowDetailMapper.selectRepayTotal(borrowId);
			borrowDetailRepayVo.setWaitToPayMoney(waitToPayMoney);
			resultMap.put("borrowDetailRepayVo", borrowDetailRepayVo);
			// 如果是还款结束
		} else if (borrowDetailVo.getStatus() == Constants.BORROW_STATUS_END_CODE) {
			BigDecimal repaymentYesAccountTotal = borrowDetailMapper.queryHavePayTotal(borrowId);
			resultMap.put("repaymentYesAccountTotal", repaymentYesAccountTotal);
			// 如果是已垫付
		} else if (borrowDetailVo.getStatus() == Constants.BORROW_STATUS_WEBPAY_CODE) {
			BorrowDetailWebPayVo borrowDetailWebPayVo = borrowDetailMapper.queryBorrowDetailWebPayById(borrowId);
			resultMap.put("borrowDetailWebPayVo", borrowDetailWebPayVo);
		}
		// 借款者信用档案
		BorrowDetailCreditVo borrowDetailCreditVo = borrowDetailMapper.queryBorrowDetailCrediByMemberId(borrowDetailVo.getUserId());
		if (null != borrowDetailCreditVo) {
			// 借款用户下的待还本息总计
			BigDecimal repaymentAccountTotal = borrowReportService.queryRepaymentAccountTotalByMemberId(borrowDetailVo.getUserId());
			borrowDetailCreditVo.setWaitToPay(repaymentAccountTotal);
			resultMap.put("borrowDetailCreditVo", borrowDetailCreditVo);
		}
		// 除了净值标和秒标以外显示用户信息和审核状态
		BorrowerVo borrowerVo = new BorrowerVo();
		if (borrowDetailVo.getBorrowtype() != Constants.BORROW_TYPE_NETVALUE && borrowDetailVo.getBorrowtype() != Constants.BORROW_TYPE_SEC) {
			// 获取借款者详细信息
			borrowerVo = borrowerMapper.queryBorrowerByBorrowId(borrowId);
			// 获取借款者抵押信息
			MortgageVo mortgageVo = mortgageService.getMortgageByBorrowId(borrowId);
			resultMap.put("mortgageVo", mortgageVo);
			// 获取用户的认证信息
			List<AccountUploadDocVo> accountUploadDocs = accountUploadDocService.queryUniqueStylesByBorrowId(borrowId);
			resultMap.put("accountUploadDocs", accountUploadDocs);
		}
		// 查询用户的信息和生日信息
		BorrowerVo borrowerBaseVo = borrowerMapper.queryBorrowerBaseInfoByBorrowId(borrowDetailVo.getUserId());
		if (null != borrowerVo) {
			borrowerVo.setUsername(borrowerBaseVo.getUsername());
			borrowerVo.setBirthday(borrowerBaseVo.getBirthday());
			resultMap.put("borrowerVo", borrowerVo);
		} else {
			resultMap.put("borrowerVo", borrowerBaseVo);
		}
		// 计算投标100元能赚的利息
		Interest inter = new Interest();
		inter.setMoney(new BigDecimal(100));
		inter.setRate(borrowDetailVo.getApr());
		inter.setPeriod(new BigDecimal(borrowDetailVo.getTimeLimit()));
		if (borrowDetailVo.getStyle() == 4) {
			inter.setCategory(1);
		} else {
			inter.setCategory(0);
		}
		inter.setStyle(borrowDetailVo.getStyle());
		calculatorService.getInterest(inter);
		resultMap.put("inter", inter);
		return resultMap;
	}
}
