package com.dxjr.portal.first.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.first.vo.FirstTransferBorrowCnd;
import com.dxjr.portal.first.vo.FirstTransferBorrowVo;

/**
 * <p>
 * Description:直通车所投借款标转让资金明细数据库访问类<br />
 * </p>
 * 
 * @title FirstTransferBorrowMapper.java
 * @package com.dxjr.portal.first.mapper
 * @author justin.xu
 * @version 0.1 2015年3月17日
 */
public interface FirstTransferBorrowMapper {
	/**
	 * <p>
	 * Description:根据转让id和最终认购数据保存直通车所投借款标转让资金明细数据<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月16日
	 * @param firstTransferLog
	 * @return
	 * @throws Exception Integer
	 */
	public Integer saveFirstTransferBorrowList(FirstTransferBorrowCnd firstTransferBorrowCnd) throws Exception;

	/**
	 * <p>
	 * Description:查询我的账号中-直通车转让对应标统计列表数量<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月11日
	 * @param firstTransferBorrowCnd
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryCanTransferBorrowCount(FirstTransferBorrowCnd firstTransferBorrowCnd) throws Exception;

	/**
	 * <p>
	 * Description:查询我的账号中-直通车转让对应标统计列表<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月13日
	 * @param firstTransferBorrowCnd
	 * @param page
	 * @return
	 * @throws Exception List<FirstTransferBorrowVo>
	 */
	public List<FirstTransferBorrowVo> queryCanTransferBorrowList(FirstTransferBorrowCnd firstTransferBorrowCnd, Page page) throws Exception;

	/**
	 * <p>
	 * Description:根据直通车转让ID查询转让资金明细信息<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年3月20日
	 * @param transferId
	 * @param page
	 * @return
	 * @throws Exception
	 * List<FirstTransferBorrowVo>
	 */
	public List<FirstTransferBorrowVo> queryTransferBorrowListByTransferId(Integer transferId,Page page) throws Exception;
	
	/**
	 * <p>
	 * Description:查询转让资金明细信息件数<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年3月20日
	 * @param transferId
	 * @return
	 * @throws Exception
	 * Integer
	 */
	public Integer queryTransferBorrowListCount(Integer transferId) throws Exception;
	
	/**
	 * <p>
	 * Description:查询转债权剩余价值合计<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年3月30日
	 * @param transferId
	 * @return
	 * @throws Exception
	 * Integer
	 */
	public BigDecimal queryBorrowCollectionAccountSum(Integer transferId) throws Exception;

	/**
	 * <p>
	 * Description:查询直通车所投标债权价格总额<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年4月1日
	 * @param firstTransferBorrowCnd
	 * @return
	 * @throws Exception
	 * BigDecimal
	 */
	public BigDecimal queryCanTransferBorrowAccountSum(FirstTransferBorrowCnd firstTransferBorrowCnd) throws Exception;
	
	/**
	 * <p>
	 * Description:查询我的账号中-直通车转让对应标统计列表<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2015年3月13日
	 * @param firstTransferBorrowCnd
	 * @param page
	 * @return
	 * @throws Exception List<FirstTransferBorrowVo>
	 */
	public List<FirstTransferBorrowVo> queryCanTransferBorrowList(FirstTransferBorrowCnd firstTransferBorrowCnd) throws Exception;
	
	/**
	 * <p>
	 * Description:查询已下车直通车转让对应标统计列表<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年5月11日
	 * @param firstTransferBorrowCnd
	 * @return
	 * @throws Exception
	 * List<FirstTransferBorrowVo>
	 */
	public List<FirstTransferBorrowVo> queryCanTransferBorrowUnList(FirstTransferBorrowCnd firstTransferBorrowCnd) throws Exception;
	
	/**
	 * <p>
	 * Description:查询直通车每个标的债权价格<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年4月15日
	 * @param firstTransferBorrowCnd
	 * @return
	 * @throws Exception
	 * FirstTransferBorrowVo
	 */
	public FirstTransferBorrowVo queryTransferedBorrow(FirstTransferBorrowCnd firstTransferBorrowCnd);
	
	/**
	 * <p>
	 * Description:根据直通车转让ID查询转让资金明细信息<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年5月11日
	 * @param firstTransferBorrowCnd
	 * @return
	 * @throws Exception
	 * List<FirstTransferBorrowVo>
	 */
	public List<FirstTransferBorrowVo> queryBorrowListByTransferId(FirstTransferBorrowCnd firstTransferBorrowCnd) throws Exception;
	
	/**
	 * <p>
	 * Description:转让资金明细信息债权价格合计<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年5月11日
	 * @param transferId
	 * @return
	 * @throws Exception
	 * BigDecimal
	 */
	public BigDecimal queryBorrowSumByTransferId(Integer transferId) throws Exception;
	
	/**
	 * <p>
	 * Description:根据直通车转让ID查询转让资金明细信息<br />
	 * </p>
	 * @author 朱泳霖
	 * @version 0.1 2015年5月11日
	 * @param firstTransferBorrowCnd
	 * @return
	 * @throws Exception
	 * List<FirstTransferBorrowVo>
	 */
	public FirstTransferBorrowVo queryBorrowListByRealId(FirstTransferBorrowCnd firstTransferBorrowCnd);
	
	/**
	 * 根据直通车转让ID查询转让金额
	 * @author WangQianJin
	 * @title @param firstTransferBorrowCnd
	 * @title @return
	 * @title @throws Exception
	 * @date 2015年9月17日
	 */
	public BigDecimal queryTransferAccountByRealId(FirstTransferBorrowCnd firstTransferBorrowCnd);
	
}