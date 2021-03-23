package com.dxjr.portal.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.FriendCnd;
import com.dxjr.portal.member.vo.MemberApproVo;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;

/**
 * <p>
 * Description:会员数据访问类<br />
 * </p>
 * @title MemberMapper.java
 * @package com.dxjr.portal.member.mapper
 * @author justin.xu
 * @version 0.1 2014年5月7日
 */
public interface MemberMapper {
	/**
	 * <p>
	 * Description:据条件查询会员记录集合<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年5月7日
	 * @param memberCnd
	 * @return
	 * @throws Exception List<MemberVo>
	 */
	public List<MemberVo> queryMemberList(MemberCnd memberCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询会员记录集合数量<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年4月15日
	 * @param MemberCnd
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryMemberCount(MemberCnd memberCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据登录名字查找记录，目前可为：用户名，邮箱名<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年5月7日
	 * @param loginname
	 * @return
	 * @throws Exception MemberVo
	 */
	public MemberVo queryMemberByloginname(String loginname);

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
	 * Description:查询某人的推广用户记录数量<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月5日
	 * @param userId
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryAllFriendCountByUserId(Integer userId) throws Exception;

	/**
	 * 查询某人的推广用户记录获得的奖励
	 * <p>
	 * Description:查询某人的推广用户记录获得的奖励<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月25日
	 * @param userId
	 * @return
	 * @throws Exception Integer
	 */
	public Double queryAllFriendSumMoneyByUserId(Integer userId) throws Exception;

	/**
	 * 查询某人的推广用户记录（分页）
	 * <p>
	 * Description:查询某人的推广用户记录（分页）
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月5日
	 * @param userId
	 * @return
	 * @throws Exception List<MemberVo>
	 */
	public List<MemberVo> queryAllFriendForPageByUserId(FriendCnd friendCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年8月12日
	 * @param memberId
	 * @return MemberVo
	 */
	public MemberVo queryMemberByCnd(MemberCnd memberCnd);

	/**
	 * 修改登录/交易密码
	 * <p>
	 * Description:<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月25日
	 * @param pwdType
	 * @param newPwd
	 * @param userId
	 * @throws Exception void
	 */
	public void updatePwd(@Param(value = "pwdType") String pwdType, @Param(value = "newPwd") String newPwd, @Param(value = "userId") Integer userId) throws Exception;

	/**
	 * <p>
	 * Description:查询是否正式用户<br />
	 * </p>
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年9月16日
	 * @param userId
	 * @return Integer
	 */
	public Integer queryUserIsAutherized(@Param("userId") Integer userId);

	/**
	 * 查询是否实名认证
	 * <p>
	 * Description:<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月26日
	 * @param userId
	 * @return Integer
	 */
	public Integer queryRealNameIspassed(Integer userId);

    public MemberVo queryUserBaseInfo(Integer userId);

	/**
	 * 查询是否邮箱认证
	 * <p>
	 * Description:<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月26日
	 * @param userId
	 * @return Integer
	 */
	public Integer queryEmailIspassed(Integer userId);

	/**
	 * 查询是否手机认证
	 * <p>
	 * Description:<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月26日
	 * @param userId
	 * @return Integer
	 */
	public Integer queryMobileIspassed(Integer userId);

	/**
	 * 查询是否VIP认证
	 * <p>
	 * Description:<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月26日
	 * @param userId
	 * @return Integer
	 */
	public Integer queryVIPIspassed(Integer userId);

	/**
	 * <p>
	 * Description:新注册用户初始化论坛角色<br />
	 * </p>
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年8月26日
	 * @param userId
	 * @return int
	 */
	public int insertBbsUserGroupForUncertified(Integer userId);

	/**
	 * <p>
	 * Description:实名认证通过更新论坛角色<br />
	 * </p>
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年8月26日
	 * @param userId
	 * @return int
	 */
	public int updateBbsUserGroupForRegistered(Integer userId);

	/**
	 * <p>
	 * Description:实名认证通过更新论坛权限<br />
	 * </p>
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年8月26日
	 * @param userId
	 * @return int
	 */
	public int insertBbsUserPermissionForRegistered(Integer userId);

	public int queryPayPasswordIsCorrect(@Param("userId") Integer userId, @Param("payPassword") String payPassword);

	/**
	 * <p>
	 * Description:登录送积分<br />
	 * </p>
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年9月16日
	 * @param memberId
	 * @return int
	 */
	public int insertAccumulatePointsForLogin(@Param("memberId") Integer memberId);

	/**
	 * <p>
	 * Description:更新用户登录信息<br />
	 * </p>
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年9月16日
	 * @param id
	 * @param ip
	 * @return int
	 */
	public int updateMemberForLogin(@Param("id") Integer id, @Param("ip") String ip);

	public int updateMemberForCustody(MemberVo memberVo);

	/**
	 * <p>
	 * Description:更新用户表积分<br />
	 * </p>
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年9月22日
	 * @param id
	 * @return int
	 */
	int updateAccumulatePointsByIdForLogin(@Param("id") int id);

	/**
	 * 只更新用户积分
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年10月27日
	 * @param id
	 * @param point
	 * @return int
	 */
	int updateAccumulatePoints(@Param("id") int id, @Param("point") int point);

	/**
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2015年2月12日
	 * @param userId
	 * @return
	 * @throws Exception MemberVo
	 */
	public MemberVo queryMemberByIdForUpdate(int userId) throws Exception;

	public MemberVo queryMemberById(int userId);

	/**
	 * <p>
	 * Description:获取用户信息中简单的有用的属性 <br />
	 * </p>
	 * m.ID, m.USERNAME, m.STATUS, m.ADDTIME, m.IS_EMPLOYEER, m.IS_FINANCIAL_USER
	 * @author 邹理享
	 * @version 0.1 2015年2月27日
	 * @param userId
	 * @return MemberVo
	 */
	public MemberVo querySimpleInfoById(int userId);

	/**
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author 陈建国
	 * @version 0.1 2015年4月15日
	 * @param queryPasswordInfoById
	 * @return MemberVo
	 */
	public MemberVo queryPasswordInfoById(int userId);

	/**
	 * <p>
	 * Description:根据邮箱查找会员信息<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2015年10月8日
	 * @param email
	 * @return MemberVo
	 */
	public MemberVo queryMemberInfoByEmail(String email);
	
	/**
	 * 获取注册用户
	 * @author WangQianJin
	 * @title @return
	 * @date 2015年12月2日
	 */
	public Integer getRegistUserCount();
	
	
	@Update("update rocky_member set honor=honor+#{accumulatePoints},  ACCUMULATEPOINTS = ACCUMULATEPOINTS + #{accumulatePoints} where ID = #{id}")
	int updateAccumulatePointsById(@Param("id") int id, @Param("accumulatePoints") int accumulatePoints);
	@Select("SELECT IFNULL((SELECT IFNULL(`ORDER`,30) FROM rocky_configuration WHERE TYPE=1397 AND `STATUS`=0 AND CURDATE() >=`NAME` AND CURDATE()<`VALUE`),30) num")
	@ResultType(Integer.class)
	Integer getSignAwardNum();
	@Select("select NAME from rocky_configuration where type=1101 and `VALUE`='PC端推荐' limit 1")
	@ResultType(Integer.class)
	Integer getSource();
	
	@Update("update rocky_member set plnum=plnum+1  where ID = #{id}")
	int updatePlNumberById(@Param("id") int id);
	
	/**
	 * 
	 * <p>
	 * Description:通过ID找到相应的用户名<br />
	 * </p>
	 * @author likang
	 * @version 0.1 2016年6月22日
	 * @param userId
	 * @return
	 * String
	 */
	public String queryMemberNameById(Integer userId);
}
