package com.dxjr.wx.account.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.common.page.Page;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.fix.vo.FixBorrowCnd;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.wx.account.service.WxRedAccountService;
import com.dxjr.wx.account.vo.WxRedAccountVo;

/**
 * 微信-我的红包
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title AccountController.java
 * @package com.dxjr.wx.account.controller
 * @author WQJ
 * @version 0.1 2016年05月19日
 */
@Controller
@RequestMapping(value = "/wx/redAccount")
public class WxRedAccountController extends BaseController {

	public Logger logger = Logger.getLogger(WxRedAccountController.class);

	@Autowired
	private WxRedAccountService redAccountService;


	/**
	 * 微信-红包-列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年7月31日
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "openRed/{opt}/{pageNum}")
	@ResponseBody
	public Map<String, Object> openRed(@PathVariable String opt, @PathVariable int pageNum) {
		try {
			WxRedAccountVo wxRedAccountVo = new WxRedAccountVo();
			wxRedAccountVo.setUserId(currentUser().getUserId());
			List<?> list = redAccountService.queryOpenRedByPage(wxRedAccountVo, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE)).getResult();
			if (list != null && list.size() > 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				if (list.size() == 10) {
					map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
				}
				map.put("openList", list);
				return map;
			}
		} catch (Exception e) {
			logger.error("微信-红包-列表-异常", e);
		}
		return null;
	}
	
	/**
	 * 微信-红包-列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年7月31日
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "closeRed/{opt}/{pageNum}")
	@ResponseBody
	public Map<String, Object> closeRed(@PathVariable String opt, @PathVariable int pageNum) {
		try {
//			FixBorrowCnd cnd = new FixBorrowCnd();
//			List<?> list = redAccountService.queryByExpiredRed(currentUser().getUserId());
//			if (list != null && list.size() > 0) {
//				Map<String, Object> map = new HashMap<String, Object>();				
//				map.put("closeList", list);
//				return map;
//			}
			WxRedAccountVo wxRedAccountVo = new WxRedAccountVo();
			wxRedAccountVo.setUserId(currentUser().getUserId());
//			wxRedAccountVo.setUserId(2);
			List<?> list = redAccountService.queryCloseRedByPage(wxRedAccountVo, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE)).getResult();
			if (list != null && list.size() > 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				if (list.size() == 10) {
					map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
				}
				map.put("closeList", list);
				return map;
			}
		
		} catch (Exception e) {
			logger.error("微信-红包-列表-异常", e);
		}
		return null;
	}
	
	/**
	 * 微信-红包-列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年7月31日
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "countRed")
	@ResponseBody
	public Map<String, Object> countRed() {
		try {
			int countRed = redAccountService.queryOpenRedCount(currentUser().getUserId());
			Map<String, Object> map = new HashMap<String, Object>();				
			map.put("redNum", countRed);
			return map;
		} catch (Exception e) {
			logger.error("微信-红包-列表-异常", e);
		}
		return null;
	}

}
