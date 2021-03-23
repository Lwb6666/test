<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

		<table width="100%" border="0">
		<thead>
			<tr>
				<th>序号(投标)</th>
				<th>开通时间</th>
				<th>交易金额</th>
				<th>转让管理费</th>
				<th>转让成交日期</th>
		</tr>
		</thead>
			<c:forEach items="${page.result}" var="firstTransferVo" varStatus="sta" step="1">
				<tr <c:if test="${sta.index%2 == 0 }">class="trcolor"</c:if>>
					<td><a href="javascript:toTransfer(${firstTransferVo.id});">${firstTransferVo.ordernum}</a></td>
					<td><fmt:formatDate value="${firstTransferVo.firstOpenTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
					<td><span class="numcolor"><fmt:formatNumber value="${firstTransferVo.transactionAccount}" pattern="#,##0.00"/></span></td>
					<td><span class="numcolor"><fmt:formatNumber value="${firstTransferVo.manageFee}" pattern="#,##0.00"/></span></td>
					<td><fmt:formatDate value="${firstTransferVo.successTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
				</tr>
			</c:forEach>
		</table>
		
		<jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
			<jsp:param name="pageNo" value="${page.pageNo}" />
			<jsp:param name="totalPage" value="${page.totalPage}" />
			<jsp:param name="hasPre" value="${page.hasPre}" />
			<jsp:param name="prePage" value="${page.prePage}" />
			<jsp:param name="hasNext" value="${page.hasNext}" />
			<jsp:param name="nextPage" value="${page.nextPage}" />
		</jsp:include>   

<script type="text/javascript">
	
	function findPage(pageNo){
		window.queryTransferedList(pageNo);
	}
	
	/**
	 * 直通车转让详细跳转
	 */
	function toTransfer(transferId){
		window.open("${basePath}/zhitongche/zhuanrang/queryTransferById/"+transferId+".html"); 

	};
</script>
            
            
            
            