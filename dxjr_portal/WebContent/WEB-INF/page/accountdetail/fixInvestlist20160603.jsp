<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<script type="text/javascript">
/**翻页处理*/
function findPage(pageNo){
	window.parent.toggleList('fix',pageNo);
}
</script>

                         <div class="rz_borrow1">
                                      <div class="tbjl">
                                           <table border="0">
                                           <tr>
                                           <td>定期宝编号</td>
                                           <td>定期宝期限</td>
                                           <td>投宝方式</td>
                                           <td>投宝金额</td>
                                           <!--<td>状态</td> -->
                                           <td>投宝时间</td>
                                           </tr>
				<c:if
					test="${null==resultPages.result || fn:length(resultPages.result)==0 }">
					<tr>
						<td colspan="5"><font color="red">无投宝记录</font></td>
					</tr>
				</c:if>
				<c:forEach items="${resultPages.result}" var="investinfo" varStatus="sta"
				step="1">
				<c:if test="${sta.index %2 ==0 }"><tr style="background:#ecfafd"></c:if>
				<c:if test="${sta.index % 2 != 0 }"><tr style="background:#fff"></c:if>
						<td><a
							href="${path }/dingqibao/${investinfo.fixborrowId }.html"
							target="_blank">${investinfo.contractNo}</a></td>
						<td>
						  <c:if test="${investinfo.locklimit==1}">1月宝</c:if>
						  <c:if test="${investinfo.locklimit==3}">3月宝</c:if>
						  <c:if test="${investinfo.locklimit==6}">6月宝</c:if>
						  <c:if test="${investinfo.locklimit==12}">12月宝</c:if>
						</td>
						<td>
							<c:if test="${investinfo.tenderFixType==0}">手动投宝</c:if>
							<c:if test="${investinfo.tenderFixType==1}">自动投宝</c:if>
						</td>
						<td>￥<span id="zcTotal"> <fmt:formatNumber
									value="${investinfo.tenderAccount }" pattern="#,##0.00" /></span></td>
			   <%-- 	<td><a
							href="${path}/accountdetail/show.html?userName=${portal:encode(portal:encode(investinfo.userNameEncrypt))}"
							 target="_blank" title="${investinfo.userNameSecret }">${investinfo.userNameSecret }</a></td> --%>
							<%-- 	<td>￥<span id="zcTotal"> <fmt:formatNumber
									value="${investinfo.tenderAccount }" pattern="#,##0.00" /></span></td> --%>
						<%-- <td><img src="${basePath }/images/tongguo.png" /></td> --%>
						
						<td>${fn:substring(investinfo.tenderFixAddTime, 0, 10)}</td>
					</tr>
				</c:forEach>
                         
                                        </table>                                                                               
                                        </div>
                                
                                <!--yema st-->
                                      <div class="yema">
                                                <div class="yema_cont">
                                                    <div class="yema rt">
				<jsp:include page="/WEB-INF/page/common/ajaxpage.jsp">
					<jsp:param name="pageNo" value="${resultPages.pageNo}" />
					<jsp:param name="totalPage" value="${resultPages.totalPage}" />
					<jsp:param name="hasPre" value="${resultPages.hasPre}" />
					<jsp:param name="prePage" value="${resultPages.prePage}" />
					<jsp:param name="hasNext" value="${resultPages.hasNext}" />
					<jsp:param name="nextPage" value="${resultPages.nextPage}" />
				</jsp:include>

			</div>
                                                </div>  
                                            </div>
                                      <!--yema off-->
                         </div>                                            
