package com.dxjr.portal.account.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.RechargeRecord;
import com.dxjr.base.entity.SystemMessage;
import com.dxjr.base.entity.SystemMessageTemplate;
import com.dxjr.base.mapper.BaseRechargeRecordMapper;
import com.dxjr.base.mapper.BaseSystemMessageMapper;
import com.dxjr.base.mapper.BaseSystemMessageTemplateMapper;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.service.AlipayService;
import com.dxjr.portal.account.service.RechargeRecordService;
import com.dxjr.portal.account.vo.TopupMoneyVo;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.SystemMessageUtil;

/**
 * <p>
 * Description:支付宝支付业务实现类<br />
 * </p>
 * 
 * @title ChinabankServiceImpl.java
 * @package com.dxjr.portal.account.service.impl
 * @author justin.xu
 * @version 0.1 2014年6月11日
 */
@Service
public class AlipayServiceImpl implements AlipayService {
	@Autowired
	private BaseRechargeRecordMapper baseRechargeRecordMapper;
	@Autowired
	private BaseSystemMessageTemplateMapper baseSystemMessageTemplateMapper;
	@Autowired
	private BaseSystemMessageMapper baseSystemMessageMapper;
	@Autowired
	private RechargeRecordService rechargeRecordService;

	@Override
	public String savepay(Integer userId, TopupMoneyVo topupMoneyVo, HttpServletRequest request) throws Exception {
		String result = "success";
		if (null == topupMoneyVo.getMoney() || topupMoneyVo.getMoney().compareTo(new BigDecimal("0")) != 1) {
			return "充值金额必须大于0元！";
		}
		// 保存充值记录
		packageRechargeRecord(userId, topupMoneyVo, request);
		// 发送站内信息 -线下充值
		// packageSystemMessage(memberVo, topupMoneyVo);
		return result;
	}

	/**
	 * <p>
	 * Description:保存充值记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月12日
	 * @param memberVo
	 * @param topupMoneyVo
	 * @param request
	 * @throws Exception void
	 */
	private void packageRechargeRecord(Integer userId, TopupMoneyVo topupMoneyVo, HttpServletRequest request) throws Exception {
		RechargeRecord rechargeRecord = new RechargeRecord();
		// 订单ID
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd-44461248-HHmmssSSS", Locale.US);
		String merOrderNum = sf.format(new Date()) + "_" + userId;
		rechargeRecord.setTradeNo(merOrderNum);
		rechargeRecord.setUserId(userId);
		rechargeRecord.setType(Constants.RECHARGE_TYPE_OFFLINE);
		rechargeRecord.setStatus(Constants.RECHARGE_STATUS_APPROVING);
		rechargeRecord.setMoney(topupMoneyVo.getMoney());
		rechargeRecord.setPayment(BusinessConstants.ALIPAY_PAYMENT);
		rechargeRecord.setFee(new BigDecimal("0"));// 支付没有手续费
		rechargeRecord.setAddip(request.getRemoteAddr());
		rechargeRecord.setAddtime(DateUtils.getTime());
		rechargeRecord.setRemark(topupMoneyVo.getRemark());
		rechargeRecord.setCardNum(BusinessConstants.ALIPAY_CARDNUM);
		rechargeRecord.setBankUsername(BusinessConstants.ALIPAY_BANK_USERNAME);
		rechargeRecord.setVersion(1);
		rechargeRecord.setVerifyUserid(-1);
		rechargeRecord.setVerifyUserid2(-1);
		rechargeRecord.setPlatform(1);
		baseRechargeRecordMapper.insertEntity(rechargeRecord);
	}

	/**
	 * <p>
	 * Description:发送站内信息 -线下充值<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月12日
	 * @param memberVo
	 * @param topupMoneyVo
	 * @throws Exception void
	 */
	private void packageSystemMessage(MemberVo memberVo, TopupMoneyVo topupMoneyVo) throws Exception {
		SystemMessageTemplate systemMessageTemplate = baseSystemMessageTemplateMapper.queryByType(Constants.SYSTEM_MESSAGE_TEMPLATE_TYPE_OFFLINE_RECHARGE);
		if (null != systemMessageTemplate) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("username", memberVo.getUsername());
			paramMap.put("time", DateUtils.format(new Date(), DateUtils.YMD_HMS));
			paramMap.put("money", topupMoneyVo.getMoney());
			SystemMessage systemMessage = new SystemMessage();
			systemMessage.setTitle(systemMessageTemplate.getTitle());
			systemMessage.setType(Constants.SYSTEM_MESSAGE_TEMPLATE_TYPE_OFFLINE_RECHARGE);
			systemMessage.setUserId(memberVo.getId());
			systemMessage.setAddtime(new Date());
			systemMessage.setIsRead(Constants.SYSTEM_MESSAGE_NOT_READ);
			systemMessage.setContent(SystemMessageUtil.generateParamContent(systemMessageTemplate.getContent(), paramMap));
			baseSystemMessageMapper.insertEntity(systemMessage);
		}
	}
}
