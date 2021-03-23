<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional"WebContent/WEB-INF/page/transfer/transferSubscribe.jsp"//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>网站账户</title>
</head>
<body style="background:#fff;">
<div class="sf" style="margin-left:20px;">
	<div class="safebox550">
	<form action="" method="post" id="subscribeTransferForm">

	<input type="hidden" name="useMoney" id="useMoney" value="${account.useMoney}"/>
	<input type="hidden" name="transferid" id="transferid" value="${transfer.id}"/>
	<input type="hidden" id="effectiveTenderMoney" value="${effectiveTenderMoney }"/>
	<input type="hidden" name="platform" id="platform" value="1"/>
	<input type="hidden" name="sumAccount" id="sumAccount" value="${sumAccount }"/>
	<div class="tipone" style="margin-top:0px;line-height:10px;">&nbsp;&nbsp;</div>
		<dl>
			<dd><font color="#F00000">此债权剩余价值¥<fmt:formatNumber value="${transfer.accountSurplus }" pattern="#,##0.00" />
			，其中剩余本金¥<fmt:formatNumber value="${transfer.capital }" pattern="#,##0.00" />
			，待收利息¥<fmt:formatNumber value="${transfer.accountSurplus-transfer.capital}" pattern="#,##0.00" />
			</font></dd>
			<dd><span>转出价格：</span>¥<fmt:formatNumber value="${transfer.accountReal }" pattern="#,##0.00" /></dd>
			<dd><span>
                	<div class="iconfont2"  onmousemove="$(this).find('div').show();" onmouseout="$(this).find('div').hide();">
						<div class="poptip-content2" style="display: none;" >
							如果系数等于0.99，相当于投标奖励1%<br/>如果系数等于1.01，相当于多出1%投标
			            </div>
					</div>	
				转让系数：</span>${transfer.coef }
				&nbsp;&nbsp;
				<a style="color:red">
				<c:choose>
					<c:when test="${transfer.coef > 1}">溢价转让</c:when>
					<c:when test="${transfer.coef < 1}">折价转让</c:when>
					<c:otherwise>原价转让</c:otherwise>
				</c:choose>
				</a>
			</dd>
			<dd><span>原利率：</span><fmt:formatNumber value="${transfer.borrowApr }" pattern="#,##0.##"/>%</dd>
			<dd class="red"><span>转让后实际收益率：</span><fmt:formatNumber value="${transfer.nowApr }" pattern="#,##0.##"/>%</dd>
			<dd><span>账户余额：</span>¥<fmt:formatNumber value="${account.useMoney }"  pattern="#,##0.00" />&nbsp;&nbsp;<a href="${path}/account/topup/toTopupIndex.html" target="_parent">&nbsp;&nbsp;<font color="#00a7e5">我要充值</font></a></dd>
			<c:if test="${isExistCurAccount == true }">
			<dd><span>活期宝可用余额：¥</span><fmt:formatNumber value="${maxCurMoney}"  pattern="#,##0.00" />&nbsp;&nbsp;
			<input type="checkbox" name = "isUseCurMoney" id="isUseCurMoney" value="0" onclick="useCurMoney();" checked="checked"/><a href="javascript:useCurMoney();">使用活期宝</a>
			</dd>
			</c:if>
			<dd><span>剩余金额：</span>¥<fmt:formatNumber value="${alsoNeed }" pattern="#,##0.00" /></dd>
			
			<dd><span>购买金额：</span> 
				 <input type="text" name="tenderMoney" id="pay_money" autocomplete="off" style="width:100px" />元&nbsp;&nbsp;<a href="javascript:enterMomey();">自动输入购买金额</a>
			</dd>
			<c:if test="${transfer.bidPassword != null && transfer.bidPassword != ''}">		
			    <dd><span>定向密码：</span> 
               	    <input type="password" name="bidPassword" id="bidPassword" autocomplete="off" style="width:100px" />&nbsp;
            	</dd>
			</c:if>	
            <dd><span>交易密码：</span> 
                <input type="password" name="payPassword" id="payPassword" autocomplete="off" style="width:100px" />&nbsp;
            </dd>			
            <dd><span>验证码 ：</span> 
                <input type="text" name="checkCode" id="checkCode" size="4" maxlength="4" style="width:80px;"/>&nbsp;
            </dd>	
            <dd><span>&nbsp;</span> 
                <a href="javascript:loadimage();">
                	<img name="randImage" id="randImage" src="${basePath}/random.jsp" style="float: left;" border="0" align="middle" />
                </a>
            </dd> 
            <dd><span>&nbsp;</span><input class="safe_button01" type="button" value="确认认购" onclick="subscribe_transfer();" id="btnSubscribeTransfer" style="cursor:pointer;"></dd>       
		</dl>
</form>
</div>
</div>
<br/>
<c:if test='${isExistCurAccount == true }'>
<div class="tishiyu" style="">温馨提示：<br/>勾选“使用活期宝”后，当您可用余额不足时，系统会使用您活期宝可用余额投资。</div>
</c:if>
</body>
<script type="text/javascript">
//自动输入投标金额 | 参数：最大认购额度，已认购金额
function enterMomey(){
	$.ajax({
		url : '${basePath}/zhaiquan/findEffectiveTenderMoney/${transfer.id}.html',
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
	var effectiveTenderMoney = Number($("#effectiveTenderMoney").val());
	var lowestAccount = Number("${transfer.lowestAccount}");
	var sumAccount = Number("${sumAccount}");
	var mostAccount = Number("${transfer.mostAccount}");
	var alsoNeed = Number("${alsoNeed}");
	var alsoAccount = 0; 
	//alert(effectiveTenderMoney);
	//alert(sumAccount);
	if(effectiveTenderMoney<=0){
		$("#pay_money").val('');
		layer.msg("您的金额不符合要求，不能购买。",2,5);
	}else{
		alsoAccount = Number((mostAccount-sumAccount).toFixed(2));  //用户自己已购买金额余值
		//alert(alsoAccount);
		//alert(alsoNeed);
		if(effectiveTenderMoney<alsoAccount){
			alsoAccount=effectiveTenderMoney;
		}
		
		if(alsoAccount>=lowestAccount){
			$("#pay_money").val(alsoAccount);
		}else{
			if(alsoAccount == alsoNeed){
				$("#pay_money").val(alsoNeed);
			}else{
				layer.msg("您的剩余可购买金额为"+alsoAccount+"，不符合要求，不能购买。",2,5);
			}
		}
		//$("#pay_money").val(effectiveTenderMoney);
	}
	//if(effectiveTenderMoney>alsoAccount){
	//	$("#pay_money").val(alsoAccount);
	//}else{
	//	$("#pay_money").val(effectiveTenderMoney);
	//}
}

/**
 * 认购
 */
function subscribe_transfer(){
    if(!validateData()){
    	return;
    }
	if(layer.confirm("确定要认购吗？",function(){
		$("#btnSubscribeTransfer").removeAttr("onclick");
		$("#subscribeTransferForm").ajaxSubmit({
		    url : '${basePath}/zhaiquan/subscribeTransfer.html',
		    type: "POST",
		    success:function(data){
		    	if(data.code=="1"){
		    		layer.msg("认购成功！",1,1);
		    		window.parent.location.href="${path}/zhaiquan/${transfer.id}.html";
		    	}else{
		    		loadimage();
		    		if(data.message != ''){
		    			layer.msg(data.message,2,5);
		    		}
		    		$("#btnSubscribeTransfer").attr("onclick","subscribe_transfer()");
		    	}
		    },
			error : function() {
				loadimage();
				$("#btnSubscribeTransfer").attr("onclick","subscribe_transfer()");
				layer.msg("网络连接超时，请您稍后重试", 2, 5);
		    } 
		 });
	}));
}
/**
 * 刷新验证码
 */
function loadimage() {
	document.getElementById("randImage").src = "${basePath}/random.jsp?" + Math.random();
}
/**
 * 验证认购数据  useMoney
 */
function validateData(){
	var msg = "";
	var pay_money = Number($("#pay_money").val());
	var alsoNeed = Number("${alsoNeed}");
	var payPassword = $("#payPassword").val();
	var checkCode = $("#checkCode").val();
	var useMoney = Number($("#useMoney").val());
	
	var lowestAccount = Number("${transfer.lowestAccount}");
	var mostAccount = Number("${transfer.mostAccount}");
	var sumAccount = Number("${sumAccount}");
	var reg= /^\d+[\.]?\d{0,2}$/g;
	var zfdsReg = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;;//金额的正则表达式
	var numOrAbcReg = /^[A-Za-z0-9]+$/;

	if(pay_money==null || pay_money==""){
		msg = msg + "-购买金额未填写！<br/>";
	}else if(!zfdsReg.test(pay_money)){
		msg = msg + "-输入金额有误！<br/>";
	}else if(pay_money == 0){
		msg = msg + "-购买金额不能为0！<br/>";
	}else if(!reg.test(pay_money)){
		msg = msg + "-只能保留2位小数！<br/>";
	}else {
		if(pay_money < lowestAccount){
			if(alsoNeed < lowestAccount){
				if(pay_money = alsoNeed){
					
				}else{
					msg = msg + "-购买金额必须等于剩余金额<br/>";
				}
			}
			else{
				msg = msg + "-购买金额不能小于"+lowestAccount+"元！<br/>";
			}
			
		}
	}
	if(pay_money > mostAccount){
		msg = msg + "-购买金额不能大于最大认购额度！<br/>";
	}

	if(Number((pay_money +sumAccount).toFixed(2))  > mostAccount){
		msg = msg + "-累计购买金额不能大于最大认购额度！<br/>";
	}
	
	if(alsoNeed!= 0 && pay_money > alsoNeed){
		msg = msg + "-剩余可认购金额为"+alsoNeed+"！<br/>";
	}
	var maxCurMoney = Number("${maxCurMoney}");
	if($("#isUseCurMoney").val() == 1){
		if( new Number(useMoney + maxCurMoney) < lowestAccount &&  new Number(useMoney + maxCurMoney) < alsoNeed){
			msg = msg + "-您的余额不足！<br/>";
		}
		if(new Number(useMoney + maxCurMoney) < pay_money){
			msg = msg + "-账户余额小于购买金额！<br/>";
		}
	}else{
		if( useMoney < lowestAccount &&  useMoney<alsoNeed){
			msg = msg + "-您的余额不足！<br/>";
		}
		if(useMoney < pay_money){
			msg = msg + "-账户余额小于购买金额！<br/>";
		}
	}
	
    if(payPassword == null || payPassword == ""){
		msg = msg + "-交易密码未填写！<br/>";
	}
	if(checkCode==null || checkCode==""){
		msg = msg + "-验证码未填写！<br/>";
	}
	if('${!empty transfer.bidPassword}'=='true'){
		var bidPassword = $("#bidPassword").val();
		if(bidPassword == null || bidPassword == ""){
			msg = msg + "-认购密码未填写！<br/>";
		}
		if(!numOrAbcReg.test(bidPassword)){
			msg = msg + "-认购密码只能为数字或字母！<br/>";
		}
	}
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
		url : '${basePath}/zhaiquan/findEffectiveTenderMoney/${transfer.id}.html',
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
//-->
</script>
</html>