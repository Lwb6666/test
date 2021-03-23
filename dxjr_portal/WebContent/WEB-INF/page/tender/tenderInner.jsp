<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://com.demo.util.datejstl/tags" prefix="date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page import = "com.dxjrweb.statics.Enumeration" %>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>顶玺金融互联网专业的P2P网站</title>
<link href="${basePath}/css/form_borrow.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

$(document).ready(function(){
	/*var use_money = ${account.use_money };
	if(use_money>0){
		if(use_money<0.01){
			$('#format_use_money').html('¥ 0.00  元');
		}else{
			layer.alert(use_money);
			$('#format_use_money').html('¥ ' + to_Number(use_money,2,"ROUND_DOWN") + ' 元');
		}
	}*/
})

//tenderBorrow.disabled = true;
function loadimage() {
	var random = Math.random();
	document.getElementById("randImage").src = "${basePath}/random.jsp?" + random;
}
//投标
function tender_borrow(){
	var msg = "";
	var pay_money = Number($("#pay_money").val());
	var payPassword = $("#payPassword").val();
	var bidPassword = $("#bidPassword").val();
	var checkCode = $("#checkCode").val();
	var alsoNeed = Number("${alsoNeed}");
	if(alsoNeed<0.01){
		alsoNeed = 0.01;
	}else{
		alsoNeed = alsoNeed.toFixed(2);
	}
	
	
	
	
	var lowestAccount = Number("${borrow.lowest_account}");
	var effectiveTenderMoney = Number($("#effectiveTenderMoney").val());
	effectiveTenderMoney=effectiveTenderMoney.toFixed(2);
	var reg= /^\d+[\.]?\d{0,2}$/g;
	var zfdsReg = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;;//金额的正则表达式
	if(pay_money==null || pay_money==""){
		msg = msg + "-投标金额未填写！\n";
	}else if(!zfdsReg.test(pay_money)){
		msg = msg + "-输入金额有误！\n";
	}else if(pay_money==0){
		msg = msg + "-投标金额不能为0！\n";
	}else if(!reg.test(pay_money)){
		msg = msg + "-只能保留2位小数！\n";
	}else {
		if(lowestAccount > effectiveTenderMoney){
			if(pay_money!=effectiveTenderMoney){
				msg = msg + "-投标金额只能为"+effectiveTenderMoney+"元！\n";
			}
		}else if(pay_money < lowestAccount){
			msg = msg + "-投标金额不能少于"+lowestAccount+"元！\n";
		}
	}

	if(alsoNeed!= 0 && pay_money > alsoNeed){
		msg = msg + "-最大投标金额为"+alsoNeed+"！\n";
	}
	if(payPassword == null || payPassword == ""){
		msg = msg + "-交易密码未填写！\n";
	}
	if(bidPassword == null || bidPassword == ""){
		msg = msg + "-定向标密码未填写！\n";
	}
	if(checkCode==null || checkCode==""){
		msg = msg + "-验证码未填写！\n";
	}
	if(msg!=""){
		layer.alert(msg);
	}else{
		var tenderBorrow = document.getElementById("tenderBorrow");
		
		var tenderClose = document.getElementById("tenderClose");
		
		if(layer.confirm("确定要投标吗？",function(){
			tenderBorrow.href="javascript:void(0)";
			//tenderClose.href="javascript:void(0)";
			$.post("${basePath}/newdxjr/investment/invest/tender/${borrow.id}.html",{
				tenderMoney:pay_money,payPassword:payPassword,bidPassword:bidPassword,checkCode:checkCode
			},function(data){
				tenderBorrow.href="javascript:tender_borrow()";
				//tenderClose.href="javascript:tender_close()";
				if(data=="OK,Rocky.J!"){
					layer.alert("投标成功！" , 1, "温馨提示");
					window.open("${path}/newdxjr/investment/invest/toInvest.html?id=${borrow.id}","_parent");
				}else{
					layer.alert(data);
					var random = Math.random();
					document.getElementById("randImage").src = "${basePath}/random.jsp?" + random;
				}
			});
		}));
		
		
	}
}

function tender_close(){
	window.open("${path}/newdxjr/investment/invest/toInvest.html?id=${borrow.id}","_parent");
}

function changePayMoney(){
	//投标有效金额
	var effectiveTenderMoney = Number($("#effectiveTenderMoney").val());
	effectiveTenderMoney=effectiveTenderMoney.toFixed(2);
	var pay_money = $("#pay_money").val();
	if(pay_money > effectiveTenderMoney){
		layer.alert("当前账户投标有效金额为：￥"+effectiveTenderMoney);
		$("#pay_money").val(effectiveTenderMoney);
	}
}
//自动输入投标金额
function enterMomey(){
	var effectiveTenderMoney = Number($("#effectiveTenderMoney").val());
	//layer.alert(effectiveTenderMoney);
	effectiveTenderMoney=to_Number(effectiveTenderMoney,2,"ROUND_DOWN");
	if(effectiveTenderMoney==0){
		layer.alert("您的金额不符合要求，不能投标。")
	}else{
		$("#pay_money").val(effectiveTenderMoney);
	}
}




function to_Number(num,precision,round_type){
	var rt_value;
	var numStr = num.toString(); 
	var numList = numStr.split('.');
	if(numList.length>1){
		if(round_type=="ROUND_DOWN"){
			rt_value = Number(numList[0]+'.'+numList[1].substring(0,precision));
		}
	}else if(numList.length==1){
		if(round_type=="ROUND_DOWN"){
			rt_value = Number(numList[0]+'.00');
		}
	}else{
		rt_value = Number(0);
	}
	
	return rt_value;
}
</script>
</head>

<body>
<input type="hidden" id="effectiveTenderMoney" value="${effectiveTenderMoney }"/>
	<div id="tc_main">
	    <div class="tc_main_a">确认投标金额</div>
		<div class="tc_main_d"> <span class="tc_main_e"> 您的资产总额：¥ </span> <span class="tc_main_b" id="total"><fmt:formatNumber value="${account.total }" pattern="#,#00.00"/></span>         </div>
		<div class="tc_main_d"> <span class="tc_main_e"> 您的可用余额：¥ </span> <span class="tc_main_b" id="use_money"><fmt:formatNumber value="${account.use_money }" pattern="#,#00.00"/><a href="${path}/newdxjr/account/topup/toTopupMain/topup.html" target="_parent">&nbsp;&nbsp;我要充值</a></span>        </div>
		<div class="tc_main_d"> <c:if test="${borrow.most_account < 1 }"><span class="tc_main_e">当前年化利率：</span> <span class="tc_main_b">${borrow.apr }%</span></c:if>
		<c:if test="${borrow.most_account > 0 }"><span class="tc_main_e"> 最多投标总额：${borrow.most_account}&nbsp;&nbsp;</span><span class="tc_main_b"> 当前年化利率： ${borrow.apr }%</span></c:if>    </div>
	
		<div class="tc_main_d"> <span class="tc_main_e"><font color="red">*</font>投标总额  ：</span> <span class="tc_main_b"><input type="text" name="money" id="pay_money"  class="input_border" value="" size="15" autocomplete="off"  maxlength="20" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"/>元&nbsp;&nbsp;<a href="javascript:enterMomey();">自动输入投标金额</a></span></div>
		<div class="tc_main_d"> <span class="tc_main_e"><font color="red">*</font>交易密码  ：</span> <span class="tc_main_b"><input type="password" name="payPassword" id="payPassword"   class="input_border" value="" size="15" autocomplete="off"  maxlength="12" />&nbsp;</span></div>
		<c:if test="${borrow.bid_password != null && borrow.bid_password != ''}">
		<div class="tc_main_d"> <span class="tc_main_e"><font color="red">*</font>定向标密码：</span> <span class="tc_main_b"><input type="password" name="bidPassword" id="bidPassword"   class="input_border" value="" size="15" autocomplete="off"  maxlength="12" />&nbsp;</span></div>
		</c:if>
		<c:if test="${borrow.bid_password == null || borrow.bid_password == ''}">
		<input type="hidden" id="bidPassword" value="no"/>
		</c:if>
		<div class="tc_main_d"><span class="tc_main_e"><font color="red">*</font>验证码 ：</span><span class="tc_main_b"><input type="text" name="checkCode" id="checkCode" size="4" maxlength="4" style="width:80px;"/>&nbsp;</span></div>
		<div class="tc_main_d"><span class="tc_main_e">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="tc_main_b"><a href="javascript:loadimage();"><img name="randImage" id="randImage" src="${basePath}/random.jsp" style="float: left;" border="0" align="middle" /></a></span></div>
		<div class="tc_main_a">
			<a  id="tenderBorrow" href="javascript:tender_borrow();"><img src="${basePath}/dxjrweb/images/tc_tj.jpg" width="77" height="33" border="0" /></a>
			&nbsp;&nbsp;
			<!--  <a  id="tenderClose" href="javascript:tender_close();"><img src="${basePath}/dxjrweb/images/tc_tj.jpg" width="77" height="33" border="0" /></a>-->
		</div>
		<script type="text/javascript">
			$("#total").html(formatMoney('${account.total}')+" 元");
			$("#use_money").html(formatMoney('${account.use_money}')+" 元");
		</script>
 	</div>
<!--用户中心的主栏目 结束-->
</body>
</html>
