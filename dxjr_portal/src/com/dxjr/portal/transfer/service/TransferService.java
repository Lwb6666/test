package com.dxjr.portal.transfer.service;

import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.transfer.entity.BTransfer;
import com.dxjr.portal.transfer.vo.BTransferVo;
import com.dxjr.portal.transfer.vo.SearchTransferVo;
import com.dxjr.portal.transfer.vo.SubscribeTransferVo;
import com.dxjr.portal.transfer.vo.TransferCnd;

/**
 * <p>
 * Description:债权转让业务类<br />
 * </p>
 * 
 * @title TransferService.java
 * @package com.dxjr.portal.transfer.service
 * @author chenpeng、邹理享
 * @version 0.1 2014年10月21日
 */
public interface TransferService {

	/**
	 * <p>
	 * Description:查询我可转让的债权<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年10月31日
	 * @param transferCnd
	 * @param page void
	 */
	void queryTransferClaim(TransferCnd transferCnd, Page page);

	/**
	 * <p>
	 * Description:查询装让的详细信息<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年10月26日
	 * @param tenderId
	 * @param userId
	 * @return BTransfer
	 */
	BTransferVo queryTransferByTenderIdAndUserId(Integer tenderId, Integer userId);

	/**
	 * <p>
	 * Description:分页查询债权转让<br />
	 * </p>
	 * 
	 * @author chenpeng
	 * @version 0.1 2014年10月27日
	 * @param curPage
	 * @return Page
	 */
	public Page findtransferList(SearchTransferVo seach, Page page) throws Exception;

	/**
	 * <p>
	 * Description:根据id查询债权转让<br />
	 * </p>
	 * 
	 * @author chenpeng
	 * @version 0.1 2014年10月27日
	 * @param id
	 * @return
	 * @throws Exception BTransferVo
	 */
	public BTransferVo selectByPrimaryKey(Integer id) throws Exception;

	/**
	 * <p>
	 * Description:进行手动认购<br />
	 * </p>
	 * 
	 * @author chenpeng
	 * @version 0.1 2014年10月27日
	 * @param SubscribeTransferVo
	 * @param memberId
	 * @param addip
	 * @param tenderType
	 * @return
	 * @throws Exception String
	 */
	public String saveManualSubscribe(SubscribeTransferVo stf, Integer userId, String realIpAddr, Integer tenderTypeManual) throws Exception;

	/**
	 * <p>
	 * Description:认购满额复审<br />
	 * </p>
	 * 
	 * @author qiongbiao.zhang
	 * @version 0.1 2014年11月7日
	 * @param transferId
	 * @param checkUserId
	 * @param checkRemark
	 * @param addIp
	 * @return String
	 * @throws Exception
	 */
	public String saveApproveTransferRecheck(Integer transferId, Integer checkUserId, String checkRemark, String addIp, Integer platForm) throws Exception;

	/**
	 * <p>
	 * Description:新增转让记录<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年10月31日
	 * @param btransfer void
	 */
	void addTransfer(BTransferVo btransfer);

	/**
	 * <p>
	 * Description:是否存在该转让记录<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年10月30日
	 * @param tenderId 投标记录id
	 * @return Boolean
	 */
	Boolean existsTransferRecordBytenderId(Integer tenderId);

	/**
	 * <p>
	 * Description:查询 转让中，转出，转入的债权列表<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年11月5日
	 * @param transferCnd
	 * @param page void
	 */
	void queryMyTransfer(TransferCnd transferCnd, Page page);

	/**
	 * <p>
	 * Description:查询是否存在该转让记录<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年11月5日
	 * @param transferCnd
	 * @return boolean
	 */
	boolean existsTransferRecord(BTransfer bTransfer);

	/**
	 * <p>
	 * Description:取消我转让<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年11月5日
	 * @param transferCnd void
	 */
	void updateMyTransferForCancel(BTransfer bTransferVo);

	/**
	 * <p>
	 * Description:查询用户转让详细信息<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年11月7日
	 * @param id
	 * @return BTransfer
	 */
	BTransfer getTransferDetailById(Integer id);

	/**
	 * <p>
	 * Description:根据转让记录id查询 认购记录<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年11月7日
	 * @param transferId
	 * @param page void
	 */
	void querySubscribesByTransferId(Integer transferId, Page page);

	/**
	 * <p>
	 * Description:根据借款id 查询转让记录<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年11月7日
	 * @param borrowId
	 * @param page void
	 * 
	 */
	void queryBorrowSubscribesByBorrowId(Integer borrowId, Page page);

	/**
	 * <p>
	 * Description:根据借款id 查询 认购人 和 投标记录<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年11月7日
	 * @param borrowId
	 * @param page void
	 */
	void queryTransferinfosByBorrowId(Integer borrowId, Integer status, Page page);

	/**
	 * <p>
	 * Description:查询个人已认购的金额总和<br />
	 * </p>
	 * 
	 * @author chenpeng
	 * @version 0.1 2014年12月17日
	 * @param TransferCnd
	 * @return String
	 */
	public String querySumAccountByUserId(TransferCnd transferCnd);

	public void updateTransferOld();

	List<BTransferVo> querytransferRecordList(SearchTransferVo seach, Page page);

	int getCountTransfering();

	/**
	 * 
	 * <p>
	 * Description:根据userId查询认购债权记录数量<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年3月25日
	 * @param userId
	 * @return
	 * @throws Exception Integer
	 */
	public Integer querySubscribesCountByUserId(Integer userId) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:去债权转让判断<br />
	 * </p>
	 * @author Shijin.Yang
	 * @version 0.1 2016年7月27日
	 * @param userId
	 * @param tenderId
	 * @return
	 * @throws Exception
	 * Integer
	 */
	public MessageBox toTransferJudge(Integer userId, Integer tenderId) throws Exception; 
}
