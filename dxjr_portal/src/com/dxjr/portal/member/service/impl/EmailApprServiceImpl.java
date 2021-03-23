package com.dxjr.portal.member.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxjr.base.email.vo.EmailSendVo;
import com.dxjr.base.entity.EmailAppro;
import com.dxjr.base.entity.Member;
import com.dxjr.base.mapper.BaseEmailApproMapper;
import com.dxjr.base.mapper.BaseMemberMapper;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.member.mapper.ApproModifyLogMapper;
import com.dxjr.portal.member.mapper.EmailApprMapper;
import com.dxjr.portal.member.mapper.MemberRegisterMapper;
import com.dxjr.portal.member.service.EmailApprService;
import com.dxjr.portal.member.service.EmailSendForUserService;
import com.dxjr.portal.member.vo.ApproModifyLog;
import com.dxjr.portal.member.vo.EmailApproCnd;
import com.dxjr.portal.member.vo.EmailApproVo;
import com.dxjr.portal.member.vo.MemberRegisterCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.statics.SendEmailTemplate;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.exception.AppException;

@Service
public class EmailApprServiceImpl implements EmailApprService {
	public Logger logger = Logger.getLogger(EmailApprServiceImpl.class);

	@Autowired
	private BaseEmailApproMapper baseEmailApproMapper;
	@Autowired
	private EmailApprMapper emailApprMapper;
	@Autowired
	private MemberRegisterMapper memberRegisterMapper;
	@Autowired
	private BaseMemberMapper baseMemberMapper;
	@Autowired
	private EmailSendForUserService emailSendForUserService;
	@Autowired
	private ApproModifyLogMapper approModifyLogMapper;

	@Override
	public EmailAppro insertEmailAppr(Integer userId,Integer platform) throws Exception {
		EmailAppro ea = new EmailAppro();
		ea.setChecked(Constants.NO);
		ea.setRandUUID(UUID.randomUUID().toString().replace("-", ""));
		ea.setUserId(userId);
		ea.setPlatform(platform);
		baseEmailApproMapper.insertEntity(ea); 
		return ea;
	}
	
	@Override
	public EmailAppro createApproveEmailRecord(Member member,String ipAddr) throws Exception {
		EmailAppro ea = new EmailAppro();
		ea.setApprIp(ipAddr);
		ea.setChecked(Constants.YES);
		ea.setRandUUID(UUID.randomUUID().toString().replace("-", ""));
		ea.setUserId(member.getId());
		ea.setApprTime(DateUtils.getTime());
		ea.setPlatform(member.getPlatform());
		baseEmailApproMapper.insertEntity(ea);
		
		ApproModifyLog apprModifyLog = ApproModifyLog.createApproModifyVo(
				member.getPlatform(), member.getId(), ipAddr, 1, "邮箱认证通过",
				member.getEmail(),
				BusinessConstants.APPRO_MODIFY_LOG_TYPE_EMAIL);
		approModifyLogMapper.insertEntity(apprModifyLog);
		return ea;
	}


	@Override
	public List<EmailApproVo> queryEmailApproList(EmailApproCnd emailApproCnd)
			throws Exception {
		return emailApprMapper.queryEmailApprList(emailApproCnd);
	}

	@Override
	public EmailApproVo queryEmailApproByUserId(Integer memberId)
			throws Exception {
		EmailApproCnd emailApproVoCnd = new EmailApproCnd();
		emailApproVoCnd.setUserId(memberId);
		List<EmailApproVo> list = emailApprMapper
				.queryEmailApprList(emailApproVoCnd);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public String updateAndSendMail(MemberVo memberVo, String email,
			HttpServletRequest request) throws Exception {
		String result = "success";
		result = this.queryMemberRepeat(memberVo, email);
		if (!"success".equals(result)) {
			return result;
		}
		// 更新用户邮箱
		Member updateMember = new Member();
		updateMember.setId(memberVo.getId());
		updateMember.setEmail(email);
		baseMemberMapper.updateEntity(updateMember);
		// 发送邮件
		try {
			// 改变email
			memberVo.setEmail(email);
			EmailApproCnd emailApproCnd = new EmailApproCnd();
			emailApproCnd.setUserId(memberVo.getId());
			EmailApproVo emailApproVo = emailApprMapper.queryEmailApprList(
					emailApproCnd).get(0);
			EmailAppro emailAppro = new EmailAppro();
			BeanUtils.copyProperties(emailApproVo, emailAppro);
			EmailSendVo emailSendVo = new EmailSendVo();
			// 封装内容
			emailSendVo.setContent(SendEmailTemplate.packageRegisterContent(
					request, emailAppro, memberVo));
			emailSendVo.setSubject("顶玺金融");
			emailSendVo.setToUsers(new String[] { email });
			emailSendForUserService.send(emailSendVo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户重新激活邮箱" + memberVo.getUsername() + "发送邮件异常:"
					+ DateUtils.format(new Date(), DateUtils.DATETIME_YMD_DASH)
					+ ":" + e.getMessage());
			throw e;
		}
		return result;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String emailVerifyInSafeCenter(MemberVo memberVo, String email,
			HttpServletRequest request) throws Exception {
		String result = "success";
		EmailApproVo checkemailIsPassAppro =this.queryEmailApproByUserId(memberVo.getId());
		if(null != checkemailIsPassAppro && checkemailIsPassAppro.getChecked() == 1){
			result = "邮箱已经认证通过";
			return result;
		}
		result = this.queryMemberRepeat(memberVo, email);
		if (!"success".equals(result)) {
			return result;
		}
		// 更新用户邮箱
		Member updateMember = new Member();
		updateMember.setId(memberVo.getId());
		updateMember.setEmail(email);
		baseMemberMapper.updateEntity(updateMember);
		final Integer platform = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getPlatform();
		// 发送邮件
		try {
			// 改变email
			memberVo.setEmail(email);

			if(checkemailIsPassAppro != null ){
				EmailAppro emailAppro = new EmailAppro();
				BeanUtils.copyProperties(checkemailIsPassAppro, emailAppro);
				emailAppro.setChecked(0);
				
				emailAppro.setPlatform(platform);
				baseEmailApproMapper.updateEntity(emailAppro);
				//新增流水记录
				ApproModifyLog apprModifyLog = ApproModifyLog.createApproModifyVo(
						platform, memberVo.getId(), HttpTookit.getRealIpAddr(request), 0, "邮箱修改，待审核",
								email,
						BusinessConstants.APPRO_MODIFY_LOG_TYPE_EMAIL);
				approModifyLogMapper.insertEntity(apprModifyLog);
				
				EmailSendVo emailSendVo = new EmailSendVo();
				// 封装内容
				emailSendVo.setContent(SendEmailTemplate.packageRegisterContent(
						request, emailAppro, memberVo));
				emailSendVo.setSubject("顶玺金融");
				emailSendVo.setToUsers(new String[] { email });
				emailSendForUserService.send(emailSendVo);
			}else {
				// 初始化邮件认证
				  EmailAppro emailAppro = this
							.insertEmailAppr(memberVo.getId(),platform);
					//新增流水记录
					ApproModifyLog apprModifyLog = ApproModifyLog.createApproModifyVo(
							platform, memberVo.getId(), HttpTookit.getRealIpAddr(request), 0, "邮箱新增，待审核",
									email,
							BusinessConstants.APPRO_MODIFY_LOG_TYPE_EMAIL);
					approModifyLogMapper.insertEntity(apprModifyLog);
					
				  EmailSendVo emailSendVo = new EmailSendVo();
					// 封装内容
					emailSendVo.setContent(SendEmailTemplate.packageRegisterContent(
							request, emailAppro, memberVo));
					emailSendVo.setSubject("顶玺金融");
					emailSendVo.setToUsers(new String[] { email });
					emailSendForUserService.send(emailSendVo);
			}
			
		} catch (Exception e) {
			logger.error("用户重新激活邮箱" + memberVo.getUsername() + "发送邮件异常:"
					+ DateUtils.format(new Date(), DateUtils.DATETIME_YMD_DASH)
					+ ":" + e.getMessage());
			throw new AppException("发送邮件错误，请输入正确邮箱");
		}
		return result;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String sendVerifyEmailAgain(MemberVo memberVo,
			HttpServletRequest request) throws Exception {
		String result = "success";
		EmailApproVo checkemailIsPassAppro =this.queryEmailApproByUserId(memberVo.getId());
		if(null != checkemailIsPassAppro && checkemailIsPassAppro.getChecked() == 1){
			result = "邮箱已经认证通过";
			return result;
		}
		// 发送邮件
		try {
			if(checkemailIsPassAppro != null ){
				EmailAppro emailAppro = new EmailAppro();
				BeanUtils.copyProperties(checkemailIsPassAppro, emailAppro);
				emailAppro.setChecked(0);
				final Integer platform = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getPlatform();
				emailAppro.setPlatform(platform);
				baseEmailApproMapper.updateEntity(emailAppro);
				//新增流水记录
				ApproModifyLog apprModifyLog = ApproModifyLog.createApproModifyVo(
						platform, memberVo.getId(), HttpTookit.getRealIpAddr(request), 0, "邮箱修改，待审核",
						memberVo.getEmail(),
						BusinessConstants.APPRO_MODIFY_LOG_TYPE_EMAIL);
				approModifyLogMapper.insertEntity(apprModifyLog);
				
				EmailSendVo emailSendVo = new EmailSendVo();
				// 封装内容
				emailSendVo.setContent(SendEmailTemplate.packageRegisterContent(
						request, emailAppro, memberVo));
				emailSendVo.setSubject("顶玺金融");
				emailSendVo.setToUsers(new String[] { memberVo.getEmail() });
				emailSendForUserService.send(emailSendVo);
			} else {
				return "您未填写邮箱，请先使用发送邮件功能，如未收到，再重新发送！";
			}
			
		} catch (Exception e) {
			logger.error("用户重新激活邮箱" + memberVo.getUsername() + "发送邮件异常:"
					+ DateUtils.format(new Date(), DateUtils.DATETIME_YMD_DASH)
					+ ":" + e.getMessage());
			throw new AppException("发送邮件错误，请输入正确邮箱");
		}
		return result;
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String emailVerify(MemberVo memberVo, String email,
			HttpServletRequest request) throws Exception {
		String result = "success";
		EmailApproVo checkemailIsPassAppro =this.queryEmailApproByUserId(memberVo.getId());
		if(null != checkemailIsPassAppro && checkemailIsPassAppro.getChecked() == 1){
			result = "邮箱已经认证通过";
			return result;
		}
		result = this.queryMemberRepeat(memberVo, email);
		if (!"success".equals(result)) {
			return result;
		}
		// 更新用户邮箱
		Member updateMember = new Member();
		updateMember.setId(memberVo.getId());
		updateMember.setEmail(email);
		baseMemberMapper.updateEntity(updateMember);
		final Integer platform = ((ShiroUser) SecurityUtils.getSubject().getPrincipal())
				.getPlatform();
		// 发送邮件
		try {
			// 改变email
			memberVo.setEmail(email);

			if(checkemailIsPassAppro != null ){
				EmailAppro emailAppro = new EmailAppro();
				BeanUtils.copyProperties(checkemailIsPassAppro, emailAppro);
				EmailSendVo emailSendVo = new EmailSendVo();
				emailAppro.setChecked(0);
				
				emailAppro.setPlatform(platform);
				baseEmailApproMapper.updateEntity(emailAppro);
				
				ApproModifyLog apprModifyLog = ApproModifyLog.createApproModifyVo(
						platform, memberVo.getId(), HttpTookit.getRealIpAddr(request), 0, "邮箱修改，待审核",
								email,
						BusinessConstants.APPRO_MODIFY_LOG_TYPE_EMAIL);
				approModifyLogMapper.insertEntity(apprModifyLog);
				
				// 封装内容
				emailSendVo.setContent(SendEmailTemplate.packageVerifyContent(
						request, emailAppro, memberVo));
				emailSendVo.setSubject("顶玺金融");
				emailSendVo.setToUsers(new String[] { email });
				emailSendForUserService.send(emailSendVo);
			}else {
				// 初始化邮件认证
				  EmailAppro emailAppro = this
							.insertEmailAppr(memberVo.getId(),platform);
				  ApproModifyLog apprModifyLog = ApproModifyLog.createApproModifyVo(
						  platform, memberVo.getId(), HttpTookit.getRealIpAddr(request), 0, "邮箱新增，待审核",
							email,
							BusinessConstants.APPRO_MODIFY_LOG_TYPE_EMAIL);
					approModifyLogMapper.insertEntity(apprModifyLog);
				  EmailSendVo emailSendVo = new EmailSendVo();
					// 封装内容
					emailSendVo.setContent(SendEmailTemplate.packageVerifyContent(
							request, emailAppro, memberVo));
					emailSendVo.setSubject("顶玺金融");
					emailSendVo.setToUsers(new String[] { email });
					emailSendForUserService.send(emailSendVo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户重新激活邮箱" + memberVo.getUsername() + "发送邮件异常:"
					+ DateUtils.format(new Date(), DateUtils.DATETIME_YMD_DASH)
					+ ":" + e.getMessage());
			throw e;
		}
		return result;
	}

	/**
	 * <p>
	 * Description:验证邮箱是否重复<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param member
	 * @param email
	 * @return
	 * @throws Exception
	 *             String
	 */
	private String queryMemberRepeat(MemberVo memberVo, String email)
			throws Exception {
		String result = "success";
		if (null == email || "".equals(email.trim())) {
			return "邮件格式错误，请确认！";
		}
		// 验证邮箱
		MemberRegisterCnd emailCnd = new MemberRegisterCnd();
		emailCnd.setId(memberVo.getId());
		emailCnd.setEmail(email);
		Integer emailCount = memberRegisterMapper
				.queryRepeatMemberCount(emailCnd);
		if (null != emailCount && emailCount > 0) {
			return "邮箱已经存在！";
		}
		return result;
	}

}
