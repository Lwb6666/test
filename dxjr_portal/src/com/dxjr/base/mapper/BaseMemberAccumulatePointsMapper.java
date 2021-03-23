package com.dxjr.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.dxjr.base.entity.MemberAccumulatePoints;
import com.dxjr.common.page.Page;

/**
 * <p>
 * Description:用户积分记录数据访问类<br />
 * </p>
 * @title MemberAccumulatePointsMapper.java
 * @package com.dxjr.base.mapper
 * @author justin.xu
 * @version 0.1 2014年4月17日
 */
public interface BaseMemberAccumulatePointsMapper {
	/**
	 * <p>
	 * Description:插入记录返回新增的主键ID<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param member
	 * @return
	 * @throws Exception int
	 */
	public int insertEntity(MemberAccumulatePoints memberAccumulatePoints) throws Exception;

	/**
	 * <p>
	 * Description:根据id查询<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param id
	 * @return
	 * @throws Exception MemberAccumulatePoints
	 */
	public MemberAccumulatePoints queryById(Integer id) throws Exception;

	/**
	 * <p>
	 * Description:更新会员<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param memberAccumulatePoints
	 * @return
	 * @throws Exception int
	 */
	public int updateEntity(MemberAccumulatePoints memberAccumulatePoints) throws Exception;

	/**
	 * 获取会员昨日元宝(只取收入)
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年10月13日
	 * @param userId
	 * @return int
	 */
	@Select("select ifnull(SUM(ACCUMULATEPOINTS),0) from rocky_member_accumulate_points where  ACCUMULATEPOINTS>0 and MEMBERID=#{userId} AND GAINDATE >= date_sub(curdate(),interval 1 day) AND GAINDATE < curdate()")
	@ResultType(Integer.class)
	public int getLastdaySycee(@Param("userId") int userId);

	/**
	 * 获取用户当前元宝
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年10月26日
	 * @param userId
	 * @return Integer
	 */
	@Select("select ACCUMULATEPOINTS from rocky_member where id=#{userId}")
	@ResultType(Integer.class)
	public Integer getUserSycee(@Param("userId") int userId);

	public List<MemberAccumulatePoints> mySyceeList(@Param("userId") int userId, Page page);

	@Select("select count(1) from rocky_member_accumulate_points where MEMBERID=#{userId} and ACCUMULATEPOINTS <>0")
	@ResultType(Integer.class)
	public int mySyceeListCount(@Param("userId") int userId);
}
