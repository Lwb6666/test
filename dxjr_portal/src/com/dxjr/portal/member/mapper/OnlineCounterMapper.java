package com.dxjr.portal.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dxjr.base.entity.OnlineCounter;
import com.dxjr.common.page.Page;
import com.dxjr.portal.member.vo.OnlineCounterCnd;
import com.dxjr.portal.member.vo.OnlineCounterVo;

/**
 * <p>
 * Description:在线人数记录数据访问类<br />
 * </p>
 * 
 * @title OnlineCounterMapper.java
 * @package com.dxjr.base.mapper
 * @author justin.xu
 * @version 0.1 2014年4月19日
 */
public interface OnlineCounterMapper {
	/**
	 * <p>
	 * Description:根据条件查询集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月19日
	 * @param onlineCounterCnd
	 * @return
	 * @throws Exception List<OnlineCounterVo>
	 */
	public List<OnlineCounterVo> queryListByCnd(OnlineCounterCnd onlineCounterCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据用户id查询上一次登录信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年5月8日
	 * @param userid
	 * @return
	 * @throws Exception OnlineCounter
	 */
	public OnlineCounterVo queryLastOnlineCounterByUserId(@Param("userId") Integer userId) throws Exception;

	public int insert(OnlineCounter onlineCounter);

	public List<OnlineCounterVo> queryListByUserId(@Param("userId") Integer userId, Page page);
}
