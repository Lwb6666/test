package com.dxjr.portal.member.mapper;

import java.util.List;

import com.dxjr.portal.member.vo.MemberRegisterCnd;
import com.dxjr.portal.member.vo.MemberRegisterVo;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:注册会员数据访问类<br />
 * </p>
 * 
 * @title MemberRegisterMapper.java
 * @package com.dxjr.member.mapper
 * @author justin.xu
 * @version 0.1 2014年4月15日
 */
public interface MemberRegisterMapper {
	/**
	 * <p>
	 * Description:根据条件查询会员记录集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param memberRegisterCnd
	 * @return
	 * @throws Exception
	 *             List<MemberRegisterVo>
	 */
	public List<MemberRegisterVo> queryMemberList(
			MemberRegisterCnd memberRegisterCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询会员记录集合数量<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param memberRegisterCnd
	 * @return
	 * @throws Exception
	 *             List<MemberVo>
	 */
	public Integer queryMemberCount(MemberRegisterCnd memberRegisterCnd)
			throws AppException;

	/**
	 * <p>
	 * Description:验证重复数据<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param memberRegisterCnd
	 * @return
	 * @throws Exception
	 *             List<MemberVo>
	 */
	public Integer queryRepeatMemberCount(MemberRegisterCnd memberRegisterCnd)
			throws AppException;
	
	public Integer 	inviterNameCount(MemberRegisterCnd memberRegisterCnd) throws AppException;
	
	public List<MemberRegisterVo> queryinviterList(MemberRegisterCnd memberRegisterCnd) throws Exception;
	
	public int existsContainSensitiveForUserName(String username);
}
