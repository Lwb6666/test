package com.dxjr.portal.first.service;

import java.util.List;
import java.util.Map;

import com.dxjr.common.page.Page;
import com.dxjr.portal.first.vo.FirstBorrowCnd;
import com.dxjr.portal.first.vo.FirstBorrowOpenCnd;
import com.dxjr.portal.first.vo.FirstBorrowVo;

/**
 * <p>
 * Description:直通车业务类<br />
 * </p>
 * 
 * @title FirstBorrowService.java
 * @package com.dxjr.portal.first.service
 * @author justin.xu
 * @version 0.1 2014年7月14日
 */
public interface FirstBorrowNewService {

	/**
	 * <p>
	 * Description:根据条件分页查询记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月2日
	 * @param firstBorrowCnd
	 * @param page
	 * @return
	 * @throws Exception Page
	 */
	public Page queryPageListByCnd(FirstBorrowCnd firstBorrowCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询单个对象<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月2日
	 * @param firstBorrowCnd
	 * @return
	 * @throws Exception FirstBorrowVo
	 */
	public FirstBorrowVo queryFirstBorrowByCnd(FirstBorrowCnd firstBorrowCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据id查询有效直通车<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年8月22日
	 * @param id
	 * @return
	 * @throws Exception FirstBorrowVo
	 */
	public FirstBorrowVo queryAvailableFirstBorrowById(Integer id) throws Exception;

	/**
	 * <p>
	 * Description:直通车统计数据<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月15日
	 * @return
	 * @throws Exception Map<String,Object>
	 */
	public Map<String, Object> queryFirstData() throws Exception;

	/**
	 * <p>
	 * Description:开通直通车,直接生成最终认购记录<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月16日
	 * @param firstBorrowOpenCnd
	 * @return
	 * @throws Exception String
	 */
	public String saveTenderFirstBorrowForTenderReal(Integer userId, FirstBorrowOpenCnd firstBorrowOpenCnd) throws Exception;

	/**
	 * <p>
	 * Description:直通车满标自动复审通过<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月17日
	 * @param firstBorrowId
	 * @return
	 * @throws Exception String
	 */
	public String saveTenderFirstAutoApprove(Integer firstBorrowId, String ip) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:开始直通车投标<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年8月12日
	 * @param borrow_id
	 * @return
	 * @throws Exception String
	 */
	public String saveFirstBorrow(int borrow_id) throws Exception;

	/**
	 * <p>
	 * Description:根据直通车id，在解锁时更新直通车实际总金额<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月28日
	 * @param unlockaccount 要解锁的金额
	 * @param id
	 * @return
	 * @throws Exception Integer
	 */
	public Integer updateRealAccountByUnlock(Integer unlockaccount, Integer firstBorrowId) throws Exception;

	/**
	 * <p>
	 * Description:根据直通车id，在解锁时更新直通车更新直通车记录的状态为失效<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年7月28日
	 * @param id
	 * @return
	 * @throws Exception Integer
	 */
	public Integer updateFirstBorrowStatusByUnlock(Integer firstBorrowId) throws Exception;

	/**
	 * 
	 * <p>
	 * 获取最新直通车信息
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月8日
	 * @return
	 * @throws Exception Map
	 */
	public FirstBorrowVo getLatest();

	/**
	 * 
	 * <p>
	 * Description:获取首页显示的直通车记录(仅4条记录)<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年11月19日
	 * @return
	 * @throws Exception FirstBorrowVo
	 */
	public FirstBorrowVo queryFirstBorrowForIndex() throws Exception;

	/**
	 * 
	 * <p>
	 * Description:获取直通车专区页面显示的直通车记录<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2014年11月20日
	 * @param firstBorrowCnd
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception Page
	 */
	public Page queryFirstListByCnd(FirstBorrowCnd firstBorrowCnd, int pageNo, int pageSize) throws Exception;

	public List<FirstBorrowVo> queryFirstBorrowByFirstBorrowCnd(FirstBorrowCnd firstBorrowCnd, Page page) throws Exception;
}
