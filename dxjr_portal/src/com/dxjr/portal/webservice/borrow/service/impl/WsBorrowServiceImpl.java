package com.dxjr.portal.webservice.borrow.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxjr.portal.borrow.service.BorrowService;
import com.dxjr.portal.util.WebServiceMD5;
import com.dxjr.portal.webservice.borrow.service.WsBorrowService;
import com.dxjr.utils.exception.AppException;

/**
 * <p>
 * Description:借款标业务类实现WebService<br />
 * </p>
 * 
 * @title WsBorrowServiceImpl.java
 * @package com.dxjr.portal.webservice.borrow.impl
 * @author justin.xu
 * @version 0.1 2014年8月13日
 */
@Service
@WebService
public class WsBorrowServiceImpl implements WsBorrowService {

	@Autowired
	private BorrowService borrowService;

	@Override
	public String saveOperatingPenalty(Integer repaymentid, String addip, String serviceKey) throws Exception {
		// 验证数据是否正确
		Map<String, Object> validateKeyMap = new HashMap<>();
		validateKeyMap.put("repaymentid", repaymentid);
		validateKeyMap.put("addip", addip);
		if (!WebServiceMD5.validateServiceKey(validateKeyMap, serviceKey)) {
			return "数据验签出错";
		}
		return borrowService.saveOperatingPenalty(repaymentid, addip);
	}

	@Override
	public String saveApproveBorrowFirst(int borrowId, int flag, int userId, String remark, String creditRating, String serviceKey) throws Exception {
		// 验证数据是否正确
		Map<String, Object> validateKeyMap = new HashMap<>();
		validateKeyMap.put("borrowId", borrowId);
		validateKeyMap.put("flag", flag);
		validateKeyMap.put("userId", userId);
		validateKeyMap.put("remark", remark);
		validateKeyMap.put("creditRating", creditRating);
		if (!WebServiceMD5.validateServiceKey(validateKeyMap, serviceKey)) {
			return "数据验签出错";
		}
		return borrowService.saveApproveBorrowFirst(borrowId, flag, userId, remark, creditRating);
	}

	@Override
	public String saveApproveBorrowAntiFraud(int borrowId, int flag, int userId, String remark, String serviceKey) throws Exception {
		// 验证数据是否正确
		Map<String, Object> validateKeyMap = new HashMap<>();
		validateKeyMap.put("borrowId", borrowId);
		validateKeyMap.put("flag", flag);
		validateKeyMap.put("userId", userId);
		validateKeyMap.put("remark", remark);
		if (!WebServiceMD5.validateServiceKey(validateKeyMap, serviceKey)) {
			return "数据验签出错";
		}
		return borrowService.saveApproveBorrowAntiFraud(borrowId, flag, userId, remark);
	}

	@Override
	public String saveApproveBorrowFinal(Integer borrowId, Integer flag, Integer userId, String remark, String addip, String serviceKey) throws AppException, Exception {
		// 验证数据是否正确
		Map<String, Object> validateKeyMap = new HashMap<>();
		validateKeyMap.put("borrowId", borrowId);
		validateKeyMap.put("flag", flag);
		validateKeyMap.put("userId", userId);
		validateKeyMap.put("remark", remark);
		validateKeyMap.put("addip", addip);
		if (!WebServiceMD5.validateServiceKey(validateKeyMap, serviceKey)) {
			return "数据验签出错";
		}
		return borrowService.saveApproveBorrowFinal(borrowId, flag, userId, remark, addip);
	}

	@Override
	public String saveApproveBorrowReCheck(Integer borrowid, Integer checkUserId, String checkRemark, String addip, String serviceKey) throws Exception {
		// 验证数据是否正确
		Map<String, Object> validateKeyMap = new HashMap<>();
		validateKeyMap.put("borrowid", borrowid);
		validateKeyMap.put("checkUserId", checkUserId);
		validateKeyMap.put("checkRemark", checkRemark);
		validateKeyMap.put("addip", addip);
		if (!WebServiceMD5.validateServiceKey(validateKeyMap, serviceKey)) {
			return "数据验签出错";
		}
		return borrowService.saveApproveBorrowReCheck(borrowid, checkUserId, checkRemark, addip);
	}
}
