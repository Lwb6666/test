<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${basePath}/css/help.css" rel="stylesheet" type="text/css" />
<title>帮助中心-如何借款_顶玺金融</title>
</head>
<body>
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<div id="Container">
		<div id="Bmain">
			<div class="title">
				<span class="home"><a href="${path}/">顶玺金融</a></span>
				<span><a href="${path }/bangzhu.html">帮助中心</a></span>
				<span><a href="${path }/bangzhu/jiekuan.html">如何借款</a></span>
				<span>认证资料</span>
			</div>
			<div id="menu_centert">
			<%@ include file="/WEB-INF/page/helpcenter/leftMenu.jsp"%>
				<div id="helpctr">
					<%@ include file="/WEB-INF/page/helpcenter/borrow/top.jsp"%>
					<div class="content" style="display: block;">
						<%@ include file="/WEB-INF/page/helpcenter/borrow/docDiv.jsp"%>
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