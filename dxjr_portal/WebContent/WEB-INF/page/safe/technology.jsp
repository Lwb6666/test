<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>独立技术保障-顶玺金融官网</title>
	<meta name="keywords" content="独立技术保障" />
	<meta name="description" content="顶玺金融从创立伊始就坚持搭建独立的技术平台，组建自己的技术团队，坚持独立自主研发。顶玺金融官方网站同公安系统对接自动进行实名认证。对于非正常访问，顶玺金融网站会及时做出预警和响应，并通知技术部处理。详情请登陆www.dxjr.com。">
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<link href="${basePath}/css/help.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<div id="Container">
		<div id="Bmain">
			<div class="title">
				<span class="home"><a href="${path}/">顶玺金融</a></span>
				<span><a href="${path }/anquan.html" class="gls">安全保障</a></span>
				<span>独立技术保障</span>
			</div>
			<div id="menu_centert">
			<%@ include file="/WEB-INF/page/safe/leftMenu.jsp"%>    
				<div id="helpctr">
					<%@ include file="/WEB-INF/page/safe/top.jsp"%>    
					<div class="content" style="display: block;">
						<%@ include file="/WEB-INF/page/safe/technologyDiv.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
</html>