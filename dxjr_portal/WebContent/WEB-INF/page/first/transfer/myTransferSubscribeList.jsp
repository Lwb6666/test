<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

                <table width="100%" border="0" cellspacing="0" cellpadding="0"  class="table center tbtr02">
		<thead>
                  <tr class="tbl-title">
				<td>开通时间</td>
				<td>投标序号</td>
				<td>交易金额</td>
				<td>转入日期</td>
		</tr>
		</thead>
			<c:forEach items="${page.result}" var="firstTransferVo" varStatus="sta" step="1">
				<tr>
					<td><fmt:formatDate value="${firstTransferVo.firstOpenTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
					<td><a href="javascript:toTransfer(${firstTransferVo.id});">${firstTransferVo.ordernum}</a></td>
					<td><span class="numcolor"><fmt:formatNumber value="${firstTransferVo.accountReal}" pattern="#,##0.00"/></span></td>
					<td><fmt:formatDate value="${firstTransferVo.successTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
				</tr>
			</c:forEach>
		</table>
		<c:if test="${page.result==null  || page.totalCount==0 }">
	<div align="center"  style="height: 70px;line-height: 70px">暂无相关信息</div>
</c:if>   
<c:if test="${page.result !=null && page.totalCount > 0 }">
		<jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
			<jsp:param name="pageNo" value="${page.pageNo}" />
			<jsp:param name="totalPage" value="${page.totalPage}" />
			<jsp:param name="hasPre" value="${page.hasPre}" />
			<jsp:param name="prePage" value="${page.prePage}" />
			<jsp:param name="hasNext" value="${page.hasNext}" />
			<jsp:param name="nextPage" value="${page.nextPage}" />
		</jsp:include>   
</c:if>
<script type="text/javascript">
	$(document).ready(function(){ 
	    var color="#f0f7ff"
	    $(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色
	    
	    $('#beginTime').val('${beginTime}');
	    $('#endTime').val('${endTime}');
	})
	
	function findPage(pageNo){
		window.queryTransferSubscribeList(pageNo);
	}
	/**
	 * 直通车转让详细跳转
	 */
	function toTransfer(transferId){
		window.open("${basePath}/zhitongche/zhuanrang/queryTransferById/"+transferId+".html"); 

	};
</script>