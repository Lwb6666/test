package com.dxjr.portal.first.mapper;

import java.util.List;

import com.dxjr.base.entity.FirstTransfer;
import com.dxjr.common.page.Page;
import com.dxjr.portal.first.vo.FirstTransferBorrowTotalVo;
import com.dxjr.portal.first.vo.FirstTransferCnd;
import com.dxjr.portal.first.vo.FirstTransferTypeCnd;
import com.dxjr.portal.first.vo.FirstTransferTypeVo;
import com.dxjr.portal.first.vo.FirstTransferVo;

/**
 * <p>
 * Description:直通车转让数据库访问类<br />
 * </p>
 * 
 * @title FirstTransferMapper.java
 * @package com.dxjr.portal.first.mapper
 * @author justin.xu
 * @version 0.1 2015年3月11日
 */
public interface FirstTransferMapper {
	/**
	 * <p>
	 * Description:根据条件查询直通车可转让的数量<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月11日
	 * @param firstTransferTypeCnd
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer queryCanTransferCount(FirstTransferTypeCnd firstTransferTypeCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据条件分页查询直通车可转让的集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月13日
	 * @param firstTransferTypeCnd
	 * @param page
	 * @return
	 * @throws Exception
	 *             List<FirstTransferTypeVo>
	 */
	public List<FirstTransferTypeVo> queryCanTransferList(FirstTransferTypeCnd firstTransferTypeCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询直通车可转让的集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月13日
	 * @param firstTransferTypeCnd
	 * @param page
	 * @return
	 * @throws Exception
	 *             List<FirstTransferTypeVo>
	 */
	public List<FirstTransferTypeVo> queryCanTransferList(FirstTransferTypeCnd firstTransferTypeCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description: 根据直通车认购记录id得到关联借款的待收统计信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月16日
	 * @param firstTenderRealId
	 * @return
	 * @throws Exception
	 *             BigDecimal
	 */
	public FirstTransferBorrowTotalVo queryFirstTransferBorrowTotal(Integer firstTenderRealId) throws Exception;

	/**
	 * <p>
	 * Description:保存直通车转让信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月16日
	 * @param record
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer saveFirstTransfer(FirstTransfer firstTransfer) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据查询条件查询直通车转让信息总件数<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月16日
	 * @param firstTransferCnd
	 * @return Integer
	 * @throws Exception
	 */
	public Integer queryFirstTransferCount(FirstTransferCnd firstTransferCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据查询条件分页查询直通车转让信息<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月16日
	 * @param firstTransferCnd
	 * @param page
	 * @return
	 * @throws Exception
	 *             List<FirstTransferVo>
	 */
	public List<FirstTransferVo> queryFirstTransfer(FirstTransferCnd firstTransferCnd, Page page) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据条件查询直通车转让信息<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月16日
	 * @param firstTransferCnd
	 * @param page
	 * @return
	 * @throws Exception
	 *             List<FirstTransferVo>
	 */
	public List<FirstTransferVo> queryFirstTransfer(FirstTransferCnd firstTransferCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据Id查询直通车转让信息<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月18日
	 * @param transferId
	 * @return
	 * @throws Exception
	 *             FirstTransferVo
	 */
	public FirstTransferVo queryTransferById(Integer transferId) throws Exception;

	/**
	 * <p>
	 * Description:根据条件分页查询我的账号下直通车转让信息的集合<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月13日
	 * @param firstTransferCnd
	 * @param page
	 * @return
	 * @throws Exception
	 *             List<FirstTransferVo>
	 */
	public List<FirstTransferVo> queryMyFirstTransferList(FirstTransferCnd firstTransferCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:更新直通车转让的状态<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月20日
	 * @throws Exception
	 *             void
	 */
	public void updateFirstTransferStatus(FirstTransfer firstTransfer) throws Exception;

	/**
	 * <p>
	 * Description:根据条件分页查询我的账号下直通车认购信息的集合<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月23日
	 * @param firstTransferCnd
	 * @param page
	 * @return
	 * @throws Exception
	 *             List<FirstTransferVo>
	 */
	public List<FirstTransferVo> queryMyFirstTransferSubscribeList(FirstTransferCnd firstTransferCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:根据条件分页查询我的账号下直通车认购信息总件数<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月23日
	 * @param firstTransferCnd
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer queryMyFirstTransferSubscribeCount(FirstTransferCnd firstTransferCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据待收ID查询直通车转让信息<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年4月11日
	 * @param firstTransferCnd
	 * @return
	 * @throws Exception
	 *             List<FirstTransferVo>
	 */
	public List<FirstTransferVo> queryMyFirstTransferByCollectionId(FirstTransferCnd firstTransferCnd) throws Exception;

	/**
	 * 根据最终认购记录ID查询直通车转让信息
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年4月11日
	 * @param firstTransferCnd
	 * @return
	 * @throws Exception
	 *             List<FirstTransferVo>
	 */
	public List<FirstTransferVo> queryMyFirstTransferByRealId(FirstTransferCnd firstTransferCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据查询条件查询直通车已转让信息总件数<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月16日
	 * @param firstTransferCnd
	 * @return Integer
	 * @throws Exception
	 */
	public Integer queryMyFirstTransferedCount(FirstTransferCnd firstTransferCnd) throws Exception;

	/**
	 * <p>
	 * Description:根据查询条件查询直通车已转让信息<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年5月5日
	 * @param firstTransferCnd
	 * @param page
	 * @return
	 * @throws Exception
	 *             List<FirstTransferVo>
	 */
	public List<FirstTransferVo> queryMyFirstTransferedList(FirstTransferCnd firstTransferCnd, Page page) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据id查询直通车转让记录并锁定<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月14日
	 * @param id
	 * @return
	 * @throws Exception
	 *             FirstTransferVo
	 */
	public FirstTransferVo selectFirstTransferByIdForUpdate(Integer id) throws Exception;
}