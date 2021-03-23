package com.dxjr.portal.account.service;

import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.AccountOperateVO;

public interface AccountOperateRecordService {

	/***
	 * 
	 * <p>
	 * Description:获取符合条件的账户操作记录<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年8月5日
	 * @param userAccountCnd
	 * @param curPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 * Page
	 */
	public Page searchPageUserAccountList(String username,String startTime ,String endTime ,String type,
			Integer curPage, Integer pageSize);
	public List<AccountOperateVO> searchOneMonthUserAccountList(String username, String startTime, String endTime, String type, String accountType);
	public Integer countUserAccount(String username,String startTime ,String endTime ,String type);
	/**
	 * 
	 * <p>
	 * Description:更加accountId查询账户操作日志<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年9月12日
	 * @param accountId
	 * @return
	 * AccountOperateVO
	 */
	public AccountOperateVO searchPaymentDetail(Integer accountId);


    public Page searchPageUserAccountList(String username,String startTime ,String endTime ,String type,
                                          String accountType, Integer curPage, Integer pageSize);
}
