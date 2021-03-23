package com.dxjr.portal.curAccount.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.Account;
import com.dxjr.base.mapper.BaseAccountMapper;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.mapper.AccountNetValueMapper;
import com.dxjr.portal.account.service.AccountLogService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.account.vo.DrawMoneyToNoDrawCnd;
import com.dxjr.portal.curAccount.entity.CurAccount;
import com.dxjr.portal.curAccount.entity.CurIn;
import com.dxjr.portal.curAccount.entity.CurOut;
import com.dxjr.portal.curAccount.mapper.CurAccountMapper;
import com.dxjr.portal.curAccount.mapper.CurInMapper;
import com.dxjr.portal.curAccount.mapper.CurOutMapper;
import com.dxjr.portal.curAccount.service.CurAccountLogService;
import com.dxjr.portal.curAccount.service.CurAccountService;
import com.dxjr.portal.curAccount.service.CurOutService;
import com.dxjr.portal.curAccount.vo.CurAccountVo;
import com.dxjr.portal.curAccount.vo.CurOutVo;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.MD5;
import com.dxjr.utils.exception.AppException;

/****
 * 
 * 
 * <p>
 * Description: 活期宝转出记录 BLL<br />
 * </p>
 * 
 * @title CurOutServiceImpl.java
 * @package com.dxjr.portal.curAccount.service.impl
 * @author HuangJun
 * @version 0.1 2015年4月27日
 */
@Service
public class CurOutServiceImpl implements CurOutService {

	Logger logger = LoggerFactory.getLogger(CurOutServiceImpl.class);

	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private CurAccountMapper curAccountMapper;
	@Autowired
	private BaseAccountMapper baseAccountMapper;
	@Autowired
	private CurOutMapper curOutMapper;
	@Autowired
	private CurInMapper curInMapper;
	@Autowired
	private CurAccountLogService curAccountLogService;
	@Autowired
	private AccountLogService accountLogService;
	@Autowired
	private CurAccountService curAccountService;
	@Autowired
	private AccountNetValueMapper accountNetValueMapper;

	@Override
	public Integer queryCountByUserIdAndDate(Integer userId, Date date) throws Exception {
		return curOutMapper.queryCountByUserIdAndDate(userId, date);
	}

	@Override
	public String validateCurOutParam(Integer userId, CurOutVo curOutVo) throws Exception {
		int hourNum = Integer.parseInt(DateUtils.format(new Date(), DateUtils.HH_ONLY));
		CurAccountVo curAccountVo = curAccountMapper.selectByUserId(userId);
		if (curAccountVo == null) {
			return "未开通活期宝，无法操作！";
		}
		if (hourNum >= 0 && hourNum < 4) {
			return "耐心等待一会，系统正在努力结算！";
		}
		if (userId == null) {
			return "请先登录后操作！";
		}
		if (curOutVo == null) {
			return "输入参数不正确，请刷新页面或稍后重试！";
		}
		if (curOutVo.getAccount() == null) {
			return "转出金额不能为空！";
		}
		if (!String.valueOf(curOutVo.getAccount().setScale(2, BigDecimal.ROUND_DOWN)).matches("^-?\\d+\\.\\d+$")
				&& String.valueOf(curOutVo.getAccount().setScale(2, BigDecimal.ROUND_DOWN)).matches("^\\d+$")) {
			return "转出金额输入不合法！";
		}
		if (curOutVo.getAccount().compareTo(BigDecimal.ZERO) <= 0) {
			return "转出金额必须大于0元！";
		}
		if (curOutVo.getAccount().compareTo(new BigDecimal(100000)) > 0) {
			return "单笔转出金额不能超过10万元";
		}
		if (curOutVo.getPaypassword() == null || curOutVo.getPaypassword().equals("")) {
			return "交易密码不能为空！";
		}
		MemberVo memberVo = memberMapper.queryPasswordInfoById(userId);
		// 未设置交易密码
		if (null == memberVo.getPaypassword() || "".equals(memberVo.getPaypassword())) {
			return "请先设置交易密码";
		}
		if (!MD5.toMD5(curOutVo.getPaypassword()).equals(memberVo.getPaypassword())) {
			return "您的交易密码输入错误！【如果您登录后修改过交易密码，请重新登录后再试！】";
		}
		if (curOutMapper.queryCountByUserIdAndDate(userId, new Date()) >= 5) {
			return "当天转出笔数已达到5笔，无法再进行转出！";
		}
		// 获取当前账户
		CurAccount curAccount = curAccountMapper.selectByUserId(userId);
		if (curAccount == null) {
			return "活期宝账户不存在，请刷新后重试！";
		}
		// 查询当天转入受限总额
		BigDecimal noDrawMoneyTotal = curInMapper.queryNoDrawMoneyTotalByUserIdAndDate(userId, new Date());
		// 最大转出额度
		BigDecimal maxOutAccount = curAccount.getTotal().subtract(noDrawMoneyTotal).setScale(2, BigDecimal.ROUND_DOWN);
		if (curOutVo.getAccount().compareTo(maxOutAccount) > 0) {
			return "本次转出金额不能超过" + maxOutAccount + "元！";
		}
		return "success";
	}

	@Override
	public String saveCurOut(Integer userId, BigDecimal money, String ip) throws Exception {
		// 查询该用户活期宝账户并锁定
		CurAccount curAccount = curAccountMapper.selectByUserIdForUpdate(userId);
		// 查询该用户的普通账户并锁定
		Account account = baseAccountMapper.queryByUserIdForUpdate(userId);
		// 效验转出金额是否合法
		String result = validateMoney(curAccount, money);
		if (!result.equals("success")) {
			return result;
		}
		// 计算待扣除的可产生收益的金额
		BigDecimal useMoney = BigDecimal.ZERO;
		// 计算待扣除的未产生收益的金额
		BigDecimal noUseMoney = BigDecimal.ZERO;
		// 计算待转出的可提金额
		BigDecimal drawMoney = BigDecimal.ZERO;
		// 计算待转出的受限金额
		BigDecimal noDrawMoney = BigDecimal.ZERO;
		if (money.compareTo(curAccount.getUseMoney()) <= 0) {
			useMoney = money;
		} else {
			useMoney = curAccount.getUseMoney();
			noUseMoney = money.subtract(useMoney);
		}
		// 封装并保存转出记录
		CurOut curOut = packageCurOutRecord(userId, money, useMoney, noUseMoney, ip, BusinessConstants.CUR_OUT_TYPE_TO_USE_MONEY, drawMoney,
				noDrawMoney);
		// 当需扣除未产生收益的金额，更新转入记录
		if (noUseMoney.compareTo(BigDecimal.ZERO) == 1) {
			// 当需扣除未产生收益的金额部分时，处理转入记录
			Map<String, BigDecimal> map = handleCurIn(userId, noUseMoney, BusinessConstants.CUR_OUT_TYPE_TO_USE_MONEY, drawMoney, noDrawMoney);
			// 更新转出记录中的可提金额和受限金额
			updateCurOutRecord(curOut, map.get("drawMoney"), map.get("noDrawMoney"));
		}
		// 更新活期宝账户（扣除转出金额）
		curAccount = packageCurAccount(curAccount, money, useMoney, noUseMoney, userId);
		// 保存活期宝账户变更日志记录
		packageCurAccountLog(curAccount, money, ip, BusinessConstants.CUR_OUT_TYPE_TO_USE_MONEY);
		// 更新普通账户（转出金额转入可用余额）
		account = packageAccount(account, curOut, BusinessConstants.CUR_OUT_TYPE_TO_USE_MONEY);
		// 普通账户变更保存日志记录
		packageAccountLog(account, money, ip, curOut.getId(), BusinessConstants.CUR_OUT_TYPE_TO_USE_MONEY);
		// 判断用户的可提金额是否大于净值额度，如果大于，转入受限金额
		DrawMoneyToNoDrawCnd drawMoneyToNoDrawCnd = new DrawMoneyToNoDrawCnd();
		drawMoneyToNoDrawCnd.setUserid(userId);
		drawMoneyToNoDrawCnd.setNetmoneytype(BusinessConstants.NET_TYPE_CUR_OUT);
		drawMoneyToNoDrawCnd.setAddip(ip);
		drawMoneyToNoDrawCnd.setAccountlogType("net_draw_to_nodraw_cur_out");
		drawMoneyToNoDrawCnd.setAccountlogRemark("活期宝转出到可用余额后,可提金额大于净值额度，可提金额转入受限金额。");
		accountNetValueMapper.dealDrawmoneyToNodraw(drawMoneyToNoDrawCnd);
		return "success";
	}

	/**
	 * 
	 * <p>
	 * Description:效验转出金额是否合法<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月6日
	 * @param curAccount
	 * @param money
	 *            转出金额
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String validateMoney(CurAccount curAccount, BigDecimal money) throws Exception {
		// 查询当天转入受限总额
		BigDecimal noDrawMoneyTotal = curInMapper.queryNoDrawMoneyTotalByUserIdAndDate(curAccount.getUserId(), new Date());
		// 最大转出额度
		BigDecimal maxOutAccount = curAccount.getTotal().subtract(noDrawMoneyTotal).setScale(2, BigDecimal.ROUND_DOWN);
		if (money.compareTo(maxOutAccount) > 0) {
			return "本次转出金额不能超过" + maxOutAccount + "元！";
		}
		return "success";
	}

	/**
	 * 
	 * <p>
	 * Description:封装并保存转出记录<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月7日
	 * @param userId
	 * @param money
	 *            转出金额
	 * @param useMoney
	 * @param noUseMoney
	 * @param ip
	 * @param type
	 *            转出类型
	 * @return
	 * @throws Exception
	 *             CurOut
	 */
	public CurOut packageCurOutRecord(Integer userId, BigDecimal money, BigDecimal useMoney, BigDecimal noUseMoney, String ip, Integer type,
			BigDecimal drawMoney, BigDecimal noDrawMoney) throws Exception {
		CurOut curOut = new CurOut();
		curOut.setUserId(userId);
		curOut.setType(type);
		curOut.setAccount(money);
		curOut.setUseMoney(useMoney);
		curOut.setNoUseMoney(noUseMoney);
		curOut.setDrawMoney(drawMoney);
		curOut.setNoDrawMoney(noDrawMoney);
		curOut.setAdduser(userId);
		curOut.setAddip(ip);
		curOut.setAddtime(new Date());
		curOut.setPlatform(((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getPlatform());
		curOutMapper.insert(curOut);
		return curOut;
	}

	/**
	 * 
	 * <p>
	 * Description:更新活期宝账户（扣除转出金额）<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月6日
	 * @param curAccount
	 * @param money
	 *            转出金额
	 * @param useMoney
	 * @param noUseMoney
	 * @param updateUserId
	 * @return
	 * @throws Exception
	 *             CurAccount
	 */
	public CurAccount packageCurAccount(CurAccount curAccount, BigDecimal money, BigDecimal useMoney, BigDecimal noUseMoney, Integer updateUserId)
			throws Exception {
		curAccount.setTotal(curAccount.getTotal().subtract(money));
		curAccount.setUseMoney(curAccount.getUseMoney().subtract(useMoney));
		curAccount.setNoUseMoney(curAccount.getNoUseMoney().subtract(noUseMoney));
		curAccount.setLastUpTime(new Date());
		curAccount.setLastUpUser(updateUserId);
		Integer updateCurAccountCount = curAccountMapper.updateByPrimaryKeySelective(curAccount);
		if (null == updateCurAccountCount || updateCurAccountCount == 0) {
			throw new AppException("活期宝账户更新失败，请刷新页面或稍后重试！");
		}
		return curAccount;
	}

	/**
	 * 
	 * <p>
	 * Description:保存活期宝账户变更日志记录<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月6日
	 * @param curAccount
	 * @param money
	 *            转出金额
	 * @param ip
	 * @param type
	 *            操作类型
	 * @throws Exception
	 *             void
	 */
	public void packageCurAccountLog(CurAccount curAccount, BigDecimal money, String ip, Integer type) throws Exception {
		if (type == BusinessConstants.CUR_OUT_TYPE_TO_USE_MONEY) {
			curAccountLogService.saveCurAccountLogByParams(curAccount, curAccount.getUserId(), money, "转出到可用余额", ip,
					BusinessConstants.CUR_OUT_TYPE_TO_USE_MONEY_CAPITAL_DETAIL, curAccount.getUserId());
		}
		if (type == BusinessConstants.CUR_OUT_TYPE_TO_TENDER_BORROW) {
			curAccountLogService.saveCurAccountLogByParams(curAccount, curAccount.getUserId(), money, "活期宝转出投标", ip,
					BusinessConstants.CUR_OUT_TYPE_TO_TENDER_BORROW_CAPITAL_DETAIL, -1);
		}
		if (type == BusinessConstants.CUR_OUT_TYPE_TO_TENDER_FIRST_BORROW) {
			curAccountLogService.saveCurAccountLogByParams(curAccount, curAccount.getUserId(), money, "活期宝转出开通直通车", ip,
					BusinessConstants.CUR_OUT_TYPE_TO_TENDER_FIRST_BORROW_CAPITAL_DETAIL, -1);
		}
		if (type == BusinessConstants.CUR_OUT_TYPE_TO_BUY_TRANSFER) {
			curAccountLogService.saveCurAccountLogByParams(curAccount, curAccount.getUserId(), money, "活期宝转出购买债权转让", ip,
					BusinessConstants.CUR_OUT_TYPE_TO_BUY_TRANSFER_CAPITAL_DETAIL, -1);
		}
		if (type == BusinessConstants.CUR_OUT_TYPE_TO_BUY_FIRST_BORROW_TRANSFER) {
			curAccountLogService.saveCurAccountLogByParams(curAccount, curAccount.getUserId(), money, "活期宝转出购买直通车转让", ip,
					BusinessConstants.CUR_OUT_TYPE_TO_BUY_FIRST_BORROW_TRANSFER_CAPITAL_DETAIL, -1);
		}
		if (type == BusinessConstants.CUR_OUT_TYPE_TO_BUY_REGULAR_BAO) {
			curAccountLogService.saveCurAccountLogByParams(curAccount, curAccount.getUserId(), money, "活期宝转出购买定期宝", ip,
					BusinessConstants.CUR_OUT_TYPE_TO_BUY_REGULAR_BAO_CAPITAL_DETAIL, -1);
		}
		if (type == BusinessConstants.CUR_OUT_TYPE_TO_BUY_STOCK) {
			curAccountLogService.saveCurAccountLogByParams(curAccount, curAccount.getUserId(), money, "活期宝转出购买有限合伙", ip,
					BusinessConstants.CUR_OUT_TYPE_TO_BUY_STOCK_CAPITAL_DETAIL, -1);
		}
	}

	/**
	 * 
	 * <p>
	 * Description:更新普通账户（转出金额转入可用余额）<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月6日
	 * @param account
	 * @param curOut
	 * @param type
	 *            转出类型
	 * @return
	 * @throws Exception
	 *             Account
	 */
	public Account packageAccount(Account account, CurOut curOut, Integer type) throws Exception {
		account.setTotal(account.getTotal().add(curOut.getAccount()));
		account.setUseMoney(account.getUseMoney().add(curOut.getAccount()));
		if (type == BusinessConstants.CUR_OUT_TYPE_TO_USE_MONEY) { // 转出
			account.setDrawMoney(account.getDrawMoney().add(curOut.getAccount()));
		} else {
			account.setDrawMoney(account.getDrawMoney().add(curOut.getAccount()).subtract(curOut.getNoDrawMoney()));
			account.setNoDrawMoney(account.getNoDrawMoney().add(curOut.getNoDrawMoney()));
		}
		Integer updateAccountCount = baseAccountMapper.updateEntity(account);
		if (null == updateAccountCount || updateAccountCount == 0) {
			throw new AppException("普通账户更新失败，请刷新页面或稍后重试！");
		}
		return account;
	}

	/**
	 * 
	 * <p>
	 * Description:普通账户变更保存日志记录<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月6日
	 * @param account
	 * @param money
	 * @param ip
	 * @param curOutId
	 * @param type
	 *            活期宝转入类型
	 * @throws Exception
	 *             void
	 */
	public void packageAccountLog(Account account, BigDecimal money, String ip, Integer curOutId, Integer type) throws Exception {
		AccountVo accountVo = new AccountVo();
		BeanUtils.copyProperties(account, accountVo);
		if (type == BusinessConstants.CUR_OUT_TYPE_TO_USE_MONEY) {
			accountLogService.saveAccountLogByParams(accountVo, account.getUserId(), money, "活期宝转入可用余额", ip,
					BusinessConstants.CUR_OUT_TYPE_TO_USE_MONEY_COMMON_CAPITAL_DETAIL, Constants.ACCOUNT_LOG_ID_TYPE_CUR_OUT, curOutId, "活期宝转入可用余额");
		}
		if (type == BusinessConstants.CUR_OUT_TYPE_TO_TENDER_BORROW) {
			accountLogService
					.saveAccountLogByParams(accountVo, account.getUserId(), money, "活期宝投标转入", ip,
							BusinessConstants.CUR_OUT_TYPE_TO_TENDER_BORROW_COMMON_CAPITAL_DETAIL, Constants.ACCOUNT_LOG_ID_TYPE_CUR_OUT, curOutId,
							"活期宝投标转入");
		}
		if (type == BusinessConstants.CUR_OUT_TYPE_TO_TENDER_FIRST_BORROW) {
			accountLogService.saveAccountLogByParams(accountVo, account.getUserId(), money, "活期宝开通直通车转入", ip,
					BusinessConstants.CUR_OUT_TYPE_TO_TENDER_FIRST_BORROW_COMMON_CAPITAL_DETAIL, Constants.ACCOUNT_LOG_ID_TYPE_CUR_OUT, curOutId,
					"活期宝开通直通车转入");
		}
		if (type == BusinessConstants.CUR_OUT_TYPE_TO_BUY_TRANSFER) {
			accountLogService.saveAccountLogByParams(accountVo, account.getUserId(), money, "活期宝购买债权转入", ip,
					BusinessConstants.CUR_OUT_TYPE_TO_BUY_TRANSFER_COMMON_CAPITAL_DETAIL, Constants.ACCOUNT_LOG_ID_TYPE_CUR_OUT, curOutId,
					"活期宝购买债权转入");
		}
		if (type == BusinessConstants.CUR_OUT_TYPE_TO_BUY_FIRST_BORROW_TRANSFER) {
			accountLogService.saveAccountLogByParams(accountVo, account.getUserId(), money, "活期宝购买直通车转让转入", ip,
					BusinessConstants.CUR_OUT_TYPE_TO_BUY_FIRST_BORROW_TRANSFER_COMMON_CAPITAL_DETAIL, Constants.ACCOUNT_LOG_ID_TYPE_CUR_OUT,
					curOutId, "活期宝购买直通车转让转入");
		}
		if (type == BusinessConstants.CUR_OUT_TYPE_TO_BUY_REGULAR_BAO) {
			accountLogService.saveAccountLogByParams(accountVo, account.getUserId(), money, "活期宝购买定期宝转入", ip,
					BusinessConstants.CUR_OUT_TYPE_TO_BUY_REGULAR_BAO_COMMON_CAPITAL_DETAIL, Constants.ACCOUNT_LOG_ID_TYPE_CUR_OUT,
					curOutId, "活期宝购买定期宝转入");
		}
		if (type == BusinessConstants.CUR_OUT_TYPE_TO_BUY_STOCK) {
			accountLogService.saveAccountLogByParams(accountVo, account.getUserId(), money, "活期宝购买有限合伙转入", ip,
					BusinessConstants.CUR_OUT_TYPE_TO_BUY_STOCK_COMMON_CAPITAL_DETAIL, Constants.ACCOUNT_LOG_ID_TYPE_CUR_OUT,
					curOutId, "活期宝购买有限合伙转入");
		}
	}

	/**
	 * 
	 * <p>
	 * Description:当需扣除未产生收益的金额部分时，处理转入记录<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月6日
	 * @param userId
	 * @param noUseMoney
	 * @param type
	 *            转出方式类型
	 * @param drawMoney
	 * @param noDrawMoney
	 * @throws Exception
	 *             void
	 */
	public Map<String, BigDecimal> handleCurIn(Integer userId, BigDecimal noUseMoney, Integer type, BigDecimal drawMoney, BigDecimal noDrawMoney)
			throws Exception {
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();

		// 查询可开始产生收益日期大于今天的未开始产生收益的记录，并锁定
		List<CurIn> curInlist = curInMapper.queryCurInListByUserIdAndDateForUpdate(userId, new Date());
		// 查询可开始产生收益日期大于今天的未开始产生收益的记录的有效可扣金额
		BigDecimal effectiveMoney = BigDecimal.ZERO;
		if (type == BusinessConstants.CUR_OUT_TYPE_TO_USE_MONEY) { // 转出到可用余额
			effectiveMoney = curInMapper.queryEffectiveMoneyByUserIdAndDateForUseMoney(userId, new Date());
		} else { // 用于投资转出（包含投标、开通直通车、认购债转、 认购直通车转让、认购定期宝等）
			effectiveMoney = curInMapper.queryEffectiveMoneyByUserIdAndDateForInvest(userId, new Date());
		}
		if (noUseMoney.compareTo(effectiveMoney) == 1) {
			throw new AppException("未产生收益金额不足，无法转出，请刷新页面或稍后重试！");
		}
		for (CurIn curIn : curInlist) {
			if (noUseMoney.compareTo(BigDecimal.ZERO) == 1) {
				// 转出到可用余额
				if (type == BusinessConstants.CUR_OUT_TYPE_TO_USE_MONEY) {
					// 如果是当天转入的记录
					if (DateUtils.format(curIn.getAddtime(), DateUtils.YMD_DASH).equals(DateUtils.format(new Date(), DateUtils.YMD_DASH))) {
						// 如果有效可提金额大于0，则可进行扣除
						if (curIn.getActualDrawMoney().compareTo(BigDecimal.ZERO) == 1) {
							// 对当天转入的记录进行扣除
							Map<String, BigDecimal> resultMap = deductCurInForNow(drawMoney, noDrawMoney, noUseMoney, curIn);
							drawMoney = resultMap.get("drawMoney");
							noDrawMoney = resultMap.get("noDrawMoney");
							noUseMoney = resultMap.get("noUseMoney");
						}
					} else {
						// 如果有效转入金额大于0，则可进行扣除
						if (curIn.getActualMoney().compareTo(BigDecimal.ZERO) == 1) {
							// 对当天之前转入的记录进行扣除
							Map<String, BigDecimal> resultMap = deductCurInForNowAdvance(drawMoney, noDrawMoney, noUseMoney, curIn);
							drawMoney = resultMap.get("drawMoney");
							noDrawMoney = resultMap.get("noDrawMoney");
							noUseMoney = resultMap.get("noUseMoney");
						}
					}
				} else { // 用于投资转出（包含投标、开通直通车、认购债转、 认购直通车转让、认购定期宝等）
					// 如果有效转入金额大于0，则可进行扣除
					if (curIn.getActualMoney().compareTo(BigDecimal.ZERO) == 1) {
						// 对当天之前转入的记录进行扣除
						Map<String, BigDecimal> resultMap = deductCurInForNowAdvance(drawMoney, noDrawMoney, noUseMoney, curIn);
						drawMoney = resultMap.get("drawMoney");
						noDrawMoney = resultMap.get("noDrawMoney");
						noUseMoney = resultMap.get("noUseMoney");
					}
				}
			} else {
				break;
			}
		}

		if (noUseMoney.compareTo(BigDecimal.ZERO) != 0) {
			throw new AppException("未产生收益金额扣除失败，请刷新页面或稍后重试！");
		}
		map.put("drawMoney", drawMoney);
		map.put("noDrawMoney", noDrawMoney);

		return map;
	}

	@Override
	public String turnCurOutForInvest(Integer userId, BigDecimal money, String ip, Integer type) throws Exception {
		int hourNum = Integer.parseInt(DateUtils.format(new Date(), DateUtils.HH_ONLY));
		if (hourNum >= 0 && hourNum < 4) {
			return "系统正在努力结算，无法使用活期宝，请耐心等待一会儿！";
		}
		// 查询该用户活期宝账户并锁定
		CurAccount curAccount = curAccountMapper.selectByUserIdForUpdate(userId);
		// 查询该用户的普通账户并锁定
		Account account = baseAccountMapper.queryByUserIdForUpdate(userId);
		// 根据userId获取当前最大可使用金额（用于投标、认购债权转让、开通直通车、认购直通车转让等）
		BigDecimal maxCurUseMoney = curAccountService.getMaxUseMoneyByNow(userId);
		if (money.compareTo(maxCurUseMoney) == 1) {
			return "活期宝转出金额不足，请刷新页面或稍后重试！";
		}
		// 计算待扣除的可产生收益的金额
		BigDecimal useMoney = BigDecimal.ZERO;
		// 计算待扣除的未产生收益的金额
		BigDecimal noUseMoney = BigDecimal.ZERO;
		// 计算待转出的可提金额
		BigDecimal drawMoney = BigDecimal.ZERO;
		// 计算待转出的受限金额
		BigDecimal noDrawMoney = BigDecimal.ZERO;
		if (money.compareTo(curAccount.getUseMoney()) <= 0) {
			useMoney = money;
		} else {
			useMoney = curAccount.getUseMoney();
			noUseMoney = money.subtract(useMoney);
		}
		// 封装并保存转出记录
		CurOut curOut = packageCurOutRecord(userId, money, useMoney, noUseMoney, ip, type, drawMoney, noDrawMoney);
		// 当需扣除未产生收益的金额，更新转入记录
		if (noUseMoney.compareTo(BigDecimal.ZERO) == 1) {
			// 当需扣除未产生收益的金额部分时，处理转入记录
			Map<String, BigDecimal> map = handleCurIn(userId, noUseMoney, type, drawMoney, noDrawMoney);
			// 更新转出记录中的可提金额和受限金额
			curOut = updateCurOutRecord(curOut, map.get("drawMoney"), map.get("noDrawMoney"));
		}
		// 更新活期宝账户（扣除转出金额）
		curAccount = packageCurAccount(curAccount, money, useMoney, noUseMoney, -1);
		// 保存活期宝账户变更日志记录
		packageCurAccountLog(curAccount, money, ip, type);
		// 更新普通账户（转出金额转入可用余额）
		account = packageAccount(account, curOut, type);
		// 普通账户变更保存日志记录
		packageAccountLog(account, money, ip, curOut.getId(), type);
		return "success";
	}

	@Override
	public CurOut queryCurOutLastByUserIdAndType(Integer type, Integer userId, BigDecimal account) throws Exception {
		return curOutMapper.queryCurOutLastByUserIdAndType(type, userId, account);
	}

	@Override
	public int updateByPrimaryKeySelective(CurOut curOut) {
		return curOutMapper.updateByPrimaryKeySelective(curOut);
	}

	/**
	 * 
	 * <p>
	 * Description:更新转出记录中的可提金额和受限金额<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月19日
	 * @param curOut
	 * @param drawMoney
	 * @param noDrawMoney
	 * @return
	 * @throws Exception
	 *             CurOut
	 */
	public CurOut updateCurOutRecord(CurOut curOut, BigDecimal drawMoney, BigDecimal noDrawMoney) throws Exception {
		curOut.setDrawMoney(drawMoney);
		curOut.setNoDrawMoney(noDrawMoney);
		Integer updateCurOutCount = curOutMapper.updateByPrimaryKey(curOut);
		if (updateCurOutCount == null || updateCurOutCount == 0) {
			throw new AppException("转出失败，请刷新页面或稍后重试！");
		}
		return curOut;
	}

	/**
	 * 
	 * <p>
	 * Description:对当天转入的记录进行扣除<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月21日
	 * @param drawMoney
	 *            总扣除的可提金额
	 * @param noDrawMoney
	 *            总扣除的受限金额
	 * @param noUseMoney
	 *            需扣除的总额金额
	 * @return Map<String,BigDecimal>
	 */
	public Map<String, BigDecimal> deductCurInForNow(BigDecimal drawMoney, BigDecimal noDrawMoney, BigDecimal noUseMoney, CurIn curIn)
			throws Exception {
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();

		BigDecimal actualMoney = curIn.getActualMoney();
		BigDecimal actualDrawMoney = curIn.getActualDrawMoney();
		// 如果要扣的钱大于可提金额
		if (noUseMoney.compareTo(actualDrawMoney) >= 0) {
			// 转出的可提金额
			drawMoney = drawMoney.add(actualDrawMoney);

			// 待扣除的未产生收益的金额 减少
			noUseMoney = noUseMoney.subtract(actualDrawMoney);

			// 更新转入记录
			curIn.setActualMoney(actualMoney.subtract(actualDrawMoney));
			curIn.setActualDrawMoney(BigDecimal.ZERO);
			Integer curInUpdateCount = curInMapper.updateByPrimaryKeySelective(curIn);
			if (curInUpdateCount == null || curInUpdateCount == 0) {
				throw new AppException("转出失败，请刷新页面或稍后重试！");
			}
		} else {
			// 转出的可提金额
			drawMoney = drawMoney.add(noUseMoney);

			// 更新转入记录
			curIn.setActualMoney(actualMoney.subtract(noUseMoney));
			curIn.setActualDrawMoney(actualDrawMoney.subtract(noUseMoney));
			Integer curInUpdateCount = curInMapper.updateByPrimaryKeySelective(curIn);
			if (curInUpdateCount == null || curInUpdateCount == 0) {
				throw new AppException("转出失败，请刷新页面或稍后重试！");
			}

			// 待扣除的未产生收益的金额 减少
			noUseMoney = BigDecimal.ZERO;
		}
		map.put("drawMoney", drawMoney);
		map.put("noDrawMoney", noDrawMoney);
		map.put("noUseMoney", noUseMoney);
		return map;
	}

	/**
	 * 
	 * <p>
	 * Description:对当天之前转入的记录进行扣除<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月21日
	 * @param drawMoney
	 *            总扣除的可提金额
	 * @param noDrawMoney
	 *            总扣除的受限金额
	 * @param noUseMoney
	 *            需扣除的总额金额
	 * @param curIn
	 * @return Map<String,BigDecimal>
	 */
	public Map<String, BigDecimal> deductCurInForNowAdvance(BigDecimal drawMoney, BigDecimal noDrawMoney, BigDecimal noUseMoney, CurIn curIn)
			throws Exception {
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();

		BigDecimal actualMoney = curIn.getActualMoney();
		BigDecimal actualDrawMoney = curIn.getActualDrawMoney();
		BigDecimal actualNoDrawMoney = curIn.getActualNoDrawMoney();

		// 有效金额大于0时，则可进行扣除
		if (actualMoney.compareTo(BigDecimal.ZERO) == 1) {
			// 如果要扣的钱大于有效金额
			if (noUseMoney.compareTo(actualMoney) >= 0) {
				// 转出的可提金额
				drawMoney = drawMoney.add(actualDrawMoney);
				// 转出的受限金额
				noDrawMoney = noDrawMoney.add(actualNoDrawMoney);

				// 待扣除的未产生收益的金额 减少
				noUseMoney = noUseMoney.subtract(actualMoney);

				// 更新转入记录
				curIn.setActualMoney(BigDecimal.ZERO);
				curIn.setActualDrawMoney(BigDecimal.ZERO);
				curIn.setActualNoDrawMoney(BigDecimal.ZERO);
				Integer curInUpdateCount = curInMapper.updateByPrimaryKeySelective(curIn);
				if (curInUpdateCount == null || curInUpdateCount == 0) {
					throw new AppException("转出失败，请刷新页面或稍后重试！");
				}
			} else {
				// 有效受限大于0
				if (actualNoDrawMoney.compareTo(BigDecimal.ZERO) == 1) {
					// 待扣金额大于等于有效受限金额
					if (noUseMoney.compareTo(actualNoDrawMoney) >= 0) {
						// 转出的可提金额
						drawMoney = drawMoney.add(noUseMoney).subtract(actualNoDrawMoney);
						// 转出的受限金额
						noDrawMoney = noDrawMoney.add(actualNoDrawMoney);

						// 更新转入记录
						curIn.setActualMoney(actualMoney.subtract(noUseMoney));
						curIn.setActualDrawMoney(actualMoney.subtract(noUseMoney));
						curIn.setActualNoDrawMoney(BigDecimal.ZERO);
						Integer curInUpdateCount = curInMapper.updateByPrimaryKeySelective(curIn);
						if (curInUpdateCount == null || curInUpdateCount == 0) {
							throw new AppException("转出失败，请刷新页面或稍后重试！");
						}

						// 待扣除的未产生收益的金额 减少
						noUseMoney = BigDecimal.ZERO;
					} else { // 待扣金额小于有效受限金额
						// 转出的受限金额
						noDrawMoney = noDrawMoney.add(noUseMoney);

						// 更新转入记录
						curIn.setActualMoney(actualMoney.subtract(noUseMoney));
						curIn.setActualDrawMoney(actualDrawMoney);
						curIn.setActualNoDrawMoney(actualNoDrawMoney.subtract(noUseMoney));
						Integer curInUpdateCount = curInMapper.updateByPrimaryKeySelective(curIn);
						if (curInUpdateCount == null || curInUpdateCount == 0) {
							throw new AppException("转出失败，请刷新页面或稍后重试！");
						}

						// 待扣除的未产生收益的金额 减少
						noUseMoney = BigDecimal.ZERO;
					}
				} else {
					if (noUseMoney.compareTo(actualDrawMoney) == -1) {
						// 转出的可提金额
						drawMoney = drawMoney.add(noUseMoney);

						// 更新转入记录
						curIn.setActualMoney(actualMoney.subtract(noUseMoney));
						curIn.setActualDrawMoney(actualDrawMoney.subtract(noUseMoney));
						curIn.setActualNoDrawMoney(actualNoDrawMoney);
						Integer curInUpdateCount = curInMapper.updateByPrimaryKeySelective(curIn);
						if (curInUpdateCount == null || curInUpdateCount == 0) {
							throw new AppException("转出失败，请刷新页面或稍后重试！");
						}

						// 待扣除的未产生收益的金额 减少
						noUseMoney = BigDecimal.ZERO;
					}

				}
			}
		}
		map.put("drawMoney", drawMoney);
		map.put("noDrawMoney", noDrawMoney);
		map.put("noUseMoney", noUseMoney);
		return map;
	}
}
