package com.dxjr.portal.curAccount.service.impl;

import com.dxjr.common.page.Page;
import com.dxjr.portal.curAccount.mapper.CurInterestDetailMapper;
import com.dxjr.portal.curAccount.service.CurInterestDetailService;
import com.dxjr.portal.curAccount.vo.CurAccountCnd;
import com.dxjr.portal.curAccount.vo.CurInterestDetailCnd;
import com.dxjr.portal.curAccount.vo.CurInterestDetailVo;
import com.dxjr.utils.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/****
 * 
 * 
 * <p>
 * Description: 收益发放日志 BLL <br />
 * </p>
 * 
 * @title CurInterestDetailServiceImpl.java
 * @package com.dxjr.portal.curAccount.service.impl
 * @author HuangJun
 * @version 0.1 2015年4月27日
 */
@Service
public class CurInterestDetailServiceImpl implements CurInterestDetailService {

    Logger logger = LoggerFactory.getLogger(CurInterestDetailServiceImpl.class);

    @Autowired
    private CurInterestDetailMapper curInterestDetailMapper;

    /**
     * 根据userId 查询收益明细 - 分页 (non-Javadoc)
     * 
     * @see com.dxjr.portal.curAccount.service.CurInterestDetailService#queryCurInterestDetailByPage(java.util.Map,
     *      com.dxjr.common.page.Page)
     */
    public Page queryCurInterestDetailByPage(CurAccountCnd curAccountCnd, Page page) throws Exception {
        int totalCount = 0;
        totalCount = curInterestDetailMapper.queryInterestDetailCount(curAccountCnd);
        page.setTotalCount(totalCount);
        List<CurInterestDetailVo> retLst = curInterestDetailMapper.queryInterestDetailList(curAccountCnd, page);
        if (retLst != null && retLst.size() > 0) {
            page.setResult(retLst);
        }
        return page;
    }

    /**
     * 电子账单-当月活期累计收益 (non-Javadoc)
     * 
     * @see com.dxjr.portal.curAccount.service.CurInterestDetailService#querySumMonthMoneyByConn(com.dxjr.portal.curAccount.vo.CurAccountCnd)
     */
    public CurInterestDetailVo querySumMonthMoneyByConn(CurInterestDetailCnd curInterestDetailCnd) throws Exception {

        CurInterestDetailVo retCurInterestDetailVo = new CurInterestDetailVo();
        try {
            retCurInterestDetailVo = curInterestDetailMapper.querySumMonthMoneyByConn(curInterestDetailCnd);
        } catch (Exception e) {
            logger.error("电子账单-当月活期累计收益异常", e);
        }
        return retCurInterestDetailVo;
    }

    @Override
    public List<BigDecimal> queryInterestMoneyListByUserId(CurAccountCnd curAccountCnd, Page page) {
        return curInterestDetailMapper.queryInterestMoneyList(curAccountCnd);
    }

    @Override
    public List<Date> queryDayInterestDateListByUserId(CurAccountCnd curAccountCnd, Page page) {
        List<Date> list = curInterestDetailMapper.queryInterestDateList(curAccountCnd);
        List<Date> resultList = new ArrayList<Date>();
        for (int i = 0; i < list.size(); i++) {
            resultList.add(DateUtils.dayOffset(list.get(i), 1));
        }
        return resultList;
    }

}
