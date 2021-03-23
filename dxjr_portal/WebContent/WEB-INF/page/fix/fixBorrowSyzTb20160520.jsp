<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/common/public.jsp"%>




<!-- 收益明细 - 列表  s  -->
  <div class=" dqb-myid whitebg nobordertop dqb-tbxx">
    <table class="dqb-table-title">
      <tr>
        <td>借款标题</td>
        <td style="width:18%">投资本金（元）</td>
        <td style="width:20%">投标时间</td>
        <td style="width:18%">状态</td>
      </tr>
    </table>

<table>
      <tbody>
						<c:forEach items="${page.result}" var="fixBorrowJoinDetail" varStatus="sta">
							<tr <c:if test="${sta.index%2==0}">class="dqb-tr1color"</c:if>
							<c:if test="${sta.index%2!=0}">class="dqb-tr2color"</c:if>
							 >
								  <td>  
						 			<a href="${path}/toubiao/${fixBorrowJoinDetail.borrowId}.html">${fixBorrowJoinDetail.borrowName }</a>	 	
								  </td>
								<td style="width:18%"> <fmt:formatNumber value="${fixBorrowJoinDetail.account}" pattern="#,##0.00" /> </td>
								
								 <td style="width:20%">
								    <fmt:formatDate value="${fixBorrowJoinDetail.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/>  
								  </td>
								<td style="width:18%">   ${fixBorrowJoinDetail.tenderStatusStr }  </td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					
					 <c:if test="${page.result==null }">
		            	<span style="padding-left:350px;display:block;"> 没有加入信息 </span>
		             </c:if>
				
				<!-- 收益明细列表 e -->


<c:if test="${page.result!=null }">
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
	<!-- fenye e -->


</c:if>
	


</div>
<!-- 收益明细 - 列表  e  -->

<script type="text/javascript">
	$(function() {

	});

	/**
	 * ajax 翻页功能
	 */
	function findPage(pageNo) {
		window.parent.pageParent_tb(pageNo);
	}
	
	
</script>