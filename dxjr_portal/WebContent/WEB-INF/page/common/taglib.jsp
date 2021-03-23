<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%-- 标签库 --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springform" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%-- 字符串处理 --%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 格式化 --%>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<%-- 自定义标签 --%>
<%@ taglib prefix="portal" uri="/WEB-INF/ELTag.tld"%>
<c:if test="${pageContext.request.serverPort=='80' || pageContext.request.serverPort=='443'}">
	<c:set var="basePath" value="${pageContext.request.scheme}://${pageContext.request.serverName}${pageContext.request.contextPath}" />
</c:if>
<c:if test="${pageContext.request.serverPort!='80' && pageContext.request.serverPort!='443'}">
	<c:set var="basePath" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
</c:if>


<%-- 公共变量 --%>
<%-- 生产：http://www.xxx.com 模拟生产 http://192.168.10.10  本地：${pageContext.request.contextPath} 
              测试:http://192.168.10.23/dxjr_portal  --%>
<c:set var="path" value="${pageContext.request.contextPath}" /> 
<%-- bbs路径 --%>
<%-- 生产:http://bbs.xxx.com 模拟生产:http://192.168.10.21   本地：--%>
<c:set var="bbsPath" value="http://192.168.10.15:8084/dxjr_bbs" />

