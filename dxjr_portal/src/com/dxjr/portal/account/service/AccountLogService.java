package com.dxjr.portal.account.service;

import java.math.BigDecimal;

import com.dxjr.base.entity.AccountLog;
import com.dxjr.portal.account.vo.AccountLogCnd;
import com.dxjr.portal.account.vo.AccountVo;

/**
 * <p>
 * Description:资金操作日志业务类<br />
 * </p>
 * 
 * @title AccountLogService.java
 * @package com.dxjr.portal.account.service
 * @author justin.xu
 * @version 0.1 2014年4月25日
 */
public interface AccountLogService {
	/**
	 * <p>
	 * Description:新增资金操作日志，新增,返回 主键<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月16日
	 * @param userId
	 * @return
	 * @throws Exception String
	 */
	public void insertAccountLog(AccountLog accountLog) throws Exception;

	/**
	 * <p>
	 * Description:根据条件查询对象集合数量<br />
	 * </p>
	 * 
	 * @author justin.xu
	 * @version 0.1 2014年4月23日
	 * @param AccountLogCnd accountLogCnd
	 * @return
	 * @throws Exception Integer
	 */
	public Integer queryAccountLogCount(AccountLogCnd accountLogCnd) throws Exception;

    /**
	 * <p>
	 * Description:根据参数集合插入资金日志<br />
	 * </p>
	 *
	 * @author justin.xu
	 * @version 0.1 2014年7月2日
	 * @param accountVo 记录的账户信息
	 * @param userId 用户
	 * @param money 交易金额
	 * @param remark 日志备注信息
	 * @param addIp 操作IP
	 * @param type 操作类型
	 * @param idType 0:借款标ID, 1:直通车id, 2:待还记录id
	 * @param borrowId 类型id
	 * @param borrowName 类型名字
     * @param isCustody 是否存管
	 * @return
	 * @throws Exception Integer
	 */
    void saveAccountLogByParams(AccountVo accountVo, Integer userId, BigDecimal money,
                                String remark, String addIp, String type, Integer idType,
                                Integer borrowId, String borrowName, Integer isCustody)
            throws Exception;


    /**
	 * <p>
	 * Description:根据参数集合插入资金日志<br />
	 * </p>
	 *
	 * @author justin.xu
	 * @version 0.1 2014年7月2日
	 * @param accountVo 记录的账户信息
	 * @param userId 用户
	 * @param money 交易金额
	 * @param remark 日志备注信息
	 * @param addIp 操作IP
	 * @param type 操作类型
	 * @param idType 0:借款标ID, 1:直通车id, 2:待还记录id
	 * @param borrowId 类型id
	 * @param borrowName 类型名字
	 * @return
	 * @throws Exception Integer
	 */
    void saveAccountLogByParams(AccountVo accountVo, Integer userId, BigDecimal money,
                                String remark, String addIp, String type, Integer idType,
                                Integer borrowId, String borrowName)
            throws Exception;
}
