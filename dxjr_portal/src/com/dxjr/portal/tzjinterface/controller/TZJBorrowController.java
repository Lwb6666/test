package com.dxjr.portal.tzjinterface.controller;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.tzjinterface.constant.TZJConstants;
import com.dxjr.portal.tzjinterface.service.RequesturlLogService;
import com.dxjr.portal.tzjinterface.service.TransferBorrowService;
import com.dxjr.portal.tzjinterface.util.DES3;
import com.dxjr.portal.tzjinterface.util.ParseURLTool;
import com.dxjr.portal.tzjinterface.vo.BorrowStateChangedVo;
import com.dxjr.portal.tzjinterface.vo.InvitationBorrowVo;
import com.dxjr.portal.tzjinterface.vo.RequesturlLogVo;
import com.dxjr.portal.tzjinterface.vo.TenderRecordAggVo;
import com.dxjr.portal.tzjinterface.vo.TzjTenderRecordCnd;
import com.dxjr.portal.util.JsonUtils;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:投之家获取可投标信息控制器<br />
 * </p>
 * 
 * @title TZJBorrowController.java
 * @package com.dxjr.portal.tzjinterface.controller
 * @author hujianpan
 * @version 0.1 2014年10月16日
 */
@Controller
@RequestMapping(value = "/api/tzj")
public class TZJBorrowController {
	public Logger logger = Logger.getLogger(TZJBorrowController.class);

	@Autowired
	private RequesturlLogService requesturlLogService;
	@Autowired
	private TransferBorrowService borrowTransferLogService;

	/**
	 * 
	 * <p>
	 * Description:获取可投标列表信息<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年10月17日
	 * @param request
	 * @param session
	 * @param response
	 * @return String
	 */
	@RequestMapping(value = "/queryborrow")
	@ResponseBody
	public String queryBorrow(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		Integer newRequestLogId = null;// 新增访问日志主键
		MessageBox logRequestURLMsb = logRequestURL(new String(request.getRequestURL() + "?" + request.getQueryString()), "PULL");

		if (logRequestURLMsb.getCode() == "1") {
			newRequestLogId = Integer.valueOf(logRequestURLMsb.getMessage());
		} else {
			return JsonUtils.bean2Json(logRequestURLMsb);
		}
		// 解析请求参数Map
		Map<String, String> params = ParseURLTool.parseURLParameters(request);
		String timestamp=params.get("timestamp");
		// //构造解密后的参数Map
		Map<String, String> result = ParseURLTool.buildDecryptionParameter(params, TZJConstants.POST_URL, timestamp);
		
		// 查询可投标记录信息
		List<InvitationBorrowVo> resultBorrowTransferLogList = null;
		try {
			// //判断MD5值是否正确
			if (result.get("sign") == null) {
				response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				return JsonUtils.bean2Json(MessageBox.build("-1000", URLEncoder.encode("MD5校验失败","utf-8")));
			}
			//判断连接时间是否在5分钟之内，防止黑客侵入
			long diffTimestampAndNow=DateUtils.diffTimestampAndNow(timestamp);
			if(diffTimestampAndNow>300){
				response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				return JsonUtils.bean2Json(MessageBox.build("-1000", URLEncoder.encode("时间失效","utf-8")));
			}
			resultBorrowTransferLogList = borrowTransferLogService.TransferBorrow();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		String desStr=DES3.encryptModeTzj(JsonUtils.bean2Json(resultBorrowTransferLogList),timestamp);
		String tzjStr=JsonUtils.returnTzjForJson(desStr,timestamp);
		logger.info("传输数据为：   =============" + tzjStr);
		return tzjStr;
		
	}

	private MessageBox logRequestURL(String requestPath, String direction) {
		// 会话请求访问路径
		String urlStr = requestPath;
		RequesturlLogVo newRequesturlLogVo = new RequesturlLogVo();
		newRequesturlLogVo.setUrlString(urlStr);
		newRequesturlLogVo.setDirection(direction);
		newRequesturlLogVo.setDr(String.valueOf(1));
		newRequesturlLogVo.setIsSuccess(Integer.valueOf(1));
		try {
			requesturlLogService.insertRequestUrlLog(newRequesturlLogVo);
		} catch (AppException e1) {
			return MessageBox.build("5", "网络异常，请稍后重试！");
		}
		return MessageBox.build("1", String.valueOf(newRequesturlLogVo.getId()));
	}
	
	/**
	 * 获取投资记录
	 * @author WangQianJin
	 * @title @param request
	 * @title @param session
	 * @title @param response
	 * @title @return
	 * @date 2015年11月29日
	 */
	@RequestMapping(value = "/tenderRecord")
	@ResponseBody
	public String tenderRecord(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		Integer newRequestLogId = null;// 新增访问日志主键
		MessageBox logRequestURLMsb = logRequestURL(new String(request.getRequestURL() + "?" + request.getQueryString()), "PULL");

		if (logRequestURLMsb.getCode() == "1") {
			newRequestLogId = Integer.valueOf(logRequestURLMsb.getMessage());
		} else {
			return JsonUtils.bean2Json(logRequestURLMsb);
		}
		// 解析请求参数Map
		Map<String, String> params = ParseURLTool.parseURLParameters(request);
		String timestamp=params.get("timestamp");
		// //构造解密后的参数Map
		Map<String, String> result = ParseURLTool.buildDecryptionParameter(params, TZJConstants.POST_URL, timestamp);
		// 查询投资记录信息
		List<TenderRecordAggVo> tenderRecordList = null;
		try {
			// //判断MD5值是否正确
			if (result.get("sign") == null) {
				response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				return JsonUtils.bean2Json(MessageBox.build("-1000", URLEncoder.encode("MD5校验失败","utf-8")));
			}
			//判断连接时间是否在5分钟之内，防止黑客侵入
			long diffTimestampAndNow=DateUtils.diffTimestampAndNow(timestamp);
			if(diffTimestampAndNow>300){
				response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				return JsonUtils.bean2Json(MessageBox.build("-1000", URLEncoder.encode("时间失效","utf-8")));
			}
			
			TzjTenderRecordCnd tzjTenderRecordCnd=new TzjTenderRecordCnd();
			tzjTenderRecordCnd.setStartTime(result.get("startTime"));
			tzjTenderRecordCnd.setEndTime(result.get("endTime"));
			tzjTenderRecordCnd.setUsername(result.get("username"));
			tzjTenderRecordCnd.setUsernamep(result.get("usernamep"));
			tenderRecordList = borrowTransferLogService.queryTenderRecord(tzjTenderRecordCnd);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		//如果无记录，则返回NONE
		if(tenderRecordList==null || tenderRecordList.size()==0){
			String none=DES3.encryptModeTzj(TZJConstants.NONE,timestamp);
			return JsonUtils.returnTzjForJson(none,timestamp);
		}
		String tzjbeforeStr=JsonUtils.bean2Json(tenderRecordList);
		logger.info("加密前数据为：   =============" + tzjbeforeStr);
		String desStr=DES3.encryptModeTzj(tzjbeforeStr,timestamp);
		String tzjStr=JsonUtils.returnTzjForJson(desStr,timestamp);
		logger.info("传输数据为：   =============" + tzjStr);
		return tzjStr;
		
	}
	
	/**
	 * 获取标的状态
	 * @author WangQianJin
	 * @title @param request
	 * @title @param session
	 * @title @param response
	 * @title @return
	 * @date 2015年11月29日
	 */
	@RequestMapping(value = "/borrowState")
	@ResponseBody
	public String borrowState(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		Integer newRequestLogId = null;// 新增访问日志主键
		MessageBox logRequestURLMsb = logRequestURL(new String(request.getRequestURL() + "?" + request.getQueryString()), "PULL");

		if (logRequestURLMsb.getCode() == "1") {
			newRequestLogId = Integer.valueOf(logRequestURLMsb.getMessage());
		} else {
			return JsonUtils.bean2Json(logRequestURLMsb);
		}
		// 解析请求参数Map
		Map<String, String> params = ParseURLTool.parseURLParameters(request);
		String timestamp=params.get("timestamp");
		// //构造解密后的参数Map
		Map<String, String> result = ParseURLTool.buildDecryptionParameter(params, TZJConstants.POST_URL, timestamp);
		// 查询投资记录信息
		List<BorrowStateChangedVo> borrowStateList = null;
		try {
			// //判断MD5值是否正确
			if (result.get("sign") == null) {
				response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				return JsonUtils.bean2Json(MessageBox.build("-1000", URLEncoder.encode("MD5校验失败","utf-8")));
			}
			//判断连接时间是否在5分钟之内，防止黑客侵入
			long diffTimestampAndNow=DateUtils.diffTimestampAndNow(timestamp);
			if(diffTimestampAndNow>300){
				response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				return JsonUtils.bean2Json(MessageBox.build("-1000", URLEncoder.encode("时间失效","utf-8")));
			}			
			TzjTenderRecordCnd tzjTenderRecordCnd=new TzjTenderRecordCnd();
			tzjTenderRecordCnd.setStartTime(result.get("startTime"));
			tzjTenderRecordCnd.setEndTime(result.get("endTime"));
			tzjTenderRecordCnd.setBidStr(JsonUtils.getSqlStrByJson(result.get("bid")));
			tzjTenderRecordCnd.setType(result.get("type"));
			borrowStateList = borrowTransferLogService.queryStatusChangedBorrow(tzjTenderRecordCnd);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		//如果无记录，则返回NONE
		if(borrowStateList==null || borrowStateList.size()==0){
			String none=DES3.encryptModeTzj(TZJConstants.NONE,timestamp);
			return JsonUtils.returnTzjForJson(none,timestamp);
		}
		String desStr=DES3.encryptModeTzj(JsonUtils.bean2Json(borrowStateList),timestamp);
		String tzjStr=JsonUtils.returnTzjForJson(desStr,timestamp);
		logger.info("传输数据为：   =============" + tzjStr);
		return tzjStr;
		
	}
}
