<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

 <table border="0">
    <tr>
        <td>直通车标题</td>
        <td>计划金额</td>
        <td>期限</td>
        <td>预期收益</td>
        <td>投标笔数</td>
        <td>发布时间</td>
    </tr>  
    <c:forEach items="${page.result }" var="firstBorrow" varStatus="index" >
    <tr  <c:if test="${index.index%2==0 }">style="background:#ecfafd"</c:if>  >    
         <td><a href="${path}/zhitongche/${firstBorrow.id}.html">${firstBorrow.name } </a></td>
         <td>￥<fmt:formatNumber value="${firstBorrow.planAccount }" pattern="#,##0" /></td>
         <td>${firstBorrow.lockLimit }${firstBorrow.lockStyleStr}</td>
         <td>${firstBorrow.perceivedRate }%</td>
         <td>${firstBorrow.tenderTimes }笔</td>
         <td>${firstBorrow.publishTimeStr }</td>
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
/**
 * ajax 翻页功能
 */
function findPage(pageNo){
	window.parent.turnFirstPageParent(pageNo);
}
</script>
</html>
