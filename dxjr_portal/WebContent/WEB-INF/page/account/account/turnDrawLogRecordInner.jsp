<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<script type="text/javascript">
	/**
	 * ajax 翻页功能
	 */
	function findPage(pageNo) {
		window.parent.turnDrawPageParent(pageNo);
	}
</script>

<!--我的账户右侧  資金管理  转可提明细-->
<div class="myid whitebg nobordertop">
	<table>
		<tr>
			<td>申请时间</td>
			<td>转可提金额</td>
			<td>到账金额</td>
			<td>手续费</td>
			<td>状态</td>
			<td>日志备注</td>
		</tr>
		<c:forEach items="${drawLogRecordList}" var="vavo" varStatus="sta"
			step="1">
			<tr class="${sta.index%2==0?'trcolor':''}">
			<td>${vavo.addtimeFMT }</td>
			<td class="numcolor"><fmt:formatNumber value="${vavo.money }" pattern="#,##0.00"/></td>
			<td class="numcolor"><fmt:formatNumber value="${vavo.credited }" pattern="#,##0.00"/></td>
			<td><fmt:formatNumber value="${vavo.fee }" pattern="#,##0.00"/></td>
			<td>${vavo.statusStr }</td>
			<td>${vavo.remarkStr }</td>
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