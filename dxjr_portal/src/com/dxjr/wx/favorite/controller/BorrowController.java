package com.dxjr.wx.favorite.controller;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.base.entity.Configuration;
import com.dxjr.common.Dictionary;
import com.dxjr.portal.account.service.BorrowReportService;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.borrow.service.NetValueBorrowService;
import com.dxjr.wx.account.service.SafeCenterService;

@Controller(value = "wxBorrowController")
@RequestMapping(value = "/wx/borrow")
public class BorrowController extends BaseController {
	public Logger logger = Logger.getLogger(BorrowController.class);

	@Autowired
	private BorrowReportService borrowReportService;

	@Autowired
	private NetValueBorrowService netValueBorrowService;

	@Autowired
	private SafeCenterService safeCenterService;

	/**
	 * 净值贷申请初始化
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年10月23日
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/applyInit")
	@ResponseBody
	public Map<String, Object> applyInit() {
		try {
			Map<String, Object> map = safeCenterService.certificationCheck(currentUser(), "", "");
			if (map != null)
				return map;
			else {
				map = new HashMap<String, Object>();
				String msg = netValueBorrowService.checkBorrowCertification(currentUser());
				if (!"".equals(msg)) {
					map.put("msg", msg);
				} else {
					BigDecimal netMoneyLimit = borrowReportService.queryNetMoneyLimit(currentUser().getUserId()).setScale(2, BigDecimal.ROUND_DOWN);
					Collection<Configuration> styleOptions = Dictionary.getValues(818);// 净值标-还款方式

					map.put("netMoneyLimit", netMoneyLimit);
					map.put("styleOptions", styleOptions);
				}
				return map;
			}
		} catch (Exception e) {
			logger.error("微信-净值贷申请初始化异常", e);
		}
		return new HashMap<String, Object>();
	}

}
