<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${basePath}/css/help.css" rel="stylesheet" type="text/css" />
<title>顶玺金融_帮助中心_如何投资_定期宝</title>
</head>
<body>

	<%@ include file="/WEB-INF/page/common/header.jsp"%>

	<!-- Container s  -->
	<div id="Container">
		<div id="Bmain">
			<div class="title">
				<span class="home"><a href="${path}/">顶玺金融</a></span> <span><a href="${path }/bangzhu.html">帮助中心</a></span> <span><a
					href="${path }/bangzhu/touzi.html">如何投资</a></span> <span><a href="${path }/bangzhu/23.html">定期宝</a></span>
			</div>

			<!-- 活期宝 内容 s  -->
			<div id="menu_centert">
				<%@ include file="/WEB-INF/page/helpcenter/leftMenu.jsp"%>
				<div id="helpctr">
					<%@ include file="/WEB-INF/page/helpcenter/bid/top.jsp"%>
					<div class="content" style="display: block;">
						<%@ include file="/WEB-INF/page/helpcenter/fix/fixAccountHelperDiv.jsp"%>
					</div>
				</div>

			</div>
			<!-- 活期宝 内容 e -->

		</div>
	</div>
	<!-- Container e  -->

	<div class="clearfix"></div>
	<%@ include file="/WEB-INF/page/common/footer.jsp"%>

</body>
</html>