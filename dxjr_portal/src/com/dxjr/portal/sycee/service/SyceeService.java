package com.dxjr.portal.sycee.service;

import java.util.List;

import com.dxjr.portal.base.MessageBox;
import com.dxjr.portal.sycee.entity.SyceeAddress;
import com.dxjr.portal.sycee.entity.SyceeGoods;

public interface SyceeService {

	/**
	 * 元宝商品分类列表
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年10月23日
	 * @param firstClass
	 * @return List<SyceeGoods>
	 */
	List<SyceeGoods> syceeClassList(int firstClass);

	/**
	 * 查询论坛签到帖，没有返回0
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年10月23日
	 * @return int
	 */
	int getSignItem();

	/**
	 * 商品详情
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年10月24日
	 * @param goodsId
	 * @return SyceeGoods
	 */
	SyceeGoods getGoodsInfo(int goodsId);

	/**
	 * 查询会员当前元宝
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年10月26日
	 * @param userId
	 * @return int
	 */
	int getUserSycee(int userId);

	/**
	 * 兑换商品
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author huangpin
	 * @version 0.1 2015年10月26日
	 * @param userId
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	MessageBox addExchange(int userId, int goodsId, String ip, int num) throws Exception;

	/**
	 * 获取打折标记：1打折 0不打折
	 * 
	 * @return
	 */
	int getSyceeDiscountFlag();

	int getExchangeTimes(int userId);
	
	/**
	 * 获取用户联系方式
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author yuanhaiyang
	 * @version 0.1 2016年4月6日
	 * @param userId
	 * @return SyceeAddress
	 */
	SyceeAddress getById (int user_id);
	/**
	 * 新增用户联系方式
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author yuanhaiyang
	 * @version 0.1 2016年4月6日
	 * @param SyceeAddress
	 * @return MessageBox
	 * @throws Exception
	 */
	MessageBox addAddress(SyceeAddress address ) throws Exception;
	
	MessageBox updateAddr(SyceeAddress address ,int user_id) throws Exception;
}
