<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的账户</title>
<link href="${basePath}/css/user.css"  type="text/css" rel="stylesheet" />
<link href="${basePath}/css/Style.css" type="text/css" rel="stylesheet" />
	</head>
	<body>
<%-- 		<jsp:include page="/dxjrweb/pages/common/topNoImage.jsp"></jsp:include> --%>
		<div id="Container"> 
			<div id="body_main">
			<div id="content2">
			<%-- <jsp:include page="/dxjrweb/pages/common/myAccountRightMenu.jsp"></jsp:include> --%>
				<div class="rocky_body" style="height:300px;text-align:center;">
					<div>&nbsp;</div>
					<div>&nbsp;</div>
					<div>&nbsp;</div>
					${msgContxt }
					<br />
					<a href="${path }/home/login.html">我的账户</a>
				</div>
			</div>
			</div>
		</div>
	</body>
</html>
