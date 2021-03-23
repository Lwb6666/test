package com.dxjr.portal.fix.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.FixAccount;
import com.dxjr.base.entity.FixBorrow;
import com.dxjr.base.entity.FixBorrowTransfer;
import com.dxjr.base.entity.FixOperationLog;
import com.dxjr.base.entity.FixTenderTransfer;
import com.dxjr.portal.borrow.mapper.BorrowMapper;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.fix.mapper.FixAccountLogMapper;
import com.dxjr.portal.fix.mapper.FixAccountMapper;
import com.dxjr.portal.fix.mapper.FixBorrowMapper;
import com.dxjr.portal.fix.mapper.FixBorrowTransferMapper;
import com.dxjr.portal.fix.mapper.FixOperationLogMapper;
import com.dxjr.portal.fix.mapper.FixTenderSubscribeMapper;
import com.dxjr.portal.fix.mapper.FixTenderTransferMapper;
import com.dxjr.portal.fix.service.FixBorrowTransferService;
import com.dxjr.portal.fix.vo.FixAccountLogVo;
import com.dxjr.portal.fix.vo.FixBorrowTransferVo;
import com.dxjr.portal.fix.vo.FixTenderSubscribeVo;
import com.dxjr.portal.fix.vo.FixTenderTransferCnd;

/**
 * @author WangQianJin
 * @title 定期宝转让服务实现类
 * @date 2015年9月15日
 */
@Service
public class FixBorrowTransferServiceImpl implements FixBorrowTransferService {
	
	@Autowired
	private FixTenderTransferMapper fixTenderTransferMapper;
	
	@Autowired
	private FixBorrowTransferMapper fixBorrowTransferMapper;
	
	@Autowired
	private FixBorrowMapper fixBorrowMapper;
	
	@Autowired
	private FixOperationLogMapper fixOperationLogMapper;
	
	@Autowired
	private FixTenderSubscribeMapper fixTenderSubscribeMapper;
	
	@Autowired
	private FixAccountMapper fixAccountMapper;
	
	@Autowired
	private BorrowMapper borrowMapper;
	
	@Autowired
	private FixAccountLogMapper fixAccountLogMapper;

	@Override
	public String saveTransferCancel(Integer fixBorrowTransferId, String ip) throws Exception {
		/*1、修改定期宝投标转让状态为取消转让*/
		FixTenderTransfer record = new FixTenderTransfer();
		record.setStatus(5);
		record.setFixBorrowTransferId(fixBorrowTransferId);
		record.setLastUpdateUser(-1);
		record.setRemark("定期宝取消转让");
		fixTenderTransferMapper.updateByTransferId(record);
		/*2、修改定期宝转让状态为取消转让*/		
		FixBorrowTransfer fixBorrowTransfer = new FixBorrowTransfer();
		fixBorrowTransfer.setId(fixBorrowTransferId);
		fixBorrowTransfer.setStatus(5);
		fixBorrowTransfer.setLastUpdateTime(new Date());
		fixBorrowTransfer.setLastUpdateUser(-1);
		fixBorrowTransfer.setRemark("定期宝取消转让");
		fixBorrowTransferMapper.updateByPrimaryKeySelective(fixBorrowTransfer);
		//获取定期宝转让信息
		FixBorrowTransferVo fixBorrowTransferVo = fixBorrowTransferMapper.queryFixBorrowTransferById(fixBorrowTransferId);
		/*3、修改定期宝状态为取消转让*/		
		FixBorrow fixBorrow = fixBorrowMapper.getFixBorrowByIdForUpdate(fixBorrowTransferVo.getFixBorrowId());
		fixBorrow.setLastModifyTime(new Date());
		fixBorrow.setLastModifylUser(-1);
		fixBorrow.setStatus(5);
		fixBorrowMapper.updateFixBorrowStatusById(fixBorrow);
		/*4、添加定期宝操作日志*/	
		addFixOperationLog(fixBorrowTransferVo.getFixBorrowId(), 11, ip);
		//根据转让ID获取认购明细
		FixTenderTransferCnd fixTenderTransferCnd=new FixTenderTransferCnd();
		fixTenderTransferCnd.setFixBorrowTransferId(fixBorrowTransferId);
		fixTenderTransferCnd.setStatus(1);  //认购中
		List<FixTenderSubscribeVo> subscribeList = fixTenderSubscribeMapper.queryTenderSubscribeListByTransferId(fixTenderTransferCnd);
		if(subscribeList!=null && subscribeList.size()>0){
			for(FixTenderSubscribeVo subscribe:subscribeList){
				/*5、根据认购ID修改状态为认购失败*/
				FixTenderSubscribeVo fixTenderSubscribeVo=new FixTenderSubscribeVo();
				fixTenderSubscribeVo.setId(subscribe.getId());
				fixTenderSubscribeVo.setRemark("定期宝取消转让,认购失败");
				fixTenderSubscribeMapper.updateStatusForSubFailureById(fixTenderSubscribeVo);
				/*6、更新认购定期宝账户总额与可用*/				
				FixAccount subscribeFixAccount = fixAccountMapper.queryFixAccountByFixBorrowId(subscribe.getNewFixBorrowId());
				//总额增加，可用增加
				subscribeFixAccount.setTotal(subscribeFixAccount.getTotal().add(subscribe.getAccount()));
				subscribeFixAccount.setUseMoney(subscribeFixAccount.getUseMoney().add(subscribe.getAccount()));
				fixAccountMapper.updateFixAccountById(subscribeFixAccount);
				/*7、添加定期宝账户日志*/	
				String borrowName="";
				BorrowVo borrowVo=borrowMapper.selectByPrimaryKey(subscribe.getBorrowId());
				if(borrowVo!=null){
					borrowName=borrowVo.getName();
				}
				insetFixAccountLog(subscribe.getNewFixBorrowId(), 12, subscribe.getBorrowId(), borrowName, subscribeFixAccount, subscribe.getAccount(), ip);
			}
		}
		
		return "success";
	}
	
	/**
	 * 添加定期宝操作日志
	 * @author WangQianJin
	 * @title @param fixBorrowId
	 * @title @param operType
	 * @title @param ip
	 * @title @throws Exception
	 * @date 2015年9月15日
	 */
	private void addFixOperationLog(Integer fixBorrowId, Integer operType, String ip) throws Exception {
		// 保存定期宝操作日志
		FixOperationLog fixOperationLog = new FixOperationLog();
		// 操作人ID
		fixOperationLog.setUserId(-1);
		// 用户类型
		fixOperationLog.setUserType(-1);
		// 定期宝ID
		fixOperationLog.setFixBorrowId(fixBorrowId);
		// 操作类型
		fixOperationLog.setOperType(operType);
		// 备注
		if (operType == 8) {
			fixOperationLog.setRemark("定期宝发起转让");
		} else if (operType == 9) {
			fixOperationLog.setRemark("定期宝转让复审中");
		} else if (operType == 10) {
			fixOperationLog.setRemark("定期宝转让成功");
		} else if (operType == 11) {
			fixOperationLog.setRemark("定期宝取消转让");
		}
		// IP
		fixOperationLog.setAddip(ip);
		// 平台来源
		fixOperationLog.setPlatform(1);
		fixOperationLogMapper.insertFixOperationLog(fixOperationLog);
	}
	
	/**
	 * 添加定期宝账户日志
	 * @author WangQianJin
	 * @title @param fixBorrowId
	 * @title @param type
	 * @title @param borrowId
	 * @title @param borrowName
	 * @title @param fixAccount
	 * @title @param subscribeAccount
	 * @title @param ip
	 * @title @throws Exception
	 * @date 2015年9月15日
	 */
	private void insetFixAccountLog(Integer fixBorrowId, Integer type, Integer borrowId, String borrowName, FixAccount fixAccount, BigDecimal subscribeAccount, String ip)
			throws Exception {
		// 新增认购方定期宝账户日志信息
		FixAccountLogVo fixAccountLog = new FixAccountLogVo();
		// 定期宝ID
		fixAccountLog.setFixBorrowId(fixBorrowId);
		// 操作类型
		fixAccountLog.setType(type);
		// 借款标ID
		fixAccountLog.setBorrowId(borrowId);
		// 借款标名称
		fixAccountLog.setBorrowName(borrowName);
		// 业务类型
		fixAccountLog.setIdType(2);
		// 操作金额
		fixAccountLog.setMoney(subscribeAccount);
		// 总额
		fixAccountLog.setTotal(fixAccount.getTotal());
		// 可用余额
		fixAccountLog.setUseMoney(fixAccount.getUseMoney());
		// 冻结金额
		fixAccountLog.setNoUseMoney(fixAccount.getNoUseMoney());
		// 待收总额
		fixAccountLog.setCollection(fixAccount.getCollection());
		// 待收总额
		fixAccountLog.setCapital(fixAccount.getCapital());
		// 实际收益
		fixAccountLog.setProfit(fixAccount.getProfit());
		// 操作人
		fixAccountLog.setAddUser(-1);
		// 操作IP
		fixAccountLog.setAddIp(ip);
		// 备注
		if (type == 7) {
			fixAccountLog.setRemark("自动认购定期宝成功");
		} else if (type == 9) {
			fixAccountLog.setRemark("定期宝自动复审成功，定期宝本金回款，总额增加");
		} else if (type == 10) {
			fixAccountLog.setRemark("定期宝自动复审成功，定期宝待收减少");
		} else if (type == 11) {
			fixAccountLog.setRemark("定期宝自动复审成功，认购定期宝总额增加");
		} else if (type == 12) {
			fixAccountLog.setRemark("定期宝自动取消转让，认购定期宝总额增加");
		}
		fixAccountLogMapper.insertFixAccountVoLog(fixAccountLog);
	}

}
