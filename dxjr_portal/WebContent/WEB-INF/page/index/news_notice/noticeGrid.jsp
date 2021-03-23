<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <!-- 公共变量 -->
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
 
	<c:forEach items="${page.result}" var="new_notice">
		<li class="tb_line">
			<c:if test="${new_notice.type==0}">
				<p class="tblist_icon"><a href="${path}/gonggao/${new_notice.id}.html">${new_notice.title }</a></p>
				<p class="tblist_date">${new_notice.addtimeStr }</p>
			</c:if>
			<c:if test="${new_notice.type==1}">
				<p class="tblist_icon"><a href="${path}/baodao/${new_notice.id}.html">${new_notice.title }</a></p>
				<p class="tblist_date">${new_notice.addtimeStr }</p>
			</c:if>
		</li>	 
	</c:forEach>
 

