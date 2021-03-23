<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>顶玺金融互联网专业的P2P网站</title>
<link href="${basePath}/css/Style.css" type="text/css" rel="stylesheet" />
<link href="${basePath}/css/belstar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${basePath}/js/jquery.form.js"></script>

<script type="text/javascript">
$(function(){
	$('#sendbtn').bind('click',forsendmsg);
	//设置文本框样式
	if('${bankInfo.cardNum}' != 'undefined' && '${bankInfo.cardNum}' != ''){
		$("#cardNum").val('${bankInfo.securityCardNum}');
		$("#bankName").attr("disabled","disabled");
		$("#branch").attr("disabled","disabled");
		$("#cardNum").attr("disabled","disabled");
	}
	//设置手机号格式
	var mobileNum = '${mobileAppro.mobileNum}';
	if(mobileNum != 'undefined' && mobileNum != ''){
		var str1 = mobileNum.substr(0,3);
		var str3 = mobileNum.substring(mobileNum.length-4);
		var str2 = "";
		for(var i=0; i<mobileNum.length-7;i++){
			str2 = str2 + "*";
		}
		$("#mobile").html(str1+str2+str3);
	};
});

/**
 * 修改并设置手机格式
 */
function modify(){
		$("#modify").hide();
		$("#mobileCheck").show();
		$("#save").show();
		$("#bankName").removeAttr("disabled");
		$("#branch").removeAttr("disabled");
		$("#cardNum").removeAttr("disabled");
		$("#bankName").val('');
		$("#branch").val('');
		$("#cardNum").val('');
}
/**
 * 发送原手机号验证码
 */
function forsendmsg(){
	$.ajax({
		url : '${basePath}/bankinfo/sendMsg.html',
		data : {},
		type : 'post',
		dataType : 'json',
		success : function(data){
	    	if(data.code=="1"){
	    		layer.alert(data.message,1);
	    		run();
	    	}else{
				layer.alert(data.message);		    		
	    	}
		},
		error : function(data) {
			layer.msg('网络连接异常，请刷新页面或稍后重试！', 1, 5);
	    }
	});
}
/**
 * 刷新过期时间
 */
var runtime=0;
function run(){
	runtime=180;
	runclock();
}

function runclock(){
	$('#sendbtn').attr("disabled","disabled");
	setTimeout(function(){
		runtime = runtime-1;
		$('#clock').html(runtime);
		if(runtime>0){
			runclock();
		}else{
			$('#clock').html('');
			$('#sendbtn').removeAttr("disabled");
			runtime=180;
		}
	},1000);
}

/**
 * 保存银行信息
*/
function saveBank(){
	//验证提交的表单数据是否正确 
	if(!validateBankinfoData()){
		return;
	}
	//提交表单
	$("#bankinfoForm").ajaxSubmit({
	    url : '${basePath }/bankinfo/updateBankcard.html',
	    type: "POST",
	    success:function(msg){
	    	if(msg=="success"){
	    		layer.alert("银行卡设置保存成功！" , 1, "温馨提示");
	    		window.parent.toBankCard();
	    	}else{
	    		layer.alert(msg);
	    	}
	    },
		error : function() {
			layer.msg('网络连接超时,请您稍后重试.', 1, 5);
	    } 
	 });
}
/**
*  验证银行卡提交的数据是否正确
*/
function validateBankinfoData(){
	var msg = "";
	var pattern= /^[0-9]+$/;
	var bankName = $("#bankName").val();
	if(bankName.length==0){
		msg = msg + "-开户行名称不能为空！\n";
	}else{
		if(bankName.length > 100){
			msg = msg + "-开户行名称长度不能超过100个字符！\n";
		}
	}
	var branch = $("#branch").val();
	if(branch.length==0){
		msg = msg + "-开户支行名称不能为空！\n";
	}else{
		if(branch.length > 100){
			msg = msg + "-开户支行名称长度不能超过100个字符！\n";
		}
	}
	var cardNum = $("#cardNum").val();
	if(cardNum.length == 0){
		msg = msg + "-银行卡号不能为空！\n";
	}else{
		if(!pattern.test(cardNum)){
			msg = msg + "-银行卡号长度必须是数字！\n";
		}
		if(cardNum.length > 20){
			msg = msg + "-银行卡号长度不能超过20个字符！\n";
		}
	}
	var activeCode = $("#activeCode").val();
	if(activeCode.length==0){
		msg = msg + "-验证码不能为空！\n";
	}
	var payPassword = $("#payPassword").val();
	if(payPassword.length == 0){
		msg = msg + "-交易密码不能为空！\n";
	}
	if(msg.length > 0){
		layer.alert(msg);
		return false;
	}
	return true;
}
</script>
</head>

<body>
<div class="user_help">注意：必须填写您实名认证的真实姓名及身份证号所开户的银行卡，否则提现时资金可能无法正常到账。</div>
<div id="normal">
 <form action="${basePath }/bankinfo/saveChinacard.html" method="post" id="bankinfoForm" name="bankinfoForm">
	<input type="hidden" name="id" id="id" value="${bankInfo.id }" />
	<div class="user_right_gl">
		<span class="ut_gl"> </span>
	</div>
	<div class="ut_list"  style="color: #FF0000;"> </div>
	<div class="user_right_gl">
	     <span class="ut_gl"><font color="red">*</font>开户行名称：</span>
		 <span class="ut_ingl">
		     <input type="text" name="bankName" id="bankName" size='30' value="${bankInfo.bankName }" 
		     	maxlength="40" style="width:200px;height:20px;"/>
		 </span>
	</div>
    <div class="user_right_gl">
    	 <span class="ut_gl"><font color="red">*</font>开户支行名称：</span>
	 	 <span class="ut_ingl"><input type="text" name="branch" id="branch" size='30' value="${bankInfo.branch }" 
	 	 		maxlength="40" style="width:200px;height:20px;"/>
	 	 </span>
 	</div>
	<div class="ut_list"  style="color: #FF0000;"> **分行****支行**	分行处或营业部（如：上海分行杨浦支行控江路分理处）</div>
	<div class="ut_list" >如果您无法确定，可电话联系您的开户行客服</div>
	<div class="user_right_gl">
	     <span class="ut_gl"><font color="red">*</font>银行卡号：</span>
	     <span class="ut_ingl">
	     <input type="text" name="cardNum" id="cardNum" size='30' maxlength="20" style="width:200px;height:20px;"/>
	     &nbsp;&nbsp;（为了您的银行账户安全，只显示您的银行账户前四位及后三位数字）</span>
	</div>
   <div class="ut_list" >特别提醒：个人银行账户基本信息务必填写正确，否则您的提现将存在风险。如果要修改，请将必填信息补全。</div>
	    <div class="ut_list" id="modify">
	   	    <input type="button" onclick="modify();" value="修改" size="30" class="user_buttom" style="cursor:pointer;"/>
		</div>
		<%--发送手机验证码 --%>
		<div id="mobileCheck" style="display:none;">
			<div>&nbsp;&nbsp;</div>
			<div class="user_right_gl">
		        <span class="ut_gl"><font color="red">*</font>手机验证码：</span>
		  		<span class="ut_ingl" style="height:25px;">
		  			<input type="text" id="activeCode" name="activeCode" maxlength="6" style="width:100px;height:20px;margin-top:0px;"/> &nbsp;&nbsp;<span id="clock"></span>
		  			<input type="button" name="sendbtn" id="sendbtn" value="发送验证码" style="cursor:pointer;"/>
		  		</span>&nbsp;&nbsp; 
			</div>
			<div class="ut_list" >(您的手机号为：<span id="mobile"></span>)</div>	 
	   	</div>	
		 <%--提交表单 --%>	
		<div id="save" style="display:none;">
			<div class="user_right_gl">
		        <span class="ut_gl"><font color="red">*</font>交易密码：</span>
		  		<span class="ut_ingl">
		  		<input type="password" id="payPassword" name="payPassword" style="width:100px;height:20px;" maxlength="12"/> 
		  		</span>
			</div>
			<div class="ut_list">
		   	    <input type="button" onclick="saveBank();" value="保存" size="30" class="user_buttom" style="cursor:pointer;"/>
		   	</div>
	   	</div>
 </form>
 </div>
</body>
</html>
