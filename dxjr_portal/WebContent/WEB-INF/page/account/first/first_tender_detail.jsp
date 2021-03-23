<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<link href="${basePath}/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	/**翻页处理*/
	function findPage(pageNo) {
		var firstTenderRealId = $("#firstTenderRealId").val();
		window.parent
				.loadFirstTenderPage('${basePath}/account/InvestManager/queryFirstTenderDetail.html?firstTenderRealId='
						+ firstTenderRealId + '&pageNum=' + pageNo);
	}
</script>

<!--我的账户右侧开始 -->
<div class="lb_waikuang whitebg">
	<div class="myid">
		<input type="hidden" size="12" name="firstTenderRealId" id="firstTenderRealId"
			value="${firstTenderRealId}" />
		<table border="0">
			<tr>
				<td>开通金额(元)</td>
				<td>开通时间</td>
				<td>开通状态</td>
			</tr>
			<c:forEach items="${list}" var="firstTenderDetail" varStatus="sta"
				step="1">
				<c:if test="${sta.index%2== 0 }">
					<tr class="trcolor">
				</c:if>
				<c:if test="${sta.index%2!= 0 }">
					<tr>
				</c:if>
				<%-- <td>${sta.index + 1 }</td> --%>
				<td><span class="numcolor"><fmt:formatNumber value="${firstTenderDetail.account}" pattern="#,##0.00"/></span></td>
				<td><fmt:formatDate value="${firstTenderDetail.addtime}"
						pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
				<td><c:if test="${firstTenderDetail.status == 0}">开通中</c:if> <c:if
						test="${firstTenderDetail.status == 1}">开通成功</c:if> <c:if
						test="${firstTenderDetail.status == 2}">开通失败</c:if></td>
				</tr>
			</c:forEach>
		</table>
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
	</div>
</div>

