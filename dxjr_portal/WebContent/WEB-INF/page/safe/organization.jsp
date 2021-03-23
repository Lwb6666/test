<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>传统金融机构支持-顶玺金融官网</title>
	<meta name="keywords" content="传统金融机构支持" />
	<meta name="description" content="上海顶玺金融信息服务有限公司宣布正式与平安银行股份有限公司签署全面金融服务战略合作协议，为投资者搭建一个放心、安全、透明的网络理财平台，详情请登陆www.dxjr.com。">
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
				<span>传统金融机构支持</span>
			</div>
			<div id="menu_centert">
			<%@ include file="/WEB-INF/page/safe/leftMenu.jsp"%>    
				<div id="helpctr">
					<%@ include file="/WEB-INF/page/safe/top.jsp"%>    
					<div class="content" style="display: block;">
						<%@ include file="/WEB-INF/page/safe/organizationDiv.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
</html>