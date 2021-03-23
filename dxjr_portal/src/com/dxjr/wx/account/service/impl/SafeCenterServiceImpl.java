package com.dxjr.wx.account.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.member.mapper.BankInfoMapper;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.security.ShiroUser;
import com.dxjr.wx.account.service.SafeCenterService;

@Service
public class SafeCenterServiceImpl implements SafeCenterService {

	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private BankInfoMapper bankMapper;

	/**
	 * 当前登录用户邮箱，手机，实名认证信息检查
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年11月5日
	 * @param loginUser
	 * @return
	 * @throws Exception Map<String,Object>
	 */
	public Map<String, Object> certificationCheck(ShiroUser loginUser, String type, String bank) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		if (loginUser == null) {
			map.put("url", "/login");
			map.put("certificationMsg", "请先登录");
			return map;
		}

		int userId = loginUser.getUserId();

		if (!"paypwd".equals(type)) {
			int erst = memberMapper.queryEmailIspassed(userId);
			int mrst = memberMapper.queryMobileIspassed(userId);
			if ("one".equals(type)) {

				// 邮箱，手机认证通过一项即可
				if (erst == 0 && mrst == 0) {
					map.put("url", "/account/safeCenter/validateMobilePage");
					map.put("certificationMsg", "请先进行手机认证");
					return map;
				}

			} else if ("mobile".equals(type)) {
				// 必须手机认证通过
				if (mrst == 0) {
					map.put("url", "/account/safeCenter/validateMobilePage");
					map.put("certificationMsg", "请先进行手机认证");
					return map;
				}

			} else {
				// 必须全部认证
				// rst = memberMapper.queryEmailIspassed(userId);
				if (erst == 0) {
					map.put("url", "/account/safeCenter/validateEmailPage");
					map.put("certificationMsg", "请先进行邮箱认证");
					return map;
				}

				// rst = memberMapper.queryMobileIspassed(userId);
				if (mrst == 0) {
					map.put("url", "/account/safeCenter/validateMobilePage");
					map.put("certificationMsg", "请先进行手机认证");
					return map;
				}
			}
			int rst = memberMapper.queryRealNameIspassed(userId);
			if (rst == 0) {
				map.put("url", "/account/safeCenter/validateRealNamePage");
				map.put("certificationMsg", "请先进行实名认证");
				return map;
			}

			// 必须先绑卡
			if ("bank".equals(bank)) {
				if (bankMapper.getUserCurrentCard(userId) == null) {
					map.put("url", "/account/bankCard/add");
					map.put("certificationMsg", "请先绑卡");
					return map;
				}
			}
		}

		MemberVo memberVo = memberMapper.queryMemberByloginname(loginUser.getUserName());
		if (memberVo.getPaypassword() == null || "".equals(memberVo.getPaypassword())) {
			map.put("url", "/account/safeCenter/setPayPasswordPage");
			map.put("certificationMsg", "请先设置交易密码");
			return map;
		}

		return null;
	}
}
