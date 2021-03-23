package com.dxjr.portal.borrow.service;

import java.math.BigDecimal;

import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.Borrow;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.security.ShiroUser;

public interface NetValueBorrowService {

	/**
	 * 净值贷申请初始化
	 * <p>
	 * Description:净值贷申请初始化<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年8月18日
	 * @param loginMem 当前登录用户
	 * @return
	 * @throws Exception ModelAndView
	 */
	public ModelAndView initApply(ShiroUser loginMem, String viewType) throws Exception;

	/**
	 * 净值贷申请
	 * <p>
	 * Description:保存净值贷申请信息<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年8月18日
	 * @param loginMem 当前登录用户
	 * @param borrow 借款信息
	 * @param addip
	 * @param netMoneyLimit 净值额度
	 * @return
	 * @throws Exception MessageBox
	 */
	public MessageBox saveApplyNetValue(ShiroUser loginMem, Borrow borrow, String addip, BigDecimal netMoneyLimit) throws Exception;

	public String checkBorrowCertification(ShiroUser loginMem) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:估算净值标满标后的净值额度和可提金额<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年1月21日
	 * @param userId
	 * @param account
	 * @param timeLimit
	 * @param style
	 * @param apr
	 * @return
	 * @throws Exception MessageBox
	 */
	public MessageBox getUserNetMoneyLimitForFullBorrow(int userId, BigDecimal account, Integer timeLimit, Integer style, BigDecimal apr) throws Exception;
}
