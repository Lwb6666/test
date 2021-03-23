package com.dxjr.portal.quickFinancing.service.impl;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.Configuration;
import com.dxjr.base.entity.SmsRecord;
import com.dxjr.base.mapper.BaseSmsRecordMapper;
import com.dxjr.common.Dictionary;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.quickFinancing.entity.QuickFinancing;
import com.dxjr.portal.quickFinancing.mapper.QuickFinancingMapper;
import com.dxjr.portal.quickFinancing.service.QuickFinancingService;
import com.dxjr.portal.sms.service.SmsTemplateService;
import com.dxjr.portal.sms.vo.SmsTemplateVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.SmsUtil;
import com.dxjr.utils.zucp.ZucpSmsUtil;
@Service
public class QuickFinancingServiceImpl implements QuickFinancingService{
	
	private static final Logger logger=Logger.getLogger(QuickFinancingServiceImpl.class);
    @Autowired 
    private QuickFinancingMapper quickFinancingMapper;
    @Autowired
    private BaseSmsRecordMapper baseSmsRecordMapper;
    @Autowired
    private SmsTemplateService smsTemplateService;
    
	public String insertQucikFinance(QuickFinancing quickFinancing,HttpServletRequest request) throws Exception {
		logger.info("快速融资"); 
		quickFinancing.setAppTime(DateUtils.getTime());
		quickFinancing.setAppIp(HttpTookit.getRealIpAddr(request));
		if(quickFinancingMapper.insertSelective(quickFinancing)>0){
			//发送短信
			Collection<Configuration> configurationList = Dictionary.getValues(1600);
			logger.info("手机号码条数:"+configurationList.size());
	    	
			for(Configuration configuration :configurationList){
				// 获取短信模板
				SmsTemplateVo smsTempale = smsTemplateService.querySmsTemplateByType(BusinessConstants.SMS_TEMPLATE_TYPE_QUCIKFINANCING);
	    		SmsRecord smsRecord=new SmsRecord();
	    		smsRecord.setMobile(configuration.getValue());
	    		smsRecord.setAddip(request.getRemoteAddr());
	    		smsRecord.setAddtime(new Date());
	    		Map<String, Object> map = new HashMap<String, Object>();
	    		map.put("content",quickFinancing.toString());
	    		String content = SmsUtil.generateParamContent(smsTempale.getContent(), map);
	    		// 发送短信
	    		smsRecord.setContent(content);
	    		smsRecord.setMobile(configuration.getValue());
	    		smsRecord.setSmsType(BusinessConstants.SMS_TEMPLATE_TYPE_QUCIKFINANCING);
	    		smsRecord.setSendStatus(BusinessConstants.SMS_SEND_STATUS_WAIT);
	    		smsRecord.setLastmodifytime(new Date());
	    		smsRecord.setSmsTemplateType(BusinessConstants.SMS_TEMPLATE_TYPE_QUCIKFINANCING); 
	    		saveSmsByZucp(smsRecord, request); 
	    		 
	    	}
			return BusinessConstants.SUCCESS;
		};
		
		return "fail";
	}
	/**
	 * 
	 * <p>
	 * Description:保存短信记录<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2015年9月16日
	 * @param smsRecord
	 * @param request
	 * @return
	 * @throws Exception
	 * String
	 */
	public String saveSmsByZucp(SmsRecord smsRecord,HttpServletRequest request) throws Exception {

 
		String result = "success";
		String taskid = "";
		String invoking_status = "";
	 
		// 调用发送短信接口
		taskid =ZucpSmsUtil.sendSms(URLEncoder.encode(smsRecord.getContent(), "UTF-8"), smsRecord.getMobile());
		if (taskid.startsWith("-") || taskid.equals("")){// 发送短信，如果是以负号开头就是发送失败。
			invoking_status = "发送失败！返回值为：" + taskid;
		}else {// 输出返回标识，为小于19位的正数，String类型的。记录您发送的批次。
			invoking_status = "发送成功，返回值为：" + taskid;
		}
		 

		smsRecord.setVendorType(Constants.SMS_RECORD_VENDOR_TYPE_ZUCP);
		smsRecord.setInvokingStatus(invoking_status);
		smsRecord.setTaskid(taskid);
		// 设置客户端类型,只设置指定类型短信模板的平台来源
		List<Integer> smsTemplateIdContainer = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 12, 13, 15, 16, 17, 18, 19, 20, 400);
		if (smsRecord.getSmsTemplateType() != null && smsTemplateIdContainer.contains(smsRecord.getSmsTemplateType())) {
			smsRecord.setPlatform(((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getPlatform());
		}
		baseSmsRecordMapper.insertEntity(smsRecord);
		return result;
	}
}
