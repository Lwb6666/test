package com.dxjr.portal.invest.service;

import java.util.List;
import java.util.Map;

import com.dxjr.common.page.Page;
import com.dxjr.portal.collection.vo.BCollectionRecordVo;
import com.dxjr.portal.invest.vo.CollectionRepayInfoVo;
import com.dxjr.portal.invest.vo.CollectionStatisticCnd;
import com.dxjr.portal.invest.vo.CommonCollectionVo;

public interface CollectionRecordService {
	/**
	 * 
	 * <p>
	 * Description:普通待收、直通车待收查询<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年8月13日
	 * @param parameter
	 * @param pages
	 * @return Page
	 */
	Page queryMyCollections(Map<String, Object> parameter, Page pages);

	List<CommonCollectionVo> queryMyCollections(Boolean isStatistic, Map<String, Object> parameter);

	/**
	 * 
	 * <p>
	 * Description:直通车投标记录查询<br />
	 * </p>
	 * 
	 * @author 胡建盼
	 * @version 0.1 2014年8月13日
	 * @param parameter
	 * @param pages
	 * @return Page
	 */
	Page queryAccFirstCollections(Map<String, Object> parameter, Page pages);

	List<CommonCollectionVo> queryAccFirstCollections(Map<String, Object> parameter);

	/**
	 * 直通车待收记录查询不分页(状态为已还款未还款)
	 * <p>
	 * Description:直通车待收记录查询不分页(状态为已还款未还款)<br />
	 * </p>
	 * @author wushaoling
	 * @version 0.1 2016年5月31日
	 * @param parameter
	 * @return
	 * List<CommonCollectionVo>
	 */
	public List<CommonCollectionVo> queryAccFirstCommonCollectionsList(Map<String,Object> parameter);

	
	// 计算逾期罚息
	public List<CommonCollectionVo> calculatedLateInterest(Page p, List<CommonCollectionVo> listReturn);

	/**
	 * 计算罚息（根据直通车投资记录查询查询罚息） 
	 * <p>
	 * 计算罚息（根据直通车投资记录查询查询罚息） <br />
	 * </p>
	 * @author wushaoling
	 * @version 0.1 2016年5月31日
	 * @param firstCollectionsList
	 * @param listReturn
	 * @return
	 * List<CommonCollectionVo>
	 */
	public List<CommonCollectionVo> calculatedLateInterestInfo(List<CommonCollectionVo> firstCollectionsList, List<CommonCollectionVo> listReturn);

	/**
	 * <p>
	 * Description:更新尚未还款为已债权转让状态<br />
	 * </p>
	 * 
	 * @author 邹理享
	 * @version 0.1 2014年10月30日
	 * @param strings void
	 */
	/* void updateCollectionRecordByIds(String[] strings); */
	/**
	 * <p>
	 * Description:根据查询条件有效应收总额和人数信息<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年12月22日
	 * @param collectionStatisticCnd
	 * @return
	 * @throws Exception BigDecimal
	 */
	public CollectionRepayInfoVo queryRepayTotalByCnd(CollectionStatisticCnd collectionStatisticCnd) throws Exception;

	Boolean existsCollectionRecordByCnd(Map<String, Integer> dto);
	
	/**
	 * 根据投标ID借标ID用户ID查询待收记录
	 * <p>
	 * Description:根据Map参数查询待收记录<br />
	 * </p>
	 * @author wushaoling
	 * @version 0.1 2016年5月30日
	 * @param params
	 * @return
	 * List<BCollectionRecordVo>
	 */
	public List<BCollectionRecordVo> queryCollectionrecord(Map<String, Object> params);
}
