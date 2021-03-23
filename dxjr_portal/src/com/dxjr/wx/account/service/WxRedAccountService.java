package com.dxjr.wx.account.service;

import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.wx.account.vo.WxRedAccountVo;

/**
 * 红包业务类
 * <p>
 * Description:红包业务类<br />
 * </p>
 * @title RedAccountService.java
 * @package com.dxjr.wx.account.service
 * @author wushaoling
 * @version 0.1 2016年5月18日
 */
public interface WxRedAccountService {
	
   /**
    * 查询可使用红包
    * <p>
    * Description:根据用户Id查询可使用红包<br />
    * </p>
    * @author wushaoling
    * @date 2016年5月18日
    * @param userId
    * @return list 红包list 
    * @throws Exception
    * 
    */
	public List<WxRedAccountVo> queryOpenRed(int userId) throws Exception;
	
	/**
    * 查询不可使用红包
    * <p>
    * Description:根据用户ID查询不可使用红包<br />
    * </p>
    * @author wushaoling
    * @date 2016年5月18日
    * @param userId
    * @return list 不可使用红包list 
    * @throws Exception
	*/
	public List<WxRedAccountVo> queryByExpiredRed(int userId) throws Exception;
	
		/**
	    * 查询可使用红包数量
	    * <p>
	    * Description:查询可使用红包数量<br />
	    * </p>
	    * @author wushaoling
	    * @date 2016年5月18日
	    * @param userId
	    * @return int 可使用红包数量
	    * @throws Exception
		*/
		public int queryOpenRedCount(Integer userId) throws Exception;

		/**
		 * 统计可用红包总数
		 * @param id
		 * @return
		 */
		public Page queryOpenRedByPage(WxRedAccountVo wxRedAccountVo,
				Page page);
		
		/**
		 * 统计不可用红包总数
		 * @param id
		 * @return
		 */
		public Page queryCloseRedByPage(WxRedAccountVo wxRedAccountVo,
				Page page);
		
		
}
