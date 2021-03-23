package com.dxjr.wx.pay.service;

import java.util.HashMap;
import java.util.Map;

import com.dxjr.wx.pay.util.DES;
import com.dxjr.wx.pay.util.FTL;
import com.dxjr.wx.pay.util.MD5;
import com.dxjr.wx.pay.util.OnlineContext;
import com.dxjr.wx.pay.util.XmlMsg;
import com.dxjr.wx.pay.vo.OnlineCardTrade;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class MotoPayService {
	
	private MotoPayProvider provider = new MotoPayProvider();
	
	/**
	 * 发起交易请求
	 * @param tradeType
	 * @return 返回交易结果 
	 */
	public String trade(String tradeType,OnlineCardTrade cardTrade){
		String respStr = "";
		try{
			String data = this.getDataElmtXML(tradeType,cardTrade);
			//将请求参数XML中的data元素用DES加密,DES密钥是在商户在网银在线后台设置的
			String dataDES = DES.encrypt(data, OnlineContext.des, OnlineContext.charset);
			//计算数据签名version+merchant+terminal+data元素，MD5密钥是在商户在网银在线后台设置，签名是为了验证请求的合法性
			String signMD5 = MD5.md5(OnlineContext.version
					+OnlineContext.JINGDONG_MERCHANT
					+OnlineContext.terminal
					+dataDES, OnlineContext.md5);
			String reqXML = this.getReqXML(dataDES, signMD5);
			System.out.println("data:"+data);
			System.out.println("dataDES:"+dataDES);
			System.out.println("signMD5:"+signMD5);
			System.out.println("reqXML:"+reqXML);
			//最后将xml用base64加密
			String reqParams = Base64.encode(reqXML.getBytes());
			System.out.println("reqParams:"+reqXML);
			//发送请求到网银在线快捷支付地址
			respStr = provider.process(OnlineContext.charset, reqParams);
		}catch(Exception e){
			e.printStackTrace();
		}
		return respStr;
	}
	/**
	 * 处理交易结果
	 * @param respStr
	 */
	public Map<String, String> operate(String respStr){
		System.out.println("respStr:"+respStr);
		Map<String, String> dataParams=null;
		try {
			//数据格式是resp=XML数据
			String temResp = respStr.substring(respStr.indexOf("=") + 1);
			//快捷支付数据先base64解码
			temResp = new String(Base64.decode(temResp));
			//解析xml中的数据
			Map<String, String> respParams= XmlMsg.parse(temResp);
			System.out.println("版本号|商户号|终端号|交易数据|数据签名"
					+respParams.get("chinabank.version")+"|"+respParams.get("chinabank.merchant")+"|"+respParams.get("chinabank.terminal")+"|"
					+respParams.get("chinabank.data")+"|"+respParams.get("chinabank.sign"));
			//验证数据签名的合法性。版本号+商户号+终端号+交易数据使用md5加密和数据签名比较，md5密钥在网银在线商户后台设置
			if(!MD5.verify(respParams.get("chinabank.version")
					+respParams.get("chinabank.merchant")+respParams.get("chinabank.terminal")
					+respParams.get("chinabank.data"), OnlineContext.md5, respParams.get("chinabank.sign"))){
				return null;
			}
			//使用DES解密data交易数据,des密钥网银在线商户后台设置
			String dataDES = DES.decrypt(respParams.get("chinabank.data"), OnlineContext.des, respParams.get("xml.charset"));
			dataParams= XmlMsg.parse(dataDES);
			dataParams.put("data.chinabank.sign", respParams.get("chinabank.sign"));
			System.out.println("交易类型："+dataParams.get("data.trade.type"));
			System.out.println("交易号："+dataParams.get("data.trade.id"));
			System.out.println("原交易跟踪号："+dataParams.get("data.trade.oid"));
			System.out.println("交易金额："+dataParams.get("data.trade.amount"));
			System.out.println("交易货币："+dataParams.get("data.trade.currency"));
			System.out.println("交易日期："+dataParams.get("data.trade.date"));
			System.out.println("交易时间："+dataParams.get("data.trade.time"));
			System.out.println("交易备注："+dataParams.get("data.trade.note"));
			System.out.println("交易状态："+dataParams.get("data.trade.status"));
			System.out.println("跳转URL："+dataParams.get("data.trade.url"));
			System.out.println("交易返回码："+dataParams.get("data.return.code"));
			System.out.println("交易返回码描述："+dataParams.get("data.return.desc"));
			System.out.println("MD5校验码："+dataParams.get("data.chinabank.sign"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataParams;
	}
	
	/**
	 * 处理异步交易结果
	 * @param respStr
	 */
	public Map<String, String> operateAsyn(String respStr){
		System.out.println("operateAsyn-respStr:"+respStr);
		Map<String, String> dataParams=null;
		try {
			//快捷支付数据先base64解码
			String temResp = new String(Base64.decode(respStr));
			//解析xml中的数据
			Map<String, String> respParams= XmlMsg.parse(temResp);
			System.out.println("版本号|商户号|终端号|交易数据|数据签名"
					+respParams.get("chinabank.version")+"|"+respParams.get("chinabank.merchant")+"|"+respParams.get("chinabank.terminal")+"|"
					+respParams.get("chinabank.data")+"|"+respParams.get("chinabank.sign"));
			//验证数据签名的合法性。版本号+商户号+终端号+交易数据使用md5加密和数据签名比较，md5密钥在网银在线商户后台设置
			if(!MD5.verify(respParams.get("chinabank.version")
					+respParams.get("chinabank.merchant")+respParams.get("chinabank.terminal")
					+respParams.get("chinabank.data"), OnlineContext.md5, respParams.get("chinabank.sign"))){
				return null;
			}
			//使用DES解密data交易数据,des密钥网银在线商户后台设置
			String dataDES = DES.decrypt(respParams.get("chinabank.data"), OnlineContext.des, respParams.get("xml.charset"));
			dataParams= XmlMsg.parse(dataDES);
			dataParams.put("data.chinabank.sign", respParams.get("chinabank.sign"));
			System.out.println("交易类型："+dataParams.get("data.trade.type"));
			System.out.println("交易号："+dataParams.get("data.trade.id"));
			System.out.println("原交易跟踪号："+dataParams.get("data.trade.oid"));
			System.out.println("交易金额："+dataParams.get("data.trade.amount"));
			System.out.println("交易货币："+dataParams.get("data.trade.currency"));
			System.out.println("交易日期："+dataParams.get("data.trade.date"));
			System.out.println("交易时间："+dataParams.get("data.trade.time"));
			System.out.println("交易备注："+dataParams.get("data.trade.note"));
			System.out.println("交易状态："+dataParams.get("data.trade.status"));
			System.out.println("跳转URL："+dataParams.get("data.trade.url"));
			System.out.println("交易返回码："+dataParams.get("data.return.code"));
			System.out.println("交易返回码描述："+dataParams.get("data.return.desc"));
			System.out.println("MD5校验码："+dataParams.get("data.chinabank.sign"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataParams;
	}
	
	public static void main(String[] args) {
		MotoPayService service = new MotoPayService();
		OnlineCardTrade cardTrade=new OnlineCardTrade();
		String respStr = service.trade(OnlineContext.ONLINE_PAY_CHINABANK_TRADE_V,cardTrade);
		service.operate(respStr);
	}
	/**
	 * 此方法为了方便，把data元素中的所有子元素都返回了
	 * 网银在线快捷支付会根据交易类型选择传入参数
	 * @return
	 */
	public String getDataElmtXML(String tradeType,OnlineCardTrade cardTrade){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("charset", OnlineContext.charset);
		if(OnlineContext.ONLINE_PAY_CHINABANK_TRADE_V.equals(tradeType)){
			//签约
			params.put("card_bank", cardTrade.getCardBank());
			params.put("card_type", cardTrade.getCardType());
			params.put("card_no", cardTrade.getCardNo());
//			params.put("card_exp", Context.getValue("card_exp"));
//			params.put("card_cvv2", Context.getValue("card_cvv2"));
			params.put("card_name", cardTrade.getCardName());
			params.put("card_idtype", cardTrade.getCardIdtype());
			params.put("card_idno", cardTrade.getCardIdno());
			params.put("card_phone", cardTrade.getCardPhone());
			params.put("trade_type", tradeType);
			params.put("trade_id",   cardTrade.getTradeId());
			params.put("trade_amount", cardTrade.getTradeAmount());
			params.put("trade_currency", cardTrade.getTradeCurrency());	
		}else if(OnlineContext.ONLINE_PAY_CHINABANK_TRADE_S.equals(tradeType)){
			//消费
			params.put("card_bank", cardTrade.getCardBank());
			params.put("card_type", cardTrade.getCardType());
			params.put("card_no", cardTrade.getCardNo());
//			params.put("card_exp", Context.getValue("card_exp"));
//			params.put("card_cvv2", Context.getValue("card_cvv2"));
			params.put("card_name", cardTrade.getCardName());
			params.put("card_idtype", cardTrade.getCardIdtype());
			params.put("card_idno", cardTrade.getCardIdno());
			params.put("card_phone", cardTrade.getCardPhone());
			params.put("trade_type", tradeType);
			params.put("trade_id",   cardTrade.getTradeId());
			params.put("trade_amount", cardTrade.getTradeAmount());
			params.put("trade_currency", cardTrade.getTradeCurrency());
			params.put("trade_date", cardTrade.getTradeDate());
			params.put("trade_time", cardTrade.getTradeTime());
			params.put("trade_notice", cardTrade.getTradeNotice());
			params.put("trade_code", cardTrade.getTradeCode());
		}else if(OnlineContext.ONLINE_PAY_CHINABANK_TRADE_Q.equals(tradeType)){
			//查询			
			params.put("trade_type", tradeType);
			params.put("trade_id", cardTrade.getTradeId());
		}else{
			return "";
		}
		return FTL.doString(params,  "/online/", "http_des_req_data.xml");
	}
	public String getReqXML(String dataElmtDES, String signMD5){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("charset", OnlineContext.charset);
		params.put("version", OnlineContext.version);
		params.put("merchant", OnlineContext.JINGDONG_MERCHANT);
		params.put("terminal", OnlineContext.terminal);
		params.put("data", dataElmtDES);
		params.put("sign", signMD5);
		return FTL.doString(params, "/online/", "http_des_req.xml");
	}
	
	

}
