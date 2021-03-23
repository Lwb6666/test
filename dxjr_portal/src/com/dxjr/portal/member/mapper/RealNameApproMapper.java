package com.dxjr.portal.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dxjr.portal.member.vo.RealNameApproCnd;
import com.dxjr.portal.member.vo.RealNameApproVo;

/**
 * <p>
 * Description:实名认证数据访问类<br />
 * </p>
 * @title RealNameApproMapper.java
 * @package com.dxjr.portal.member.mapper
 * @author justin.xu
 * @version 0.1 2014年4月24日
 */
public interface RealNameApproMapper {
	/**
	 * <p>
	 * Description:根据条件查询集合<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param realNameApproCnd
	 * @return List<EmailApproVo>
	 */
	public List<RealNameApproVo> queryRealNameApproList(RealNameApproCnd realNameApproCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询对象集合数量<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param realNameApproCnd
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryRealNameApproCount(RealNameApproCnd realNameApproCnd) throws Exception;

	/**
	 * <p>
	 * Description:验证重复数据<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param memberRegisterCnd
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryRepeatRealNameApproCount(RealNameApproCnd realNameApproCnd) throws Exception;

	public int insertRealnameAuthLog(@Param("userId") Integer userId, @Param("realName") String realName, @Param("idCardNo") String idCardNo, @Param("isPass") Integer isPass);

	public int countRealnameAuthLogByUserId(@Param("userId") Integer userId);

	public RealNameApproVo getByUserId(Integer userId);
}
