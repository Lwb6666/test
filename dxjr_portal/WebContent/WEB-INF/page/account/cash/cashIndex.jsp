<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public4.jsp" %>
<title>我的帐户_提现_顶玺金融</title>
</head>

<body>
<!--头部开始-->
<%@ include file="/WEB-INF/page/common/header.jsp" %>
<!--头部结束-->
<div class="clear"></div>
<!--我的账户左侧开始-->
<div id="myaccount">
    <div class="topmenu">
        <ul>
            <li class="active"><a href="${basePath}/myaccount/toIndex.html">账户总览</a></li>
            <li><a href="${basePath}/dingqibao/myAccount.html">投资管理</a></li>
            <li><a href="${basePath}/lottery_use_record/lott_use_record.html">账户奖励</a></li>
            <li><a href="${path }/AccountSafetyCentre/safetyIndex.html">安全中心</a></li>
        </ul>
    </div>
    <div class="wraper mt20">
        <!--table-->
        <div class="prduct-menu">
            <div class="menu-tbl">
                <ul class="col3">
                    <li class="active" id="linkRecharge" onclick="gotoTakeCash(this)">非存管提现 </li>
                    <c:if test="${cardType == null or cardType == '' or cardType == 1}">
                        <li id="linkCustody" onclick="loadCustodyInner(this)">存管提现</li>
                    </c:if>
                    <li id="rechargeRecord" onclick="javascript:rechargeRecord1();">提现记录</li>
                </ul>
            </div>
				<div id="containerRight" class="recharge change" style="clear: both">
				</div>
			</div>
    </div>
</div>

<!--弹层star--->
<%-- 没有绑定银行卡 --%>
<div class="reveal-modal"  id="notBound">
	<div class="cont-word">
		<p style="padding:60px 0;"><span class="f16">您还未绑定银行卡，绑定后才能提现</span></p>
	<div class="form-col2">
		<button type="button" class="remove " onclick="javascript:$('#notBound').hide();">取消</button><button type="button" class="enter">去绑定</button>
	</div>
	</div>
</div>
<!--转可提--->
<div class="reveal-modal"   id="judgeToDraw1"></div>
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
				window.location.href="${basePath}/AccountSafetyCentre/safetyIndex.html";
			});
		}else if(errorCode=="-1"){
			layer.msg('请行进行实名认证！',1,5,function(){
				window.location.href="${basePath}/AccountSafetyCentre/safetyIndex.html";
			});
		}else if(errorCode=="-4"){
			layer.msg('请先设置交易密码！',1,5,function(){
				window.location.href="${basePath}/AccountSafetyCentre/safetyIndex.html";
			});
		}else if(errorCode=="-6"){
			layer.msg('请先绑定银行卡！',1,5,function(){
				window.location.href="${basePath}/AccountSafetyCentre/safetyIndex.html";
			});
		}else if(errorCode=="-7"){
			 layer.alert('您的银行卡正在审核中，请耐心等待！客服会在两个工作日内处理或联系客服热线400-000-0000');
			//加载提现页面
			gotoTakeCash(document.getElementById("linkTakeCash"));
		}else if(errorCode=="-8"){
			layer.alert('您的银行卡未通过审核，请7天后重新申请或联系客服热线400-000-0000');
			//加载提现页面
			gotoTakeCash(document.getElementById("linkTakeCash"));
		} 
		return;
	}	
	//加载提现页面
	gotoTakeCash(document.getElementById("linkTakeCash"));
});
/* 添加银行卡 */
function toAddBankCard() {
	$.post("${basePath}/bankinfo/isPhoneValidated.html", {},
			function(data) {
				if (data == 'no') {
					layer.alert('您还没有手机认证！');
				} else if (data == 'yes') {
					$.layer({
						type : 2,
						fix : false,
						shade : [ 0.6, '#E3E3E3', true ],
						shadeClose : true,
						border : [ 10, 0.7, '#272822', true ],
						title : [ '', false ],
						offset : [ '50px', '' ],
						area : [ '1000px', '600px' ],
						iframe : {
							src : '${basePath}/bankinfo/addBankCard.html'
						},
						close : function(index) {
							layer.close(index);
						}
					});
				}
			});
}
/**
 * 充值记录
 */
function rechargeRecord(obj){
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
     * 资金存管提现页面加载
     */
    function loadCustodyInner(obj) {
        window.clearTimeout(window.tt);
        $.ajax({
            url : '${basePath}/myaccount/cashRecord/toCustodyInner.html',
            type: 'post',
            success: function (data) {
                $("#containerRight").html(data);
            },
            error: function (data) {
                layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);
            }
        });
    }

/**
 *加载转可提记录页面
 */
function loadturnDrawRecord(obj){
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
  * 默认提现记录查询
  */
 function rechargeRecord1() {
     $.ajax({
         url: '${basePath}/account/topup/toCashRecordInnerMain.html',
         type: 'post',
         dataType: 'text',
         success: function (data) {
             $("#containerRight").html(data);
         },
         error: function (data) {
             layer.msg("网络连接异常，请刷新页面或稍后重试！", 1, 5);  // 弹出框
         }
     });
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
