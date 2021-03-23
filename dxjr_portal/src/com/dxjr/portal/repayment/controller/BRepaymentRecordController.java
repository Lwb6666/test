package com.dxjr.portal.repayment.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.common.page.Page;
import com.dxjr.common.report.JasperExportUtils;
import com.dxjr.common.report.ReportData;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.repayment.service.BRepaymentRecordService;
import com.dxjr.portal.repayment.vo.BRepaymentRecordVo;
import com.dxjr.portal.repayment.vo.RepaymentRecordCnd;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;

@Controller
@RequestMapping(value = "/repayment/")
public class BRepaymentRecordController extends BaseController {

	private final static Logger logger = Logger.getLogger(BRepaymentRecordController.class);

	@Autowired
	private BRepaymentRecordService bRepaymentRecordService;
	@Autowired
	private BorrowService borrowService;

	/**
	 * <p>
	 * Description:根据当前用户分页查询待还记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年3月3日
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/forrepaymenetlist/{pageNo}")
	public ModelAndView forrepaymenetlist(@PathVariable Integer pageNo) {
		ModelAndView mav = new ModelAndView("borrow/repaymentlist");
		ShiroUser shiroUser = currentUser();
		try {
			Page page = bRepaymentRecordService.queryRepaymentsByUserId(shiroUser.getUserId(), new Page(pageNo, BusinessConstants.DEFAULT_PAGE_SIZE));
			mav.addObject("page", page);
		} catch (Exception e) {
			logger.error("根据当前用户分页查询待还记录出错", e);
		}
		return mav;
	}

	/**
	 * 
	 * <p>
	 * Description:进入待还列表页面<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年9月5日
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception ModelAndView
	 */
	// 判断是否登录 
	@RequiresAuthentication
	@RequestMapping(value = "/enterRepayMent")
	public ModelAndView enterRepayMent(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("account/borrow/repayment/repayment-main");
		return mav;
	}

	/**
	 * 
	 * <p>
	 * Description:查询待还记录<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月19日
	 * @param session
	 * @param request
	 * @param pPageNum
	 * @param pageSize
	 * @return
	 * @throws Exception String
	 */
	// 判断是否登录 
	@RequiresAuthentication
	@RequestMapping(value = "queryRepaymentDetails")
	public ModelAndView repayment_record(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("account/borrow/repayment/repayment_record");
		ShiroUser shiroUser = currentUser();

		// 设置菜单名
		mav.addObject(BusinessConstants.ACCOUNT_FIRST_MENU, BusinessConstants.LEFT_MENU_RZ);
		mav.addObject(BusinessConstants.ACCOUNT_SECOND_MENU, BusinessConstants.LEFT_MENU_RZ_REPAYMENT);

		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String keyword = request.getParameter("keyword");
		String pageNum_str = request.getParameter("pageNum");
		String pageSize_str = request.getParameter("pageSize");
		String status = request.getParameter("status");

		int pageNum = 1;
		if (pageNum_str != null && !pageNum_str.equals("")) {
			pageNum = Integer.parseInt(pageNum_str);
		}
		int pageSize = 10;
		if (pageSize_str != null && !pageSize_str.equals("")) {
			pageSize = Integer.parseInt(pageSize_str);
		}
		if (keyword != null && !keyword.equals("")) {
			keyword = keyword.trim();
		}
		RepaymentRecordCnd repaymentRecordCnd = new RepaymentRecordCnd();
		repaymentRecordCnd.setUserId(shiroUser.getUserId());
		repaymentRecordCnd.setBorrowName(keyword);
		repaymentRecordCnd.setBeginTime(beginTime);
		repaymentRecordCnd.setEndTime(endTime);
		repaymentRecordCnd.setStatus(Integer.parseInt(status));

		Page page = bRepaymentRecordService.queryRepaymentList(repaymentRecordCnd, pageNum, pageSize);
		// 统计还款总额、本金总额、利息总额、罚息总额等数据项
		Map<String, Object> map = bRepaymentRecordService.repaymentRecordTongji(repaymentRecordCnd);
		request.setAttribute("page", page);
		request.setAttribute("beginTime", beginTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("keyword", keyword);
		request.setAttribute("status", status);
		request.setAttribute("map", map);
		return mav;
	}

	/**
	 * 
	 * <p>
	 * Description:还款计划<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月26日
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "searchRepaymentList/{borrowId}/{pageNo}")
	public ModelAndView searchRepaymentList(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer borrowId, @PathVariable Integer pageNo) {
		ModelAndView mv = new ModelAndView("investment/toInvest_repaymentPlan");
		try {
			if (null == borrowId || null == pageNo) {
				/** 报500错误 */
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return null;
			}
			RepaymentRecordCnd repaymentRecordCnd = new RepaymentRecordCnd();
			repaymentRecordCnd.setBorrowId(borrowId);
			repaymentRecordCnd.setOrderByOrder("ASC");// 还款计划按照order升序排列
			Page pgRepayment = bRepaymentRecordService.queryRepaymentList(repaymentRecordCnd, pageNo, BusinessConstants.DEFAULT_PAGE_SIZE);
			mv.addObject("page", pgRepayment);
		} catch (Exception e) {
			logger.error("进入我要投标详细页面_查询还款计划出错");
		}
		return mv;
	}

	/**
	 * 
	 * <p>
	 * Description:待还记录导出excel<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月19日
	 * @param request
	 * @param response void
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/exportRepaymentRecord")
	public void exportRepaymentRecord(HttpServletRequest request, HttpServletResponse response) {
		try {
			ShiroUser shiroUser = currentUser();
			String status = request.getParameter("status");
			String beginTime = request.getParameter("beginTime");
			String endTime = request.getParameter("endTime");
			String keyword = request.getParameter("keyword");

			RepaymentRecordCnd repaymentRecordCnd = new RepaymentRecordCnd();
			repaymentRecordCnd.setUserId(shiroUser.getUserId());
			repaymentRecordCnd.setBorrowName(keyword);
			repaymentRecordCnd.setBeginTime(beginTime);
			repaymentRecordCnd.setEndTime(endTime);
			repaymentRecordCnd.setStatus(Integer.parseInt(status));

			Page page = bRepaymentRecordService.queryRepaymentList(repaymentRecordCnd, 1, Integer.MAX_VALUE);

			List<BRepaymentRecordVo> list = (List<BRepaymentRecordVo>) page.getResult();

			Map dto = new HashMap();
			dto.put("reportTitle", "待还报表");
			dto.put("beginTime", beginTime);
			dto.put("endTime", endTime);
			dto.put("keyword", keyword);
			// 统计还款总额、本金总额、利息总额、罚息总额等数据项
			Map<String, Object> map = bRepaymentRecordService.repaymentRecordTongji(repaymentRecordCnd);
			if (map != null && map.get("repaymentAccountTotal") != null) {
				BigDecimal repaymentAccountTotal = new BigDecimal(map.get("repaymentAccountTotal").toString());
				dto.put("sumRepaymentAccountStr", String.valueOf(repaymentAccountTotal.setScale(2, BigDecimal.ROUND_DOWN)));
			} else {
				dto.put("sumRepaymentAccountStr", "0.00");
			}

			if (map != null && map.get("capitalTotal") != null) {
				BigDecimal capitalTotal = new BigDecimal(map.get("capitalTotal").toString());
				dto.put("sumCapitalStr", String.valueOf(capitalTotal.setScale(2, BigDecimal.ROUND_DOWN)));
			} else {
				dto.put("sumCapitalStr", "0.00");
			}

			if (map != null && map.get("interestTotal") != null) {
				BigDecimal interestTotal = new BigDecimal(map.get("interestTotal").toString());
				dto.put("sumInterestStr", String.valueOf(interestTotal.setScale(2, BigDecimal.ROUND_DOWN)));
			} else {
				dto.put("sumInterestStr", "0.00");
			}

			if (map != null && map.get("lateInterestTotal") != null) {
				BigDecimal lateInterestTotal = new BigDecimal(map.get("lateInterestTotal").toString());
				dto.put("sumLateInterestStr", String.valueOf(lateInterestTotal.setScale(2, BigDecimal.ROUND_DOWN)));
			} else {
				dto.put("sumLateInterestStr", "0.00");
			}
			ReportData reportData = new ReportData();
			reportData.setParametersDto(dto);
			reportData.setFieldsList(list);
			reportData.setReportFilePath("/report/exportExcel/frepaymentRecordExcel2.jasper");

			java.io.InputStream is = request.getSession().getServletContext().getResourceAsStream(reportData.getReportFilePath());
			// 这里可以传入pdf,excel,word,html,print导出各种类型文件
			JasperExportUtils.export(reportData.getFieldsList(), reportData.getParametersDto(), "excel", is, request, response, "待还报表" + DateUtils.format(new Date(), DateUtils.YMD));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<BRepaymentRecordVo> getRepaymentListForExport(List<BRepaymentRecordVo> list) throws Exception {
		for (BRepaymentRecordVo obj : list) {
			obj.setPeriodStr();
		}
		return list;
	}
}
