package com.dxjr.portal.outerInterface.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.portal.outerInterface.service.HSWInterfaceService;
import com.dxjr.portal.outerInterface.vo.OuterParamCnd;
import com.dxjr.utils.HttpTookit;

/**
 * 
 * <p>
 * Description:海树网接口Controller<br />
 * </p>
 * 
 * @title HSWInterfaceController.java
 * @package com.dxjr.portal.outerInterface.controller
 * @author yangshijin
 * @version 0.1 2014年8月19日
 */
@Controller
@RequestMapping(value = "/hsw/api/")
public class HSWInterfaceController {
	@Autowired
	private HSWInterfaceService hswInterfaceService;

	/**
	 * 
	 * <p>
	 * Description:获取借款标(海树网接口)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "interface/getBorrows")
	public @ResponseBody
	String getBorrows(HttpServletRequest request) {
		String result = "-2";
		try {
			String status = request.getParameter("status");
			String time_from = request.getParameter("time_from"); // 起始时间
			String time_to = request.getParameter("time_to"); // 截止时间
			String page_size = request.getParameter("page_size"); // 每页记录条数
			int pageSize = 20;
			if (page_size != null && !page_size.equals("")) {
				pageSize = Integer.parseInt(page_size);
			}
			String page_index = request.getParameter("page_index"); // 页码
			int pageNum = 1;
			if (page_index != null && !page_index.equals("")) {
				pageNum = Integer.parseInt(page_index);
			}
			OuterParamCnd outerParamCnd = new OuterParamCnd();
			outerParamCnd.setType(status);
			outerParamCnd.setBeginTime(time_from);
			outerParamCnd.setEndTime(time_to);
			result = hswInterfaceService.getBorrows(
					HttpTookit.getRealIpAddr(request),
					request.getHeader("Referer"), outerParamCnd, pageNum,
					pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * Description:根据借款标ID获取投标记录(海树网接口)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "interface/getTenderRecord")
	public @ResponseBody
	String getTenderRecord(HttpServletRequest request) {
		String result = "-2";
		try {
			String id = request.getParameter("id");
			String page_size = request.getParameter("page_size"); // 每页记录条数
			int pageSize = 20;
			if (page_size != null && !page_size.equals("")) {
				pageSize = Integer.parseInt(page_size);
			}
			String page_index = request.getParameter("page_index"); // 页码
			int pageNum = 1;
			if (page_index != null && !page_index.equals("")) {
				pageNum = Integer.parseInt(page_index);
			}
			result = hswInterfaceService.getTenderRecord(
					HttpTookit.getRealIpAddr(request),
					request.getHeader("Referer"), id, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * Description:获取逾期的借款标(海树网接口)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月19日
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "interface/getOverdueBorrows")
	public @ResponseBody
	String getOverdueBorrows(HttpServletRequest request) {
		String result = "-2";
		try {
			String page_size = request.getParameter("page_size"); // 每页记录条数
			int pageSize = 20;
			if (page_size != null && !page_size.equals("")) {
				pageSize = Integer.parseInt(page_size);
			}
			String page_index = request.getParameter("page_index"); // 页码
			int pageNum = 1;
			if (page_index != null && !page_index.equals("")) {
				pageNum = Integer.parseInt(page_index);
			}
			result = hswInterfaceService.getOverdueBorrows(
					HttpTookit.getRealIpAddr(request),
					request.getHeader("Referer"), pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
