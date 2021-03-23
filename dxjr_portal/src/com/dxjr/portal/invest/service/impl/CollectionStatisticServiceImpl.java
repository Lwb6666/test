package com.dxjr.portal.invest.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.invest.mapper.CollectionStatisticMapper;
import com.dxjr.portal.invest.service.CollectionStatisticService;
import com.dxjr.portal.invest.vo.CollectionStatisticCnd;
import com.dxjr.portal.invest.vo.CollectionStatisticVo;

@Service
public class CollectionStatisticServiceImpl implements CollectionStatisticService {
	@Autowired
	private CollectionStatisticMapper collectionStatisticMapper;

	@Override
	public CollectionStatisticVo countCollectionMoney(Map<String, Object> parameter) {

		return collectionStatisticMapper.countCollectionMoney(parameter);
	}

	/**
	 * <p>
	 * Description:待收利息 总和 <br />
	 * </p>
	 * 
	 * @author HuangJun
	 * @version 0.1 2015年5月7日
	 * @param collectionStatisticCnd
	 * @return
	 * @throws Exception
	 *             CollectionRepayInfoVo
	 */
	public CollectionStatisticVo myAccountCollectionMoneyDS(CollectionStatisticCnd collectionStatisticCnd) throws Exception {

		CollectionStatisticVo collectionStatisticVo = new CollectionStatisticVo();

		try {
			collectionStatisticVo = collectionStatisticMapper.myAccountCollectionMoneyDS(collectionStatisticCnd);

		} catch (Exception e) {
		}
		return collectionStatisticVo;

	}

	/**
	 * <p>
	 * Description: 已收利息 总和 <br />
	 * </p>
	 * 
	 * @author HuangJun
	 * @version 0.1 2015年5月7日
	 * @param collectionStatisticCnd
	 * @return
	 * @throws Exception
	 *             CollectionRepayInfoVo
	 */
	public CollectionStatisticVo myAccountCollectionMoneyYS(CollectionStatisticCnd collectionStatisticCnd) throws Exception {

		CollectionStatisticVo collectionStatisticVo = new CollectionStatisticVo();

		try {

			collectionStatisticVo = collectionStatisticMapper.myAccountCollectionMoneyYS(collectionStatisticCnd);

		} catch (Exception e) {
		}
		return collectionStatisticVo;

	}

}
