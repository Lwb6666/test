package com.dxjr.portal.tzjinterface.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.Member;
import com.dxjr.portal.member.mapper.MemberRegisterMapper;
import com.dxjr.portal.member.mapper.MobileApproMapper;
import com.dxjr.portal.member.service.MemberRegisterService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberRegisterCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.MobileApproCnd;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.tzjinterface.constant.TZJConstants;
import com.dxjr.portal.tzjinterface.controller.TZJMemberController;
import com.dxjr.portal.tzjinterface.entity.BidInfo;
import com.dxjr.portal.tzjinterface.entity.CreateUserReq;
import com.dxjr.portal.tzjinterface.entity.InvestInfo;
import com.dxjr.portal.tzjinterface.entity.LoginReq;
import com.dxjr.portal.tzjinterface.entity.QueryReq;
import com.dxjr.portal.tzjinterface.entity.Redirect;
import com.dxjr.portal.tzjinterface.entity.RepayInfo;
import com.dxjr.portal.tzjinterface.entity.UserInfo;
import com.dxjr.portal.tzjinterface.exception.PlatformException;
import com.dxjr.portal.tzjinterface.mapper.InvitationBorrowMapper;
import com.dxjr.portal.tzjinterface.mapper.MemberBindingMapper;
import com.dxjr.portal.tzjinterface.service.MemberBindingService;
import com.dxjr.portal.tzjinterface.service.PlatformService;
import com.dxjr.portal.tzjinterface.util.CryptException;
import com.dxjr.portal.tzjinterface.vo.MemberBindingVo;
import com.dxjr.portal.tzjinterface.vo.TzjQueryCnd;
import com.dxjr.portal.util.JsonUtils;
import com.dxjr.portal.util.StringUtils;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.HttpTookit;
import com.dxjr.utils.exception.AppException;

@Service
public class PlatformServiceImpl implements PlatformService {
	
	public Logger logger = Logger.getLogger(TZJMemberController.class);
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private MobileApproMapper mobileApproMapper;
	
	@Autowired
	private MemberBindingService memberBindingService;
	
	@Autowired
	private MemberRegisterMapper memberRegisterMapper;
	
	@Autowired
	private MemberBindingMapper memberBindingMapper;
	
	@Autowired
	private MemberRegisterService memberRegisterService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private InvitationBorrowMapper invitationBorrowMapper;

	
	/**
	 * 创建新账户
	 */
	@Override
	public UserInfo createUser(CreateUserReq req) throws Exception {
		logger.debug("======================== createUser 创建新账户  begin ====================== ");
		UserInfo userInfo=null;
		//获取投之家用户名与手机号
		String telephone=req.getTelephone();
		String username=req.getUsername();
		if(telephone==null || "".equals(telephone)){
			throw new PlatformException(CryptException.MobileIsNull,CryptException.getMessage(CryptException.MobileIsNull));
		}
		if(username==null || "".equals(username)){
			throw new PlatformException(CryptException.UserIsNull,CryptException.getMessage(CryptException.UserIsNull));
		}

		/*根据手机号或用户名获取用户信息*/
		MemberBindingVo cnd=new MemberBindingVo();
		cnd.setIsSuccess(1);
		cnd.setUserName(username);
		MemberBindingVo bindUser = memberBindingMapper.queryMemberBindingInfo(cnd);		
		/*判断投之家用户是否存在，不存在则创建，存在则直接返回*/
		if(bindUser==null){
			//验证手机是否存在
			if (this.isMobileNumUsed(telephone)) {
				throw new PlatformException(CryptException.MobileIsExist,CryptException.getMessage(CryptException.MobileIsExist));
			}
			//验证邮箱是否存在
//			if (this.isEmailUsed(req.getEmail())) {
//				throw new PlatformException(CryptException.EmailIsExist,CryptException.getMessage(CryptException.EmailIsExist));
//			}
			String autoGenerateUserName = null;
			String autoGeneratePassword = null;
			Integer bmid=null;				
			Member member = new Member();
			autoGenerateUserName = generateUserName(username, null);
			if (autoGenerateUserName != null) {
				member.setUsername(autoGenerateUserName);
			}
			// 生成6位随机密码
			autoGeneratePassword = generatePassword();
			if (autoGeneratePassword != null) {
				member.setLogpassword(autoGeneratePassword);
			}
			member.setIsFinancialUser(1);// 默认都生成为理财用户
			member.setEmail(req.getEmail());
			member.setSource(Integer.valueOf(17));// 投之家
			member.settId("tzj");
			member.setPlatform(BusinessConstants.MEMBER_OPERATE_ON_PORTAL);
			member.setIp(HttpTookit.getRealIpAddr(request));
			String result = memberRegisterService.tzjInsertAutoGenerateMember(member, req, request, session);
			if ("success".equals(result)) {
				// 绑定用户
				bmid=bindingMemberInfo(member.getId(), String.valueOf(0), req, TZJConstants.POST_URL);
				if(bmid!=null){
					MemberBindingVo cndMember=new MemberBindingVo();
					cndMember.setIsSuccess(1);
					cndMember.setId(bmid);
					MemberBindingVo memberResult=memberBindingService.queryMemberBindingInfo(cndMember);						
					userInfo=packageUserInfo(memberResult);
				}
			}								
		}else{
			userInfo = packageUserInfo(bindUser);
		}

		logger.debug("======================== createUser 创建新账户  end ====================== ");
		return userInfo;
	}
	
	/**
	 * 平台老账户绑定接口
	 * @author WangQianJin
	 * @title @param req
	 * @title @return
	 * @title @throws TzjNewInterfaceException
	 * @date 2016年3月25日
	 */
	@Override
	public Redirect bindUser(CreateUserReq req) throws PlatformException {
		logger.debug("======================== createUser 平台老账户绑定  begin ====================== ");
		Map<String,String> result  = new TreeMap<String,String>();
		result.put("username", req.getUsername());
		result.put("telephone", req.getTelephone());
		result.put("timestamp", (String)request.getAttribute("timestamp"));
		result.put("sign", (String)request.getAttribute("signature"));
		result.put("nonce", (String)request.getAttribute("nonce"));
		result.put("email", req.getEmail());
		result.put("cardNo", req.getIdCard().getNumber());
		result.put("realName", req.getIdCard().getName());
		result.put("from", "touzhijia");
		result.put("service", "bindUser");		
		result.put("type", String.valueOf(req.getType()));	
		request.getServletContext().setAttribute("bindUserResult", result);
		request.getServletContext().setAttribute("bindUserMessage", request.getAttribute("reqMessage"));
		logger.debug("======================== createUser 平台老账户绑定  end ====================== ");
		return new Redirect("/api/tzj/forTZJLoginNew.html");
	}


	/**
	 * 单点登录
	 * @author WangQianJin
	 * @title @param req
	 * @title @return
	 * @title @throws TzjNewInterfaceException
	 * @date 2016年3月25日
	 */
	@Override
	public Redirect login(LoginReq req) throws PlatformException {
		logger.debug("======================== login 单点登录  begin ====================== ");
		Map<String,String> result  = new TreeMap<String,String>();
		result.put("username", req.getUsername());
		result.put("usernamep", req.getUsernamep());
		result.put("bid", req.getBid());
		result.put("type", String.valueOf(req.getType()));		
		request.getServletContext().setAttribute("ssoResult", result);
		request.getServletContext().setAttribute("reqMessage", request.getAttribute("reqMessage"));
		logger.debug("======================== login 单点登录  end ====================== ");
		return new Redirect("/api/tzj/ssologinNew.html");
	}

	/**
	 * 用户信息查询
	 * @author WangQianJin
	 * @title @param req
	 * @title @return
	 * @title @throws TzjNewInterfaceException
	 * @date 2016年3月25日
	 */
	@Override
	public List<UserInfo> queryUser(QueryReq req) throws PlatformException {
		logger.debug("======================== queryUser 用户信息查询  queryUser ====================== ");
		TzjQueryCnd cnd=new TzjQueryCnd();
		cnd.setStartTime(req.getTimeRange().getStartTime());
		cnd.setEndTime(req.getTimeRange().getEndTime());
		String arrayValStr="";
		if(StringUtils.isNotEmpty(req.getIndex().getName())){
			if("username".equals(req.getIndex().getName())){
				arrayValStr=JsonUtils.getSqlStrByArray(req.getIndex().getVals());
			}else{
				throw new PlatformException(CryptException.ParamIsError,CryptException.getMessage(CryptException.ParamIsError));
			}			
		}
		if(cnd.getStartTime()==null && cnd.getEndTime()==null && StringUtils.isEmpty(req.getIndex().getName())){
			throw new PlatformException(CryptException.ParamIsError,CryptException.getMessage(CryptException.ParamIsError));
		}
		cnd.setArrayValStr(arrayValStr);
		List<UserInfo> userList=memberBindingMapper.queryUserInfoByCnd(cnd);
		logger.debug("======================== queryUser 用户信息查询  queryUser ====================== ");
		return userList;
	}

	/**
	 * 标的信息查询
	 * @author WangQianJin
	 * @title @param req
	 * @title @return
	 * @title @throws TzjNewInterfaceException
	 * @date 2016年3月25日
	 */
	@Override
	public List<BidInfo> queryBids(QueryReq req) throws PlatformException {
		logger.debug("======================== queryBids 标的信息查询  begin ====================== ");
		TzjQueryCnd cnd=new TzjQueryCnd();
		cnd.setStartTime(req.getTimeRange().getStartTime());
		cnd.setEndTime(req.getTimeRange().getEndTime());
		String arrayValStr="";
		if(StringUtils.isNotEmpty(req.getIndex().getName())){
			if("id".equals(req.getIndex().getName())){
				arrayValStr=JsonUtils.getSqlStrByArray(req.getIndex().getVals());
			}else{
				throw new PlatformException(CryptException.ParamIsError,CryptException.getMessage(CryptException.ParamIsError));
			}			
		}
		if(cnd.getStartTime()==null && cnd.getEndTime()==null && StringUtils.isEmpty(req.getIndex().getName())){
			throw new PlatformException(CryptException.ParamIsError,CryptException.getMessage(CryptException.ParamIsError));
		}
		cnd.setArrayValStr(arrayValStr);
		List<BidInfo> bidInfos=invitationBorrowMapper.queryBorrowListByCnd(cnd);
		logger.debug("======================== queryBids 标的信息查询  begin ====================== ");
		return bidInfos;
	}

	/**
	 * 投资信息查询
	 * @author WangQianJin
	 * @title @param req
	 * @title @return
	 * @title @throws TzjNewInterfaceException
	 * @date 2016年3月25日
	 */
	@Override
	public List<InvestInfo> queryInvests(QueryReq req) throws PlatformException {
		logger.debug("======================== queryInvests 投资信息查询  begin ====================== ");
		TzjQueryCnd cnd=new TzjQueryCnd();
		cnd.setStartTime(req.getTimeRange().getStartTime());
		cnd.setEndTime(req.getTimeRange().getEndTime());
		String arrayValStr="";
		if(StringUtils.isNotEmpty(req.getIndex().getName())){
			if("id".equals(req.getIndex().getName())){
				cnd.setType("1");
			}else if("bid".equals(req.getIndex().getName())){
				cnd.setType("2");
			}else if("username".equals(req.getIndex().getName())){
				cnd.setType("3");
			}else{
				throw new PlatformException(CryptException.ParamIsError,CryptException.getMessage(CryptException.ParamIsError));
			}
			arrayValStr=JsonUtils.getSqlStrByArray(req.getIndex().getVals());
		}
		if(cnd.getStartTime()==null && cnd.getEndTime()==null && StringUtils.isEmpty(req.getIndex().getName())){
			throw new PlatformException(CryptException.ParamIsError,CryptException.getMessage(CryptException.ParamIsError));
		}
		cnd.setArrayValStr(arrayValStr);
		List<InvestInfo> investList=invitationBorrowMapper.queryTenderRecordListByCnd(cnd);
		logger.debug("======================== queryInvests 投资信息查询  begin ====================== ");
		return investList;
	}

	/**
	 * 回款记录查询
	 * @author WangQianJin
	 * @title @param req
	 * @title @return
	 * @title @throws TzjNewInterfaceException
	 * @date 2016年3月25日
	 */
	@Override
	public List<RepayInfo> queryRepays(QueryReq req) throws PlatformException {		
		logger.debug("======================== queryRepays 回款记录查询  begin ====================== ");
		TzjQueryCnd cnd=new TzjQueryCnd();
		cnd.setStartTime(req.getTimeRange().getStartTime());
		cnd.setEndTime(req.getTimeRange().getEndTime());
		String arrayValStr="";
		if(StringUtils.isNotEmpty(req.getIndex().getName())){
			if("id".equals(req.getIndex().getName())){
				cnd.setType("1");
			}else if("bid".equals(req.getIndex().getName())){
				cnd.setType("2");
			}else if("username".equals(req.getIndex().getName())){
				cnd.setType("3");
			}else{
				throw new PlatformException(CryptException.ParamIsError,CryptException.getMessage(CryptException.ParamIsError));
			}
			arrayValStr=JsonUtils.getSqlStrByArray(req.getIndex().getVals());
		}
		if(cnd.getStartTime()==null && cnd.getEndTime()==null && StringUtils.isEmpty(req.getIndex().getName())){
			throw new PlatformException(CryptException.ParamIsError,CryptException.getMessage(CryptException.ParamIsError));
		}
		cnd.setArrayValStr(arrayValStr);
		List<RepayInfo> repayList=invitationBorrowMapper.queryRepayInfoListByCnd(cnd);
		logger.debug("======================== queryRepays 回款记录查询  begin ====================== ");
		return repayList;
	}
	
	/**
	 * 生成用户名
	 * @author WangQianJin
	 * @title @param userName
	 * @title @param autoIndex
	 * @title @return
	 * @date 2016年3月24日
	 */
	private String generateUserName(String userName, Integer autoIndex) {
		String name = userName + (autoIndex == null ? "" : ++autoIndex);
		try {
			memberRegisterService.queryMemberRepeat(null, null, name);
		} catch (AppException app) {
			autoIndex = autoIndex == null ? 0 : autoIndex;
			name = generateUserName(userName, autoIndex);
		} catch (Exception e) {
			logger.info(e.getStackTrace());
		}
		return name;
	}
	
	/**
	 * 生成密码
	 * @author WangQianJin
	 * @title @return
	 * @date 2016年3月24日
	 */
	private String generatePassword() {
		StringBuffer buffer = new StringBuffer(UUID.randomUUID().toString().replace("-", ""));
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int range = buffer.length();
		for (int i = 0; i < 6; i++) {
			sb.append(buffer.charAt(r.nextInt(range)));
		}
		return sb.toString();
	}
	
	/**
	 * 绑定用户
	 * @author WangQianJin
	 * @title @param userId
	 * @title @param loggingType
	 * @title @param result
	 * @title @param askMode
	 * @title @return
	 * @title @throws AppException
	 * @date 2016年3月24日
	 */
	public Integer bindingMemberInfo(Integer userId, String loggingType, CreateUserReq req, String askMode) throws AppException {
		MemberVo memberVo;
		if (userId == null) {
			return null;
		} else {
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setId(userId);
			memberVo = memberService.queryMemberByCnd(memberCnd);
		}
		// 已绑定成功直接返回
		if (!isBindingSucess(memberVo)) {
			logger.error("用户ID为   " + userId + "的账户已经绑定成功！");
			return null;
		}
		MemberBindingVo memberBindingVo = new MemberBindingVo();
		/**投之家传递参数*/
		memberBindingVo.setRequestFrom("tzj");
		memberBindingVo.setService("createUser");
		memberBindingVo.setUserName(req.getUsername());
		memberBindingVo.setEmail(req.getEmail());
		memberBindingVo.setTelephone(req.getTelephone());
		memberBindingVo.setRealName(req.getIdCard().getName());
		memberBindingVo.setCardNo(req.getIdCard().getNumber());
		memberBindingVo.setTs((String)request.getAttribute("timestamp"));
		memberBindingVo.setSign((String)request.getAttribute("signature"));
		/**内部信息*/
		memberBindingVo.setDr(String.valueOf(1));
		memberBindingVo.setRegTime(memberVo.getAddtime());// 注册时间
		memberBindingVo.setUserId(memberVo.getId());	
		memberBindingVo.setUserNamep(memberVo.getUsername());
		memberBindingVo.setIsSuccess(Integer.valueOf(1));
		memberBindingVo.setLoggingType(loggingType);
		memberBindingVo.setMessage("投之家创建新账户成功");		
		try {
			memberBindingService.insertMemberBindingInfo(memberBindingVo);
		} catch (AppException e) {
			logger.error(e.getStackTrace());
			return null;
		}		
		return memberBindingVo.getId();
	}
	
	/**
	 * 判断是否已绑定
	 * @author WangQianJin
	 * @title @param memberVo
	 * @title @return
	 * @title @throws AppException
	 * @date 2016年3月24日
	 */
	private Boolean isBindingSucess(MemberVo memberVo) throws AppException {
		MemberBindingVo isBindinVoCnd = new MemberBindingVo();
		isBindinVoCnd.setDr(String.valueOf(1));
		isBindinVoCnd.setUserId(memberVo.getId());
		isBindinVoCnd.setUserNamep(memberVo.getUsername());
		isBindinVoCnd.setIsSuccess(Integer.valueOf(1));
		MemberBindingVo isBindinVo = memberBindingService.queryMemberBindingInfo(isBindinVoCnd);
		if (isBindinVo == null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	/**
	 * 封装绑定用户信息
	 * @author WangQianJin
	 * @title @param member
	 * @title @return
	 * @date 2016年3月24日
	 */
	private UserInfo packageUserInfo(MemberBindingVo bindUser){
		UserInfo user=new UserInfo();
		if(bindUser!=null){
			user.setUsername(bindUser.getUserName());
			user.setUsernamep(bindUser.getUserNamep());
			Date regTime=DateUtils.parse(DateUtils.timeStampToDate(bindUser.getRegTime(),DateUtils.YMD_HMS), DateUtils.YMD_HMS);
			user.setRegisterAt(regTime);
			Date bindTime=DateUtils.parse(DateUtils.timeStampToDate(bindUser.getAddTime(),DateUtils.YMD_HMS), DateUtils.YMD_HMS);
			user.setBindAt(bindTime);
			user.setBindType(Integer.parseInt(bindUser.getLoggingType()));
		}
		return user;
	}
	
	/**
	 * 验证手机号是否存在
	 * @author WangQianJin
	 * @title @param mobileNum
	 * @title @return
	 * @date 2016年3月25日
	 */
	public Boolean isMobileNumUsed(String mobileNum) {
		if (null != mobileNum && !"".equals(mobileNum.trim())) {
			// 验证手机号是否被使用
			MobileApproCnd mobileApproCnd = new MobileApproCnd();
			mobileApproCnd.setMobileNum(mobileNum);
			Integer usedCount = mobileApproMapper.queryRepeatMobileApproCount(mobileApproCnd);
			if (null != usedCount && usedCount > 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 验证邮箱是否存在
	 * @author WangQianJin
	 * @title @param mobileNum
	 * @title @return
	 * @date 2016年3月25日
	 */
	public Boolean isEmailUsed(String email) {
		if (null != email && !"".equals(email.trim())) {
			MemberRegisterCnd emailCnd = new MemberRegisterCnd();
			emailCnd.setEmail(email);
			Integer emailCount=null;
			try {
				emailCount = memberRegisterMapper.queryRepeatMemberCount(emailCnd);
			} catch (AppException e) {
				logger.error("验证邮箱异常："+e.getMessage());
			}					
			if (null != emailCount && emailCount > 0) {
				return true;
			}
		}
		return false;
	}

}
