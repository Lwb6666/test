<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<head>
<link href="${basePath}/css/circle.css" rel="stylesheet" type="text/css" />

</head>

<body>
	<table border="0">
		<tr>
			<th>序号（投标）</th>
			<th>发布时间</th>
			<th>转出价格</th>
			<th>奖励</th>
			<th>奖励比例</th>
			<th>状态</th>
		</tr>

		<c:forEach items="${page.result}" var="transfer" varStatus="idx">
			<tr>
				<td align="center">
					<c:if test="${null!=transfer.bidPassword && transfer.bidPassword!= ''}"><font color="#EE30A7">[密]</font></c:if> 
					<i><a href="javascript:void(0);" onclick="toTransfer(${transfer.id});">${transfer.ordernum}</a></i>
					<a href="##" title="${transfer.userNameSecret}">
						<img src="${basePath}/images/user.png" width="20" height="20"/>
					</a>
				</td>
				<td align="center"><i><fmt:formatDate
							value="${transfer.addtime}" pattern="yyyy-MM-dd HH:mm:ss" /></i></td>

				<td align="center"><i><fmt:formatNumber
							value="${transfer.accountReal}" pattern="#,##0.00" />元</i></td>

				<td align="center"><i><fmt:formatNumber
							value="${transfer.awards}" pattern="#,##0.00" />元</i></td>
			
				<td align="center"><i>${transfer.awardApr}%</i></td>
							
				<td align="center"><input type="button"
					<c:if test="${transfer.status==4}">class="but gray"</c:if>
					<c:if test="${transfer.status==2}">class="but bluess"</c:if>
					value="${portal:desc(1102, transfer.status)}"
					onclick="toTransfer(${transfer.id});" /></td>
			</tr>
		</c:forEach>
		<c:if test="${page.result.size()==0}">
			<tr>
				<td colspan="5" align="center"><b>没有直通车转让项目</b></td>
			</tr>
		</c:if>
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
</body>
<script type="text/javascript">
/**
 * ajax 翻页功能
 */
function findPage(pageNum){
	searchTransferList(pageNum);
}

/**
 * 直通车转让详细跳转
 */
function toTransfer(transferId){
	window.open("${basePath}/zhitongche/zhuanrang/queryTransferById/"+transferId+".html"); 

};

</script>