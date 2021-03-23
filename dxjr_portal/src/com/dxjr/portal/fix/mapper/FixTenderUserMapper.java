package com.dxjr.portal.fix.mapper;

import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.fix.vo.FixTenderUserCnd;
import com.dxjr.portal.fix.vo.FixTenderUserVo;

/**
 * <p>
 * Description:定期宝操作日志数据库接口类<br />
 * </p>
 * 
 * @title FixBorrowMapper.java
 * @package com.dxjr.portal.fix1.mapper
 * @author caodefeng
 * @version 0.1 2015年5月14日
 */
public interface FixTenderUserMapper {
	/**
	 * 通过条件查询记录
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * @author 陈建国
	 * @version 0.1 2015年5月23日
	 * @param fixTenderDetailCnd
	 * @return
	 * FixTenderDetailVo
	 */
	public FixTenderUserVo  getFixTenderDetailStaticByBorrowId(FixTenderUserCnd fixTenderUserCnd);

	/**
	 * 统计定期宝认购总次数
	 * @param id
	 * @return
	 */
	public Integer queryTotalTBCounts(FixTenderUserCnd fixTenderUserCnd);
	
	/**
	 * 统计定期宝认购总次数
	 * @param id
	 * @return
	 */
	public List<FixTenderUserVo> queryTotalTBByPage(FixTenderUserCnd fixTenderUserCnd,Page page);
	
}
