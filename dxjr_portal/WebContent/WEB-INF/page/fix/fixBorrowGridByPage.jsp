<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/common/public3.jsp"%> 

<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="table center tbg">
	<tr class="tbl-title">
		<td class="tdpdtitle30" width="16%">名称</td>
		<td class="textr" width="12%">开放金额</td>
		<td class="textr" width="10%">预期利率</td>
		<td class="textr" width="10%">期限</td>
		<td class="textr" width="10%">加入人次</td>
		<td class="textr" width="15%">进度</td>
		<td class="tdtitl06" width="13%"></td>
	</tr>
	<c:forEach items="${page.result}" var="fixBorrow" varStatus="sta" >
	<tr>
	  	<td class="tdtitl07 bule">
	  	<a href="javascript:toFixBorrowDetail(${fixBorrow.id})" style="color:#333;">定期宝  【${fixBorrow.contractNo}】</a>
	  	<c:if test="${fixBorrow.areaType==1}">
			<div style="position:absolute; display:inline-block; margin-top:3px;"><img src="${basePath}/images/new-icon.png?version=<%=version%>"/></div>
		</c:if>
	  	</td>
	  	<td class="textr"><fmt:formatNumber value="${fixBorrow.planAccount}" pattern="#,##0.##" />元</td>
	  	<td class="textr">${fixBorrow.apr}%</td>
	  	<td class="textr">${fixBorrow.lockLimit}个月</td>
	  	<td class="textr">${fixBorrow.tenderTimes}</td>
	  	<td class="textr">
	      	<div class="votebox fr" style="width:110px; overflow:hidden">
				<dl class="barbox fl">
		    	<dd class="barline"  style="width:60px;">
		    	<div w="${fixBorrow.scheduleStrNoDecimal}" style="width:0px;" class="charts"></div>
		        </dd>   
		        </dl><span class="fr">${fixBorrow.scheduleStrNoDecimal}%</span>
	 	 	</div>
		</td>
 		<td  class="tdtitl06">
 		<c:choose>
      		<c:when test="${fixBorrow.status == 3 }">
      			<c:if test="${fixBorrow.joinStatus==0}">
      				<button type="button" class="btn-middle btn-ywc" onclick="toFixBorrowDetail(${fixBorrow.id})">敬请期待</button>
      			</c:if>
      			<c:if test="${fixBorrow.joinStatus==1}">
      				<button type="button" class="btn-middle btn-blue" onclick="toFixBorrowDetail(${fixBorrow.id})">立即加入</button>
      			</c:if>
      		</c:when>
      		<c:when test="${fixBorrow.status == 5 }"><button type="button" class="btn-middle btn-gcsyz" style="cursor:default;"}>收益中</button></c:when>
      		<c:when test="${fixBorrow.status == 7 }"><button type="button" class="btn-middle btn-ywc" >已退出</button></c:when>
      	</c:choose>
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
<style>
.tbg tr:nth-of-type(odd){background:#f0f7ff;}
</style>
<script type="text/javascript">
/**
 * ajax 翻页功能
 */
function findPage(pageNum){
	searchFixBorrowListStart(pageNum);
}

/**
 * 定期宝详细
 */
function toFixBorrowDetail(fixBorrowId){
	window.open("${basePath}/dingqibao/"+fixBorrowId+".html"); 
};

</script>