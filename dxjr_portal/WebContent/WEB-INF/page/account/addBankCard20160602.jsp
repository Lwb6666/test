<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/page/common/public.jsp"%>
<script type="text/javascript" src="${basePath}/js/location.js"></script>
<script type="text/javascript" src="${path }/js/formValid.js?${version}"></script>
</head>
<body>
 <!--弹出添加银行卡框开始-->   
         <div class="sfmain">
         <div class="sf">
          <p class="safe_p"style="background:none;border-bottom:none">添加银行卡</p>     
           <div class="safebox550">
            <dl>
				<dd><span>姓名：</span><p>${realNameAppro.securityRealName}</p></dd>
				<dd><span>身份证：</span><p>${realNameAppro.securityIdCardNo}</p></dd>
				<dd><span>开户行：</span><select class="select" id="bank" name="bank" style="width:308px; margin-top: 0px;box-shadow: 1px 1px 5px #e7e7e7 inset;"></select></dd>
				<dd><span>银行卡号：</span><input type="text" id="cardNum" name="cardNum" style="width:300px" /></dd>
				<dd><span>确认卡号：</span><input type="text" id="verifycardNum" name="verifycardNum" style="width:300px" /></dd>
				<dd><span>手机号：</span><input type="hidden" id="bankPhone" name="bankPhone" style="width:300px" value="${mobileAppro.mobileNum }"/>${mobileAppro.securitymobileNum }(请与您银行卡预留手机保持一致)</dd>
				<dd><span>平台交易密码：</span><input type="password" id="payPassword" name="payPassword" onblur="forbidBlank(this)"
				placeholder="平台交易密码" style="width:300px" /></dd>
				<dd>
					<span>手机验证码：</span>
					<input type="text" id="activeCode" />
					<input class="sbtn" type="button" name="button" id="sendbtn"  value="发送验证码"  style="cursor:pointer;"/>
					<input class="sbtn"   id="clock" type="button" name="button"  value="180秒后重发" disabled="disabled" />
				</dd>
			</dl>
			<div class="gg_btn"><input type="button" value="提交" id="btnSaveBank" onclick="saveBank();" style="cursor:pointer;"/></div>             
          </div>
          </div>
          <div class="tip">
             <div class="safebox550">
     <h3>温馨提示：</h3>
     <!-- <p>1.****分行**********支行******  分行处或营业部（如：上海分行杨浦支行控江路分理处），如果您无法确定，可电话联系您的开户行客服</p> -->
     <p>1.为了您的银行账户安全，只显示您的银行账户前四位及后三位数字 </p>
     <p>2.个人银行账户基本信息务必填写正确，否则您的提现将存在风险。如果要修改，请将必填信息补全  </p>     
     </div>
     </div>
     </div>
     <!--弹出添加银行卡框结束--> 
</body>
<script type="text/javascript">
$(function(){
	$('#sendbtn').bind('click', forsendmsg);
 	populateBank();
});

//银行列表
function populateBank() {
	var _load = layer.load('处理中..');
	var bank = $("#bank");
	bank.empty(); 
	bank.append($("<option>").val('').text(''));
	$.ajax({
		url : '${basePath}/bankinfo/queryBankList.html',
		data : {'district' : ''},
		type : 'post',
		dataType :"json",
		success : function(result) {
			layer.close(_load);
			$.each(result, function(index, item) {
				bank.append($("<option>").val(item.bankCode).text(item.bankName));
			});
			//populateBranch();
		},
		error : function(result) {
			layer.close(_load);
			layer.msg('网络连接超时,请您稍后重试.', 1, 5);
			//populateBranch();
	    }
	});
}

function saveBank(){
	var bankCode = $("#bank").val();
	if(bankCode == '') {
		layer.msg("-开户银行不能为空！\n", 1, 5);
		return;
	}
	
	var cardNum = $.trim($("#cardNum").val());
	if(cardNum == '') {
		layer.msg("-银行卡号不能为空！\n", 1, 5);
		return;
	}
	var bankPhone = $.trim($("#bankPhone").val());
	if(bankPhone == '') {
		layer.msg("-银行预留手机号不能为空！\n", 1, 5);
		return;
	}
	if(cardNum.length > 20) {
		layer.msg("-银行卡号过长！\n", 1, 5);
		return;
	}
	
	var verifycardNum = $.trim($("#verifycardNum").val());
	if(verifycardNum == '') {
		layer.msg("-确认银行卡号不能为空！\n", 1, 5);
		return;
	}
	if(cardNum.replace('/%/g',"") != verifycardNum.replace('/%/g',"")) {
		layer.msg("-两次银行卡号输入不一致！\n", 1, 5);
		return;
	}
	
	var payPassword = $("#payPassword").val();
	if(payPassword == '') {
		layer.msg("-交易密码不能为空！\n", 1, 5);
		return;
	}
	
 	var activeCode = $("#activeCode").val();
	if(activeCode == ''){
		layer.msg("-手机验证码不能为空！\n");
		return;
	}
	
	layer.confirm("确认绑定?", function() {
		$("#btnSaveBank").removeAttr("onclick");
		var branchKey = $("#branchKey").val();
		var activeCode = $("#activeCode").val();
		var _load = layer.load('处理中..');
		$.ajax({
			url : '${basePath}/bankinfo/saveChinabank.html',
			data : {
				'bankCode' : bankCode,
				'cardNum' : cardNum,
				'payPassword' : payPassword,
				'activeCode':activeCode,
				'bankPhone' : bankPhone
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				$("#btnSaveBank").attr("onclick","saveBank()");
				layer.close(_load);
				if (data.code == '0') {
					layer.msg(data.message, 1, 5);
					return;
				} else {
					layer.msg('保存成功', 1, 1, function() {
						runtime = 0;
						window.parent.location.href = window.parent.location.href;
					});
				}
			},
			error : function(data) {
				$("#btnSaveBank").attr("onclick","saveBank()");
				layer.close(_load);
				layer.msg("网络连接异常，请刷新页面或稍后重试！");
		    }
		});
	});
}

//发送短信
function forsendmsg(){

	$.post("${basePath}/account/topup/sendMsg.html", {
	}, function(data) {
		if (data.code == '0') {
			layer.msg(data.message);
		} else {
			layer.msg(data.message, 1, 1);
			run();
		}
	});
}
var runtime=0;
function run(){
	runtime=180;
	runclock();
}
function runclock(){
	$('#sendbtn').attr("disabled","disabled");
	setTimeout(function(){
		runtime = runtime-1;
		$('#clock').val(runtime+"秒后重发");
		if(runtime>0){
			runclock();
		}else{
			$('#clock').val('180秒后重发');
			$('#sendbtn').removeAttr("disabled");
			runtime=180;
		}
	},1000);
}
</script>
</html>