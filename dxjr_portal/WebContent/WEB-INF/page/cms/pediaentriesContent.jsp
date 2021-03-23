<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
      <c:forEach items="${cmsPediaEntries}"  var="cmsPediaEntrie" varStatus="index" >
	                      <c:if test="${(index.index+1)%7==1 }">
	                       <ul>
	                     </c:if>
	                          <li><h2><a href="${basePath}/baike/${cmsPediaEntrie.id}.html">${cmsPediaEntrie.name}</a></h2></li>
	                      <c:if test="${(index.index+1)%7==0 }">
	                        </ul>
	                     </c:if>
       </c:forEach>   