<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<title>网站账户</title>
</head>
<body style="overflow-y:hidden;background:#fff;">
<div class="sf sfbox" >
	<div class="safebox550" style="width:500px;">
	<form action="" method="post" id="tenderBorrowForm">
	<input type="hidden" id="effectiveTenderMoney" value="${effectiveTenderMoney }"/>
	<input type="hidden" name="firstBorrowId" value="${firstBorrow.id}"/>
	
	<div style=" padding-top:0px;">
		<dl>
			<dd ><span  style="padding-left:8px; ">您的资产总额：¥</span><fmt:formatNumber value="${account.total }" pattern="#,##0.00" /></dd>
			<dd><span  style="padding-left:8px; ">您的可用余额：¥</span><fmt:formatNumber value="${account.useMoney }"  pattern="#,##0.00" />&nbsp;&nbsp;<a href="${path}/account/topup/toTopupIndex.html" target="_parent">&nbsp;&nbsp;我要充值</a></dd>
			<c:if test="${isExistCurAccount == true }">
			<dd><span>活期宝可用余额：¥</span><fmt:formatNumber value="${maxCurMoney}"  pattern="#,##0.00" />
			&nbsp;&nbsp;<input type="checkbox" name = "isUseCurMoney" id="isUseCurMoney" value="0" onclick="useCurMoney();" checked="checked"/><a href="javascript:useCurMoney();">使用活期宝</a>
			</dd>
			</c:if>
			<dd><span style="padding-left:8px; ">最多开通总额：¥</span><fmt:formatNumber value="${firstBorrow.mostAccount }" pattern="#,##0.00" /></dd>
			<dd><span>开通总额：</span> 
				 <input type="text" name="tenderMoney" id="pay_money" autocomplete="off" style="width:100px" />元&nbsp;&nbsp;<a href="javascript:enterMomey();">自动输入开通金额</a>
			</dd>
<!-- 			<dd> -->
<!-- 				<span>使用红包：</span> &nbsp;  -->
				
<%-- 				<c:if test="${empty reds}"> --%>
<!-- 			             无可用红包 -->
<%--                 </c:if>  --%>
<%-- 				<c:if test="${not empty reds}">  --%>
<!-- 				<select   name="redId" style="color: width: 150px;">  -->
<%-- 					<c:forEach items="${reds }" var="r">  --%>
<%-- 						<option value="${r.id }">  --%>
<%-- 						<fmt:formatNumber value="${r.money }" pattern="###" />元  --%>
<%-- 						(满<fmt:formatNumber value="${r.money*100 }" pattern="###" />元可用)  --%>
<!-- 						</option>  -->
<%-- 				</c:forEach>  --%>
<!-- 				<option value="0">不使用</option>  -->
<!-- 				</select>  -->
<%-- 				</c:if>  --%>
				
<!-- 			</dd> -->
			<c:if test="${firstBorrow.bidPassword != null && firstBorrow.bidPassword != ''}">		
			    <dd><span>开通密码：</span> 
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
            <dd><span>&nbsp;</span>
                <input type="button" value="确认开通" onclick="tender_borrow();" id="btnTenderBorrow" style="cursor:pointer; background:#00a7e5; color:#fff; padding:0 20px; height:30px; border:0; font-size:14px; display:block;"/>
            </dd>             
		</dl>
		</div>
</form>
</div>
</div>
<br/>
<c:if test='${isExistCurAccount == true }'>
<div class="tishiyu" style="">温馨提示：<br/>勾选“使用活期宝”后，当您可用余额不足时，系统会使用您活期宝可用余额投资。</div>
</c:if>
</body>
<script type="text/javascript">
//自动输入开通金额
function enterMomey(){
	$.ajax({
		url : '${basePath }/zhitongche/findEffectiveTenderMoney/${firstBorrow.id}.html',
		data : {isUseCurMoney:$("#isUseCurMoney").val()},
		type : 'post',
		dataType : 'json',
		success : function(data){
		 
			if(data != null){
				var effectiveTenderMoney = data;
				
				if(effectiveTenderMoney<=0){
					layer.msg("您的金额不符合要求，不能开通。",2,5);
				}else{
					$("#pay_money").val(parseInt(effectiveTenderMoney/1000)*1000);
				}
			}else{
				layer.msg("您的金额不符合要求，不能开通。",2,5);
			}
		},
		error : function(data) {
			layer.msg("网络连接异常，请刷新页面或稍后重试！", 2, 5);
		}
	});
}

/**
 * 开通
 */
function tender_borrow(){
    if(!validateTenderData()){
    	return;
    }
	if(layer.confirm("确定要开通吗？",function(){
		$("#btnTenderBorrow").removeAttr("onclick");
		$("#tenderBorrowForm").ajaxSubmit({
		    url : '${basePath }/zhitongche/tender/${firstBorrow.id}.html',
		    type: "POST",
		    success:function(msg){
		    	if(msg.code=="1"){
		    		layer.msg("开通成功！",1,1);
		    		window.parent.location.href="${path }/zhitongche/${firstBorrow.id}.html";
		    	}else{
		    		loadimage();
		    		if(msg.message != ''){
		    			layer.msg(msg.message, 2, 5);
		    		}
		    		$("#btnTenderBorrow").attr("onclick","tender_borrow()");
		    	}
		    },
			error : function() {
				$("#btnTenderBorrow").attr("onclick","tender_borrow()");
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
 * 验证开通数据
 */
function validateTenderData(){
	var msg = "";
	var pay_money = Number($("#pay_money").val());
	var alsoNeed = Number("${alsoNeed}");
	var payPassword = $("#payPassword").val();
	var checkCode = $("#checkCode").val();
	
	var lowestAccount = Number("${borrow.lowestAccount}");
	var mostAccount = Number("${firstBorrow.mostAccount}");
	var effectiveTenderMoney = Number($("#effectiveTenderMoney").val());

	var zfdsReg = /^[1-9]\d*$/;//金额的正则表达式 正整数 
	if(pay_money==null || pay_money==""){
		msg = msg + "-开通金额未填写！<br/>";
	}else if(!zfdsReg.test(pay_money)){
		msg = msg + "-开通金额必须是正整数！<br/>";
	}else if(parseInt(pay_money)<1000 || parseInt(pay_money) % 1000 !=0){
		msg = msg + "-开通金额必须是千元的整数倍！<br/>";
	}else {
		if(lowestAccount > effectiveTenderMoney){
			if(pay_money!=effectiveTenderMoney){
				msg = msg + "-开通金额只能为"+parseInt(effectiveTenderMoney/1000)/10+"万元！<br/>";
			}
		}else if(pay_money < lowestAccount){
			msg = msg + "-开通金额不能少于"+parseInt(lowestAccount/1000)/10+"万元！<br/>";
		}else if(effectiveTenderMoney<=0){
			msg = msg + "-余额不足！<br/>";
		}else if(pay_money>effectiveTenderMoney){
			msg = msg + "-开通金额不能大于"+parseInt(effectiveTenderMoney/1000)/10+"万元！<br/>";
		}
	}
	if(alsoNeed!= 0 && pay_money > alsoNeed){
		//如果
		if(mostAccount!=0 && alsoNeed>mostAccount){
			msg = msg + "-你可开通的最大金额为"+parseInt(mostAccount/1000)/10+"万元！<br/>";
		}else{
			msg = msg + "-你可开通的最大金额为"+parseInt(alsoNeed/1000)/10+"万元！<br/>";
		}
	}
	if(payPassword == null || payPassword == ""){
		msg = msg + "-交易密码未填写！<br/>";
	}
	if(checkCode==null || checkCode==""){
		msg = msg + "-验证码未填写！<br/>";
	}
	if('${!empty firstBorrow.bidPassword}'=='true'){
		var bidPassword = $("#bidPassword").val();
		if(bidPassword == null || bidPassword == ""){
			msg = msg + "-开通密码未填写！<br/>";
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
		url : '${basePath }/zhitongche/findEffectiveTenderMoney/${firstBorrow.id}.html',
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