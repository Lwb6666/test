<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>我的账户_资金记录_顶玺金融</title>
<%@ include file="/WEB-INF/page/common/public.jsp"%>

</head>
<body style="background: #f9f9f9;">
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--头部结束-->
	<!--我的账户左侧开始-->
	<div id="Container">
		<div id="Bmain">
			<div class="title">
				<span class="home"><a href="${path}/">顶玺金融</a></span>
				<span><a href="${path }/myaccount/toIndex.html">我的账户</a></span>
				<span>资金管理</span>
				<span><a  href="${path}/accOpertingRecord/paymentDetails/1.html" >资金明细</a></span>
			</div>
			<div id="menu_centert">
				<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>
				<!--我的账户左侧 结束 -->

				<!--我的账户右侧开始 -->
				<div id="menu_right" style=""></div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
		<%@ include file="/WEB-INF/page/common/footer.jsp"%>

</body>
<script type="text/javascript">
	$(function() {
		paymentinfo();
	});
	/**
	 * 显示账户交易明细
	 */

	var startTime = '${startTime}';
	var endTime = '${endTime}';
	var type = '${type}';
	/* var pageNum = '${pageNum}'; */
	function paymentinfo() {

		$.ajax({
			url : '${basePath}/accOpertingRecord/showdetail.html',
			data : {
				startTime : startTime,
				endTime : endTime,
				type : type
			},
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#menu_right").html(data);

			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
	}
	/**搜索*/
	function search(pageNum) {
		$.ajax({
			url : '${basePath}/accOpertingRecord/searchpaymentDetails/' + pageNum
					+ '.html',
			data : {
				startTime : $('#startTime').val(),
				endTime : $('#endTime').val(),
				type : $('#type').val()
			},
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#menu_right").html(data);

			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
			}
		});
	}
</script>
</html>
