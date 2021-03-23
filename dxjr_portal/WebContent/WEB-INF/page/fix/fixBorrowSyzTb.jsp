<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table center tbtr">
		<thead>
			<tr class="tbl-title">
				<td>借款标题</td>
				<td align="left">投资本金(元)</td>
				<td>投标时间</td>
				<td>状态</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.result}" var="fixBorrowJoinDetail" varStatus="sta">
			<tr>		
				<td><a href="${path}/toubiao/${fixBorrowJoinDetail.borrowId}.html">${fixBorrowJoinDetail.borrowName }</a></td>
				<td align="left"><fmt:formatNumber value="${fixBorrowJoinDetail.account}" pattern="#,##0.00" /> </td>
				<td> <fmt:formatDate value="${fixBorrowJoinDetail.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${fixBorrowJoinDetail.tenderStatusStr }</td>
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

<script type="text/javascript">
	$(function() {

	});

	/**
	 * ajax 翻页功能
	 */
	function findPage(pageNo) {
		window.parent.load_tb(pageNo);
	}
	
	
</script>