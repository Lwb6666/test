package com.dxjr.portal.member.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.base.entity.Configuration;
import com.dxjr.base.entity.Integral;
import com.dxjr.base.mapper.BaseIntegralMapper;
import com.dxjr.common.Dictionary;
import com.dxjr.common.statics.Constants;
import com.dxjr.portal.member.mapper.IntegralMapper;
import com.dxjr.portal.member.service.IntegralService;
import com.dxjr.portal.member.vo.IntegralCnd;
import com.dxjr.portal.member.vo.IntegralVo;

/**
 * <p>
 * Description:积分等级接口的实现类<br />
 * </p>
 * 
 * @title IntegralServiceImpl.java
 * @package com.dxjr.integral.service.impl
 * @author justin.xu
 * @version 0.1 2014年4月16日
 */
@Service
public class IntegralServiceImpl implements IntegralService {

	@Autowired
	private BaseIntegralMapper baseIntegralMapper;
	@Autowired
	private IntegralMapper integralMapper;

	@Override
	public Integer insertIntegral(Integer userId) throws Exception {
		Integral integral = new Integral();
		integral.setCreditIntegral(1);
		integral.setInvestIntegral(1);
		integral.setCreditLevel("HR");
		integral.setInvestLevel("实习生");
		integral.setUserId(userId);
		integral.setIntegral(0);
		baseIntegralMapper.insertEntity(integral);
		return integral.getId();
	}

	@Override
	public IntegralVo queryIntegralByUserId(Integer memberId) throws Exception {
		IntegralCnd integralCnd = new IntegralCnd();
		integralCnd.setUserId(memberId);
		List<IntegralVo> list = integralMapper.queryIntegralList(integralCnd);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void updateCreditLevel(IntegralVo integralVo) throws Exception {
		Collection<Configuration> investList = Dictionary
				.getValues(Constants.CONFIGURATION_TYPE_INVEST_LEVEL); // 投资等级
		Collection<Configuration> creditList = Dictionary
				.getValues(Constants.CONFIGURATION_TYPE_CREDIT_LEVEL); // 信用等级

		// 设置投资等级
		for (Configuration con : investList) {
			long begin = Long.parseLong(con.getValue().split("-")[0]);
			long end = Long.parseLong(con.getValue().split("-")[1]);
			if (integralVo.getInvestIntegral() < end
					&& integralVo.getInvestIntegral() > begin) {
				integralVo.setInvestLevel(con.getName());
			}
		}
		// 设置信用等级
		for (Configuration con : creditList) {
			long begin = Long.parseLong(con.getValue().split("-")[0]);
			long end = Long.parseLong(con.getValue().split("-")[1]);
			if (integralVo.getCreditIntegral() < end
					&& integralVo.getCreditIntegral() > begin) {
				integralVo.setCreditLevel(con.getName());
			}
		}
		Integral integral = new Integral();
		BeanUtils.copyProperties(integralVo, integral);
		baseIntegralMapper.updateEntity(integral);
	}

}
