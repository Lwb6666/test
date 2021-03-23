<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
	<div class="searchbox fl whitebg ">
	    <ul class="lb_title nobordertop"> 
	 		<li class="fl red"><span>直通车债权价格（${bondsAccount }元）</span>=直通车所投标债权价格总额（${accountSum }元）+直通车可用余额（${useMoney }元）</li>                                         
	    </ul>
	    <table width="100%" border="0">
	    </div>

		<thead>
			<tr>
				<th>借款标题</th>
				<th>年利率</th>
				<th>还款方式</th>
				<th>周期</th>
				<th>期数</th>
				<th>剩余本金</th>
				<th>债权价格</th>
				<th>下个还款日</th>
			</tr>
		</thead>
			<c:forEach items="${page.result}" var="firstTransferBorrowVo" varStatus="sta" step="1">
				<c:set var="btype" value="${firstTransferBorrowVo.borrowtype }" scope="request"  />
					<c:choose>   
						<c:when test="${btype == 1}">
							<c:set var="cls" value="xin" scope="request"  />
						</c:when>   
						<c:when test="${btype == 2}">
							<c:set var="cls" value="di" scope="request"  /> 
						</c:when>
						<c:when test="${btype == 3}">
							<c:set var="cls" value="jing" scope="request"  />  
						</c:when>
						<c:when test="${btype == 4}">
							<c:set var="cls" value="" scope="request"  />   
						</c:when>
						<c:when test="${btype == 5}">
							<c:set var="cls" value="bao" scope="request"  />     
						</c:when>
						<c:otherwise></c:otherwise> 
				</c:choose> 
				<tr <c:if test="${sta.index%2 == 0 }">class="trcolor"</c:if>>
					<td align="left"><em class="${cls } lxnomargin"></em><a target="_blank" href="${path }/toubiao/${firstTransferBorrowVo.borrowId}.html">${fn:substring(firstTransferBorrowVo.borrowName,0,7)}<c:if
							test="${fn:length(firstTransferBorrowVo.borrowName)>7}">..</c:if></a></td>
					<td align="center"><fmt:formatNumber value="${firstTransferBorrowVo.borrowApr}" pattern="#,##0.##"/>%</td>
	                <td align="center">
		                <c:choose>
		            		<c:when test="${firstTransferBorrowVo.borrowStyle == 0}">
								没有限制     
							</c:when>   
							<c:when test="${firstTransferBorrowVo.borrowStyle == 1}">
								等额本息
							</c:when>   
							<c:when test="${firstTransferBorrowVo.borrowStyle == 2}">
								<a href="##" title="按月付息到期还本">按月付息</a> 
							</c:when>
							<c:when test="${firstTransferBorrowVo.borrowStyle == 3}">
								<a href="##" title="到期还本付息">到期还本</a> 
							</c:when>
							<c:when test="${firstTransferBorrowVo.borrowStyle == 4}">
									按天还款   
							</c:when>
								<c:otherwise></c:otherwise> 
						</c:choose> 
	                </td>
	                <td align="center">
	                          <c:if test="${firstTransferBorrowVo.borrowtype==4}">秒还</c:if>
							  <c:if test="${firstTransferBorrowVo.borrowtype!=4 && firstTransferBorrowVo.borrowStyle !=4 }">${firstTransferBorrowVo.borrowTimeLimit }月</c:if>
							  <c:if test="${firstTransferBorrowVo.borrowtype!=4 && firstTransferBorrowVo.borrowStyle ==4 }">${firstTransferBorrowVo.borrowTimeLimit }天</c:if>
					</td>
					<td align="center">
						${firstTransferBorrowVo.startOrder }/${firstTransferBorrowVo.borrowOrder}
					</td>
					<td><span class="numcolor"><fmt:formatNumber value="${firstTransferBorrowVo.borrowCollectionCapital}" pattern="#,##0.00"/></span></td>
					<td><span class="numcolor"><fmt:formatNumber value="${firstTransferBorrowVo.account}" pattern="#,##0.00"/></span>
					</td>
					<td><fmt:formatDate value="${firstTransferBorrowVo.nextRepayTime }" pattern="yyyy-MM-dd"/></td>
				</tr>
			</c:forEach>
		</table>
		<div>
		
	</div>
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
		queryTransferBorrowDetailList('${tenderRealId}',pageNo);
	}
	
</script>