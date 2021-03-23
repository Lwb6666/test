package com.dxjr.portal.autoInvestConfig.service;

import com.dxjr.common.page.Page;
import com.dxjr.portal.autoInvestConfig.entity.FixAutoInvest;
import com.dxjr.security.ShiroUser;

/**
 * 
 * <p>
 * Description:自动投资-设置定期宝<br />
 * </p>
 * @title FixAutoInvestService.java
 * @package com.dxjr.portal.autoInvestConfig.service 
 * @author yubin
 * @version 0.1 2015年11月20日
 */
public interface FixAutoInvestService {
	public String initSetting(int userId,int autoId);
	
	/**
	 * 
	 * <p>
	 * Description:添加自动投宝<br />
	 * </p>
	 * @author yubin
	 * @version 0.1 2015年11月20日
	 * @param record
	 * @return
	 * int
	 */
    String insert(FixAutoInvest record,ShiroUser shrioUser,String ip)throws Exception;
    /**
     * 
     * <p>
     * Description:修改自动投宝<br />
     * </p>
     * @author yubin
     * @version 0.1 2015年11月20日
     * @param record
     * @return
     * int
     */
    String updateByPrimaryKeySelective(FixAutoInvest record,int record_type,ShiroUser shrioUser,String ip)throws Exception;
    /**
     * 
     * <p>
     * Description:查看定期宝<br />
     * </p>
     * @author yubin
     * @version 0.1 2015年11月20日
     * @param id
     * @return
     * FixAutoInvest
     */
    FixAutoInvest selectByPrimaryKey(Integer id)throws Exception;
    
    /**
     * 我的设置
     * <p>
     * Description:查询用户未删除的投宝设置<br />
     * </p>
     * @author yubin
     * @version 0.1 2015年11月23日
     * @param userId
     * @return
     * @throws Exception
     * FixAutoInvest
     */
    FixAutoInvest selectByUserId(Integer userId)throws Exception;

    /**
     * 
     * <p>
     * Description:获取排队时间<br />
     * </p>
     * @author yubin
     * @version 0.1 2015年11月23日
     * @param userId
     * @return
     * @throws Exception
     * String
     */
    public String getUptime(Integer userId )throws Exception;
    /**
     * 
     * <p>
     * Description:查询投标记录<br />
     * </p>
     * @author yubin
     * @version 0.1 2015年11月23日
     * @param userId
     * @param curPage
     * @param pageSize
     * @return
     * @throws Exception
     * Page
     */
    public Page queryListByAutoInvestConfigRecordCnd(Integer userId, int curPage, int pageSize) throws Exception;
}
