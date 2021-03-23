<%@page import="com.dxjr.utils.DateUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/page/stockright/common/public.jsp"%>
<meta charset="utf-8">
<title>我的内转账户_挂单记录成交记录</title>
</head>
<body>
<div class="main-offsetHeight">
	<!-- header start   -->
	<%@ include file="/WEB-INF/page/stockright/common/header.jsp"%>
	<!-- header end     -->
	
<!-- main start	 -->
<div class="product-wrap">
<form id="collection"  action="" method="post" > 
<input type="hidden" value="${entrust.entrustType}" id="J_entrustType"/>
<input type="hidden" value="${entrust.id}" id="J_entrustId"/>

	<div class="grid-1100">
        <div class="product-deatil clearfix ">
            <h1 class="f16"><span>挂单状态：
            <c:choose>
				<c:when test="${entrust.status==1 }">已挂单</c:when>
				<c:when test="${entrust.status==2 }">部分成交</c:when>
				<c:when test="${entrust.status==3 }">全部成交</c:when>
				<c:when test="${entrust.status==-1 }">已撤销</c:when>
			</c:choose>
            </span><span class="gary2 ml10">(${entrust.entrustCode})</span></h1>
            <div class="gkzt clearboth">
            	<ul>
                	<li class="baifen20"><span>委托类型：</span>
                	<c:choose>
						<c:when test="${entrust.entrustType==1 }">认购</c:when>
						<c:when test="${entrust.entrustType==2 }">转让</c:when>
					</c:choose>
                	</li>
                    <li class="baifen35"><span>委托份额：</span>
                     <c:choose>
						<c:when test="${entrust.amount>0}"><fmt:formatNumber value="${entrust.amount}" pattern="#,#00"/></c:when>
						<c:when test="${entrust.amount<=0}">0</c:when>
					</c:choose>份
					</li>
                    <li class="baifen25"><span>成交份额：</span>
                    <c:choose>
						<c:when test="${entrust.dealAmount>0}"><fmt:formatNumber value="${entrust.dealAmount}" pattern="#,#00"/></c:when>
						<c:when test="${entrust.dealAmount<=0}">0</c:when>
					</c:choose>份
					</li>
                    <li class="baifen20"><span>未成交份额：</span>
                    <c:choose>
						<c:when test="${entrust.residueAmount>0}"><fmt:formatNumber value="${entrust.residueAmount}" pattern="#,#00"/></c:when>
						<c:when test="${entrust.residueAmount<=0}">0</c:when>
					</c:choose>
                                       份</li>
                    
                    <li class="baifen20"><span>委托价格：</span>${entrust.price}元/份</li>
                    <li class="baifen35"><span>委托时间：</span><fmt:formatDate value="${entrust.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></li>
                    <li class="baifen25"><span>成交总价：</span>
                  	<c:choose>
						<c:when test="${entrust.dealTotalPrice>0}"><fmt:formatNumber value="${entrust.dealTotalPrice}" pattern="#,#00.00"/></c:when>
						<c:when test="${entrust.dealTotalPrice<=0}">0.00</c:when>
					</c:choose>
                                       元</li>
                    <li class="baifen20"><span>交易服务费：</span>
                    <c:choose>
						<c:when test="${entrust.dealFee>0}">${entrust.dealFee}</c:when>
						<c:when test="${entrust.dealFee<=0}">0.00</c:when>
					</c:choose>
                    	元</li>
                </ul>
            </div>
            <div id="J_datalist">
               
            </div>
                    
        </div>
        
    </div>
    </form>
</div>

</div>
<!-- main end	 -->
	
	<!-- footer start   -->
	<%@ include file="/WEB-INF/page/stockright/common/footer.jsp"%>
	<!-- footer end     -->
</body>
<script type="text/javascript">
var entrustType;
var entrustId;
$(function(){  
	 entrustType = $("#J_entrustType").val();
	 entrustId = $("#J_entrustId").val();
	 findPage(1);
});
function findPage(pageNum) {
	$.ajax({
		url : '${basePath}/stockSeller/findEntrustDetailList.html',
		data : {
			pageNum : pageNum,
			entrustType:entrustType,
			id:entrustId
		},
		type : 'post',
		dataType : 'text',
		success : function(data) {
			$("#J_datalist").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！");
		}
	});
}

//下载
function stockDownloadPDF(dealId) {
	url = '${basePath }/stockDeal/stockDownloadPDF/' + dealId+ '.html';
	layer.confirm("你确定要下载吗?",function (){
		$("#collection").attr("action", url);
		$("#collection").submit();
		layer.msg("下载成功!", 2, 9);
	});
}
</script>
</html>
