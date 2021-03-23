package com.dxjr.wx.entry.mainIn.controller;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxjr.portal.base.BaseController;
import com.dxjr.wx.common.WxConstants;
import com.dxjr.wx.entry.bind.service.BindService;
import com.dxjr.wx.entry.mainIn.service.MainInService;
import com.dxjr.wx.entry.mainIn.vo.HistoryMessage;
import com.dxjr.wx.entry.mainIn.vo.WxEvent;

/**
 * 与微信端关联主入口
 * <p>
 * Description:这里写描述<br />
 * </p>
 * @title MainInController.java
 * @package com.dxjr.wx.entry.mainIn.controller
 * @author Wang Bo
 * @version 0.1 2015年3月12日
 */
@Controller
@RequestMapping("/wx/mainIn")
public class MainInController extends BaseController {
	private static final Logger logger = Logger.getLogger(MainInController.class);
	@Autowired
	private MainInService mainInService;
	@Autowired
	private BindService bindService;

	/**
	 * <p>
	 * Description:微信主入口 用于菜单和回复<br />
	 * </p>
	 * @author Wang Bo
	 * @version 0.1 2014年10月24日
	 * @param request
	 * @param wxEvent
	 * @return TextMessage
	 */
	@RequestMapping("/mainIn")
	@ResponseBody
	public String mainIn(WxEvent wxEvent, HttpServletResponse response) {
		String message = null;
		HistoryMessage historyMessage = new HistoryMessage();
		Integer wId = wxEvent.getWxId();
		String msgType = wxEvent.getMsgType();
		historyMessage.setMsgType(wxEvent.getMsgType());
		historyMessage.setwId(wId);
		try {
			historyMessage.setCreateTime(new Date().getTime() / 1000);
			// 关键字回复1-6
			if (msgType.equals("text")) {
				String key = wxEvent.getContent();
				// System.out.println("mainIn-key--------------------------" + key);
				historyMessage.setContent(key);
				// if (WxConstants.VIP_KEY.equals(key)) {
				// message = mainInService.queryVip(wId);
				// } else
				if (WxConstants.MONEYDETAIL.equals(key)) {
					// message = mainInService.queryOption(wId);
					message = mainInService.queryMoneyDetail(wId);
				} else if (WxConstants.COLLECTION_DETAIL.equals(key)) {
					// message = mainInService.queryCollected(wId);
					message = mainInService.queryCollectionDetail(wId);
				} else if (WxConstants.Tender_DETAIL.equals(key)) {
					// message = mainInService.queryLeastinvest(wId);
					message = mainInService.queryTenderDetail(wId);
				} else if (WxConstants.NETVALUE_DETAIL.equals(key)) {
					message = mainInService.queryBorrowDetail(wId);
				} else if (WxConstants.PROMOT_KEY.equals(key)) {
					message = mainInService.queryPromot(wId);
				} else if (WxConstants.UNBIND_KEY.equals(key)) {
					try {
						message = mainInService.updateUnBind(wId);
					} catch (Exception e) {
						message = e.getMessage();
						logger.error("微信关键字解绑", e);
					}
				} else {
					message = mainInService.queryAutoMessage(key);
				}

			} else if (msgType.equalsIgnoreCase(WxConstants.REQ_MESSAGE_TYPE_EVENT)) {
				String eventType = wxEvent.getEventType();
				// click按钮回复
				if (eventType.equalsIgnoreCase(WxConstants.EVENT_TYPE_CLICK)) {
					String clickKey = wxEvent.getClickKey();
					historyMessage.setEventKey(clickKey);
					if (WxConstants.MONEYDETAIL.equals(clickKey)) {
						message = mainInService.queryMoneyDetail(wId);
					} else if (WxConstants.COLLECTION_DETAIL.equals(clickKey)) {
						message = mainInService.queryCollectionDetail(wId);
					} else if (WxConstants.Tender_DETAIL.equalsIgnoreCase(clickKey)) {
						message = mainInService.queryTenderDetail(wId);
					} else if (WxConstants.NETVALUE_DETAIL.equals(clickKey)) {
						message = mainInService.queryBorrowDetail(wId);
					} else {
						message = mainInService.queryMessageByClickKey(wId, clickKey);
					}
					// 地理位置
				} else if (eventType.equalsIgnoreCase(WxConstants.EVENT_TYPE_LOCATION)) {
					historyMessage.setEventKey(eventType);
					String latitude = wxEvent.getLatitude();
					String longitude = wxEvent.getLongitude();
					String precision = wxEvent.getPrecision();
					historyMessage.setLatitude(latitude);
					historyMessage.setLongitude(longitude);
					historyMessage.setPrecision(precision);
				}
			}
			// 将消息插入历史
			mainInService.insertHistoryMessage(historyMessage);
		} catch (Exception e) {
			logger.error("微信主入口异常", e);
		}
		return message;
	}
}
