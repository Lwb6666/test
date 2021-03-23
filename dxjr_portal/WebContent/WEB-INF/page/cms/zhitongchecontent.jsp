<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>    
    
   <c:forEach  items="${firstBorrowVos}"  var="firstBorrowVo" >
            <ul>
              <li class="col-1"><a   rel="nofollow" target="_blank" class="gblue" href="${path}/zhitongche/${firstBorrowVo.id}.html">${firstBorrowVo.name}</a><c:if test="${firstBorrowVo.status==3}"><img src="${basePath}/images/new.gif"/></c:if></li>
              <li class="col-2">
                
	               <c:if test="${firstBorrowVo.status==3 && firstBorrowVo.publishTime > nowTime }">
		          	<input class="but gray" type="button" value="即将开通" onclick="toInvestFirst(${firstBorrowVo.id});"/>
		          	</c:if>
		          	<c:if test="${(firstBorrowVo.status==3 && firstBorrowVo.publishTime <= nowTime) || (firstBorrowVo.status==5 && firstBorrowVo.planAccount > firstBorrowVo.realAccount)}">
		          	<input class="but bluess" type="button" value="开通未满" onclick="toInvestFirst(${firstBorrowVo.id});"/>
		          	</c:if>
		          	<c:if test="${firstBorrowVo.status==5 && firstBorrowVo.planAccount == firstBorrowVo.realAccount }">
		          	<input class="but greens" type="button" value="开通已满" onclick="toInvestFirst(${firstBorrowVo.id});"/>
		          	</c:if>
              
              </li>
            </ul>
    </c:forEach>  