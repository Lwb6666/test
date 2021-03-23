package com.dxjr.portal.member.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.Account;
import com.dxjr.base.entity.AccountLog;
import com.dxjr.base.entity.Member;
import com.dxjr.base.entity.VIPApply;
import com.dxjr.base.entity.VIPAppro;
import com.dxjr.base.mapper.BaseAccountMapper;
import com.dxjr.base.mapper.BaseMemberMapper;
import com.dxjr.base.mapper.BaseVIPApplyMapper;
import com.dxjr.base.mapper.BaseVIPApproMapper;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.mapper.AccountNetValueMapper;
import com.dxjr.portal.account.service.AccountLogService;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.account.vo.DrawMoneyToNoDrawCnd;
import com.dxjr.portal.member.mapper.VIPApproMapper;
import com.dxjr.portal.member.service.IntegralService;
import com.dxjr.portal.member.service.VIPApproService;
import com.dxjr.portal.member.vo.IntegralVo;
import com.dxjr.portal.member.vo.VIPApproCnd;
import com.dxjr.portal.member.vo.VIPApproVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.MD5;
import com.dxjr.utils.exception.AppException;

@Service
public class VIPApproServiceImpl implements VIPApproService {
	public Logger logger = Logger.getLogger(VIPApproServiceImpl.class);

	@Autowired
	private VIPApproMapper vipApproMapper;
	@Autowired
	private BaseMemberMapper baseMemberMapper;

	@Autowired
	private AccountService accountService;

	@Autowired
	private BaseAccountMapper baseAccountMapper;

	@Autowired
	private BaseVIPApproMapper baseVIPApproMapper;

	@Autowired
	private BaseVIPApplyMapper baseVIPApplyMapper;

	@Autowired
	private AccountLogService accountLogService;
	@Autowired
	private IntegralService integralService;
	@Autowired
	private AccountNetValueMapper accountNetValueMapper;

	@Override
	public VIPApproVo queryVIPApproByUserId(Integer memberId) throws Exception {
		VIPApproCnd vipApproCnd = new VIPApproCnd();
		vipApproCnd.setUserId(memberId);
		List<VIPApproVo> list = vipApproMapper.queryVIPApproList(vipApproCnd);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public String saveVIPAppro(String payPassword, Integer userId, HttpServletRequest request) throws Exception {
		String result = "success";
		Member member = baseMemberMapper.queryById(userId);
		// 判断用户可用余额
		AccountVo accountVo = accountService.queryAccountByUserIdForUpdate(userId);
		if (member.getPaypassword() == null || member.getPaypassword().equals("")) {
			return "交易密码未设置，请先设置交易密码！";
		}
		if (null != accountVo) {
			BigDecimal total = accountVo.getTotal();
			BigDecimal useMoney = accountVo.getUseMoney();
			total = total.subtract(BusinessConstants.VIP_MONEY);
			useMoney = useMoney.subtract(BusinessConstants.VIP_MONEY);
			if (total.compareTo(BigDecimal.ZERO) == -1 || useMoney.compareTo(BigDecimal.ZERO) == -1) {
				return "余额不足";
			}
		}
		if (payPassword == null || payPassword.equals("")) {
			return "交易密码不能为空";
		}
		if (!MD5.toMD5(payPassword).equals(member.getPaypassword())) {
			return "交易密码输入错误！";
		}
		// 查询是否已存在VIP认证记录
		VIPApproVo vipApproVo = queryVIPApproByUserId(userId);
		if (vipApproVo != null && vipApproVo.getPassed() == 1) {
			// 到期日期的3个月前
			Date date = DateUtils.monthOffset(vipApproVo.getIndate(), -3);
			String dateTimeStamp = DateUtils.dateTime2TimeStamp(DateUtils.format(date, DateUtils.YMD_HMS));
			String nowTimeStamp = DateUtils.getTime();
			if (Long.parseLong(nowTimeStamp) < Long.parseLong(dateTimeStamp)) {
				return "您已经进行了VIP认证，请勿重复操作！";
			}
		}
		VIPApply vipApply = new VIPApply();
		vipApply.setUserId(userId);
		// 封装VIP认证并保存数据
		packageVipApply(vipApply, request);
		// VIP申请冻结 优化业务去除
		accountVo = vipApplyFreeze(userId, request, accountVo);
		// 增加信用积分
		IntegralVo integralVo = integralService.queryIntegralByUserId(userId);
		integralVo.setCreditIntegral(integralVo.getCreditIntegral() + BusinessConstants.VIP_CREDIT_INTEGRAL);
		integralService.updateCreditLevel(integralVo);
		// 扣除VIP费用
		subtractVipCost(userId, request, accountVo);
		return result;
	}

	/**
	 * <p>
	 * Description:扣除VIP费用<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月28日
	 * @param member
	 * @param request
	 * @param accountVo
	 * @throws Exception AccountVo
	 */
	private void subtractVipCost(Integer userId, HttpServletRequest request, AccountVo accountVo) throws Exception {
		// 更新帐号
		accountVo.setNoUseMoney(accountVo.getNoUseMoney().subtract(BusinessConstants.VIP_MONEY));
		accountVo.setTotal(accountVo.getTotal().subtract(BusinessConstants.VIP_MONEY));
		Account account = new Account();
		BeanUtils.copyProperties(accountVo, account);
		account.setSelfVersion(account.getVersion());
		account.setVersion(account.getVersion() + 1);
		Integer updateCount = baseAccountMapper.updateEntity(account);
		if (null == updateCount || updateCount == 0) {
			throw new AppException("帐号数据已变更,请刷新页面或稍后重试！");
		}
		// 插入日志
		AccountLog accountLog = new AccountLog();
		accountLog.setAddip(HttpTookit.getRealIpAddr(request));
		accountLog.setCollection(accountVo.getCollection());
		accountLog.setUseMoney(accountVo.getUseMoney());
		accountLog.setNoUseMoney(accountVo.getNoUseMoney());
		accountLog.setTotal(accountVo.getTotal());
		accountLog.setFirstBorrowUseMoney(accountVo.getFirstBorrowUseMoney());
		accountLog.setNoDrawMoney(accountVo.getNoDrawMoney());
		accountLog.setDrawMoney(accountVo.getDrawMoney());
		accountLog.setMoney(BusinessConstants.VIP_MONEY);// 交易金额
		accountLog.setRemark("vip认证通过");
		accountLog.setToUser(userId);// TODO 暂时设置为本人
		accountLog.setUserId(accountVo.getUserId());
		accountLog.setType("vip_cost");
		accountLogService.insertAccountLog(accountLog);

		// 判断用户的可提金额是否大于净值额度，如果大于，转入受限金额
		DrawMoneyToNoDrawCnd drawMoneyToNoDrawCnd = new DrawMoneyToNoDrawCnd();
		drawMoneyToNoDrawCnd.setUserid(userId);
		drawMoneyToNoDrawCnd.setNetmoneytype(BusinessConstants.NET_TYPE_VIP_COST);
		drawMoneyToNoDrawCnd.setAddip(HttpTookit.getRealIpAddr(request));
		drawMoneyToNoDrawCnd.setAccountlogType("net_draw_to_nodraw_vip_cost");
		drawMoneyToNoDrawCnd.setAccountlogRemark("VIP扣费之后,可提金额大于净值额度，可提金额转入受限金额。");
		accountNetValueMapper.dealDrawmoneyToNodraw(drawMoneyToNoDrawCnd);
	}

	/**
	 * <p>
	 * Description:VIP申请冻结 优化业务去除<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月28日
	 * @param member
	 * @param request
	 * @param accountVo
	 * @throws Exception AccountVo
	 */
	private AccountVo vipApplyFreeze(Integer userId, HttpServletRequest request, AccountVo accountVo) throws Exception {
		// 更新帐号
		accountVo.setNoUseMoney(accountVo.getNoUseMoney().add(BusinessConstants.VIP_MONEY));
		accountVo.setUseMoney(accountVo.getUseMoney().subtract(BusinessConstants.VIP_MONEY));
		if (accountVo.getNoDrawMoney().compareTo(BigDecimal.ZERO) == 1) {
			if (accountVo.getNoDrawMoney().compareTo(BusinessConstants.VIP_MONEY) >= 0) {
				accountVo.setNoDrawMoney(accountVo.getNoDrawMoney().subtract(BusinessConstants.VIP_MONEY));
			} else {
				BigDecimal remaind = BusinessConstants.VIP_MONEY.subtract(accountVo.getNoDrawMoney());
				accountVo.setDrawMoney(accountVo.getDrawMoney().subtract(remaind));
				accountVo.setNoDrawMoney(BigDecimal.ZERO);
			}
		} else {
			accountVo.setDrawMoney(accountVo.getDrawMoney().subtract(BusinessConstants.VIP_MONEY));
		}
		Account account = new Account();
		BeanUtils.copyProperties(accountVo, account);
		baseAccountMapper.updateEntity(account);
		// 插入日志
		AccountLog accountLog = new AccountLog();
		accountLog.setAddip(HttpTookit.getRealIpAddr(request));
		accountLog.setCollection(accountVo.getCollection());
		accountLog.setUseMoney(accountVo.getUseMoney());
		accountLog.setNoUseMoney(accountVo.getNoUseMoney());
		accountLog.setTotal(accountVo.getTotal());
		accountLog.setFirstBorrowUseMoney(accountVo.getFirstBorrowUseMoney());
		accountLog.setNoDrawMoney(accountVo.getNoDrawMoney());
		accountLog.setDrawMoney(accountVo.getDrawMoney());
		accountLog.setMoney(BusinessConstants.VIP_MONEY);// 交易金额
		accountLog.setRemark("VIP申请冻结");
		accountLog.setToUser(userId);// TODO 暂时设置为本人
		accountLog.setUserId(accountVo.getUserId());
		accountLog.setType("vip_cost");
		accountLogService.insertAccountLog(accountLog);
		return accountVo;
	}

	/**
	 * <p>
	 * Description:封装实名认证并保存数据<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月25日
	 * @param vipAppro
	 * @param request
	 * @throws Exception void
	 */
	private void packageVipApply(VIPApply vipApply, HttpServletRequest request) throws Exception {
		vipApply.setAddTime(new Date());
		vipApply.setAddIp(HttpTookit.getRealIpAddr(request));
		vipApply.setPassed(Constants.VIP_APPRO_PASSED_YES);
		vipApply.setApprover("-1");
		vipApply.setApproveTime(new Date());
		vipApply.setApproveRemark("系统自动审核通过");
		vipApply.setIsFee(1);
		final Integer platform = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getPlatform();
		vipApply.setPlatform(platform);

		// 查询是否已存在VIP认证记录
		VIPApproVo vipApproVo = queryVIPApproByUserId(vipApply.getUserId());
		if (vipApproVo != null) {
			if (vipApproVo.getPassed() == 1) { // 续费
				vipApply.setType(1);
				Date new_indate = DateUtils.yearOffset(vipApproVo.getIndate(), 1);
				// 续费后的有效截止日期
				vipApply.setIndate(new_indate);
				vipApply.setFee(BusinessConstants.VIP_MONEY);

				vipApproVo.setIndate(new_indate);
				vipApproVo.setIsFee(1);
				vipApproVo.setApproveTime(DateUtils.getTime());
				vipApproVo.setFee(BusinessConstants.VIP_MONEY.add(vipApproVo.getFee()));
			} else if (vipApproVo.getPassed() == -1) { // 重新申请VIP
				vipApply.setType(2);
				Date new_indate = DateUtils.yearOffset(new Date(), 1);
				// 重新申请后的有效截止日期
				vipApply.setIndate(new_indate);
				vipApply.setFee(BusinessConstants.VIP_MONEY);
				vipApproVo.setIndate(new_indate);
				vipApproVo.setPassed(1); // 生效
				vipApproVo.setIsFee(Constants.VIP_APPRO_PASSED_YES);
				vipApproVo.setApproveTime(DateUtils.getTime());
				vipApproVo.setFee(BusinessConstants.VIP_MONEY.add(vipApproVo.getFee()));
			}
		} else { // 首次申请VIP
			vipApply.setType(0);
			Date new_indate = DateUtils.yearOffset(new Date(), 1);
			// 首次申请后的有效截止日期
			vipApply.setIndate(new_indate);
			vipApply.setFee(BusinessConstants.VIP_MONEY);

			vipApproVo = new VIPApproVo();
			vipApproVo.setUserId(vipApply.getUserId());
			vipApproVo.setIndate(new_indate);
			vipApproVo.setPassed(1); // 生效
			vipApproVo.setAddTime(DateUtils.getTime());
			vipApproVo.setAddIp(HttpTookit.getRealIpAddr(request));
			vipApproVo.setPassed(Constants.VIP_APPRO_PASSED_YES);
			vipApproVo.setApprover("-1");
			vipApproVo.setApproveTime(DateUtils.getTime());
			vipApproVo.setApproveRemark("系统自动审核通过");
			vipApproVo.setServiceNum(vipApply.getServiceNum());
			vipApproVo.setRemark(vipApply.getRemark());
			vipApproVo.setIsFee(1);
			vipApproVo.setFee(BusinessConstants.VIP_MONEY);
		}
		VIPAppro vipAppro = new VIPAppro();
		BeanUtils.copyProperties(vipApproVo, vipAppro);
		vipAppro.setPlatform(platform);
		// 保存申请记录
		baseVIPApplyMapper.insertEntity(vipApply);
		// 更新
		if (null != vipApproVo.getId()) {
			baseVIPApproMapper.updateEntity(vipAppro);
			// 新增
		} else {
			baseVIPApproMapper.insertEntity(vipAppro);
		}
	}
}
