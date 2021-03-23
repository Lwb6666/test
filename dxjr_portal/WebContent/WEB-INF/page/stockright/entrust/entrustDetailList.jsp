<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table center tbtr">
                		<thead>
                          <tr class="tbl-title">
                            <td>成交时间</td>
                            <td>成交份额(份)</td>
                            <td>成交价格(元)</td>
                            <td>份额总价(元)</td>
                            <td>交易服务费(元)</td>
                            <td>成交类型</td>
                            <td>状态</td>
                            <td>操作</td>
                          </tr>
                          </thead>
                          
                          <tbody>
                          <c:forEach items="${page.result}" var="deal">
                          <tr>
                            <td><fmt:formatDate value="${deal.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td><fmt:formatNumber value="${deal.turnoverAmount}" pattern="#,#00"/></td>
                            <td>${deal.turnoverPrice}</td>
                            <td><fmt:formatNumber value="${deal.turnoverTotalPrice}" pattern="#,#00.00"/></td>
                            <td>
	                            <c:if test="${deal.sellerId == userId}">
	                            	${deal.sellerFee }
	                            </c:if>
	                            <c:if test="${deal.buyerId == userId}">
	                            	${deal.buyerFee }
	                            </c:if>
                            </td>
                            <td>
	                            <c:if test="${deal.sellerId == userId}">
	                            	<c:if test="${deal.dealType == 1 }">主动转让成交</c:if>
	                            	<c:if test="${deal.dealType == 2 }">被动转让成交</c:if>
	                            </c:if>
	                             <c:if test="${deal.buyerId == userId}">
	                            	<c:if test="${deal.dealType == 1 }">被动认购成交</c:if>
	                            	<c:if test="${deal.dealType == 2 }">主动认购成交</c:if>
	                            </c:if>
                            </td>
                            <td>
                            <c:choose>
								<c:when test="${deal.status==1 }">交易处理中</c:when>
								<c:when test="${deal.status==2 }">交易完成</c:when>
							</c:choose>
                            </td>
                           	<td><a href="javascript:stockDownloadPDF('${deal.id}');">交易协议</a><a class="ml10" href="${basePath}/stockDeal/findDealDetail/${deal.id}/${entrustId}.html">详情</a> </td>
                          </tr>
                         </c:forEach>
                          <c:if test="${fn:length(page.result)<= 0}">
								<td colspan="8">暂无数据</td>
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