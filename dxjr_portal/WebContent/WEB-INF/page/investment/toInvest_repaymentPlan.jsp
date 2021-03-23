<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/common/public2.jsp"%>

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table center tbtr02">
	<tr class="tbl-title">
		<td>预计还款日期</td>
        <td style="text-align: right;">预计还款金额</td>
        <td style="text-align: right;">还款本金</td>
        <td style="text-align: right;">还款利息</td>
	</tr>
	<c:forEach items="${page.result}" var="repaymentRecord" varStatus="status">
	<tr>
		<td ><fmt:formatDate value="${repaymentRecord.repaymentTimeDate}" pattern="yyyy-MM-dd"/> </td>
		<td style="text-align: right;"><fmt:formatNumber value="${repaymentRecord.repaymentAccount}" type="currency" pattern="#,##0.##"/>元 </td>
		<td style="text-align: right;"><fmt:formatNumber value="${repaymentRecord.capital}" type="currency" pattern="#,##0.##"/>元 </td>
		<td style="text-align: right;"><fmt:formatNumber value="${repaymentRecord.interest}" type="currency" pattern="#,##0.##"/>元 </td>
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
 
</style>
<script type="text/javascript">
function findPage(pageNum){
	searchRepaymentList(pageNum);
}
 
</script>