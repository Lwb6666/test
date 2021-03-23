package com.dxjr.wx.entry.bind.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.mapper.MobileApproMapper;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.MobileApproCnd;
import com.dxjr.portal.member.vo.MobileApproVo;
import com.dxjr.utils.MD5;
import com.dxjr.wx.entry.bind.mapper.BindHistoryVoMapper;
import com.dxjr.wx.entry.bind.mapper.BindVoMapper;
import com.dxjr.wx.entry.bind.service.BindService;
import com.dxjr.wx.entry.bind.vo.BindVo;
import com.mysql.jdbc.StringUtils;

@Service
public class BindServiceImpl implements BindService {
	@Autowired
	private BindVoMapper bindMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private MobileApproMapper mobileApproMapper;
	@Autowired
	private MobileApproService mobileApproService;
	@Autowired
	private BindHistoryVoMapper bindHistoryVoMapper;

	public MemberVo queryIsBind(Integer wId) {
		return bindMapper.selectByWxId(wId);
	}

	public MessageBox saveBind(BindVo bindVo, String modelName, int smsTemplateType, HttpServletRequest request) throws Exception {
		MessageBox info = verfiBindInfo(bindVo);
		Integer hadBind = bindMapper.queryHadBind(bindVo.getWxId());
		Long date = new Date().getTime() / 1000;
		if (!"success".equals(info.getMessage()))
			return info;
		MemberVo memberVo = memberMapper.queryMemberByloginname(bindVo.getTelphone());
		String result = mobileApproService.verifyMobileCodeBeforeAddBankCard(memberVo, bindVo.getTelphone(), bindVo.getActiveCode(), request, modelName, smsTemplateType);
		if (!"success".equals(result)) {
			return MessageBox.build("0", result);
		}
		bindVo.setUserId(memberVo.getId());
		bindVo.setStatus(1);
		bindVo.setFromstatus(0);
		bindVo.setCreateTime(date);
		bindVo.setUsername(memberVo.getUsername());
		int i = 0;
		if (hadBind != null && hadBind != 0) {
			bindVo.setUpdateTime(date);
			i = bindMapper.update(bindVo);
		} else {
			i = bindMapper.insert(bindVo);
		}
		if (i != 0) {
			bindHistoryVoMapper.saveBindHistory(bindVo);
			return MessageBox.build("1", "您好" + bindVo.getUsername() + ",你的账户绑定成功");
		}
		return MessageBox.build("0", "绑定失败,请重试");
	}

	@Override
	public MessageBox verfiBindInfo(BindVo bindVo) throws Exception {
		MemberVo vo = memberMapper.queryMemberByloginname(bindVo.getTelphone());
		if (bindVo.getWxId() == null || bindVo.getWxId() == 0 || StringUtils.isNullOrEmpty(bindVo.getOpenId())) {
			return MessageBox.build("0", "获取微信相关信息失败,请重新获取绑定链接");
		}
		if(vo==null){
			return MessageBox.build("0", "尚未进行手机认证，无法绑定");
		}
		MobileApproCnd mobileApproCnd = new MobileApproCnd();
		mobileApproCnd.setUserId(vo.getId());
		List<MobileApproVo> list;
		list = mobileApproMapper.queryMobileApproList(mobileApproCnd);
		 
		MobileApproVo approVo = list.get(0);
		if (approVo.getPassed() != 1) {
			return MessageBox.build("0", "手机认证尚未通过,无法进行绑定");
		}
		 
		if (!(MD5.toMD5(bindVo.getPassword())).equals(vo.getLogpassword())) {
			return MessageBox.build("0", "手机号码与登录密码不匹配");
		}
		if (vo.getIsFinancialUser() != 1) {
			return MessageBox.build("0", "非理财用户无法进行微信绑定");
		}
	 
		MemberVo m = bindMapper.selectByWxId(bindVo.getWxId());
		if (m != null) {
			return MessageBox.build("0", "当前微信账号已经与官网账号绑定,请解绑后重新绑定");
		}
		bindVo.setPassword(MD5.toMD5(bindVo.getPassword()));
		Integer member = bindMapper.selectWxIdByMember(bindVo);
		if (member != null) {
			return MessageBox.build("0", "当前账号已与其他微信账号绑定");
		}
		return MessageBox.build("1", "success");
	}

	public MessageBox updateUnBind(BindVo bindVo) throws Exception {
		if (bindVo.getWxId() == null || bindVo.getWxId() == 0 || StringUtils.isNullOrEmpty(bindVo.getOpenId())) {
			return MessageBox.build("0", "获取微信相关信息失败,请重新获取绑定链接");
		}
		MemberVo bd = bindMapper.selectByWxId(bindVo.getWxId());
		if (bd == null) {
			return MessageBox.build("0", "当前微信账户没有绑定顶玺金融账户");
		}
		bindVo.setUpdateTime(new Date().getTime() / 1000);
		bindVo.setStatus(0);
		bindVo.setFromstatus(1);
		if (bindMapper.update(bindVo) == 1) {
			return MessageBox.build("1", "解绑成功");
		}
		return MessageBox.build("0", "解绑失败");
	}

	public MessageBox updateUnBindUnsubscribe(BindVo bindVo) {
		MemberVo vo = bindMapper.selectByWxId(bindVo.getWxId());
		if (vo == null) {
			return MessageBox.build("0", "非绑定账号无需解绑");
		}
		Long date = new Date().getTime() / 1000;
		bindVo.setUpdateTime(date);
		bindVo.setStatus(0);
		bindVo.setFromstatus(1);
		if (bindMapper.update(bindVo) == 1) {
			bindVo.setCreateTime(date);
			bindHistoryVoMapper.saveBindHistory(bindVo);
			return MessageBox.build("1", "解绑成功");
		}
		return MessageBox.build("0", "解绑失败");
	}

	@Override
	public MemberVo queryMemberByLoginname(String name) throws Exception {
		return memberMapper.queryMemberByloginname(name);
	}

	@Override
	public void updateBindStartUp(String ids) {
		long time = new Date().getTime() / 1000;
		bindHistoryVoMapper.saveubsubBind(time, "项目维护时需要自动解绑记录", ids);
		bindMapper.updateBindunsub(time, ids);
		bindMapper.clearunBind();
	}

	public Integer queryWxIdByUserId(Integer userId) {
		return bindMapper.queryWxIdByUserId(userId);
	}

	@Override
	public Integer queryHadBind(Integer wxId) {
		return bindMapper.queryHadBind(wxId);
	}
}
