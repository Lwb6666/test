<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>顶玺金融互联网专业的P2P网站</title>
<link href="${basePath}/css/belstar.css" rel="stylesheet" type="text/css" />
<link href="${basePath}/css/Style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${basePath}/js/jquery-1.11.0.js" ></script>
</head>
<body>

<div id="Container"> 
<div id="body_main">
    <div id="countlist">
       <div id="success">
             <div class="jihuo" align="center">
             	<p>${myAccountApproMsgVo.msg }</p>
             	<c:if test="${myAccountApproMsgVo.linkName != null}">
              		<div class="cgbtn" style="margin-left:40%"><a href="${path}/${myAccountApproMsgVo.linkUrl}">${myAccountApproMsgVo.linkName}</a></div>
              </c:if>
             </div>
       </div>
 	</div>
</div>
</div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
</html>
