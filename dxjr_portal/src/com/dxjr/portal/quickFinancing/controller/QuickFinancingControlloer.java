package com.dxjr.portal.quickFinancing.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.quickFinancing.entity.QuickFinancing;
import com.dxjr.portal.quickFinancing.service.QuickFinancingService;
import com.dxjr.portal.statics.BusinessConstants;
/**
 * 
 * <p>
 * Description:我要融资业务<br />
 * </p>
 * @title FinancingControlloer.java
 * @package com.dxjr.portal.member.controller 
 * @author yubin
 * @version 0.1 2015年9月8日
 */
@Controller
@RequestMapping(value = "/financing")
public class QuickFinancingControlloer extends BaseController{
	
	private static final Logger logger=Logger.getLogger(QuickFinancingControlloer.class);
	@Autowired
	private QuickFinancingService quickFinancingService;
	/**
	 *  
	 * <p>
	 * Description:快速融资申请<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2015年9月9日
	 * @param request
	 * @param session
	 * @return
	 * MessageBox
	 */
	@RequestMapping(value = "/financingApply")
	@ResponseBody
	public MessageBox financingApply(@ModelAttribute QuickFinancing quickFinance) {
		String result = BusinessConstants.SUCCESS;
		try {
			// 验证验证码验证
			String validatecode = currentRequest().getParameter("validatecode");
			String randCode = (String) currentSession().getAttribute("randomCode");
			if (null == validatecode || null == randCode || !validatecode.equals(randCode)) {
				return new MessageBox("0", "验证码输入有误！");
			}
			if(quickFinance.getAccount()==null){
				return new MessageBox("0", "请输入融资金额");
			}
			if(quickFinance.getTimeLimit()==null){
				return new MessageBox("0", "请输入融资期限");
			}
			if(StringUtils.isEmpty(quickFinance.getName())){
				return new MessageBox("0", "请输入联系人");
			}
			if(StringUtils.isEmpty(quickFinance.getTel())){
				return new MessageBox("0", "请输入联系电话");
			}
			//插入数据
			quickFinancingService.insertQucikFinance(quickFinance, currentRequest());
		    currentSession().removeAttribute("randomCode"); 
		} catch (Exception e) {
			logger.error("快速融资", e);
			return new MessageBox("0", "网络异常");
		}

		return new MessageBox("1", result);
	}
	
}
