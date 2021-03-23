package com.dxjr.portal.red.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.dxjr.base.entity.Member;
import com.dxjr.common.page.Page;
import com.dxjr.portal.red.entity.RedAccount;
import com.dxjr.portal.red.entity.RedAccountCnd;
import com.dxjr.portal.red.entity.RedRule;

public interface RedAccountMapper {

	@Select("SELECT COUNT(1) from t_red_envelop_account where USER_ID=#{userId} and `STATUS`in(1,2)")
	@ResultType(Integer.class)
	int getUnuseCount(@Param("userId") int userId);

	int add(RedAccount redAccount) throws Exception;

	RedAccount getById(int id);

	// 校验红包ID是否存在 liutao 20151103
	int isExistRed(Member member);

	RedAccount getByIdForUpdate(int id);

	/**
	 * 获取我可使用的红包，每种面值一个，按到期时间倒序
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年11月4日
	 * @param userId
	 * @return List<RedAccount>
	 */
	List<RedAccount> getMyReds(@Param("userId") int userId);

	/**
	 * 获取某个业务使用的红包
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年11月4日
	 * @param usebizId
	 * @param usebizType
	 *            1-认购定期宝，2-认购标的，3-认购直通车
	 * @param status
	 *            3已冻结；4已使用
	 * @return List<RedAccount>
	 */
	List<RedAccount> getBizReds(@Param("usebizId") int usebizId, @Param("usebizType") int usebizType, @Param("status") int status);

	/**
	 * 冻结红包
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年11月4日
	 * @param redId
	 * @return int
	 */
	@Update("update t_red_envelop_account set USEBIZ_ID=#{usebizId},USEBIZ_TYPE=#{usebizType},status=3,freeze_time=now(),LAST_UPDATE_TIME=now() where id=#{redId}")
	@ResultType(Integer.class)
	int freezeRed(@Param("redId") int redId, @Param("usebizId") int usebizId, @Param("usebizType") int usebizType) throws Exception;

	
	/**
	 * 冻结红包
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年11月4日
	 * @param redId
	 * @return int
	 */
	@Update("update t_red_envelop_account set platform=#{platform},USEBIZ_ID=#{usebizId},USEBIZ_TYPE=#{usebizType},status=3,freeze_time=now(),LAST_UPDATE_TIME=now() where id=#{redId}")
	@ResultType(Integer.class)
	int freezeRedpPlatform(@Param("redId") int redId, @Param("usebizId") int usebizId, @Param("usebizType") int usebizType,@Param("platform") int platform) throws Exception;

	/**
	 * 使用红包-冻结中转已使用
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年11月4日
	 * @param redId
	 * @return
	 * @throws Exception
	 *             int
	 */
	@Update("update t_red_envelop_account set status=4,USE_TIME=now(),LAST_UPDATE_TIME=now() where id=#{redId}")
	@ResultType(Integer.class)
	int useRed(@Param("redId") int redId) throws Exception;

	/**
	 * 使用红包-未使用转已使用
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年11月4日
	 * @param redId
	 * @param usebizId
	 * @param usebizType
	 * @return
	 * @throws Exception
	 *             int
	 */
	@Update("update t_red_envelop_account set status=4,USE_TIME=now(),LAST_UPDATE_TIME=now(),USEBIZ_ID=#{usebizId},USEBIZ_TYPE=#{usebizType} where id=#{redId}")
	@ResultType(Integer.class)
	int useRedForUnuse(@Param("redId") int redId, @Param("usebizId") int usebizId, @Param("usebizType") int usebizType) throws Exception;

	List<RedAccount> queryOpenRed(Integer userId) throws Exception;

	List<RedAccount> queryExpiredRed(RedAccountCnd redAccountCnd, Page page) throws Exception;

	int queryExpiredRedCount(RedAccountCnd redAccountCnd) throws Exception;

	RedAccount queryRedDotState(Integer userId) throws Exception;

	void updateRedDotState(Integer userId) throws Exception;

	int updateByPrimaryKeySelective(RedAccount record);

	/**
	 * 查询已使用红包总额
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author liutao
	 * @version 0.1 2015年11月13日
	 */
	BigDecimal queryRedMoneyTotal(RedAccountCnd redAccountCnd);

	List<RedAccount> getMyRuleReds(Map map);

	RedRule queryRedRule(RedAccountCnd redAccountCnd);
	
	@Select("select count(1) from t_red_envelop_account where USER_ID=#{userId} and status=2")
	@ResultType(Integer.class)
	int queryRedCount(@Param("userId") int userId) throws Exception;
	@Select("select IFNULL(SUM(MONEY),0) from t_red_envelop_account where USER_ID=#{userId} and RED_TYPE=#{redType}")
	@ResultType(java.math.BigDecimal.class)
	int queryRedRewardTotal(@Param("userId") int userId,@Param("redType") int redType) throws Exception;
	
	@Select("SELECT MIN(MONEY) from t_red_envelop_account ra WHERE ra.USER_ID=#{userId} and exists(select 1 from t_red_rule ru where ru.RED_TYPE= ra.RED_TYPE and ru.RED_MONEY=ra.MONEY and ru.USE_TYPE =1 and (ru.USE_MONTH=#{fixLimit} OR ru.USE_MONTH=9999) ) AND `STATUS`=2")
	@ResultType(BigDecimal.class)
	BigDecimal queryMinRedMoney(@Param("userId") int userId,@Param("fixLimit") int fixLimit) throws Exception;
}
