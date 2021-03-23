<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/page/common/public.jsp"%>
	</head>
	<body>
		发布失败！
		<form method="post">
			<a href="javascript:forindex()">返回首页</a>
		</form>
		
	</body>
</html>

<script type="text/javascript">
function forindex(){
	document.forms[0].action="${basePath}/global/forindex.html";
	document.forms[0].submit();
}
</script>
