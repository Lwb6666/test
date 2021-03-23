<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<table width="200" border="0" cellspacing="0" cellpadding="0">
	<tr align="center">
		<th scope="col" width="20%">时间</th>
		<th scope="col" width="15%">收入</th>
		<th scope="col" width="15%">支出</th>
		<th scope="col" width="15%">结余元宝</th>
		<th scope="col" width="15%">结余荣誉值</th>
		<th scope="col" width="20%" style="text-align: right; padding-right: 15px;">明细</th>
	</tr>
	<c:forEach items="${page.result}" var="s" varStatus="status">
	<tr align="center" <c:if test="${status.count%2==0 }">class="yb-tr"</c:if>>
		<td ><fmt:formatDate value="${s.gainDate }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
		<td ><fmt:formatNumber value="${s.accumulatePoints>=0?s.accumulatePoints:'' }" pattern="###,###" /><c:if test="${s.accumulatePoints<0 }">--</c:if></td>
		<td ><fmt:formatNumber value="${s.accumulatePoints<0?s.accumulatePoints*-1:'' }" pattern="###,###" /><c:if test="${s.accumulatePoints>=0 }">--</c:if></td>
		<td ><fmt:formatNumber value="${s.sycee }" pattern="###,###" /></td>
		<td ><fmt:formatNumber value="${s.honor }" pattern="###,###" /></td>
		<td style="text-align: right; padding-right: 15px;" >${s.detail }</td>
	</tr>
	</c:forEach>
	<tr>
		<td colspan="6" class="pagination">
			<jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
				<jsp:param name="pageNo" value="${page.pageNo}" />
				<jsp:param name="totalPage" value="${page.totalPage}" />
				<jsp:param name="hasPre" value="${page.hasPre}" />
				<jsp:param name="prePage" value="${page.prePage}" />
				<jsp:param name="hasNext" value="${page.hasNext}" />
				<jsp:param name="nextPage" value="${page.nextPage}" />
			</jsp:include>
		</td>
	</tr>
</table>

<script>
function findPage(pageNum) {
	$.ajax({
		url : '${path}/account/sycee/list/' + pageNum + '.html',
		type : 'post',
		dataType : 'text',
		success : function(data) {
			$("#syceeDiv").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
	    }
	});
}
</script>