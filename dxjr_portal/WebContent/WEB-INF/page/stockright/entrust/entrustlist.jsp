<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table center tbtr">
                		<thead>
                          <tr class="tbl-title">
                         	<td>挂单编号</td>
                            <td>挂单类别</td>
                            <td>委托时间</td>
                            <td>委托价格(元)</td>
                            <td>委托挂单份额(份)</td>
                            <td>已成交份额(份)</td>
                            <td>未成交份额(份)</td>
                            <td>状态</td>
                            <td>操作</td>
                          </tr>
                          </thead>
                          
                          <tbody>
                          <c:forEach items="${page.result}" var="entrust">
                          <tr>
                          	<td>${entrust.entrustCode}</td>
                            <td>
                             <c:choose>
								<c:when test="${entrust.entrustType==1 }">认购</c:when>
								<c:when test="${entrust.entrustType==2 }">转让</c:when>
							</c:choose>
                            </td>
                            <td><fmt:formatDate value="${entrust.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>${entrust.price}</td>
                            <td>
                            <c:choose>
									<c:when test="${entrust.amount>0}"><fmt:formatNumber value="${entrust.amount}" pattern="#,#00"/></c:when>
									<c:when test="${entrust.amount<=0}">0</c:when>
								</c:choose>
                            
                            </td>
                            <td>
                             <c:choose>
									<c:when test="${entrust.dealAmount>0}"><fmt:formatNumber value="${entrust.dealAmount}" pattern="#,#00"/></c:when>
									<c:when test="${entrust.dealAmount<=0}">0</c:when>
								</c:choose>
                            
                            </td>
                            <td>
                             <c:choose>
									<c:when test="${entrust.residueAmount>0}"><fmt:formatNumber value="${entrust.residueAmount}" pattern="#,#00"/></c:when>
									<c:when test="${entrust.residueAmount<=0}">0</c:when>
								</c:choose>
                            <td>
                             <c:choose>
								<c:when test="${entrust.status==1 }">已挂单</c:when>
								<c:when test="${entrust.status==2 }">部分成交</c:when>
								<c:when test="${entrust.status==3 }">全部成交</c:when>
								<c:when test="${entrust.status==-1 }">已撤销</c:when>
							</c:choose>
                            
                           </td>
                           	<td>
                           	<c:if test="${entrust.status==1 || entrust.status==2}">
                           		<a onclick="revokeEntrust('${entrust.id}');" href="javascript:void(0);">撤单</a>
                           	</c:if>
                           	<a href="${basePath}/stockSeller/findEntrustDetail/${entrust.id}.html">详情</a></td>
                          </tr>
                          </c:forEach>
                           <c:if test="${fn:length(page.result)<= 0}">
								<td colspan="9">暂无数据</td>
							</c:if>
                          </tbody>
                     </table>
                     <c:if test="${fn:length(page.result) > 0}">
					<div class="yema" style="padding:40px 0">
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