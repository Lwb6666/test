<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>

<title>我的账户_直通车投标列表_顶玺金融</title>

</head>

<body style="background: #f9f9f9;">
	<!--头部开始-->
	<%@ include file="/WEB-INF/page/common/header.jsp"%>
	<!--头部结束-->
	<!--我的账户左侧开始-->
	<div id="Container">
		<div id="Bmain">
			<div class="title">
				<span class="home"><a href="${path}/">顶玺金融</a></span><span><a href="${path}/myaccount/toIndex.html">我的账户</a></span><span><a
					href="#">投资管理</a></span><span   id="ztclbSpanId"  ><a href="${path }/account/InvestManager/firsttenderindex.html">直通车列表</a></span>
			</div>     
			<div id="menu_centert">
				<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%>
				<!--我的账户左侧 结束 -->

				<!--我的账户右侧开始 -->
				<div id="menu_right" ></div>

			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<div>
		<%@ include file="/WEB-INF/page/common/footer.jsp"%>
	</div>
</body>
<script type="text/javascript">

/**
 * hujianpan
 * 切换统计数据,即已收和待收页签的切换
 */
function toggleList(firstTenderRealId,collectionStatus){
	 $("#un").removeClass("men_li");
	 $("#rec").removeClass("men_li");
		 if(collectionStatus == 'unRec'){			
			 $("#un").attr("class",'men_li');
			 loadFirstTenderPage('${basePath}/account/InvestManager/collectionFirst_record/1.html?firstTenderRealId='+firstTenderRealId+'&collectionStatus='+collectionStatus);
		 }else {					
			 $("#rec").attr("class",'men_li');
			 loadFirstTenderPage('${basePath}/account/InvestManager/collectionFirst_record/1.html?firstTenderRealId='+firstTenderRealId+'&collectionStatus='+collectionStatus);
		 }
}

$(function(){
	loadFirstTenderPage('${basePath}/account/InvestManager/queryFirstTenderReal/1.html?status=0');
});
//加载投标直通车列表

function loadFirstTenderPage(url){
	$.ajax({
		url : url,
		data : {},
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#menu_right").html(data);
			
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
	    }
	});
	
}

</script>

</html>
