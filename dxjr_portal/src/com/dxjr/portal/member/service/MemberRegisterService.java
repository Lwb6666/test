package com.dxjr.portal.member.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.dxjr.base.entity.Member;
import com.dxjr.portal.member.vo.MemberRegisterCnd;
import com.dxjr.portal.tzjinterface.entity.CreateUserReq;

/**
 * <p>
 * Description:注册会员业务类<br />
 * </p>
 * 
 * @title MemberService.java
 * @package com.dxjr.member.service
 * @author justin.xu
 * @version 0.1 2014年4月15日
 */
public interface MemberRegisterService {
	/**
	 * <p>
	 * Description:会员新增、注册<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param member
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String insertMember(Member member, String inviterName,
			HttpServletRequest request, HttpSession session) throws Exception;

	/**
	 * <p>
	 * Description:验证注册的重复数据<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月21日
	 * @param memberRegisterCnd
	 * @return String
	 */
	public String queryMemberRepeat(MemberRegisterCnd memberRegisterCnd, Integer userId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:注册会员，成功后不进行授权<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年9月2日
	 * @param member
	 * @param inviterName
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 * String
	 */
	String insertMemberWithoutEmail(Member member, String inviterName,
			HttpServletRequest request, HttpSession session) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:发送用户注册时进行邮箱验证激活链接用例<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年9月3日
	 * @param request
	 * @param destinationEmail
	 * @param again
	 * @param member
	 * @return
	 * String
	 */
	String sendEmailLinkActivateMember(HttpServletRequest request,
			String destinationEmail, Boolean again, Member member) throws Exception;

	/**
	 * <p>
	 * 验证名用户名是否包含敏感词<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年9月23日
	 * @param username
	 * @return boolean
	 */
	/* */public boolean existsContainSensitiveForUserName(String username);

	/**
	 * 
	 * <p>
	 * Description:验证邮箱和手机号是否已经被使用了。<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月15日
	 * @param email
	 * @param mobaile
	 * @param userName
	 * @return
	 * @throws Exception
	 * String
	 */
	String queryMemberRepeat(String email, String mobaile,String userName) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:投之家导流接口登入时自动生成用户信息<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @param member
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 * String
	 */
	String insertAutoGenerateMember(Member member,Map<String, String> paramterMap, HttpServletRequest request,
			HttpSession session) throws Exception;
	/**
	 * 网贷天眼自动注册用户
	 * @author WangQianJin
	 * @title @param member
	 * @title @param telephone
	 * @title @param request
	 * @title @param session
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年12月16日
	 */
	String insertAutoGenerateMemberForWdty(Member member,String telephone, HttpServletRequest request,HttpSession session) throws Exception;
	String insertAutoGenerateMemberForGT(Member member,Map<String, String> paramterMap, HttpServletRequest request,
			HttpSession session) throws Exception;
	
	public String queryInviterName(MemberRegisterCnd inviterName ) throws Exception ;
	
	public  String  confrim(Member member, String inviterName,
			HttpServletRequest request, HttpSession session) throws Exception;
	
	/**
	 * 投之家自动生成用户信息
	 * @author WangQianJin
	 * @title @param member
	 * @title @param req
	 * @title @return
	 * @title @throws Exception
	 * @date 2016年3月24日
	 */
	String tzjInsertAutoGenerateMember(Member member,CreateUserReq req,HttpServletRequest request, HttpSession session) throws Exception;
}
