<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/page/common/public.jsp"%>
	</head>
	<body>
	<form method="post">
        <table>
        <tr>
	        <td>Name:</td>
	        <td>
	        	<input type="text" id="username" name="username" />
	        </td>
        </tr>
        <tr>
	        <td>PassWord:</td>
	        <td>
	        	<input type="password" id="password" name="password" />
	        </td>
        </tr>
        <tr>
	        <td colspan="2">
	            <input type="button" name="btn" value="提交" onclick="forlogin()"/>
	        </td>
        </tr>
        </table>
    </form>
	</body>
</html>
<script language="javascript">
function forlogin(){
	document.forms[0].action="${basePath}/login/login.html"
	document.forms[0].submit();	
}
</script>