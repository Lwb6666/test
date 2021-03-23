<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<%response.setStatus(404);%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords" content="顶玺金融,金融,投资,理财" />
<meta name="description" content="顶玺金融提供安全、有担保的互联网理财投资服务。风险保证金保障！随时可赎回！上顶玺，好收益！">
<meta name="generator" content="顶玺金融" />
<meta name="author" content="顶玺金融" />
<meta name="copyright" content="2014 上海顶玺金融信息服务有限公司" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>404</title>
<%@ include file="/WEB-INF/page/common/public2.jsp"%>
<link href="${basePath}/css/404.css?version=<%=version%>" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="${path }/images/favicon.ico?version=<%=version%>" type="image/x-icon"/>
<style type="text/css">

</style>
</head>

<body>
<!--头部开始-->
<div class="header">
	<!--topbar-->
	<div class="topbar">
    	<div class="grid-1100">
            <div class="topbar-left"></div>
            <div class="topbar-right"></div>
        </div>
    </div>
    <!--nav main-->
    <div class="navbar" role="navigation">
    	<div class="grid-1100">
        	<div class="logo">
    			<a href="${path }/"><img src="${path}/images/v5/logo.png" alt="顶玺金融"/></a>
            </div>
            <div class="menu">
          </div>
       </div>
    </div>
</div><!--头部end-->
<div class="clear"></div>
<!--头部结束-->
<!--内容开始-->

   <div class="network-content" style="padding-bottom: 300px;">
     <img class="network-wrong" src="${basePath}/images/404.png" width="176" height="176"/>
     <p class="headline">您访问的页面不存在</p>
     <p class="txts">可能该服务已经过期，或者您输入的地址有误。<br/>
     	返回
     <a href="${path}/">顶玺金融首页</a>
     </p>
   </div>


<!--内容结束-->
<div class="clearfix bompd"></div>
<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
</html>
