<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${basePath}/css/help.css" rel="stylesheet" type="text/css" />
<title>帮助中心-如何理财_顶玺金融</title>
</head>
<body>
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<div id="Container">
		<div id="Bmain">
			<div class="title">
				<span class="home"><a href="${path}/">顶玺金融</a></span> <span><a
					href="${path }/bangzhu.html">帮助中心</a></span> <span>如何理财</span>
			</div>
			<div id="menu_centert">
				<%@ include file="/WEB-INF/page/helpcenter/leftMenu.jsp"%>
				<div id="helpctr">
					<%@ include file="/WEB-INF/page/helpcenter/bid/top.jsp"%>
					<div class="content" style="display: block;">
						<jsp:include  page="/WEB-INF/page/helpcenter/bid/newDiv.jsp" >
							 <jsp:param value="1" name="isHome"/>
						</jsp:include>
						
						<jsp:include  page="/WEB-INF/page/helpcenter/bid/productDiv.jsp" >
							 <jsp:param value="1" name="isHome"/>
						</jsp:include>
						
						<jsp:include  page="/WEB-INF/page/helpcenter/bid/feeDiv.jsp" >
							 <jsp:param value="1" name="isHome"/>
						</jsp:include>
						
						<jsp:include  page="/WEB-INF/page/helpcenter/bid/directDiv.jsp" >
							 <jsp:param value="1" name="isHome"/>
						</jsp:include>
						
						<jsp:include  page="/WEB-INF/page/helpcenter/bid/officialDiv.jsp" >
							 <jsp:param value="1" name="isHome"/>
						</jsp:include>
						
						<jsp:include  page="/WEB-INF/page/helpcenter/bid/transferDiv.jsp" >
							 <jsp:param value="1" name="isHome"/>
						</jsp:include>
						
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
</html>