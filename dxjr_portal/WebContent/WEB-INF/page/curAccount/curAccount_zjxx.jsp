<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
       
<table width="100%" border="0" cellspacing="0" cellpadding="0"  class="table center tbtr02">
  <thead>
      <tr class="tbl-title">
        <td>交易日期</td>
        <td>交易类型</td>
        <td align="left">交易金额(元)</td>
        <td align="left">活期宝资产(元)</td>
      </tr>
  </thead>
  <tbody>
  <c:forEach items="${page.result}" var="curAccountLog" varStatus="sta">
   <tr>
     <td><fmt:formatDate value="${curAccountLog.addtime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
     <td>${curAccountLog.type_z }</td>
     <td align="left"><fmt:formatNumber value="${curAccountLog.money } " pattern="#,##0.00" />  </td>
     <td align="left"><fmt:formatNumber value="${curAccountLog.total } " pattern="#,##0.00" />  </td>
   </tr>
  </c:forEach>
  </tbody>
</table>
<c:if test="${page.result==null || page.totalCount==0 }">
	<div align="center" style="height: 70px;line-height: 70px" >暂无相关数据</div>
</c:if>            
<c:if test="${page.result!=null && page.totalCount>0}">
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
</c:if>
	
	<script>
	 $(document).ready(function(){ 
         var color="#f0f7ff"
         $(".tbtr tr:even,.tbtr01 tr:even,.tbtr02 tr:even,.tbtr03 tr:even,.tbtr04 tr:even,.tbtr05 tr:even,.tbtr06 tr:even,.tbtr07 tr:even,.tbtr08 tr:even,.tbtr09 tr:even,.tbtr10 tr:even,.tbtr11 tr:even,.tbtr12 tr:even,.tbtr13 tr:even,.tbtr14 tr:even,.tbtr15 tr:even,.tbtr16 tr:even,.tbtr17 tr:even").attr("bgcolor",color);//改变奇数行背景色
   	})
   	
    function findPage(pageNo) {
		var type = $('#type').val();
		var tag = $('#jyTag').val();
		window.parent.serachTab2(pageNo,type,tag);
	}
	</script>