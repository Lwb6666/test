package com.dxjr.portal.curAccount.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.curAccount.vo.CurAccountCnd;
import com.dxjr.portal.curAccount.vo.CurInterestDetailCnd;
import com.dxjr.portal.curAccount.vo.CurInterestDetailVo;

/***
 * 
 * 
 * <p>
 * Description: 收益发放日志 BLL 接口 <br />
 * </p>
 * 
 * @title CurInterestDetailService.java
 * @package com.dxjr.portal.curAccount.service
 * @author HuangJun
 * @version 0.1 2015年4月27日
 */
public interface CurInterestDetailService {

    /**
     * <p>
     * Description:query 资金明细 - 分页<br />
     * </p>
     * 
     * @author HuangJun
     * @version 0.1 2015年5月4日
     * @param mapParam
     * @param page
     * @return
     * @throws Exception
     *             Page
     */
    public Page queryCurInterestDetailByPage(CurAccountCnd curAccountCnd, Page page) throws Exception;

    /**
     * <p>
     * Description: 电子账单-当月活期累计收益 <br />
     * </p>
     * 
     * @author HuangJun
     * @version 0.1 2015年5月27日
     * @param curAccountCnd
     * @return
     * @throws Exception
     *             CurInterestDetailVo
     */
    public CurInterestDetailVo querySumMonthMoneyByConn(CurInterestDetailCnd curInterestDetailCnd) throws Exception;

    /**
     * 
     * <p>
     * Description:根据userId查询最近N天的收益明细金额（用于折现图显示）<br />
     * </p>
     * 
     * @author yangshijin
     * @version 0.1 2016年1月20日
     * @param curAccountCnd
     * @param page
     * @return List<BigDecimal>
     */
    public List<BigDecimal> queryInterestMoneyListByUserId(CurAccountCnd curAccountCnd, Page page);

    /**
     * 
     * <p>
     * Description:根据userId查询最近N天的收益明细日期集合（用于折现图显示）<br />
     * </p>
     * 
     * @author yangshijin
     * @version 0.1 2016年1月20日
     * @param curAccountCnd
     * @param page
     * @return List<Date>
     */
    public List<Date> queryDayInterestDateListByUserId(CurAccountCnd curAccountCnd, Page page);

}
