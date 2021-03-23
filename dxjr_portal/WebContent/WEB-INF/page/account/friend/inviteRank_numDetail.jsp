<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>推荐成功人数_顶玺金融</title>
</head>
<body>
	<div class="myid noborder whitebg" style="width: 600px;">
		<p class="safe_p" style="width: 600px;">${type == 'm' ? '当月' : (type == 'q' ? '当季' : (type == 'y' ? '年度' : ''))}推荐成功人数</p>
		<table>
			<tr>
				<td>序号</td>
				<td>被推荐人</td>
				<td>推荐成功时间</td>
			</tr>
			<c:forEach items="${page.result}" var="item" varStatus="status">
			<tr ${status.count % 2 == 0 ? 'class="trcolor"' : ''}>
				<td>${status.count}</td>
				<td><a href="${path}/accountdetail/show.html?userId=${item.invitedUserid}"  target="_blank">${item.userNameSecret}</a></td>
				<td><fmt:formatDate value="${item.inviteSuccessTime}" pattern="yyyy-MM-dd" /></td>
			</tr>
			</c:forEach>
			<c:if test="${empty page.result}">
			<tr>
				<td colspan="4">无成功推荐数据</td>
			</tr>
			</c:if>
		</table>
		<jsp:include page="/WEB-INF/page/common/page.jsp">
			<jsp:param name="url" value="${basePath}/myaccount/friend/inviteNumDetailList/${type}/#pageNo#.html" />
			<jsp:param name="pageNo" value="${page.pageNo}" />
			<jsp:param name="totalPage" value="${page.totalPage}" />
			<jsp:param name="hasPre" value="${page.hasPre}" />
			<jsp:param name="prePage" value="${page.prePage}" />
			<jsp:param name="hasNext" value="${page.hasNext}" />
			<jsp:param name="nextPage" value="${page.nextPage}" />
		</jsp:include>
	</div>
</body>
</html>