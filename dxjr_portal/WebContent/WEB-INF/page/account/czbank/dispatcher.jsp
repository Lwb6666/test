<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户异步跳转页面</title>
<script language="JavaScript" type="text/JavaScript">

function init() {
    document.form1.submit();
}

</script>
</head>
<body onload="javascript:init();">

<form action="<%=(String)request.getAttribute("url")%>" name="form1" method="POST">
	<input type="hidden" name="reqMsg" value="<%=(String)request.getAttribute("reqMsg")%>"/>
</form>

</body>
</html>