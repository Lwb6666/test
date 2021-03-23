<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>我的帐户_提现_顶玺金融</title>
</head>

<body style="background:#f9f9f9;">
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<!--头部结束-->

<!--我的账户左侧开始-->
<div id="Container">
<div id="Bmain">
    <div class="title">
    	<span class="home"><a href="${path}/">顶玺金融</a></span><span><a href="${path}/myaccount/toIndex.html">我的账户</a></span><span><a href="#">资金管理</a></span><span><a href="${path }/myaccount/cashRecord/toCashIndex.html">提现</a></span>
  	</div>
	<!--我的账户左侧 开始 -->    
	<%@ include file="/WEB-INF/page/account/myAccount_leftMenu.jsp"%> 
	<!--我的账户左侧 结束 -->    
	<!--我的账户右侧开始 -->             
	<div class="lb_waikuang" >
		 <div class="men_title col3">
             <ul id="topupInnerDiv">
                 <li class="men_li"><a id="linkTakeCash" href="javascript:void(0);" onclick="gotoTakeCash(this)">提现</a> </li>
                 <li id="cashredord"><a  href="javascript:void(0);" onclick="loadcashRecord(this)">提现记录 </a></li>
                 <li><a href="javascript:void(0);" onclick="loadturnDrawRecord(this)">转可提记录 </a></li>	             
             </ul>
         </div> 
         <div class="clear"></div>
         <div id="containerRight" ></div>
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
		if(errorCode=="-5"){
			layer.msg('无法进行该操作！',1,5,function(){});
		} if(errorCode=="-3"){
			layer.msg('请先进行手机认证！',1,5,function(){
				window.location.href="${path }/account/approve/mobile/mobileforinsert.html";
			});
		}else if(errorCode=="-1"){
			layer.msg('请行进行实名认证！',1,5,function(){
				window.location.href="${path}/account/approve/realname/toRealNameAppro.html";
			});
		}else if(errorCode=="-4"){
			layer.msg('请先设置交易密码！',1,5,function(){
				window.location.href="${path}/account/safe/toSetPayPwd.html";
			});
		}
		
		return;
	}	
	//加载提现页面
	gotoTakeCash(document.getElementById("linkTakeCash"));
});

/**
 * 充值记录
 */
function rechargeRecord(obj){
	//切换选项卡的时候样式处理
	processTabStyle(obj);
	$.ajax({
		url : '${basePath}/account/topup/toRechargeRecordInner/1.html',
		data :{
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
			 }
			 ,
			type : 'post',
			dataType : 'text',
			success : function(data){
				$("#main_content").html(data);
				
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
		    }
		});
}

/**
 *加载转可提记录页面
 */
function loadturnDrawRecord(obj){
	//切换选项卡的时候样式处理
	processTabStyle(obj);
	$.ajax({
		url : '${basePath}/account/topup/toDrawLogRecord/1.html',
		data :{
		} ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#containerRight").html(data);
			
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
	    }
	});
}

/**
 * 加载
 */
function loadcashRecord(obj){
	//切换选项卡的时候样式处理
	processTabStyle(obj);
	$.ajax({
		url : '${basePath}/account/topup/toCashRecordInner/1.html',
		data :{
			status:2
		} ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#containerRight").html(data);
			
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
	    }
	});
}
/**
 * 提现记录查询
 */
function searchcashRecord(){
	//切换选项卡的时候样式处理
	$("#topupInnerDiv").find("li").removeClass("men_li");
 	$('#cashredord').addClass("men_li");
	$.ajax({
		url : '${basePath}/account/topup/toCashRecordInner/1.html',
		data :{
			status:$('#status').val(),
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val()
		} ,
		type : 'post',
		dataType : 'text',
		success : function(data){
			$("#containerRight").html(data);
			
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
	    }
	});
}
/**
 * 提现记录翻页
 */
 function turncashPageParent(pageNo){
	 $.ajax({
			url : '${basePath}/account/topup/toCashRecordInner/'+pageNo+'.html',
			data :{
				status:$('#status').val(),
				beginTime:$("#beginTime").val(),
				endTime:$("#endTime").val()
			 }
			 ,
			type : 'post',
			dataType : 'text',
			success : function(data){
				$("#containerRight").html(data);
				
			},
			error : function(data) {
				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
		    }
		});
}
 /**
  * 转可提记录翻页
  */
  function turnDrawPageParent(pageNo){
 	 $.ajax({
 			url : '${basePath}/account/topup/toDrawLogRecord/'+pageNo+'.html',
 			data :{
 			 }
 			 ,
 			type : 'post',
 			dataType : 'text',
 			success : function(data){
 				$("#containerRight").html(data);
 				
 			},
 			error : function(data) {
 				layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
 		    }
 		});
 }
 
 /**
 * 提现申请
 */
 function gotoTakeCash(obj){
	//切换选项卡的时候样式处理
	processTabStyle(obj);
	$.ajax({
		url : '${basePath}/myaccount/cashRecord/toGetcash.html',
		type : 'post',
		success : function(data){
			$("#containerRight").html(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
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
 /**
  * 判断充值金额是否为非负浮点数
  */
 function checkFloat(pay_money){
 	var re = /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;//判断浮点数为非负浮点数 
     if (!re.test(pay_money))
     {
         return false;
     }else{
     	return true;
     }
 }
</script>
</html>
