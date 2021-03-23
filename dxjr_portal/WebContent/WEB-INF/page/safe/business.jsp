<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>业务模式-顶玺金融官网</title>
	<meta name="keywords" content="业务模式" />
	<meta name="description" content="顶玺金融为专业P2P信息中介平台，为资金需求方和资金借出方提供撮合交易服务。通过传统的线下房地产抵押借贷和P2P互联网金融平台进行高效整合，最新投资理财新模式详情请登陆www.dxjr.com。">
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
				<span>业务模式</span>
			</div>
			<div id="menu_centert">
			<%@ include file="/WEB-INF/page/safe/leftMenu.jsp"%>    
				<div id="helpctr">
					<%@ include file="/WEB-INF/page/safe/top.jsp"%>    
					<div class="content" style="display: block;">
						<%@ include file="/WEB-INF/page/safe/businessDiv.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>
</html>