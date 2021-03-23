package com.dxjr.portal.member.mapper;

import org.apache.ibatis.annotations.Param;

import com.dxjr.portal.member.vo.VipLevelVo;

/**
 * 
 * <p>
 * Description:终身顶级会员类数据访问类<br />
 * </p>
 * 
 * @title VipLevelMapper.java
 * @package com.dxjr.base.mapper
 * @author yangshijin
 * @version 0.1 2015年1月13日
 */
public interface VipLevelMapper {

	/**
	 * 
	 * <p>
	 * Description:根据id查询对象<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年1月13日
	 * @param id
	 * @return
	 * @throws Exception VipLevelVo
	 */
	public VipLevelVo queryById(Integer id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据id查询对象，并锁定<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年1月13日
	 * @param id
	 * @return
	 * @throws Exception VipLevelVo
	 */
	public VipLevelVo queryByIdForUpdate(Integer id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据用户id及type查询VIP<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年1月13日
	 * @param userId
	 * @return
	 * @throws Exception VipLevelVo
	 */
	public VipLevelVo queryByUserIdAndType(@Param("userId") Integer userId, @Param("type") Integer type) throws Exception;

}
