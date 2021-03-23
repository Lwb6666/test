package com.dxjr.portal.borrow.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.dxjr.common.page.Page;
import com.dxjr.portal.borrow.vo.BorrowCnd;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.borrow.vo.TenderBorrowCnd;
import com.dxjr.portal.repayment.vo.BRepaymentRecordVo;

/**
 * <p>
 * Description:借款标业务接口<br />
 * </p>
 * 
 * @title BorrowService.java
 * @package com.dxjr.portal.borrow.service
 * @author justin.xu
 * @version 0.1 2014年8月13日
 */
public interface BorrowService {

	/**
	 * <p>
	 * Description:根据id查询借款标<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月13日
	 * @param id
	 * @return
	 * @throws Exception BorrowVo
	 */
	public BorrowVo selectByPrimaryKey(Integer id) throws Exception;

	/**
	 * <p>
	 * Description:根据id查询借款标并锁定<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月13日
	 * @param id
	 * @return
	 * @throws Exception BorrowVo
	 */
	public BorrowVo selectByPrimaryKeyForUpdate(Integer id) throws Exception;

	/**
	 * <p>
	 * Description:分页查询投标中的借款标<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月13日
	 * @param curPage
	 * @return Page
	 */
	public Page selectTenderBorrow(Page page) throws Exception;

	/**
	 * <p>
	 * Description:借款标初审<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月13日
	 * @param borrowId
	 * @param flag
	 * @param userId
	 * @param remark
	 * @return String
	 */
	public String saveApproveBorrowFirst(Integer borrowId, Integer flag, Integer userId, String remark, String creditRating) throws Exception;

	/**
	 * <p>
	 * Description:反欺诈审核<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月13日
	 * @param borrowId
	 * @param flag
	 * @param userId
	 * @param remark
	 * @return String
	 */
	public String saveApproveBorrowAntiFraud(Integer borrowId, Integer flag, Integer userId, String remark) throws Exception;

	/**
	 * <p>
	 * Description:借款标终审<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月13日
	 * @param borrowId
	 * @param flag
	 * @param userId
	 * @param remark
	 * @return String
	 */
	public String saveApproveBorrowFinal(Integer borrowId, Integer flag, Integer userId, String remark, String addip) throws Exception;

	/**
	 * <p>
	 * Description:借款标复审<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月13日
	 * @param borrowid
	 * @param flag
	 * @param checkuserid
	 * @param addip
	 * @param remark
	 * @return String
	 */
	public String saveApproveBorrowReCheck(Integer borrowid, Integer checkUserId, String checkRemark, String addip) throws Exception;

	/**
	 * <p>
	 * Description:用户还款<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月13日
	 * @param repaymentid
	 * @param addip
	 * @return
	 * @throws Exception String
	 */
	public String saveRepayBorrow(Integer repaymentid, String addip, Integer userId) throws Exception;

	/**
	 * <p>
	 * Description:操作罚息<br />
	 * </p>
	 * 
	 * @author zhanghao
	 * @version 0.1 2014年5月27日
	 * @param borrowid
	 * @throws Exception
	 */
	public String saveOperatingPenalty(Integer repaymentid, String addip) throws Exception;

	/**
	 * <p>
	 * Description:进行手动投标<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月14日
	 * @param tenderBorrowCnd
	 * @param memberId
	 * @param addip
	 * @param tenderType
	 * @return
	 * @throws Exception String
	 */
	public String saveManualTender(TenderBorrowCnd tenderBorrowCnd, Integer memberId, String addip, Integer tenderType) throws Exception;

	/**
	 * <p>
	 * Description:撤标<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月6日
	 * @param borrowId
	 * @throws Exception String
	 */
	public String cancelBorrow(int borrowId, int userId, String ip) throws Exception;

	/**
	 * <p>
	 * 获取成交总金额
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月6日
	 * @return
	 * @throws Exception BigDecimal
	 */
	public BigDecimal getTotalMoney() throws Exception;

	/**
	 * <p>
	 * 未满抵押标<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月12日
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception List<Borrow>
	 */
	public List<BorrowVo> getLatestNotFull(int pageNum, int pageSize) throws Exception;

	/**
	 * <p>
	 * 已经满的抵押标<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月12日
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception List<Borrow>
	 */
	public List<BorrowVo> getLatestFull(int pageNum, int pageSize) throws Exception;

	/**
	 * <p>
	 * 预发标<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月12日
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception List<Borrow>
	 */
	public List<BorrowVo> getAdvanced(int pageNum, int pageSize) throws Exception;

	/**
	 * <p>
	 * 根据待还记录查询借款标并转换成map对应<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月16日
	 * @param list
	 * @return Map<Integer,BorrowVo>
	 */
	public Map<Integer, BorrowVo> repaymentList2map(List<BRepaymentRecordVo> list) throws Exception;

	/**
	 * <p>
	 * Description:查询投标中列表记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年9月9日
	 * @param borrowCnd
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception Page
	 */
	public Page queryTendering(BorrowCnd borrowCnd, int pageNum, int pageSize) throws Exception;

	/**
	 * 查询用户所有发标列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2014年10月13日
	 * @param borrowCnd
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception Page
	 */
	public Page queryAll(BorrowCnd borrowCnd, int pageNum, int pageSize) throws Exception;

	/**
	 * <p>
	 * Description:查询借款列表<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月25日
	 * @param map
	 * @param p
	 * @return
	 * @throws Exception Page
	 */
	public Page getBorrowList(Map<String, Object> map, Page p) throws Exception;

	/**
	 * <p>
	 * Description:净值标提前满标<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月27日
	 * @param borrowId
	 * @return
	 * @throws Exception String
	 */
	public String advanceFullBorrow(int borrowId, int userId, String ip) throws Exception;

	/**
	 * <p>
	 * Description:投资管理===正在投标中列表==即当前用户投资其他正在招标中的借款标信息<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年9月3日
	 * @param map
	 * @param page
	 * @return
	 * @throws Exception Page
	 */
	Page queryTenderingForOtherBorrow(Map<String, Object> map, Page page) throws Exception;
	
	/**
	 * 散标列表
	 * <p>
	 * Description:重写<br />
	 * </p>
	 * @author wushaoling
	 * @version 0.1 2016年5月27日
	 * @param map
	 * @param page
	 * @return
	 * @throws Exception
	 * Page
	 */
	Page queryTenderingForOtherBorrowNew(Map<String, Object> map, Page page) throws Exception;

	/**
	 * 根据投标记录id查询投标记录
	 * <p>
	 * Description:根据id查询投标记录<br />
	 * </p>
	 * @author wushaoling
	 * @version 0.1 2016年5月30日
	 * @param id
	 * @return
	 * BorrowVo
	 */
	BorrowVo queryTenderingForId(Map<String, Object> map);

	/**
	 * 根据认购记录Map查询投标信息
	 * <p>
	 * Description:根据认购记录Map查询投标信息<br />
	 * </p>
	 * @author wushaoling
	 * @version 0.1 2016年5月30日
	 * @param id
	 * @return
	 * BorrowVo
	 */
	public BorrowVo queryTenderingForMap(Map<String, Object> map);
	/**
	 * <p>
	 * Description:验证投标数据是否正确<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年9月17日
	 * @param tenderBorrowCnd
	 * @param memberId
	 * @return
	 * @throws Exception String
	 */
	public String queryTenderDataBefore(TenderBorrowCnd tenderBorrowCnd, Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:获取未满的抵押标，包含最多3个债转<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年3月2日
	 * @param pageNum
	 * @param pageSize
	 * @return List<BorrowVo>
	 */
	public List<BorrowVo> getLatestNotFullConstainTransfer(int pageNum, int pageSize);

	/**
	 * <p>
	 * Description:获取已满的抵押标和转让标的<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年3月2日
	 * @param pageNum
	 * @param pageSize
	 * @return List<BorrowVo>
	 */
	public List<BorrowVo> getLatestFullConstainTransfer(int pageNum, int pageSize);



	public Integer updateBorrow(BorrowVo borrowVo);
}
