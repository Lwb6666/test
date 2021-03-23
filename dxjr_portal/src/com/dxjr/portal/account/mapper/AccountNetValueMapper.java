package com.dxjr.portal.account.mapper;

import com.dxjr.portal.account.util.UserLevelRatio;
import com.dxjr.portal.account.util.UserNetFullBorrowValue;
import com.dxjr.portal.account.util.UserNetRepayMoneyTotal;
import com.dxjr.portal.account.util.UserNetValue;
import com.dxjr.portal.account.vo.DrawMoneyToNoDrawCnd;

public interface AccountNetValueMapper {

	/**
	 * 
	 * <p>
	 * Description:获取净值额度等信息<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年10月28日
	 * @param netValue
	 * @throws Exception void
	 */
	public void callGetUserNetMoneyLimit(UserNetValue netValue) throws Exception;

	/**
	 * <p>
	 * Description:可提金额大于净值额度时，将可提转入受限<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年10月23日
	 * @param drawMoneyToNoDrawCnd
	 * @throws Exception void
	 */
	public void dealDrawmoneyToNodraw(DrawMoneyToNoDrawCnd drawMoneyToNoDrawCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取用户投标中的净值标预还总额和借款管理费<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年10月28日
	 * @param userNetRepayMoneyTotal
	 * @return
	 * @throws Exception UserNetRepayMoneyTotal
	 */
	public UserNetRepayMoneyTotal callGetUserNetRepayMoneyTotal(UserNetRepayMoneyTotal userNetRepayMoneyTotal) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获得用户会员等级和比率<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年12月18日
	 * @param userLevelRatio
	 * @throws Exception void
	 */
	public void callGetUserLevelRatio(UserLevelRatio userLevelRatio) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:估算净值标满标后的净值额度和可提金额 <br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年1月21日
	 * @param userNetFullBorrowValue
	 * @return
	 * @throws Exception UserNetFullBorrowValue
	 */
	public UserNetFullBorrowValue getUserNetMoneyLimitForFullBorrow(UserNetFullBorrowValue userNetFullBorrowValue) throws Exception;
}
