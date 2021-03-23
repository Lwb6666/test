package com.dxjr.portal.autoInvestConfig.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dxjr.base.entity.AutoInvestConfig;
import com.dxjr.portal.autoInvestConfig.vo.AutoInvestConfigVo;
import com.dxjr.security.ShiroUser;

/**
 * 
 * <p>
 * Description:自动投标业务处理接口<br />
 * </p>
 * 
 * @title AutoInvestService.java
 * @package com.dxjr.portal.autoInvestConfig.service
 * @author yangshijin
 * @version 0.1 2014年5月19日
 */
public interface AutoInvestService {
	/**
	 * 
	 * <p>
	 * Description:插入自动投标规则<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年5月19日
	 * @param autoInvestConfig
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int insertAutoInvestConfig(AutoInvestConfig autoInvestConfig) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:更新自动投标规则<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年5月19日
	 * @param autoInvestConfig
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int updateAutoInvestConfig(AutoInvestConfig autoInvestConfig) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据ID查询自动投标规则<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年5月19日
	 * @param id
	 * @return
	 * @throws Exception
	 *             AutoInvestConfig
	 */
	public AutoInvestConfig queryById(int id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据user_id查询自动投标规则(其中已启用的规则只取最新的一条)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年5月19日
	 * @param user_id
	 * @return
	 * @throws Exception
	 *             List<AutoInvestConfigVo>
	 */
	public List<AutoInvestConfigVo> queryListByUserId(Integer user_id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某用户的自动投标记录数<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年5月19日
	 * @param user_id
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int queryCountByUserId(int user_id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询某用户所有已启用的自动投标规则并获取排队号(可能存在多条)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年5月20日
	 * @param user_id
	 * @return List<AutoInvestConfig>
	 */
	public List<AutoInvestConfigVo> queryAutoInvestConfigAllByUser_id(int user_id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据Id查询已启用的自动投标规则并获取排队号<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年5月20日
	 * @param id
	 * @return AutoInvestConfigVo
	 */
	public AutoInvestConfigVo queryAutoInvestConfigById(int id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:开始自动投标<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年5月26日
	 * @param request
	 * @param borrow_id
	 * @return
	 * @throws Exception
	 *             Borrow
	 */
	public String saveAutoTenderBorrow(int borrow_id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:新增或修改规则<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月18日
	 * @param userId
	 * @param autoInvestConfig
	 * @param accountVo
	 * @param ip
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String insertOrUpdate(int userId, AutoInvestConfig autoInvestConfig, String ip) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:启用或停用规则<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月18日
	 * @param id
	 * @param status
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String EnableOrDisableRecord(int id, int status, ShiroUser shiroUser) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:删除规则<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月18日
	 * @param id
	 * @throws Exception
	 *             String
	 */
	public String delAutoInvest(int id, ShiroUser shiroUser) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据user_id和自动类型查询记录数量<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年11月25日
	 * @param user_id
	 * @param autoType
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer findCountByUserIdAndAutoType(@Param("user_id") Integer user_id, @Param("autoType") Integer autoType) throws Exception;
	/**
	 * 
	 * <p>
	 * Description:每个用户设置、修改、启用、停用、删除自动投标一个月内最多10次<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2015年11月17日
	 * @param user_id
	 * @return
	 * @throws Exception
	 * Integer
	 */
	public Integer countTotalsByUserId(Integer user_id)throws Exception;

}
