<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>每日加权待收明细-顶玺金融</title>
</head>
<body>
<div class="caiwubg"> 
<table>
    <tr>
    	<td>序号</td>
		<td>用户名</td>
	    <td>待收总额</td>
	    <td>待收日期</td>
    </tr>
    <c:if test="${accountDayLogList != null}">
    <c:forEach items="${accountDayLogList}" var="shareholderRank" varStatus="sta" step="1">
	  <tr align="center">
	  	<td>${sta.index+1 }</td>
	  	<td><a href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(shareholderRank.userName))}" target="_blank"
	  	 title="${shareholderRank.userName }">${shareholderRank.userName }</a></td>
	   	<td><fmt:formatNumber value="${shareholderRank.collection}" pattern="#,##0.00"/></td>
	   	<td>${shareholderRank.addTimeStr }</td>
	  </tr>
	  </c:forEach>
	  </c:if>
	  <c:if test="${accountDayLogList == null}">
	  <tr><td colspan="4">您当前无待收记录</td></tr>
	  </c:if>
</table>
</div>
</body>
</html>
