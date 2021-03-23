package com.dxjr.portal.borrow.service.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.AccountUploadDoc;
import com.dxjr.base.entity.Borrow;
import com.dxjr.base.entity.Borrower;
import com.dxjr.base.entity.Configuration;
import com.dxjr.base.entity.GuarantyOrganization;
import com.dxjr.base.entity.Member;
import com.dxjr.base.entity.Mortgage;
import com.dxjr.base.mapper.BaseGuarantyOrganizationMapper;
import com.dxjr.base.mapper.BaseMemberMapper;
import com.dxjr.base.mapper.BaseRealNameApproMapper;
import com.dxjr.common.Dictionary;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.mapper.AccountUploadDocMapper;
import com.dxjr.portal.account.vo.AccountUploadDocCnd;
import com.dxjr.portal.account.vo.AccountUploadDocVo;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.borrow.entity.BorrowBusiness;
import com.dxjr.portal.borrow.mapper.BorrowBusinessMapper;
import com.dxjr.portal.borrow.mapper.BorrowMapper;
import com.dxjr.portal.borrow.mapper.BorrowerMapper;
import com.dxjr.portal.borrow.mapper.BusinessMapper;
import com.dxjr.portal.borrow.mapper.MortgageMapper;
import com.dxjr.portal.borrow.mapper.SalariatBorrowMapper;
import com.dxjr.portal.borrow.service.SalariatBorrowService;
import com.dxjr.portal.borrow.util.BorrowUtil;
import com.dxjr.portal.borrow.vo.BorrowBusinessVo;
import com.dxjr.portal.borrow.vo.BorrowCnd;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.borrow.vo.SalariatBorrowVo;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.statics.NewLoactionConstants;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.MD5;
import com.dxjr.utils.RandomGUIDUtil;
import com.dxjr.utils.file.FileUtil;

@Service
public class SalariatBorrowServiceImpl implements SalariatBorrowService {

	@Autowired
	private SalariatBorrowMapper salariatBorrowMapper;

	@Autowired
	private BorrowMapper borrowMapper;

	@Autowired
	private BorrowerMapper borrowerMapper;

	@Autowired
	private MortgageMapper mortgageMapper;

	@Autowired
	private BaseGuarantyOrganizationMapper organizationMapper;

	@Autowired
	private AccountUploadDocMapper docMapper;

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private BaseRealNameApproMapper approMapper;
	@Autowired
	private BaseMemberMapper baseMemberMapper;
	@Autowired
	private BorrowBusinessMapper borrowBusinessMapper;
	@Autowired
	private BusinessMapper businessMapper;

	/**
	 * 诚薪贷申请初始化
	 * <p>
	 * Description:诚薪贷申请初始化<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年8月18日
	 * @param loginMem
	 *            当前登录用户
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@Override
	public ModelAndView initApply(ShiroUser loginMem, int productType, String viewType) throws Exception {
		// 验证
		ModelAndView mv = checkBorrowReturnView(loginMem, null, viewType, productType, "");
		if (mv != null) {
			return mv;
		}

		// 跳转页面设置
		if (productType == Constants.BORROW_PRODUCT_TYPE_SALARIAT) {
			mv = new ModelAndView("/borrow/applySalariatBorrow");
		} else {
			mv = new ModelAndView("/borrow/applyCommerceBorrow");
		}

		mv = BorrowUtil.initInfos(mv, loginMem, approMapper, organizationMapper,businessMapper);

		return mv;
	}

	/**
	 * 诚薪贷申请
	 * <p>
	 * Description:保存诚薪贷申请信息<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年8月18日
	 * @param loginMem
	 *            当前登录用户
	 * @param salariatBorrowVo
	 *            借款信息
	 * @param borrower
	 *            借款人信息
	 * @param mortgage
	 *            资产信息
	 * @return
	 * @throws Exception
	 *             MessageBox
	 */
	@Override
	public MessageBox saveApplySalariat(ShiroUser loginMem, SalariatBorrowVo salariatBorrowVo, Borrower borrower, Mortgage mortgage, String addip,
			int productType,Integer businessUserId) throws Exception {
		MessageBox msg = null;

		if (!"".equals(checkBorrowCertification(loginMem, null, ""))) {
			return MessageBox.build("0", checkBorrowCertification(loginMem, null, ""));
		}

		// 合同编号是否重复
		String newNo = salariatBorrowVo.getContractNo();
		Borrow b = borrowMapper.queryBorrowByContractNo(newNo);
		if (b != null) {
			return MessageBox.build("0", "借款单编号已存在，请重新输入。");
		}

		// 保存诚薪贷基本信息
		salariatBorrowVo.setUserId(loginMem.getUserId());
		salariatBorrowVo.setAddtime(new Date());
		salariatBorrowVo.setAddip(addip);
		salariatBorrowVo.setUuid(RandomGUIDUtil.newGuid());
		salariatBorrowVo.setStatus(Constants.BORROW_STATUS_NEW_CODE);// 1：新标，审核中
		salariatBorrowVo.setApprstatus(Constants.BORROW_APPSTATUS_WAIT_CODE);// 0：待审核
		salariatBorrowVo.setProductType(productType);// 借贷类型：1-诚薪贷 2-诚商贷 3-净值贷

		// 密码加密
		if (salariatBorrowVo.getBidPassword() != null && !"".equals(salariatBorrowVo.getBidPassword())) {
			salariatBorrowVo.setBidPassword(MD5.toMD5(salariatBorrowVo.getBidPassword()));
		}

		salariatBorrowVo = BorrowUtil.dealBorrowInfo(salariatBorrowVo);
		salariatBorrowVo.setPlatform(loginMem.getPlatform());
		salariatBorrowMapper.insertEntity(salariatBorrowVo);
		Integer borrowId = salariatBorrowVo.getId();

		// 保存借款人信息
		borrower.setBorrowId(borrowId);
		borrower.setAddtime(new Date());
		borrower.setAddip(addip);
		borrowerMapper.insertEntity(borrower);
		
		/*保存借款标业务员关联信息*/
		BorrowBusiness borrowBusiness = new BorrowBusiness();
		borrowBusiness.setBorrowId(borrowId);
	    borrowBusiness.setUserId(businessUserId);
	    //判断业务员是否选择的其他
	    if(businessUserId.intValue()==0){
	    	borrowBusiness.setUsername("其他");
	    }else{
	    	Member member = this.baseMemberMapper.queryById(businessUserId);
		    if (member != null) {
		    	borrowBusiness.setUsername(member.getUsername());
		    }
	    }	    
	    borrowBusiness.setAddip(addip);
	    borrowBusiness.setPlatform(loginMem.getPlatform());
	    this.borrowBusinessMapper.insertBorrowBusiness(borrowBusiness);
	    
		// 保存审批信息
		salariatBorrowMapper.insertBorrowApproved(borrowId);

		if (1 == salariatBorrowVo.getIsMortgage()) {
			// 保存抵押物信息
			mortgage.setBorrowId(borrowId);
			mortgage.setAddtime(new Date());
			mortgage.setAddip(addip);
			mortgageMapper.insertEntity(mortgage);
		}

		msg = MessageBox.build("1|" + borrowId, "保存成功");
		return msg;
	}

	/**
	 * 诚薪贷资料上传初始化
	 * <p>
	 * Description:诚薪贷资料上传初始化<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年8月18日
	 * @param loginMem
	 *            当前登录用户
	 * @param borrowId
	 *            借款记录ID
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@Override
	public ModelAndView initUpload(ShiroUser loginMem, Integer borrowId) throws Exception {
		ModelAndView mv = new ModelAndView("/borrow/borrowInfoUpload");

		if (checkBorrowReturnView(loginMem, borrowId, "", 0, "doc") != null) {
			return checkBorrowReturnView(loginMem, borrowId, "", 0, "doc");
		}

		Collection<Configuration> guarantyOptions = Dictionary.getValues(817);// 上传资料类型
		mv.addObject("styleOptions", guarantyOptions);
		mv.addObject("borrowId", borrowId);

		AccountUploadDocCnd cnd = new AccountUploadDocCnd();
		cnd.setBorrowId(borrowId);
		List<AccountUploadDocVo> list = docMapper.queryAccountUploadDocList(cnd);// 已上传图片
		mv.addObject("list", list);

		BorrowVo borrowVo = borrowMapper.selectByPrimaryKey(borrowId);
		mv.addObject("productType", borrowVo.getProductType());

		// 续贷标查询之前的记录
		mv.addObject("pledgeType", borrowVo.getPledgeType());
		if (borrowVo.getPledgeType() != null && borrowVo.getPledgeType() == 2) {
			List<Borrow> beforeList = salariatBorrowMapper.getBeforeBorrow(loginMem.getUserId(), borrowId);
			mv.addObject("beforeList", beforeList);
		}

		return mv;
	}

	/**
	 * 保存上传资料
	 * <p>
	 * Description:保存上传资料<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年8月18日
	 * @param loginMem
	 *            当前登录用户
	 * @param files
	 *            上传的文件
	 * @param borrowId
	 *            借款记录ID
	 * @param style
	 *            上传证件类型
	 * @param request
	 * @return
	 * @throws Exception
	 *             MessageBox
	 */
	@Override
	public MessageBox saveBorrowInfos(ShiroUser loginMem, MultipartFile[] files, Integer borrowId, Integer style, HttpServletRequest request)
			throws Exception {
		if (!"".equals(checkBorrowCertification(loginMem, borrowId, "doc"))) {
			return MessageBox.build("0", checkBorrowCertification(loginMem, borrowId, "doc"));
		}

		if (null == files || files.length <= 0) {
			return MessageBox.build("0", "请选择文件");
		}

		if (files.length > 5) {
			return MessageBox.build("0", "最多选择5个文件");
		}

		// 循环获取file数组中得文件
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];

			// 验证文件格式和大小是否正确
			String s = FileUtil.validateFileInfo(file, BusinessConstants.IMAGE_MAX_SIZE, BusinessConstants.IMAGE_FILE_TYPE_LIST);
			if (!"success".equals(s)) {
				return MessageBox.build("0", s);
			}

			// 改变文件名
			String filename = file.getOriginalFilename();
			String extName = filename.substring(filename.lastIndexOf(".")).toLowerCase();
			String lastFileName = UUID.randomUUID().toString() + extName;

			// 增加水印上传
			FileUtil.uploadAddWatermark(file, request, NewLoactionConstants.IMAGENEW, lastFileName, BusinessConstants.WATERMARK_PNG);

			// 保存
			AccountUploadDoc doc = new AccountUploadDoc();
			doc.setBorrowId(borrowId);
			doc.setUploadIp(HttpTookit.getRealIpAddr(request));
			doc.setUserId(loginMem.getUserId());
			doc.setStyle(style);
			doc.setDocName(filename);
			doc.setDocPath(NewLoactionConstants.IMAGENEW + lastFileName);
			doc.setUploadTime(new Date());
			docMapper.insertEntity(doc);
		}

		return MessageBox.build("1", "上传成功");

	}

	/**
	 * 根据历史标上传资料
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年10月13日
	 * @param loginMem
	 * @param addip
	 * @param borrowId
	 * @param oldBorrowId
	 * @return
	 * @throws Exception
	 *             MessageBox
	 */
	@Override
	public MessageBox saveBorrowInfosFromOldBorrow(ShiroUser loginMem, String addip, Integer borrowId, Integer oldBorrowId) throws Exception {
		BorrowVo borrowVo = borrowMapper.selectByPrimaryKey(oldBorrowId);
		if (borrowVo == null) {
			return MessageBox.build("0", "借款记录不存在");
		}
		borrowVo = borrowMapper.selectByPrimaryKey(borrowId);
		if (borrowVo == null) {
			return MessageBox.build("0", "借款记录不存在");
		}
		int borrowUserId = borrowVo.getUserId();
		int userId = loginMem.getUserId();
		if (userId != borrowUserId) {
			return MessageBox.build("0", "非借款用户本人不能操作");
		}
		salariatBorrowMapper.insertPicFromBorrowId(addip, borrowId, oldBorrowId);
		return MessageBox.build("1", "上传成功");
	}

	/**
	 * 删除上传资料
	 * <p>
	 * Description:删除上传资料<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年8月18日
	 * @param loginMem
	 *            当前登录用户
	 * @param docId
	 *            文件ID
	 * @param borrowId
	 *            借款记录ID
	 * @return
	 * @throws Exception
	 *             MessageBox
	 */
	@Override
	public MessageBox deleteDoc(ShiroUser loginMem, Integer docId, Integer borrowId) throws Exception {
		if (!"".equals(checkBorrowCertification(loginMem, borrowId, "doc"))) {
			return MessageBox.build("0", checkBorrowCertification(loginMem, borrowId, "doc"));
		}
		docMapper.deleteDoc(docId);
		return MessageBox.build("1", "删除成功");
	}

	/**
	 * 删除借款标全部资料
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年10月17日
	 * @param loginMem
	 * @param borrowId
	 * @return
	 * @throws Exception
	 *             MessageBox
	 */
	@Override
	public MessageBox deleteAllDoc(ShiroUser loginMem, Integer borrowId) throws Exception {
		if (!"".equals(checkBorrowCertification(loginMem, borrowId, "doc"))) {
			return MessageBox.build("0", checkBorrowCertification(loginMem, borrowId, "doc"));
		}
		docMapper.deleteDocByBorrowId(borrowId);
		return MessageBox.build("1", "删除成功");
	}

	/**
	 * 验证借款资格
	 * <p>
	 * Description:验证借款资格<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年8月18日
	 * @param userId
	 * @param borrowId
	 * @return
	 * @throws Exception
	 *             String
	 */
	private String checkBorrowCertification(ShiroUser loginMem, Integer borrowId, String doType) throws Exception {
		String msg = "";

		if (loginMem == null) {
			return "未登录用户不能操作。";
		}

		int userId = loginMem.getUserId();// 当前登录用户
		// Member memberVo = memberMapper.queryById(userId);

		int rst = memberMapper.queryRealNameIspassed(userId);
		if (rst == 0) {
			return "还未进行实名认证！";
		}

		rst = memberMapper.queryEmailIspassed(userId);
		if (rst == 0) {
			return "还未进行邮箱认证！";
		}

		rst = memberMapper.queryMobileIspassed(userId);
		if (rst == 0) {
			return "还未进行手机认证！";
		}

		if (0 != loginMem.getIsFinancialUser()) {// 1 理财用户 0 借款用户
			return "抱歉，您是理财用户，不能申请诚薪贷和诚商贷。";
		}
		if (borrowId != null) {
			BorrowVo borrowVo = borrowMapper.selectByPrimaryKey(borrowId);
			if (borrowVo == null) {
				return "借款记录不存在";
			}
			int borrowUserId = borrowVo.getUserId();
			if (userId != borrowUserId) {
				return "非借款用户本人不能操作。";
			}

		}
		if (!"doc".equals(doType)) {
			BorrowCnd borrowCnd = new BorrowCnd();
			borrowCnd.setUserId(userId);
			int count = borrowMapper.getTenderingCount(borrowCnd);
			if (count > 0) {
				return "抱歉，您已经有一个借款标正在投标中，必须在满标后才能发新的借款标！";
			}

		}

		return msg;
	}

	/**
	 * 验证借款资格
	 * <p>
	 * Description:跳转至产品页面/帮助中心关于借款页面<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年9月11日
	 * @param loginMem
	 * @param borrowId
	 * @param viewType
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	public ModelAndView checkBorrowReturnView(ShiroUser loginMem, Integer borrowId, String viewType, int productType, String doType) throws Exception {
		String s = checkBorrowCertification(loginMem, borrowId, doType);
		if (!"".equals(s)) {
			ModelAndView mv = null;
			if ("help".equals(viewType)) {
				if (productType == Constants.BORROW_PRODUCT_TYPE_SALARIAT) {
					mv = new ModelAndView("/helpcenter/aboutBorrow/chengxindai");
				} else if (productType == Constants.BORROW_PRODUCT_TYPE_COMMERCE) {
					mv = new ModelAndView("/helpcenter/aboutBorrow/chengshangdai");
				} else {
					mv = new ModelAndView("/helpcenter/aboutBorrow/jingzhidai");
				}
				if (productType == Constants.BORROW_PRODUCT_TYPE_COMMERCE) {
					mv.addObject("scrollToHeight", 1200);
				}
			} else {
				mv = new ModelAndView("/borrow/borrowProduct");
			}
			mv.addObject("msg", s);
			return mv;
		}
		return null;
	}

	/**
	 * 借款标编辑初始化
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年10月11日
	 * @param borrowId
	 * @param loginMem
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@Override
	public ModelAndView initEditBorrow(Integer borrowId, ShiroUser loginMem) throws Exception {

		ModelAndView mv = new ModelAndView("/borrow/editBorrow");

		BorrowVo b = borrowMapper.selectByPrimaryKey(borrowId);

		if (b.getStatus() == 1 && b.getApprstatus() == 0) {
			mv = new ModelAndView("/borrow/editBorrowAll");
		}

		if (b.getMostAccount().compareTo(new BigDecimal(0)) == 0) {
			b.setMostAccount(null);
		}

		mv.addObject("b", b);

		if (b.getIsGuaranty() != null && b.getIsGuaranty() == 1 && b.getGuarantyOrganization() > 0) {
			GuarantyOrganization g = organizationMapper.getByProperty("id", b.getGuarantyOrganization() + "").get(0);
			mv.addObject("g", g);
		}

		if (b.getIsMortgage() != null) {
			mv.addObject("m", mortgageMapper.getMortgageByBorrowId(borrowId));
		}

		mv.addObject("ber", borrowerMapper.queryBorrowerByBorrowId(borrowId));
		
		/*获取借款标与业务员关系*/
		BorrowBusinessVo borrowBusinessVo=borrowBusinessMapper.selectBorBusByBorrowId(borrowId);
		mv.addObject("borrowBusinessVo", borrowBusinessVo);

		mv = BorrowUtil.initInfos(mv, loginMem, approMapper, organizationMapper,businessMapper);

		return mv;
	}

	/**
	 * 借款标编辑提交
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年10月11日
	 * @param loginMem
	 * @param salariatBorrowVo
	 * @param borrower
	 * @param mortgage
	 * @param borrowId
	 * @return
	 * @throws Exception
	 *             MessageBox
	 */
	@Override
	public MessageBox updateBorrow(ShiroUser loginMem, SalariatBorrowVo salariatBorrowVo, Borrower borrower, Mortgage mortgage, int borrowId, Integer businessUserId)
			throws Exception {

		BorrowVo borrowVo = borrowMapper.selectByPrimaryKeyForUpdate(borrowId);
		if (borrowVo == null) {
			return MessageBox.build("0", "借款记录不存在");
		}
		int borrowUserId = borrowVo.getUserId();
		int userId = loginMem.getUserId();
		if (userId != borrowUserId) {
			return MessageBox.build("0", "非借款用户本人不能操作");
		}
		if (borrowVo.getBorrowtype() == 3 || borrowVo.getBorrowtype() == 4 || (borrowVo.getStatus() != 1 && borrowVo.getStatus() != 2)) {
			return MessageBox.build("0", "该借款标不能修改，可能原因：1)该标为净值标或秒标 2)该标不处于审核或投标中状态");
		}

		if (borrowVo.getStatus() == 1 && borrowVo.getApprstatus() == 0) {
			// 合同编号是否重复
			String newNo = salariatBorrowVo.getContractNo();
			Borrow b = borrowMapper.queryBorrowByContractNo(newNo);
			if (b != null && b.getId().intValue() != salariatBorrowVo.getId().intValue()) {
				return MessageBox.build("0", "借款单编号已存在，请重新输入。");
			}
			salariatBorrowVo = BorrowUtil.dealBorrowInfo(salariatBorrowVo);

			// 密码加密
			String newBidPwd = salariatBorrowVo.getBidPassword();
			String oldBidPwd = salariatBorrowVo.getOldBidPassword();
			if (newBidPwd != null && !"".equals(newBidPwd) && !"mimamima".equals(newBidPwd)) {
				salariatBorrowVo.setBidPassword(MD5.toMD5(salariatBorrowVo.getBidPassword()));
			} else if (newBidPwd == null || "".equals(newBidPwd)) {
				salariatBorrowVo.setBidPassword(null);
			} else {
				salariatBorrowVo.setBidPassword(oldBidPwd);
			}
			/*
			 * if (salariatBorrowVo.getBidPassword() != null &&
			 * !"".equals(salariatBorrowVo.getBidPassword()) &&
			 * !MD5.toMD5(salariatBorrowVo
			 * .getBidPassword()).equals(borrowVo.getBidPassword())) {
			 * salariatBorrowVo
			 * .setBidPassword(MD5.toMD5(salariatBorrowVo.getBidPassword())); }
			 */

			salariatBorrowMapper.updateBorrowFullInfoById(salariatBorrowVo);
		} else {
			if (salariatBorrowVo.getIsJointGuaranty() == 0) {
				salariatBorrowVo.setJointGuaranty(null);
			}
			salariatBorrowMapper.updateBorrowById(salariatBorrowVo);
		}

		borrower.setBorrowId(borrowId);
		salariatBorrowMapper.updateBorrowerByBorrowId(borrower);
		
		/*更新借款标业务员关联信息*/
		BorrowBusinessVo borrowBusinessVo = borrowBusinessMapper.selectBorBusByBorrowIdForUpdate(Integer.valueOf(borrowId));
	    borrowBusinessVo.setUserId(businessUserId);
	    //判断业务员是否选择的其他
	    if(businessUserId.intValue()==0){
	    	borrowBusinessVo.setUsername("其他");
	    }else{
	    	Member member = baseMemberMapper.queryById(businessUserId);
		    if (member != null) {
		    	borrowBusinessVo.setUsername(member.getUsername());
		    }
	    }	    
	    BorrowBusiness borrowBusiness = new BorrowBusiness();
	    BeanUtils.copyProperties(borrowBusinessVo, borrowBusiness);
	    borrowBusinessMapper.updateBorrowBusiness(borrowBusiness);

		if (1 == borrowVo.getIsMortgage()) {
			mortgage.setBorrowId(borrowId);
			salariatBorrowMapper.updateMortgageByBorrowId(mortgage);
		}

		return MessageBox.build("1", "修改成功");
	}
}
