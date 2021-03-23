package com.dxjr.portal.investment.service.impl;

import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.investment.service.InvestMentService;
import com.dxjr.portal.member.service.MemberRegisterService;
import com.dxjr.utils.exception.AppException;

@Service
public class InvestMentServiceImpl implements InvestMentService {

	@Autowired
	private MemberRegisterService memberRegisterService;
	
	public Logger logger = Logger.getLogger(InvestMentServiceImpl.class);
	
	@Override
	public String generatePassword() {
		StringBuffer buffer = new StringBuffer(UUID.randomUUID().toString().replace("-", ""));
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < 6; i++) {
			sb.append(buffer.charAt(r.nextInt(range)));
		}
		return sb.toString();
	}

	@Override
	public String generateUserName(String userName, Integer autoIndex) {
		String name = userName + (autoIndex == null ? "" : ++autoIndex);
		try {
			memberRegisterService.queryMemberRepeat(null, null, name);
		} catch (AppException app) {
			autoIndex = autoIndex == null ? 0 : autoIndex;
			name = generateUserName(userName, autoIndex);
		} catch (Exception e) {
			logger.info(e.getStackTrace());
		}
		return name;
	}

}
