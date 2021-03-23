package com.dxjr.portal.member.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

import com.dxjr.base.email.vo.EmailSendVo;
import com.dxjr.base.entity.ChannelBindingVo;
import com.dxjr.base.entity.EmailAppro;
import com.dxjr.base.entity.Member;
import com.dxjr.base.entity.OnlineCounter;
import com.dxjr.base.entity.SystemMessage;
import com.dxjr.base.entity.SystemMessageTemplate;
import com.dxjr.base.mapper.BaseMemberAccumulatePointsMapper;
import com.dxjr.base.mapper.BaseMemberMapper;
import com.dxjr.base.mapper.BaseSystemMessageMapper;
import com.dxjr.base.mapper.BaseSystemMessageTemplateMapper;
import com.dxjr.base.mapper.ChannelBindingMapper;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.AccountService;
import com.dxjr.portal.lottery.service.LotteryChanceInfoService;
import com.dxjr.portal.member.mapper.ApproModifyLogMapper;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.mapper.MemberRegisterMapper;
import com.dxjr.portal.member.mapper.MobileApproMapper;
import com.dxjr.portal.member.service.ApproService;
import com.dxjr.portal.member.service.EmailApprService;
import com.dxjr.portal.member.service.EmailSendForUserService;
import com.dxjr.portal.member.service.IntegralService;
import com.dxjr.portal.member.service.MemberRegisterService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.member.service.OnlineCounterService;
import com.dxjr.portal.member.util.BaiDuIp;
import com.dxjr.portal.member.vo.ApproModifyLog;
import com.dxjr.portal.member.vo.EmailApproCnd;
import com.dxjr.portal.member.vo.EmailApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberRegisterCnd;
import com.dxjr.portal.member.vo.MemberRegisterVo;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.MobileApproCnd;
import com.dxjr.portal.member.vo.OnlineCounterVo;
import com.dxjr.portal.red.service.RedAccountService;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.statics.SendEmailTemplate;
import com.dxjr.portal.tzjinterface.entity.CreateUserReq;
import com.dxjr.security.ShiroUser;
import com.dxjr.security.UsernamePasswordToken;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.MD5;
import com.dxjr.utils.RegResource;
import com.dxjr.utils.SystemMessageUtil;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:会员接口的实现类<br />
 * </p>
 * 
 * @title MemberServiceImpl.java
 * @package com.dxjr.member.service.impl
 * @author justin.xu
 * @version 0.1 2014年4月15日
 */
@Service
public class MemberRegisterServiceImpl implements MemberRegisterService {
	public Logger logger = Logger.getLogger(MemberRegisterServiceImpl.class);

	@Autowired
	private ApproModifyLogMapper approModifyLogMapper;
	@Autowired
	private BaseMemberMapper baseMemberMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private MemberRegisterMapper memberRegisterMapper;
	@Autowired
	private MobileApproMapper mobileApproMapper;
	@Autowired
	private AccountService accountService;
	@Autowired
	private IntegralService integralService;
	@Autowired
	private EmailApprService emailApprService;
	@Autowired
	private EmailSendForUserService emailSendForUserService;
	@Autowired
	private BaseMemberAccumulatePointsMapper memberAccumulatePointsMapper;
	@Autowired
	private BaseSystemMessageTemplateMapper baseSystemMessageTemplateMapper;
	@Autowired
	private BaseSystemMessageMapper baseSystemMessageMapper;
	@Autowired
	private OnlineCounterService onlineCounterService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MobileApproService mobileApproService;
	@Autowired
	private ApproService approService;
	@Autowired
	private LotteryChanceInfoService lotteryChanceInfoService;
	@Autowired
	private RedAccountService redAccountService;
	@Autowired
	private ChannelBindingMapper channelBindingMapper;

	@Override
	public String insertMember(Member member, String inviterName, HttpServletRequest request, HttpSession session) throws Exception {
		String result = "success";
		// 验证注册数据是否正确
		MemberRegisterCnd memberRegisterCnd = new MemberRegisterCnd();
		BeanUtils.copyProperties(member, memberRegisterCnd);
		memberRegisterCnd.setInviterName(inviterName);
		result = this.validateRegisterMember(memberRegisterCnd, member);
		if (!"success".equals(result)) {
			return result;
		}
		// 验证推荐人
		if (null != inviterName && !"".equals(inviterName.trim())) {
			MemberRegisterCnd inviterNameCnd = new MemberRegisterCnd();
			inviterNameCnd.setInviterName(memberRegisterCnd.getInviterName());
			String flag = queryInviterName(inviterNameCnd);
			if (!flag.equals(BusinessConstants.SUCCESS)) {
				return flag;
			}
			List<MemberRegisterVo> inviterNameList = memberRegisterMapper.queryinviterList(inviterNameCnd);
			// 设置推荐人
			member.setInviterid(inviterNameList.get(0).getId());
		}
		// 初始化用户信息
		member = initMemberInfo(request, member, session);
		// 保存注册积分
		saveRegisterPoints(member);
		// 记录访问日志
		OnlineCounter onLineCounter = onlineCounterService.insertOnlineCounter(member, Constants.ONLINE_COUNTER_SYSTEM_PORTAL, request, session);
		// 发送站内信息
		sendRegisterSystemMessage(member, session);
		// 初始化邮件认证
		EmailAppro emailAppro = emailApprService.insertEmailAppr(member.getId(), member.getPlatform());
		// 发送邮件
		sendRegisterEmail(member, request, emailAppro);
		OnlineCounterVo onLineCounterVo = new OnlineCounterVo();
		BeanUtils.copyProperties(onLineCounter, onLineCounterVo);
		session.setAttribute("lastOnLineCounterVo", onLineCounterVo);

		// shiro管理
		UsernamePasswordToken token = new UsernamePasswordToken(member.getUsername(), member.getLogpassword(), BusinessConstants.MEMBER_OPERATE_ON_PORTAL);
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.login(token);

		return result;
	}

	@Override
	public String insertMemberWithoutEmail(Member member, String inviterName, HttpServletRequest request, HttpSession session) throws Exception {
		String result = "success";
		// 验证注册数据是否正确
		MemberRegisterCnd memberRegisterCnd = new MemberRegisterCnd();
		BeanUtils.copyProperties(member, memberRegisterCnd);
		memberRegisterCnd.setInviterName(inviterName);
		result = this.validateRegisterMember(memberRegisterCnd, member);
		if (!"success".equals(result)) {
			return result;
		}
		// 验证推荐人
		if (null != inviterName && !"".equals(inviterName.trim())) {
			MemberRegisterCnd inviterNameCnd = new MemberRegisterCnd();
			inviterNameCnd.setInviterName(memberRegisterCnd.getInviterName());
			String flag = queryInviterName(inviterNameCnd);
			if (!flag.equals(BusinessConstants.SUCCESS)) {
				return flag;
			}
			List<MemberRegisterVo> inviterNameList = memberRegisterMapper.queryinviterList(inviterNameCnd);
			// 设置推荐人
			member.setInviterid(inviterNameList.get(0).getId());
		}
		// 初始化用户信息
		member = initMemberInfo(request, member, session);

		// 注册时如果输入了手机号码，则初始化手机号码待认证记录
		if (member != null && member.getMobileNum() != null && !StringUtils.isEmpty(member.getMobileNum())) {
			MemberVo memberVo = new MemberVo();
			memberVo.setId(member.getId());
			memberVo.setPlatform(member.getPlatform());
			try {
				mobileApproService.AutoGenerateMobileAppro(memberVo, member.getMobileNum().trim(), member.getActiveCode(), member.getIp());
			} catch (Exception e) {
				result = "手机号码已被使用";
				throw new RuntimeException("手机号已被使用！");
			}

		}

		// 记录访问日志
		OnlineCounter onLineCounter = onlineCounterService.insertOnlineCounter(member, Constants.ONLINE_COUNTER_SYSTEM_PORTAL, null, session);

		OnlineCounterVo onLineCounterVo = new OnlineCounterVo();
		BeanUtils.copyProperties(onLineCounter, onLineCounterVo);
		session.setAttribute("lastOnLineCounterVo", onLineCounterVo);


		// 发送红包
		// 在2016-08-01日0点至2016-09-30日24点期间不在发送红包执行
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date activityTimeStart = sdf.parse("2016-08-01 00:00:00");
		Date activityTimeend = sdf.parse("2016-09-30 23:59:59");
		if (new Date().before(activityTimeStart) || (new Date().after(activityTimeend))) {
			redAccountService.insertRedByRegister(member, HttpTookit.getRealIpAddr(request), member.getMobileNum());
		}
		// 新增“新手注册奖”抽奖机会信息记录
		if (lotteryChanceInfoService.selectCountByCodeAndUserId(BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_NEW_REGISTER, member.getId()) == 0) { // 注册时间大于等于定义的新手起始时间
			// 新增“新手注册奖”抽奖机会信息记录
			lotteryChanceInfoService.insertLotteryChanceInfoByCode(member.getId(), BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_NEW_REGISTER);
		}
		// 注册借款用户需后台审核，审核通过才能登录系统
		if (member.getIsFinancialUser() != null && member.getIsFinancialUser() == Integer.parseInt(Constants.IS_FINANCIAL_USER)) {
			// shiro管理
			UsernamePasswordToken token = new UsernamePasswordToken(member.getUsername(), member.getLogpassword(), member.getPlatform());
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
		}
		return result;
	}

	@Override
	public String insertAutoGenerateMember(Member member, Map<String, String> paramterMap, HttpServletRequest request, HttpSession session) throws Exception {
		String result = "success";
		String loginPwd = member.getLogpassword();
		// 验证注册数据是否正确
		MemberRegisterCnd memberRegisterCnd = new MemberRegisterCnd();
		BeanUtils.copyProperties(member, memberRegisterCnd);
		// 初始化用户信息
		member = initMemberInfo(request, member, session);
		// 保存注册积分
		saveRegisterPoints(member);
		// 记录访问日志
		OnlineCounter onLineCounter = onlineCounterService.insertOnlineCounter(member, Constants.ONLINE_COUNTER_SYSTEM_PORTAL, null, session);
		// 发送站内信息
		sendRegisterSystemMessage(member, session);

		// shiro管理
		UsernamePasswordToken token = new UsernamePasswordToken(member.getUsername(), member.getLogpassword(), member.getPlatform());
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.login(token);

		// 初始化邮箱审核日记录
		emailApprService.createApproveEmailRecord(member, member.getIp());

		// 自动生成手机认证信息
		MemberVo memberVo = new MemberVo();
		memberVo.setId(member.getId());
		memberVo.setLogpassword(loginPwd);
		memberVo.setUsername(member.getUsername());
		memberVo.setPlatform(member.getPlatform());
		mobileApproService.AutoGenerateMobileAppro(memberVo, paramterMap.get("telephone"), null, member.getIp());
		// 自动审核手机认证信息
		mobileApproService.AutoApproGenerateMobile(memberVo, paramterMap.get("telephone"), "111111", member.getIp());
		// 自动发送用户名和密码到用户手机
		mobileApproService.sendAutoApproGenerateMemberInfo(memberVo, paramterMap.get("telephone"), member.getIp());
		OnlineCounterVo onLineCounterVo = new OnlineCounterVo();
		BeanUtils.copyProperties(onLineCounter, onLineCounterVo);
		session.setAttribute("lastOnLineCounterVo", onLineCounterVo);

		// 初始化用户论坛角色
		memberMapper.insertBbsUserGroupForUncertified(member.getId());
		// 发送红包
		// 在2016-08-01日0点至2016-09-30日24点期间不在发送红包执行
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date activityTimeStart = sdf.parse("2016-08-01 00:00:00");
		Date activityTimeend = sdf.parse("2016-09-30 23:59:59");
		if (new Date().before(activityTimeStart) || (new Date().after(activityTimeend))) {
			redAccountService.insertRedByRegister(member, member.getIp(), paramterMap.get("telephone"));
		}
		// 新增“新手注册奖”抽奖机会信息记录
		if (lotteryChanceInfoService.selectCountByCodeAndUserId(BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_NEW_REGISTER, member.getId()) == 0) { // 注册时间大于等于定义的新手起始时间
			// 新增“新手注册奖”抽奖机会信息记录
			lotteryChanceInfoService.insertLotteryChanceInfoByCode(member.getId(), BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_NEW_REGISTER);
		}
		return result;
	}

	/**
	 * 网贷天眼自动注册用户
	 * 
	 * @author WangQianJin
	 * @title @param member
	 * @title @param telephone
	 * @title @param request
	 * @title @param session
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年12月16日
	 */
	@Override
	public String insertAutoGenerateMemberForWdty(Member member, String telephone, HttpServletRequest request, HttpSession session) throws Exception {
		String result = "success";
		String loginPwd = member.getLogpassword();
		// 验证注册数据是否正确
		MemberRegisterCnd memberRegisterCnd = new MemberRegisterCnd();
		BeanUtils.copyProperties(member, memberRegisterCnd);
		// 初始化用户信息
		member = initMemberInfo(request, member, session);
		// 保存注册积分
		saveRegisterPoints(member);
		// 记录访问日志
		OnlineCounter onLineCounter = onlineCounterService.insertOnlineCounter(member, Constants.ONLINE_COUNTER_SYSTEM_PORTAL, null, session);
		// 发送站内信息
		sendRegisterSystemMessage(member, session);

		// shiro管理
		UsernamePasswordToken token = new UsernamePasswordToken(member.getUsername(), member.getLogpassword(), member.getPlatform());
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.login(token);

		// 自动生成手机认证信息
		MemberVo memberVo = new MemberVo();
		memberVo.setId(member.getId());
		memberVo.setLogpassword(loginPwd);
		memberVo.setUsername(member.getUsername());
		memberVo.setPlatform(member.getPlatform());
		mobileApproService.AutoGenerateMobileAppro(memberVo, telephone, null, member.getIp());
		// 自动审核手机认证信息
		mobileApproService.AutoApproGenerateMobile(memberVo, telephone, "111111", member.getIp());
		// 自动发送用户名和密码到用户手机
		mobileApproService.sendAutoApproGenerateMemberInfo(memberVo, telephone, member.getIp());
		OnlineCounterVo onLineCounterVo = new OnlineCounterVo();
		BeanUtils.copyProperties(onLineCounter, onLineCounterVo);
		session.setAttribute("lastOnLineCounterVo", onLineCounterVo);

		// 初始化用户论坛角色
		memberMapper.insertBbsUserGroupForUncertified(member.getId());

		// 发送红包
		// 在2016-08-01日0点至2016-09-30日24点期间不在发送红包执行
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date activityTimeStart = sdf.parse("2016-08-01 00:00:00");
		Date activityTimeend = sdf.parse("2016-09-30 23:59:59");
		if (new Date().before(activityTimeStart) || (new Date().after(activityTimeend))) {
			redAccountService.insertRedByRegister(member, member.getIp(), telephone);
		}
		// 新增“新手注册奖”抽奖机会信息记录
		if (lotteryChanceInfoService.selectCountByCodeAndUserId(BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_NEW_REGISTER, member.getId()) == 0) { // 注册时间大于等于定义的新手起始时间
			// 新增“新手注册奖”抽奖机会信息记录
			lotteryChanceInfoService.insertLotteryChanceInfoByCode(member.getId(), BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_NEW_REGISTER);
		}

		return result;
	}

	@Override
	public String queryMemberRepeat(MemberRegisterCnd memberRegisterCnd, Integer userId) throws Exception {
		if (null != userId) {
			memberRegisterCnd.setId(userId);
		}
		if (null != memberRegisterCnd.getEmail() && !"".equals(memberRegisterCnd.getEmail().trim())) {
			// 验证邮箱
			MemberRegisterCnd emailCnd = new MemberRegisterCnd();
			emailCnd.setEmail(memberRegisterCnd.getEmail());
			emailCnd.setId(memberRegisterCnd.getId());
			Integer emailCount = memberRegisterMapper.queryRepeatMemberCount(emailCnd);
			if (null != emailCount && emailCount > 0) {
				return "邮箱已经存在！";
			}
		}
		if (null != memberRegisterCnd.getUsername() && !"".equals(memberRegisterCnd.getUsername().trim())) {
			// 验证用户名
			MemberRegisterCnd usernameCnd = new MemberRegisterCnd();
			usernameCnd.setUsername(memberRegisterCnd.getUsername());
			usernameCnd.setId(memberRegisterCnd.getId());
			Integer usernameCount = memberRegisterMapper.queryRepeatMemberCount(usernameCnd);
			if (null != usernameCount && usernameCount > 0) {
				return "该用户名已经存在！";
			}
		}

		if (null != memberRegisterCnd.getInviterName() && !"".equals(memberRegisterCnd.getInviterName().trim())) {
			MemberRegisterCnd inviterNameCnd = new MemberRegisterCnd();
			inviterNameCnd.setInviterName(memberRegisterCnd.getInviterName());
			String result = queryInviterName(inviterNameCnd);
			if (!result.equals(BusinessConstants.SUCCESS)) {
				return result;
			}
		}
		return "success";
	}

	@Override
	public String queryMemberRepeat(String email, String mobaile, String userName) throws Exception {
		StringBuilder sb = new StringBuilder();
		if (null != email && !"".equals(email.trim())) {
			// 验证邮箱
			MemberRegisterCnd emailCnd = new MemberRegisterCnd();
			emailCnd.setEmail(email);
			Integer emailCount = memberRegisterMapper.queryRepeatMemberCount(emailCnd);
			if (null != emailCount && emailCount > 0) {
				sb.append("邮箱已经存在！");
			}
		}
		if (null != mobaile && !"".equals(mobaile.trim())) {
			// 验证手机号是否存在
			MobileApproCnd mobileApproCnd = new MobileApproCnd();
			mobileApproCnd.setMobileNum(mobaile);
			Integer usernameCount = mobileApproMapper.queryRepeatMobileApproCount(mobileApproCnd);
			if (null != usernameCount && usernameCount > 0) {
				sb.append("该手机号已经被使用！");
			}
		}
		if (null != userName && !"".equals(userName.trim())) {
			// 验证用户名
			MemberRegisterCnd usernameCnd = new MemberRegisterCnd();
			usernameCnd.setUsername(userName.trim());
			Integer usernameCount = memberRegisterMapper.queryRepeatMemberCount(usernameCnd);
			if (null != usernameCount && usernameCount > 0) {
				sb.append("该用户名已经存在！");
			}
		}
		if (!StringUtils.isEmpty(sb.toString())) {
			throw new AppException(sb.toString());
		}
		return "success";
	}

	/**
	 * <p>
	 * Description:发送站内信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月19日
	 * @param member
	 * @param session
	 * @throws Exception
	 *             void
	 */
	private void sendRegisterSystemMessage(Member member, HttpSession session) throws Exception {
		SystemMessageTemplate systemMessageTemplate = baseSystemMessageTemplateMapper.queryByType(Constants.SYSTEM_MESSAGE_TEMPLATE_TYPE_REGISTER_SUCCESS);
		if (null != systemMessageTemplate) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("username", member.getUsername());
			SystemMessage systemMessage = new SystemMessage();
			systemMessage.setTitle(systemMessageTemplate.getTitle());
			systemMessage.setType(Constants.SYSTEM_MESSAGE_TEMPLATE_TYPE_REGISTER_SUCCESS);
			systemMessage.setUserId(member.getId());
			systemMessage.setAddtime(new Date());
			systemMessage.setIsRead(Constants.SYSTEM_MESSAGE_NOT_READ);
			systemMessage.setContent(SystemMessageUtil.generateParamContent(systemMessageTemplate.getContent(), paramMap));
			baseSystemMessageMapper.insertEntity(systemMessage);
		}
	}

	/**
	 * <p>
	 * Description:保存注册积分<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月18日
	 * @param member
	 * @throws Exception
	 *             void
	 */
	private void saveRegisterPoints(Member member) throws Exception {
	}

	/**
	 * <p>
	 * Description:封装会员注册基本信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月16日
	 * @param request
	 * @param member
	 * @return Member
	 */
	private Member packageRegisterMember(HttpServletRequest request, HttpSession session, Member member) {
		String ip = member.getIp();
		member = this.packageCityById(ip, member);
		member.setAddip(ip);
		member.setAddtime(DateUtils.getTime());
		member.setHeadimg("/images/main/head.png");
		member.setIsCustody(0);
		// 借款用户需后台审核
		if (member.getIsFinancialUser() != null && member.getIsFinancialUser() == Integer.parseInt(Constants.IS_BORROWER_USER)) {
			member.setStatus(Constants.MEMBER_STATUS_UNAUDIT);
		} else {
			member.setStatus(Constants.MEMBER_STATUS_NORMAL);
		}

		member.setType(BusinessConstants.VISITOR_TOURIST);// 游客身份
		// 注册不给积分
		member.setAccumulatepoints(Integer.valueOf(0));
		member.setGainaccumulatepoints(Integer.valueOf(0));
		String md5 = MD5.toMD5(member.getUsername() + member.getAddtime());
		member.setUseridmd5(md5);
		// 加密密码
		member.setLogpassword(MD5.toMD5(member.getLogpassword()));
		member.setAwardmoney(new BigDecimal("0"));
		member.setLastloginip(ip);
		member.setLastlogintime(DateUtils.getTime());
		member.setLogintimes(1);
		member.setBorrowtimes(0);
		member.setInvesttimes(0);
		member.setLogintimes(0);
		if (session.getAttribute("wangdai3") != null) {
			// 网贷第三方链接推广注册
			member.setSource(Constants.MEMBER_SOURCE_WANGDAI3);
			session.removeAttribute("wangdai3");
		} else {
			Integer sourceValue = member.getSource();// 保存原有推广来源
			final String tId = (String) session.getAttribute("tid");
			final String attribute = (String) session.getAttribute("linkSourceValue");
			String cook_tId = null;
			Cookie tidCookie = WebUtils.getCookie(request, "tid");
			if (tidCookie != null) {
				cook_tId = tidCookie.getValue();
			}

			String cook_attribute = null;
			Cookie linkSourceValueCookie = WebUtils.getCookie(request, "linkSourceValue");
			if (linkSourceValueCookie != null) {
				cook_attribute = linkSourceValueCookie.getValue();
			}

			if (!StringUtils.isEmpty(attribute)) {
				sourceValue = Integer.valueOf(attribute.toString());
			}

			member.settId(tId);
			member.setSource(sourceValue);
			if (!StringUtils.isEmpty(cook_attribute) && (cook_attribute.equals("37") || cook_attribute.equals("60"))) {
				sourceValue = Integer.valueOf(cook_attribute.toString());
				member.settId(cook_tId);
				member.setSource(sourceValue);
			}
			session.removeAttribute("tid");
			session.removeAttribute("linkSourceValue");
			// 默认来源
			if (member.getSource() == null) {
				if (null != member.getInviterid() && null != memberMapper.getSource()) {
					member.setSource(memberMapper.getSource());
				} else {
					member.setSource(Constants.MEMBER_SOURCE_CHENGXINDAI);
				}
				member.settId("");
			}

		}
		// 默认是理财用户
		if (null == member.getIsFinancialUser() || "".equals(member.getIsFinancialUser())) {
			member.setIsFinancialUser(Constants.MEMBER_FINANCIAL_USER);
		}
		// 默认是非内部员工
		if (null == member.getIsEmployeer()) {
			member.setIsEmployeer(Constants.NO);
		}
		return member;
	}

	/**
	 * <p>
	 * Description:验证注册数据是否正确<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param memberRegisterCnd
	 * @param member
	 * @return String
	 */
	private String validateRegisterMember(MemberRegisterCnd memberRegisterCnd, Member member) throws Exception {
		String result = "success";
		// 验证重复数据
		result = queryMemberRepeat(memberRegisterCnd, null);
		// 如果再添加验证方法，需要判断result的值是否是success
		return result;
	}

	/**
	 * <p>
	 * Description:设置省份，城市，区域<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param ip
	 * @return Member
	 */
	private Member packageCityById(String ip, Member member) {
		BaiDuIp.IpAddr ipAddr = BaiDuIp.queryAddrByIp(ip);
		member.setProvince(ipAddr.getProvince());
		member.setCity(ipAddr.getCity());
		member.setArea(ipAddr.getArea());
		return member;
	}

	/**
	 * <p>
	 * Description:初始化用户信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月17日
	 * @param member
	 * @param request
	 * @return
	 * @throws Exception
	 *             Member
	 */
	private Member initMemberInfo(HttpServletRequest request, Member member, HttpSession session) throws Exception {
		if (null == member.getRedId() || StringUtils.isEmpty(member.getRedId())) {
			member.setRedId(null);
		}
		// 封装会员 用户基本信息
		member = this.packageRegisterMember(request, session, member);
		// 新增会员
		Integer id = baseMemberMapper.insertEntity(member);
		// 初始化资金账户
		accountService.insertAccount(member.getId());
		// 初始化积分等级
		integralService.insertIntegral(member.getId());
		// 保存渠道用户信息
		saveChannelUserInfo(member, session);
		return member;
	}

	/**
	 * 保存渠道用户信息
	 * 
	 * @author WangQianJin
	 * @title @param member
	 * @date 2016年5月27日
	 */
	private void saveChannelUserInfo(Member member, HttpSession session) throws Exception {
		// 惠享游
		if (member.getSource() != null && member.getSource().intValue() == 78) {
			final String hxyid = (String) session.getAttribute("hxyid");
			session.removeAttribute("hxyid");
			if (StringUtils.isNotEmpty(hxyid)) {
				// 保存惠享游信息
				ChannelBindingVo channel = channelBindingMapper.queryChannelByUserId(hxyid);
				ChannelBindingVo channelBinding = new ChannelBindingVo();
				channelBinding.setChannelUid(hxyid);
				channelBinding.setSource(member.getSource());
				channelBinding.setStatus(1);
				channelBinding.setUserId(member.getId());
				if (channel == null) {
					channelBindingMapper.insertEntity(channelBinding);
				} else {
					channelBindingMapper.updateEntity(channelBinding);
				}
			}
		}
	}

	/**
	 * <p>
	 * Description:发送注册邮件<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param member
	 * @param request
	 * @param emailAppro
	 *            void
	 */
	private void sendRegisterEmail(Member member, HttpServletRequest request, EmailAppro emailAppro) {
		// 发送邮件
		try {
			EmailSendVo emailSendVo = new EmailSendVo();
			MemberVo memberVo = new MemberVo();
			BeanUtils.copyProperties(member, memberVo);
			emailSendVo.setContent(SendEmailTemplate.packageRegisterContent(request, emailAppro, memberVo));
			emailSendVo.setSubject("顶玺金融");
			emailSendVo.setToUsers(new String[] { member.getEmail() });
			emailSendForUserService.send(emailSendVo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户注册" + member.getUsername() + "发送邮件异常:" + DateUtils.format(new Date(), DateUtils.DATETIME_YMD_DASH) + ":" + e.getMessage());
		}
	}

	/**
	 * <p>
	 * Description:发送注册邮件<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年9月3日
	 * @param member
	 * @param request
	 * @param emailAppro
	 *            void
	 */
	private void sendVerifyEmail(Member member, HttpServletRequest request, EmailAppro emailAppro) throws Exception {
		// 发送邮件
		try {
			EmailSendVo emailSendVo = new EmailSendVo();
			MemberVo memberVo = new MemberVo();
			BeanUtils.copyProperties(member, memberVo);
			emailSendVo.setContent(SendEmailTemplate.packageVerifyContent(request, emailAppro, memberVo));
			emailSendVo.setSubject("顶玺金融");
			emailSendVo.setToUsers(new String[] { member.getEmail() });
			emailSendForUserService.send(emailSendVo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("邮箱认证" + member.getUsername() + "发送邮件异常:" + DateUtils.format(new Date(), DateUtils.DATETIME_YMD_DASH) + ":" + e.getMessage());
			throw new AppException("邮箱不存在，请输入正确的邮箱");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String sendEmailLinkActivateMember(HttpServletRequest request, String destinationEmail, Boolean again, Member member) throws Exception {
		String result = BusinessConstants.SUCCESS;
		// 通过用户名查询上一步的信息
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setUsername(member.getUsername());
		MemberVo memberVo = memberService.queryMemberByCnd(memberCnd);
		if (memberVo != null && memberVo.getType() != BusinessConstants.VISITOR_OFFICIAL) {
			if (again && !StringUtils.isEmpty(memberVo.getEmail())) {
				member.setEmail(memberVo.getEmail());
			} else if (!again) {
				member.setEmail(destinationEmail);
			} else {
				logger.info("发送验证邮箱时出错，请先填写正确邮箱，再重新发送");
				result = "发送验证邮箱时出错，请先填写正确邮箱，再重新发送";
				return result;
			}
		} else if (memberVo != null && memberVo.getType() == BusinessConstants.VISITOR_OFFICIAL) {
			logger.info("发送验证邮箱时出错，用户已认证");
			result = "发送验证邮箱时出错，用户已认证";
			return result;
		} else {
			result = "发送验证邮箱时出错,关联用户失败。";
			return result;
		}

		member.setId(memberVo.getId());

		if (again) {// 重新发送邮件
			EmailApproCnd emailApproCnd = new EmailApproCnd();
			emailApproCnd.setUserId(memberVo.getId());
			EmailAppro emailAppro = new EmailAppro();
			List<EmailApproVo> emailApproList = null;
			try {
				emailApproList = emailApprService.queryEmailApproList(emailApproCnd);
			} catch (Exception e) {
				logger.info("重新发送验证邮箱失败");
				result = "重新发送验证邮箱失败";
				return result;
			}
			if (null != emailApproList && emailApproList.size() > 0) {
				if (1 == emailApproList.get(0).getChecked()) {
					logger.info("重新发送验证邮箱失败,邮箱已审核通过");
					result = "重新发送验证邮箱失败,邮箱已审核通过";
					return result;
				} else {
					emailAppro.setApprIp(emailApproList.get(0).getApprIp());
					emailAppro.setApprTime(emailApproList.get(0).getApprTime());
					emailAppro.setChecked(emailApproList.get(0).getChecked());
					emailAppro.setId(emailApproList.get(0).getId());
					emailAppro.setRandUUID(emailApproList.get(0).getRandUUID());
					emailAppro.setUserId(emailApproList.get(0).getUserId());
				}

			} else if (null == emailApproList) {
				logger.info("重新发送验证邮箱失败,邮箱未设置");
				result = "重新发送验证邮箱失败,邮箱未设置";
				return result;
			}

			this.sendVerifyEmail(member, request, emailAppro);
		} else if (!StringUtils.isEmpty(destinationEmail)) {
			try {
				// 校验邮箱是否重复
				MemberRegisterCnd memberRegisterCnd = new MemberRegisterCnd();
				memberRegisterCnd.setId(memberVo.getId());
				memberRegisterCnd.setEmail(destinationEmail);
				String isEmailRepeat = this.queryMemberRepeat(memberRegisterCnd, null);
				if (!"success".equals(isEmailRepeat)) {
					result = "当前邮箱已经使用，请重新输入。";
					return result;
				}
				// 校验邮箱是否激活认证
				EmailApproVo emailApproVo = null;
				emailApproVo = emailApprService.queryEmailApproByUserId(memberVo.getId());
				EmailAppro emailAppro = null;
				if (emailApproVo != null && 1 == emailApproVo.getChecked()) {
					result = "当前用户邮箱已审核。";
					return result;
				} else if (emailApproVo != null && 1 != emailApproVo.getChecked()) {

					emailApprService.emailVerify(memberVo, destinationEmail, request);
					return result;
				} else {
					/** 更新邮箱，初始化邮箱认证信息并发送邮件 */

					String memberUpdateResult = memberService.updateEntity(member);
					if (!"sucess".equals(memberUpdateResult)) {// 更新失败
						result = memberUpdateResult;
						return result;
					}
					final Integer platform = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getPlatform();
					// 初始化邮件认证
					emailAppro = emailApprService.insertEmailAppr(memberVo.getId(), platform);
					// 生成流水日志

					ApproModifyLog apprModifyLog = ApproModifyLog.createApproModifyVo(platform, member.getId(), HttpTookit.getRealIpAddr(request), 0,
							"邮箱新增，待审核", member.getEmail(), BusinessConstants.APPRO_MODIFY_LOG_TYPE_EMAIL);
					approModifyLogMapper.insertEntity(apprModifyLog);
					// 发送邮件
					this.sendVerifyEmail(member, request, emailAppro);
					return result;
				}

			} catch (Exception e) {
				logger.info("邮箱不存在，请输入正确的邮箱");
				throw new AppException("邮箱不存在，请输入正确的邮箱");

			}
		} else {
			logger.info("发送验证邮箱失败");
			result = "发送验证邮箱失败,邮箱不能为空。";
			return result;
		}
		return result;
	}

	/**/
	@Override
	public boolean existsContainSensitiveForUserName(String username) {
		int result = memberRegisterMapper.existsContainSensitiveForUserName(username);
		if (result > 0) {
			return true;
		}
		return false;
	}

	@Override
	public String insertAutoGenerateMemberForGT(Member member, Map<String, String> paramterMap, HttpServletRequest request, HttpSession session)
			throws Exception {
		String result = "success";
		String loginPwd = member.getLogpassword();
		// 验证注册数据是否正确
		MemberRegisterCnd memberRegisterCnd = new MemberRegisterCnd();
		BeanUtils.copyProperties(member, memberRegisterCnd);
		// 初始化用户信息
		member = initMemberInfo(request, member, session);
		// 保存注册积分
		saveRegisterPoints(member);
		// 记录访问日志
		OnlineCounter onLineCounter = onlineCounterService.insertOnlineCounter(member, Constants.ONLINE_COUNTER_SYSTEM_PORTAL, null, session);
		// 发送站内信息
		sendRegisterSystemMessage(member, session);

		// 初始化邮箱审核日记录
		// emailApprService.createApproveEmailRecord(member, member.getIp());

		// 自动生成手机认证信息
		MemberVo memberVo = new MemberVo();
		memberVo.setId(member.getId());
		memberVo.setLogpassword(loginPwd);
		memberVo.setUsername(member.getUsername());
		memberVo.setPlatform(member.getPlatform());
		mobileApproService.AutoGenerateMobileAppro(memberVo, paramterMap.get("mobile"), null, member.getIp());
		// 自动审核手机认证信息
		mobileApproService.AutoApproGenerateMobile(memberVo, paramterMap.get("mobile"), paramterMap.get("activeCode"), member.getIp());
		// 自动发送用户名和密码到用户手机
		// mobileApproService.sendAutoApproGenerateMemberInfo(memberVo,
		// paramterMap.get("mobile"), member.getIp());
		OnlineCounterVo onLineCounterVo = new OnlineCounterVo();
		BeanUtils.copyProperties(onLineCounter, onLineCounterVo);
		session.setAttribute("lastOnLineCounterVo", onLineCounterVo);

		// 初始化用户论坛角色
		memberMapper.insertBbsUserGroupForUncertified(member.getId());

		// shiro管理
		UsernamePasswordToken token = new UsernamePasswordToken(member.getUsername(), member.getLogpassword(), member.getPlatform());
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.login(token);

		return result;
	}

	/**
	 * <p>
	 * Description:推荐人用户名 手机号码 邮箱<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年9月21日
	 * @param inviterName
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String queryInviterName(MemberRegisterCnd inviterName) throws Exception {
		if (RegResource.checkMobileNumber(inviterName.getInviterName())) {

			Integer inviterNameCount = memberRegisterMapper.inviterNameCount(inviterName);
			if (null == inviterNameCount || inviterNameCount == 0) {
				return "推荐手机号码不存在,请确认！";
			}
		} else {
			Integer inviterNameCount = memberRegisterMapper.inviterNameCount(inviterName);
			if (null == inviterNameCount || inviterNameCount == 0) {
				return "推荐人用户名有误,请确认！";
			}
		}
		return BusinessConstants.SUCCESS;
	}

	/**
	 * <p>
	 * Description:验证微信客户端数据输入是否正确<br />
	 * </p>
	 * 
	 * @author yubin
	 * @version 0.1 2015年9月23日
	 * @param member
	 * @param inviterName
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String confrim(Member member, String inviterName, HttpServletRequest request, HttpSession session) throws Exception {
		String result = "success";
		// 验证注册数据是否正确
		MemberRegisterCnd memberRegisterCnd = new MemberRegisterCnd();
		BeanUtils.copyProperties(member, memberRegisterCnd);
		memberRegisterCnd.setInviterName(inviterName);
		result = this.validateRegisterMember(memberRegisterCnd, member);
		if (!"success".equals(result)) {
			return result;
		}
		// 验证推荐人
		if (null != inviterName && !"".equals(inviterName.trim())) {
			MemberRegisterCnd inviterNameCnd = new MemberRegisterCnd();
			inviterNameCnd.setInviterName(memberRegisterCnd.getInviterName());
			String flag = queryInviterName(inviterNameCnd);
			if (!flag.equals(BusinessConstants.SUCCESS)) {
				return flag;
			}
		}
		// 注册时如果输入了手机号码，则初始化手机号码待认证记录
		if (mobileApproService.isMobileNumUsed(member.getMobileNum())) {
			return "手机号码已经被使用！";
		}
		return result;
	}

	/**
	 * 投之家自动生成用户信息
	 * 
	 * @author WangQianJin
	 * @title @param member
	 * @title @param req
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年3月24日
	 */
	@Override
	public String tzjInsertAutoGenerateMember(Member member, CreateUserReq req, HttpServletRequest request, HttpSession session) throws Exception {
		String result = "success";
		String loginPwd = member.getLogpassword();
		// 验证注册数据是否正确
		MemberRegisterCnd memberRegisterCnd = new MemberRegisterCnd();
		BeanUtils.copyProperties(member, memberRegisterCnd);
		// 初始化用户信息
		member = initMemberInfo(request, member, session);
		// 保存注册积分
		saveRegisterPoints(member);
		// 记录访问日志
		OnlineCounter onLineCounter = onlineCounterService.insertOnlineCounter(member, Constants.ONLINE_COUNTER_SYSTEM_PORTAL, null, session);
		// 发送站内信息
		sendRegisterSystemMessage(member, session);

		// 初始化邮箱审核日记录
		emailApprService.createApproveEmailRecord(member, member.getIp());

		// 自动生成手机认证信息
		MemberVo memberVo = new MemberVo();
		memberVo.setId(member.getId());
		memberVo.setLogpassword(loginPwd);
		memberVo.setUsername(member.getUsername());
		memberVo.setPlatform(member.getPlatform());
		mobileApproService.AutoGenerateMobileAppro(memberVo, req.getTelephone(), null, member.getIp());
		// 自动审核手机认证信息
		mobileApproService.AutoApproGenerateMobile(memberVo, req.getTelephone(), "111111", member.getIp());
		// 自动发送用户名和密码到用户手机
		mobileApproService.sendAutoApproGenerateMemberInfo(memberVo, req.getTelephone(), member.getIp());
		OnlineCounterVo onLineCounterVo = new OnlineCounterVo();
		BeanUtils.copyProperties(onLineCounter, onLineCounterVo);
		session.setAttribute("lastOnLineCounterVo", onLineCounterVo);

		// 初始化用户论坛角色
		memberMapper.insertBbsUserGroupForUncertified(member.getId());
		// 发送红包
		// 在2016-08-01日0点至2016-09-30日24点期间不在发送红包执行
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date activityTimeStart = sdf.parse("2016-08-01 00:00:00");
		Date activityTimeend = sdf.parse("2016-09-30 23:59:59");
		if (new Date().before(activityTimeStart) || (new Date().after(activityTimeend))) {
			redAccountService.insertRedByRegister(member, member.getIp(), req.getTelephone());
		}
		// 新增“新手注册奖”抽奖机会信息记录
		if (lotteryChanceInfoService.selectCountByCodeAndUserId(BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_NEW_REGISTER, member.getId()) == 0) { // 注册时间大于等于定义的新手起始时间
			// 新增“新手注册奖”抽奖机会信息记录
			lotteryChanceInfoService.insertLotteryChanceInfoByCode(member.getId(), BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_NEW_REGISTER);
		}
		return result;
	}

}
