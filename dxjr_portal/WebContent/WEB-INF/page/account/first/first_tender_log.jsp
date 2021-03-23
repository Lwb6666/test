<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<script type="text/javascript"
	src="${basePath}/js/BigDecimal-all-last.min.js"></script>
<link href="${basePath}/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
/**翻页处理*/
function findPage(pageNo){
	window.parent.loadFirstTenderPage('${basePath}/account/InvestManager/queryFirstTenderLog/'+pageNo+'.html');
}
/** 直通车列表 */
function queryFirstTenderRealList(){
	window.parent.loadFirstTenderPage('${basePath}/account/InvestManager/queryFirstTenderReal/1.html');
}
/** 直通车投标日志记录 */
function queryfirstTenderLogVoList(){
	window.parent.loadFirstTenderPage('${basePath}/account/InvestManager/queryFirstTenderLog/1.html');
}
function queryfirstTenderLogVoDetail(id){
	window.parent.loadFirstTenderPage('${basePath}/account/InvestManager/queryFirstTenderLogDetail/'+id+'.html?pageNo='+${page.pageNo});
}
</script>
<div id="menu_column">
    <div class="men_title">
         <ul>
          <li><a href="javascript:queryFirstTenderRealList();">直通车列表</a> </li>
          <li class="men_li"><a href="javascript:queryfirstTenderLogVoList();">直通车投标记录 </a></li>
         </ul>
    </div>
</div>
<div id="main_content">
<div class="myid nobordertop whitebg">
	<div class="myid" style="border:0px;border-top:0px solid #dbdbdb;">
		<table width="100%" border="0">
			<tr>
				<td>序号（投标）</td>
				<td>所投借款标</td>
				<td>发标时间</td>
				<td>投标前标剩余金额</td>
				<td>投标前可用余额</td>
				<td>状态</td>
				<td>操作</td>
			</tr>
			<c:forEach items="${page.result}" var="firstTenderLogVo" varStatus="sta"
				step="1">
				<c:if test="${sta.index%2 == 0 }">
					<tr class="trcolor">
				</c:if>
				<c:if test="${sta.index%2 != 0 }">
					<tr>
				</c:if>
				<td>
				<span class="tipone">第${firstTenderLogVo.orderNum}位</span>
				</td>
				<td>
					<a title ='${firstTenderLogVo.borrowName }' 
					href="${path }/toubiao/${firstTenderLogVo.borrowId}.html"
					target="_blank">${fn:substring(firstTenderLogVo.borrowName,0,10)}
					<c:if test="${fn:length(firstTenderLogVo.borrowName)>10}">..</c:if>
					</a>
				</td>
				<td><fmt:formatDate value="${firstTenderLogVo.borrowPublishTime}"
						pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
				</td>
				<td><span class="numcolor"><fmt:formatNumber value="${firstTenderLogVo.remaindMoney}" pattern="#,##0.00"/></span></td>
				<td>
				<span class="numcolor"><fmt:formatNumber value="${firstTenderLogVo.useMoney}" pattern="#,##0.00"/></span>
				</td>
				<td>
				<span class="numcolor"><c:if test="${firstTenderLogVo.status==1}">投标成功</c:if></span>
				<span style="color:red;"><c:if test="${firstTenderLogVo.status==2}">投标失败</c:if></span>
				</td>
				<td>
					<a class="#"
					href="javascript:queryfirstTenderLogVoDetail('${firstTenderLogVo.id}');">查看详情</a>
				</td>
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
</div>
				