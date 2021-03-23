package com.dxjr.wx.entry.bind.mapper;

import org.apache.ibatis.annotations.Param;

import com.dxjr.wx.entry.bind.vo.BindVo;

public interface BindHistoryVoMapper {
	/**
	 * 保存绑定历史
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月29日
	 * @param record
	 * @return int
	 */
	int saveBindHistory(BindVo vo);

	/**
	 * 项目启动时保存自动解绑记录
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月30日
	 * @param time
	 *            void
	 */
	void saveubsubBind(@Param("updateTime") Long updateTime, @Param("exemmo") String exemmo, @Param("ids") String ids);
}