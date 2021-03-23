package com.dxjr.portal.invest.service;

import java.util.List;
import java.util.Map;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.SearchInvestCnd;
import com.dxjr.portal.borrow.vo.BorrowVo;
import com.dxjr.portal.borrow.vo.TenderRecordCnd;
import com.dxjr.portal.borrow.vo.TenderRecordVo;
import com.dxjr.portal.first.vo.FirstTenderRealCnd;
import com.dxjr.security.ShiroUser;

public interface InvestRecordService {

	/**
	 * 
	 * <p>
	 * Description:查询我要投标列表<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月20日
	 * @param map
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception Page
	 */

	public Page findInvest(SearchInvestCnd seach, Page p) throws Exception;
	/**
	 * 
	 * <p>
	 * Description:查询我要投标列表<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月20日
	 * @param map
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception Page
	 */

	public Page findInvest1(SearchInvestCnd seach, Page p) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据借款标查询该标的投标记录<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年9月4日
	 * @param borrowId
	 * @return
	 * @throws Exception List<TenderRecordVo>
	 */
	List<TenderRecordVo> queryTenderRecordByBorrowId(Integer borrowId) throws Exception;

	/**
	 * <p>
	 * Description:根据投标条件查询投标记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年2月5日
	 * @param borrowId
	 * @return
	 * @throws Exception List<TenderRecordVo>
	 */
	public List<TenderRecordVo> queryListByTenderRecordCnd(TenderRecordCnd tenderRecordCnd) throws Exception;

	
	/**
	 * <p>
	 * Description:根据借款标id获得协议内容 <br />
	 * 自己查看-显示全名称</br>
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年6月2日
	 * @param borrowId
	 * @param contextPath
	 * @param userId
	 * @return
	 * @throws Exception
	 * String
	 */
	public String queryBorrowProtocol(Integer borrowId, String contextPath,Integer userId) throws Exception;

	public List<BorrowVo> queryInvestRecordList(SearchInvestCnd seach, Page page);

	public List<BorrowVo> queryBorrowByLimit(int type, int i, int j);

   
	/**
	 * <p>
	 * Description:根据待收ID查询债转协议内容<br />
	 * 债权转让协议- 自己查看全名称<br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年6月2日
	 * @param collectionId
	 * @param contextPath
	 * @param userId
	 * @return
	 * @throws Exception
	 * String
	 */
	public String queryTransferXiyiContent(int collectionId, String contextPath,Integer userId) throws Exception;
	
	/**
	 * <p>
	 * Description: 下载-债权转让协议 <br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年6月4日
	 * @param collectionId
	 * @param contextPath
	 * @param userId
	 * @return
	 * @throws Exception
	 * String
	 */
	public String queryTransferXiyiContentDownLoad(int collectionId, String contextPath,Integer userId,Map<String, Object> reqMap) throws Exception;
    
	public Page selectAllTenderBorrowConstainTransfer(SearchInvestCnd seach, Page page);
	
	/**
	 * <p>
	 * Description:根据待收ID查询直通车转让协议<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年4月11日
	 * @param collectionId
	 * @param contextPath
	 * @return
	 * @throws Exception
	 * String
	 */
	public String queryFirstTransferXiyiContent(int collectionId, String contextPath,ShiroUser shiroUser,int flag) throws Exception;
	
	/**
	 * <p>
	 * Description:根据最终认购记录查询直通车转让协议<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年4月11日
	 * @param firstTenderRealId
	 * @return
	 * @throws Exception
	 * String
	 */
	public String queryFirstRealTransferXiyiContent(int firstTenderRealId,ShiroUser shiroUser,String contextPath) throws Exception ;



	/**
	 * <p>
	 * Description:下载协议<br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年6月2日
	 * @param borrowId
	 * @param contextPath
	 * @param userId
	 * @return
	 * @throws Exception
	 * String
	 */
	public String queryBorrowProtocolDload(Integer borrowId, String contextPath,Integer userId,Map<String ,Object> reqMap) throws Exception;
	
	
	/**
	 * <p>
	 * Description:下载协议-直转让-待收<br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年6月8日
	 * @param collectionId
	 * @param contextPath
	 * @param shiroUser
	 * @param flag
	 * @param reqMap
	 * @return
	 * @throws Exception
	 * String
	 */
	public String queryFirstTransferXiyiContentDownLoad(int collectionId, String contextPath, ShiroUser shiroUser, int flag,Map<String ,Object> reqMap) throws Exception;

	
	/**
	 * <p>
	 * Description:下载协议 - 直通车 - 已解锁 <br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年6月8日
	 * @param firstTenderRealCnd
	 * @param shiroUser
	 * @param reqMap
	 * @return
	 * @throws Exception
	 * String
	 */
	public String toFirstTenderXiyiDownLoad(FirstTenderRealCnd firstTenderRealCnd, ShiroUser shiroUser,Map<String ,Object> reqMap) throws Exception;

	
	
	/**
	 * <p>
	 * Description: 下载-直- 转让- 已解锁 <br />
	 * </p>
	 * @author HuangJun
	 * @version 0.1 2015年6月8日
	 * @param firstTenderRealId
	 * @param shiroUser
	 * @param contextPath
	 * @param reqMap
	 * @return
	 * @throws Exception
	 * String
	 */
	public String queryFirstRealTransferXiyiContentDownLoad(int firstTenderRealId,ShiroUser shiroUser,String contextPath, Map<String ,Object> reqMap) throws Exception ;

	
}
