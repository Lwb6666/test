package com.dxjr.portal.borrow.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dxjr.base.entity.Borrower;
import com.dxjr.base.entity.Mortgage;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.borrow.vo.SalariatBorrowVo;
import com.dxjr.security.ShiroUser;

public interface SalariatBorrowService {

	/**
	 * 诚薪贷申请初始化
	 * <p>
	 * Description:诚薪贷申请初始化<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月18日
	 * @param loginMem 当前登录用户
	 * @return
	 * @throws Exception ModelAndView
	 */
	public ModelAndView initApply(ShiroUser loginMem, int productType, String viewType) throws Exception;

	/**
	 * 诚薪贷申请
	 * <p>
	 * Description:保存诚薪贷申请信息<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月18日
	 * @param loginMem 当前登录用户
	 * @param salariatBorrowVo 借款信息
	 * @param borrower 借款人信息
	 * @param mortgage 资产信息
	 * @return
	 * @throws Exception MessageBox
	 */
	public MessageBox saveApplySalariat(ShiroUser loginMem, SalariatBorrowVo salariatBorrowVo, Borrower borrower, Mortgage mortgage, String addip, int productType,Integer businessUserId) throws Exception;

	/**
	 * 诚薪贷资料上传初始化
	 * <p>
	 * Description:诚薪贷资料上传初始化<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月18日
	 * @param loginMem 当前登录用户
	 * @param borrowId 借款记录ID
	 * @return
	 * @throws Exception ModelAndView
	 */
	public ModelAndView initUpload(ShiroUser loginMem, Integer borrowId) throws Exception;

	/**
	 * 保存上传资料
	 * <p>
	 * Description:保存上传资料<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月18日
	 * @param loginMem 当前登录用户
	 * @param files 上传的文件
	 * @param borrowId 借款记录ID
	 * @param style 上传证件类型
	 * @param request
	 * @return
	 * @throws Exception MessageBox
	 */
	public MessageBox saveBorrowInfos(ShiroUser loginMem, MultipartFile[] files, Integer borrowId, Integer style, HttpServletRequest request) throws Exception;

	/**
	 * 根据历史标上传资料
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年10月13日
	 * @param loginMem
	 * @param addip
	 * @param borrowId
	 * @param oldBorrowId
	 * @return
	 * @throws Exception MessageBox
	 */
	public MessageBox saveBorrowInfosFromOldBorrow(ShiroUser loginMem, String addip, Integer borrowId, Integer oldBorrowId) throws Exception;

	/**
	 * 删除上传资料
	 * <p>
	 * Description:删除上传资料<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年8月18日
	 * @param loginMem 当前登录用户
	 * @param docId 文件ID
	 * @param borrowId 借款记录ID
	 * @return
	 * @throws Exception MessageBox
	 */
	public MessageBox deleteDoc(ShiroUser loginMem, Integer docId, Integer borrowId) throws Exception;

	/**
	 * 删除借款标全部资料
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年10月17日
	 * @param loginMem
	 * @param borrowId
	 * @return
	 * @throws Exception MessageBox
	 */
	public MessageBox deleteAllDoc(ShiroUser loginMem, Integer borrowId) throws Exception;

	/**
	 * 借款标编辑初始化
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年10月11日
	 * @param borrowId
	 * @param loginMem
	 * @return
	 * @throws Exception ModelAndView
	 */
	public ModelAndView initEditBorrow(Integer borrowId, ShiroUser loginMem) throws Exception;

	/**
	 * 借款标编辑提交
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author huangpin
	 * @version 0.1 2014年10月11日
	 * @param loginMem
	 * @param salariatBorrowVo
	 * @param borrower
	 * @param mortgage
	 * @param borrowId
	 * @return
	 * @throws Exception MessageBox
	 */
	public MessageBox updateBorrow(ShiroUser loginMem, SalariatBorrowVo salariatBorrowVo, Borrower borrower, Mortgage mortgage, int borrowId,Integer businessUserId) throws Exception;

}
