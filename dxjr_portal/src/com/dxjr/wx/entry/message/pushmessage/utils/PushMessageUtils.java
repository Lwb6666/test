package com.dxjr.wx.entry.message.pushmessage.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dxjr.portal.util.JsonUtils;
import com.dxjr.wx.common.WxConstants;
import com.dxjr.wx.entry.message.pushmessage.vo.AutoMessage;
import com.dxjr.wx.entry.message.pushmessage.vo.PushMessage;

/**
 * 主动推送工具类
 * <p>
 * Description:这里写描述<br />
 * </p>
 * 
 * @title PushMessageUtils.java
 * @package com.dxjr.wx.entry.message.pushmessage.utils
 * @author Wang Bo
 * @version 0.1 2014年10月25日
 */
public class PushMessageUtils {
	/**
	 * <p>
	 * Description:绑定通知<br />
	 * </p>
	 * 
	 * @author Wang Bo
	 * @version 0.1 2014年9月11日
	 * @param httpclient
	 * @param userId
	 *            用户id
	 * @param wxId
	 *            微信id
	 * @param username
	 *            帐户名
	 * @param d
	 *            操作日期
	 * @param url
	 * @param head
	 *            提示消息1 (用于头部显示)
	 * @param color
	 *            颜色 eg:#C123D8
	 * @param remark
	 *            备注
	 * @return JSONObject 通知结果 json格式
	 * @throws Exception
	 */
	public static AutoMessage pushBindMessage(Integer userId, Integer wxId, String username, Date d, String url, String head, String remark) throws Exception {
		PushMessage pushMessage = new PushMessage(WxConstants.PUSH_BIND_TEMPLET, url, WxConstants.YELLOW_COLOR, null);
		SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s1 = sp.format(d);
		Map<String, Map<String, String>> m = new HashMap<String, Map<String, String>>();
		Map<String, String> m1 = new HashMap<>();
		m1.put("color", WxConstants.YELLOW_COLOR);
		m1.put("value", head);
		m.put("first", m1);
		m1 = new HashMap<>();
		m1.put("color", WxConstants.YELLOW_COLOR);
		m1.put("value", username);
		m.put("name1", m1);

		m1 = new HashMap<>();
		m1.put("value", "微信");
		m1.put("color", WxConstants.YELLOW_COLOR);
		m.put("name2", m1);

		m1 = new HashMap<>();
		m1.put("value", s1);
		m1.put("color", WxConstants.YELLOW_COLOR);
		m.put("time", m1);
		m1 = new HashMap<>();
		m1.put("value", remark);
		m1.put("color", WxConstants.YELLOW_COLOR);
		m.put("remark", m1);
		pushMessage.setData(m);
		String j = JsonUtils.bean2Json(pushMessage);
		AutoMessage autoMessage = new AutoMessage();
		autoMessage.setMessage(j);
		autoMessage.setWxId(wxId);
		autoMessage.setUserId(userId);
		autoMessage.setType(3);
		return autoMessage;
	}
}
