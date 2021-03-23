<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>关于我们_管理团队-顶玺金融</title>
<%@ include file="/WEB-INF/page/common/public3.jsp"%>
<meta name="keywords" content="投资理财" />
<meta name="description" content="顶玺金融（www.dxjr.com）">
<script type="text/javascript">
	$(function() {
		var nav = $('.navbar'), doc = $(document), win = $(window);
		win.scroll(function() {
			if (doc.scrollTop() > 70) {
				//alert("00")
				nav.addClass('scrolled');
			} else {
				nav.removeClass('scrolled');
			}
		});
		win.scroll();
		$("#team").addClass("active");
	});
</script>
</head>
<body>
	<div class="aboutus-bigbox">
     <%@ include file="/WEB-INF/page/index/about-leftmain.jsp"%> 
		<div class="abt-content-box about-gsjj">
			<div class=" abt-content-box fzlcbox ">
				<h1>管理团队</h1>
				<div>
				<p>融合大众创业万众创新的基因</p> 
				</div>
			</div>
		</div>
		<style>
.gltdbigbox {
	background: #fff;
	padding-bottom: 40px;
}

.bos-photo-1, .bos-photo-2, .bos-photo-3 {
	width: 246px;
	float: left;
	overflow: hidden;
	height: 320px;
}

.abt-gltdbox .bos-photo-1 {
	background: url(${basePath}/images/v5/aboutus/bosimg.png) no-repeat;
}

.abt-gltdbox .bos-photo-2 {
	background: url(${basePath}/images/v5/aboutus/bosimg.png) no-repeat -246px 0;
}

.abt-gltdbox .bos-photo-3 {
	background: url(${basePath}/images/v5/aboutus/bosimg.png) no-repeat -492px 0;
}

.abt-gltdbox dl {
	float: right;
	width: 70%;
	padding-top: 20px;
}

.abt-gltdbox dl dt {
	color: #35aaf2;
	font-size: 18px;
	line-height: 45px;
}

.abt-gltdbox dl dd {
	font-size: 14px;
	line-height: 30px;
}
</style>
         <%@ include file="/WEB-INF/page/common/header.jsp" %> 



      <div class="clearfix"></div>
    <%@ include file="/WEB-INF/page/common/footer.jsp" %> 
	<script type="text/javascript">
		function animate() {
			$(".charts").each(function(i, item) {
				var a = parseInt($(item).attr("w"));
				$(item).animate({
					width : a + "%"
				}, 2000);
			});
		}
		animate();
	</script>
</body>
</html>