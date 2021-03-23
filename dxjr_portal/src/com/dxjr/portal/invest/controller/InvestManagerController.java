package com.dxjr.portal.invest.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.Borrow;
import com.dxjr.common.BorrowXiyi;
import com.dxjr.common.page.Page;
import com.dxjr.common.report.JasperExportUtils;
import com.dxjr.common.report.ReportData;
import com.dxjr.portal.base.BaseController;
import com.dxjr.portal.borrow.entity.TenderRecord;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.service.TendRecordService;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.borrow.vo.TenderRecordVo;
import com.dxjr.portal.collection.vo.BCollectionRecordVo;
import com.dxjr.portal.first.service.FirstBorrowService;
import com.dxjr.portal.first.service.FirstTenderDetailService;
import com.dxjr.portal.first.service.FirstTenderLogService;
import com.dxjr.portal.first.service.FirstTenderRealService;
import com.dxjr.portal.first.vo.FirstBorrowCnd;
import com.dxjr.portal.first.vo.FirstBorrowVo;
import com.dxjr.portal.first.vo.FirstTenderDetailCnd;
import com.dxjr.portal.first.vo.FirstTenderLogCnd;
import com.dxjr.portal.first.vo.FirstTenderLogVo;
import com.dxjr.portal.first.vo.FirstTenderRealCnd;
import com.dxjr.portal.first.vo.FirstTenderRealVo;
import com.dxjr.portal.invest.service.CollectionRecordService;
import com.dxjr.portal.invest.service.CollectionStatisticService;
import com.dxjr.portal.invest.service.InvestRecordService;
import com.dxjr.portal.invest.service.impl.InvestRecordServiceImpl;
import com.dxjr.portal.invest.vo.CollectionStatisticVo;
import com.dxjr.portal.invest.vo.CommonCollectionVo;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.member.service.RealNameApproService;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.MobileApproVo;
import com.dxjr.portal.member.vo.RealNameApproVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.util.ChangeCapitalMoney;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;

import freemarker.cache.StrongCacheStorage;

/***
 * 投資管理控制器
 * 
 * @author 胡建盼
 * @since
 * 
 */
@Controller
@RequestMapping(value = "/account/InvestManager")
public class InvestManagerController extends BaseController {
	public Logger logger = Logger.getLogger(InvestManagerController.class);
	@Autowired
	private CollectionRecordService collectionRecordServiceImpl;
	@Autowired
	private InvestRecordService investRecordService;
	@Autowired
	BorrowService borrowServiceImpl;
	@Autowired
	MemberService memberServiceImpl;
	@Autowired
	FirstTenderRealService firstTenderRealServiceImpl;
	@Autowired
	FirstBorrowService firstBorrowServiceImpl;
	@Autowired
	RealNameApproService realNameApproServiceImpl;
	@Autowired
	MobileApproService mobileApproServiceImpl;
	@Autowired
	FirstTenderDetailService firstTenderDetailServiceImpl;
	@Autowired
	CollectionStatisticService collectionStatisticServiceImpl;
	@Autowired
	private FirstTenderLogService firstTenderLogService;
	
	@Autowired
	private TendRecordService tendRecordService;

	@RequiresAuthentication
	@RequestMapping(value = "queryTendeIndex")
	public ModelAndView queryTendeIndex(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/account/borrow/InvestManagerTenderOuter");
		return mv;
	}

	/**
	 * 
	 * <p>
	 * Description: 投资管理===正在投标中列表==即当前用户投资其他正在借款标的借款标信息<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年9月3日
	 * @param session
	 * @param request
	 * @param pPageNum
	 * @param pageSize
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "queryTenderingForOtherBorrow/{pageNo}")
	public ModelAndView queryTenderingForOtherBorrow(HttpSession session, HttpServletRequest request, @PathVariable Integer pageNo) {		
		ModelAndView mv = new ModelAndView();
		mv = new ModelAndView("/account/borrow/InvestManagerTenderInner");
		
		
		String beginTime = request.getParameter("beginTime");  //开始时间
		String endTime = request.getParameter("endTime");   //结束时间
		String borrowName = request.getParameter("borrowName");
		String borrowStatus = request.getParameter("borrowStatus");  //状态 
		String custody = request.getParameter("custody"); //存管方式   0：非存管，1：浙商存管
		String timeScope = request.getParameter("timeScope"); //时间范围  
		
		if("month".equals(timeScope)){
			 endTime = DateUtils.getSysdate();
			 beginTime =  DateUtils.format(DateUtils.monthOffset(new Date(),-1),DateUtils.YMD_DASH);
		} else if("threemonth".equals(timeScope)){
			 endTime = DateUtils.getSysdate();
			 beginTime =  DateUtils.format(DateUtils.monthOffset(new Date(),-3),DateUtils.YMD_DASH);
		} else if("sixmonth".equals(timeScope)){
			endTime = DateUtils.getSysdate();
			beginTime =  DateUtils.format(DateUtils.monthOffset(new Date(),-6),DateUtils.YMD_DASH);
		} else if("all".equals(timeScope)){
			endTime = "";
			beginTime = "";
		}

//		if (borrowStatus == null || "underway".equals(borrowStatus)) {
//			mv = new ModelAndView("/account/borrow/InvestManagerTenderInner");
//		} else {
//			mv = new ModelAndView("/account/borrow/InvestManagerTenderSuccess");
//		}
		
		ShiroUser shiroUser = currentUser();

		Integer pageNum = pageNo;
		Integer pageSize = BusinessConstants.DEFAULT_PAGE_SIZE;
		if (pageNum == null) {
			pageNum = 0;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("borrowName", borrowName);
		map.put("userId", shiroUser.getUserId());
		map.put("custody", custody);
		map.put("borrowStatus", borrowStatus);

		//convertFormateDateToSec(map);

		Page p = new Page();
		try {
			p = borrowServiceImpl.queryTenderingForOtherBorrowNew(map, new Page(pageNum, pageSize));
		} catch (Exception e) {
			logger.error("查询正在招标出错");
			e.printStackTrace();
		}
		request.setAttribute("beginTime", beginTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("borrowName", borrowName);
		request.setAttribute("page", p);
		request.setAttribute("borrowlist", p.getResult());
		request.setAttribute("custody", custody);
		request.setAttribute("timeScope", timeScope);
		request.setAttribute("borrowStatus", borrowStatus);
		return mv;
	}

	private void convertFormateDateToSec(Map<String, Object> parameter) {
		String beginTime = (String) parameter.get("beginTime");
		String endTime = (String) parameter.get("endTime");
		// 时间参数格式转换
		if (beginTime != null && !"".equals(beginTime)) {
			beginTime = beginTime + " 00:00:00";
			parameter.put("beginTime", beginTime);
		}
		if (endTime != null && !"".equals(endTime)) {
			endTime = DateUtils.format(com.dxjr.utils.DateUtils.dayOffset(DateUtils.parse(endTime, DateUtils.YMD_DASH), 1), DateUtils.YMD_DASH) + " 00:00:00";
			parameter.put("endTime", endTime);
		}
		// end transTime
	}

	/** 进入待收页面--普通和直通车待收 */
	// 判断是否登录 
	@RequiresAuthentication
	@RequestMapping(value = "collectionindex/{collection_type}")
	public ModelAndView forCollectionRecord(@PathVariable String collection_type) { // : c--普通待收 
																					// z--直通车待收
		if("c".equals(collection_type)){
			ModelAndView mav = new ModelAndView("/account/borrow/collectionPlainIndex");
			return mav.addObject("collection_type", collection_type);// collection_type
		} else {
			ModelAndView mav = new ModelAndView("/account/borrow/collection_index");
			return mav.addObject("collection_type", collection_type);// collection_type
		}													
	}

	/** 进入投标直通车列表 */
	@RequiresAuthentication
	@RequestMapping(value = "firsttenderindex")
	public ModelAndView forFirstTenderindex() {
		return forword("/account/first/firstTender_index");
	}

	/**
	 * <p>
	 * Description:待收列表，包含普通待收和投标直通车待收。<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年8月13日
	 * @param session
	 * @param request
	 * @param pageSize
	 * @param type_collection
	 * @param status_type
	 * @param pageNum
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "collection_record/{status_type}/{type_collection}/{pageNum}")
	public ModelAndView collection_record(HttpSession session, HttpServletRequest request, @PathVariable String type_collection, @PathVariable String status_type, @PathVariable int pageNum) {
		ModelAndView mv = new ModelAndView("/account/borrow/collection_record"); // 普通待收页面
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String keyword = request.getParameter("keyword");
		String pageNum_str = request.getParameter("pageNum");
		// String status_type = request.getParameter("status_type");
		if (BusinessConstants.COLLECTION_TYPE_FIST.equals(type_collection)) { // 投标直通车待收页面
			mv = new ModelAndView("/account/borrow/z_collection_record");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", shiroUser.getUserId());
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("keyword", keyword);
		map.put("status_type", status_type);
		map.put("type_collection", type_collection);

		Page p = collectionRecordServiceImpl.queryMyCollections(map, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE));
		List<CommonCollectionVo> listReturn = new ArrayList<CommonCollectionVo>();
		// 根据代收记录状态计算罚息
		listReturn = collectionRecordServiceImpl.calculatedLateInterest(p, listReturn);
		Map<Integer, BorrowVo> borrowMap = new HashMap<Integer, BorrowVo>();
		borrowMap = collectionListTransitionMap((List<CommonCollectionVo>) p.getResult());
		request.setAttribute("borrowMap", borrowMap);
		request.setAttribute("CollectionRecordList", listReturn);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pageSize", BusinessConstants.DEFAULT_PAGE_SIZE);
		request.setAttribute("totalCount", p.getTotalCount());
		request.setAttribute("totalPage", p.getTotalPage());

		request.setAttribute("beginTime", beginTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("keyword", keyword);
		request.setAttribute("status_type", status_type);
		request.setAttribute("type_collection", type_collection);

		return mv.addObject("borrowMap", borrowMap).addObject("CollectionRecordList", listReturn).addObject("page", p);
	}

	/**
	 * <p>
	 * Description:普通待收-待收明細。<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年8月13日
	 * @param session
	 * @param request
	 * @param pageSize
	 * @param type_collection
	 * @param status_type
	 * @param pageNum
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "unCollection_record/{status_type}/{type_collection}/{pageNum}")
	public ModelAndView unCollection_record(HttpSession session, HttpServletRequest request, @PathVariable String type_collection, @PathVariable String status_type, @PathVariable int pageNum) {
		ModelAndView mv = new ModelAndView("/account/borrow/uncollection_record"); // 普通待收页面
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String keyword = request.getParameter("keyword");
		String timeScope = request.getParameter("timeScope");
		String custody = request.getParameter("custody");  //存管方式(0：非存管，1：浙商存管)
		
		
		if("month".equals(timeScope)){
			beginTime = DateUtils.getSysdate();
			 endTime =  DateUtils.format(DateUtils.monthOffset(new Date(),1),DateUtils.YMD_DASH);
		} else if("threemonth".equals(timeScope)){
			beginTime = DateUtils.getSysdate();
			endTime =  DateUtils.format(DateUtils.monthOffset(new Date(),3),DateUtils.YMD_DASH);
		} else if("sixmonth".equals(timeScope)){
			beginTime = DateUtils.getSysdate();
			endTime =  DateUtils.format(DateUtils.monthOffset(new Date(),6),DateUtils.YMD_DASH);
		} else if("all".equals(timeScope)){
			beginTime = "";
			endTime = "";
		}
		
		String pageNum_str = request.getParameter("pageNum");
		// String status_type = request.getParameter("status_type");
		if (BusinessConstants.COLLECTION_TYPE_FIST.equals(type_collection)) { // 投标直通车待收页面
			mv = new ModelAndView("/account/borrow/z_uncollection_record");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", shiroUser.getUserId());
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("keyword", keyword);
		map.put("status_type", status_type);
		map.put("type_collection", type_collection);
		map.put("iscustody", custody);

		Page p = collectionRecordServiceImpl.queryMyCollections(map, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE));
		List<CommonCollectionVo> listReturn = new ArrayList<CommonCollectionVo>();
		listReturn = collectionRecordServiceImpl.calculatedLateInterest(p, listReturn);
		// 根据代收记录状态计算罚息
		CollectionStatisticVo collectionStatisticVo = new CollectionStatisticVo();
		if ((null != beginTime && !"".equals(beginTime) && null != endTime && !"".equals(endTime)) || (null != keyword && !"".equals(keyword))) {
			/** 统计待收记录信息---有查询条件时才统计 */
			collectionStatisticVo = collectionStatisticServiceImpl.countCollectionMoney(map);
			List<CommonCollectionVo> forstatisticCommonCollections = collectionRecordServiceImpl.queryMyCollections(true, map);
			collectionRecordServiceImpl.calculatedLateInterest(null, forstatisticCommonCollections);
			BigDecimal lateInterestSum = BigDecimal.ZERO;
			for (CommonCollectionVo collectionVo : forstatisticCommonCollections) {
				lateInterestSum = lateInterestSum.add(collectionVo.getLateInterest());
			}
			// 没有待收
			if (null != collectionStatisticVo) {
				collectionStatisticVo.setLateInterestSum(lateInterestSum);
				collectionStatisticVo.setIsPay(Integer.valueOf(status_type));
			} else {
				collectionStatisticVo = new CollectionStatisticVo();// 把统计数据默认设置为
																	// 0,
			}

		}

		Map<Integer, BorrowVo> borrowMap = new HashMap<Integer, BorrowVo>();
		borrowMap = collectionListTransitionMap((List<CommonCollectionVo>) p.getResult());
		request.setAttribute("borrowMap", borrowMap);
		request.setAttribute("CollectionRecordList", listReturn);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pageSize", BusinessConstants.DEFAULT_PAGE_SIZE);
		request.setAttribute("totalCount", p.getTotalCount());
		request.setAttribute("totalPage", p.getTotalPage());

		request.setAttribute("beginTime", beginTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("keyword", keyword);
		request.setAttribute("status_type", status_type);
		request.setAttribute("type_collection", type_collection);
		request.setAttribute("timeScope", timeScope);
		request.setAttribute("custody", custody);

		return mv.addObject("borrowMap", borrowMap).addObject("CollectionRecordList", listReturn).addObject("page", p).addObject("collectionStatisticVo", collectionStatisticVo);
	}

	/**
	 * <p>
	 * Description:普通待收-已收明細。<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年8月13日
	 * @param session
	 * @param request
	 * @param pageSize
	 * @param type_collection
	 * @param status_type
	 * @param pageNum
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "receivedCollection_record/{status_type}/{type_collection}/{pageNum}")
	public ModelAndView receivedCollection_record(HttpSession session, HttpServletRequest request, @PathVariable String type_collection, @PathVariable String status_type, @PathVariable int pageNum) {
		ModelAndView mv = new ModelAndView("/account/borrow/receivedcollection_record"); // 普通待收页面
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String keyword = request.getParameter("keyword");
		
		String timeScope = request.getParameter("timeScope");
		String custody = request.getParameter("custody");  //存管方式(0：非存管，1：浙商存管)
		
		if("month".equals(timeScope)){
			 endTime = DateUtils.getSysdate();
			 beginTime =  DateUtils.format(DateUtils.monthOffset(new Date(),-1),DateUtils.YMD_DASH);
		} else if("threemonth".equals(timeScope)){
			 endTime = DateUtils.getSysdate();
			 beginTime =  DateUtils.format(DateUtils.monthOffset(new Date(),-3),DateUtils.YMD_DASH);
		} else if("sixmonth".equals(timeScope)){
			endTime = DateUtils.getSysdate();
			beginTime =  DateUtils.format(DateUtils.monthOffset(new Date(),-6),DateUtils.YMD_DASH);
		} else if("all".equals(timeScope)){
			endTime = "";
			beginTime = "";
		}
		
		String pageNum_str = request.getParameter("pageNum");
		// String status_type = request.getParameter("status_type");
		if (BusinessConstants.COLLECTION_TYPE_FIST.equals(type_collection)) { // 投标直通车待收页面
			mv = new ModelAndView("/account/borrow/z_receivedcollection_record");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", shiroUser.getUserId());
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("keyword", keyword);
		map.put("status_type", status_type);
		map.put("type_collection", type_collection);
		map.put("iscustody", custody);

		Page p = collectionRecordServiceImpl.queryMyCollections(map, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE));
		List<CommonCollectionVo> listReturn = new ArrayList<CommonCollectionVo>();
		// 根据代收记录状态计算罚息
		listReturn = collectionRecordServiceImpl.calculatedLateInterest(p, listReturn);
		// 根据代收记录状态计算罚息
		CollectionStatisticVo collectionStatisticVo = new CollectionStatisticVo();
		/** 统计待收记录信息---有查询条件时才统计 */
		if ((null != beginTime && !"".equals(beginTime) && null != endTime && !"".equals(endTime)) || (null != keyword && !"".equals(keyword))) {
			collectionStatisticVo = collectionStatisticServiceImpl.countCollectionMoney(map);
			List<CommonCollectionVo> forstatisticCommonCollections = collectionRecordServiceImpl.queryMyCollections(true, map);
			collectionRecordServiceImpl.calculatedLateInterest(null, forstatisticCommonCollections);
			BigDecimal lateInterestSum = BigDecimal.ZERO;
			for (CommonCollectionVo collectionVo : forstatisticCommonCollections) {
				lateInterestSum = lateInterestSum.add(collectionVo.getLateInterest());
			}
			// 没有待收
			if (null != collectionStatisticVo) {
				collectionStatisticVo.setLateInterestSum(lateInterestSum);
				collectionStatisticVo.setIsPay(Integer.valueOf(status_type));
			} else {
				collectionStatisticVo = new CollectionStatisticVo();
			}
		}

		Map<Integer, BorrowVo> borrowMap = new HashMap<Integer, BorrowVo>();
		borrowMap = collectionListTransitionMap((List<CommonCollectionVo>) p.getResult());
		request.setAttribute("borrowMap", borrowMap);
		request.setAttribute("CollectionRecordList", listReturn);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pageSize", BusinessConstants.DEFAULT_PAGE_SIZE);
		request.setAttribute("totalCount", p.getTotalCount());
		request.setAttribute("totalPage", p.getTotalPage());

		request.setAttribute("beginTime", beginTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("keyword", keyword);
		request.setAttribute("status_type", status_type);
		request.setAttribute("type_collection", type_collection);
		request.setAttribute("timeScope", timeScope);
		request.setAttribute("custody", custody);
		
		return mv.addObject("borrowMap", borrowMap).addObject("CollectionRecordList", listReturn).addObject("page", p).addObject("collectionStatisticVo", collectionStatisticVo);
	}

	/**
	 * 根据待还记录查询借款标并转换成Map
	 * 
	 */
	private Map<Integer, BorrowVo> collectionListTransitionMap(List<CommonCollectionVo> list) {
		Map<Integer, BorrowVo> map = new HashMap<Integer, BorrowVo>();
		for (CommonCollectionVo bcr : list) {

			map.put(bcr.getTenderId(), bcr.getBorrowVo());
		}
		return map;
	}

	/**
	 * 当前用户的待收记录导出excel
	 * 
	 * @param request
	 * @return
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/exportCollectionRecord/{status_type}/{type_collection}")
	public void exportCollectionRecord(HttpServletRequest request, HttpServletResponse response, @PathVariable String type_collection, @PathVariable String status_type) {
		// excel文件名
		String fileName = "普通待收记录";

		try {
			ShiroUser shiroUser = currentUser();
			if (shiroUser == null) {
				forword(BusinessConstants.NO_PAGE_FOUND_404);
			}
			String beginTime = request.getParameter("beginTime");
			String endTime = request.getParameter("endTime");
			String keyword = request.getParameter("keyword");
			// String status_type = request.getParameter("status_type");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("beginTime", beginTime);
			map.put("endTime", endTime);
			map.put("keyword", keyword);
			map.put("status_type", status_type);
			map.put("type_collection", type_collection);
			map.put("user_id", shiroUser.getUserId());

			/** false 表示参数需要转换--格式化时间参数 */
			List<CommonCollectionVo> collectionList = collectionRecordServiceImpl.queryMyCollections(false, map);
			collectionList = collectionRecordServiceImpl.calculatedLateInterest(null, collectionList);
			Map dto = new HashMap();
			dto.put("reportTitle", "待收记录");
			dto.put("beginTime", beginTime);
			dto.put("endTime", endTime);
			dto.put("keyword", keyword);

			ReportData reportData = new ReportData();
			reportData.setParametersDto(dto);
			reportData.setFieldsList(collectionList);
			if (null != status_type && "0".equals(status_type)) {// 待收记录
				if (type_collection != null && type_collection.equals(BusinessConstants.COLLECTION_TYPE_FIST)) { // 投标直通车待收页面
					fileName = "直通车待收记录";

					reportData.setReportFilePath("/report/exportExcel/fcollectionFirstRecordExcel.jasper");
				} else {
					fileName = "普通待收记录";
					reportData.setReportFilePath("/report/exportExcel/fcollectionRecordExcel.jasper");
				}
			} else if (null != status_type && "1".equals(status_type)) {// 已收记录
				if (type_collection != null && type_collection.equals(BusinessConstants.COLLECTION_TYPE_FIST)) { // 投标直通车待收页面
					fileName = "直通车待收记录";

					reportData.setReportFilePath("/report/exportExcel/fcollectionFirstRecordExcel_rec.jasper");
				} else {
					fileName = "普通待收记录";
					reportData.setReportFilePath("/report/exportExcel/fcollectionRecordExcel_rec.jasper");
				}
			}

			java.io.InputStream is = request.getSession().getServletContext().getResourceAsStream(reportData.getReportFilePath());
			// 这里可以传入pdf,excel,word,html,print导出各种类型文件
			JasperExportUtils.export(reportData.getFieldsList(), reportData.getParametersDto(), "excel", is, request, response, fileName + DateUtils.format(new Date(), DateUtils.YMD));
		} catch (Exception e) {
			forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
	}

	/**
	 * <p>
	 * Description:查看借款标协议<br />
	 * 自己查看-显示全名称<br />
	 * 普通待收,普通已收<br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年6月3日
	 * @param request
	 * @param borrow_id
	 * @return
	 * ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/toBorrowXiyi/{borrow_id}")
	public ModelAndView toBorrowXiyi(HttpServletRequest request, @PathVariable int borrow_id) {
		ModelAndView mv = new ModelAndView("/account/borrow/borrow_xiyi");
		try {
			// fateson 2015年4月23日 update start 安全渗透测试报告，只能看自己投资过标的协议
			ShiroUser shiroUser = currentUser();
			Integer userId = shiroUser.getUserId();
			Map<String, Integer> dto = new HashMap<String, Integer>();
			dto.put("userId", userId);
			dto.put("borrowId", borrow_id);
			Boolean result = collectionRecordServiceImpl.existsCollectionRecordByCnd(dto);
			if (result) {
				return redirect("/member/toLoginPage.html");
			}
			// fateson end

			//查看借款标协议-自己查看显示全名称（普通待收）
			String content = investRecordService.queryBorrowProtocol(borrow_id, request.getContextPath(), userId);
			mv.addObject("content", content);
		} catch (Exception e) {
			logger.error("查看借款标协议出错", e);
		}
		return mv;
	}

	/**
	 * <p>
	 * Description: 债权转让协议- 自己查看全名称 <br />
	 * 普待1</br>
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年6月2日
	 * @param request
	 * @param collectionId
	 * @return
	 * ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/toTransferXiyi/{collectionId}")
	public ModelAndView toTransferXiyi(HttpServletRequest request, @PathVariable int collectionId) {
		ModelAndView mv = new ModelAndView("/account/borrow/borrow_xiyi");
		try {
			ShiroUser shiroUser = currentUser();
			Integer userId = shiroUser.getUserId();
			String content = investRecordService.queryTransferXiyiContent(collectionId, request.getContextPath(),userId);
			mv.addObject("content", content);
		} catch (Exception e) {
			logger.error("查看债转协议出错", e);
		}
		return mv;
	}

	/***
	 * 
	 * <p>
	 * Description:借款标合同hucuo<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年8月12日
	 * @param request
	 * @param response
	 *            void
	 */
	@RequestMapping(value = "/agreementDownLoad/{borrow_id}")
	public void agreementDownLoad(HttpServletRequest request, HttpServletResponse response, @PathVariable int borrow_id) {

	}

	/**
	 * 
	 * <p>
	 * Description:优先计划最终开通信息列表<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年8月12日
	 * @param session
	 * @param request
	 * @param pageSize
	 * @param pageNum
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/queryFirstTenderReal/{pageNum}")
	public ModelAndView queryFirstTenderReal(HttpSession session, HttpServletRequest request, @PathVariable int pageNum) {

		ModelAndView mv = new ModelAndView("/account/first/first_tender_real");

		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		
		String timeScope = request.getParameter("timeScope");
		if("month".equals(timeScope)){
			 endTime = DateUtils.getSysdate();
			 beginTime =  DateUtils.format(DateUtils.monthOffset(new Date(),-1),DateUtils.YMD_DASH);
		} else if("sixmonth".equals(timeScope)){
			endTime = DateUtils.getSysdate();
			beginTime =  DateUtils.format(DateUtils.monthOffset(new Date(),-6),DateUtils.YMD_DASH);
		} else if("sixmonthago".equals(timeScope)){
			beginTime = "";
			endTime =  DateUtils.format(DateUtils.monthOffset(new Date(),-6),DateUtils.YMD_DASH);
		}
		
		String status = request.getParameter("status");
		if (pageNum <= 0) {
			pageNum = Integer.parseInt("1");
		}
		FirstTenderRealCnd firstTenderRealCnd = new FirstTenderRealCnd();
		firstTenderRealCnd.setUserId(shiroUser.getUserId());
		if (status != null && !status.equals("")) {
			firstTenderRealCnd.setStatus(Integer.parseInt(status));
//			if (status.equals("1")) {
//				mv = new ModelAndView("/account/first/first_tender_real_yes");
//			}
		}
		
		
		if (beginTime != null && !beginTime.equals("")) {
			firstTenderRealCnd.setBeginTime(DateUtils.parse(beginTime, DateUtils.YMD_DASH));
		}
		if (endTime != null && !endTime.equals("")) {
			firstTenderRealCnd.setEndTime(DateUtils.parse(endTime + " 23:59:59", DateUtils.YMD_HMS));
		}
		Page p = new Page();
		try {
			p = firstTenderRealServiceImpl.queryPageListByCnd(firstTenderRealCnd, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE));
		} catch (Exception e) {
			forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		request.setAttribute("beginTime", beginTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("status", status);
		request.setAttribute("timeScope", timeScope);

		request.setAttribute("list", p.getResult());
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pageSize", BusinessConstants.DEFAULT_PAGE_SIZE);
		request.setAttribute("totalCount", p.getTotalCount());
		request.setAttribute("totalPage", p.getTotalPage());

		return mv.addObject("page", p);
	}

	/**
	 * 
	 * <p>
	 * Description:查看直通车协议<br />
	 * </p>
	 * 
	 * @author hujianpan
	 * @version 0.1 2014年8月13日
	 * @param request
	 * @param firstTenderId
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/toFirstTenderXiyi/{firstTnederRealId}")
	public ModelAndView toFirstTenderXiyi(HttpServletRequest request, @PathVariable int firstTnederRealId) {
		logger.info("enter toFirstTenderXiyi method......");
		ModelAndView mv = new ModelAndView("/account/first/first_tender_xiyi");
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		Page p = new Page();
		try {
			FirstTenderRealCnd firstTenderRealCnd = new FirstTenderRealCnd();
			firstTenderRealCnd.setId(firstTnederRealId);
			firstTenderRealCnd.setUserId(shiroUser.getUserId());
			p = firstTenderRealServiceImpl.queryPageListByCnd(firstTenderRealCnd, new Page(1, 1));
		} catch (Exception e) {
			forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		if (p.getResult().size() > 0) {
			FirstTenderRealVo firstTenderReal = (FirstTenderRealVo) p.getResult().get(0);
			FirstBorrowCnd firstBorrowCnd = new FirstBorrowCnd();
			firstBorrowCnd.setId(firstTenderReal.getFirstBorrowId());
			FirstBorrowVo firstBorrow = new FirstBorrowVo();
			RealNameApproVo realNameAppro = new RealNameApproVo();
			MobileApproVo mobileAppro = new MobileApproVo();
			try {
				firstBorrow = firstBorrowServiceImpl.queryFirstBorrowByCnd(firstBorrowCnd);
				realNameAppro = realNameApproServiceImpl.queryRealNameApproByUserId(shiroUser.getUserId());
				mobileAppro = mobileApproServiceImpl.queryMobileApproByUserId(shiroUser.getUserId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			mv.addObject("subscribeMoney", ChangeCapitalMoney.digitUppercase(firstTenderReal.getAccount()));
			mv.addObject("firstTenderReal", firstTenderReal);
			mv.addObject("firstBorrow", firstBorrow);
			mv.addObject("realNameAppro", realNameAppro);
			mv.addObject("mobileAppro", mobileAppro);
			try {
				firstBorrow = firstBorrowServiceImpl.queryFirstBorrowByCnd(firstBorrowCnd);
				realNameAppro = realNameApproServiceImpl.queryRealNameApproByUserId(shiroUser.getUserId());
				mobileAppro = mobileApproServiceImpl.queryMobileApproByUserId(shiroUser.getUserId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			mv.addObject("member", shiroUser);
		}
		return mv;
	}

	/**
	 * 
	 * <p>
	 * Description:优先计划开通明细列表<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年8月13日
	 * @param session
	 * @param request
	 * @param pageSize
	 * @return String
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/queryFirstTenderDetail")
	public ModelAndView queryFirstTenderDetail(HttpSession session, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/account/first/first_tender_detail");
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			forword(BusinessConstants.NO_PAGE_FOUND_404);
		}

		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String keyword = request.getParameter("keyword");
		String firstTenderRealId = request.getParameter("firstTenderRealId");

		String pageNum_str = request.getParameter("pageNum");
		int pageNum = 1;
		if (pageNum_str != null && !pageNum_str.equals("")) {
			pageNum = Integer.parseInt(pageNum_str);
		}

		FirstTenderDetailCnd firstTenderDetailCnd = new FirstTenderDetailCnd();
		firstTenderDetailCnd.setFirstTenderRealId(Integer.valueOf(firstTenderRealId));

		firstTenderDetailCnd.setUserId(shiroUser.getUserId());

		Page p = new Page();
		try {
			p = firstTenderDetailServiceImpl.queryFirstTenderDetail(firstTenderDetailCnd, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE));
		} catch (Exception e) {
			forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		mv.addObject("beginTime", beginTime);
		mv.addObject("endTime", endTime);
		mv.addObject("keyword", keyword);
		mv.addObject("firstTenderRealId", firstTenderRealId);
		mv.addObject("page", p);
		mv.addObject("list", p.getResult());
		mv.addObject("pageNum", pageNum);
		mv.addObject("pageSize", BusinessConstants.DEFAULT_PAGE_SIZE);
		mv.addObject("totalCount", p.getTotalCount());
		mv.addObject("totalPage", p.getTotalPage());
		return mv;
	}

	/**
	 * 
	 * <p>
	 * Description:优先计划投的抵押标待收明细<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年8月13日
	 * @param session
	 * @param request
	 * @param pageSize
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "collectionFirst_record/{pageNum}")
	public ModelAndView collectionFirst_record(HttpSession session, HttpServletRequest request, @PathVariable int pageNum) {
		ModelAndView mv = new ModelAndView("/account/borrow/first_collection_record");
		String firstTenderRealId = request.getParameter("firstTenderRealId");
		String collectionStatus = request.getParameter("collectionStatus");
		if (null == firstTenderRealId || "".equals(firstTenderRealId)) {
			forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", shiroUser.getUserId());
		map.put("firstTenderRealId", firstTenderRealId);
		/**** rec:已还 unrec:未还 ***/
		map.put("collectionStatus", StringUtils.isEmpty(collectionStatus) ? "rec" : collectionStatus);

		Page p = collectionRecordServiceImpl.queryAccFirstCollections(map, new Page(pageNum, BusinessConstants.DEFAULT_PAGE_SIZE));
		List<CommonCollectionVo> listReturn = new ArrayList<CommonCollectionVo>();
		// 计算罚息
		collectionRecordServiceImpl.calculatedLateInterest(p, listReturn);
		Map<Integer, BorrowVo> borrowMap = new HashMap<Integer, BorrowVo>();
		borrowMap = collectionListTransitionMap((List<CommonCollectionVo>) p.getResult());
		return mv.addObject("borrowMap", borrowMap).addObject("CollectionRecordList", listReturn).addObject("recSelect", StringUtils.isEmpty(collectionStatus) ? "rec" : collectionStatus)
				.addObject("firstTenderRealId", firstTenderRealId).addObject("page", p);

	}

	/************************************************** begin private method ***********************************************************/

	/**
	 * <p>
	 * Description:生成借款标协议<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年8月12日
	 * @param request
	 * @param borrow_id
	 * @return String
	 */
	private String generateAgreemen(HttpServletRequest request, int borrow_id) {
		logger.info("enter generateAgreemen method ...");
		// 查询借款标
		Borrow borrow = new Borrow();
		// 发标人
		MemberVo borrowMember = new MemberVo();
		// 投标人
		List<TenderRecordVo> list = null;
		try {
			borrow = borrowServiceImpl.selectByPrimaryKey(borrow_id);
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(borrow.getUserId());
			borrowMember = memberServiceImpl.queryMemberByCnd(memberCnd);
			logger.info("befor enter investRecordService.queryTenderRecordByBorrowId method ...paramet =[" + borrow_id + "]");
			list = investRecordService.queryTenderRecordByBorrowId(Integer.valueOf(borrow_id));
			logger.info("after enter investRecordService.queryTenderRecordByBorrowId method ...paramet =[" + borrow_id + "]");
		} catch (Exception e) {
			logger.error(e.getStackTrace());
			logger.error(e.getMessage());
			return "协议查看失败，请稍后...";
		}
		// 邮件内容
		String content = BorrowXiyi.getBorrowXiyi(request, borrow, list, borrowMember.getUsername());
		logger.info("leave generateAgreemen method ...");
		return content;
	}

	/********************************************* end private method ******************************************************************/

	/**
	 * 
	 * <p>
	 * Description:查询直通车投标日志记录（分页）<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年3月11日
	 * @param request
	 * @param response
	 * @param pageNo
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/queryFirstTenderLog/{pageNo}")
	public ModelAndView queryList(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer pageNo) {
		ModelAndView mv = new ModelAndView("account/first/first_tender_log");
		ShiroUser shiroUser = currentUser();
		FirstTenderLogCnd firstTenderLogCnd = new FirstTenderLogCnd();
		// 设置为当前用户
		firstTenderLogCnd.setUserId(shiroUser.getUserId());
		firstTenderLogCnd.setStatus("1");
		firstTenderLogCnd.setOrderSql(" ORDER BY tl.addtime desc");
		Page page;
		try {
			page = firstTenderLogService.queryPageListByCnd(firstTenderLogCnd, new Page(pageNo, BusinessConstants.DEFAULT_PAGE_SIZE));
			mv.addObject("page", page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询直通车投标日志记录失败！");
		}

		return mv;
	}

	/**
	 * 
	 * <p>
	 * Description:查询直通车投标日志记录详情<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年3月11日
	 * @param request
	 * @param id
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/queryFirstTenderLogDetail/{id}")
	public ModelAndView queryAutoInvestConfigRecordDetail(HttpServletRequest request, @PathVariable Integer id) {
		ModelAndView mv = new ModelAndView("account/first/first_tender_log_detail");
		try {
			FirstTenderLogVo firstTenderLogVo = firstTenderLogService.queryById(id);
			mv.addObject("firstTenderLogVo", firstTenderLogVo);
			mv.addObject("pageNo", request.getParameter("pageNo"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * <p>
	 * Description:待收直通车转让协议<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年5月11日
	 * @param request
	 * @param collectionId
	 *            0:表示普通直通车转让协议；1：表示直通车转让协议
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/toFirstTransferXiyi/{collectionId}/{flag}")
	public ModelAndView toFirstTransferXiyi(HttpServletRequest request, @PathVariable int collectionId, @PathVariable int flag) {
		ModelAndView mv = new ModelAndView("/account/borrow/borrow_xiyi");
		ShiroUser shiroUser = currentUser();
		try {
			String content = investRecordService.queryFirstTransferXiyiContent(collectionId, request.getContextPath(), shiroUser,flag);
			mv.addObject("content", content);
		} catch (Exception e) {
			logger.error("查看直通车转让协议出错", e);
		}
		return mv;
	}

	/**
	 * <p>
	 * Description:查看直通车转让协议<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年4月11日
	 * @param request
	 * @param tenderRealId
	 * @return ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/toFirstRealTransferXiyi/{tenderRealId}")
	public ModelAndView toFirstRealTransferXiyi(HttpServletRequest request, @PathVariable int tenderRealId) {
		ModelAndView mv = new ModelAndView("/account/borrow/borrow_xiyi");
		try {
			ShiroUser shiroUser = currentUser();
			String content = investRecordService.queryFirstRealTransferXiyiContent(tenderRealId, shiroUser, request.getContextPath());
			mv.addObject("content", content);
		} catch (Exception e) {
			logger.error("查看直通车转让协议出错", e);
		}
		return mv;
	}
	
	
	/**
	 * <p>
	 * Description: 下载协议 <br />
	 * 普待待收-借款 <br/>
	 * 直-待收-借款 <br/>
	 * 直-已收-借款 <br/>
	 * 直-未还款 <br/>
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年6月3日
	 * @param request
	 * @param borrow_id
	 * @return
	 * ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/toBorrowXiyiDownLoad/{borrow_id}")
	public void toBorrowXiyiDownLoad(HttpServletRequest request,HttpServletResponse response, @PathVariable int borrow_id) {
		logger.info("下载协议-借款  start ");
		try {
			ShiroUser shiroUser = currentUser();
			Integer userId = shiroUser.getUserId();
			Map<String ,Object> reqMap = new HashMap<String ,Object>();
			String path = request.getContextPath();
			String port = request.getServerPort()+"";
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + port + path + "/";
			String realPath = request.getRealPath("/");
			//转换-匹配服务器路径
			String realPathNew = realPath.replaceAll("dxjr_portal","ROOT");
			reqMap.put("request", request);
			reqMap.put("response", response);
			reqMap.put("path", path);
			reqMap.put("port", port);
			reqMap.put("basePath", basePath);
			reqMap.put("realPath", realPath);
			reqMap.put("realPathNew", realPathNew);
			investRecordService.queryBorrowProtocolDload(borrow_id, request.getContextPath(), userId,reqMap);
			
		} catch (Exception e) {
			logger.error("下载借款协议出错", e);
		}
		logger.info("下载协议-借款  end ");
	}
	
	
	/**
	 * <p>
	 * Description:下载 - 债权转让协议<br />
	 * 普待待收-转让<br/>
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年6月4日
	 * @param request
	 * @param collectionId
	 * @return
	 * ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/toTransferXiyiDownLoad/{collectionId}")
	public void toTransferXiyiDownLoad(HttpServletRequest request, HttpServletResponse response, @PathVariable int collectionId) {
		logger.info("下载 - 债权转让协议MB-start ");
		try {
			ShiroUser shiroUser = currentUser();
			Integer userId = shiroUser.getUserId();
			Map<String, Object> reqMap = new HashMap<String, Object>();
			String path = request.getContextPath();
			String port = request.getServerPort() + "";
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + port + path + "/";
			String realPath = request.getRealPath("/");
			//转换-匹配服务器路径
			String realPathNew = realPath.replaceAll("dxjr_portal","ROOT");
			reqMap.put("request", request);
			reqMap.put("response", response);
			reqMap.put("path", path);
			reqMap.put("port", port);
			reqMap.put("basePath", basePath);
			reqMap.put("realPath", realPath);
			reqMap.put("realPathNew", realPathNew);
			investRecordService.queryTransferXiyiContentDownLoad(collectionId, request.getContextPath(), userId, reqMap);
		} catch (Exception e) {
			logger.error("下载债转协议出错", e);
		}
		logger.info("下载 - 债权转让协议MB-end ");
	}
	
	
	/**
	 * <p>
	 * Description:下载协议-直转让-待收<br />
	 * 直-已收 <br/>
	 * 普待-已收-转让 <br/>
	 * 普待-待收-转让<br/>
	 * 直-查看投标-未还款<br/>
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年6月8日
	 * @param request
	 * @param collectionId
	 * @param flag
	 * @return
	 * ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/toFirstTransferXiyiDownLoad/{collectionId}/{flag}")
	public void toFirstTransferXiyiDownLoad(HttpServletRequest request, HttpServletResponse response,@PathVariable int collectionId, @PathVariable int flag) {
		try {
			ShiroUser shiroUser = currentUser();
			Map<String, Object> reqMap = new HashMap<String, Object>();
			String path = request.getContextPath();
			String port = request.getServerPort() + "";
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + port + path + "/";
			String realPath = request.getRealPath("/");
			//转换-匹配服务器路径
			String realPathNew = realPath.replaceAll("dxjr_portal","ROOT");
			reqMap.put("request", request);
			reqMap.put("response", response);
			reqMap.put("path", path);
			reqMap.put("port", port);
			reqMap.put("basePath", basePath);
			reqMap.put("realPath", realPath);
			reqMap.put("realPathNew", realPathNew);
			
			//下载协议-直转让-待收
			investRecordService.queryFirstTransferXiyiContentDownLoad(collectionId, request.getContextPath(), shiroUser,flag,reqMap);
		} catch (Exception e) {
			logger.error("查看直通车转让协议出错", e);
		}
	}
	
	/**
	 * <p>
	 * Description:下载协议-直- 已解锁<br />
	 * 直-借款- 未解锁<br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年6月8日
	 * @param request
	 * @param response
	 * @param id
	 * void
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/toFirstTenderXiyiDownLoad/{firstTnederRealId}")
	public void toFirstTenderXiyiDownLoad(HttpServletRequest request, HttpServletResponse response, @PathVariable int firstTnederRealId) {
		try {
			ShiroUser shiroUser = currentUser();
			if (shiroUser == null) {
				forword(BusinessConstants.NO_PAGE_FOUND_404);
			}
			Map<String, Object> reqMap = new HashMap<String, Object>();
			String path = request.getContextPath();
			String port = request.getServerPort() + "";
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + port + path + "/";
			String realPath = request.getRealPath("/");
			//转换-匹配服务器路径
			String realPathNew = realPath.replaceAll("dxjr_portal","ROOT");
			reqMap.put("request", request);
			reqMap.put("response", response);
			reqMap.put("path", path);
			reqMap.put("port", port);
			reqMap.put("basePath", basePath);
			reqMap.put("realPath", realPath);
			reqMap.put("realPathNew", realPathNew);
			
			FirstTenderRealCnd firstTenderRealCnd = new FirstTenderRealCnd();
			firstTenderRealCnd.setId(firstTnederRealId);
			firstTenderRealCnd.setUserId(shiroUser.getUserId());
			investRecordService.toFirstTenderXiyiDownLoad(firstTenderRealCnd, shiroUser, reqMap);
			
		} catch (Exception e) {
			logger.error("下载直通车协议出错", e);
		}
		
	}
	
	
	/**
	 * <p>
	 * Description:下载协议-直- 转让- 已解锁 <br />
	 * 下载协议-直通车列表- 转让-未解锁 <br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年6月8日
	 * @param request
	 * @param response
	 * @param tenderRealId
	 * void
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/toFirstRealTransferXiyiDownLoad/{tenderRealId}")
	public void toFirstRealTransferXiyiDownLoad(HttpServletRequest request, HttpServletResponse response, @PathVariable int tenderRealId) {
		try {
			ShiroUser shiroUser = currentUser();
			if (shiroUser == null) {
				logger.error("用户未登录!");
				forword(BusinessConstants.NO_PAGE_FOUND_404);
			}
			Map<String, Object> reqMap = new HashMap<String, Object>();
			String path = request.getContextPath();
			String port = request.getServerPort() + "";
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + port + path + "/";
			String realPath = request.getRealPath("/");
			// 转换-匹配服务器路径
			String realPathNew = realPath.replaceAll("dxjr_portal", "ROOT");
			reqMap.put("request", request);
			reqMap.put("response", response);
			reqMap.put("path", path);
			reqMap.put("port", port);
			reqMap.put("basePath", basePath);
			reqMap.put("realPath", realPath);
			reqMap.put("realPathNew", realPathNew);

			investRecordService.queryFirstRealTransferXiyiContentDownLoad(tenderRealId, shiroUser, path, reqMap);

		} catch (Exception e) {
			logger.error("下载直通车协议出错", e);
		}
	}
	
	/**
	 * 根据借款标ID和投标记录ID查询待收记录
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author wushaoling
	 * @version 0.1 2016年5月30日
	 * @param request
	 * @param borrowId
	 * @param recordId
	 * @return
	 * ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/searchRecordList")
	public ModelAndView searchRecordList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		BorrowVo borrowVo = new BorrowVo();
		mv = new ModelAndView("/account/borrow/collectionRecord");
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			logger.error("用户未登录!");
			forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		 
		Map<String, Object> params = new HashMap<>();
		params.put("userId", shiroUser.getUserId());
		
		String borrowIdStr = request.getParameter("borrowId");
		String tenderIdStr = request.getParameter("tenderId");
		
		if(StringUtils.isNotEmpty(borrowIdStr)){
			params.put("borrowId", Integer.parseInt(borrowIdStr));
		} else {
			params.put("borrowId","");
		} 
		
		if(StringUtils.isNotEmpty(tenderIdStr)){
			params.put("tenderId", Integer.parseInt(tenderIdStr));
		} else {
			params.put("tenderId","");
		}
		
		borrowVo = borrowServiceImpl.queryTenderingForMap(params); 
		//根据投标ID借标ID用户ID查询待收记录
		List<BCollectionRecordVo> recordList = collectionRecordServiceImpl.queryCollectionrecord(params);
		
		mv.addObject("recordList",recordList);
		mv.addObject("borrowVo",borrowVo);
		
		return mv;
	}
	
	/**
	 * 根据直通车列表信息获取投标信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author wushaoling
	 * @version 0.1 2016年5月30日
	 * @param request
	 * @return
	 * ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "/showTenderBorrow")
	public ModelAndView showTenderBorrow(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		List<TenderRecord> tenderRecordList = new ArrayList();
		String realIdStr = request.getParameter("realId");    //认购ID
		if(StringUtils.isNotEmpty(realIdStr)){
			//根据直通车认购记录ID获取投标信息
			tenderRecordList = tendRecordService.queryTenderBorrow(Integer.parseInt(realIdStr));
		}
		mv = new ModelAndView("/account/first/firstTenderBorrow");
		mv.addObject("tenderRecordList",tenderRecordList).addObject("realId",realIdStr);
		return mv;
	}
	
	/**
	 * 优先计划投的抵押标明细
	 * <p>
	 * Description:优先计划投的抵押标明细<br />
	 * </p>
	 * @author wushaoling
	 * @version 0.1 2016年5月31日
	 * @param request
	 * @return
	 * ModelAndView
	 */
	@RequiresAuthentication
	@RequestMapping(value = "collectionFirstRecordInfo")
	public ModelAndView collectionFirstRecordInfo(HttpServletRequest request) {
		BorrowVo borrowVo = new BorrowVo();
		
		ModelAndView mv = new ModelAndView("/account/borrow/firstCollectionRecord");
		String firstTenderRealId = request.getParameter("firstTenderRealId");   //最终认购记录ID 
		String borrowId = request.getParameter("borrowId");  //标ID
		if (null == firstTenderRealId || "".equals(firstTenderRealId)) {
			forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		ShiroUser shiroUser = currentUser();
		if (shiroUser == null) {
			forword(BusinessConstants.NO_PAGE_FOUND_404);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", shiroUser.getUserId());
		map.put("firstTenderRealId", firstTenderRealId);
		map.put("borrowId", borrowId);
		//直通车待收记录查询不分页(状态为已还款未还款)
		List<CommonCollectionVo> firstCollectionsList = collectionRecordServiceImpl.queryAccFirstCommonCollectionsList(map);
		List<CommonCollectionVo> listReturn = new ArrayList<CommonCollectionVo>();
		// 计算罚息
		collectionRecordServiceImpl.calculatedLateInterestInfo(firstCollectionsList, listReturn);
		borrowVo = borrowServiceImpl.queryTenderingForId(map);

//		Map<Integer, BorrowVo> borrowMap = new HashMap<Integer, BorrowVo>();
//		borrowMap = collectionListTransitionMap(firstCollectionsList);
		return mv.addObject("CollectionRecordList", listReturn).addObject("firstTenderRealId", firstTenderRealId).addObject("borrowVo",borrowVo);
	}
}
