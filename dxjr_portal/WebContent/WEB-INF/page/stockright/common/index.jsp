<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>我的内转账户</title>
<%@ include file="/WEB-INF/page/common/public4.jsp"%>
<body>
	<div style=" position:absolute;left:50%; top:50%;margin:-100px 0 0 -150px ; font-size: 30px ">
	<c:if test="${isBlack!='false'}">
	<a href="javascript:chengeUserToStock();">内转窗口</a>
	</c:if>
	<c:if test="${isBlack=='false'}">
		该用户在黑名单,不能操作!
	</c:if>
	</div>
</body>
<script type="text/javascript">
function chengeUserToStock() {
	$.ajax({
		url : '${basePath}/stockApply/checkUserInfo.html',
		type : 'post',
		dataType : 'text',
		success : function(data) {
			var jsonobj = eval('(' + data + ')');
			if (jsonobj.status == 3) {
				location.href = "${path}/stockAccount/accountIndex.html";
			}else if (jsonobj.status == 2) {
				location.href = "${path}/stockApply/inRegister.html";
			}else if(jsonobj.status == 4){
				layer.msg(jsonobj.message, 2, 5);
			}else if(jsonobj.status==-1){
				location.href = "${path}/member/toLoginPage.html";
			}else if(jsonobj.status==1){
				layer.msg(jsonobj.message, 2, 5);
				layer.confirm(jsonobj.message,
						function() {
							location.href = "${path}/AccountSafetyCentre/safetyIndex.html";
						});
				
				
			}
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
		}
	});
}
</script>
</html>
