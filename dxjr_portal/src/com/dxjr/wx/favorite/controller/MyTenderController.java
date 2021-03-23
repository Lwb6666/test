package com.dxjr.wx.favorite.controller;

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
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.security.ShiroUser;

@Controller
@RequestMapping(value = "/wx/favorite")
public class MyTenderController extends BaseController {
	private static final Logger logger = Logger.getLogger(MyTenderController.class);
	@Autowired
	BorrowService borrowService;

	/**
	 * 获取我的投标相关数据
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author ZHUCHEN
	 * @version 0.1 2014年10月30日
	 * @param session
	 * @param request
	 * @param type
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/myTender/{type}/{pageNum}")
	@ResponseBody
	public Map<String, Object> unCollection_record(@PathVariable String type, @PathVariable int pageNum) {
		Map<String, Object> result = new HashMap<String, Object>();
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			return result;
		}

		Map<String, Object> tp = new HashMap<String, Object>();
		tp.put("userId", shiroUser.getUserId());
		tp.put("borrowStatus", type);
		List<?> list;
		try {
			list = borrowService.queryTenderingForOtherBorrow(tp, new Page(pageNum, 10)).getResult();
			if (list != null && list.size() > 0) {
				if (list.size() == 10) {
					result.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
				}
				result.put("collectionRecordList", list);
			}
		} catch (Exception e) {
			logger.error("微信-获取我的投标数据异常：", e);
		}

		return result;
	}
}
