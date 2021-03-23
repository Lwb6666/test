package com.dxjr.portal.member.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.Member;
import com.dxjr.portal.account.util.UserLevelRatio;
import com.dxjr.portal.member.vo.LoginCnd;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.security.ShiroUser;

/**
 * <p>
 * Description:会员业务类<br />
 * </p>
 * @title MemberService.java
 * @package com.dxjr.portal.member.service
 * @author justin.xu
 * @version 0.1 2014年5月7日
 */
public interface MemberService {

	/**
	 * <p>
	 * Description:通过ID更新Member<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年8月30日
	 * @param member
	 * @return
	 * @throws Exception String
	 */
	public String updateEntity(Member member) throws Exception;

	/**
	 * <p>
	 * Description:会员登录方法<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年5月7日
	 * @param memberLoginCnd
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception String
	 */
	public String saveLogin(LoginCnd loginCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据用户id查询用户的认证信息<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年5月13日
	 * @param memberId
	 * @return
	 * @throws Exception MemberApproVo
	 */
	public MemberApproVo queryMemberApproByUserId(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:根据用户Id取得会员等级<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年5月13日
	 * @param memberId
	 * @return String
	 */
	public String queryUserLevelByMemberId(Integer memberId) throws Exception;

	public UserLevelRatio queryUserLevel(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:根据条件取member对象<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年8月12日
	 * @param memberId
	 * @return
	 * @throws Exception MemberVo
	 */
	public MemberVo queryMemberByCnd(MemberCnd memberCnd);

	/**
	 * <p>
	 * Description:激活注册邮箱功能<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年9月2日
	 * @param request
	 * @param user_id
	 * @param uuid
	 * @param email
	 * @return String
	 */
	String activateRegistEmail(HttpServletRequest request, Integer user_id, String uuid, String email);

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
	public String haveStockMoney(ShiroUser shiroUser) throws Exception;

	public ModelAndView addUserNetValue(int memberId, ModelAndView mav) throws Exception;

	public MemberVo queryPasswordInfoById(int userId);


	/**
	 * <p>
	 * Description:用户修改用户名<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2015年9月24日
	 * @param userName 新用户名
	 * @param oldname 老用户名
	 * @return String
	 */
 
	public String modifyUsername(ShiroUser loginuser,String userName,String ip); 
	
	/**
	 * 获取注册用户
	 * @author WangQianJin
	 * @title @return
	 * @date 2015年12月2日
	 */
	public Integer getRegistUserCount();

	/**
	 * 
	 * <p>
	 * Description:通过ID找到对应的用户名<br />
	 * </p>
	 * @author likang
	 * @version 0.1 2016年6月22日
	 * @param userId
	 * @return
	 * String
	 */
	public String queryMemberNameById(Integer userId);
 
}
