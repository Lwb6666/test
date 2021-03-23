package com.dxjr.portal.account.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.Account;
import com.dxjr.base.entity.AccountLog;
import com.dxjr.base.entity.TodrawLog;
import com.dxjr.base.mapper.BaseAccountMapper;
import com.dxjr.base.mapper.BaseTodrawLogMapper;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.mapper.AccountNetValueMapper;
import com.dxjr.portal.account.mapper.TodrawLogMapper;
import com.dxjr.portal.account.service.AccountLogService;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.service.BorrowReportService;
import com.dxjr.portal.account.service.TodrawLogService;
import com.dxjr.portal.account.util.UserNetValue;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.account.vo.TodrawLogCnd;
import com.dxjr.portal.account.vo.TodrawLogVo;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.MD5;
import com.dxjr.utils.exception.AppException;

@Service
public class TodrawLogServiceImpl implements TodrawLogService {

	@Autowired
	private BaseTodrawLogMapper baseTodrawLogMapper;
	@Autowired
	private BaseAccountMapper baseAccountMapper;

	@Autowired
	private TodrawLogMapper todrawLogMapper;

	@Autowired
	private BorrowReportService borrowReportService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountLogService accountLogService;
	@Autowired
	private AccountNetValueMapper netValueMapper;

	@Override
	public String saveTodrawLog(MemberVo memberVo, String money, String paypassword, String addip) throws Exception {
		String result = "success";
		// 验证转可提金额的数据是否正确
		result = validateTodrawData(memberVo, money, paypassword);
		if (!"success".equals(result)) {
			return result;
		}
		// 当前帐号信息
		AccountVo accountVo = accountService.queryAccountByUserId(memberVo.getId());
		// 保留两位小数
		BigDecimal takeMoney = new BigDecimal(money).setScale(2, RoundingMode.DOWN);
		// 冻结用户资金
		Account account = coldTodrawAccount(accountVo, takeMoney);
		// 插入转可提申请资金记录
		this.packageAccountlogByApplyToDraw(addip, takeMoney, account);
		// 计算手续费
		BigDecimal fee = takeMoney.multiply(Constants.TO_DRAW_LOG_FEE_APR).setScale(2, BigDecimal.ROUND_HALF_UP);
		// 插入转可提记录
		TodrawLog todrawLog = this.addTodraw(account, takeMoney, fee, addip);

		// 系统自动审核通过
		todrawLog = this.autoApproTodraw(todrawLog);
		if (todrawLog != null && todrawLog.getId() != null) {
			// 审核通过，转可提金额转入和插入资金记录
			this.packageAccountByPassedToDraw(todrawLog);
			// 审核通过，扣除手续费和插入资金记录
			this.packageAccountByDeductFee(todrawLog);
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * Description:验证转可提现的数据是否正确<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param takeCashMoneyVo
	 * @param memberVo
	 * @return
	 * @throws Exception String
	 */
	private String validateTodrawData(MemberVo memberVo, String money, String paypassword) throws Exception {
		String result = "success";
		if (memberVo.getIsFinancialUser() == Constants.MEMBER_BORROW_USER) {
			return "您是借款用户，不能申请转可提现！";
		}
		if (null == money || "".equals(money.trim())) {
			return "请填写转可提现金额！";
		}
		if (!money.matches("^-?\\d+\\.\\d+$") && !money.matches("^\\d+$")) {
			return "金额输入不合法！";
		}
		if (null == paypassword || !MD5.toMD5(paypassword).equals(memberVo.getPaypassword())) {
			return "您的交易密码输入错误！【如果您登录后修改过交易密码，请重新登录后再试！】";
		}
		BigDecimal takeMoney = new BigDecimal(money);
		// 计算当前的净值额度
		UserNetValue netValue = new UserNetValue();
		netValue.setUserid(memberVo.getId());
		netValueMapper.callGetUserNetMoneyLimit(netValue);
		BigDecimal maxAccount = netValue.getNetMoneyLimit();
		// 当前帐号信息
		AccountVo accountVo = accountService.queryAccountByUserId(memberVo.getId());
		// 查询冻结中的转可提金额
		BigDecimal noMoneyToal = queryTodrawNoMoney(memberVo.getId());
		// 最大转可提金额
		BigDecimal maxDrawMoney = BigDecimal.ZERO;
		if (takeMoney.compareTo(accountVo.getNoDrawMoney()) == 1) {
			return "提转可提金额必须小于￥" + accountVo.getNoDrawMoney().setScale(2, BigDecimal.ROUND_DOWN);
		}
		if (noMoneyToal != null) {
			BigDecimal cale = accountVo.getDrawMoney().add(noMoneyToal);
			if (maxAccount.compareTo(cale) == -1) {
				maxDrawMoney = new BigDecimal("0.00");
			} else {
				maxDrawMoney = maxAccount.subtract(accountVo.getDrawMoney()).subtract(noMoneyToal).setScale(2, BigDecimal.ROUND_DOWN);
				if (maxDrawMoney.compareTo(new BigDecimal(100)) == -1) {
					maxDrawMoney = new BigDecimal("0.00");
				}
			}
		} else {
			if (maxAccount.compareTo(accountVo.getDrawMoney()) == -1) {
				maxDrawMoney = new BigDecimal("0.00");
			} else {
				maxDrawMoney = maxAccount.subtract(accountVo.getDrawMoney()).setScale(2, BigDecimal.ROUND_DOWN);
			}
		}
		if (takeMoney.compareTo(new BigDecimal(100)) == -1) {
			return "您的转可提现金额必须大于￥100";
		}
		if (takeMoney.compareTo(maxDrawMoney) == 1) {
			return "您的最大转可提现金额为:￥" + maxDrawMoney.setScale(2, BigDecimal.ROUND_DOWN);
		}
		if (takeMoney.compareTo(accountVo.getNoDrawMoney()) == 1) {
			return "您的不可提金额不足";
		}
		return result;
	}

	public BigDecimal queryTodrawNoMoney(int userid) throws Exception {
		return todrawLogMapper.queryTodrawNoMoney(userid);
	}

	/**
	 * 
	 * <p>
	 * Description:冻结用户资金<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param accountVo
	 * @param takeMoney
	 * @return
	 * @throws Exception
	 * @throws AppException Account
	 */
	private Account coldTodrawAccount(AccountVo accountVo, BigDecimal takeMoney) throws Exception, AppException {
		accountVo.setNoDrawMoney(accountVo.getNoDrawMoney().subtract(takeMoney));
		accountVo.setUseMoney(accountVo.getUseMoney().subtract(takeMoney));
		accountVo.setNoUseMoney(accountVo.getNoUseMoney().add(takeMoney));
		Account account = new Account();
		BeanUtils.copyProperties(accountVo, account);
		account.setSelfVersion(account.getVersion());
		account.setVersion(account.getVersion() + 1);
		Integer updateCount = baseAccountMapper.updateEntity(account);
		if (null == updateCount || updateCount == 0) {
			throw new AppException("帐号数据已变更,请刷新页面或稍后重试！");
		}
		return account;
	}

	/**
	 * 
	 * <p>
	 * Description:插入转可提金额申请资金记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param addip
	 * @param takeMoney
	 * @param account
	 * @throws Exception void
	 */
	private void packageAccountlogByApplyToDraw(String addip, BigDecimal takeMoney, Account account) throws Exception {
		AccountLog accountLog = new AccountLog();
		accountLog.setAddip(addip);
		accountLog.setAddtime(DateUtils.getTime());
		accountLog.setCollection(account.getCollection());
		accountLog.setUseMoney(account.getUseMoney());
		accountLog.setNoUseMoney(account.getNoUseMoney());
		accountLog.setTotal(account.getTotal());
		accountLog.setFirstBorrowUseMoney(account.getFirstBorrowUseMoney());
		accountLog.setMoney(takeMoney);// 交易金额
		accountLog.setRemark("转可提金额申请已经提交");
		accountLog.setToUser(account.getUserId());// TODO 暂时设置为本人
		accountLog.setUserId(account.getUserId());
		accountLog.setType("draw_cold");
		accountLog.setDrawMoney(account.getDrawMoney());
		accountLog.setNoDrawMoney(account.getNoDrawMoney());
		accountLogService.insertAccountLog(accountLog);
	}

	/**
	 * 
	 * <p>
	 * Description:增加转可提记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param account
	 * @param money
	 * @param fee
	 * @param addip
	 * @return
	 * @throws Exception TodrawLog
	 */
	private TodrawLog addTodraw(Account account, BigDecimal money, BigDecimal fee, String addip) throws Exception {
		TodrawLog todrawLog = new TodrawLog();
		todrawLog.setAddtime(new Date());
		todrawLog.setAddip(addip);
		todrawLog.setUserId(account.getUserId());
		todrawLog.setMoney(money);
		todrawLog.setFee(fee);
		todrawLog.setCredited(money.subtract(fee));
		todrawLog.setStatus(Constants.TO_DRAW_LOG_STATUS_APPLYING);
		todrawLog.setVersion(1);
		todrawLog.setPlatform(((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getPlatform());
		baseTodrawLogMapper.insertEntity(todrawLog);
		return todrawLog;
	}

	@Override
	public String cancelCash(int draw_id, int userid, String addip) throws Exception {
		String result = "success";
		TodrawLogCnd todrawLogCnd = new TodrawLogCnd();
		todrawLogCnd.setId(draw_id);
		todrawLogCnd.setUserId(userid);
		TodrawLogVo todrawLogVo = this.queryTodrawLogVoByCnd(todrawLogCnd);
		if (null == todrawLogVo || todrawLogVo.getStatus() != Constants.TO_DRAW_LOG_STATUS_APPLYING) {
			return "转可提记录未找到或状态已变更,请刷新数据或稍后重试！";
		}
		// 更新转可提记录为取消状态
		TodrawLog todrawLog = this.updateDrawStatusByCancel(todrawLogVo);
		// 解冻账户资金
		Account account = uncoldAccount(todrawLog);
		// 插入取消提现资金记录
		this.packageAccountlogByCancelDraw(addip, todrawLog, account);
		return result;
	}

	public TodrawLogVo queryTodrawLogVoByCnd(TodrawLogCnd todrawLogCnd) throws Exception {
		List<TodrawLogVo> list = todrawLogMapper.queryTodrawLogList(todrawLogCnd);
		if (null != list && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * Description:更新转可提记录为取消状态<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param todrawLogVo
	 * @return
	 * @throws Exception
	 * @throws AppException TodrawLog
	 */
	private TodrawLog updateDrawStatusByCancel(TodrawLogVo todrawLogVo) throws Exception, AppException {
		TodrawLog todrawLog = new TodrawLog();
		todrawLogVo.setStatus(Constants.TO_DRAW_LOG_STATUS_CANCEL_CASH);
		todrawLogVo.setVerifyUserId(todrawLogVo.getUserId());
		todrawLogVo.setVerifyTime(new Date());
		todrawLogVo.setVerifyRemark("取消转可提");

		BeanUtils.copyProperties(todrawLogVo, todrawLog);
		todrawLog.setSelfVersion(todrawLogVo.getVersion());
		todrawLog.setVersion(todrawLogVo.getVersion() + 1);
		Integer updateCount = baseTodrawLogMapper.updateEntity(todrawLog);
		if (null == updateCount || updateCount == 0) {
			throw new AppException("转可提记录已变更,请刷新页面或稍后重试！");
		}
		return todrawLog;
	}

	/**
	 * 
	 * <p>
	 * Description:解冻账户资金<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param todrawLog
	 * @return
	 * @throws Exception
	 * @throws AppException Account
	 */
	private Account uncoldAccount(TodrawLog todrawLog) throws Exception, AppException {
		AccountVo accountVo = accountService.queryAccountByUserId(todrawLog.getUserId());
		accountVo.setNoUseMoney(accountVo.getNoUseMoney().subtract(todrawLog.getMoney()));
		accountVo.setNoDrawMoney(accountVo.getNoDrawMoney().add(todrawLog.getMoney()));
		Account account = new Account();
		BeanUtils.copyProperties(accountVo, account);
		account.setSelfVersion(account.getVersion());
		account.setVersion(account.getVersion() + 1);
		Integer updateCount = baseAccountMapper.updateEntity(account);
		if (null == updateCount || updateCount == 0) {
			throw new AppException("帐号数据已变更,请刷新页面或稍后重试！");
		}
		return account;
	}

	/**
	 * 
	 * <p>
	 * Description:插入取消转可提日志记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param addip
	 * @param todrawLog
	 * @param account
	 * @throws Exception void
	 */
	private void packageAccountlogByCancelDraw(String addip, TodrawLog todrawLog, Account account) throws Exception {
		AccountLog accountLog = new AccountLog();
		accountLog.setAddip(addip);
		accountLog.setAddtime(DateUtils.getTime());
		accountLog.setCollection(account.getCollection());
		accountLog.setUseMoney(account.getUseMoney());
		accountLog.setNoUseMoney(account.getNoUseMoney());
		accountLog.setTotal(account.getTotal());
		accountLog.setFirstBorrowUseMoney(account.getFirstBorrowUseMoney());
		accountLog.setMoney(todrawLog.getMoney());// 交易金额
		accountLog.setRemark("取消转可提");
		accountLog.setToUser(account.getUserId());// TODO 暂时设置为本人
		accountLog.setUserId(account.getUserId());
		accountLog.setType("cancel_draw");
		accountLog.setDrawMoney(account.getDrawMoney());
		accountLog.setNoDrawMoney(account.getNoDrawMoney());
		accountLogService.insertAccountLog(accountLog);
	}

	/**
	 * 
	 * <p>
	 * Description:系统自动审核通过<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param todrawLog
	 * @return
	 * @throws Exception TodrawLog
	 */
	public TodrawLog autoApproTodraw(TodrawLog todrawLog) throws Exception {
		TodrawLog todrawLogVo = null;
		if (todrawLog != null && todrawLog.getId() != null) {
			todrawLogVo = baseTodrawLogMapper.queryById(todrawLog.getId());
		}
		if (todrawLogVo != null && todrawLogVo.getId() != null) {
			BeanUtils.copyProperties(todrawLogVo, todrawLog);
			todrawLog.setSelfVersion(todrawLog.getVersion());
			todrawLog.setVersion(todrawLog.getVersion() + 1);
			todrawLog.setStatus(Constants.TO_DRAW_LOG_STATUS_APPROVE_SUCCESS);
			todrawLog.setVerifyUserId(-1);
			todrawLog.setVerifyTime(new Date());
			todrawLog.setVerifyRemark("系统自动审核通过");
			Integer updateCount = baseTodrawLogMapper.updateEntity(todrawLog);
			if (null == updateCount || updateCount == 0) {
				throw new AppException("转可提记录数据已变更,请刷新页面或稍后重试！");
			}
		}
		return todrawLog;
	}

	/**
	 * 
	 * <p>
	 * Description:审核通过，转可提金额转入和插入资金记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param todrawLog
	 * @throws Exception void
	 */
	public void packageAccountByPassedToDraw(TodrawLog todrawLog) throws Exception {
		AccountVo accountVo = accountService.queryAccountByUserId(todrawLog.getUserId());
		Account account = new Account();
		BeanUtils.copyProperties(accountVo, account);
		// 可提现金额增加
		account.setDrawMoney(account.getDrawMoney().add(todrawLog.getMoney()));
		account.setUseMoney(account.getUseMoney().add(todrawLog.getMoney()));
		// 解除冻结
		account.setNoUseMoney(account.getNoUseMoney().subtract(todrawLog.getMoney()));
		account.setSelfVersion(account.getVersion());
		account.setVersion(account.getVersion() + 1);

		Integer updateCount = baseAccountMapper.updateEntity(account);
		if (null == updateCount || updateCount == 0) {
			throw new AppException("帐号数据已变更,请刷新页面或稍后重试！");
		}
		AccountLog accountLog = new AccountLog();
		accountLog.setAddip("0.0.0.0");
		accountLog.setAddtime(DateUtils.getTime());
		accountLog.setCollection(account.getCollection());
		accountLog.setUseMoney(account.getUseMoney());
		accountLog.setNoUseMoney(account.getNoUseMoney());
		accountLog.setTotal(account.getTotal());
		accountLog.setFirstBorrowUseMoney(account.getFirstBorrowUseMoney());
		accountLog.setMoney(todrawLog.getMoney());// 交易金额
		accountLog.setRemark("转可提成功");
		accountLog.setToUser(-1);// TODO 暂时设置为本人
		accountLog.setUserId(account.getUserId());
		accountLog.setType("draw_success");
		accountLog.setDrawMoney(account.getDrawMoney());
		accountLog.setNoDrawMoney(account.getNoDrawMoney());
		accountLogService.insertAccountLog(accountLog);
	}

	/**
	 * 
	 * <p>
	 * Description:审核通过，扣除手续费和插入资金记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param todrawLog
	 * @throws Exception void
	 */
	public void packageAccountByDeductFee(TodrawLog todrawLog) throws Exception {
		AccountVo accountVo = accountService.queryAccountByUserId(todrawLog.getUserId());
		Account account = new Account();
		BeanUtils.copyProperties(accountVo, account);
		// 可提扣除手续费
		account.setDrawMoney(account.getDrawMoney().subtract(todrawLog.getFee()));
		// 可用余额扣除手续费
		account.setUseMoney(account.getUseMoney().subtract(todrawLog.getFee()));
		// 资产总额扣除手续费
		account.setTotal(account.getTotal().subtract(todrawLog.getFee()));
		account.setSelfVersion(account.getVersion());
		account.setVersion(account.getVersion() + 1);

		Integer updateCount = baseAccountMapper.updateEntity(account);
		if (null == updateCount || updateCount == 0) {
			throw new AppException("帐号数据已变更,请刷新页面或稍后重试！");
		}
		AccountLog accountLog = new AccountLog();
		accountLog.setAddip("0.0.0.0");
		accountLog.setAddtime(DateUtils.getTime());
		accountLog.setCollection(account.getCollection());
		accountLog.setUseMoney(account.getUseMoney());
		accountLog.setNoUseMoney(account.getNoUseMoney());
		accountLog.setTotal(account.getTotal());
		accountLog.setFirstBorrowUseMoney(account.getFirstBorrowUseMoney());
		accountLog.setMoney(todrawLog.getFee());// 交易金额
		accountLog.setRemark("转可提扣除手续费");
		accountLog.setToUser(-1);// TODO 暂时设置为本人
		accountLog.setUserId(account.getUserId());
		accountLog.setType("draw_deduct_fee");
		accountLog.setDrawMoney(account.getDrawMoney());
		accountLog.setNoDrawMoney(account.getNoDrawMoney());
		accountLogService.insertAccountLog(accountLog);
	}
}
