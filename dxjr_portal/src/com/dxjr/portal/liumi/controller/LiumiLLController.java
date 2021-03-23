package com.dxjr.portal.liumi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dxjr.portal.liumi.service.LmChannelServices;

@Controller
@RequestMapping(value = "/lmllcon")
public class LiumiLLController {

	
	@Autowired
	private LmChannelServices lmChannelServices;
	/**
	 * 跳转到投天下来源的用户登录页面
	 * @author WangQianJin
	 * @title @param request
	 * @title @param session
	 * @title @return
	 * @date 2016年3月25日
	 */
	@RequestMapping(value = "/getToken")
	public String getToken(HttpServletRequest request, HttpSession session,HttpServletResponse httpResp){
         String res="";
		try {
			 res = lmChannelServices.getToken();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
}
