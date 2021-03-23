package com.dxjr.portal.member.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.dxjr.base.entity.Member;
import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.portal.member.vo.MobileApproVo;

/**
 * <p>
 * Description:手机验证业务类<br />
 * </p>
 * 
 * @title MobileApproService.java
 * @package com.dxjr.portal.member.service
 * @author justin.xu
 * @version 0.1 2014年4月25日
 */
public interface MobileApproService {

	/**
	 * <p>
	 * Description:根据用户ID查询对象<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param memberId
	 * @return MobileApproVo
	 */
	public MobileApproVo queryMobileApproByUserId(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:发送手机认证短信<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月29日
	 * @param mobile
	 * @param modelName 功能名称
	 * @return
	 * @throws Exception String
	 */
	public String sendMobileApprValidate(String mobile, HttpServletRequest request, String userName, String modelName) throws Exception;

	/**
	 * <p>
	 * Description:进行手机认证<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月30日
	 * @param member
	 * @param mobile
	 * @param activeCode
	 * @param request
	 * @return
	 * @throws Exception String
	 */
	public String saveMobileAppro(MemberVo memberVo, String mobile, String activeCode, HttpServletRequest request, String modelName) throws Exception;

	/**
	 * <p>
	 * Description:手机与实名认证通过，发放奖励<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月30日
	 * @param userId
	 * @param ip
	 * @throws Exception void
	 */
	public void saveRealNameMobileAward(int userId, String ip) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:指定短信模板发送手机认证短信<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年9月2日
	 * @param mobile
	 * @param request
	 * @param userName
	 * @param modelName
	 * @param smsTemplateType
	 * @return
	 * @throws Exception String
	 */
	String sendMobileApprValidate(String mobile, HttpServletRequest request, String userName, String modelName, Integer smsTemplateType) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:指定短信模板进行手机短信认证<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年9月2日
	 * @param memberVo
	 * @param mobile
	 * @param activeCode
	 * @param request
	 * @param modelName
	 * @param smsTemplateType
	 * @return
	 * @throws Exception String
	 */
	String saveMobileAppro(MemberVo memberVo, String mobile, String activeCode, HttpServletRequest request, String modelName, Integer smsTemplateType) throws Exception;

	/**
	 * <p>
	 * Description:发送手机短信验证码<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年9月3日
	 * @param request
	 * @param member
	 * @return String
	 */
	String sendMobailMessageActiveCode(HttpServletRequest request, Member member) throws Exception;

	/**
	 * <p>
	 * Description:校验手机短信认证码<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年9月3日
	 * @param request
	 * @param member
	 * @param mobileParam
	 * @param activeCodeParam
	 * @return String
	 */
	String verificationMobailActiveCode(HttpServletRequest request, Member member, String mobileParam, String activeCodeParam);

	/**
	 * 
	 * <p>
	 * Description:新增银行卡是进行手机验证码校验<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年9月16日
	 * @param memberVo
	 * @param mobile
	 * @param activeCode
	 * @param request
	 * @param modelName
	 * @param smsTemplateType
	 * @return
	 * @throws Exception String
	 */
	String verifyMobileCodeBeforeAddBankCard(MemberVo memberVo, String mobile, String activeCode, HttpServletRequest request, String modelName, Integer smsTemplateType) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:添加成功后发送短信<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年9月18日
	 * @param memberVo
	 * @param mobile
	 * @param activeCode
	 * @param request
	 * @param modelName
	 * @param smsTemplateType
	 * @return
	 * @throws Exception String
	 */
	String sendMobileCodeAfterAddBankCard(MemberVo memberVo, String mobile, HttpServletRequest request, String modelName, Integer smsTemplateType) throws Exception;

	// 发送验证码的时候插入数据
	void packageMobileApproCode(MemberVo memberVo, String mobile, String activeCode, HttpServletRequest request) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:自动生成手机认证待审核记录<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @param memberVo
	 * @param mobile
	 * @param activeCode
	 * @param request
	 * @throws Exception
	 * void
	 */
	void AutoGenerateMobileAppro(MemberVo memberVo, String mobile,
			String activeCode, String ip) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:自动审核手机认证信息<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @param memberVo
	 * @param mobile
	 * @param activeCode
	 * @param request
	 * @param modelName
	 * @return
	 * @throws Exception
	 * String
	 */
	public String AutoApproGenerateMobile(MemberVo memberVo, String mobile,
			String activeCode, String ip)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:将自动生成的用户名和密码通过手机发送给用户<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月16日
	 * @param memberVo
	 * @param mobile
	 * @param request
	 * @throws Exception
	 * void
	 */
	void sendAutoApproGenerateMemberInfo(MemberVo memberVo, String mobile,
			String ip) throws Exception;

	/**
	 * <p>
	 * Description:查询测试手机号码<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年1月4日
	 * @return List<String>
	 */
	public List<String> querySendSmsMobile();

	/**
	 * 
	 * <p>
	 * Description:判断手机号是否已使用<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年12月31日
	 * @param mobileNum
	 * @return
	 * Integer
	 */
	public Boolean isMobileNumUsed(String mobileNum);
	public String saveMobile(String mobile, String activeCode, HttpServletRequest request, String modelName, HttpSession session) throws Exception;
}
