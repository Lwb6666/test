package com.dxjr.portal.invest.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.SearchInvestCnd;
import com.dxjr.portal.borrow.mapper.BorrowBusinessMapper;
import com.dxjr.portal.borrow.mapper.TenderRecordMapper;
import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.borrow.vo.BorrowBusinessVo;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.borrow.vo.TenderRecordCnd;
import com.dxjr.portal.borrow.vo.TenderRecordVo;
import com.dxjr.portal.first.mapper.FirstTransferBorrowMapper;
import com.dxjr.portal.first.mapper.FirstTransferMapper;
import com.dxjr.portal.first.service.FirstBorrowService;
import com.dxjr.portal.first.service.FirstTenderRealService;
import com.dxjr.portal.first.vo.FirstBorrowCnd;
import com.dxjr.portal.first.vo.FirstBorrowVo;
import com.dxjr.portal.first.vo.FirstTenderRealCnd;
import com.dxjr.portal.first.vo.FirstTenderRealVo;
import com.dxjr.portal.first.vo.FirstTransferBorrowCnd;
import com.dxjr.portal.first.vo.FirstTransferBorrowVo;
import com.dxjr.portal.first.vo.FirstTransferCnd;
import com.dxjr.portal.first.vo.FirstTransferVo;
import com.dxjr.portal.freemarker.service.BaseFreemarkerService;
import com.dxjr.portal.invest.mapper.InvestRecordMapper;
import com.dxjr.portal.invest.service.InvestRecordService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.member.service.RealNameApproService;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.MobileApproVo;
import com.dxjr.portal.member.vo.RealNameApproVo;
import com.dxjr.portal.repayment.service.BRepaymentRecordService;
import com.dxjr.portal.repayment.vo.BRepaymentRecordVo;
import com.dxjr.portal.statics.EnumerationUtil;
import com.dxjr.portal.transfer.mapper.BSubscribeMapper;
import com.dxjr.portal.transfer.mapper.BTransferMapper;
import com.dxjr.portal.transfer.vo.BTransferVo;
import com.dxjr.portal.transfer.vo.BsubscribeVo;
import com.dxjr.portal.util.ChangeCapitalMoney;
import com.dxjr.portal.util.PdfLowManager;
import com.dxjr.portal.util.PdfManager;
import com.dxjr.portal.util.SimpleMoneyFormat;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.file.FileUtil;
import com.itextpdf.text.pdf.PdfReader;

/**   
 * <p>
 * Description:  借款标，转让标等协议查看，下载 BLL <br />
 * </p>
 * @title InvestRecordServiceImpl.java
 * @package com.dxjr.portal.invest.service.impl 
 * @author HuangJun
 * @version 0.1 2015年6月9日
 */
@Service
public class InvestRecordServiceImpl implements InvestRecordService {

	private static final Logger logger = Logger.getLogger(InvestRecordServiceImpl.class);

	@Autowired
	private InvestRecordMapper investRecordMapper;
	@Autowired
	private TenderRecordMapper tenderRecordMapper;
	@Autowired
	private BaseFreemarkerService baseFreemarkerService;

	@Autowired
	private BorrowService borrowService;
	@Autowired
	MemberService memberService;
	@Autowired
	private BTransferMapper bTransferMapper;
	@Autowired
	private BSubscribeMapper bSubscribeMapper;
	@Autowired
	private FirstTransferMapper firstTransferMapper;
	@Autowired
	private FirstTransferBorrowMapper firstTransferBorrowMapper;

	@Autowired
	private FirstTenderRealService firstTenderRealServiceImpl;

	@Autowired
	private FirstBorrowService firstBorrowServiceImpl;

	@Autowired
	private RealNameApproService realNameApproServiceImpl;

	@Autowired
	private MobileApproService mobileApproServiceImpl;
	
	@Autowired
	private BRepaymentRecordService bRepaymentRecordService;
	
	@Autowired
    private BorrowBusinessMapper borrowBusinessMapper;

	@Override
	public Page findInvest(SearchInvestCnd seach, Page p) throws Exception {

		if (StringUtils.isNotEmpty(seach.getBeginTime())) {
			seach.setBeginTime(DateUtils.dateTime2TimeStamp(seach.getBeginTime() + " 00:00:00"));
		}
		if (StringUtils.isNotEmpty(seach.getEndTime())) {
			seach.setEndTime(DateUtils.dateTime2TimeStamp(seach.getEndTime() + " 23:59:59"));
		}

		Integer totalCount = investRecordMapper.queryInvestRecordCount(seach);
		p.setTotalCount(totalCount);
		// set orderType
		String orderType = seach.getOrderType();
		if (null != orderType && (orderType.equals("asc") || orderType.equals("desc"))) {
			seach.setOrderType(orderType);
		} else {
			seach.setOrderType("asc");
		}

		List<BorrowVo> list = investRecordMapper.queryInvestRecord(seach, p);
		list = BorrowVo.handleBorrow(list);
		p.setResult(list);
		return p;
	}
	public Page findInvest1(SearchInvestCnd seach, Page p) throws Exception {

		if (StringUtils.isNotEmpty(seach.getBeginTime())) {
			seach.setBeginTime(DateUtils.dateTime2TimeStamp(seach.getBeginTime() + " 00:00:00"));
		}
		if (StringUtils.isNotEmpty(seach.getEndTime())) {
			seach.setEndTime(DateUtils.dateTime2TimeStamp(seach.getEndTime() + " 23:59:59"));
		}
        //只查询投标中记录总数
		Integer totalCount2 = investRecordMapper.queryInvestRecordCount2(seach);
		p.setTotalCount(totalCount2);
		// set orderType
		String orderType = seach.getOrderType();
		if (null != orderType && (orderType.equals("asc") || orderType.equals("desc"))) {
			seach.setOrderType(orderType);
		} else {
			seach.setOrderType("asc");
		}
          //查询投标中 预发中记录
		List<BorrowVo> list = investRecordMapper.queryInvestRecord(seach, p);
		if(null!=list&&list.size()>0){
			//说明预发中记录数大于等于2条，则预发中记录数只取2条
			if(list.size()>=totalCount2+2){
				p.setTotalCount(totalCount2+2);
			}else if(list.size()==totalCount2+1){
				p.setTotalCount(totalCount2+1);
			}else{
				p.setTotalCount(totalCount2);
			}
		}
		list = BorrowVo.handleBorrow(list);
		p.setResult(list);
		return p;
	}

	@Override
	public List<TenderRecordVo> queryTenderRecordByBorrowId(Integer borrowId) throws Exception {
		return tenderRecordMapper.queryTenderRecordByBorrowId(borrowId);
	}

	/**
	 * 查看借款标协议 - 自己查看显示全名称(普通待收-直通车待收-净值标) (non-Javadoc)
	 * 
	 * @see com.dxjr.portal.invest.service.InvestRecordService#queryBorrowProtocol(java.lang.Integer,
	 *      java.lang.String)
	 */
	@Override
	public String queryBorrowProtocol(Integer borrowId, String contextPath, Integer userId) throws Exception {
		// 查询借款标
		BorrowVo borrowVo = borrowService.selectByPrimaryKey(borrowId);
		// 发标人
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setId(borrowVo.getUserId());
		MemberVo borrowMember = memberService.queryMemberByCnd(memberCnd);
		// 投标人
		TenderRecordCnd tenderRecordCnd = new TenderRecordCnd();
		tenderRecordCnd.setBorrowId(borrowId);
		// 查询原始投标记录
		tenderRecordCnd.setRecordType(0);
		List<TenderRecordVo> list = this.queryListByTenderRecordCnd(tenderRecordCnd);
		// 计算到期应还本息总额
		BigDecimal totalRepaymentAccount = new BigDecimal("0");
		for (TenderRecordVo tenderRecordVo : list) {
			totalRepaymentAccount = totalRepaymentAccount.add(tenderRecordVo.getRepaymentAccount());
		}
		//查询借款标还款计划
		List<BRepaymentRecordVo> repayList=bRepaymentRecordService.queryRepaymentByBorrowId(borrowId);
		
		String templateName = EnumerationUtil.getProtocolPathByBorrowType(borrowVo.getBorrowtype().toString());
		Map<String, Object> templateMap = new HashMap<String, Object>();		
		templateMap.put("borrowVo", borrowVo);
		templateMap.put("tenderRecordVoList", list);
		templateMap.put("username", borrowMember.getUsername());
		templateMap.put("totalRepaymentAccount", totalRepaymentAccount);
		templateMap.put("contextPath", contextPath);
		templateMap.put("userId", userId);
		templateMap.put("repayList", repayList);
		templateMap.put("businessName", getBusinessName(borrowId));

		// 得到协议内容
		String content = baseFreemarkerService.generateEmailContentByFreeMarker(templateName, templateMap);
		return content;
	}
	
	/**
	 * 根据借款标ID或获取业务员
	 * @author WangQianJin
	 * @title @param borrowId
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年8月25日
	 */
	private String getBusinessName(Integer borrowId) throws Exception{
		String bussinesName="";
		BorrowBusinessVo borrowBusinessVo = borrowBusinessMapper.selectBorBusByBorrowId(borrowId);
		if(borrowBusinessVo!=null){
			if("其他".equals(borrowBusinessVo.getUsername())){
				bussinesName="顶玺金融";
			}else{
				bussinesName=borrowBusinessVo.getUsername();
			}
		}
		return bussinesName;
	}

	@Override
	public List<BorrowVo> queryInvestRecordList(SearchInvestCnd seach, Page page) {
		List<BorrowVo> list = Collections.emptyList();
		try {
			list = investRecordMapper.queryInvestRecord(seach, page);
		} catch (Exception e) {

		}
		return list;
	}

	@Override
	public List<BorrowVo> queryBorrowByLimit(int type, int i, int j) {
		return investRecordMapper.queryBorrowByLimit(type, i, j);
	}

	@Override
	public List<TenderRecordVo> queryListByTenderRecordCnd(TenderRecordCnd tenderRecordCnd) throws Exception {
		return tenderRecordMapper.queryListByTenderRecordCnd(tenderRecordCnd);
	}

	/**
	 * 债权转让协议- 自己查看全名称<br/>
	 * 普待1</br> (non-Javadoc)
	 * 
	 * @see com.dxjr.portal.invest.service.InvestRecordService#queryTransferXiyiContent(int,
	 *      java.lang.String)
	 */
	@Override
	public String queryTransferXiyiContent(int collectionId, String contextPath, Integer userId) throws Exception {
		// 查询债转信息
		BTransferVo bTransferVo = bTransferMapper.selectByCollectionId(collectionId);
		if (bTransferVo != null) {
			//安全渗透测试  只能查看自己认购的债权协议
			if(null!=bTransferVo.getSubUserId()&&!bTransferVo.getSubUserId().equals(userId)){
				return "";
			}
			// 查询借款标信息
			BorrowVo borrowVo = borrowService.selectByPrimaryKey(bTransferVo.getBorrowId());
			// 查询债权转让认购信息
			List<BsubscribeVo> bsubscribeVoList = bSubscribeMapper.querySubscribesListByTransferId(bTransferVo.getId());
			BigDecimal sumAccount = BigDecimal.ZERO;
			BigDecimal sumRepayAccount = BigDecimal.ZERO;
			if (bsubscribeVoList.size() > 0) {
				for (BsubscribeVo bsubscribeVo : bsubscribeVoList) {
					sumAccount = sumAccount.add(bsubscribeVo.getAccount());
					sumRepayAccount = sumRepayAccount.add(bsubscribeVo.getRepaymentAccount());
				}
			}
			String templateName = EnumerationUtil.getProtocolPathByBorrowType("6"); // 获取债权转让协议模版路径
			
			//最后一期还款时间
			Date repayTime=DateUtils.monthOffset(DateUtils.timeStampToDate(bTransferVo.getDateBorrowTime()),bTransferVo.getBorrowTimeLimit());
			String repayTimeStr=String.valueOf(repayTime.getTime() / 1000L);
			bTransferVo.setRepayTime(repayTimeStr);
			
			Map<String, Object> templateMap = new HashMap<String, Object>();
			templateMap.put("bTransferVo", bTransferVo);
			templateMap.put("bsubscribeVoList", bsubscribeVoList);
			templateMap.put("tenderTimes", String.valueOf(bsubscribeVoList.size()));
			templateMap.put("borrowVo", borrowVo);
			templateMap.put("contextPath", contextPath);
			templateMap.put("sumAccount", sumAccount);
			templateMap.put("sumRepayAccount", sumRepayAccount);
			templateMap.put("userId", userId);
			// 得到协议内容
			String content = baseFreemarkerService.generateEmailContentByFreeMarker(templateName, templateMap);
			return content;
		}
		return "";
	}

	@Override
	public Page selectAllTenderBorrowConstainTransfer(SearchInvestCnd seach, Page page) {
		page.setTotalCount(investRecordMapper.selectCountAllTenderBorrowConstainTransfer(seach));
		// set orderType
		String orderType = seach.getOrderType();
		if (null != orderType && (orderType.equals("asc") || orderType.equals("desc"))) {
			seach.setOrderType(orderType);
		} else {
			seach.setOrderType("asc");
		}

		List<BorrowVo> list = investRecordMapper.selectListAllTenderBorrowConstainTransfer(seach, page);
		BorrowVo.handleBorrow(list);
		page.setResult(list);
		return page;
	}

	/**
	 * 债权转让协议<br>
	 * (直)查看显示全名称 (non-Javadoc)
	 * 
	 * @see com.dxjr.portal.invest.service.InvestRecordService#queryFirstTransferXiyiContent(int,
	 *      java.lang.String, com.dxjr.security.ShiroUser, int)
	 */
	@Override
	public String queryFirstTransferXiyiContent(int collectionId, String contextPath, ShiroUser shiroUser, int flag) throws Exception {
		// 查询债转信息
		FirstTransferCnd firstTransferCnd = new FirstTransferCnd();
		// 待收ID
		firstTransferCnd.setCollectionId(collectionId);

		List<FirstTransferVo> transferList = firstTransferMapper.queryMyFirstTransferByCollectionId(firstTransferCnd);

		if (transferList != null && transferList.size() > 0) {
			FirstTransferVo firstTransferVo = transferList.get(0);
			//安全渗透测试  只能查看自己认购的债权协议
			if(null!=firstTransferVo.getSubUserId()&&!firstTransferVo.getSubUserId().equals(shiroUser.getUserId())){
				return "";
			}
			BorrowVo borrowVo = borrowService.selectByPrimaryKey(firstTransferVo.getBorrowId());
			FirstTransferBorrowCnd firstTransferBorrowCnd = new FirstTransferBorrowCnd();
			// 用户ID
			firstTransferBorrowCnd.setUserId(shiroUser.getUserId());
			// 直通车最终认购记录ID
			firstTransferBorrowCnd.setFirstTenderRealId(firstTransferVo.getTenderRealId());
			// 借款标ID
			firstTransferBorrowCnd.setBorrowId(firstTransferVo.getBorrowId());
			// 直通车转让ID
			firstTransferBorrowCnd.setFirstTransferId(firstTransferVo.getId());
			List<FirstTransferBorrowVo> firstTransferBorrowList = firstTransferBorrowMapper.queryBorrowListByTransferId(firstTransferBorrowCnd);

			if (firstTransferBorrowList != null && firstTransferBorrowList.size() > 0) {
				FirstTransferBorrowVo firstTransferBorrowVo = firstTransferBorrowList.get(0);
				// 获取债权转让协议模版路径
				String templateName = EnumerationUtil.getProtocolPathByBorrowType("7");
				Map<String, Object> templateMap = new HashMap<String, Object>();
				templateMap.put("firstTransferVo", firstTransferVo);
				templateMap.put("borrowVo", borrowVo);
				templateMap.put("contextPath", contextPath);
				templateMap.put("firstTransferBorrowVo", firstTransferBorrowVo);
				templateMap.put("borrowAccountToChinese", SimpleMoneyFormat.getInstance().format(firstTransferBorrowVo.getBorrowCollectionCapital()));
				templateMap.put("userId", shiroUser.getUserId());
				// 得到协议内容
				String content = baseFreemarkerService.generateEmailContentByFreeMarker(templateName, templateMap);
				return content;
			}
		}
		return "";
	}

	@Override
	public String queryFirstRealTransferXiyiContent(int firstTenderRealId, ShiroUser shiroUser, String contextPath) throws Exception {
		// 查询债转信息
		FirstTransferCnd firstTransferCnd = new FirstTransferCnd();
		// 最终认购记录ID
		firstTransferCnd.setFirstTenderRealId(firstTenderRealId);

		List<FirstTransferVo> transferList = firstTransferMapper.queryMyFirstTransferByRealId(firstTransferCnd);

		if (transferList != null && transferList.size() > 0) {
			// 查询直通车转让信息
			FirstTransferVo firstTransfer = transferList.get(0);
			if (firstTransfer != null) {

				FirstTransferBorrowCnd firstTransferBorrowCnd = new FirstTransferBorrowCnd();
				// 转让人ID
				firstTransferBorrowCnd.setUserId(shiroUser.getUserId());
				// 直通车最终认购记录ID
				firstTransferBorrowCnd.setFirstTenderRealId(firstTenderRealId);
				// 直通车转让ID
				firstTransferBorrowCnd.setFirstTransferId(firstTransfer.getId());
				// 债权转让标的债权价格总额
				BigDecimal borrowAccountSum = firstTransferBorrowMapper.queryBorrowSumByTransferId(firstTransfer.getId());
				// 债权标的信息
				List<FirstTransferBorrowVo> firstTransferBorrowList = firstTransferBorrowMapper.queryBorrowListByTransferId(firstTransferBorrowCnd);

				// 获取债权转让协议模版路径
				String templateName = EnumerationUtil.getProtocolPathByBorrowType("8");
				Map<String, Object> templateMap = new HashMap<String, Object>();
				templateMap.put("firstTransfer", firstTransfer);
				templateMap.put("firstTransferBorrowList", firstTransferBorrowList);
				templateMap.put("borrowAccountSum", borrowAccountSum);
				templateMap.put("subscribeUsername", firstTransfer.getSubscribeUsername());
				templateMap.put("transferUsername", firstTransfer.getUserName());
				templateMap.put("contextPath", contextPath);
				// 得到协议内容
				String content = baseFreemarkerService.generateEmailContentByFreeMarker(templateName, templateMap);
				return content;
			}
		}
		return "";
	}

	/**
	 * 下载协议 普-直-待收 (non-Javadoc)
	 * 
	 * @see com.dxjr.portal.invest.service.InvestRecordService#queryBorrowProtocolDload(java.lang.Integer,
	 *      java.lang.String, java.lang.Integer)
	 */
	@Override
	public String queryBorrowProtocolDload(Integer borrowId, String contextPath, Integer userId, Map<String, Object> reqMap) throws Exception {
		// 查询借款标
		BorrowVo borrowVo = borrowService.selectByPrimaryKey(borrowId);
		// 发标人
		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setId(borrowVo.getUserId());
		MemberVo borrowMember = memberService.queryMemberByCnd(memberCnd);
		// 投标人
		TenderRecordCnd tenderRecordCnd = new TenderRecordCnd();
		tenderRecordCnd.setBorrowId(borrowId);
		// 查询原始投标记录
		tenderRecordCnd.setRecordType(0);
		List<TenderRecordVo> list = this.queryListByTenderRecordCnd(tenderRecordCnd);
		// 计算到期应还本息总额
		BigDecimal totalRepaymentAccount = new BigDecimal("0");
		for (TenderRecordVo tenderRecordVo : list) {
			totalRepaymentAccount = totalRepaymentAccount.add(tenderRecordVo.getRepaymentAccount());
		}
		//查询借款标还款计划
		List<BRepaymentRecordVo> repayList=bRepaymentRecordService.queryRepaymentByBorrowId(borrowId);
		
		String templateName = EnumerationUtil.getProtocolPathByBorrowType(borrowVo.getBorrowtype().toString());
		Map<String, Object> templateMap = new HashMap<String, Object>();
		templateMap.put("borrowVo", borrowVo);
		templateMap.put("tenderRecordVoList", list);
		templateMap.put("username", borrowMember.getUsername());
		templateMap.put("totalRepaymentAccount", totalRepaymentAccount);
		templateMap.put("contextPath", contextPath);
		templateMap.put("userId", userId);
		templateMap.put("repayList", repayList);
		templateMap.put("businessName", getBusinessName(borrowId));
		/********************** download s **************************************/
		commDownLoad(templateName, templateMap, reqMap, borrowVo.getName());
		/********************** download e **************************************/
		return "success";
	}

	/**
	 * 下载 - 债权转让协议 (non-Javadoc)
	 * 
	 * @see com.dxjr.portal.invest.service.InvestRecordService#queryTransferXiyiContentDownLoad(int,
	 *      java.lang.String, java.lang.Integer, java.util.Map)
	 */
	@Override
	public String queryTransferXiyiContentDownLoad(int collectionId, String contextPath, Integer userId, Map<String, Object> reqMap) throws Exception {
		try {

			// 查询债转信息
			BTransferVo bTransferVo = bTransferMapper.selectByCollectionId(collectionId);
			if (bTransferVo != null) {
				// 查询借款标信息
				BorrowVo borrowVo = borrowService.selectByPrimaryKey(bTransferVo.getBorrowId());
				// 查询债权转让认购信息
				List<BsubscribeVo> bsubscribeVoList = bSubscribeMapper.querySubscribesListByTransferId(bTransferVo.getId());
				BigDecimal sumAccount = BigDecimal.ZERO;
				BigDecimal sumRepayAccount = BigDecimal.ZERO;
				if (bsubscribeVoList.size() > 0) {
					for (BsubscribeVo bsubscribeVo : bsubscribeVoList) {
						sumAccount = sumAccount.add(bsubscribeVo.getAccount());
						sumRepayAccount = sumRepayAccount.add(bsubscribeVo.getRepaymentAccount());
					}
				}
				String templateName = EnumerationUtil.getProtocolPathByBorrowType("6"); // 获取债权转让协议模版路径
				
				//最后一期还款时间
				Date repayTime=DateUtils.monthOffset(DateUtils.timeStampToDate(bTransferVo.getDateBorrowTime()),bTransferVo.getBorrowTimeLimit());
				String repayTimeStr=String.valueOf(repayTime.getTime() / 1000L);
				bTransferVo.setRepayTime(repayTimeStr);
						
				
				Map<String, Object> templateMap = new HashMap<String, Object>();
				templateMap.put("bTransferVo", bTransferVo);
				templateMap.put("bsubscribeVoList", bsubscribeVoList);
				templateMap.put("tenderTimes", String.valueOf(bsubscribeVoList.size()));
				templateMap.put("borrowVo", borrowVo);
				templateMap.put("contextPath", contextPath);
				templateMap.put("sumAccount", sumAccount);
				templateMap.put("sumRepayAccount", sumRepayAccount);
				templateMap.put("userId", userId);
				/********************** download s **************************************/
				commDownLoad(templateName, templateMap, reqMap, borrowVo.getName());
				/********************** download e **************************************/
			}

		} catch (Exception e) {
			logger.error("下载转让协议异常:", e);
		}
		return "success";
	}

	/**
	 * <p>
	 * Description: 下载 协议- 公共方法<br />
	 * </p>
	 * 
	 * @author HuangJun
	 * @version 0.1 2015年6月4日
	 * @param templateName
	 *            模板文件名称
	 * @param templateMap
	 *            模板文件map
	 * @param reqMap
	 *            : request map
	 * @param titleName
	 *            :保存协议默认标题名称
	 * @throws Exception
	 *             void
	 */
	public void commDownLoad(String templateName, Map<String, Object> templateMap, Map<String, Object> reqMap, String titleName) throws Exception {
		logger.info("下载 协议- 公共方法 start ");
		try {
			// 临时html文件
			String htmlPathName = "tmpFile/borrowInfoDownLoad" + System.currentTimeMillis() + ".html";
			// 读取协议文件pledge.ftl-生成临时html文件
			baseFreemarkerService.createHtml(templateName, templateMap, reqMap.get("realPathNew") + htmlPathName);
			// 临时pdf文件
			String pdfPathName = "tmpFile/borrowInfoDownLoad" + System.currentTimeMillis() + ".pdf";
			// 页眉图片
			String headerImagePath = reqMap.get("realPathNew") + "images/header.png";
			try {
				// 生成临时pdf文件
				PdfManager.createPdf(reqMap.get("realPathNew") + htmlPathName, reqMap.get("realPathNew") + pdfPathName,headerImagePath);
			} catch (Exception e) {
				logger.error("生成临时pdf文件错误", e);
			}
			PdfReader reader = new PdfReader(reqMap.get("realPathNew") + pdfPathName);
			int pagecount = reader.getNumberOfPages();
			String finishPdfName = "tmpFile/borrowInfoDownLoad_new" + System.currentTimeMillis() + ".pdf";
			// 图章path
			String markImagePath = reqMap.get("realPathNew") + "images/tuzhang.png";
			// 加水印前pdf path
			String InPdfFile = reqMap.get("realPathNew") + pdfPathName;
			// 加水印后pdf path
			String outPdfFile = reqMap.get("realPathNew") + finishPdfName;

			logger.error("InPdfFile================================"+InPdfFile);
			logger.error("outPdfFile================================"+outPdfFile);
			logger.error("markImagePath================================"+markImagePath);
			logger.error("pagecount================================"+pagecount);
			// 给临时pdf文件加水印图片
			PdfLowManager.addPdfMarkLow(InPdfFile, outPdfFile, markImagePath, pagecount);

			// 下载pdf文件
			FileUtil.streamDownload((HttpServletRequest) reqMap.get("request"), finishPdfName, "【" + titleName.trim().replaceAll(" ", "") + "】借款标协议"
					+ DateUtils.format(new Date(), DateUtils.YMD) + ".pdf", (HttpServletResponse) reqMap.get("response"));

			// 删除临时html,pdf,pdf(加水印)文件
			FileUtil.deleteTempFileByDownLoad((String) reqMap.get("realPathNew"), htmlPathName);
			FileUtil.deleteTempFileByDownLoad((String) reqMap.get("realPathNew"), pdfPathName);
			FileUtil.deleteTempFileByDownLoad((String) reqMap.get("realPathNew"), finishPdfName);
		} catch (Exception e) {
			logger.error("下载 协议- 公共方法异常", e);
		}
		logger.info("下载 协议- 公共方法 end ");

	}

	/**
	 * 下载协议-直转让-待收 (non-Javadoc)
	 * 
	 * @see com.dxjr.portal.invest.service.InvestRecordService#queryFirstTransferXiyiContentDownLoad(int,
	 *      java.lang.String, com.dxjr.security.ShiroUser, int, java.util.Map)
	 */
	public String queryFirstTransferXiyiContentDownLoad(int collectionId, String contextPath, ShiroUser shiroUser, int flag,
			Map<String, Object> reqMap) throws Exception {
		// 查询债转信息
		FirstTransferCnd firstTransferCnd = new FirstTransferCnd();
		// 待收ID
		firstTransferCnd.setCollectionId(collectionId);

		List<FirstTransferVo> transferList = firstTransferMapper.queryMyFirstTransferByCollectionId(firstTransferCnd);

		if (transferList != null && transferList.size() > 0) {
			FirstTransferVo firstTransferVo = transferList.get(0);
			BorrowVo borrowVo = borrowService.selectByPrimaryKey(firstTransferVo.getBorrowId());
			FirstTransferBorrowCnd firstTransferBorrowCnd = new FirstTransferBorrowCnd();
			// 用户ID
			firstTransferBorrowCnd.setUserId(shiroUser.getUserId());
			// 直通车最终认购记录ID
			firstTransferBorrowCnd.setFirstTenderRealId(firstTransferVo.getTenderRealId());
			// 借款标ID
			firstTransferBorrowCnd.setBorrowId(firstTransferVo.getBorrowId());
			// 直通车转让ID
			firstTransferBorrowCnd.setFirstTransferId(firstTransferVo.getId());
			List<FirstTransferBorrowVo> firstTransferBorrowList = firstTransferBorrowMapper.queryBorrowListByTransferId(firstTransferBorrowCnd);

			if (firstTransferBorrowList != null && firstTransferBorrowList.size() > 0) {
				FirstTransferBorrowVo firstTransferBorrowVo = firstTransferBorrowList.get(0);
				// 获取债权转让协议模版路径
				String templateName = EnumerationUtil.getProtocolPathByBorrowType("7");
				Map<String, Object> templateMap = new HashMap<String, Object>();
				templateMap.put("firstTransferVo", firstTransferVo);
				templateMap.put("borrowVo", borrowVo);
				templateMap.put("contextPath", contextPath);
				templateMap.put("firstTransferBorrowVo", firstTransferBorrowVo);
				templateMap.put("borrowAccountToChinese", SimpleMoneyFormat.getInstance().format(firstTransferBorrowVo.getBorrowCollectionCapital()));
				templateMap.put("userId", shiroUser.getUserId());
				/********************** download s **************************************/
				commDownLoad(templateName, templateMap, reqMap, borrowVo.getName());
				/********************** download e **************************************/

			}
		}
		return "success";

	}

	/**
	 * 下载协议 - 直-借款 - 已解锁 下载协议-直-借款- 未解锁<br />
	 * (non-Javadoc)
	 * 
	 * @see com.dxjr.portal.invest.service.InvestRecordService#toFirstTenderXiyiDownLoad(com.dxjr.portal.first.vo.FirstTenderRealCnd,
	 *      com.dxjr.security.ShiroUser, java.util.Map)
	 */
	public String toFirstTenderXiyiDownLoad(FirstTenderRealCnd firstTenderRealCnd, ShiroUser shiroUser, Map<String, Object> reqMap) throws Exception {
		Page p = new Page();
		try {

			p = firstTenderRealServiceImpl.queryPageListByCnd(firstTenderRealCnd, new Page(1, 1));

			if (p.getResult().size() > 0) {
				FirstTenderRealVo firstTenderReal = (FirstTenderRealVo) p.getResult().get(0);
				FirstBorrowCnd firstBorrowCnd = new FirstBorrowCnd();
				firstBorrowCnd.setId(firstTenderReal.getFirstBorrowId());
				FirstBorrowVo firstBorrow = new FirstBorrowVo();
				RealNameApproVo realNameAppro = new RealNameApproVo();
				MobileApproVo mobileAppro = new MobileApproVo();

				firstBorrow = firstBorrowServiceImpl.queryFirstBorrowByCnd(firstBorrowCnd);
				realNameAppro = realNameApproServiceImpl.queryRealNameApproByUserId(shiroUser.getUserId());
				mobileAppro = mobileApproServiceImpl.queryMobileApproByUserId(shiroUser.getUserId());

				// 获取模版路径-直-已解锁
				String templateName = EnumerationUtil.getProtocolPathByBorrowType("9");
				Map<String, Object> templateMap = new HashMap<String, Object>();
				templateMap.put("subscribeMoney", ChangeCapitalMoney.digitUppercase(firstTenderReal.getAccount()));
				templateMap.put("firstTenderReal", firstTenderReal);
				templateMap.put("firstBorrow", firstBorrow);
				templateMap.put("realNameAppro", realNameAppro);
				templateMap.put("mobileAppro", mobileAppro);
				templateMap.put("member", shiroUser);
				/********************** download s **************************************/
				commDownLoad(templateName, templateMap, reqMap, firstBorrow.getName());
				/********************** download e **************************************/
			}

		} catch (Exception e) {
			logger.error("下载-直通车协议错误", e);
		}

		return "success";
	}

	/**
	 * 下载-直- 转让- 已解锁 (non-Javadoc)<br />
	 * 下载协议-直通车列表- 转让-未解锁 <br />
	 * 
	 * @see com.dxjr.portal.invest.service.InvestRecordService#queryFirstRealTransferXiyiContentDownLoad(int,
	 *      com.dxjr.security.ShiroUser, java.lang.String, java.util.Map)
	 */
	public String queryFirstRealTransferXiyiContentDownLoad(int firstTenderRealId, ShiroUser shiroUser, String contextPath, Map<String, Object> reqMap)
			throws Exception {
		try {

			// 查询债转信息
			FirstTransferCnd firstTransferCnd = new FirstTransferCnd();
			// 最终认购记录ID
			firstTransferCnd.setFirstTenderRealId(firstTenderRealId);

			List<FirstTransferVo> transferList = firstTransferMapper.queryMyFirstTransferByRealId(firstTransferCnd);

			if (transferList != null && transferList.size() > 0) {
				// 查询直通车转让信息
				FirstTransferVo firstTransfer = transferList.get(0);
				if (firstTransfer != null) {

					FirstTransferBorrowCnd firstTransferBorrowCnd = new FirstTransferBorrowCnd();
					// 转让人ID
					firstTransferBorrowCnd.setUserId(shiroUser.getUserId());
					// 直通车最终认购记录ID
					firstTransferBorrowCnd.setFirstTenderRealId(firstTenderRealId);
					// 直通车转让ID
					firstTransferBorrowCnd.setFirstTransferId(firstTransfer.getId());
					// 债权转让标的债权价格总额
					BigDecimal borrowAccountSum = firstTransferBorrowMapper.queryBorrowSumByTransferId(firstTransfer.getId());
					// 债权标的信息
					List<FirstTransferBorrowVo> firstTransferBorrowList = firstTransferBorrowMapper
							.queryBorrowListByTransferId(firstTransferBorrowCnd);

					// 获取债权转让协议模版路径
					String templateName = EnumerationUtil.getProtocolPathByBorrowType("8");
					Map<String, Object> templateMap = new HashMap<String, Object>();
					templateMap.put("firstTransfer", firstTransfer);
					templateMap.put("firstTransferBorrowList", firstTransferBorrowList);
					templateMap.put("borrowAccountSum", borrowAccountSum);
					templateMap.put("subscribeUsername", firstTransfer.getSubscribeUsername());
					templateMap.put("transferUsername", firstTransfer.getUserName());
					templateMap.put("contextPath", contextPath);
					/********************** download s **************************************/
					commDownLoad(templateName, templateMap, reqMap, firstTransfer.getTransferName());
					/********************** download e **************************************/
				}
			}

		} catch (Exception e) {
			logger.error("下载-直通车转让协议错误", e);
		}
		return "success";

	}

}
