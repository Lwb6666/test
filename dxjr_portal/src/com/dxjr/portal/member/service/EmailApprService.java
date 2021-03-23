package com.dxjr.portal.member.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dxjr.base.entity.EmailAppro;
import com.dxjr.base.entity.Member;
import com.dxjr.portal.member.vo.EmailApproCnd;
import com.dxjr.portal.member.vo.EmailApproVo;
import com.dxjr.portal.member.vo.MemberVo;

/**
 * <p>
 * Description:邮箱认证业务类<br />
 * </p>
 * 
 * @title EmailApprService.java
 * @package com.dxjr.emailappr.service
 * @author justin.xu
 * @version 0.1 2014年4月16日
 */
public interface EmailApprService {
	/**
	 * <p>
	 * Description:初始化邮箱认证，新增,返回 主键<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月16日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             EmailAppro 如果新增失败，会返回null对象
	 */
	public EmailAppro insertEmailAppr(Integer userId,Integer platform) throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param emailApproCnd
	 * @return List<EmailApproVo>
	 */
	public List<EmailApproVo> queryEmailApproList(EmailApproCnd emailApproCnd)
			throws Exception;

	/**
	 * <p>
	 * Description:根据用户ID查询对象<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param memberId
	 * @return RealNameApproVo
	 */
	public EmailApproVo queryEmailApproByUserId(Integer memberId)
			throws Exception;

	/**
	 * <p>
	 * Description:更新邮箱并发送邮件<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String updateAndSendMail(MemberVo member, String email,
			HttpServletRequest request) throws Exception;

	/**
	 * <p>
	 * Description:邮件存在就更新，不存在则初始化。  并发送邮件<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年9月3日
	 * @param memberVo
	 * @param email
	 * @param request
	 * @return
	 * @throws Exception
	 * String
	 */
	String emailVerify(MemberVo memberVo, String email,
			HttpServletRequest request) throws Exception;

	String emailVerifyInSafeCenter(MemberVo memberVo, String email,
			HttpServletRequest request) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:生成邮箱认证记录，并审核通过<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月20日
	 * @param userId
	 * @return
	 * @throws Exception
	 * EmailAppro
	 */
	EmailAppro createApproveEmailRecord(Member member,String ipAddr) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:重新发送认证邮件<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月31日
	 * @param memberVo
	 * @param email
	 * @param request
	 * @return
	 * @throws Exception
	 * String
	 */
	String sendVerifyEmailAgain(MemberVo memberVo,
			HttpServletRequest request) throws Exception;
}
