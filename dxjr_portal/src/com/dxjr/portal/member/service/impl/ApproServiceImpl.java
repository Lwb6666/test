package com.dxjr.portal.member.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxjr.base.entity.EmailAppro;
import com.dxjr.base.entity.Member;
import com.dxjr.base.mapper.BaseEmailApproMapper;
import com.dxjr.base.mapper.BaseMemberMapper;
import com.dxjr.portal.member.mapper.ApproModifyLogMapper;
import com.dxjr.portal.member.mapper.EmailApprMapper;
import com.dxjr.portal.member.service.ApproService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.ApproModifyLog;
import com.dxjr.portal.member.vo.EmailApproCnd;
import com.dxjr.portal.member.vo.EmailApproVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;

@Service
public class ApproServiceImpl implements ApproService {

	@Autowired
	private BaseMemberMapper baseMemberMapper;
	@Autowired
	private BaseEmailApproMapper baseEmailApproMapper;
	@Autowired
	private EmailApprMapper emailApprMapper;
	@Autowired
	MemberService memberService;
	@Autowired
    private ApproModifyLogMapper approModifyLogMapper;

	@Override
	public String updateEmailAppr(Integer userId, String uuid, String email,
			HttpServletRequest request) throws Exception {
		String result = "邮箱验证失败，请重新发送验证信息到邮箱，重新验证!";
		if (null == userId || null == uuid || null == email) {
			return "格式错误，请确认邮件认证地址";
		}
		// 是否已认证
		EmailApproCnd emailApproCnd = new EmailApproCnd();
		emailApproCnd.setUserId(userId);
		List<EmailApproVo> eaList = emailApprMapper.queryEmailApprList(emailApproCnd);
		if (null == eaList || eaList.size() != 1) {
			return "未找到对应的邮箱，请确认!";
		}
		if(!eaList.get(0).getEmail().equals(email)){
			return "绑定邮箱与验证邮箱不一致！";
		}
		
		EmailApproVo ea = eaList.get(0);
		if (ea.getChecked() == 1) {
			return "邮箱验证已经通过，无需重复提交！";
		}

		Member member = baseMemberMapper.queryById(userId);
		if (null != member.getEmail() && member.getEmail().equals(email)) {
			if (ea.getRandUUID().equals(uuid)) {
				ea.setApprIp(HttpTookit.getRealIpAddr(request));
				ea.setChecked(1);
				ea.setApprTime(DateUtils.getTime());
				EmailAppro emailAppro = new EmailAppro();
				BeanUtils.copyProperties(ea, emailAppro);
				final Integer platform = ea.getPlatform();
				emailAppro.setPlatform(null);
				baseEmailApproMapper.updateEntity(emailAppro);
				ApproModifyLog apprModifyLog = ApproModifyLog.createApproModifyVo(
						platform, userId, HttpTookit.getRealIpAddr(request), 1, "邮箱认证通过",
						baseMemberMapper.queryById(userId).getEmail(),
						BusinessConstants.APPRO_MODIFY_LOG_TYPE_EMAIL);
				approModifyLogMapper.insertEntity(apprModifyLog);
				installOfficialIdentity(member);
				result = "success";
			}

		}
		
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void autoApprEmail(Integer userId,
			HttpServletRequest request) throws Exception {

		EmailApproVo ea = new EmailApproVo();

		Member member = baseMemberMapper.queryById(userId);
		ea.setApprIp(HttpTookit.getRealIpAddr(request));
		ea.setChecked(1);
		ea.setApprTime(DateUtils.getTime());
		EmailAppro emailAppro = new EmailAppro();
		BeanUtils.copyProperties(ea, emailAppro);
		final Integer platform = ((ShiroUser) SecurityUtils.getSubject().getPrincipal())
				.getPlatform();
		emailAppro.setPlatform(platform);
		baseEmailApproMapper.updateEntity(emailAppro);
		installOfficialIdentity(member);		
		ApproModifyLog apprModifyLog = ApproModifyLog.createApproModifyVo(
				platform, userId, HttpTookit.getRealIpAddr(request), 1, "邮箱认证通过",
				baseMemberMapper.queryById(userId).getEmail(),
				BusinessConstants.APPRO_MODIFY_LOG_TYPE_EMAIL);
		approModifyLogMapper.insertEntity(apprModifyLog);
	}


	/**
	 * 
	 * <p>
	 * Description:如果不是正式身份，则将用户设置为正式身份<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月9日
	 * @param userId
	 * @param member
	 * @throws Exception
	 * void
	 */
	private void installOfficialIdentity(Member member)
			throws Exception {
		//    当前用户身份 0：正式身份 -1：游客身份 
		if (null != member && BusinessConstants.VISITOR_OFFICIAL != member.getType()) {
			member.setType(BusinessConstants.VISITOR_OFFICIAL);
			memberService.updateEntity(member);//将用户状态更新为正式用户
		}
	}
}
