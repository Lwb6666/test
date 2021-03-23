<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>关于我们_联系我们-顶玺金融</title>
<%@ include file="/WEB-INF/page/common/public3.jsp"%>
<meta name="keywords" content="投资理财" />
<meta name="description"
	content="">
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
		$("#contact").addClass("active");
	});
</script>

</head>
<body>
	<%@ include file="/WEB-INF/page/common/header.jsp" %> 
	<div class="aboutus-bigbox">
    <%@ include file="/WEB-INF/page/index/about-leftmain.jsp"%> 
		<div class="abt-content-box about-gsjj">
			<div class=" abt-content-box fzlcbox ">
			 <h1>联系我们</h1>
				<div>        
					<p>如果您对顶玺金融<a href="http://www.dxjr.com/" style="padding:0 10px; color:#000;">www.dxjr.com</a>平台有任何疑问</p>
					<p>或在使用过程中出现什么问题</p>
					<p>请您与顶玺金融客服人员联系</p>
				</div>
			</div>
		</div>
		<div class="jiandubigbox">
			<div class="abt-content-box gc-address">
				<h1 class="contactwe">顶玺金融（总部）</h1>
				<p>© Copyright 2017 www.dxjr.com</p>
				<p>公司地址：上海市普陀区华池路58弄新体育3楼</p>
				<p>联系电话：021-00000000</p>
				<p>邮编：200061</p>

				<div class="maptitle">公司地址地图</div>
				<div style="margin: 0 auto;">
					<img src="${basePath}/images/v5/aboutus/map.jpg" alt="顶玺金融地址" />
				</div>
				<h1 class="contactwe rgfw">人工服务</h1>
				<p>
					财富热线：<span style="color: #35aaf2;">400-000-0000</span>
				</p>
				<p>在线QQ：<a   target="_blank"  href="http://www.dxjr.com/onLineServices/webQQ.html">00000000</a></p>
				<p style="padding-bottom: 50px;">客服服务时间：工作日（9:00-21:30）</p>
			</div>
		</div>
		<div class="abt-content-box lianxi3">
			<h1 class="contactwe rgfw" style="border-bottom: #c5c4c4 solid 1px;">商务合作</h1>
			<p>
				如果您有意向与我们合作，请将合作意向文档发送至：顶玺金融渠道负责人【刘小姐】：marketing@dxjr.com，
				<!-- <a href="mailto:xujingbo@dxjr.com" style="color: #35aaf2;">xujingbo@dxjr.com，</a> -->我司工作人员会尽快与您联系
			</p>
			<h1 class="contactwe rgfw" style="border-bottom: #c5c4c4 solid 1px;">媒体合作</h1>
			<p>
				如媒体有采访需求或合作需要，请将媒体名称、采访提纲、联系方式等相关资料发送到 marketing@dxjr.com<!-- <a href="mailto:marketing@dxjr.com" ><span style="color: #35aaf2;">marketing@dxjr.com</span></a>
				 -->或致电021-80100993（顶玺金融市场部），我们将尽快与您联系
			</p>
		</div>
		<style>
</style>
		<div class="jiandubigbox">
			<div class="abt-content-box lianxi4">
				<h1 class="contactwe rgfw" style="border-bottom: #c5c4c4 solid 1px;">了解我们</h1>
				<p style="padding-top: 40px;">
					顶玺金融微信账号：<span>guochengjinrong (点击屏幕下方官方微信，扫描二维码关注顶玺金融微信)</span>
				</p>
				<p>
					顶玺金融新浪微博： <a href="http://weibo.com/p/1006063805446108/home?from=page_100606&amp;mod=TAB#place" rel="nofollow"><span> http://weibo.com/p/1006063805446108</span></a>
				</p>
				<p>
					顶玺金融腾讯微博： <a href="http://t.qq.com/guochengjinrong" rel="nofollow"><span>http://t.qq.com/guochengjinrong</span></a>
				</p>
				<p>
					顶玺金融新浪博客：<a href="http://blog.sina.com.cn/u/3805446108" rel="nofollow"><span> http://blog.sina.com.cn/u/3805446108</span></a>
				</p>
			</div>
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