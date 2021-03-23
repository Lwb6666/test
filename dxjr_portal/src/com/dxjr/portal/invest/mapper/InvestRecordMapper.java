package com.dxjr.portal.invest.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.SearchInvestCnd;
import com.dxjr.portal.borrow.vo.BorrowVo;

public interface InvestRecordMapper {
	/**
	 * 
	 * <p>
	 * Description:查询我要投标列表<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月20日
	 * @param map
	 * @return
	 * @throws Exception
	 *             List<BorrowVo>
	 */
	public List<BorrowVo> queryInvestRecord(SearchInvestCnd seach, Page p)
			throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询我要投标列表记录数<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月21日
	 * @param map
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int queryInvestRecordCount(SearchInvestCnd seach) throws Exception;
	/**
	 * 
	 * <p>
	 * Description:查询我要投标列表记录数<br />
	 * </p>
	 * 
	 * @author chenlu
	 * @version 0.1 2014年8月21日
	 * @param map
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int queryInvestRecordCount2(SearchInvestCnd seach) throws Exception;

	/**
	 * <p>
	 * Description:投标查询-limit<br />
	 * </p>
	 * @author chenpeng
	 * @version 0.1 2015年2月3日
	 * @param 
	 * @return list
	 */
	public List<BorrowVo> queryBorrowByLimit(@Param("type") int type, @Param("start") int start,  @Param("count") int count);

	/**
	 * <p>
	 * Description:查询全部投标<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2015年3月3日
	 * @param seach
	 * @param page FSTASK
	 * @return List<BorrowVo>
	 */
	public List<BorrowVo> selectListAllTenderBorrowConstainTransfer(SearchInvestCnd seach, Page page);

	public int selectCountAllTenderBorrowConstainTransfer(SearchInvestCnd seach);
}
