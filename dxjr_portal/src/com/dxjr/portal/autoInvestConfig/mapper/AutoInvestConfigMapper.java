package com.dxjr.portal.autoInvestConfig.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dxjr.portal.autoInvestConfig.vo.AutoInvestConfigCnd;
import com.dxjr.portal.autoInvestConfig.vo.AutoInvestConfigVo;

/**
 * 
 * <p>
 * Description:自动投标规则类数据访问类<br />
 * </p>
 * 
 * @title BaseAutoInvestConfigMapper.java
 * @package com.dxjr.base.mapper
 * @author yangshijin
 * @version 0.1 2014年5月19日
 */
public interface AutoInvestConfigMapper {

	/**
	 * 
	 * <p>
	 * Description:根据user_id查询自动投标规则<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年5月19日
	 * @param user_id
	 * @return
	 * @throws Exception List<AutoInvestConfigVo>
	 */
	public List<AutoInvestConfigVo> queryListByUserId(Integer user_id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据ID查询自投标规则（包含排队号）<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年5月20日
	 * @param id
	 * @return
	 * @throws Exception AutoInvestConfigVo
	 */
	public AutoInvestConfigVo queryById(Integer id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据user_id查询用户生效的规则数量.<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年5月20日
	 * @param user_id
	 * @return
	 * @throws Exception int
	 */
	public int queryCountByUserId(Integer user_id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据USER_ID查询所有已启用的自动投标规则并获取排队号(可能存在多条)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年5月20日
	 * @param user_id
	 * @return
	 * @throws Exception List<AutoInvestConfigVo>
	 */
	public List<AutoInvestConfigVo> queryAllByUserId(Integer user_id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:匹配抵押标、利率范围、还款方式、 借款期限（按月或无）等条件符合的投标规则<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年5月26日
	 * @param autoInvestConfigCnd
	 * @return
	 * @throws Exception List<AutoInvestConfigVo>
	 */
	public List<AutoInvestConfigVo> queryForDYByMonth(AutoInvestConfigCnd autoInvestConfigCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:匹配抵押标、利率范围、还款方式、 借款期限（按天或无）等条件符合的投标规则<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年5月26日
	 * @param autoInvestConfigCnd
	 * @return
	 * @throws Exception List<AutoInvestConfigVo>
	 */
	public List<AutoInvestConfigVo> queryForDYByDay(AutoInvestConfigCnd autoInvestConfigCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:匹配净值标、利率范围、还款方式、 借款期限（按月或无）等条件符合的投标规则<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年5月26日
	 * @param autoInvestConfigCnd
	 * @return
	 * @throws Exception List<AutoInvestConfigVo>
	 */
	public List<AutoInvestConfigVo> queryForJZByMonth(AutoInvestConfigCnd autoInvestConfigCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:匹配净值标、利率范围、还款方式、 借款期限（按天或无）等条件符合的投标规则<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年5月26日
	 * @param autoInvestConfigCnd
	 * @return
	 * @throws Exception List<AutoInvestConfigVo>
	 */
	public List<AutoInvestConfigVo> queryForJZByDay(AutoInvestConfigCnd autoInvestConfigCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:匹配推荐标、利率范围、还款方式、 借款期限（按月或无）等条件符合的投标规则<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年5月26日
	 * @param autoInvestConfigCnd
	 * @return
	 * @throws Exception List<AutoInvestConfigVo>
	 */
	public List<AutoInvestConfigVo> queryForTJByMonth(AutoInvestConfigCnd autoInvestConfigCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:匹配推荐标、利率范围、还款方式、 借款期限（按天或无）等条件符合的投标规则<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年5月26日
	 * @param autoInvestConfigCnd
	 * @return
	 * @throws Exception List<AutoInvestConfigVo>
	 */
	public List<AutoInvestConfigVo> queryForTJByDay(AutoInvestConfigCnd autoInvestConfigCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询资产总额小于2000元的已启用的自动投标规则<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月5日
	 * @return
	 * @throws Exception List<AutoInvestConfigVo>
	 */
	public List<AutoInvestConfigVo> queryByAccountTotal() throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取36天内未进行自动投标的已启用规则<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年6月6日
	 * @param now
	 * @return
	 * @throws Exception List<AutoInvestConfigVo>
	 */
	public List<AutoInvestConfigVo> queryListForOverdueByNow(String now) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:调用自动投标存储过程<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param map void
	 */
	public void autoTender(Map map);

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
	 * @throws Exception Integer
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
	public Integer countTotalsByUserId(Integer userId)throws Exception;
}
