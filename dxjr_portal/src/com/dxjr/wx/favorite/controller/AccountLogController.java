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

import com.dxjr.portal.account.service.AccountOperateRecordService;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.statics.BusinessConstants;

@Controller
@RequestMapping(value = "/wx/accountLog")
public class AccountLogController extends BaseController {

	public Logger logger = Logger.getLogger(AccountLogController.class);

	@Autowired
	private AccountOperateRecordService accountOperateRecordService;

	/**
	 * 资金明细-列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年10月30日
	 * @param pageNum
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/logList/{pageNum}")
	@ResponseBody
	public Map<String, Object> logList(@PathVariable Integer pageNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (currentUser() != null) {
				List<?> logList = accountOperateRecordService.searchPageUserAccountList(currentUser().getUserName(), "", "", "", pageNum, BusinessConstants.DEFAULT_PAGE_SIZE).getResult();
				if (logList != null) {
					map.put("logList", logList);
					if (logList.size() == BusinessConstants.DEFAULT_PAGE_SIZE)
						map.put("moreDiv", "<a id='clickA'><div id='contain'><div class='f2'><h4>查看更多</h4></div></div></a>");
				}
				return map;
			}
		} catch (Exception e) {
			logger.error("微信-资金明细-列表获取异常", e);
		}
		return map;
	}
}
