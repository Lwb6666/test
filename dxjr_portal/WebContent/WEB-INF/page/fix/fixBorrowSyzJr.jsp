<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table center tbtr02">
	<thead>
		<tr class="tbl-title">
			<td>加入金额(元)</td>
			<td>加入时间</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${page.result}" var="fixBorrowJoinDetail" varStatus="sta">
			<tr>
				<td><fmt:formatNumber value="${fixBorrowJoinDetail.account }" pattern="#,##0.00" /> 
					<c:if test="${fixBorrowJoinDetail.tenderType==1}">
						<img src="${basePath}/images/account/zdicon.png" />
					</c:if></td>
				<td><fmt:formatDate value="${fixBorrowJoinDetail.addTime}"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
		</c:forEach>

	</tbody>
</table>
<c:if test="${page.totalPage == 0}">
	<div align="center">暂无数据</div>
</c:if>

		<!-- fenye s -->  
		<div class="yema">
			<div class="yema_cont">
				<div class="yema rt">
					<jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
						<jsp:param name="pageNo" value="${page.pageNo}" />
						<jsp:param name="totalPage" value="${page.totalPage}" />
						<jsp:param name="hasPre" value="${page.hasPre}" />
						<jsp:param name="prePage" value="${page.prePage}" />
						<jsp:param name="hasNext" value="${page.hasNext}" />
						<jsp:param name="nextPage" value="${page.nextPage}" />
					</jsp:include>

				</div>
			</div>
		</div>
