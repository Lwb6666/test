package com.dxjr.portal.curAccount.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.dxjr.common.page.Page;
import com.dxjr.portal.curAccount.entity.CurInterestDetail;
import com.dxjr.portal.curAccount.vo.CurAccountCnd;
import com.dxjr.portal.curAccount.vo.CurInterestDetailCnd;
import com.dxjr.portal.curAccount.vo.CurInterestDetailVo;

public interface CurInterestDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CurInterestDetail record);

    int insertSelective(CurInterestDetail record);

    CurInterestDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CurInterestDetail record);

    int updateByPrimaryKey(CurInterestDetail record);

    /**
     * <p>
     * Description:根据userId 查询收益明细count<br />
     * </p>
     * 
     * @author HuangJun
     * @version 0.1 2015年5月6日
     * @param curAccountCnd
     * @return Integer
     */
    public Integer queryInterestDetailCount(CurAccountCnd curAccountCnd);

    /**
     * <p>
     * Description:根据userId 查询收益明细List<br />
     * </p>
     * 
     * @author HuangJun
     * @version 0.1 2015年5月6日
     * @param curAccountCnd
     * @param page
     * @return List<CurInterestDetailVo>
     */
    public List<CurInterestDetailVo> queryInterestDetailList(CurAccountCnd curAccountCnd, Page page);

    /**
     * <p>
     * Description: 电子账单-当月活期累计收益 <br />
     * </p>
     * 
     * @author HuangJun
     * @version 0.1 2015年5月27日
     * @param curAccountCnd
     * @return CurInterestDetailVo
     */
    public CurInterestDetailVo querySumMonthMoneyByConn(CurInterestDetailCnd curInterestDetailCnd);

    /**
     * 
     * <p>
     * Description:根据userId查询最近N天的收益明细金额（用于折现图显示）<br />
     * </p>
     * 
     * @author yangshijin
     * @version 0.1 2016年1月20日
     * @param curAccountCnd
     * @return List<BigDecimal>
     */
    public List<BigDecimal> queryInterestMoneyList(CurAccountCnd curAccountCnd);

    /**
     * 
     * <p>
     * Description:根据userId查询最近N天的收益明细日期集合（用于折现图显示）<br />
     * </p>
     * 
     * @author yangshijin
     * @version 0.1 2016年1月20日
     * @param curAccountCnd
     * @return List<Date>
     */
    public List<Date> queryInterestDateList(CurAccountCnd curAccountCnd);
}