package com.dxjr.portal.account.service;

import java.util.List;
import java.util.Map;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.AccountUploadDocCnd;
import com.dxjr.portal.account.vo.AccountUploadDocVo;

/**
 * <p>
 * Description:上传资料业务类<br />
 * </p>
 * 
 * @title AccountUploadDocService.java
 * @package com.dxjr.portal.account.service
 * @author justin.xu
 * @version 0.1 2014年5月14日
 */
public interface AccountUploadDocService {
	/**
	 * <p>
	 * Description:根据用户id查询集合数量<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param Integer memberId
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryUploadDocCountByUserId(Integer memberId) throws Exception;

	/**
	 * <p>
	 * Description:根据借款标id查询认证类型集合和时间<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年9月14日
	 * @param borrowId
	 * @return
	 * @throws Exception List<AccountUploadDocVo>
	 */
	public List<AccountUploadDocVo> queryUniqueStylesByBorrowId(Integer borrowId) throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询对象集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param accountUploadDocCnd
	 * @return
	 * @throws Exception List<AccountUploadDocVo>
	 */
	public List<AccountUploadDocVo> queryAccountUploadDocList(AccountUploadDocCnd accountUploadDocCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据BorrowID查询记录集<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月27日
	 * @param map
	 * @param p
	 * @return
	 * @throws Exception Page
	 */
	public Page queryByBorrowId(Map<String, Object> map, Page p) throws Exception;

}
