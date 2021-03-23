package com.dxjr.wx.advertised.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxjr.base.entity.BankInfo;
import com.dxjr.base.entity.BankinfoLog;
import com.dxjr.base.entity.Member;
import com.dxjr.base.entity.SmsRecord;
import com.dxjr.base.mapper.BaseBankInfoMapper;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.lottery.mapper.LotteryChanceRuleInfoMapper;
import com.dxjr.portal.lottery.service.LotteryChanceInfoService;
import com.dxjr.portal.member.mapper.BankInfoMapper;
import com.dxjr.portal.member.mapper.BankinfoLogMapper;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.service.MemberRegisterService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.member.service.RealNameApproService;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.MobileApproVo;
import com.dxjr.portal.member.vo.RealNameApproVo;
import com.dxjr.portal.red.service.RedAccountService;
import com.dxjr.portal.sms.service.SmsSendService;
import com.dxjr.portal.sms.service.SmsTemplateService;
import com.dxjr.portal.sms.vo.SmsTemplateVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.util.service.PhoneService;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.MD5;
import com.dxjr.utils.SmsUtil;
import com.dxjr.wx.advertised.service.AdvertisedService;
import com.dxjr.wx.advertised.vo.AdvertisedVo;

/**
 * <p>
 * Description:微信端市场活动服务实现类<br />
 * </p>
 * 
 * @title AdvertisedServiceImpl.java
 * @package com.dxjr.wx.advertised.service.impl
 * @author hujianpan
 * @version 0.1 2015年4月13日
 */
@Service
public class AdvertisedServiceImpl implements AdvertisedService {
	public Logger logger = Logger.getLogger(AdvertisedServiceImpl.class);

	@Autowired
	private BankInfoMapper bankInfoMapper;
	@Autowired
	private BaseBankInfoMapper baseBankInfoMapper;
	@Autowired
	private MobileApproService mobileApproService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private BankinfoLogMapper bankinfoLogMapper;
	@Autowired
	private PhoneService phoneService;
	@Autowired
	private SmsTemplateService smsTemplateService;
	@Autowired
	private SmsSendService smsSendService;
	@Autowired
	private MemberRegisterService memberRegisterService;
	@Autowired
	private RealNameApproService realNameApproService;
    @Autowired
    private LotteryChanceRuleInfoMapper lotteryChanceRuleInfoMapper;
    @Autowired
    LotteryChanceInfoService lotteryChanceInfoService;
    @Autowired 
    RedAccountService redAccountService;
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String regist(AdvertisedVo vo, HttpServletRequest request, String modelName, HttpSession session) throws Exception {
		String result = "success";
		// 判断输入的手机校验码是否正确
		String valiateResult = phoneService.compareSmsValidate(vo.getMobile(), vo.getActiveCode(), modelName);
		if (valiateResult != "success") {
			return valiateResult;
		}
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("mobile", vo.getMobile());
		resultMap.put("activeCode", vo.getActiveCode());
		String ip=HttpTookit.getRealIpAddr(request);
		Member member = new Member();
		member.setPlatform(vo.getPlatform());
		member.setUsername(vo.getUsername());
		member.setLogpassword(vo.getNewPwd());
		member.setSource(vo.getSource());
		member.setInviterid(vo.getInviterid());
		member.setIsFinancialUser(1);// 默认都生成为理财用户
		member.settId("");
		member.setIp(ip);
		result=memberRegisterService.insertAutoGenerateMemberForGT(member, resultMap, request, session);
        if(result.equals("success")){
        	//发送红包
    		//在2016-08-01日0点至2016-08-31日24点期间不在发送红包执行
			// 在2016-08-01日0点至2016-09-30日24点期间不在发送红包执行
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date activityTimeStart = sdf.parse("2016-08-01 00:00:00");
			Date activityTimeend = sdf.parse("2016-09-30 23:59:59");
    		if(new Date().before(activityTimeStart)||(new Date().after(activityTimeend))){
           	   redAccountService.insertRedByRegister(member, ip,  vo.getMobile());
    		}
        }
        // 新增“新手注册奖”抽奖机会信息记录
 		if (lotteryChanceInfoService.selectCountByCodeAndUserId(BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_NEW_REGISTER, member.getId()) == 0) { // 注册时间大于等于定义的新手起始时间
 			// 新增“新手注册奖”抽奖机会信息记录
 			lotteryChanceInfoService.insertLotteryChanceInfoByCode(member.getId(), BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_NEW_REGISTER);
 		}
		// 自动发送用户名和密码到用户手机
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", vo.getUsername());
		map.put("logpassword", vo.getNewPwd());

		SmsRecord smsRecord = boxSmsRecordVo(vo, request);
		sendMobileMessage(smsRecord, map);
		return result;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String registNoActiveCode(AdvertisedVo vo, HttpServletRequest request, HttpSession session) throws Exception {
		String result = "success";
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("mobile", vo.getMobile());
		resultMap.put("activeCode", vo.getActiveCode());
		Member member = new Member();
		member.setPlatform(vo.getPlatform());
		member.setUsername(vo.getUsername());
		member.setLogpassword(vo.getNewPwd());
		member.setSource(vo.getSource());
		member.setInviterid(vo.getInviterid());
		member.setIsFinancialUser(1);// 默认都生成为理财用户
		member.settId("");
		member.setIp(HttpTookit.getRealIpAddr(request));
		memberRegisterService.insertAutoGenerateMemberForGT(member, resultMap, request, session);

		return result;
	}

	@Override
	public void sendMobileMessage(SmsRecord smsRecord, Map<String, Object> map) throws Exception {
		// 短信模板类型不能为空
		if (null == smsRecord || smsRecord.getSmsTemplateType() == null) {
			return;
		}
		// 获取短信模板
		SmsTemplateVo smsTempale = smsTemplateService.querySmsTemplateByType(smsRecord.getSmsTemplateType());

		String content = SmsUtil.generateParamContent(smsTempale.getContent(), map);
		smsRecord.setContent(content);

		smsSendService.saveSmsByZucp(smsRecord);
	}

	@Override
	public String saveBankcard(BankInfo bankInfo) throws Exception {

		if (bankInfoMapper.countCardByCardNum(bankInfo.getCardNum()) > 0) {
			return "卡号已存在！";
		}

		MemberVo memberVo = new MemberVo();
		ShiroUser shiroUser = currentUser();
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setId(shiroUser.getUserId());
		memberVo = memberService.queryMemberByCnd(memberCnd);
		MobileApproVo mobileApproVo = mobileApproService.queryMobileApproByUserId(shiroUser.getUserId());
		if (mobileApproVo == null || mobileApproVo.getPassed() == null || mobileApproVo.getPassed() != 1) {
			return "手机未认证";
		}
		if (StringUtils.isEmpty(mobileApproVo.getMobileNum()) && mobileApproVo.getMobileNum().trim().length() != 11) {
			return "手机号码有误，请先认证！";
		}
		if (null != memberVo && StringUtils.isEmpty(memberVo.getPaypassword())) {
			// 将交易密码设置为手机号码后六位
			String payPassword = MD5.toMD5(generatePassword(mobileApproVo.getMobileNum().trim()));
			memberMapper.updatePwd("paypassword", payPassword, shiroUser.getUserId());
		}

		// 检查实名认证
		RealNameApproVo rna = realNameApproService.queryRealNameApproByUserId(bankInfo.getUserId());
		if (null == rna || rna.getIsPassed() != Constants.REALNAME_APPR_ISPASSED_PASSED) {
			return "您还未通过实名认证，无法设置银行卡信息。";
		}

		Date currentTime = new Date();

		bankInfo.setRealName(rna.getRealName());
		bankInfo.setIdCardNo(rna.getIdCardNo());
		bankInfo.setVerifyStatus(1);
		baseBankInfoMapper.insertEntity(bankInfo);

		// 添加银行卡信息日志表
		saveBankCardOperateHis(bankInfo, shiroUser, currentTime);

		return "success";
	}

	/***************************** begin private method *****************************************/
	/**
	 * <p>
	 * Description:保存银行卡操作日志<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2015年4月14日
	 * @param bankInfo
	 * @param shiroUser
	 * @param currentTime void
	 */
	private void saveBankCardOperateHis(BankInfo bankInfo, ShiroUser shiroUser, Date currentTime) {
		BankinfoLog bankinfoLog = new BankinfoLog();
		bankinfoLog.setUserId(bankInfo.getUserId());
		bankinfoLog.setCardNum(bankInfo.getCardNum());
		bankinfoLog.setType(1);// 1：添加
		bankinfoLog.setStatus(0);// 0：有效
		bankinfoLog.setAddBy(shiroUser.getUserId());
		bankinfoLog.setAddTime(currentTime);
		bankinfoLog.setRemark("前台新增银行卡");

		bankinfoLogMapper.insertOld(bankinfoLog);
	}

	private ShiroUser currentUser() {
		return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * 
	 * <p>
	 * Description:取手机号后六位作为密码<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年10月15日
	 * @return String
	 */
	private String generatePassword(String mobile) {
		return mobile.substring(5, mobile.length());
	}

	private SmsRecord boxSmsRecordVo(AdvertisedVo vo, HttpServletRequest request) {
		SmsRecord smsRecord = new SmsRecord();
		smsRecord.setAddip(HttpTookit.getRealIpAddr(request));
		smsRecord.setAddtime(new Date());
		smsRecord.setMobile(vo.getMobile());
		smsRecord.setSmsType(Integer.valueOf(400));
		smsRecord.setSendStatus(BusinessConstants.SMS_SEND_STATUS_WAIT);
		smsRecord.setLastmodifytime(new Date());
		// 设置短信模板
		smsRecord.setSmsTemplateType(400);
		return smsRecord;
	}
	/***************************** end private method *****************************************/
}
