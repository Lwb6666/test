package com.dxjr.portal.report.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dxjr.common.report.JasperExportUtils;
import com.dxjr.common.report.ReportData;
import com.dxjr.portal.account.vo.CashRecordCnd;
import com.dxjr.portal.account.vo.CashRecordVo;
import com.dxjr.portal.account.vo.RechargeRecordCnd;
import com.dxjr.portal.account.vo.RechargeRecordVo;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.repayment.service.BRepaymentRecordService;
import com.dxjr.portal.report.service.CashReportService;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;

/**
 * <p>
 * Description:用户资金导出excel<br />
 * </p>
 * 
 * @title CashReportController.java
 * @package com.dxjr.portal.report.controller
 * @author justin.xu
 * @version 0.1 2014年6月10日
 */
@Controller
@RequestMapping(value = "/cashReport")
public class CashReportController extends BaseController {
	@Autowired
	private CashReportService cashReportService;
	@Autowired
	private BRepaymentRecordService bRepaymentRecordService;
	@Autowired
	private BorrowService borrowService;

	/**
	 * 当前用户的充值记录导出excel
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/exportRechargeRecord")
	public void exportRechargeRecord(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			ShiroUser shiroUser = currentUser();
			RechargeRecordCnd rechargeRecordCnd = new RechargeRecordCnd();
			rechargeRecordCnd.setUserId(shiroUser.getUserId());
			List<RechargeRecordVo> list = cashReportService
					.queryRechargeRecordList(rechargeRecordCnd);
			Map dto = new HashMap();
			dto.put("reportTitle", "账户充值报表");

			ReportData reportData = new ReportData();
			reportData.setParametersDto(dto);
			reportData.setFieldsList(list);
			reportData
					.setReportFilePath("/report/exportExcel/frechargeRecordExcel.jasper");

			java.io.InputStream is = request.getSession().getServletContext()
					.getResourceAsStream(reportData.getReportFilePath());
			// 这里可以传入pdf,excel,word,html,print导出各种类型文件
			JasperExportUtils.export(reportData.getFieldsList(),
					reportData.getParametersDto(), "excel", is, request,
					response,
					"账户充值报表" + DateUtils.format(new Date(), DateUtils.YMD));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Description:当前用户的提现记录导出excel<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年6月18日
	 * @param request
	 * @param response
	 *            void
	 */
	@RequestMapping(value = "/exportCashRecord")
	public void exportCashRecord(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			ShiroUser shiroUser = currentUser();
			CashRecordCnd cashRecordCnd = new CashRecordCnd();
			cashRecordCnd.setUserId(shiroUser.getUserId());
			List<CashRecordVo> list = cashReportService
					.queryCashRecordList(cashRecordCnd);

			Map dto = new HashMap();
			dto.put("reportTitle", "账户提现报表");

			ReportData reportData = new ReportData();
			reportData.setParametersDto(dto);
			reportData.setFieldsList(list);
			reportData
					.setReportFilePath("/report/exportExcel/fcashReportExcel.jasper");

			java.io.InputStream is = request.getSession().getServletContext()
					.getResourceAsStream(reportData.getReportFilePath());
			// 这里可以传入pdf,excel,word,html,print导出各种类型文件
			JasperExportUtils.export(reportData.getFieldsList(),
					reportData.getParametersDto(), "excel", is, request,
					response,
					"账户提现报表" + DateUtils.format(new Date(), DateUtils.YMD));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
