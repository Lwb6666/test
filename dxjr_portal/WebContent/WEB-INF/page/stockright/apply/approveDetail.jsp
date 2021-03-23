<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%@ include file="/WEB-INF/page/stockright/common/public.jsp"%>
<div class="cont-word cont-w02" style="margin-top: 10px;">
        <div class="form-info-layer">
        	<div class="equity-tlb" id="J_approveDataList">
            	<table width="100%" border="0">
				  <tr>
				    <th>审核时间</th>
				    <th>状态</td>
				    <th>备注</th>
				  </tr>
				  <c:forEach items="${approveList}" var="app">
				  <tr>
				    <td><fmt:formatDate value="${app.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				    <td><c:choose>
							<c:when test="${app.status==1 }">待审核</c:when>
							<c:when test="${app.status==2 }">审核通过</c:when>
							<c:when test="${app.status==3 }">审核不通过</c:when>
							<c:when test="${app.status==-1 }">已作废</c:when>
						</c:choose></td>
				    <td>${app.remark}</td>
				  </tr>
				  </c:forEach>
				</table>
            </div>
         </div>
    </div> 

