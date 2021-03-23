<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>顶玺金融_新手引导-顶玺金融</title>
<%@ include file="/WEB-INF/page/common/public1.jsp"%>
<meta name="keywords" content="顶玺数据,投资理财平台,金融理财,房产抵押,消费金融,网贷平台" />
<meta name="description"
	content="顶玺金融（www.dxjr.com），一个专注于消费金融与房产抵押贷款的中国社科院网贷评级A级的理财平台。致力于为投资理财用户和贷款用户两端搭建安全、规范、透明的互联网金融平台。顶玺为您的资金保驾护航！  ">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${basePath}/css/help.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<div id="Container">
		<div id="Bmain">
			<div class="title">
				<span class="home"><a href="${path}/">顶玺金融</a></span> <span><a
					href="${path }/bangzhu.html">帮助中心</a></span> <span>新手导航</span>
			</div>
			<div id="menu_centert">
				<%@ include file="/WEB-INF/page/helpcenter/leftMenu.jsp"%>
				<div id="helpctr">
					<%@ include file="/WEB-INF/page/helpcenter/newcomer/top.jsp"%>
					<div class="content" style="display: block;">
						<jsp:include  page="/WEB-INF/page/helpcenter/newcomer/knowgcDiv.jsp" >
						   <jsp:param value="1" name="isHome"/>
						</jsp:include>
						
						 <jsp:include  page="/WEB-INF/page/helpcenter/newcomer/regloginDiv.jsp" >
						   <jsp:param value="1" name="isHome"/>
						</jsp:include>
						
						 <jsp:include  page="/WEB-INF/page/helpcenter/newcomer/rechargeDiv.jsp" >
						   <jsp:param value="1" name="isHome"/>
						</jsp:include>
						  
						<%@ include file="/WEB-INF/page/helpcenter/newcomer/safeDiv.jsp"%>
						
						 <jsp:include  page="/WEB-INF/page/helpcenter/newcomer/bidDiv.jsp" >
						   <jsp:param value="1" name="isHome"/>
						</jsp:include>
						
					    <jsp:include  page="/WEB-INF/page/helpcenter/newcomer/feeDiv.jsp" >
						   <jsp:param value="1" name="isHome"/>
						</jsp:include>
						 
					</div>
				</div>
				<script type="text/javascript">
					function list(i) {
						$("#list" + i).animate({
							height : 'toggle'
						});
					}
				</script>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
</html>