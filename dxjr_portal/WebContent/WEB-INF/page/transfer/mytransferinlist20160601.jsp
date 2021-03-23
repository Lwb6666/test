<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
 <%@ include file="/WEB-INF/page/common/taglib.jsp"%>
                   <table> 
                        <thead>
                                  <tr>
                                    <th>借款标题 </th>
                                    <th>年利率</th>
                                    <th>还款方式</th>
                                    <th>期限</th>
                                    <th>转让系数</th>
                                    <th>交易金额 </th>
                                    <th>转入日期</th>
                                  </tr>
                                  </thead>
                                  <tbody>
                                  
                                    <c:forEach  var="transferVo"  items="${page.result}"   varStatus="index">
		                            
		                             <c:set var="btype" value="${transferVo.borrowType }" scope="page"  />
	 
									<c:choose>   
										<c:when test="${btype == 1}">
											<c:set var="cls" value="xin" scope="page"  />
										</c:when>   
										<c:when test="${btype == 2}">
											<c:set var="cls" value="di" scope="page"  /> 
										</c:when>
										<c:when test="${btype == 3}">
											<c:set var="cls" value="jing" scope="page"  />  
										</c:when>
										<c:when test="${btype == 4}">
											<c:set var="cls" value="" scope="page"  />   
										</c:when>
										<c:when test="${btype == 5}">
											<c:set var="cls" value="bao" scope="page"  />     
										</c:when>
										<c:otherwise></c:otherwise> 
									</c:choose> 
		                            
		                              <tr id="row1"    <c:if test="${(index.index+1)%2==1}">class="trcolor parent"</c:if>  <c:if test="${(index.index+1)%2==0}"> class="parent"</c:if>  >
	                                    <td align="left"><em class="${cls} lxnomargin"></em><a title="${transferVo.borrowName}" href="${path }/zhaiquan/${transferVo.id }.html"><font  color='red' >[转]</font>${fn:substring(transferVo.borrowName,0,10)}<c:if test="${fn:length(transferVo.borrowName)>10}">..</c:if></a></td>
		                                <td>${transferVo.borrowApr}%</td>
	                                    <td> 
		                                      <c:choose>
			                                	<c:when test="${transferVo.borrowStyle == 0}">
													没有限制     
												</c:when>   
												<c:when test="${transferVo.borrowStyle == 1}">
													等额本息
												</c:when>   
												<c:when test="${transferVo.borrowStyle == 2}">
													<a href="##" title="按月付息到期还本">按月付息</a> 
												</c:when>
												<c:when test="${transferVo.borrowStyle == 3}">
													<a href="##" title="到期还本付息">到期还本</a> 
												</c:when>
												<c:when test="${transferVo.borrowStyle == 4}">
													按天还款   
												</c:when>
												<c:otherwise></c:otherwise> 
											</c:choose>
	                                     </td>
	                                    <td>
		                                    <c:if test="${transferVo.borrowType!=4 && transferVo.borrowStyle !=4 }">${transferVo.borrowTimeLimit }月</c:if>
									        <c:if test="${transferVo.borrowType!=4 && transferVo.borrowStyle ==4 }">${transferVo.borrowTimeLimit }天</c:if>
	                                    </td> 
	                                    <td>${transferVo.coef}</td>
	                                    <td>${transferVo.repaymentAccount}</td>
	                                    <td><fmt:formatDate value="${transferVo.successTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                                  </tr>
                                 </c:forEach>
                   </tbody></table>
                             <!--popowindow-->
                   <div class="sf"></div>
                        <div class="tip"></div>

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
                 
      <script type="text/javascript">

	function findPage(pageNo){
		transfer(4,pageNo);
	}
 
</script>            
                  