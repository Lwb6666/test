<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  	<title>双十一活动-顶玺金融官网</title>
	<meta name="keywords" content="关于顶玺金融" />
	<meta name="description" content="顶玺金融是专业的互联网金融公司，成立于2013年，总部位于国际金融中心上海。以其自主研发的技术平台和规范、透明的运作方式，成为成长最快、口碑最佳的平台之一。关于顶玺金融详情请登陆www.dxjr.com。">	
</head>
<style>
.redTR{color:red;}
a{color:#555;text-decoration:none;}
a:hover{ color:#00a7e5;text-decoration:none;}
</style>
<body style="background-color:#77172B;" onload="scrollTo((document.body.scrollWidth-document.body.clientWidth)/2,0)">
<div id="top" >
<img src="${path}/images/doubleEleven/top.png">
</div>
<div id="mid">

<div style="width:1920px;height:295px;background-image:url(${path}/images/doubleEleven/mid.png);text-align:center;">

<div align="center" style="float:left;display:inline;background:#FFFFFF;width:400px;;margin-left:461px;">  
<table style="width:360px;text-align:center;margin-top:9px;" cellpadding="3px" >
	<tr>
	  <th>排名</th>
	  <th>昵称</th>
	  <th>投资金额</th>
	</tr>
	<c:forEach items="${threeList}" var="doubleEleven" varStatus="sta">
		<c:if test="${sta.index<3}">
			<tr class="redTR">
			  <td>${sta.index+1 }</td>
			  <td><a href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(doubleEleven.userNameEncrypt))}" target="_blank">${doubleEleven.username }</a></td>
			  <td><fmt:formatNumber value="${doubleEleven.invertTotal }" pattern="#,##0" /></td>
			</tr>
		</c:if>
		<c:if test="${sta.index>=3}">
			<tr>
			  <td>${sta.index+1 }</td>
			  <td><a href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(doubleEleven.userNameEncrypt))}" target="_blank">${doubleEleven.username }</a></td>
			  <td><fmt:formatNumber value="${doubleEleven.invertTotal }" pattern="#,##0" /></td>
			</tr>
		</c:if>
	</c:forEach>
</table>
</div>

<div align="center" style="float:left;display:inline;background:#FFFFFF;width:400px;margin-left:10px;">  
<table style="width:360px;text-align:center;margin-top:9px;"  cellpadding="3px" >
<tr>
  <th>排名</th>
  <th>昵称</th>
  <th>投资金额</th>
</tr>
	<c:forEach items="${sixList}" var="doubleEleven" varStatus="sta">
		<c:if test="${sta.index<3}">
			<tr class="redTR">
			  <td>${sta.index+1 }</td>
			  <td><a href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(doubleEleven.userNameEncrypt))}" target="_blank">${doubleEleven.username }</a></td>
			  <td><fmt:formatNumber value="${doubleEleven.invertTotal }" pattern="#,##0" /></td>
			</tr>
		</c:if>
		<c:if test="${sta.index>=3}">
			<tr>
			  <td>${sta.index+1 }</td>
			  <td><a href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(doubleEleven.userNameEncrypt))}" target="_blank">${doubleEleven.username }</a></td>
			  <td><fmt:formatNumber value="${doubleEleven.invertTotal }" pattern="#,##0" /></td>
			</tr>
		</c:if>
	</c:forEach>
</table>
</div>

</div>

</div>
<div id="buttom">
<img src="${path}/images/doubleEleven/bottom.png">
</div>
<div style="width:1920px;" align="center">
<button style="width:100px;height:30px;" onClick="location.href='http://www.dxjr.com'">返回</button>
</div>
</body>
</html>