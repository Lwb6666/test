package com.dxjr.portal.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.dxjr.portal.member.vo.MobileApproCnd;
import com.dxjr.portal.member.vo.MobileApproVo;

/**
 * <p>
 * Description:手机认证数据访问类<br />
 * </p>
 * 
 * @title MobileApproMapper.java
 * @package com.dxjr.portal.member.mapper
 * @author justin.xu
 * @version 0.1 2014年4月25日
 */
public interface MobileApproMapper {
	/**
	 * <p>
	 * Description:根据条件查询对象集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param mobileApproCnd
	 * @return
	 * @throws Exception
	 *             List<VIPApproVo>
	 */
	public List<MobileApproVo> queryMobileApproList(MobileApproCnd mobileApproCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询对象集合数量<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param MobileApproCnd
	 *            mobileApproCnd
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer queryMobileApproCount(MobileApproCnd mobileApproCnd) throws Exception;

	/**
	 * <p>
	 * Description:验证手机认证数据是否重复<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月30日
	 * @param mobileApproCnd
	 * @return Integer
	 */
	public Integer queryRepeatMobileApproCount(MobileApproCnd mobileApproCnd);

	public List<String> querySendSmsMobile();

	public MobileApproVo getByUserId(int userId);

	/**
	 * 根据用户名获取手机号
	 * 
	 * @author WangQianJin
	 * @title @param username
	 * @title @return
	 * @date 2015年12月7日
	 */
	public String getMobileByUsername(String username);

	@Select("select MOBILENUM from rocky_mobile_appro where USER_ID=#{userId} and PASSED=1")
	@ResultType(String.class)
	public String getMobile(@Param("userId") int userId);
}
