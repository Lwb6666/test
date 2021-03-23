package com.dxjr.portal.tzjinterface.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxjr.portal.statics.ApplicationConstants;
import com.dxjr.portal.tzjinterface.mapper.BorrowStateChangeMapper;
import com.dxjr.portal.tzjinterface.mapper.BorrowTransferLogMapper;
import com.dxjr.portal.tzjinterface.mapper.InvitationBorrowMapper;
import com.dxjr.portal.tzjinterface.mapper.TenderRecordTransferLogMapper;
import com.dxjr.portal.tzjinterface.service.TransferBorrowService;
import com.dxjr.portal.tzjinterface.util.HttpclientPost;
import com.dxjr.portal.tzjinterface.util.ParseURLTool;
import com.dxjr.portal.tzjinterface.vo.BorrowStateChangedVo;
import com.dxjr.portal.tzjinterface.vo.BorrowStateVo;
import com.dxjr.portal.tzjinterface.vo.BorrowTransferLogVo;
import com.dxjr.portal.tzjinterface.vo.InvitationBorrowVo;
import com.dxjr.portal.tzjinterface.vo.TenderRecordAggVo;
import com.dxjr.portal.tzjinterface.vo.TzjTenderRecordCnd;
import com.dxjr.portal.util.JsonUtils;
import com.dxjr.utils.DateUtils;
import com.dxjr.utils.exception.AppException;

@Service
public class TransferBorrowServiceImpl implements TransferBorrowService {

	@Autowired
	private InvitationBorrowMapper invitationBorrowMapper;
	@Autowired
	private BorrowTransferLogMapper borrowTransferLogMapper;
	@Autowired
	BorrowStateChangeMapper borrowStateChangeMapper;
	@Autowired
	TenderRecordTransferLogMapper tenderRecordTransferLogMapper;
	private Logger logger = Logger.getLogger(TransferBorrowServiceImpl.class);

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<InvitationBorrowVo> TransferBorrow() throws AppException {
		List<InvitationBorrowVo> borrowTransferLogList = queryBorrowList();
		// borrowTransferLogMapper.insertBorrowTransferLogVo();

		return borrowTransferLogList;
	}

	@Override
	public List<InvitationBorrowVo> queryBorrowList() throws AppException {
		return invitationBorrowMapper.queryBorrowList();
	}

	@Override
	public List<BorrowTransferLogVo> queryBorrowTransferLogs() throws AppException {
		return borrowTransferLogMapper.queryBorrowTransferLogs();
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<BorrowStateVo> queryStateChangeBorrowList() throws AppException {
		List<BorrowStateVo> bidinfo = null;
		try {
			bidinfo = borrowStateChangeMapper.queryStateChangeBorrowList();
		} catch (AppException e) {
			logger.error(e.getMessage());
		}
		if (null == bidinfo) {
			logger.info("没有状态变动的借款标！");
			logger.info("BorrowStateChangeTrigger  END:" + DateUtils.format(new Date(), DateUtils.YMD_HMS) + ".....");
			return null;
		}
		// 将结果集转换成JSON格式
		String ruselt = JsonUtils.bean2Json(bidinfo);
		System.err.println("JSON ========  " + ruselt);
		logger.debug("传输的json数据为：=========  " + ruselt);
		// 构造POST请求参数
		NameValuePair[] postData = constructPostDate(ruselt);

		// 提交POST请求
		String state = HttpclientPost.getHttpUrlPost(ApplicationConstants.TZJ_PINGTAI_SERVICE_POSTDATE_URL, postData);
		Map result = JsonUtils.json2Map(state);
		if (result != null) {
			if (Boolean.FALSE.toString().equals(result.get("success").toString())) {
				logger.error("发送失败，请注意！ 失败原因为：  " + result.get("msg"));
			} else {
				logger.debug("借款标状态变化数据传输成功！");
			}

		} else {
			logger.error("请求异常，返回参数为 null");
		}
		tenderRecordTransferLogMapper.updateTransferTenderRecordStatus();
		return null;
	}

	private NameValuePair[] constructPostDate(String ruselt) {

		Map<String, String> params = new TreeMap<String, String>();
		params.put("service", "bidchangereport");
		params.put("from", "gcjr");
		params.put("result", ruselt);

		// 构造加密的请求参数
		Map<String, String> resultParamMap = new TreeMap<String, String>();
		resultParamMap = ParseURLTool.buildSignParameter(params);

		// Construct the POST data for the parameters
		NameValuePair[] postData = { new NameValuePair("service", resultParamMap.get("service")), // REST
																									// 服务端口名称
				new NameValuePair("from", resultParamMap.get("from")), // POST请求来源
				new NameValuePair("result", resultParamMap.get("result")), // JSON格式的借款标状态变化数据
				new NameValuePair("sign", resultParamMap.get("sign")) // 数据指纹，即数据签名
		};
		return postData;
	}
	
	/**
	 * 根据查询条件获取投资记录
	 * @author WangQianJin
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年11月29日
	 */
	public List<TenderRecordAggVo> queryTenderRecord(TzjTenderRecordCnd tzjTenderRecordCnd) throws AppException{
		return invitationBorrowMapper.queryTenderRecord(tzjTenderRecordCnd);
	}
	
	/**
	 * 根据查询条件获取标的状态
	 * @author WangQianJin
	 * @title @param tzjTenderRecordCnd
	 * @title @return
	 * @title @throws AppException
	 * @date 2015年11月29日
	 */
	public List<BorrowStateChangedVo> queryStatusChangedBorrow(TzjTenderRecordCnd tzjTenderRecordCnd) throws AppException{
		return invitationBorrowMapper.queryStatusChangedBorrow(tzjTenderRecordCnd);
	}
}
