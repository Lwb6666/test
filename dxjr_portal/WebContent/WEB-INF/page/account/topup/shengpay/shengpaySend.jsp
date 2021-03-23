<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "com.dxjr.portal.account.util.ShengpayConfig" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>顶玺金融互联网专业的P2P网站</title>
<script type="text/javascript" src="${basePath}/js/jquery-1.11.0.js" ></script>

</head>

<body>

<body>
<form action="<%=ShengpayConfig.gateway %>" method="POST" name="shengpayform" id="shengpayform">
<table width="100%" style="display:none;">
	<tr>
		<td width="50%">报文名称</td>
		<td><input readonly  name="Name" type="text" id="Name" value="${shengPayForm.name}" /></td>
	</tr>
	<tr>
		<td width="50%">版本号</td>
		<td><input readonly name="Version" type="text" id="Version" value="${shengPayForm.version}" /></td>
	</tr>
	<tr>
		<td width="50%">字符集</td>
		<td><input readonly name="Charset" type="text" id="Charset" value="${shengPayForm.charset}" /></td>
	</tr>
	<tr>
		<td width="50%">请求提交方(一般为商户号)</td>
		<td><input readonly name="MsgSender" type="text" id="MsgSender" value="${shengPayForm.msgSender}" /></td>
	</tr>
	<tr>
		<td>报文发送时间yyyyMMddHHmmss</td>
		<td><input readonly name="SendTime" type="text" id="SendTime" value="${shengPayForm.sendTime}" /></td>
	</tr>	
	<tr>
		<td>订单号</td>
		<td><input readonly  name="OrderNo" type="text" id="OrderNo" value="${shengPayForm.orderNo}" /></td>
	</tr>
	<tr>
		<td>订单金额（数字形式，两位小数）</td>
		<td><input readonly name="OrderAmount" type="text" id="OrderAmount" value="${shengPayForm.orderAmount}" /></td>
	</tr>
	<tr>
		<td>订单时间yyyyMMddHHmmss</td>
		<td><input readonly name="OrderTime" type="text" id="OrderTime" value="${shengPayForm.orderTime}" /></td>
	</tr>	
	<tr>
		<td>支付方式</td>
		<td><input readonly name="PayType" type="text" id="PayType" value="${shengPayForm.payType}" /></td>
	</tr>
	<tr>
		<td>支付渠道</td>
		<td><input readonly name="PayChannel" type="text" id="PayChannel" value="${shengPayForm.payChannel}" /></td>
	</tr>	
	<tr>
		<td>付款机构代码</td>
		<td><input readonly name="InstCode" type="text" id="InstCode" value="${shengPayForm.instCode}" /></td>
	</tr>	
	<tr>
		<td>回调地址</td>
		<td><input readonly name="PageUrl" type="text" id="PageUrl" value="${shengPayForm.pageUrl}" /></td>
	</tr>
	<tr>
		<td>服务器通知地址</td>
		<td><input readonly name="NotifyUrl" type="text" id="NotifyUrl" value="${shengPayForm.notifyUrl}"/></td>
	</tr>
	<tr>
		<td>商品名称</td>
		<td><input readonly name="ProductName" type="text" id="ProductName" value="${shengPayForm.productName}" /></td>
	</tr>
	
	<tr>
		<td>支付人联系方式</td>
		<td><input readonly name="BuyerContact" type="text" id="BuyerContact" value="${shengPayForm.buyerContact}" /></td>
	</tr>
	<tr>
		<td>买家IP地址</td>
		<td><input readonly name="BuyerIp" type="text" id="BuyerIp" value="${shengPayForm.buyerIp}" /></td>
	</tr>
	
	<tr>
		<td>备注1,回调时会传回给商户</td>
		<td><input readonly name="Ext1" type="text" id="Ext1" value="${shengPayForm.ext1}" /></td>
	</tr>
	
	<tr>
		<td>签名类型（RSA签名，MD5签名）</td>
		<td><input readonly name="SignType" type="text" id="SignType" value="${shengPayForm.signType}" /></td>
	</tr>
	<tr>
		<td>签名密钥</td>
		<td><input readonly name="SignMsg" type="text" id="SignMsg" value="${shengPayForm.signMsg}" /></td>
	</tr>
	<tr>
		<td>签名密钥</td>
		<td><input readonly name="SignKey" type="text" id="SignKey" value="${shengPayForm.signMsg }" /></td>
	</tr>
	<tr>
		<td colspan=2 width="100%">
		<center><input type="submit" name="submit" id="submit" value="提交网关" /></center>
		</td>
	</tr>
</table>
</form>
<script type="text/javascript">
	$("#submit").click();	
</script>
</body>
</html>
