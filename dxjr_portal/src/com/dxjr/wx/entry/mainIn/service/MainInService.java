package com.dxjr.wx.entry.mainIn.service;

import com.dxjr.wx.entry.mainIn.vo.HistoryMessage;

public interface MainInService {
	/**
	 * 查询vip详情
	 * <p>
	 * Description: 查询vip详情<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月25日
	 * @param openId
	 * @return
	 * @throws Exception
	 *             String
	 */
	String queryVip(Integer wId) throws Exception;

	/**
	 * 查询期权详情
	 * <p>
	 * Description:查询期权详情<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月25日
	 * @param openId
	 * @return
	 * @throws Exception
	 *             String
	 */
	String queryOption(Integer wId) throws Exception;

	/**
	 * 查询加权待收详情
	 * <p>
	 * Description:查询加权待收详情<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月25日
	 * @param openId
	 * @return
	 * @throws Exception
	 *             String
	 */
	String queryCollected(Integer wId) throws Exception;

	/**
	 * 期权最少投资额
	 * <p>
	 * Description:期权最少投资额<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月25日
	 * @param openId
	 * @return
	 * @throws Exception
	 *             String
	 */
	String queryLeastinvest(Integer wId) throws Exception;

	/**
	 * 回复关键字自动解绑
	 * <p>
	 * Description:br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月25日
	 * @param openId
	 * @return
	 * @throws Exception
	 *             String
	 */
	String updateUnBind(Integer wId) throws Exception;

	/**
	 * 查询推荐人
	 * <p>
	 * Description:查询推荐人<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月25日
	 * @param openId
	 * @return
	 * @throws Exception
	 *             String
	 */
	String queryPromot(Integer wId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询账户详情<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月25日
	 * @param openId
	 * @return
	 * @throws Exception
	 *             String
	 */
	String queryMoneyDetail(Integer wId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询收益详情<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月25日
	 * @param openId
	 * @return
	 * @throws Exception
	 *             String
	 */
	String queryCollectionDetail(Integer wId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:借款详情<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月25日
	 * @param openId
	 * @return
	 * @throws Exception
	 *             String
	 */
	String queryBorrowDetail(Integer wId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:查询投标详情<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月25日
	 * @param openId
	 * @return
	 * @throws Exception
	 *             String
	 */
	String queryTenderDetail(Integer wId) throws Exception;

	/**
	 * 
	 * <p>
	 * Description:click事件被动直接推送<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月25日
	 * @param openId
	 * @param key
	 * @return String
	 */
	String queryMessageByClickKey(Integer wId, String key);

	/**
	 * 
	 * <p>
	 * Description:绑定click事件推送<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月25日
	 * @param openId
	 * @return String
	 */
	String queryBindMessage(Integer wId);

	/**
	 * 保存历史记录
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年10月27日
	 * @param message
	 * @return int
	 */
	int insertHistoryMessage(HistoryMessage message);

	/**
	 * 非业务类自动回复
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年11月4日
	 * @param key
	 * @return String
	 */
	String queryAutoMessage(String key);

}
