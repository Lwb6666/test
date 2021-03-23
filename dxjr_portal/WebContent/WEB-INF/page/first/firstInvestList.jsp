<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>理财直通车_p2p网贷理财平台_直通车专区-顶玺金融官网</title>
	<meta name="keywords" content="P2P网络投资理财, P2P网贷理财,直通车专区" />
	<meta name="description" content="投标直通车是顶玺金融推出的一种投标功能，目的是简化出借者的投标流程，提高资金使用率，为投资人提供快捷、有效、安全、的理财。如果你想了解顶玺金融理财直通车或者P2P网贷理财平台，详情请登陆www.dxjr.com。">
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<script type="text/javascript" src="${basePath}/js/init.js"></script>
<script type="text/javascript" src="${basePath}/js/raphael.js"></script>

</head>

<body>
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp"%>
<!--头部结束-->
<!--内容开始-->
<div id="Bmain">
	<div class="title"><span class="home"><a href="${path}/">顶玺金融</a></span><span><a href="javascript:void(0);">我要投资</a></span><span><a href="${path}/zhitongche.html"> 直通车专区</a></span>
	
			<span class="fr">
			    <a href="${path}/toubiao.html">[普通投标]</a>
				<a href="${path }/zhaiquan.html"   >[债权转让]</a>
			</span>
			
	</div>
    <div id="rz_main">
    	<div class="rztitle">筛选投资项目</div>
        <div class="rz_borrow1">   
        	<div class="tbproject">
	            <table>
	                <tr height="35">
		                <td class="rz_font">开通状态：</td>
		                <td><span id="unlimit" class="tbp_btn selected"><a href="javascript:setParam('type','')">不限</a></span></td>
		                <td><span id="willOpen" class="tbp_btn"><a href="javascript:setParam('type','1')">即将开通</a></span></td>
		                <td><span id="opening" class="tbp_btn"><a href="javascript:setParam('type','2')">开通未满</a></span></td>
		                <td><span id="opened" class="tbp_btn"><a href="javascript:setParam('type','3')">开通已满</a></span></td>
	                </tr> 
	            </table>                                                                               
             </div>
        </div>
	</div>
    <div class="tbsort">
        <div class="tbproject">
	        <table width="82%" border="0">
		        <tr>
			        <td width="14%" class="rz_font">排序方式：</td>
			        <td width="19%">
			        &nbsp;&nbsp;<span class="tbtype"><a href="javascript:setParam('orderParam','periods')">期数   <label id="periodsOrderBy"></label></a></span>
			        &nbsp;&nbsp;&nbsp;&nbsp;<span class="tbtype"><a href="javascript:setParam('orderParam','progress')">进度<label id="progressOrderBy"></label></a></span></td>
			        <td width="36%"></td>
			        <td width="3%">&nbsp;</td>
			        <td width="28%">&nbsp;</td>
		        </tr>
	        </table>
    	</div> 
    </div>   
    <div id="first_invest_list">
    </div>
</div>
<!--内容结束-->
<%@ include file="/WEB-INF/page/common/footer.jsp"%>
<form id="firstInvestForm" action="">
	<input type="hidden" name="type" id="type" value="${type}"/>
	<input type="hidden" name="orderParam" id="orderParam" value="${orderParam}"/>
	<input type="hidden" name="orderType" id="orderType" value="${orderType}"/>
	<input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>
	<input type="hidden" name="pageSize" id="pageSize" value="10"/>
</form>
</body>
<script type="text/javascript">
$(function(){
	firstInvestList();
});

function setParam(paramType,paramValue){
	if(paramType == 'orderParam'){
		$("#periodsOrderBy").html("");
		$("#progressOrderBy").html("");
		var orderParam =$("#orderParam").val();
		if(orderParam == '' || orderParam != paramValue){
			$("#orderParam").val(paramValue);
			$("#orderType").val("desc");
			if(paramValue == 'periods'){
				$("#periodsOrderBy").html("&darr;");
			}
			if(paramValue == 'progress'){
				$("#progressOrderBy").html("&darr;");
			}
		}
		if(orderParam == paramValue){
			var orderType = $("#orderType").val();
			if(orderType == 'desc'){
				$("#orderType").val("asc");
				if(paramValue == 'periods'){
					$("#periodsOrderBy").html("&uarr;");
				}
				if(paramValue == 'progress'){
					$("#progressOrderBy").html("&uarr;");
				}
			}
			if(orderType == 'asc'){
				$("#orderType").val("desc");
				if(paramValue == 'periods'){
					$("#periodsOrderBy").html("&darr;");
				}
				if(paramValue == 'progress'){
					$("#progressOrderBy").html("&darr;");
				}
			}
			$("#orderParam").val(paramValue);
		}
	}
	if(paramType == 'type'){
		$("#unlimit").removeClass("selected");
		$("#willOpen").removeClass("selected");
		$("#opening").removeClass("selected");
		$("#opened").removeClass("selected");
		if(paramValue == ''){
			$("#unlimit").addClass("selected");
		}
		if(paramValue == '1'){
			$("#willOpen").addClass("selected");
		}
		if(paramValue == '2'){
			$("#opening").addClass("selected");
		}
		if(paramValue == '3'){
			$("#opened").addClass("selected");
		}
	}
	$("#"+paramType).val(paramValue);
	$("#pageNo").val(1);
	
	firstInvestList();
}
/**
 * 直通车专区列表
 */
function firstInvestList(){
	var type = $("#type").val();
	var orderParam = $("#orderParam").val();
	var orderType = $("#orderType").val();
	var pageNo = $("#pageNo").val();
	var pageSize = $("#pageSize").val();
	$.ajax({
		url : '${basePath}/searchFirstList.html',
		data :{
			type:type,orderParam:orderParam,orderType:orderType,pageNo:pageNo,pageSize:pageSize
		} ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#first_invest_list").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
	    }
	});
}

/**
 * 列表翻页
 */
 function turnPageParent(pageNo){
	 $("#pageNo").val(pageNo);
	 firstInvestList();
}
</script>