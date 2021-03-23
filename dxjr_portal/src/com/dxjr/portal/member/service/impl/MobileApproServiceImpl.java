package com.dxjr.portal.member.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxjr.base.entity.Account;
import com.dxjr.base.entity.Member;
import com.dxjr.base.entity.MobileAppro;
import com.dxjr.base.entity.SmsRecord;
import com.dxjr.base.mapper.BaseAccountMapper;
import com.dxjr.base.mapper.BaseMobileApproMapper;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.AccountLogService;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.account.vo.AccountLogCnd;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.lottery.service.LotteryChanceInfoService;
import com.dxjr.portal.member.mapper.ApproModifyLogMapper;
import com.dxjr.portal.member.mapper.MobileApproMapper;
import com.dxjr.portal.member.service.MemberRegisterService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.member.service.RealNameApproService;
import com.dxjr.portal.member.vo.ApproModifyLog;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.MobileApproCnd;
import com.dxjr.portal.member.vo.MobileApproVo;
import com.dxjr.portal.member.vo.RealNameApproVo;
import com.dxjr.portal.sms.service.SmsSendService;
import com.dxjr.portal.sms.service.SmsTemplateService;
import com.dxjr.portal.sms.vo.SmsTemplateVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.util.service.PhoneService;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.SmsUtil;
import com.dxjr.utils.exception.AppException;

@Service
public class MobileApproServiceImpl implements MobileApproService {
	public Logger logger = Logger.getLogger(MobileApproServiceImpl.class);

	@Autowired
	private MobileApproMapper mobileApproMapper;
	@Autowired
	private PhoneService phoneService;
	@Autowired
	private SmsTemplateService smsTemplateService;
	@Autowired
	private SmsSendService smsSendService;
	@Autowired
	private BaseMobileApproMapper baseMobileApproMapper;
	@Autowired
	private RealNameApproService realNameApproService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private BaseAccountMapper baseAccountMapper;
	@Autowired
	private AccountLogService accountLogService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private ApproModifyLogMapper approModifyLogMapper;
	@Autowired
	private MemberRegisterService memberRegisterService;
	@Autowired
	private LotteryChanceInfoService lotteryChanceInfoService;

	@Override
	public MobileApproVo queryMobileApproByUserId(Integer memberId) throws Exception {
		MobileApproCnd mobileApproCnd = new MobileApproCnd();
		mobileApproCnd.setUserId(memberId);
		List<MobileApproVo> list = mobileApproMapper.queryMobileApproList(mobileApproCnd);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String sendMobileApprValidate(String mobile, HttpServletRequest request, String userName, String modelName) throws Exception {
		String result = "success";
		MobileApproCnd mobileApproCnd = new MobileApproCnd();
		mobileApproCnd.setMobileNum(mobile);
		Integer repeatCount = mobileApproMapper.queryRepeatMobileApproCount(mobileApproCnd);
		if (repeatCount > 0) {
			return "手机号已存在,请重新输入！";
		}
		// 获得验证码并存入缓存
		String randCode = phoneService.querySmsValidate(mobile, modelName);
		// 获取短信模板
		SmsTemplateVo smsTempale = smsTemplateService.querySmsTemplateByType(BusinessConstants.SMS_TEMPLATE_TYPE_MODIFY_MOBILE_REQUEST);

		SmsRecord smsRecord = new SmsRecord();
		smsRecord.setAddip(HttpTookit.getRealIpAddr(request));
		smsRecord.setAddtime(new Date());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", userName);
		map.put("time", DateUtils.format(new Date(), DateUtils.YMD_HMS));
		map.put("randCode", randCode);

		String content = SmsUtil.generateParamContent(smsTempale.getContent(), map);
		// 发送短信
		smsRecord.setContent(content);
		smsRecord.setMobile(mobile);
		smsRecord.setSmsType(BusinessConstants.SMS_TEMPLATE_TYPE_MODIFY_MOBILE_REQUEST);
		smsRecord.setSendStatus(BusinessConstants.SMS_SEND_STATUS_WAIT);
		smsRecord.setLastmodifytime(new Date());
		smsRecord.setSmsTemplateType(BusinessConstants.SMS_TEMPLATE_TYPE_MODIFY_MOBILE_REQUEST);
		result = smsSendService.saveSmsByZucp(smsRecord);

		/*
		 * // 输入验证码的时候就写入 BaseController shiro = new ShiroUser(); shiroUser =
		 * shiroUser.currentUser(); MemberVo memberVo = new MemberVo();
		 * memberVo.setId(shiroUser.getUserId()); MobileApproVo mobileApproVo =
		 * this.queryMobileApproByUserId(memberVo.getId()); // 更新或修改手机认证信息
		 * packageMobileApproCode(memberVo, mobile, randCode, request,
		 * mobileApproVo);
		 */

		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String sendMobileApprValidate(String mobile, HttpServletRequest request, String userName, String modelName, Integer smsTemplateType) throws Exception {
		String result = "success";
		// 获得验证码并存入缓存
		String randCode = phoneService.querySmsValidate(mobile, modelName);
		// 获取短信模板
		SmsTemplateVo smsTempale = smsTemplateService.querySmsTemplateByType(smsTemplateType);

		SmsRecord smsRecord = new SmsRecord();
		smsRecord.setAddip(HttpTookit.getRealIpAddr(request));
		smsRecord.setAddtime(new Date());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", userName);
		map.put("time", DateUtils.format(new Date(), DateUtils.YMD_HMS));
		map.put("randCode", randCode);

		String content = SmsUtil.generateParamContent(smsTempale.getContent(), map);
		// 发送短信
		smsRecord.setContent(content);
		smsRecord.setMobile(mobile);
		smsRecord.setSmsType(smsTemplateType);
		smsRecord.setSendStatus(BusinessConstants.SMS_SEND_STATUS_WAIT);
		smsRecord.setLastmodifytime(new Date());
		smsRecord.setSmsTemplateType(smsTemplateType);
		result = smsSendService.saveSmsByZucp(smsRecord);
		return result;
	}

	@Override
	public String saveMobileAppro(MemberVo memberVo, String mobile, String activeCode, HttpServletRequest request, String modelName) throws Exception {
		String result = "success";
		MobileApproVo mobileApproVo = this.queryMobileApproByUserId(memberVo.getId());
		if (mobileApproVo != null && mobileApproVo.getPassed() == 1) {
			return "手机认证已通过，请勿重复操作！";
		}
		// 验证数据是否正确
		result = validateSaveMobileAppro(memberVo, mobile, activeCode, mobileApproVo, modelName);
		if (result != "success") {
			return result;
		}

		// 未已激活，成为了正式用户 当前用户身份 0：正式身份 -1：游客身份
		if (null != memberVo && BusinessConstants.VISITOR_OFFICIAL != memberVo.getType()) {
			// 将当前用户身份设置为正式用户
			Member member = new Member();
			member.setType(BusinessConstants.VISITOR_OFFICIAL);
			member.setId(memberVo.getId());
			memberService.updateEntity(member);// 将用户状态更新为正式用户
		}

		// 更新或修改手机认证信息
		packageMobileAppro(memberVo, mobile, activeCode, HttpTookit.getRealIpAddr(request), mobileApproVo);
		// 判断实名认证是否通过决定是否发放奖励
		RealNameApproVo realNameApproVo = realNameApproService.queryRealNameApproByUserId(memberVo.getId());
		if (null != realNameApproVo && null != realNameApproVo.getIsPassed() && realNameApproVo.getIsPassed() == 1) {
			// 手机与实名认证通过，发放奖励
			this.saveRealNameMobileAward(memberVo.getId(), HttpTookit.getRealIpAddr(request));
		}
		// 获取短信模板
		SmsTemplateVo smsTempale = smsTemplateService.querySmsTemplateByType(BusinessConstants.SMS_TEMPLATE_TYPE_MODIFY_MOBILE_SUCCESS);

		SmsRecord smsRecord = new SmsRecord();
		smsRecord.setAddip(request.getRemoteAddr());
		smsRecord.setAddtime(new Date());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", memberVo.getUsername());
		String content = SmsUtil.generateParamContent(smsTempale.getContent(), map);
		// 发送短信
		smsRecord.setContent(content);
		smsRecord.setMobile(mobile);
		smsRecord.setSmsType(BusinessConstants.SMS_TEMPLATE_TYPE_MODIFY_MOBILE_SUCCESS);
		smsRecord.setSendStatus(BusinessConstants.SMS_SEND_STATUS_WAIT);
		smsRecord.setLastmodifytime(new Date());
		smsRecord.setSmsTemplateType(BusinessConstants.SMS_TEMPLATE_TYPE_MODIFY_MOBILE_SUCCESS);
		smsSendService.saveSmsByZucp(smsRecord);
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String sendMobileCodeAfterAddBankCard(MemberVo memberVo, String mobile, HttpServletRequest request, String modelName, Integer smsTemplateType) throws Exception {
		String result = BusinessConstants.SUCCESS;

		// 获取短信模板
		SmsTemplateVo smsTempale = smsTemplateService.querySmsTemplateByType(smsTemplateType);

		SmsRecord smsRecord = new SmsRecord();
		smsRecord.setAddip(request.getRemoteAddr());
		smsRecord.setAddtime(new Date());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", memberVo.getUsername());
		String content = SmsUtil.generateParamContent(smsTempale.getContent(), map);
		// 发送短信
		smsRecord.setContent(content);
		smsRecord.setMobile(mobile);
		smsRecord.setSmsType(smsTemplateType);
		smsRecord.setSendStatus(BusinessConstants.SMS_SEND_STATUS_WAIT);
		smsRecord.setLastmodifytime(new Date());
		smsRecord.setSmsTemplateType(smsTemplateType);
		smsSendService.saveSmsByZucp(smsRecord);
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String verifyMobileCodeBeforeAddBankCard(MemberVo memberVo, String mobile, String activeCode, HttpServletRequest request, String modelName, Integer smsTemplateType) throws Exception {
		String result = "success";
		MobileApproVo mobileApproVo = this.queryMobileApproByUserId(memberVo.getId());
		// 验证数据是否正确
		result = validateSaveMobileAppro(memberVo, mobile, activeCode, mobileApproVo, modelName);
		if (result != "success") {
			return result;
		}

		return result;
	}

	@Override
	public String saveMobileAppro(MemberVo memberVo, String mobile, String activeCode, HttpServletRequest request, String modelName, Integer smsTemplateType) throws Exception {
		String result = "success";
		MobileApproVo mobileApproVo = this.queryMobileApproByUserId(memberVo.getId());
		// 验证数据是否正确
		result = validateSaveMobileAppro(memberVo, mobile, activeCode, mobileApproVo, modelName);
		if (result != "success") {
			return result;
		}
		// 更新或修改手机认证信息
		packageMobileAppro(memberVo, mobile, activeCode, HttpTookit.getRealIpAddr(request), mobileApproVo);
		// 判断实名认证是否通过决定是否发放奖励
		RealNameApproVo realNameApproVo = realNameApproService.queryRealNameApproByUserId(memberVo.getId());
		if (null != realNameApproVo && null != realNameApproVo.getIsPassed() && realNameApproVo.getIsPassed() == 1) {
			// 手机与实名认证通过，发放奖励
			this.saveRealNameMobileAward(memberVo.getId(), HttpTookit.getRealIpAddr(request));
		}
		// 获取短信模板
		SmsTemplateVo smsTempale = smsTemplateService.querySmsTemplateByType(smsTemplateType);

		SmsRecord smsRecord = new SmsRecord();
		smsRecord.setAddip(request.getRemoteAddr());
		smsRecord.setAddtime(new Date());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", memberVo.getUsername());
		String content = SmsUtil.generateParamContent(smsTempale.getContent(), map);
		// 发送短信
		smsRecord.setContent(content);
		smsRecord.setMobile(mobile);
		smsRecord.setSmsType(smsTemplateType);
		smsRecord.setSendStatus(BusinessConstants.SMS_SEND_STATUS_WAIT);
		smsRecord.setLastmodifytime(new Date());
		smsRecord.setSmsTemplateType(smsTemplateType);
		smsSendService.saveSmsByZucp(smsRecord);
		return result;
	}

	/**
	 * <p>
	 * Description:手机与实名认证通过，发放奖励<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月30日
	 * @param userId
	 * @return Account
	 */
	@Override
	public void saveRealNameMobileAward(int userId, String ip) throws Exception {

		// 12月活动规则改变,此处不再奖励10元(1417363200000L = 2014-12-01)
		if (System.currentTimeMillis() >= 1417363200000L) {
			return;
		}

		// 查询是否已发放过奖励
		AccountLogCnd accountLogCnd = new AccountLogCnd();
		accountLogCnd.setUserId(userId);
		accountLogCnd.setType("web_recharge");
		accountLogCnd.setRemark("实名与手机认证通过奖励");
		Integer count = accountLogService.queryAccountLogCount(accountLogCnd);
		// 已发放直接返回
		if (count > 0) {
			return;
		}
		accountLogCnd.setRemark("邮箱、手机及实名认证通过奖励");
		count = accountLogService.queryAccountLogCount(accountLogCnd);
		// 已发放直接返回
		if (count > 0) {
			return;
		}
		AccountVo accountVo = accountService.queryAccountByUserId(userId);
		// 更新帐号
		accountVo.setTotal(accountVo.getTotal().add(BusinessConstants.APPRO_PASS_MONEY));
		accountVo.setUseMoney(accountVo.getUseMoney().add(BusinessConstants.APPRO_PASS_MONEY));
		accountVo.setNoDrawMoney(accountVo.getNoDrawMoney().add(BusinessConstants.APPRO_PASS_MONEY));
		Account account = new Account();
		BeanUtils.copyProperties(accountVo, account);
		account.setSelfVersion(account.getVersion());
		account.setVersion(account.getVersion() + 1);
		Integer updateCount = baseAccountMapper.updateEntity(account);
		if (null == updateCount || updateCount == 0) {
			throw new AppException("帐号数据已变更,请刷新页面或稍后重试！");
		}
		// 插入日志
		accountLogService.saveAccountLogByParams(accountVo, userId, BusinessConstants.APPRO_PASS_MONEY, "邮箱、手机及实名认证通过奖励", ip, "web_recharge", null, null, null);
	}

	/**
	 * <p>
	 * Description:更新或修改手机认证信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月30日
	 * @param member
	 * @param mobile
	 * @param activeCode
	 * @param request
	 * @param mobileApproVo
	 * @throws Exception void
	 */
	private void packageMobileAppro(MemberVo memberVo, String mobile, String activeCode, String ip, MobileApproVo mobileApproVo) throws Exception {
		// 要修改的实体
		MobileAppro mobileAppro = new MobileAppro();
		// 更新或修改手机认证信息
		Integer platform = null;
		if (memberVo.getPlatform() != null && memberVo.getIsModify() == null) {
			platform = memberVo.getPlatform();
		} else if (memberVo.getIsModify() == null) {
			platform = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getPlatform();
		} else {
			platform = null;
		}

		if (null != mobileApproVo) {
			if (!mobileApproVo.getMobileNum().equals(mobile) || mobileApproVo.getPassed() != Constants.YES) {
				mobileApproVo.setMobileNum(mobile);
				mobileApproVo.setPassed(Constants.YES);
				mobileApproVo.setRandCode(activeCode);
				BeanUtils.copyProperties(mobileApproVo, mobileAppro);
				mobileAppro.setPlatform(platform);
				if (null == mobileAppro.getApproTime() || "".equals(mobileAppro.getApproTime().trim())) {
					mobileAppro.setApproTime(DateUtils.getTime());
				}
				baseMobileApproMapper.updateEntity(mobileAppro);
				if (mobileAppro.getPassed() != null && mobileAppro.getPassed() == 1) {
					// 发放新手注奖抽奖机会
					if (lotteryChanceInfoService.selectCountByCodeAndUserId(BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_NEW_REGISTER, memberVo.getId()) == 0
							&& approModifyLogMapper.queryCountForUpdateMObile(memberVo.getId()) == 0) {
						// 新增“新手注册奖”抽奖机会信息记录
						lotteryChanceInfoService.insertLotteryChanceInfoByCode(memberVo.getId(), BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_NEW_REGISTER);
					}
				}
			}
		} else {
			mobileAppro.setAddIp(ip);
			mobileAppro.setAddTime(DateUtils.getTime());
			mobileAppro.setPassed(Constants.YES);
			mobileAppro.setMobileNum(mobile);
			mobileAppro.setRandCode(activeCode);
			mobileAppro.setUserId(memberVo.getId());
			mobileAppro.setApproTime(DateUtils.getTime());
			// 第一次认证通时记录操作客户端
			mobileAppro.setPlatform(platform);
			baseMobileApproMapper.insertEntity(mobileAppro);
			if (mobileAppro.getPassed() != null && mobileAppro.getPassed().intValue() == Constants.YES.intValue()) {
				// 发放新手注奖抽奖机会
				if (lotteryChanceInfoService.selectCountByCodeAndUserId(BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_NEW_REGISTER, memberVo.getId()) == 0
						&& approModifyLogMapper.queryCountForUpdateMObile(memberVo.getId()) == 0) { // 注册时间大于等于定义的新手起始时间
					// 新增“新手注册奖”抽奖机会信息记录
					lotteryChanceInfoService.insertLotteryChanceInfoByCode(memberVo.getId(), BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_NEW_REGISTER);
				}
			}
		}
		// 修改邮箱验证通过时不记录流水
		if (memberVo != null && (memberVo.getIsModify() == null || memberVo.getIsModify() != true)) {
			// 生成流水日志
			ApproModifyLog apprModifyLog = ApproModifyLog.createApproModifyVo(platform, memberVo.getId(), ip, 1, "手机认证通过", mobile, BusinessConstants.APPRO_MODIFY_LOG_TYPE_MOBILE);
			approModifyLogMapper.insertEntity(apprModifyLog);
		}
		memberVo.setIsModify(null);

	}

	/**
	 * <p>
	 * Description:验证手机认证数据是否正确 <br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月30日
	 * @param member
	 * @param mobile
	 * @param activeCode
	 * @return String
	 */
	public String validateSaveMobileAppro(MemberVo memberVo, String mobile, String activeCode, MobileApproVo mobileApproVo, String modelName) throws Exception {
		String result = "success";
		// 验证验证码是否正确
		String valiateResult = phoneService.compareSmsValidate(mobile, activeCode, modelName);
		if (valiateResult != "success") {
			return valiateResult;
		}
		MobileApproCnd mobileApproCnd = new MobileApproCnd();
		mobileApproCnd.setMobileNum(mobile);
		// 更新手机号去除当前用户判断
		if (null != mobileApproVo) {
			mobileApproCnd.setUserId(mobileApproVo.getUserId());
		}
		Integer repeatCount = mobileApproMapper.queryRepeatMobileApproCount(mobileApproCnd);
		if (repeatCount > 0) {
			return "手机号已存在,请重新输入！";
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String sendMobailMessageActiveCode(HttpServletRequest request, Member member) throws Exception {
		String result = BusinessConstants.SUCCESS;
		// 通过用户名查询上一步的信息
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setUsername(member.getUsername());
		MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
		// 0：正式身份 -1：游客身份
		if (null != memberVo && BusinessConstants.VISITOR_OFFICIAL == memberVo.getType()) {
			result = "当前用户已通过手机认证，请核对！";
			return result;
		}

		String userName = (member == null) ? "" : member.getUsername();
		String mobile = request.getParameter("mobile");

		if (StringUtils.isEmpty(userName)) {
			result = "发送失败，用户名不能为空。";
			return result;
		}
		if (StringUtils.isEmpty(mobile)) {
			result = "发送失败，用户手机号不能为空。";
			return result;
		}

		/** 除当前用户，判断手机号是否存在 */
		MobileApproCnd mobileApproCnd = new MobileApproCnd();
		mobileApproCnd.setMobileNum(mobile);
		// 更新手机号去除当前用户判断
		if (null != memberVo && memberVo.getId() != null) {
			mobileApproCnd.setUserId(memberVo.getId());
		}
		Integer repeatCount = mobileApproMapper.queryRepeatMobileApproCount(mobileApproCnd);
		if (repeatCount > 0) {
			result = "手机号已存在,请重新输入！";
			return result;
		}
		this.packageMobileApproCode(memberVo, mobile, null, request);
		try {
			// 指定短信模板
			result = this.sendMobileApprValidate(mobile, request, userName, BusinessConstants.MOBILE_APPRO_FUNCTION, BusinessConstants.SMS_TEMPLATE_TYPE_REGIST_MOBILE_CODE);
		} catch (Exception e) {
			result = "网络连接异常，请刷新页面或稍后重试!";
			return result;
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String verificationMobailActiveCode(HttpServletRequest request, Member member, String mobileParam, String activeCodeParam) {
		String result = BusinessConstants.SUCCESS;
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setUsername(member.getUsername());
		try {
			MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
			// 验证用户是否已经通过认证
			// 0：正式身份 -1：游客身份
			if (null != memberVo && BusinessConstants.VISITOR_OFFICIAL == memberVo.getType()) {
				result = "当前用户已通过手机认证，请核对！";
				return result;
			}

			result = this.saveMobileAppro(memberVo, mobileParam, activeCodeParam, request, BusinessConstants.MOBILE_APPRO_FUNCTION, BusinessConstants.SMS_TEMPLATE_TYPE_REGISTER_SUCCESS);
			if (result != "success") {
				return result;
			}
			// 将当前用户身份设置为正式用户
			member.setType(BusinessConstants.VISITOR_OFFICIAL);
			member.setId(memberVo.getId());
			memberService.updateEntity(member);
		} catch (AppException ae) {
			result = "验证过程中出现异常，请稍后";
			return result;
		} catch (Exception e) {
			result = "网络连接异常，请刷新页面或稍后重试!";
			return result;
		}
		return result;
	}

	// 输入验证码的时候插入手机号码
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void packageMobileApproCode(MemberVo memberVo, String mobile, String activeCode, HttpServletRequest request) throws Exception {

		MobileApproVo mobileApproVo = this.queryMobileApproByUserId(memberVo.getId());
		// 要修改的实体
		MobileAppro mobileAppro = new MobileAppro();
		// 更新或修改手机认证信息
		activeCode = "111111";  //数据库里这个字段不能为null  暂用11111代替
		Integer platform = null;
		if (memberVo.getPlatform() != null) {
			platform = memberVo.getPlatform();
		} else {
			platform = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getPlatform();
		}

		if (null != mobileApproVo) {
			mobileApproVo.setMobileNum(mobile);
			mobileApproVo.setPassed(Constants.NO);
			mobileApproVo.setRandCode(activeCode);
			BeanUtils.copyProperties(mobileApproVo, mobileAppro);
			mobileAppro.setPlatform(platform);
			baseMobileApproMapper.updateEntity(mobileAppro);
			// 生成流水日志
			ApproModifyLog apprModifyLog = ApproModifyLog.createApproModifyVo(platform, memberVo.getId(), HttpTookit.getRealIpAddr(request), 0, "更新手机，待审核", mobile,
					BusinessConstants.APPRO_MODIFY_LOG_TYPE_MOBILE);
			approModifyLogMapper.insertEntity(apprModifyLog);
		} else {
			mobileAppro.setAddIp(HttpTookit.getRealIpAddr(request));
			mobileAppro.setAddTime(DateUtils.getTime());
			mobileAppro.setPassed(Constants.NO);
			mobileAppro.setMobileNum(mobile);
			mobileAppro.setRandCode(activeCode);
			mobileAppro.setUserId(memberVo.getId());
			mobileAppro.setPlatform(platform);
			baseMobileApproMapper.insertEntity(mobileAppro);
			// 生成流水日志
			ApproModifyLog apprModifyLog = ApproModifyLog.createApproModifyVo(platform, memberVo.getId(), HttpTookit.getRealIpAddr(request), 0, "新增手机，待审核", mobile,
					BusinessConstants.APPRO_MODIFY_LOG_TYPE_MOBILE);
			approModifyLogMapper.insertEntity(apprModifyLog);
		}
	}

	// 输入验证码的时候插入手机号码
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void AutoGenerateMobileAppro(MemberVo memberVo, String mobile, String activeCode, String ip) throws Exception {

		if (this.isMobileNumUsed(mobile)) {
			throw new RuntimeException("手机号已被使用！");
		}
		// 要修改的实体
		MobileAppro mobileAppro = new MobileAppro();
		//activeCode = "111111";
		// 更新或修改手机认证信息
		mobileAppro.setAddIp(ip);
		mobileAppro.setAddTime(DateUtils.getTime());
		mobileAppro.setPassed(Constants.YES);
		mobileAppro.setMobileNum(mobile);
		mobileAppro.setRandCode(activeCode);
		mobileAppro.setUserId(memberVo.getId());
		mobileAppro.setPlatform(memberVo.getPlatform());
		mobileAppro.setApproTime(DateUtils.getTime());
		baseMobileApproMapper.insertEntity(mobileAppro);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public String AutoApproGenerateMobile(MemberVo memberVo, String mobile, String activeCode, String ip) throws Exception {
		String result = "success";
		MobileApproVo mobileApproVo = this.queryMobileApproByUserId(memberVo.getId());
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setId(memberVo.getId());
		MemberVo currMemberVo = memberService.queryMemberByCnd(memberCnd);
		// 未已激活，成为了正式用户 当前用户身份 0：正式身份 -1：游客身份
		if (null != currMemberVo && BusinessConstants.VISITOR_OFFICIAL != currMemberVo.getType()) {
			// 将当前用户身份设置为正式用户
			Member member = new Member();
			member.setType(BusinessConstants.VISITOR_OFFICIAL);
			member.setId(currMemberVo.getId());
			memberService.updateEntity(member);// 将用户状态更新为正式用户
		}

		// 更新或修改手机认证信息
		packageMobileAppro(memberVo, mobile, activeCode, ip, mobileApproVo);
		// 判断实名认证是否通过决定是否发放奖励
		RealNameApproVo realNameApproVo = realNameApproService.queryRealNameApproByUserId(memberVo.getId());
		if (null != realNameApproVo && null != realNameApproVo.getIsPassed() && realNameApproVo.getIsPassed() == 1) {
			// 手机与实名认证通过，发放奖励
			this.saveRealNameMobileAward(memberVo.getId(), ip);
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * Description:将自动生成的用户名和密码通过手机发送给用户<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @param memberVo
	 * @param mobile
	 * @param request
	 * @throws Exception void
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void sendAutoApproGenerateMemberInfo(MemberVo memberVo, String mobile, String ip) throws Exception {
		// 获取短信模板
		SmsTemplateVo smsTempale = smsTemplateService.querySmsTemplateByType(Integer.valueOf(20));

		SmsRecord smsRecord = new SmsRecord();
		smsRecord.setAddip(ip);
		smsRecord.setAddtime(new Date());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", memberVo.getUsername());
		map.put("logpassword", memberVo.getLogpassword());
		String content = SmsUtil.generateParamContent(smsTempale.getContent(), map);
		// 发送短信
		smsRecord.setContent(content);
		smsRecord.setMobile(mobile);
		smsRecord.setSmsType(Integer.valueOf(20));
		smsRecord.setSendStatus(BusinessConstants.SMS_SEND_STATUS_WAIT);
		smsRecord.setLastmodifytime(new Date());
		smsRecord.setSmsTemplateType(20);
		smsSendService.saveSmsByZucp(smsRecord);
	}

	@Override
	public List<String> querySendSmsMobile() {
		return mobileApproMapper.querySendSmsMobile();
	}

	@Override
	public Boolean isMobileNumUsed(String mobileNum) {
		if (null != mobileNum && !"".equals(mobileNum.trim())) {
			// 验证手机号是否被使用
			MobileApproCnd mobileApproCnd = new MobileApproCnd();
			mobileApproCnd.setMobileNum(mobileNum);
			Integer usedCount = mobileApproMapper.queryRepeatMobileApproCount(mobileApproCnd);
			if (null != usedCount && usedCount > 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String saveMobile(String mobile, String activeCode, HttpServletRequest request, String modelName, HttpSession session) throws Exception {
		String result = "success";
		// 判断输入的手机校验码是否正确
		String valiateResult = phoneService.compareSmsValidate(mobile, activeCode, modelName);
		if (valiateResult != "success") {
			return valiateResult;
		}
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("mobile", mobile);
		resultMap.put("activeCode", activeCode);
		Member member = new Member();
		member.setUsername(generateUserName("gcjr" + mobile, null));
		String passWord = generatePassword(mobile);
		member.setLogpassword(passWord);

		member.setIsFinancialUser(1);// 默认都生成为理财用户
		String source = request.getParameter("source");
		if (!StringUtils.isEmpty(source)) {
			member.setSource(Integer.valueOf(source));
		} else {
			member.setSource(0);
		}

		member.settId("");
		member.setPlatform(BusinessConstants.MEMBER_OPERATE_ON_PORTAL);
		member.setIp(HttpTookit.getRealIpAddr(request));
		memberRegisterService.insertAutoGenerateMemberForGT(member, resultMap, request, session);
		// 判断实名认证是否通过决定是否发放奖励
		RealNameApproVo realNameApproVo = realNameApproService.queryRealNameApproByUserId(member.getId());
		if (null != realNameApproVo && null != realNameApproVo.getIsPassed() && realNameApproVo.getIsPassed() == 1) {
			// 手机与实名认证通过，发放奖励
			this.saveRealNameMobileAward(member.getId(), HttpTookit.getRealIpAddr(request));
		}
		// 获取短信模板
		SmsTemplateVo smsTempale = smsTemplateService.querySmsTemplateByType(BusinessConstants.SMS_TEMPLATE_TYPE_GT_ADVERTISEMENT_REGIST_SUCCESS);

		SmsRecord smsRecord = new SmsRecord();
		smsRecord.setAddip(request.getRemoteAddr());
		smsRecord.setAddtime(new Date());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", member.getUsername());
		map.put("logpassword", passWord);
		String content = SmsUtil.generateParamContent(smsTempale.getContent(), map);
		// 发送短信
		smsRecord.setContent(content);
		smsRecord.setMobile(mobile);
		smsRecord.setSmsType(BusinessConstants.SMS_TEMPLATE_TYPE_GT_ADVERTISEMENT_REGIST_SUCCESS);
		smsRecord.setSendStatus(BusinessConstants.SMS_SEND_STATUS_WAIT);
		smsRecord.setLastmodifytime(new Date());
		smsRecord.setSmsTemplateType(BusinessConstants.SMS_TEMPLATE_TYPE_GT_ADVERTISEMENT_REGIST_SUCCESS);
		smsSendService.saveSmsByZucp(smsRecord);
		return result;
	}

	/**
	 * 
	 * <p>
	 * Description:生成随机密码<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年10月15日
	 * @return String
	 */
	private String generatePassword(String mobile) {
		return mobile.substring(5, mobile.length());
	}

	/**
	 * 
	 * <p>
	 * Description:生成用户名规则 <br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年10月15日
	 * @return String
	 */
	private String generateUserName(String commonName, Integer autoIndex) {
		// 默认为 userName = 输入的手机号码 ，如果已存在，则加前缀
		String name = (autoIndex == null ? "" : ++autoIndex) + commonName;
		try {
			memberRegisterService.queryMemberRepeat(null, null, name);
		} catch (AppException app) {
			autoIndex = autoIndex == null ? 0 : autoIndex;
			name = generateUserName(commonName, autoIndex);
		} catch (Exception e) {
			logger.info(e.getStackTrace());
		}
		return name;
	}

}
