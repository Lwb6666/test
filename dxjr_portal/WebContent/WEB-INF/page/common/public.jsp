<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
 	// 发布的版本号，每次发布版本时将此版本号加1,其主要原因是引用最新的css文件，避免缓存
    String version = "20170720";
 
	response.setHeader("Cache-Control","no-store"); 
	response.setHeader("Pragrma","no-cache"); 
	response.setDateHeader("Expires",0); 
%>
<%-- 公共meta --%>

<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<%-- <c:if test="${logotype == '1'}"> --%>
<meta name="keywords" content="投资理财,互联网金融,p2p理财投资" />
<meta name="description" content="顶玺金融">
<%-- </c:if> --%>
<meta name="generator" content="顶玺金融" />
<meta name="author" content="顶玺金融" />
<meta name="copyright" content="2017 上海顶玺金融信息服务有限公司" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<%-- 公共css --%>
<link href="${basePath}/images/dxjr.ico" rel="shortcut icon" type="image/x-icon"/>
<link href="${basePath}/css/style.css?version=<%=version%>" rel="stylesheet" type="text/css" />
<link href="${basePath}/css/common.css?version=<%=version%>" rel="stylesheet" type="text/css" />
<link href="${basePath}/css/css.css?version=<%=version%>" rel="stylesheet" type="text/css" />


<%-- 公共js --%>
<script type="text/javascript" src="${basePath }/js/jquery-1.11.0.js?version=<%=version%>"></script>
<script type="text/javascript" src="${basePath }/js/jquery.form.js?version=<%=version%>"></script>
<script type="text/javascript" src="${basePath }/js/formValidatorRegex.js?version=<%=version%>"></script>
<script type="text/javascript" src="${basePath }/js/jquery-migrate-1.2.1.js?version=<%=version%>"></script>
<script type="text/javascript" src="${basePath }/js/layer/layer.min.js?version=<%=version%>"></script>
<script type="text/javascript" src="${basePath }/js/layer/extend/layer.ext.js?version=<%=version%>"></script>
<script type="text/javascript" src="${basePath }/js/My97DatePicker/WdatePicker.js?version=<%=version%>"></script>
<script type="text/javascript" src="${basePath}/js/formatMoney.js?version=<%=version%>"></script>
 

<%-- 改版5.0 --%>
<link href="${basePath}/css/base2.css?version=<%=version%>" rel="stylesheet" type="text/css" />
<script src="${basePath }/js/common2.js?version=<%=version%>"></script>
