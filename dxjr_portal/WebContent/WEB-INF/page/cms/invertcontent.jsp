<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>    
 
   <c:forEach  items="${investList}"  var="borrow"  >
	            <ul>
	              <li class="col-1"><a class="gblue"   rel="nofollow" target="_blank" href="${path}/toubiao/${borrow.id}.html">
                       <c:if test="${borrow.name==null }">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
                        ${borrow.subName}
	              </a></li>
	              <li class="col-2">
                              <c:if test="${borrow.status==1}">
									<input type="button" class="but" value="预发中" onclick="goToInvest(${borrow.id});"/>
								</c:if>
								<c:if test="${borrow.status==42}">
									<input type="button" class="but yellow" value="已垫付" onclick="goToInvest(${borrow.id});"/>
								</c:if>
								<c:if test="${borrow.status==2 }">
									<input type="button" class="but bluess" value="${portal:desc(100, borrow.status)}" onclick="goToInvest(${borrow.id});"/>
								</c:if>
								<c:if test="${borrow.status==4 }">
									<input type="button" class="but greens" value="${portal:desc(100, borrow.status)}" onclick="goToInvest(${borrow.id});"/>
								</c:if>
								<c:if test="${borrow.status==5 }">
									<input type="button" class="but gray" value="${portal:desc(100, borrow.status)}" onclick="goToInvest(${borrow.id});"/>
								</c:if>
	              </li>
	            </ul>
	        </c:forEach>