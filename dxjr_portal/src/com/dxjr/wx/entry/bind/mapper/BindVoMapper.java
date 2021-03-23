package com.dxjr.wx.entry.bind.mapper;

import org.apache.ibatis.annotations.Param;

import com.dxjr.portal.member.vo.MemberVo;
import com.dxjr.wx.entry.bind.vo.BindVo;

public interface BindVoMapper {
	/**
	 * 
	 * <p>
	 * Description:根据微信id查询用户<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月24日
	 * @param openId
	 * @return MemberLoginCnd
	 */
	MemberVo selectByWxId(@Param("wxId") int wxId);

	/**
	 * 
	 * <p>
	 * Description:插入绑定记录<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月24日
	 * @param blindVo
	 * @return int
	 */
	int insert(BindVo blindVo);

	/**
	 * 
	 * <p>
	 * Description:根据用户查询绑定<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月24日
	 * @param blindVo
	 * @return String
	 */
	Integer selectWxIdByMember(BindVo blindVo);

	/**
	 * 
	 * <p>
	 * Description:更新绑定状态<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月24日
	 * @param bindVo
	 * @return int
	 */
	int update(BindVo bindVo);

	/**
	 * 项目启动时更新取消关注用户 执行自动解绑
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月30日
	 * @param updateTime
	 *            void
	 */
	void updateBindunsub(@Param("updateTime") Long updateTime, @Param("ids") String ids);

	/**
	 * 清楚解绑了的用户记录
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月30日 void
	 */
	void clearunBind();

	/**
	 * 根据用户id查询绑定的微信id
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月31日
	 * @param userId
	 * @return Integer
	 */
	Integer queryWxIdByUserId(@Param("userId") Integer userId);

	/**
	 * 查询是否绑定
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2015年3月12日
	 * @param wxId
	 * @return Integer
	 */
	Integer queryHadBind(@Param("wxId") Integer wxId);
}
