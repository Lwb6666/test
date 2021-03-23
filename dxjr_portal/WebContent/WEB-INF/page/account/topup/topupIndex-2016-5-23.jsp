<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>我的帐户_充值_顶玺金融</title>
</head>

<body style="background:#f9f9f9;">
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<!--头部结束-->

<!--我的账户左侧开始-->
<div id="Container">
<div id="Bmain">
    <div class="title">
    	<span class="home"><a href="${path}/">顶玺金融</a></span>
    	<span><a href="${path }/myaccount/toIndex.html">我的账户</a></span>
    	<span>资金管理</span>
    	<span><a href="${path }/account/topup/toTopupIndex.html">充值</a></span>
  	</div>
	<!--我的账户左侧 开始 -->    
	<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%> 
	<!--我的账户左侧 结束 -->    
	<!--我的账户右侧开始 -->             
	<div class="lb_waikuang" >
		 <div class="men_title col2">
             <ul id="topupInnerDiv">
	             <li class="men_li"><a id="linkRecharge" href="javascript:void(0);" onclick="loadRechargeInner(this)">充值</a> </li>
	             <li id="rechargeRecord"><a href="javascript:void(0);" onclick="javascript:rechargeRecord(this);">充值记录 </a></li>
             </ul>
         </div> 
         <div id="main_content"></div>
	</div> 
	<!--我的账户右侧结束 -->
</div>
</div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
<script type="text/javascript">
$(function(){
	var errorCode = '${errorCode}';
	if(errorCode!=''){
		if(errorCode=="-1"){
			layer.msg('请先进行手机或邮箱认证！',2,5,function(){
				window.location.href="${path}/AccountSafetyCentre/safetyIndex.html";
			});
		} else if(errorCode=="-2"){
			layer.msg('请先进行实名认证！',2,5,function(){
				window.location.href="${path}/account/approve/realname/toRealNameAppro.html";
			});
		} else if(errorCode=="-3"){
			layer.msg('请先添加银行卡！',2,5,function(){
				window.location.href="${path}/bankinfo/toBankCard.html";
			});
		}
		return;
	}
	//加载充值页面
	loadRechargeInner(document.getElementById("linkRecharge"));
});
/**
 * 充值页面加载
 */
function loadRechargeInner(obj){
	//切换选项卡的时候样式处理
	processTabStyle(obj);
	$.ajax({
		url : '${basePath}/account/topup/toTopupInner.html',
		type : 'post',
		success : function(data){
			$("#main_content").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
	    }
	});
}


/**
 * 充值记录
 */
function rechargeRecord(obj){
	//切换选项卡的时候样式处理
	processTabStyle(obj);
	$.ajax({
		url : '${basePath}/account/topup/toRechargeRecordInner/1.html',
		data :{
			status:1
		} ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#main_content").html(data);
			
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);  // 弹出框
	    }
	});
}

/**
 * 查询充值记录
 */
function searchrechargeRecord(){
	//切换选项卡的时候样式处理
	$("#topupInnerDiv").find("li").removeClass("men_li");
	$("#rechargeRecord").addClass("men_li");
	$.ajax({
		url : '${basePath}/account/topup/toRechargeRecordInner/1.html',
		data :{
			status:$('#status').val(),
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val()
		} ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#main_content").html(data);
			
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);  // 弹出框
	    }
	});
}

/**
 * 充值记录列表翻页
 */
 function turnrechargePageParent(pageNo){
	 $.ajax({
			url : '${basePath}/account/topup/toRechargeRecordInner/'+pageNo+'.html',
			data :{
				status:$('#status').val(),
				beginTime:$("#beginTime").val(),
				endTime:$("#endTime").val()
			 }
			 ,
			type : 'post',
			dataType : 'text',
			success : function(data){
				$("#main_content").html(data);
				
			},
			error : function(data) {
				layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
		    }
		});
}
/**
 * 切换选项卡的时候样式处理
 */
function processTabStyle(obj){
	$("#topupInnerDiv").find("li").removeClass("men_li");
	$(obj).parent().addClass("men_li");
}
</script>
</html>
