<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>网站账户</title>
</head>
<body style="background:#fff;overflow: scroll;">
<div class="sf" >
	<div class="safebox550" style="width:520px;">
	<form action="" method="post" id="tenderBorrowForm">
	<input type="hidden" id="effectiveTenderMoney" value="${effectiveTenderMoney }"/>
	<input type="hidden" name="borrowid" value="${borrow.id}"/>
	<div class="tipone" style="margin-top:0px;">&nbsp;&nbsp;</div>
		<dl style="margin-top:-30px;">
			<dd><span style="padding-left:10px;"> 您的资产总额：¥</span><fmt:formatNumber value="${account.total }" pattern="#,##0.00" /></dd>
			<dd><span style="padding-left:10px;">您的可用余额：¥</span><fmt:formatNumber value="${account.useMoney }"  pattern="#,##0.00" />&nbsp;&nbsp;<a href="${path}/account/topup/toTopupIndex.html" target="_parent">&nbsp;&nbsp;我要充值</a></dd>
			<c:if test="${isExistCurAccount == true }">
			<dd><span style="padding-left:10px;">活期宝可用余额：¥</span><fmt:formatNumber value="${maxCurMoney}"  pattern="#,##0.00" />&nbsp;&nbsp;
			<input type="checkbox" name = "isUseCurMoney" id="isUseCurMoney" value="0" onclick="useCurMoney();" checked="checked"/><a href="javascript:useCurMoney();">使用活期宝</a>
			</dd>
			</c:if>
			<dd><span style="padding-left:10px;">当前年化利率：&nbsp;&nbsp;</span>${borrow.apr }%</dd>
			<dd><span style="padding-left:10px;">剩余金额：¥</span><fmt:formatNumber value="${remainMoney}"  pattern="#,##0.00" /></dd>
			<c:if test="${borrow.mostAccount > 0 }">
				<dd><span style="padding-left:10px;">最多投标总额：¥</span><fmt:formatNumber value="${borrow.mostAccount }" pattern="#,##0.00" /></dd>
			</c:if>
			<dd><span>投标总额：</span> 
				 <input type="text" name="tenderMoney" id="pay_money" autocomplete="off" style="width:100px" />元&nbsp;&nbsp;<a href="javascript:enterMomey();">自动输入投标金额</a>
			</dd>

			<c:if test="${borrow.bidPassword != null && borrow.bidPassword != ''}">		
			    <dd><span>定向密码：</span> 
               	    <input type="password" name="bidPassword" id="bidPassword" autocomplete="off" style="width:100px" />&nbsp;
            	</dd>
			</c:if>	
            <dd><span>交易密码：</span> 
                <input type="password" name="payPassword" id="payPassword" autocomplete="off" style="width:100px" />&nbsp;
            </dd>			
            <dd><span>验证码 ：</span> 
                <input type="text" name="checkCode" id="checkCode" size="4" maxlength="4" style="width:80px;"/>&nbsp;
                <a href="javascript:loadimage();">
                	<img name="randImage" id="randImage" src="${basePath}/random.jsp" style="" border="0" align="middle" />
                </a>
            </dd>	
            <dd><span>&nbsp;</span><input class="safe_button01" type="button" value="确认投标" onclick="tender_borrow();" id="btnTenderBorrow" style="cursor:pointer;"></dd>       
		</dl>
		<input type="hidden" name="${borrow.uuid }" value="${borrow.uuid}"/>	 
</form>
</div>
<br/>
<c:if test='${isExistCurAccount == true }'>
<div class="tishiyu" style="">温馨提示：<br/>勾选"使用活期宝"后，当您可用余额不足时，系统会使用您活期宝可用余额投资。</div>
</c:if>
</div>
</body>
<script type="text/javascript">
var _load;
//自动输入投标金额
function enterMomey(){
	$.ajax({
		url : '${basePath}/borrow/borrow/findEffectiveTenderMoney/${borrow.id}.html',
		data : {isUseCurMoney:$("#isUseCurMoney").val()},
		type : 'post',
		dataType : 'json',
		success : function(data){
			if(data != null){
				var effectiveTenderMoney = data;
				if(effectiveTenderMoney<=0){
					layer.msg("您的金额不符合要求，不能投标。",2,5);
				}else{
					$("#pay_money").val(effectiveTenderMoney);
				}
			}else{
				layer.msg("您的金额不符合要求，不能投标。",2,5);
			}
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		}
	});	
}

/**
 * 投标
 */
function tender_borrow(){
	var xs_tag = '${xs_tag}';
    if(!validateTenderData()){
    	return;
    }
	if(layer.confirm("确定要投标吗？",function(){
		$("#btnTenderBorrow").removeAttr("onclick");
		_load = layer.load('处理中..');
		$("#tenderBorrowForm").ajaxSubmit({
		    url : '${basePath}/borrow/borrow/tenderBorrow.html',
		    type: "POST",
		    success:function(msg){
		    	layer.close(_load);
		    	if(msg.code=="1"){
		    		layer.msg("投标成功！",1,1);
		    		if(xs_tag=='9'){
		    			// 新手专区  新手标
		    			window.parent.location.href="${path}/newPeopleArea/newPeopleAreaBiao.html";
		    		}else{
		    			window.parent.location.href="${path}/toubiao/${borrow.id}.html";
		    		}
		    	}else{
		    		loadimage();
		    		if(msg.message != ''){
		    			layer.msg(msg.message, 2, 5);
		    		}
		    		$("#btnTenderBorrow").attr("onclick","tender_borrow()");
		    	}
		    },
			error : function() {
				layer.close(_load);
				loadimage();
				$("#btnTenderBorrow").attr("onclick","tender_borrow()");
				layer.msg("网络连接超时，请您稍后重试", 2, 5);
		    } 
		 });
	},'',function(){
		$("#btnTenderBorrow").attr("onclick","tender_borrow()");
	}));
}
/**
 * 刷新验证码
 */
function loadimage() {
	document.getElementById("randImage").src = "${basePath}/random.jsp?" + Math.random();
}
/**
 * 验证投标数据
 */
function validateTenderData(){
	var msg = "";
	var pay_money = Number($("#pay_money").val());
	var alsoNeed = Number("${alsoNeed}");
	var payPassword = $("#payPassword").val();
	var checkCode = $("#checkCode").val();
	
	var lowestAccount = Number("${borrow.lowestAccount}");
	var effectiveTenderMoney = Number($("#effectiveTenderMoney").val());
	var reg= /^\d+[\.]?\d{0,2}$/g;
	var zfdsReg = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;;//金额的正则表达式

	if(pay_money==null || pay_money==""){
		msg = msg + "-投标金额未填写！<br/>";
	}else if(!zfdsReg.test(pay_money)){
		msg = msg + "-输入金额有误！<br/>";
	}else if(pay_money==0){
		msg = msg + "-投标金额不能为0！<br/>";
	}else if(!reg.test(pay_money)){
		msg = msg + "-只能保留2位小数！<br/>";
	}else {
		var accountUseMoney = Number("${account.useMoney}");
		var maxCurMoney = Number("${maxCurMoney}");
		if($("#isUseCurMoney").val() == 1){
			if(new Number(accountUseMoney + maxCurMoney) <= 0){
				msg = msg + "-您的可用余额不足！<br/>";
			}
		}else{
			if(accountUseMoney <= 0){
				msg = msg + "-您的可用余额不足！<br/>";
			}
		}
		if(lowestAccount > effectiveTenderMoney && pay_money != effectiveTenderMoney){
			msg = msg + "-投标金额只能为"+effectiveTenderMoney+"元！<br/>";
		}
		if(effectiveTenderMoney >= lowestAccount && pay_money < lowestAccount){
			msg = msg + "-投标金额不能少于"+lowestAccount+"元！<br/>";
		}
	}
	if(alsoNeed!= 0 && pay_money > alsoNeed){
		msg = msg + "-最大投标金额为"+alsoNeed+"！<br/>";
	}
    if(payPassword == null || payPassword == ""){
		msg = msg + "-交易密码未填写！<br/>";
	}
	if(checkCode==null || checkCode==""){
		msg = msg + "-验证码未填写！<br/>";
	}
	if('${!empty borrow.bidPassword}'=='true'){
		var bidPassword = $("#bidPassword").val();
		if(bidPassword == null || bidPassword == ""){
			msg = msg + "-定向标密码未填写！<br/>";
		}
	}
	
	//验证红包投资金额
// 	var _redId = $('#redId').val();
// 	var _tenderMoney = $("#pay_money").val();
// 	if(_redId>0 && _tenderMoney>0){
// 		var _redUseMoney = parseInt($('#redHid'+_redId).val());
// 		if(_tenderMoney<_redUseMoney){
// 			msg = msg +"-投资金额不满足红包使用条件！<br/>";
// 		}
// 	}
	
	
	if(msg!=""){
		layer.msg(msg,2,5);
		return false;
	}
	return true;
}
/**
 * 使用活期宝金额
 */
function useCurMoney(){
	var isUseCurMoney = $("#isUseCurMoney").val();
	if(isUseCurMoney == 0){
		$("#isUseCurMoney").val(1);
		$("#isUseCurMoney").attr("checked","checked");
	}else{
		$("#isUseCurMoney").val(0);
		$("#isUseCurMoney").removeAttr("checked");
	}
	$.ajax({
		url : '${basePath}/borrow/borrow/findEffectiveTenderMoney/${borrow.id}.html',
		data : {isUseCurMoney:$("#isUseCurMoney").val()},
		type : 'post',
		dataType : 'json',
		success : function(data){
			$("#effectiveTenderMoney").val(data);
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		}
	});
}
$(function() {
	useCurMoney();//2015.12.18 默认勾选活期宝
});

</script>
</html>