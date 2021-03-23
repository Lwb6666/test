package com.dxjr.portal.first.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dxjr.common.page.Page;
import com.dxjr.portal.first.vo.FirstSubscribeVo;

/**
 * <p>
 * Description:直通车转让认购数据访问类<br />
 * </p>
 * 
 * @title FirstSubscribeMapper.java
 * @package com.dxjr.portal.first.mapper
 * @author 朱泳霖
 * @version 0.1 2015年3月19日
 */
public interface FirstSubscribeMapper {

	/**
	 * <p>
	 * Description:根据直通车转让Id查询购买记录<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月19日
	 * @param firstSubscribeCnd
	 * @return
	 * @throws Exception
	 *             List<FirstSubscribeVo>
	 */
	public List<FirstSubscribeVo> querySubscribeListByTransferId(Integer transferId, Page page) throws Exception;

	/**
	 * <p>
	 * Description:查询直通车转让购买记录件数<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月20日
	 * @param transferId
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer querySubscribeListByTransferCount(Integer transferId) throws Exception;

	/**
	 * <p>
	 * Description:调用手动认购存储过程<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月27日
	 * @param params
	 *            void
	 */
	void saveManualSubscribe(Map<String, Object> params);

	/**
	 * <p>
	 * Description:调用直通车自动复审存储过程<br />
	 * </p>
	 * 
	 * @author 朱泳霖
	 * @version 0.1 2015年3月27日
	 * @param params
	 *            void
	 */
	void saveTransferRecheck(Map<String, Object> params);

	/**
	 * 
	 * <p>
	 * Description:查询userId和firstTransferId查询该用户所认购的最新一笔直通车转让认购记录<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月8日
	 * @param firstTransferId
	 * @param userId
	 * @param account
	 * @return
	 * @throws Exception
	 *             FirstSubscribeVo
	 */
	public FirstSubscribeVo querySubscribesLastByUserIdAndTransferId(@Param("firstTransferId") Integer firstTransferId,
			@Param("userId") Integer userId, @Param("account") BigDecimal account) throws Exception;
}
