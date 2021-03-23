<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table center tbtr">
<thead>
        <tr class="tbl-title">
       	<td>申请时间</td>
          <td>平台待收</td>
          <td>状态</td>
          <td>操作</td>
        </tr>
        </thead>
       <tbody>
       <c:forEach items="${page.result}" var="apply" varStatus="idx">
        <tr>
          <td><fmt:formatDate value="${apply.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
          <td>
          <fmt:formatNumber value="${apply.collection}" pattern="#,#00.00"/>
         	 元</td>
          <td>
          <c:choose>
			<c:when test="${apply.status==1 }">待审核</c:when>
			<c:when test="${apply.status==2 }">审核通过</c:when>
			<c:when test="${apply.status==3 }">审核不通过</c:when>
			<c:when test="${apply.status==-1 }">已作废</c:when>
		</c:choose>
        </td>
         	<td><a href="javascript:searchApprove(${apply.id},${apply.type});">查看详情</a></td>
        </tr>
       </c:forEach>
        <c:if test="${fn:length(page.result)<= 0}">
			<td colspan="4">暂无数据</td>
		</c:if>
       </tbody>
   </table>
 <c:if test="${fn:length(page.result)> 0}">
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