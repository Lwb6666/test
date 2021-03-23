/**   
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title NewPService.java
 * @package com.dxjr.portal.newP.service.impl 
 * @author HuangJun
 * @version 0.1 2015年4月9日
 */
package com.dxjr.portal.newP.service;

import com.dxjr.portal.borrow.vo.BorrowVo;

/**
 * <p>
 * Description:新手专享标接口<br />
 * </p>
 * 
 * @title NewPService.java
 * @package com.dxjr.portal.newP.service.impl
 * @author HuangJun
 * @version 0.1 2015年4月9日
 */
public interface NewPService {

	/**
	 * <p>
	 * Description:实名认证<br />
	 * </p>
	 * 
	 * @author HuangJun
	 * @version 0.1 2015年4月9日
	 * @param userId
	 * @return
	 * @throws Exception
	 *             int
	 */
	public int validRealName(Integer userId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:新手专享标<br />
	 * </p>
	 * @author YangShiJin
	 * @version 0.1 2015年4月18日
	 * @return
	 * @throws Exception
	 * BorrowVo
	 */
	public BorrowVo getAdvancedNew() throws Exception;

}
