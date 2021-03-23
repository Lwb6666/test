<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>顶玺金融互联网专业的P2P网站</title>
</head>
<body onLoad="javascript:document.E_FORM.submit()">
<form action="https://pay3.chinabank.com.cn/PayGate" method="POST" name="E_FORM">
  
<input type="text" name="v_md5info"    value="${chinabankPayForm.v_md5info }" size="100" style="display:none;">
<input type="text" name="v_mid"        value="${chinabankPayForm.v_mid }" style="display:none;">
<input type="text" name="v_oid"        value="${chinabankPayForm.v_oid }" style="display:none;">
<input type="text" name="v_amount"     value="${chinabankPayForm.v_amount }" style="display:none;">
<input type="text" name="v_moneytype"  value="${chinabankPayForm.v_moneytype }" style="display:none;">
<input type="text" name="v_url"        value="${chinabankPayForm.v_url }" style="display:none;">
<input type="text" name="pmode_id"        value="${chinabankPayForm.pmode_id }" style="display:none;"> 
<input type="text" name="remark2"        value="${chinabankPayForm.remark2 }" style="display:none;"> 
</form>
</body>
</html>
