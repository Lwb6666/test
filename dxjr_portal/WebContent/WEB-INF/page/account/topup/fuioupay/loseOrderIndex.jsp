<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//Dtd HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
.main{
	border: 1px solid black;
	width: 600px;
	margin: 30px auto;
}
.bottomLine{
	border-bottom: 1px solid black;
}
</style>
</head>
<body>
<div class="main" >
<form method="post" action="${basePath}/account/topup/fuioupay/loseSend.html">
	<table cellpadding="5" cellspacing="0" align="center" width="100%">
		<tr>
			<td bgcolor="#FFFFFF" class="bottomLine">富友支付订单查询</td>
		</tr>
		<tr>
			<td>
				<table cellspacing="0" cellpadding="0" width="100%" border="0">
					<tr bgcolor="#f1f8fd"  height="40">
						<td width="30%">&nbsp;&raquo; <font color=#666666>富友支付订单号：</font></td>
						<td><input size="30" name="v_oid" value="" maxlength="30"> <font color=#666666>&nbsp;</font></td>
					</tr>
					<tr bgcolor="#f1f8fd" align="center" height="40">
						<td colSpan=2><input type=submit value=search name=Submit2></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
</div>
</body>
</html>