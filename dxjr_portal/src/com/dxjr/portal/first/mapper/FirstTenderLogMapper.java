package com.dxjr.portal.first.mapper;

import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.first.vo.FirstTenderLogCnd;
import com.dxjr.portal.first.vo.FirstTenderLogVo;

/**
 * 
 * <p>
 * Description:直通车投标日志记录数据访问类<br />
 * </p>
 * 
 * @title BaseFirstTenderLogMapper.java
 * @package com.dxjr.base.mapper
 * @author yangshijin
 * @version 0.1 2015年3月11日
 */
public interface FirstTenderLogMapper {
	/**
	 * 
	 * <p>
	 * Description:根据条件查询记录，分页<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年3月11日
	 * @param firstTenderLogCnd
	 * @return
	 * @throws Exception List<FirstTenderLogVo>
	 */
	public List<FirstTenderLogVo> queryListByCnd(FirstTenderLogCnd firstTenderLogCnd, Page page) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据条件查询记录，分页<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年3月11日
	 * @param firstTenderLogCnd
	 * @return
	 * @throws Exception List<FirstTenderLogVo>
	 */
	public List<FirstTenderLogVo> queryListByCnd(FirstTenderLogCnd firstTenderLogCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据条件，查询记录数量<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年3月11日
	 * @param firstTenderLogCnd
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryListCountByCnd(FirstTenderLogCnd firstTenderLogCnd) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据ID查询<br />
	 * </p>
	 * 
	 * @author yangshijin
	 * @version 0.1 2015年3月11日
	 * @param id
	 * @return
	 * @throws Exception FirstTenderLogVo
	 */
	public FirstTenderLogVo queryById(int id) throws Exception;
}
