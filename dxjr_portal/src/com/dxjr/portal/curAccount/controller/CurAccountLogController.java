package com.dxjr.portal.curAccount.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.page.Page;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.curAccount.service.CurAccountLogService;
import com.dxjr.portal.curAccount.vo.CurAccountLogCnd;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;

/****
 * 
 * <p>
 * Description: 活期宝账户操作日志记录 <br />
 * </p>
 * 
 * @title CurAccountLogController.java
 * @package com.dxjr.portal.curAccount.controller
 * @author HuangJun
 * @version 0.1 2015年4月27日
 */
@Controller
@RequestMapping(value = "/curAccountLogController")
public class CurAccountLogController extends BaseController {

	private static final Logger logger = Logger.getLogger(CurAccountLogController.class);

	@Autowired
	private CurAccountLogService curAccountLogService;

	// 保存查询条件
	private String sDate="";
	private String eDate="";

	public CurAccountLogController() {

	}

	/**
	 * <p>
	 * Description: 点击tab2- 资金信息 <br />
	 * </p>
	 * 
	 * @author HuangJun
	 * @version 0.1 2015年5月5日
	 * @param request
	 * @param pageNum
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/tab2Zjxx/{pageNum}")
	public ModelAndView tab2Zjxx(HttpServletRequest request, @PathVariable int pageNum,@ModelAttribute CurAccountLogCnd curAccountLogCnd) {
		ModelAndView mv = new ModelAndView("/curAccount/curAccount_zjxx");
		
		Page retPage = null;
		try {
			// 根据userId 查询 资金信息
			ShiroUser shiroUser = super.currentUser();
			if (shiroUser != null) {
				curAccountLogCnd.setUserId(shiroUser.getUserId());
			} else {
				logger.error("当前未登陆，无当前登录用户!");
				return new ModelAndView(BusinessConstants.NO_PAGE_FOUND_404);
			}
			
			String tag = request.getParameter("tag");
			if(!StringUtils.isEmpty(tag)){
				if("week".equals(tag)){  // 一周
					String sysDate = DateUtils.getSysdate();
					String sysDate_pre7 = DateUtils.dateFormatPreDay(6);
					curAccountLogCnd.setBeginDay(sysDate_pre7);
					curAccountLogCnd.setEndDay(sysDate);
				} else if("month".equals(tag)){  //一个月
					String sysDate = DateUtils.getSysdate();
					String sysDate_pre30 = DateUtils.dateFormatPreDay(29);
					curAccountLogCnd.setBeginDay(sysDate_pre30);
					curAccountLogCnd.setEndDay(sysDate);
				}
			}
			// 分页- 页数处理
		    retPage = curAccountLogService.queryCurAccountLogByPage(curAccountLogCnd, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE));
			if(retPage!=null)
			{
				HashMap<Object, Object> loadMap = new HashMap<Object, Object>();
				loadMap.put("1", "转入");
				loadMap.put("2", "转出");
				mv.addObject("loadMap", loadMap);
				
				if (!StringUtils.isEmpty(curAccountLogCnd.getBeginDay()) && !StringUtils.isEmpty(curAccountLogCnd.getEndDay())) {
					this.sDate = curAccountLogCnd.getBeginDay();
					this.eDate = curAccountLogCnd.getEndDay();
				}
			}
			mv.addObject("beginDay", this.sDate);
			mv.addObject("endDay", this.eDate);
			mv.addObject("page", retPage);

		} catch (Exception e) {
			logger.error("点击tab2-资金信息异常", e);
		}
		return mv;
	}

}
