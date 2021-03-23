package com.dxjr.portal.fuiou.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.Account;
import com.dxjr.base.entity.AccountLog;
import com.dxjr.base.entity.AccountRechargeFeedback;
import com.dxjr.base.entity.RechargeRecord;
import com.dxjr.base.mapper.BaseAccountLogMapper;
import com.dxjr.base.mapper.BaseAccountMapper;
import com.dxjr.base.mapper.BaseAccountRechargeFeedbackMapper;
import com.dxjr.base.mapper.BaseRechargeRecordMapper;
import com.dxjr.common.Dictionary;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.mapper.RechargeRecordMapper;
import com.dxjr.portal.account.vo.RechargeRecordCnd;
import com.dxjr.portal.account.vo.RechargeRecordVo;
import com.dxjr.portal.fuiou.service.FuiouPayService;
import com.dxjr.portal.fuiou.util.FuiouUtil;
import com.dxjr.portal.fuiou.vo.FuiouPayBackVo;
import com.dxjr.portal.fuiou.vo.FuiouPayVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;

@Service
public class FuiouPayServiceImpl implements FuiouPayService {

	@Autowired
	private BaseRechargeRecordMapper baseRechargeMapper;
	@Autowired
	private RechargeRecordMapper rechargeMapper;
	@Autowired
	private BaseAccountMapper baseAccountMapper;
	@Autowired
	private BaseAccountLogMapper baseAccountLogMapper;
	@Autowired
	private BaseAccountRechargeFeedbackMapper rechargeFeedbackMapper;

	public int saveRecharge(FuiouPayVo vo, String ip, ShiroUser user) throws Exception {
		// 生成待付款充值记录
		RechargeRecord r = new RechargeRecord();
		r.setTradeNo(vo.getOrder_id());
		r.setUserId(user.getUserId());
		r.setType(Constants.RECHARGE_TYPE_ONLINE);
		r.setStatus(Constants.RECHARGE_STATUS_ONLINE_WAIT);
		r.setMoney(new BigDecimal(Float.parseFloat(vo.getOrder_amt()) / 100));
		r.setPayment(Dictionary.getValue(80706, vo.getIss_ins_cd()));// 支付银行名称
		r.setFee(BigDecimal.ZERO);// 在线支付没有手续费
		r.setAddip(ip);
		r.setAddtime(DateUtils.getTime());
		r.setOnlinetype(Constants.ONLINE_TYPE_FUIOUPAY);
		r.setVersion(1);
		r.setPlatform(user.getPlatform());
		return baseRechargeMapper.insertEntity(r);
	}

	public String updateRecharge(FuiouPayBackVo vo, String ip) throws Exception {
		// 找到充值记录并锁定
		RechargeRecordCnd cnd = new RechargeRecordCnd();
		cnd.setTradeNo(vo.getOrder_id());
		cnd.setLockedRecordYn(BusinessConstants.LOCKED_RECORD_YES);
		RechargeRecordVo r = rechargeMapper.queryRechargeRecordList(cnd).get(0);

		// 记录接口日志
		AccountRechargeFeedback rechargeFeedback = new AccountRechargeFeedback();
		rechargeFeedback.setOrderno(vo.getOrder_id());
		rechargeFeedback.setPaymode(r.getPayment());
		rechargeFeedback.setPaystatus(vo.getOrder_pay_code());
		rechargeFeedback.setPaystring(vo.getOrder_pay_error());
		rechargeFeedback.setAmount((Float.parseFloat(vo.getOrder_amt()) * 0.01) + "");
		rechargeFeedback.setMoneytype(BusinessConstants.ONLINE_PAY_CURRENCYTYPE);
		rechargeFeedback.setMd5str(vo.getMd5());
		rechargeFeedback.setRemark1("富友流水号：" + vo.getFy_ssn());
		rechargeFeedback.setRemark2("富友订单状态：" + vo.getOrder_st());
		rechargeFeedback.setAddtime(new Date());
		rechargeFeedback.setOnlinetype(Constants.ONLINE_TYPE_FUIOUPAY);
		rechargeFeedbackMapper.insertEntity(rechargeFeedback);

		// 记录为待支付
		if (r.getStatus().equals(Constants.RECHARGE_STATUS_ONLINE_WAIT)) {
			// 支付返回码为0000 & 返回通知有效 & 订单状态：已支付
			if ("0000".equals(vo.getOrder_pay_code()) && FuiouUtil.checkBackMd5(vo) && "11".equals(vo.getOrder_st())) {
				// 充值成功
				return this.doPay(r, ip);
			} else {
				// 充值失败
				RechargeRecord record = baseRechargeMapper.queryByIdForUpdate(r.getId());
				record.setStatus(Constants.RECHARGE_STATUS_FAILURE);
				baseRechargeMapper.updateEntity(record);
				return vo.getOrder_pay_code() + vo.getOrder_pay_error();
			}
		}

		return "failure";
	}

	public String updateRecharge(String order_id, String ip) throws Exception {
		// 找到充值记录并锁定
		RechargeRecordCnd cnd = new RechargeRecordCnd();
		cnd.setTradeNo(order_id);
		cnd.setLockedRecordYn(BusinessConstants.LOCKED_RECORD_YES);
		RechargeRecordVo r = rechargeMapper.queryRechargeRecordList(cnd).get(0);

		if (r.getStatus().equals(Constants.RECHARGE_STATUS_ONLINE_WAIT)) {
			// 进行充值
			return this.doPay(r, ip);
		} else {
			return "补单失败：充值记录状态非待付款";
		}

	}

	/**
	 * 充值
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年8月21日
	 * @param r
	 * @param ip
	 * @return
	 * @throws Exception String
	 */
	private String doPay(RechargeRecordVo r, String ip) throws Exception {
		// 更新充值记录
		RechargeRecord record = baseRechargeMapper.queryByIdForUpdate(r.getId());
		record.setStatus(Constants.RECHARGE_STATUS_SUCCESS);
		record.setVerifyTime(DateUtils.getTime());
		record.setVerifyRemark(Constants.ONLINE_TYPE_FUIOUPAY_NAME.concat("调用接口调用自动审核"));
		record.setVerifyTime2(DateUtils.getTime());
		record.setVerifyRemark2(Constants.ONLINE_TYPE_FUIOUPAY_NAME.concat("调用接口调用自动审核"));
		int n = baseRechargeMapper.updateEntity(record);

		if (n > 0) {
			// 查询是否有充值账户日志
			int isRecharge = baseAccountLogMapper.queryRecharge(r.getId());
			if (isRecharge == 0) {
				// 更新账户
				Account account = baseAccountMapper.queryByUserIdForUpdate(r.getUserId());
				account.setTotal(account.getTotal().add(r.getMoney()));
				account.setUseMoney(account.getUseMoney().add(r.getMoney()));
				account.setNoDrawMoney(account.getNoDrawMoney().add(r.getMoney()));
				baseAccountMapper.updateEntity(account);

				// 记录账户日志
				String remark = "在线充值成功,本次使用的是" + Constants.ONLINE_TYPE_FUIOUPAY_NAME + "进行充值.";
				AccountLog accountLog = new AccountLog();
				accountLog.setUserId(r.getUserId());
				accountLog.setAddip(ip);
				accountLog.setAddtime(new Date().getTime() / 1000 + "");
				accountLog.setMoney(r.getMoney());
				accountLog.setTotal(account.getTotal());
				accountLog.setDrawMoney(account.getDrawMoney());
				accountLog.setNoDrawMoney(account.getNoDrawMoney());
				accountLog.setUseMoney(account.getUseMoney());
				accountLog.setNoUseMoney(account.getNoUseMoney());
				accountLog.setCollection(account.getCollection());
				accountLog.setFirstBorrowUseMoney(account.getFirstBorrowUseMoney());
				accountLog.setToUser(r.getUserId());
				accountLog.setType("online_recharge");
				accountLog.setRemark(remark);
				accountLog.setBorrowId(r.getId());
				baseAccountLogMapper.insertEntity(accountLog);
				return "success";
			}
		}
		return "undo";
	}
}
