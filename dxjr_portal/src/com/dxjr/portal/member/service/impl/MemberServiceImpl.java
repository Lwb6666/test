package com.dxjr.portal.member.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.Member;
import com.dxjr.base.entity.OnlineCounter;
import com.dxjr.base.entity.Stock;
import com.dxjr.base.mapper.BaseMemberMapper;
import com.dxjr.base.memberaccumulatepoints.service.MemberAccumulatePointsService;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.account.mapper.AccountLogMapper;
import com.dxjr.portal.account.mapper.AccountMapper;
import com.dxjr.portal.account.mapper.AccountNetValueMapper;
import com.dxjr.portal.account.mapper.ModifyMemberMapper;
import com.dxjr.portal.account.service.AccountDayLogService;
import com.dxjr.portal.account.util.UserLevelRatio;
import com.dxjr.portal.account.util.UserNetValue;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.account.vo.ModifyMember;
import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.mapper.MemberRegisterMapper;
import com.dxjr.portal.member.mapper.OnlineCounterMapper;
import com.dxjr.portal.member.service.ApproService;
import com.dxjr.portal.member.service.MemberRegisterService;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.service.OnlineCounterService;
import com.dxjr.portal.member.service.VIPApproService;
import com.dxjr.portal.member.util.BaiDuIp;
import com.dxjr.portal.member.util.BaiDuIp.IpAddr;
import com.dxjr.portal.member.vo.LoginCnd;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberRegisterCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.statics.BusinessConstants;
import com.dxjr.portal.stock.mapper.StockMapper;
import com.dxjr.security.ShiroUser;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.MoneyUtil;

/**
 * <p>
 * Description:会员接口的实现类<br />
 * </p>
 * @title MemberServiceImpl.java
 * @package com.dxjr.portal.member.service.impl
 * @author justin.xu
 * @version 0.1 2014年5月7日
 */
@Service
public class MemberServiceImpl implements MemberService {
	public Logger logger = Logger.getLogger(MemberServiceImpl.class);

	@Autowired
	private MemberRegisterMapper memberRegisterMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private MemberAccumulatePointsService memberAccumulatePointsService;
	@Autowired
	private OnlineCounterService onlineCounterService;
	@Autowired
	private BaseMemberMapper baseMemberMapper;
	@Autowired
	private AccountDayLogService accountDayLogService;
	@Autowired
	private VIPApproService vipApproService;
	@Autowired
	private ApproService approService;
	@Autowired
	private OnlineCounterMapper onlineCounterMapper;

	@Autowired
	private AccountLogMapper accountLogMapper;

	@Autowired
	private StockMapper stockMapper;
	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private AccountNetValueMapper netValueMapper;
	@Autowired
	private MemberRegisterService memberRegisterService;
	@Autowired
	private ModifyMemberMapper modifyMemberMapper;

	@Override
	public String saveLogin(LoginCnd loginCnd) throws Exception {
		// 判断今天登录次数,决定是否增加登录积分
		// int i =
		// memberMapper.insertAccumulatePointsForLogin(loginCnd.getUserId());
		// if (i > 0) {
		// memberMapper.updateAccumulatePointsByIdForLogin(loginCnd.getUserId());
		// }

		long t1 = System.currentTimeMillis();
		if (!ArrayUtils.contains(Constants.IDC_IPS, loginCnd.getIp())) {
			// 记录登录信息
			OnlineCounter onlineCounter = new OnlineCounter();
			IpAddr ipAddr = BaiDuIp.queryAddrByIp(loginCnd.getIp()); // 调用百度api获得位置
			onlineCounter.setUserId(loginCnd.getUserId());
			onlineCounter.setUsername(loginCnd.getUserName());
			onlineCounter.setSessionid(loginCnd.getSessionId());
			onlineCounter.setAddip(loginCnd.getIp());
			onlineCounter.setSystem(Constants.ONLINE_COUNTER_SYSTEM_PORTAL);
			onlineCounter.setArea(ipAddr.getArea());
			onlineCounter.setProvince(ipAddr.getProvince());
			onlineCounter.setCity(ipAddr.getCity());
			onlineCounter.setPlatform(loginCnd.getPlatform());
			onlineCounterMapper.insert(onlineCounter);
		}
		long t2 = System.currentTimeMillis();
		logger.info(loginCnd.getUserName() + " ----- login ----- save login ----- insertOnlineCounter ----- cost : " + (t2 - t1));

		// 更新登录次数、最后登录时间、最后登录ip
		memberMapper.updateMemberForLogin(loginCnd.getUserId(), loginCnd.getIp());

		long t3 = System.currentTimeMillis();
		logger.info(loginCnd.getUserName() + " ----- login ----- save login ----- updateMemberForLogin ----- cost : " + (t3 - t2));

		/* 0：正式身份 -1：游客身份 */
		if (memberMapper.queryUserIsAutherized(loginCnd.getUserId()) == 0) {
			return BusinessConstants.VISITOR_UNAUTHERIZED; // 当前用户未认证
		}

		long t4 = System.currentTimeMillis();
		logger.info(loginCnd.getUserName() + " ----- login ----- save login ----- queryUserIsAutherized ----- cost : " + (t4 - t3));
		return "success";
	}

	@Override
	public MemberApproVo queryMemberApproByUserId(Integer memberId) throws Exception {
		return memberMapper.queryMemberApproByUserId(memberId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String queryUserLevelByMemberId(Integer memberId) throws Exception {
		UserLevelRatio userLevelRatio = new UserLevelRatio();
		userLevelRatio.setUserid(memberId);
		netValueMapper.callGetUserLevelRatio(userLevelRatio);
		return userLevelRatio.getO_userLevel();
	}

	@Transactional(rollbackFor = Exception.class)
	public UserLevelRatio queryUserLevel(Integer memberId) throws Exception {
		UserLevelRatio userLevelRatio = new UserLevelRatio();
		userLevelRatio.setUserid(memberId);
		netValueMapper.callGetUserLevelRatio(userLevelRatio);
		return userLevelRatio;
	}

	@Override
	public MemberVo queryMemberByCnd(MemberCnd memberCnd) {
		return memberMapper.queryMemberByCnd(memberCnd);
	}

	@Override
	public String updateEntity(Member member) throws Exception {
		Integer affectedRow = baseMemberMapper.updateEntity(member);
		if (affectedRow != null && affectedRow == 1) {
			return "sucess";
		}
		return "未知异常";
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String activateRegistEmail(HttpServletRequest request, Integer user_id, String uuid, String email) {

		MemberVo memberVo = null;
		String info = "您好，恭喜您注册成功！";
		String userName = null;
		String result = "success";

		MemberCnd memberCnd = new MemberCnd();
		memberCnd.setId(user_id);
		memberVo = this.queryMemberByCnd(memberCnd);
		// 已激活，成为了正式用户
		if (null != memberVo && BusinessConstants.VISITOR_OFFICIAL == memberVo.getType()) {
			return "您已经是正式用户！";
		}
		if (null == memberVo || "".equals(memberVo.getUsername().trim())) {
			return "用户不存在，激活失败";
		}

		if (null != memberVo && !"".equals(memberVo.getUsername().trim())) {
			userName = memberVo.getUsername().trim() + info;
		}
		try {
			// 校验邮箱是否重复
			MemberRegisterCnd memberRegisterCnd = new MemberRegisterCnd();
			memberRegisterCnd.setId(memberVo.getId());
			memberRegisterCnd.setEmail(email.trim());
			String isEmailRepeat = memberRegisterService.queryMemberRepeat(memberRegisterCnd, null);
			if (!"success".equals(isEmailRepeat)) {
				result = "当前邮箱已经使用，请重新输入。";
				return result;
			}
			String updateEmailresult = approService.updateEmailAppr(user_id, uuid, email, request);

			if (updateEmailresult.equals("success")) {
				Member member = new Member();
				// 将当前用户身份设置为正式用户
				member.setType(BusinessConstants.VISITOR_OFFICIAL);
				member.setId(user_id);
				this.updateEntity(member);
			}
		} catch (Exception e) {
			result = "网络异常，请稍候重试！";
		}
		if ("success".equals(result)) {
			return userName;
		} else {
			return result;
		}

	}

	/**
	 * 当前登录用户股东加权最低总额
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年9月23日
	 * @param shiroUser
	 * @return
	 * @throws Exception String
	 */
	public String haveStockMoney(ShiroUser shiroUser) throws Exception {
		List<Stock> list = stockMapper.getByProperty("user_id", shiroUser.getUserId() + "");
		if (list != null && list.size() > 0) {
			BigDecimal money = accountLogMapper.haveStockMoney(shiroUser.getUserId());
			return MoneyUtil.fmtMoneyByDot(money);
		}
		return null;
	}

	@Override
	public ModelAndView addUserNetValue(int memberId, ModelAndView mav) throws Exception {
		UserNetValue netValue = new UserNetValue();
		netValue.setUserid(memberId);
		netValueMapper.callGetUserNetMoneyLimit(netValue);
		mav.addObject("netValue", netValue);

		AccountVo account = accountMapper.queryByUserId(memberId);
		mav.addObject("total", account.getTotal());

		if (netValue.getNetWaitToPayCount() <= 0) {
			mav.addObject("divWidth", 370);
		} else {
			mav.addObject("divWidth", 470);
		}

		return mav;
	}

	@Override
	public MemberVo queryPasswordInfoById(int userId) {
		// TODO Auto-generated method stub
		return memberMapper.queryPasswordInfoById(userId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String modifyUsername(ShiroUser user,String username,String ip) {
		String result=BusinessConstants.SUCCESS;
		MemberCnd memberCnd=new MemberCnd();
		Member member=new Member();
		memberCnd.setUsername(user.getUserName());
		MemberVo memberVo=queryMemberByCnd(memberCnd);
		
		try {
			if(memberVo==null){
				return result="用户名不存在";
			}
			ModifyMember modifyMember=modifyMemberMapper.findMemberById(memberVo.getId());
			if(modifyMember!=null){
				return result="用户名已经修改过一次";
			}
			modifyMember=new ModifyMember();
			modifyMember.setAddtime(DateUtils.getTime());
			modifyMember.setUserid(memberVo.getId());
			modifyMember.setNweusername(username);
			modifyMember.setOldusername(user.getUserName());
			modifyMember.setAddip(ip);
			memberVo.setUsername(username);
			BeanUtils.copyProperties(memberVo,member);
		    updateEntity(member);
		    modifyMemberMapper.updatePassGesturePwd(modifyMember);
		   //修改完用户名后，同步更新投之家 人人利 生菜网  网贷天眼 投天下绑定的平台用户名
		    modifyMemberMapper.updateRrl(modifyMember);
		    modifyMemberMapper.updateScw(modifyMember);
		    modifyMemberMapper.updateTzj(modifyMember);
		    modifyMemberMapper.updateWdty(modifyMember);
		    modifyMemberMapper.updateTtx(modifyMember);
		    modifyMemberMapper.insert(modifyMember);
		} catch (Exception e) {
			logger.error("修改用户名出错", e);
		}
		return result;
	}
	
	/**
	 * 获取注册用户
	 * @author WangQianJin
	 * @title @return
	 * @date 2015年12月2日
	 */
	public Integer getRegistUserCount(){
		return memberMapper.getRegistUserCount();
	}

	/**
	 * 通过ID找到相应的用户名
	 */
	public String queryMemberNameById(Integer userId) {
		return memberMapper.queryMemberNameById(userId);
	}
}
