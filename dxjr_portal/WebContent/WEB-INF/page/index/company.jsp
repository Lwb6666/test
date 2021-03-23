<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> 
<%@ include file="/WEB-INF/page/common/taglib.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html> 
<head>
<title>关于我们_顶玺金融</title>
<%@ include file="/WEB-INF/page/common/public3.jsp"%> 
<meta name="keywords" content="顶玺金融,定期宝" />
<meta name="description" content="顶玺金融">
<style type="text/css">

@media only screen and (min-width: 321px) and (max-width: 1024px){ 
.ab-zhengjian,.office-hjbox{ width:1100px; } 
} 
</style>
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
		$("#introduction").addClass("active");
	});
</script>
</head>
<body>
<%@ include file="/WEB-INF/page/common/header.jsp" %> 
	<div class="aboutus-bigbox">
      <%@ include file="/WEB-INF/page/index/about-leftmain.jsp"%> 
		<div class="abt-content-box about-gsjj">
			<div class="introduce-l">
				<img src="${basePath}/images/v5/aboutus/leftimg.jpg" alt="顶玺金融办公楼" />
			</div>
			<div class="introduce-r">
				<p class="text-p-title" style="padding-top: 0px;">公司简介</p>
				<p class="text-p">上海顶玺金融信息服务有限公司（www.dxjr.com）是一家专业的互联网金融公司</p>
	          	<p class="text-p">旗下的优参堂海参品牌源自于卢炜翎先生创立的优参号参堂，经过一百多年的发展</p>
	            <p class="text-p-title">企业口号</p>
	            <p class="text-p">规模化，现代化，专业化的海参加工生产企业之一</p>
	            <p class="text-p-title">企业理念</p>
	            <p class="text-p">台生活频道电商平台共同进行优参堂海参的销售，致力于将</p>
	            <p class="text-p-title">企业愿景</p>
	            <p class="text-p">客户至上，质量为本</p>
			</div>
			<div class="clearfix"></div>
		</div>

	</div>
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