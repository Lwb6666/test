<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="table center tbtr02">
	<tr class="tbl-title">
		<td>申请时间</td>
		<td align="right">申请金额(元)</td>
		<td align="right">到账金额(元)</td>
		<td>手续费(元)</td>
		<td>账户</td>
		<!-- <td>账户类型</td> -->
		<td>状态</td>
		<td>操作</td>
	</tr>
	<c:forEach items="${page.result }" var="cashRecord" varStatus="sta">
		<tr>
			<td>${cashRecord.addtimeymd }</td>
			<td align="right"><fmt:formatNumber
					value="${cashRecord.credited+cashRecord.fee }" pattern="#,##0.00" /></td>
			<td align="right"><fmt:formatNumber
					value="${cashRecord.credited }" pattern="#,##0.00" /></td>
			<td><fmt:formatNumber value="${cashRecord.fee }"
					pattern="#,##0.00" /></td>
			<td>${cashRecord.accountFormat }</td>
			<!-- <td>存管</td> -->
			<td>${cashRecord.statusStr}</td>
			<td><c:if test="${cashRecord.status==0 && cashRecord.isCustody!=1}">
					<a href="javascript:cancelCash(${cashRecord.id})" title="取消提现"><font
						color='red'>取消提现</font> </a>
				</c:if></td>
		</tr>
	</c:forEach>
</table>
<c:if test="${!empty page.result}">
<div>
	<jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
		<jsp:param name="pageNo" value="${page.pageNo}" />
		<jsp:param name="totalPage" value="${page.totalPage}" />
		<jsp:param name="hasPre" value="${page.hasPre}" />
		<jsp:param name="prePage" value="${page.prePage}" />
		<jsp:param name="hasNext" value="${page.hasNext}" />
		<jsp:param name="nextPage" value="${page.nextPage}" />
	</jsp:include>
</div>
</c:if>
<script type="text/javascript">
	/**
	 * ajax 翻页功能
	 */
	function findPage(pageNo) {
		window.parent.turncashPageParent(pageNo);
	}
	$(function() {
		var color = "#f0f7ff"
		$(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor", color);//改变奇数行背景色
		$("#cashTotal").html('${cashTotal}');
    	$("#beginTime2").val('${beginTime}');
    	$("#endTime2").val('${endTime}');
	});

	/**
	 * 取消提现
	 */
	function cancelCash(id) {
		if (layer.confirm("确认要取消提现吗？", function() {
			$.ajax({
				url : '${basePath}/myaccount/cashRecord/cancelCash.html',
				data : {
					id : id
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if ("1" == data.code) {
						layer.msg("取消提现成功！", 2, 1);
					} else {
						layer.msg(data.message, 2, 5);
					}
					findPage(1);
				},
				error : function(data) {
					layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
				}
			});
		}))
			;
	}
</script>