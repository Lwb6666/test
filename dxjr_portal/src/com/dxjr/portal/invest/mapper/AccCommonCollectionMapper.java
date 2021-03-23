package com.dxjr.portal.invest.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dxjr.common.page.Page;
import com.dxjr.portal.invest.vo.CommonCollectionVo;

/**
 * <p>
 * Description:我的账户普通待收数据访问类<br />
 * </p>
 * 
 * @title AccCommonCollectionMapper.java
 * @author 胡建盼
 * @version 0.1 2014年8月7日
 */
public interface AccCommonCollectionMapper {
	/**
	 * 
	 * <p>
	 * Description:普通待收记录查询<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年9月3日
	 * @param parameter
	 * @param page
	 * @return
	 * List<CommonCollectionVo>
	 */
	public List<CommonCollectionVo> queryAccCommonCollectionsWithPage(Map<String,Object> parameter,Page page);
	public List<CommonCollectionVo> queryAccCommonCollections(Map<String,Object> parameter);
	/**
	 * 
	 * <p>
	 * Description:统计条件范围内，普通待收记录条数<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年9月3日
	 * @param parameter
	 * @return
	 * Integer
	 */
	public Integer countAccCommonCollections(Map<String,Object> parameter);
	/**
	 * 
	 * <p>
	 * Description:直通车待收记录查询<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年9月3日
	 * @param parameter
	 * @param page
	 * @return
	 * List<CommonCollectionVo>
	 */
	public List<CommonCollectionVo> queryAccFirstCommonCollectionsWithPage(Map<String,Object> parameter,Page page);
	public List<CommonCollectionVo> queryAccFirstCommonCollections(Map<String,Object> parameter);
	
	/**
	 * 直通车待收记录查询不分页(状态为已还款未还款)
	 * <p>
	 * Description:直通车待收记录查询不分页<br />
	 * </p>
	 * @author wushaoling
	 * @version 0.1 2016年5月31日
	 * @param parameter
	 * @param page
	 * @return
	 * List<CommonCollectionVo>
	 */
	public List<CommonCollectionVo> queryAccFirstCommonCollectionsList(Map<String,Object> parameter);

	
	/**
	 * 
	 * <p>
	 * Description:直通车待收记录条数<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年9月3日
	 * @param parameter
	 * @return
	 * Integer
	 */
	public Integer countAccFirstCommonCollections(Map<String,Object> parameter);

	public void updateCollectionRecordByIds(@Param("ids") String[] collectionIds);
	}
