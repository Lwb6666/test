package com.dxjr.portal.autoInvestConfig.service;

import com.dxjr.base.entity.AutoInvestConfig;
import com.dxjr.base.entity.AutoInvestConfigRecord;
import com.dxjr.common.page.Page;
import com.dxjr.portal.autoInvestConfig.vo.AutoInvestConfigRecordCnd;
import com.dxjr.portal.autoInvestConfig.vo.AutoInvestConfigRecordVo;
import com.dxjr.portal.autoInvestConfig.vo.AutoInvestConfigVo;


/**
 * 
 * <p>
 * Description:自动投标规则日志业务处理接口.<br />
 * </p>
 * @title AutoInvestConfigRecordService.java
 * @package com.dxjr.portal.autoInvestConfig.service 
 * @author yangshijin
 * @version 0.1 2014年5月20日
 */
public interface AutoInvestConfigRecordService {
	/**
	 * 
	 * <p>
	 * Description:插入记录到自动投标规则日志表,返回新增的主键ID<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年5月19日
	 * @param autoInvestConfigRecord
	 * @return
	 * @throws Exception
	 * int
	 */
	public int insertAutoInvestConfigRecord(AutoInvestConfigRecord autoInvestConfigRecord) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据id查询自动投标规则日志类<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年5月19日
	 * @param id
	 * @return
	 * @throws Exception
	 * AutoInvestConfig
	 */
	public AutoInvestConfigRecord queryById(Integer id) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:更新自动投标规则日志类<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年5月19日
	 * @param autoInvestConfigRecord
	 * @return
	 * @throws Exception
	 * int
	 */
	public int updateAutoInvestConfigRecord(AutoInvestConfigRecord autoInvestConfigRecord) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:初始化自动投标规则日志信息.<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年5月20日
	 * @param autoInvestConfig
	 * @return
	 * AutoInvestConfigRecord
	 */
	public AutoInvestConfigRecord setAutoInvestConfigRecord(AutoInvestConfigVo autoInvestConfigVo);
	
	/**
	 * 
	 * <p>
	 * Description:初始化自动投标规则日志信息.<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年5月20日
	 * @param autoInvestConfig
	 * @return
	 * AutoInvestConfigRecord
	 */
	public AutoInvestConfigRecord setAutoInvestConfigRecord(AutoInvestConfig autoInvestConfig);
	
	/**
	 * 
	 * <p>
	 * Description:根据条件查询自动投标规则日志数量<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年5月20日
	 * @param autoInvestConfigRecordCnd
	 * @return
	 * @throws Exception
	 * int
	 */
	public int queryAutoInvestConfigRecordCount(AutoInvestConfigRecordCnd autoInvestConfigRecordCnd) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:根据类型查询记录 并分页<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年5月20日
	 * @param autoInvestConfigRecordCnd
	 * @param curPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 * Page
	 */
	public Page queryListByAutoInvestConfigRecordCnd(AutoInvestConfigRecordCnd autoInvestConfigRecordCnd, int curPage, int pageSize) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:根据id查询自动投标规则日志类<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年5月28日
	 * @param id
	 * @return
	 * @throws Exception
	 * AutoInvestConfigRecordVo
	 */
	public AutoInvestConfigRecordVo queryForVoById(Integer id) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Description:查询最后一笔自动投标的投标规则日志信息<br />
	 * </p>
	 * @author yangshijin
	 * @version 0.1 2014年6月18日
	 * @param borrow_id
	 * @return
	 * AutoInvestConfigRecordVo
	 */
	public AutoInvestConfigRecordVo queryLastRecordByBorrowId(int borrow_id) throws Exception;
}
