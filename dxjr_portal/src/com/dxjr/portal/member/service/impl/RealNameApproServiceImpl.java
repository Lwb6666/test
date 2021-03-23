package com.dxjr.portal.member.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dxjr.base.entity.RealNameAppro;
import com.dxjr.base.mapper.BaseRealNameApproMapper;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.lottery.service.LotteryChanceInfoService;
import com.dxjr.portal.member.mapper.ApproModifyLogMapper;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.mapper.RealNameApproMapper;
import com.dxjr.portal.member.service.IntegralService;
import com.dxjr.portal.member.service.MobileApproService;
import com.dxjr.portal.member.service.RealNameApproService;
import com.dxjr.portal.member.vo.ApproModifyLog;
import com.dxjr.portal.member.vo.IntegralVo;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.MobileApproVo;
import com.dxjr.portal.member.vo.RealNameApproCnd;
import com.dxjr.portal.member.vo.RealNameApproVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.statics.NewLoactionConstants;
import com.dxjr.portal.webservice.client.identifier.Identifier;
import com.dxjr.portal.webservice.client.identifier.finance.CheckResponse;
import com.dxjr.portal.webservice.client.identifier.finance.IdentifierData;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.file.FileUtil;

/**
 * <p>
 * Description:实名认证实现类<br />
 * </p>
 * 
 * @title RealNameApproServiceImpl.java
 * @package com.dxjr.portal.member.service.impl
 * @author justin.xu
 * @version 0.1 2014年4月24日
 */
@Service
public class RealNameApproServiceImpl implements RealNameApproService {
	private static final Logger logger = Logger.getLogger(RealNameApproServiceImpl.class);

	@Autowired
	private RealNameApproMapper realNameApproMapper;
	@Autowired
	private BaseRealNameApproMapper baseRealNameApproMapper;
	@Autowired
	private IntegralService integralService;
	@Autowired
	private MobileApproService mobileApproService;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private ApproModifyLogMapper approModifyLogMapper;
	@Autowired
	private LotteryChanceInfoService lotteryChanceInfoService;

	@Override
	public List<RealNameApproVo> queryRealNameApproList(RealNameApproCnd realNameApproCnd) throws Exception {
		return realNameApproMapper.queryRealNameApproList(realNameApproCnd);
	}

	@Override
	public RealNameApproVo queryRealNameApproByUserId(Integer memberId) throws Exception {
		RealNameApproCnd realNameApproCnd = new RealNameApproCnd();
		realNameApproCnd.setUserId(memberId);
		List<RealNameApproVo> list = realNameApproMapper.queryRealNameApproList(realNameApproCnd);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public String saveRealNameAppr(MultipartFile[] files, RealNameAppro realNameAppro, HttpServletRequest request) throws Exception {
		String result = "success";

		RealNameApproCnd realNameApproCnd = new RealNameApproCnd();
		realNameApproCnd.setUserId(realNameAppro.getUserId());
		List<RealNameApproVo> realNameApproVoList = realNameApproMapper.queryRealNameApproList(realNameApproCnd);

		if (realNameApproVoList != null && realNameApproVoList.size() > 0) {
			if (realNameApproVoList.get(0).getIsPassed() == 0) {
				return "实名认证已申请提交，请勿重复操作！";
			}
			if (realNameApproVoList.get(0).getIsPassed() == 1) {
				return "实名认证已通过，请勿重复操作！";
			}
		}
		// 上传文件
		result = this.uploadRealNameFiles(files, realNameAppro, request);
		if (!result.equals("success")) {
			return result;
		}
		// 验证实名认证信息
		result = this.validateRealNameAppr(realNameAppro, request);
		if (!result.equals("success")) {
			return result;
		}
		// 保存数据
		realNameAppro.setIsPassed(Constants.REALNAME_APPR_ISPASSED_WAIT_APPROVE);

		// 更新
		final Integer platform = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getPlatform();
		if (null != realNameApproVoList && realNameApproVoList.size() > 0) {
			realNameAppro.setAppTime(DateUtils.getTime());
			realNameAppro.setAppIp(request.getRemoteAddr());
			realNameAppro.setVersion(realNameApproVoList.get(0).getVersion() + 1); // 更新版本号
			realNameAppro.setPlatform(platform);
			baseRealNameApproMapper.updateEntity(realNameAppro);

			ApproModifyLog apprModifyLog = ApproModifyLog.createApproModifyVo(platform, realNameAppro.getUserId(), request.getRemoteAddr(), 0, "修改实名认证信息，待审核", realNameAppro.getRealName(),
					BusinessConstants.APPRO_MODIFY_LOG_TYPE_REALNAME);
			approModifyLogMapper.insertEntity(apprModifyLog);

			// 新增
		} else {
			realNameAppro.setAppTime(DateUtils.getTime());
			realNameAppro.setAppIp(HttpTookit.getRealIpAddr(request));
			realNameAppro.setVersion(1); // 版本号初始为1
			realNameAppro.setPlatform(platform);
			baseRealNameApproMapper.insertEntity(realNameAppro);

			ApproModifyLog apprModifyLog = ApproModifyLog.createApproModifyVo(platform, realNameAppro.getUserId(), request.getRemoteAddr(), 0, "新增实名认证信息，待审核", realNameAppro.getRealName(),
					BusinessConstants.APPRO_MODIFY_LOG_TYPE_REALNAME);
			approModifyLogMapper.insertEntity(apprModifyLog);
		}
		return result;
	}

	@Override
	public String queryRealNameApproRepeat(RealNameApproCnd realNameApproCnd) throws Exception {
		String result = "success";
		if (null == realNameApproCnd) {
			return "数据有误，请确认";
		}
		// 身分证号码是否重复
		if (null != realNameApproCnd.getIdCardNo()) {
			RealNameApproCnd idCardNoCnd = new RealNameApproCnd();
			idCardNoCnd.setIdCardNo(realNameApproCnd.getIdCardNo());
			idCardNoCnd.setId(realNameApproCnd.getId());
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			MemberVo memberVo = memberMapper.queryMemberByloginname(shiroUser.getUserName());
			Integer IsFinancialUser = memberVo.getIsFinancialUser();
			/**
			 * 理财/借款用户（1 理财用户 0 借款用户） 一个借款人可能需要有多个账号,排除借款人身份证唯一限制检查！
			 */
			if (IsFinancialUser == Integer.parseInt(Constants.IS_FINANCIAL_USER)) {
				Integer count = realNameApproMapper.queryRepeatRealNameApproCount(idCardNoCnd);
				if (count > 0) {
					return "身份证号码已存在,请确认！";
				}
			}

		}
		return result;
	}

	/**
	 * <p>
	 * Description:验证实名认证信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月24日
	 * @param realNameAppro
	 * @return String
	 */
	private String validateRealNameAppr(RealNameAppro realNameAppro, HttpServletRequest request) throws Exception {
		StringBuffer msg = new StringBuffer();
		if (realNameAppro.getRealName() == null || realNameAppro.getRealName().equals("")) {
			msg.append("-真实姓名不能为空 \n");
		}
		if (realNameAppro.getNation() == null || realNameAppro.getNation().equals("")) {
			msg.append("-民族不能为空 \n");
		}
		if (realNameAppro.getBirthDay() == null || realNameAppro.getBirthDay().equals("")) {
			msg.append("-出生日期不能为空\n");
		}
		String resultBirthDay = judgeBirthDay(realNameAppro.getBirthDay());
		if (!resultBirthDay.equals("success")) {
			msg.append(resultBirthDay + "\n");
		}
		if (realNameAppro.getIdCardNo() == null || realNameAppro.getIdCardNo().equals("")) {
			msg.append("-证件号码不能为空\n");
		} else {
			RealNameApproCnd realNameApproCnd = new RealNameApproCnd();
			realNameApproCnd.setIdCardNo(realNameAppro.getIdCardNo());
			String idCardNoResult = this.queryRealNameApproRepeat(realNameApproCnd);
			if (!idCardNoResult.equals("success")) {
				msg.append("证件号码已存在，请重新输入\n");
			} else {
				if (realNameAppro.getCardType().equals("身份证")) {
					if (realNameAppro.getIdCardNo().length() != 15 && realNameAppro.getIdCardNo().length() != 18) {
						msg.append("身份证号码必须为15位或18位\n");
					} else {
						String resultMsg = judgeIdcard(realNameAppro.getIdCardNo());
						if (!resultMsg.equals("success")) {
							msg.append(resultMsg + "\n");
						}
					}
				}
			}
		}
		if (realNameAppro.getNativePlace() == null || realNameAppro.getNativePlace().equals("")) {
			msg.append("-籍贯不能为空\n");
		}
		if (realNameAppro.getPic1() == null || realNameAppro.getPic1().equals("")) {
			msg.append("-证件正面照片不能为空\n");
		}
		if (realNameAppro.getPic2() == null || realNameAppro.getPic2().equals("")) {
			msg.append("-证件背面照片不能为空\n");
		}
		// 如果有错误信息
		if (msg.length() > 0) {
			FileUtil.deleteFile(request, realNameAppro.getPic1());
			FileUtil.deleteFile(request, realNameAppro.getPic2());
		} else {
			msg.append("success");
		}
		return msg.toString();
	}

	/**
	 * <p>
	 * Description:上传图像<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月24日
	 * @param files
	 * @param realNameAppro
	 * @return
	 * @throws Exception String
	 */
	private String uploadRealNameFiles(MultipartFile[] files, RealNameAppro realNameAppro, HttpServletRequest request) throws Exception {
		String result = "success";
		if (null == files || files.length <= 0) {
			return "请选择身份证文件！";
		}
		// 循环获取file数组中得文件
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			// 验证文件格式和大小是否正确
			result = FileUtil.validateFileInfo(file, BusinessConstants.IMAGE_MAX_SIZE, BusinessConstants.IMAGE_FILE_TYPE_LIST);
			if (!result.equals("success")) {
				return result;
			}
			// 改变文件名
			String filename = file.getOriginalFilename();
			String extName = filename.substring(filename.lastIndexOf(".")).toLowerCase();
			String lastFileName = UUID.randomUUID().toString() + extName;
			// 增加水印上传
			FileUtil.uploadAddWatermark(file, request, NewLoactionConstants.UPLOAD_FILE_PATH, lastFileName, BusinessConstants.WATERMARK_PNG);
			// 正面
			if (i == 0) {
				realNameAppro.setPic1("/" + NewLoactionConstants.UPLOAD_FILE_PATH + "/" + lastFileName);
				// 反面
			} else if (i == 1) {
				realNameAppro.setPic2("/" + NewLoactionConstants.UPLOAD_FILE_PATH + "/" + lastFileName);
			}
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String realnameAppro(int userid, String realname, String idcard, String ip) throws Exception {
		String result = "success";
		if (realname == null || realname.equals("")) {
			return "真实姓名不能为空！";
		}
		if (idcard == null || idcard.equals("")) {
			return "身份证号不能为空！";
		}
		if (idcard.length() != 15 && idcard.length() != 18) {
			return "身份证号长度必须为15位或18位！";
		}
		String msg = judgeIdcard(idcard);
		if (!msg.equals("success")) {
			return msg;
		}
		// 锁定用户记录
		MemberVo memberVo = memberMapper.queryMemberByIdForUpdate(userid);

		RealNameApproCnd realNameApproCnd = new RealNameApproCnd();
		realNameApproCnd.setIdCardNo(idcard);
		String idCardNoResult = this.queryRealNameApproRepeat(realNameApproCnd);
		if (!idCardNoResult.equals("success")) {
			return "证件号码已存在，请重新输入";
		}
		realNameApproCnd = new RealNameApproCnd();
		realNameApproCnd.setUserId(memberVo.getId());

		List<RealNameApproVo> realNameApproVoList = realNameApproMapper.queryRealNameApproList(realNameApproCnd);
		if (realNameApproVoList != null && realNameApproVoList.size() > 0) {
			if (realNameApproVoList.get(0).getIsPassed() == 1) {
				return "实名认证已通过，请勿重复操作！";
			}
		}

		if (realNameApproMapper.countRealnameAuthLogByUserId(userid) >= 3) {
			realNameApproMapper.insertRealnameAuthLog(userid, realname, idcard, 3);
			return "您进行系统实名认证次数过多，请转人工实名认证。";
		}

		// 进行真实姓名与身份证号匹配验证
		/**
		 *CheckResponse checkResponse = Identifier.simpleCheck(idcard, realname);
		if (checkResponse == null || !Integer.valueOf(100).equals(checkResponse.getResponseCode())) {
			if (Integer.valueOf(-31).equals(checkResponse.getResponseCode())) {
				logger.error("实名认证,账户余额不足..");
			}
			realNameApproMapper.insertRealnameAuthLog(userid, realname, idcard, 4);
			return "系统实名认证失败，请进行人工实名认证。";
		}
		 * 
		 * 
		 */
		CheckResponse checkResponse = null;
		if (1 == 1 ) {

			return "系统实名认证失败，请进行人工实名认证。";
		}

		IdentifierData identifierData = checkResponse.getIdentifier().getValue();
		String resultVal = identifierData.getResult().getValue();
		// boolean ispass = Identifier.isPass(identifierData);
		if ("一致".equals(resultVal)) {
			realNameApproMapper.insertRealnameAuthLog(userid, realname, idcard, 0);

			RealNameAppro realNameAppro = new RealNameAppro();
			realNameApproCnd = new RealNameApproCnd();
			realNameApproCnd.setUserId(userid);

			// 更新
			final Integer platform = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getPlatform();
			if (null != realNameApproVoList && realNameApproVoList.size() > 0) {
				RealNameApproVo realNameApproVo = realNameApproVoList.get(0);
				if (realNameApproVo != null) {
					realNameApproVo.setRealName(realname);
					realNameApproVo.setIdCardNo(idcard);
					realNameApproVo.setIsPassed(Constants.REALNAME_APPR_ISPASSED_PASSED); // 认证通过
					realNameApproVo.setSex("男性".equals(identifierData.getSex().getValue()) ? "0" : "1");
					realNameApproVo.setBirthDay(identifierData.getBirthday().getValue());
					realNameApproVo.setAppTime(DateUtils.getTime());
					realNameApproVo.setCardType("身份证");
					realNameApproVo.setAppIp(ip);
					realNameApproVo.setVersion(1); // 版本号初始为1
					realNameApproVo.setApproveTime(DateUtils.getTime());
					realNameApproVo.setApproveUser("-1");
					realNameApproVo.setReason("自动审核通过");
					BeanUtils.copyProperties(realNameApproVo, realNameAppro);
					realNameAppro.setPlatform(platform);
					baseRealNameApproMapper.updateEntity(realNameAppro);
					ApproModifyLog apprModifyLog = ApproModifyLog.createApproModifyVo(platform, realNameAppro.getUserId(), ip, 1, "实名认证通过", realname, BusinessConstants.APPRO_MODIFY_LOG_TYPE_REALNAME);
					approModifyLogMapper.insertEntity(apprModifyLog);
				}
			} else { // 新增
				realNameAppro.setUserId(userid);
				realNameAppro.setRealName(realname);
				realNameAppro.setIdCardNo(idcard);
				realNameAppro.setIsPassed(Constants.REALNAME_APPR_ISPASSED_PASSED); // 认证通过
				realNameAppro.setSex("男性".equals(identifierData.getSex().getValue()) ? "0" : "1");
				realNameAppro.setBirthDay(identifierData.getBirthday().getValue());
				realNameAppro.setAppTime(DateUtils.getTime());
				realNameAppro.setCardType("身份证");
				realNameAppro.setAppIp(ip);
				realNameAppro.setVersion(1); // 版本号初始为1
				realNameAppro.setApproveTime(DateUtils.getTime());
				realNameAppro.setApproveUser("-1");
				realNameAppro.setReason("自动审核通过");
				realNameAppro.setPlatform(platform);
				baseRealNameApproMapper.insertEntity(realNameAppro);

				ApproModifyLog apprModifyLog = ApproModifyLog.createApproModifyVo(platform, realNameAppro.getUserId(), ip, 1, "新增实名认证通过", realname, BusinessConstants.APPRO_MODIFY_LOG_TYPE_REALNAME);
				approModifyLogMapper.insertEntity(apprModifyLog);

			}

			// 增加信用积分
			IntegralVo integralVo = integralService.queryIntegralByUserId(realNameAppro.getUserId());
			integralVo.setCreditIntegral(integralVo.getCreditIntegral() + BusinessConstants.REALNAME_APPRO_CREDIT_INTEGRAL);
			integralService.updateCreditLevel(integralVo);
			// 手机与实名认证通过，发放奖励
			MobileApproVo mobileApproVo = mobileApproService.queryMobileApproByUserId(realNameAppro.getUserId());
			if (null != mobileApproVo && null != mobileApproVo.getPassed() && mobileApproVo.getPassed() == 1) {
				mobileApproService.saveRealNameMobileAward(realNameAppro.getUserId(), ip);
			}

			// 实名认证通过,更新用户的论坛权限
			memberMapper.updateBbsUserGroupForRegistered(userid);
			memberMapper.insertBbsUserPermissionForRegistered(userid);
			
/*		//发放“实名认证奖”抽奖机会
			if (lotteryChanceInfoService.selectCountByCodeAndUserId(BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_REAL_NAME_APPRO, realNameAppro.getUserId()) == 0) { //注册时间大于等于定义的新手起始时间
				//新增“实名认证奖”抽奖机会信息记录
				lotteryChanceInfoService.insertLotteryChanceInfoByCode(realNameAppro.getUserId(), BusinessConstants.LOTTERY_CHANCE_RULE_INFO_CODE_TYPE_REAL_NAME_APPRO);
			}*/
			return result;
		} else if ("不一致".equals(resultVal)) {
			realNameApproMapper.insertRealnameAuthLog(userid, realname, idcard, 1);
		} else if ("库中无此号".equals(resultVal)) {
			realNameApproMapper.insertRealnameAuthLog(userid, realname, idcard, 2);
		} else {
			realNameApproMapper.insertRealnameAuthLog(userid, realname, idcard, 5);
		}
		return "您的真实姓名与身份证号匹配失败";
	}

	/**
	 * 
	 * <p>
	 * Description:判断是否满18周岁<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年10月16日
	 * @param idcard
	 * @return String
	 */
	public String judgeIdcard(String idcard) {
		String result = "success";
		String idcardYMD = "";
		if (idcard.length() == 18) {
			idcardYMD = idcard.substring(6, 14);
		}
		if (idcard.length() == 15) {
			idcardYMD = "19" + idcard.substring(6, 12);
		}
		try {
			Date birthDay = DateUtils.parse(idcardYMD, DateUtils.YMD);
			Date date = DateUtils.yearOffset(birthDay, 18);
			Date now = DateUtils.parse(DateUtils.format(new Date(), DateUtils.YMD_DASH), DateUtils.YMD_DASH);
			int days = DateUtils.dayDiff(now, date);
			if (days <= 0) {
				return "未满18周岁，无法进行实名认证！";
			}
		} catch (Exception e) {
			result = "身份证格式不正确，出生日期错误！";
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * Description:判断生日是否满18周岁<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年10月16日
	 * @param birthDay
	 * @return String
	 */
	public String judgeBirthDay(String birthDays) {
		String result = "success";
		try {
			Date birthDay = DateUtils.parse(birthDays, DateUtils.YMD_DASH);
			Date date = DateUtils.yearOffset(birthDay, 18);
			Date now = DateUtils.parse(DateUtils.format(new Date(), DateUtils.YMD_DASH), DateUtils.YMD_DASH);
			int days = DateUtils.dayDiff(now, date);
			if (days <= 0) {
				return "出生日期未满18周岁，无法进行实名认证！";
			}
		} catch (Exception e) {
			result = "出生日期格式错误！";
			e.printStackTrace();
		}
		return result;
	}
}
