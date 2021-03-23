<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>顶玺金融互联网专业的P2P网站</title>
<link href="${basePath}/css/belstar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${basePath}/js/jquery-1.11.0.js" ></script>
<script type="text/javascript" src="${basePath}/js/jquery-migrate-1.2.1.js" ></script>
<script type="text/javascript" src="${basePath}/js/formatMoney.js"></script>
<script type="text/javascript" src="${basePath}/js/layer/layer.min.js" ></script>
</head>
<body>
<div id="user_mian">
<div id="user_mian_right">
   <div class="zjjl">
        <ul>
			<li id="topup"><a href="javascript:topupFun();" id="topupa">线上充值</a></li>
			<li id="toRechargeRecord"><a href="javascript:toRechargeRecord(1);" id="topuprecorda">充值记录</a></li>
			<li id="getcash" ><a href="javascript:getcashFun();" id="getcash">提现</a></li>
			<li id="toCashRecord"><a href="javascript:toCashRecord(1);" >提现记录</a></li>
			<li id="paymentDetail"><a href="javascript:paymentDetail(1);">资金记录</a></li>
			<li id="bankcardmanage"><a href="javascript:toBankCard();" >银行卡管理</a></li>
		</ul>
   </div>
   <div class="zjjl_list" id="user_right_main">
   </div>
</div>
</div>
<%@ include file="/WEB-INF/page/common/footer.jsp" %>
</body>
<script type="text/javascript">
$(function(){
	var type = '${type}';
	/** 线上充值 */
	if(type == 'topup'){
		topupFun();
	}
	/** 提现 */
	if(type == 'getcash'){
		getcashFun();
	}
	/**资金记录*/
	if(type == 'paymentRecord'){
		paymentDetail(1);
	}
	/**银行卡管理*/
	if(type == 'toBankCard'){
		toBankCard();
	}
	/**充值记录*/
	if(type == 'toRechargeRecord'){  
		toRechargeRecord(1);
	}
	/**提现记录*/
	if(type == 'toCashRecord'){   
		toCashRecord(1);  
	}
});

/**
 * 进入线上充值页面
 */
function topupFun(){
	$.post("${basePath}/account/topup/toTopup.html", {
	}, function(data) {
		setlicss();
		$("#topup").children("a").attr("style","background:#ffaa29; color:#fff;");
		$("#user_right_main").html(data); 
	});
}

//进入线下充值页面
function topdownFun(){
	$.post("${basePath}/newdxjr/account/topup/IsJudgeBank.html", {
	}, function(data) {
		if(data == 'fail'){
			layer.alert("您的初始交易密码为空，请先设置交易密码");
			return ;
		}
		if(data=='false'){
			layer.alert('您还没有设置银行卡,请先设置银行卡信息！');
			toChinabank();
		}else if(data=='true'){
			$.post("${basePath}/newdxjr/account/topup/toTopdown.html", {
			}, function(data) {
				setlicss();
				$("#topdown").children("a").attr("style","background:#ffaa29; color:#fff;");
				$("#user_right_main").html(data); 
			});
		}else{
			layer.alert("维护中,敬请谅解...");
		}
	});
	
}
/**
 * 进入提现页面
 */
function getcashFun(){
	$.post("${basePath}/myaccount/cashRecord/toGetcash.html", {
	}, function(data) {
		setlicss();
		$("#getcash").children("a").attr("style","background:#ffaa29; color:#fff;");
		$("#user_right_main").html(data);
	});
}

//进入充值记录页面
function toRechargeRecord(pageNum){
	$.post("${basePath}/myaccount/rechargeRecord/queryPageList/"+pageNum+".html", {
	}, function(data) {
		setlicss();
		$("#toRechargeRecord").children("a").attr("style","background:#ffaa29; color:#fff;");
		$("#user_right_main").html(data); 
	});
}

//进入提现记录页面
function toCashRecord(pageNum){
	$.post("${basePath}/myaccount/cashRecord/queryPageList/"+pageNum+".html", {
	}, function(data) {
		setlicss();
		$("#toCashRecord").children("a").attr("style","background:#ffaa29; color:#fff;");
		$("#user_right_main").html(data); 
	});
}
//进入资金记录页面
function paymentDetail(pageNum){
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var type = $("#type").val();
	$.post("${basePath}/newdxjr/myaccount/paymentDetails/"+pageNum+"/10.html", {
		startTime:startTime,endTime:endTime,type:type
	}, function(data) {
		setlicss();
		$("#paymentDetail").children("a").attr("style","background:#ffaa29; color:#fff;");
		$("#user_right_main").html(data); 
	});
}

//查询资金记录列表
function paymentDetailQuery(){
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var type = $("#type").val();
	$.post("${basePath}/newdxjr/myaccount/paymentDetails/1/10.html", 
		{startTime:startTime,endTime:endTime,type:type}, function(data) {
		setlicss();
		$("#paymentDetail").children("a").attr("style","background:#ffaa29; color:#fff;");
		$("#user_right_main").html(data); 
	});
}

//进入银行卡管理页面
function toBankCard(){
	/*$.post("${basePath}/newdxjr/account/topup/IsJudgePayPassword.html", {
	}, function(data) {
		if(data=='false'){
			layer.alert("您的初始交易密码为空，请先设置交易密码");
			$.layer({
				type : 2,
				fix : false,
				shade : [0.6, '#E3E3E3' , true],
				shadeClose : true,
				border : [10 , 0.7 , '#272822', true],
				title : ['',false],
				offset : ['100px',''],
				area : ['550px','350px'],
				iframe : {src : '${basePath}/member/go/pwd.html?redirectType=bank'},
				close : function(index){
					layer.close(index);
					$.post("${basePath}/newdxjr/account/topup/IsJudgePayPassword.html", {
					}, function(data) {
						if(data=='true'){
							window.open("${path}/newdxjr/account/topup/toTopupMain/toChinabank.html",'_parent');
						}
					});
				}
			});
		}else if(data=='true'){
			$.post("${basePath}/newdxjr/account/topup/toBankManger.html", {
			}, function(data) {
				setlicss();
				$("#bankcardmanage").children("a").attr("style","background:#ffaa29; color:#fff;");
				$("#user_right_main").html(data); 
			});
		}else{
			layer.alert("维护中,敬请谅解...");
		}
	});*/
	$.post("${basePath}/bankinfo/toBankCard.html", {
	}, function(data) {
		setlicss();
		$("#bankcardmanage").children("a").attr("style","background:#ffaa29; color:#fff;");
		$("#user_right_main").html(data); 
	});
}

/**
 * 将li中的current样式去掉
 */
function setlicss(){
	$("#topup").children("a").removeAttr("style");
	$("#topdown").children("a").removeAttr("style");
	$("#getcash").children("a").removeAttr("style");
	$("#toRechargeRecord").children("a").removeAttr("style");
	$("#toCashRecord").children("a").removeAttr("style");
	$("#paymentDetail").children("a").removeAttr("style");
	$("#bankcardmanage").children("a").removeAttr("style");
}

</script>
</html>
