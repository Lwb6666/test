<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>[顶玺金融理财产品]个人投资理财平台_互联网金融理财-顶玺金融 </title>
<%@ include file="/WEB-INF/page/common/public3.jsp"%> 
<meta name="keywords" content="顶玺金融理财,投资理财平台,金融理财,顶玺金融理财产品,投资理财攻略;顶玺金融,理财产品,互联网理财,金融理财产品,投资理财,投资理财产品" />
<meta name="description" content="顶玺金融（www.dxjr.com），一个专注于消费金融与房产抵押贷款的中国社科院网贷评级A级的理财平台。致力于为投资理财用户和贷款用户两端搭建安全、规范、透明的互联网金融平台。投资理财用户可通过顶玺金融官网官方网站进行散标投资、定期宝、活期宝、购买债权转让等方式进行投资获得稳定收益。">
</head>

<body>
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp"%>

<div class="product-wrap wrapperbanner">
	<div class="grid-1100">
       <div class="tz-detail">
           <img src="${path}/images/v5/detalibanner.png?version=<%=version%>" alt="个人理财产品"/>
           <ul class="col4"><li><a href="${path}/dingqibao.html" class="active">定期宝</a></li><li><a href="${path}/curInController.html">活期宝</a></li><li><a href="${path}/toubiao.html" >散标投资</a></li><li><a href="${path}/zhaiquan.html">债权转让</a></li></ul>
       </div>
	</div>
</div>
<div class="product-wrap">
	<div class="grid-1100">
		<div class="product-deatil clearfix ">
			<h1 class="f16 bule">定期宝投资列表</h1>
            <div class="tz-dqb clearfix">
                <ul class="col4 center">
                    <li><div class=" pdtb10 center"><span class="gc-img-wap tz-icon01"></span></div><h2 class="f20 oren">${totalJoinCounts}次</h2><h4 class="f14">累计加入人次</h4></li>
                    <li><div class=" pdtb10 center"><span class="gc-img-wap tz-icon02"></span></div><h2 class="f20 oren"><fmt:formatNumber value="${totalAccountYes}" pattern="#,##0.##" />元</h2><h4 class="f14">累计金额</h4></li>
                    <li><div class=" pdtb10 center"><span class="gc-img-wap tz-icon03"></span></div><h2 class="f20 oren"><fmt:formatNumber value="${totalInterest}" pattern="#,##0.##" />元</h2><h4 class="f14">已累计为用户赚取</h4></li>
                    <li><div class=" pdtb10 center"><span class="gc-img-wap tz-icon04"></span></div><h2 class="f20 oren">${fn:replace(totalAccountInUseRate,'%','')}%</h2><h4 class="f14">资金利用率</h4></li>
                </ul>
             </div>
             <div id="fixBorrowList"></div>
		</div>
	</div>
</div>
<div class="clearfix bompd60"></div>
<%@ include file="/WEB-INF/page/common/footer.jsp"%>
</body>

<script type="text/javascript">

	
	$(document).ready(function() {
		searchFixBorrowListStart(1);
	});

	function searchFixBorrowListStart(pageNum) {
		$.ajax({
			url : '${basePath}/dingqibao/queryFixBorrowList.html',
			data : {
				pageNum : pageNum,
				pageSize : 10
			},
			type : 'post',
			dataType : 'text',
			success : function(data) {
				$("#fixBorrowList").html(data);
			},
			error : function(data) {
				alert("网络连接异常，请刷新页面或稍后重试！");
			}
		});
	}
</script>
</html>
