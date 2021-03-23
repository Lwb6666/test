<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>风险备用金-顶玺金融官网</title>
	<meta name="keywords" content="风险备用金" />
	<meta name="description" content="顶玺金融P2P网络借贷平台设立风险备用金，风险备用金将由招商银行开设独立托管账户，进行专户管理，用于对逾期借款进行回购。为投资者搭建一个轻松、安全、透明的网络理财平台，详情请登陆www.dxjr.com。">
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
				<span>风险备用金</span>
			</div>
			<div id="menu_centert">
			<%@ include file="/WEB-INF/page/safe/leftMenu.jsp"%>    
				<div id="helpctr">
					<%@ include file="/WEB-INF/page/safe/top.jsp"%>    
					<div class="content" style="display: block;">
						<%@ include file="/WEB-INF/page/safe/backupDiv.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
</html>