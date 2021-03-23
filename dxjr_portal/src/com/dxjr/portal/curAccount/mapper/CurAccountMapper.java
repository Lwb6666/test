package com.dxjr.portal.curAccount.mapper;

import java.util.List;
import java.util.Map;

import com.dxjr.portal.curAccount.entity.CurAccount;
import com.dxjr.portal.curAccount.vo.CurAccountCnd;
import com.dxjr.portal.curAccount.vo.CurAccountVo;

/**
 * <p>
 * Description:活期宝账户数据访问类<br />
 * </p>
 * 
 * @title CurAccountMapper.java
 * @package com.dxjr.portal.curAccount.mapper
 * @author 朱泳霖
 * @version 0.1 2015年5月5日
 */
public interface CurAccountMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(CurAccount record);

	int insertSelective(CurAccount record);

	CurAccountVo selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(CurAccount record);

	int updateByPrimaryKey(CurAccount record);

	/**
	 * <p>
	 * Description: 活期生息查询 <br />
	 * </p>
	 * 
	 * @author HuangJun
	 * @version 0.1 2015年5月6日
	 * @param curAccountCnd
	 * @return List<java.util.Map>
	 */
	public List<Map<String ,Object>> queryCurAccountListMap(CurAccountCnd curAccountCnd);
	

	/**
	 * <p>
	 * Description:根据用户ID查询活期宝账户信息<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年5月5日
	 * @param userId
	 * @return
	 * CurAccount
	 */
	public CurAccountVo selectByUserId(Integer userId);

	/**
	 * 
	 * <p>
	 * Description:根据用户ID查询活期宝账户信息，并锁定<br />
	 * </p>
	 * @author YangShiJin
	 * @version 0.1 2015年5月5日
	 * @param userId
	 * @return
	 * CurAccount
	 */
	public CurAccountVo selectByUserIdForUpdate(Integer userId);
	
	/**
	 * <p>
	 * Description:查询加入活期宝的人数<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年5月21日
	 * @return
	 * Integer
	 */
	public Integer queryCurAccountCount();

}