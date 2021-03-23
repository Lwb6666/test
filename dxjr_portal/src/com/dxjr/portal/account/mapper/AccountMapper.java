package com.dxjr.portal.account.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Update;

import com.dxjr.portal.account.vo.AccountCnd;
import com.dxjr.portal.account.vo.AccountVo;
import com.dxjr.portal.account.vo.LoginRemindLogVo;
import com.dxjr.portal.account.vo.YearCollect;

/**
 * <p>
 * Description:帐号数据访问类<br />
 * </p>
 * @title AccountMapper.java
 * @package com.dxjr.portal.account.mapper
 * @author justin.xu
 * @version 0.1 2014年4月25日
 */
public interface AccountMapper {
	/**
	 * <p>
	 * Description:根据条件查询对象集合<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param accountCnd
	 * @return
	 * @throws Exception List<AccountVo>
	 */
	public List<AccountVo> queryAccountList(AccountCnd accountCnd) throws Exception;
	public BigDecimal queryCollectionCapital(AccountCnd accountCnd) throws Exception;
	
	public AccountVo findAccountByUserIdForUpdate(Integer userId);

	/**
	 * <p>
	 * Description:根据条件查询对象集合数量<br />
	 * </p>
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param CapitalAccountCnd accountCnd
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryAccountCount(AccountCnd accountCnd) throws Exception;

	public AccountVo queryByUserId(Integer userId) throws Exception;

	int insert(LoginRemindLogVo lv);

	/**
	 * 更新账户信息-使用&退回红包
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2015年11月4日
	 * @param redMoney
	 * @param userId
	 * @return int
	 */
	@Update("update rocky_account set total = total + #{redMoney},use_money = use_money + #{redMoney},NO_DRAW_MONEY=NO_DRAW_MONEY + #{redMoney} where user_id = #{userId}")
	@ResultType(Integer.class)
	int updateAccountForRed(@Param("redMoney") BigDecimal redMoney, @Param("userId") int userId);
	public YearCollect queryDayCollect(AccountCnd accountCnd);
	public List<YearCollect> queryYearCollect(AccountCnd accountCnd);
	
	
	/**
	 * 
	 * <p>
	 * Description:更新存管金额<br />
	 * </p>
	 * @author tanghaitao
	 * @version 0.1 2016年5月24日
	 * @param accountVo
	 * @return
	 * int
	 */
	public Integer updateCGAccount(AccountVo accountVo);

}
