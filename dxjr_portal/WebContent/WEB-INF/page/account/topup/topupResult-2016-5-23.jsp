<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<link href="${basePath}/css/404.css" rel="stylesheet" type="text/css" />
<title>充值成功_顶玺金融</title>
</head>

<body style="background:#f9f9f9;">
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<!--头部结束-->
<div class="clear"></div>
<!--头部结束-->
<!--内容开始-->
                 <div id="Bmain">
                       <div class="network-content">
                       		<c:if test="${topupResult == 'success' }">
                            	 <img class="network-wrong" src="${basePath}/images/success.png" width="176" height="176"/>
	                             <p class="topup green">充值成功</p>
	                             <p class="txts">返回 <a href="${path}/myaccount/toIndex.html" >我的账户</a> 页面</p>     
                            </c:if>
                             <c:if test="${topupResult == 'failure' }">
                                 <img class="network-wrong" src="${basePath}/images/fail.png" width="176" height="176"/>

	                             <p class="txts">返回 	                             <p class="topup red">您充值失败了！ </p><a href="${path}/myaccount/toIndex.html" >我的账户</a> 页面</p>
                             </c:if>
                       </div>
                 </div>

<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
</html>
