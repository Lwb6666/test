<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
	<script type="text/javascript" src="${basePath}/js/location.js"></script>
    <script type="text/javascript" src="${path }/js/formValid.js?${version}"></script>
    <style type="text/css">
      .select {
	    line-height: 30px;
	    color: #333;
	    display: block;
	    text-align: left;
	}
    </style>
    <script type="text/javascript">
	$(function(){
		$('.tip-bottom').poshytip({
			className: 'tip-yellowsimple',
			showTimeout: 1,
			alignTo: 'target',
			alignX: 'center',
			alignY: 'bottom',
			offsetX: 15,
			offsetY: 10,
			allowTipHover: false,
		});
		$(".close").click(function(){
		$(".bankcard-tip").fadeOut();
		});
	});
	</script>
   <!--弹层star--->
	<div class="cont-word">
    	<div class="title"><h4>绑定银行卡</h4><a onclick="closeWin();" class="icon icon-close fr"></a></div>
        <div class="form-info-layer">
            <div class="form-col2">
                <label for="" class="colleft form-lable">手机号</label>
                 <input type="hidden" id="bankPhone" name="bankPhone"  value="${mobileAppro.mobileNum }"/>
                 <span class="fl money"><strong class="f14">${mobileAppro.securitymobileNum }</strong><i class="iconfont orange2 tip-bottom"  title="请与您银行卡预留手机保持一致">&#xe608;</i></span>
            </div>
            <div class="form-col2">
                <label for="" class="colleft form-lable">身份证号</label>
                 <span class="fl money"><strong class="f14">${realNameAppro.securityIdCardNo}</strong></span>
            </div>
            <div class="form-col2">
                <label for="" class="colleft form-lable">开户银行</label>
                <dl class="colright select" >
                 <select  id="bank" name="bank" class="colright form-inpyt-sm" style="width:205px;"></select>
                </dl>
            </div>
            <div class="form-col2">
                <label for="" class="colleft form-lable">银行卡号</label>
                
                <input type="text" id="cardNum" name="cardNum"  style="width:198px" class="colright form-inpyt-sm" placeholder="请输入银行卡号">
            </div>
            <div class="form-col2">
                <label for="" class="colleft form-lable">确认卡号</label>
                
                <input type="text"  id="verifycardNum" name="verifycardNum"  style="width:198px" class="colright form-inpyt-sm" placeholder="请确认银行卡号">
            </div>
            <div class="form-col2">
                <label for="" class="colleft form-lable">交易密码</label>
                
                <input type="password" style="width:198px" class="colright form-inpyt-sm" id="payPassword" name="payPassword" onblur="forbidBlank(this)" placeholder="请输入交易密码">
            </div>
            <div class="form-col2">
                <label for="" class="colleft form-lable">验证码</label>
                <input type="text"  style="width:90px" id="activeCode" class="colright form-inpyt-sm"  placeholder="请输入验证码">
                <p class="fl pdlr10 line32" id="sendActiveCode" ><span id="sendbtn"><a>发送验证码</a></span></p>
                <p class="fl pdlr10 line32" id="clockActiveCode" style="display: none;"><span class="bule" id="clock">180</span><span>秒后重发</span></p>
            </div>
             <div class="form-col2">
               <div class="f14 gary2">
               <p><span class="orange2">*</span> 绑定的银行卡必须是与注册实名一致的储蓄卡
                                   否则将提现失败</p>
                  <p><span class="orange2">*</span> 提现资金将汇入您在此绑定的银行卡</p>
              </div>
            </div>
            <div class="form-col2">
             <c:if test="${flagAppro==0 }">
                 <button type="button" class="remove icon-close" onclick="closeWin();">取消</button><button type="button" class="enter" id="btnSaveBank" onclick="saveBank();">确定</button>
			</c:if>
			<c:if test="${flagAppro==1 }">
			    <button type="button" class="remove icon-close" onclick="closeWin();">取消</button><button type="button" class="enter" id="btnSaveBank" onclick="saveBankAppro();">提交人工审核</button>
			</c:if>		
            </div>
         </div>
    </div> 
<!--弹层end---> 
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

//人工审核
function saveBankAppro(){
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
			url : '${basePath}/bankinfo/saveChinabankAppro.html',
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
				$("#btnSaveBank").attr("onclick","saveBankAppro()");
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
				$("#btnSaveBank").attr("onclick","saveBankAppro()");
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
	$("#sendActiveCode").hide();
	$("#clockActiveCode").show();
	$('#sendbtn').attr("disabled","disabled");
	setTimeout(function(){
		runtime = runtime-1;
		$('#clock').html(runtime);
		if(runtime>0){
			runclock();
		}else{
			$('#clock').html('180');
			$("#sendActiveCode").show();
			$("#clockActiveCode").hide();
			$('#sendbtn').removeAttr("disabled");
			runtime=180;
		}
	},1000);
}
</script>
</html>