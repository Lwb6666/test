package com.dxjr.portal.account.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dxjr.common.page.Page;
import com.dxjr.portal.account.vo.TurnDrawLogVO;

/**
 * <p>
 * Description:账户资金记录查询<br />
 * </p>
 * 
 * @title AccOperatingRecordMapper.java
 * @package com.dxjr.portal.account.mapper
 * @author 胡建盼
 * @version 0.1 2014年8月5日
 */
public interface DrawLogRecordMapper {
	public Integer countDrawLogRecord(Map<String, Object> params);
	public List<TurnDrawLogVO> queryDrawLogRecordList(Map<String, Object> params,Page page);
	public List<TurnDrawLogVO> queryOneMonthDrawLogRecordList(
			Map<String, Object> params);
}
