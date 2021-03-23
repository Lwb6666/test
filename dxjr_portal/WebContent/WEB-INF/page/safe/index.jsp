<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>顶玺安全保障_P2P理财安全_投资理财保障-顶玺金融官网</title>
<meta name="keywords" content="P2P理财安全,投资理财保障,顶玺安全保障" />
	<meta name="description" content="顶玺金融以上海房产抵押为主，致力打造“规范、专业、透明的网贷融资平台”，为投资人和借款人之间提供安全、有保障、高效的互联网理财服务，详情请登陆www.dxjr.com。">
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<link href="${basePath}/css/help.css" rel="stylesheet" type="text/css" />

</head>

<body>
<%@ include file="/WEB-INF/page/common/header.jsp"%>
<div id="Container">
<div id="Bmain">
  <div class="title">
    <span class="home"><a href="${path}/">顶玺金融</a></span><span>安全保障</span>
  </div>
        <div id="menu_centert">
        <%@ include file="/WEB-INF/page/safe/leftMenu.jsp"%>    

<div id="helpctr">
	<%@ include file="/WEB-INF/page/safe/top.jsp"%> 
   	<div class="content" style="display: block;">     
		<jsp:include  page="/WEB-INF/page/safe/businessDiv.jsp" >
		   <jsp:param value="1" name="isHome"/>
		</jsp:include>   
		
		<jsp:include  page="/WEB-INF/page/safe/principalDiv.jsp" >
		   <jsp:param value="1" name="isHome"/>
		</jsp:include>   
		
		<jsp:include  page="/WEB-INF/page/safe/backupDiv.jsp" >
		   <jsp:param value="1" name="isHome"/>
		</jsp:include> 
		
		<jsp:include  page="/WEB-INF/page/safe/buybackDiv.jsp" >
		   <jsp:param value="1" name="isHome"/>
		</jsp:include> 
		
		<jsp:include  page="/WEB-INF/page/safe/externalDiv.jsp" >
		   <jsp:param value="1" name="isHome"/>
		</jsp:include> 
		 
		 <jsp:include  page="/WEB-INF/page/safe/interiorDiv.jsp" >
		   <jsp:param value="1" name="isHome"/>
		</jsp:include> 
	 
	    <jsp:include  page="/WEB-INF/page/safe/technologyDiv.jsp" >
		   <jsp:param value="1" name="isHome"/>
		</jsp:include> 
		
		<jsp:include  page="/WEB-INF/page/safe/lawDiv.jsp" >
		   <jsp:param value="1" name="isHome"/>
		</jsp:include> 
		
		 <jsp:include  page="/WEB-INF/page/safe/organizationDiv.jsp" >
		   <jsp:param value="1" name="isHome"/>
		</jsp:include>
		 
		  <jsp:include  page="/WEB-INF/page/safe/youxiaofengkongDiv.jsp" >
		   <jsp:param value="1" name="isHome"/>
		</jsp:include>
     </div>  
</div>
<script type="text/javascript">
function list(i){
	$("#list"+i).animate({height:'toggle'});
}
</script>
</div>
</div>
</div>
<div class="clearfix"></div>

<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>