package com.dxjr.portal.curAccount.service;

import java.math.BigDecimal;
import java.util.Date;

import com.dxjr.portal.curAccount.entity.CurOut;
import com.dxjr.portal.curAccount.vo.CurOutVo;

/***
 * 
 * 
 * <p>
 * Description:活期宝转出记录 BLL 接口 <br />
 * </p>
 * 
 * @title CurOutService.java
 * @package com.dxjr.portal.curAccount.service
 * @author HuangJun
 * @version 0.1 2015年4月27日
 */
public interface CurOutService {
	/**
	 * 
	 * <p>
	 * Description:根据userId和date查询转出到普通可用余额的记录<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月5日
	 * @param userId
	 * @param date
	 * @return
	 * @throws Exception
	 *             Integer
	 */
	public Integer queryCountByUserIdAndDate(Integer userId, Date date) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:较验转出参数及可否可转出<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月5日
	 * @param userId
	 * @param curOutVo
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String validateCurOutParam(Integer userId, CurOutVo curOutVo) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:活期宝金额转出到普通可用余额<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月6日
	 * @param userId
	 * @param money
	 *            转出金额
	 * @param ip
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String saveCurOut(Integer userId, BigDecimal money, String ip) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:活期宝金额转出用于投资理财（投标、开通直通车、认购债权转让、认购直通车转让等）<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月7日
	 * @param userId
	 * @param money
	 *            转出金额
	 * @param ip
	 * @param type
	 *            转出类型
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String turnCurOutForInvest(Integer userId, BigDecimal money, String ip, Integer type) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:根据userId和type查询活期宝最新一条转出记录<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月7日
	 * @param type
	 * @param userId
	 * @param account
	 * @return
	 * @throws Exception
	 *             CurOut
	 */
	public CurOut queryCurOutLastByUserIdAndType(Integer type, Integer userId, BigDecimal account) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:更新转出记录<br />
	 * </p>
	 * 
	 * @author YangShiJin
	 * @version 0.1 2015年5月7日
	 * @param curOut
	 * @return int
	 */
	public int updateByPrimaryKeySelective(CurOut curOut);
}
